package com.meilancycling.mema.constant;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/12/22 5:22 PM
 */
public interface Device {
    /**
     * 蓝牙名称
     */
    String NAME_M1 = "FINDER";
    String NAME_M2 = "OVAL";
    String NAME_M4 = "BLADE";
    String NAME_M5 = "PEAK420";

    /**
     * 产品编号
     */
    String PRODUCT_NO_M1 = "10101";
    String PRODUCT_NO_M2 = "10102";
    String PRODUCT_NO_M4 = "10104";
    /**
     * 更新状态
     * 0 没有更新
     * 1 有可用的更新
     * 2 更新未完成
     */
    int DEVICE_UPDATE_NORMAL = 0;
    int DEVICE_UPDATE = 1;
    int DEVICE_UPDATE_UNDONE = 2;
    /**
     * 传感器类型
     */
    int SENSOR_HEART_RATE = 0;
    int SENSOR_SPEED_CADENCE = 1;
    int SENSOR_CADENCE = 2;
    int SENSOR_SPEED = 3;
    int SENSOR_POWER = 4;
    int SENSOR_TAILLIGHT = 5;
    /**
     * 删除数据开关
     */
    int SWITCH_CLOSE = 1;
    int SWITCH_OPEN = 0;

    /**
     * 设备状态
     * 0  正在连接设备
     * 1  设备已连接
     */
    int DEVICE_CONNECTING = 0;
    int DEVICE_CONNECTED = 1;

}
