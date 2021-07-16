//
// Created by Administrator on 2020/8/24.
//

#ifndef MEILAN_BLE_CINPUTINTERFACE_H
#define MEILAN_BLE_CINPUTINTERFACE_H

#include "../../stdint.h"
#include "sensorParametersDescription.h"

//sensor type
typedef enum
{
    E_SENSOR_TYPE_HRM,      //心率传感器
    E_SENSOR_TYPE_CSC,      //速度+踏频传感器
    E_SENSOR_TYPE_CAD,      //踏频传感器
    E_SENSOR_TYPE_SPD,      //速度传感器
    E_SENSOR_TYPE_POWER,    //功率传感器
    /*
     * GPS数据打包格式
     * 1.使用小端模式打包
     * 2.经度纬度放大倍数10^7倍 22.1234567==>221234567：正值为北纬和东经 负值为南纬和西经
     * 3.速度放大10倍，单位为0.1km/h
     * 4.航向范围0-360
     * 经度(4byte) + 纬度(4byte) + 速度（2byte） + 航向(2byte)
     */
    E_SENSOR_TYPE_GPS,      //GPS信息【经度+纬度+速度+航向】
    
    /*
     * 环境打包格式
     * 1.打包采用小端模式
     * 2.温度单位为0.1℃
     * 3.海拔单位为0.1m
     * 4.气压单位为0.001mbar
     * 温度(2byte) + 海拔（4byte） + 气压（4byte）
     */    
    E_SENSOR_TYPE_ENVIRONMENT,//环境传感器【温度、海拔、气压】
    E_SENSOR_TYPE_MAX
}sensorType_t;

//ctrl operate code
typedef enum
{
    E_CTRL_OP_CODE_CYCLING_START,   //运动开始
    E_CTRL_OP_CODE_CYCLING_PAUSE,   //运动暂停
    E_CTRL_OP_CODE_CYCLING_LAP,     //分圈
    E_CTRL_OP_CODE_CYCLING_END,     //结束,更新最后一圈的结束数据【结束时间、结束位置等信息】
    E_CTRL_OP_CODE_CYCLING_CLEAR,   //清除数据
    E_CTRL_OP_CODE_CYCLING_MAX,     
}ctrlOpCode_t;

//注册回调函数
typedef struct
{
    uint32_t (*CIIGetLocalTimecb)(void);
    uint32_t (*CIIGetTimestampcb)(void);
    uint32_t (*CIILocalTime2Timestamp)(uint32_t localTime);
    void (*addFitElements)(const int8_t *const p, int length);
    void (*modifyFitElements)(const int8_t *const p, int startPos, int length);
}CIIcb_t;

typedef struct {
    
    //注册回调函数
    uint32_t (*regiserCb)(CIIcb_t *pCIIcb);
    
    //sensor心跳
    void     (*tickUpdata)(uint16_t ms);

    //设置模式
    uint32_t (*setMode)(uint8_t mode);
    
    //每一秒执行一次
    void     (*processEverySecond)(void);
    
    //sensor原始数据输入
    uint32_t (*putSensorData)(uint8_t *buf, uint8_t len, sensorType_t type);

    sensorType_t (*checkSensorType)(uint8_t *buf, uint8_t len, sensorType_t type);
    
    //sensor状态更新
    uint32_t (*sensorStatusUpdate)(sensorType_t type, bool connected);
    
    //骑行状态控制
    uint32_t (*ctrl)(ctrlOpCode_t opCode);
    
    //轮径配置
    uint32_t (*configCircumference)(uint16_t circumference1mm);
    //体重配置
    uint32_t (*configWeight)(uint16_t weight001KG);
    //心率区间配置
    uint32_t (*configHZ)(hz_t *hz);
    //功率区间配置
    uint32_t (*configPZ)(pz_t *pz);
    
    //初始化
    uint32_t (*init)(void);
    
    //去初始化
    uint32_t (*uninit)(void);
}sensorCIIFun_t;


extern const sensorCIIFun_t *const pSensorCIIFun;


#endif //MEILAN_BLE_CINPUTINTERFACE_H
