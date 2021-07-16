package com.meilancycling.mema.network.bean.response;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/12/4 2:18 PM
 */
public class MostMotionResponse {


    private double sumDistance;
    private int sumTime;
    private int sumMotion;
    private double sumKcal;
    private double maxDistance;
    private int maxTime;
    private int maxSpeed;
    private int maxAscent;
    private int motionTimeId;
    private int motionDistanceId;
    private int motionMaxSpeedId;
    private int motionAscentId;

    public double getSumDistance() {
        return sumDistance;
    }

    public void setSumDistance(double sumDistance) {
        this.sumDistance = sumDistance;
    }

    public int getSumTime() {
        return sumTime;
    }

    public void setSumTime(int sumTime) {
        this.sumTime = sumTime;
    }

    public int getSumMotion() {
        return sumMotion;
    }

    public void setSumMotion(int sumMotion) {
        this.sumMotion = sumMotion;
    }

    public double getSumKcal() {
        return sumKcal;
    }

    public void setSumKcal(double sumKcal) {
        this.sumKcal = sumKcal;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getMaxAscent() {
        return maxAscent;
    }

    public void setMaxAscent(int maxAscent) {
        this.maxAscent = maxAscent;
    }

    public int getMotionTimeId() {
        return motionTimeId;
    }

    public void setMotionTimeId(int motionTimeId) {
        this.motionTimeId = motionTimeId;
    }

    public int getMotionDistanceId() {
        return motionDistanceId;
    }

    public void setMotionDistanceId(int motionDistanceId) {
        this.motionDistanceId = motionDistanceId;
    }

    public int getMotionMaxSpeedId() {
        return motionMaxSpeedId;
    }

    public void setMotionMaxSpeedId(int motionMaxSpeedId) {
        this.motionMaxSpeedId = motionMaxSpeedId;
    }

    public int getMotionAscentId() {
        return motionAscentId;
    }

    public void setMotionAscentId(int motionAscentId) {
        this.motionAscentId = motionAscentId;
    }
    
}
