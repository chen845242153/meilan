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

//传感器心跳包函数
static void sensorCIITickUpdte(uint16_t ms);

//传感器每一秒需要处理的事务
static void sensorCIICallEverySecond(void);

//设置模式
static uint32_t sensorCIISetMode(uint8_t mode);

//注册回调函数
static uint32_t sensorCIIRegisterCb(CIIcb_t *pCIIcb);

//传感器原始数据入口
static uint32_t sensorCIIPutSensorData(uint8_t *buf, uint8_t len, sensorType_t type);

//检查传感器类型
static sensorType_t sensorCIIcheckSensorType(uint8_t *buf, uint8_t len, sensorType_t type);

//传感器状态更新
static uint32_t sensorCIIensorStatusUpdate(sensorType_t type, bool connected);

//传感器控制
static uint32_t sensorCIICtrl(ctrlOpCode_t opCode);

//传感器配置函数
static uint32_t sensorCIIConfigCircumference(uint16_t circumference1mm);

static uint32_t sensorCIIConfigWeight(uint16_t weight001KG);

static uint32_t sensorCIIConfigHZ(hz_t *hz);

static uint32_t sensorCIIConfigPZ(pz_t *pz);

static uint32_t sensorCIIUninit(void);

static uint32_t sensorCIIInit(void);

static const sensorCIIFun_t mSensorCIIFun = {

        //注册回调函数
        .regiserCb = sensorCIIRegisterCb,

        //sensor心跳
        .tickUpdata = sensorCIITickUpdte,

        .setMode = sensorCIISetMode,

        //每一秒执行一次
        .processEverySecond = sensorCIICallEverySecond,

        //sensor原始数据输入
        .putSensorData = sensorCIIPutSensorData,

        //检查sensor类型
        .checkSensorType = sensorCIIcheckSensorType,

        //sensor状态更新
        .sensorStatusUpdate = sensorCIIensorStatusUpdate,

        //骑行状态控制
        .ctrl = sensorCIICtrl,

        //轮径配置
        .configCircumference = sensorCIIConfigCircumference,
        //体重配置
        .configWeight = sensorCIIConfigWeight,
        //心率区间配置
        .configHZ = sensorCIIConfigHZ,
        //功率区间配置
        .configPZ = sensorCIIConfigPZ,

        //初始化
        .init = sensorCIIInit,

        //去初始化
        .uninit = sensorCIIUninit,
};

const sensorCIIFun_t *const pSensorCIIFun = &mSensorCIIFun;

/*************************************************************************   
**	function name:	    sensorCIITickUpdte
**	description:	    更新sensor模块的心跳包                    
**	input para:		    ms:每隔ms个毫秒调用一次此函数
**                  	
**	return:			    无                                     
**************************************************************************/
static void sensorCIITickUpdte(uint16_t ms) {
    sensorTickUpdate(ms);
}

/*************************************************************************   
**	function name:	    sensorCIICallEverySecond
**	description:	    每一秒调用一次                    
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
**	description:	    设置功率区间值                
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