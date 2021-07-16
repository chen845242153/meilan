//
// Created by 周欢欢 on 2020/8/20.
//

#include "com_meilancycling_mema_ble_sensor_JniBleController.h"
#include "../ios_interface/sensorCInputInterface.h"
#include "../../sensorConfig.h"
#include "../common/sensorAPI.h"
#include "../../jniError.h"
#include "androidOutputInterface.h"

typedef struct {
    JNIEnv pJNI;
    JNIEnv *ppJNI;
    jobject instance;
    jmethodID getLocalTimeID;
    jmethodID getTimestampID;
    jmethodID localTime2Timestamp;
    jmethodID addFitElementID;
    jmethodID modifyFitElementID;
} jniStruct_t;

static jniStruct_t jniStruct;

static uint32_t jniRegisterCIIcb(void);

static uint32_t jniGetLocalTime(void);


static void jniUpdateJNIStruct(JNIEnv *env, jobject instance) {
    jniStruct.pJNI = *env;
    jniStruct.ppJNI = env;
    jniStruct.instance = instance;
}

JNIEXPORT void JNICALL

Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorCIITickUpdate(JNIEnv *env,
                                                                            jobject instance,
                                                                            jint ms) {

    // TODO
    //jniUpdateJNIStruct(env, instance);
    pSensorCIIFun->tickUpdata(ms);

}


static uint32_t jniGetLocalTime(void) {

    jlong time;


    time = jniStruct.pJNI->CallLongMethod(jniStruct.ppJNI,
                                          jniStruct.instance,
                                          jniStruct.getLocalTimeID);

    LOGI("get local time:%d\n", (uint32_t) time);

    return (uint32_t) time;
}

static uint32_t jniGetTimestamp(void) {

    jlong time;


    time = jniStruct.pJNI->CallLongMethod(jniStruct.ppJNI,
                                          jniStruct.instance,
                                          jniStruct.getTimestampID);

    //LOGI("get timestamp time:%d\n", (uint32_t) time);

    return (uint32_t) time;
}

static uint32_t jniLocalTime2Timestamp(uint32_t LocalTime) {

    jlong time;


    time = jniStruct.pJNI->CallLongMethod(jniStruct.ppJNI,
                                          jniStruct.instance,
                                          jniStruct.localTime2Timestamp,
                                          LocalTime);

    //LOGI("get timestamp time:%d\n", (uint32_t) time);

    return (uint32_t) time;
}

static void jniAddFitElement(const int8_t *const p, int length) {

    for (int i = 0; i < length; i++) {
        jniStruct.pJNI->CallVoidMethod(jniStruct.ppJNI,
                                       jniStruct.instance,
                                       jniStruct.addFitElementID,
                                       p[i]);
    }
}

static void jniModifyFitElement(const int8_t *const p, int startPos, int length) {

    for (int i = 0; i < length; i++) {
        jniStruct.pJNI->CallVoidMethod(jniStruct.ppJNI,
                                       jniStruct.instance,
                                       jniStruct.modifyFitElementID,
                                       p[i],
                                       i + startPos);
    }
}


static uint32_t jniRegisterCIIcb(void) {
    uint32_t err_code;
    CIIcb_t cb;

    cb.CIIGetLocalTimecb = jniGetLocalTime;
    cb.CIIGetTimestampcb = jniGetTimestamp;
    cb.CIILocalTime2Timestamp = jniLocalTime2Timestamp;
    cb.addFitElements = jniAddFitElement;
    cb.modifyFitElements = jniModifyFitElement;

    err_code = pSensorCIIFun->regiserCb(&cb);


    if (err_code != JNI_SUCCESS) {
        LOGE("sensor Init error :%d\n", err_code);
    } else {
        LOGI("sensor Init success:%#x\n", (uint32_t) jniGetLocalTime);
    }
    return err_code;
}


JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorParametersConfigPZ(JNIEnv *env,
                                                                                 jobject instance,
                                                                                 jobject powerZone) {

    // TODO
    pz_t pz;
    jlong err_code;
    JNIEnv p;

    jniUpdateJNIStruct(env, instance);

    p = *env;

    jclass jcs = p->GetObjectClass(env, powerZone);
    jfieldID filedID;

    if (jcs == NULL) {
        return JNI_ERROR_FORBIDDEN;
    }

    pz.FTP = (uint16_t) GET_JAVA_INT_VALUE(powerZone, GET_JAVA_INT_ID("ftp"));
    pz.pz[0] = (uint16_t) GET_JAVA_INT_VALUE(powerZone, GET_JAVA_INT_ID("zone2"));
    pz.pz[1] = (uint16_t) GET_JAVA_INT_VALUE(powerZone, GET_JAVA_INT_ID("zone3"));
    pz.pz[2] = (uint16_t) GET_JAVA_INT_VALUE(powerZone, GET_JAVA_INT_ID("zone4"));
    pz.pz[3] = (uint16_t) GET_JAVA_INT_VALUE(powerZone, GET_JAVA_INT_ID("zone5"));
    pz.pz[4] = (uint16_t) GET_JAVA_INT_VALUE(powerZone, GET_JAVA_INT_ID("zone6"));
    pz.pz[5] = (uint16_t) GET_JAVA_INT_VALUE(powerZone, GET_JAVA_INT_ID("zone7"));

    err_code = pSensorCIIFun->configPZ(&pz);

    return err_code;
}

JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorParametersConfigHZ(JNIEnv *env,
                                                                                 jobject instance,
                                                                                 jobject hrmZone) {

    // TODO
    hz_t hz;
    jlong err_code;
    JNIEnv p = *env;

    jniUpdateJNIStruct(env, instance);

    jclass jcs = p->GetObjectClass(env, hrmZone);
    jfieldID filedID;

    if (jcs == NULL) {
        return JNI_ERROR_FORBIDDEN;
    }
    hz.hz[0] = (uint8_t) GET_JAVA_INT_VALUE(hrmZone, GET_JAVA_INT_ID("zone2"));
    hz.hz[1] = (uint8_t) GET_JAVA_INT_VALUE(hrmZone, GET_JAVA_INT_ID("zone3"));
    hz.hz[2] = (uint8_t) GET_JAVA_INT_VALUE(hrmZone, GET_JAVA_INT_ID("zone4"));
    hz.hz[3] = (uint8_t) GET_JAVA_INT_VALUE(hrmZone, GET_JAVA_INT_ID("zone5"));

    err_code = pSensorCIIFun->configHZ(&hz);

    return err_code;
}

JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorParametersConfigWeight(JNIEnv *env,
                                                                                     jobject instance,
                                                                                     jint weight001KG) {

    // TODO
    jlong err_code;

    jniUpdateJNIStruct(env, instance);

    err_code = pSensorCIIFun->configWeight(weight001KG);
    return err_code;
}

JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorParametersConfigCircumference(
        JNIEnv *env, jobject instance, jint circumference1mm) {

    // TODO
    jlong err_code;

    jniUpdateJNIStruct(env, instance);

    err_code = pSensorCIIFun->configCircumference(circumference1mm);
    return err_code;
}

static ctrlOpCode_t ctrlOpCodeConverseFromJavaToC(JNIEnv *env,
                                                  jobject instance,
                                                  jint opCode) {
    JNIEnv p = (*env);
    jfieldID filedID;
    jint op;

    jclass jcs = p->GetObjectClass(env, instance);

    if (jcs == NULL) {
        LOGE("jcs is NULL, line: %d, file：%s", __LINE__, __FILE__);
        return E_CTRL_OP_CODE_CYCLING_MAX;
    }

    op = GET_JAVA_INT_VALUE(instance, GET_JAVA_INT_ID("E_CTRL_OP_CODE_CYCLING_START"));

    if (op == opCode) {
        return E_CTRL_OP_CODE_CYCLING_START;
    }

    op = GET_JAVA_INT_VALUE(instance, GET_JAVA_INT_ID("E_CTRL_OP_CODE_CYCLING_PAUSE"));
    if (op == opCode) {
        return E_CTRL_OP_CODE_CYCLING_PAUSE;
    }

    op = GET_JAVA_INT_VALUE(instance, GET_JAVA_INT_ID("E_CTRL_OP_CODE_CYCLING_LAP"));
    if (op == opCode) {
        return E_CTRL_OP_CODE_CYCLING_LAP;
    }

    op = GET_JAVA_INT_VALUE(instance, GET_JAVA_INT_ID("E_CTRL_OP_CODE_CYCLING_END"));
    if (op == opCode) {
        return E_CTRL_OP_CODE_CYCLING_END;
    }

    return E_CTRL_OP_CODE_CYCLING_MAX;
}

JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorCtrl(JNIEnv *env, jobject instance,
                                                                   jint opCode) {

    // TODO
    jlong err_code;

    jniUpdateJNIStruct(env, instance);

    ctrlOpCode_t op = ctrlOpCodeConverseFromJavaToC(env, instance, opCode);

    if (op == E_CTRL_OP_CODE_CYCLING_MAX) {
        return JNI_ERROR_FORBIDDEN;
    }

    err_code = pSensorCIIFun->ctrl(op);

    return err_code;
}

static sensorType_t sensorTypeConverseFromJavaToC(JNIEnv *env,
                                                  jobject instance,
                                                  jint sensorType) {
    JNIEnv p = (*env);
    jint type;

    jclass jcs = p->GetObjectClass(env, instance);
    if (jcs == NULL) {
        LOGE("jcs is NULL, line: %d, file：%s", __LINE__, __FILE__);
        return E_SENSOR_TYPE_MAX;
    }

    type = GET_JAVA_INT_VALUE(instance, GET_JAVA_INT_ID("E_SENSOR_TYPE_HRM"));
    if (type == sensorType) {
        return E_SENSOR_TYPE_HRM;
    }


    type = GET_JAVA_INT_VALUE(instance, GET_JAVA_INT_ID("E_SENSOR_TYPE_CSC"));
    if (type == sensorType) {
        return E_SENSOR_TYPE_CSC;
    }

    type = GET_JAVA_INT_VALUE(instance, GET_JAVA_INT_ID("E_SENSOR_TYPE_CAD"));
    if (type == sensorType) {
        return E_SENSOR_TYPE_CAD;
    }

    type = GET_JAVA_INT_VALUE(instance, GET_JAVA_INT_ID("E_SENSOR_TYPE_SPD"));
    if (type == sensorType) {
        return E_SENSOR_TYPE_SPD;
    }

    type = GET_JAVA_INT_VALUE(instance, GET_JAVA_INT_ID("E_SENSOR_TYPE_POWER"));
    if (type == sensorType) {
        return E_SENSOR_TYPE_POWER;
    }

    type = GET_JAVA_INT_VALUE(instance, GET_JAVA_INT_ID("E_SENSOR_TYPE_GPS"));
    if (type == sensorType) {
        return E_SENSOR_TYPE_GPS;
    }

    type = GET_JAVA_INT_VALUE(instance, GET_JAVA_INT_ID("E_SENSOR_TYPE_ENVIRONMENT"));
    if (type == sensorType) {
        return E_SENSOR_TYPE_ENVIRONMENT;
    }

    return E_SENSOR_TYPE_MAX;
}

JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorStatusUpdate(JNIEnv *env,
                                                                           jobject instance,
                                                                           jint sensorType,
                                                                           jboolean connected) {

    // TODO
    jlong err_code;

    jniUpdateJNIStruct(env, instance);

    sensorType_t type = sensorTypeConverseFromJavaToC(env, instance, sensorType);

    if (type == E_SENSOR_TYPE_MAX) {
        return JNI_ERROR_FORBIDDEN;
    }

    err_code = pSensorCIIFun->sensorStatusUpdate(type, connected);

    return err_code;
}

JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorDataTransfer(JNIEnv *env,
                                                                           jobject instance,
                                                                           jint sensorType,
                                                                           jbyteArray arr_,
                                                                           jint arrLen) {
    jlong err_code;
    JNIEnv p = (*env);

    jniUpdateJNIStruct(env, instance);

    jbyte *arr = p->GetByteArrayElements(env, arr_, NULL);

    // TODO
    sensorType_t type = sensorTypeConverseFromJavaToC(env, instance, sensorType);

    if (type == E_SENSOR_TYPE_MAX) {
        err_code = JNI_ERROR_FORBIDDEN;
    } else {
        err_code = pSensorCIIFun->putSensorData((uint8_t *) arr, arrLen, type);
    }

    p->ReleaseByteArrayElements(env, arr_, arr, 0);

    return err_code;
}

JNIEXPORT jint JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_getSensorTypeBaseData(JNIEnv *env,
                                                                              jobject instance,
                                                                              jint sensorType,
                                                                              jbyteArray arr_,
                                                                              jint arrLen) {
    // TODO: implement getSensorTypeBaseData()
    jlong err_code;
    jint type1;
    JNIEnv p = (*env);

    jniUpdateJNIStruct(env, instance);

    jbyte *arr = p->GetByteArrayElements(env, arr_, NULL);

    // TODO
    sensorType_t type = sensorTypeConverseFromJavaToC(env, instance, sensorType);

    if (type == E_SENSOR_TYPE_MAX) {
        type1 = type;
    } else {
        type1 = pSensorCIIFun->checkSensorType((uint8_t *) arr, arrLen, type);
    }

    p->ReleaseByteArrayElements(env, arr_, arr, 0);

    return type1;

}

