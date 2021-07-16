//
// Created by Administrator on 2020/8/26.
//



/***********************************************************************
*                               include files
************************************************************************
*/
#include "sensorGPS.h"
#include "../../../stddef.h"
#include "../../../jniError.h"
#include "../sensorAPI.h"
#include "../sensorDataDefine.h"
#include "../csc/sensorCSC.h"
#include "../sensorBaseCalculateProcess.h"
#include "../cyclingStatus.h"
#include "../sensorTick.h"

/***********************************************************************
*                               macro define
************************************************************************
*/
//经度(4byte) + 纬度(4byte) + 速度（2byte） + 航向(2byte)

#define GPS_DATA_LENGTH                         12

#define GPS_MAX_SPEED                           200//200m/s     
/***********************************************************************
*                               my type
************************************************************************
*/
typedef struct
{
    bool        available;
    uint32_t    pre_ticks;
    int32_t     pre_long;
    int32_t     pre_lat;
    uint32_t    accumulate_distance;
}GPS_para_t;
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
static GPS_para_t m_GPS_para;


/***********************************************************************
*                               static function
************************************************************************
*/

/***********************************************************************
*                                   end
************************************************************************
*/
/*************************************************************************   
**	function name:	calculate_distance_base_GPS()
**	description:	                    
**	input para:		1m/h 
**                  	
**	return:			                                       
**************************************************************************/
static void calculate_distance_base_GPS(int32_t longitude, int32_t latitude) {

    uint32_t dist,cur_ticks, diff_ticks, max_diff_dist;

    if(!m_GPS_para.available)
    {
        m_GPS_para.available = true;
        m_GPS_para.pre_lat   = latitude;
        m_GPS_para.pre_long  = longitude;
        m_GPS_para.pre_ticks  = sensorTickgetTick();
        m_GPS_para.accumulate_distance = 0;
        return;
    }
    
    cur_ticks  = sensorTickgetTick();
    diff_ticks = cur_ticks - m_GPS_para.pre_ticks;
    m_GPS_para.pre_ticks  = cur_ticks;    
    max_diff_dist = diff_ticks * GPS_MAX_SPEED;
    
    dist = calculate_2point_long_lat_distance(m_GPS_para.pre_long,
                                              m_GPS_para.pre_lat, 
                                              longitude,
                                              latitude);
    m_GPS_para.pre_lat   = latitude;
    m_GPS_para.pre_long  = longitude;  

    //added by cc 20180806
    //当两点之间的距离超过TWO_POINT_MAX_DISTANCEmm后就不需要此数据
    //因为是1秒进入此函数1次
    //modified by cc 20180820
    /*
        根据丢失信号的时间长短来计算距离
    */
    if(dist >= max_diff_dist)
    {
        dist = 0;
    }

    m_GPS_para.accumulate_distance += dist;

    if(m_GPS_para.accumulate_distance > 100)
    {        
        dist = m_GPS_para.accumulate_distance / 100;
        m_GPS_para.accumulate_distance %= 100;//去掉最高位数据

        update_distance_relate_value(dist, sensorModuleCheckCylingStart());        
    }
}    
/*************************************************************************   
**	function name:	 
**	description:	经度(4byte) + 纬度(4byte) + 速度（2byte） + 航向(2byte) 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
uint32_t sensorGPSDecodeData(uint8_t *buf, uint8_t len)
{    
    if(buf == NULL)
    {
        return JNI_ERROR_NULL;
    }
    
    if(len != GPS_DATA_LENGTH){
        return JNI_ERROR_INVALID_LENGTH;
    }
    
    uint16_t speed, index = 0 ;
    
    sensor_value->realtime_data.longitude = uint32_decode(&buf[index]);
    index += sizeof(int32_t);

    sensor_value->realtime_data.latitude = uint32_decode(&buf[index]);
    index += sizeof(int32_t);

    speed = uint16_decode(&buf[index]);
    index += sizeof(uint16_t);
    if(!sensorSPDGetStatus())
    {
        calculate_distance_base_GPS(sensor_value->realtime_data.longitude,
                                    sensor_value->realtime_data.latitude);
        
        update_speed_relate_value(true, speed, sensorModuleCheckCylingStart());
    }

    sensor_value->realtime_data.heading = uint16_decode(&buf[index]);
    
    return JNI_SUCCESS;
}
/*************************************************************************   
**	function name:	 
**	description:	
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
void sensorGPSStatusUpdate(bool connected){
    m_GPS_para.available = false;
}