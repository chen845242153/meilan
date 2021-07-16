//
// Created by Administrator on 2020/8/27.
//

#ifndef MEILAN_SENSORENVIRONMENT_H
#define MEILAN_SENSORENVIRONMENT_H

#include "../../../stdint.h"

uint32_t sensorEnvironmentDecodeData(uint8_t *buf, uint8_t len);

void clear_savg_altitude_count(void);
void clear_lavg_altitude_count(void);

void clear_savg_temperature_count(void);
void clear_lavg_temperature_count(void);

#endif //MEILAN_SENSORENVIRONMENT_H
