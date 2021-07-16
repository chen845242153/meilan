package com.meilancycling.mema.ble.sensor;

/**
 * @Description: java类作用描述
 * @Author: sorelion qq 571135591
 * @CreateDate: 2020/8/27 15:39
 */
public class StatisticalBean {
    
    //总圈数
    private int lap;

    //本次、单圈骑行的总时间(不包括暂停时间)
    private int activity_time;  //s 0-4294967295s ==> 0-3268year 

    //本次、单圈骑行的总时间(包括暂停时间)
    private int elapsed_time;
    
    //本次、单圈骑行的总里程
    private int dist_travelled;//m   0-4294967295m ==> 0-4294967.295km
    
    //本次、圈 功率
    private int avg_power;//1w
    private int max_power;//1w
    private int avg_bal;
    //added by cc 20190408  功率项
    private int NP;    //1w
    private int EF;    //0.01 Efficiency Factor NP/AVG_HRM
    private int IF;    //0.001 Intensity Factor  NP/FTP
    private int P01;   //1w 10s内产生的峰值功率
    private int P1;    //1w 60s产生的峰值功率
    private int P5;    //1w 300s产生的峰值功率
    private int P30;   //1w 1800s产生的峰值功率
    private int P60;   //1w 3600s产生的峰值功率
    private int VI;    //0.01Variability Index NP/AVG_POWER
    private int TSS;   //0.1 Training Stress Score=10*((sec x NP x IF)/(FTP x 3600) x 100)
    private int KJ;    //avg.pwr*t(s)/1000(kj)
    
    private int power_zone1;
    private int power_zone2;
    private int power_zone3;
    private int power_zone4;
    private int power_zone5;
    private int power_zone6;
    private int power_zone7;

    //本次、本圈温度
    private int avg_temperture;//0.01度   -327.68--327.67  
    private int max_temperture;//0.01度   -327.68--327.67
    private int min_temperture;//0.01度   -327.68--327.67     


    //本次、单圈骑行的踏频数据
    private int avg_cadence;//1rpm  计算的是包含0的平均值
    private int max_cadence;//1rpm 

    //本次、单圈骑行的心率数据
    private int avg_hrm;//1bpm
    private int max_hrm;//1bpm
    private int min_hrm;//1bpm

    private int hrm_zone1;
    private int hrm_zone2;
    private int hrm_zone3;
    private int hrm_zone4;
    private int hrm_zone5;

    //本次、单圈骑行的速度数据
    private int avg_speed;///0.1KM/H 0-6553.5km/h(包含速度为0的平均速度)

    private int max_speed;///0.1KM/H 0-6553.5km/h

    //本次、单圈骑行的海拔数据
    private int avg_altitude;//0.1m
    private int max_altitude;//0.1m为单位
    private int min_altitude;//0.1m为单位

    //本次、单圈骑行的起始结束时间 0时区时间戳
    private int start_utc;
    private int end_utc;

    //本次、圈起始位置    
    private int start_latitude;    //0.0000001度10^7
    private int start_longtitude;  //0.0000001度10^7
    private int end_latitude;      //0.0000001度10^7
    private int end_longtitude;    //0.0000001度10^7

    //本次、圈卡路里
    private int calories;

    public int getLap() {
        return lap;
    }

    public void setLap(int lap) {
        this.lap = lap;
    }

    public int getActivity_time() {
        return activity_time;
    }

    public void setActivity_time(int activity_time) {
        this.activity_time = activity_time;
    }

