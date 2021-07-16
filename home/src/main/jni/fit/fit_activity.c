/***********************************************************************
*                               include files
************************************************************************
*/
#include "fit_activity.h"
#include "fit_activity_encode.h"
#include "../sensor/common/sensorDataDefine.h"
#include "../sensorCallback.h"
#include "../string.h"
#include "../stddef.h"

/***********************************************************************
*                               macro define
************************************************************************
*/

//file id
#define FIT_PRODUCT                                1//Managed by manufacturer
#define FIT_SERIAL_NUMBER                          20200820//Managed by manufacturer
#define FIT_PRODUCT_NAME                           "APP"
//file creator
#define FIT_SOFTWARE_VERSION                       100//1.00
#define FIT_HARDWARE_VERSION                       1

#define FIT_BIKE_COMPUTER_DEVCIE_TYPE              0

#define TWO_POWER_31                               0x80000000//2147483648=2^31
#define DEGREE_TO_SEMICIRCLES(degree)              ((degree) * (TWO_POWER_31 / 180 ))

//1989 12 31 00:00
#define FIT_START_TIMESTAMP                       0X259D4C00

//当地时间的时间戳
#define GET_LOCAL_TIMESTAMP()                     sensorGetLocalTime()
#define FIT_LOCAL_TIMESTAMP()                     (GET_LOCAL_TIMESTAMP() - FIT_START_TIMESTAMP)
//0时区的时间戳
#define GET_ZONE_0_TIMESTAMP()                    sensorGetTimestamp()
#define FIT_ZONE_0_TIMESTAMP()                    (GET_ZONE_0_TIMESTAMP() - FIT_START_TIMESTAMP)
//已知时间转换为fit时间
#define FIT_LOCAL_UTC_TO_0_TIMESTAMP(local_utc)   (sensorLocalTime2Timestamp(local_utc) - FIT_START_TIMESTAMP)

#define FIT_CALORIES_CONVERSE(cal)                ((cal) / 1000)

#define FIT_ALTITUDE_CONVERSE(altitude)           (((altitude) == SENSOR_INT32_INVALID_DATA) ? FIT_UINT16_INVALID : (((altitude) + 5000) / 2))//((altitudem + 500m) * 5 )
#define FIT_DISTANCE_CONVERSE(distance)           ((distance) * 100)
#define FIT_SPEED_CONVERSE(speed)                 (((speed) == SENSOR_UINT16_INVALID_DATA) ? 0 : ((speed) * 1000 / 36))//((speed * 100 m/h ) * 1000 / 3600)
#define FIT_POWER_CONVERSE(power)                 (((power) == SENSOR_UINT16_INVALID_DATA) ? 0 : power)
#define FIT_HRM_CONVERSE(hrm)                     (((hrm) == SENSOR_UINT16_INVALID_DATA) ? 0 : hrm)
#define FIT_CAD_CONVERSE(cad)                     (((cad) == SENSOR_UINT16_INVALID_DATA) ? 0 : cad)

#define FIT_TEMPERTURE_CONVERSE(tempterture)      ((tempterture) / 100) //1C
// 100 * % + 0, grade -->0.1%
#define FIT_GRADE_CONVERS(grade)                  ((grade) * 10)
#define FIT_TIME_CONVERSE(time)                   ((time) * 1000)

#define CHECK_GPS_VALID(long, lati)               ((long!=SENSOR_INT32_INVALID_DATA)&&(lati!=SENSOR_INT32_INVALID_DATA))

/***********************************************************************
*                               my type
************************************************************************
*/
typedef void (*get_fit_file_mesg_t)(void *p_mesg, void *p_arg);

/***********************************************************************
*                               extern variable
************************************************************************
*/

/***********************************************************************
*                               extern function
************************************************************************
*/

/***********************************************************************
*                               global variable
************************************************************************
*/

/***********************************************************************
*                               global function
************************************************************************
*/

