package com.meilancycling.mema.ui.login;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.ActivityUnbindingEmailBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.GetCodeRequest;
import com.meilancycling.mema.network.bean.request.UserBindingRequest;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

/**
 * 取消绑定
 *
 * @author lion
 */
public class UnbindingEmailActivity extends BaseActivity implements View.OnClickListener {
    private ActivityUnbindingEmailBinding mActivityUnbindingEmailBinding;
    private UserInfoEntity mUserInfoEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityUnbindingEmailBinding = DataBindingUtil.setContentView(this, R.layout.activity_unbinding_email);
        mUserInfoEntity = getUserInfoEntity();
        mActivityUnbindingEmailBinding.tvUserName.setText(mUserInfoEntity.getMail());
        mActivityUnbindingEmailBinding.viewBack.setOnClickListener(this);
        mActivityUnbindingEmailBinding.viewCode.setOnClickListener(this);
        mActivityUnbindingEmailBinding.tvConfirm.setOnClickListener(this);

        mActivityUnbindingEmailBinding.etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String trim = s.toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    mActivityUnbindingEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(UnbindingEmailActivity.this, R.drawable.shape_f2_23));
                    mActivityUnbindingEmailBinding.tvConfirm.setTextColor(Color.parseColor("#C0C0C0"));
                } else {
                    mActivityUnbindingEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(UnbindingEmailActivity.this, R.drawable.shape_main_23));
                    mActivityUnbindingEmailBinding.tvConfirm.setTextColor(ContextCompat.getColor(UnbindingEmailActivity.this, R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_back:
                finish();
                break;
            case R.id.view_code:
                if (time == 0) {
                    getCode();
                }
                break;
            case R.id.tv_confirm:
                String code = mActivityUnbindingEmailBinding.etCode.getText().toString().trim();
                if (!TextUtils.isEmpty(code) && code.length() >= 6) {
                    unbindingEmail(code);
                }
                break;
            default:
        }

    }

    /**
     * 解绑
     */
    private void unbindingEmail(String code) {
        UserBindingRequest userBindingRequest = new UserBindingRequest();
        userBindingRequest.setMobile(mUserInfoEntity.getMail());
        userBindingRequest.setReceiveType(2);
        userBindingRequest.setSession(mUserInfoEntity.getSession());
        userBindingRequest.setSmsCode(code);
        RetrofitUtils.getApiUrl()
                .userUnbindmailbox(userBindingRequest)
                .compose(RxHelper.observableToMain(UnbindingEmailActivity.this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        mUserInfoEntity.setMail(null);
                        updateUserInfoEntity(mUserInfoEntity);
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(UnbindingEmailActivity.this, resultCode);
                    }
                });
    }


    /**
     * 发送短信或者邮箱
     */
    public void getCode() {
        GetCodeRequest getCodeRequest = new GetCodeRequest();
        getCodeRequest.setMesType(String.valueOf(5));
        if (mUserInfoEntity.getRegType() == Config.TYPE_PHONE) {
            getCodeRequest.setMobile(mUserInfoEntity.getPhone());
            getCodeRequest.setReceiveType(String.valueOf(1));
        } else {
            getCodeRequest.setMobile(mUserInfoEntity.getMail());
            getCodeRequest.setReceiveType(String.valueOf(2));
        }
        showLoadingDialog();
        RetrofitUtils.getApiUrl().getVerificationCode(getCodeRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadingDialog();
                        time = 30 * 60;
                        mHandler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(UnbindingEmailActivity.this, resultCode);
                    }
                });
    }

    private int time;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (time > 0) {
                --time;
                String timeValue = UnitConversionUtil.getUnitConversionUtil().timeToMS(time);
                mActivityUnbindingEmailBinding.tvCode.setText(timeValue);
                mHandler.postDelayed(() -> mHandler.sendEmptyMessage(0), 1000);
            } else {
                mActivityUnbindingEmailBinding.tvCode.setText(R.string.get_now);
            }
        }
    };
}
