//
// Created by Administrator on 2020/8/27.
//

#ifndef MEILAN_SENSORPOWER_H
#define MEILAN_SENSORPOWER_H

#include "../../../stdint.h"
#include "../../../stdbool.h"

uint32_t sensorPowerDecodeData(uint8_t *buf, uint8_t len) ;

void sensorPowerProcessEverySecond(void);

void clear_savg_power_count(void);
void clear_lavg_power_count(void);

void sensorPowerStatusUpdate(bool connected);
void sensorPowerUpdateFTP(uint16_t ftp);

#endif //MEILAN_SENSORPOWER_H
