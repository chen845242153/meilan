package com.meilancycling.mema.ui.sensor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.SensorType;
import com.meilancycling.mema.databinding.FragmentSensorCurrentBinding;

/**
 * @Description: 当前段
 * @Author: sore_lion
 * @CreateDate: 2/20/21 2:05 PM
 */
public class CurrentFragment extends BaseFragment {
    private FragmentSensorCurrentBinding mFragmentSensorCurrentBinding;
    /**
     * 0 当前值
     * 1 平均值
     * 2 最大值
     */
    private int dataType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentSensorCurrentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sensor_current, container, false);
        dataType = 0;
        updateUi();
        mFragmentSensorCurrentBinding.view.setOnClickListener(v -> {
            if (dataType == 2) {
                dataType = 0;
            } else {
                ++dataType;
            }
            updateUi();
        });
        return mFragmentSensorCurrentBinding.getRoot();
    }


    public void updateUi() {
        switch (dataType) {
            case 0:
                mFragmentSensorCurrentBinding.sensorItem1.setSensorItemViewData(SensorType.speed);
                mFragmentSensorCurrentBinding.sensorItem2.setSensorItemViewData(SensorType.cadence);
                mFragmentSensorCurrentBinding.sensorItem3.setSensorItemViewData(SensorType.hrm);
                mFragmentSensorCurrentBinding.sensorItem4.setSensorItemViewData(SensorType.power);
                break;
            case 1:
                mFragmentSensorCurrentBinding.sensorItem1.setSensorItemViewData(SensorType.current_avg_speed);
                mFragmentSensorCurrentBinding.sensorItem2.setSensorItemViewData(SensorType.current_avg_cadence);
                mFragmentSensorCurrentBinding.sensorItem3.setSensorItemViewData(SensorType.current_avg_hrm);
                mFragmentSensorCurrentBinding.sensorItem4.setSensorItemViewData(SensorType.current_avg_power);
                break;
            case 2:
                mFragmentSensorCurrentBinding.sensorItem1.setSensorItemViewData(SensorType.current_max_speed);
                mFragmentSensorCurrentBinding.sensorItem2.setSensorItemViewData(SensorType.current_max_cadence);
                mFragmentSensorCurrentBinding.sensorItem3.setSensorItemViewData(SensorType.current_max_hrm);
                mFragmentSensorCurrentBinding.sensorItem4.setSensorItemViewData(SensorType.current_max_power);
                break;
            default:
        }
        mFragmentSensorCurrentBinding.sensorItem5.setSensorItemViewData(SensorType.sport_distance);
        mFragmentSensorCurrentBinding.sensorItem6.setSensorItemViewData(SensorType.current_sport_time);
    }
}
