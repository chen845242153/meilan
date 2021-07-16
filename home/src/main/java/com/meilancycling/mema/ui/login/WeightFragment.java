package com.meilancycling.mema.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.databinding.FragmentInitWeightBinding;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;


/**
 * @Description: 性别
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 2:17 PM
 */
public class WeightFragment extends BaseFragment {
    private FragmentInitWeightBinding mFragmentInitWeightBinding;
    private PerfectInformationActivity activity;
    private int weight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentInitWeightBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_init_weight, container, false);
        activity = (PerfectInformationActivity) getActivity();
        assert activity != null;
        weight = activity.mUserInfoEntity.getWeight();
        UnitBean weightSetting = UnitConversionUtil.getUnitConversionUtil().weightSetting(activity, weight);
        mFragmentInitWeightBinding.tvValue.setText(weightSetting.getValue());
        mFragmentInitWeightBinding.tvUnit.setText(weightSetting.getUnit());
        mFragmentInitWeightBinding.rv.setCurrentAndType(AppUtils.stringToFloat(weightSetting.getValue()), Config.unit, 1);
        mFragmentInitWeightBinding.rv.setCurrentValueCallback(currentValue -> {
            if (Config.unit == Unit.IMPERIAL.value) {
                weight = (int) Math.round(AppUtils.multiplyDouble(currentValue * 100, 0.4536));
            } else {
                weight = (int) currentValue * 100;
            }
            activity.mUserInfoEntity.setWeight(weight);
            UnitBean weightBean = UnitConversionUtil.getUnitConversionUtil().weightSetting(activity, weight);
            mFragmentInitWeightBinding.tvValue.setText(weightBean.getValue());
            mFragmentInitWeightBinding.tvUnit.setText(weightBean.getUnit());
        });
        return mFragmentInitWeightBinding.getRoot();
    }


}
