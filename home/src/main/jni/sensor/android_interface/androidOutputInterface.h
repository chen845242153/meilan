//
// Created by Administrator on 2020/8/27.
//

#include "com_meilancycling_mema_ble_sensor_JniBleController.h"
#include "../../stdint.h"

#ifndef MEILAN_ANDROIDOUTPUTINTERFACE_H
#define MEILAN_ANDROIDOUTPUTINTERFACE_H

typedef enum
{
    E_GET_STATISTICS_SESSION,
    E_GET_STATISTICS_LAP,
    E_GET_STATISTICS_LAST_LAP,
    E_GET_STATISTICS_MAX,
}statisticsType_t;

uint32_t sensorGetRealtimeData(JNIEnv *env, jobject instance, jobject realtimeBean);
uint32_t sensorGetStatisticsData(JNIEnv *env, jobject instance, jobject statisticsData, statisticsType_t type);

#endif //MEILAN_ANDROIDOUTPUTINTERFACE_H
