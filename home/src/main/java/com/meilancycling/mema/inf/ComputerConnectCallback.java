package com.meilancycling.mema.inf;


/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 3/9/21 1:51 PM
 */
public interface ComputerConnectCallback {
    /**
     * 电量改变
     *
     * @param powerValue 电量值
     */
    void powerChange(int powerValue);

    /**
     * 设备已连接
     *
     * @param macAddress 连接的mac值
     */
    void computerConnected(String macAddress);

    /**
     * 断开设备
     *
     * @param macAddress 断开设备mac值
     */
    void disconnectComputer(String macAddress);

    /**
     * ota 回调
     *
     * @param status 0 进入OTA成功
     *               1 进入失败，设备电池电量不足
     *               2 进入失败，其他错误
     */
    void otaCallback(int status);

    /**
     * 设备信息获取完成
     */
    void deviceInformation();

    /**
     * 设置获取完成
     */
    void settingFinish();

    /**
     * 扫描传感器更新
     */
    void scanSensorUpdate();

    /**
     * 设备传感器更新
     */
    void deviceSensorUpdate();

    /**
     * 文件进度
     *
     * @param total   总长度
     * @param current 当前长度
     */
    void recordProgress(int total, int current);

    /**
     * 文件传输完毕
     *
     * @param fileName   文件名
     * @param motionType 运动类型
     * @param files      文件流
     */
    void fileFinish(String fileName, int motionType, byte[] files);
}
