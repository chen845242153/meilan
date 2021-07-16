package com.meilancycling.mema.ui.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivityFeedbackBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.AddFeedbackRequest;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.LocationUtils;
import com.meilancycling.mema.utils.ToastUtils;

import java.util.List;

/**
 * 意见反馈
 *
 * @author lion
 */
public class FeedbackActivity extends BaseActivity implements View.OnClickListener {
    private ActivityFeedbackBinding mActivityFeedbackBinding;
    private int select = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFeedbackBinding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);
        mActivityFeedbackBinding.tvFeedbackItem1.setOnClickListener(this);
        mActivityFeedbackBinding.tvFeedbackItem2.setOnClickListener(this);
        mActivityFeedbackBinding.tvFeedbackItem3.setOnClickListener(this);
        mActivityFeedbackBinding.tvFeedbackItem4.setOnClickListener(this);
        mActivityFeedbackBinding.tvFeedbackCurrentCount.setText(String.valueOf(0));
        mActivityFeedbackBinding.tvFeedbackSubmit.setOnClickListener(this);
        mActivityFeedbackBinding.ctvFeedback.setData(getString(R.string.feedback), this);
        mActivityFeedbackBinding.etFeedbackContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mActivityFeedbackBinding.tvFeedbackCurrentCount.setText(String.valueOf(s.toString().length()));
                if (s.toString().length() > 0) {
                    mActivityFeedbackBinding.tvFeedbackSubmit.setTextColor(ContextCompat.getColor(FeedbackActivity.this, R.color.white));
                    mActivityFeedbackBinding.tvFeedbackSubmit.setBackground(ContextCompat.getDrawable(FeedbackActivity.this, R.drawable.shape_main_4));
                } else {
                    mActivityFeedbackBinding.tvFeedbackSubmit.setTextColor(Color.parseColor("#FFB5B5B5"));
                    mActivityFeedbackBinding.tvFeedbackSubmit.setBackground(ContextCompat.getDrawable(FeedbackActivity.this, R.drawable.shape_f2_4));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_feedback_item1:
                updateUi(1);
                break;
            case R.id.tv_feedback_item2:
                updateUi(2);
                break;
            case R.id.tv_feedback_item3:
                updateUi(3);
                break;
            case R.id.tv_feedback_item4:
                updateUi(4);
                break;
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tv_feedback_submit:
                String value = mActivityFeedbackBinding.etFeedbackContent.getText().toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    AddFeedbackRequest addFeedbackRequest = new AddFeedbackRequest();
                    addFeedbackRequest.setContent(value);
                    addFeedbackRequest.setType(String.valueOf(select));
                    addFeedbackRequest.setSession(getUserInfoEntity().getSession());
                    String contact = mActivityFeedbackBinding.etContact.getText().toString().trim();
                    if (!TextUtils.isEmpty(contact)) {
                        addFeedbackRequest.setContact(contact);
                    }
                    addFeedbackRequest.setAppVersion(AppUtils.getVersionName(this));
                    addFeedbackRequest.setLogLat(LocationUtils.getInstance(FeedbackActivity.this).showLocation());
                    addFeedbackRequest.setMobileModel(android.os.Build.MODEL);
                    addFeedbackRequest.setNetworkType(getNetworkType(FeedbackActivity.this));
                    addFeedbackRequest.setPhoneType("android");
                    addFeedbackRequest.setPhoneVersion(android.os.Build.VERSION.RELEASE);
                    DisplayMetrics dm = getResources().getDisplayMetrics();
                    int screenWidth = dm.widthPixels;
                    int screenHeight = dm.heightPixels;
                    addFeedbackRequest.setResoltion(screenWidth + "*" + screenHeight);
                    List<DeviceInformationEntity> deviceInformationEntities = DbUtils.getInstance().deviceInfoList(getUserId());
                    if (deviceInformationEntities != null && deviceInformationEntities.size() != 0) {
                        addFeedbackRequest.setRidingComputer(deviceInformationEntities.get(0).getProductNo());
                    }
                    addFeedback(addFeedbackRequest);
                }
                break;
            default:
        }
    }

    /**
     * 添加反馈意见
     */
    private void addFeedback(AddFeedbackRequest addFeedbackRequest) {
        showLoadingDialog();
        RetrofitUtils.getApiUrl().addFeedback(addFeedbackRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        hideLoadingDialog();
                        ToastUtils.show(FeedbackActivity.this, getString(R.string.feedback_submitted));
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.show(FeedbackActivity.this, e.getMessage());
                    }
                });

    }

    private void updateUi(int currentSelect) {
        if (currentSelect != select) {
            switch (select) {
                case 1:
                    mActivityFeedbackBinding.tvFeedbackItem1.setTextColor(ContextCompat.getColor(this, R.color.black_9));
                    mActivityFeedbackBinding.tvFeedbackItem1.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_line_999_4));
                    break;
                case 2:
                    mActivityFeedbackBinding.tvFeedbackItem2.setTextColor(ContextCompat.getColor(this, R.color.black_9));
                    mActivityFeedbackBinding.tvFeedbackItem2.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_line_999_4));
                    break;
                case 3:
                    mActivityFeedbackBinding.tvFeedbackItem3.setTextColor(ContextCompat.getColor(this, R.color.black_9));
                    mActivityFeedbackBinding.tvFeedbackItem3.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_line_999_4));
                    break;
                case 4:
                    mActivityFeedbackBinding.tvFeedbackItem4.setTextColor(ContextCompat.getColor(this, R.color.black_9));
                    mActivityFeedbackBinding.tvFeedbackItem4.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_line_999_4));
                    break;
                default:
            }
            switch (currentSelect) {
                case 1:
                    mActivityFeedbackBinding.tvFeedbackItem1.setTextColor(ContextCompat.getColor(this, R.color.white));
                    mActivityFeedbackBinding.tvFeedbackItem1.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_main_4));
                    break;
                case 2:
                    mActivityFeedbackBinding.tvFeedbackItem2.setTextColor(ContextCompat.getColor(this, R.color.white));
                    mActivityFeedbackBinding.tvFeedbackItem2.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_main_4));
                    break;
                case 3:
                    mActivityFeedbackBinding.tvFeedbackItem3.setTextColor(ContextCompat.getColor(this, R.color.white));
                    mActivityFeedbackBinding.tvFeedbackItem3.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_main_4));
                    break;
                case 4:
                    mActivityFeedbackBinding.tvFeedbackItem4.setTextColor(ContextCompat.getColor(this, R.color.white));
                    mActivityFeedbackBinding.tvFeedbackItem4.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_main_4));
                    break;
                default:
            }
        }
        select = currentSelect;
    }

    public static final String wifi = "wifi";
    public static final String moblie2g = "2G";
    public static final String moblie3g = "3G";
    public static final String moblie4g = "4G";

    /**
     * 判断当前网络类型
     *
     * @param context
     * @return
     */
    public static String getNetworkType(Context context) {
        String strNetworkType = null;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    strNetworkType = wifi;
                } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    String _strSubTypeName = info.getSubtypeName();
                    int networkType = info.getSubtype();
                    switch (networkType) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                            strNetworkType = moblie2g;
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                        case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                        case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                            strNetworkType = moblie3g;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                            strNetworkType = moblie4g;
                            break;
                        default:
                            // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                            if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                strNetworkType = moblie3g;
                            } else {
                                strNetworkType = _strSubTypeName;
                            }
                            break;
                    }
                }
            }
        }
        return strNetworkType;

    }

}