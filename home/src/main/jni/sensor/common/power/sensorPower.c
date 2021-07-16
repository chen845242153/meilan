//
// Created by Administrator on 2020/8/27.
//




/***********************************************************************
*                               include files
************************************************************************
*/
#include "sensorPower.h"
#include "../../../stddef.h"
#include "../../../jniError.h"
#include "../sensorAPI.h"
#include "../sensorDataDefine.h"
#include "../csc/sensorCSC.h"
#include "../../../math.h"
#include "../cyclingStatus.h"
#include "../../../sensorConfig.h"

/***********************************************************************
*                               macro define
************************************************************************
*/
#define BLE_CYCLING_POWER_BALANCE_FLAG_Pos           0    /**< balance */
#define BLE_CYCLING_POWER_BALANCEL_FLAG_Pos          1    /**< 2side balance*/
#define BLE_CYCLING_POWER_TORQUE_FLAG_Pos            2
#define BLE_CYCLING_POWER_WHEEL_REV_FLAG_Pos         4     /**< Wheel Revolution Data Supported bit. */
#define BLE_CYCLING_POWER_CRANK_REV_FLAG_Pos         5     /**< Crank Revolution Data Supported bit. */
#define BLE_CYCLING_POWER_FORCE_MAGNITUDES_FLAG_pos  6
#define BLE_CYCLING_POWER_TORQUE_MAGNITUDES_FLAG_pos 7
#define BLE_CYCLING_POWER_ANGLES_FLAG_pos            8
#define BLE_CYCLING_POWER_TOP_DEDEAD_FLAG_Pos        9
#define BLE_CYCLING_POWER_BOTTOM_DEDEAD_FLAG_Pos     10
#define BLE_CYCLING_POWER_ACCUMULATED_FLAG_Pos       11


#define CACHE_BUF_SIZE                                  61
#define NP_REGION_TIME                                  30//30s

#if CACHE_BUF_SIZE <= 60
#error xxxx
#endif
/***********************************************************************
*                               my type
************************************************************************
*/
/*
 * calculate power/balance avgerage 
 * */
typedef struct {
    uint32_t power_ssum;
    uint32_t power_scount;
    uint32_t power_lsum;
    uint32_t power_lcount;

    uint32_t bal_ssum;          //balance session sum
    uint32_t bal_scount;        //balance session count
    uint32_t bal_lsum;          //balance lap sum
    uint32_t bal_lcount;        //balance lap count
} power_avg_para_t;

typedef struct {
    uint8_t cnt_valid;    //power sensor valid cnt
    uint8_t cnt_invalid;   //power sensor invlaid cnt
    const uint8_t elements;
} pb_cnt_t;

typedef struct {
    uint8_t b_sec;
    uint8_t b_sec1;
    uint8_t p_sec;
    pb_cnt_t ps_cnt[E_AVG_PB_MAXs];
    pb_cnt_t pm_cnt[E_AVG_PB_MAXm];
    pb_cnt_t bs_cnt[E_AVG_PB_MAXs];
    pb_cnt_t bm_cnt[E_AVG_PB_MAXm];
    uint32_t bs_sum[E_AVG_PB_MAXs];
    uint32_t bm_sum[E_AVG_PB_MAXm];
    uint32_t ps_sum[E_AVG_PB_MAXs];
    uint32_t pm_sum[E_AVG_PB_MAXm];
} pb_para_t;


/*
    if cnt0 == cnt1, if m_connected == false,
    calculate the avg3s 5s 10s from start
*/
typedef struct {
    uint8_t bpos;
    uint8_t ppos;
    uint8_t balance[CACHE_BUF_SIZE];
    uint16_t power[CACHE_BUF_SIZE];
} pb_cache_t;//power balance


