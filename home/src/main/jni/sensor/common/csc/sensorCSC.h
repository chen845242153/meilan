//
// Created by Administrator on 2020/8/25.
//

#ifndef MEILAN_SENSORCSC_H
#define MEILAN_SENSORCSC_H


#include "../../../stdint.h"
#include "../../../stdbool.h"

#define SPEED_ZERO_MAX_COUNT                 0
#define CADENCE_ZERO_MAX_COUNT               1
#define CSC_DISCARD_PACKETS                  2
#define CAHCE_BUF_LEN1                       3
#define CAHCE_BUF_LEN2                       5

/* 1.如果两包之间的间隔小于_2PKT_PEROID，那么maxIdx = CAHCE_BUF_LEN2 - 1
 * 2.如果两包之间的间隔大于_2PKT_PEROID，那么maxIdx = CAHCE_BUF_LEN1 - 1
 * */
#define _2PKT_PEROID                         500//ms

#define DISCARD_DATA_PEROID                  5//s

//为了兼容ANT+里面计算速度、踏频，将BCBS_REV_VAL_MAX改为0xFF(power)
//为了兼容ANT+power的速度踏频，BCBS_REV_VAL_MAX由0xFFFF改为0x7FFF 1/1024
#define BCBS_TIM_VAL_MAX                     0x7FFF
#define BCBS_REV_VAL_MAX                     0xFF

#define CADENCE_MAX_THRESHOLD                200 //200r/m
#define SPEED_MAX_THRESHOLD                  999 //99.9km/h

#define CYCLE_NEXT_INDEX(cur_index, max_index)   (((cur_index) != (max_index)) ? ((cur_index) + 1) : 0)
#define CYCLE_PREV_INDEX(cur_index, max_index)   (((cur_index) != 0) ? ((cur_index) - 1) : (max_index))


typedef struct {
    uint8_t cnt;       //已经存储元素的个数
    uint8_t maxCnt;    //最多存储的元素个数
    uint8_t rPos;      //read pos
    uint8_t wPos;      //write pos
    uint16_t tim[CAHCE_BUF_LEN2];  //速度时间 (1/1024s)0-64s

    /*BLE的圈数是uint32_t类型，ANT+的圈数是uint16_t类型，
      为了兼容，这里取uint16_t类型
    */
    uint16_t rev[CAHCE_BUF_LEN2];  //圈数 0-65535  
} dataCsc_t;


/*************************************************************************   
**	function name:	 cadence_data_in_queue
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
static __inline void cacheDataInFifo(dataCsc_t *p_dataCsc, uint16_t time, uint16_t revs) {
    uint8_t idx;

    if (p_dataCsc->cnt == 0) {
        p_dataCsc->wPos = p_dataCsc->rPos;
    }

    if (p_dataCsc->cnt == p_dataCsc->maxCnt) {
        p_dataCsc->wPos = p_dataCsc->rPos;
        p_dataCsc->rPos = CYCLE_NEXT_INDEX(p_dataCsc->rPos, p_dataCsc->maxCnt - 1);
    } else {
        if (p_dataCsc->cnt != 0) {
            p_dataCsc->wPos = CYCLE_NEXT_INDEX(p_dataCsc->wPos, p_dataCsc->maxCnt - 1);
        }
        p_dataCsc->cnt++;
    }

    idx = p_dataCsc->wPos;

    p_dataCsc->tim[idx] = time;
    p_dataCsc->rev[idx] = revs;
}

void sc_sensor_data_process_init(uint32_t circumference);

void sensorCADStatusUpdate(bool connected);
void sensorSPDStatusUpdate(bool connected);

bool sensorCADGetStatus(void);
bool sensorSPDGetStatus(void);

void speed_data_recv_process(bool is_init, uint16_t tim, uint16_t rev);
void cadence_data_recv_process(bool is_init, uint16_t tim, uint16_t rev);

uint32_t sensorCSCDecodeData(uint8_t *buf, uint8_t len);
uint8_t checkCSCType(uint8_t *buf, uint8_t len);
#endif //MEILAN_SENSORCSC_H
