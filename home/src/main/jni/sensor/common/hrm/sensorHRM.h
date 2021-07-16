//
// Created by Administrator on 2020/8/24.
//

#ifndef MEILAN_SENSORHRM_H
#define MEILAN_SENSORHRM_H

#include "../../../stdint.h"
#include "../../../stdbool.h"

void hr_zone_time_update(void);
uint32_t sensorHRMDecodeData(uint8_t *buf, uint8_t len);

void clear_cur_hrm(void);
void clear_lavg_hrm_count(void);
void clear_savg_hrm_count(void);
void sensorHRMStatusUpdate(bool connected);

#endif //MEILAN_SENSORHRM_H
