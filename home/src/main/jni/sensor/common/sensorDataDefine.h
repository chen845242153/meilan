//
// Created by Administrator on 2020/8/25.
//

#ifndef MEILAN_SENSORDATADEFINE_H
#define MEILAN_SENSORDATADEFINE_H


#include "../../stdint.h"
#include "config/sensorParametersConfig.h"

#define POWER_BALANCE_LR                0x80    //left = 100-(bal&0x7F), right = (bal&0x7F)
#define CYCLING_MODE_OUTDOOR            1
#define CYCLING_MODE_INDOOR             0

enum
{
    E_AVG_PB_3s,
    E_AVG_PB_5s,
    E_AVG_PB_10s,
    E_AVG_PB_30s,
    E_AVG_PB_60s,
    E_AVG_PB_MAXs,
};

enum
{
    E_AVG_PB_5min,
    E_AVG_PB_30min,
    E_AVG_PB_60min,
    E_AVG_PB_MAXm,
};

//实时数据
typedef struct
{
    //每次开始结束时需要清零
    uint16_t  lap;    //1-65535圈     0-65535
    uint8_t   mode;  // cycling mode 
    uint8_t   hrm_zone; //心率区间
    uint16_t  power; //1w
    uint8_t   power_zone;

    /*
        //bit7-RDPC, bit6-bit0 PPP, 0xFF-pedal power not used
        1.if bit7 is 1, indicate left and right; left = 100-right, right = (power_bal & 0x7F)
        2.if bit7 is 0, indicate sigle bal = power_bal & 0x7F
        3.IF is 0xFF power not used
    */
    uint8_t  power_bal;

    /*
        ped_smooth instruction:
        bit15:1 indicate left and right, 0:indicate only left;        
        1.if LR:bit14-bit8:left, bit7-bit0 right
        2.if L: bit7-bit0 left
    */
//    uint16_t ped_smooth;
    uint16_t PFTP;//Power / FTP percent 1% 
//    uint16_t torque_effect;//bit15-bit8 : left torque percent, bit7-bit0 right torque percent[left+right != 100]
    uint16_t FTP;//1wFunctional Threshold Power 阈值功率
    uint16_t PWR;//0.01w/kg Power to Weight Ratio
    uint16_t avgPs[E_AVG_PB_MAXs];
    uint16_t avgPm[E_AVG_PB_MAXm];
    uint8_t  avgBs[E_AVG_PB_MAXs];//bit7-true[indicate left/right] bit7-false[indicate only one]
    uint8_t  avgBm[E_AVG_PB_MAXm];//bit7-true[indicate left/right] bit7-false[indicate only one]
    int16_t  temperature;//0.1度   -3276.8--3276.7
    uint16_t cadence;//1rpm
    uint16_t hrm;//1bpm
    uint16_t speed;///0.1KM/H 0-6553.5km/h
    int32_t  altitude;    //0.1m  --0.0m小数部分为0  
    uint32_t pressure;  //0.001mbar  
    int32_t  longitude; //经度0.0000001度GPS_MAP_VALUE_TIMES
    int32_t  latitude;  //纬度 0.0000001度GPS_MAP_VALUE_TIMES
    uint16_t heading;   //航向1度
}realtime_data_t;

