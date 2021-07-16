package com.meilancycling.mema.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.FragmentInitGenderBinding;


/**
 * @Description: 性别
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 2:17 PM
 */
public class GenderFragment extends BaseFragment {
    private FragmentInitGenderBinding mFragmentInitGenderBinding;
    private PerfectInformationActivity activity;
    private int gender;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentInitGenderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_init_gender, container, false);
        activity = (PerfectInformationActivity) getActivity();
        assert activity != null;
        gender = activity.mUserInfoEntity.getGender();
        showUi();
        mFragmentInitGenderBinding.viewBg.setOnClickListener(v -> {
            if (gender == 1) {
                gender = 2;
            } else {
                gender = 1;
            }
            activity.mUserInfoEntity.setGender(gender);
            showUi();
        });
        return mFragmentInitGenderBinding.getRoot();
    }

    private void showUi() {
        if (gender == 1) {
            mFragmentInitGenderBinding.tvItem1.setBackground(ContextCompat.getDrawable(activity, R.drawable.shape_main_40));
            mFragmentInitGenderBinding.tvItem1.setTextColor(ContextCompat.getColor(activity, R.color.white));
            mFragmentInitGenderBinding.tvItem2.setBackground(null);
            mFragmentInitGenderBinding.tvItem2.setTextColor(ContextCompat.getColor(activity, R.color.main_color));
        } else {
            mFragmentInitGenderBinding.tvItem2.setBackground(ContextCompat.getDrawable(activity, R.drawable.shape_main_40));
            mFragmentInitGenderBinding.tvItem2.setTextColor(ContextCompat.getColor(activity, R.color.white));
            mFragmentInitGenderBinding.tvItem1.setBackground(null);
            mFragmentInitGenderBinding.tvItem1.setTextColor(ContextCompat.getColor(activity, R.color.main_color));
        }
    }
}
