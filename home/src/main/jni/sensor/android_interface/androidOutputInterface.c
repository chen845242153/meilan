//
// Created by Administrator on 2020/8/27.
//

#include "androidOutputInterface.h"
#include "../../stddef.h"
#include "../common/sensorDataDefine.h"
#include "../../sensorConfig.h"
#include "../../jniError.h"

uint32_t sensorGetRealtimeData(JNIEnv *env, jobject instance, jobject realtimeBean)
{
    //LOGD("get realtime data");

    jclass jcs = (*env)->GetObjectClass(env, realtimeBean);
    if(jcs == NULL)
    {
        LOGD("jcs is null");
        return JNI_ERROR_FORBIDDEN;
    }
    
    //LOGD("hrm:%d",  sensor_value->realtime_data.hrm);
    //LOGD("cad:%d",  sensor_value->realtime_data.cadence);
    //LOGD("speed:%d",  sensor_value->realtime_data.speed);

    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("lap"), sensor_value->realtime_data.lap);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("mode"), sensor_value->realtime_data.mode);
    
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("hrm_zone"), sensor_value->realtime_data.hrm_zone);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("power"), sensor_value->realtime_data.power);    
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("power_zone"), sensor_value->realtime_data.power_zone);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("power_bal"), sensor_value->realtime_data.power_bal);

    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("PWR"), sensor_value->realtime_data.PWR);

    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgP3s"), sensor_value->realtime_data.avgPs[E_AVG_PB_3s]);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgP5s"), sensor_value->realtime_data.avgPs[E_AVG_PB_5s]);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgP10s"), sensor_value->realtime_data.avgPs[E_AVG_PB_10s]);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgP30s"), sensor_value->realtime_data.avgPs[E_AVG_PB_30s]);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgP60s"), sensor_value->realtime_data.avgPs[E_AVG_PB_60s]);
    
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgP5m"), sensor_value->realtime_data.avgPm[E_AVG_PB_5min]);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgP30m"), sensor_value->realtime_data.avgPm[E_AVG_PB_30min]);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgP60m"), sensor_value->realtime_data.avgPm[E_AVG_PB_60min]);

    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgB3s"), sensor_value->realtime_data.avgBs[E_AVG_PB_3s]);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgB5s"), sensor_value->realtime_data.avgBs[E_AVG_PB_5s]);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgB10s"), sensor_value->realtime_data.avgBs[E_AVG_PB_10s]);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgB30s"), sensor_value->realtime_data.avgBs[E_AVG_PB_30s]);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgB60s"), sensor_value->realtime_data.avgBs[E_AVG_PB_60s]);

    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgB5m"), sensor_value->realtime_data.avgBm[E_AVG_PB_5min]);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgB30m"), sensor_value->realtime_data.avgBm[E_AVG_PB_30min]);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("avgB60m"), sensor_value->realtime_data.avgBm[E_AVG_PB_60min]);
    
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("temperature"), sensor_value->realtime_data.temperature);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("cadence"), sensor_value->realtime_data.cadence);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("hrm"), sensor_value->realtime_data.hrm);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("speed"), sensor_value->realtime_data.speed);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("altitude"), sensor_value->realtime_data.altitude);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("pressure"), sensor_value->realtime_data.pressure);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("longitude"), sensor_value->realtime_data.longitude);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("latitude"), sensor_value->realtime_data.latitude);
    SET_JAVA_INT_VALUE(realtimeBean, GET_JAVA_INT_ID("heading"), sensor_value->realtime_data.heading);
    
    return JNI_SUCCESS;
}

static uint32_t sensorGetStatisticsData1(JNIEnv *env, jobject instance, jobject statisticsData, base_cycling_t *p_cycling)
{
    jclass jcs = (*env)->GetObjectClass(env, statisticsData);
    if(jcs == NULL)
    {
        LOGD("jcs is null");
        return JNI_ERROR_FORBIDDEN;
    }

    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("lap"), p_cycling->lap);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("activity_time"), p_cycling->activity_time);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("elapsed_time"), p_cycling->elapsed_time);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("dist_travelled"), p_cycling->dist_travelled);
    
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("avg_power"), p_cycling->avg_power);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("max_power"), p_cycling->max_power);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("avg_bal"), p_cycling->avg_bal);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("NP"), p_cycling->NP);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("EF"), p_cycling->EF);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("IF"), p_cycling->IF);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("P01"), p_cycling->P01);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("P1"), p_cycling->P1);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("P5"), p_cycling->P5);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("P30"), p_cycling->P30);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("P60"), p_cycling->P60);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("VI"), p_cycling->VI);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("TSS"), p_cycling->TSS);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("KJ"), p_cycling->KJ);
    
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("power_zone1"), p_cycling->power_zone[0]);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("power_zone2"), p_cycling->power_zone[1]);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("power_zone3"), p_cycling->power_zone[2]);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("power_zone4"), p_cycling->power_zone[3]);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("power_zone5"), p_cycling->power_zone[4]);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("power_zone6"), p_cycling->power_zone[5]);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("power_zone7"), p_cycling->power_zone[6]);
    
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("avg_temperture"), p_cycling->avg_temperture);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("max_temperture"), p_cycling->max_temperture);    
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("min_temperture"), p_cycling->min_temperture);

    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("avg_cadence"), p_cycling->avg_cadence);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("max_cadence"), p_cycling->max_cadence);
    
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("avg_hrm"), p_cycling->avg_hrm);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("max_hrm"), p_cycling->max_hrm);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("min_hrm"), p_cycling->min_hrm);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("hrm_zone1"), p_cycling->hrm_zone[0]);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("hrm_zone2"), p_cycling->hrm_zone[1]);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("hrm_zone3"), p_cycling->hrm_zone[2]);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("hrm_zone4"), p_cycling->hrm_zone[3]);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("hrm_zone5"), p_cycling->hrm_zone[4]);    
    
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("avg_speed"), p_cycling->avg_speed);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("max_speed"), p_cycling->max_speed);
    
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("avg_altitude"), p_cycling->avg_altitude);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("start_longtitude"), p_cycling->start_longtitude);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("end_latitude"), p_cycling->end_latitude);
    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("end_longtitude"), p_cycling->end_longtitude);

    SET_JAVA_INT_VALUE(statisticsData, GET_JAVA_INT_ID("calories"), p_cycling->calories);
    
    return JNI_SUCCESS;
}

uint32_t sensorGetStatisticsData(JNIEnv *env, jobject instance, jobject statisticsData, statisticsType_t type)
{
    if(type >= E_GET_STATISTICS_MAX)
    {
        return JNI_ERROR_INVALID_PARAM;
    }
    
    uint32_t err_code;
    
    switch(type)
    {
        case E_GET_STATISTICS_SESSION:
            
            err_code = sensorGetStatisticsData1(env, instance, statisticsData, &sensor_value->session_cycling);
            break;
        
        case E_GET_STATISTICS_LAP:

            err_code = sensorGetStatisticsData1(env, instance, statisticsData, &sensor_value->lap_cycling);
            break;
        
        case E_GET_STATISTICS_LAST_LAP:

            err_code = sensorGetStatisticsData1(env, instance, statisticsData, &sensor_value->last_lap_cycling);
            break;
    }

    return err_code;
    
}