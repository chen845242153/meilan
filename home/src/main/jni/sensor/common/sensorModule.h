//
// Created by Administrator on 2020/8/24.
//

#ifndef MEILAN_SENSOR_MODULE_H
#define MEILAN_SENSOR_MODULE_H

#include "sensorDataDefine.h"
#include "cyclingStatus.h"

//开始运动之前初始化数据
void cydm_data_management_init(void);

void cydm_init_sensor_value_when_new_lap(void);
void cydm_init_sensor_value_when_cycling_end(void);
void cydm_clear_sensor_value(void);

void sensorModuleProcessEverySecond(void);
uint32_t sensorModuleStautsUpdate(uint8_t type, bool connected);
uint32_t sensorModuleProcessSensorData(uint8_t type, uint8_t *buf, uint8_t len);
uint8_t sensorModuleCheckSensorType(uint8_t type, uint8_t *buf, uint8_t len);
uint32_t sensorModuleCtrl(uint8_t opCode);

uint32_t sensorModuleUninit(void);
uint32_t sensorModuleInit(void);

uint32_t sensorModuleSetMode(uint8_t mode);

#endif //MEILAN_SENSOR_MODULE_H