JNIEXPORT void JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorProcessEverySecond(JNIEnv *env,
                                                                                 jobject instance) {

    // TODO
    jniUpdateJNIStruct(env, instance);
    pSensorCIIFun->processEverySecond();
}


JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorInit(JNIEnv *env, jobject instance) {

    // TODO

    jlong err_code;
    jniUpdateJNIStruct(env, instance);


    jclass claz = jniStruct.pJNI->GetObjectClass(env, instance);

    //get callback function ID
    jniStruct.getLocalTimeID = jniStruct.pJNI->GetMethodID(env, claz, "getLocalTime", "()J");
    jniStruct.getTimestampID = jniStruct.pJNI->GetMethodID(env, claz, "getTimestamp", "()J");
    jniStruct.localTime2Timestamp = jniStruct.pJNI->GetMethodID(env, claz, "LocalTime2Timestamp",
                                                                "(J)J");
    jniStruct.addFitElementID = jniStruct.pJNI->GetMethodID(env, claz, "addFitElement", "(B)V");
    jniStruct.modifyFitElementID = jniStruct.pJNI->GetMethodID(env, claz, "modifyFitElement",
                                                               "(BI)V");

    err_code = pSensorCIIFun->init();

    if (err_code == JNI_SUCCESS) {
        err_code = jniRegisterCIIcb();
    }


    return err_code;
}

JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorUnInit(JNIEnv *env,
                                                                     jobject instance) {

    // TODO

    jniUpdateJNIStruct(env, instance);

    jlong err_code;

    err_code = pSensorCIIFun->uninit();

    return err_code;
}

JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorGetRealtimeData(JNIEnv *env,
                                                                              jobject instance,
                                                                              jobject realtimeBean) {

    // TODO   
    jlong err_code;

    jniUpdateJNIStruct(env, instance);
    err_code = sensorGetRealtimeData(env, instance, realtimeBean);
    return err_code;

}

JNIEXPORT jlong  JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorGetSessionData(JNIEnv *env,
                                                                             jobject instance,
                                                                             jobject statisticalBean) {

    // TODO
    jlong err_code;
    jniUpdateJNIStruct(env, instance);

    err_code = sensorGetStatisticsData(env, instance, statisticalBean, E_GET_STATISTICS_SESSION);
    return err_code;

}

JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorGetLapData(JNIEnv *env,
                                                                         jobject instance,
                                                                         jobject statisticalBean) {

    // TODO
    jlong err_code;

    jniUpdateJNIStruct(env, instance);

    err_code = sensorGetStatisticsData(env, instance, statisticalBean, E_GET_STATISTICS_LAP);
    return err_code;

}

JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorGetLastLapData(JNIEnv *env,
                                                                             jobject instance,
                                                                             jobject statisticalBean) {

    // TODO
    jlong err_code;


    jniUpdateJNIStruct(env, instance);

    err_code = sensorGetStatisticsData(env, instance, statisticalBean, E_GET_STATISTICS_LAST_LAP);
    return err_code;

}

JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorGetAllData(JNIEnv *env,
                                                                         jobject instance,
                                                                         jobject realtimeData,
                                                                         jobject sessionData,
                                                                         jobject lapData,
                                                                         jobject lastLapData) {

    // TODO
    jlong err_code;

    jniUpdateJNIStruct(env, instance);

    err_code = sensorGetRealtimeData(env, instance, realtimeData);
    if (err_code != JNI_SUCCESS) {
        return err_code;
    }
    err_code = sensorGetStatisticsData(env, instance, sessionData, E_GET_STATISTICS_SESSION);
    if (err_code != JNI_SUCCESS) {
        return err_code;
    }

    err_code = sensorGetStatisticsData(env, instance, lapData, E_GET_STATISTICS_LAP);
    if (err_code != JNI_SUCCESS) {
        return err_code;
    }

    err_code = sensorGetStatisticsData(env, instance, lastLapData, E_GET_STATISTICS_LAST_LAP);
    if (err_code != JNI_SUCCESS) {
        return err_code;
    }

    return err_code;

}

JNIEXPORT jlong JNICALL
Java_com_meilancycling_mema_ble_sensor_JniBleController_sensorSetMode(JNIEnv *env, jobject thiz,
                                                                      jint mode) {
    // TODO: implement sensorSetMode()
    return pSensorCIIFun->setMode(mode);
}

