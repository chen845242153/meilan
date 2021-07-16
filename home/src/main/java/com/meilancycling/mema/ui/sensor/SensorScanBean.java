package com.meilancycling.mema.ui.sensor;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/8/25 5:50 PM
 */
public class SensorScanBean {

    private String macAddress;

    private String sensorName;

    private int sensorType;
    /**
     * 0 未选中
     * 1 已选择
     */
    private int isCheck;
    /**
     * -1 轮经值不存在
     */
    private int wheelValue;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public int getSensorType() {
        return sensorType;
    }

    public void setSensorType(int sensorType) {
        this.sensorType = sensorType;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public int getWheelValue() {
        return wheelValue;
    }

    public void setWheelValue(int wheelValue) {
        this.wheelValue = wheelValue;
    }
}
