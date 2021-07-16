package com.meilancycling.mema.inf;


/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/10/20 9:54 AM
 */
public interface DisconnectCallback {
    /**
     * 设备断开
     *
     * @param macAddress 断开的地址
     */
    void disconnectDevice(String macAddress);


}
