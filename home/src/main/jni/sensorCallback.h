//
// Created by Administrator on 2020/8/26.
//

#ifndef MEILAN_SENSORCALLBACK_H
#define MEILAN_SENSORCALLBACK_H

#include "stdint.h"

uint32_t sensorSetCallback(void *cb);

uint32_t sensorGetLocalTime(void);
uint32_t sensorGetTimestamp(void);
uint32_t sensorLocalTime2Timestamp(uint32_t localTime);
void fitAddElements(const int8_t *const p, int length);
void fitModifyElements(const int8_t *const p, int startPos, int length);

#endif //MEILAN_SENSORCALLBACK_H
