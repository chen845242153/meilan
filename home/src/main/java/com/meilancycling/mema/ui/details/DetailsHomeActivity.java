package com.meilancycling.mema.ui.details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.mapbox.mapboxsdk.Mapbox;
import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.customview.dialog.AskDialog;
import com.meilancycling.mema.databinding.ActivityDetailsHomeBinding;
import com.meilancycling.mema.db.AuthorEntity;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.network.bean.request.QueryMotionInfoRequest;
import com.meilancycling.mema.network.bean.request.UpdateMotionRequest;
import com.meilancycling.mema.network.download.FileDownloadCallback;
import com.meilancycling.mema.ui.common.FullLoadingFragment;
import com.meilancycling.mema.ui.common.NoNetworkFragment;
import com.meilancycling.mema.ui.record.ShareImageActivity;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.network.bean.request.DeleteMotionRequest;
import com.meilancycling.mema.network.bean.request.UpMotionInfoRequest;
import com.meilancycling.mema.network.bean.response.MotionDetailsResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.customview.dialog.RecordDetailsDialog;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.ui.setting.StravaBean;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.StatusAppUtils;
import com.meilancycling.mema.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 11:09 AM
 */
public class DetailsHomeActivity extends BaseActivity implements View.OnClickListener {
    private ActivityDetailsHomeBinding mActivityDetailsHomeBinding;
    private MapDetailsFragment mMapDetailsFragment;
    private FullLoadingFragment mFullLoadingFragment;
    private NoNetworkFragment mNoNetworkFragment;
    private ListDetailsFragment mListDetailsFragment;
    private ChartDetailsFragment mChartDetailsFragment;
    public static MotionDetailsResponse mMotionDetailsResponse;
    public int motionType;
    private int motionId;
    public String isCompetition;
    public boolean haveMap;
    private boolean isShow;
    /**
     * 当前选择
     */
    private int position;
    /**
     * 是否显示列表
     */
    private boolean isShowChart;

    private AuthorEntity stravaBean;
    private MotionDetailsResponse.MotionCyclingResponseVoBean motionCyclingResponseVoBean;

    /**
     * @param motionId 运动id
     */
    public static void enterDetailsHome(Context context, int motionId) {
        Intent intent = new Intent(context, DetailsHomeActivity.class);
        intent.putExtra("motionId", motionId);
        context.startActivity(intent);
    }

