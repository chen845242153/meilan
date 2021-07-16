package com.meilancycling.mema.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description: 设备数据
 * @Author: sore_lion
 * @CreateDate: 2020/12/9 5:33 PM
 */
@Entity
public class DeviceInformationEntity {
    @Id(autoincrement = true)
    private Long id;

    private long userId;
    private String macAddress;
    /**
     * 产品编号
     */
    private String productNo;
    /**
     * 0 删除
     * 1 不删除
     */
    private int deleteSwitch;

    /**
     * 是否有更新
     * 0 没有更新
     * 1 有可用的更新
     * 2 更新未完成
     */
    private int deviceUpdate;

    /**
     * 设备序号(排序用,倒序)
     */
    private int deviceSerialNumber;

    /**
     * 更新弹窗时间戳
     */
    private long showTime;

    private String messageCh;
    private String messageEn;
    private String otaUrl;

    @Generated(hash = 1806452485)
    public DeviceInformationEntity(Long id, long userId, String macAddress,
                                   String productNo, int deleteSwitch, int deviceUpdate,
                                   int deviceSerialNumber, long showTime, String messageCh,
                                   String messageEn, String otaUrl) {
        this.id = id;
        this.userId = userId;
        this.macAddress = macAddress;
        this.productNo = productNo;
        this.deleteSwitch = deleteSwitch;
        this.deviceUpdate = deviceUpdate;
        this.deviceSerialNumber = deviceSerialNumber;
        this.showTime = showTime;
        this.messageCh = messageCh;
        this.messageEn = messageEn;
        this.otaUrl = otaUrl;
    }

    @Generated(hash = 2067924351)
    public DeviceInformationEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getProductNo() {
        return this.productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getDeleteSwitch() {
        return this.deleteSwitch;
    }

    public void setDeleteSwitch(int deleteSwitch) {
        this.deleteSwitch = deleteSwitch;
    }

    public int getDeviceUpdate() {
        return this.deviceUpdate;
    }

    public void setDeviceUpdate(int deviceUpdate) {
        this.deviceUpdate = deviceUpdate;
    }

    public int getDeviceSerialNumber() {
        return this.deviceSerialNumber;
    }

    public void setDeviceSerialNumber(int deviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber;
    }

    public long getShowTime() {
        return this.showTime;
    }

    public void setShowTime(long showTime) {
        this.showTime = showTime;
    }

    public String getMessageCh() {
        return this.messageCh;
    }

    public void setMessageCh(String messageCh) {
        this.messageCh = messageCh;
    }

    public String getMessageEn() {
        return this.messageEn;
    }

    public void setMessageEn(String messageEn) {
        this.messageEn = messageEn;
    }

    public String getOtaUrl() {
        return this.otaUrl;
    }

    public void setOtaUrl(String otaUrl) {
        this.otaUrl = otaUrl;
    }


}
