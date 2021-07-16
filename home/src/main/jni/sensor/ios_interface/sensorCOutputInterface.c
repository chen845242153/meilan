//
// Created by Administrator on 2020/8/27.
//



/***********************************************************************
*                               include files
************************************************************************
*/
#include "sensorCOutputInterface.h"
#include "../../stddef.h"
#include "../../jniError.h"

/***********************************************************************
*                               macro define
************************************************************************
*/

/***********************************************************************
*                               my type
************************************************************************
*/

/***********************************************************************
*                               extern variasensor
************************************************************************
*/

/***********************************************************************
*                               extern function
************************************************************************
*/

/***********************************************************************
*                               global variasensor
************************************************************************
*/

/***********************************************************************
*                               global function
************************************************************************
*/

/***********************************************************************
*                               static variasensor
************************************************************************
*/

/***********************************************************************
*                               static function
************************************************************************
*/
static uint32_t sensorCOIGetAllData(sensor_value_t *p);
static uint32_t sensorCOIGetRealtimeData(realtime_data_t *p);
static uint32_t sensorCOIGetSessionData(base_cycling_t *p);
static uint32_t sensorCOIGetCurrentLapData(base_cycling_t *p);
static uint32_t sensorCOIGetLastLapData(base_cycling_t *p);

static const sensorCOI_t mSensorCOI = {
        .All      = sensorCOIGetAllData,
        .realtime = sensorCOIGetRealtimeData,
        .session  = sensorCOIGetSessionData,
        .lap      = sensorCOIGetCurrentLapData,
        .lastLap  = sensorCOIGetLastLapData,        
};

const sensorCOI_t *const pSensorCOI = &mSensorCOI;

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
static uint32_t sensorCOIGetAllData(sensor_value_t *p)
{
    if(p == NULL){
        return JNI_ERROR_NULL;
    }
    
    *p = *sensor_value;
    
    return JNI_SUCCESS;
}
/*************************************************************************   
**	function name:	    
**	description:	                        
**	input para:		    
**                  	
**	return:			                                         
**************************************************************************/
static uint32_t sensorCOIGetRealtimeData(realtime_data_t *p)
{
    if(p == NULL){
        return JNI_ERROR_NULL;
    }

    *p = sensor_value->realtime_data;

    return JNI_SUCCESS;
}

/*************************************************************************   
**	function name:	    
**	description:	                        
**	input para:		    
**                  	
**	return:			                                         
**************************************************************************/
static uint32_t sensorCOIGetSessionData(base_cycling_t *p)
{
    if(p == NULL){
        return JNI_ERROR_NULL;
    }

    *p = sensor_value->session_cycling;

    return JNI_SUCCESS;
}

/*************************************************************************   
**	function name:	    
**	description:	                        
**	input para:		    
**                  	
**	return:			                                         
**************************************************************************/
static uint32_t sensorCOIGetCurrentLapData(base_cycling_t *p)
{
    if(p == NULL){
        return JNI_ERROR_NULL;
    }

    *p = sensor_value->lap_cycling;

    return JNI_SUCCESS;
}


/*************************************************************************   
**	function name:	    
**	description:	                        
**	input para:		    
**                  	
**	return:			                                         
**************************************************************************/
static uint32_t sensorCOIGetLastLapData(base_cycling_t *p)
{
    if(p == NULL){
        return JNI_ERROR_NULL;
    }

    *p = sensor_value->last_lap_cycling;

    return JNI_SUCCESS;
}