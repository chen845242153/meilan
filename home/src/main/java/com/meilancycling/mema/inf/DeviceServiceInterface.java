package com.meilancycling.mema.inf;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/9/28 4:29 PM
 */
public interface DeviceServiceInterface {
    /**
     * 添加设备
     *
     * @param bleName    蓝牙名称
     * @param macAddress 蓝牙地址
     */

    void addDevice(String bleName, String macAddress);

    /**
     * 删除设备
     *
     * @param macAddress 删除的设备
     */
    void deleteDevice(String macAddress);

    /**
     * 切换设备
     *
     * @param macAddress 切换的设备
     */
    void switchDevice(String macAddress);

    /**
     * 获取电量
     *
     * @return 设备电量值
     */
    int getBatteryValue();
}
