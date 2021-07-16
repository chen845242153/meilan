package com.meilancycling.mema.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.databinding.FragmentLoginHomeBinding;
import com.meilancycling.mema.ui.setting.WebViewActivity;
import com.meilancycling.mema.utils.AppUtils;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 2:17 PM
 */
public class LoginHomeFragment extends BaseFragment implements View.OnClickListener {
    private FragmentLoginHomeBinding mFragmentLoginHomeBinding;
    private LoginActivity mLoginActivity;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentLoginHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_home, container, false);
        mLoginActivity = (LoginActivity) getActivity();
        mFragmentLoginHomeBinding.tvLogin.setOnClickListener(this);
        mFragmentLoginHomeBinding.tvRegistered.setOnClickListener(this);
        mFragmentLoginHomeBinding.tvUserAgreement.setOnClickListener(this);
        mFragmentLoginHomeBinding.tvUserPrivacy.setOnClickListener(this);
        mFragmentLoginHomeBinding.tvUserAgreement.setText(getString(R.string.agree_agreement) + getString(R.string.user_agreement));
        return mFragmentLoginHomeBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                mLoginActivity.showFragment(new LoginFragment());
                break;
            case R.id.tv_registered:
                mLoginActivity.showFragment(new RegisteredFragment());
                break;
            case R.id.tv_user_agreement:
                if (mLoginActivity.value1 != null) {
                    Intent intent = new Intent(mLoginActivity, WebViewActivity.class);
                    if (AppUtils.isChinese()) {
                        intent.putExtra(WebViewActivity.title, mLoginActivity.value1.getName());
                        intent.putExtra(WebViewActivity.loadUrl, mLoginActivity.value1.getUrl());
                    } else {
                        intent.putExtra(WebViewActivity.title, mLoginActivity.value1.getEnName());
                        intent.putExtra(WebViewActivity.loadUrl, mLoginActivity.value1.getEnUrl());
                    }
                    startActivity(intent);
                }
                break;
            case R.id.tv_user_privacy:
                if (mLoginActivity.value2 != null) {
                    Intent intent = new Intent(mLoginActivity, WebViewActivity.class);
                    if (AppUtils.isChinese()) {
                        intent.putExtra(WebViewActivity.title, mLoginActivity.value2.getName());
                        intent.putExtra(WebViewActivity.loadUrl, mLoginActivity.value2.getUrl());
                    } else {
                        intent.putExtra(WebViewActivity.title, mLoginActivity.value2.getEnName());
                        intent.putExtra(WebViewActivity.loadUrl, mLoginActivity.value2.getEnUrl());
                    }
                    startActivity(intent);
                }
                break;
            default:
        }
    }
}
