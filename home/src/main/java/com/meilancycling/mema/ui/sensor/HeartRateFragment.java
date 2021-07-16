package com.meilancycling.mema.ui.sensor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.databinding.FragmentSensorHeartBinding;
import com.meilancycling.mema.db.HeartZoneEntity;
import com.meilancycling.mema.service.SensorControllerService;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.List;

/**
 * @Description: 心率
 * @Author: sore_lion
 * @CreateDate: 2020/9/2 9:38 AM
 */
public class HeartRateFragment extends BaseFragment {
    private FragmentSensorHeartBinding mFragmentSensorHeartBinding;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentSensorHeartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sensor_heart, container, false);
        SensorHomeActivity activity = (SensorHomeActivity) getActivity();
//        if (activity != null) {
//            HeartZoneEntity heartZoneEntity = activity.mHeartZoneEntity;
//            if (heartZoneEntity.getSelect() == 0) {
//                mFragmentSensorHeartBinding.tvZoneValue1.setText(getString(R.string.less_equal) + heartZoneEntity.getMaxZoneValue1());
//                mFragmentSensorHeartBinding.tvZoneValue2.setText(heartZoneEntity.getMaxZoneValue2() + "-" + (heartZoneEntity.getMaxZoneValue3() - 1));
//                mFragmentSensorHeartBinding.tvZoneValue3.setText(heartZoneEntity.getMaxZoneValue3() + "-" + (heartZoneEntity.getMaxZoneValue4() - 1));
//                mFragmentSensorHeartBinding.tvZoneValue4.setText(heartZoneEntity.getMaxZoneValue4() + "-" + (heartZoneEntity.getMaxZoneValue5() - 1));
//                mFragmentSensorHeartBinding.tvZoneValue5.setText(getString(R.string.greater_equal) + heartZoneEntity.getMaxZoneValue5());
//            } else {
//                mFragmentSensorHeartBinding.tvZoneValue1.setText(getString(R.string.less_equal) + heartZoneEntity.getReserveZoneValue1());
//                mFragmentSensorHeartBinding.tvZoneValue2.setText(heartZoneEntity.getReserveZoneValue2() + "-" + (heartZoneEntity.getReserveZoneValue3() - 1));
//                mFragmentSensorHeartBinding.tvZoneValue3.setText(heartZoneEntity.getReserveZoneValue3() + "-" + (heartZoneEntity.getReserveZoneValue4() - 1));
//                mFragmentSensorHeartBinding.tvZoneValue4.setText(heartZoneEntity.getReserveZoneValue4() + "-" + (heartZoneEntity.getReserveZoneValue5() - 1));
//                mFragmentSensorHeartBinding.tvZoneValue5.setText(getString(R.string.greater_equal) + heartZoneEntity.getReserveZoneValue5());
//            }
//        }
        updateUi();
        return mFragmentSensorHeartBinding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    public void updateUi() {
        List<Integer> percentage = AppUtils.percentage(SensorControllerService.sessionData.getHrm_zone1(),
                SensorControllerService.sessionData.getHrm_zone2(),
                SensorControllerService.sessionData.getHrm_zone3(),
                SensorControllerService.sessionData.getHrm_zone4(),
                SensorControllerService.sessionData.getHrm_zone5()
        );
        mFragmentSensorHeartBinding.tvTime1.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getHrm_zone1()));
        mFragmentSensorHeartBinding.tvTime2.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getHrm_zone2()));
        mFragmentSensorHeartBinding.tvTime3.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getHrm_zone3()));
        mFragmentSensorHeartBinding.tvTime4.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getHrm_zone4()));
        mFragmentSensorHeartBinding.tvTime5.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getHrm_zone5()));
        mFragmentSensorHeartBinding.tvPercent1.setText(percentage.get(0) + "%");
        mFragmentSensorHeartBinding.tvPercent2.setText(percentage.get(1) + "%");
        mFragmentSensorHeartBinding.tvPercent3.setText(percentage.get(2) + "%");
        mFragmentSensorHeartBinding.tvPercent4.setText(percentage.get(3) + "%");
        mFragmentSensorHeartBinding.tvPercent5.setText(percentage.get(4) + "%");

        mFragmentSensorHeartBinding.heartZone.setProgress(percentage);

        if (SensorControllerService.sessionData.getMax_hrm() != 0xFFFF) {
            mFragmentSensorHeartBinding.stvMax.setText(String.valueOf(SensorControllerService.sessionData.getMax_hrm()));
        } else {
            mFragmentSensorHeartBinding.stvMax.setText(R.string.invalid_value);
        }
        if (SensorControllerService.sessionData.getAvg_hrm() != 0xFFFF) {
            mFragmentSensorHeartBinding.stvAvg.setText(String.valueOf(SensorControllerService.sessionData.getAvg_hrm()));
        } else {
            mFragmentSensorHeartBinding.stvAvg.setText(R.string.invalid_value);
        }
        if (SensorControllerService.mRealtimeBean.getHrm() != 0xFFFF) {
            mFragmentSensorHeartBinding.stvCurrent.setText(String.valueOf(SensorControllerService.mRealtimeBean.getHrm()));
        } else {
            mFragmentSensorHeartBinding.stvCurrent.setText(R.string.invalid_value);
        }
    }
}
