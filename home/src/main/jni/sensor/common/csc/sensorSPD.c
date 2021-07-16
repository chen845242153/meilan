//
// Created by Administrator on 2020/8/25.
//


/***********************************************************************
*                               include files
************************************************************************
*/
#include "../sensorBaseCalculateProcess.h"
#include "../config/sensorParametersConfig.h"
#include "../sensorModule.h"
#include "../sensorTick.h"
#include "../sensorAPI.h"
#include "sensorCSC.h"
#include "../../../sensorConfig.h"
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
    uint32_t  rev_count;            //记录上次轮圈的圈数
    dataCsc_t data;                 //速度原始数据buf
}speedCache_t;


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
/**
**  做主机时用于计算速度、踏频变量
**/
static speedCache_t mSpeedCache;
static uint32_t     mWheelSize;//1mm
static bool         m_connected;
/***********************************************************************
*                               static function
************************************************************************
*/

/***********************************************************************
*                                   end
************************************************************************
*/
/*************************************************************************   
**	function name:	 sc_sensor_data_process_init
**	description:	 
**                                      
**	input para:		weight:kg wheel_size:mm
**                  	
**	return:			                                       
**************************************************************************/
void sc_sensor_data_process_init(uint32_t circumference)
{
    mWheelSize = circumference;
}

/*************************************************************************   
**	function name:	calculate_distance_base_sensor()
**	description:	                    
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
static void calculate_distance_base_sensor(speedCache_t *p_speedCache)
{
    static   uint16_t bak_distance;
    uint8_t  rpos,wpos;
    uint32_t tmp_val;

    //计算上次计算到本次计算过程中轮子所转的圈数
    //刚连接上蓝牙
    
    rpos = p_speedCache->data.rPos;
    wpos = p_speedCache->data.wPos;
    
    if(p_speedCache->rev_count == SENSOR_UINT32_INVALID_DATA)
    {
        bak_distance = 0;
        p_speedCache->rev_count = p_speedCache->data.rev[rpos];
    }
    
    tmp_val = (p_speedCache->data.rev[wpos] - p_speedCache->rev_count) & BCBS_REV_VAL_MAX;
    p_speedCache->rev_count = p_speedCache->data.rev[wpos];

    tmp_val = tmp_val * mWheelSize ;//0.001m
    bak_distance += tmp_val;

    if(bak_distance >= 1000)
    {
        tmp_val = bak_distance / 1000;
        bak_distance %= 1000;
    }
    else
    {
        tmp_val = 0;
    }

    //需要将错误数据丢弃
    /*
        测试中遇到过数据突然很大，100km以上
        为了兼容很早之前的C3
    */
    if(tmp_val >= 100)//100m
    {
        tmp_val = 0;
    }

    update_distance_relate_value(tmp_val, sensorModuleCheckCylingStart());
}

