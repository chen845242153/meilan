//
// Created by Administrator on 2020/8/25.
//

/***********************************************************************
*                               include files
************************************************************************
*/

#include "sensorCSC.h"
#include "../../ios_interface/sensorCInputInterface.h"
#include "../sensorAPI.h"
#include "../../../jniError.h"

/***********************************************************************
*                               macro define
************************************************************************
*/
#define BLE_CSCS_WHEEL_REV_PRESENT                  0x00     /**< Wheel Revolution Data Supported bit. */
#define BLE_CSCS_CRANK_REV_PRESENT                  0x01     /**< Crank Revolution Data Supported bit. */

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
uint32_t sensorCSCDecodeData(uint8_t *buf, uint8_t len) {
    if (buf == NULL) {
        return JNI_ERROR_NULL;
    }

    bool wheel, crank;
    uint32_t tim, revs;
    uint32_t index = 0;

    wheel = (buf[index] >> BLE_CSCS_WHEEL_REV_PRESENT) & 0x01;
    crank = (buf[index] >> BLE_CSCS_CRANK_REV_PRESENT) & 0x01;
    index++;

    if (wheel) {
        revs = uint32_decode(&buf[index]);
        index += sizeof(uint32_t);

        tim = uint16_decode(&buf[index]);
        index += sizeof(uint16_t);

        speed_data_recv_process(false, tim, revs);
    }

    if (crank) {
        revs = uint16_decode(&buf[index]);
        index += sizeof(uint16_t);

        tim = uint16_decode(&buf[index]);

        cadence_data_recv_process(false, tim, revs);
    }
}


/*************************************************************************   
**	function name:	 
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
uint8_t checkCSCType(uint8_t *buf, uint8_t len) {
    if((buf[0]  == ((1<<BLE_CSCS_CRANK_REV_PRESENT) | (1<<BLE_CSCS_WHEEL_REV_PRESENT)))){
        return E_SENSOR_TYPE_CSC;
    }
    else if(buf[0] == (1<<BLE_CSCS_CRANK_REV_PRESENT)){
        return E_SENSOR_TYPE_CAD;
    }
    else if(buf[0] == (1<<BLE_CSCS_WHEEL_REV_PRESENT)){
        return E_SENSOR_TYPE_SPD;
    }
    return E_SENSOR_TYPE_MAX;
}