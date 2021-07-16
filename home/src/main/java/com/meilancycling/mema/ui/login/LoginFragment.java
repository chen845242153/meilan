package com.meilancycling.mema.ui.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.meilancycling.mema.databinding.FragmentLoginBinding;
import com.meilancycling.mema.network.bean.request.MobileLogin;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ToastUtils;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 2:17 PM
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private FragmentLoginBinding mFragmentLoginBinding;
    private LoginActivity mLoginActivity;
    /**
     * 密码是否隐藏
     */
    private boolean showPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        mLoginActivity = (LoginActivity) getActivity();
        setStatusBar(mFragmentLoginBinding.ivBack);
        initView();
        showPassword = true;
        return mFragmentLoginBinding.getRoot();
    }

    private void initView() {
        if (AppUtils.isChinese()) {
            mFragmentLoginBinding.etName.setHint(R.string.phone_number);
            mFragmentLoginBinding.ivLoginItem1.setImageDrawable(ContextCompat.getDrawable(mLoginActivity, R.drawable.login_wx));
            mFragmentLoginBinding.ivLoginItem2.setImageDrawable(ContextCompat.getDrawable(mLoginActivity, R.drawable.login_qq));
        } else {
            mFragmentLoginBinding.etName.setHint(R.string.mailbox);
            mFragmentLoginBinding.ivLoginItem1.setImageDrawable(ContextCompat.getDrawable(mLoginActivity, R.drawable.login_facebook));
            mFragmentLoginBinding.ivLoginItem2.setImageDrawable(ContextCompat.getDrawable(mLoginActivity, R.drawable.login_google));
        }
        mFragmentLoginBinding.ivBack.setOnClickListener(this);
        mFragmentLoginBinding.ivPassword.setOnClickListener(this);
        mFragmentLoginBinding.tvLogin.setOnClickListener(this);
        mFragmentLoginBinding.tvForgetPassword.setOnClickListener(this);
        mFragmentLoginBinding.ivLoginItem1.setOnClickListener(this);
        mFragmentLoginBinding.ivLoginItem2.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                mLoginActivity.showFragment(new LoginHomeFragment());
                break;
            case R.id.iv_password:
                if (showPassword) {
                    showPassword = false;
                    mFragmentLoginBinding.ivPassword.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.login_eye_open));
                    mFragmentLoginBinding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    showPassword = true;
                    mFragmentLoginBinding.ivPassword.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.login_eye));
                    mFragmentLoginBinding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mFragmentLoginBinding.etPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                if (!TextUtils.isEmpty(mFragmentLoginBinding.etPassword.getText().toString())) {
                    mFragmentLoginBinding.etPassword.setSelection(mFragmentLoginBinding.etPassword.getText().toString().length());
                }
                break;
            case R.id.tv_forget_password:
                mLoginActivity.showFragment(new ForgetPasswordFragment());
                break;
            case R.id.tv_login:
                checkLogin();
                break;
            case R.id.iv_login_item1:
                if (AppUtils.isChinese()) {
                    mLoginActivity.wxLogin();
                } else {
                    mLoginActivity.facebookLogin();
                }

                break;
            case R.id.iv_login_item2:
                if (AppUtils.isChinese()) {
                    mLoginActivity.qqLogin();
                } else {
                    mLoginActivity.googleLogin();
                }
                break;
            default:
        }
    }

    private String mUsername;
    private String mPassword;
    private String mCacheKey;

    private void checkLogin() {
        mUsername = mFragmentLoginBinding.etName.getText().toString().trim();
        mPassword = mFragmentLoginBinding.etPassword.getText().toString().trim();
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
        mLoginActivity.showLoadingDialog();
        mLoginActivity.checkPhoneOrMail(this, mUsername);
    }

    /**
     * 帐号是否注册
     */
    public void phoneOrMailCallback(String result, String resultCode) {
        if (TextUtils.isEmpty(result)) {
            mLoginActivity.hideLoadingDialog();
            ToastUtils.showError(mActivity, resultCode);
        } else {
            String noRegistered = "false";
            if (noRegistered.equals(result)) {
                mLoginActivity.hideLoadingDialog();
                ToastUtils.show(mActivity, getString(R.string.CODE_20501));
            } else {
                mLoginActivity.getKey(this);
            }
        }
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
            MobileLogin mobileLogin = new MobileLogin();
            mobileLogin.setCacheKey(mCacheKey);
            mobileLogin.setLoginName(mUsername);
            mobileLogin.setPassword(password);
            mobileLogin.setPhoneType(1);
            mLoginActivity.mobileLogin(this, mobileLogin);
        }
    }

    /**
     * 登陆回调
     */
    public void loginFail(String resultCode) {
        mLoginActivity.hideLoadingDialog();
        ToastUtils.showError(mActivity, resultCode);
    }

}
