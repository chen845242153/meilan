package com.meilancycling.mema.inf;

import com.meilancycling.mema.ble.bean.DeviceInformation;
import com.meilancycling.mema.ble.bean.DeviceSettingBean;
import com.meilancycling.mema.ui.device.bean.ScanSensorBean;
import com.meilancycling.mema.ui.device.bean.SensorValueBean;

import java.util.List;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/10/23 3:04 PM
 */
public interface DeviceReceivedCallback {
    /**
     * 设备支持信息
     *
     * @param deviceInformation 设备支持信息
     */
    void deviceSupportInformationCallback(DeviceInformation deviceInformation);

    /**
     * 设备信息
     *
     * @param deviceSettingBean 设备信息
     */
    void deviceInformationCallback(DeviceSettingBean deviceSettingBean);

    /**
     * 删除扫描的传感器
     *
     * @param mList 传感器集合
     */
    void deleteScanSensor(List<Integer> mList);

    /**
     * 添加扫描的传感器
     *
     * @param scanSensorBeanList 传感器扫描集合
     */
    void addScanSensor(List<ScanSensorBean> scanSensorBeanList);

    /**
     * 删除传感器
     *
     * @param mList 传感器集合
     */
    void deleteSensor(List<Integer> mList);

    /**
     * 添加传感器
     *
     * @param sensorBeanList 传感器集合
     */
    void addSensor(List<SensorValueBean> sensorBeanList);

    /**
     * ota 回调
     *
     * @param status 0 进入OTA成功
     *               2 进入失败，其他错误
     *               1 进入失败，设备电池电量不足
     */
    void otaCallback(int status);
}
