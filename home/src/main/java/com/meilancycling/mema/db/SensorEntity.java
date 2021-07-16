package com.meilancycling.mema.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description: 传感器对象
 * @Author: sore_lion
 * @CreateDate: 2020/8/26 5:27 PM
 */
@Entity
public class SensorEntity {
    @Id(autoincrement = true)
    private Long id;

    /**
     * 用户id
     */
    private long userId;
    private String macAddress;
    private String sensorName;
    private int sensorType;
    /**
     * -1 轮经值不存在
     */
    private int wheelValue;
    private int connectStatus;
    @Generated(hash = 691425613)
    public SensorEntity(Long id, long userId, String macAddress, String sensorName,
            int sensorType, int wheelValue, int connectStatus) {
        this.id = id;
        this.userId = userId;
        this.macAddress = macAddress;
        this.sensorName = sensorName;
        this.sensorType = sensorType;
        this.wheelValue = wheelValue;
        this.connectStatus = connectStatus;
    }
    @Generated(hash = 1009197587)
    public SensorEntity() {
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
    public String getSensorName() {
        return this.sensorName;
    }
    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
    public int getSensorType() {
        return this.sensorType;
    }
    public void setSensorType(int sensorType) {
        this.sensorType = sensorType;
    }
    public int getWheelValue() {
        return this.wheelValue;
    }
    public void setWheelValue(int wheelValue) {
        this.wheelValue = wheelValue;
    }
    public int getConnectStatus() {
        return this.connectStatus;
    }
    public void setConnectStatus(int connectStatus) {
        this.connectStatus = connectStatus;
    }

}