/***********************************************************************
*                               static variable
************************************************************************
*/
//file id info
static const FIT_FILE_ID_MESG_DEF fit_file_id_mesg_def =
        {
                0, // reserved_1
                FIT_ARCH_ENDIAN, // arch
                FIT_MESG_NUM_FILE_ID, // global_mesg_num
                7, // num_fields
                { // field_def_num, size, base_type
                        FIT_FILE_ID_FIELD_NUM_SERIAL_NUMBER, (sizeof(FIT_UINT32Z) * 1),
                        FIT_BASE_TYPE_UINT32Z,
                        FIT_FILE_ID_FIELD_NUM_TIME_CREATED, (sizeof(FIT_DATE_TIME) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_FILE_ID_FIELD_NUM_PRODUCT_NAME,
                        (sizeof(FIT_STRING) * FIT_FILE_ID_MESG_PRODUCT_NAME_COUNT),
                        FIT_BASE_TYPE_STRING,
                        FIT_FILE_ID_FIELD_NUM_MANUFACTURER, (sizeof(FIT_MANUFACTURER) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_FILE_ID_FIELD_NUM_PRODUCT, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_FILE_ID_FIELD_NUM_NUMBER, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_FILE_ID_FIELD_NUM_TYPE, (sizeof(FIT_FILE) * 1), FIT_BASE_TYPE_ENUM,
                }
        };

//file creator
static const FIT_FILE_CREATOR_MESG_DEF fit_file_creator_mesg_def =
        {
                0, // reserved_1
                FIT_ARCH_ENDIAN, // arch
                FIT_MESG_NUM_FILE_CREATOR, // global_mesg_num
                2, // num_fields
                { // field_def_num, size, base_type
                        FIT_FILE_CREATOR_FIELD_NUM_SOFTWARE_VERSION, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_FILE_CREATOR_FIELD_NUM_HARDWARE_VERSION, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
                }
        };


//device_info
static const FIT_DEVICE_INFO_MESG_DEF fit_device_info_mesg_def =
        {
                0, // reserved_1
                FIT_ARCH_ENDIAN, // arch
                FIT_MESG_NUM_DEVICE_INFO, // global_mesg_num
                12, // num_fields
                { // field_def_num, size, base_type
                        FIT_DEVICE_INFO_FIELD_NUM_TIMESTAMP, (sizeof(FIT_DATE_TIME) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_DEVICE_INFO_FIELD_NUM_SERIAL_NUMBER, (sizeof(FIT_UINT32Z) * 1),
                        FIT_BASE_TYPE_UINT32Z,
//      FIT_DEVICE_INFO_FIELD_NUM_CUM_OPERATING_TIME, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
                        FIT_DEVICE_INFO_FIELD_NUM_PRODUCT_NAME,
                        (sizeof(FIT_STRING) * FIT_DEVICE_INFO_MESG_PRODUCT_NAME_COUNT),
                        FIT_BASE_TYPE_STRING,
                        FIT_DEVICE_INFO_FIELD_NUM_MANUFACTURER, (sizeof(FIT_MANUFACTURER) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_DEVICE_INFO_FIELD_NUM_PRODUCT, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_DEVICE_INFO_FIELD_NUM_SOFTWARE_VERSION, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_DEVICE_INFO_FIELD_NUM_BATTERY_VOLTAGE, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
//      FIT_DEVICE_INFO_FIELD_NUM_ANT_DEVICE_NUMBER, (sizeof(FIT_UINT16Z)*1), FIT_BASE_TYPE_UINT16Z,
                        FIT_DEVICE_INFO_FIELD_NUM_DEVICE_INDEX, (sizeof(FIT_DEVICE_INDEX) * 1),
                        FIT_BASE_TYPE_UINT8,
                        FIT_DEVICE_INFO_FIELD_NUM_DEVICE_TYPE, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
                        FIT_DEVICE_INFO_FIELD_NUM_HARDWARE_VERSION, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
                        FIT_DEVICE_INFO_FIELD_NUM_BATTERY_STATUS, (sizeof(FIT_BATTERY_STATUS) * 1),
                        FIT_BASE_TYPE_UINT8,
//      FIT_DEVICE_INFO_FIELD_NUM_SENSOR_POSITION, (sizeof(FIT_BODY_LOCATION)*1), FIT_BASE_TYPE_ENUM,
//      FIT_DEVICE_INFO_FIELD_NUM_DESCRIPTOR, (sizeof(FIT_STRING)*1), FIT_BASE_TYPE_STRING,
//      FIT_DEVICE_INFO_FIELD_NUM_ANT_TRANSMISSION_TYPE, (sizeof(FIT_UINT8Z)*1), FIT_BASE_TYPE_UINT8Z,
//      FIT_DEVICE_INFO_FIELD_NUM_ANT_NETWORK, (sizeof(FIT_ANT_NETWORK)*1), FIT_BASE_TYPE_ENUM,
                        FIT_DEVICE_INFO_FIELD_NUM_SOURCE_TYPE, (sizeof(FIT_SOURCE_TYPE) * 1),
                        FIT_BASE_TYPE_ENUM,
                }
        };

//event
static const FIT_EVENT_MESG_DEF fit_event_mesg_def =
        {
                0, // reserved_1
                FIT_ARCH_ENDIAN, // arch
                FIT_MESG_NUM_EVENT, // global_mesg_num
                4, // num_fields
                { // field_def_num, size, base_type
                        FIT_EVENT_FIELD_NUM_TIMESTAMP, (sizeof(FIT_DATE_TIME) * 1),
                        FIT_BASE_TYPE_UINT32,
//      FIT_EVENT_FIELD_NUM_DATA, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_EVENT_FIELD_NUM_DATA16, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_EVENT_FIELD_NUM_SCORE, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_EVENT_FIELD_NUM_OPPONENT_SCORE, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
                        FIT_EVENT_FIELD_NUM_EVENT, (sizeof(FIT_EVENT) * 1), FIT_BASE_TYPE_ENUM,
                        FIT_EVENT_FIELD_NUM_EVENT_TYPE, (sizeof(FIT_EVENT_TYPE) * 1),
                        FIT_BASE_TYPE_ENUM,
                        FIT_EVENT_FIELD_NUM_EVENT_GROUP, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
//      FIT_EVENT_FIELD_NUM_FRONT_GEAR_NUM, (sizeof(FIT_UINT8Z)*1), FIT_BASE_TYPE_UINT8Z,
//      FIT_EVENT_FIELD_NUM_FRONT_GEAR, (sizeof(FIT_UINT8Z)*1), FIT_BASE_TYPE_UINT8Z,
//      FIT_EVENT_FIELD_NUM_REAR_GEAR_NUM, (sizeof(FIT_UINT8Z)*1), FIT_BASE_TYPE_UINT8Z,
//      FIT_EVENT_FIELD_NUM_REAR_GEAR, (sizeof(FIT_UINT8Z)*1), FIT_BASE_TYPE_UINT8Z,
                }
        };

//record 
static const FIT_RECORD_MESG_DEF fit_record_mesg_def =
        {
                0, // reserved_1
                FIT_ARCH_ENDIAN, // arch
                FIT_MESG_NUM_RECORD, // global_mesg_num
                11, // num_fields
                { // field_def_num, size, base_type
                        FIT_RECORD_FIELD_NUM_TIMESTAMP, (sizeof(FIT_DATE_TIME) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_RECORD_FIELD_NUM_POSITION_LAT, (sizeof(FIT_SINT32) * 1),
                        FIT_BASE_TYPE_SINT32,
                        FIT_RECORD_FIELD_NUM_POSITION_LONG, (sizeof(FIT_SINT32) * 1),
                        FIT_BASE_TYPE_SINT32,
                        FIT_RECORD_FIELD_NUM_DISTANCE, (sizeof(FIT_UINT32) * 1),
                        FIT_BASE_TYPE_UINT32,
//      FIT_RECORD_FIELD_NUM_TIME_FROM_COURSE, (sizeof(FIT_SINT32)*1), FIT_BASE_TYPE_SINT32,
//      FIT_RECORD_FIELD_NUM_TOTAL_CYCLES, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_RECORD_FIELD_NUM_ACCUMULATED_POWER, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_RECORD_FIELD_NUM_ENHANCED_SPEED, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_RECORD_FIELD_NUM_ENHANCED_ALTITUDE, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
                        FIT_RECORD_FIELD_NUM_ALTITUDE, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_RECORD_FIELD_NUM_SPEED, (sizeof(FIT_UINT16) * 1), FIT_BASE_TYPE_UINT16,
                        FIT_RECORD_FIELD_NUM_POWER, (sizeof(FIT_UINT16) * 1), FIT_BASE_TYPE_UINT16,
                        FIT_RECORD_FIELD_NUM_GRADE, (sizeof(FIT_SINT16) * 1), FIT_BASE_TYPE_SINT16,
//      FIT_RECORD_FIELD_NUM_COMPRESSED_ACCUMULATED_POWER, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_RECORD_FIELD_NUM_VERTICAL_SPEED, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_RECORD_FIELD_NUM_CALORIES, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_RECORD_FIELD_NUM_VERTICAL_OSCILLATION, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_RECORD_FIELD_NUM_STANCE_TIME_PERCENT, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_RECORD_FIELD_NUM_STANCE_TIME, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_RECORD_FIELD_NUM_BALL_SPEED, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_RECORD_FIELD_NUM_CADENCE256, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_RECORD_FIELD_NUM_TOTAL_HEMOGLOBIN_CONC, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_RECORD_FIELD_NUM_TOTAL_HEMOGLOBIN_CONC_MIN, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_RECORD_FIELD_NUM_TOTAL_HEMOGLOBIN_CONC_MAX, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_RECORD_FIELD_NUM_SATURATED_HEMOGLOBIN_PERCENT, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_RECORD_FIELD_NUM_SATURATED_HEMOGLOBIN_PERCENT_MIN, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_RECORD_FIELD_NUM_SATURATED_HEMOGLOBIN_PERCENT_MAX, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
                        FIT_RECORD_FIELD_NUM_HEART_RATE, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
                        FIT_RECORD_FIELD_NUM_CADENCE, (sizeof(FIT_UINT8) * 1), FIT_BASE_TYPE_UINT8,
//      FIT_RECORD_FIELD_NUM_COMPRESSED_SPEED_DISTANCE, (sizeof(FIT_BYTE)*3), FIT_BASE_TYPE_BYTE,
//      FIT_RECORD_FIELD_NUM_RESISTANCE, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//      FIT_RECORD_FIELD_NUM_CYCLE_LENGTH, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
                        FIT_RECORD_FIELD_NUM_TEMPERATURE, (sizeof(FIT_SINT8) * 1),
                        FIT_BASE_TYPE_SINT8,
//      FIT_RECORD_FIELD_NUM_SPEED_1S, (sizeof(FIT_UINT8)*5), FIT_BASE_TYPE_UINT8,
//      FIT_RECORD_FIELD_NUM_CYCLES, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//      FIT_RECORD_FIELD_NUM_LEFT_RIGHT_BALANCE, (sizeof(FIT_LEFT_RIGHT_BALANCE)*1), FIT_BASE_TYPE_UINT8,
//      FIT_RECORD_FIELD_NUM_GPS_ACCURACY, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//      FIT_RECORD_FIELD_NUM_ACTIVITY_TYPE, (sizeof(FIT_ACTIVITY_TYPE)*1), FIT_BASE_TYPE_ENUM,
//       FIT_RECORD_FIELD_NUM_LEFT_TORQUE_EFFECTIVENESS, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//       FIT_RECORD_FIELD_NUM_RIGHT_TORQUE_EFFECTIVENESS, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//       FIT_RECORD_FIELD_NUM_LEFT_PEDAL_SMOOTHNESS, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//       FIT_RECORD_FIELD_NUM_RIGHT_PEDAL_SMOOTHNESS, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//       FIT_RECORD_FIELD_NUM_COMBINED_PEDAL_SMOOTHNESS, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//       FIT_RECORD_FIELD_NUM_TIME128, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//       FIT_RECORD_FIELD_NUM_STROKE_TYPE, (sizeof(FIT_STROKE_TYPE)*1), FIT_BASE_TYPE_ENUM,
//       FIT_RECORD_FIELD_NUM_ZONE, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//       FIT_RECORD_FIELD_NUM_FRACTIONAL_CADENCE, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//       FIT_RECORD_FIELD_NUM_DEVICE_INDEX, (sizeof(FIT_DEVICE_INDEX)*1), FIT_BASE_TYPE_UINT8,
                }
        };
//lap
static const FIT_LAP_MESG_DEF fit_lap_mesg_def =
        {
                0, // reserved_1
                FIT_ARCH_ENDIAN, // arch
                FIT_MESG_NUM_LAP, // global_mesg_num
                36, // num_fields
                { // field_def_num, size, base_type
                        FIT_LAP_FIELD_NUM_TIMESTAMP, (sizeof(FIT_DATE_TIME) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_LAP_FIELD_NUM_START_TIME, (sizeof(FIT_DATE_TIME) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_LAP_FIELD_NUM_START_POSITION_LAT, (sizeof(FIT_SINT32) * 1),
                        FIT_BASE_TYPE_SINT32,
                        FIT_LAP_FIELD_NUM_START_POSITION_LONG, (sizeof(FIT_SINT32) * 1),
                        FIT_BASE_TYPE_SINT32,
                        FIT_LAP_FIELD_NUM_END_POSITION_LAT, (sizeof(FIT_SINT32) * 1),
                        FIT_BASE_TYPE_SINT32,
                        FIT_LAP_FIELD_NUM_END_POSITION_LONG, (sizeof(FIT_SINT32) * 1),
                        FIT_BASE_TYPE_SINT32,
                        FIT_LAP_FIELD_NUM_TOTAL_ELAPSED_TIME, (sizeof(FIT_UINT32) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_LAP_FIELD_NUM_TOTAL_TIMER_TIME, (sizeof(FIT_UINT32) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_LAP_FIELD_NUM_TOTAL_DISTANCE, (sizeof(FIT_UINT32) * 1),
                        FIT_BASE_TYPE_UINT32,
//      FIT_LAP_FIELD_NUM_TOTAL_CYCLES, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_LAP_FIELD_NUM_TOTAL_WORK, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_LAP_FIELD_NUM_TOTAL_MOVING_TIME, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
                        FIT_LAP_FIELD_NUM_TIME_IN_HR_ZONE,
                        (sizeof(FIT_UINT32) * FIT_LAP_MESG_TIME_IN_HR_ZONE_COUNT),
                        FIT_BASE_TYPE_UINT32,
//      FIT_LAP_FIELD_NUM_TIME_IN_SPEED_ZONE, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_LAP_FIELD_NUM_TIME_IN_CADENCE_ZONE, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
                        FIT_LAP_FIELD_NUM_TIME_IN_POWER_ZONE,
                        (sizeof(FIT_UINT32) * FIT_LAP_MESG_TIME_IN_POWER_ZONE_COUNT),
                        FIT_BASE_TYPE_UINT32,
//      FIT_LAP_FIELD_NUM_ENHANCED_AVG_SPEED, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_LAP_FIELD_NUM_ENHANCED_MAX_SPEED, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_LAP_FIELD_NUM_ENHANCED_AVG_ALTITUDE, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_LAP_FIELD_NUM_ENHANCED_MIN_ALTITUDE, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_LAP_FIELD_NUM_ENHANCED_MAX_ALTITUDE, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
                        FIT_LAP_FIELD_NUM_MESSAGE_INDEX, (sizeof(FIT_MESSAGE_INDEX) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_LAP_FIELD_NUM_TOTAL_CALORIES, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_TOTAL_FAT_CALORIES, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
                        FIT_LAP_FIELD_NUM_AVG_SPEED, (sizeof(FIT_UINT16) * 1), FIT_BASE_TYPE_UINT16,
                        FIT_LAP_FIELD_NUM_MAX_SPEED, (sizeof(FIT_UINT16) * 1), FIT_BASE_TYPE_UINT16,
                        FIT_LAP_FIELD_NUM_AVG_POWER, (sizeof(FIT_UINT16) * 1), FIT_BASE_TYPE_UINT16,
                        FIT_LAP_FIELD_NUM_MAX_POWER, (sizeof(FIT_UINT16) * 1), FIT_BASE_TYPE_UINT16,
                        FIT_LAP_FIELD_NUM_TOTAL_ASCENT, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_LAP_FIELD_NUM_TOTAL_DESCENT, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_NUM_LENGTHS, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_NORMALIZED_POWER, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_LEFT_RIGHT_BALANCE, (sizeof(FIT_LEFT_RIGHT_BALANCE_100)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_FIRST_LENGTH_INDEX, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_AVG_STROKE_DISTANCE, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_NUM_ACTIVE_LENGTHS, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
                        FIT_LAP_FIELD_NUM_AVG_ALTITUDE, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_LAP_FIELD_NUM_MAX_ALTITUDE, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_LAP_FIELD_NUM_AVG_GRADE, (sizeof(FIT_SINT16) * 1), FIT_BASE_TYPE_SINT16,
//      FIT_LAP_FIELD_NUM_AVG_POS_GRADE, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_LAP_FIELD_NUM_AVG_NEG_GRADE, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_LAP_FIELD_NUM_MAX_POS_GRADE, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_LAP_FIELD_NUM_MAX_NEG_GRADE, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_LAP_FIELD_NUM_AVG_POS_VERTICAL_SPEED, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_LAP_FIELD_NUM_AVG_NEG_VERTICAL_SPEED, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_LAP_FIELD_NUM_MAX_POS_VERTICAL_SPEED, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_LAP_FIELD_NUM_MAX_NEG_VERTICAL_SPEED, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_LAP_FIELD_NUM_REPETITION_NUM, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
                        FIT_LAP_FIELD_NUM_MIN_ALTITUDE, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_WKT_STEP_INDEX, (sizeof(FIT_MESSAGE_INDEX)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_OPPONENT_SCORE, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_STROKE_COUNT, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_ZONE_COUNT, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_AVG_VERTICAL_OSCILLATION, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_AVG_STANCE_TIME_PERCENT, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_AVG_STANCE_TIME, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_PLAYER_SCORE, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_AVG_TOTAL_HEMOGLOBIN_CONC, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_MIN_TOTAL_HEMOGLOBIN_CONC, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_MAX_TOTAL_HEMOGLOBIN_CONC, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_AVG_SATURATED_HEMOGLOBIN_PERCENT, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_MIN_SATURATED_HEMOGLOBIN_PERCENT, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_MAX_SATURATED_HEMOGLOBIN_PERCENT, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_LAP_FIELD_NUM_AVG_VAM, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
                        FIT_LAP_FIELD_NUM_EVENT, (sizeof(FIT_EVENT) * 1), FIT_BASE_TYPE_ENUM,
                        FIT_LAP_FIELD_NUM_EVENT_TYPE, (sizeof(FIT_EVENT_TYPE) * 1),
                        FIT_BASE_TYPE_ENUM,
                        FIT_LAP_FIELD_NUM_AVG_HEART_RATE, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
                        FIT_LAP_FIELD_NUM_MAX_HEART_RATE, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
                        FIT_LAP_FIELD_NUM_AVG_CADENCE, (sizeof(FIT_UINT8) * 1), FIT_BASE_TYPE_UINT8,
                        FIT_LAP_FIELD_NUM_MAX_CADENCE, (sizeof(FIT_UINT8) * 1), FIT_BASE_TYPE_UINT8,
//      FIT_LAP_FIELD_NUM_INTENSITY, (sizeof(FIT_INTENSITY)*1), FIT_BASE_TYPE_ENUM,
                        FIT_LAP_FIELD_NUM_LAP_TRIGGER, (sizeof(FIT_LAP_TRIGGER) * 1),
                        FIT_BASE_TYPE_ENUM,
                        FIT_LAP_FIELD_NUM_SPORT, (sizeof(FIT_SPORT) * 1), FIT_BASE_TYPE_ENUM,
                        FIT_LAP_FIELD_NUM_EVENT_GROUP, (sizeof(FIT_UINT8) * 1), FIT_BASE_TYPE_UINT8,
//      FIT_LAP_FIELD_NUM_SWIM_STROKE, (sizeof(FIT_SWIM_STROKE)*1), FIT_BASE_TYPE_ENUM,
                        FIT_LAP_FIELD_NUM_SUB_SPORT, (sizeof(FIT_SUB_SPORT) * 1),
                        FIT_BASE_TYPE_ENUM,
//      FIT_LAP_FIELD_NUM_GPS_ACCURACY, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
                        FIT_LAP_FIELD_NUM_AVG_TEMPERATURE, (sizeof(FIT_SINT8) * 1),
                        FIT_BASE_TYPE_SINT8,
                        FIT_LAP_FIELD_NUM_MAX_TEMPERATURE, (sizeof(FIT_SINT8) * 1),
                        FIT_BASE_TYPE_SINT8,
                        FIT_LAP_FIELD_NUM_MIN_HEART_RATE, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
//      FIT_LAP_FIELD_NUM_AVG_FRACTIONAL_CADENCE, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//      FIT_LAP_FIELD_NUM_MAX_FRACTIONAL_CADENCE, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//      FIT_LAP_FIELD_NUM_TOTAL_FRACTIONAL_CYCLES, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
                }
        };


//session
static const FIT_SESSION_MESG_DEF fit_session_mesg_def =
        {
                0, // reserved_1
                FIT_ARCH_ENDIAN, // arch
                FIT_MESG_NUM_SESSION, // global_mesg_num
                36, // num_fields
                { // field_def_num, size, base_type
                        FIT_SESSION_FIELD_NUM_TIMESTAMP, (sizeof(FIT_DATE_TIME) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_SESSION_FIELD_NUM_START_TIME, (sizeof(FIT_DATE_TIME) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_SESSION_FIELD_NUM_START_POSITION_LAT, (sizeof(FIT_SINT32) * 1),
                        FIT_BASE_TYPE_SINT32,
                        FIT_SESSION_FIELD_NUM_START_POSITION_LONG, (sizeof(FIT_SINT32) * 1),
                        FIT_BASE_TYPE_SINT32,
                        FIT_SESSION_FIELD_NUM_TOTAL_ELAPSED_TIME, (sizeof(FIT_UINT32) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_SESSION_FIELD_NUM_TOTAL_TIMER_TIME, (sizeof(FIT_UINT32) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_SESSION_FIELD_NUM_TOTAL_DISTANCE, (sizeof(FIT_UINT32) * 1),
                        FIT_BASE_TYPE_UINT32,
//      FIT_SESSION_FIELD_NUM_TOTAL_CYCLES, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_SESSION_FIELD_NUM_NEC_LAT, (sizeof(FIT_SINT32)*1), FIT_BASE_TYPE_SINT32,
//      FIT_SESSION_FIELD_NUM_NEC_LONG, (sizeof(FIT_SINT32)*1), FIT_BASE_TYPE_SINT32,
//      FIT_SESSION_FIELD_NUM_SWC_LAT, (sizeof(FIT_SINT32)*1), FIT_BASE_TYPE_SINT32,
//      FIT_SESSION_FIELD_NUM_SWC_LONG, (sizeof(FIT_SINT32)*1), FIT_BASE_TYPE_SINT32,
//      FIT_SESSION_FIELD_NUM_AVG_STROKE_COUNT, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_SESSION_FIELD_NUM_TOTAL_WORK, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_SESSION_FIELD_NUM_TOTAL_MOVING_TIME, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
                        FIT_SESSION_FIELD_NUM_TIME_IN_HR_ZONE,
                        (sizeof(FIT_UINT32) * FIT_SESSION_MESG_TIME_IN_HR_ZONE_COUNT),
                        FIT_BASE_TYPE_UINT32,
//      FIT_SESSION_FIELD_NUM_TIME_IN_SPEED_ZONE, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_SESSION_FIELD_NUM_TIME_IN_CADENCE_ZONE, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
                        FIT_SESSION_FIELD_NUM_TIME_IN_POWER_ZONE,
                        (sizeof(FIT_UINT32) * FIT_SESSION_MESG_TIME_IN_POWER_ZONE_COUNT),
                        FIT_BASE_TYPE_UINT32,
//      FIT_SESSION_FIELD_NUM_AVG_LAP_TIME, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_SESSION_FIELD_NUM_ENHANCED_AVG_SPEED, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_SESSION_FIELD_NUM_ENHANCED_MAX_SPEED, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_SESSION_FIELD_NUM_ENHANCED_AVG_ALTITUDE, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_SESSION_FIELD_NUM_ENHANCED_MIN_ALTITUDE, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
//      FIT_SESSION_FIELD_NUM_ENHANCED_MAX_ALTITUDE, (sizeof(FIT_UINT32)*1), FIT_BASE_TYPE_UINT32,
                        FIT_SESSION_FIELD_NUM_MESSAGE_INDEX, (sizeof(FIT_MESSAGE_INDEX) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_TOTAL_CALORIES, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_TOTAL_FAT_CALORIES, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_AVG_SPEED, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_MAX_SPEED, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_AVG_POWER, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_MAX_POWER, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_TOTAL_ASCENT, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_TOTAL_DESCENT, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_FIRST_LAP_INDEX, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_NUM_LAPS, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_NORMALIZED_POWER, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_TRAINING_STRESS_SCORE, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_INTENSITY_FACTOR, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_LEFT_RIGHT_BALANCE, (sizeof(FIT_LEFT_RIGHT_BALANCE_100)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_AVG_STROKE_DISTANCE, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_POOL_LENGTH, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_THRESHOLD_POWER, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_NUM_ACTIVE_LENGTHS, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_AVG_ALTITUDE, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_MAX_ALTITUDE, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_AVG_GRADE, (sizeof(FIT_SINT16) * 1),
                        FIT_BASE_TYPE_SINT16,
//      FIT_SESSION_FIELD_NUM_AVG_POS_GRADE, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_SESSION_FIELD_NUM_AVG_NEG_GRADE, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_SESSION_FIELD_NUM_MAX_POS_GRADE, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_SESSION_FIELD_NUM_MAX_NEG_GRADE, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_SESSION_FIELD_NUM_AVG_POS_VERTICAL_SPEED, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_SESSION_FIELD_NUM_AVG_NEG_VERTICAL_SPEED, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_SESSION_FIELD_NUM_MAX_POS_VERTICAL_SPEED, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_SESSION_FIELD_NUM_MAX_NEG_VERTICAL_SPEED, (sizeof(FIT_SINT16)*1), FIT_BASE_TYPE_SINT16,
//      FIT_SESSION_FIELD_NUM_BEST_LAP_INDEX, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_MIN_ALTITUDE, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_PLAYER_SCORE, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_OPPONENT_SCORE, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_STROKE_COUNT, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_ZONE_COUNT, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_MAX_BALL_SPEED, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_AVG_BALL_SPEED, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_AVG_VERTICAL_OSCILLATION, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_AVG_STANCE_TIME_PERCENT, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_AVG_STANCE_TIME, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
//      FIT_SESSION_FIELD_NUM_AVG_VAM, (sizeof(FIT_UINT16)*1), FIT_BASE_TYPE_UINT16,
                        FIT_SESSION_FIELD_NUM_EVENT, (sizeof(FIT_EVENT) * 1), FIT_BASE_TYPE_ENUM,
                        FIT_SESSION_FIELD_NUM_EVENT_TYPE, (sizeof(FIT_EVENT_TYPE) * 1),
                        FIT_BASE_TYPE_ENUM,
                        FIT_SESSION_FIELD_NUM_SPORT, (sizeof(FIT_SPORT) * 1), FIT_BASE_TYPE_ENUM,
                        FIT_SESSION_FIELD_NUM_SUB_SPORT, (sizeof(FIT_SUB_SPORT) * 1),
                        FIT_BASE_TYPE_ENUM,
                        FIT_SESSION_FIELD_NUM_AVG_HEART_RATE, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
                        FIT_SESSION_FIELD_NUM_MAX_HEART_RATE, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
                        FIT_SESSION_FIELD_NUM_AVG_CADENCE, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
                        FIT_SESSION_FIELD_NUM_MAX_CADENCE, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
//      FIT_SESSION_FIELD_NUM_TOTAL_TRAINING_EFFECT, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
                        FIT_SESSION_FIELD_NUM_EVENT_GROUP, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
                        FIT_SESSION_FIELD_NUM_TRIGGER, (sizeof(FIT_SESSION_TRIGGER) * 1),
                        FIT_BASE_TYPE_ENUM,
//      FIT_SESSION_FIELD_NUM_SWIM_STROKE, (sizeof(FIT_SWIM_STROKE)*1), FIT_BASE_TYPE_ENUM,
//      FIT_SESSION_FIELD_NUM_POOL_LENGTH_UNIT, (sizeof(FIT_DISPLAY_MEASURE)*1), FIT_BASE_TYPE_ENUM,
//      FIT_SESSION_FIELD_NUM_GPS_ACCURACY, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
                        FIT_SESSION_FIELD_NUM_AVG_TEMPERATURE, (sizeof(FIT_SINT8) * 1),
                        FIT_BASE_TYPE_SINT8,
                        FIT_SESSION_FIELD_NUM_MAX_TEMPERATURE, (sizeof(FIT_SINT8) * 1),
                        FIT_BASE_TYPE_SINT8,
                        FIT_SESSION_FIELD_NUM_MIN_HEART_RATE, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
//      FIT_SESSION_FIELD_NUM_OPPONENT_NAME, (sizeof(FIT_STRING)*1), FIT_BASE_TYPE_STRING,
//      FIT_SESSION_FIELD_NUM_AVG_FRACTIONAL_CADENCE, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//      FIT_SESSION_FIELD_NUM_MAX_FRACTIONAL_CADENCE, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//      FIT_SESSION_FIELD_NUM_TOTAL_FRACTIONAL_CYCLES, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//      FIT_SESSION_FIELD_NUM_SPORT_INDEX, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
//      FIT_SESSION_FIELD_NUM_TOTAL_ANAEROBIC_TRAINING_EFFECT, (sizeof(FIT_UINT8)*1), FIT_BASE_TYPE_UINT8,
                }
        };

//activity
static const FIT_ACTIVITY_MESG_DEF fit_activity_mesg_def =
        {
                0, // reserved_1
                FIT_ARCH_ENDIAN, // arch
                FIT_MESG_NUM_ACTIVITY, // global_mesg_num
                8, // num_fields
                { // field_def_num, size, base_type
                        FIT_ACTIVITY_FIELD_NUM_TIMESTAMP, (sizeof(FIT_DATE_TIME) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_ACTIVITY_FIELD_NUM_TOTAL_TIMER_TIME, (sizeof(FIT_UINT32) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_ACTIVITY_FIELD_NUM_LOCAL_TIMESTAMP, (sizeof(FIT_LOCAL_DATE_TIME) * 1),
                        FIT_BASE_TYPE_UINT32,
                        FIT_ACTIVITY_FIELD_NUM_NUM_SESSIONS, (sizeof(FIT_UINT16) * 1),
                        FIT_BASE_TYPE_UINT16,
                        FIT_ACTIVITY_FIELD_NUM_TYPE, (sizeof(FIT_ACTIVITY) * 1), FIT_BASE_TYPE_ENUM,
                        FIT_ACTIVITY_FIELD_NUM_EVENT, (sizeof(FIT_EVENT) * 1), FIT_BASE_TYPE_ENUM,
                        FIT_ACTIVITY_FIELD_NUM_EVENT_TYPE, (sizeof(FIT_EVENT_TYPE) * 1),
                        FIT_BASE_TYPE_ENUM,
                        FIT_ACTIVITY_FIELD_NUM_EVENT_GROUP, (sizeof(FIT_UINT8) * 1),
                        FIT_BASE_TYPE_UINT8,
                }
        };


const FIT_CONST_MESG_DEF_PTR fit_mesg_defs[] =
        {
                (FIT_CONST_MESG_DEF_PTR) &fit_file_id_mesg_def,
                (FIT_CONST_MESG_DEF_PTR) &fit_file_creator_mesg_def,
                (FIT_CONST_MESG_DEF_PTR) &fit_device_info_mesg_def,
                (FIT_CONST_MESG_DEF_PTR) &fit_event_mesg_def,

                (FIT_CONST_MESG_DEF_PTR) &fit_record_mesg_def,
                (FIT_CONST_MESG_DEF_PTR) &fit_lap_mesg_def,
                (FIT_CONST_MESG_DEF_PTR) &fit_session_mesg_def,
                (FIT_CONST_MESG_DEF_PTR) &fit_activity_mesg_def,
        };

/***********************************************************************
*                               static function
************************************************************************
*/
static void get_fit_file_id_message(void *p_mesg, void *p_arg);

static void get_fit_file_creator_mesg(void *p_mesg, void *p_arg);

static void get_fit_file_device_info_mesg_bike_computer(void *p_mesg, void *p_arg);

static void get_fit_file_event_mesg(void *p_mesg, void *p_arg);

static void get_fit_file_record_mesg(void *p_mesg, void *p_arg);

static void get_fit_file_lap_mesg(void *p_mesg, void *p_arg);

static void get_fit_file_session_mesg(void *p_mesg, void *p_arg);

static void get_fit_file_activity_mesg(void *p_mesg, void *p_arg);


static get_fit_file_mesg_t get_fit_file_mesg[FIT_MESGS] =
        {
                get_fit_file_id_message,
                get_fit_file_creator_mesg,
                get_fit_file_device_info_mesg_bike_computer,
                get_fit_file_event_mesg,
                get_fit_file_record_mesg,
                get_fit_file_lap_mesg,
                get_fit_file_session_mesg,
                get_fit_file_activity_mesg,
        };
/***********************************************************************
*                                   end
************************************************************************
*/
/*************************************************************************   
**	function name:	 
**	description:	                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
static bool check_length_of_fit_data(const FIT_MESG_DEF *p_FIT_MESG_DEF, uint8_t check_def_length1,
                                     uint8_t check_length) {
    uint8_t length = 0;

    length = Fit_GetMesgSize(p_FIT_MESG_DEF->global_mesg_num);


    if (length == check_length) {
#ifdef __FIT_ACTIVITY_DEBUG__
        printf("length is right :%d[%d]\r\n", length, check_length);
#endif
    } else {
#ifdef __FIT_ACTIVITY_DEBUG__
        printf("length is error :%d[%d]\r\n", length, check_length);
#endif

        return false;
    }

    length = p_FIT_MESG_DEF->num_fields * 3 + 5;

    if (length != check_def_length1) {
#ifdef __FIT_ACTIVITY_DEBUG__
        printf("def length is error :%d[%d]\r\n", length, check_def_length1);
#endif
        return false;
    } else {
#ifdef __FIT_ACTIVITY_DEBUG__
        printf("def length is right :%d[%d]\r\n", length, check_def_length1);
#endif
    }

    return true;
}

/*************************************************************************   
**	function name:	 
**	description:	                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
FIT_UINT8 check_fit_data_length(void) {
#ifdef __FIT_ACTIVITY_DEBUG__
    printf("fit_file_id_mesg_def\r\n");
#endif

    if (!check_length_of_fit_data((const FIT_MESG_DEF *) &fit_file_id_mesg_def,
                                  FIT_FILE_ID_MESG_DEF_SIZE, FIT_FILE_ID_MESG_SIZE)) {
        return false;
    }

#ifdef __FIT_ACTIVITY_DEBUG__
    printf("fit_file_creator_mesg_def\r\n");
#endif
    if (!check_length_of_fit_data((const FIT_MESG_DEF *) &fit_file_creator_mesg_def,
                                  FIT_FILE_CREATOR_MESG_DEF_SIZE, FIT_FILE_CREATOR_MESG_SIZE)) {
        return false;
    }

#ifdef __FIT_ACTIVITY_DEBUG__
    printf("fit_activity_mesg_def\r\n");
#endif
    if (!check_length_of_fit_data((const FIT_MESG_DEF *) &fit_activity_mesg_def,
                                  FIT_ACTIVITY_MESG_DEF_SIZE, FIT_ACTIVITY_MESG_SIZE)) {
        return false;
    }


#ifdef __FIT_ACTIVITY_DEBUG__
    printf("fit_session_mesg_def\r\n");
#endif
    if (!check_length_of_fit_data((const FIT_MESG_DEF *) &fit_session_mesg_def,
                                  FIT_SESSION_MESG_DEF_SIZE, FIT_SESSION_MESG_SIZE)) {
        return false;
    }

#ifdef __FIT_ACTIVITY_DEBUG__
    printf("fit_lap_mesg_def\r\n");
#endif
    if (!check_length_of_fit_data((const FIT_MESG_DEF *) &fit_lap_mesg_def, FIT_LAP_MESG_DEF_SIZE,
                                  FIT_LAP_MESG_SIZE)) {
        return false;
    }

#ifdef __FIT_ACTIVITY_DEBUG__
    printf("fit_record_mesg_def\r\n");
#endif
    if (!check_length_of_fit_data((const FIT_MESG_DEF *) &fit_record_mesg_def,
                                  FIT_RECORD_MESG_DEF_SIZE, FIT_RECORD_MESG_SIZE)) {
        return false;
    }

#ifdef __FIT_ACTIVITY_DEBUG__
    printf("fit_event_mesg_def\r\n");
#endif
    if (!check_length_of_fit_data((const FIT_MESG_DEF *) &fit_event_mesg_def,
                                  FIT_EVENT_MESG_DEF_SIZE, FIT_EVENT_MESG_SIZE)) {
        return false;
    }

#ifdef __FIT_ACTIVITY_DEBUG__
    printf("fit_device_info_mesg_def\r\n");
#endif
    if (!check_length_of_fit_data((const FIT_MESG_DEF *) &fit_device_info_mesg_def,
                                  FIT_DEVICE_INFO_MESG_DEF_SIZE, FIT_DEVICE_INFO_MESG_SIZE)) {
        return false;
    }
    return true;
}
//=======================================================================
//                     fit 转换函数--start
//=======================================================================
/*************************************************************************   
**	function name:degree_to_semicircles
**	description:	                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
static int32_t degree_to_semicircles(int32_t degrees) {
    int64_t tmp_value;
    int32_t semicircles;

    if (degrees == SENSOR_INT32_INVALID_DATA) {
        return FIT_SINT32_INVALID;
    }

    tmp_value = (int64_t) degrees;
    tmp_value = DEGREE_TO_SEMICIRCLES(tmp_value);
    tmp_value = tmp_value / GPS_MAP_VALUE_TIMES;

    semicircles = (int32_t) tmp_value;//

    return semicircles;
}
//=======================================================================
//                     fit 转换函数--end
//=======================================================================


/*************************************************************************   
**	function name:get_fit_file_id_message	 
**	description:	                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
static void get_fit_file_id_message(void *p_mesg, void *p_arg) {
    FIT_FILE_ID_MESG *p;

    //将所有的值都赋值为invalid
    Fit_InitMesg((const FIT_MESG_DEF *) &fit_file_id_mesg_def, p_mesg);

    p = (FIT_FILE_ID_MESG *) p_mesg;

//    p->number         = 0;
    p->product = FIT_PRODUCT;
    p->serial_number = FIT_SERIAL_NUMBER;
    p->type = FIT_FILE_ACTIVITY;
    p->manufacturer = FIT_MANUFACTURER_INVALID;
    p->time_created = FIT_ZONE_0_TIMESTAMP();//0时区时间
    strcpy(p->product_name, FIT_PRODUCT_NAME);
}

/*************************************************************************   
**	function name:get_fit_file_creator_mesg	 
**	description:	                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
static void get_fit_file_creator_mesg(void *p_mesg, void *p_arg) {
    FIT_FILE_CREATOR_MESG *p;

    //将所有的值都赋值为invalid
    Fit_InitMesg((const FIT_MESG_DEF *) &fit_file_creator_mesg_def, p_mesg);

    p = (FIT_FILE_CREATOR_MESG *) p_mesg;

    p->software_version = FIT_SOFTWARE_VERSION;
    p->hardware_version = FIT_HARDWARE_VERSION;
}

/*************************************************************************   
**	function name:get_fit_file_device_info_mesg_bike_computer	 
**	description:	                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
static void get_fit_file_device_info_mesg_bike_computer(void *p_mesg, void *p_arg) {
    FIT_DEVICE_INFO_MESG *p;

    //将所有的值都赋值为invalid
    Fit_InitMesg((const FIT_MESG_DEF *) &fit_device_info_mesg_def, p_mesg);

    p = (FIT_DEVICE_INFO_MESG *) p_mesg;

    p->timestamp = FIT_ZONE_0_TIMESTAMP();//0时区时间
    p->serial_number = FIT_SERIAL_NUMBER;
    strcpy(p->product_name, FIT_PRODUCT_NAME);
    p->product = FIT_PRODUCT;
    p->software_version = FIT_SOFTWARE_VERSION;


    //从机的device index往下累加
    p->device_index = FIT_DEVICE_INDEX_CREATOR;
    p->device_type = FIT_BIKE_COMPUTER_DEVCIE_TYPE;
    p->hardware_version = FIT_HARDWARE_VERSION;

#if 0
    p->battery_voltage  = cb_get_volt();
    p->battery_voltage  = p->battery_voltage * 256 / 1000;//v * 256  4.2*256 = 1075
    if(cbm_get_status() != BATT_IDLE)
    {
        p->battery_status = FIT_BATTERY_STATUS_CHARGING;
    }
    else
    {
        if(batt_get_lvl() >= BATT_MT_EQ_4_5_LT_5_5)
        {
            p->battery_status = FIT_BATTERY_STATUS_GOOD;
        }
        else if(batt_get_lvl() >= BATT_MT_EQ_2_5_LT_3_5)
        {
            p->battery_status = FIT_BATTERY_STATUS_OK;
        }
        else 
        {
            p->battery_status = FIT_BATTERY_STATUS_LOW;
        }
    }

#else
#warning fit batt!!!!
    p->battery_status = FIT_BATTERY_STATUS_CHARGING;
    p->battery_voltage = 50;
    p->battery_voltage = p->battery_voltage * 256 / 1000;//v * 256  4.2*256 = 1075

#endif
    p->source_type = FIT_SOURCE_TYPE_BLUETOOTH_LOW_ENERGY;
}

/*************************************************************************   
**	function name:get_fit_file_event_mesg	 
**	description:	                  
**	input para:	  此函数在开始 暂停 结束时调用	 
**                  	
**	return:			                                       
**************************************************************************/
static void get_fit_file_event_mesg(void *p_mesg, void *p_arg) {
    FIT_EVENT_MESG *p;
    fit_activity_event_t event;


    //将所有的值都赋值为invalid
    Fit_InitMesg((const FIT_MESG_DEF *) &fit_event_mesg_def, p_mesg);

    p = (FIT_EVENT_MESG *) p_mesg;

    event = *(fit_activity_event_t *) p_arg;

    p->timestamp = FIT_ZONE_0_TIMESTAMP();//0时区时间

    switch (event) {
        case E_ACTIVITY_START:

            p->event = FIT_EVENT_TIMER;
            p->event_type = FIT_EVENT_TYPE_START;
            p->event_group = 0;
            break;

        case E_ACTIVITY_PAUSE:

            p->event = FIT_EVENT_LAP;
            p->event_type = FIT_EVENT_TYPE_STOP;
            break;

        case E_ACTIVITY_STOP_OLD_LAP:

            p->event = FIT_EVENT_LAP;
            p->event_type = FIT_EVENT_TYPE_MARKER;
            break;

        case E_ACTIVITY_START_NEW_LAP:

            p->event = FIT_EVENT_LAP;
            p->event_type = FIT_EVENT_TYPE_START;
            break;

        case E_ACTIVITY_STOP_ALL:

            p->event = FIT_EVENT_TIMER;
            p->event_type = FIT_EVENT_TYPE_STOP_ALL;
            break;

        default:
            break;
    }
}

/*************************************************************************   
**	function name:get_fit_file_record_mesg
**	description:	call it every 1sec                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
static void get_fit_file_record_mesg(void *p_mesg, void *p_arg) {
    FIT_RECORD_MESG *p;

    //将所有的值都赋值为invalid
    Fit_InitMesg((const FIT_MESG_DEF *) &fit_record_mesg_def, p_mesg);

    p = (FIT_RECORD_MESG *) p_mesg;

    p->timestamp = FIT_ZONE_0_TIMESTAMP();  //0时区时间   

    if (CHECK_GPS_VALID(sensor_value->realtime_data.longitude,
                        sensor_value->realtime_data.latitude)) {
        p->position_long = degree_to_semicircles(sensor_value->realtime_data.longitude);
        p->position_lat = degree_to_semicircles(sensor_value->realtime_data.latitude);
    }

    p->distance = FIT_DISTANCE_CONVERSE(sensor_value->session_cycling.dist_travelled);
//    p->accumulated_power = p_session->accumulated_power;
//    p->enhanced_speed    = FIT_SPEED_CONVERSE(sensor_value->realtime_data.cur_speed);
//    p->enhanced_altitude = FIT_ALTITUDE_CONVERSE(sensor_value->realtime_data.cur_altitude);
    p->altitude = FIT_ALTITUDE_CONVERSE(sensor_value->realtime_data.altitude);

    p->speed = FIT_SPEED_CONVERSE(sensor_value->realtime_data.speed);


    p->power = FIT_POWER_CONVERSE(sensor_value->realtime_data.power);

//    p->grade             = FIT_GRADE_CONVERS(sensor_value->realtime_data.slope);
//    p->calories          = p_session->calories;

    p->heart_rate = FIT_HRM_CONVERSE(sensor_value->realtime_data.hrm);
    p->cadence = FIT_CAD_CONVERSE(sensor_value->realtime_data.cadence);
    p->temperature = 0x7F;//FIT_TEMPERTURE_CONVERSE(sensor_value->realtime_data.temperature);
//    p->activity_type     = FIT_ACTIVITY_TYPE_CYCLING;
}

/*************************************************************************   
**	function name:get_fit_file_lap_mesg
**	description:	                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
static void get_fit_file_lap_mesg(void *p_mesg, void *p_arg) {
    uint8_t i;
    FIT_LAP_MESG *p;

    base_cycling_t *p_cycling;

    if ((p_mesg == NULL) || (p_arg == NULL)) {
        return;
    }

    //将所有的值都赋值为invalid
    Fit_InitMesg((const FIT_MESG_DEF *) &fit_lap_mesg_def, p_mesg);

    p = (FIT_LAP_MESG *) p_mesg;
    p_cycling = (base_cycling_t *) &sensor_value->lap_cycling;

    p->timestamp = FIT_ZONE_0_TIMESTAMP();//0时区时间
    p->start_time = FIT_LOCAL_UTC_TO_0_TIMESTAMP(p_cycling->start_utc);

    if (CHECK_GPS_VALID(p_cycling->start_longtitude, p_cycling->start_latitude)) {
        p->start_position_lat = degree_to_semicircles(p_cycling->start_latitude);
        p->start_position_long = degree_to_semicircles(p_cycling->start_longtitude);
    }
    //结束地点为现在分圈地点
    if (CHECK_GPS_VALID(p_cycling->end_longtitude, p_cycling->end_latitude)) {
        p->end_position_long = degree_to_semicircles(p_cycling->end_longtitude);
        p->end_position_lat = degree_to_semicircles(p_cycling->end_latitude);
    }

    //包含暂停时间
    p->total_elapsed_time = FIT_TIME_CONVERSE(p_cycling->elapsed_time);

    //不包含暂停时间
    p->total_timer_time = FIT_TIME_CONVERSE(p_cycling->activity_time);
    p->total_distance = FIT_DISTANCE_CONVERSE(p_cycling->dist_travelled);

    if (sensorParameters.hz.available) {
        for (i = 0; i < FIT_LAP_MESG_TIME_IN_HR_ZONE_COUNT; i++) {
            p->time_in_hr_zone[i] = FIT_TIME_CONVERSE(p_cycling->hrm_zone[i]);
        }
    }

    if (sensorParameters.pz.available) {
        //mofified by cc 20200311 time need multiple 1000
        for (i = 0; i < FIT_LAP_MESG_TIME_IN_POWER_ZONE_COUNT; i++) {
            p->time_in_power_zone[i] = FIT_TIME_CONVERSE(p_cycling->power_zone[i]);
        }
    }

//    p->message_index      = ;
    p->total_calories = FIT_CALORIES_CONVERSE(p_cycling->calories);
    p->avg_speed = FIT_SPEED_CONVERSE(p_cycling->avg_speed);
    p->max_speed = FIT_SPEED_CONVERSE(p_cycling->max_speed);
    p->avg_power = FIT_POWER_CONVERSE(p_cycling->avg_power);
    p->max_power = FIT_POWER_CONVERSE(p_cycling->max_power);
//    p->total_ascent = p_cycling->ascent;
//    p->total_descent = p_cycling->descent;
    p->avg_altitude = FIT_ALTITUDE_CONVERSE(p_cycling->avg_altitude);
    p->max_altitude = FIT_ALTITUDE_CONVERSE(p_cycling->max_altitude);
//    p->avg_grade = FIT_GRADE_CONVERS(p_cycling->avg_grade);
    p->min_altitude = FIT_ALTITUDE_CONVERSE(p_cycling->min_altitude);

    p->event = FIT_EVENT_LAP;
    p->event_type = FIT_EVENT_TYPE_STOP;
    p->avg_heart_rate = FIT_HRM_CONVERSE(p_cycling->avg_hrm);
    p->max_heart_rate = FIT_HRM_CONVERSE(p_cycling->max_hrm);
//    p->min_heart_rate     = (FIT_UINT8)p_cycling->min_hrm;
    p->avg_cadence = FIT_CAD_CONVERSE(p_cycling->avg_cadence);
    p->max_cadence = FIT_CAD_CONVERSE(p_cycling->max_cadence);
    p->lap_trigger = FIT_LAP_TRIGGER_MANUAL;
    p->sport = FIT_SPORT_CYCLING;
//    p->event_group        =;
    p->sub_sport = FIT_SUB_SPORT_GENERIC;
    p->avg_temperature = 0x7F;//FIT_TEMPERTURE_CONVERSE(p_cycling->avg_temperture);
    p->max_temperature = 0x7F;//FIT_TEMPERTURE_CONVERSE(p_cycling->max_temperture);
}

/*************************************************************************   
**	function name:get_fit_file_session_mesg
**	description:	                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
static void get_fit_file_session_mesg(void *p_mesg, void *p_arg) {
    uint8_t i;
    FIT_SESSION_MESG *p;
    base_cycling_t *p_session;

    if ((p_mesg == NULL) || (p_arg == NULL)) {
        return;
    }

    //将所有的值都赋值为invalid
    Fit_InitMesg((const FIT_MESG_DEF *) &fit_session_mesg_def, p_mesg);

    p = (FIT_SESSION_MESG *) p_mesg;
    p_session = (base_cycling_t *) &sensor_value->session_cycling;//p_arg;

    p->timestamp = FIT_ZONE_0_TIMESTAMP();

    ////0时区时间
    p->start_time = FIT_LOCAL_UTC_TO_0_TIMESTAMP(p_session->start_utc);

    if (CHECK_GPS_VALID(p_session->start_longtitude, p_session->start_latitude)) {
        p->start_position_lat = degree_to_semicircles(p_session->start_latitude);
        p->start_position_long = degree_to_semicircles(p_session->start_longtitude);
    }

    p->total_elapsed_time = FIT_TIME_CONVERSE(p_session->elapsed_time);
    p->total_timer_time = FIT_TIME_CONVERSE(p_session->activity_time);

    p->total_distance = FIT_DISTANCE_CONVERSE(p_session->dist_travelled);

    if (sensorParameters.hz.available) {
        //mofified by cc 20200311 time need multiple 1000
        for (i = 0; i < FIT_LAP_MESG_TIME_IN_HR_ZONE_COUNT; i++) {
            p->time_in_hr_zone[i] = FIT_TIME_CONVERSE(p_session->hrm_zone[i]);
        }
    }

    if (sensorParameters.pz.available) {
        //mofified by cc 20200311 time need multiple 1000
        for (i = 0; i < FIT_LAP_MESG_TIME_IN_POWER_ZONE_COUNT; i++) {
            p->time_in_power_zone[i] = FIT_TIME_CONVERSE(p_session->power_zone[i]);
        }
    }

//    p->message_index       = ;
    p->total_calories = FIT_CALORIES_CONVERSE(p_session->calories);
    p->avg_speed = FIT_SPEED_CONVERSE(p_session->avg_speed);
    p->max_speed = FIT_SPEED_CONVERSE(p_session->max_speed);
    p->avg_power = FIT_POWER_CONVERSE(p_session->avg_power);
    p->max_power = FIT_POWER_CONVERSE(p_session->max_power);
//    p->total_ascent = p_session->ascent;
//    p->total_descent = p_session->descent;
    p->first_lap_index = 1;
    p->num_laps = p_session->lap;
    p->avg_altitude = FIT_ALTITUDE_CONVERSE(p_session->avg_altitude);
    p->max_altitude = FIT_ALTITUDE_CONVERSE(p_session->max_altitude);
//    p->avg_grade = FIT_GRADE_CONVERS(p_session->avg_grade);
    p->min_altitude = FIT_ALTITUDE_CONVERSE(p_session->min_altitude);

    p->event = FIT_EVENT_LAP;
    p->event_type = FIT_EVENT_TYPE_STOP;
    p->sport = FIT_SPORT_CYCLING;
    p->sub_sport = FIT_SUB_SPORT_GENERIC;

    p->avg_heart_rate = FIT_HRM_CONVERSE(p_session->avg_hrm);
    p->max_heart_rate = FIT_HRM_CONVERSE(p_session->max_hrm);
//    p->min_heart_rate      = (FIT_UINT8)p_session->min_hrm;
    p->avg_cadence = FIT_CAD_CONVERSE(p_session->avg_cadence);
    p->max_cadence = FIT_CAD_CONVERSE(p_session->max_cadence);

//    p->event_group         = ;
    p->trigger = FIT_SESSION_TRIGGER_ACTIVITY_END;

    p->avg_temperature = 0x7F;//FIT_TEMPERTURE_CONVERSE(p_session->avg_temperture);
    p->max_temperature = 0x7F;//FIT_TEMPERTURE_CONVERSE(p_session->max_temperture);
}

/*************************************************************************   
**	function name:get_fit_file_activity_mesg
**	description:	                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
static void get_fit_file_activity_mesg(void *p_mesg, void *p_arg) {
    FIT_ACTIVITY_MESG *p;
    base_cycling_t *p_session;

    if ((p_mesg == NULL) || (p_arg == NULL)) {
        return;
    }

    //将所有的值都赋值为invalid
    Fit_InitMesg((const FIT_MESG_DEF *) &fit_activity_mesg_def, p_mesg);

    p = (FIT_ACTIVITY_MESG *) p_mesg;
    p_session = (base_cycling_t *) &sensor_value->session_cycling;//p_arg;

    p->timestamp = FIT_ZONE_0_TIMESTAMP();//0时区时间
    p->total_timer_time = FIT_TIME_CONVERSE(p_session->activity_time);
    p->local_timestamp = FIT_LOCAL_TIMESTAMP();//本地时间
    p->num_sessions = 1;
    p->type = FIT_ACTIVITY_MANUAL;
    p->event = FIT_EVENT_ACTIVITY;
    p->event_type = FIT_EVENT_TYPE_STOP;
//    p->event_group      = ;
}

/*************************************************************************   
**	function name:get_fit_file_activity_mesg
**	description:	                  
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
void fit_activity_get_fit_file_mesg(FIT_MESG fit_file_mesg_type, void *p_mesg, void *p_arg) {
    get_fit_file_mesg[fit_file_mesg_type](p_mesg, p_arg);
}


