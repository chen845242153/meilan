//
// Created by Administrator on 2020/8/24.
//


/***********************************************************************
*                               include files
************************************************************************
*/
#include <malloc.h>
#include "sensorModule.h"
#include "../../jniError.h"
#include "sensorBaseCalculateProcess.h"
#include "hrm/sensorHRM.h"
#include "sensorAPI.h"
#include "../../sensorCallback.h"
#include "../../sensorConfig.h"
#include "environment/sensorEnvironment.h"
#include "power/sensorPower.h"
#include "../ios_interface/sensorCInputInterface.h"
#include "csc/sensorCSC.h"
#include "gps/sensorGPS.h"
#include "../../fit/fit_activity_encode.h"

/***********************************************************************
*                               macro define
************************************************************************
*/

/***********************************************************************
*                               my type
************************************************************************
*/


/***********************************************************************
*                               extern variable
************************************************************************
*/

/***********************************************************************
*                               extern function
************************************************************************
*/

/***********************************************************************
*                               global variable
************************************************************************
*/
sensor_value_t *sensor_value = NULL;
/***********************************************************************
*                               global function
************************************************************************
*/

/***********************************************************************
*                               static variable
************************************************************************
*/
static cyclingStatus_t mCyclingStatus;
/***********************************************************************
*                               static function
************************************************************************
*/

/***********************************************************************
*                                   end
************************************************************************
*/
/*************************************************************************   
**	function name:	 
**	description:	                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
uint32_t sensorModuleSetCylingStatus(cyclingStatus_t status) {
    if (status >= E_CYCLING_MAX) {
        return JNI_ERROR_INVALID_PARAM;
    }
    mCyclingStatus = status;

    return JNI_SUCCESS;
}

/*************************************************************************   
**	function name:	 
**	description:	                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
cyclingStatus_t sensorModuleGetCylingStatus(void) {
    return mCyclingStatus;
}
/*************************************************************************   
**	function name:	 
**	description:	                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
bool sensorModuleCheckCylingStart(void) {
    return (mCyclingStatus == E_CYCLING_START);
}


/*************************************************************************   
**	function name:	 cydm_init_computer_value_realtime_data
**	description:	 仅仅在整个工程开始运行时调用一次
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
static void cydm_init_computer_value_realtime_data(realtime_data_t *p_realtime_data) {
    sensor_memset(p_realtime_data, 0xFF, sizeof(realtime_data_t));

    p_realtime_data->lap = 0;
    p_realtime_data->power = SENSOR_UINT16_INVALID_DATA;
    p_realtime_data->FTP = sensorParameters.pz.FTP;
    p_realtime_data->temperature = SENSOR_INT16_INVALID_DATA;
    p_realtime_data->cadence = SENSOR_UINT16_INVALID_DATA;
    p_realtime_data->hrm = SENSOR_UINT16_INVALID_DATA;
    p_realtime_data->speed = SENSOR_UINT16_INVALID_DATA;
    p_realtime_data->altitude = SENSOR_INT32_INVALID_DATA;
    p_realtime_data->longitude = SENSOR_INT32_INVALID_DATA;
    p_realtime_data->latitude = SENSOR_INT32_INVALID_DATA;
}

/*************************************************************************   
**	function name:	cydm_init_computer_value_base_cycling
**	description:	      
**	input para:			
**	return:					                                         
**************************************************************************/
static void cydm_init_computer_value_base_cycling(base_cycling_t *p_cycling, bool start) {
    uint8_t i;

    //s/l time
    p_cycling->activity_time = 0;
    p_cycling->elapsed_time = 0;

    //s/l distance
    p_cycling->dist_travelled = 0;

    //speed
    p_cycling->avg_speed = SENSOR_UINT16_INVALID_DATA;
    p_cycling->max_speed = SENSOR_UINT16_INVALID_DATA;
    
    //power
    p_cycling->avg_power = SENSOR_UINT16_INVALID_DATA;
    p_cycling->max_power = SENSOR_UINT16_INVALID_DATA;
    p_cycling->avg_bal = SENSOR_UINT8_INVALID_DATA;
    p_cycling->NP = SENSOR_UINT16_INVALID_DATA;
    p_cycling->EF = SENSOR_UINT16_INVALID_DATA;
    p_cycling->IF = SENSOR_UINT16_INVALID_DATA;
    p_cycling->P01 = SENSOR_UINT16_INVALID_DATA;
    p_cycling->P1 = SENSOR_UINT16_INVALID_DATA;
    p_cycling->P5 = SENSOR_UINT16_INVALID_DATA;
    p_cycling->P30 = SENSOR_UINT16_INVALID_DATA;
    p_cycling->P60 = SENSOR_UINT16_INVALID_DATA;
    p_cycling->VI = SENSOR_UINT16_INVALID_DATA;
    p_cycling->TSS = SENSOR_UINT16_INVALID_DATA;
    p_cycling->KJ = SENSOR_UINT32_INVALID_DATA;


    //power zone
    for (i = 0; i < POWER_ZONE_CNT; i++) {
        p_cycling->power_zone[i] = 0;
    }


    //temperature
    p_cycling->avg_temperture = SENSOR_INT32_INVALID_DATA;
    p_cycling->max_temperture = SENSOR_INT32_INVALID_DATA;
    p_cycling->min_temperture = SENSOR_INT32_INVALID_DATA;

    //cadence
    p_cycling->avg_cadence = SENSOR_UINT16_INVALID_DATA;
    p_cycling->max_cadence = SENSOR_UINT16_INVALID_DATA;


    //hrm
    p_cycling->avg_hrm = SENSOR_UINT16_INVALID_DATA;
    p_cycling->max_hrm = SENSOR_UINT16_INVALID_DATA;
    p_cycling->min_hrm = SENSOR_UINT16_INVALID_DATA;

    //hrm zone
    for (i = 0; i < HRM_ZONE_CNT; i++) {
        p_cycling->hrm_zone[i] = 0;
    }


    //altitude 
    p_cycling->avg_altitude = SENSOR_INT32_INVALID_DATA;
    p_cycling->max_altitude = SENSOR_INT32_INVALID_DATA;
    p_cycling->min_altitude = SENSOR_INT32_INVALID_DATA;

    //position[clear it to invalid]
    p_cycling->start_latitude = SENSOR_INT32_INVALID_DATA;
    p_cycling->start_longtitude = SENSOR_INT32_INVALID_DATA;
    p_cycling->end_latitude = SENSOR_INT32_INVALID_DATA;
    p_cycling->end_longtitude = SENSOR_INT32_INVALID_DATA;

    if (start) {
        p_cycling->calories = 0;
    } else {
        p_cycling->calories = SENSOR_UINT32_INVALID_DATA;
    }
}

