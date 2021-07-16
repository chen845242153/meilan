package com.meilancycling.mema.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 功率区间
 *
 * @author lion 571135591
 */
@Entity
public class WarningEntity {
    @Id(autoincrement = true)
    private Long id;
    private long userId;
    /**
     * 预警间隔
     * 单位 分钟
     */
    private int warningInterval;
    /**
     * 时间
     * 单位 分钟
     */
    private int timeValue;
    private int timeSwitch;
    /**
     * 距离
     * 单位 米
     */
    private int distanceValue;
    private int distanceSwitch;
    /**
     * 速度
     * 单位 m/s *100
     */
    private int minSpeedValue;
    private int minSpeedSwitch;
    private int maxSpeedValue;
    private int maxSpeedSwitch;
    /**
     * 踏频
     */
    private int minCadenceValue;
    private int minCadenceSwitch;
    private int maxCadenceValue;
    private int maxCadenceSwitch;
    /**
     * 心率
     */
    private int minHeartValue;
    private int minHeartSwitch;
    private int maxHeartValue;
    private int maxHeartSwitch;
    /**
     * 功率
     */
    private int minPowerValue;
    private int minPowerSwitch;
    private int maxPowerValue;
    private int maxPowerSwitch;
    /**
     * 声音开关
     * 0 关
     * 1 开
     */
    private int voiceSwitch;

    /**
     * 屏幕常亮
     * 0 关
     * 1 开
     */
    private int brightScreen;

    @Generated(hash = 668966064)
    public WarningEntity(Long id, long userId, int warningInterval, int timeValue,
                         int timeSwitch, int distanceValue, int distanceSwitch,
                         int minSpeedValue, int minSpeedSwitch, int maxSpeedValue,
                         int maxSpeedSwitch, int minCadenceValue, int minCadenceSwitch,
                         int maxCadenceValue, int maxCadenceSwitch, int minHeartValue,
                         int minHeartSwitch, int maxHeartValue, int maxHeartSwitch,
                         int minPowerValue, int minPowerSwitch, int maxPowerValue,
                         int maxPowerSwitch, int voiceSwitch, int brightScreen) {
        this.id = id;
        this.userId = userId;
        this.warningInterval = warningInterval;
        this.timeValue = timeValue;
        this.timeSwitch = timeSwitch;
        this.distanceValue = distanceValue;
        this.distanceSwitch = distanceSwitch;
        this.minSpeedValue = minSpeedValue;
        this.minSpeedSwitch = minSpeedSwitch;
        this.maxSpeedValue = maxSpeedValue;
        this.maxSpeedSwitch = maxSpeedSwitch;
        this.minCadenceValue = minCadenceValue;
        this.minCadenceSwitch = minCadenceSwitch;
        this.maxCadenceValue = maxCadenceValue;
        this.maxCadenceSwitch = maxCadenceSwitch;
        this.minHeartValue = minHeartValue;
        this.minHeartSwitch = minHeartSwitch;
        this.maxHeartValue = maxHeartValue;
        this.maxHeartSwitch = maxHeartSwitch;
        this.minPowerValue = minPowerValue;
        this.minPowerSwitch = minPowerSwitch;
        this.maxPowerValue = maxPowerValue;
        this.maxPowerSwitch = maxPowerSwitch;
        this.voiceSwitch = voiceSwitch;
        this.brightScreen = brightScreen;
    }