//骑行的基本数据
typedef struct
{
    //总圈数
    uint16_t lap;

    //本次、单圈骑行的总时间(不包括暂停时间)
    uint32_t activity_time;  //s 0-4294967295s ==> 0-3268year 

    //本次、单圈骑行的总时间(包括暂停时间)
    uint32_t elapsed_time;
    //本次、单圈骑行的总里程
    uint32_t dist_travelled;//m   0-4294967295m ==> 0-4294967.295km


    //本次、圈 功率
    uint16_t avg_power;//1w
    uint16_t max_power;//1w
    uint8_t avg_bal;
//added by cc 20190408  功率项
    uint16_t NP;    //1w
    uint16_t EF;    //0.01 Efficiency Factor NP/AVG_HRM
    uint16_t IF;    //0.001 Intensity Factor  NP/FTP
    uint16_t P01;   //1w 10s内产生的峰值功率
    uint16_t P1;    //1w 60s产生的峰值功率
    uint16_t P5;    //1w 300s产生的峰值功率
    uint16_t P30;   //1w 1800s产生的峰值功率
    uint16_t P60;   //1w 3600s产生的峰值功率
    uint16_t VI;    //0.01Variability Index NP/AVG_POWER
    uint16_t TSS;   //0.1 Training Stress Score=10*((sec x NP x IF)/(FTP x 3600) x 100)
    uint32_t KJ;    //avg.pwr*t(s)/1000(kj)

    /*
        power_zoneV[0] : flag @ref ZONE_INVALID_FLAG
        zone1:  (0, power_zoneV[1]]
        zone2:  (power_zoneV[1], power_zoneV[2]]
        zone3:  (power_zoneV[2], power_zoneV[3]]
        zone4:  (power_zoneV[3], power_zoneV[4]]
        zone5:  (power_zoneV[4], power_zoneV[5]]
        zone6:  (power_zoneV[5], power_zoneV[6]]
        zone7:  (power_zoneV[6], gigantic)
    */
    uint32_t power_zone[POWER_ZONE_CNT];//1s    


    //本次、本圈温度
    int32_t  avg_temperture;//0.01度   -327.68--327.67  
    int32_t  max_temperture;//0.01度   -327.68--327.67
    int32_t  min_temperture;//0.01度   -327.68--327.67     


    //本次、单圈骑行的踏频数据
    uint16_t avg_cadence;//1rpm  计算的是包含0的平均值
    uint16_t max_cadence;//1rpm 

    //本次、单圈骑行的心率数据
    uint16_t avg_hrm;//1bpm
    uint16_t max_hrm;//1bpm
    uint16_t min_hrm;//1bpm

    /*
        hrm_zoneV[0] : flag @ref ZONE_INVALID_FLAG
        zone1:  (0, hrm_zoneV[1]]
        zone2:  (hrm_zoneV[1], hrm_zoneV[2]]
        zone3:  (hrm_zoneV[2], hrm_zoneV[3]]
        zone4:  (hrm_zoneV[3], hrm_zoneV[4]]
        zone5:  (hrm_zoneV[4], gigantic)
    */
    uint32_t hrm_zone[HRM_ZONE_CNT];
    
    //本次、单圈骑行的速度数据
    uint16_t avg_speed;///0.1KM/H 0-6553.5km/h(包含速度为0的平均速度)

    uint16_t max_speed;///0.1KM/H 0-6553.5km/h

    //本次、单圈骑行的海拔数据
    int32_t  avg_altitude;//0.1m
    int32_t  max_altitude;//0.1m为单位
    int32_t  min_altitude;//0.1m为单位

    //本次、单圈骑行的起始结束时间 0时区时间戳
    uint32_t start_utc;
    uint32_t end_utc;

    //本次、圈起始位置    
    int32_t start_latitude;    //0.0000001度GPS_MAP_VALUE_TIMES
    int32_t start_longtitude;  //0.0000001度GPS_MAP_VALUE_TIMES
    int32_t end_latitude;      //0.0000001度GPS_MAP_VALUE_TIMES
    int32_t end_longtitude;    //0.0000001度GPS_MAP_VALUE_TIMES

    //本次、圈卡路里
    uint32_t calories;        //1cal 

}base_cycling_t;

//做主机或从机公用的数据--//31
typedef struct
{
    //实时数据realtime_data初始化需要清零，其他任何情况不需要清零
    realtime_data_t  realtime_data;

    //本次骑行数据session_cycling，在开始骑行和结束骑行需要清零，其他任何情况不需要清零
    base_cycling_t   session_cycling;

    //当前单圈骑行数据lap_cycling，开始骑行，分圈时，结束骑行都需要清零
    base_cycling_t   lap_cycling;

    //单圈需要显示的数据
    base_cycling_t   last_lap_cycling;

}sensor_value_t;


#define SENSOR_UINT32_INVALID_DATA    0xFFFFFFFF
#define SENSOR_INT32_INVALID_DATA     0x7FFFFFFF

#define SENSOR_UINT16_INVALID_DATA    0xFFFF
#define SENSOR_INT16_INVALID_DATA     0x7FFF

#define SENSOR_UINT8_INVALID_DATA     0xFF
#define SENSOR_INT8_INVALID_DATA      0x7F

//GPS 10^7
#define GPS_MAP_VALUE_TIMES           10000000

extern sensor_value_t *sensor_value;

#endif //MEILAN_SENSORDATADEFINE_H
