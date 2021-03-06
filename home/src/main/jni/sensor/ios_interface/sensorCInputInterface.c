//
// Created by Administrator on 2020/8/24.
//
/***********************************************************************
*                               include files
************************************************************************
*/
#include "sensorCInputInterface.h"
#include "../../jniError.h"
#include "../../stddef.h"
#include "../../sensorConfig.h"
#include "../common/sensorTick.h"
#include "../../sensorCallback.h"
#include "../common/sensorModule.h"


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

/***********************************************************************
*                                   end
************************************************************************
*/

//????????????????????????
static void sensorCIITickUpdte(uint16_t ms);

//???????????????????????????????????????
static void sensorCIICallEverySecond(void);

//????????????
static uint32_t sensorCIISetMode(uint8_t mode);

//??????????????????
static uint32_t sensorCIIRegisterCb(CIIcb_t *pCIIcb);

//???????????????????????????
static uint32_t sensorCIIPutSensorData(uint8_t *buf, uint8_t len, sensorType_t type);

//?????????????????????
static sensorType_t sensorCIIcheckSensorType(uint8_t *buf, uint8_t len, sensorType_t type);

//?????????????????????
static uint32_t sensorCIIensorStatusUpdate(sensorType_t type, bool connected);

//???????????????
static uint32_t sensorCIICtrl(ctrlOpCode_t opCode);

//?????????????????????
static uint32_t sensorCIIConfigCircumference(uint16_t circumference1mm);

static uint32_t sensorCIIConfigWeight(uint16_t weight001KG);

static uint32_t sensorCIIConfigHZ(hz_t *hz);

static uint32_t sensorCIIConfigPZ(pz_t *pz);

static uint32_t sensorCIIUninit(void);

static uint32_t sensorCIIInit(void);

static const sensorCIIFun_t mSensorCIIFun = {

        //??????????????????
        .regiserCb = sensorCIIRegisterCb,

        //sensor??????
        .tickUpdata = sensorCIITickUpdte,

        .setMode = sensorCIISetMode,

        //?????????????????????
        .processEverySecond = sensorCIICallEverySecond,

        //sensor??????????????????
        .putSensorData = sensorCIIPutSensorData,

        //??????sensor??????
        .checkSensorType = sensorCIIcheckSensorType,

        //sensor????????????
        .sensorStatusUpdate = sensorCIIensorStatusUpdate,

        //??????????????????
        .ctrl = sensorCIICtrl,

        //????????????
        .configCircumference = sensorCIIConfigCircumference,
        //????????????
        .configWeight = sensorCIIConfigWeight,
        //??????????????????
        .configHZ = sensorCIIConfigHZ,
        //??????????????????
        .configPZ = sensorCIIConfigPZ,

        //?????????
        .init = sensorCIIInit,

        //????????????
        .uninit = sensorCIIUninit,
};

const sensorCIIFun_t *const pSensorCIIFun = &mSensorCIIFun;

/*************************************************************************   
**	function name:	    sensorCIITickUpdte
**	description:	    ??????sensor??????????????????                    
**	input para:		    ms:??????ms??????????????????????????????
**                  	
**	return:			    ???                                     
**************************************************************************/
static void sensorCIITickUpdte(uint16_t ms) {
    sensorTickUpdate(ms);
}

/*************************************************************************   
**	function name:	    sensorCIICallEverySecond
**	description:	    ?????????????????????                    
**	input para:		    
**                  	
**	return:			                                         
**************************************************************************/
static void sensorCIICallEverySecond(void) {

    sensorModuleProcessEverySecond();
}
/*************************************************************************
**	function name:
**	description:
**	input para:
**
**	return:
**************************************************************************/
static uint32_t sensorCIISetMode(uint8_t mode)
{
    return sensorModuleSetMode(mode);
}
/*************************************************************************   
**	function name:      	 
**	description:	                    
**	input para:		 
**                  	
**	return:			                                      
**************************************************************************/
static uint32_t sensorCIIensorStatusUpdate(sensorType_t type, bool connected) {
    return sensorModuleStautsUpdate(type, connected);
}

/*************************************************************************   
**	function name:      	 
**	description:	                    
**	input para:		 
**                  	
**	return:			                                      
**************************************************************************/
static uint32_t sensorCIIPutSensorData(uint8_t *buf, uint8_t len, sensorType_t type) {
    return sensorModuleProcessSensorData(type, buf, len);
}

/*************************************************************************   
**	function name:      	 
**	description:	                    
**	input para:		 
**                  	
**	return:			                                      
**************************************************************************/
static sensorType_t sensorCIIcheckSensorType(uint8_t *buf, uint8_t len, sensorType_t type) {
    return (sensorType_t)sensorModuleCheckSensorType(type, buf, len);
}

/*************************************************************************   
**	function name:	 
**	description:	                    
**	input para:		 
**                  	
**	return:			                                      
**************************************************************************/
static uint32_t sensorCIICtrl(ctrlOpCode_t opCode) {
    return sensorModuleCtrl(opCode);
}

/*************************************************************************   
**	function name:	 
**	description:	                    
**	input para:		 
**                  	
**	return:			                                      
**************************************************************************/
static uint32_t sensorCIIConfigWeight(uint16_t weight001KG) {
    return sensorPCWeight(weight001KG);
}

/*************************************************************************   
**	function name:	 
**	description:	                    
**	input para:		 
**                  	
**	return:			                                      
**************************************************************************/
static uint32_t sensorCIIConfigCircumference(uint16_t circumference1mm) {
    return sensorPCCircumference(circumference1mm);
}

/*************************************************************************   
**	function name:	 
**	description:	                    
**	input para:		      	
**	return:			                                      
**************************************************************************/
static uint32_t sensorCIIConfigHZ(hz_t *hz) {
    return sensorPChz(hz);
}

/*************************************************************************   
**	function name:      sensorCIISetPowerZone	 
**	description:	    ?????????????????????                
**	input para:		    
**                  	
**	return:			                                      
**************************************************************************/
static uint32_t sensorCIIConfigPZ(pz_t *pz) {
    return sensorPCpz(pz);
}

/*************************************************************************   
**	function name:      	 
**	description:	                    
**	input para:		    
**                  	
**	return:			                                      
**************************************************************************/
static uint32_t sensorCIIRegisterCb(CIIcb_t *pCIIcb) {
    return sensorSetCallback(pCIIcb);
}

/*************************************************************************   
**	function name:      	 
**	description:	                    
**	input para:		    
**                  	
**	return:			                                      
**************************************************************************/
static uint32_t sensorCIIInit(void) {
    return sensorModuleInit();
}

/*************************************************************************   
**	function name:      	 
**	description:	                    
**	input para:		    
**                  	
**	return:			                                      
**************************************************************************/
static uint32_t sensorCIIUninit(void) {
    return sensorModuleUninit();
} 