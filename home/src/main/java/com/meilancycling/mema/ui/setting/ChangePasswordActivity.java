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
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.ActivityChangePasswordBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.Encryption;
import com.meilancycling.mema.network.bean.request.GetCodeRequest;
import com.meilancycling.mema.network.bean.response.GetKeyBean;
import com.meilancycling.mema.network.bean.response.ResetPasswordRequest;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 修改密码
 *
 * @author lion
 */
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    private ActivityChangePasswordBinding mActivityChangePasswordBinding;
    private UserInfoEntity mUserInfoEntity;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityChangePasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        mUserInfoEntity = getUserInfoEntity();
        if (mUserInfoEntity.getRegType() == Config.TYPE_PHONE) {
            mActivityChangePasswordBinding.tvUserName.setText(mUserInfoEntity.getPhone());
        } else {
            mActivityChangePasswordBinding.tvUserName.setText(mUserInfoEntity.getMail());
        }
        mActivityChangePasswordBinding.viewBack.setOnClickListener(this);
        mActivityChangePasswordBinding.viewCode.setOnClickListener(this);
        mActivityChangePasswordBinding.tvConfirm.setOnClickListener(this);
        time = 0;

        mActivityChangePasswordBinding.etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String code = s.toString().trim();
                String password = mActivityChangePasswordBinding.etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(code) || TextUtils.isEmpty(password) || password.length() < 6) {
                    mActivityChangePasswordBinding.tvConfirm.setBackground(ContextCompat.getDrawable(ChangePasswordActivity.this, R.drawable.shape_f2_23));
                    mActivityChangePasswordBinding.tvConfirm.setTextColor(Color.parseColor("#C0C0C0"));
                } else {
                    mActivityChangePasswordBinding.tvConfirm.setBackground(ContextCompat.getDrawable(ChangePasswordActivity.this, R.drawable.shape_main_23));
                    mActivityChangePasswordBinding.tvConfirm.setTextColor(ContextCompat.getColor(ChangePasswordActivity.this, R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mActivityChangePasswordBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString().trim();
                String code = mActivityChangePasswordBinding.etCode.getText().toString().trim();
                if (TextUtils.isEmpty(code) || TextUtils.isEmpty(password) || password.length() < 6) {
                    mActivityChangePasswordBinding.tvConfirm.setBackground(ContextCompat.getDrawable(ChangePasswordActivity.this, R.drawable.shape_f2_23));
                    mActivityChangePasswordBinding.tvConfirm.setTextColor(Color.parseColor("#C0C0C0"));
                } else {
                    mActivityChangePasswordBinding.tvConfirm.setBackground(ContextCompat.getDrawable(ChangePasswordActivity.this, R.drawable.shape_main_23));
                    mActivityChangePasswordBinding.tvConfirm.setTextColor(ContextCompat.getColor(ChangePasswordActivity.this, R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                mActivityChangePasswordBinding.tvCode.setText(timeValue);
                mHandler.postDelayed(() -> mHandler.sendEmptyMessage(0), 1000);
            } else {
                mActivityChangePasswordBinding.tvCode.setText(R.string.get_now);
            }
        }
    };

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
                String password = mActivityChangePasswordBinding.etPassword.getText().toString().trim();
                String code = mActivityChangePasswordBinding.etCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.show(this, getString(R.string.CODE_20508));
                    return;
                }
                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    ToastUtils.show(this, getString(R.string.password_length));
                    return;
                }
                updatePassword();
                break;
            default:
        }
    }


    /**
     * 发送短信或者邮箱
     */
    public void getCode() {
        GetCodeRequest getCodeRequest = new GetCodeRequest();
        getCodeRequest.setMesType(String.valueOf(2));
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
                        ToastUtils.showError(ChangePasswordActivity.this, resultCode);
                    }
                });
    }

    /**
     * 修改密码
     */
    private void updatePassword() {
        showLoadingDialog();
        getKey();
    }

    /**
     * 获取RSA公钥+私钥缓存KEY
     */
    public void getKey() {
        RetrofitUtils.getApiUrl().getKey("password/security/getkey")
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<GetKeyBean>() {
                    @Override
                    public void onSuccess(GetKeyBean getKeyBean) {
                        String publicKey = getKeyBean.getPublicKey();
                        String cacheKey = getKeyBean.getCacheKey();
                        encryption(cacheKey, publicKey);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(ChangePasswordActivity.this, resultCode);
                    }
                });
    }

    /**
     * 密码加密
     */
    public void encryption(String cacheKey, String publicKey) {
        Encryption encryption = new Encryption();
        encryption.setPassword(stringMd5(mActivityChangePasswordBinding.etPassword.getText().toString().trim()));
        encryption.setPublicKey(publicKey);
        RetrofitUtils.getApiUrl().encryption(encryption)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<String>() {
                    @Override
                    public void onSuccess(String string) {
                        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
                        resetPasswordRequest.setCacheKey(cacheKey);
                        if (mUserInfoEntity.getRegType() == Config.TYPE_PHONE) {
                            mActivityChangePasswordBinding.tvUserName.setText(mUserInfoEntity.getPhone());
                            resetPasswordRequest.setMobile(mUserInfoEntity.getPhone());
                            resetPasswordRequest.setReceiveType(String.valueOf(1));
                        } else {
                            resetPasswordRequest.setMobile(mUserInfoEntity.getMail());
                            resetPasswordRequest.setReceiveType(String.valueOf(2));
                        }
                        resetPasswordRequest.setSmsCode(mActivityChangePasswordBinding.etCode.getText().toString().trim());
                        resetPasswordRequest.setPassword(string);
                        resetPassword(resetPasswordRequest);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(ChangePasswordActivity.this, resultCode);
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

    /**
     * 重置登录密码
     */
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        RetrofitUtils.getApiUrl().resetPassword(resetPasswordRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadingDialog();
                        ToastUtils.show(ChangePasswordActivity.this, getString(R.string.password_reset_complete));
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(ChangePasswordActivity.this, resultCode);
                    }
                });
    }
}