/*************************************************************************   
**	function name:	 cydm_init_computer_value
**	description:	
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
void cydm_data_management_init(void) {
    cydm_init_computer_value_realtime_data(&sensor_value->realtime_data);
    cydm_init_computer_value_base_cycling(&sensor_value->session_cycling, false);
    cydm_init_computer_value_base_cycling(&sensor_value->lap_cycling, false);
    cydm_init_computer_value_base_cycling(&sensor_value->last_lap_cycling, false);
}

/*************************************************************************   
**	function name:	clear_avg_count
**	description:	      
**	input para:			
**	return:					                                         
**************************************************************************/
static void clear_avg_count(bool include_session) {
    if (include_session) {
        //清除踏频计算平均值参数
        clear_savg_cadence_count();
        //清除心率计算平均值参数
        clear_savg_hrm_count();
//        //清除功率计算平均值参数
        clear_savg_power_count();
//        //清除海拔计算平均值参数
        clear_savg_altitude_count();
//        //清除温度计算平均值参数
        clear_savg_temperature_count();
    }
    //清除踏频计算平均值参数
    clear_lavg_cadence_count();

    //清除心率计算平均值参数
    clear_lavg_hrm_count();

//    //清除功率计算平均值参数
    clear_lavg_power_count();
//
//    //清除海拔计算平均值参数
    clear_lavg_altitude_count();
//
//    //清除温度计算平均值参数
    clear_lavg_temperature_count();
}


/*************************************************************************   
**	function name:	update_special_data_when_new_lap
**	description:	      
**	input para:			
**	return:					                                         
**************************************************************************/
static void update_special_data_when_new_lap(base_cycling_t *p_cycling) {
    if (sensor_value->realtime_data.lap > 0) {
        *p_cycling = sensor_value->lap_cycling;
    }
}

