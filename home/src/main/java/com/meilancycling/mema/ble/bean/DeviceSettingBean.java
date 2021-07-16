package com.meilancycling.mema.ble.bean;

/**
 * 设备设置
 *
 * @author sorelion qq 571135591
 */

public class DeviceSettingBean {

    /**
     * 选中的语言
     */
    private int selectLanguage = 1;
    /**
     * 时间格式 1,24h 0.12h
     */
    private int timeType;
    /**
     * 声音 0关 1开
     */
    private int sound;

    /**
     * 来电/信息开关 0关 1开
     */
    private int informationSwitch;

    /**
     * ODO预设
     */
    private int odo;
    /**
     * 海拔
     */
    private int altitude;


    public int getSelectLanguage() {
        return selectLanguage;
    }

    public void setSelectLanguage(int selectLanguage) {
        this.selectLanguage = selectLanguage;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public int getInformationSwitch() {
        return informationSwitch;
    }

    public void setInformationSwitch(int informationSwitch) {
        this.informationSwitch = informationSwitch;
    }

    public int getOdo() {
        return odo;
    }

    public void setOdo(int odo) {
        this.odo = odo;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }
}
