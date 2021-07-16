package com.meilancycling.mema.ui.setting;

import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.ble.bean.CommandEntity;
import com.meilancycling.mema.ble.command.BleCommandManager;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.databinding.ActivityWarningBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.WarningEntity;
import com.meilancycling.mema.ui.setting.view.RemindIntervalDialog;
import com.meilancycling.mema.ui.setting.view.TwoWarningView;
import com.meilancycling.mema.ui.setting.view.WarningView;
import com.meilancycling.mema.utils.UnitConversionUtil;

/**
 * 锻炼目标
 *
 * @author lion
 */
public class WarningActivity extends BaseActivity {
    public static final int WARNING_CLOSE = 0;
    public static final int WARNING_OPEN = 1;
    public static final int INTERVAL_DEFAULT = 5;
    public static final int INTERVAL_MIN = 1;
    public static final int INTERVAL_MAX = 30;
    public static final int TIME_MIN = 10;
    public static final int TIME_MAX = 999;
    public static final int DISTANCE_MIN = 5 * 1000;
    public static final int DISTANCE_MAX = 999 * 1000;
    public static final int SPEED_MIN = 0;
    public static final int SPEED_MAX = 120 * 1000;
    public static final int CADENCE_MIN = 0;
    public static final int CADENCE_MAX = 300;
    public static final int HEART_MIN = 30;
    public static final int HEART_MAX = 300;
    public static final int POWER_MIN = 0;
    public static final int POWER_MAX = 600;