typedef struct {
    bool rn1st;                     //region is not 1st
    uint8_t _30s_pos;
    uint16_t power[NP_REGION_TIME];     //cache power
    uint32_t _30s_sum;                  //power region(30s is a region) session sum
    uint32_t rcount;                    //region count
    uint32_t actime;                    //have power value's activity time for TSS
    double rsum;                      //region avg power pow(4) sum
} NP_temp_t;

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
static power_avg_para_t m_power_avg_para;
static NP_temp_t m_sNP_temp, m_lNP_temp;
static pb_cache_t m_pbcs;//power balance cache second
static pb_cache_t m_pbcm;//power balance cache mininute
static pb_para_t m_pb_para =
        {
                .ps_cnt = {{.elements=3},
                           {.elements=5},
                           {.elements=10},
                           {.elements=30},
                           {.elements=60}},
                .pm_cnt = {{.elements=5},
                           {.elements=30},
                           {.elements=60}},
                .bs_cnt = {{.elements=3},
                           {.elements=5},
                           {.elements=10},
                           {.elements=30},
                           {.elements=60}},
                .bm_cnt = {{.elements=5},
                           {.elements=30},
                           {.elements=60}},
        };

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
**	description:姣忛殧1s杩涘叆涓€娆? 
**										
**	input para: 	
**						
**	return: 											   
**************************************************************************/
static void computer_data_power_zone_time_update(void) {
    bool power_zone_valid = false;
    uint8_t zone;
    uint16_t power;

    if (sensorModuleCheckCylingStart() &&
        (sensor_value->realtime_data.power != SENSOR_UINT16_INVALID_DATA)) {
        power = sensor_value->realtime_data.power;

        if ((power != 0) && sensorParameters.pz.available) {
            if (power < sensorParameters.pz.pz[0])//zone1
            {
                zone = 0;
            } else if (power >= sensorParameters.pz.pz[POWER_ZONE_CNT - 2]) //zone7
            {
                zone = POWER_ZONE_CNT - 1;
            } else//zone2,zone3,zone4,zone5, zone6
            {
                for (uint8_t i = 0; i < POWER_ZONE_CNT - 2; i++) {
                    if ((power >= sensorParameters.pz.pz[i]) &&
                        (power < sensorParameters.pz.pz[i + 1])) {
                        zone = i + 1;
                        break;
                    }
                }
            }

            sensor_value->lap_cycling.power_zone[zone]++;
            sensor_value->session_cycling.power_zone[zone]++;
            sensor_value->realtime_data.power_zone = zone + 1;
            power_zone_valid = true;
        }
    }

    if (power_zone_valid == false) {
        sensor_value->realtime_data.power_zone = SENSOR_UINT8_INVALID_DATA;
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
static __inline uint8_t prev_n_pos(uint8_t total_elements, uint8_t cur_pos, uint8_t prev_n) {
    uint8_t res_pos;
    uint8_t n;

    n = prev_n;

    if (n > total_elements) {
        n = n % total_elements;
    }

    if (cur_pos >= n) {
        res_pos = cur_pos - n;
    } else {
        res_pos = total_elements - (n - cur_pos);
    }

    return res_pos;
}

/*************************************************************************	 
**	function name:	 
**	description: 
**										
**	input para:     pb : true- power , false -balance
**						
**	return: 											   
**************************************************************************/
static void pb_shift_buf(pb_cache_t *p_power, uint16_t pb_value, bool pb) {
    if (pb) {
        //must place first 
        p_power->ppos = CYCLE_NEXT_INDEX(p_power->ppos, CACHE_BUF_SIZE - 1);
        p_power->power[p_power->ppos] = pb_value;
    } else {
        //must place first 
        p_power->bpos = CYCLE_NEXT_INDEX(p_power->bpos, CACHE_BUF_SIZE - 1);
        p_power->balance[p_power->bpos] = (uint8_t) pb_value;
    }
}

/*************************************************************************	 
**	function name:	 
**	description: 
**										
**	input para: pb  : true-power, false-balance
**						
**	return: 											   
**************************************************************************/
static uint16_t
pb_calculate_smavg(pb_cache_t *p_cache, pb_cnt_t *p_pb_cnt, uint32_t *p_sum, bool pb) {
    bool rm = true;
    uint8_t idx, cnt;
    uint16_t avg;
    uint32_t sum;

    if (p_pb_cnt->cnt_valid == 0) {
        sum = 0;
        avg = 0;
    } else {
        sum = *p_sum;
    }

    if (p_pb_cnt->cnt_valid < p_pb_cnt->elements) {
        rm = false;
        p_pb_cnt->cnt_valid++;
    }

    cnt = p_pb_cnt->cnt_valid;
    p_pb_cnt->cnt_invalid = p_pb_cnt->cnt_valid;

    if (pb) {
        if (rm)//have reached elements
        {
            idx = prev_n_pos(CACHE_BUF_SIZE, p_cache->ppos, p_pb_cnt->elements);
            sum -= p_cache->power[idx];
        }

        sum += p_cache->power[p_cache->ppos];
        avg = sum / cnt;
    } else {
        if (rm)//have reached elements
        {
            idx = prev_n_pos(CACHE_BUF_SIZE, p_cache->bpos, p_pb_cnt->elements);
            sum -= p_cache->balance[idx];
        }

        sum += p_cache->balance[p_cache->bpos];
        avg = sum / cnt;
    }

    *p_sum = sum;

    return avg;
}

/*************************************************************************	 
**	function name:	 
**	description: calculate power 3s/5s/10s/30s/60s/5min/20min/30min/60min
**										
**	input para: Less than average time is not calculated	
**						
**	return: 											   
**************************************************************************/
static void calculate_balance_avg(void) {
    bool _1min = false;
    uint8_t i, balance;


    if (m_connected) {
        m_pb_para.b_sec++;

        if (m_pb_para.b_sec >= 60) {
            _1min = true;
            m_pb_para.b_sec = 0;
        }

        balance = (sensor_value->realtime_data.power_bal == SENSOR_UINT8_INVALID_DATA) ? 0 : (
                sensor_value->realtime_data.power_bal & 0x7F);

        pb_shift_buf(&m_pbcs, balance, false);

        if (_1min) {
            pb_shift_buf(&m_pbcm,
                         sensor_value->realtime_data.avgBs[E_AVG_PB_60s],
                         false);
        }

        //calculate avg.bal-start    	
        for (i = 0; i < E_AVG_PB_MAXs; i++) {
            sensor_value->realtime_data.avgBs[i] = pb_calculate_smavg(&m_pbcs, &m_pb_para.bs_cnt[i],
                                                                      &m_pb_para.bs_sum[i], false);


            if (sensor_value->realtime_data.power_bal & POWER_BALANCE_LR) {
                sensor_value->realtime_data.avgBs[i] |= POWER_BALANCE_LR;
            }
        }

        if (_1min) {
            for (i = 0; i < E_AVG_PB_MAXm; i++) {
                sensor_value->realtime_data.avgBm[i] = pb_calculate_smavg(&m_pbcs,
                                                                          &m_pb_para.bm_cnt[i],
                                                                          &m_pb_para.bm_sum[i],
                                                                          false);

                if (sensor_value->realtime_data.power_bal & POWER_BALANCE_LR) {
                    sensor_value->realtime_data.avgBm[i] |= POWER_BALANCE_LR;
                }
            }
        }
        //calculate avg.bal-end
    } else {
        m_pb_para.b_sec1++;

        if (m_pb_para.b_sec1 >= 60) {
            _1min = true;
            m_pb_para.b_sec1 = 0;
        }

        //calculate avg.bal-start    	
        for (i = 0; i < E_AVG_PB_MAXs; i++) {
            if (m_pb_para.bs_cnt[i].cnt_invalid > 0) {
                m_pb_para.bs_cnt[i].cnt_invalid--;
            } else {
                sensor_value->realtime_data.avgBs[i] = SENSOR_UINT8_INVALID_DATA;
            }
        }

        if (_1min) {
            for (i = 0; i < E_AVG_PB_MAXm; i++) {
                if (m_pb_para.bm_cnt[i].cnt_invalid > 0) {
                    m_pb_para.bm_cnt[i].cnt_invalid--;
                } else {
                    sensor_value->realtime_data.avgBm[i] = SENSOR_UINT8_INVALID_DATA;
                }
            }
        }
        //calculate avg.bal-end
    }
}

/*************************************************************************	 
**	function name:	 
**	description: calculate power 3s/5s/10s/30s/60s/5min/20min/30min/60min
**										
**	input para:
**						
**	return: 											   
**************************************************************************/
static void calculate_power_avg(void) {
    static uint8_t delay;

    bool _1min = false;
    uint8_t i;

    if (m_connected || (delay > 0)) {
        m_pb_para.p_sec++;

        if (m_pb_para.p_sec >= 60) {
            _1min = true;
            m_pb_para.p_sec = 0;
        }

        pb_shift_buf(&m_pbcs, sensor_value->realtime_data.power, true);

        if (_1min) {
            pb_shift_buf(&m_pbcm,
                         sensor_value->realtime_data.avgPs[E_AVG_PB_60s],
                         true);
        }

        //calculate avg.pwr-start
        for (i = 0; i < E_AVG_PB_MAXs; i++) {
            sensor_value->realtime_data.avgPs[i] = pb_calculate_smavg(&m_pbcs, &m_pb_para.ps_cnt[i],
                                                                      &m_pb_para.ps_sum[i], true);
        }

        if (_1min) {
            for (i = 0; i < E_AVG_PB_MAXm; i++) {
                sensor_value->realtime_data.avgPm[i] = pb_calculate_smavg(&m_pbcm,
                                                                          &m_pb_para.pm_cnt[i],
                                                                          &m_pb_para.pm_sum[i],
                                                                          true);
            }
        }
        //calculate avg.pwr-end  

        if (!m_connected) {
            if (delay > 0) {
                delay--;
            }
        } else {
            delay = 5;
        }
    } else {
        //calculate avg.pwr-start
        for (i = 0; i < E_AVG_PB_MAXs; i++) {
            sensor_value->realtime_data.avgPs[i] = SENSOR_UINT16_INVALID_DATA;
        }

        for (i = 0; i < E_AVG_PB_MAXm; i++) {
            sensor_value->realtime_data.avgPm[i] = SENSOR_UINT16_INVALID_DATA;
        }
        //calculate avg.pwr-end
    }
}

/*************************************************************************	 
**	function name:	 
**	description: calculate power 3s/5s/10s/30s/60s/5min/20min/30min/60min
**										
**	input para: Less than average time is not calculated	
**						
**	return: 											   
**************************************************************************/
static void computer_data_pb_average_update(void) {
    calculate_balance_avg();
    calculate_power_avg();
}

/*************************************************************************	 
**	function name:	
**	description:	|_______________________________________________| 
**                   ...
**                    ...
**                     ...
**                  1st   : s1=Cumulative sum[1s+2s+...+30s]
**                  2nd   : s2=Cumulative sum[2s+3s+...+31s]
**                  3rd   : s3=Cumulative sum[3s+4s+...+32s]  
**                  ...         ...
**                  NP = pow((s1^4 + s2^4 + s3^4 + ... + sn^4)/n, 0.25)	 
**	input para: 	
**						
**	return: 	         									   
**************************************************************************/
static void calculate_slNP(NP_temp_t *p, uint16_t power_value, uint16_t *NP) {
    double temp;

    p->actime++;

    //sum 1st 30s power value
    if (!p->rn1st) {
        p->power[p->_30s_pos++] = power_value;
        p->_30s_sum += power_value;

        if (p->_30s_pos == NP_REGION_TIME) {
            p->rn1st = true;
        }
    } else //sum 2.3.4... 30s power value
    {
        p->_30s_sum -= p->power[p->_30s_pos];
        p->_30s_sum += power_value;

        p->power[p->_30s_pos++] = power_value;
    }

    if (p->_30s_pos == NP_REGION_TIME) {
        p->_30s_pos = 0;
    }

    if (p->rn1st) {
        temp = p->_30s_sum;
        temp = temp / NP_REGION_TIME;
        temp = pow(temp, 4);

        p->rsum += temp;

        //update NP
        p->rcount++;
        temp = p->rsum / p->rcount;
        *NP = (uint16_t) pow(temp, 0.25);
    }
}

/*************************************************************************	 
**	function name:	void computer_NP_region(void)
**	description:	|_______________________________________________| 
**                   ...
**                    ...
**                     ...
**                  1st     : s1=Cumulative sum[1s+2s+...+30s]
**                  2nd     : s2=Cumulative sum[2s+3s+...+31s]
**                  3rd     : s3=Cumulative sum[3s+4s+...+32s]  
**                  ...         ...
**                  NP = pow((s1^4 + s2^4 + s3^4 + ... + sn^4)/n, 0.25)
**	input para: 	
**						
**	return: 	    
**************************************************************************/
static void calculate_NP(uint16_t power_value) {
    calculate_slNP(&m_sNP_temp, power_value, &sensor_value->session_cycling.NP);
    calculate_slNP(&m_lNP_temp, power_value, &sensor_value->lap_cycling.NP);
}

/*************************************************************************	 
**	function name:	 power_data_process_from_ble
**	description:	 
**										
**	input para: 	
**						
**	return: 											   
**************************************************************************/
static void computer_data_power_process(void) {
    uint16_t power_value;

    power_value = sensor_value->realtime_data.power;

    sensor_value->session_cycling.avg_power = calculate_avg_value_base_sum_divid_count(power_value,
                                                                                       &m_power_avg_para.power_ssum,
                                                                                       &m_power_avg_para.power_scount);

    sensor_value->session_cycling.KJ =
            sensor_value->session_cycling.avg_power * sensor_value->session_cycling.activity_time /
            1000;

    if ((sensor_value->realtime_data.FTP != 0) &&
        (sensor_value->session_cycling.NP != SENSOR_UINT16_INVALID_DATA)) {
        sensor_value->session_cycling.IF =
                sensor_value->session_cycling.NP * 1000 / sensor_value->realtime_data.FTP;
    }


    sensor_value->session_cycling.max_power = calculate_max_value_expect_invalid_data(power_value,
                                                                                      sensor_value->session_cycling.max_power,
                                                                                      SENSOR_UINT16_INVALID_DATA);


    sensor_value->session_cycling.avg_bal = calculate_avg_value_base_sum_divid_count(
            sensor_value->realtime_data.power_bal,
            &m_power_avg_para.bal_ssum,
            &m_power_avg_para.bal_scount);

    sensor_value->lap_cycling.avg_bal = calculate_avg_value_base_sum_divid_count(
            sensor_value->realtime_data.power_bal,
            &m_power_avg_para.bal_lsum,
            &m_power_avg_para.bal_lcount);


    sensor_value->lap_cycling.avg_power = calculate_avg_value_base_sum_divid_count(power_value,
                                                                                   &m_power_avg_para.power_lsum,
                                                                                   &m_power_avg_para.power_lcount);

    sensor_value->lap_cycling.KJ =
            sensor_value->lap_cycling.avg_power * sensor_value->lap_cycling.activity_time / 1000;

    if ((sensor_value->realtime_data.FTP != 0) &&
        (sensor_value->lap_cycling.NP != SENSOR_UINT16_INVALID_DATA)) {
        sensor_value->lap_cycling.IF =
                sensor_value->lap_cycling.NP * 1000 / sensor_value->realtime_data.FTP;
    }

    sensor_value->lap_cycling.max_power = calculate_max_value_expect_invalid_data(power_value,
                                                                                  sensor_value->lap_cycling.max_power,
                                                                                  SENSOR_UINT16_INVALID_DATA);


    uint16_t NP, FTP;//1w
    uint32_t tm;//1s
    uint64_t temp;

    FTP = sensor_value->realtime_data.FTP;

    if (sensor_value->session_cycling.NP != SENSOR_UINT16_INVALID_DATA) {
        NP = sensor_value->session_cycling.NP;
        tm = m_sNP_temp.actime;

        sensor_value->session_cycling.VI = NP * 100 / sensor_value->session_cycling.avg_power;

        if (sensor_value->session_cycling.avg_hrm != 0) {
            sensor_value->session_cycling.EF = NP * 100 / sensor_value->session_cycling.avg_hrm;
        }

        if (FTP != 0) {
            /*
                sensor_value->session_cycling.IF : multiple 1000 times
                TSS = (sec x NP x IF)/(FTP x 3600) x 100     SEC璁粌鏃堕暱锛堝崟浣嶏細绉掞級
                = (sec * NP * NP )/(36*FTP*FTP)
            */

            //        sensor_value->session_cycling.TSS = NP * sensor_value->session_cycling.activity_time*sensor_value->session_cycling.IF/(sensor_value->realtime_data.FTP*3600);
            //        sensor_value->session_cycling.TSS = 10 * NP * NP * tm / (FTP * FTP * 36);
            temp = 10;
            temp = temp * NP * NP * tm / (FTP * FTP * 36);
            sensor_value->session_cycling.TSS = (uint16_t) temp;
        }
    }
    if (sensor_value->lap_cycling.NP != SENSOR_UINT16_INVALID_DATA) {
        NP = sensor_value->lap_cycling.NP;
        tm = m_lNP_temp.actime;

        sensor_value->lap_cycling.VI = NP * 100 / sensor_value->lap_cycling.avg_power;

        if (sensor_value->lap_cycling.avg_hrm != 0) {
            sensor_value->lap_cycling.EF = NP * 100 / sensor_value->lap_cycling.avg_hrm;
        }

        if (FTP != 0) {
            //        sensor_value->lap_cycling.TSS = 10 * NP * NP * tm / (FTP * FTP * 36);
            temp = 10;
            temp = temp * NP * NP * tm / (FTP * FTP * 36);
            sensor_value->lap_cycling.TSS = (uint16_t) temp;
        }
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
void clear_savg_power_count(void) {
    m_power_avg_para.power_scount = 0;
    m_power_avg_para.bal_scount = 0;
    m_sNP_temp.rn1st = false;
    m_sNP_temp._30s_pos = 0;
    m_sNP_temp._30s_sum = 0;
    m_sNP_temp.rcount = 0;
    m_sNP_temp.actime = 0;
    m_sNP_temp.rsum = 0;
}

/*************************************************************************	 
**	function name:	 clear_lavg_power_count
**	description:	 
**										
**	input para: 	
**						
**	return: 											   
**************************************************************************/
void clear_lavg_power_count(void) {
    m_power_avg_para.power_lcount = 0;
    m_power_avg_para.bal_lcount = 0;

    m_lNP_temp.rn1st = false;
    m_lNP_temp._30s_pos = 0;
    m_lNP_temp._30s_sum = 0;
    m_lNP_temp.rcount = 0;
    m_lNP_temp.actime = 0;
    m_lNP_temp.rsum = 0;
}

/*************************************************************************
**	function name:	
**	description:	                    
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
static void calculatePowerExtendData(void) {
    uint16_t power;

    power = sensor_value->realtime_data.power;

    if (sensorParameters.weight != 0) {
        sensor_value->realtime_data.PWR = power * 10000 / sensorParameters.weight;
    }

    if (sensor_value->realtime_data.FTP != 0) {
        sensor_value->realtime_data.PFTP = power * 100 / sensor_value->realtime_data.FTP;
    }
}

/*************************************************************************
**	function name:	
**	description:	                    
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
uint32_t sensorPowerDecodeData(uint8_t *buf, uint8_t len) {

#if 1
    if (buf == NULL) {
        return JNI_ERROR_NULL;
    }

    uint16_t msk, index = 0;

    //解析flag 时发现和功率计发上来的数据不是小端模式
    msk = uint16_decode(&buf[index]);
    index += sizeof(uint16_t);

    //瞬时功率
    sensor_value->realtime_data.power = uint16_decode(&buf[index]);
    index += sizeof(uint16_t);

    //23 00 00 00 32 1d 00 05 6a

    //0010 0011

    //balance u8
    if (msk & (1 << BLE_CYCLING_POWER_BALANCE_FLAG_Pos)) {
        uint8_t balance = buf[index++];

        if (msk & (1 << BLE_CYCLING_POWER_BALANCEL_FLAG_Pos)) {
            balance |= POWER_BALANCE_LR;
        }

        sensor_value->realtime_data.power_bal = balance;
    }

    //torque   u16         
    if (msk & (1 << BLE_CYCLING_POWER_TORQUE_FLAG_Pos)) {
        uint16_t torque = uint16_decode(&buf[index]);
        index += sizeof(uint16_t);
    }

    if (msk & (1 << BLE_CYCLING_POWER_WHEEL_REV_FLAG_Pos)) {
        uint32_t revs = uint32_decode(&buf[index]);
        index += sizeof(uint32_t);

        uint16_t tim = uint16_decode(&buf[index]);
        index += sizeof(uint16_t);

        if (!sensorSPDGetStatus()) {
            speed_data_recv_process(true, tim, revs);
        }
    }

    if (msk & (1 << BLE_CYCLING_POWER_CRANK_REV_FLAG_Pos)) {
        uint16_t revs = uint16_decode(&buf[index]);
        index += sizeof(uint16_t);

        uint16_t tim = uint16_decode(&buf[index]);

        if (!sensorCADGetStatus()) {
            cadence_data_recv_process(true, tim, revs);
        }
    }

    //后续数据不解析...

    if (sensorModuleCheckCylingStart()) {
        computer_data_power_process();
    }

    calculatePowerExtendData();
//#else
    for (uint8_t i = 0; i < len; i++) {
        LOGE("power[%d]:%x", i, buf[i]);
    }
#endif
}

/*************************************************************************
**	function name:	
**	description:	                    
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
void sensorPowerProcessEverySecond(void) {
    computer_data_pb_average_update();
    computer_data_power_zone_time_update();

    if (sensorModuleCheckCylingStart()) {
        if (sensor_value->realtime_data.power != 0) {
            calculate_NP(sensor_value->realtime_data.power);
        }
    }
}

void clear_cur_power(void) {

    sensor_value->realtime_data.power = 0;
    sensor_value->realtime_data.power_bal = 0xFF;

}

/*************************************************************************
**	function name:
**	description:
**	input para:
**
**	return:
**************************************************************************/
void sensorPowerStatusUpdate(bool connected) {
    m_connected = connected;

    LOGD("sensorCADStatusUpdate:%d\r\n", connected);
    clear_cur_power();
}


        