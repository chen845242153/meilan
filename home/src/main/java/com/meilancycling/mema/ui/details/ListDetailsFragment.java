package com.meilancycling.mema.ui.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.response.MotionDetailsResponse;
import com.meilancycling.mema.databinding.FragmentListDetailsBinding;
import com.meilancycling.mema.utils.UnitConversionUtil;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 2:17 PM
 */
public class ListDetailsFragment extends BaseFragment {
    FragmentListDetailsBinding mFragmentListDetailsBinding;
    DetailsHomeActivity baseActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentListDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_details, container, false);
        baseActivity = (DetailsHomeActivity) getActivity();
        updateUi();
        return mFragmentListDetailsBinding.getRoot();
    }

    /**
     * 页面页面
     */
    @SuppressLint("SetTextI18n")
    private void updateUi() {
        if ( DetailsHomeActivity.mMotionDetailsResponse!=null&& DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo()!=null){
            MotionDetailsResponse.MotionCyclingResponseVoBean motionCyclingResponseVo = DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo();
            mFragmentListDetailsBinding.tvDetailsTime.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(motionCyclingResponseVo.getActivityTime()));
            mFragmentListDetailsBinding.tvDetailsTotalTime.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(motionCyclingResponseVo.getTotalTime()));
            UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), motionCyclingResponseVo.getDistance());
            mFragmentListDetailsBinding.tvDetailsDistance.setText(unitBean.getValue() + unitBean.getUnit());
            UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) motionCyclingResponseVo.getAvgSpeed()/10);
            mFragmentListDetailsBinding.tvDetailsAvgSpeed.setText(speedSetting.getValue() + speedSetting.getUnit());
            UnitBean maxSpeed = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) motionCyclingResponseVo.getMaxSpeed()/10);
            mFragmentListDetailsBinding.tvDetailsMaxSpeed.setText(maxSpeed.getValue() + maxSpeed.getUnit());
            if (motionCyclingResponseVo.getAvgCadence() == 0 || motionCyclingResponseVo.getMaxCadence() == 0) {
                mFragmentListDetailsBinding.slDetailsCadence.setVisibility(View.GONE);
            } else {
                mFragmentListDetailsBinding.tvDetailsAvgCadence.setText(motionCyclingResponseVo.getAvgCadence() + getString(R.string.cadence_unit));
                mFragmentListDetailsBinding.tvDetailsMaxCadence.setText(motionCyclingResponseVo.getMaxCadence() + getString(R.string.cadence_unit));
            }
            if (motionCyclingResponseVo.getAvgHrm() == 0 || motionCyclingResponseVo.getMaxHrm() == 0) {
                mFragmentListDetailsBinding.slDetailsHeartRate.setVisibility(View.GONE);
            } else {
                mFragmentListDetailsBinding.tvDetailsAvgHeartRate.setText(motionCyclingResponseVo.getAvgHrm() + getString(R.string.heart_unit));
                mFragmentListDetailsBinding.tvDetailsMaxHeartRate.setText(motionCyclingResponseVo.getMaxHrm() + getString(R.string.heart_unit));
            }
            if (motionCyclingResponseVo.getMaxAltitude() == null
                    || motionCyclingResponseVo.getMinAltitude() == null
                    || motionCyclingResponseVo.getAscent() == null
                    || motionCyclingResponseVo.getDescent() == null) {
                mFragmentListDetailsBinding.slDetailsAltitude.setVisibility(View.GONE);
            } else {
                UnitBean maxAltitude = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(getContext(), motionCyclingResponseVo.getMaxAltitude());
                UnitBean minAltitude = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(getContext(), motionCyclingResponseVo.getMinAltitude());
                UnitBean ascentAltitude = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(getContext(), motionCyclingResponseVo.getAscent());
                UnitBean descentAltitude = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(getContext(), motionCyclingResponseVo.getDescent());
                mFragmentListDetailsBinding.tvDetailsHighestAltitude.setText(maxAltitude.getValue() + maxAltitude.getUnit());
                mFragmentListDetailsBinding.tvDetailsLowestAltitude.setText(minAltitude.getValue() + minAltitude.getUnit());
                mFragmentListDetailsBinding.tvDetailsRise.setText(ascentAltitude.getValue() + ascentAltitude.getUnit());
                mFragmentListDetailsBinding.tvDetailsDecline.setText(descentAltitude.getValue() + descentAltitude.getUnit());
            }
            if (motionCyclingResponseVo.getAvgTemperature() == null || motionCyclingResponseVo.getMaxTemperature() == null) {
                mFragmentListDetailsBinding.slDetailsTemperature.setVisibility(View.GONE);
            } else {
                UnitBean avgTemperature = UnitConversionUtil.getUnitConversionUtil().temperatureSetting(getContext(), motionCyclingResponseVo.getAvgTemperature());
                UnitBean maxTemperature = UnitConversionUtil.getUnitConversionUtil().temperatureSetting(getContext(), motionCyclingResponseVo.getMaxTemperature());
                mFragmentListDetailsBinding.tvDetailsAvgTemperature.setText(avgTemperature.getValue() + avgTemperature.getUnit());
                mFragmentListDetailsBinding.tvDetailsMaxTemperature.setText(maxTemperature.getValue() + maxTemperature.getUnit());
            }
            if (motionCyclingResponseVo.getMaxPower() == 0 || motionCyclingResponseVo.getAvgPower() == 0) {
                mFragmentListDetailsBinding.slDetailsPower.setVisibility(View.GONE);
            } else {
                mFragmentListDetailsBinding.tvDetailsMaxPower.setText(motionCyclingResponseVo.getMaxPower() + getString(R.string.unit_w));
                mFragmentListDetailsBinding.tvDetailsAvgPower.setText(motionCyclingResponseVo.getAvgPower() + getString(R.string.unit_w));
            }
            if (motionCyclingResponseVo.getTotalCalories() == 0) {
                mFragmentListDetailsBinding.slDetailsEnergyConsumption.setVisibility(View.GONE);
            } else {
                mFragmentListDetailsBinding.tvDetailsCalories.setText(motionCyclingResponseVo.getTotalCalories() + getString(R.string.unit_cal));
            }
        }
    }
}
