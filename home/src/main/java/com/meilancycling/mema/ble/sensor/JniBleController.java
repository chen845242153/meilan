package com.meilancycling.mema.ble.sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * @Description: jni操作类
 * @Author: sore_lion
 * @CreateDate: 2020/8/21 2:15 PM
 */
public class JniBleController {

    /**
     * 骑行状态控制
     */
    public final int E_CTRL_OP_CODE_CYCLING_START = 0;
    public final int E_CTRL_OP_CODE_CYCLING_PAUSE = 1;
    public final int E_CTRL_OP_CODE_CYCLING_LAP = 2;
    public final int E_CTRL_OP_CODE_CYCLING_END = 3;
    /**
     * 传感器类型
     */
    public final int E_SENSOR_TYPE_HRM = 0;
    public final int E_SENSOR_TYPE_CSC = 1;
    public final int E_SENSOR_TYPE_CAD = 2;
    public final int E_SENSOR_TYPE_SPD = 3;
    public final int E_SENSOR_TYPE_POWER = 4;
    public final int E_SENSOR_TYPE_GPS = 5;
    public final int E_SENSOR_TYPE_ENVIRONMENT = 6;

    /**
     * 模式
     */
    public final int E_CYCLING_MODE_INDOOR = 0;
    public final int E_CYCLING_MODE_OUTDOOR = 1;


    static {
        System.loadLibrary("lib_ble");
    }

    private static JniBleController mJniBleController;

    private JniBleController() {
    }

    public static JniBleController getBleController() {
        if (mJniBleController == null) {
            synchronized (JniBleController.class) {
                if (mJniBleController == null) {
                    mJniBleController = new JniBleController();
                }
            }
        }

        return mJniBleController;
    }

    /**
     * 获取本地时间
     */
    private long getLocalTime() {
        int offset = TimeZone.getDefault().getOffset(System.currentTimeMillis());
        return (System.currentTimeMillis() + offset) / 1000;
    }

    /**
     * 获取时间戳：0时区时间
     */
    private long getTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    private long LocalTime2Timestamp(long localTime) {
        int offset = TimeZone.getDefault().getOffset(System.currentTimeMillis());
        return localTime - offset / 1000;
    }

    public native void sensorCIITickUpdate(int ms);

    /**
     * 初始化sensor
     */
    public native long sensorInit();

    /**
     * 去初始化
     */
    public native long sensorUnInit();

    /**
     * sensor 配置参数方法
     * return error code:
     * 0   :   JNI_SUCCESS
     * 15  :   JNI_ERROR_FORBIDDEN
     * 8   :   JNI_ERROR_INVALID_STATE
     * 14  :   JNI_ERROR_NULL
     */
    public native long sensorParametersConfigPZ(PowerZone powerZone);

    public native long sensorParametersConfigHZ(HrmZone hrmZone);

    public native long sensorParametersConfigWeight(int weight001KG);

    public native long sensorParametersConfigCircumference(int circumference1mm);


    /**
     * ctrl方法
     * opCode : E_CTRL_OP_CODE_CYCLING_START
     */
    public native long sensorCtrl(int opCode);

    /**
     * 模式设置
     * mode : E_CYCLING_MODE_INDOOR
     */
    public native long sensorSetMode(int mode);

    /**
     * 传感器连接状态更新
     * sensorType : E_SENSOR_TYPE_HRM
     * connected  : true -连接， false-断开
     */
    public native long sensorStatusUpdate(int sensorType, boolean connected);

    /**
     * 传感器原始数据传递
     */
    public native long sensorDataTransfer(int sensorType, byte[] arr, int arrLen);

    /**
     * 区分传感器类型根据数据
     */
    public native int getSensorTypeBaseData(int sensorType, byte[] arr, int arrLen);

    /**
     * 传感器每秒处理事务
     */
    public native void sensorProcessEverySecond();

    /**
     * 输出实时数据
     */
    public native long sensorGetRealtimeData(RealtimeBean realtimeBean);

    /**
     * 输出本次骑行的统计数据
     */
    public native long sensorGetSessionData(StatisticalBean statisticalBean);

    /**
     * 输出本圈骑行的统计数据
     */
    public native long sensorGetLapData(StatisticalBean statisticalBean);

    /**
     * 输出上一圈骑行的统计数据
     */
    public native long sensorGetLastLapData(StatisticalBean statisticalBean);

    /**
     * @param realtimeData 当前实时数据
     * @param sessionData  本次数据（平均速度）
     * @param lapData      本圈数据
     * @param lastLapData  上一圈
     */
    public native long sensorGetAllData(RealtimeBean realtimeData,
                                        StatisticalBean sessionData,
                                        StatisticalBean lapData,
                                        StatisticalBean lastLapData);

    /**
     * fit 文件使用的回调函数
     */
    public static List<Byte> mList = new ArrayList<>();

    private void addFitElement(byte value) {
        mList.add(value);
    }

    private void modifyFitElement(byte value, int position) {
        if (mList.size() > position && position >= 0) {
            mList.set(position, value);
        }
    }
}
