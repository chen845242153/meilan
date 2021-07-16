package com.meilancycling.mema.ui.login;

import android.os.Bundle;
import android.util.Log;
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
import com.meilancycling.mema.databinding.FragmentInitHeightBinding;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.ui.login.view.RulerView;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;


/**
 * @Description: 性别
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 2:17 PM
 */
public class HeightFragment extends BaseFragment {
    private FragmentInitHeightBinding mFragmentInitHeightBinding;
    private PerfectInformationActivity activity;
    private int height;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentInitHeightBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_init_height, container, false);
        activity = (PerfectInformationActivity) getActivity();
        assert activity != null;
        height = activity.mUserInfoEntity.getHeight();
        UnitBean heightSetting = UnitConversionUtil.getUnitConversionUtil().heightSetting(activity, height);
        mFragmentInitHeightBinding.tvValue.setText(heightSetting.getValue());
        mFragmentInitHeightBinding.tvUnit.setText(heightSetting.getUnit());
        mFragmentInitHeightBinding.rv.setCurrentAndType( AppUtils.stringToFloat(heightSetting.getValue()), Config.unit, 0);
        mFragmentInitHeightBinding.rv.setCurrentValueCallback(currentValue -> {
            if (Config.unit == Unit.IMPERIAL.value) {
                height = (int) Math.round(AppUtils.multiplyDouble(currentValue, 3048));
            } else {
                height = (int) currentValue * 100;
            }
            activity.mUserInfoEntity.setHeight(height);
            UnitBean heightBean = UnitConversionUtil.getUnitConversionUtil().heightSetting(activity, height);
            mFragmentInitHeightBinding.tvValue.setText(heightBean.getValue());
            mFragmentInitHeightBinding.tvUnit.setText(heightBean.getUnit());
        });
        return mFragmentInitHeightBinding.getRoot();
    }


}