    public void showGuide() {
        isShow = true;
        StatusAppUtils.setColor(this, ContextCompat.getColor(this, R.color.guide_bg));
        mActivityDetailsHomeBinding.viewGuide.setVisibility(View.VISIBLE);
        mActivityDetailsHomeBinding.viewBottom.setBackgroundColor(ContextCompat.getColor(this, R.color.guide_bg));
        mActivityDetailsHomeBinding.viewGuideBottom.setVisibility(View.VISIBLE);
        mActivityDetailsHomeBinding.fakeStatusBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.guide_bg));
    }

    public void hideGuide() {
        isShow = false;
        StatusAppUtils.setColor(this, ContextCompat.getColor(this, R.color.white));
        mActivityDetailsHomeBinding.viewGuide.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.viewBottom.setBackgroundColor(ContextCompat.getColor(this, R.color.line_colors));
        mActivityDetailsHomeBinding.viewGuideBottom.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.fakeStatusBarView.setBackgroundColor(0);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        mActivityDetailsHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_details_home);
        position = -1;
        motionId = getIntent().getIntExtra("motionId", 0);
        mFullLoadingFragment = new FullLoadingFragment();
        mMapDetailsFragment = new MapDetailsFragment();
        mListDetailsFragment = new ListDetailsFragment();
        mChartDetailsFragment = new ChartDetailsFragment();
        switchFragment(fromFragment, mFullLoadingFragment);
        getDetailsData();
        haveMap = false;
        mActivityDetailsHomeBinding.viewBack.setOnClickListener(this);
        mActivityDetailsHomeBinding.viewShare.setOnClickListener(this);
        mActivityDetailsHomeBinding.viewMore.setOnClickListener(this);
        mActivityDetailsHomeBinding.viewClick1.setOnClickListener(this);
        mActivityDetailsHomeBinding.viewClick2.setOnClickListener(this);
        mActivityDetailsHomeBinding.viewClick3.setOnClickListener(this);
        stravaBean = DbUtils.getInstance().queryAuthorEntity(getUserId(), 1);
    }

    /**
     * 获取详情数据
     */
    public void getDetailsData() {
        mActivityDetailsHomeBinding.ivBack.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.tvDetailsTitle.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.ivShare.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.ivMore.setVisibility(View.GONE);
        hideBottom();
        QueryMotionInfoRequest queryMotionInfoRequest = new QueryMotionInfoRequest();
        queryMotionInfoRequest.setMotionId(motionId);
        queryMotionInfoRequest.setSession(getUserInfoEntity().getSession());
        RetrofitUtils.getApiUrl().queryMotionInfo(queryMotionInfoRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<MotionDetailsResponse>() {
                    @Override
                    public void onSuccess(MotionDetailsResponse motionDetailsResponse) {
                        mMotionDetailsResponse = motionDetailsResponse;
                        motionType = motionDetailsResponse.getMotionCyclingResponseVo().getMotionType();
                        isCompetition = motionDetailsResponse.getMotionCyclingResponseVo().getIsCompetition();
                        switch (motionType) {
                            case Config.SPORT_OUTDOOR:
                                mActivityDetailsHomeBinding.tvDetailsTitle.setText(R.string.outdoor);
                                break;
                            case Config.SPORT_INDOOR:
                                mActivityDetailsHomeBinding.tvDetailsTitle.setText(R.string.indoor);
                                break;
                            case Config.SPORT_COMPETITION:
                                mActivityDetailsHomeBinding.tvDetailsTitle.setText(R.string.competition);
                                break;
                            default:
                        }
                        motionCyclingResponseVoBean = motionDetailsResponse.getMotionCyclingResponseVo();
                        MotionDetailsResponse.MotionCyclingResponseVoBean.MotionCyclingRecordPoBean motionCyclingRecordPo = motionCyclingResponseVoBean.getMotionCyclingRecordPo();
                        if (motionCyclingRecordPo != null) {
                            String altitudeVos = motionCyclingRecordPo.getAltitudeVos();
                            String cadenceVos = motionCyclingRecordPo.getCadenceVos();
                            String hrmVos = motionCyclingRecordPo.getHrmVos();
                            String powerVos = motionCyclingRecordPo.getPowerVos();
                            String speedVos = motionCyclingRecordPo.getSpeedVos();
                            String temperatureVos = motionCyclingRecordPo.getTemperatureVos();
                            String emptyFlag = "[]";
                            boolean speedFlag = false;
                            //速度图表
                            if (emptyFlag.equals(speedVos) || motionCyclingResponseVoBean.getMaxSpeed() == 0) {
                                speedFlag = true;
                            }
                            //踏频图表
                            boolean cadenceFlag = false;
                            if (emptyFlag.equals(cadenceVos) || motionCyclingResponseVoBean.getMaxCadence() == 0) {
                                cadenceFlag = true;
                            }
                            //海拔图表
                            boolean altitudeFlag = false;
                            if (emptyFlag.equals(altitudeVos)) {
                                altitudeFlag = true;
                            }
                            //心率图表
                            boolean hrmFlag = false;
                            if (emptyFlag.equals(hrmVos) || motionCyclingResponseVoBean.getMaxHrm() == 0) {
                                hrmFlag = true;
                            }
                            //功率图表
                            boolean powerFlag = false;
                            if (emptyFlag.equals(powerVos) || motionCyclingResponseVoBean.getMaxPower() == 0) {
                                powerFlag = true;
                            }
                            //温度
                            boolean temperatureFlag = false;
                            if (emptyFlag.equals(temperatureVos)) {
                                temperatureFlag = true;
                            }
                            isShowChart = !speedFlag
                                    || !cadenceFlag
                                    || !altitudeFlag
                                    || !hrmFlag
                                    || !powerFlag
                                    || !temperatureFlag;
                        } else {
                            isShowChart = false;
                        }
                        switchFragment(fromFragment, mMapDetailsFragment);
                        mActivityDetailsHomeBinding.ivBack.setVisibility(View.VISIBLE);
                        mActivityDetailsHomeBinding.tvDetailsTitle.setVisibility(View.VISIBLE);
                        mActivityDetailsHomeBinding.ivShare.setVisibility(View.VISIBLE);
                        mActivityDetailsHomeBinding.ivMore.setVisibility(View.VISIBLE);
                        showBottom();
                        position = 0;
                        bottomUi();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        ToastUtils.showError(DetailsHomeActivity.this, resultCode);
                        mNoNetworkFragment = new NoNetworkFragment();
                        switchFragment(fromFragment, mNoNetworkFragment);
                        mActivityDetailsHomeBinding.ivBack.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void switchFragment(Fragment from, Fragment to) {
        if (from != to && to != null) {
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!to.isAdded()) {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.add(R.id.record_details, to, to.getClass().getName()).commit();
            } else {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.show(to).commit();
            }
            fromFragment = to;
        }
    }

    private Fragment fromFragment;

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_back:
                finish();
                break;
            case R.id.view_click_1:
                if (!isShow) {
                    position = 0;
                    bottomUi();
                    switchFragment(fromFragment, mMapDetailsFragment);
                }
                break;
            case R.id.view_click_2:
                if (!isShow) {
                    position = 1;
                    bottomUi();
                    switchFragment(fromFragment, mListDetailsFragment);
                }
                break;
            case R.id.view_click_3:
                if (!isShow) {
                    position = 2;
                    bottomUi();
                    switchFragment(fromFragment, mChartDetailsFragment);
                }
                break;
            case R.id.view_more:
                if (position >= 0 && !isShow) {
                    String favorite = "0";
                    RecordDetailsDialog recordDetailsDialog;
                    if (favorite.equals(isCompetition)) {
                        recordDetailsDialog = new RecordDetailsDialog(this, getString(R.string.favorites), stravaBean, motionCyclingResponseVoBean.getFitUrl());
                    } else {
                        recordDetailsDialog = new RecordDetailsDialog(this, getString(R.string.un_favorite), stravaBean, motionCyclingResponseVoBean.getFitUrl());
                    }
                    recordDetailsDialog.setCanceledOnTouchOutside(false);
                    recordDetailsDialog.show();
                    recordDetailsDialog.setRecordDetailsDialogClickListener(mRecordDetailsDialogClickListener);
                }
                break;
            case R.id.view_share:
                if (!isShow) {
                    Intent intent = new Intent(this, ShareImageActivity.class);
                    intent.putExtra("motionType", motionType);
                    intent.putExtra("haveMap", haveMap);
                    startActivity(intent);
//                    Bitmap bitmap = mMapDetailsFragment.getBitmap();
//                    int byteCount = bitmap.getByteCount();
                }
                break;
            default:
        }
    }

    private final RecordDetailsDialog.RecordDetailsDialogClickListener mRecordDetailsDialogClickListener = new RecordDetailsDialog.RecordDetailsDialogClickListener() {
        @Override
        public void clickTitle() {
            upByMotionInfo();
        }

        @Override
        public void clickEditRecord() {
            DescriptionActivity.enterDescription(DetailsHomeActivity.this, motionId);
        }

        @Override
        public void clickDelete() {
            AskDialog askDialog = new AskDialog(DetailsHomeActivity.this, getString(R.string.delete_record), getString(R.string.delete_activity));
            askDialog.show();
            askDialog.setAskDialogListener(new AskDialog.AskDialogListener() {
                @Override
                public void clickCancel() {
                }

                @Override
                public void clickConfirm() {
                    deleteRecord();
                }
            });
        }

        @Override
        public void clickStrava() {
            if (stravaBean != null && motionCyclingResponseVoBean != null && motionCyclingResponseVoBean.getFitUrl() != null) {
                showLoadingDialog();
                String filePath = getExternalFilesDir("") + File.separator + "str.fit";
                RetrofitUtils.downloadUrl(motionCyclingResponseVoBean.getFitUrl()).execute(filePath, new FileDownloadCallback<File>() {
                    @Override
                    public void onSuccess(File file) {
                        if (System.currentTimeMillis() > Long.parseLong(stravaBean.getTimeOut()) * 1000) {
                            OkHttpClient okHttpClient = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("client_id", "54706")
                                    .add("client_secret", "02361fefe4813ddc67f3288c555f80eae85bb448")
                                    .add("grant_type", "refresh_token")
                                    .add("refresh_token", stravaBean.getPullToken())
                                    .build();
                            Request request = new Request.Builder().url("https://www.strava.com/api/v3/oauth/token")
                                    .post(requestBody)
                                    .build();
                            okHttpClient.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    strUploadFail();
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    if (response.code() == 200) {
                                        StravaBean strBean = new Gson().fromJson(response.body().string(), StravaBean.class);
                                        stravaBean.setToken(strBean.getToken_type() + " " + strBean.getAccess_token());
                                        stravaBean.setPullToken(strBean.getRefresh_token());
                                        stravaBean.setTimeOut(String.valueOf(strBean.getExpires_at()));
                                        DbUtils.getInstance().updateAuthorEntity(stravaBean);
                                        strUpload(filePath, stravaBean.getToken());
                                    } else {
                                        strUploadFail();
                                    }
                                }
                            });
                        } else {
                            strUpload(filePath, stravaBean.getToken());
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        strUploadFail();
                    }

                    @Override
                    public void onProgress(long current, long total) {
                    }
                });
            }
        }
    };

    /**
     * strava 文件上传
     */
    private void strUpload(String filePath, String token) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //打印一次请求的全部信息
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //设置读取超时时间
                .readTimeout(RetrofitUtils.DEFAULT_TIME, TimeUnit.SECONDS)
                //设置请求超时时间
                .connectTimeout(RetrofitUtils.DEFAULT_TIME, TimeUnit.SECONDS)
                //设置写入超时时间
                .writeTimeout(RetrofitUtils.DEFAULT_TIME, TimeUnit.SECONDS)
                //添加打印拦截器
                .addInterceptor(loggingInterceptor)
                //设置出现错误进行重新连接。
                .retryOnConnectionFailure(true)
                .build();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), AppUtils.getBytesByFile(filePath));
        requestBody.addFormDataPart("file", "upload.fit", body);
        requestBody.addFormDataPart("data_type", "fit");
        Request request = new Request.Builder()
                .url("https://www.strava.com/api/v3/uploads")
                .addHeader("Authorization", token)
                .post(requestBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hideLoadingDialog();
                runOnUiThread(() -> ToastUtils.show(DetailsHomeActivity.this, getString(R.string.strava_share_fail)));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                hideLoadingDialog();
                if (response.code() >= 200 && response.code() < 300) {
                    runOnUiThread(() -> ToastUtils.show(DetailsHomeActivity.this, getString(R.string.strava_share_success)));
                } else {
                    runOnUiThread(() -> ToastUtils.show(DetailsHomeActivity.this, getString(R.string.strava_hava)));
                }
            }
        });
    }

    /**
     * 上传失败
     */
    private void strUploadFail() {
        hideLoadingDialog();
        runOnUiThread(() -> ToastUtils.show(DetailsHomeActivity.this, getString(R.string.strava_share_fail)));
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void bottomUi() {
        switch (position) {
            case 0:
                if (isShowChart) {
                    mActivityDetailsHomeBinding.ivDetails3.setImageDrawable(getDrawable(R.drawable.details_item_3));
                    mActivityDetailsHomeBinding.view3.setVisibility(View.GONE);
                } else {
                    mActivityDetailsHomeBinding.ivDetails3.setVisibility(View.GONE);
                    mActivityDetailsHomeBinding.view3.setVisibility(View.GONE);
                    mActivityDetailsHomeBinding.viewClick3.setVisibility(View.GONE);
                }
                mActivityDetailsHomeBinding.ivDetails1.setImageDrawable(getDrawable(R.drawable.details_item_1_select));
                mActivityDetailsHomeBinding.ivDetails2.setImageDrawable(getDrawable(R.drawable.details_item_2));
                mActivityDetailsHomeBinding.view1.setVisibility(View.VISIBLE);
                mActivityDetailsHomeBinding.view2.setVisibility(View.GONE);

                switch (motionType) {
                    case Config.SPORT_OUTDOOR:
                        mActivityDetailsHomeBinding.tvDetailsTitle.setText(R.string.outdoor);
                        break;
                    case Config.SPORT_INDOOR:
                        mActivityDetailsHomeBinding.tvDetailsTitle.setText(R.string.indoor);
                        break;
                    case Config.SPORT_COMPETITION:
                        mActivityDetailsHomeBinding.tvDetailsTitle.setText(R.string.competition);
                        break;
                    default:
                }
                mActivityDetailsHomeBinding.ivShare.setVisibility(View.VISIBLE);
                break;
            case 1:
                if (isShowChart) {
                    mActivityDetailsHomeBinding.ivDetails3.setImageDrawable(getDrawable(R.drawable.details_item_3));
                    mActivityDetailsHomeBinding.view3.setVisibility(View.GONE);
                } else {
                    mActivityDetailsHomeBinding.ivDetails3.setVisibility(View.GONE);
                    mActivityDetailsHomeBinding.view3.setVisibility(View.GONE);
                    mActivityDetailsHomeBinding.viewClick3.setVisibility(View.GONE);
                }
                mActivityDetailsHomeBinding.ivDetails1.setImageDrawable(getDrawable(R.drawable.details_item_1));
                mActivityDetailsHomeBinding.ivDetails2.setImageDrawable(getDrawable(R.drawable.details_item_2_select));
                mActivityDetailsHomeBinding.tvDetailsTitle.setText(R.string.details);
                mActivityDetailsHomeBinding.view1.setVisibility(View.GONE);
                mActivityDetailsHomeBinding.view2.setVisibility(View.VISIBLE);
                mActivityDetailsHomeBinding.ivShare.setVisibility(View.GONE);
                break;
            case 2:
                if (isShowChart) {
                    mActivityDetailsHomeBinding.ivDetails1.setImageDrawable(getDrawable(R.drawable.details_item_1));
                    mActivityDetailsHomeBinding.ivDetails2.setImageDrawable(getDrawable(R.drawable.details_item_2));
                    mActivityDetailsHomeBinding.ivDetails3.setImageDrawable(getDrawable(R.drawable.details_item_3_select));
                    mActivityDetailsHomeBinding.tvDetailsTitle.setText(R.string.details);
                    mActivityDetailsHomeBinding.view1.setVisibility(View.GONE);
                    mActivityDetailsHomeBinding.view2.setVisibility(View.GONE);
                    mActivityDetailsHomeBinding.view3.setVisibility(View.VISIBLE);
                    mActivityDetailsHomeBinding.ivShare.setVisibility(View.GONE);
                }
                break;
            default:
        }
    }

    /**
     * 收藏取消收藏
     */
    public void upByMotionInfo() {
        showLoadingDialog();
        UpMotionInfoRequest upMotionInfoRequest = new UpMotionInfoRequest();
        upMotionInfoRequest.setSession(getUserInfoEntity().getSession());
        String favorite = "0";
        if (favorite.equals(isCompetition)) {
            upMotionInfoRequest.setUpStatus(1);
        } else {
            upMotionInfoRequest.setUpStatus(0);
        }
        upMotionInfoRequest.setId(motionId);
        RetrofitUtils.getApiUrl().upCompetition(upMotionInfoRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object obj) {
                        hideLoadingDialog();
                        if (favorite.equals(isCompetition)) {
                            ToastUtils.show(DetailsHomeActivity.this, getString(R.string.favorite_success));
                        } else {
                            upMotionInfoRequest.setUpStatus(0);
                            ToastUtils.show(DetailsHomeActivity.this, getString(R.string.un_feedback_success));
                        }
                        isCompetition = String.valueOf(upMotionInfoRequest.getUpStatus());
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        if (favorite.equals(isCompetition)) {
                            ToastUtils.show(DetailsHomeActivity.this, getString(R.string.favorite_fail));
                        } else {
                            upMotionInfoRequest.setUpStatus(0);
                            ToastUtils.show(DetailsHomeActivity.this, getString(R.string.un_feedback_fail));
                        }
                    }
                });
    }

    /**
     * 删除数据
     */
    private void deleteRecord() {
        showLoadingDialog();
        DeleteMotionRequest deleteMotionRequest = new DeleteMotionRequest();
        deleteMotionRequest.setSession(getUserInfoEntity().getSession());
        deleteMotionRequest.setMotionType(motionType);
        deleteMotionRequest.setMotionId(motionId);
        RetrofitUtils.getApiUrl().deleteByMotionInfo(deleteMotionRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object obj) {
                        Intent intent = new Intent(BroadcastConstant.ACTION_DELETE_RECORD);
                        sendBroadcast(intent);
                        hideLoadingDialog();
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                    }
                });
    }

    /**
     * 修改描述信息
     */
    public void changeType() {
        showLoadingDialog();
        UpdateMotionRequest updateMotionRequest = new UpdateMotionRequest();
        updateMotionRequest.setId(motionId);
        updateMotionRequest.setSession(getUserInfoEntity().getSession());
        switch (motionType) {
            case Config.SPORT_OUTDOOR:
                updateMotionRequest.setMotionType(Config.SPORT_INDOOR);
                break;
            case Config.SPORT_INDOOR:
                updateMotionRequest.setMotionType(Config.SPORT_COMPETITION);
                break;
            case Config.SPORT_COMPETITION:
                updateMotionRequest.setMotionType(Config.SPORT_OUTDOOR);
                break;
        }
        RetrofitUtils.getApiUrl().updateMotion(updateMotionRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        hideLoadingDialog();
                        Intent intent = new Intent(BroadcastConstant.ACTION_DELETE_RECORD);
                        sendBroadcast(intent);
                        motionType = updateMotionRequest.getMotionType();
                        mMapDetailsFragment.updateType();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(DetailsHomeActivity.this, resultCode);
                    }
                });
        showLoadingDialog();
    }

    private void showBottom() {
        mActivityDetailsHomeBinding.viewBottom.setVisibility(View.VISIBLE);
        mActivityDetailsHomeBinding.view1.setVisibility(View.VISIBLE);
        mActivityDetailsHomeBinding.view2.setVisibility(View.VISIBLE);
        mActivityDetailsHomeBinding.view3.setVisibility(View.VISIBLE);
        mActivityDetailsHomeBinding.ivDetails1.setVisibility(View.VISIBLE);
        mActivityDetailsHomeBinding.ivDetails2.setVisibility(View.VISIBLE);
        mActivityDetailsHomeBinding.ivDetails3.setVisibility(View.VISIBLE);
        mActivityDetailsHomeBinding.viewClick1.setVisibility(View.VISIBLE);
        mActivityDetailsHomeBinding.viewClick2.setVisibility(View.VISIBLE);
        mActivityDetailsHomeBinding.viewClick3.setVisibility(View.VISIBLE);
    }

    private void hideBottom() {
        mActivityDetailsHomeBinding.viewBottom.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.view1.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.view2.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.view3.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.ivDetails1.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.ivDetails2.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.ivDetails3.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.viewClick1.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.viewClick2.setVisibility(View.GONE);
        mActivityDetailsHomeBinding.viewClick3.setVisibility(View.GONE);
    }

}
