//
// Created by Administrator on 2020/8/25.
//

#ifndef MEILAN_SENSOR_BASE_CACULATE_PRONCESS_H
#define MEILAN_SENSOR_BASE_CACULATE_PRONCESS_H

#include "../../stdint.h"
#include "../../stdbool.h"

void speed_calories_process_init(uint32_t weightKG);
void update_speed_relate_value(bool speed_valid, uint32_t real_speed, bool condition);
void update_cadence_relate_value(bool cadence_valid, uint16_t real_cadence, bool condition);
void update_distance_relate_value(uint32_t dist, bool condition);
void clear_savg_cadence_count(void);
void clear_lavg_cadence_count(void);
void clear_cur_speed(void);
void clear_cur_cadence(void);

#endif //MEILAN_SENSORCSC_H
