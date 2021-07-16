//
// Created by Administrator on 2020/8/25.
//

/***********************************************************************
*                               include files
************************************************************************
*/
#include "sensorBaseCalculateProcess.h"
#include "sensorModule.h"
#include "sensorAPI.h"
#include "../../sensorConfig.h"


/***********************************************************************
*                               macro define
************************************************************************
*/

/***********************************************************************
*                               my type
************************************************************************
*/
typedef struct
{
//单次平均
    uint32_t cadence_ssum;
    uint32_t cadence_scount;

//单圈平均
    uint32_t cadence_lsum;
    uint32_t cadence_lcount;
}bicycle_avg_para_t;

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
static uint16_t m_weight;//kg
static bicycle_avg_para_t bicycle_avg_para;                  //平均值

/***********************************************************************
*                               static function
************************************************************************
*/

/***********************************************************************
*                                   end
************************************************************************
*/
/*************************************************************************   
**	function name:	calculate_calories_base_sensor()
**	description:	                    
**	input para:		 speed : 0.1km/h  tmp_tm :1ms
**                  	
**	return:			                                       
**************************************************************************/
void speed_calories_process_init(uint32_t weightKG)
{
    m_weight = weightKG;
}
/*************************************************************************   
**	function name:	calculate_calories_base_sensor()
**	description:	                    
**	input para:		 speed : 0.1km/h  tmp_tm :1ms
**                  	
**	return:			                                       
**************************************************************************/
static void calculate_calories(void)
{
    uint32_t bicycle_calorie, speed, tmp_tm;

    //消耗的卡路里（Kcal）=时速(km/h)×体重(kg)×0.24×运动时间(h)
    // (0.1km/h/10)*kg*(24/100)*(1ms/1000*3600) ==> 1Kcal
    // (0.1km/h/10)*kg*(24/100)*(1ms/1000*3600)) * 1000 ==> 1cal
    // (speed/10)*kg*(24/100)*(1ms/3600)


    speed  = sensor_value->session_cycling.avg_speed ;
    tmp_tm = sensor_value->session_cycling.activity_time;
    bicycle_calorie = (tmp_tm * speed * m_weight) / (150);
    sensor_value->session_cycling.calories = bicycle_calorie;

    speed  = sensor_value->lap_cycling.avg_speed ;
    tmp_tm = sensor_value->lap_cycling.activity_time;
    bicycle_calorie = (tmp_tm * speed * m_weight) / (150);
    sensor_value->lap_cycling.calories = bicycle_calorie;

}

/*************************************************************************   
**	function name:	 calculate_speed_pace
**	description:	                  
**	input para:	 0.1km/h	 
**                  	
**	return:			                                       
**************************************************************************/
void update_speed_relate_value(bool speed_valid, uint32_t real_speed, bool condition)
{
    //sensor采集速度时，如果real_time为0，需要保持3次为0才显示为0
    if(speed_valid)
    {
        sensor_value->realtime_data.speed = (uint16_t)real_speed;

        LOGE("lion spd:%d", real_speed);
    }


    //最大速度 平均速度
    if(condition)
    {
        if(sensor_value->session_cycling.activity_time != 0)
        {
            //0.1km/h
            sensor_value->session_cycling.avg_speed = 36*sensor_value->session_cycling.dist_travelled / (sensor_value->session_cycling.activity_time);

            if(sensor_value->session_cycling.avg_speed > sensor_value->session_cycling.max_speed)
            {
                sensor_value->session_cycling.avg_speed = sensor_value->session_cycling.max_speed;
            }
        }

        if(sensor_value->lap_cycling.activity_time != 0)
        {
            //0.1km/h
            sensor_value->lap_cycling.avg_speed = 36*sensor_value->lap_cycling.dist_travelled / (sensor_value->lap_cycling.activity_time);

            if(sensor_value->lap_cycling.avg_speed > sensor_value->lap_cycling.max_speed)
            {
                sensor_value->lap_cycling.avg_speed = sensor_value->lap_cycling.max_speed;
            }
        }

        if(real_speed != 0)
        {
            sensor_value->session_cycling.max_speed = calculate_max_value_expect_invalid_data(real_speed,
                                                                                             sensor_value->session_cycling.max_speed, 
                                                                                             SENSOR_UINT16_INVALID_DATA);

            sensor_value->lap_cycling.max_speed = calculate_max_value_expect_invalid_data(real_speed,
                                                                                         sensor_value->lap_cycling.max_speed, 
                                                                                         SENSOR_UINT16_INVALID_DATA);
        }

        calculate_calories();
    }
}

/*************************************************************************   
**	function name:	 set_cur_cadence
**	description:                                  
**	input para:		
**	return:			                                       
**************************************************************************/
void update_cadence_relate_value(bool cadence_valid, uint16_t real_cadence, bool condition)
{
    if(cadence_valid)
    {
        sensor_value->realtime_data.cadence = real_cadence;

        LOGE("lion cad:%d", real_cadence);

    }

    //最大踏频 平均踏频
    if(condition && (real_cadence != 0))
    {
        sensor_value->session_cycling.avg_cadence = calculate_avg_value_base_sum_divid_count(real_cadence, 
                                                                             &bicycle_avg_para.cadence_ssum, 
                                                                             &bicycle_avg_para.cadence_scount);
        
        sensor_value->session_cycling.max_cadence = calculate_max_value_expect_invalid_data(real_cadence, 
                                                                            sensor_value->session_cycling.max_cadence, 
                                                                            SENSOR_UINT16_INVALID_DATA);

        sensor_value->lap_cycling.avg_cadence = calculate_avg_value_base_sum_divid_count(real_cadence, 
                                                                             &bicycle_avg_para.cadence_lsum, 
                                                                             &bicycle_avg_para.cadence_lcount);
        
        sensor_value->lap_cycling.max_cadence = calculate_max_value_expect_invalid_data(real_cadence, 
                                                                            sensor_value->lap_cycling.max_cadence, 
                                                                            SENSOR_UINT16_INVALID_DATA);
    }
}
/*************************************************************************   
**	function name:	 clear_cur_speed
**	description:                                  
**	input para:		
**	return:			                                       
**************************************************************************/
void update_distance_relate_value(uint32_t dist, bool condition)
{
    if(condition)
    {
        sensor_value->session_cycling.dist_travelled += dist;
        sensor_value->lap_cycling.dist_travelled += dist;

        LOGE("lion dist:%d", sensor_value->session_cycling.dist_travelled);
    }
}


/*************************************************************************   
**	function name:	clear_savg_cadence_count()
**	description:	                    
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
void clear_savg_cadence_count(void)
{
    bicycle_avg_para.cadence_scount = 0;
}

/*************************************************************************   
**	function name:	clear_lavg_cadence_count()
**	description:	                    
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
void clear_lavg_cadence_count(void)
{
    bicycle_avg_para.cadence_lcount = 0;
}

/*************************************************************************   
**	function name:	 clear_cur_speed
**	description:                                  
**	input para:		
**	return:			                                       
**************************************************************************/
void clear_cur_speed(void)
{
    sensor_value->realtime_data.speed = 0;
}

/*************************************************************************   
**	function name:	 clear_cur_cadence
**	description:                                  
**	input para:		
**	return:			                                       
**************************************************************************/
void clear_cur_cadence(void)
{
    sensor_value->realtime_data.cadence = 0;
}
/*************************************************************************   
**	function name:	 
**	description:                                  
**	input para:		
**	return:			                                       
**************************************************************************/
void sensorPowerUpdateFTP(uint16_t ftp)
{
    sensor_value->realtime_data.FTP = ftp;
}