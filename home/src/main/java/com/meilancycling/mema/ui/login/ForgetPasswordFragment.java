package com.meilancycling.mema.ui.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.databinding.FragmentForgetPasswordBinding;
import com.meilancycling.mema.network.bean.request.GetCodeRequest;
import com.meilancycling.mema.network.bean.response.ResetPasswordRequest;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 2:17 PM
 */
public class ForgetPasswordFragment extends BaseFragment implements View.OnClickListener {
    private FragmentForgetPasswordBinding mFragmentForgetPasswordBinding;
    private LoginActivity mLoginActivity;
    /**
     * 密码是否隐藏
     */
    private boolean showPassword;

    private boolean isSend = false;

    private int time;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentForgetPasswordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_forget_password, container, false);
        mLoginActivity = (LoginActivity) getActivity();
        setStatusBar(mFragmentForgetPasswordBinding.ivBack);
        initView();
        return mFragmentForgetPasswordBinding.getRoot();
    }

    private void initView() {
        if (AppUtils.isChinese()) {
            mFragmentForgetPasswordBinding.etName.setHint(R.string.phone_number);
        } else {
            mFragmentForgetPasswordBinding.etName.setHint(R.string.mailbox);
        }
        mFragmentForgetPasswordBinding.tvCodeTime.setOnClickListener(this);
        mFragmentForgetPasswordBinding.tvConfirm.setOnClickListener(this);
        mFragmentForgetPasswordBinding.ivBack.setOnClickListener(this);
        mFragmentForgetPasswordBinding.ivPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_code_time:
                if (!isSend) {
                    sendCode();
                }
                break;
            case R.id.tv_confirm:
                resetPassword();
                break;
            case R.id.iv_back:
                mLoginActivity.showFragment(new LoginFragment());
                break;
            case R.id.iv_password:
                if (showPassword) {
                    showPassword = false;
                    mFragmentForgetPasswordBinding.ivPassword.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.login_eye_open));
                    mFragmentForgetPasswordBinding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    showPassword = true;
                    mFragmentForgetPasswordBinding.ivPassword.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.login_eye));
                    mFragmentForgetPasswordBinding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mFragmentForgetPasswordBinding.etPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                if (!TextUtils.isEmpty(mFragmentForgetPasswordBinding.etPassword.getText().toString())) {
                    mFragmentForgetPasswordBinding.etPassword.setSelection(mFragmentForgetPasswordBinding.etPassword.getText().toString().length());
                }
                break;
            default:
        }
    }

    private String mUsername;
    private String mPassword;
    private String mCacheKey;
    private String mCode;

    private void sendCode() {
        mUsername = mFragmentForgetPasswordBinding.etName.getText().toString().trim();
        if (TextUtils.isEmpty(mUsername)) {
            ToastUtils.show(mActivity, getString(R.string.account_err));
            return;
        }
        if (!mLoginActivity.isNumLegal(mUsername) && !mLoginActivity.isEmail(mUsername)) {
            ToastUtils.show(mActivity, getString(R.string.account_err));
            return;
        }
        mLoginActivity.showLoadingDialog();
        mLoginActivity.checkPhoneOrMail(this, mUsername);
    }

    /**
     * 判断号码或邮箱是否注册回调
     */
    public void phoneOrMailCallback(String result, String resultCode) {
        if (!TextUtils.isEmpty(result)) {
            String noRegistered = "false";
            if (noRegistered.equals(result)) {
                mLoginActivity.hideLoadingDialog();
                ToastUtils.show(mActivity, getString(R.string.CODE_20501));
            } else {
                GetCodeRequest getCodeRequest = new GetCodeRequest();
                getCodeRequest.setMesType(String.valueOf(2));
                getCodeRequest.setMobile(mUsername);
                if (mLoginActivity.isNumLegal(mUsername)) {
                    getCodeRequest.setReceiveType(String.valueOf(1));
                } else {
                    getCodeRequest.setReceiveType(String.valueOf(2));
                }
                mLoginActivity.getCode(this, getCodeRequest);
            }
        } else {
            mLoginActivity.hideLoadingDialog();
        }
    }
    /**
     * 验证码回调
     */
    public void codeCallback(String result) {
        mLoginActivity.hideLoadingDialog();
        if (TextUtils.isEmpty(result)) {
            isSend = true;
            time = 30 * 60;
            mHandler.sendEmptyMessage(0);
        } else {
            ToastUtils.showError(mActivity, result);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (time > 0) {
                --time;
                String timeValue = UnitConversionUtil.getUnitConversionUtil().timeToMS(time);
                mFragmentForgetPasswordBinding.tvCodeTime.setText(timeValue);
                mHandler.postDelayed(() -> mHandler.sendEmptyMessage(0), 1000);
            } else {
                mFragmentForgetPasswordBinding.tvCodeTime.setText(R.string.get_now);
                isSend = false;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
    }

    private void resetPassword() {
        mUsername = mFragmentForgetPasswordBinding.etName.getText().toString().trim();
        mPassword = mFragmentForgetPasswordBinding.etPassword.getText().toString().trim();
        mCode = mFragmentForgetPasswordBinding.etVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(mUsername)) {
            ToastUtils.show(mActivity, getString(R.string.account_err));
            return;
        }
        if (!mLoginActivity.isNumLegal(mUsername) && !mLoginActivity.isEmail(mUsername)) {
            ToastUtils.show(mActivity, getString(R.string.account_err));
            return;
        }
        // 密码最小长度为6
        int passwordMinLength = 6;
        if (TextUtils.isEmpty(mPassword) || mPassword.length() < passwordMinLength) {
            ToastUtils.show(mActivity, getString(R.string.password_length));
            return;
        }
        if (TextUtils.isEmpty(mCode)) {
            ToastUtils.show(mActivity, getString(R.string.CODE_20508));
            return;
        }
        mLoginActivity.showLoadingDialog();
        mLoginActivity.getKey(this);
    }

    /**
     * 公钥 私钥回调
     */
    public void keyCallback(String publicKey, String key) {
        if (TextUtils.isEmpty(publicKey)) {
            mLoginActivity.hideLoadingDialog();
            ToastUtils.showError(mActivity, key);
        } else {
            mCacheKey = key;
            mLoginActivity.encryption(this, mPassword, publicKey);
        }
    }

    /**
     * 密码加密回调
     */
    public void encryptionCallback(String password, String resultCode) {
        if (TextUtils.isEmpty(password)) {
            mLoginActivity.hideLoadingDialog();
            ToastUtils.showError(mActivity, resultCode);
        } else {
            ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
            resetPasswordRequest.setCacheKey(mCacheKey);
            resetPasswordRequest.setMobile(mUsername);
            resetPasswordRequest.setSmsCode(mCode);
            resetPasswordRequest.setPassword(password);
            if (mLoginActivity.isNumLegal(mUsername)) {
                resetPasswordRequest.setReceiveType(String.valueOf(1));
            } else {
                resetPasswordRequest.setReceiveType(String.valueOf(2));
            }
            mLoginActivity.resetPassword(this, resetPasswordRequest);
        }
    }

    /**
     * 修改密码回调
     */
    public void resetPasswordCallback(String result) {
        if (TextUtils.isEmpty(result)) {
            mLoginActivity.hideLoadingDialog();
            ToastUtils.show(mActivity, getString(R.string.password_reset_complete));
            mLoginActivity.showFragment(new LoginFragment());
        } else {
            mLoginActivity.hideLoadingDialog();
            ToastUtils.showError(mActivity, result);
        }
    }
}
