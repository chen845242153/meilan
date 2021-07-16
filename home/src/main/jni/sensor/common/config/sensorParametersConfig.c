//
// Created by Administrator on 2020/8/24.
//



/***********************************************************************
*                               include files
************************************************************************
*/
#include "sensorParametersConfig.h"
#include "../../../jniError.h"
#include "../../../stddef.h"
#include "../../../string.h"
#include "../sensorModule.h"
#include "../../../sensorConfig.h"
#include "../sensorBaseCalculateProcess.h"
#include "../csc/sensorCSC.h"
#include "../power/sensorPower.h"
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
sensorParameters_t sensorParameters =
        {
        .weight = 7500,
        .circumference = 2050,
        };
/***********************************************************************
*                               global function
************************************************************************
*/

/***********************************************************************
*                               static variable
************************************************************************
*/
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
uint32_t sensorPCWeight(uint16_t weight) {
    if (weight == 0) {
        return JNI_ERROR_INVALID_PARAM;
    }

    sensorParameters.weight = weight;

    speed_calories_process_init(weight/100);
    return JNI_SUCCESS;
}

/*************************************************************************   
**	function name:	    
**	description:	                  
**	input para:		    
**                  	
**	return:			                                  
**************************************************************************/
uint32_t sensorPCCircumference(uint32_t circumference) {
    
    if (circumference == 0) {
        return JNI_ERROR_INVALID_PARAM;
    }

    sensorParameters.circumference = circumference;

    sc_sensor_data_process_init(sensorParameters.circumference);
    return JNI_SUCCESS;
}

/*************************************************************************   
**	function name:	    
**	description:	                  
**	input para:		    
**                  	
**	return:			                                  
**************************************************************************/
uint32_t sensorPChz(hz_t *hz) {

    if (sensorModuleGetCylingStatus() != E_CYCLING_INVALID) {
        return JNI_ERROR_INVALID_STATE;
    }

    if (hz == NULL) {
        return JNI_ERROR_NULL;
    }
    
    sensorParameters.hz = *hz;
    sensorParameters.hz.available = true;
    
    LOGI("hz2:%d, hz3:%d, hz4:%d, hz5:%d, sensor_value size:%d", hz->hz[0],hz->hz[1],hz->hz[2],hz->hz[3], sizeof(sensor_value_t));

    return JNI_SUCCESS;
}


/*************************************************************************   
**	function name:	    
**	description:	                  
**	input para:		    
**                  	
**	return:			                                  
**************************************************************************/
uint32_t sensorPCpz(pz_t *pz) {

    if (sensorModuleGetCylingStatus() != E_CYCLING_INVALID) {
        return JNI_ERROR_INVALID_STATE;
    }
    if (pz == NULL) {
        return JNI_ERROR_NULL;
    }
    
    sensorParameters.pz = *pz;
    sensorParameters.pz.available = true;

    sensorPowerUpdateFTP(sensorParameters.pz.FTP);

    LOGI("ftp:%d, pz2:%d, pz3:%d, pz4:%d, pz5:%d, pz6:%d, pz7:%d\r\n",
         pz->FTP,
         pz->pz[0],
         pz->pz[1],
         pz->pz[2],
         pz->pz[3],
         pz->pz[4],
         pz->pz[5]);

    return JNI_SUCCESS;
}