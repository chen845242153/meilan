//
// Created by Administrator on 2020/8/26.
//


/***********************************************************************
*                               include files
************************************************************************
*/


#include "sensorConfig.h"
#include "sensor/ios_interface/sensorCInputInterface.h"
#include "sensorCallback.h"
#include "stddef.h"
#include "jniError.h"

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
static CIIcb_t mCIIcb;
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
uint32_t sensorSetCallback(void *cb) {
    if (cb == NULL) {
        return JNI_ERROR_NULL;
    }

    CIIcb_t *p;

    p = (CIIcb_t *) cb;

    if ((p->CIIGetLocalTimecb == NULL) ||
        (p->CIIGetTimestampcb == NULL) ||
        (p->CIILocalTime2Timestamp == NULL) ||
        (p->addFitElements == NULL) ||
        (p->modifyFitElements == NULL)) {
        return JNI_ERROR_NULL;
    }

    mCIIcb = *p;

//    LOGD("1callback addr:%#x\r\n",  (uint32_t)p->CIIGetLocalTimecb);

    return JNI_SUCCESS;
}

/*************************************************************************   
**	function name:	    
**	description:	                  
**	input para:		    
**                  	
**	return:			                                  
**************************************************************************/
uint32_t sensorGetLocalTime(void) {
    if (mCIIcb.CIIGetLocalTimecb != NULL) {
        return mCIIcb.CIIGetLocalTimecb();
    }
    return 0;
}

/*************************************************************************   
**	function name:	    
**	description:	                  
**	input para:		    
**                  	
**	return:			                                  
**************************************************************************/
uint32_t sensorGetTimestamp(void) {
    if (mCIIcb.CIIGetTimestampcb != NULL) {
        return mCIIcb.CIIGetTimestampcb();
    }
    return 0;
}

/*************************************************************************   
**	function name:	    
**	description:	                  
**	input para:		    
**                  	
**	return:			                                  
**************************************************************************/
uint32_t sensorLocalTime2Timestamp(uint32_t localTime) {
    if (mCIIcb.CIILocalTime2Timestamp != NULL) {
        return mCIIcb.CIILocalTime2Timestamp(localTime);
    }
    return 0;
}

/*************************************************************************   
**	function name:	    
**	description:	                  
**	input para:		    
**                  	
**	return:			                                  
**************************************************************************/
void fitAddElements(const int8_t *const p, int length) {
    if (mCIIcb.addFitElements != NULL) {
        mCIIcb.addFitElements(p, length);
    }
}

/*************************************************************************   
**	function name:	    
**	description:	                  
**	input para:		    
**                  	
**	return:			                                  
**************************************************************************/
void fitModifyElements(const int8_t *const p, int startPos, int length) {
    if (mCIIcb.modifyFitElements != NULL) {
        mCIIcb.modifyFitElements(p, startPos, length);
    }
}