//
// Created by Administrator on 2020/8/24.
//
/***********************************************************************
*                               include files
************************************************************************
*/
#include "sensorHRM.h"
#include "../sensorAPI.h"
#include "../sensorModule.h"
#include "../config/sensorParametersConfig.h"
#include "../../../jniError.h"
#include "../../../sensorConfig.h"

/***********************************************************************
*                               macro define
************************************************************************
*/
#define HRM_FLAG_MASK_HR_16BIT  (0x01 << 0)           /**< Bit mask used to extract the type of heart rate value. This is used to find if the received heart rate is a 16 bit value or an 8 bit value. */
#define HRM_FLAG_MASK_HR_RR_INT (0x01 << 4)           /**< Bit mask used to extract the presence of RR_INTERVALS. This is used to find if the received measurement includes RR_INTERVALS. */

/** @brief  Maximum number of RR intervals to be decoded for each HRM notifications (any extra RR intervals will be ignored).
 *
 * This define should be defined in the sdk_config.h file to override the default.
 */
#define BLE_HRS_C_RR_INTERVALS_MAX_CNT 20

/***********************************************************************
*                               my type
************************************************************************
*/
typedef struct {
    uint32_t hrs_ssum;
    uint32_t hrm_scount;
    uint32_t hrs_lsum;
    uint32_t hrm_lcount;
} hrm_avg_para_t;
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
    心率平均值计算
**/
static hrm_avg_para_t hrm_avg_para;

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
**	function name:	 hrm_data_recv_process
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
static void hrm_data_recv_process(bool is_init, uint16_t hr_value, uint32_t rr_interval) {

    LOGE("lion hrm:%d", hr_value);
    if (is_init) {
        //骑行过程中有蓝牙有可能断开
        return;
    }

    sensor_value->realtime_data.hrm = hr_value;

    if ((sensorModuleCheckCylingStart()) && (hr_value != 0)) {

        sensor_value->session_cycling.avg_hrm = calculate_avg_value_base_sum_divid_count(hr_value,
                                                                                         &hrm_avg_para.hrs_ssum,
                                                                                         &hrm_avg_para.hrm_scount);


        sensor_value->session_cycling.max_hrm = calculate_max_value_expect_invalid_data(hr_value,
                                                                                        sensor_value->session_cycling.max_hrm,
                                                                                        SENSOR_UINT16_INVALID_DATA);


        sensor_value->session_cycling.min_hrm = calculate_min_value_expect_invalid_data(hr_value,
                                                                                        sensor_value->session_cycling.min_hrm,
                                                                                        SENSOR_UINT16_INVALID_DATA);

        sensor_value->lap_cycling.avg_hrm = calculate_avg_value_base_sum_divid_count(hr_value,
                                                                                     &hrm_avg_para.hrs_lsum,
                                                                                     &hrm_avg_para.hrm_lcount);

        sensor_value->lap_cycling.max_hrm = calculate_max_value_expect_invalid_data(hr_value,
                                                                                    sensor_value->lap_cycling.max_hrm,
                                                                                    SENSOR_UINT16_INVALID_DATA);

        sensor_value->lap_cycling.min_hrm = calculate_min_value_expect_invalid_data(hr_value,
                                                                                    sensor_value->lap_cycling.min_hrm,
                                                                                    SENSOR_UINT16_INVALID_DATA);
    }
}

/*************************************************************************   
**	function name:	 hr_zone_time_update
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
void hr_zone_time_update(void) {
    uint8_t val, i, zone;

    if (sensorModuleCheckCylingStart()) {
        if (sensorParameters.hz.available && (sensor_value->realtime_data.hrm != SENSOR_UINT16_INVALID_DATA)) {
            val = sensor_value->realtime_data.hrm;
            zone = HRM_ZONE_CNT;

            if ((val > 0) && (val < sensorParameters.hz.hz[0])) {
                zone = 0;
            } else if (val >= sensorParameters.hz.hz[HRM_ZONE_CNT - 2]) {
                zone = HRM_ZONE_CNT - 1;
            } else {
                for (i = 0; i < HRM_ZONE_CNT - 2; i++) {
                    if ((val >= sensorParameters.hz.hz[i]) &&
                        (val < sensorParameters.hz.hz[i + 1])) {
                        zone = i + 1;
                        break;
                    }
                }
            }

            if (zone < HRM_ZONE_CNT) {
                sensor_value->lap_cycling.hrm_zone[zone]++;
                sensor_value->session_cycling.hrm_zone[zone]++;
                sensor_value->realtime_data.hrm_zone = zone;
            }
        }
    }
}


/*************************************************************************
**	function name:	 clear_savg_hrm_count
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
void clear_savg_hrm_count(void) {
    hrm_avg_para.hrm_scount = 0;
}

/*************************************************************************
**	function name:	 clear_lavg_hrm_count
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
void clear_lavg_hrm_count(void) {
    hrm_avg_para.hrm_lcount = 0;
}

/*************************************************************************   
**	function name:	 clear_hrm_savg
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
void clear_cur_hrm(void) {
    sensor_value->realtime_data.hrm = 0;
}

/*************************************************************************
**	function name:	 clear_hrm_savg
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
void sensorHRMStatusUpdate(bool connected) {

    m_connected = connected;

    clear_cur_hrm();

    if (connected) {
        hrm_data_recv_process(true, 0, 0);
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
uint32_t sensorHRMDecodeData(uint8_t *buf, uint8_t len) {
    uint32_t index = 0;
    uint16_t hrm;
    uint8_t rr_intervals_cnt;                                /**< Number of RR intervals. */
    uint16_t rr_intervals[BLE_HRS_C_RR_INTERVALS_MAX_CNT];    /**< RR intervals. */

    if (buf == NULL) {
        return JNI_ERROR_NULL;
    }

    if (!(buf[index++] & HRM_FLAG_MASK_HR_16BIT)) {
        // 8 Bit heart rate value received.
        hrm = buf[index++];  //lint !e415 suppress Lint Warning 415: Likely access out of bond
    } else {
        // 16 bit heart rate value received.
        hrm = uint16_decode(&(buf[index]));
        index += sizeof(uint16_t);
    }

    if ((buf[0] & HRM_FLAG_MASK_HR_RR_INT)) {
        uint32_t i;
        /*lint --e{415} --e{416} --e{662} --e{661} -save suppress Warning 415: possible access out of bond */
        for (i = 0; i < BLE_HRS_C_RR_INTERVALS_MAX_CNT; i++) {
            if (index >= len) {
                break;
            }
            rr_intervals[i] = uint16_decode(&(buf[index]));
            index += sizeof(uint16_t);
        }
        /*lint -restore*/
        rr_intervals_cnt = (uint8_t) i;
    }

    hrm_data_recv_process(false, hrm, 0);

    return JNI_SUCCESS;
}
