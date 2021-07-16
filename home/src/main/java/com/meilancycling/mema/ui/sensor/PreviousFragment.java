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
import com.meilancycling.mema.databinding.FragmentSensorPreviousBinding;

/**
 * @Description: 上一路段
 * @Author: sore_lion
 * @CreateDate: 2020/9/2 10:57 AM
 */
public class PreviousFragment extends BaseFragment {
    /**
     * 0 平均值
     * 1 最大值
     */
    private int dataType;
    private FragmentSensorPreviousBinding mFragmentSensorPreviousBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentSensorPreviousBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sensor_previous, container, false);
        dataType = 0;
        updateUi();
        mFragmentSensorPreviousBinding.view.setOnClickListener(v -> {
            if (dataType == 0) {
                dataType = 1;
            } else {
                dataType = 0;
            }
            updateUi();
        });
        return mFragmentSensorPreviousBinding.getRoot();
    }

    public void updateUi() {
        if (dataType == 0) {
            mFragmentSensorPreviousBinding.sensorItem1.setSensorItemViewData(SensorType.last_avg_speed);
            mFragmentSensorPreviousBinding.sensorItem2.setSensorItemViewData(SensorType.last_avg_cadence);
            mFragmentSensorPreviousBinding.sensorItem3.setSensorItemViewData(SensorType.last_avg_hrm);
            mFragmentSensorPreviousBinding.sensorItem4.setSensorItemViewData(SensorType.last_avg_power);
        } else {
            mFragmentSensorPreviousBinding.sensorItem1.setSensorItemViewData(SensorType.last_max_speed);
            mFragmentSensorPreviousBinding.sensorItem2.setSensorItemViewData(SensorType.last_max_cadence);
            mFragmentSensorPreviousBinding.sensorItem3.setSensorItemViewData(SensorType.last_max_hrm);
            mFragmentSensorPreviousBinding.sensorItem4.setSensorItemViewData(SensorType.last_max_power);
        }
        mFragmentSensorPreviousBinding.sensorItem5.setSensorItemViewData(SensorType.last_sport_distance);
        mFragmentSensorPreviousBinding.sensorItem6.setSensorItemViewData(SensorType.last_sport_time);
    }
}
