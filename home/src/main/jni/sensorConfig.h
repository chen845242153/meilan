//
// Created by Administrator on 2020/8/24.
//

#ifndef MEILAN_SENSORCONFIG_H
#define MEILAN_SENSORCONFIG_H

//根据android或IOS配置其需要配置的信息
#define ANDROID_ENABLE              1
#define IOS_ENABLE                  0

#if (ANDROID_ENABLE+IOS_ENABLE != 1)
#error config error!!!!
#endif

//根据系统来配置打印信息
#if ANDROID_ENABLE
    #include <android/log.h>   
//    #include "D:\Android\Sdk\ndk-bundle\toolchains\llvm\prebuilt\windows-x86_64\sysroot\usr\include\android\log.h"
    #define LOG_TAG         "tag"
    
    #define LOGD(...)       __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
    #define LOGI(...)       __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
    #define LOGE(...)       __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#endif

#if IOS_ENABLE
    #define LOG_TAG         "sensor"
    #define LOGD(...) 
    #define LOGI(...)
#endif

#endif //MEILAN_SENSORCONFIG_H