/*************************************************************************   
**	function name:	
**	description:	      
**	input para:			
**	return:					                                         
**************************************************************************/
static void update_sensor_value_when_start(base_cycling_t *p_cycling) {
    //开始时间(单圈开始、单次开始)

    p_cycling->start_utc = sensorGetLocalTime();

    if (sensor_value->realtime_data.mode == CYCLING_MODE_OUTDOOR) {
        p_cycling->start_latitude = sensor_value->realtime_data.latitude;
        p_cycling->start_longtitude = sensor_value->realtime_data.longitude;
    }
}

/*************************************************************************   
**	function name:	
**	description:	      
**	input para:			
**	return:					                                         
**************************************************************************/
static void update_sensor_value_when_lap_end(base_cycling_t *p_cycling) {
    p_cycling->end_utc = sensorGetLocalTime();
    p_cycling->lap = sensor_value->realtime_data.lap;

    if (sensor_value->realtime_data.mode == CYCLING_MODE_OUTDOOR) {
        p_cycling->end_latitude = sensor_value->realtime_data.latitude;
        p_cycling->end_longtitude = sensor_value->realtime_data.longitude;
    }
}

/*************************************************************************   
**	function name:	update_cycling_start_end_position
**	description:	每一秒更新一次
**	input para:			
**	return:					                                         
**************************************************************************/
static void update_cycling_start_end_position(base_cycling_t *p_cycling, cyclingStatus_t status) {
    if ((status != E_CYCLING_INVALID) &&
        (sensor_value->realtime_data.mode == CYCLING_MODE_OUTDOOR)) {
        if ((p_cycling->start_latitude == SENSOR_INT32_INVALID_DATA) ||
            (p_cycling->start_longtitude == SENSOR_INT32_INVALID_DATA)) {
            p_cycling->start_latitude = sensor_value->realtime_data.latitude;
            p_cycling->start_longtitude = sensor_value->realtime_data.longitude;
        }

        p_cycling->end_latitude = sensor_value->realtime_data.latitude;
        p_cycling->end_longtitude = sensor_value->realtime_data.longitude;
    }
}

/*************************************************************************   
**	function name:	cydm_init_computer_value_when_new_lap
**	description:	      
**	input para:			
**	return:					                                         
**************************************************************************/
void cydm_init_sensor_value_when_new_lap(void) {
    if (!sensorModuleCheckCylingStart()) {
        return;
    }

    //开始骑行时，对整个(本次、单圈)数据进行清除
    if (sensor_value->realtime_data.lap == 0) {
        clear_avg_count(true);
        cydm_init_computer_value_base_cycling(&sensor_value->session_cycling, true);
        
        if(sensorParameters.pz.available)
        {
            sensorPowerUpdateFTP(sensorParameters.pz.FTP);    
        }

        fit_activity_init();
        write_event_message(E_ACTIVITY_START);
        
    } else {
        //分圈过程中，对每一圈骑行使用的中间变量清零
        clear_avg_count(false);

        update_sensor_value_when_lap_end(&sensor_value->lap_cycling);
        update_special_data_when_new_lap(&sensor_value->last_lap_cycling);

        write_event_message(E_ACTIVITY_STOP_OLD_LAP);
        write_lap_message((uint32_t)&sensor_value->lap_cycling);
        write_event_message(E_ACTIVITY_START_NEW_LAP);
    }

    cydm_init_computer_value_base_cycling(&sensor_value->lap_cycling, true);
    update_sensor_value_when_start(&sensor_value->lap_cycling);

    //本次骑行的开始时间
    if (sensor_value->realtime_data.lap == 0) {
        update_sensor_value_when_start(&sensor_value->session_cycling);
    }

    sensor_value->realtime_data.lap++;

    LOGI("new lap:%d\r\n", sensor_value->realtime_data.lap);
}

/*************************************************************************   
**	function name:	cydm_init_computer_value_when_end
**	description:	      
**	input para:			
**	return:					                                         
**************************************************************************/
void cydm_init_sensor_value_when_cycling_end(void) {
    clear_avg_count(true);

    update_sensor_value_when_lap_end(&sensor_value->lap_cycling);
    update_sensor_value_when_lap_end(&sensor_value->session_cycling);

    write_event_message(E_ACTIVITY_STOP_OLD_LAP);
    write_lap_message((uint32_t)&sensor_value->lap_cycling);
    write_event_message(E_ACTIVITY_STOP_ALL);
    write_session_message((uint32_t)&sensor_value->session_cycling);
    write_activity_message((uint32_t)&sensor_value->session_cycling);
}

