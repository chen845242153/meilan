package com.meilancycling.mema.ui.sensor;

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
import com.meilancycling.mema.databinding.FragmentSensorPowerBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.PowerZoneEntity;
import com.meilancycling.mema.service.SensorControllerService;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.List;

/**
 * @Description: 功率
 * @Author: sore_lion
 * @CreateDate: 2020/9/2 9:39 AM
 */
public class PowerFragment extends BaseFragment {
    /**
     * 0 显示功率
     * 1 显示平衡
     */
    private int dataType;
    private FragmentSensorPowerBinding mFragmentSensorPowerBinding;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentSensorPowerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sensor_power, container, false);
        SensorHomeActivity activity = (SensorHomeActivity) getActivity();
        dataType = 0;
//        DbUtils.getInstance().queryPowerZoneEntity(getUserId());
//        if (activity != null) {
//            PowerZoneEntity powerZoneEntity = activity.mPowerZoneEntity;
//            mFragmentSensorPowerBinding.tvZoneValue1.setText(getString(R.string.less_equal) + powerZoneEntity.getZoneValue1());
//            mFragmentSensorPowerBinding.tvZoneValue2.setText(powerZoneEntity.getZoneValue2() + "-" + (powerZoneEntity.getZoneValue3() - 1));
//            mFragmentSensorPowerBinding.tvZoneValue3.setText(powerZoneEntity.getZoneValue3() + "-" + (powerZoneEntity.getZoneValue4() - 1));
//            mFragmentSensorPowerBinding.tvZoneValue4.setText(powerZoneEntity.getZoneValue4() + "-" + (powerZoneEntity.getZoneValue5() - 1));
//            mFragmentSensorPowerBinding.tvZoneValue5.setText(powerZoneEntity.getZoneValue5() + "-" + (powerZoneEntity.getZoneValue6() - 1));
//            mFragmentSensorPowerBinding.tvZoneValue6.setText(powerZoneEntity.getZoneValue6() + "-" + (powerZoneEntity.getZoneValue7() - 1));
//            mFragmentSensorPowerBinding.tvZoneValue7.setText(getString(R.string.greater_equal) + powerZoneEntity.getZoneValue7());
//        }
        updateUi();
        mFragmentSensorPowerBinding.slRoot.setOnClickListener(v -> {
            if (dataType == 0) {
                dataType = 1;
            } else {
                dataType = 0;
            }
            updateUi();
        });
        return mFragmentSensorPowerBinding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    public void updateUi() {
        if (dataType == 0) {
            mFragmentSensorPowerBinding.tvTitleLeft.setText(R.string.maximum_power);
            mFragmentSensorPowerBinding.tvTitleCenter.setText(R.string.power);
            mFragmentSensorPowerBinding.tvTitleRight.setText(R.string.average_power);
            mFragmentSensorPowerBinding.stvValueLeft.setText(powerValue(SensorControllerService.sessionData.getMax_power()));
            if (SensorControllerService.mRealtimeBean.getPower() != 0xFFFF) {
                mFragmentSensorPowerBinding.stvValueCenter.setText(String.valueOf(SensorControllerService.mRealtimeBean.getPower()));
            } else {
                mFragmentSensorPowerBinding.stvValueCenter.setText(R.string.invalid_value);
            }
            mFragmentSensorPowerBinding.stvValueCenter.setTextSize(60);
            mFragmentSensorPowerBinding.stvValueRight.setText(powerValue(SensorControllerService.sessionData.getAvg_power()));
            mFragmentSensorPowerBinding.stvSeconds5.setText(powerValue(SensorControllerService.mRealtimeBean.getAvgP5s()));
            mFragmentSensorPowerBinding.stvSeconds10.setText(powerValue(SensorControllerService.mRealtimeBean.getAvgP10s()));
            mFragmentSensorPowerBinding.stvSeconds30.setText(powerValue(SensorControllerService.mRealtimeBean.getAvgP30s()));
            mFragmentSensorPowerBinding.stvMinute1.setText(powerValue(SensorControllerService.mRealtimeBean.getAvgP60s()));
            mFragmentSensorPowerBinding.stvMinute5.setText(powerValue(SensorControllerService.mRealtimeBean.getAvgP5m()));
            mFragmentSensorPowerBinding.stvMinute30.setText(powerValue(SensorControllerService.mRealtimeBean.getAvgP30m()));
        } else {
            mFragmentSensorPowerBinding.tvTitleLeft.setText(R.string.power_weight_ratio);
            mFragmentSensorPowerBinding.tvTitleCenter.setText(R.string.left_right_balance);
            mFragmentSensorPowerBinding.tvTitleRight.setText(R.string.avg_left_right_balance);

            if (SensorControllerService.mRealtimeBean.getPWR() != 0xFFFF) {
                mFragmentSensorPowerBinding.stvValueLeft.setText(String.valueOf(AppUtils.multiplyDouble(SensorControllerService.mRealtimeBean.getPWR(), 0.01)));
            } else {
                mFragmentSensorPowerBinding.stvValueLeft.setText(R.string.invalid_value);
            }
            mFragmentSensorPowerBinding.stvValueCenter.setText(powerBalance(SensorControllerService.mRealtimeBean.getPower_bal()));
            mFragmentSensorPowerBinding.stvValueCenter.setTextSize(20);
            mFragmentSensorPowerBinding.stvValueRight.setText(powerBalance(SensorControllerService.sessionData.getAvg_bal()));

            mFragmentSensorPowerBinding.stvSeconds5.setText(powerBalance(SensorControllerService.mRealtimeBean.getAvgB5s()));
            mFragmentSensorPowerBinding.stvSeconds10.setText(powerBalance(SensorControllerService.mRealtimeBean.getAvgB10s()));
            mFragmentSensorPowerBinding.stvSeconds30.setText(powerBalance(SensorControllerService.mRealtimeBean.getAvgB30s()));
            mFragmentSensorPowerBinding.stvMinute1.setText(powerBalance(SensorControllerService.mRealtimeBean.getAvgB60s()));
            mFragmentSensorPowerBinding.stvMinute5.setText(powerBalance(SensorControllerService.mRealtimeBean.getAvgB5m()));
            mFragmentSensorPowerBinding.stvMinute30.setText(powerBalance(SensorControllerService.mRealtimeBean.getAvgB30m()));
        }
        mFragmentSensorPowerBinding.stvNp.setText(powerValue(SensorControllerService.sessionData.getNP()));
        if (SensorControllerService.sessionData.getIF() != 0xFFFF) {
            mFragmentSensorPowerBinding.stvIf.setText(String.valueOf(AppUtils.multiplyDouble(SensorControllerService.sessionData.getIF(), 0.001)));
        } else {
            mFragmentSensorPowerBinding.stvIf.setText(R.string.invalid_value);
        }
        if (SensorControllerService.sessionData.getTSS() != 0xFFFF) {
            mFragmentSensorPowerBinding.stvTss.setText(String.valueOf(AppUtils.multiplyDouble(SensorControllerService.sessionData.getTSS(), 0.1)));
        } else {
            mFragmentSensorPowerBinding.stvTss.setText(R.string.invalid_value);
        }
        List<Integer> percentage = AppUtils.percentage(SensorControllerService.sessionData.getPower_zone1(),
                SensorControllerService.sessionData.getPower_zone2(),
                SensorControllerService.sessionData.getPower_zone3(),
                SensorControllerService.sessionData.getPower_zone4(),
                SensorControllerService.sessionData.getPower_zone5(),
                SensorControllerService.sessionData.getPower_zone6(),
                SensorControllerService.sessionData.getPower_zone7()
        );
        mFragmentSensorPowerBinding.tvTime1.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getPower_zone1()));
        mFragmentSensorPowerBinding.tvTime2.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getPower_zone2()));
        mFragmentSensorPowerBinding.tvTime3.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getPower_zone3()));
        mFragmentSensorPowerBinding.tvTime4.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getPower_zone4()));
        mFragmentSensorPowerBinding.tvTime5.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getPower_zone5()));
        mFragmentSensorPowerBinding.tvTime6.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getPower_zone6()));
        mFragmentSensorPowerBinding.tvTime7.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getPower_zone7()));
        mFragmentSensorPowerBinding.tvPercent1.setText(percentage.get(0) + "%");
        mFragmentSensorPowerBinding.tvPercent2.setText(percentage.get(1) + "%");
        mFragmentSensorPowerBinding.tvPercent3.setText(percentage.get(2) + "%");
        mFragmentSensorPowerBinding.tvPercent4.setText(percentage.get(3) + "%");
        mFragmentSensorPowerBinding.tvPercent5.setText(percentage.get(4) + "%");
        mFragmentSensorPowerBinding.tvPercent6.setText(percentage.get(5) + "%");
        mFragmentSensorPowerBinding.tvPercent7.setText(percentage.get(6) + "%");
        mFragmentSensorPowerBinding.heartZone.setProgress(percentage);
    }

    /**
     * 设置功率值
     */
    private String powerValue(int powerValue) {
        if (powerValue != 0xFFFF) {
            return String.valueOf(powerValue);
        } else {
            return getString(R.string.invalid_value);
        }
    }

    /**
     * 计算功率平衡
     */
    private String powerBalance(int powerBalance) {
        if (powerBalance == 0xFF) {
            return getString(R.string.invalid_value);
        }
        if (((powerBalance >> 7) & 0x01) == 0) {
            return (powerBalance & 0x7F) + "%";
        } else {
            return (100 - (powerBalance & 0x7F)) + "%" + "-" + (powerBalance & 0x7F) + "%";
        }
    }
}