    @Generated(hash = 772010572)
    public WarningEntity() {
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

    public int getWarningInterval() {
        return this.warningInterval;
    }

    public void setWarningInterval(int warningInterval) {
        this.warningInterval = warningInterval;
    }

    public int getTimeValue() {
        return this.timeValue;
    }

    public void setTimeValue(int timeValue) {
        this.timeValue = timeValue;
    }

    public int getTimeSwitch() {
        return this.timeSwitch;
    }

    public void setTimeSwitch(int timeSwitch) {
        this.timeSwitch = timeSwitch;
    }

    public int getDistanceValue() {
        return this.distanceValue;
    }

    public void setDistanceValue(int distanceValue) {
        this.distanceValue = distanceValue;
    }

    public int getDistanceSwitch() {
        return this.distanceSwitch;
    }

    public void setDistanceSwitch(int distanceSwitch) {
        this.distanceSwitch = distanceSwitch;
    }

    public int getMinSpeedValue() {
        return this.minSpeedValue;
    }

    public void setMinSpeedValue(int minSpeedValue) {
        this.minSpeedValue = minSpeedValue;
    }

    public int getMinSpeedSwitch() {
        return this.minSpeedSwitch;
    }

    public void setMinSpeedSwitch(int minSpeedSwitch) {
        this.minSpeedSwitch = minSpeedSwitch;
    }

    public int getMaxSpeedValue() {
        return this.maxSpeedValue;
    }

    public void setMaxSpeedValue(int maxSpeedValue) {
        this.maxSpeedValue = maxSpeedValue;
    }

    public int getMaxSpeedSwitch() {
        return this.maxSpeedSwitch;
    }

    public void setMaxSpeedSwitch(int maxSpeedSwitch) {
        this.maxSpeedSwitch = maxSpeedSwitch;
    }

    public int getMinCadenceValue() {
        return this.minCadenceValue;
    }

    public void setMinCadenceValue(int minCadenceValue) {
        this.minCadenceValue = minCadenceValue;
    }

    public int getMinCadenceSwitch() {
        return this.minCadenceSwitch;
    }

    public void setMinCadenceSwitch(int minCadenceSwitch) {
        this.minCadenceSwitch = minCadenceSwitch;
    }

    public int getMaxCadenceValue() {
        return this.maxCadenceValue;
    }

    public void setMaxCadenceValue(int maxCadenceValue) {
        this.maxCadenceValue = maxCadenceValue;
    }

    public int getMaxCadenceSwitch() {
        return this.maxCadenceSwitch;
    }

    public void setMaxCadenceSwitch(int maxCadenceSwitch) {
        this.maxCadenceSwitch = maxCadenceSwitch;
    }

    public int getMinHeartValue() {
        return this.minHeartValue;
    }

    public void setMinHeartValue(int minHeartValue) {
        this.minHeartValue = minHeartValue;
    }

    public int getMinHeartSwitch() {
        return this.minHeartSwitch;
    }

    public void setMinHeartSwitch(int minHeartSwitch) {
        this.minHeartSwitch = minHeartSwitch;
    }

    public int getMaxHeartValue() {
        return this.maxHeartValue;
    }

    public void setMaxHeartValue(int maxHeartValue) {
        this.maxHeartValue = maxHeartValue;
    }

    public int getMaxHeartSwitch() {
        return this.maxHeartSwitch;
    }

    public void setMaxHeartSwitch(int maxHeartSwitch) {
        this.maxHeartSwitch = maxHeartSwitch;
    }

    public int getMinPowerValue() {
        return this.minPowerValue;
    }

    public void setMinPowerValue(int minPowerValue) {
        this.minPowerValue = minPowerValue;
    }

    public int getMinPowerSwitch() {
        return this.minPowerSwitch;
    }

    public void setMinPowerSwitch(int minPowerSwitch) {
        this.minPowerSwitch = minPowerSwitch;
    }

    public int getMaxPowerValue() {
        return this.maxPowerValue;
    }

    public void setMaxPowerValue(int maxPowerValue) {
        this.maxPowerValue = maxPowerValue;
    }

    public int getMaxPowerSwitch() {
        return this.maxPowerSwitch;
    }

    public void setMaxPowerSwitch(int maxPowerSwitch) {
        this.maxPowerSwitch = maxPowerSwitch;
    }

    public int getVoiceSwitch() {
        return this.voiceSwitch;
    }

    public void setVoiceSwitch(int voiceSwitch) {
        this.voiceSwitch = voiceSwitch;
    }

    public int getBrightScreen() {
        return this.brightScreen;
    }

    public void setBrightScreen(int brightScreen) {
        this.brightScreen = brightScreen;
    }

}
