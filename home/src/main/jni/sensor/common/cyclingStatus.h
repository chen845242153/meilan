//
// Created by Administrator on 2020/8/25.
//

#ifndef MEILAN_CYCLINGSTATUS_H
#define MEILAN_CYCLINGSTATUS_H


#include "../../stdint.h"
#include "../../stdbool.h"

typedef enum {
    E_CYCLING_INVALID,
    E_CYCLING_START,
    E_CYCLING_PAUSE,
    E_CYCLING_SAVING,
    E_CYCLING_MAX,
} cyclingStatus_t;


uint32_t sensorModuleSetCylingStatus(cyclingStatus_t status);

cyclingStatus_t sensorModuleGetCylingStatus(void);

bool sensorModuleCheckCylingStart(void);

#endif //MEILAN_CYCLINGSTATUS_H