/*************************************************************************   
**	function name:	
**	description:	      
**	input para:			
**	return:					                                         
**************************************************************************/
void cydm_clear_sensor_value(void) {
    cydm_data_management_init();
}

/*************************************************************************   
**	function name:	update_cycling_time
**	description:    运动开始计时累加        
**	input para:			
**	return:					                                         
**************************************************************************/
void update_cycling_time(void) {
    uint32_t curSpeed;
    curSpeed = (sensor_value->realtime_data.speed == SENSOR_UINT16_INVALID_DATA) ? 0 : sensor_value->realtime_data.speed;

    if (sensorModuleCheckCylingStart()) {
        if((curSpeed != 0) || (sensor_value->session_cycling.activity_time < 6)) {
            sensor_value->session_cycling.activity_time++;
            sensor_value->lap_cycling.activity_time++;
        }
    }

    if (sensorModuleGetCylingStatus() != E_CYCLING_INVALID) {
        //单圈、单次总的运动时间，包括暂停时间
        sensor_value->lap_cycling.elapsed_time++;
        sensor_value->session_cycling.elapsed_time++;
    }

    LOGI("cycling status:%d, actime:%d,totaltime:%d", sensorModuleGetCylingStatus(),
            sensor_value->session_cycling.activity_time,
            sensor_value->session_cycling.elapsed_time);
}
/*************************************************************************   
**	function name:	update_cycling_time
**	description:    运动开始计时累加        
**	input para:			
**	return:					                                         
**************************************************************************/
static void update_cycling_record(void)
{
    if(sensorModuleGetCylingStatus() != E_CYCLING_INVALID)
    {
        write_record_message();
    }
}
/*************************************************************************   
**	function name:	
**	description:            
**	input para:			
**	return:					                                         
**************************************************************************/
void sensorModuleProcessEverySecond(void) {

    cyclingStatus_t status;

    status = sensorModuleGetCylingStatus();

    if((status == E_CYCLING_SAVING)||
       (status == E_CYCLING_INVALID)||
       (status == E_CYCLING_MAX))
    {
        return;
    }
    update_cycling_time();
    hr_zone_time_update();
    sensorPowerProcessEverySecond();
    update_cycling_record();
}
/*************************************************************************   
**	function name:	
**	description:            
**	input para:			
**	return:					                                         
**************************************************************************/
uint32_t sensorModuleStautsUpdate(uint8_t type, bool connected) {
    if (type >= E_SENSOR_TYPE_MAX) {
        return JNI_ERROR_INVALID_PARAM;
    }

    LOGD("sensor:%d status update:%d", type, connected);

    switch (type) {
        case E_SENSOR_TYPE_HRM:

            sensorHRMStatusUpdate(connected);
            break;

        case E_SENSOR_TYPE_CSC:

            sensorCADStatusUpdate(connected);
            sensorSPDStatusUpdate(connected);
            break;

        case E_SENSOR_TYPE_CAD:

            sensorCADStatusUpdate(connected);
            break;

        case E_SENSOR_TYPE_SPD:

            sensorSPDStatusUpdate(connected);
            break;

        case E_SENSOR_TYPE_POWER:

            sensorPowerStatusUpdate(connected);
            break;

        default:
            break;
    }

    

    return JNI_SUCCESS;
}

/*************************************************************************   
**	function name:	
**	description:            
**	input para:			
**	return:					                                         
**************************************************************************/
uint32_t sensorModuleProcessSensorData(uint8_t type, uint8_t *buf, uint8_t len) {

    if (type >= E_SENSOR_TYPE_MAX) {
        return JNI_ERROR_INVALID_PARAM;
    }

    uint32_t err_code;

    switch (type) {

        case E_SENSOR_TYPE_HRM:

            err_code = sensorHRMDecodeData(buf, len);
            break;

        case E_SENSOR_TYPE_CSC:
        case E_SENSOR_TYPE_CAD:
        case E_SENSOR_TYPE_SPD:

            err_code = sensorCSCDecodeData(buf, len);
            break;

        case E_SENSOR_TYPE_POWER:

            err_code = sensorPowerDecodeData(buf, len);
            break;

        case E_SENSOR_TYPE_GPS:

            err_code = sensorGPSDecodeData(buf, len);
            break;

        case E_SENSOR_TYPE_ENVIRONMENT:

            err_code = sensorEnvironmentDecodeData(buf, len);
            break;

        default:
            err_code = JNI_ERROR_INVALID_PARAM;
            break;
    }

    return err_code;
}

