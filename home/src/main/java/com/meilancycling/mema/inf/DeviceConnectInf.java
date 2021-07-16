package com.meilancycling.mema.inf;

import android.content.Context;

import com.meilancycling.mema.ble.bean.CommandEntity;

import java.io.File;

/**
 * @Description: 码表连接设备
 * @Author: sore_lion
 * @CreateDate: 2020/9/28 11:34 AM
 */
public interface DeviceConnectInf {
    /**
     * 初始化
     *
     * @param computerConnectCallback 回调
     */
    void init(ComputerConnectCallback computerConnectCallback);

    /**
     * 连接设备
     *
     * @param macAddress 设备mac值
     */
    void computerConnect(String macAddress);

    /**
     * 断开设备
     *
     * @param macAddress 设备mac值
     */
    void computerDisconnect(String macAddress);

    /**
     * 发送设置数据
     *
     * @param commandEntity 发送的数据
     */
    void sendComputerData(CommandEntity commandEntity);

    /**
     * 发送文件数据
     *
     * @param commandEntity 发送的数据
     */
    void sendFileData(CommandEntity commandEntity);

    /**
     * ota升级
     *
     * @param context    上下文
     * @param macAddress 设备升级地址
     */
    void otaUpgrade(Context context,  String macAddress);

}
