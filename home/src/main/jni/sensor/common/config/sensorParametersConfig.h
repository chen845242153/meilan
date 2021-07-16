//
// Created by Administrator on 2020/8/24.
//

#ifndef MEILAN_SENSORPARAMETERSCONFIG_H
#define MEILAN_SENSORPARAMETERSCONFIG_H

#include "../../ios_interface/sensorParametersDescription.h"

typedef struct
{
    uint16_t weight;                //0.01kg
    uint32_t circumference;         //1mm   当前连接sensor的传感器
    hz_t     hz;
    pz_t     pz;
}sensorParameters_t;

extern sensorParameters_t sensorParameters;

uint32_t sensorPCWeight(uint16_t weight);
uint32_t sensorPCCircumference(uint32_t circumference);
uint32_t sensorPChz(hz_t *hz);
uint32_t sensorPCpz(pz_t *pz);

#endif //MEILAN_SENSORPARAMETERSCONFIG_H
