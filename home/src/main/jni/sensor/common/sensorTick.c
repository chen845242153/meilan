//
// Created by Administrator on 2020/8/24.
//

/***********************************************************************
*                               include files
************************************************************************
*/

#include "sensorTick.h"
#include "../../sensorConfig.h"
#include "../../sensorCallback.h"

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
static uint8_t mThread;
static uint32_t mSensorTickms;
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
void sensorTickUpdate(uint16_t ms) {
    mThread = 1;
    mSensorTickms += ms;
}

/*************************************************************************   
**	function name:	    
**	description:	                  
**	input para:		    
**                  	
**	return:			                                  
**************************************************************************/
uint32_t sensorTickgetTick(void) {
    uint32_t tick;

    do {
        mThread = 0;
        tick = mSensorTickms;
    } while (mThread);    

    return tick;
}
