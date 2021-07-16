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
import com.meilancycling.mema.databinding.FragmentRegisteredBinding;
import com.meilancycling.mema.network.bean.request.GetCodeRequest;
import com.meilancycling.mema.network.bean.request.PhoneRegisterRequest;
import com.meilancycling.mema.network.bean.response.LoginBean;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 2:17 PM
 */
public class RegisteredFragment extends BaseFragment implements View.OnClickListener {
    private FragmentRegisteredBinding mFragmentRegisteredBinding;
    private LoginActivity mLoginActivity;
    private boolean isSend = false;
    /**
     * 密码是否隐藏
     */
    private boolean showPassword;
    private int time;
    private String mUsername;
    private String mPassword;
    private String mCacheKey;
    private String mCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentRegisteredBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_registered, container, false);
        mLoginActivity = (LoginActivity) getActivity();
        setStatusBar(mFragmentRegisteredBinding.ivBack);
        initView();
        return mFragmentRegisteredBinding.getRoot();
    }

    private void initView() {
        if (AppUtils.isChinese()) {
            mFragmentRegisteredBinding.etName.setHint(R.string.phone_number);
            mFragmentRegisteredBinding.ivLoginItem1.setImageDrawable(ContextCompat.getDrawable(mLoginActivity, R.drawable.login_wx));
            mFragmentRegisteredBinding.ivLoginItem2.setImageDrawable(ContextCompat.getDrawable(mLoginActivity, R.drawable.login_qq));
        } else {
            mFragmentRegisteredBinding.etName.setHint(R.string.mailbox);
            mFragmentRegisteredBinding.ivLoginItem1.setImageDrawable(ContextCompat.getDrawable(mLoginActivity, R.drawable.login_facebook));
            mFragmentRegisteredBinding.ivLoginItem2.setImageDrawable(ContextCompat.getDrawable(mLoginActivity, R.drawable.login_google));
        }
        mFragmentRegisteredBinding.ivBack.setOnClickListener(this);
        mFragmentRegisteredBinding.tvCodeTime.setOnClickListener(this);
        mFragmentRegisteredBinding.ivPassword.setOnClickListener(this);
        mFragmentRegisteredBinding.tvRegistered.setOnClickListener(this);
        mFragmentRegisteredBinding.tvLogin.setOnClickListener(this);
        mFragmentRegisteredBinding.ivLoginItem1.setOnClickListener(this);
        mFragmentRegisteredBinding.ivLoginItem2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_login:
                mLoginActivity.showFragment(new LoginHomeFragment());
                break;
            case R.id.tv_code_time:
                if (!isSend) {
                    sendCode();
                }
                break;
            case R.id.iv_password:
                if (showPassword) {
                    showPassword = false;
                    mFragmentRegisteredBinding.ivPassword.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.login_eye_open));
                    mFragmentRegisteredBinding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    showPassword = true;
                    mFragmentRegisteredBinding.ivPassword.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.login_eye));
                    mFragmentRegisteredBinding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mFragmentRegisteredBinding.etPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                if (!TextUtils.isEmpty(mFragmentRegisteredBinding.etPassword.getText().toString())) {
                    mFragmentRegisteredBinding.etPassword.setSelection(mFragmentRegisteredBinding.etPassword.getText().toString().length());
                }
                break;
            case R.id.tv_registered:
                checkRegistered();
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

    private void sendCode() {
        mUsername = mFragmentRegisteredBinding.etName.getText().toString().trim();
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
        if (TextUtils.isEmpty(result)) {
            ToastUtils.showError(mActivity, resultCode);
        } else {
            String noRegistered = "false";
            if (noRegistered.equals(result)) {
                GetCodeRequest getCodeRequest = new GetCodeRequest();
                getCodeRequest.setMesType(String.valueOf(1));
                getCodeRequest.setMobile(mUsername);
                if (mLoginActivity.isNumLegal(mUsername)) {
                    getCodeRequest.setReceiveType(String.valueOf(1));
                } else {
                    getCodeRequest.setReceiveType(String.valueOf(2));
                }
                mLoginActivity.getCode(this, getCodeRequest);
            } else {
                mLoginActivity.hideLoadingDialog();
                ToastUtils.show(mActivity, getString(R.string.change_account));
            }
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
            ToastUtils.show(mActivity, result);
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
                mFragmentRegisteredBinding.tvCodeTime.setText(timeValue);
                mHandler.postDelayed(() -> mHandler.sendEmptyMessage(0), 1000);
            } else {
                mFragmentRegisteredBinding.tvCodeTime.setText(R.string.get_now);
                isSend = false;
            }
        }
    };


    private void checkRegistered() {
        mUsername = mFragmentRegisteredBinding.etName.getText().toString().trim();
        mPassword = mFragmentRegisteredBinding.etPassword.getText().toString().trim();
        mCode = mFragmentRegisteredBinding.etVerificationCode.getText().toString().trim();
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
            PhoneRegisterRequest phoneRegisterRequest = new PhoneRegisterRequest();
            phoneRegisterRequest.setCacheKey(mCacheKey);
            phoneRegisterRequest.setLoginName(mUsername);
            phoneRegisterRequest.setPassword(password);
            phoneRegisterRequest.setPhoneType(1);
            if (mLoginActivity.isNumLegal(mUsername)) {
                phoneRegisterRequest.setReceiveType(String.valueOf(1));
            } else {
                phoneRegisterRequest.setReceiveType(String.valueOf(2));
            }
            phoneRegisterRequest.setSmsCode(mCode);

            mLoginActivity.mobileRegister(this, phoneRegisterRequest);
        }
    }

    /**
     * register回调
     */
    public void registerCallback(LoginBean loginBean, String resultCode) {
        if (loginBean == null) {
            mLoginActivity.hideLoadingDialog();
            ToastUtils.showError(mActivity, resultCode);
        } else {
            mLoginActivity.loginSuccess(loginBean, Integer.parseInt(loginBean.getRegType()));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
    }
}
