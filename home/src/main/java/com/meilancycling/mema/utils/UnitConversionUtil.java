package com.meilancycling.mema.utils;

import android.content.Context;
import android.util.Log;

import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;

import java.text.DecimalFormat;

/**
 * 单位换算
 *
 * @author sorelion qq 571135591
 */
public class UnitConversionUtil {

    private static UnitConversionUtil mUnitConversionUtil;

    public static UnitConversionUtil getUnitConversionUtil() {
        if (null == mUnitConversionUtil) {
            mUnitConversionUtil = new UnitConversionUtil();
        }
        return mUnitConversionUtil;
    }

    /**
     * 温度
     */
    public UnitBean temperatureSetting(Context context, int temperatureValue) {
        UnitBean unitBean = new UnitBean();
        if (Config.unit == Unit.METRIC.value) {
            unitBean.setUnit(context.getString(R.string.unit_c));
            unitBean.setValue(String.valueOf(temperatureValue));
        } else {
            unitBean.setUnit(context.getString(R.string.unit_f));
            unitBean.setValue(String.valueOf(Math.round((double) temperatureValue * 9 / 5 + 32)));
        }
        return unitBean;
    }

    /**
     * 时间不到十前面加0
     */
    private int timeFlag = 10;

    /**
     * 显示时间
     */
    public String timeFormat(int secondValue) {
        StringBuilder result = new StringBuilder();
        int hour = secondValue / 3600;
        if (hour < timeFlag) {
            result.append(0);
        }
        result.append(hour);
        result.append(":");
        int valueMinute = secondValue - (hour * 3600);
        int minute = valueMinute / 60;
        if (minute < timeFlag) {
            result.append(0);
        }
        result.append(minute);

        return result.toString();
    }

    /**
     * 秒转成时分
     */
    public String timeToHMS(int secondValue) {
        StringBuilder result = new StringBuilder();
        int hour = secondValue / 3600;
        if (hour < timeFlag) {
            result.append(0);
        }
        result.append(hour);
        result.append(":");
        int valueMinute = secondValue - (hour * 3600);
        int minute = valueMinute / 60;
        if (minute < timeFlag) {
            result.append(0);
        }
        result.append(minute);
        result.append(":");
        int second = valueMinute - (minute * 60);
        if (second < timeFlag) {
            result.append(0);
        }
        result.append(second);
        return result.toString();
    }

    /**
     * 秒转成时分
     */
    public String timeToMS(int secondValue) {
        StringBuilder result = new StringBuilder();

        int minute = secondValue / 60;
        if (minute < timeFlag) {
            result.append(0);
        }
        result.append(minute);
        result.append(":");
        int second = secondValue - (minute * 60);
        if (second < timeFlag) {
            result.append(0);
        }
        result.append(second);
        return result.toString();
    }

    public double formatDistance(int distance) {
        if (Config.unit == Unit.METRIC.value) {
            return AppUtils.multiplyDouble(distance, 0.001);
        }
        return AppUtils.multiplyDouble(distance, 0.0006213712);
    }


    /**
     * 速度 转 保存数据
     */
    public int speed2Data(int value) {
        if (Config.unit == Unit.METRIC.value) {
            return value * 1000;
        } else {
            return (int) Math.round(AppUtils.multiplyDouble(value, 1609.344));
        }
    }

    /**
     * 速度转int
     */
    public int speed2Int(int value) {
        if (Config.unit == Unit.METRIC.value) {
            return value / 1000;
        } else {
            return (int) Math.round(AppUtils.multiplyDouble(value, 0.0006213712));
        }
    }

    /**
     * m转int 距离
     */
    public int distance2Int(int value) {
        if (Config.unit == Unit.METRIC.value) {
            return value / 1000;
        } else {
            return (int) Math.round(AppUtils.multiplyDouble(value, 0.0006213712));
        }
    }

    /**
     * 距离转m
     */
    public int distance2m(int value) {
        if (Config.unit == Unit.METRIC.value) {
            return value * 1000;
        } else {
            return (int) Math.round(AppUtils.multiplyDouble(value, 1609.344));
        }
    }


    /**
     * 距离（上升下降）
     * 条件              显示单位      小数位数
     * [0,10000)           km            2
     * [10000,100000)      km            1
     * [100,∞)             km            0
     * (0,1609)            ft            0
     * [1609,16094)       mile           2
     * [16094,160935)     mile           1
     * [160935,∞)         mile           0
     *
     * @param distanceValue 距离值 单位m
     */
    public UnitBean distanceSetting(Context context, int distanceValue) {
        UnitBean unitBean = new UnitBean();
        if (Config.unit == Unit.METRIC.value) {
            int boundary = 10000 * 1000;
            if (distanceValue < boundary) {
                DecimalFormat df = new DecimalFormat("0.00");
                unitBean.setUnit(context.getString(R.string.unit_km));
                unitBean.setValue(df.format(AppUtils.multiplyDouble(distanceValue, 0.001)));
            } else {
                unitBean.setUnit(context.getString(R.string.unit_km));
                unitBean.setValue(String.valueOf(distanceValue / 1000));
            }
        } else {
            int boundary = 16093440;
            if (distanceValue < boundary) {
                DecimalFormat df = new DecimalFormat("0.00");
                unitBean.setUnit(context.getString(R.string.unit_mile));
                unitBean.setValue(df.format(AppUtils.multiplyDouble(distanceValue, 0.0006213712)));
            } else {
                unitBean.setUnit(context.getString(R.string.unit_mile));
                unitBean.setValue(String.valueOf(Math.round(AppUtils.multiplyDouble(distanceValue, 0.0006213712))));
            }
        }
        return unitBean;
    }

