package com.meilancycling.mema.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.databinding.FragmentInitUnitBinding;
import com.meilancycling.mema.utils.AppUtils;


/**
 * @Description: 性别
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 2:17 PM
 */
public class UnitFragment extends BaseFragment {
    private FragmentInitUnitBinding mFragmentInitUnitBinding;
    private PerfectInformationActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentInitUnitBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_init_unit, container, false);
        activity = (PerfectInformationActivity) getActivity();
        showUi();
        mFragmentInitUnitBinding.viewBg.setOnClickListener(v -> {
            if (Config.unit == Unit.IMPERIAL.value) {
                Config.unit = Unit.METRIC.value;
            } else {
                Config.unit = Unit.IMPERIAL.value;
            }
            activity.mUserInfoEntity.setUnit(Config.unit);
            showUi();
        });
        return mFragmentInitUnitBinding.getRoot();
    }

    private void showUi() {
        if ( Config.unit == Unit.METRIC.value) {
            mFragmentInitUnitBinding.tvItem1.setBackground(ContextCompat.getDrawable(activity, R.drawable.shape_main_40));
            mFragmentInitUnitBinding.tvItem1.setTextColor(ContextCompat.getColor(activity, R.color.white));
            mFragmentInitUnitBinding.tvItem2.setBackground(null);
            mFragmentInitUnitBinding.tvItem2.setTextColor(ContextCompat.getColor(activity, R.color.main_color));
        } else {
            mFragmentInitUnitBinding.tvItem2.setBackground(ContextCompat.getDrawable(activity, R.drawable.shape_main_40));
            mFragmentInitUnitBinding.tvItem2.setTextColor(ContextCompat.getColor(activity, R.color.white));
            mFragmentInitUnitBinding.tvItem1.setBackground(null);
            mFragmentInitUnitBinding.tvItem1.setTextColor(ContextCompat.getColor(activity, R.color.main_color));
        }
    }
}