/*************************************************************************   
**	function name:	calculate_speed_base_sensor()
**	description:	                    
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
static void calculate_speed_base_sensor(speedCache_t *p_speedCache)
{
    static uint8_t zero_speed_count;
    bool     speed_valid = false;
    uint8_t  rpos, wpos;
    uint16_t tmp_tm, tmp_val;
    uint32_t bak_speed, bicycle_speed;
    
    wpos    = p_speedCache->data.wPos;
    rpos    = p_speedCache->data.rPos;
    
    tmp_tm  = (p_speedCache->data.tim[wpos]-p_speedCache->data.tim[rpos]) & BCBS_TIM_VAL_MAX;
    tmp_val = (p_speedCache->data.rev[wpos]-p_speedCache->data.rev[rpos]) & BCBS_REV_VAL_MAX;


    LOGE("speed tm:%d, val:%d,mWheelSize:%d", tmp_tm, tmp_val,mWheelSize);
    
    if(tmp_tm == 0)
    {
        bicycle_speed = 0;
    }
    else
    {
        //单位是0.1km/h 
        //n(圈) * wheel(轮径) / (t(时间差)*1/1024) = mm/s
        //(n(圈) * wheel(轮径)/1000) / (t(时间差)*1/1024) = m/s
        //((n(圈) * wheel(轮径)/1000)/100) / (t(时间差)*1/1024) = 0.1km/s
        //((n(圈) * wheel(轮径)/1000)/100) / ((t(时间差)*1/1024)/3600) = 0.1km/h
        //n(圈) * wheel(轮径) * 9 * 512 / (t(时间差)*125)
        
        bak_speed = sensor_value->realtime_data.speed;
        bicycle_speed = (46080 * mWheelSize * tmp_val) / (125*tmp_tm) + 5;//0.01km/h
        bicycle_speed /= 10;



        if((bak_speed != 0) && (bicycle_speed > 400))//40km/h
        {
            if((bak_speed + 200) < bicycle_speed)
            {
                bicycle_speed = bak_speed;

                //将老数据丢弃重新开始计算，有可能是误差数据导致
                p_speedCache->data.cnt = 0;
            }
        }
        else if(bicycle_speed >= SPEED_MAX_THRESHOLD) // < 99.9km/h
        {
            bicycle_speed = bak_speed;
        }
    }

    //当前速度
    if((bicycle_speed == 0) && (p_speedCache->data.cnt >= p_speedCache->data.maxCnt))
    {
        zero_speed_count++;
        if(zero_speed_count > SPEED_ZERO_MAX_COUNT)
        {
            speed_valid = true;
        }
    }
    else
    {
        zero_speed_count = 0;
        speed_valid = true;
    }

    update_speed_relate_value(speed_valid, bicycle_speed, sensorModuleCheckCylingStart());
}
/*************************************************************************   
**	function name:	 
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
static bool filterInvalidPacket(uint8_t *discard_packt)
{
    static uint32_t bak_ticks;
    uint8_t pkt;
    uint32_t cur_ticks, delt_ticks;
    
    pkt =  *discard_packt;
    
    if(pkt > CSC_DISCARD_PACKETS)
    {
        return false;
    }
    
    if(pkt == 1)
    {
        bak_ticks = sensorTickgetTick();
    }
    else if(pkt == 2)
    {
        cur_ticks  = sensorTickgetTick();
        delt_ticks = ((cur_ticks-bak_ticks) & 0xFFFFFFFF);

        if(delt_ticks > _2PKT_PEROID)
        {
            mSpeedCache.data.maxCnt = CAHCE_BUF_LEN1;
        }
        else
        {
            mSpeedCache.data.maxCnt = CAHCE_BUF_LEN2;
        }
    }  
    
    *discard_packt = pkt + 1;
    
    return true;
}

/*************************************************************************   
**	function name:	 
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
void speed_data_recv_process(bool is_init, uint16_t tim, uint16_t rev)
{    
    static uint8_t  discard_packt; 
    static uint32_t discard_utc;
    uint32_t        cur_ticks;
    
    if(is_init)
    {
        mSpeedCache.rev_count   = SENSOR_UINT32_INVALID_DATA;
        mSpeedCache.data.maxCnt = CAHCE_BUF_LEN1;
        mSpeedCache.data.cnt    = 0;
        discard_packt           = 0;
        return;
    }
    
    if(filterInvalidPacket(&discard_packt))
    {
        return;
    }
    
    cur_ticks = sensorTickgetTick();
    
    if((sensor_value->realtime_data.speed == 0) &&
       (mSpeedCache.data.cnt >= mSpeedCache.data.maxCnt) &&
       (sensor_abs(discard_utc, cur_ticks) > DISCARD_DATA_PEROID))
    {
        if((mSpeedCache.data.tim[mSpeedCache.data.wPos] != tim) ||
           (mSpeedCache.data.rev[mSpeedCache.data.wPos] != rev))
        {
            mSpeedCache.data.cnt = 0;
            discard_utc = cur_ticks;
        }
    }
    
    cacheDataInFifo(&mSpeedCache.data, tim, rev);
    
//    if(mSpeedCache.data.idx == mSpeedCache.data.maxIdx)
    {
        //calculate the speed and distance
        calculate_speed_base_sensor(&mSpeedCache);
        calculate_distance_base_sensor(&mSpeedCache);
    }
}

/*************************************************************************   
**	function name:	 clear_hrm_savg
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
void sensorSPDStatusUpdate(bool connected){

    m_connected = connected;

    clear_cur_speed();

    if(connected){
        speed_data_recv_process(true, 0, 0);
    }
}

/*************************************************************************   
**	function name:	 clear_hrm_savg
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
bool sensorSPDGetStatus(void)
{
    return m_connected;
}