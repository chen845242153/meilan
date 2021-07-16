package com.meilancycling.mema.ui.setting;

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
import com.meilancycling.mema.databinding.ActivityEmailBinding;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.CheckPhoneOrMailRequest;
import com.meilancycling.mema.network.bean.request.GetCodeRequest;
import com.meilancycling.mema.network.bean.request.UserBindingRequest;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 绑定邮箱
 *
 * @author lion
 */
public class EmailActivity extends BaseActivity implements View.OnClickListener {
    private ActivityEmailBinding mActivityEmailBinding;
    private boolean isSend;
    private String mUsername;
    private String mPassword;
    private String mCode;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityEmailBinding = DataBindingUtil.setContentView(this, R.layout.activity_email);
        mActivityEmailBinding.viewCode.setOnClickListener(this);
        mActivityEmailBinding.tvConfirm.setOnClickListener(this);
        mActivityEmailBinding.viewBack.setOnClickListener(this);
        isSend = false;
        mActivityEmailBinding.etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = s.toString().trim();
                String code = mActivityEmailBinding.etCode.getText().toString().trim();
                String password = mActivityEmailBinding.etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code) || TextUtils.isEmpty(password) || password.length() < 6) {
                    mActivityEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(EmailActivity.this, R.drawable.shape_f2_23));
                    mActivityEmailBinding.tvConfirm.setTextColor(Color.parseColor("#C0C0C0"));
                } else {
                    mActivityEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(EmailActivity.this, R.drawable.shape_main_23));
                    mActivityEmailBinding.tvConfirm.setTextColor(ContextCompat.getColor(EmailActivity.this, R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mActivityEmailBinding.etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = mActivityEmailBinding.etUserName.getText().toString().trim();
                String code = s.toString().trim();
                String password = mActivityEmailBinding.etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code) || TextUtils.isEmpty(password) || password.length() < 6) {
                    mActivityEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(EmailActivity.this, R.drawable.shape_f2_23));
                    mActivityEmailBinding.tvConfirm.setTextColor(Color.parseColor("#C0C0C0"));
                } else {
                    mActivityEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(EmailActivity.this, R.drawable.shape_main_23));
                    mActivityEmailBinding.tvConfirm.setTextColor(ContextCompat.getColor(EmailActivity.this, R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mActivityEmailBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = mActivityEmailBinding.etUserName.getText().toString().trim();
                String code = mActivityEmailBinding.etCode.getText().toString().trim();
                String password = s.toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code) || TextUtils.isEmpty(password) || password.length() < 6) {
                    mActivityEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(EmailActivity.this, R.drawable.shape_f2_23));
                    mActivityEmailBinding.tvConfirm.setTextColor(Color.parseColor("#C0C0C0"));
                } else {
                    mActivityEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(EmailActivity.this, R.drawable.shape_main_23));
                    mActivityEmailBinding.tvConfirm.setTextColor(ContextCompat.getColor(EmailActivity.this, R.color.white));
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
            case R.id.view_code:
                if (!isSend) {
                    codeCheck();
                }
                break;
            case R.id.tv_confirm:
                checkContent();
                break;
            case R.id.view_back:
                finish();
                mHandler.removeMessages(0);
                break;
            default:
        }
    }

    private void codeCheck() {
        mUsername = mActivityEmailBinding.etUserName.getText().toString().trim();
        if (TextUtils.isEmpty(mUsername)) {
            ToastUtils.show(this, getString(R.string.CODE_120002));
            return;
        }
        if (!isEmail(mUsername)) {
            ToastUtils.show(this, getString(R.string.CODE_120002));
            return;
        }
        showLoadingDialog();
        checkPhoneOrMail(mUsername);
    }

    /**
     * 查询手机号与邮箱是否已注册
     */
    private void checkPhoneOrMail(String username) {
        CheckPhoneOrMailRequest checkPhoneOrMailRequest = new CheckPhoneOrMailRequest();
        checkPhoneOrMailRequest.setAccountNumber(username);
        RetrofitUtils.getApiUrl().checkPhoneOrMail(checkPhoneOrMailRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<String>() {
                    @Override
                    public void onSuccess(String result) {
                        String noRegistered = "false";
                        if (TextUtils.isEmpty(result) || noRegistered.equals(result)) {
                            GetCodeRequest getCodeRequest = new GetCodeRequest();
                            getCodeRequest.setMesType(String.valueOf(4));
                            getCodeRequest.setMobile(mUsername);
                            getCodeRequest.setReceiveType(String.valueOf(2));
                            getCode(getCodeRequest);
                        } else {
                            hideLoadingDialog();
                            ToastUtils.show(EmailActivity.this, getString(R.string.change_account));
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(EmailActivity.this, resultCode);
                    }
                });
    }

    /**
     * 发送短信或者邮箱
     */
    public void getCode(GetCodeRequest getCodeRequest) {
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
                        ToastUtils.showError(EmailActivity.this, resultCode);
                    }
                });
    }


    private boolean isEmail(String strEmail) {
        String regEx = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return strEmail.matches(regEx);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (time > 0) {
                --time;
                String timeValue = UnitConversionUtil.getUnitConversionUtil().timeToMS(time);
                mActivityEmailBinding.tvCode.setText(timeValue);
                mHandler.postDelayed(() -> mHandler.sendEmptyMessage(0), 1000);
            } else {
                mActivityEmailBinding.tvCode.setText(R.string.get_now);
                isSend = false;
            }
        }
    };

    /**
     * 输入内容检查
     */
    private void checkContent() {
        mUsername = mActivityEmailBinding.etUserName.getText().toString().trim();
        mPassword = mActivityEmailBinding.etPassword.getText().toString().trim();
        mCode = mActivityEmailBinding.etCode.getText().toString().trim();
        if (TextUtils.isEmpty(mUsername)) {
            ToastUtils.show(this, getString(R.string.account_err));
            return;
        }
        if (!isEmail(mUsername)) {
            ToastUtils.show(this, getString(R.string.account_err));
            return;
        }
        // 密码最小长度为6
        int passwordMinLength = 6;
        if (TextUtils.isEmpty(mPassword) || mPassword.length() < passwordMinLength) {
            ToastUtils.show(this, getString(R.string.password_length));
            return;
        }
        if (TextUtils.isEmpty(mCode)) {
            ToastUtils.show(this, getString(R.string.CODE_20508));
            return;
        }
        showLoadingDialog();
        //绑定邮箱
        UserBindingRequest userBindingRequest = new UserBindingRequest();
        userBindingRequest.setMobile(mUsername);
        userBindingRequest.setPassword(stringMd5(mPassword));
        userBindingRequest.setReceiveType(2);
        UserInfoEntity userInfoEntity = getUserInfoEntity();
        userBindingRequest.setSession(userInfoEntity.getSession());
        userBindingRequest.setSmsCode(mCode);
        RetrofitUtils.getApiUrl()
                .userBindingAccount(userBindingRequest)
                .compose(RxHelper.observableToMain(EmailActivity.this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        userInfoEntity.setMail(mUsername);
                        updateUserInfoEntity(userInfoEntity);
                        mHandler.removeMessages(0);
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(EmailActivity.this, resultCode);
                    }
                });
    }

    /**
     * 对字符串进行MD5加密
     */
    private String stringMd5(String plainText) {
        if (TextUtils.isEmpty(plainText)) {
            return "";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte value : b) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i & 0xff));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
    }
}