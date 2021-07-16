package com.meilancycling.mema.ui.device.bean;

/**
 * @Description: 传感器对象
 * @Author: sore_lion
 * @CreateDate: 2020/12/24 3:25 PM
 */
public class SensorValueBean {
    /**
     * 传感器标识符
     */
    private int sensorKey;
    /**
     * 传感器的名称
     */
    private String sensorName;
    /**
     * 轮圈值
     */
    private int wheelValue;

    private boolean isConnect;
    /**
     * 传感器类型
     */
    private int type;

    public int getSensorKey() {
        return sensorKey;
    }

    public void setSensorKey(int sensorKey) {
        this.sensorKey = sensorKey;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public int getWheelValue() {
        return wheelValue;
    }

    public void setWheelValue(int wheelValue) {
        this.wheelValue = wheelValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }
}
