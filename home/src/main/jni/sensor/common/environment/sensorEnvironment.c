//
// Created by Administrator on 2020/8/27.
//



/***********************************************************************
*                               include files
************************************************************************
*/
#include "sensorEnvironment.h"
#include "../../../stddef.h"
#include "../../../jniError.h"
#include "../sensorDataDefine.h"
#include "../sensorAPI.h"
#include "../cyclingStatus.h"
#include "../../../sensorConfig.h"

/***********************************************************************
*                               macro define
************************************************************************
*/
//温度(2byte) + 海拔（4byte） + 气压（4byte）

#define ENVIRONMENT_DATA_LENGTH                 10

/***********************************************************************
*                               my type
************************************************************************
*/
typedef struct
{
    int32_t  altitude_ssum;
    uint32_t altitude_scount;

    int32_t  altitude_lsum;
    uint32_t altitude_lcount;

    int32_t  temperature_ssum;
    uint32_t temperature_scount;

    int32_t  temperature_lsum;
    uint32_t temperature_lcount;
}environment_avg_para_t;
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

/***********************************************************************
*                               global function
************************************************************************
*/

/***********************************************************************
*                               static variable
************************************************************************
*/
static environment_avg_para_t m_environment_avg_para;

/***********************************************************************
*                               static function
************************************************************************
*/

/***********************************************************************
*                                   end
************************************************************************
*/
/*************************************************************************   
**	function name:	 environment_sensor_altitude_update()
**	description:	 每1s进入一次
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
static void environment_sensor_temerature_update(int16_t temp)
{
    sensor_value->realtime_data.temperature = temp;
    
    if((sensorModuleCheckCylingStart())&&(temp!=SENSOR_INT16_INVALID_DATA))
    {
        calculate_signed_max_min(temp, 
                                 &sensor_value->session_cycling.max_temperture,
                                 &sensor_value->session_cycling.min_temperture,
                                 SENSOR_INT32_INVALID_DATA);

        calculate_signed_max_min(temp, 
                                 &sensor_value->lap_cycling.max_temperture,
                                 &sensor_value->lap_cycling.min_temperture,
                                 SENSOR_INT32_INVALID_DATA);

        sensor_value->session_cycling.avg_temperture = calculate_signed_avg_value_base_sum_divid_count(temp, 
                                                                                                     &m_environment_avg_para.temperature_ssum, 
                                                                                                     &m_environment_avg_para.temperature_scount);

        sensor_value->lap_cycling.avg_temperture = calculate_signed_avg_value_base_sum_divid_count(temp, 
                                                                                                 &m_environment_avg_para.temperature_lsum, 
                                                                                                 &m_environment_avg_para.temperature_lcount);
    }
}

/*************************************************************************   
**	function name:	 environment_sensor_altitude_update()
**	description:	 每1s进入一次
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
static void environment_sensor_altitude_update(int32_t altitude)
{
    int32_t tmp_altitude;

    if(altitude == SENSOR_INT32_INVALID_DATA)
    {
        return;
    }

    
    if(altitude<0)
    {
        tmp_altitude = (altitude-5) / 10 * 10;
    }    
    else
    {
        tmp_altitude = (altitude+5) / 10 * 10;
    }
    sensor_value->realtime_data.altitude = tmp_altitude;//10;

    if(sensorModuleCheckCylingStart())
    {
        calculate_signed_max_min(tmp_altitude, 
                                 &sensor_value->session_cycling.max_altitude,
                                 &sensor_value->session_cycling.min_altitude,
                                 SENSOR_INT32_INVALID_DATA);

        calculate_signed_max_min(tmp_altitude, 
                                 &sensor_value->lap_cycling.max_altitude,
                                 &sensor_value->lap_cycling.min_altitude,
                                 SENSOR_INT32_INVALID_DATA);

        sensor_value->session_cycling.avg_altitude = calculate_signed_avg_value_base_sum_divid_count(altitude, 
                                                                                                   &m_environment_avg_para.altitude_ssum, 
                                                                                                   &m_environment_avg_para.altitude_scount);

        sensor_value->lap_cycling.avg_altitude = calculate_signed_avg_value_base_sum_divid_count(altitude, 
                                                                                               &m_environment_avg_para.altitude_lsum, 
                                                                                               &m_environment_avg_para.altitude_lcount);
    }
}

/*************************************************************************   
**	function name:	
**	description:	   
 *                      //温度(2byte) + 海拔（4byte） + 气压（4byte）  
 *                      1.打包采用小端模式
                     *  2.温度单位为0.1℃
                     *  3.海拔单位为0.1m
                     *  4.气压单位为0.001mbar
**	input para:		 
**                  	
**	return:			                                       
*************************************************************************/
uint32_t sensorEnvironmentDecodeData(uint8_t *buf, uint8_t len) {
    if (buf == NULL) {
        return JNI_ERROR_NULL;
    }
    
    if (len != ENVIRONMENT_DATA_LENGTH) {
        return JNI_ERROR_INVALID_LENGTH;
    }
    
    uint8_t index = 0;
    int16_t temperature;
    int32_t altitude;
    
    temperature = (int16_t)uint16_decode(&buf[index]);
    index += sizeof(uint16_t);

    altitude = (int32_t)uint32_decode(&buf[index]);
    index += sizeof(uint32_t);

    sensor_value->realtime_data.pressure = uint32_decode(&buf[index]);

    LOGD("envi", "temp:%x, alti:%x, press:%x",temperature,altitude,sensor_value->realtime_data.pressure);

    environment_sensor_temerature_update(temperature);
    environment_sensor_altitude_update(altitude);
    
    return JNI_SUCCESS;
}


/*************************************************************************   
**	function name:	clear_savg_altitude_count()
**	description:	                    
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
void clear_savg_altitude_count(void)
{
    m_environment_avg_para.altitude_scount = 0;
}

/*************************************************************************   
**	function name:	clear_lavg_cadence_count()
**	description:	                    
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
void clear_lavg_altitude_count(void)
{
    m_environment_avg_para.altitude_lcount = 0;
}

/*************************************************************************   
**	function name:	clear_savg_temperature_count()
**	description:	                    
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
void clear_savg_temperature_count(void)
{
    m_environment_avg_para.temperature_scount = 0;
}

/*************************************************************************   
**	function name:	clear_lavg_temperature_count()
**	description:	                    
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
void clear_lavg_temperature_count(void)
{
    m_environment_avg_para.temperature_lcount = 0;
}