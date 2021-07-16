package com.meilancycling.mema.ble.bean;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/10/23 3:07 PM
 */
public class DeviceInformation {
    /**
     * 协议版本
     */
    private int protocolVer;
    /**
     * 设备类型
     */
    private int devType;
    /**
     * 设备型号
     * 0 M1
     * 1 M2
     * 2 M4
     * 3 M5
     */
    private int devModel;

    private int sdk;
    private int sd;
    /**
     * 图库版本
     */
    private int picLibVer;
    /**
     * 字库版本
     */
    private int fontLibVer;
    /**
     * 设备软件信息
     */
    private int devSoftInfo;
    /**
     * BT版本
     */
    private int BTVer;
    /**
     * 附加条件
     */
    private int attachPara;
    /**
     * 运动类型支持字段
     */
    private int sportsSupportRide;
    private int sportsSupportRun;
    private int sportsSupportCommon;
    /**
     * 功率区间支持字段
     */
    private int powerRangeRide;
    private int powerRangeRun;
    private int powerRangeCommon;
    /**
     * 心率区间支持字段
     */
    private int heartRateRide;
    private int heartRateRun;
    private int heartRateCommon;
    private int heartRateReserve;
    /**
     * 支持字段
     */
    private int timeFormat;
    private int sound;
    private int languageSupport;
    private int language;
    /**
     * 消息提醒 0 不支持 1 支持
     */
    private int messageNotification;
    /**
     * 单位设置类型 0 仅整体设置  1 可单独设置
     */
    private int unitType;
    private int altitude;
    private int oxygen;
    private int earlyWarningTime;
    private int earlyWarningDistance;
    private int earlyWarningSpeed;
    private int earlyWarningCadence;
    private int earlyWarningHR;
    private int earlyWarningPower;

    public int getProtocolVer() {
        return protocolVer;
    }

    public void setProtocolVer(int protocolVer) {
        this.protocolVer = protocolVer;
    }

    public int getDevType() {
        return devType;
    }

    public void setDevType(int devType) {
        this.devType = devType;
    }

    public int getDevModel() {
        return devModel;
    }

    public void setDevModel(int devModel) {
        this.devModel = devModel;
    }

    public int getSdk() {
        return sdk;
    }

    public void setSdk(int sdk) {
        this.sdk = sdk;
    }

    public int getSd() {
        return sd;
    }

    public void setSd(int sd) {
        this.sd = sd;
    }

    public int getPicLibVer() {
        return picLibVer;
    }

    public void setPicLibVer(int picLibVer) {
        this.picLibVer = picLibVer;
    }

    public int getFontLibVer() {
        return fontLibVer;
    }

    public void setFontLibVer(int fontLibVer) {
        this.fontLibVer = fontLibVer;
    }

    public int getDevSoftInfo() {
        return devSoftInfo;
    }

    public void setDevSoftInfo(int devSoftInfo) {
        this.devSoftInfo = devSoftInfo;
    }

    public int getBTVer() {
        return BTVer;
    }

    public void setBTVer(int BTVer) {
        this.BTVer = BTVer;
    }

    public int getAttachPara() {
        return attachPara;
    }

    public void setAttachPara(int attachPara) {
        this.attachPara = attachPara;
    }

    public int getSportsSupportRide() {
        return sportsSupportRide;
    }

    public void setSportsSupportRide(int sportsSupportRide) {
        this.sportsSupportRide = sportsSupportRide;
    }

    public int getSportsSupportRun() {
        return sportsSupportRun;
    }

    public void setSportsSupportRun(int sportsSupportRun) {
        this.sportsSupportRun = sportsSupportRun;
    }

    public int getSportsSupportCommon() {
        return sportsSupportCommon;
    }

    public void setSportsSupportCommon(int sportsSupportCommon) {
        this.sportsSupportCommon = sportsSupportCommon;
    }

    public int getPowerRangeRide() {
        return powerRangeRide;
    }

    public void setPowerRangeRide(int powerRangeRide) {
        this.powerRangeRide = powerRangeRide;
    }

    public int getPowerRangeRun() {
        return powerRangeRun;
    }

    public void setPowerRangeRun(int powerRangeRun) {
        this.powerRangeRun = powerRangeRun;
    }

    public int getPowerRangeCommon() {
        return powerRangeCommon;
    }

    public void setPowerRangeCommon(int powerRangeCommon) {
        this.powerRangeCommon = powerRangeCommon;
    }

    public int getHeartRateRide() {
        return heartRateRide;
    }

    public void setHeartRateRide(int heartRateRide) {
        this.heartRateRide = heartRateRide;
    }

    public int getHeartRateRun() {
        return heartRateRun;
    }

    public void setHeartRateRun(int heartRateRun) {
        this.heartRateRun = heartRateRun;
    }

    public int getHeartRateCommon() {
        return heartRateCommon;
    }

    public void setHeartRateCommon(int heartRateCommon) {
        this.heartRateCommon = heartRateCommon;
    }

    public int getHeartRateReserve() {
        return heartRateReserve;
    }

    public void setHeartRateReserve(int heartRateReserve) {
        this.heartRateReserve = heartRateReserve;
    }

    public int getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(int timeFormat) {
        this.timeFormat = timeFormat;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public int getLanguageSupport() {
        return languageSupport;
    }

    public void setLanguageSupport(int languageSupport) {
        this.languageSupport = languageSupport;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getMessageNotification() {
        return messageNotification;
    }

    public void setMessageNotification(int messageNotification) {
        this.messageNotification = messageNotification;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getOxygen() {
        return oxygen;
    }

    public void setOxygen(int oxygen) {
        this.oxygen = oxygen;
    }

    public int getEarlyWarningTime() {
        return earlyWarningTime;
    }

    public void setEarlyWarningTime(int earlyWarningTime) {
        this.earlyWarningTime = earlyWarningTime;
    }

    public int getEarlyWarningDistance() {
        return earlyWarningDistance;
    }

    public void setEarlyWarningDistance(int earlyWarningDistance) {
        this.earlyWarningDistance = earlyWarningDistance;
    }

    public int getEarlyWarningSpeed() {
        return earlyWarningSpeed;
    }

    public void setEarlyWarningSpeed(int earlyWarningSpeed) {
        this.earlyWarningSpeed = earlyWarningSpeed;
    }

    public int getEarlyWarningCadence() {
        return earlyWarningCadence;
    }

    public void setEarlyWarningCadence(int earlyWarningCadence) {
        this.earlyWarningCadence = earlyWarningCadence;
    }

    public int getEarlyWarningHR() {
        return earlyWarningHR;
    }

    public void setEarlyWarningHR(int earlyWarningHR) {
        this.earlyWarningHR = earlyWarningHR;
    }

    public int getEarlyWarningPower() {
        return earlyWarningPower;
    }

    public void setEarlyWarningPower(int earlyWarningPower) {
        this.earlyWarningPower = earlyWarningPower;
    }
}
