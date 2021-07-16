package com.meilancycling.mema.ble.sensor;

/**
 * @Description: java类作用描述
 * @Author: sorelion qq 571135591
 * @CreateDate: 2020/8/27 15:20
 */
public class RealtimeBean {

    //每次开始结束时需要清零
    private int lap;    //1-65535圈     0-65535
    private int mode;  // cycling mode 
    private int hrm_zone; //心率区间
    private int power; //1w
    private int power_zone;
    /**
     * //bit7-RDPC, bit6-bit0 PPP, 0xFF-pedal power not used
     * 1.if bit7 is 1, indicate left and right; left = 100-right, right = (power_bal & 0x7F)
     * 2.if bit7 is 0, indicate sigle bal = power_bal & 0x7F
     * 3.IF is 0xFF power not used
     */
    private int power_bal;

    private int PFTP;//Power / FTP percent 1% 
    private int FTP;//1wFunctional Threshold Power 阈值功率
    /**
     *
     */
    private int PWR;//0.01w/kg Power to Weight Ratio
    /**
     * 平均功率/秒
     */
    private int avgP3s;
    private int avgP5s;
    private int avgP10s;
    private int avgP30s;
    private int avgP60s;

    /**
     * 平均功率/分钟
     */
    private int avgP5m;
    private int avgP30m;
    private int avgP60m;

    /**
     * 左右平衡
     */
    private int avgB3s;
    private int avgB5s;
    private int avgB10s;
    private int avgB30s;
    private int avgB60s;

    /**
     * 左右平衡/分钟
     */
    private int avgB5m;
    private int avgB30m;
    private int avgB60m;

    private int temperature;//0.1度   -3276.8--3276.7
    private int cadence;//1rpm
    private int hrm;//1bpm
    private int speed;///0.1KM/H 0-6553.5km/h
    private int altitude;    //0.1m  --0.0m小数部分为0  
    private int pressure;  //0.001mbar  
    private int longitude; //经度0.0000001度GPS_MAP_VALUE_TIMES
    private int latitude;  //纬度 0.0000001度GPS_MAP_VALUE_TIMES
    private int heading;   //航向1度

    public int getLap() {
        return lap;
    }

    public void setLap(int lap) {
        this.lap = lap;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getHrm_zone() {
        return hrm_zone;
    }

    public void setHrm_zone(int hrm_zone) {
        this.hrm_zone = hrm_zone;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower_zone() {
        return power_zone;
    }

    public void setPower_zone(int power_zone) {
        this.power_zone = power_zone;
    }

    public int getPower_bal() {
        return power_bal;
    }

    public void setPower_bal(int power_bal) {
        this.power_bal = power_bal;
    }

    public int getPFTP() {
        return PFTP;
    }

    public void setPFTP(int PFTP) {
        this.PFTP = PFTP;
    }

    public int getFTP() {
        return FTP;
    }

    public void setFTP(int FTP) {
        this.FTP = FTP;
    }

    public int getPWR() {
        return PWR;
    }

    public void setPWR(int PWR) {
        this.PWR = PWR;
    }

    public int getAvgP3s() {
        return avgP3s;
    }

    public void setAvgP3s(int avgP3s) {
        this.avgP3s = avgP3s;
    }

    public int getAvgP5s() {
        return avgP5s;
    }

    public void setAvgP5s(int avgP5s) {
        this.avgP5s = avgP5s;
    }

    public int getAvgP10s() {
        return avgP10s;
    }

    public void setAvgP10s(int avgP10s) {
        this.avgP10s = avgP10s;
    }

    public int getAvgP30s() {
        return avgP30s;
    }

    public void setAvgP30s(int avgP30s) {
        this.avgP30s = avgP30s;
    }

    public int getAvgP60s() {
        return avgP60s;
    }

    public void setAvgP60s(int avgP60s) {
        this.avgP60s = avgP60s;
    }

    public int getAvgP5m() {
        return avgP5m;
    }

    public void setAvgP5m(int avgP5m) {
        this.avgP5m = avgP5m;
    }

    public int getAvgP30m() {
        return avgP30m;
    }

    public void setAvgP30m(int avgP30m) {
        this.avgP30m = avgP30m;
    }

    public int getAvgP60m() {
        return avgP60m;
    }

    public void setAvgP60m(int avgP60m) {
        this.avgP60m = avgP60m;
    }

    public int getAvgB3s() {
        return avgB3s;
    }

    public void setAvgB3s(int avgB3s) {
        this.avgB3s = avgB3s;
    }

    public int getAvgB5s() {
        return avgB5s;
    }

    public void setAvgB5s(int avgB5s) {
        this.avgB5s = avgB5s;
    }

    public int getAvgB10s() {
        return avgB10s;
    }

    public void setAvgB10s(int avgB10s) {
        this.avgB10s = avgB10s;
    }

    public int getAvgB30s() {
        return avgB30s;
    }

    public void setAvgB30s(int avgB30s) {
        this.avgB30s = avgB30s;
    }

    public int getAvgB60s() {
        return avgB60s;
    }

    public void setAvgB60s(int avgB60s) {
        this.avgB60s = avgB60s;
    }

    public int getAvgB5m() {
        return avgB5m;
    }

    public void setAvgB5m(int avgB5m) {
        this.avgB5m = avgB5m;
    }

    public int getAvgB30m() {
        return avgB30m;
    }

    public void setAvgB30m(int avgB30m) {
        this.avgB30m = avgB30m;
    }

    public int getAvgB60m() {
        return avgB60m;
    }

    public void setAvgB60m(int avgB60m) {
        this.avgB60m = avgB60m;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getCadence() {
        return cadence;
    }

    public void setCadence(int cadence) {
        this.cadence = cadence;
    }

    public int getHrm() {
        return hrm;
    }

    public void setHrm(int hrm) {
        this.hrm = hrm;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    @Override
    public String toString() {
        return "RealtimeBean{" +
                "lap=" + lap +
                ", mode=" + mode +
                ", hrm_zone=" + hrm_zone +
                ", power=" + power +
                ", power_zone=" + power_zone +
                ", power_bal=" + power_bal +
                ", PFTP=" + PFTP +
                ", FTP=" + FTP +
                ", PWR=" + PWR +
                ", avgP3s=" + avgP3s +
                ", avgP5s=" + avgP5s +
                ", avgP10s=" + avgP10s +
                ", avgP30s=" + avgP30s +
                ", avgP60s=" + avgP60s +
                ", avgP5m=" + avgP5m +
                ", avgP30m=" + avgP30m +
                ", avgP60m=" + avgP60m +
                ", avgB3s=" + avgB3s +
                ", avgB5s=" + avgB5s +
                ", avgB10s=" + avgB10s +
                ", avgB30s=" + avgB30s +
                ", avgB60s=" + avgB60s +
                ", avgB5m=" + avgB5m +
                ", avgB30m=" + avgB30m +
                ", avgB60m=" + avgB60m +
                ", temperature=" + temperature +
                ", cadence=" + cadence +
                ", hrm=" + hrm +
                ", speed=" + speed +
                ", altitude=" + altitude +
                ", pressure=" + pressure +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", heading=" + heading +
                '}';
    }
}
