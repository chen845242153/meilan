package com.meilancycling.mema.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivityBindingEmailBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.CheckPhoneOrMailRequest;
import com.meilancycling.mema.network.bean.request.GetCodeRequest;
import com.meilancycling.mema.network.bean.request.UserBindingRequest;
import com.meilancycling.mema.ui.home.HomeActivity;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 绑定邮箱
 *
 * @author lion
 */
public class BindingEmailActivity extends BaseActivity implements View.OnClickListener {
    private ActivityBindingEmailBinding mActivityBindingEmailBinding;
    private String mUsername;
    private String mPassword;
    private String mCode;
    /**
     * 密码是否隐藏
     */
    private boolean showPassword;
    private int time;
    private boolean isSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBindingEmailBinding = DataBindingUtil.setContentView(this, R.layout.activity_binding_email);
        mActivityBindingEmailBinding.viewNext.setOnClickListener(this);
        mActivityBindingEmailBinding.viewGetCode.setOnClickListener(this);
        mActivityBindingEmailBinding.viewPassword.setOnClickListener(this);
        mActivityBindingEmailBinding.tvConfirm.setOnClickListener(this);
        isSend = false;
        showPassword = false;

        mActivityBindingEmailBinding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = s.toString().trim();
                String code = mActivityBindingEmailBinding.etCode.getText().toString().trim();
                String password = mActivityBindingEmailBinding.etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code) || TextUtils.isEmpty(password) || password.length() < 6) {
                    mActivityBindingEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(BindingEmailActivity.this, R.drawable.shape_f2_23));
                    mActivityBindingEmailBinding.tvConfirm.setTextColor(Color.parseColor("#C0C0C0"));
                } else {
                    mActivityBindingEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(BindingEmailActivity.this, R.drawable.shape_main_23));
                    mActivityBindingEmailBinding.tvConfirm.setTextColor(ContextCompat.getColor(BindingEmailActivity.this, R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mActivityBindingEmailBinding.etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = mActivityBindingEmailBinding.etName.getText().toString().trim();
                String code = s.toString().trim();
                String password = mActivityBindingEmailBinding.etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code) || TextUtils.isEmpty(password) || password.length() < 6) {
                    mActivityBindingEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(BindingEmailActivity.this, R.drawable.shape_f2_23));
                    mActivityBindingEmailBinding.tvConfirm.setTextColor(Color.parseColor("#C0C0C0"));
                } else {
                    mActivityBindingEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(BindingEmailActivity.this, R.drawable.shape_main_23));
                    mActivityBindingEmailBinding.tvConfirm.setTextColor(ContextCompat.getColor(BindingEmailActivity.this, R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mActivityBindingEmailBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = mActivityBindingEmailBinding.etName.getText().toString().trim();
                String code = mActivityBindingEmailBinding.etCode.getText().toString().trim();
                String password = s.toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code) || TextUtils.isEmpty(password) || password.length() < 6) {
                    mActivityBindingEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(BindingEmailActivity.this, R.drawable.shape_f2_23));
                    mActivityBindingEmailBinding.tvConfirm.setTextColor(Color.parseColor("#C0C0C0"));
                } else {
                    mActivityBindingEmailBinding.tvConfirm.setBackground(ContextCompat.getDrawable(BindingEmailActivity.this, R.drawable.shape_main_23));
                    mActivityBindingEmailBinding.tvConfirm.setTextColor(ContextCompat.getColor(BindingEmailActivity.this, R.color.white));
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
            case R.id.view_next:
                startActivity(new Intent(this, HomeActivity.class));
                mHandler.removeMessages(0);
                finish();
                break;
            case R.id.view_getCode:
                if (!isSend) {
                    codeCheck();
                }
                break;
            case R.id.view_password:
                if (showPassword) {
                    showPassword = false;
                    mActivityBindingEmailBinding.ivPassword.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.login_eye_open));
                    mActivityBindingEmailBinding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    showPassword = true;
                    mActivityBindingEmailBinding.ivPassword.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.login_eye));
                    mActivityBindingEmailBinding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mActivityBindingEmailBinding.etPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                if (!TextUtils.isEmpty(mActivityBindingEmailBinding.etPassword.getText().toString())) {
                    mActivityBindingEmailBinding.etPassword.setSelection(mActivityBindingEmailBinding.etPassword.getText().toString().length());
                }
                break;
            case R.id.tv_confirm:
                checkContent();
                break;
            default:
        }
    }

    /**
     * 输入内容检查
     */
    private void checkContent() {
        mUsername = mActivityBindingEmailBinding.etName.getText().toString().trim();
        mPassword = mActivityBindingEmailBinding.etPassword.getText().toString().trim();
        mCode = mActivityBindingEmailBinding.etCode.getText().toString().trim();
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
                .compose(RxHelper.observableToMain(BindingEmailActivity.this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        userInfoEntity.setMail(mUsername);
                        updateUserInfoEntity(userInfoEntity);
                        startActivity(new Intent(BindingEmailActivity.this, HomeActivity.class));
                        mHandler.removeMessages(0);
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(BindingEmailActivity.this, resultCode);
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

    private void codeCheck() {
        mUsername = mActivityBindingEmailBinding.etName.getText().toString().trim();
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

    private boolean isEmail(String strEmail) {
        String regEx = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return strEmail.matches(regEx);
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
                            ToastUtils.show(BindingEmailActivity.this, getString(R.string.change_account));
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(BindingEmailActivity.this, resultCode);
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
                        ToastUtils.showError(BindingEmailActivity.this, resultCode);
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (time > 0) {
                --time;
                String timeValue = UnitConversionUtil.getUnitConversionUtil().timeToMS(time);
                mActivityBindingEmailBinding.tvTime.setText(timeValue);
                mHandler.postDelayed(() -> mHandler.sendEmptyMessage(0), 1000);
            } else {
                mActivityBindingEmailBinding.tvTime.setText(R.string.get_now);
                isSend = false;
            }
        }
    };

}