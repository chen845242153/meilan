package com.meilancycling.mema.ui.device.bean;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/10/23 3:34 PM
 */
public class ScanSensorBean {
    /**
     * true ble
     * false ant
     */
    private boolean isBle;

    private int sensorType;
    private int sensorKey;
    private int value;
    private String name;

    public int getSensorType() {
        return sensorType;
    }

    public void setSensorType(int sensorType) {
        this.sensorType = sensorType;
    }

    public int getSensorKey() {
        return sensorKey;
    }

    public void setSensorKey(int sensorKey) {
        this.sensorKey = sensorKey;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBle() {
        return isBle;
    }

    public void setBle(boolean ble) {
        isBle = ble;
    }
}