    private ActivityWarningBinding mActivityWarningBinding;
    private WarningEntity mWarningEntity;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityWarningBinding = DataBindingUtil.setContentView(this, R.layout.activity_warning);
        mActivityWarningBinding.ctvWarning.setData(getString(R.string.exercise_goal), v -> finish());
        mWarningEntity = DbUtils.getInstance().queryWarningEntity(getUserId());
        initView();
        mActivityWarningBinding.llInterval.setOnClickListener(v -> {
            RemindIntervalDialog remindIntervalDialog = new RemindIntervalDialog(WarningActivity.this, mWarningEntity.getWarningInterval());
            remindIntervalDialog.show();
            remindIntervalDialog.setRemindIntervalClickCallback(result -> {
                mWarningEntity.setWarningInterval(result);
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
                mActivityWarningBinding.tvIntervalValue.setText(mWarningEntity.getWarningInterval() + getString(R.string.minute));
            });
        });
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        //提醒间隔
        mActivityWarningBinding.tvIntervalValue.setText(mWarningEntity.getWarningInterval() + getString(R.string.minute));
        timeView();
        distanceView();
        speedView();
        cadenceView();
        hrView();
        powerView();
    }

    /**
     * 时间预警
     */
    private void timeView() {
        mActivityWarningBinding.wvTime.setTitle(getString(R.string.time_goal), new WarningView.WarningViewListener() {
            @Override
            public void warningValueCallback(int value) {
                mWarningEntity.setTimeValue(value);
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
                sendDataToDevice(1);
            }

            @Override
            public void warningToggleCallback(boolean toggle) {
                if (toggle) {
                    mWarningEntity.setTimeSwitch(WARNING_OPEN);
                } else {
                    mWarningEntity.setTimeSwitch(WARNING_CLOSE);
                }
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
                sendDataToDevice(1);
            }
        });
        mActivityWarningBinding.wvTime.setWarningData(mWarningEntity.getTimeValue(), TIME_MAX, TIME_MIN, mWarningEntity.getTimeSwitch());
    }

    /**
     * 距离预警
     */
    private void distanceView() {
        String title;
        if (Config.unit == Unit.METRIC.value) {
            title = getString(R.string.distance_warning) + "(" + getString(R.string.unit_km) + ")";
        } else {
            title = getString(R.string.distance_warning) + "(" + getString(R.string.unit_mile) + ")";
        }
        mActivityWarningBinding.wvDistance.setTitle(title, new WarningView.WarningViewListener() {
            @Override
            public void warningValueCallback(int value) {
                mWarningEntity.setDistanceValue(UnitConversionUtil.getUnitConversionUtil().distance2m(value));
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
                sendDataToDevice(2);
            }

            @Override
            public void warningToggleCallback(boolean toggle) {
                if (toggle) {
                    mWarningEntity.setDistanceSwitch(WARNING_OPEN);
                } else {
                    mWarningEntity.setDistanceSwitch(WARNING_CLOSE);
                }
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
                sendDataToDevice(2);
            }
        });
        mActivityWarningBinding.wvDistance.setWarningData(
                UnitConversionUtil.getUnitConversionUtil().distance2Int(mWarningEntity.getDistanceValue()),
                UnitConversionUtil.getUnitConversionUtil().distance2Int(DISTANCE_MAX),
                UnitConversionUtil.getUnitConversionUtil().distance2Int(DISTANCE_MIN),
                mWarningEntity.getDistanceSwitch());
    }

    /**
     * 速度预警
     */
    private void speedView() {
        String title;
        if (Config.unit == Unit.METRIC.value) {
            title = getString(R.string.speed_warning) + "(" + getString(R.string.unit_kmh) + ")";
        } else {
            title = getString(R.string.speed_warning) + "(" + getString(R.string.unit_mph) + ")";
        }
        mActivityWarningBinding.twvSpeed.setTitle(title, new TwoWarningView.TwoWarningViewListener() {
            @Override
            public void warningMinValueCallback(int value) {
                mWarningEntity.setMinSpeedValue(UnitConversionUtil.getUnitConversionUtil().speed2Data(value));
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
            }

            @Override
            public void warningMinToggleCallback(boolean toggle) {
                if (toggle) {
                    mWarningEntity.setMinSpeedSwitch(WARNING_OPEN);
                } else {
                    mWarningEntity.setMinSpeedSwitch(WARNING_CLOSE);
                }
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
            }

            @Override
            public void warningMaxValueCallback(int value) {
                mWarningEntity.setMaxSpeedValue(UnitConversionUtil.getUnitConversionUtil().speed2Data(value));
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
                sendDataToDevice(3);
            }

            @Override
            public void warningMaxToggleCallback(boolean toggle) {
                if (toggle) {
                    mWarningEntity.setMaxSpeedSwitch(WARNING_OPEN);
                } else {
                    mWarningEntity.setMaxSpeedSwitch(WARNING_CLOSE);
                }
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
                sendDataToDevice(3);
            }
        });
        mActivityWarningBinding.twvSpeed.setWarningData(
                UnitConversionUtil.getUnitConversionUtil().speed2Int(mWarningEntity.getMinSpeedValue()),
                UnitConversionUtil.getUnitConversionUtil().speed2Int(mWarningEntity.getMaxSpeedValue()),
                UnitConversionUtil.getUnitConversionUtil().speed2Int(SPEED_MAX),
                UnitConversionUtil.getUnitConversionUtil().speed2Int(SPEED_MIN),
                mWarningEntity.getMinSpeedSwitch(),
                mWarningEntity.getMaxSpeedSwitch());
    }

    /**
     * 踏频预警
     */
    private void cadenceView() {
        String title = getString(R.string.cadence_warning) + "(" + getString(R.string.cadence_unit) + ")";
        mActivityWarningBinding.twvCadence.setTitle(title, new TwoWarningView.TwoWarningViewListener() {
            @Override
            public void warningMinValueCallback(int value) {
                mWarningEntity.setMinCadenceValue(value);
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
            }

            @Override
            public void warningMinToggleCallback(boolean toggle) {
                if (toggle) {
                    mWarningEntity.setMinCadenceSwitch(WARNING_OPEN);
                } else {
                    mWarningEntity.setMinCadenceSwitch(WARNING_CLOSE);
                }
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
            }

            @Override
            public void warningMaxValueCallback(int value) {
                mWarningEntity.setMaxCadenceValue(value);
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
                sendDataToDevice(4);
            }

            @Override
            public void warningMaxToggleCallback(boolean toggle) {
                if (toggle) {
                    mWarningEntity.setMaxCadenceSwitch(WARNING_OPEN);
                } else {
                    mWarningEntity.setMaxCadenceSwitch(WARNING_CLOSE);
                }
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
                sendDataToDevice(4);
            }
        });
        mActivityWarningBinding.twvCadence.setWarningData(
                mWarningEntity.getMinCadenceValue(),
                mWarningEntity.getMaxCadenceValue(),
                CADENCE_MAX,
                CADENCE_MIN,
                mWarningEntity.getMinCadenceSwitch(),
                mWarningEntity.getMaxCadenceSwitch());
    }

    /**
     * 心率预警
     */
    private void hrView() {
        String title = getString(R.string.heart_warning) + "(" + getString(R.string.heart_unit) + ")";
        mActivityWarningBinding.twvHr.setTitle(title, new TwoWarningView.TwoWarningViewListener() {
            @Override
            public void warningMinValueCallback(int value) {
                mWarningEntity.setMinHeartValue(value);
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
            }

            @Override
            public void warningMinToggleCallback(boolean toggle) {
                if (toggle) {
                    mWarningEntity.setMinHeartSwitch(WARNING_OPEN);
                } else {
                    mWarningEntity.setMinHeartSwitch(WARNING_CLOSE);
                }
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
            }

            @Override
            public void warningMaxValueCallback(int value) {
                mWarningEntity.setMaxHeartValue(value);
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
                sendDataToDevice(5);
            }

            @Override
            public void warningMaxToggleCallback(boolean toggle) {
                if (toggle) {
                    mWarningEntity.setMaxHeartSwitch(WARNING_OPEN);
                } else {
                    mWarningEntity.setMaxHeartSwitch(WARNING_CLOSE);
                }
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
                sendDataToDevice(5);
            }
        });
        mActivityWarningBinding.twvHr.setWarningData(
                mWarningEntity.getMinHeartValue(),
                mWarningEntity.getMaxHeartValue(),
                HEART_MAX,
                HEART_MIN,
                mWarningEntity.getMinHeartSwitch(),
                mWarningEntity.getMaxHeartSwitch());
    }

    /**
     * 功率预警
     */
    private void powerView() {
        String title = getString(R.string.power_warning) + "(" + getString(R.string.unit_w) + ")";
        mActivityWarningBinding.twvPower.setTitle(title, new TwoWarningView.TwoWarningViewListener() {
            @Override
            public void warningMinValueCallback(int value) {
                mWarningEntity.setMinPowerValue(value);
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
            }

            @Override
            public void warningMinToggleCallback(boolean toggle) {
                if (toggle) {
                    mWarningEntity.setMinPowerSwitch(WARNING_OPEN);
                } else {
                    mWarningEntity.setMinPowerSwitch(WARNING_CLOSE);
                }
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
            }

            @Override
            public void warningMaxValueCallback(int value) {
                mWarningEntity.setMaxPowerValue(value);
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
                sendDataToDevice(6);
            }

            @Override
            public void warningMaxToggleCallback(boolean toggle) {
                if (toggle) {
                    mWarningEntity.setMaxPowerSwitch(WARNING_OPEN);
                } else {
                    mWarningEntity.setMaxPowerSwitch(WARNING_CLOSE);
                }
                DbUtils.getInstance().updateWarningEntity(mWarningEntity);
                sendDataToDevice(6);
            }
        });
        mActivityWarningBinding.twvPower.setWarningData(
                mWarningEntity.getMinPowerValue(),
                mWarningEntity.getMaxPowerValue(),
                POWER_MAX,
                POWER_MIN,
                mWarningEntity.getMinPowerSwitch(),
                mWarningEntity.getMaxPowerSwitch());
    }

    /**
     * 发送数据到设备
     *
     * @param type 1 时间
     *             2 距离
     *             3 最大速度
     *             4 最大踏频
     *             5 最大心率
     *             6 最大功率
     */
    private void sendDataToDevice(int type) {
        CommandEntity commandEntity = null;
        switch (type) {
            case 1:
                commandEntity = BleCommandManager.getInstance().warningReminder(mWarningEntity.getTimeSwitch(), type, mWarningEntity.getTimeValue() * 60);
                break;
            case 2:
                commandEntity = BleCommandManager.getInstance().warningReminder(mWarningEntity.getDistanceSwitch(), type, mWarningEntity.getDistanceValue());
                break;
            case 3:
                commandEntity = BleCommandManager.getInstance().warningReminder(mWarningEntity.getMaxSpeedSwitch(), type, mWarningEntity.getMaxSpeedValue());
                break;
            case 4:
                commandEntity = BleCommandManager.getInstance().warningReminder(mWarningEntity.getMaxCadenceSwitch(), type, mWarningEntity.getMaxCadenceValue());
                break;
            case 5:
                commandEntity = BleCommandManager.getInstance().warningReminder(mWarningEntity.getMaxHeartSwitch(), type, mWarningEntity.getMaxHeartValue());
                break;
            case 6:
                commandEntity = BleCommandManager.getInstance().warningReminder(mWarningEntity.getMaxPowerSwitch(), type, mWarningEntity.getMaxPowerValue());
                break;
            default:
        }
        sendCommandData(commandEntity);
    }
}