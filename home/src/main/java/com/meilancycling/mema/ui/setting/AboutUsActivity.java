package com.meilancycling.mema.ui.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.customview.dialog.AppUpdateDialog;
import com.meilancycling.mema.databinding.ActivityAboutUsBinding;
import com.meilancycling.mema.network.BaseObserver;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.AppUpdateRequest;
import com.meilancycling.mema.network.bean.request.QueryWebUrlRequest;
import com.meilancycling.mema.network.bean.response.AppUpdateResponse;
import com.meilancycling.mema.network.bean.response.CommonProblemResponse;
import com.meilancycling.mema.network.download.FileDownloadCallback;
import com.meilancycling.mema.ui.setting.view.SavePictureDialog;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.SPUtils;
import com.meilancycling.mema.utils.ToastUtils;

import java.io.File;

/**
 * 关于我们
 *
 * @author lion
 */
public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    private ActivityAboutUsBinding mActivityAboutUsBinding;
    private CommonProblemResponse mCommonProblemResponse1;
    private CommonProblemResponse mCommonProblemResponse2;
    private AppUpdateResponse mAppUpdateResponse;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAboutUsBinding = DataBindingUtil.setContentView(this, R.layout.activity_about_us);
        mActivityAboutUsBinding.ctvAbout.setData(getString(R.string.about_us), this);
        mActivityAboutUsBinding.tvVersion.setText(getString(R.string.version_) + AppUtils.getVersionName(this));
        getAppVersion();
        mActivityAboutUsBinding.viewItem1.setOnClickListener(this);
        mActivityAboutUsBinding.viewItem2.setOnClickListener(this);
        mActivityAboutUsBinding.viewItem3.setOnClickListener(this);

        mActivityAboutUsBinding.ivEnFacebook.setOnClickListener(this);
        mActivityAboutUsBinding.ivEnYoutube.setOnClickListener(this);
        mActivityAboutUsBinding.ivEnIn.setOnClickListener(this);
        mActivityAboutUsBinding.ivEnWeb.setOnClickListener(this);

        mActivityAboutUsBinding.ivZhWeb.setOnClickListener(this);
        mActivityAboutUsBinding.ivZhWb.setOnClickListener(this);
        mActivityAboutUsBinding.ivZhWx.setOnClickListener(this);

        if (AppUtils.isChinese()) {
            mActivityAboutUsBinding.aboutEn.setVisibility(View.GONE);
            mActivityAboutUsBinding.aboutZh.setVisibility(View.VISIBLE);
        } else {
            mActivityAboutUsBinding.aboutEn.setVisibility(View.VISIBLE);
            mActivityAboutUsBinding.aboutZh.setVisibility(View.GONE);
        }
    }

    /**
     * 获取app版本
     */
    private void getAppVersion() {
        showLoadingDialog();
        String clientType = "1";
        AppUpdateRequest appUpdateRequest = new AppUpdateRequest();
        appUpdateRequest.setClientType(clientType);
        appUpdateRequest.setClientVersion(AppUtils.getVersionName(this));
        RetrofitUtils.getApiUrl()
                .appUpdate(appUpdateRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new BaseObserver<AppUpdateResponse>() {
                    @Override
                    public void onSuccess(AppUpdateResponse result) {
                        mAppUpdateResponse = result;
                        showVersion();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        showVersion();
                    }
                });
    }

    /**
     * 显示
     */
    private void showVersion() {
        if (mAppUpdateResponse == null) {
            mActivityAboutUsBinding.tvNew.setVisibility(View.GONE);
            mActivityAboutUsBinding.tvNewed.setVisibility(View.VISIBLE);
        } else {
            mActivityAboutUsBinding.tvNew.setVisibility(View.VISIBLE);
            mActivityAboutUsBinding.tvNewed.setVisibility(View.GONE);
        }
        queryContent(showType1);
    }

    /**
     * 查询注册协议
     */
    private String showType1 = "1";
    /**
     * 隐私政策查询
     */
    private String showType2 = "3";

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.view_item1:
                if (mCommonProblemResponse1 != null) {
                    Intent intent = new Intent(AboutUsActivity.this, WebViewActivity.class);
                    if (AppUtils.isChinese()) {
                        intent.putExtra(WebViewActivity.title, mCommonProblemResponse1.getName());
                        intent.putExtra(WebViewActivity.loadUrl, mCommonProblemResponse1.getUrl());
                    } else {
                        intent.putExtra(WebViewActivity.title, mCommonProblemResponse1.getEnName());
                        intent.putExtra(WebViewActivity.loadUrl, mCommonProblemResponse1.getEnUrl());
                    }
                    startActivity(intent);
                }
                break;
            case R.id.view_item2:
                if (mCommonProblemResponse2 != null) {
                    Intent intent = new Intent(AboutUsActivity.this, WebViewActivity.class);
                    if (AppUtils.isChinese()) {
                        intent.putExtra(WebViewActivity.title, mCommonProblemResponse2.getName());
                        intent.putExtra(WebViewActivity.loadUrl, mCommonProblemResponse2.getUrl());
                    } else {
                        intent.putExtra(WebViewActivity.title, mCommonProblemResponse2.getEnName());
                        intent.putExtra(WebViewActivity.loadUrl, mCommonProblemResponse2.getEnUrl());
                    }
                    startActivity(intent);
                }
                break;
            case R.id.view_item3:
                if (mAppUpdateResponse == null) {
                    ToastUtils.show(this, getString(R.string.is_latest));
                } else {
                    String content;
                    if (AppUtils.isChinese()) {
                        content = mAppUpdateResponse.getContent();
                    } else {
                        content = mAppUpdateResponse.getEnContent();
                    }
                    SPUtils.putLong(AboutUsActivity.this, SPUtils.APP_TIMESTAMP, System.currentTimeMillis());
                    AppUpdateDialog appUpdateDialog = new AppUpdateDialog(this, mAppUpdateResponse.getIsUpgrade(), content);
                    appUpdateDialog.show();
                    appUpdateDialog.setAppUpdateConfirmListener(new AppUpdateDialog.AppUpdateConfirmListener() {
                        @Override
                        public void confirmListener() {
                            downApp(mAppUpdateResponse.getDownloadUrl(), appUpdateDialog);
                        }
                    });
                }
                break;
            case R.id.iv_en_facebook:
                String facebook = "https://www.facebook.com/meilankj/";
                openWeb(facebook);
                break;
            case R.id.iv_en_youtube:
                String youtube = "https://www.youtube.com/c/Meilanoutdoorfitness/";
                openWeb(youtube);
                break;
            case R.id.iv_en_in:
                String instagram = "https://www.instagram.com/meilan_outdoor/";
                openWeb(instagram);
                break;
            case R.id.iv_en_web:
            case R.id.iv_zh_web:
                String web = "http://www.meilancycling.com/";
                openWeb(web);
                break;
            case R.id.iv_zh_wb:
                String webo = "https://m.weibo.cn/u/7090338048";
                openWeb(webo);
                break;
            case R.id.iv_zh_wx:
                SavePictureDialog savePictureDialog = new SavePictureDialog(this);
                savePictureDialog.setCanceledOnTouchOutside(false);
                savePictureDialog.show();
                savePictureDialog.setSavePictureListener(() -> {
                    showLoadingDialog();
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wechat_public, null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        if (AppUtils.saveImageToGallery(this, bitmap)) {
                            ToastUtils.show(this, getString(R.string.save_success));
                        } else {
                            ToastUtils.show(this, getString(R.string.save_failed));
                        }
                    } else {
                        if (AppUtils.saveImage(this, bitmap)) {
                            ToastUtils.show(this, getString(R.string.save_success));
                        } else {
                            ToastUtils.show(this, getString(R.string.save_failed));
                        }
                    }
                    hideLoadingDialog();
                });
                break;
            default:
        }
    }

    /**
     * 查询协议
     */
    private void queryContent(String showType) {
        QueryWebUrlRequest queryWebUrlRequest = new QueryWebUrlRequest();
        queryWebUrlRequest.setShowType(showType);
        RetrofitUtils.getApiUrl().queryContentInfoByType(queryWebUrlRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<CommonProblemResponse>() {
                    @Override
                    public void onSuccess(CommonProblemResponse commonProblemResponse) {
                        if (showType1.equals(showType)) {
                            mCommonProblemResponse1 = commonProblemResponse;
                            queryContent(showType2);
                        } else if (showType2.equals(showType)) {
                            hideLoadingDialog();
                            mCommonProblemResponse2 = commonProblemResponse;
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                    }
                });
    }

    private void openWeb(String parse) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri contentUrl = Uri.parse(parse);
        intent.setData(contentUrl);
        startActivity(intent);
    }

    /**
     * 下载ota
     */
    private void downApp(String url, AppUpdateDialog appUpdateDialog) {
        String filePath = getExternalFilesDir("") + File.separator + "meilan.apk";
        RetrofitUtils.downloadUrl(url).execute(filePath, new FileDownloadCallback<File>() {
            @Override
            public void onSuccess(File file) {
                installApp(filePath);
            }

            @Override
            public void onFail(Throwable throwable) {
                ToastUtils.show(AboutUsActivity.this, getString(R.string.file_failed));
                appUpdateDialog.updateFail();
            }

            @Override
            public void onProgress(long current, long total) {
                appUpdateDialog.updateProgress((int) ((float) current / total * 100));
            }
        });
    }

    private int installAppCode = 1;
    private String filePath;

    /**
     * apk 安装
     */
    private void installApp(String filePath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean b = getPackageManager().canRequestPackageInstalls();
            if (b) {
                AppUtils.isInstallApk(AboutUsActivity.this, filePath);
            } else {
                Uri uri = Uri.parse("package:" + getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri);
                startActivityForResult(intent, installAppCode);
                this.filePath = filePath;
            }
        } else {
            AppUtils.isInstallApk(AboutUsActivity.this, filePath);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == installAppCode) {
            if (getPackageManager().canRequestPackageInstalls() && !TextUtils.isEmpty(filePath)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean b = getPackageManager().canRequestPackageInstalls();
                    if (b) {
                        AppUtils.isInstallApk(AboutUsActivity.this, filePath);
                    }
                } else {
                    AppUtils.isInstallApk(AboutUsActivity.this, filePath);
                }
            }
        }
    }
}