/*************************************************************************   
**	function name:	
**	description:            
**	input para:			
**	return:					                                         
**************************************************************************/
uint8_t sensorModuleCheckSensorType(uint8_t type, uint8_t *buf, uint8_t len) {

    if (type >= E_SENSOR_TYPE_MAX) {
        return E_SENSOR_TYPE_MAX;
    }

    switch (type) {

        default:

            return type;

        case E_SENSOR_TYPE_CSC:
        case E_SENSOR_TYPE_CAD:
        case E_SENSOR_TYPE_SPD:

            return checkCSCType(buf, len);
    }
    return E_SENSOR_TYPE_MAX;
}


/*************************************************************************   
**	function name:	
**	description:            
**	input para:			
**	return:					                                         
**************************************************************************/
uint32_t sensorModuleCtrl(uint8_t opCode) {
    if (opCode >= E_CTRL_OP_CODE_CYCLING_MAX) {
        return JNI_ERROR_INVALID_PARAM;
    }
    switch (opCode) {

        case E_CTRL_OP_CODE_CYCLING_START:

            if (sensorModuleGetCylingStatus() == E_CYCLING_INVALID) {

                sensorModuleSetCylingStatus(E_CYCLING_START);
                cydm_init_sensor_value_when_new_lap();
            }
            else{
                write_event_message(E_ACTIVITY_START);
                sensorModuleSetCylingStatus(E_CYCLING_START);
            }


            break;

        case E_CTRL_OP_CODE_CYCLING_PAUSE:

            sensorModuleSetCylingStatus(E_CYCLING_PAUSE);
            write_event_message(E_ACTIVITY_PAUSE);
            break;

        case E_CTRL_OP_CODE_CYCLING_LAP:

            write_event_message(E_ACTIVITY_START_NEW_LAP);
            cydm_init_sensor_value_when_new_lap();

            break;

        case E_CTRL_OP_CODE_CYCLING_END:

            cydm_init_sensor_value_when_cycling_end();
            sensorModuleSetCylingStatus(E_CYCLING_SAVING);

            cydm_clear_sensor_value();
            sensorModuleSetCylingStatus(E_CYCLING_INVALID);
            break;

        case E_CTRL_OP_CODE_CYCLING_CLEAR:

            cydm_clear_sensor_value();
            sensorModuleSetCylingStatus(E_CYCLING_INVALID);
            break;
    }

    LOGD("opcode:%d", opCode);

    return JNI_SUCCESS;
}
/*************************************************************************   
**	function name:	
**	description:            
**	input para:			
**	return:					                                         
**************************************************************************/
uint32_t sensorModuleInit(void) {
    if (sensor_value == NULL) {
        sensor_value = (sensor_value_t *) malloc(sizeof(sensor_value_t));
    }

    if (sensor_value == NULL) {
        return JNI_ERROR_NO_MEM;
    }



    sensorCADStatusUpdate(false);
    sensorSPDStatusUpdate(false);
    sensorHRMStatusUpdate(false);
    sensorPowerStatusUpdate(false);
    cydm_data_management_init();

    sensorModuleSetCylingStatus(E_CYCLING_INVALID);

    sc_sensor_data_process_init(sensorParameters.circumference);
    speed_calories_process_init(sensorParameters.weight/100);

    return JNI_SUCCESS;
}

/*************************************************************************
**	function name:
**	description:
**	input para:
**	return:
**************************************************************************/
uint32_t sensorModuleUninit(void) {
    free(sensor_value);
    sensor_value = NULL;
    return JNI_SUCCESS;
}
/*************************************************************************
**	function name:
**	description:
**	input para:
**	return:
**************************************************************************/
uint32_t sensorModuleSetMode(uint8_t mode)
{
    if(mode > CYCLING_MODE_OUTDOOR)
    {
        sensor_value->realtime_data.mode = mode;
        return JNI_SUCCESS;
    }

    return JNI_ERROR_INVALID_PARAM;
}