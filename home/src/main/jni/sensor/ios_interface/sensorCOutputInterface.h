//
// Created by Administrator on 2020/8/27.
//

#ifndef MEILAN_SENSORCOUTPUTINTERFACE_H
#define MEILAN_SENSORCOUTPUTINTERFACE_H

#include "../../stdint.h"
#include "../common/sensorDataDefine.h"

typedef struct 
{
    //获取所有数据
    uint32_t (*All)(sensor_value_t *p);
    
    //获取实时数据
    uint32_t (*realtime)(sensor_value_t *p);
    
    //获取本次骑行数据
    uint32_t (*session)(sensor_value_t *p);
    
    //获取当前圈的数据
    uint32_t (*lap)(sensor_value_t *p);
    
    //获取上一圈的数据
    uint32_t (*lastLap)(sensor_value_t *p);
}sensorCOI_t;

extern const sensorCOI_t *const pSensorCOI;

#endif //MEILAN_SENSORCOUTPUTINTERFACE_H