    public int getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(int elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public int getDist_travelled() {
        return dist_travelled;
    }

    public void setDist_travelled(int dist_travelled) {
        this.dist_travelled = dist_travelled;
    }

    public int getAvg_power() {
        return avg_power;
    }

    public void setAvg_power(int avg_power) {
        this.avg_power = avg_power;
    }

    public int getMax_power() {
        return max_power;
    }

    public void setMax_power(int max_power) {
        this.max_power = max_power;
    }

    public int getAvg_bal() {
        return avg_bal;
    }

    public void setAvg_bal(int avg_bal) {
        this.avg_bal = avg_bal;
    }

    public int getNP() {
        return NP;
    }

    public void setNP(int NP) {
        this.NP = NP;
    }

    public int getEF() {
        return EF;
    }

    public void setEF(int EF) {
        this.EF = EF;
    }

    public int getIF() {
        return IF;
    }

    public void setIF(int IF) {
        this.IF = IF;
    }

    public int getP01() {
        return P01;
    }

    public void setP01(int p01) {
        P01 = p01;
    }

    public int getP1() {
        return P1;
    }

    public void setP1(int p1) {
        P1 = p1;
    }

    public int getP5() {
        return P5;
    }

    public void setP5(int p5) {
        P5 = p5;
    }

    public int getP30() {
        return P30;
    }

    public void setP30(int p30) {
        P30 = p30;
    }

    public int getP60() {
        return P60;
    }

    public void setP60(int p60) {
        P60 = p60;
    }

    public int getVI() {
        return VI;
    }

    public void setVI(int VI) {
        this.VI = VI;
    }

    public int getTSS() {
        return TSS;
    }

    public void setTSS(int TSS) {
        this.TSS = TSS;
    }

    public int getKJ() {
        return KJ;
    }

    public void setKJ(int KJ) {
        this.KJ = KJ;
    }

    public int getPower_zone1() {
        return power_zone1;
    }

    public void setPower_zone1(int power_zone1) {
        this.power_zone1 = power_zone1;
    }

    public int getPower_zone2() {
        return power_zone2;
    }

    public void setPower_zone2(int power_zone2) {
        this.power_zone2 = power_zone2;
    }

    public int getPower_zone3() {
        return power_zone3;
    }

    public void setPower_zone3(int power_zone3) {
        this.power_zone3 = power_zone3;
    }

    public int getPower_zone4() {
        return power_zone4;
    }

    public void setPower_zone4(int power_zone4) {
        this.power_zone4 = power_zone4;
    }

    public int getPower_zone5() {
        return power_zone5;
    }

    public void setPower_zone5(int power_zone5) {
        this.power_zone5 = power_zone5;
    }

    public int getPower_zone6() {
        return power_zone6;
    }

    public void setPower_zone6(int power_zone6) {
        this.power_zone6 = power_zone6;
    }

    public int getPower_zone7() {
        return power_zone7;
    }

    public void setPower_zone7(int power_zone7) {
        this.power_zone7 = power_zone7;
    }

    public int getAvg_temperture() {
        return avg_temperture;
    }

    public void setAvg_temperture(int avg_temperture) {
        this.avg_temperture = avg_temperture;
    }

    public int getMax_temperture() {
        return max_temperture;
    }

    public void setMax_temperture(int max_temperture) {
        this.max_temperture = max_temperture;
    }

    public int getMin_temperture() {
        return min_temperture;
    }

    public void setMin_temperture(int min_temperture) {
        this.min_temperture = min_temperture;
    }

    public int getAvg_cadence() {
        return avg_cadence;
    }

    public void setAvg_cadence(int avg_cadence) {
        this.avg_cadence = avg_cadence;
    }

    public int getMax_cadence() {
        return max_cadence;
    }

    public void setMax_cadence(int max_cadence) {
        this.max_cadence = max_cadence;
    }

    public int getAvg_hrm() {
        return avg_hrm;
    }

    public void setAvg_hrm(int avg_hrm) {
        this.avg_hrm = avg_hrm;
    }

    public int getMax_hrm() {
        return max_hrm;
    }

    public void setMax_hrm(int max_hrm) {
        this.max_hrm = max_hrm;
    }

    public int getMin_hrm() {
        return min_hrm;
    }

    public void setMin_hrm(int min_hrm) {
        this.min_hrm = min_hrm;
    }

    public int getHrm_zone1() {
        return hrm_zone1;
    }

    public void setHrm_zone1(int hrm_zone1) {
        this.hrm_zone1 = hrm_zone1;
    }

    public int getHrm_zone2() {
        return hrm_zone2;
    }

    public void setHrm_zone2(int hrm_zone2) {
        this.hrm_zone2 = hrm_zone2;
    }

    public int getHrm_zone3() {
        return hrm_zone3;
    }

    public void setHrm_zone3(int hrm_zone3) {
        this.hrm_zone3 = hrm_zone3;
    }

    public int getHrm_zone4() {
        return hrm_zone4;
    }

    public void setHrm_zone4(int hrm_zone4) {
        this.hrm_zone4 = hrm_zone4;
    }

    public int getHrm_zone5() {
        return hrm_zone5;
    }

    public void setHrm_zone5(int hrm_zone5) {
        this.hrm_zone5 = hrm_zone5;
    }

    public int getAvg_speed() {
        return avg_speed;
    }

    public void setAvg_speed(int avg_speed) {
        this.avg_speed = avg_speed;
    }

    public int getMax_speed() {
        return max_speed;
    }

    public void setMax_speed(int max_speed) {
        this.max_speed = max_speed;
    }

    public int getAvg_altitude() {
        return avg_altitude;
    }

    public void setAvg_altitude(int avg_altitude) {
        this.avg_altitude = avg_altitude;
    }

    public int getMax_altitude() {
        return max_altitude;
    }

    public void setMax_altitude(int max_altitude) {
        this.max_altitude = max_altitude;
    }

    public int getMin_altitude() {
        return min_altitude;
    }

    public void setMin_altitude(int min_altitude) {
        this.min_altitude = min_altitude;
    }

    public int getStart_utc() {
        return start_utc;
    }

    public void setStart_utc(int start_utc) {
        this.start_utc = start_utc;
    }

    public int getEnd_utc() {
        return end_utc;
    }

    public void setEnd_utc(int end_utc) {
        this.end_utc = end_utc;
    }

    public int getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(int start_latitude) {
        this.start_latitude = start_latitude;
    }

    public int getStart_longtitude() {
        return start_longtitude;
    }

    public void setStart_longtitude(int start_longtitude) {
        this.start_longtitude = start_longtitude;
    }

    public int getEnd_latitude() {
        return end_latitude;
    }

    public void setEnd_latitude(int end_latitude) {
        this.end_latitude = end_latitude;
    }

    public int getEnd_longtitude() {
        return end_longtitude;
    }

    public void setEnd_longtitude(int end_longtitude) {
        this.end_longtitude = end_longtitude;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
