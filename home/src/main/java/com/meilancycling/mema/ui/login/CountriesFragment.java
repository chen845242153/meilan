package com.meilancycling.mema.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.databinding.FragmentInitCountriesBinding;
import com.meilancycling.mema.databinding.FragmentInitGenderBinding;
import com.meilancycling.mema.ui.setting.ChooseCountryActivity;
import com.meilancycling.mema.utils.AppUtils;


/**
 * @Description: 性别
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 2:17 PM
 */
public class CountriesFragment extends BaseFragment {
    private FragmentInitCountriesBinding mFragmentInitCountriesBinding;
    private PerfectInformationActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentInitCountriesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_init_countries, container, false);
        activity = (PerfectInformationActivity) getActivity();
        return mFragmentInitCountriesBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String country = activity.mUserInfoEntity.getCountry();
        if (TextUtils.isEmpty(country)) {
            mFragmentInitCountriesBinding.tvCountries.setText(R.string.select);
            activity.isClick(false);
        } else {
            String[] split = country.split("&");
            if (split.length == 2) {
                if (AppUtils.isChinese()) {
                    mFragmentInitCountriesBinding.tvCountries.setText(split[0]);
                } else {
                    mFragmentInitCountriesBinding.tvCountries.setText(split[1]);
                }
            }
            activity.isClick(true);
        }
        mFragmentInitCountriesBinding.viewSelect.setOnClickListener(v -> startActivityForResult(new Intent(mActivity, ChooseCountryActivity.class), 100));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            String result = data.getStringExtra("result");
            activity.nationalFlag = data.getStringExtra("nationalFlag");
            activity.mUserInfoEntity.setCountry(result);
            String[] split = result.split("&");
            if (split.length == 2) {
                if (AppUtils.isChinese()) {
                    mFragmentInitCountriesBinding.tvCountries.setText(split[0]);
                } else {
                    mFragmentInitCountriesBinding.tvCountries.setText(split[1]);
                }
            }
            activity.isClick(true);
        }
    }
}
