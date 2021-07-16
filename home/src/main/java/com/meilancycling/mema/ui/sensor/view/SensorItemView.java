package com.meilancycling.mema.ui.sensor.view;


import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.SensorType;
import com.meilancycling.mema.databinding.SensorItemBinding;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.service.SensorControllerService;
import com.meilancycling.mema.ui.sensor.SensorChooseActivity;
import com.meilancycling.mema.ui.sensor.SensorHomeActivity;
import com.meilancycling.mema.utils.UnitConversionUtil;

/**
 * @Description: 传感器条目view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class SensorItemView extends LinearLayout {
    private SensorItemBinding mSensorItemBinding;

    public SensorItemView(Context context) {
        super(context);
        init();
    }

    public SensorItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SensorItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mSensorItemBinding = DataBindingUtil.inflate(inflater, R.layout.sensor_item, this, false);
            this.addView(mSensorItemBinding.getRoot());
        }
    }

    /**
     * 设置数据
     */
    public void setSensorItemViewData(SensorType sensorType) {
        if (SensorControllerService.sessionData == null) {
            return;
        }

        int timeSize = 36;
        int normalSize = 50;
        switch (sensorType) {
            case sport_time:
                mSensorItemBinding.tvTitle.setText(getContext().getString(R.string.time_sport));
                mSensorItemBinding.ctvContent.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getActivity_time()));
                mSensorItemBinding.tvTitleUnit.setText("");
                mSensorItemBinding.ctvContent.setTextSize(timeSize);
                break;
            case total_time:
                mSensorItemBinding.tvTitle.setText(getContext().getString(R.string.total_time_details));
                mSensorItemBinding.ctvContent.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getElapsed_time()));
                mSensorItemBinding.tvTitleUnit.setText("");
                mSensorItemBinding.ctvContent.setTextSize(timeSize);
                break;
            case current_sport_time:
                mSensorItemBinding.tvTitle.setText(getContext().getString(R.string.time_sport));
                mSensorItemBinding.ctvContent.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.lapData.getActivity_time()));
                mSensorItemBinding.tvTitleUnit.setText("");
                mSensorItemBinding.ctvContent.setTextSize(timeSize);
                break;
            case sport_distance:
                mSensorItemBinding.tvTitle.setText(getContext().getString(R.string.distance_sport));
                UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), SensorControllerService.sessionData.getDist_travelled());
                mSensorItemBinding.ctvContent.setText(unitBean.getValue());
                mSensorItemBinding.tvTitleUnit.setText(unitBean.getUnit());
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case speed:
                UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) SensorControllerService.mRealtimeBean.getSpeed() / 10);
                mSensorItemBinding.tvTitle.setText(R.string.speed);
                mSensorItemBinding.tvTitleUnit.setText(speedSetting.getUnit());
                if (SensorControllerService.mRealtimeBean.getSpeed() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(speedSetting.getValue());
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case avg_speed:
                speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) SensorControllerService.sessionData.getAvg_speed() / 10);
                mSensorItemBinding.tvTitle.setText(R.string.average_speed);
                mSensorItemBinding.tvTitleUnit.setText(speedSetting.getUnit());
                if (SensorControllerService.sessionData.getAvg_speed() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(speedSetting.getValue());
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case max_speed:
                speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) SensorControllerService.sessionData.getMax_speed() / 10);
                mSensorItemBinding.tvTitle.setText(R.string.maximum_speed);
                mSensorItemBinding.tvTitleUnit.setText(speedSetting.getUnit());
                if (SensorControllerService.sessionData.getMax_speed() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(speedSetting.getValue());
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case cadence:
                mSensorItemBinding.tvTitle.setText(R.string.cadence);
                mSensorItemBinding.tvTitleUnit.setText(R.string.cadence_unit);
                if (SensorControllerService.mRealtimeBean.getCadence() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.mRealtimeBean.getCadence()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case avg_cadence:
                mSensorItemBinding.tvTitle.setText(R.string.average_cadence);
                mSensorItemBinding.tvTitleUnit.setText(R.string.cadence_unit);
                if (SensorControllerService.sessionData.getAvg_cadence() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.sessionData.getAvg_cadence()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case max_cadence:
                mSensorItemBinding.tvTitle.setText(R.string.maximum_cadence);
                mSensorItemBinding.tvTitleUnit.setText(R.string.cadence_unit);
                if (SensorControllerService.sessionData.getMax_cadence() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.sessionData.getMax_cadence()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case hrm:
                mSensorItemBinding.tvTitle.setText(R.string.heart_rate);
                mSensorItemBinding.tvTitleUnit.setText(R.string.heart_unit);
                if (SensorControllerService.mRealtimeBean.getHrm() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.mRealtimeBean.getHrm()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case avg_hrm:
                mSensorItemBinding.tvTitle.setText(R.string.average_heart_rate);
                mSensorItemBinding.tvTitleUnit.setText(R.string.heart_unit);
                if (SensorControllerService.sessionData.getAvg_hrm() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.sessionData.getAvg_hrm()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case max_hrm:
                mSensorItemBinding.tvTitle.setText(R.string.maximum_heart_rate);
                mSensorItemBinding.tvTitleUnit.setText(R.string.heart_unit);
                if (SensorControllerService.sessionData.getMax_hrm() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.sessionData.getMax_hrm()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case power:
                mSensorItemBinding.tvTitle.setText(R.string.power);
                mSensorItemBinding.tvTitleUnit.setText(R.string.unit_w);
                if (SensorControllerService.mRealtimeBean.getPower() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.mRealtimeBean.getPower()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case left_right_balance:
                mSensorItemBinding.tvTitle.setText(R.string.left_right_balance);
                mSensorItemBinding.tvTitleUnit.setText("");
                if (SensorControllerService.mRealtimeBean.getPower_bal() != 0xFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.mRealtimeBean.getPower_bal()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case avg_left_right_balance:
                mSensorItemBinding.tvTitle.setText(R.string.avg_left_right_balance);
                mSensorItemBinding.tvTitleUnit.setText("");
                if (SensorControllerService.sessionData.getAvg_bal() != 0xFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.sessionData.getAvg_bal()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case avg_power:
                mSensorItemBinding.tvTitle.setText(R.string.average_power);
                mSensorItemBinding.tvTitleUnit.setText(R.string.unit_w);
                if (SensorControllerService.sessionData.getAvg_power() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.sessionData.getAvg_power()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case max_power:
                mSensorItemBinding.tvTitle.setText(R.string.maximum_power);
                mSensorItemBinding.tvTitleUnit.setText(R.string.unit_w);
                if (SensorControllerService.sessionData.getMax_power() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.sessionData.getMax_power()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case calories:
                mSensorItemBinding.tvTitle.setText(R.string.calories);
                mSensorItemBinding.tvTitleUnit.setText(R.string.unit_cal);
                if (SensorControllerService.sessionData.getCalories() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.sessionData.getCalories() / 1000));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case current_avg_speed:
                speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) SensorControllerService.lapData.getAvg_speed() / 10);
                mSensorItemBinding.tvTitle.setText(R.string.average_speed);
                mSensorItemBinding.tvTitleUnit.setText(speedSetting.getUnit());
                if (SensorControllerService.lapData.getAvg_speed() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(speedSetting.getValue());
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case current_max_speed:
                speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) SensorControllerService.lapData.getMax_speed() / 10);
                mSensorItemBinding.tvTitle.setText(R.string.average_speed);
                mSensorItemBinding.tvTitleUnit.setText(speedSetting.getUnit());
                if (SensorControllerService.lapData.getMax_speed() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(speedSetting.getValue());
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }

                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case current_avg_cadence:
                mSensorItemBinding.tvTitle.setText(R.string.average_cadence);
                mSensorItemBinding.tvTitleUnit.setText(R.string.cadence_unit);
                if (SensorControllerService.lapData.getAvg_cadence() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.lapData.getAvg_cadence()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case current_max_cadence:
                mSensorItemBinding.tvTitle.setText(R.string.maximum_cadence);
                mSensorItemBinding.tvTitleUnit.setText(R.string.cadence_unit);
                if (SensorControllerService.lapData.getMax_cadence() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.lapData.getMax_cadence()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case current_avg_hrm:
                mSensorItemBinding.tvTitle.setText(R.string.average_heart_rate);
                mSensorItemBinding.tvTitleUnit.setText(R.string.heart_unit);
                if (SensorControllerService.lapData.getAvg_hrm() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.lapData.getAvg_hrm()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case current_max_hrm:
                mSensorItemBinding.tvTitle.setText(R.string.maximum_heart_rate);
                mSensorItemBinding.tvTitleUnit.setText(R.string.heart_unit);
                if (SensorControllerService.lapData.getMax_hrm() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.lapData.getMax_hrm()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case current_avg_power:
                mSensorItemBinding.tvTitle.setText(R.string.average_power);
                mSensorItemBinding.tvTitleUnit.setText(R.string.unit_w);
                if (SensorControllerService.lapData.getAvg_power() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.lapData.getAvg_power()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case current_max_power:
                mSensorItemBinding.tvTitle.setText(R.string.maximum_power);
                mSensorItemBinding.tvTitleUnit.setText(R.string.unit_w);
                if (SensorControllerService.lapData.getMax_power() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.lapData.getMax_power()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case last_avg_speed:
                speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) SensorControllerService.lastLapData.getAvg_speed() / 10);
                mSensorItemBinding.tvTitle.setText(R.string.average_speed);
                mSensorItemBinding.tvTitleUnit.setText(speedSetting.getUnit());
                if (SensorControllerService.lastLapData.getAvg_speed() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(speedSetting.getValue());
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case last_max_speed:
                speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) SensorControllerService.lastLapData.getMax_speed() / 10);
                mSensorItemBinding.tvTitle.setText(R.string.maximum_speed);
                mSensorItemBinding.tvTitleUnit.setText(speedSetting.getUnit());
                if (SensorControllerService.lastLapData.getMax_speed() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(speedSetting.getValue());
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case last_avg_cadence:
                mSensorItemBinding.tvTitle.setText(R.string.average_cadence);
                mSensorItemBinding.tvTitleUnit.setText(R.string.cadence_unit);
                if (SensorControllerService.lastLapData.getAvg_cadence() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.lastLapData.getAvg_cadence()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case last_max_cadence:
                mSensorItemBinding.tvTitle.setText(R.string.maximum_cadence);
                mSensorItemBinding.tvTitleUnit.setText(R.string.cadence_unit);
                if (SensorControllerService.lastLapData.getMax_cadence() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.lastLapData.getMax_cadence()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case last_avg_hrm:
                mSensorItemBinding.tvTitle.setText(R.string.average_heart_rate);
                mSensorItemBinding.tvTitleUnit.setText(R.string.heart_unit);
                if (SensorControllerService.lastLapData.getAvg_hrm() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.lastLapData.getAvg_hrm()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case last_max_hrm:
                mSensorItemBinding.tvTitle.setText(R.string.maximum_heart_rate);
                mSensorItemBinding.tvTitleUnit.setText(R.string.heart_unit);
                if (SensorControllerService.lastLapData.getMax_hrm() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.lastLapData.getMax_hrm()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case last_avg_power:
                mSensorItemBinding.tvTitle.setText(R.string.average_power);
                mSensorItemBinding.tvTitleUnit.setText(R.string.unit_w);
                if (SensorControllerService.lastLapData.getAvg_power() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.lastLapData.getAvg_power()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case last_max_power:
                mSensorItemBinding.tvTitle.setText(R.string.maximum_power);
                mSensorItemBinding.tvTitleUnit.setText(R.string.unit_w);
                if (SensorControllerService.lastLapData.getMax_hrm() != 0xFFFF) {
                    mSensorItemBinding.ctvContent.setText(String.valueOf(SensorControllerService.lastLapData.getMax_power()));
                } else {
                    mSensorItemBinding.ctvContent.setText(R.string.invalid_value);
                }
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            case last_sport_time:
                mSensorItemBinding.tvTitle.setText(getContext().getString(R.string.time_sport));
                mSensorItemBinding.ctvContent.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.lastLapData.getActivity_time()));
                mSensorItemBinding.tvTitleUnit.setText("");
                mSensorItemBinding.ctvContent.setTextSize(timeSize);
                break;
            case last_sport_distance:
                mSensorItemBinding.tvTitle.setText(getContext().getString(R.string.distance_sport));
                unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), SensorControllerService.lastLapData.getDist_travelled());
                mSensorItemBinding.ctvContent.setText(unitBean.getValue());
                mSensorItemBinding.tvTitleUnit.setText(unitBean.getUnit());
                mSensorItemBinding.ctvContent.setTextSize(normalSize);
                break;
            default:
        }
    }

    public void setSensorItemListener(SensorHomeActivity context, int position) {
        mSensorItemBinding.sl.setOnLongClickListener(v -> {
            Intent intent = new Intent(getContext(), SensorChooseActivity.class);
            intent.putExtra("position", position);
            context.startActivityForResult(intent, 1);
            return false;
        });
    }
}
