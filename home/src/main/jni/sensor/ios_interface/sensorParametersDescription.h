//
// Created by Administrator on 2020/8/26.
//

#ifndef MEILAN_SENSORPARAMETERSDESCRIPTION_H
#define MEILAN_SENSORPARAMETERSDESCRIPTION_H


#include "../../stdbool.h"
#include "../../stdint.h"

#define HRM_ZONE_CNT            5
#define POWER_ZONE_CNT          7

typedef struct
{
    bool available;
    uint8_t  hz[HRM_ZONE_CNT-1];    //1bpm  hz2_left, hz3_left, hz4_left, hz5_left
}hz_t;

typedef struct
{
    bool available;
    uint16_t FTP;
    uint16_t pz[POWER_ZONE_CNT-1];  //1w    pz2_left, pz3_left, pz4_left, pz5_left,pz6_left, pz7_left
}pz_t;



#endif //MEILAN_SENSORPARAMETERSDESCRIPTION_H