    public UnitBean rankDistanceSetting(Context context, int distanceValue) {
        UnitBean unitBean = new UnitBean();
        if (Config.unit == Unit.METRIC.value) {
            unitBean.setUnit(context.getString(R.string.unit_km));
            unitBean.setValue(String.valueOf(Math.round((double) distanceValue / 1000)));
        } else {
            unitBean.setUnit(context.getString(R.string.unit_mile));
            unitBean.setValue(String.valueOf(Math.round(AppUtils.multiplyDouble(distanceValue, 0.0006213712))));
        }
        return unitBean;
    }

    /**
     * 速度设置
     */
    public UnitBean speedSetting(Context context, double speedValue) {
        UnitBean unitBean = new UnitBean();
        DecimalFormat df = new DecimalFormat("0.0");
        if (Config.unit == Unit.METRIC.value) {
            unitBean.setUnit(context.getString(R.string.unit_kmh));
            unitBean.setValue(df.format(speedValue));
        } else {
            unitBean.setUnit(context.getString(R.string.unit_mph));
            unitBean.setValue(df.format(AppUtils.multiplyDouble(speedValue, 0.6213712)).replace(",", "."));
        }
        return unitBean;
    }

    /**
     * 海拔设置
     */
    public int altitude2Int(int altitudeValue) {
        if (Config.unit == Unit.METRIC.value) {
            return altitudeValue * 100;
        } else {
            return (int) Math.round((altitudeValue * 30.48));
        }
    }

    /**
     * 海拔
     */
    public UnitBean altitudeSetting(Context context, double altitudeValue) {
        UnitBean unitBean = new UnitBean();
        if (Config.unit == Unit.METRIC.value) {
            unitBean.setUnit(context.getString(R.string.unit_m));
            unitBean.setValue(String.valueOf((int) altitudeValue));
        } else {
            unitBean.setUnit(context.getString(R.string.unit_feet));
            unitBean.setValue(String.valueOf(Math.round((altitudeValue * 3.2808399))));
        }
        return unitBean;
    }

    /**
     * 身高  放大一百倍的数据转换
     */
    public UnitBean heightSetting(Context context, int heightValue) {
        UnitBean unitBean = new UnitBean();
        DecimalFormat df = new DecimalFormat("0.0");
        if (Config.unit == Unit.METRIC.value) {
            unitBean.setUnit(context.getString(R.string.unit_cm));
            unitBean.setValue(String.valueOf(heightValue / 100));
        } else {
            unitBean.setUnit(context.getString(R.string.unit_feet));
            unitBean.setValue(df.format(AppUtils.multiplyDouble(heightValue, 0.000328084)).replace(",", "."));
        }
        return unitBean;
    }

    /**
     * 体重 放大一百倍的数据转换
     */
    public UnitBean weightSetting(Context context, int weightValue) {
        UnitBean unitBean = new UnitBean();
        if (Config.unit == Unit.METRIC.value) {
            unitBean.setUnit(context.getString(R.string.unit_kg));
            unitBean.setValue(String.valueOf(weightValue / 100));
        } else {
            unitBean.setUnit(context.getString(R.string.unit_lb));
            double multiple = 0.022046226;
            int maxValue = 550;
            String value;
            if (Math.round(AppUtils.multiplyDouble(weightValue, multiple)) > maxValue) {
                value = String.valueOf(maxValue);
            } else {
                value = String.valueOf(Math.round(AppUtils.multiplyDouble(weightValue, multiple)));
            }
            unitBean.setValue(value);
        }
        return unitBean;
    }

    /**
     * 轮径
     */
    public UnitBean wheelSetting(Context context, double wheelValue) {
        UnitBean unitBean = new UnitBean();
        if (Config.unit == Unit.METRIC.value) {
            unitBean.setUnit(context.getString(R.string.unit_mm));
            unitBean.setValue(String.valueOf((int) wheelValue));
        } else {
            DecimalFormat df = new DecimalFormat("0.00");
            unitBean.setUnit(context.getString(R.string.unit_inch));
            unitBean.setValue(df.format(AppUtils.multiplyDouble(wheelValue, 0.0393701)));
        }
        return unitBean;
    }

    /**
     * 保存轮径
     */
    public int saveWheel(float value) {
        if (Config.unit == Unit.METRIC.value) {
            return (int) Math.round(value * 100);
        } else {
            return (int) Math.round(AppUtils.multiplyDouble(value, 2540));
        }
    }

}