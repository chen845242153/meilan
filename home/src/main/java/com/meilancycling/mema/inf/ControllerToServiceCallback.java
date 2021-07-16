package com.meilancycling.mema.inf;

import com.meilancycling.mema.ble.bean.DeviceInformation;
import com.meilancycling.mema.ble.bean.DeviceSettingBean;
import com.meilancycling.mema.ui.device.bean.ScanSensorBean;
import com.meilancycling.mema.ui.device.bean.SensorValueBean;

import java.util.List;

/**
 * @Description: 蓝牙状态回调
 * @Author: sore_lion
 * @CreateDate: 2020/9/28 10:18 AM
 */
public interface ControllerToServiceCallback {
    /**
     * 蓝牙状态已改变
     */
    void BluetoothStateChanged();

    /**
     * 设备已连接
     *
     * @param macAddress     设备地址
     * @param deviceSoftware 软件版本
     * @param deviceHardware 硬件版本
     */
    void deviceConnected(String macAddress, String deviceSoftware, String deviceHardware);

    /**
     * 设备已断开
     *
     * @param macAddress 设备地址
     */
    void deviceDisconnect(String macAddress);

    /**
     * 电量更新
     *
     * @param value 电量
     */
    void batteryModification(int value);

    /**
     * 设备支持信息
     *
     * @param deviceInformation 设备支持信息
     */
    void deviceSupportInformation(DeviceInformation deviceInformation);

    /**
     * 设备信息
     *
     * @param deviceSettingBean 设备信息
     */
    void deviceInformation(DeviceSettingBean deviceSettingBean);

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
     * 文件传输进度
     *
     * @param total   总长度
     * @param current 当前长度
     */
    void recordProgress(int total, int current);

    /**
     * 文件内容
     *
     * @param fileName   文件名
     * @param motionType 运动类型
     * @param files      文件数组
     */
    void recordFileData(String fileName, int motionType, byte[] files);

    /**
     * ota 回调
     *
     * @param status 0 进入OTA成功
     *               2 进入失败，其他错误
     *               1 进入失败，设备电池电量不足
     */
    void otaCallback(int status);
}
