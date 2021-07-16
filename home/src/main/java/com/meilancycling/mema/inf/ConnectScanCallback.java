package com.meilancycling.mema.inf;

/**
 * @Description: 连接扫描
 * @Author: sore_lion
 * @CreateDate: 2020/9/28 11:34 AM
 */
public interface ConnectScanCallback {
    /**
     * 连接扫描回调
     *
     * @param macAddress 设备mac值
     */
    void connectScanCallback(String macAddress);

    /**
     * 搜索完成
     */
    void connectScanFinish();
}
