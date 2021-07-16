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

static dataCsc_t cadenceData;                 //踏频原始数据buf
static bool m_connected;
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
            cadenceData.maxCnt = CAHCE_BUF_LEN1;
        }
        else
        {
            cadenceData.maxCnt = CAHCE_BUF_LEN2;
        }
    }

    *discard_packt = pkt + 1;

    return true;
}

/*************************************************************************   
**	function name:	calculate_cadence_base_sensor()
**	description:	                    
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
static void calculate_cadence_base_sensor(dataCsc_t *p_cadenceData)
{
    static uint8_t zeror_cadece_count;
    bool     cadence_valid = false;
    uint8_t  rpos, wpos;
    uint16_t tmp_tm;
    uint32_t bak_cadence = 0, bicycle_candence;   
    uint32_t tmp_val;
    
    rpos = p_cadenceData->rPos;
    wpos = p_cadenceData->wPos;

    tmp_tm  = (p_cadenceData->tim[wpos]-p_cadenceData->tim[rpos]) & BCBS_TIM_VAL_MAX;
    tmp_val = (p_cadenceData->rev[wpos]-p_cadenceData->rev[rpos]) & BCBS_REV_VAL_MAX;
    
    if(tmp_tm == 0)
    {
        bicycle_candence = 0;
    }
    else
    {        
        //单位 转/min
        //n(转)/tm(时间差) = n(转)/(tm*1/1024s) = 60*1024*n/tm
        bak_cadence = sensor_value->realtime_data.cadence;
        bicycle_candence = 60*1024*tmp_val / tmp_tm; //rpm  r/min

        if((bak_cadence != 0) &&
           (bicycle_candence > 115))//115rpm
        {
            if((bak_cadence + 30) < bicycle_candence)
            {
                bicycle_candence = bak_cadence;
            
                //将老数据丢弃重新开始计算，有可能是干扰数据导致
                p_cadenceData->cnt = 0;
            }
        }
        else if(bicycle_candence >= CADENCE_MAX_THRESHOLD)
        {
            bicycle_candence = bak_cadence;
        }
    }

    //当前踏频
    if(bicycle_candence == 0)
    {
        zeror_cadece_count++;
        if(zeror_cadece_count > CADENCE_ZERO_MAX_COUNT)
        {
            cadence_valid = true;
        }
    }
    else
    {
        zeror_cadece_count = 0;  
        cadence_valid = true;
    }
    update_cadence_relate_value(cadence_valid, bicycle_candence, sensorModuleCheckCylingStart());

}
/*************************************************************************   
**	function name:	 
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
void cadence_data_recv_process(bool is_init, uint16_t tim, uint16_t rev)
{
    static uint8_t  discard_packt;
    static uint32_t discard_utc;
    uint32_t        cur_ticks;

    if(is_init)
    {        
        cadenceData.maxCnt = CAHCE_BUF_LEN1;
        cadenceData.cnt    = 0;
        discard_packt      = 0;
        return;
    }

    if(filterInvalidPacket(&discard_packt))
    {
        return;
    }

    cur_ticks = sensorTickgetTick();

    if((sensor_value->realtime_data.cadence == 0) &&
       (cadenceData.cnt >= cadenceData.maxCnt) &&
       (sensor_abs(discard_utc, cur_ticks) > DISCARD_DATA_PEROID))
    {
        if((cadenceData.tim[cadenceData.wPos] != tim) ||
           (cadenceData.rev[cadenceData.wPos] != rev))
        {
            cadenceData.cnt = 0;
            discard_utc = cur_ticks;
        }
    }

    cacheDataInFifo(&cadenceData, tim, rev);
    
    calculate_cadence_base_sensor(&cadenceData);    
}


/*************************************************************************   
**	function name:	 clear_hrm_savg
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
void sensorCADStatusUpdate(bool connected){

    m_connected = connected;
    
    LOGD("sensorCADStatusUpdate:%d\r\n", connected);

    clear_cur_cadence();

    if(connected){
        cadence_data_recv_process(true, 0, 0);
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
bool sensorCADGetStatus(void)
{
    return m_connected;
}