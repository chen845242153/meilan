﻿#ifndef __FIT_ACTIVITY_H__
#define __FIT_ACTIVITY_H__

#ifdef __cplusplus
extern "C"{
#endif

#include "fit.h"
///////////////////////////////////////////////////////////////////////
// Public Definitions
///////////////////////////////////////////////////////////////////////

#define FIT_ALIGNMENT                  4

///////////////////////////////////////////////////////////////////////
// Types
///////////////////////////////////////////////////////////////////////


typedef FIT_ENUM FIT_FILE;
#define FIT_FILE_INVALID                                                         FIT_ENUM_INVALID
#define FIT_FILE_DEVICE                                                          ((FIT_FILE)1) // Read only, single file. Must be in root directory.
#define FIT_FILE_SETTINGS                                                        ((FIT_FILE)2) // Read/write, single file. Directory=Settings
#define FIT_FILE_SPORT                                                           ((FIT_FILE)3) // Read/write, multiple files, file number = sport type. Directory=Sports
#define FIT_FILE_ACTIVITY                                                        ((FIT_FILE)4) // Read/erase, multiple files. Directory=Activities
#define FIT_FILE_WORKOUT                                                         ((FIT_FILE)5) // Read/write/erase, multiple files. Directory=Workouts
#define FIT_FILE_COURSE                                                          ((FIT_FILE)6) // Read/write/erase, multiple files. Directory=Courses
#define FIT_FILE_SCHEDULES                                                       ((FIT_FILE)7) // Read/write, single file. Directory=Schedules
#define FIT_FILE_WEIGHT                                                          ((FIT_FILE)9) // Read only, single file. Circular buffer. All message definitions at start of file. Directory=Weight
#define FIT_FILE_TOTALS                                                          ((FIT_FILE)10) // Read only, single file. Directory=Totals
#define FIT_FILE_GOALS                                                           ((FIT_FILE)11) // Read/write, single file. Directory=Goals
#define FIT_FILE_BLOOD_PRESSURE                                                  ((FIT_FILE)14) // Read only. Directory=Blood Pressure
#define FIT_FILE_MONITORING_A                                                    ((FIT_FILE)15) // Read only. Directory=Monitoring. File number=sub type.
#define FIT_FILE_ACTIVITY_SUMMARY                                                ((FIT_FILE)20) // Read/erase, multiple files. Directory=Activities
#define FIT_FILE_MONITORING_DAILY                                                ((FIT_FILE)28)
#define FIT_FILE_MONITORING_B                                                    ((FIT_FILE)32) // Read only. Directory=Monitoring. File number=identifier
#define FIT_FILE_SEGMENT                                                         ((FIT_FILE)34) // Read/write/erase. Multiple Files.  Directory=Segments
#define FIT_FILE_SEGMENT_LIST                                                    ((FIT_FILE)35) // Read/write/erase. Single File.  Directory=Segments
#define FIT_FILE_EXD_CONFIGURATION                                               ((FIT_FILE)40) // Read/write/erase. Single File. Directory=Settings
#define FIT_FILE_MFG_RANGE_MIN                                                   ((FIT_FILE)0xF7) // 0xF7 - 0xFE reserved for manufacturer specific file types
#define FIT_FILE_MFG_RANGE_MAX                                                   ((FIT_FILE)0xFE) // 0xF7 - 0xFE reserved for manufacturer specific file types
#define FIT_FILE_COUNT                                                           20

typedef FIT_UINT16 FIT_MESG_NUM;
#define FIT_MESG_NUM_INVALID                                                     FIT_UINT16_INVALID
#define FIT_MESG_NUM_FILE_ID                                                     ((FIT_MESG_NUM)0)
#define FIT_MESG_NUM_CAPABILITIES                                                ((FIT_MESG_NUM)1)
#define FIT_MESG_NUM_DEVICE_SETTINGS                                             ((FIT_MESG_NUM)2)
#define FIT_MESG_NUM_USER_PROFILE                                                ((FIT_MESG_NUM)3)
#define FIT_MESG_NUM_HRM_PROFILE                                                 ((FIT_MESG_NUM)4)
#define FIT_MESG_NUM_SDM_PROFILE                                                 ((FIT_MESG_NUM)5)
#define FIT_MESG_NUM_BIKE_PROFILE                                                ((FIT_MESG_NUM)6)
#define FIT_MESG_NUM_ZONES_TARGET                                                ((FIT_MESG_NUM)7)
#define FIT_MESG_NUM_HR_ZONE                                                     ((FIT_MESG_NUM)8)
#define FIT_MESG_NUM_POWER_ZONE                                                  ((FIT_MESG_NUM)9)
#define FIT_MESG_NUM_MET_ZONE                                                    ((FIT_MESG_NUM)10)
#define FIT_MESG_NUM_SPORT                                                       ((FIT_MESG_NUM)12)
#define FIT_MESG_NUM_GOAL                                                        ((FIT_MESG_NUM)15)
#define FIT_MESG_NUM_SESSION                                                     ((FIT_MESG_NUM)18)
#define FIT_MESG_NUM_LAP                                                         ((FIT_MESG_NUM)19)
#define FIT_MESG_NUM_RECORD                                                      ((FIT_MESG_NUM)20)
#define FIT_MESG_NUM_EVENT                                                       ((FIT_MESG_NUM)21)
#define FIT_MESG_NUM_DEVICE_INFO                                                 ((FIT_MESG_NUM)23)
#define FIT_MESG_NUM_WORKOUT                                                     ((FIT_MESG_NUM)26)
#define FIT_MESG_NUM_WORKOUT_STEP                                                ((FIT_MESG_NUM)27)
#define FIT_MESG_NUM_SCHEDULE                                                    ((FIT_MESG_NUM)28)
#define FIT_MESG_NUM_WEIGHT_SCALE                                                ((FIT_MESG_NUM)30)
#define FIT_MESG_NUM_COURSE                                                      ((FIT_MESG_NUM)31)
#define FIT_MESG_NUM_COURSE_POINT                                                ((FIT_MESG_NUM)32)
#define FIT_MESG_NUM_TOTALS                                                      ((FIT_MESG_NUM)33)
#define FIT_MESG_NUM_ACTIVITY                                                    ((FIT_MESG_NUM)34)
#define FIT_MESG_NUM_SOFTWARE                                                    ((FIT_MESG_NUM)35)
#define FIT_MESG_NUM_FILE_CAPABILITIES                                           ((FIT_MESG_NUM)37)
#define FIT_MESG_NUM_MESG_CAPABILITIES                                           ((FIT_MESG_NUM)38)
#define FIT_MESG_NUM_FIELD_CAPABILITIES                                          ((FIT_MESG_NUM)39)
#define FIT_MESG_NUM_FILE_CREATOR                                                ((FIT_MESG_NUM)49)
#define FIT_MESG_NUM_BLOOD_PRESSURE                                              ((FIT_MESG_NUM)51)
#define FIT_MESG_NUM_SPEED_ZONE                                                  ((FIT_MESG_NUM)53)
#define FIT_MESG_NUM_MONITORING                                                  ((FIT_MESG_NUM)55)
#define FIT_MESG_NUM_TRAINING_FILE                                               ((FIT_MESG_NUM)72)
#define FIT_MESG_NUM_HRV                                                         ((FIT_MESG_NUM)78)
#define FIT_MESG_NUM_ANT_RX                                                      ((FIT_MESG_NUM)80)
#define FIT_MESG_NUM_ANT_TX                                                      ((FIT_MESG_NUM)81)
#define FIT_MESG_NUM_ANT_CHANNEL_ID                                              ((FIT_MESG_NUM)82)
#define FIT_MESG_NUM_LENGTH                                                      ((FIT_MESG_NUM)101)
#define FIT_MESG_NUM_MONITORING_INFO                                             ((FIT_MESG_NUM)103)
#define FIT_MESG_NUM_PAD                                                         ((FIT_MESG_NUM)105)
#define FIT_MESG_NUM_SLAVE_DEVICE                                                ((FIT_MESG_NUM)106)
#define FIT_MESG_NUM_CONNECTIVITY                                                ((FIT_MESG_NUM)127)
#define FIT_MESG_NUM_WEATHER_CONDITIONS                                          ((FIT_MESG_NUM)128)
#define FIT_MESG_NUM_WEATHER_ALERT                                               ((FIT_MESG_NUM)129)
#define FIT_MESG_NUM_CADENCE_ZONE                                                ((FIT_MESG_NUM)131)
#define FIT_MESG_NUM_HR                                                          ((FIT_MESG_NUM)132)
#define FIT_MESG_NUM_SEGMENT_LAP                                                 ((FIT_MESG_NUM)142)
#define FIT_MESG_NUM_MEMO_GLOB                                                   ((FIT_MESG_NUM)145)
#define FIT_MESG_NUM_SEGMENT_ID                                                  ((FIT_MESG_NUM)148)
#define FIT_MESG_NUM_SEGMENT_LEADERBOARD_ENTRY                                   ((FIT_MESG_NUM)149)
#define FIT_MESG_NUM_SEGMENT_POINT                                               ((FIT_MESG_NUM)150)
#define FIT_MESG_NUM_SEGMENT_FILE                                                ((FIT_MESG_NUM)151)
#define FIT_MESG_NUM_WORKOUT_SESSION                                             ((FIT_MESG_NUM)158)
#define FIT_MESG_NUM_WATCHFACE_SETTINGS                                          ((FIT_MESG_NUM)159)
#define FIT_MESG_NUM_GPS_METADATA                                                ((FIT_MESG_NUM)160)
#define FIT_MESG_NUM_CAMERA_EVENT                                                ((FIT_MESG_NUM)161)
#define FIT_MESG_NUM_TIMESTAMP_CORRELATION                                       ((FIT_MESG_NUM)162)
#define FIT_MESG_NUM_GYROSCOPE_DATA                                              ((FIT_MESG_NUM)164)
#define FIT_MESG_NUM_ACCELEROMETER_DATA                                          ((FIT_MESG_NUM)165)
#define FIT_MESG_NUM_THREE_D_SENSOR_CALIBRATION                                  ((FIT_MESG_NUM)167)
#define FIT_MESG_NUM_VIDEO_FRAME                                                 ((FIT_MESG_NUM)169)
#define FIT_MESG_NUM_OBDII_DATA                                                  ((FIT_MESG_NUM)174)
#define FIT_MESG_NUM_NMEA_SENTENCE                                               ((FIT_MESG_NUM)177)
#define FIT_MESG_NUM_AVIATION_ATTITUDE                                           ((FIT_MESG_NUM)178)
#define FIT_MESG_NUM_VIDEO                                                       ((FIT_MESG_NUM)184)
#define FIT_MESG_NUM_VIDEO_TITLE                                                 ((FIT_MESG_NUM)185)
#define FIT_MESG_NUM_VIDEO_DESCRIPTION                                           ((FIT_MESG_NUM)186)
#define FIT_MESG_NUM_VIDEO_CLIP                                                  ((FIT_MESG_NUM)187)
#define FIT_MESG_NUM_OHR_SETTINGS                                                ((FIT_MESG_NUM)188)
#define FIT_MESG_NUM_EXD_SCREEN_CONFIGURATION                                    ((FIT_MESG_NUM)200)
#define FIT_MESG_NUM_EXD_DATA_FIELD_CONFIGURATION                                ((FIT_MESG_NUM)201)
#define FIT_MESG_NUM_EXD_DATA_CONCEPT_CONFIGURATION                              ((FIT_MESG_NUM)202)
#define FIT_MESG_NUM_FIELD_DESCRIPTION                                           ((FIT_MESG_NUM)206)
#define FIT_MESG_NUM_DEVELOPER_DATA_ID                                           ((FIT_MESG_NUM)207)
#define FIT_MESG_NUM_MAGNETOMETER_DATA                                           ((FIT_MESG_NUM)208)
#define FIT_MESG_NUM_BAROMETER_DATA                                              ((FIT_MESG_NUM)209)
#define FIT_MESG_NUM_ONE_D_SENSOR_CALIBRATION                                    ((FIT_MESG_NUM)210)
#define FIT_MESG_NUM_SET                                                         ((FIT_MESG_NUM)225)
#define FIT_MESG_NUM_STRESS_LEVEL                                                ((FIT_MESG_NUM)227)
#define FIT_MESG_NUM_DIVE_SETTINGS                                               ((FIT_MESG_NUM)258)
#define FIT_MESG_NUM_DIVE_GAS                                                    ((FIT_MESG_NUM)259)
#define FIT_MESG_NUM_DIVE_ALARM                                                  ((FIT_MESG_NUM)262)
#define FIT_MESG_NUM_EXERCISE_TITLE                                              ((FIT_MESG_NUM)264)
#define FIT_MESG_NUM_DIVE_SUMMARY                                                ((FIT_MESG_NUM)268)
#define FIT_MESG_NUM_MFG_RANGE_MIN                                               ((FIT_MESG_NUM)0xFF00) // 0xFF00 - 0xFFFE reserved for manufacturer specific messages
#define FIT_MESG_NUM_MFG_RANGE_MAX                                               ((FIT_MESG_NUM)0xFFFE) // 0xFF00 - 0xFFFE reserved for manufacturer specific messages
#define FIT_MESG_NUM_COUNT                                                       88

typedef FIT_UINT8 FIT_CHECKSUM;
#define FIT_CHECKSUM_INVALID                                                     FIT_UINT8_INVALID
#define FIT_CHECKSUM_CLEAR                                                       ((FIT_CHECKSUM)0) // Allows clear of checksum for flash memory where can only write 1 to 0 without erasing sector.
#define FIT_CHECKSUM_OK                                                          ((FIT_CHECKSUM)1) // Set to mark checksum as valid if computes to invalid values 0 or 0xFF.  Checksum can also be set to ok to save encoding computation time.
#define FIT_CHECKSUM_COUNT                                                       2

typedef FIT_UINT8Z FIT_FILE_FLAGS;
#define FIT_FILE_FLAGS_INVALID                                                   FIT_UINT8Z_INVALID
#define FIT_FILE_FLAGS_READ                                                      ((FIT_FILE_FLAGS)0x02)
#define FIT_FILE_FLAGS_WRITE                                                     ((FIT_FILE_FLAGS)0x04)
#define FIT_FILE_FLAGS_ERASE                                                     ((FIT_FILE_FLAGS)0x08)
#define FIT_FILE_FLAGS_COUNT                                                     3

typedef FIT_ENUM FIT_MESG_COUNT;
#define FIT_MESG_COUNT_INVALID                                                   FIT_ENUM_INVALID
#define FIT_MESG_COUNT_NUM_PER_FILE                                              ((FIT_MESG_COUNT)0)
#define FIT_MESG_COUNT_MAX_PER_FILE                                              ((FIT_MESG_COUNT)1)
#define FIT_MESG_COUNT_MAX_PER_FILE_TYPE                                         ((FIT_MESG_COUNT)2)
#define FIT_MESG_COUNT_COUNT                                                     3

typedef FIT_UINT32 FIT_DATE_TIME; // seconds since UTC 00:00 Dec 31 1989
#define FIT_DATE_TIME_INVALID                                                    FIT_UINT32_INVALID
#define FIT_DATE_TIME_MIN                                                        ((FIT_DATE_TIME)0x10000000) // if date_time is < 0x10000000 then it is system time (seconds from device power on)
#define FIT_DATE_TIME_COUNT                                                      1

typedef FIT_UINT32 FIT_LOCAL_DATE_TIME; // seconds since 00:00 Dec 31 1989 in local time zone
#define FIT_LOCAL_DATE_TIME_INVALID                                              FIT_UINT32_INVALID
#define FIT_LOCAL_DATE_TIME_MIN                                                  ((FIT_LOCAL_DATE_TIME)0x10000000) // if date_time is < 0x10000000 then it is system time (seconds from device power on)
#define FIT_LOCAL_DATE_TIME_COUNT                                                1

typedef FIT_UINT16 FIT_MESSAGE_INDEX;
#define FIT_MESSAGE_INDEX_INVALID                                                FIT_UINT16_INVALID
#define FIT_MESSAGE_INDEX_SELECTED                                               ((FIT_MESSAGE_INDEX)0x8000) // message is selected if set
#define FIT_MESSAGE_INDEX_RESERVED                                               ((FIT_MESSAGE_INDEX)0x7000) // reserved (default 0)
#define FIT_MESSAGE_INDEX_MASK                                                   ((FIT_MESSAGE_INDEX)0x0FFF) // index
#define FIT_MESSAGE_INDEX_COUNT                                                  3

typedef FIT_UINT8 FIT_DEVICE_INDEX;
#define FIT_DEVICE_INDEX_INVALID                                                 FIT_UINT8_INVALID
#define FIT_DEVICE_INDEX_CREATOR                                                 ((FIT_DEVICE_INDEX)0) // Creator of the file is always device index 0.
#define FIT_DEVICE_INDEX_COUNT                                                   1

typedef FIT_ENUM FIT_GENDER;
#define FIT_GENDER_INVALID                                                       FIT_ENUM_INVALID
#define FIT_GENDER_FEMALE                                                        ((FIT_GENDER)0)
#define FIT_GENDER_MALE                                                          ((FIT_GENDER)1)
#define FIT_GENDER_COUNT                                                         2

typedef FIT_ENUM FIT_LANGUAGE;
#define FIT_LANGUAGE_INVALID                                                     FIT_ENUM_INVALID
#define FIT_LANGUAGE_ENGLISH                                                     ((FIT_LANGUAGE)0)
#define FIT_LANGUAGE_FRENCH                                                      ((FIT_LANGUAGE)1)
#define FIT_LANGUAGE_ITALIAN                                                     ((FIT_LANGUAGE)2)
#define FIT_LANGUAGE_GERMAN                                                      ((FIT_LANGUAGE)3)
#define FIT_LANGUAGE_SPANISH                                                     ((FIT_LANGUAGE)4)
#define FIT_LANGUAGE_CROATIAN                                                    ((FIT_LANGUAGE)5)
#define FIT_LANGUAGE_CZECH                                                       ((FIT_LANGUAGE)6)
#define FIT_LANGUAGE_DANISH                                                      ((FIT_LANGUAGE)7)
#define FIT_LANGUAGE_DUTCH                                                       ((FIT_LANGUAGE)8)
#define FIT_LANGUAGE_FINNISH                                                     ((FIT_LANGUAGE)9)
#define FIT_LANGUAGE_GREEK                                                       ((FIT_LANGUAGE)10)
#define FIT_LANGUAGE_HUNGARIAN                                                   ((FIT_LANGUAGE)11)
#define FIT_LANGUAGE_NORWEGIAN                                                   ((FIT_LANGUAGE)12)
#define FIT_LANGUAGE_POLISH                                                      ((FIT_LANGUAGE)13)
#define FIT_LANGUAGE_PORTUGUESE                                                  ((FIT_LANGUAGE)14)
#define FIT_LANGUAGE_SLOVAKIAN                                                   ((FIT_LANGUAGE)15)
#define FIT_LANGUAGE_SLOVENIAN                                                   ((FIT_LANGUAGE)16)
#define FIT_LANGUAGE_SWEDISH                                                     ((FIT_LANGUAGE)17)
#define FIT_LANGUAGE_RUSSIAN                                                     ((FIT_LANGUAGE)18)
#define FIT_LANGUAGE_TURKISH                                                     ((FIT_LANGUAGE)19)
#define FIT_LANGUAGE_LATVIAN                                                     ((FIT_LANGUAGE)20)
#define FIT_LANGUAGE_UKRAINIAN                                                   ((FIT_LANGUAGE)21)
#define FIT_LANGUAGE_ARABIC                                                      ((FIT_LANGUAGE)22)
#define FIT_LANGUAGE_FARSI                                                       ((FIT_LANGUAGE)23)
#define FIT_LANGUAGE_BULGARIAN                                                   ((FIT_LANGUAGE)24)
#define FIT_LANGUAGE_ROMANIAN                                                    ((FIT_LANGUAGE)25)
#define FIT_LANGUAGE_CHINESE                                                     ((FIT_LANGUAGE)26)
#define FIT_LANGUAGE_JAPANESE                                                    ((FIT_LANGUAGE)27)
#define FIT_LANGUAGE_KOREAN                                                      ((FIT_LANGUAGE)28)
#define FIT_LANGUAGE_TAIWANESE                                                   ((FIT_LANGUAGE)29)
#define FIT_LANGUAGE_THAI                                                        ((FIT_LANGUAGE)30)
#define FIT_LANGUAGE_HEBREW                                                      ((FIT_LANGUAGE)31)
#define FIT_LANGUAGE_BRAZILIAN_PORTUGUESE                                        ((FIT_LANGUAGE)32)
#define FIT_LANGUAGE_INDONESIAN                                                  ((FIT_LANGUAGE)33)
#define FIT_LANGUAGE_MALAYSIAN                                                   ((FIT_LANGUAGE)34)
#define FIT_LANGUAGE_VIETNAMESE                                                  ((FIT_LANGUAGE)35)
#define FIT_LANGUAGE_BURMESE                                                     ((FIT_LANGUAGE)36)
#define FIT_LANGUAGE_MONGOLIAN                                                   ((FIT_LANGUAGE)37)
#define FIT_LANGUAGE_CUSTOM                                                      ((FIT_LANGUAGE)254)
#define FIT_LANGUAGE_COUNT                                                       39

typedef FIT_UINT8Z FIT_LANGUAGE_BITS_0; // Bit field corresponding to language enum type (1 << language).
#define FIT_LANGUAGE_BITS_0_INVALID                                              FIT_UINT8Z_INVALID
#define FIT_LANGUAGE_BITS_0_ENGLISH                                              ((FIT_LANGUAGE_BITS_0)0x01)
#define FIT_LANGUAGE_BITS_0_FRENCH                                               ((FIT_LANGUAGE_BITS_0)0x02)
#define FIT_LANGUAGE_BITS_0_ITALIAN                                              ((FIT_LANGUAGE_BITS_0)0x04)
#define FIT_LANGUAGE_BITS_0_GERMAN                                               ((FIT_LANGUAGE_BITS_0)0x08)
#define FIT_LANGUAGE_BITS_0_SPANISH                                              ((FIT_LANGUAGE_BITS_0)0x10)
#define FIT_LANGUAGE_BITS_0_CROATIAN                                             ((FIT_LANGUAGE_BITS_0)0x20)
#define FIT_LANGUAGE_BITS_0_CZECH                                                ((FIT_LANGUAGE_BITS_0)0x40)
#define FIT_LANGUAGE_BITS_0_DANISH                                               ((FIT_LANGUAGE_BITS_0)0x80)
#define FIT_LANGUAGE_BITS_0_COUNT                                                8

typedef FIT_UINT8Z FIT_LANGUAGE_BITS_1;
#define FIT_LANGUAGE_BITS_1_INVALID                                              FIT_UINT8Z_INVALID
#define FIT_LANGUAGE_BITS_1_DUTCH                                                ((FIT_LANGUAGE_BITS_1)0x01)
#define FIT_LANGUAGE_BITS_1_FINNISH                                              ((FIT_LANGUAGE_BITS_1)0x02)
#define FIT_LANGUAGE_BITS_1_GREEK                                                ((FIT_LANGUAGE_BITS_1)0x04)
#define FIT_LANGUAGE_BITS_1_HUNGARIAN                                            ((FIT_LANGUAGE_BITS_1)0x08)
#define FIT_LANGUAGE_BITS_1_NORWEGIAN                                            ((FIT_LANGUAGE_BITS_1)0x10)
#define FIT_LANGUAGE_BITS_1_POLISH                                               ((FIT_LANGUAGE_BITS_1)0x20)
#define FIT_LANGUAGE_BITS_1_PORTUGUESE                                           ((FIT_LANGUAGE_BITS_1)0x40)
#define FIT_LANGUAGE_BITS_1_SLOVAKIAN                                            ((FIT_LANGUAGE_BITS_1)0x80)
#define FIT_LANGUAGE_BITS_1_COUNT                                                8

typedef FIT_UINT8Z FIT_LANGUAGE_BITS_2;
#define FIT_LANGUAGE_BITS_2_INVALID                                              FIT_UINT8Z_INVALID
#define FIT_LANGUAGE_BITS_2_SLOVENIAN                                            ((FIT_LANGUAGE_BITS_2)0x01)
#define FIT_LANGUAGE_BITS_2_SWEDISH                                              ((FIT_LANGUAGE_BITS_2)0x02)
#define FIT_LANGUAGE_BITS_2_RUSSIAN                                              ((FIT_LANGUAGE_BITS_2)0x04)
#define FIT_LANGUAGE_BITS_2_TURKISH                                              ((FIT_LANGUAGE_BITS_2)0x08)
#define FIT_LANGUAGE_BITS_2_LATVIAN                                              ((FIT_LANGUAGE_BITS_2)0x10)
#define FIT_LANGUAGE_BITS_2_UKRAINIAN                                            ((FIT_LANGUAGE_BITS_2)0x20)
#define FIT_LANGUAGE_BITS_2_ARABIC                                               ((FIT_LANGUAGE_BITS_2)0x40)
#define FIT_LANGUAGE_BITS_2_FARSI                                                ((FIT_LANGUAGE_BITS_2)0x80)
#define FIT_LANGUAGE_BITS_2_COUNT                                                8

typedef FIT_UINT8Z FIT_LANGUAGE_BITS_3;
#define FIT_LANGUAGE_BITS_3_INVALID                                              FIT_UINT8Z_INVALID
#define FIT_LANGUAGE_BITS_3_BULGARIAN                                            ((FIT_LANGUAGE_BITS_3)0x01)
#define FIT_LANGUAGE_BITS_3_ROMANIAN                                             ((FIT_LANGUAGE_BITS_3)0x02)
#define FIT_LANGUAGE_BITS_3_CHINESE                                              ((FIT_LANGUAGE_BITS_3)0x04)
#define FIT_LANGUAGE_BITS_3_JAPANESE                                             ((FIT_LANGUAGE_BITS_3)0x08)
#define FIT_LANGUAGE_BITS_3_KOREAN                                               ((FIT_LANGUAGE_BITS_3)0x10)
#define FIT_LANGUAGE_BITS_3_TAIWANESE                                            ((FIT_LANGUAGE_BITS_3)0x20)
#define FIT_LANGUAGE_BITS_3_THAI                                                 ((FIT_LANGUAGE_BITS_3)0x40)
#define FIT_LANGUAGE_BITS_3_HEBREW                                               ((FIT_LANGUAGE_BITS_3)0x80)
#define FIT_LANGUAGE_BITS_3_COUNT                                                8

typedef FIT_UINT8Z FIT_LANGUAGE_BITS_4;
#define FIT_LANGUAGE_BITS_4_INVALID                                              FIT_UINT8Z_INVALID
#define FIT_LANGUAGE_BITS_4_BRAZILIAN_PORTUGUESE                                 ((FIT_LANGUAGE_BITS_4)0x01)
#define FIT_LANGUAGE_BITS_4_INDONESIAN                                           ((FIT_LANGUAGE_BITS_4)0x02)
#define FIT_LANGUAGE_BITS_4_MALAYSIAN                                            ((FIT_LANGUAGE_BITS_4)0x04)
#define FIT_LANGUAGE_BITS_4_VIETNAMESE                                           ((FIT_LANGUAGE_BITS_4)0x08)
#define FIT_LANGUAGE_BITS_4_BURMESE                                              ((FIT_LANGUAGE_BITS_4)0x10)
#define FIT_LANGUAGE_BITS_4_MONGOLIAN                                            ((FIT_LANGUAGE_BITS_4)0x20)
#define FIT_LANGUAGE_BITS_4_COUNT                                                6

typedef FIT_ENUM FIT_TIME_ZONE;
#define FIT_TIME_ZONE_INVALID                                                    FIT_ENUM_INVALID
#define FIT_TIME_ZONE_ALMATY                                                     ((FIT_TIME_ZONE)0)
#define FIT_TIME_ZONE_BANGKOK                                                    ((FIT_TIME_ZONE)1)
#define FIT_TIME_ZONE_BOMBAY                                                     ((FIT_TIME_ZONE)2)
#define FIT_TIME_ZONE_BRASILIA                                                   ((FIT_TIME_ZONE)3)
#define FIT_TIME_ZONE_CAIRO                                                      ((FIT_TIME_ZONE)4)
#define FIT_TIME_ZONE_CAPE_VERDE_IS                                              ((FIT_TIME_ZONE)5)
#define FIT_TIME_ZONE_DARWIN                                                     ((FIT_TIME_ZONE)6)
#define FIT_TIME_ZONE_ENIWETOK                                                   ((FIT_TIME_ZONE)7)
#define FIT_TIME_ZONE_FIJI                                                       ((FIT_TIME_ZONE)8)
#define FIT_TIME_ZONE_HONG_KONG                                                  ((FIT_TIME_ZONE)9)
#define FIT_TIME_ZONE_ISLAMABAD                                                  ((FIT_TIME_ZONE)10)
#define FIT_TIME_ZONE_KABUL                                                      ((FIT_TIME_ZONE)11)
#define FIT_TIME_ZONE_MAGADAN                                                    ((FIT_TIME_ZONE)12)
#define FIT_TIME_ZONE_MID_ATLANTIC                                               ((FIT_TIME_ZONE)13)
#define FIT_TIME_ZONE_MOSCOW                                                     ((FIT_TIME_ZONE)14)
#define FIT_TIME_ZONE_MUSCAT                                                     ((FIT_TIME_ZONE)15)
#define FIT_TIME_ZONE_NEWFOUNDLAND                                               ((FIT_TIME_ZONE)16)
#define FIT_TIME_ZONE_SAMOA                                                      ((FIT_TIME_ZONE)17)
#define FIT_TIME_ZONE_SYDNEY                                                     ((FIT_TIME_ZONE)18)
#define FIT_TIME_ZONE_TEHRAN                                                     ((FIT_TIME_ZONE)19)
#define FIT_TIME_ZONE_TOKYO                                                      ((FIT_TIME_ZONE)20)
#define FIT_TIME_ZONE_US_ALASKA                                                  ((FIT_TIME_ZONE)21)
#define FIT_TIME_ZONE_US_ATLANTIC                                                ((FIT_TIME_ZONE)22)
#define FIT_TIME_ZONE_US_CENTRAL                                                 ((FIT_TIME_ZONE)23)
#define FIT_TIME_ZONE_US_EASTERN                                                 ((FIT_TIME_ZONE)24)
#define FIT_TIME_ZONE_US_HAWAII                                                  ((FIT_TIME_ZONE)25)
#define FIT_TIME_ZONE_US_MOUNTAIN                                                ((FIT_TIME_ZONE)26)
#define FIT_TIME_ZONE_US_PACIFIC                                                 ((FIT_TIME_ZONE)27)
#define FIT_TIME_ZONE_OTHER                                                      ((FIT_TIME_ZONE)28)
#define FIT_TIME_ZONE_AUCKLAND                                                   ((FIT_TIME_ZONE)29)
#define FIT_TIME_ZONE_KATHMANDU                                                  ((FIT_TIME_ZONE)30)
#define FIT_TIME_ZONE_EUROPE_WESTERN_WET                                         ((FIT_TIME_ZONE)31)
#define FIT_TIME_ZONE_EUROPE_CENTRAL_CET                                         ((FIT_TIME_ZONE)32)
#define FIT_TIME_ZONE_EUROPE_EASTERN_EET                                         ((FIT_TIME_ZONE)33)
#define FIT_TIME_ZONE_JAKARTA                                                    ((FIT_TIME_ZONE)34)
#define FIT_TIME_ZONE_PERTH                                                      ((FIT_TIME_ZONE)35)
#define FIT_TIME_ZONE_ADELAIDE                                                   ((FIT_TIME_ZONE)36)
#define FIT_TIME_ZONE_BRISBANE                                                   ((FIT_TIME_ZONE)37)
#define FIT_TIME_ZONE_TASMANIA                                                   ((FIT_TIME_ZONE)38)
#define FIT_TIME_ZONE_ICELAND                                                    ((FIT_TIME_ZONE)39)
#define FIT_TIME_ZONE_AMSTERDAM                                                  ((FIT_TIME_ZONE)40)
#define FIT_TIME_ZONE_ATHENS                                                     ((FIT_TIME_ZONE)41)
#define FIT_TIME_ZONE_BARCELONA                                                  ((FIT_TIME_ZONE)42)
#define FIT_TIME_ZONE_BERLIN                                                     ((FIT_TIME_ZONE)43)
#define FIT_TIME_ZONE_BRUSSELS                                                   ((FIT_TIME_ZONE)44)
#define FIT_TIME_ZONE_BUDAPEST                                                   ((FIT_TIME_ZONE)45)
#define FIT_TIME_ZONE_COPENHAGEN                                                 ((FIT_TIME_ZONE)46)
#define FIT_TIME_ZONE_DUBLIN                                                     ((FIT_TIME_ZONE)47)
#define FIT_TIME_ZONE_HELSINKI                                                   ((FIT_TIME_ZONE)48)
#define FIT_TIME_ZONE_LISBON                                                     ((FIT_TIME_ZONE)49)
#define FIT_TIME_ZONE_LONDON                                                     ((FIT_TIME_ZONE)50)
#define FIT_TIME_ZONE_MADRID                                                     ((FIT_TIME_ZONE)51)
#define FIT_TIME_ZONE_MUNICH                                                     ((FIT_TIME_ZONE)52)
#define FIT_TIME_ZONE_OSLO                                                       ((FIT_TIME_ZONE)53)
#define FIT_TIME_ZONE_PARIS                                                      ((FIT_TIME_ZONE)54)
#define FIT_TIME_ZONE_PRAGUE                                                     ((FIT_TIME_ZONE)55)
#define FIT_TIME_ZONE_REYKJAVIK                                                  ((FIT_TIME_ZONE)56)
#define FIT_TIME_ZONE_ROME                                                       ((FIT_TIME_ZONE)57)
#define FIT_TIME_ZONE_STOCKHOLM                                                  ((FIT_TIME_ZONE)58)
#define FIT_TIME_ZONE_VIENNA                                                     ((FIT_TIME_ZONE)59)
#define FIT_TIME_ZONE_WARSAW                                                     ((FIT_TIME_ZONE)60)
#define FIT_TIME_ZONE_ZURICH                                                     ((FIT_TIME_ZONE)61)
#define FIT_TIME_ZONE_QUEBEC                                                     ((FIT_TIME_ZONE)62)
#define FIT_TIME_ZONE_ONTARIO                                                    ((FIT_TIME_ZONE)63)
#define FIT_TIME_ZONE_MANITOBA                                                   ((FIT_TIME_ZONE)64)
#define FIT_TIME_ZONE_SASKATCHEWAN                                               ((FIT_TIME_ZONE)65)
#define FIT_TIME_ZONE_ALBERTA                                                    ((FIT_TIME_ZONE)66)
#define FIT_TIME_ZONE_BRITISH_COLUMBIA                                           ((FIT_TIME_ZONE)67)
#define FIT_TIME_ZONE_BOISE                                                      ((FIT_TIME_ZONE)68)
#define FIT_TIME_ZONE_BOSTON                                                     ((FIT_TIME_ZONE)69)
#define FIT_TIME_ZONE_CHICAGO                                                    ((FIT_TIME_ZONE)70)
#define FIT_TIME_ZONE_DALLAS                                                     ((FIT_TIME_ZONE)71)
#define FIT_TIME_ZONE_DENVER                                                     ((FIT_TIME_ZONE)72)
#define FIT_TIME_ZONE_KANSAS_CITY                                                ((FIT_TIME_ZONE)73)
#define FIT_TIME_ZONE_LAS_VEGAS                                                  ((FIT_TIME_ZONE)74)
#define FIT_TIME_ZONE_LOS_ANGELES                                                ((FIT_TIME_ZONE)75)
#define FIT_TIME_ZONE_MIAMI                                                      ((FIT_TIME_ZONE)76)
#define FIT_TIME_ZONE_MINNEAPOLIS                                                ((FIT_TIME_ZONE)77)
#define FIT_TIME_ZONE_NEW_YORK                                                   ((FIT_TIME_ZONE)78)
#define FIT_TIME_ZONE_NEW_ORLEANS                                                ((FIT_TIME_ZONE)79)
#define FIT_TIME_ZONE_PHOENIX                                                    ((FIT_TIME_ZONE)80)
#define FIT_TIME_ZONE_SANTA_FE                                                   ((FIT_TIME_ZONE)81)
#define FIT_TIME_ZONE_SEATTLE                                                    ((FIT_TIME_ZONE)82)
#define FIT_TIME_ZONE_WASHINGTON_DC                                              ((FIT_TIME_ZONE)83)
#define FIT_TIME_ZONE_US_ARIZONA                                                 ((FIT_TIME_ZONE)84)
#define FIT_TIME_ZONE_CHITA                                                      ((FIT_TIME_ZONE)85)
#define FIT_TIME_ZONE_EKATERINBURG                                               ((FIT_TIME_ZONE)86)
#define FIT_TIME_ZONE_IRKUTSK                                                    ((FIT_TIME_ZONE)87)
#define FIT_TIME_ZONE_KALININGRAD                                                ((FIT_TIME_ZONE)88)
#define FIT_TIME_ZONE_KRASNOYARSK                                                ((FIT_TIME_ZONE)89)
#define FIT_TIME_ZONE_NOVOSIBIRSK                                                ((FIT_TIME_ZONE)90)
#define FIT_TIME_ZONE_PETROPAVLOVSK_KAMCHATSKIY                                  ((FIT_TIME_ZONE)91)
#define FIT_TIME_ZONE_SAMARA                                                     ((FIT_TIME_ZONE)92)
#define FIT_TIME_ZONE_VLADIVOSTOK                                                ((FIT_TIME_ZONE)93)
#define FIT_TIME_ZONE_MEXICO_CENTRAL                                             ((FIT_TIME_ZONE)94)
#define FIT_TIME_ZONE_MEXICO_MOUNTAIN                                            ((FIT_TIME_ZONE)95)
#define FIT_TIME_ZONE_MEXICO_PACIFIC                                             ((FIT_TIME_ZONE)96)
#define FIT_TIME_ZONE_CAPE_TOWN                                                  ((FIT_TIME_ZONE)97)
#define FIT_TIME_ZONE_WINKHOEK                                                   ((FIT_TIME_ZONE)98)
#define FIT_TIME_ZONE_LAGOS                                                      ((FIT_TIME_ZONE)99)
#define FIT_TIME_ZONE_RIYAHD                                                     ((FIT_TIME_ZONE)100)
#define FIT_TIME_ZONE_VENEZUELA                                                  ((FIT_TIME_ZONE)101)
#define FIT_TIME_ZONE_AUSTRALIA_LH                                               ((FIT_TIME_ZONE)102)
#define FIT_TIME_ZONE_SANTIAGO                                                   ((FIT_TIME_ZONE)103)
#define FIT_TIME_ZONE_MANUAL                                                     ((FIT_TIME_ZONE)253)
#define FIT_TIME_ZONE_AUTOMATIC                                                  ((FIT_TIME_ZONE)254)
#define FIT_TIME_ZONE_COUNT                                                      106

typedef FIT_ENUM FIT_DISPLAY_MEASURE;
#define FIT_DISPLAY_MEASURE_INVALID                                              FIT_ENUM_INVALID
#define FIT_DISPLAY_MEASURE_METRIC                                               ((FIT_DISPLAY_MEASURE)0)
#define FIT_DISPLAY_MEASURE_STATUTE                                              ((FIT_DISPLAY_MEASURE)1)
#define FIT_DISPLAY_MEASURE_NAUTICAL                                             ((FIT_DISPLAY_MEASURE)2)
#define FIT_DISPLAY_MEASURE_COUNT                                                3

typedef FIT_ENUM FIT_DISPLAY_HEART;
#define FIT_DISPLAY_HEART_INVALID                                                FIT_ENUM_INVALID
#define FIT_DISPLAY_HEART_BPM                                                    ((FIT_DISPLAY_HEART)0)
#define FIT_DISPLAY_HEART_MAX                                                    ((FIT_DISPLAY_HEART)1)
#define FIT_DISPLAY_HEART_RESERVE                                                ((FIT_DISPLAY_HEART)2)
#define FIT_DISPLAY_HEART_COUNT                                                  3

typedef FIT_ENUM FIT_DISPLAY_POWER;
#define FIT_DISPLAY_POWER_INVALID                                                FIT_ENUM_INVALID
#define FIT_DISPLAY_POWER_WATTS                                                  ((FIT_DISPLAY_POWER)0)
#define FIT_DISPLAY_POWER_PERCENT_FTP                                            ((FIT_DISPLAY_POWER)1)
#define FIT_DISPLAY_POWER_COUNT                                                  2

typedef FIT_ENUM FIT_DISPLAY_POSITION;
#define FIT_DISPLAY_POSITION_INVALID                                             FIT_ENUM_INVALID
#define FIT_DISPLAY_POSITION_DEGREE                                              ((FIT_DISPLAY_POSITION)0) // dd.dddddd
#define FIT_DISPLAY_POSITION_DEGREE_MINUTE                                       ((FIT_DISPLAY_POSITION)1) // dddmm.mmm
#define FIT_DISPLAY_POSITION_DEGREE_MINUTE_SECOND                                ((FIT_DISPLAY_POSITION)2) // dddmmss
#define FIT_DISPLAY_POSITION_AUSTRIAN_GRID                                       ((FIT_DISPLAY_POSITION)3) // Austrian Grid (BMN)
#define FIT_DISPLAY_POSITION_BRITISH_GRID                                        ((FIT_DISPLAY_POSITION)4) // British National Grid
#define FIT_DISPLAY_POSITION_DUTCH_GRID                                          ((FIT_DISPLAY_POSITION)5) // Dutch grid system
#define FIT_DISPLAY_POSITION_HUNGARIAN_GRID                                      ((FIT_DISPLAY_POSITION)6) // Hungarian grid system
#define FIT_DISPLAY_POSITION_FINNISH_GRID                                        ((FIT_DISPLAY_POSITION)7) // Finnish grid system Zone3 KKJ27
#define FIT_DISPLAY_POSITION_GERMAN_GRID                                         ((FIT_DISPLAY_POSITION)8) // Gausss Krueger (German)
#define FIT_DISPLAY_POSITION_ICELANDIC_GRID                                      ((FIT_DISPLAY_POSITION)9) // Icelandic Grid
#define FIT_DISPLAY_POSITION_INDONESIAN_EQUATORIAL                               ((FIT_DISPLAY_POSITION)10) // Indonesian Equatorial LCO
#define FIT_DISPLAY_POSITION_INDONESIAN_IRIAN                                    ((FIT_DISPLAY_POSITION)11) // Indonesian Irian LCO
#define FIT_DISPLAY_POSITION_INDONESIAN_SOUTHERN                                 ((FIT_DISPLAY_POSITION)12) // Indonesian Southern LCO
#define FIT_DISPLAY_POSITION_INDIA_ZONE_0                                        ((FIT_DISPLAY_POSITION)13) // India zone 0
#define FIT_DISPLAY_POSITION_INDIA_ZONE_IA                                       ((FIT_DISPLAY_POSITION)14) // India zone IA
#define FIT_DISPLAY_POSITION_INDIA_ZONE_IB                                       ((FIT_DISPLAY_POSITION)15) // India zone IB
#define FIT_DISPLAY_POSITION_INDIA_ZONE_IIA                                      ((FIT_DISPLAY_POSITION)16) // India zone IIA
#define FIT_DISPLAY_POSITION_INDIA_ZONE_IIB                                      ((FIT_DISPLAY_POSITION)17) // India zone IIB
#define FIT_DISPLAY_POSITION_INDIA_ZONE_IIIA                                     ((FIT_DISPLAY_POSITION)18) // India zone IIIA
#define FIT_DISPLAY_POSITION_INDIA_ZONE_IIIB                                     ((FIT_DISPLAY_POSITION)19) // India zone IIIB
#define FIT_DISPLAY_POSITION_INDIA_ZONE_IVA                                      ((FIT_DISPLAY_POSITION)20) // India zone IVA
#define FIT_DISPLAY_POSITION_INDIA_ZONE_IVB                                      ((FIT_DISPLAY_POSITION)21) // India zone IVB
#define FIT_DISPLAY_POSITION_IRISH_TRANSVERSE                                    ((FIT_DISPLAY_POSITION)22) // Irish Transverse Mercator
#define FIT_DISPLAY_POSITION_IRISH_GRID                                          ((FIT_DISPLAY_POSITION)23) // Irish Grid
#define FIT_DISPLAY_POSITION_LORAN                                               ((FIT_DISPLAY_POSITION)24) // Loran TD
#define FIT_DISPLAY_POSITION_MAIDENHEAD_GRID                                     ((FIT_DISPLAY_POSITION)25) // Maidenhead grid system
#define FIT_DISPLAY_POSITION_MGRS_GRID                                           ((FIT_DISPLAY_POSITION)26) // MGRS grid system
#define FIT_DISPLAY_POSITION_NEW_ZEALAND_GRID                                    ((FIT_DISPLAY_POSITION)27) // New Zealand grid system
#define FIT_DISPLAY_POSITION_NEW_ZEALAND_TRANSVERSE                              ((FIT_DISPLAY_POSITION)28) // New Zealand Transverse Mercator
#define FIT_DISPLAY_POSITION_QATAR_GRID                                          ((FIT_DISPLAY_POSITION)29) // Qatar National Grid
#define FIT_DISPLAY_POSITION_MODIFIED_SWEDISH_GRID                               ((FIT_DISPLAY_POSITION)30) // Modified RT-90 (Sweden)
#define FIT_DISPLAY_POSITION_SWEDISH_GRID                                        ((FIT_DISPLAY_POSITION)31) // RT-90 (Sweden)
#define FIT_DISPLAY_POSITION_SOUTH_AFRICAN_GRID                                  ((FIT_DISPLAY_POSITION)32) // South African Grid
#define FIT_DISPLAY_POSITION_SWISS_GRID                                          ((FIT_DISPLAY_POSITION)33) // Swiss CH-1903 grid
#define FIT_DISPLAY_POSITION_TAIWAN_GRID                                         ((FIT_DISPLAY_POSITION)34) // Taiwan Grid
#define FIT_DISPLAY_POSITION_UNITED_STATES_GRID                                  ((FIT_DISPLAY_POSITION)35) // United States National Grid
#define FIT_DISPLAY_POSITION_UTM_UPS_GRID                                        ((FIT_DISPLAY_POSITION)36) // UTM/UPS grid system
#define FIT_DISPLAY_POSITION_WEST_MALAYAN                                        ((FIT_DISPLAY_POSITION)37) // West Malayan RSO
#define FIT_DISPLAY_POSITION_BORNEO_RSO                                          ((FIT_DISPLAY_POSITION)38) // Borneo RSO
#define FIT_DISPLAY_POSITION_ESTONIAN_GRID                                       ((FIT_DISPLAY_POSITION)39) // Estonian grid system
#define FIT_DISPLAY_POSITION_LATVIAN_GRID                                        ((FIT_DISPLAY_POSITION)40) // Latvian Transverse Mercator
#define FIT_DISPLAY_POSITION_SWEDISH_REF_99_GRID                                 ((FIT_DISPLAY_POSITION)41) // Reference Grid 99 TM (Swedish)
#define FIT_DISPLAY_POSITION_COUNT                                               42

typedef FIT_ENUM FIT_SWITCH;
#define FIT_SWITCH_INVALID                                                       FIT_ENUM_INVALID
#define FIT_SWITCH_OFF                                                           ((FIT_SWITCH)0)
#define FIT_SWITCH_ON                                                            ((FIT_SWITCH)1)
#define FIT_SWITCH_AUTO                                                          ((FIT_SWITCH)2)
#define FIT_SWITCH_COUNT                                                         3

typedef FIT_ENUM FIT_SPORT;
#define FIT_SPORT_INVALID                                                        FIT_ENUM_INVALID
#define FIT_SPORT_GENERIC                                                        ((FIT_SPORT)0)
#define FIT_SPORT_RUNNING                                                        ((FIT_SPORT)1)
#define FIT_SPORT_CYCLING                                                        ((FIT_SPORT)2)
#define FIT_SPORT_TRANSITION                                                     ((FIT_SPORT)3) // Mulitsport transition
#define FIT_SPORT_FITNESS_EQUIPMENT                                              ((FIT_SPORT)4)
#define FIT_SPORT_SWIMMING                                                       ((FIT_SPORT)5)
#define FIT_SPORT_BASKETBALL                                                     ((FIT_SPORT)6)
#define FIT_SPORT_SOCCER                                                         ((FIT_SPORT)7)
#define FIT_SPORT_TENNIS                                                         ((FIT_SPORT)8)
#define FIT_SPORT_AMERICAN_FOOTBALL                                              ((FIT_SPORT)9)
#define FIT_SPORT_TRAINING                                                       ((FIT_SPORT)10)
#define FIT_SPORT_WALKING                                                        ((FIT_SPORT)11)
#define FIT_SPORT_CROSS_COUNTRY_SKIING                                           ((FIT_SPORT)12)
#define FIT_SPORT_ALPINE_SKIING                                                  ((FIT_SPORT)13)
#define FIT_SPORT_SNOWBOARDING                                                   ((FIT_SPORT)14)
#define FIT_SPORT_ROWING                                                         ((FIT_SPORT)15)
#define FIT_SPORT_MOUNTAINEERING                                                 ((FIT_SPORT)16)
#define FIT_SPORT_HIKING                                                         ((FIT_SPORT)17)
#define FIT_SPORT_MULTISPORT                                                     ((FIT_SPORT)18)
#define FIT_SPORT_PADDLING                                                       ((FIT_SPORT)19)
#define FIT_SPORT_FLYING                                                         ((FIT_SPORT)20)
#define FIT_SPORT_E_BIKING                                                       ((FIT_SPORT)21)
#define FIT_SPORT_MOTORCYCLING                                                   ((FIT_SPORT)22)
#define FIT_SPORT_BOATING                                                        ((FIT_SPORT)23)
#define FIT_SPORT_DRIVING                                                        ((FIT_SPORT)24)
#define FIT_SPORT_GOLF                                                           ((FIT_SPORT)25)
#define FIT_SPORT_HANG_GLIDING                                                   ((FIT_SPORT)26)
#define FIT_SPORT_HORSEBACK_RIDING                                               ((FIT_SPORT)27)
#define FIT_SPORT_HUNTING                                                        ((FIT_SPORT)28)
#define FIT_SPORT_FISHING                                                        ((FIT_SPORT)29)
#define FIT_SPORT_INLINE_SKATING                                                 ((FIT_SPORT)30)
#define FIT_SPORT_ROCK_CLIMBING                                                  ((FIT_SPORT)31)
#define FIT_SPORT_SAILING                                                        ((FIT_SPORT)32)
#define FIT_SPORT_ICE_SKATING                                                    ((FIT_SPORT)33)
#define FIT_SPORT_SKY_DIVING                                                     ((FIT_SPORT)34)
#define FIT_SPORT_SNOWSHOEING                                                    ((FIT_SPORT)35)
#define FIT_SPORT_SNOWMOBILING                                                   ((FIT_SPORT)36)
#define FIT_SPORT_STAND_UP_PADDLEBOARDING                                        ((FIT_SPORT)37)
#define FIT_SPORT_SURFING                                                        ((FIT_SPORT)38)
#define FIT_SPORT_WAKEBOARDING                                                   ((FIT_SPORT)39)
#define FIT_SPORT_WATER_SKIING                                                   ((FIT_SPORT)40)
#define FIT_SPORT_KAYAKING                                                       ((FIT_SPORT)41)
#define FIT_SPORT_RAFTING                                                        ((FIT_SPORT)42)
#define FIT_SPORT_WINDSURFING                                                    ((FIT_SPORT)43)
#define FIT_SPORT_KITESURFING                                                    ((FIT_SPORT)44)
#define FIT_SPORT_TACTICAL                                                       ((FIT_SPORT)45)
#define FIT_SPORT_JUMPMASTER                                                     ((FIT_SPORT)46)
#define FIT_SPORT_BOXING                                                         ((FIT_SPORT)47)
#define FIT_SPORT_FLOOR_CLIMBING                                                 ((FIT_SPORT)48)
#define FIT_SPORT_ALL                                                            ((FIT_SPORT)254) // All is for goals only to include all sports.
#define FIT_SPORT_COUNT                                                          50

typedef FIT_UINT8Z FIT_SPORT_BITS_0; // Bit field corresponding to sport enum type (1 << sport).
#define FIT_SPORT_BITS_0_INVALID                                                 FIT_UINT8Z_INVALID
#define FIT_SPORT_BITS_0_GENERIC                                                 ((FIT_SPORT_BITS_0)0x01)
#define FIT_SPORT_BITS_0_RUNNING                                                 ((FIT_SPORT_BITS_0)0x02)
#define FIT_SPORT_BITS_0_CYCLING                                                 ((FIT_SPORT_BITS_0)0x04)
#define FIT_SPORT_BITS_0_TRANSITION                                              ((FIT_SPORT_BITS_0)0x08) // Mulitsport transition
#define FIT_SPORT_BITS_0_FITNESS_EQUIPMENT                                       ((FIT_SPORT_BITS_0)0x10)
#define FIT_SPORT_BITS_0_SWIMMING                                                ((FIT_SPORT_BITS_0)0x20)
#define FIT_SPORT_BITS_0_BASKETBALL                                              ((FIT_SPORT_BITS_0)0x40)
#define FIT_SPORT_BITS_0_SOCCER                                                  ((FIT_SPORT_BITS_0)0x80)
#define FIT_SPORT_BITS_0_COUNT                                                   8

typedef FIT_UINT8Z FIT_SPORT_BITS_1; // Bit field corresponding to sport enum type (1 << (sport-8)).
#define FIT_SPORT_BITS_1_INVALID                                                 FIT_UINT8Z_INVALID
#define FIT_SPORT_BITS_1_TENNIS                                                  ((FIT_SPORT_BITS_1)0x01)
#define FIT_SPORT_BITS_1_AMERICAN_FOOTBALL                                       ((FIT_SPORT_BITS_1)0x02)
#define FIT_SPORT_BITS_1_TRAINING                                                ((FIT_SPORT_BITS_1)0x04)
#define FIT_SPORT_BITS_1_WALKING                                                 ((FIT_SPORT_BITS_1)0x08)
#define FIT_SPORT_BITS_1_CROSS_COUNTRY_SKIING                                    ((FIT_SPORT_BITS_1)0x10)
#define FIT_SPORT_BITS_1_ALPINE_SKIING                                           ((FIT_SPORT_BITS_1)0x20)
#define FIT_SPORT_BITS_1_SNOWBOARDING                                            ((FIT_SPORT_BITS_1)0x40)
#define FIT_SPORT_BITS_1_ROWING                                                  ((FIT_SPORT_BITS_1)0x80)
#define FIT_SPORT_BITS_1_COUNT                                                   8

typedef FIT_UINT8Z FIT_SPORT_BITS_2; // Bit field corresponding to sport enum type (1 << (sport-16)).
#define FIT_SPORT_BITS_2_INVALID                                                 FIT_UINT8Z_INVALID
#define FIT_SPORT_BITS_2_MOUNTAINEERING                                          ((FIT_SPORT_BITS_2)0x01)
#define FIT_SPORT_BITS_2_HIKING                                                  ((FIT_SPORT_BITS_2)0x02)
#define FIT_SPORT_BITS_2_MULTISPORT                                              ((FIT_SPORT_BITS_2)0x04)
#define FIT_SPORT_BITS_2_PADDLING                                                ((FIT_SPORT_BITS_2)0x08)
#define FIT_SPORT_BITS_2_FLYING                                                  ((FIT_SPORT_BITS_2)0x10)
#define FIT_SPORT_BITS_2_E_BIKING                                                ((FIT_SPORT_BITS_2)0x20)
#define FIT_SPORT_BITS_2_MOTORCYCLING                                            ((FIT_SPORT_BITS_2)0x40)
#define FIT_SPORT_BITS_2_BOATING                                                 ((FIT_SPORT_BITS_2)0x80)
#define FIT_SPORT_BITS_2_COUNT                                                   8

typedef FIT_UINT8Z FIT_SPORT_BITS_3; // Bit field corresponding to sport enum type (1 << (sport-24)).
#define FIT_SPORT_BITS_3_INVALID                                                 FIT_UINT8Z_INVALID
#define FIT_SPORT_BITS_3_DRIVING                                                 ((FIT_SPORT_BITS_3)0x01)
#define FIT_SPORT_BITS_3_GOLF                                                    ((FIT_SPORT_BITS_3)0x02)
#define FIT_SPORT_BITS_3_HANG_GLIDING                                            ((FIT_SPORT_BITS_3)0x04)
#define FIT_SPORT_BITS_3_HORSEBACK_RIDING                                        ((FIT_SPORT_BITS_3)0x08)
#define FIT_SPORT_BITS_3_HUNTING                                                 ((FIT_SPORT_BITS_3)0x10)
#define FIT_SPORT_BITS_3_FISHING                                                 ((FIT_SPORT_BITS_3)0x20)
#define FIT_SPORT_BITS_3_INLINE_SKATING                                          ((FIT_SPORT_BITS_3)0x40)
#define FIT_SPORT_BITS_3_ROCK_CLIMBING                                           ((FIT_SPORT_BITS_3)0x80)
#define FIT_SPORT_BITS_3_COUNT                                                   8

typedef FIT_UINT8Z FIT_SPORT_BITS_4; // Bit field corresponding to sport enum type (1 << (sport-32)).
#define FIT_SPORT_BITS_4_INVALID                                                 FIT_UINT8Z_INVALID
#define FIT_SPORT_BITS_4_SAILING                                                 ((FIT_SPORT_BITS_4)0x01)
#define FIT_SPORT_BITS_4_ICE_SKATING                                             ((FIT_SPORT_BITS_4)0x02)
#define FIT_SPORT_BITS_4_SKY_DIVING                                              ((FIT_SPORT_BITS_4)0x04)
#define FIT_SPORT_BITS_4_SNOWSHOEING                                             ((FIT_SPORT_BITS_4)0x08)
#define FIT_SPORT_BITS_4_SNOWMOBILING                                            ((FIT_SPORT_BITS_4)0x10)
#define FIT_SPORT_BITS_4_STAND_UP_PADDLEBOARDING                                 ((FIT_SPORT_BITS_4)0x20)
#define FIT_SPORT_BITS_4_SURFING                                                 ((FIT_SPORT_BITS_4)0x40)
#define FIT_SPORT_BITS_4_WAKEBOARDING                                            ((FIT_SPORT_BITS_4)0x80)
#define FIT_SPORT_BITS_4_COUNT                                                   8

typedef FIT_UINT8Z FIT_SPORT_BITS_5; // Bit field corresponding to sport enum type (1 << (sport-40)).
#define FIT_SPORT_BITS_5_INVALID                                                 FIT_UINT8Z_INVALID
#define FIT_SPORT_BITS_5_WATER_SKIING                                            ((FIT_SPORT_BITS_5)0x01)
#define FIT_SPORT_BITS_5_KAYAKING                                                ((FIT_SPORT_BITS_5)0x02)
#define FIT_SPORT_BITS_5_RAFTING                                                 ((FIT_SPORT_BITS_5)0x04)
#define FIT_SPORT_BITS_5_WINDSURFING                                             ((FIT_SPORT_BITS_5)0x08)
#define FIT_SPORT_BITS_5_KITESURFING                                             ((FIT_SPORT_BITS_5)0x10)
#define FIT_SPORT_BITS_5_TACTICAL                                                ((FIT_SPORT_BITS_5)0x20)
#define FIT_SPORT_BITS_5_JUMPMASTER                                              ((FIT_SPORT_BITS_5)0x40)
#define FIT_SPORT_BITS_5_BOXING                                                  ((FIT_SPORT_BITS_5)0x80)
#define FIT_SPORT_BITS_5_COUNT                                                   8

typedef FIT_UINT8Z FIT_SPORT_BITS_6; // Bit field corresponding to sport enum type (1 << (sport-48)).
#define FIT_SPORT_BITS_6_INVALID                                                 FIT_UINT8Z_INVALID
#define FIT_SPORT_BITS_6_FLOOR_CLIMBING                                          ((FIT_SPORT_BITS_6)0x01)
#define FIT_SPORT_BITS_6_COUNT                                                   1

typedef FIT_ENUM FIT_SUB_SPORT;
#define FIT_SUB_SPORT_INVALID                                                    FIT_ENUM_INVALID
#define FIT_SUB_SPORT_GENERIC                                                    ((FIT_SUB_SPORT)0)
#define FIT_SUB_SPORT_TREADMILL                                                  ((FIT_SUB_SPORT)1) // Run/Fitness Equipment
#define FIT_SUB_SPORT_STREET                                                     ((FIT_SUB_SPORT)2) // Run
#define FIT_SUB_SPORT_TRAIL                                                      ((FIT_SUB_SPORT)3) // Run
#define FIT_SUB_SPORT_TRACK                                                      ((FIT_SUB_SPORT)4) // Run
#define FIT_SUB_SPORT_SPIN                                                       ((FIT_SUB_SPORT)5) // Cycling
#define FIT_SUB_SPORT_INDOOR_CYCLING                                             ((FIT_SUB_SPORT)6) // Cycling/Fitness Equipment
#define FIT_SUB_SPORT_ROAD                                                       ((FIT_SUB_SPORT)7) // Cycling
#define FIT_SUB_SPORT_MOUNTAIN                                                   ((FIT_SUB_SPORT)8) // Cycling
#define FIT_SUB_SPORT_DOWNHILL                                                   ((FIT_SUB_SPORT)9) // Cycling
#define FIT_SUB_SPORT_RECUMBENT                                                  ((FIT_SUB_SPORT)10) // Cycling
#define FIT_SUB_SPORT_CYCLOCROSS                                                 ((FIT_SUB_SPORT)11) // Cycling
#define FIT_SUB_SPORT_HAND_CYCLING                                               ((FIT_SUB_SPORT)12) // Cycling
#define FIT_SUB_SPORT_TRACK_CYCLING                                              ((FIT_SUB_SPORT)13) // Cycling
#define FIT_SUB_SPORT_INDOOR_ROWING                                              ((FIT_SUB_SPORT)14) // Fitness Equipment
#define FIT_SUB_SPORT_ELLIPTICAL                                                 ((FIT_SUB_SPORT)15) // Fitness Equipment
#define FIT_SUB_SPORT_STAIR_CLIMBING                                             ((FIT_SUB_SPORT)16) // Fitness Equipment
#define FIT_SUB_SPORT_LAP_SWIMMING                                               ((FIT_SUB_SPORT)17) // Swimming
#define FIT_SUB_SPORT_OPEN_WATER                                                 ((FIT_SUB_SPORT)18) // Swimming
#define FIT_SUB_SPORT_FLEXIBILITY_TRAINING                                       ((FIT_SUB_SPORT)19) // Training
#define FIT_SUB_SPORT_STRENGTH_TRAINING                                          ((FIT_SUB_SPORT)20) // Training
#define FIT_SUB_SPORT_WARM_UP                                                    ((FIT_SUB_SPORT)21) // Tennis
#define FIT_SUB_SPORT_MATCH                                                      ((FIT_SUB_SPORT)22) // Tennis
#define FIT_SUB_SPORT_EXERCISE                                                   ((FIT_SUB_SPORT)23) // Tennis
#define FIT_SUB_SPORT_CHALLENGE                                                  ((FIT_SUB_SPORT)24)
#define FIT_SUB_SPORT_INDOOR_SKIING                                              ((FIT_SUB_SPORT)25) // Fitness Equipment
#define FIT_SUB_SPORT_CARDIO_TRAINING                                            ((FIT_SUB_SPORT)26) // Training
#define FIT_SUB_SPORT_INDOOR_WALKING                                             ((FIT_SUB_SPORT)27) // Walking/Fitness Equipment
#define FIT_SUB_SPORT_E_BIKE_FITNESS                                             ((FIT_SUB_SPORT)28) // E-Biking
#define FIT_SUB_SPORT_BMX                                                        ((FIT_SUB_SPORT)29) // Cycling
#define FIT_SUB_SPORT_CASUAL_WALKING                                             ((FIT_SUB_SPORT)30) // Walking
#define FIT_SUB_SPORT_SPEED_WALKING                                              ((FIT_SUB_SPORT)31) // Walking
#define FIT_SUB_SPORT_BIKE_TO_RUN_TRANSITION                                     ((FIT_SUB_SPORT)32) // Transition
#define FIT_SUB_SPORT_RUN_TO_BIKE_TRANSITION                                     ((FIT_SUB_SPORT)33) // Transition
#define FIT_SUB_SPORT_SWIM_TO_BIKE_TRANSITION                                    ((FIT_SUB_SPORT)34) // Transition
#define FIT_SUB_SPORT_ATV                                                        ((FIT_SUB_SPORT)35) // Motorcycling
#define FIT_SUB_SPORT_MOTOCROSS                                                  ((FIT_SUB_SPORT)36) // Motorcycling
#define FIT_SUB_SPORT_BACKCOUNTRY                                                ((FIT_SUB_SPORT)37) // Alpine Skiing/Snowboarding
#define FIT_SUB_SPORT_RESORT                                                     ((FIT_SUB_SPORT)38) // Alpine Skiing/Snowboarding
#define FIT_SUB_SPORT_RC_DRONE                                                   ((FIT_SUB_SPORT)39) // Flying
#define FIT_SUB_SPORT_WINGSUIT                                                   ((FIT_SUB_SPORT)40) // Flying
#define FIT_SUB_SPORT_WHITEWATER                                                 ((FIT_SUB_SPORT)41) // Kayaking/Rafting
#define FIT_SUB_SPORT_SKATE_SKIING                                               ((FIT_SUB_SPORT)42) // Cross Country Skiing
#define FIT_SUB_SPORT_YOGA                                                       ((FIT_SUB_SPORT)43) // Training
#define FIT_SUB_SPORT_PILATES                                                    ((FIT_SUB_SPORT)44) // Training
#define FIT_SUB_SPORT_INDOOR_RUNNING                                             ((FIT_SUB_SPORT)45) // Run
#define FIT_SUB_SPORT_GRAVEL_CYCLING                                             ((FIT_SUB_SPORT)46) // Cycling
#define FIT_SUB_SPORT_E_BIKE_MOUNTAIN                                            ((FIT_SUB_SPORT)47) // Cycling
#define FIT_SUB_SPORT_COMMUTING                                                  ((FIT_SUB_SPORT)48) // Cycling
#define FIT_SUB_SPORT_MIXED_SURFACE                                              ((FIT_SUB_SPORT)49) // Cycling
#define FIT_SUB_SPORT_NAVIGATE                                                   ((FIT_SUB_SPORT)50)
#define FIT_SUB_SPORT_TRACK_ME                                                   ((FIT_SUB_SPORT)51)
#define FIT_SUB_SPORT_MAP                                                        ((FIT_SUB_SPORT)52)
#define FIT_SUB_SPORT_SINGLE_GAS_DIVING                                          ((FIT_SUB_SPORT)53) // Diving
#define FIT_SUB_SPORT_MULTI_GAS_DIVING                                           ((FIT_SUB_SPORT)54) // Diving
#define FIT_SUB_SPORT_GAUGE_DIVING                                               ((FIT_SUB_SPORT)55) // Diving
#define FIT_SUB_SPORT_APNEA_DIVING                                               ((FIT_SUB_SPORT)56) // Diving
#define FIT_SUB_SPORT_APNEA_HUNTING                                              ((FIT_SUB_SPORT)57) // Diving
#define FIT_SUB_SPORT_VIRTUAL_ACTIVITY                                           ((FIT_SUB_SPORT)58)
#define FIT_SUB_SPORT_OBSTACLE                                                   ((FIT_SUB_SPORT)59) // Used for events where participants run, crawl through mud, climb over walls, etc.
#define FIT_SUB_SPORT_ALL                                                        ((FIT_SUB_SPORT)254)
#define FIT_SUB_SPORT_COUNT                                                      61

typedef FIT_ENUM FIT_SPORT_EVENT;
#define FIT_SPORT_EVENT_INVALID                                                  FIT_ENUM_INVALID
#define FIT_SPORT_EVENT_UNCATEGORIZED                                            ((FIT_SPORT_EVENT)0)
#define FIT_SPORT_EVENT_GEOCACHING                                               ((FIT_SPORT_EVENT)1)
#define FIT_SPORT_EVENT_FITNESS                                                  ((FIT_SPORT_EVENT)2)
#define FIT_SPORT_EVENT_RECREATION                                               ((FIT_SPORT_EVENT)3)
#define FIT_SPORT_EVENT_RACE                                                     ((FIT_SPORT_EVENT)4)
#define FIT_SPORT_EVENT_SPECIAL_EVENT                                            ((FIT_SPORT_EVENT)5)
#define FIT_SPORT_EVENT_TRAINING                                                 ((FIT_SPORT_EVENT)6)
#define FIT_SPORT_EVENT_TRANSPORTATION                                           ((FIT_SPORT_EVENT)7)
#define FIT_SPORT_EVENT_TOURING                                                  ((FIT_SPORT_EVENT)8)
#define FIT_SPORT_EVENT_COUNT                                                    9

typedef FIT_ENUM FIT_ACTIVITY;
#define FIT_ACTIVITY_INVALID                                                     FIT_ENUM_INVALID
#define FIT_ACTIVITY_MANUAL                                                      ((FIT_ACTIVITY)0)
#define FIT_ACTIVITY_AUTO_MULTI_SPORT                                            ((FIT_ACTIVITY)1)
#define FIT_ACTIVITY_COUNT                                                       2

typedef FIT_ENUM FIT_INTENSITY;
#define FIT_INTENSITY_INVALID                                                    FIT_ENUM_INVALID
#define FIT_INTENSITY_ACTIVE                                                     ((FIT_INTENSITY)0)
#define FIT_INTENSITY_REST                                                       ((FIT_INTENSITY)1)
#define FIT_INTENSITY_WARMUP                                                     ((FIT_INTENSITY)2)
#define FIT_INTENSITY_COOLDOWN                                                   ((FIT_INTENSITY)3)
#define FIT_INTENSITY_COUNT                                                      4

typedef FIT_ENUM FIT_SESSION_TRIGGER;
#define FIT_SESSION_TRIGGER_INVALID                                              FIT_ENUM_INVALID
#define FIT_SESSION_TRIGGER_ACTIVITY_END                                         ((FIT_SESSION_TRIGGER)0)
#define FIT_SESSION_TRIGGER_MANUAL                                               ((FIT_SESSION_TRIGGER)1) // User changed sport.
#define FIT_SESSION_TRIGGER_AUTO_MULTI_SPORT                                     ((FIT_SESSION_TRIGGER)2) // Auto multi-sport feature is enabled and user pressed lap button to advance session.
#define FIT_SESSION_TRIGGER_FITNESS_EQUIPMENT                                    ((FIT_SESSION_TRIGGER)3) // Auto sport change caused by user linking to fitness equipment.
#define FIT_SESSION_TRIGGER_COUNT                                                4

typedef FIT_ENUM FIT_AUTOLAP_TRIGGER;
#define FIT_AUTOLAP_TRIGGER_INVALID                                              FIT_ENUM_INVALID
#define FIT_AUTOLAP_TRIGGER_TIME                                                 ((FIT_AUTOLAP_TRIGGER)0)
#define FIT_AUTOLAP_TRIGGER_DISTANCE                                             ((FIT_AUTOLAP_TRIGGER)1)
#define FIT_AUTOLAP_TRIGGER_POSITION_START                                       ((FIT_AUTOLAP_TRIGGER)2)
#define FIT_AUTOLAP_TRIGGER_POSITION_LAP                                         ((FIT_AUTOLAP_TRIGGER)3)
#define FIT_AUTOLAP_TRIGGER_POSITION_WAYPOINT                                    ((FIT_AUTOLAP_TRIGGER)4)
#define FIT_AUTOLAP_TRIGGER_POSITION_MARKED                                      ((FIT_AUTOLAP_TRIGGER)5)
#define FIT_AUTOLAP_TRIGGER_OFF                                                  ((FIT_AUTOLAP_TRIGGER)6)
#define FIT_AUTOLAP_TRIGGER_COUNT                                                7

typedef FIT_ENUM FIT_LAP_TRIGGER;
#define FIT_LAP_TRIGGER_INVALID                                                  FIT_ENUM_INVALID
#define FIT_LAP_TRIGGER_MANUAL                                                   ((FIT_LAP_TRIGGER)0)
#define FIT_LAP_TRIGGER_TIME                                                     ((FIT_LAP_TRIGGER)1)
#define FIT_LAP_TRIGGER_DISTANCE                                                 ((FIT_LAP_TRIGGER)2)
#define FIT_LAP_TRIGGER_POSITION_START                                           ((FIT_LAP_TRIGGER)3)
#define FIT_LAP_TRIGGER_POSITION_LAP                                             ((FIT_LAP_TRIGGER)4)
#define FIT_LAP_TRIGGER_POSITION_WAYPOINT                                        ((FIT_LAP_TRIGGER)5)
#define FIT_LAP_TRIGGER_POSITION_MARKED                                          ((FIT_LAP_TRIGGER)6)
#define FIT_LAP_TRIGGER_SESSION_END                                              ((FIT_LAP_TRIGGER)7)
#define FIT_LAP_TRIGGER_FITNESS_EQUIPMENT                                        ((FIT_LAP_TRIGGER)8)
#define FIT_LAP_TRIGGER_COUNT                                                    9

typedef FIT_ENUM FIT_TIME_MODE;
#define FIT_TIME_MODE_INVALID                                                    FIT_ENUM_INVALID
#define FIT_TIME_MODE_HOUR12                                                     ((FIT_TIME_MODE)0)
#define FIT_TIME_MODE_HOUR24                                                     ((FIT_TIME_MODE)1) // Does not use a leading zero and has a colon
#define FIT_TIME_MODE_MILITARY                                                   ((FIT_TIME_MODE)2) // Uses a leading zero and does not have a colon
#define FIT_TIME_MODE_HOUR_12_WITH_SECONDS                                       ((FIT_TIME_MODE)3)
#define FIT_TIME_MODE_HOUR_24_WITH_SECONDS                                       ((FIT_TIME_MODE)4)
#define FIT_TIME_MODE_UTC                                                        ((FIT_TIME_MODE)5)
#define FIT_TIME_MODE_COUNT                                                      6

typedef FIT_ENUM FIT_BACKLIGHT_MODE;
#define FIT_BACKLIGHT_MODE_INVALID                                               FIT_ENUM_INVALID
#define FIT_BACKLIGHT_MODE_OFF                                                   ((FIT_BACKLIGHT_MODE)0)
#define FIT_BACKLIGHT_MODE_MANUAL                                                ((FIT_BACKLIGHT_MODE)1)
#define FIT_BACKLIGHT_MODE_KEY_AND_MESSAGES                                      ((FIT_BACKLIGHT_MODE)2)
#define FIT_BACKLIGHT_MODE_AUTO_BRIGHTNESS                                       ((FIT_BACKLIGHT_MODE)3)
#define FIT_BACKLIGHT_MODE_SMART_NOTIFICATIONS                                   ((FIT_BACKLIGHT_MODE)4)
#define FIT_BACKLIGHT_MODE_KEY_AND_MESSAGES_NIGHT                                ((FIT_BACKLIGHT_MODE)5)
#define FIT_BACKLIGHT_MODE_KEY_AND_MESSAGES_AND_SMART_NOTIFICATIONS              ((FIT_BACKLIGHT_MODE)6)
#define FIT_BACKLIGHT_MODE_COUNT                                                 7

typedef FIT_ENUM FIT_DATE_MODE;
#define FIT_DATE_MODE_INVALID                                                    FIT_ENUM_INVALID
#define FIT_DATE_MODE_DAY_MONTH                                                  ((FIT_DATE_MODE)0)
#define FIT_DATE_MODE_MONTH_DAY                                                  ((FIT_DATE_MODE)1)
#define FIT_DATE_MODE_COUNT                                                      2

typedef FIT_UINT8 FIT_BACKLIGHT_TIMEOUT; // Timeout in seconds.
#define FIT_BACKLIGHT_TIMEOUT_INVALID                                            FIT_UINT8_INVALID
#define FIT_BACKLIGHT_TIMEOUT_INFINITE                                           ((FIT_BACKLIGHT_TIMEOUT)0) // Backlight stays on forever.
#define FIT_BACKLIGHT_TIMEOUT_COUNT                                              1

typedef FIT_ENUM FIT_EVENT;
#define FIT_EVENT_INVALID                                                        FIT_ENUM_INVALID
#define FIT_EVENT_TIMER                                                          ((FIT_EVENT)0) // Group 0.  Start / stop_all
#define FIT_EVENT_WORKOUT                                                        ((FIT_EVENT)3) // start / stop
#define FIT_EVENT_WORKOUT_STEP                                                   ((FIT_EVENT)4) // Start at beginning of workout.  Stop at end of each step.
#define FIT_EVENT_POWER_DOWN                                                     ((FIT_EVENT)5) // stop_all group 0
#define FIT_EVENT_POWER_UP                                                       ((FIT_EVENT)6) // stop_all group 0
#define FIT_EVENT_OFF_COURSE                                                     ((FIT_EVENT)7) // start / stop group 0
#define FIT_EVENT_SESSION                                                        ((FIT_EVENT)8) // Stop at end of each session.
#define FIT_EVENT_LAP                                                            ((FIT_EVENT)9) // Stop at end of each lap.
#define FIT_EVENT_COURSE_POINT                                                   ((FIT_EVENT)10) // marker
#define FIT_EVENT_BATTERY                                                        ((FIT_EVENT)11) // marker
#define FIT_EVENT_VIRTUAL_PARTNER_PACE                                           ((FIT_EVENT)12) // Group 1. Start at beginning of activity if VP enabled, when VP pace is changed during activity or VP enabled mid activity.  stop_disable when VP disabled.
#define FIT_EVENT_HR_HIGH_ALERT                                                  ((FIT_EVENT)13) // Group 0.  Start / stop when in alert condition.
#define FIT_EVENT_HR_LOW_ALERT                                                   ((FIT_EVENT)14) // Group 0.  Start / stop when in alert condition.
#define FIT_EVENT_SPEED_HIGH_ALERT                                               ((FIT_EVENT)15) // Group 0.  Start / stop when in alert condition.
#define FIT_EVENT_SPEED_LOW_ALERT                                                ((FIT_EVENT)16) // Group 0.  Start / stop when in alert condition.
#define FIT_EVENT_CAD_HIGH_ALERT                                                 ((FIT_EVENT)17) // Group 0.  Start / stop when in alert condition.
#define FIT_EVENT_CAD_LOW_ALERT                                                  ((FIT_EVENT)18) // Group 0.  Start / stop when in alert condition.
#define FIT_EVENT_POWER_HIGH_ALERT                                               ((FIT_EVENT)19) // Group 0.  Start / stop when in alert condition.
#define FIT_EVENT_POWER_LOW_ALERT                                                ((FIT_EVENT)20) // Group 0.  Start / stop when in alert condition.
#define FIT_EVENT_RECOVERY_HR                                                    ((FIT_EVENT)21) // marker
#define FIT_EVENT_BATTERY_LOW                                                    ((FIT_EVENT)22) // marker
#define FIT_EVENT_TIME_DURATION_ALERT                                            ((FIT_EVENT)23) // Group 1.  Start if enabled mid activity (not required at start of activity). Stop when duration is reached.  stop_disable if disabled.
#define FIT_EVENT_DISTANCE_DURATION_ALERT                                        ((FIT_EVENT)24) // Group 1.  Start if enabled mid activity (not required at start of activity). Stop when duration is reached.  stop_disable if disabled.
#define FIT_EVENT_CALORIE_DURATION_ALERT                                         ((FIT_EVENT)25) // Group 1.  Start if enabled mid activity (not required at start of activity). Stop when duration is reached.  stop_disable if disabled.
#define FIT_EVENT_ACTIVITY                                                       ((FIT_EVENT)26) // Group 1..  Stop at end of activity.
#define FIT_EVENT_FITNESS_EQUIPMENT                                              ((FIT_EVENT)27) // marker
#define FIT_EVENT_LENGTH                                                         ((FIT_EVENT)28) // Stop at end of each length.
#define FIT_EVENT_USER_MARKER                                                    ((FIT_EVENT)32) // marker
#define FIT_EVENT_SPORT_POINT                                                    ((FIT_EVENT)33) // marker
#define FIT_EVENT_CALIBRATION                                                    ((FIT_EVENT)36) // start/stop/marker
#define FIT_EVENT_FRONT_GEAR_CHANGE                                              ((FIT_EVENT)42) // marker
#define FIT_EVENT_REAR_GEAR_CHANGE                                               ((FIT_EVENT)43) // marker
#define FIT_EVENT_RIDER_POSITION_CHANGE                                          ((FIT_EVENT)44) // marker
#define FIT_EVENT_ELEV_HIGH_ALERT                                                ((FIT_EVENT)45) // Group 0.  Start / stop when in alert condition.
#define FIT_EVENT_ELEV_LOW_ALERT                                                 ((FIT_EVENT)46) // Group 0.  Start / stop when in alert condition.
#define FIT_EVENT_COMM_TIMEOUT                                                   ((FIT_EVENT)47) // marker
#define FIT_EVENT_COUNT                                                          36

typedef FIT_ENUM FIT_EVENT_TYPE;
#define FIT_EVENT_TYPE_INVALID                                                   FIT_ENUM_INVALID
#define FIT_EVENT_TYPE_START                                                     ((FIT_EVENT_TYPE)0)
#define FIT_EVENT_TYPE_STOP                                                      ((FIT_EVENT_TYPE)1)
#define FIT_EVENT_TYPE_CONSECUTIVE_DEPRECIATED                                   ((FIT_EVENT_TYPE)2)
#define FIT_EVENT_TYPE_MARKER                                                    ((FIT_EVENT_TYPE)3)
#define FIT_EVENT_TYPE_STOP_ALL                                                  ((FIT_EVENT_TYPE)4)
#define FIT_EVENT_TYPE_BEGIN_DEPRECIATED                                         ((FIT_EVENT_TYPE)5)
#define FIT_EVENT_TYPE_END_DEPRECIATED                                           ((FIT_EVENT_TYPE)6)
#define FIT_EVENT_TYPE_END_ALL_DEPRECIATED                                       ((FIT_EVENT_TYPE)7)
#define FIT_EVENT_TYPE_STOP_DISABLE                                              ((FIT_EVENT_TYPE)8)
#define FIT_EVENT_TYPE_STOP_DISABLE_ALL                                          ((FIT_EVENT_TYPE)9)
#define FIT_EVENT_TYPE_COUNT                                                     10

typedef FIT_ENUM FIT_TIMER_TRIGGER; // timer event data
#define FIT_TIMER_TRIGGER_INVALID                                                FIT_ENUM_INVALID
#define FIT_TIMER_TRIGGER_MANUAL                                                 ((FIT_TIMER_TRIGGER)0)
#define FIT_TIMER_TRIGGER_AUTO                                                   ((FIT_TIMER_TRIGGER)1)
#define FIT_TIMER_TRIGGER_FITNESS_EQUIPMENT                                      ((FIT_TIMER_TRIGGER)2)
#define FIT_TIMER_TRIGGER_COUNT                                                  3

typedef FIT_ENUM FIT_FITNESS_EQUIPMENT_STATE; // fitness equipment event data
#define FIT_FITNESS_EQUIPMENT_STATE_INVALID                                      FIT_ENUM_INVALID
#define FIT_FITNESS_EQUIPMENT_STATE_READY                                        ((FIT_FITNESS_EQUIPMENT_STATE)0)
#define FIT_FITNESS_EQUIPMENT_STATE_IN_USE                                       ((FIT_FITNESS_EQUIPMENT_STATE)1)
#define FIT_FITNESS_EQUIPMENT_STATE_PAUSED                                       ((FIT_FITNESS_EQUIPMENT_STATE)2)
#define FIT_FITNESS_EQUIPMENT_STATE_UNKNOWN                                      ((FIT_FITNESS_EQUIPMENT_STATE)3) // lost connection to fitness equipment
#define FIT_FITNESS_EQUIPMENT_STATE_COUNT                                        4

typedef FIT_ENUM FIT_TONE;
#define FIT_TONE_INVALID                                                         FIT_ENUM_INVALID
#define FIT_TONE_OFF                                                             ((FIT_TONE)0)
#define FIT_TONE_TONE                                                            ((FIT_TONE)1)
#define FIT_TONE_VIBRATE                                                         ((FIT_TONE)2)
#define FIT_TONE_TONE_AND_VIBRATE                                                ((FIT_TONE)3)
#define FIT_TONE_COUNT                                                           4

typedef FIT_ENUM FIT_AUTOSCROLL;
#define FIT_AUTOSCROLL_INVALID                                                   FIT_ENUM_INVALID
#define FIT_AUTOSCROLL_NONE                                                      ((FIT_AUTOSCROLL)0)
#define FIT_AUTOSCROLL_SLOW                                                      ((FIT_AUTOSCROLL)1)
#define FIT_AUTOSCROLL_MEDIUM                                                    ((FIT_AUTOSCROLL)2)
#define FIT_AUTOSCROLL_FAST                                                      ((FIT_AUTOSCROLL)3)
#define FIT_AUTOSCROLL_COUNT                                                     4

typedef FIT_ENUM FIT_ACTIVITY_CLASS;
#define FIT_ACTIVITY_CLASS_INVALID                                               FIT_ENUM_INVALID
#define FIT_ACTIVITY_CLASS_LEVEL                                                 ((FIT_ACTIVITY_CLASS)0x7F) // 0 to 100
#define FIT_ACTIVITY_CLASS_LEVEL_MAX                                             ((FIT_ACTIVITY_CLASS)100)
#define FIT_ACTIVITY_CLASS_ATHLETE                                               ((FIT_ACTIVITY_CLASS)0x80)
#define FIT_ACTIVITY_CLASS_COUNT                                                 3

typedef FIT_ENUM FIT_HR_ZONE_CALC;
#define FIT_HR_ZONE_CALC_INVALID                                                 FIT_ENUM_INVALID
#define FIT_HR_ZONE_CALC_CUSTOM                                                  ((FIT_HR_ZONE_CALC)0)
#define FIT_HR_ZONE_CALC_PERCENT_MAX_HR                                          ((FIT_HR_ZONE_CALC)1)
#define FIT_HR_ZONE_CALC_PERCENT_HRR                                             ((FIT_HR_ZONE_CALC)2)
#define FIT_HR_ZONE_CALC_COUNT                                                   3

typedef FIT_ENUM FIT_PWR_ZONE_CALC;
#define FIT_PWR_ZONE_CALC_INVALID                                                FIT_ENUM_INVALID
#define FIT_PWR_ZONE_CALC_CUSTOM                                                 ((FIT_PWR_ZONE_CALC)0)
#define FIT_PWR_ZONE_CALC_PERCENT_FTP                                            ((FIT_PWR_ZONE_CALC)1)
#define FIT_PWR_ZONE_CALC_COUNT                                                  2

typedef FIT_ENUM FIT_WKT_STEP_DURATION;
#define FIT_WKT_STEP_DURATION_INVALID                                            FIT_ENUM_INVALID
#define FIT_WKT_STEP_DURATION_TIME                                               ((FIT_WKT_STEP_DURATION)0)
#define FIT_WKT_STEP_DURATION_DISTANCE                                           ((FIT_WKT_STEP_DURATION)1)
#define FIT_WKT_STEP_DURATION_HR_LESS_THAN                                       ((FIT_WKT_STEP_DURATION)2)
#define FIT_WKT_STEP_DURATION_HR_GREATER_THAN                                    ((FIT_WKT_STEP_DURATION)3)
#define FIT_WKT_STEP_DURATION_CALORIES                                           ((FIT_WKT_STEP_DURATION)4)
#define FIT_WKT_STEP_DURATION_OPEN                                               ((FIT_WKT_STEP_DURATION)5)
#define FIT_WKT_STEP_DURATION_REPEAT_UNTIL_STEPS_CMPLT                           ((FIT_WKT_STEP_DURATION)6)
#define FIT_WKT_STEP_DURATION_REPEAT_UNTIL_TIME                                  ((FIT_WKT_STEP_DURATION)7)
#define FIT_WKT_STEP_DURATION_REPEAT_UNTIL_DISTANCE                              ((FIT_WKT_STEP_DURATION)8)
#define FIT_WKT_STEP_DURATION_REPEAT_UNTIL_CALORIES                              ((FIT_WKT_STEP_DURATION)9)
#define FIT_WKT_STEP_DURATION_REPEAT_UNTIL_HR_LESS_THAN                          ((FIT_WKT_STEP_DURATION)10)
#define FIT_WKT_STEP_DURATION_REPEAT_UNTIL_HR_GREATER_THAN                       ((FIT_WKT_STEP_DURATION)11)
#define FIT_WKT_STEP_DURATION_REPEAT_UNTIL_POWER_LESS_THAN                       ((FIT_WKT_STEP_DURATION)12)
#define FIT_WKT_STEP_DURATION_REPEAT_UNTIL_POWER_GREATER_THAN                    ((FIT_WKT_STEP_DURATION)13)
#define FIT_WKT_STEP_DURATION_POWER_LESS_THAN                                    ((FIT_WKT_STEP_DURATION)14)
#define FIT_WKT_STEP_DURATION_POWER_GREATER_THAN                                 ((FIT_WKT_STEP_DURATION)15)
#define FIT_WKT_STEP_DURATION_TRAINING_PEAKS_TSS                                 ((FIT_WKT_STEP_DURATION)16)
#define FIT_WKT_STEP_DURATION_REPEAT_UNTIL_POWER_LAST_LAP_LESS_THAN              ((FIT_WKT_STEP_DURATION)17)
#define FIT_WKT_STEP_DURATION_REPEAT_UNTIL_MAX_POWER_LAST_LAP_LESS_THAN          ((FIT_WKT_STEP_DURATION)18)
#define FIT_WKT_STEP_DURATION_POWER_3S_LESS_THAN                                 ((FIT_WKT_STEP_DURATION)19)
#define FIT_WKT_STEP_DURATION_POWER_10S_LESS_THAN                                ((FIT_WKT_STEP_DURATION)20)
#define FIT_WKT_STEP_DURATION_POWER_30S_LESS_THAN                                ((FIT_WKT_STEP_DURATION)21)
#define FIT_WKT_STEP_DURATION_POWER_3S_GREATER_THAN                              ((FIT_WKT_STEP_DURATION)22)
#define FIT_WKT_STEP_DURATION_POWER_10S_GREATER_THAN                             ((FIT_WKT_STEP_DURATION)23)
#define FIT_WKT_STEP_DURATION_POWER_30S_GREATER_THAN                             ((FIT_WKT_STEP_DURATION)24)
#define FIT_WKT_STEP_DURATION_POWER_LAP_LESS_THAN                                ((FIT_WKT_STEP_DURATION)25)
#define FIT_WKT_STEP_DURATION_POWER_LAP_GREATER_THAN                             ((FIT_WKT_STEP_DURATION)26)
#define FIT_WKT_STEP_DURATION_REPEAT_UNTIL_TRAINING_PEAKS_TSS                    ((FIT_WKT_STEP_DURATION)27)
#define FIT_WKT_STEP_DURATION_REPETITION_TIME                                    ((FIT_WKT_STEP_DURATION)28)
#define FIT_WKT_STEP_DURATION_REPS                                               ((FIT_WKT_STEP_DURATION)29)
#define FIT_WKT_STEP_DURATION_COUNT                                              30

typedef FIT_ENUM FIT_WKT_STEP_TARGET;
#define FIT_WKT_STEP_TARGET_INVALID                                              FIT_ENUM_INVALID
#define FIT_WKT_STEP_TARGET_SPEED                                                ((FIT_WKT_STEP_TARGET)0)
#define FIT_WKT_STEP_TARGET_HEART_RATE                                           ((FIT_WKT_STEP_TARGET)1)
#define FIT_WKT_STEP_TARGET_OPEN                                                 ((FIT_WKT_STEP_TARGET)2)
#define FIT_WKT_STEP_TARGET_CADENCE                                              ((FIT_WKT_STEP_TARGET)3)
#define FIT_WKT_STEP_TARGET_POWER                                                ((FIT_WKT_STEP_TARGET)4)
#define FIT_WKT_STEP_TARGET_GRADE                                                ((FIT_WKT_STEP_TARGET)5)
#define FIT_WKT_STEP_TARGET_RESISTANCE                                           ((FIT_WKT_STEP_TARGET)6)
#define FIT_WKT_STEP_TARGET_POWER_3S                                             ((FIT_WKT_STEP_TARGET)7)
#define FIT_WKT_STEP_TARGET_POWER_10S                                            ((FIT_WKT_STEP_TARGET)8)
#define FIT_WKT_STEP_TARGET_POWER_30S                                            ((FIT_WKT_STEP_TARGET)9)
#define FIT_WKT_STEP_TARGET_POWER_LAP                                            ((FIT_WKT_STEP_TARGET)10)
#define FIT_WKT_STEP_TARGET_SWIM_STROKE                                          ((FIT_WKT_STEP_TARGET)11)
#define FIT_WKT_STEP_TARGET_SPEED_LAP                                            ((FIT_WKT_STEP_TARGET)12)
#define FIT_WKT_STEP_TARGET_HEART_RATE_LAP                                       ((FIT_WKT_STEP_TARGET)13)
#define FIT_WKT_STEP_TARGET_COUNT                                                14

typedef FIT_ENUM FIT_GOAL;
#define FIT_GOAL_INVALID                                                         FIT_ENUM_INVALID
#define FIT_GOAL_TIME                                                            ((FIT_GOAL)0)
#define FIT_GOAL_DISTANCE                                                        ((FIT_GOAL)1)
#define FIT_GOAL_CALORIES                                                        ((FIT_GOAL)2)
#define FIT_GOAL_FREQUENCY                                                       ((FIT_GOAL)3)
#define FIT_GOAL_STEPS                                                           ((FIT_GOAL)4)
#define FIT_GOAL_ASCENT                                                          ((FIT_GOAL)5)
#define FIT_GOAL_ACTIVE_MINUTES                                                  ((FIT_GOAL)6)
#define FIT_GOAL_COUNT                                                           7

typedef FIT_ENUM FIT_GOAL_RECURRENCE;
#define FIT_GOAL_RECURRENCE_INVALID                                              FIT_ENUM_INVALID
#define FIT_GOAL_RECURRENCE_OFF                                                  ((FIT_GOAL_RECURRENCE)0)
#define FIT_GOAL_RECURRENCE_DAILY                                                ((FIT_GOAL_RECURRENCE)1)
#define FIT_GOAL_RECURRENCE_WEEKLY                                               ((FIT_GOAL_RECURRENCE)2)
#define FIT_GOAL_RECURRENCE_MONTHLY                                              ((FIT_GOAL_RECURRENCE)3)
#define FIT_GOAL_RECURRENCE_YEARLY                                               ((FIT_GOAL_RECURRENCE)4)
#define FIT_GOAL_RECURRENCE_CUSTOM                                               ((FIT_GOAL_RECURRENCE)5)
#define FIT_GOAL_RECURRENCE_COUNT                                                6

typedef FIT_ENUM FIT_GOAL_SOURCE;
#define FIT_GOAL_SOURCE_INVALID                                                  FIT_ENUM_INVALID
#define FIT_GOAL_SOURCE_AUTO                                                     ((FIT_GOAL_SOURCE)0) // Device generated
#define FIT_GOAL_SOURCE_COMMUNITY                                                ((FIT_GOAL_SOURCE)1) // Social network sourced goal
#define FIT_GOAL_SOURCE_USER                                                     ((FIT_GOAL_SOURCE)2) // Manually generated
#define FIT_GOAL_SOURCE_COUNT                                                    3

typedef FIT_ENUM FIT_SCHEDULE;
#define FIT_SCHEDULE_INVALID                                                     FIT_ENUM_INVALID
#define FIT_SCHEDULE_WORKOUT                                                     ((FIT_SCHEDULE)0)
#define FIT_SCHEDULE_COURSE                                                      ((FIT_SCHEDULE)1)
#define FIT_SCHEDULE_COUNT                                                       2

typedef FIT_ENUM FIT_COURSE_POINT;
#define FIT_COURSE_POINT_INVALID                                                 FIT_ENUM_INVALID
#define FIT_COURSE_POINT_GENERIC                                                 ((FIT_COURSE_POINT)0)
#define FIT_COURSE_POINT_SUMMIT                                                  ((FIT_COURSE_POINT)1)
#define FIT_COURSE_POINT_VALLEY                                                  ((FIT_COURSE_POINT)2)
#define FIT_COURSE_POINT_WATER                                                   ((FIT_COURSE_POINT)3)
#define FIT_COURSE_POINT_FOOD                                                    ((FIT_COURSE_POINT)4)
#define FIT_COURSE_POINT_DANGER                                                  ((FIT_COURSE_POINT)5)
#define FIT_COURSE_POINT_LEFT                                                    ((FIT_COURSE_POINT)6)
#define FIT_COURSE_POINT_RIGHT                                                   ((FIT_COURSE_POINT)7)
#define FIT_COURSE_POINT_STRAIGHT                                                ((FIT_COURSE_POINT)8)
#define FIT_COURSE_POINT_FIRST_AID                                               ((FIT_COURSE_POINT)9)
#define FIT_COURSE_POINT_FOURTH_CATEGORY                                         ((FIT_COURSE_POINT)10)
#define FIT_COURSE_POINT_THIRD_CATEGORY                                          ((FIT_COURSE_POINT)11)
#define FIT_COURSE_POINT_SECOND_CATEGORY                                         ((FIT_COURSE_POINT)12)
#define FIT_COURSE_POINT_FIRST_CATEGORY                                          ((FIT_COURSE_POINT)13)
#define FIT_COURSE_POINT_HORS_CATEGORY                                           ((FIT_COURSE_POINT)14)
#define FIT_COURSE_POINT_SPRINT                                                  ((FIT_COURSE_POINT)15)
#define FIT_COURSE_POINT_LEFT_FORK                                               ((FIT_COURSE_POINT)16)
#define FIT_COURSE_POINT_RIGHT_FORK                                              ((FIT_COURSE_POINT)17)
#define FIT_COURSE_POINT_MIDDLE_FORK                                             ((FIT_COURSE_POINT)18)
#define FIT_COURSE_POINT_SLIGHT_LEFT                                             ((FIT_COURSE_POINT)19)
#define FIT_COURSE_POINT_SHARP_LEFT                                              ((FIT_COURSE_POINT)20)
#define FIT_COURSE_POINT_SLIGHT_RIGHT                                            ((FIT_COURSE_POINT)21)
#define FIT_COURSE_POINT_SHARP_RIGHT                                             ((FIT_COURSE_POINT)22)
#define FIT_COURSE_POINT_U_TURN                                                  ((FIT_COURSE_POINT)23)
#define FIT_COURSE_POINT_SEGMENT_START                                           ((FIT_COURSE_POINT)24)
#define FIT_COURSE_POINT_SEGMENT_END                                             ((FIT_COURSE_POINT)25)
#define FIT_COURSE_POINT_COUNT                                                   26

typedef FIT_UINT16 FIT_MANUFACTURER;
#define FIT_MANUFACTURER_INVALID                                                 FIT_UINT16_INVALID
#define FIT_MANUFACTURER_GARMIN                                                  ((FIT_MANUFACTURER)1)
#define FIT_MANUFACTURER_GARMIN_FR405_ANTFS                                      ((FIT_MANUFACTURER)2) // Do not use.  Used by FR405 for ANTFS man id.
#define FIT_MANUFACTURER_ZEPHYR                                                  ((FIT_MANUFACTURER)3)
#define FIT_MANUFACTURER_DAYTON                                                  ((FIT_MANUFACTURER)4)
#define FIT_MANUFACTURER_IDT                                                     ((FIT_MANUFACTURER)5)
#define FIT_MANUFACTURER_SRM                                                     ((FIT_MANUFACTURER)6)
#define FIT_MANUFACTURER_QUARQ                                                   ((FIT_MANUFACTURER)7)
#define FIT_MANUFACTURER_IBIKE                                                   ((FIT_MANUFACTURER)8)
#define FIT_MANUFACTURER_SARIS                                                   ((FIT_MANUFACTURER)9)
#define FIT_MANUFACTURER_SPARK_HK                                                ((FIT_MANUFACTURER)10)
#define FIT_MANUFACTURER_TANITA                                                  ((FIT_MANUFACTURER)11)
#define FIT_MANUFACTURER_ECHOWELL                                                ((FIT_MANUFACTURER)12)
#define FIT_MANUFACTURER_DYNASTREAM_OEM                                          ((FIT_MANUFACTURER)13)
#define FIT_MANUFACTURER_NAUTILUS                                                ((FIT_MANUFACTURER)14)
#define FIT_MANUFACTURER_DYNASTREAM                                              ((FIT_MANUFACTURER)15)
#define FIT_MANUFACTURER_TIMEX                                                   ((FIT_MANUFACTURER)16)
#define FIT_MANUFACTURER_METRIGEAR                                               ((FIT_MANUFACTURER)17)
#define FIT_MANUFACTURER_XELIC                                                   ((FIT_MANUFACTURER)18)
#define FIT_MANUFACTURER_BEURER                                                  ((FIT_MANUFACTURER)19)
#define FIT_MANUFACTURER_CARDIOSPORT                                             ((FIT_MANUFACTURER)20)
#define FIT_MANUFACTURER_A_AND_D                                                 ((FIT_MANUFACTURER)21)
#define FIT_MANUFACTURER_HMM                                                     ((FIT_MANUFACTURER)22)
#define FIT_MANUFACTURER_SUUNTO                                                  ((FIT_MANUFACTURER)23)
#define FIT_MANUFACTURER_THITA_ELEKTRONIK                                        ((FIT_MANUFACTURER)24)
#define FIT_MANUFACTURER_GPULSE                                                  ((FIT_MANUFACTURER)25)
#define FIT_MANUFACTURER_CLEAN_MOBILE                                            ((FIT_MANUFACTURER)26)
#define FIT_MANUFACTURER_PEDAL_BRAIN                                             ((FIT_MANUFACTURER)27)
#define FIT_MANUFACTURER_PEAKSWARE                                               ((FIT_MANUFACTURER)28)
#define FIT_MANUFACTURER_SAXONAR                                                 ((FIT_MANUFACTURER)29)
#define FIT_MANUFACTURER_LEMOND_FITNESS                                          ((FIT_MANUFACTURER)30)
#define FIT_MANUFACTURER_DEXCOM                                                  ((FIT_MANUFACTURER)31)
#define FIT_MANUFACTURER_WAHOO_FITNESS                                           ((FIT_MANUFACTURER)32)
#define FIT_MANUFACTURER_OCTANE_FITNESS                                          ((FIT_MANUFACTURER)33)
#define FIT_MANUFACTURER_ARCHINOETICS                                            ((FIT_MANUFACTURER)34)
#define FIT_MANUFACTURER_THE_HURT_BOX                                            ((FIT_MANUFACTURER)35)
#define FIT_MANUFACTURER_CITIZEN_SYSTEMS                                         ((FIT_MANUFACTURER)36)
#define FIT_MANUFACTURER_MAGELLAN                                                ((FIT_MANUFACTURER)37)
#define FIT_MANUFACTURER_OSYNCE                                                  ((FIT_MANUFACTURER)38)
#define FIT_MANUFACTURER_HOLUX                                                   ((FIT_MANUFACTURER)39)
#define FIT_MANUFACTURER_CONCEPT2                                                ((FIT_MANUFACTURER)40)
#define FIT_MANUFACTURER_ONE_GIANT_LEAP                                          ((FIT_MANUFACTURER)42)
#define FIT_MANUFACTURER_ACE_SENSOR                                              ((FIT_MANUFACTURER)43)
#define FIT_MANUFACTURER_BRIM_BROTHERS                                           ((FIT_MANUFACTURER)44)
#define FIT_MANUFACTURER_XPLOVA                                                  ((FIT_MANUFACTURER)45)
#define FIT_MANUFACTURER_PERCEPTION_DIGITAL                                      ((FIT_MANUFACTURER)46)
#define FIT_MANUFACTURER_BF1SYSTEMS                                              ((FIT_MANUFACTURER)47)
#define FIT_MANUFACTURER_PIONEER                                                 ((FIT_MANUFACTURER)48)
#define FIT_MANUFACTURER_SPANTEC                                                 ((FIT_MANUFACTURER)49)
#define FIT_MANUFACTURER_METALOGICS                                              ((FIT_MANUFACTURER)50)
#define FIT_MANUFACTURER_4IIIIS                                                  ((FIT_MANUFACTURER)51)
#define FIT_MANUFACTURER_SEIKO_EPSON                                             ((FIT_MANUFACTURER)52)
#define FIT_MANUFACTURER_SEIKO_EPSON_OEM                                         ((FIT_MANUFACTURER)53)
#define FIT_MANUFACTURER_IFOR_POWELL                                             ((FIT_MANUFACTURER)54)
#define FIT_MANUFACTURER_MAXWELL_GUIDER                                          ((FIT_MANUFACTURER)55)
#define FIT_MANUFACTURER_STAR_TRAC                                               ((FIT_MANUFACTURER)56)
#define FIT_MANUFACTURER_BREAKAWAY                                               ((FIT_MANUFACTURER)57)
#define FIT_MANUFACTURER_ALATECH_TECHNOLOGY_LTD                                  ((FIT_MANUFACTURER)58)
#define FIT_MANUFACTURER_MIO_TECHNOLOGY_EUROPE                                   ((FIT_MANUFACTURER)59)
#define FIT_MANUFACTURER_ROTOR                                                   ((FIT_MANUFACTURER)60)
#define FIT_MANUFACTURER_GEONAUTE                                                ((FIT_MANUFACTURER)61)
#define FIT_MANUFACTURER_ID_BIKE                                                 ((FIT_MANUFACTURER)62)
#define FIT_MANUFACTURER_SPECIALIZED                                             ((FIT_MANUFACTURER)63)
#define FIT_MANUFACTURER_WTEK                                                    ((FIT_MANUFACTURER)64)
#define FIT_MANUFACTURER_PHYSICAL_ENTERPRISES                                    ((FIT_MANUFACTURER)65)
#define FIT_MANUFACTURER_NORTH_POLE_ENGINEERING                                  ((FIT_MANUFACTURER)66)
#define FIT_MANUFACTURER_BKOOL                                                   ((FIT_MANUFACTURER)67)
#define FIT_MANUFACTURER_CATEYE                                                  ((FIT_MANUFACTURER)68)
#define FIT_MANUFACTURER_STAGES_CYCLING                                          ((FIT_MANUFACTURER)69)
#define FIT_MANUFACTURER_SIGMASPORT                                              ((FIT_MANUFACTURER)70)
#define FIT_MANUFACTURER_TOMTOM                                                  ((FIT_MANUFACTURER)71)
#define FIT_MANUFACTURER_PERIPEDAL                                               ((FIT_MANUFACTURER)72)
#define FIT_MANUFACTURER_WATTBIKE                                                ((FIT_MANUFACTURER)73)
#define FIT_MANUFACTURER_MOXY                                                    ((FIT_MANUFACTURER)76)
#define FIT_MANUFACTURER_CICLOSPORT                                              ((FIT_MANUFACTURER)77)
#define FIT_MANUFACTURER_POWERBAHN                                               ((FIT_MANUFACTURER)78)
#define FIT_MANUFACTURER_ACORN_PROJECTS_APS                                      ((FIT_MANUFACTURER)79)
#define FIT_MANUFACTURER_LIFEBEAM                                                ((FIT_MANUFACTURER)80)
#define FIT_MANUFACTURER_BONTRAGER                                               ((FIT_MANUFACTURER)81)
#define FIT_MANUFACTURER_WELLGO                                                  ((FIT_MANUFACTURER)82)
#define FIT_MANUFACTURER_SCOSCHE                                                 ((FIT_MANUFACTURER)83)
#define FIT_MANUFACTURER_MAGURA                                                  ((FIT_MANUFACTURER)84)
#define FIT_MANUFACTURER_WOODWAY                                                 ((FIT_MANUFACTURER)85)
#define FIT_MANUFACTURER_ELITE                                                   ((FIT_MANUFACTURER)86)
#define FIT_MANUFACTURER_NIELSEN_KELLERMAN                                       ((FIT_MANUFACTURER)87)
#define FIT_MANUFACTURER_DK_CITY                                                 ((FIT_MANUFACTURER)88)
#define FIT_MANUFACTURER_TACX                                                    ((FIT_MANUFACTURER)89)
#define FIT_MANUFACTURER_DIRECTION_TECHNOLOGY                                    ((FIT_MANUFACTURER)90)
#define FIT_MANUFACTURER_MAGTONIC                                                ((FIT_MANUFACTURER)91)
#define FIT_MANUFACTURER_1PARTCARBON                                             ((FIT_MANUFACTURER)92)
#define FIT_MANUFACTURER_INSIDE_RIDE_TECHNOLOGIES                                ((FIT_MANUFACTURER)93)
#define FIT_MANUFACTURER_SOUND_OF_MOTION                                         ((FIT_MANUFACTURER)94)
#define FIT_MANUFACTURER_STRYD                                                   ((FIT_MANUFACTURER)95)
#define FIT_MANUFACTURER_ICG                                                     ((FIT_MANUFACTURER)96) // Indoorcycling Group
#define FIT_MANUFACTURER_MIPULSE                                                 ((FIT_MANUFACTURER)97)
#define FIT_MANUFACTURER_BSX_ATHLETICS                                           ((FIT_MANUFACTURER)98)
#define FIT_MANUFACTURER_LOOK                                                    ((FIT_MANUFACTURER)99)
#define FIT_MANUFACTURER_CAMPAGNOLO_SRL                                          ((FIT_MANUFACTURER)100)
#define FIT_MANUFACTURER_BODY_BIKE_SMART                                         ((FIT_MANUFACTURER)101)
#define FIT_MANUFACTURER_PRAXISWORKS                                             ((FIT_MANUFACTURER)102)
#define FIT_MANUFACTURER_LIMITS_TECHNOLOGY                                       ((FIT_MANUFACTURER)103) // Limits Technology Ltd.
#define FIT_MANUFACTURER_TOPACTION_TECHNOLOGY                                    ((FIT_MANUFACTURER)104) // TopAction Technology Inc.
#define FIT_MANUFACTURER_COSINUSS                                                ((FIT_MANUFACTURER)105)
#define FIT_MANUFACTURER_FITCARE                                                 ((FIT_MANUFACTURER)106)
#define FIT_MANUFACTURER_MAGENE                                                  ((FIT_MANUFACTURER)107)
#define FIT_MANUFACTURER_GIANT_MANUFACTURING_CO                                  ((FIT_MANUFACTURER)108)
#define FIT_MANUFACTURER_TIGRASPORT                                              ((FIT_MANUFACTURER)109) // Tigrasport
#define FIT_MANUFACTURER_SALUTRON                                                ((FIT_MANUFACTURER)110)
#define FIT_MANUFACTURER_TECHNOGYM                                               ((FIT_MANUFACTURER)111)
#define FIT_MANUFACTURER_BRYTON_SENSORS                                          ((FIT_MANUFACTURER)112)
#define FIT_MANUFACTURER_LATITUDE_LIMITED                                        ((FIT_MANUFACTURER)113)
#define FIT_MANUFACTURER_SOARING_TECHNOLOGY                                      ((FIT_MANUFACTURER)114)
#define FIT_MANUFACTURER_IGPSPORT                                                ((FIT_MANUFACTURER)115)
#define FIT_MANUFACTURER_THINKRIDER                                              ((FIT_MANUFACTURER)116)
#define FIT_MANUFACTURER_GOPHER_SPORT                                            ((FIT_MANUFACTURER)117)
#define FIT_MANUFACTURER_WATERROWER                                              ((FIT_MANUFACTURER)118)
#define FIT_MANUFACTURER_ORANGETHEORY                                            ((FIT_MANUFACTURER)119)
#define FIT_MANUFACTURER_INPEAK                                                  ((FIT_MANUFACTURER)120)
#define FIT_MANUFACTURER_KINETIC                                                 ((FIT_MANUFACTURER)121)
#define FIT_MANUFACTURER_JOHNSON_HEALTH_TECH                                     ((FIT_MANUFACTURER)122)
#define FIT_MANUFACTURER_DEVELOPMENT                                             ((FIT_MANUFACTURER)255)
#define FIT_MANUFACTURER_HEALTHANDLIFE                                           ((FIT_MANUFACTURER)257)
#define FIT_MANUFACTURER_LEZYNE                                                  ((FIT_MANUFACTURER)258)
#define FIT_MANUFACTURER_SCRIBE_LABS                                             ((FIT_MANUFACTURER)259)
#define FIT_MANUFACTURER_ZWIFT                                                   ((FIT_MANUFACTURER)260)
#define FIT_MANUFACTURER_WATTEAM                                                 ((FIT_MANUFACTURER)261)
#define FIT_MANUFACTURER_RECON                                                   ((FIT_MANUFACTURER)262)
#define FIT_MANUFACTURER_FAVERO_ELECTRONICS                                      ((FIT_MANUFACTURER)263)
#define FIT_MANUFACTURER_DYNOVELO                                                ((FIT_MANUFACTURER)264)
#define FIT_MANUFACTURER_STRAVA                                                  ((FIT_MANUFACTURER)265)
#define FIT_MANUFACTURER_PRECOR                                                  ((FIT_MANUFACTURER)266) // Amer Sports
#define FIT_MANUFACTURER_BRYTON                                                  ((FIT_MANUFACTURER)267)
#define FIT_MANUFACTURER_SRAM                                                    ((FIT_MANUFACTURER)268)
#define FIT_MANUFACTURER_NAVMAN                                                  ((FIT_MANUFACTURER)269) // MiTAC Global Corporation (Mio Technology)
#define FIT_MANUFACTURER_COBI                                                    ((FIT_MANUFACTURER)270) // COBI GmbH
#define FIT_MANUFACTURER_SPIVI                                                   ((FIT_MANUFACTURER)271)
#define FIT_MANUFACTURER_MIO_MAGELLAN                                            ((FIT_MANUFACTURER)272)
#define FIT_MANUFACTURER_EVESPORTS                                               ((FIT_MANUFACTURER)273)
#define FIT_MANUFACTURER_SENSITIVUS_GAUGE                                        ((FIT_MANUFACTURER)274)
#define FIT_MANUFACTURER_PODOON                                                  ((FIT_MANUFACTURER)275)
#define FIT_MANUFACTURER_LIFE_TIME_FITNESS                                       ((FIT_MANUFACTURER)276)
#define FIT_MANUFACTURER_FALCO_E_MOTORS                                          ((FIT_MANUFACTURER)277) // Falco eMotors Inc.
#define FIT_MANUFACTURER_MINOURA                                                 ((FIT_MANUFACTURER)278)
#define FIT_MANUFACTURER_CYCLIQ                                                  ((FIT_MANUFACTURER)279)
#define FIT_MANUFACTURER_LUXOTTICA                                               ((FIT_MANUFACTURER)280)
#define FIT_MANUFACTURER_TRAINER_ROAD                                            ((FIT_MANUFACTURER)281)
#define FIT_MANUFACTURER_THE_SUFFERFEST                                          ((FIT_MANUFACTURER)282)
#define FIT_MANUFACTURER_FULLSPEEDAHEAD                                          ((FIT_MANUFACTURER)283)
#define FIT_MANUFACTURER_VIRTUALTRAINING                                         ((FIT_MANUFACTURER)284)
#define FIT_MANUFACTURER_FEEDBACKSPORTS                                          ((FIT_MANUFACTURER)285)
#define FIT_MANUFACTURER_OMATA                                                   ((FIT_MANUFACTURER)286)
#define FIT_MANUFACTURER_VDO                                                     ((FIT_MANUFACTURER)287)
#define FIT_MANUFACTURER_MAGNETICDAYS                                            ((FIT_MANUFACTURER)288)
#define FIT_MANUFACTURER_HAMMERHEAD                                              ((FIT_MANUFACTURER)289)
#define FIT_MANUFACTURER_KINETIC_BY_KURT                                         ((FIT_MANUFACTURER)290)
#define FIT_MANUFACTURER_SHAPELOG                                                ((FIT_MANUFACTURER)291)
#define FIT_MANUFACTURER_DABUZIDUO                                               ((FIT_MANUFACTURER)292)
#define FIT_MANUFACTURER_JETBLACK                                                ((FIT_MANUFACTURER)293)
#define FIT_MANUFACTURER_ACTIGRAPHCORP                                           ((FIT_MANUFACTURER)5759)
#define FIT_MANUFACTURER_COUNT                                                   158

typedef FIT_UINT16 FIT_GARMIN_PRODUCT;
#define FIT_GARMIN_PRODUCT_INVALID                                               FIT_UINT16_INVALID
#define FIT_GARMIN_PRODUCT_HRM1                                                  ((FIT_GARMIN_PRODUCT)1)
#define FIT_GARMIN_PRODUCT_AXH01                                                 ((FIT_GARMIN_PRODUCT)2) // AXH01 HRM chipset
#define FIT_GARMIN_PRODUCT_AXB01                                                 ((FIT_GARMIN_PRODUCT)3)
#define FIT_GARMIN_PRODUCT_AXB02                                                 ((FIT_GARMIN_PRODUCT)4)
#define FIT_GARMIN_PRODUCT_HRM2SS                                                ((FIT_GARMIN_PRODUCT)5)
#define FIT_GARMIN_PRODUCT_DSI_ALF02                                             ((FIT_GARMIN_PRODUCT)6)
#define FIT_GARMIN_PRODUCT_HRM3SS                                                ((FIT_GARMIN_PRODUCT)7)
#define FIT_GARMIN_PRODUCT_HRM_RUN_SINGLE_BYTE_PRODUCT_ID                        ((FIT_GARMIN_PRODUCT)8) // hrm_run model for HRM ANT+ messaging
#define FIT_GARMIN_PRODUCT_BSM                                                   ((FIT_GARMIN_PRODUCT)9) // BSM model for ANT+ messaging
#define FIT_GARMIN_PRODUCT_BCM                                                   ((FIT_GARMIN_PRODUCT)10) // BCM model for ANT+ messaging
#define FIT_GARMIN_PRODUCT_AXS01                                                 ((FIT_GARMIN_PRODUCT)11) // AXS01 HRM Bike Chipset model for ANT+ messaging
#define FIT_GARMIN_PRODUCT_HRM_TRI_SINGLE_BYTE_PRODUCT_ID                        ((FIT_GARMIN_PRODUCT)12) // hrm_tri model for HRM ANT+ messaging
#define FIT_GARMIN_PRODUCT_FR225_SINGLE_BYTE_PRODUCT_ID                          ((FIT_GARMIN_PRODUCT)14) // fr225 model for HRM ANT+ messaging
#define FIT_GARMIN_PRODUCT_FR301_CHINA                                           ((FIT_GARMIN_PRODUCT)473)
#define FIT_GARMIN_PRODUCT_FR301_JAPAN                                           ((FIT_GARMIN_PRODUCT)474)
#define FIT_GARMIN_PRODUCT_FR301_KOREA                                           ((FIT_GARMIN_PRODUCT)475)
#define FIT_GARMIN_PRODUCT_FR301_TAIWAN                                          ((FIT_GARMIN_PRODUCT)494)
#define FIT_GARMIN_PRODUCT_FR405                                                 ((FIT_GARMIN_PRODUCT)717) // Forerunner 405
#define FIT_GARMIN_PRODUCT_FR50                                                  ((FIT_GARMIN_PRODUCT)782) // Forerunner 50
#define FIT_GARMIN_PRODUCT_FR405_JAPAN                                           ((FIT_GARMIN_PRODUCT)987)
#define FIT_GARMIN_PRODUCT_FR60                                                  ((FIT_GARMIN_PRODUCT)988) // Forerunner 60
#define FIT_GARMIN_PRODUCT_DSI_ALF01                                             ((FIT_GARMIN_PRODUCT)1011)
#define FIT_GARMIN_PRODUCT_FR310XT                                               ((FIT_GARMIN_PRODUCT)1018) // Forerunner 310
#define FIT_GARMIN_PRODUCT_EDGE500                                               ((FIT_GARMIN_PRODUCT)1036)
#define FIT_GARMIN_PRODUCT_FR110                                                 ((FIT_GARMIN_PRODUCT)1124) // Forerunner 110
#define FIT_GARMIN_PRODUCT_EDGE800                                               ((FIT_GARMIN_PRODUCT)1169)
#define FIT_GARMIN_PRODUCT_EDGE500_TAIWAN                                        ((FIT_GARMIN_PRODUCT)1199)
#define FIT_GARMIN_PRODUCT_EDGE500_JAPAN                                         ((FIT_GARMIN_PRODUCT)1213)
#define FIT_GARMIN_PRODUCT_CHIRP                                                 ((FIT_GARMIN_PRODUCT)1253)
#define FIT_GARMIN_PRODUCT_FR110_JAPAN                                           ((FIT_GARMIN_PRODUCT)1274)
#define FIT_GARMIN_PRODUCT_EDGE200                                               ((FIT_GARMIN_PRODUCT)1325)
#define FIT_GARMIN_PRODUCT_FR910XT                                               ((FIT_GARMIN_PRODUCT)1328)
#define FIT_GARMIN_PRODUCT_EDGE800_TAIWAN                                        ((FIT_GARMIN_PRODUCT)1333)
#define FIT_GARMIN_PRODUCT_EDGE800_JAPAN                                         ((FIT_GARMIN_PRODUCT)1334)
#define FIT_GARMIN_PRODUCT_ALF04                                                 ((FIT_GARMIN_PRODUCT)1341)
#define FIT_GARMIN_PRODUCT_FR610                                                 ((FIT_GARMIN_PRODUCT)1345)
#define FIT_GARMIN_PRODUCT_FR210_JAPAN                                           ((FIT_GARMIN_PRODUCT)1360)
#define FIT_GARMIN_PRODUCT_VECTOR_SS                                             ((FIT_GARMIN_PRODUCT)1380)
#define FIT_GARMIN_PRODUCT_VECTOR_CP                                             ((FIT_GARMIN_PRODUCT)1381)
#define FIT_GARMIN_PRODUCT_EDGE800_CHINA                                         ((FIT_GARMIN_PRODUCT)1386)
#define FIT_GARMIN_PRODUCT_EDGE500_CHINA                                         ((FIT_GARMIN_PRODUCT)1387)
#define FIT_GARMIN_PRODUCT_FR610_JAPAN                                           ((FIT_GARMIN_PRODUCT)1410)
#define FIT_GARMIN_PRODUCT_EDGE500_KOREA                                         ((FIT_GARMIN_PRODUCT)1422)
#define FIT_GARMIN_PRODUCT_FR70                                                  ((FIT_GARMIN_PRODUCT)1436)
#define FIT_GARMIN_PRODUCT_FR310XT_4T                                            ((FIT_GARMIN_PRODUCT)1446)
#define FIT_GARMIN_PRODUCT_AMX                                                   ((FIT_GARMIN_PRODUCT)1461)
#define FIT_GARMIN_PRODUCT_FR10                                                  ((FIT_GARMIN_PRODUCT)1482)
#define FIT_GARMIN_PRODUCT_EDGE800_KOREA                                         ((FIT_GARMIN_PRODUCT)1497)
#define FIT_GARMIN_PRODUCT_SWIM                                                  ((FIT_GARMIN_PRODUCT)1499)
#define FIT_GARMIN_PRODUCT_FR910XT_CHINA                                         ((FIT_GARMIN_PRODUCT)1537)
#define FIT_GARMIN_PRODUCT_FENIX                                                 ((FIT_GARMIN_PRODUCT)1551)
#define FIT_GARMIN_PRODUCT_EDGE200_TAIWAN                                        ((FIT_GARMIN_PRODUCT)1555)
#define FIT_GARMIN_PRODUCT_EDGE510                                               ((FIT_GARMIN_PRODUCT)1561)
#define FIT_GARMIN_PRODUCT_EDGE810                                               ((FIT_GARMIN_PRODUCT)1567)
#define FIT_GARMIN_PRODUCT_TEMPE                                                 ((FIT_GARMIN_PRODUCT)1570)
#define FIT_GARMIN_PRODUCT_FR910XT_JAPAN                                         ((FIT_GARMIN_PRODUCT)1600)
#define FIT_GARMIN_PRODUCT_FR620                                                 ((FIT_GARMIN_PRODUCT)1623)
#define FIT_GARMIN_PRODUCT_FR220                                                 ((FIT_GARMIN_PRODUCT)1632)
#define FIT_GARMIN_PRODUCT_FR910XT_KOREA                                         ((FIT_GARMIN_PRODUCT)1664)
#define FIT_GARMIN_PRODUCT_FR10_JAPAN                                            ((FIT_GARMIN_PRODUCT)1688)
#define FIT_GARMIN_PRODUCT_EDGE810_JAPAN                                         ((FIT_GARMIN_PRODUCT)1721)
#define FIT_GARMIN_PRODUCT_VIRB_ELITE                                            ((FIT_GARMIN_PRODUCT)1735)
#define FIT_GARMIN_PRODUCT_EDGE_TOURING                                          ((FIT_GARMIN_PRODUCT)1736) // Also Edge Touring Plus
#define FIT_GARMIN_PRODUCT_EDGE510_JAPAN                                         ((FIT_GARMIN_PRODUCT)1742)
#define FIT_GARMIN_PRODUCT_HRM_TRI                                               ((FIT_GARMIN_PRODUCT)1743)
#define FIT_GARMIN_PRODUCT_HRM_RUN                                               ((FIT_GARMIN_PRODUCT)1752)
#define FIT_GARMIN_PRODUCT_FR920XT                                               ((FIT_GARMIN_PRODUCT)1765)
#define FIT_GARMIN_PRODUCT_EDGE510_ASIA                                          ((FIT_GARMIN_PRODUCT)1821)
#define FIT_GARMIN_PRODUCT_EDGE810_CHINA                                         ((FIT_GARMIN_PRODUCT)1822)
#define FIT_GARMIN_PRODUCT_EDGE810_TAIWAN                                        ((FIT_GARMIN_PRODUCT)1823)
#define FIT_GARMIN_PRODUCT_EDGE1000                                              ((FIT_GARMIN_PRODUCT)1836)
#define FIT_GARMIN_PRODUCT_VIVO_FIT                                              ((FIT_GARMIN_PRODUCT)1837)
#define FIT_GARMIN_PRODUCT_VIRB_REMOTE                                           ((FIT_GARMIN_PRODUCT)1853)
#define FIT_GARMIN_PRODUCT_VIVO_KI                                               ((FIT_GARMIN_PRODUCT)1885)
#define FIT_GARMIN_PRODUCT_FR15                                                  ((FIT_GARMIN_PRODUCT)1903)
#define FIT_GARMIN_PRODUCT_VIVO_ACTIVE                                           ((FIT_GARMIN_PRODUCT)1907)
#define FIT_GARMIN_PRODUCT_EDGE510_KOREA                                         ((FIT_GARMIN_PRODUCT)1918)
#define FIT_GARMIN_PRODUCT_FR620_JAPAN                                           ((FIT_GARMIN_PRODUCT)1928)
#define FIT_GARMIN_PRODUCT_FR620_CHINA                                           ((FIT_GARMIN_PRODUCT)1929)
#define FIT_GARMIN_PRODUCT_FR220_JAPAN                                           ((FIT_GARMIN_PRODUCT)1930)
#define FIT_GARMIN_PRODUCT_FR220_CHINA                                           ((FIT_GARMIN_PRODUCT)1931)
#define FIT_GARMIN_PRODUCT_APPROACH_S6                                           ((FIT_GARMIN_PRODUCT)1936)
#define FIT_GARMIN_PRODUCT_VIVO_SMART                                            ((FIT_GARMIN_PRODUCT)1956)
#define FIT_GARMIN_PRODUCT_FENIX2                                                ((FIT_GARMIN_PRODUCT)1967)
#define FIT_GARMIN_PRODUCT_EPIX                                                  ((FIT_GARMIN_PRODUCT)1988)
#define FIT_GARMIN_PRODUCT_FENIX3                                                ((FIT_GARMIN_PRODUCT)2050)
#define FIT_GARMIN_PRODUCT_EDGE1000_TAIWAN                                       ((FIT_GARMIN_PRODUCT)2052)
#define FIT_GARMIN_PRODUCT_EDGE1000_JAPAN                                        ((FIT_GARMIN_PRODUCT)2053)
#define FIT_GARMIN_PRODUCT_FR15_JAPAN                                            ((FIT_GARMIN_PRODUCT)2061)
#define FIT_GARMIN_PRODUCT_EDGE520                                               ((FIT_GARMIN_PRODUCT)2067)
#define FIT_GARMIN_PRODUCT_EDGE1000_CHINA                                        ((FIT_GARMIN_PRODUCT)2070)
#define FIT_GARMIN_PRODUCT_FR620_RUSSIA                                          ((FIT_GARMIN_PRODUCT)2072)
#define FIT_GARMIN_PRODUCT_FR220_RUSSIA                                          ((FIT_GARMIN_PRODUCT)2073)
#define FIT_GARMIN_PRODUCT_VECTOR_S                                              ((FIT_GARMIN_PRODUCT)2079)
#define FIT_GARMIN_PRODUCT_EDGE1000_KOREA                                        ((FIT_GARMIN_PRODUCT)2100)
#define FIT_GARMIN_PRODUCT_FR920XT_TAIWAN                                        ((FIT_GARMIN_PRODUCT)2130)
#define FIT_GARMIN_PRODUCT_FR920XT_CHINA                                         ((FIT_GARMIN_PRODUCT)2131)
#define FIT_GARMIN_PRODUCT_FR920XT_JAPAN                                         ((FIT_GARMIN_PRODUCT)2132)
#define FIT_GARMIN_PRODUCT_VIRBX                                                 ((FIT_GARMIN_PRODUCT)2134)
#define FIT_GARMIN_PRODUCT_VIVO_SMART_APAC                                       ((FIT_GARMIN_PRODUCT)2135)
#define FIT_GARMIN_PRODUCT_ETREX_TOUCH                                           ((FIT_GARMIN_PRODUCT)2140)
#define FIT_GARMIN_PRODUCT_EDGE25                                                ((FIT_GARMIN_PRODUCT)2147)
#define FIT_GARMIN_PRODUCT_FR25                                                  ((FIT_GARMIN_PRODUCT)2148)
#define FIT_GARMIN_PRODUCT_VIVO_FIT2                                             ((FIT_GARMIN_PRODUCT)2150)
#define FIT_GARMIN_PRODUCT_FR225                                                 ((FIT_GARMIN_PRODUCT)2153)
#define FIT_GARMIN_PRODUCT_FR630                                                 ((FIT_GARMIN_PRODUCT)2156)
#define FIT_GARMIN_PRODUCT_FR230                                                 ((FIT_GARMIN_PRODUCT)2157)
#define FIT_GARMIN_PRODUCT_VIVO_ACTIVE_APAC                                      ((FIT_GARMIN_PRODUCT)2160)
#define FIT_GARMIN_PRODUCT_VECTOR_2                                              ((FIT_GARMIN_PRODUCT)2161)
#define FIT_GARMIN_PRODUCT_VECTOR_2S                                             ((FIT_GARMIN_PRODUCT)2162)
#define FIT_GARMIN_PRODUCT_VIRBXE                                                ((FIT_GARMIN_PRODUCT)2172)
#define FIT_GARMIN_PRODUCT_FR620_TAIWAN                                          ((FIT_GARMIN_PRODUCT)2173)
#define FIT_GARMIN_PRODUCT_FR220_TAIWAN                                          ((FIT_GARMIN_PRODUCT)2174)
#define FIT_GARMIN_PRODUCT_TRUSWING                                              ((FIT_GARMIN_PRODUCT)2175)
#define FIT_GARMIN_PRODUCT_FENIX3_CHINA                                          ((FIT_GARMIN_PRODUCT)2188)
#define FIT_GARMIN_PRODUCT_FENIX3_TWN                                            ((FIT_GARMIN_PRODUCT)2189)
#define FIT_GARMIN_PRODUCT_VARIA_HEADLIGHT                                       ((FIT_GARMIN_PRODUCT)2192)
#define FIT_GARMIN_PRODUCT_VARIA_TAILLIGHT_OLD                                   ((FIT_GARMIN_PRODUCT)2193)
#define FIT_GARMIN_PRODUCT_EDGE_EXPLORE_1000                                     ((FIT_GARMIN_PRODUCT)2204)
#define FIT_GARMIN_PRODUCT_FR225_ASIA                                            ((FIT_GARMIN_PRODUCT)2219)
#define FIT_GARMIN_PRODUCT_VARIA_RADAR_TAILLIGHT                                 ((FIT_GARMIN_PRODUCT)2225)
#define FIT_GARMIN_PRODUCT_VARIA_RADAR_DISPLAY                                   ((FIT_GARMIN_PRODUCT)2226)
#define FIT_GARMIN_PRODUCT_EDGE20                                                ((FIT_GARMIN_PRODUCT)2238)
#define FIT_GARMIN_PRODUCT_D2_BRAVO                                              ((FIT_GARMIN_PRODUCT)2262)
#define FIT_GARMIN_PRODUCT_APPROACH_S20                                          ((FIT_GARMIN_PRODUCT)2266)
#define FIT_GARMIN_PRODUCT_VARIA_REMOTE                                          ((FIT_GARMIN_PRODUCT)2276)
#define FIT_GARMIN_PRODUCT_HRM4_RUN                                              ((FIT_GARMIN_PRODUCT)2327)
#define FIT_GARMIN_PRODUCT_VIVO_ACTIVE_HR                                        ((FIT_GARMIN_PRODUCT)2337)
#define FIT_GARMIN_PRODUCT_VIVO_SMART_GPS_HR                                     ((FIT_GARMIN_PRODUCT)2347)
#define FIT_GARMIN_PRODUCT_VIVO_SMART_HR                                         ((FIT_GARMIN_PRODUCT)2348)
#define FIT_GARMIN_PRODUCT_VIVO_MOVE                                             ((FIT_GARMIN_PRODUCT)2368)
#define FIT_GARMIN_PRODUCT_VARIA_VISION                                          ((FIT_GARMIN_PRODUCT)2398)
#define FIT_GARMIN_PRODUCT_VIVO_FIT3                                             ((FIT_GARMIN_PRODUCT)2406)
#define FIT_GARMIN_PRODUCT_FENIX3_HR                                             ((FIT_GARMIN_PRODUCT)2413)
#define FIT_GARMIN_PRODUCT_VIRB_ULTRA_30                                         ((FIT_GARMIN_PRODUCT)2417)
#define FIT_GARMIN_PRODUCT_INDEX_SMART_SCALE                                     ((FIT_GARMIN_PRODUCT)2429)
#define FIT_GARMIN_PRODUCT_FR235                                                 ((FIT_GARMIN_PRODUCT)2431)
#define FIT_GARMIN_PRODUCT_FENIX3_CHRONOS                                        ((FIT_GARMIN_PRODUCT)2432)
#define FIT_GARMIN_PRODUCT_OREGON7XX                                             ((FIT_GARMIN_PRODUCT)2441)
#define FIT_GARMIN_PRODUCT_RINO7XX                                               ((FIT_GARMIN_PRODUCT)2444)
#define FIT_GARMIN_PRODUCT_NAUTIX                                                ((FIT_GARMIN_PRODUCT)2496)
#define FIT_GARMIN_PRODUCT_EDGE_820                                              ((FIT_GARMIN_PRODUCT)2530)
#define FIT_GARMIN_PRODUCT_EDGE_EXPLORE_820                                      ((FIT_GARMIN_PRODUCT)2531)
#define FIT_GARMIN_PRODUCT_FENIX5S                                               ((FIT_GARMIN_PRODUCT)2544)
#define FIT_GARMIN_PRODUCT_D2_BRAVO_TITANIUM                                     ((FIT_GARMIN_PRODUCT)2547)
#define FIT_GARMIN_PRODUCT_VARIA_UT800                                           ((FIT_GARMIN_PRODUCT)2567) // Varia UT 800 SW
#define FIT_GARMIN_PRODUCT_RUNNING_DYNAMICS_POD                                  ((FIT_GARMIN_PRODUCT)2593)
#define FIT_GARMIN_PRODUCT_FENIX5X                                               ((FIT_GARMIN_PRODUCT)2604)
#define FIT_GARMIN_PRODUCT_VIVO_FIT_JR                                           ((FIT_GARMIN_PRODUCT)2606)
#define FIT_GARMIN_PRODUCT_FR935                                                 ((FIT_GARMIN_PRODUCT)2691)
#define FIT_GARMIN_PRODUCT_FENIX5                                                ((FIT_GARMIN_PRODUCT)2697)
#define FIT_GARMIN_PRODUCT_SDM4                                                  ((FIT_GARMIN_PRODUCT)10007) // SDM4 footpod
#define FIT_GARMIN_PRODUCT_EDGE_REMOTE                                           ((FIT_GARMIN_PRODUCT)10014)
#define FIT_GARMIN_PRODUCT_TRAINING_CENTER                                       ((FIT_GARMIN_PRODUCT)20119)
#define FIT_GARMIN_PRODUCT_CONNECTIQ_SIMULATOR                                   ((FIT_GARMIN_PRODUCT)65531)
#define FIT_GARMIN_PRODUCT_ANDROID_ANTPLUS_PLUGIN                                ((FIT_GARMIN_PRODUCT)65532)
#define FIT_GARMIN_PRODUCT_CONNECT                                               ((FIT_GARMIN_PRODUCT)65534) // Garmin Connect website
#define FIT_GARMIN_PRODUCT_COUNT                                                 157

typedef FIT_UINT8 FIT_ANTPLUS_DEVICE_TYPE;
#define FIT_ANTPLUS_DEVICE_TYPE_INVALID                                          FIT_UINT8_INVALID
#define FIT_ANTPLUS_DEVICE_TYPE_ANTFS                                            ((FIT_ANTPLUS_DEVICE_TYPE)1)
#define FIT_ANTPLUS_DEVICE_TYPE_BIKE_POWER                                       ((FIT_ANTPLUS_DEVICE_TYPE)11)
#define FIT_ANTPLUS_DEVICE_TYPE_ENVIRONMENT_SENSOR_LEGACY                        ((FIT_ANTPLUS_DEVICE_TYPE)12)
#define FIT_ANTPLUS_DEVICE_TYPE_MULTI_SPORT_SPEED_DISTANCE                       ((FIT_ANTPLUS_DEVICE_TYPE)15)
#define FIT_ANTPLUS_DEVICE_TYPE_CONTROL                                          ((FIT_ANTPLUS_DEVICE_TYPE)16)
#define FIT_ANTPLUS_DEVICE_TYPE_FITNESS_EQUIPMENT                                ((FIT_ANTPLUS_DEVICE_TYPE)17)
#define FIT_ANTPLUS_DEVICE_TYPE_BLOOD_PRESSURE                                   ((FIT_ANTPLUS_DEVICE_TYPE)18)
#define FIT_ANTPLUS_DEVICE_TYPE_GEOCACHE_NODE                                    ((FIT_ANTPLUS_DEVICE_TYPE)19)
#define FIT_ANTPLUS_DEVICE_TYPE_LIGHT_ELECTRIC_VEHICLE                           ((FIT_ANTPLUS_DEVICE_TYPE)20)
#define FIT_ANTPLUS_DEVICE_TYPE_ENV_SENSOR                                       ((FIT_ANTPLUS_DEVICE_TYPE)25)
#define FIT_ANTPLUS_DEVICE_TYPE_RACQUET                                          ((FIT_ANTPLUS_DEVICE_TYPE)26)
#define FIT_ANTPLUS_DEVICE_TYPE_CONTROL_HUB                                      ((FIT_ANTPLUS_DEVICE_TYPE)27)
#define FIT_ANTPLUS_DEVICE_TYPE_MUSCLE_OXYGEN                                    ((FIT_ANTPLUS_DEVICE_TYPE)31)
#define FIT_ANTPLUS_DEVICE_TYPE_BIKE_LIGHT_MAIN                                  ((FIT_ANTPLUS_DEVICE_TYPE)35)
#define FIT_ANTPLUS_DEVICE_TYPE_BIKE_LIGHT_SHARED                                ((FIT_ANTPLUS_DEVICE_TYPE)36)
#define FIT_ANTPLUS_DEVICE_TYPE_EXD                                              ((FIT_ANTPLUS_DEVICE_TYPE)38)
#define FIT_ANTPLUS_DEVICE_TYPE_BIKE_RADAR                                       ((FIT_ANTPLUS_DEVICE_TYPE)40)
#define FIT_ANTPLUS_DEVICE_TYPE_WEIGHT_SCALE                                     ((FIT_ANTPLUS_DEVICE_TYPE)119)
#define FIT_ANTPLUS_DEVICE_TYPE_HEART_RATE                                       ((FIT_ANTPLUS_DEVICE_TYPE)120)
#define FIT_ANTPLUS_DEVICE_TYPE_BIKE_SPEED_CADENCE                               ((FIT_ANTPLUS_DEVICE_TYPE)121)
#define FIT_ANTPLUS_DEVICE_TYPE_BIKE_CADENCE                                     ((FIT_ANTPLUS_DEVICE_TYPE)122)
#define FIT_ANTPLUS_DEVICE_TYPE_BIKE_SPEED                                       ((FIT_ANTPLUS_DEVICE_TYPE)123)
#define FIT_ANTPLUS_DEVICE_TYPE_STRIDE_SPEED_DISTANCE                            ((FIT_ANTPLUS_DEVICE_TYPE)124)
#define FIT_ANTPLUS_DEVICE_TYPE_COUNT                                            23

typedef FIT_ENUM FIT_ANT_NETWORK;
#define FIT_ANT_NETWORK_INVALID                                                  FIT_ENUM_INVALID
#define FIT_ANT_NETWORK_PUBLIC                                                   ((FIT_ANT_NETWORK)0)
#define FIT_ANT_NETWORK_ANTPLUS                                                  ((FIT_ANT_NETWORK)1)
#define FIT_ANT_NETWORK_ANTFS                                                    ((FIT_ANT_NETWORK)2)
#define FIT_ANT_NETWORK_PRIVATE                                                  ((FIT_ANT_NETWORK)3)
#define FIT_ANT_NETWORK_COUNT                                                    4

typedef FIT_UINT32Z FIT_WORKOUT_CAPABILITIES;
#define FIT_WORKOUT_CAPABILITIES_INVALID                                         FIT_UINT32Z_INVALID
#define FIT_WORKOUT_CAPABILITIES_INTERVAL                                        ((FIT_WORKOUT_CAPABILITIES)0x00000001)
#define FIT_WORKOUT_CAPABILITIES_CUSTOM                                          ((FIT_WORKOUT_CAPABILITIES)0x00000002)
#define FIT_WORKOUT_CAPABILITIES_FITNESS_EQUIPMENT                               ((FIT_WORKOUT_CAPABILITIES)0x00000004)
#define FIT_WORKOUT_CAPABILITIES_FIRSTBEAT                                       ((FIT_WORKOUT_CAPABILITIES)0x00000008)
#define FIT_WORKOUT_CAPABILITIES_NEW_LEAF                                        ((FIT_WORKOUT_CAPABILITIES)0x00000010)
#define FIT_WORKOUT_CAPABILITIES_TCX                                             ((FIT_WORKOUT_CAPABILITIES)0x00000020) // For backwards compatibility.  Watch should add missing id fields then clear flag.
#define FIT_WORKOUT_CAPABILITIES_SPEED                                           ((FIT_WORKOUT_CAPABILITIES)0x00000080) // Speed source required for workout step.
#define FIT_WORKOUT_CAPABILITIES_HEART_RATE                                      ((FIT_WORKOUT_CAPABILITIES)0x00000100) // Heart rate source required for workout step.
#define FIT_WORKOUT_CAPABILITIES_DISTANCE                                        ((FIT_WORKOUT_CAPABILITIES)0x00000200) // Distance source required for workout step.
#define FIT_WORKOUT_CAPABILITIES_CADENCE                                         ((FIT_WORKOUT_CAPABILITIES)0x00000400) // Cadence source required for workout step.
#define FIT_WORKOUT_CAPABILITIES_POWER                                           ((FIT_WORKOUT_CAPABILITIES)0x00000800) // Power source required for workout step.
#define FIT_WORKOUT_CAPABILITIES_GRADE                                           ((FIT_WORKOUT_CAPABILITIES)0x00001000) // Grade source required for workout step.
#define FIT_WORKOUT_CAPABILITIES_RESISTANCE                                      ((FIT_WORKOUT_CAPABILITIES)0x00002000) // Resistance source required for workout step.
#define FIT_WORKOUT_CAPABILITIES_PROTECTED                                       ((FIT_WORKOUT_CAPABILITIES)0x00004000)
#define FIT_WORKOUT_CAPABILITIES_COUNT                                           14

typedef FIT_UINT8 FIT_BATTERY_STATUS;
#define FIT_BATTERY_STATUS_INVALID                                               FIT_UINT8_INVALID
#define FIT_BATTERY_STATUS_NEW                                                   ((FIT_BATTERY_STATUS)1)
#define FIT_BATTERY_STATUS_GOOD                                                  ((FIT_BATTERY_STATUS)2)
#define FIT_BATTERY_STATUS_OK                                                    ((FIT_BATTERY_STATUS)3)
#define FIT_BATTERY_STATUS_LOW                                                   ((FIT_BATTERY_STATUS)4)
#define FIT_BATTERY_STATUS_CRITICAL                                              ((FIT_BATTERY_STATUS)5)
#define FIT_BATTERY_STATUS_CHARGING                                              ((FIT_BATTERY_STATUS)6)
#define FIT_BATTERY_STATUS_UNKNOWN                                               ((FIT_BATTERY_STATUS)7)
#define FIT_BATTERY_STATUS_COUNT                                                 7

typedef FIT_ENUM FIT_HR_TYPE;
#define FIT_HR_TYPE_INVALID                                                      FIT_ENUM_INVALID
#define FIT_HR_TYPE_NORMAL                                                       ((FIT_HR_TYPE)0)
#define FIT_HR_TYPE_IRREGULAR                                                    ((FIT_HR_TYPE)1)
#define FIT_HR_TYPE_COUNT                                                        2

typedef FIT_UINT32Z FIT_COURSE_CAPABILITIES;
#define FIT_COURSE_CAPABILITIES_INVALID                                          FIT_UINT32Z_INVALID
#define FIT_COURSE_CAPABILITIES_PROCESSED                                        ((FIT_COURSE_CAPABILITIES)0x00000001)
#define FIT_COURSE_CAPABILITIES_VALID                                            ((FIT_COURSE_CAPABILITIES)0x00000002)
#define FIT_COURSE_CAPABILITIES_TIME                                             ((FIT_COURSE_CAPABILITIES)0x00000004)
#define FIT_COURSE_CAPABILITIES_DISTANCE                                         ((FIT_COURSE_CAPABILITIES)0x00000008)
#define FIT_COURSE_CAPABILITIES_POSITION                                         ((FIT_COURSE_CAPABILITIES)0x00000010)
#define FIT_COURSE_CAPABILITIES_HEART_RATE                                       ((FIT_COURSE_CAPABILITIES)0x00000020)
#define FIT_COURSE_CAPABILITIES_POWER                                            ((FIT_COURSE_CAPABILITIES)0x00000040)
#define FIT_COURSE_CAPABILITIES_CADENCE                                          ((FIT_COURSE_CAPABILITIES)0x00000080)
#define FIT_COURSE_CAPABILITIES_TRAINING                                         ((FIT_COURSE_CAPABILITIES)0x00000100)
#define FIT_COURSE_CAPABILITIES_NAVIGATION                                       ((FIT_COURSE_CAPABILITIES)0x00000200)
#define FIT_COURSE_CAPABILITIES_BIKEWAY                                          ((FIT_COURSE_CAPABILITIES)0x00000400)
#define FIT_COURSE_CAPABILITIES_COUNT                                            11

typedef FIT_UINT16 FIT_WEIGHT;
#define FIT_WEIGHT_INVALID                                                       FIT_UINT16_INVALID
#define FIT_WEIGHT_CALCULATING                                                   ((FIT_WEIGHT)0xFFFE)
#define FIT_WEIGHT_COUNT                                                         1

typedef FIT_UINT32 FIT_WORKOUT_HR; // 0 - 100 indicates% of max hr; >100 indicates bpm (255 max) plus 100
#define FIT_WORKOUT_HR_INVALID                                                   FIT_UINT32_INVALID
#define FIT_WORKOUT_HR_BPM_OFFSET                                                ((FIT_WORKOUT_HR)100)
#define FIT_WORKOUT_HR_COUNT                                                     1

typedef FIT_UINT32 FIT_WORKOUT_POWER; // 0 - 1000 indicates % of functional threshold power; >1000 indicates watts plus 1000.
#define FIT_WORKOUT_POWER_INVALID                                                FIT_UINT32_INVALID
#define FIT_WORKOUT_POWER_WATTS_OFFSET                                           ((FIT_WORKOUT_POWER)1000)
#define FIT_WORKOUT_POWER_COUNT                                                  1

typedef FIT_ENUM FIT_BP_STATUS;
#define FIT_BP_STATUS_INVALID                                                    FIT_ENUM_INVALID
#define FIT_BP_STATUS_NO_ERROR                                                   ((FIT_BP_STATUS)0)
#define FIT_BP_STATUS_ERROR_INCOMPLETE_DATA                                      ((FIT_BP_STATUS)1)
#define FIT_BP_STATUS_ERROR_NO_MEASUREMENT                                       ((FIT_BP_STATUS)2)
#define FIT_BP_STATUS_ERROR_DATA_OUT_OF_RANGE                                    ((FIT_BP_STATUS)3)
#define FIT_BP_STATUS_ERROR_IRREGULAR_HEART_RATE                                 ((FIT_BP_STATUS)4)
#define FIT_BP_STATUS_COUNT                                                      5

typedef FIT_UINT16 FIT_USER_LOCAL_ID;
#define FIT_USER_LOCAL_ID_INVALID                                                FIT_UINT16_INVALID
#define FIT_USER_LOCAL_ID_LOCAL_MIN                                              ((FIT_USER_LOCAL_ID)0x0000)
#define FIT_USER_LOCAL_ID_LOCAL_MAX                                              ((FIT_USER_LOCAL_ID)0x000F)
#define FIT_USER_LOCAL_ID_STATIONARY_MIN                                         ((FIT_USER_LOCAL_ID)0x0010)
#define FIT_USER_LOCAL_ID_STATIONARY_MAX                                         ((FIT_USER_LOCAL_ID)0x00FF)
#define FIT_USER_LOCAL_ID_PORTABLE_MIN                                           ((FIT_USER_LOCAL_ID)0x0100)
#define FIT_USER_LOCAL_ID_PORTABLE_MAX                                           ((FIT_USER_LOCAL_ID)0xFFFE)
#define FIT_USER_LOCAL_ID_COUNT                                                  6

typedef FIT_ENUM FIT_SWIM_STROKE;
#define FIT_SWIM_STROKE_INVALID                                                  FIT_ENUM_INVALID
#define FIT_SWIM_STROKE_FREESTYLE                                                ((FIT_SWIM_STROKE)0)
#define FIT_SWIM_STROKE_BACKSTROKE                                               ((FIT_SWIM_STROKE)1)
#define FIT_SWIM_STROKE_BREASTSTROKE                                             ((FIT_SWIM_STROKE)2)
#define FIT_SWIM_STROKE_BUTTERFLY                                                ((FIT_SWIM_STROKE)3)
#define FIT_SWIM_STROKE_DRILL                                                    ((FIT_SWIM_STROKE)4)
#define FIT_SWIM_STROKE_MIXED                                                    ((FIT_SWIM_STROKE)5)
#define FIT_SWIM_STROKE_IM                                                       ((FIT_SWIM_STROKE)6) // IM is a mixed interval containing the same number of lengths for each of: Butterfly, Backstroke, Breaststroke, Freestyle, swam in that order.
#define FIT_SWIM_STROKE_COUNT                                                    7

typedef FIT_ENUM FIT_ACTIVITY_TYPE;
#define FIT_ACTIVITY_TYPE_INVALID                                                FIT_ENUM_INVALID
#define FIT_ACTIVITY_TYPE_GENERIC                                                ((FIT_ACTIVITY_TYPE)0)
#define FIT_ACTIVITY_TYPE_RUNNING                                                ((FIT_ACTIVITY_TYPE)1)
#define FIT_ACTIVITY_TYPE_CYCLING                                                ((FIT_ACTIVITY_TYPE)2)
#define FIT_ACTIVITY_TYPE_TRANSITION                                             ((FIT_ACTIVITY_TYPE)3) // Mulitsport transition
#define FIT_ACTIVITY_TYPE_FITNESS_EQUIPMENT                                      ((FIT_ACTIVITY_TYPE)4)
#define FIT_ACTIVITY_TYPE_SWIMMING                                               ((FIT_ACTIVITY_TYPE)5)
#define FIT_ACTIVITY_TYPE_WALKING                                                ((FIT_ACTIVITY_TYPE)6)
#define FIT_ACTIVITY_TYPE_SEDENTARY                                              ((FIT_ACTIVITY_TYPE)8)
#define FIT_ACTIVITY_TYPE_ALL                                                    ((FIT_ACTIVITY_TYPE)254) // All is for goals only to include all sports.
#define FIT_ACTIVITY_TYPE_COUNT                                                  9

typedef FIT_ENUM FIT_ACTIVITY_SUBTYPE;
#define FIT_ACTIVITY_SUBTYPE_INVALID                                             FIT_ENUM_INVALID
#define FIT_ACTIVITY_SUBTYPE_GENERIC                                             ((FIT_ACTIVITY_SUBTYPE)0)
#define FIT_ACTIVITY_SUBTYPE_TREADMILL                                           ((FIT_ACTIVITY_SUBTYPE)1) // Run
#define FIT_ACTIVITY_SUBTYPE_STREET                                              ((FIT_ACTIVITY_SUBTYPE)2) // Run
#define FIT_ACTIVITY_SUBTYPE_TRAIL                                               ((FIT_ACTIVITY_SUBTYPE)3) // Run
#define FIT_ACTIVITY_SUBTYPE_TRACK                                               ((FIT_ACTIVITY_SUBTYPE)4) // Run
#define FIT_ACTIVITY_SUBTYPE_SPIN                                                ((FIT_ACTIVITY_SUBTYPE)5) // Cycling
#define FIT_ACTIVITY_SUBTYPE_INDOOR_CYCLING                                      ((FIT_ACTIVITY_SUBTYPE)6) // Cycling
#define FIT_ACTIVITY_SUBTYPE_ROAD                                                ((FIT_ACTIVITY_SUBTYPE)7) // Cycling
#define FIT_ACTIVITY_SUBTYPE_MOUNTAIN                                            ((FIT_ACTIVITY_SUBTYPE)8) // Cycling
#define FIT_ACTIVITY_SUBTYPE_DOWNHILL                                            ((FIT_ACTIVITY_SUBTYPE)9) // Cycling
#define FIT_ACTIVITY_SUBTYPE_RECUMBENT                                           ((FIT_ACTIVITY_SUBTYPE)10) // Cycling
#define FIT_ACTIVITY_SUBTYPE_CYCLOCROSS                                          ((FIT_ACTIVITY_SUBTYPE)11) // Cycling
#define FIT_ACTIVITY_SUBTYPE_HAND_CYCLING                                        ((FIT_ACTIVITY_SUBTYPE)12) // Cycling
#define FIT_ACTIVITY_SUBTYPE_TRACK_CYCLING                                       ((FIT_ACTIVITY_SUBTYPE)13) // Cycling
#define FIT_ACTIVITY_SUBTYPE_INDOOR_ROWING                                       ((FIT_ACTIVITY_SUBTYPE)14) // Fitness Equipment
#define FIT_ACTIVITY_SUBTYPE_ELLIPTICAL                                          ((FIT_ACTIVITY_SUBTYPE)15) // Fitness Equipment
#define FIT_ACTIVITY_SUBTYPE_STAIR_CLIMBING                                      ((FIT_ACTIVITY_SUBTYPE)16) // Fitness Equipment
#define FIT_ACTIVITY_SUBTYPE_LAP_SWIMMING                                        ((FIT_ACTIVITY_SUBTYPE)17) // Swimming
#define FIT_ACTIVITY_SUBTYPE_OPEN_WATER                                          ((FIT_ACTIVITY_SUBTYPE)18) // Swimming
#define FIT_ACTIVITY_SUBTYPE_ALL                                                 ((FIT_ACTIVITY_SUBTYPE)254)
#define FIT_ACTIVITY_SUBTYPE_COUNT                                               20

typedef FIT_ENUM FIT_ACTIVITY_LEVEL;
#define FIT_ACTIVITY_LEVEL_INVALID                                               FIT_ENUM_INVALID
#define FIT_ACTIVITY_LEVEL_LOW                                                   ((FIT_ACTIVITY_LEVEL)0)
#define FIT_ACTIVITY_LEVEL_MEDIUM                                                ((FIT_ACTIVITY_LEVEL)1)
#define FIT_ACTIVITY_LEVEL_HIGH                                                  ((FIT_ACTIVITY_LEVEL)2)
#define FIT_ACTIVITY_LEVEL_COUNT                                                 3

typedef FIT_ENUM FIT_SIDE;
#define FIT_SIDE_INVALID                                                         FIT_ENUM_INVALID
#define FIT_SIDE_RIGHT                                                           ((FIT_SIDE)0)
#define FIT_SIDE_LEFT                                                            ((FIT_SIDE)1)
#define FIT_SIDE_COUNT                                                           2

typedef FIT_UINT8 FIT_LEFT_RIGHT_BALANCE;
#define FIT_LEFT_RIGHT_BALANCE_INVALID                                           FIT_UINT8_INVALID
#define FIT_LEFT_RIGHT_BALANCE_MASK                                              ((FIT_LEFT_RIGHT_BALANCE)0x7F) // % contribution
#define FIT_LEFT_RIGHT_BALANCE_RIGHT                                             ((FIT_LEFT_RIGHT_BALANCE)0x80) // data corresponds to right if set, otherwise unknown
#define FIT_LEFT_RIGHT_BALANCE_COUNT                                             2

typedef FIT_UINT16 FIT_LEFT_RIGHT_BALANCE_100;
#define FIT_LEFT_RIGHT_BALANCE_100_INVALID                                       FIT_UINT16_INVALID
#define FIT_LEFT_RIGHT_BALANCE_100_MASK                                          ((FIT_LEFT_RIGHT_BALANCE_100)0x3FFF) // % contribution scaled by 100
#define FIT_LEFT_RIGHT_BALANCE_100_RIGHT                                         ((FIT_LEFT_RIGHT_BALANCE_100)0x8000) // data corresponds to right if set, otherwise unknown
#define FIT_LEFT_RIGHT_BALANCE_100_COUNT                                         2

typedef FIT_ENUM FIT_LENGTH_TYPE;
#define FIT_LENGTH_TYPE_INVALID                                                  FIT_ENUM_INVALID
#define FIT_LENGTH_TYPE_IDLE                                                     ((FIT_LENGTH_TYPE)0) // Rest period. Length with no strokes
#define FIT_LENGTH_TYPE_ACTIVE                                                   ((FIT_LENGTH_TYPE)1) // Length with strokes.
#define FIT_LENGTH_TYPE_COUNT                                                    2

typedef FIT_ENUM FIT_DAY_OF_WEEK;
#define FIT_DAY_OF_WEEK_INVALID                                                  FIT_ENUM_INVALID
#define FIT_DAY_OF_WEEK_SUNDAY                                                   ((FIT_DAY_OF_WEEK)0)
#define FIT_DAY_OF_WEEK_MONDAY                                                   ((FIT_DAY_OF_WEEK)1)
#define FIT_DAY_OF_WEEK_TUESDAY                                                  ((FIT_DAY_OF_WEEK)2)
#define FIT_DAY_OF_WEEK_WEDNESDAY                                                ((FIT_DAY_OF_WEEK)3)
#define FIT_DAY_OF_WEEK_THURSDAY                                                 ((FIT_DAY_OF_WEEK)4)
#define FIT_DAY_OF_WEEK_FRIDAY                                                   ((FIT_DAY_OF_WEEK)5)
#define FIT_DAY_OF_WEEK_SATURDAY                                                 ((FIT_DAY_OF_WEEK)6)
#define FIT_DAY_OF_WEEK_COUNT                                                    7

typedef FIT_UINT32Z FIT_CONNECTIVITY_CAPABILITIES;
#define FIT_CONNECTIVITY_CAPABILITIES_INVALID                                    FIT_UINT32Z_INVALID
#define FIT_CONNECTIVITY_CAPABILITIES_BLUETOOTH                                  ((FIT_CONNECTIVITY_CAPABILITIES)0x00000001)
#define FIT_CONNECTIVITY_CAPABILITIES_BLUETOOTH_LE                               ((FIT_CONNECTIVITY_CAPABILITIES)0x00000002)
#define FIT_CONNECTIVITY_CAPABILITIES_ANT                                        ((FIT_CONNECTIVITY_CAPABILITIES)0x00000004)
#define FIT_CONNECTIVITY_CAPABILITIES_ACTIVITY_UPLOAD                            ((FIT_CONNECTIVITY_CAPABILITIES)0x00000008)
#define FIT_CONNECTIVITY_CAPABILITIES_COURSE_DOWNLOAD                            ((FIT_CONNECTIVITY_CAPABILITIES)0x00000010)
#define FIT_CONNECTIVITY_CAPABILITIES_WORKOUT_DOWNLOAD                           ((FIT_CONNECTIVITY_CAPABILITIES)0x00000020)
#define FIT_CONNECTIVITY_CAPABILITIES_LIVE_TRACK                                 ((FIT_CONNECTIVITY_CAPABILITIES)0x00000040)
#define FIT_CONNECTIVITY_CAPABILITIES_WEATHER_CONDITIONS                         ((FIT_CONNECTIVITY_CAPABILITIES)0x00000080)
#define FIT_CONNECTIVITY_CAPABILITIES_WEATHER_ALERTS                             ((FIT_CONNECTIVITY_CAPABILITIES)0x00000100)
#define FIT_CONNECTIVITY_CAPABILITIES_GPS_EPHEMERIS_DOWNLOAD                     ((FIT_CONNECTIVITY_CAPABILITIES)0x00000200)
#define FIT_CONNECTIVITY_CAPABILITIES_EXPLICIT_ARCHIVE                           ((FIT_CONNECTIVITY_CAPABILITIES)0x00000400)
#define FIT_CONNECTIVITY_CAPABILITIES_SETUP_INCOMPLETE                           ((FIT_CONNECTIVITY_CAPABILITIES)0x00000800)
#define FIT_CONNECTIVITY_CAPABILITIES_CONTINUE_SYNC_AFTER_SOFTWARE_UPDATE        ((FIT_CONNECTIVITY_CAPABILITIES)0x00001000)
#define FIT_CONNECTIVITY_CAPABILITIES_CONNECT_IQ_APP_DOWNLOAD                    ((FIT_CONNECTIVITY_CAPABILITIES)0x00002000)
#define FIT_CONNECTIVITY_CAPABILITIES_GOLF_COURSE_DOWNLOAD                       ((FIT_CONNECTIVITY_CAPABILITIES)0x00004000)
#define FIT_CONNECTIVITY_CAPABILITIES_DEVICE_INITIATES_SYNC                      ((FIT_CONNECTIVITY_CAPABILITIES)0x00008000) // Indicates device is in control of initiating all syncs
#define FIT_CONNECTIVITY_CAPABILITIES_CONNECT_IQ_WATCH_APP_DOWNLOAD              ((FIT_CONNECTIVITY_CAPABILITIES)0x00010000)
#define FIT_CONNECTIVITY_CAPABILITIES_CONNECT_IQ_WIDGET_DOWNLOAD                 ((FIT_CONNECTIVITY_CAPABILITIES)0x00020000)
#define FIT_CONNECTIVITY_CAPABILITIES_CONNECT_IQ_WATCH_FACE_DOWNLOAD             ((FIT_CONNECTIVITY_CAPABILITIES)0x00040000)
#define FIT_CONNECTIVITY_CAPABILITIES_CONNECT_IQ_DATA_FIELD_DOWNLOAD             ((FIT_CONNECTIVITY_CAPABILITIES)0x00080000)
#define FIT_CONNECTIVITY_CAPABILITIES_CONNECT_IQ_APP_MANAGMENT                   ((FIT_CONNECTIVITY_CAPABILITIES)0x00100000) // Device supports delete and reorder of apps via GCM
#define FIT_CONNECTIVITY_CAPABILITIES_SWING_SENSOR                               ((FIT_CONNECTIVITY_CAPABILITIES)0x00200000)
#define FIT_CONNECTIVITY_CAPABILITIES_SWING_SENSOR_REMOTE                        ((FIT_CONNECTIVITY_CAPABILITIES)0x00400000)
#define FIT_CONNECTIVITY_CAPABILITIES_INCIDENT_DETECTION                         ((FIT_CONNECTIVITY_CAPABILITIES)0x00800000) // Device supports incident detection
#define FIT_CONNECTIVITY_CAPABILITIES_AUDIO_PROMPTS                              ((FIT_CONNECTIVITY_CAPABILITIES)0x01000000)
#define FIT_CONNECTIVITY_CAPABILITIES_WIFI_VERIFICATION                          ((FIT_CONNECTIVITY_CAPABILITIES)0x02000000) // Device supports reporting wifi verification via GCM
#define FIT_CONNECTIVITY_CAPABILITIES_TRUE_UP                                    ((FIT_CONNECTIVITY_CAPABILITIES)0x04000000) // Device supports True Up
#define FIT_CONNECTIVITY_CAPABILITIES_FIND_MY_WATCH                              ((FIT_CONNECTIVITY_CAPABILITIES)0x08000000) // Device supports Find My Watch
#define FIT_CONNECTIVITY_CAPABILITIES_REMOTE_MANUAL_SYNC                         ((FIT_CONNECTIVITY_CAPABILITIES)0x10000000)
#define FIT_CONNECTIVITY_CAPABILITIES_LIVE_TRACK_AUTO_START                      ((FIT_CONNECTIVITY_CAPABILITIES)0x20000000) // Device supports LiveTrack auto start
#define FIT_CONNECTIVITY_CAPABILITIES_LIVE_TRACK_MESSAGING                       ((FIT_CONNECTIVITY_CAPABILITIES)0x40000000) // Device supports LiveTrack Messaging
#define FIT_CONNECTIVITY_CAPABILITIES_INSTANT_INPUT                              ((FIT_CONNECTIVITY_CAPABILITIES)0x80000000) // Device supports instant input feature
#define FIT_CONNECTIVITY_CAPABILITIES_COUNT                                      32

typedef FIT_ENUM FIT_WEATHER_REPORT;
#define FIT_WEATHER_REPORT_INVALID                                               FIT_ENUM_INVALID
#define FIT_WEATHER_REPORT_CURRENT                                               ((FIT_WEATHER_REPORT)0)
#define FIT_WEATHER_REPORT_FORECAST                                              ((FIT_WEATHER_REPORT)1) // Deprecated use hourly_forecast instead
#define FIT_WEATHER_REPORT_HOURLY_FORECAST                                       ((FIT_WEATHER_REPORT)1)
#define FIT_WEATHER_REPORT_DAILY_FORECAST                                        ((FIT_WEATHER_REPORT)2)
#define FIT_WEATHER_REPORT_COUNT                                                 4

typedef FIT_ENUM FIT_WEATHER_STATUS;
#define FIT_WEATHER_STATUS_INVALID                                               FIT_ENUM_INVALID
#define FIT_WEATHER_STATUS_CLEAR                                                 ((FIT_WEATHER_STATUS)0)
#define FIT_WEATHER_STATUS_PARTLY_CLOUDY                                         ((FIT_WEATHER_STATUS)1)
#define FIT_WEATHER_STATUS_MOSTLY_CLOUDY                                         ((FIT_WEATHER_STATUS)2)
#define FIT_WEATHER_STATUS_RAIN                                                  ((FIT_WEATHER_STATUS)3)
#define FIT_WEATHER_STATUS_SNOW                                                  ((FIT_WEATHER_STATUS)4)
#define FIT_WEATHER_STATUS_WINDY                                                 ((FIT_WEATHER_STATUS)5)
#define FIT_WEATHER_STATUS_THUNDERSTORMS                                         ((FIT_WEATHER_STATUS)6)
#define FIT_WEATHER_STATUS_WINTRY_MIX                                            ((FIT_WEATHER_STATUS)7)
#define FIT_WEATHER_STATUS_FOG                                                   ((FIT_WEATHER_STATUS)8)
#define FIT_WEATHER_STATUS_HAZY                                                  ((FIT_WEATHER_STATUS)11)
#define FIT_WEATHER_STATUS_HAIL                                                  ((FIT_WEATHER_STATUS)12)
#define FIT_WEATHER_STATUS_SCATTERED_SHOWERS                                     ((FIT_WEATHER_STATUS)13)
#define FIT_WEATHER_STATUS_SCATTERED_THUNDERSTORMS                               ((FIT_WEATHER_STATUS)14)
#define FIT_WEATHER_STATUS_UNKNOWN_PRECIPITATION                                 ((FIT_WEATHER_STATUS)15)
#define FIT_WEATHER_STATUS_LIGHT_RAIN                                            ((FIT_WEATHER_STATUS)16)
#define FIT_WEATHER_STATUS_HEAVY_RAIN                                            ((FIT_WEATHER_STATUS)17)
#define FIT_WEATHER_STATUS_LIGHT_SNOW                                            ((FIT_WEATHER_STATUS)18)
#define FIT_WEATHER_STATUS_HEAVY_SNOW                                            ((FIT_WEATHER_STATUS)19)
#define FIT_WEATHER_STATUS_LIGHT_RAIN_SNOW                                       ((FIT_WEATHER_STATUS)20)
#define FIT_WEATHER_STATUS_HEAVY_RAIN_SNOW                                       ((FIT_WEATHER_STATUS)21)
#define FIT_WEATHER_STATUS_CLOUDY                                                ((FIT_WEATHER_STATUS)22)
#define FIT_WEATHER_STATUS_COUNT                                                 21

typedef FIT_ENUM FIT_WEATHER_SEVERITY;
#define FIT_WEATHER_SEVERITY_INVALID                                             FIT_ENUM_INVALID
#define FIT_WEATHER_SEVERITY_UNKNOWN                                             ((FIT_WEATHER_SEVERITY)0)
#define FIT_WEATHER_SEVERITY_WARNING                                             ((FIT_WEATHER_SEVERITY)1)
#define FIT_WEATHER_SEVERITY_WATCH                                               ((FIT_WEATHER_SEVERITY)2)
#define FIT_WEATHER_SEVERITY_ADVISORY                                            ((FIT_WEATHER_SEVERITY)3)
#define FIT_WEATHER_SEVERITY_STATEMENT                                           ((FIT_WEATHER_SEVERITY)4)
#define FIT_WEATHER_SEVERITY_COUNT                                               5

typedef FIT_ENUM FIT_WEATHER_SEVERE_TYPE;
#define FIT_WEATHER_SEVERE_TYPE_INVALID                                          FIT_ENUM_INVALID
#define FIT_WEATHER_SEVERE_TYPE_UNSPECIFIED                                      ((FIT_WEATHER_SEVERE_TYPE)0)
#define FIT_WEATHER_SEVERE_TYPE_TORNADO                                          ((FIT_WEATHER_SEVERE_TYPE)1)
#define FIT_WEATHER_SEVERE_TYPE_TSUNAMI                                          ((FIT_WEATHER_SEVERE_TYPE)2)
#define FIT_WEATHER_SEVERE_TYPE_HURRICANE                                        ((FIT_WEATHER_SEVERE_TYPE)3)
#define FIT_WEATHER_SEVERE_TYPE_EXTREME_WIND                                     ((FIT_WEATHER_SEVERE_TYPE)4)
#define FIT_WEATHER_SEVERE_TYPE_TYPHOON                                          ((FIT_WEATHER_SEVERE_TYPE)5)
#define FIT_WEATHER_SEVERE_TYPE_INLAND_HURRICANE                                 ((FIT_WEATHER_SEVERE_TYPE)6)
#define FIT_WEATHER_SEVERE_TYPE_HURRICANE_FORCE_WIND                             ((FIT_WEATHER_SEVERE_TYPE)7)
#define FIT_WEATHER_SEVERE_TYPE_WATERSPOUT                                       ((FIT_WEATHER_SEVERE_TYPE)8)
#define FIT_WEATHER_SEVERE_TYPE_SEVERE_THUNDERSTORM                              ((FIT_WEATHER_SEVERE_TYPE)9)
#define FIT_WEATHER_SEVERE_TYPE_WRECKHOUSE_WINDS                                 ((FIT_WEATHER_SEVERE_TYPE)10)
#define FIT_WEATHER_SEVERE_TYPE_LES_SUETES_WIND                                  ((FIT_WEATHER_SEVERE_TYPE)11)
#define FIT_WEATHER_SEVERE_TYPE_AVALANCHE                                        ((FIT_WEATHER_SEVERE_TYPE)12)
#define FIT_WEATHER_SEVERE_TYPE_FLASH_FLOOD                                      ((FIT_WEATHER_SEVERE_TYPE)13)
#define FIT_WEATHER_SEVERE_TYPE_TROPICAL_STORM                                   ((FIT_WEATHER_SEVERE_TYPE)14)
#define FIT_WEATHER_SEVERE_TYPE_INLAND_TROPICAL_STORM                            ((FIT_WEATHER_SEVERE_TYPE)15)
#define FIT_WEATHER_SEVERE_TYPE_BLIZZARD                                         ((FIT_WEATHER_SEVERE_TYPE)16)
#define FIT_WEATHER_SEVERE_TYPE_ICE_STORM                                        ((FIT_WEATHER_SEVERE_TYPE)17)
#define FIT_WEATHER_SEVERE_TYPE_FREEZING_RAIN                                    ((FIT_WEATHER_SEVERE_TYPE)18)
#define FIT_WEATHER_SEVERE_TYPE_DEBRIS_FLOW                                      ((FIT_WEATHER_SEVERE_TYPE)19)
#define FIT_WEATHER_SEVERE_TYPE_FLASH_FREEZE                                     ((FIT_WEATHER_SEVERE_TYPE)20)
#define FIT_WEATHER_SEVERE_TYPE_DUST_STORM                                       ((FIT_WEATHER_SEVERE_TYPE)21)
#define FIT_WEATHER_SEVERE_TYPE_HIGH_WIND                                        ((FIT_WEATHER_SEVERE_TYPE)22)
#define FIT_WEATHER_SEVERE_TYPE_WINTER_STORM                                     ((FIT_WEATHER_SEVERE_TYPE)23)
#define FIT_WEATHER_SEVERE_TYPE_HEAVY_FREEZING_SPRAY                             ((FIT_WEATHER_SEVERE_TYPE)24)
#define FIT_WEATHER_SEVERE_TYPE_EXTREME_COLD                                     ((FIT_WEATHER_SEVERE_TYPE)25)
#define FIT_WEATHER_SEVERE_TYPE_WIND_CHILL                                       ((FIT_WEATHER_SEVERE_TYPE)26)
#define FIT_WEATHER_SEVERE_TYPE_COLD_WAVE                                        ((FIT_WEATHER_SEVERE_TYPE)27)
#define FIT_WEATHER_SEVERE_TYPE_HEAVY_SNOW_ALERT                                 ((FIT_WEATHER_SEVERE_TYPE)28)
#define FIT_WEATHER_SEVERE_TYPE_LAKE_EFFECT_BLOWING_SNOW                         ((FIT_WEATHER_SEVERE_TYPE)29)
#define FIT_WEATHER_SEVERE_TYPE_SNOW_SQUALL                                      ((FIT_WEATHER_SEVERE_TYPE)30)
#define FIT_WEATHER_SEVERE_TYPE_LAKE_EFFECT_SNOW                                 ((FIT_WEATHER_SEVERE_TYPE)31)
#define FIT_WEATHER_SEVERE_TYPE_WINTER_WEATHER                                   ((FIT_WEATHER_SEVERE_TYPE)32)
#define FIT_WEATHER_SEVERE_TYPE_SLEET                                            ((FIT_WEATHER_SEVERE_TYPE)33)
#define FIT_WEATHER_SEVERE_TYPE_SNOWFALL                                         ((FIT_WEATHER_SEVERE_TYPE)34)
#define FIT_WEATHER_SEVERE_TYPE_SNOW_AND_BLOWING_SNOW                            ((FIT_WEATHER_SEVERE_TYPE)35)
#define FIT_WEATHER_SEVERE_TYPE_BLOWING_SNOW                                     ((FIT_WEATHER_SEVERE_TYPE)36)
#define FIT_WEATHER_SEVERE_TYPE_SNOW_ALERT                                       ((FIT_WEATHER_SEVERE_TYPE)37)
#define FIT_WEATHER_SEVERE_TYPE_ARCTIC_OUTFLOW                                   ((FIT_WEATHER_SEVERE_TYPE)38)
#define FIT_WEATHER_SEVERE_TYPE_FREEZING_DRIZZLE                                 ((FIT_WEATHER_SEVERE_TYPE)39)
#define FIT_WEATHER_SEVERE_TYPE_STORM                                            ((FIT_WEATHER_SEVERE_TYPE)40)
#define FIT_WEATHER_SEVERE_TYPE_STORM_SURGE                                      ((FIT_WEATHER_SEVERE_TYPE)41)
#define FIT_WEATHER_SEVERE_TYPE_RAINFALL                                         ((FIT_WEATHER_SEVERE_TYPE)42)
#define FIT_WEATHER_SEVERE_TYPE_AREAL_FLOOD                                      ((FIT_WEATHER_SEVERE_TYPE)43)
#define FIT_WEATHER_SEVERE_TYPE_COASTAL_FLOOD                                    ((FIT_WEATHER_SEVERE_TYPE)44)
#define FIT_WEATHER_SEVERE_TYPE_LAKESHORE_FLOOD                                  ((FIT_WEATHER_SEVERE_TYPE)45)
#define FIT_WEATHER_SEVERE_TYPE_EXCESSIVE_HEAT                                   ((FIT_WEATHER_SEVERE_TYPE)46)
#define FIT_WEATHER_SEVERE_TYPE_HEAT                                             ((FIT_WEATHER_SEVERE_TYPE)47)
#define FIT_WEATHER_SEVERE_TYPE_WEATHER                                          ((FIT_WEATHER_SEVERE_TYPE)48)
#define FIT_WEATHER_SEVERE_TYPE_HIGH_HEAT_AND_HUMIDITY                           ((FIT_WEATHER_SEVERE_TYPE)49)
#define FIT_WEATHER_SEVERE_TYPE_HUMIDEX_AND_HEALTH                               ((FIT_WEATHER_SEVERE_TYPE)50)
#define FIT_WEATHER_SEVERE_TYPE_HUMIDEX                                          ((FIT_WEATHER_SEVERE_TYPE)51)
#define FIT_WEATHER_SEVERE_TYPE_GALE                                             ((FIT_WEATHER_SEVERE_TYPE)52)
#define FIT_WEATHER_SEVERE_TYPE_FREEZING_SPRAY                                   ((FIT_WEATHER_SEVERE_TYPE)53)
#define FIT_WEATHER_SEVERE_TYPE_SPECIAL_MARINE                                   ((FIT_WEATHER_SEVERE_TYPE)54)
#define FIT_WEATHER_SEVERE_TYPE_SQUALL                                           ((FIT_WEATHER_SEVERE_TYPE)55)
#define FIT_WEATHER_SEVERE_TYPE_STRONG_WIND                                      ((FIT_WEATHER_SEVERE_TYPE)56)
#define FIT_WEATHER_SEVERE_TYPE_LAKE_WIND                                        ((FIT_WEATHER_SEVERE_TYPE)57)
#define FIT_WEATHER_SEVERE_TYPE_MARINE_WEATHER                                   ((FIT_WEATHER_SEVERE_TYPE)58)
#define FIT_WEATHER_SEVERE_TYPE_WIND                                             ((FIT_WEATHER_SEVERE_TYPE)59)
#define FIT_WEATHER_SEVERE_TYPE_SMALL_CRAFT_HAZARDOUS_SEAS                       ((FIT_WEATHER_SEVERE_TYPE)60)
#define FIT_WEATHER_SEVERE_TYPE_HAZARDOUS_SEAS                                   ((FIT_WEATHER_SEVERE_TYPE)61)
#define FIT_WEATHER_SEVERE_TYPE_SMALL_CRAFT                                      ((FIT_WEATHER_SEVERE_TYPE)62)
#define FIT_WEATHER_SEVERE_TYPE_SMALL_CRAFT_WINDS                                ((FIT_WEATHER_SEVERE_TYPE)63)
#define FIT_WEATHER_SEVERE_TYPE_SMALL_CRAFT_ROUGH_BAR                            ((FIT_WEATHER_SEVERE_TYPE)64)
#define FIT_WEATHER_SEVERE_TYPE_HIGH_WATER_LEVEL                                 ((FIT_WEATHER_SEVERE_TYPE)65)
#define FIT_WEATHER_SEVERE_TYPE_ASHFALL                                          ((FIT_WEATHER_SEVERE_TYPE)66)
#define FIT_WEATHER_SEVERE_TYPE_FREEZING_FOG                                     ((FIT_WEATHER_SEVERE_TYPE)67)
#define FIT_WEATHER_SEVERE_TYPE_DENSE_FOG                                        ((FIT_WEATHER_SEVERE_TYPE)68)
#define FIT_WEATHER_SEVERE_TYPE_DENSE_SMOKE                                      ((FIT_WEATHER_SEVERE_TYPE)69)
#define FIT_WEATHER_SEVERE_TYPE_BLOWING_DUST                                     ((FIT_WEATHER_SEVERE_TYPE)70)
#define FIT_WEATHER_SEVERE_TYPE_HARD_FREEZE                                      ((FIT_WEATHER_SEVERE_TYPE)71)
#define FIT_WEATHER_SEVERE_TYPE_FREEZE                                           ((FIT_WEATHER_SEVERE_TYPE)72)
#define FIT_WEATHER_SEVERE_TYPE_FROST                                            ((FIT_WEATHER_SEVERE_TYPE)73)
#define FIT_WEATHER_SEVERE_TYPE_FIRE_WEATHER                                     ((FIT_WEATHER_SEVERE_TYPE)74)
#define FIT_WEATHER_SEVERE_TYPE_FLOOD                                            ((FIT_WEATHER_SEVERE_TYPE)75)
#define FIT_WEATHER_SEVERE_TYPE_RIP_TIDE                                         ((FIT_WEATHER_SEVERE_TYPE)76)
#define FIT_WEATHER_SEVERE_TYPE_HIGH_SURF                                        ((FIT_WEATHER_SEVERE_TYPE)77)
#define FIT_WEATHER_SEVERE_TYPE_SMOG                                             ((FIT_WEATHER_SEVERE_TYPE)78)
#define FIT_WEATHER_SEVERE_TYPE_AIR_QUALITY                                      ((FIT_WEATHER_SEVERE_TYPE)79)
#define FIT_WEATHER_SEVERE_TYPE_BRISK_WIND                                       ((FIT_WEATHER_SEVERE_TYPE)80)
#define FIT_WEATHER_SEVERE_TYPE_AIR_STAGNATION                                   ((FIT_WEATHER_SEVERE_TYPE)81)
#define FIT_WEATHER_SEVERE_TYPE_LOW_WATER                                        ((FIT_WEATHER_SEVERE_TYPE)82)
#define FIT_WEATHER_SEVERE_TYPE_HYDROLOGICAL                                     ((FIT_WEATHER_SEVERE_TYPE)83)
#define FIT_WEATHER_SEVERE_TYPE_SPECIAL_WEATHER                                  ((FIT_WEATHER_SEVERE_TYPE)84)
#define FIT_WEATHER_SEVERE_TYPE_COUNT                                            85

typedef FIT_UINT32 FIT_TIME_INTO_DAY; // number of seconds into the day since 00:00:00 UTC
#define FIT_TIME_INTO_DAY_INVALID                                                FIT_UINT32_INVALID
#define FIT_TIME_INTO_DAY_COUNT                                                  0

typedef FIT_UINT32 FIT_LOCALTIME_INTO_DAY; // number of seconds into the day since local 00:00:00
#define FIT_LOCALTIME_INTO_DAY_INVALID                                           FIT_UINT32_INVALID
#define FIT_LOCALTIME_INTO_DAY_COUNT                                             0

typedef FIT_ENUM FIT_STROKE_TYPE;
#define FIT_STROKE_TYPE_INVALID                                                  FIT_ENUM_INVALID
#define FIT_STROKE_TYPE_NO_EVENT                                                 ((FIT_STROKE_TYPE)0)
#define FIT_STROKE_TYPE_OTHER                                                    ((FIT_STROKE_TYPE)1) // stroke was detected but cannot be identified
#define FIT_STROKE_TYPE_SERVE                                                    ((FIT_STROKE_TYPE)2)
#define FIT_STROKE_TYPE_FOREHAND                                                 ((FIT_STROKE_TYPE)3)
#define FIT_STROKE_TYPE_BACKHAND                                                 ((FIT_STROKE_TYPE)4)
#define FIT_STROKE_TYPE_SMASH                                                    ((FIT_STROKE_TYPE)5)
#define FIT_STROKE_TYPE_COUNT                                                    6

typedef FIT_ENUM FIT_BODY_LOCATION;
#define FIT_BODY_LOCATION_INVALID                                                FIT_ENUM_INVALID
#define FIT_BODY_LOCATION_LEFT_LEG                                               ((FIT_BODY_LOCATION)0)
#define FIT_BODY_LOCATION_LEFT_CALF                                              ((FIT_BODY_LOCATION)1)
#define FIT_BODY_LOCATION_LEFT_SHIN                                              ((FIT_BODY_LOCATION)2)
#define FIT_BODY_LOCATION_LEFT_HAMSTRING                                         ((FIT_BODY_LOCATION)3)
#define FIT_BODY_LOCATION_LEFT_QUAD                                              ((FIT_BODY_LOCATION)4)
#define FIT_BODY_LOCATION_LEFT_GLUTE                                             ((FIT_BODY_LOCATION)5)
#define FIT_BODY_LOCATION_RIGHT_LEG                                              ((FIT_BODY_LOCATION)6)
#define FIT_BODY_LOCATION_RIGHT_CALF                                             ((FIT_BODY_LOCATION)7)
#define FIT_BODY_LOCATION_RIGHT_SHIN                                             ((FIT_BODY_LOCATION)8)
#define FIT_BODY_LOCATION_RIGHT_HAMSTRING                                        ((FIT_BODY_LOCATION)9)
#define FIT_BODY_LOCATION_RIGHT_QUAD                                             ((FIT_BODY_LOCATION)10)
#define FIT_BODY_LOCATION_RIGHT_GLUTE                                            ((FIT_BODY_LOCATION)11)
#define FIT_BODY_LOCATION_TORSO_BACK                                             ((FIT_BODY_LOCATION)12)
#define FIT_BODY_LOCATION_LEFT_LOWER_BACK                                        ((FIT_BODY_LOCATION)13)
#define FIT_BODY_LOCATION_LEFT_UPPER_BACK                                        ((FIT_BODY_LOCATION)14)
#define FIT_BODY_LOCATION_RIGHT_LOWER_BACK                                       ((FIT_BODY_LOCATION)15)
#define FIT_BODY_LOCATION_RIGHT_UPPER_BACK                                       ((FIT_BODY_LOCATION)16)
#define FIT_BODY_LOCATION_TORSO_FRONT                                            ((FIT_BODY_LOCATION)17)
#define FIT_BODY_LOCATION_LEFT_ABDOMEN                                           ((FIT_BODY_LOCATION)18)
#define FIT_BODY_LOCATION_LEFT_CHEST                                             ((FIT_BODY_LOCATION)19)
#define FIT_BODY_LOCATION_RIGHT_ABDOMEN                                          ((FIT_BODY_LOCATION)20)
#define FIT_BODY_LOCATION_RIGHT_CHEST                                            ((FIT_BODY_LOCATION)21)
#define FIT_BODY_LOCATION_LEFT_ARM                                               ((FIT_BODY_LOCATION)22)
#define FIT_BODY_LOCATION_LEFT_SHOULDER                                          ((FIT_BODY_LOCATION)23)
#define FIT_BODY_LOCATION_LEFT_BICEP                                             ((FIT_BODY_LOCATION)24)
#define FIT_BODY_LOCATION_LEFT_TRICEP                                            ((FIT_BODY_LOCATION)25)
#define FIT_BODY_LOCATION_LEFT_BRACHIORADIALIS                                   ((FIT_BODY_LOCATION)26) // Left anterior forearm
#define FIT_BODY_LOCATION_LEFT_FOREARM_EXTENSORS                                 ((FIT_BODY_LOCATION)27) // Left posterior forearm
#define FIT_BODY_LOCATION_RIGHT_ARM                                              ((FIT_BODY_LOCATION)28)
#define FIT_BODY_LOCATION_RIGHT_SHOULDER                                         ((FIT_BODY_LOCATION)29)
#define FIT_BODY_LOCATION_RIGHT_BICEP                                            ((FIT_BODY_LOCATION)30)
#define FIT_BODY_LOCATION_RIGHT_TRICEP                                           ((FIT_BODY_LOCATION)31)
#define FIT_BODY_LOCATION_RIGHT_BRACHIORADIALIS                                  ((FIT_BODY_LOCATION)32) // Right anterior forearm
#define FIT_BODY_LOCATION_RIGHT_FOREARM_EXTENSORS                                ((FIT_BODY_LOCATION)33) // Right posterior forearm
#define FIT_BODY_LOCATION_NECK                                                   ((FIT_BODY_LOCATION)34)
#define FIT_BODY_LOCATION_THROAT                                                 ((FIT_BODY_LOCATION)35)
#define FIT_BODY_LOCATION_WAIST_MID_BACK                                         ((FIT_BODY_LOCATION)36)
#define FIT_BODY_LOCATION_WAIST_FRONT                                            ((FIT_BODY_LOCATION)37)
#define FIT_BODY_LOCATION_WAIST_LEFT                                             ((FIT_BODY_LOCATION)38)
#define FIT_BODY_LOCATION_WAIST_RIGHT                                            ((FIT_BODY_LOCATION)39)
#define FIT_BODY_LOCATION_COUNT                                                  40

typedef FIT_ENUM FIT_SEGMENT_LAP_STATUS;
#define FIT_SEGMENT_LAP_STATUS_INVALID                                           FIT_ENUM_INVALID
#define FIT_SEGMENT_LAP_STATUS_END                                               ((FIT_SEGMENT_LAP_STATUS)0)
#define FIT_SEGMENT_LAP_STATUS_FAIL                                              ((FIT_SEGMENT_LAP_STATUS)1)
#define FIT_SEGMENT_LAP_STATUS_COUNT                                             2

typedef FIT_ENUM FIT_SEGMENT_LEADERBOARD_TYPE;
#define FIT_SEGMENT_LEADERBOARD_TYPE_INVALID                                     FIT_ENUM_INVALID
#define FIT_SEGMENT_LEADERBOARD_TYPE_OVERALL                                     ((FIT_SEGMENT_LEADERBOARD_TYPE)0)
#define FIT_SEGMENT_LEADERBOARD_TYPE_PERSONAL_BEST                               ((FIT_SEGMENT_LEADERBOARD_TYPE)1)
#define FIT_SEGMENT_LEADERBOARD_TYPE_CONNECTIONS                                 ((FIT_SEGMENT_LEADERBOARD_TYPE)2)
#define FIT_SEGMENT_LEADERBOARD_TYPE_GROUP                                       ((FIT_SEGMENT_LEADERBOARD_TYPE)3)
#define FIT_SEGMENT_LEADERBOARD_TYPE_CHALLENGER                                  ((FIT_SEGMENT_LEADERBOARD_TYPE)4)
#define FIT_SEGMENT_LEADERBOARD_TYPE_KOM                                         ((FIT_SEGMENT_LEADERBOARD_TYPE)5)
#define FIT_SEGMENT_LEADERBOARD_TYPE_QOM                                         ((FIT_SEGMENT_LEADERBOARD_TYPE)6)
#define FIT_SEGMENT_LEADERBOARD_TYPE_PR                                          ((FIT_SEGMENT_LEADERBOARD_TYPE)7)
#define FIT_SEGMENT_LEADERBOARD_TYPE_GOAL                                        ((FIT_SEGMENT_LEADERBOARD_TYPE)8)
#define FIT_SEGMENT_LEADERBOARD_TYPE_RIVAL                                       ((FIT_SEGMENT_LEADERBOARD_TYPE)9)
#define FIT_SEGMENT_LEADERBOARD_TYPE_CLUB_LEADER                                 ((FIT_SEGMENT_LEADERBOARD_TYPE)10)
#define FIT_SEGMENT_LEADERBOARD_TYPE_COUNT                                       11

typedef FIT_ENUM FIT_SEGMENT_DELETE_STATUS;
#define FIT_SEGMENT_DELETE_STATUS_INVALID                                        FIT_ENUM_INVALID
#define FIT_SEGMENT_DELETE_STATUS_DO_NOT_DELETE                                  ((FIT_SEGMENT_DELETE_STATUS)0)
#define FIT_SEGMENT_DELETE_STATUS_DELETE_ONE                                     ((FIT_SEGMENT_DELETE_STATUS)1)
#define FIT_SEGMENT_DELETE_STATUS_DELETE_ALL                                     ((FIT_SEGMENT_DELETE_STATUS)2)
#define FIT_SEGMENT_DELETE_STATUS_COUNT                                          3

typedef FIT_ENUM FIT_SEGMENT_SELECTION_TYPE;
#define FIT_SEGMENT_SELECTION_TYPE_INVALID                                       FIT_ENUM_INVALID
#define FIT_SEGMENT_SELECTION_TYPE_STARRED                                       ((FIT_SEGMENT_SELECTION_TYPE)0)
#define FIT_SEGMENT_SELECTION_TYPE_SUGGESTED                                     ((FIT_SEGMENT_SELECTION_TYPE)1)
#define FIT_SEGMENT_SELECTION_TYPE_COUNT                                         2

typedef FIT_ENUM FIT_SOURCE_TYPE;
#define FIT_SOURCE_TYPE_INVALID                                                  FIT_ENUM_INVALID
#define FIT_SOURCE_TYPE_ANT                                                      ((FIT_SOURCE_TYPE)0) // External device connected with ANT
#define FIT_SOURCE_TYPE_ANTPLUS                                                  ((FIT_SOURCE_TYPE)1) // External device connected with ANT+
#define FIT_SOURCE_TYPE_BLUETOOTH                                                ((FIT_SOURCE_TYPE)2) // External device connected with BT
#define FIT_SOURCE_TYPE_BLUETOOTH_LOW_ENERGY                                     ((FIT_SOURCE_TYPE)3) // External device connected with BLE
#define FIT_SOURCE_TYPE_WIFI                                                     ((FIT_SOURCE_TYPE)4) // External device connected with Wifi
#define FIT_SOURCE_TYPE_LOCAL                                                    ((FIT_SOURCE_TYPE)5) // Onboard device
#define FIT_SOURCE_TYPE_COUNT                                                    6

typedef FIT_UINT8 FIT_LOCAL_DEVICE_TYPE;
#define FIT_LOCAL_DEVICE_TYPE_INVALID                                            FIT_UINT8_INVALID
#define FIT_LOCAL_DEVICE_TYPE_COUNT                                              0

typedef FIT_ENUM FIT_DISPLAY_ORIENTATION;
#define FIT_DISPLAY_ORIENTATION_INVALID                                          FIT_ENUM_INVALID
#define FIT_DISPLAY_ORIENTATION_AUTO                                             ((FIT_DISPLAY_ORIENTATION)0) // automatic if the device supports it
#define FIT_DISPLAY_ORIENTATION_PORTRAIT                                         ((FIT_DISPLAY_ORIENTATION)1)
#define FIT_DISPLAY_ORIENTATION_LANDSCAPE                                        ((FIT_DISPLAY_ORIENTATION)2)
#define FIT_DISPLAY_ORIENTATION_PORTRAIT_FLIPPED                                 ((FIT_DISPLAY_ORIENTATION)3) // portrait mode but rotated 180 degrees
#define FIT_DISPLAY_ORIENTATION_LANDSCAPE_FLIPPED                                ((FIT_DISPLAY_ORIENTATION)4) // landscape mode but rotated 180 degrees
#define FIT_DISPLAY_ORIENTATION_COUNT                                            5

typedef FIT_ENUM FIT_WORKOUT_EQUIPMENT;
#define FIT_WORKOUT_EQUIPMENT_INVALID                                            FIT_ENUM_INVALID
#define FIT_WORKOUT_EQUIPMENT_NONE                                               ((FIT_WORKOUT_EQUIPMENT)0)
#define FIT_WORKOUT_EQUIPMENT_SWIM_FINS                                          ((FIT_WORKOUT_EQUIPMENT)1)
#define FIT_WORKOUT_EQUIPMENT_SWIM_KICKBOARD                                     ((FIT_WORKOUT_EQUIPMENT)2)
#define FIT_WORKOUT_EQUIPMENT_SWIM_PADDLES                                       ((FIT_WORKOUT_EQUIPMENT)3)
#define FIT_WORKOUT_EQUIPMENT_SWIM_PULL_BUOY                                     ((FIT_WORKOUT_EQUIPMENT)4)
#define FIT_WORKOUT_EQUIPMENT_SWIM_SNORKEL                                       ((FIT_WORKOUT_EQUIPMENT)5)
#define FIT_WORKOUT_EQUIPMENT_COUNT                                              6

typedef FIT_ENUM FIT_WATCHFACE_MODE;
#define FIT_WATCHFACE_MODE_INVALID                                               FIT_ENUM_INVALID
#define FIT_WATCHFACE_MODE_DIGITAL                                               ((FIT_WATCHFACE_MODE)0)
#define FIT_WATCHFACE_MODE_ANALOG                                                ((FIT_WATCHFACE_MODE)1)
#define FIT_WATCHFACE_MODE_CONNECT_IQ                                            ((FIT_WATCHFACE_MODE)2)
#define FIT_WATCHFACE_MODE_DISABLED                                              ((FIT_WATCHFACE_MODE)3)
#define FIT_WATCHFACE_MODE_COUNT                                                 4

typedef FIT_ENUM FIT_DIGITAL_WATCHFACE_LAYOUT;
#define FIT_DIGITAL_WATCHFACE_LAYOUT_INVALID                                     FIT_ENUM_INVALID
#define FIT_DIGITAL_WATCHFACE_LAYOUT_TRADITIONAL                                 ((FIT_DIGITAL_WATCHFACE_LAYOUT)0)
#define FIT_DIGITAL_WATCHFACE_LAYOUT_MODERN                                      ((FIT_DIGITAL_WATCHFACE_LAYOUT)1)
#define FIT_DIGITAL_WATCHFACE_LAYOUT_BOLD                                        ((FIT_DIGITAL_WATCHFACE_LAYOUT)2)
#define FIT_DIGITAL_WATCHFACE_LAYOUT_COUNT                                       3

typedef FIT_ENUM FIT_ANALOG_WATCHFACE_LAYOUT;
#define FIT_ANALOG_WATCHFACE_LAYOUT_INVALID                                      FIT_ENUM_INVALID
#define FIT_ANALOG_WATCHFACE_LAYOUT_MINIMAL                                      ((FIT_ANALOG_WATCHFACE_LAYOUT)0)
#define FIT_ANALOG_WATCHFACE_LAYOUT_TRADITIONAL                                  ((FIT_ANALOG_WATCHFACE_LAYOUT)1)
#define FIT_ANALOG_WATCHFACE_LAYOUT_MODERN                                       ((FIT_ANALOG_WATCHFACE_LAYOUT)2)
#define FIT_ANALOG_WATCHFACE_LAYOUT_COUNT                                        3

typedef FIT_ENUM FIT_RIDER_POSITION_TYPE;
#define FIT_RIDER_POSITION_TYPE_INVALID                                          FIT_ENUM_INVALID
#define FIT_RIDER_POSITION_TYPE_SEATED                                           ((FIT_RIDER_POSITION_TYPE)0)
#define FIT_RIDER_POSITION_TYPE_STANDING                                         ((FIT_RIDER_POSITION_TYPE)1)
#define FIT_RIDER_POSITION_TYPE_TRANSITION_TO_SEATED                             ((FIT_RIDER_POSITION_TYPE)2)
#define FIT_RIDER_POSITION_TYPE_TRANSITION_TO_STANDING                           ((FIT_RIDER_POSITION_TYPE)3)
#define FIT_RIDER_POSITION_TYPE_COUNT                                            4

typedef FIT_ENUM FIT_POWER_PHASE_TYPE;
#define FIT_POWER_PHASE_TYPE_INVALID                                             FIT_ENUM_INVALID
#define FIT_POWER_PHASE_TYPE_POWER_PHASE_START_ANGLE                             ((FIT_POWER_PHASE_TYPE)0)
#define FIT_POWER_PHASE_TYPE_POWER_PHASE_END_ANGLE                               ((FIT_POWER_PHASE_TYPE)1)
#define FIT_POWER_PHASE_TYPE_POWER_PHASE_ARC_LENGTH                              ((FIT_POWER_PHASE_TYPE)2)
#define FIT_POWER_PHASE_TYPE_POWER_PHASE_CENTER                                  ((FIT_POWER_PHASE_TYPE)3)
#define FIT_POWER_PHASE_TYPE_COUNT                                               4

typedef FIT_ENUM FIT_CAMERA_EVENT_TYPE;
#define FIT_CAMERA_EVENT_TYPE_INVALID                                            FIT_ENUM_INVALID
#define FIT_CAMERA_EVENT_TYPE_VIDEO_START                                        ((FIT_CAMERA_EVENT_TYPE)0) // Start of video recording
#define FIT_CAMERA_EVENT_TYPE_VIDEO_SPLIT                                        ((FIT_CAMERA_EVENT_TYPE)1) // Mark of video file split (end of one file, beginning of the other)
#define FIT_CAMERA_EVENT_TYPE_VIDEO_END                                          ((FIT_CAMERA_EVENT_TYPE)2) // End of video recording
#define FIT_CAMERA_EVENT_TYPE_PHOTO_TAKEN                                        ((FIT_CAMERA_EVENT_TYPE)3) // Still photo taken
#define FIT_CAMERA_EVENT_TYPE_VIDEO_SECOND_STREAM_START                          ((FIT_CAMERA_EVENT_TYPE)4)
#define FIT_CAMERA_EVENT_TYPE_VIDEO_SECOND_STREAM_SPLIT                          ((FIT_CAMERA_EVENT_TYPE)5)
#define FIT_CAMERA_EVENT_TYPE_VIDEO_SECOND_STREAM_END                            ((FIT_CAMERA_EVENT_TYPE)6)
#define FIT_CAMERA_EVENT_TYPE_VIDEO_SPLIT_START                                  ((FIT_CAMERA_EVENT_TYPE)7) // Mark of video file split start
#define FIT_CAMERA_EVENT_TYPE_VIDEO_SECOND_STREAM_SPLIT_START                    ((FIT_CAMERA_EVENT_TYPE)8)
#define FIT_CAMERA_EVENT_TYPE_VIDEO_PAUSE                                        ((FIT_CAMERA_EVENT_TYPE)11) // Mark when a video recording has been paused
#define FIT_CAMERA_EVENT_TYPE_VIDEO_SECOND_STREAM_PAUSE                          ((FIT_CAMERA_EVENT_TYPE)12)
#define FIT_CAMERA_EVENT_TYPE_VIDEO_RESUME                                       ((FIT_CAMERA_EVENT_TYPE)13) // Mark when a video recording has been resumed
#define FIT_CAMERA_EVENT_TYPE_VIDEO_SECOND_STREAM_RESUME                         ((FIT_CAMERA_EVENT_TYPE)14)
#define FIT_CAMERA_EVENT_TYPE_COUNT                                              13

typedef FIT_ENUM FIT_SENSOR_TYPE;
#define FIT_SENSOR_TYPE_INVALID                                                  FIT_ENUM_INVALID
#define FIT_SENSOR_TYPE_ACCELEROMETER                                            ((FIT_SENSOR_TYPE)0)
#define FIT_SENSOR_TYPE_GYROSCOPE                                                ((FIT_SENSOR_TYPE)1)
#define FIT_SENSOR_TYPE_COMPASS                                                  ((FIT_SENSOR_TYPE)2) // Magnetometer
#define FIT_SENSOR_TYPE_BAROMETER                                                ((FIT_SENSOR_TYPE)3)
#define FIT_SENSOR_TYPE_COUNT                                                    4

typedef FIT_ENUM FIT_BIKE_LIGHT_NETWORK_CONFIG_TYPE;
#define FIT_BIKE_LIGHT_NETWORK_CONFIG_TYPE_INVALID                               FIT_ENUM_INVALID
#define FIT_BIKE_LIGHT_NETWORK_CONFIG_TYPE_AUTO                                  ((FIT_BIKE_LIGHT_NETWORK_CONFIG_TYPE)0)
#define FIT_BIKE_LIGHT_NETWORK_CONFIG_TYPE_INDIVIDUAL                            ((FIT_BIKE_LIGHT_NETWORK_CONFIG_TYPE)4)
#define FIT_BIKE_LIGHT_NETWORK_CONFIG_TYPE_HIGH_VISIBILITY                       ((FIT_BIKE_LIGHT_NETWORK_CONFIG_TYPE)5)
#define FIT_BIKE_LIGHT_NETWORK_CONFIG_TYPE_TRAIL                                 ((FIT_BIKE_LIGHT_NETWORK_CONFIG_TYPE)6)
#define FIT_BIKE_LIGHT_NETWORK_CONFIG_TYPE_COUNT                                 4

typedef FIT_UINT16 FIT_COMM_TIMEOUT_TYPE;
#define FIT_COMM_TIMEOUT_TYPE_INVALID                                            FIT_UINT16_INVALID
#define FIT_COMM_TIMEOUT_TYPE_WILDCARD_PAIRING_TIMEOUT                           ((FIT_COMM_TIMEOUT_TYPE)0) // Timeout pairing to any device
#define FIT_COMM_TIMEOUT_TYPE_PAIRING_TIMEOUT                                    ((FIT_COMM_TIMEOUT_TYPE)1) // Timeout pairing to previously paired device
#define FIT_COMM_TIMEOUT_TYPE_CONNECTION_LOST                                    ((FIT_COMM_TIMEOUT_TYPE)2) // Temporary loss of communications
#define FIT_COMM_TIMEOUT_TYPE_CONNECTION_TIMEOUT                                 ((FIT_COMM_TIMEOUT_TYPE)3) // Connection closed due to extended bad communications
#define FIT_COMM_TIMEOUT_TYPE_COUNT                                              4

typedef FIT_ENUM FIT_CAMERA_ORIENTATION_TYPE;
#define FIT_CAMERA_ORIENTATION_TYPE_INVALID                                      FIT_ENUM_INVALID
#define FIT_CAMERA_ORIENTATION_TYPE_CAMERA_ORIENTATION_0                         ((FIT_CAMERA_ORIENTATION_TYPE)0)
#define FIT_CAMERA_ORIENTATION_TYPE_CAMERA_ORIENTATION_90                        ((FIT_CAMERA_ORIENTATION_TYPE)1)
#define FIT_CAMERA_ORIENTATION_TYPE_CAMERA_ORIENTATION_180                       ((FIT_CAMERA_ORIENTATION_TYPE)2)
#define FIT_CAMERA_ORIENTATION_TYPE_CAMERA_ORIENTATION_270                       ((FIT_CAMERA_ORIENTATION_TYPE)3)
#define FIT_CAMERA_ORIENTATION_TYPE_COUNT                                        4

typedef FIT_ENUM FIT_ATTITUDE_STAGE;
#define FIT_ATTITUDE_STAGE_INVALID                                               FIT_ENUM_INVALID
#define FIT_ATTITUDE_STAGE_FAILED                                                ((FIT_ATTITUDE_STAGE)0)
#define FIT_ATTITUDE_STAGE_ALIGNING                                              ((FIT_ATTITUDE_STAGE)1)
#define FIT_ATTITUDE_STAGE_DEGRADED                                              ((FIT_ATTITUDE_STAGE)2)
#define FIT_ATTITUDE_STAGE_VALID                                                 ((FIT_ATTITUDE_STAGE)3)
#define FIT_ATTITUDE_STAGE_COUNT                                                 4

typedef FIT_UINT16 FIT_ATTITUDE_VALIDITY;
#define FIT_ATTITUDE_VALIDITY_INVALID                                            FIT_UINT16_INVALID
#define FIT_ATTITUDE_VALIDITY_TRACK_ANGLE_HEADING_VALID                          ((FIT_ATTITUDE_VALIDITY)0x0001)
#define FIT_ATTITUDE_VALIDITY_PITCH_VALID                                        ((FIT_ATTITUDE_VALIDITY)0x0002)
#define FIT_ATTITUDE_VALIDITY_ROLL_VALID                                         ((FIT_ATTITUDE_VALIDITY)0x0004)
#define FIT_ATTITUDE_VALIDITY_LATERAL_BODY_ACCEL_VALID                           ((FIT_ATTITUDE_VALIDITY)0x0008)
#define FIT_ATTITUDE_VALIDITY_NORMAL_BODY_ACCEL_VALID                            ((FIT_ATTITUDE_VALIDITY)0x0010)
#define FIT_ATTITUDE_VALIDITY_TURN_RATE_VALID                                    ((FIT_ATTITUDE_VALIDITY)0x0020)
#define FIT_ATTITUDE_VALIDITY_HW_FAIL                                            ((FIT_ATTITUDE_VALIDITY)0x0040)
#define FIT_ATTITUDE_VALIDITY_MAG_INVALID                                        ((FIT_ATTITUDE_VALIDITY)0x0080)
#define FIT_ATTITUDE_VALIDITY_NO_GPS                                             ((FIT_ATTITUDE_VALIDITY)0x0100)
#define FIT_ATTITUDE_VALIDITY_GPS_INVALID                                        ((FIT_ATTITUDE_VALIDITY)0x0200)
#define FIT_ATTITUDE_VALIDITY_SOLUTION_COASTING                                  ((FIT_ATTITUDE_VALIDITY)0x0400)
#define FIT_ATTITUDE_VALIDITY_TRUE_TRACK_ANGLE                                   ((FIT_ATTITUDE_VALIDITY)0x0800)
#define FIT_ATTITUDE_VALIDITY_MAGNETIC_HEADING                                   ((FIT_ATTITUDE_VALIDITY)0x1000)
#define FIT_ATTITUDE_VALIDITY_COUNT                                              13

typedef FIT_ENUM FIT_AUTO_SYNC_FREQUENCY;
#define FIT_AUTO_SYNC_FREQUENCY_INVALID                                          FIT_ENUM_INVALID
#define FIT_AUTO_SYNC_FREQUENCY_NEVER                                            ((FIT_AUTO_SYNC_FREQUENCY)0)
#define FIT_AUTO_SYNC_FREQUENCY_OCCASIONALLY                                     ((FIT_AUTO_SYNC_FREQUENCY)1)
#define FIT_AUTO_SYNC_FREQUENCY_FREQUENT                                         ((FIT_AUTO_SYNC_FREQUENCY)2)
#define FIT_AUTO_SYNC_FREQUENCY_ONCE_A_DAY                                       ((FIT_AUTO_SYNC_FREQUENCY)3)
#define FIT_AUTO_SYNC_FREQUENCY_REMOTE                                           ((FIT_AUTO_SYNC_FREQUENCY)4)
#define FIT_AUTO_SYNC_FREQUENCY_COUNT                                            5

typedef FIT_ENUM FIT_EXD_LAYOUT;
#define FIT_EXD_LAYOUT_INVALID                                                   FIT_ENUM_INVALID
#define FIT_EXD_LAYOUT_FULL_SCREEN                                               ((FIT_EXD_LAYOUT)0)
#define FIT_EXD_LAYOUT_HALF_VERTICAL                                             ((FIT_EXD_LAYOUT)1)
#define FIT_EXD_LAYOUT_HALF_HORIZONTAL                                           ((FIT_EXD_LAYOUT)2)
#define FIT_EXD_LAYOUT_HALF_VERTICAL_RIGHT_SPLIT                                 ((FIT_EXD_LAYOUT)3)
#define FIT_EXD_LAYOUT_HALF_HORIZONTAL_BOTTOM_SPLIT                              ((FIT_EXD_LAYOUT)4)
#define FIT_EXD_LAYOUT_FULL_QUARTER_SPLIT                                        ((FIT_EXD_LAYOUT)5)
#define FIT_EXD_LAYOUT_HALF_VERTICAL_LEFT_SPLIT                                  ((FIT_EXD_LAYOUT)6)
#define FIT_EXD_LAYOUT_HALF_HORIZONTAL_TOP_SPLIT                                 ((FIT_EXD_LAYOUT)7)
#define FIT_EXD_LAYOUT_COUNT                                                     8

typedef FIT_ENUM FIT_EXD_DISPLAY_TYPE;
#define FIT_EXD_DISPLAY_TYPE_INVALID                                             FIT_ENUM_INVALID
#define FIT_EXD_DISPLAY_TYPE_NUMERICAL                                           ((FIT_EXD_DISPLAY_TYPE)0)
#define FIT_EXD_DISPLAY_TYPE_SIMPLE                                              ((FIT_EXD_DISPLAY_TYPE)1)
#define FIT_EXD_DISPLAY_TYPE_GRAPH                                               ((FIT_EXD_DISPLAY_TYPE)2)
#define FIT_EXD_DISPLAY_TYPE_BAR                                                 ((FIT_EXD_DISPLAY_TYPE)3)
#define FIT_EXD_DISPLAY_TYPE_CIRCLE_GRAPH                                        ((FIT_EXD_DISPLAY_TYPE)4)
#define FIT_EXD_DISPLAY_TYPE_VIRTUAL_PARTNER                                     ((FIT_EXD_DISPLAY_TYPE)5)
#define FIT_EXD_DISPLAY_TYPE_BALANCE                                             ((FIT_EXD_DISPLAY_TYPE)6)
#define FIT_EXD_DISPLAY_TYPE_STRING_LIST                                         ((FIT_EXD_DISPLAY_TYPE)7)
#define FIT_EXD_DISPLAY_TYPE_STRING                                              ((FIT_EXD_DISPLAY_TYPE)8)
#define FIT_EXD_DISPLAY_TYPE_SIMPLE_DYNAMIC_ICON                                 ((FIT_EXD_DISPLAY_TYPE)9)
#define FIT_EXD_DISPLAY_TYPE_GAUGE                                               ((FIT_EXD_DISPLAY_TYPE)10)
#define FIT_EXD_DISPLAY_TYPE_COUNT                                               11

typedef FIT_ENUM FIT_EXD_DATA_UNITS;
#define FIT_EXD_DATA_UNITS_INVALID                                               FIT_ENUM_INVALID
#define FIT_EXD_DATA_UNITS_NO_UNITS                                              ((FIT_EXD_DATA_UNITS)0)
#define FIT_EXD_DATA_UNITS_LAPS                                                  ((FIT_EXD_DATA_UNITS)1)
#define FIT_EXD_DATA_UNITS_MILES_PER_HOUR                                        ((FIT_EXD_DATA_UNITS)2)
#define FIT_EXD_DATA_UNITS_KILOMETERS_PER_HOUR                                   ((FIT_EXD_DATA_UNITS)3)
#define FIT_EXD_DATA_UNITS_FEET_PER_HOUR                                         ((FIT_EXD_DATA_UNITS)4)
#define FIT_EXD_DATA_UNITS_METERS_PER_HOUR                                       ((FIT_EXD_DATA_UNITS)5)
#define FIT_EXD_DATA_UNITS_DEGREES_CELSIUS                                       ((FIT_EXD_DATA_UNITS)6)
#define FIT_EXD_DATA_UNITS_DEGREES_FARENHEIT                                     ((FIT_EXD_DATA_UNITS)7)
#define FIT_EXD_DATA_UNITS_ZONE                                                  ((FIT_EXD_DATA_UNITS)8)
#define FIT_EXD_DATA_UNITS_GEAR                                                  ((FIT_EXD_DATA_UNITS)9)
#define FIT_EXD_DATA_UNITS_RPM                                                   ((FIT_EXD_DATA_UNITS)10)
#define FIT_EXD_DATA_UNITS_BPM                                                   ((FIT_EXD_DATA_UNITS)11)
#define FIT_EXD_DATA_UNITS_DEGREES                                               ((FIT_EXD_DATA_UNITS)12)
#define FIT_EXD_DATA_UNITS_MILLIMETERS                                           ((FIT_EXD_DATA_UNITS)13)
#define FIT_EXD_DATA_UNITS_METERS                                                ((FIT_EXD_DATA_UNITS)14)
#define FIT_EXD_DATA_UNITS_KILOMETERS                                            ((FIT_EXD_DATA_UNITS)15)
#define FIT_EXD_DATA_UNITS_FEET                                                  ((FIT_EXD_DATA_UNITS)16)
#define FIT_EXD_DATA_UNITS_YARDS                                                 ((FIT_EXD_DATA_UNITS)17)
#define FIT_EXD_DATA_UNITS_KILOFEET                                              ((FIT_EXD_DATA_UNITS)18)
#define FIT_EXD_DATA_UNITS_MILES                                                 ((FIT_EXD_DATA_UNITS)19)
#define FIT_EXD_DATA_UNITS_TIME                                                  ((FIT_EXD_DATA_UNITS)20)
#define FIT_EXD_DATA_UNITS_ENUM_TURN_TYPE                                        ((FIT_EXD_DATA_UNITS)21)
#define FIT_EXD_DATA_UNITS_PERCENT                                               ((FIT_EXD_DATA_UNITS)22)
#define FIT_EXD_DATA_UNITS_WATTS                                                 ((FIT_EXD_DATA_UNITS)23)
#define FIT_EXD_DATA_UNITS_WATTS_PER_KILOGRAM                                    ((FIT_EXD_DATA_UNITS)24)
#define FIT_EXD_DATA_UNITS_ENUM_BATTERY_STATUS                                   ((FIT_EXD_DATA_UNITS)25)
#define FIT_EXD_DATA_UNITS_ENUM_BIKE_LIGHT_BEAM_ANGLE_MODE                       ((FIT_EXD_DATA_UNITS)26)
#define FIT_EXD_DATA_UNITS_ENUM_BIKE_LIGHT_BATTERY_STATUS                        ((FIT_EXD_DATA_UNITS)27)
#define FIT_EXD_DATA_UNITS_ENUM_BIKE_LIGHT_NETWORK_CONFIG_TYPE                   ((FIT_EXD_DATA_UNITS)28)
#define FIT_EXD_DATA_UNITS_LIGHTS                                                ((FIT_EXD_DATA_UNITS)29)
#define FIT_EXD_DATA_UNITS_SECONDS                                               ((FIT_EXD_DATA_UNITS)30)
#define FIT_EXD_DATA_UNITS_MINUTES                                               ((FIT_EXD_DATA_UNITS)31)
#define FIT_EXD_DATA_UNITS_HOURS                                                 ((FIT_EXD_DATA_UNITS)32)
#define FIT_EXD_DATA_UNITS_CALORIES                                              ((FIT_EXD_DATA_UNITS)33)
#define FIT_EXD_DATA_UNITS_KILOJOULES                                            ((FIT_EXD_DATA_UNITS)34)
#define FIT_EXD_DATA_UNITS_MILLISECONDS                                          ((FIT_EXD_DATA_UNITS)35)
#define FIT_EXD_DATA_UNITS_SECOND_PER_MILE                                       ((FIT_EXD_DATA_UNITS)36)
#define FIT_EXD_DATA_UNITS_SECOND_PER_KILOMETER                                  ((FIT_EXD_DATA_UNITS)37)
#define FIT_EXD_DATA_UNITS_CENTIMETER                                            ((FIT_EXD_DATA_UNITS)38)
#define FIT_EXD_DATA_UNITS_ENUM_COURSE_POINT                                     ((FIT_EXD_DATA_UNITS)39)
#define FIT_EXD_DATA_UNITS_BRADIANS                                              ((FIT_EXD_DATA_UNITS)40)
#define FIT_EXD_DATA_UNITS_ENUM_SPORT                                            ((FIT_EXD_DATA_UNITS)41)
#define FIT_EXD_DATA_UNITS_INCHES_HG                                             ((FIT_EXD_DATA_UNITS)42)
#define FIT_EXD_DATA_UNITS_MM_HG                                                 ((FIT_EXD_DATA_UNITS)43)
#define FIT_EXD_DATA_UNITS_MBARS                                                 ((FIT_EXD_DATA_UNITS)44)
#define FIT_EXD_DATA_UNITS_HECTO_PASCALS                                         ((FIT_EXD_DATA_UNITS)45)
#define FIT_EXD_DATA_UNITS_FEET_PER_MIN                                          ((FIT_EXD_DATA_UNITS)46)
#define FIT_EXD_DATA_UNITS_METERS_PER_MIN                                        ((FIT_EXD_DATA_UNITS)47)
#define FIT_EXD_DATA_UNITS_METERS_PER_SEC                                        ((FIT_EXD_DATA_UNITS)48)
#define FIT_EXD_DATA_UNITS_EIGHT_CARDINAL                                        ((FIT_EXD_DATA_UNITS)49)
#define FIT_EXD_DATA_UNITS_COUNT                                                 50

typedef FIT_ENUM FIT_EXD_QUALIFIERS;
#define FIT_EXD_QUALIFIERS_INVALID                                               FIT_ENUM_INVALID
#define FIT_EXD_QUALIFIERS_NO_QUALIFIER                                          ((FIT_EXD_QUALIFIERS)0)
#define FIT_EXD_QUALIFIERS_INSTANTANEOUS                                         ((FIT_EXD_QUALIFIERS)1)
#define FIT_EXD_QUALIFIERS_AVERAGE                                               ((FIT_EXD_QUALIFIERS)2)
#define FIT_EXD_QUALIFIERS_LAP                                                   ((FIT_EXD_QUALIFIERS)3)
#define FIT_EXD_QUALIFIERS_MAXIMUM                                               ((FIT_EXD_QUALIFIERS)4)
#define FIT_EXD_QUALIFIERS_MAXIMUM_AVERAGE                                       ((FIT_EXD_QUALIFIERS)5)
#define FIT_EXD_QUALIFIERS_MAXIMUM_LAP                                           ((FIT_EXD_QUALIFIERS)6)
#define FIT_EXD_QUALIFIERS_LAST_LAP                                              ((FIT_EXD_QUALIFIERS)7)
#define FIT_EXD_QUALIFIERS_AVERAGE_LAP                                           ((FIT_EXD_QUALIFIERS)8)
#define FIT_EXD_QUALIFIERS_TO_DESTINATION                                        ((FIT_EXD_QUALIFIERS)9)
#define FIT_EXD_QUALIFIERS_TO_GO                                                 ((FIT_EXD_QUALIFIERS)10)
#define FIT_EXD_QUALIFIERS_TO_NEXT                                               ((FIT_EXD_QUALIFIERS)11)
#define FIT_EXD_QUALIFIERS_NEXT_COURSE_POINT                                     ((FIT_EXD_QUALIFIERS)12)
#define FIT_EXD_QUALIFIERS_TOTAL                                                 ((FIT_EXD_QUALIFIERS)13)
#define FIT_EXD_QUALIFIERS_THREE_SECOND_AVERAGE                                  ((FIT_EXD_QUALIFIERS)14)
#define FIT_EXD_QUALIFIERS_TEN_SECOND_AVERAGE                                    ((FIT_EXD_QUALIFIERS)15)
#define FIT_EXD_QUALIFIERS_THIRTY_SECOND_AVERAGE                                 ((FIT_EXD_QUALIFIERS)16)
#define FIT_EXD_QUALIFIERS_PERCENT_MAXIMUM                                       ((FIT_EXD_QUALIFIERS)17)
#define FIT_EXD_QUALIFIERS_PERCENT_MAXIMUM_AVERAGE                               ((FIT_EXD_QUALIFIERS)18)
#define FIT_EXD_QUALIFIERS_LAP_PERCENT_MAXIMUM                                   ((FIT_EXD_QUALIFIERS)19)
#define FIT_EXD_QUALIFIERS_ELAPSED                                               ((FIT_EXD_QUALIFIERS)20)
#define FIT_EXD_QUALIFIERS_SUNRISE                                               ((FIT_EXD_QUALIFIERS)21)
#define FIT_EXD_QUALIFIERS_SUNSET                                                ((FIT_EXD_QUALIFIERS)22)
#define FIT_EXD_QUALIFIERS_COMPARED_TO_VIRTUAL_PARTNER                           ((FIT_EXD_QUALIFIERS)23)
#define FIT_EXD_QUALIFIERS_MAXIMUM_24H                                           ((FIT_EXD_QUALIFIERS)24)
#define FIT_EXD_QUALIFIERS_MINIMUM_24H                                           ((FIT_EXD_QUALIFIERS)25)
#define FIT_EXD_QUALIFIERS_MINIMUM                                               ((FIT_EXD_QUALIFIERS)26)
#define FIT_EXD_QUALIFIERS_FIRST                                                 ((FIT_EXD_QUALIFIERS)27)
#define FIT_EXD_QUALIFIERS_SECOND                                                ((FIT_EXD_QUALIFIERS)28)
#define FIT_EXD_QUALIFIERS_THIRD                                                 ((FIT_EXD_QUALIFIERS)29)
#define FIT_EXD_QUALIFIERS_SHIFTER                                               ((FIT_EXD_QUALIFIERS)30)
#define FIT_EXD_QUALIFIERS_LAST_SPORT                                            ((FIT_EXD_QUALIFIERS)31)
#define FIT_EXD_QUALIFIERS_MOVING                                                ((FIT_EXD_QUALIFIERS)32)
#define FIT_EXD_QUALIFIERS_STOPPED                                               ((FIT_EXD_QUALIFIERS)33)
#define FIT_EXD_QUALIFIERS_ESTIMATED_TOTAL                                       ((FIT_EXD_QUALIFIERS)34)
#define FIT_EXD_QUALIFIERS_ZONE_9                                                ((FIT_EXD_QUALIFIERS)242)
#define FIT_EXD_QUALIFIERS_ZONE_8                                                ((FIT_EXD_QUALIFIERS)243)
#define FIT_EXD_QUALIFIERS_ZONE_7                                                ((FIT_EXD_QUALIFIERS)244)
#define FIT_EXD_QUALIFIERS_ZONE_6                                                ((FIT_EXD_QUALIFIERS)245)
#define FIT_EXD_QUALIFIERS_ZONE_5                                                ((FIT_EXD_QUALIFIERS)246)
#define FIT_EXD_QUALIFIERS_ZONE_4                                                ((FIT_EXD_QUALIFIERS)247)
#define FIT_EXD_QUALIFIERS_ZONE_3                                                ((FIT_EXD_QUALIFIERS)248)
#define FIT_EXD_QUALIFIERS_ZONE_2                                                ((FIT_EXD_QUALIFIERS)249)
#define FIT_EXD_QUALIFIERS_ZONE_1                                                ((FIT_EXD_QUALIFIERS)250)
#define FIT_EXD_QUALIFIERS_COUNT                                                 44

typedef FIT_ENUM FIT_EXD_DESCRIPTORS;
#define FIT_EXD_DESCRIPTORS_INVALID                                              FIT_ENUM_INVALID
#define FIT_EXD_DESCRIPTORS_BIKE_LIGHT_BATTERY_STATUS                            ((FIT_EXD_DESCRIPTORS)0)
#define FIT_EXD_DESCRIPTORS_BEAM_ANGLE_STATUS                                    ((FIT_EXD_DESCRIPTORS)1)
#define FIT_EXD_DESCRIPTORS_BATERY_LEVEL                                         ((FIT_EXD_DESCRIPTORS)2)
#define FIT_EXD_DESCRIPTORS_LIGHT_NETWORK_MODE                                   ((FIT_EXD_DESCRIPTORS)3)
#define FIT_EXD_DESCRIPTORS_NUMBER_LIGHTS_CONNECTED                              ((FIT_EXD_DESCRIPTORS)4)
#define FIT_EXD_DESCRIPTORS_CADENCE                                              ((FIT_EXD_DESCRIPTORS)5)
#define FIT_EXD_DESCRIPTORS_DISTANCE                                             ((FIT_EXD_DESCRIPTORS)6)
#define FIT_EXD_DESCRIPTORS_ESTIMATED_TIME_OF_ARRIVAL                            ((FIT_EXD_DESCRIPTORS)7)
#define FIT_EXD_DESCRIPTORS_HEADING                                              ((FIT_EXD_DESCRIPTORS)8)
#define FIT_EXD_DESCRIPTORS_TIME                                                 ((FIT_EXD_DESCRIPTORS)9)
#define FIT_EXD_DESCRIPTORS_BATTERY_LEVEL                                        ((FIT_EXD_DESCRIPTORS)10)
#define FIT_EXD_DESCRIPTORS_TRAINER_RESISTANCE                                   ((FIT_EXD_DESCRIPTORS)11)
#define FIT_EXD_DESCRIPTORS_TRAINER_TARGET_POWER                                 ((FIT_EXD_DESCRIPTORS)12)
#define FIT_EXD_DESCRIPTORS_TIME_SEATED                                          ((FIT_EXD_DESCRIPTORS)13)
#define FIT_EXD_DESCRIPTORS_TIME_STANDING                                        ((FIT_EXD_DESCRIPTORS)14)
#define FIT_EXD_DESCRIPTORS_ELEVATION                                            ((FIT_EXD_DESCRIPTORS)15)
#define FIT_EXD_DESCRIPTORS_GRADE                                                ((FIT_EXD_DESCRIPTORS)16)
#define FIT_EXD_DESCRIPTORS_ASCENT                                               ((FIT_EXD_DESCRIPTORS)17)
#define FIT_EXD_DESCRIPTORS_DESCENT                                              ((FIT_EXD_DESCRIPTORS)18)
#define FIT_EXD_DESCRIPTORS_VERTICAL_SPEED                                       ((FIT_EXD_DESCRIPTORS)19)
#define FIT_EXD_DESCRIPTORS_DI2_BATTERY_LEVEL                                    ((FIT_EXD_DESCRIPTORS)20)
#define FIT_EXD_DESCRIPTORS_FRONT_GEAR                                           ((FIT_EXD_DESCRIPTORS)21)
#define FIT_EXD_DESCRIPTORS_REAR_GEAR                                            ((FIT_EXD_DESCRIPTORS)22)
#define FIT_EXD_DESCRIPTORS_GEAR_RATIO                                           ((FIT_EXD_DESCRIPTORS)23)
#define FIT_EXD_DESCRIPTORS_HEART_RATE                                           ((FIT_EXD_DESCRIPTORS)24)
#define FIT_EXD_DESCRIPTORS_HEART_RATE_ZONE                                      ((FIT_EXD_DESCRIPTORS)25)
#define FIT_EXD_DESCRIPTORS_TIME_IN_HEART_RATE_ZONE                              ((FIT_EXD_DESCRIPTORS)26)
#define FIT_EXD_DESCRIPTORS_HEART_RATE_RESERVE                                   ((FIT_EXD_DESCRIPTORS)27)
#define FIT_EXD_DESCRIPTORS_CALORIES                                             ((FIT_EXD_DESCRIPTORS)28)
#define FIT_EXD_DESCRIPTORS_GPS_ACCURACY                                         ((FIT_EXD_DESCRIPTORS)29)
#define FIT_EXD_DESCRIPTORS_GPS_SIGNAL_STRENGTH                                  ((FIT_EXD_DESCRIPTORS)30)
#define FIT_EXD_DESCRIPTORS_TEMPERATURE                                          ((FIT_EXD_DESCRIPTORS)31)
#define FIT_EXD_DESCRIPTORS_TIME_OF_DAY                                          ((FIT_EXD_DESCRIPTORS)32)
#define FIT_EXD_DESCRIPTORS_BALANCE                                              ((FIT_EXD_DESCRIPTORS)33)
#define FIT_EXD_DESCRIPTORS_PEDAL_SMOOTHNESS                                     ((FIT_EXD_DESCRIPTORS)34)
#define FIT_EXD_DESCRIPTORS_POWER                                                ((FIT_EXD_DESCRIPTORS)35)
#define FIT_EXD_DESCRIPTORS_FUNCTIONAL_THRESHOLD_POWER                           ((FIT_EXD_DESCRIPTORS)36)
#define FIT_EXD_DESCRIPTORS_INTENSITY_FACTOR                                     ((FIT_EXD_DESCRIPTORS)37)
#define FIT_EXD_DESCRIPTORS_WORK                                                 ((FIT_EXD_DESCRIPTORS)38)
#define FIT_EXD_DESCRIPTORS_POWER_RATIO                                          ((FIT_EXD_DESCRIPTORS)39)
#define FIT_EXD_DESCRIPTORS_NORMALIZED_POWER                                     ((FIT_EXD_DESCRIPTORS)40)
#define FIT_EXD_DESCRIPTORS_TRAINING_STRESS_SCORE                                ((FIT_EXD_DESCRIPTORS)41)
#define FIT_EXD_DESCRIPTORS_TIME_ON_ZONE                                         ((FIT_EXD_DESCRIPTORS)42)
#define FIT_EXD_DESCRIPTORS_SPEED                                                ((FIT_EXD_DESCRIPTORS)43)
#define FIT_EXD_DESCRIPTORS_LAPS                                                 ((FIT_EXD_DESCRIPTORS)44)
#define FIT_EXD_DESCRIPTORS_REPS                                                 ((FIT_EXD_DESCRIPTORS)45)
#define FIT_EXD_DESCRIPTORS_WORKOUT_STEP                                         ((FIT_EXD_DESCRIPTORS)46)
#define FIT_EXD_DESCRIPTORS_COURSE_DISTANCE                                      ((FIT_EXD_DESCRIPTORS)47)
#define FIT_EXD_DESCRIPTORS_NAVIGATION_DISTANCE                                  ((FIT_EXD_DESCRIPTORS)48)
#define FIT_EXD_DESCRIPTORS_COURSE_ESTIMATED_TIME_OF_ARRIVAL                     ((FIT_EXD_DESCRIPTORS)49)
#define FIT_EXD_DESCRIPTORS_NAVIGATION_ESTIMATED_TIME_OF_ARRIVAL                 ((FIT_EXD_DESCRIPTORS)50)
#define FIT_EXD_DESCRIPTORS_COURSE_TIME                                          ((FIT_EXD_DESCRIPTORS)51)
#define FIT_EXD_DESCRIPTORS_NAVIGATION_TIME                                      ((FIT_EXD_DESCRIPTORS)52)
#define FIT_EXD_DESCRIPTORS_COURSE_HEADING                                       ((FIT_EXD_DESCRIPTORS)53)
#define FIT_EXD_DESCRIPTORS_NAVIGATION_HEADING                                   ((FIT_EXD_DESCRIPTORS)54)
#define FIT_EXD_DESCRIPTORS_POWER_ZONE                                           ((FIT_EXD_DESCRIPTORS)55)
#define FIT_EXD_DESCRIPTORS_TORQUE_EFFECTIVENESS                                 ((FIT_EXD_DESCRIPTORS)56)
#define FIT_EXD_DESCRIPTORS_TIMER_TIME                                           ((FIT_EXD_DESCRIPTORS)57)
#define FIT_EXD_DESCRIPTORS_POWER_WEIGHT_RATIO                                   ((FIT_EXD_DESCRIPTORS)58)
#define FIT_EXD_DESCRIPTORS_LEFT_PLATFORM_CENTER_OFFSET                          ((FIT_EXD_DESCRIPTORS)59)
#define FIT_EXD_DESCRIPTORS_RIGHT_PLATFORM_CENTER_OFFSET                         ((FIT_EXD_DESCRIPTORS)60)
#define FIT_EXD_DESCRIPTORS_LEFT_POWER_PHASE_START_ANGLE                         ((FIT_EXD_DESCRIPTORS)61)
#define FIT_EXD_DESCRIPTORS_RIGHT_POWER_PHASE_START_ANGLE                        ((FIT_EXD_DESCRIPTORS)62)
#define FIT_EXD_DESCRIPTORS_LEFT_POWER_PHASE_FINISH_ANGLE                        ((FIT_EXD_DESCRIPTORS)63)
#define FIT_EXD_DESCRIPTORS_RIGHT_POWER_PHASE_FINISH_ANGLE                       ((FIT_EXD_DESCRIPTORS)64)
#define FIT_EXD_DESCRIPTORS_GEARS                                                ((FIT_EXD_DESCRIPTORS)65) // Combined gear information
#define FIT_EXD_DESCRIPTORS_PACE                                                 ((FIT_EXD_DESCRIPTORS)66)
#define FIT_EXD_DESCRIPTORS_TRAINING_EFFECT                                      ((FIT_EXD_DESCRIPTORS)67)
#define FIT_EXD_DESCRIPTORS_VERTICAL_OSCILLATION                                 ((FIT_EXD_DESCRIPTORS)68)
#define FIT_EXD_DESCRIPTORS_VERTICAL_RATIO                                       ((FIT_EXD_DESCRIPTORS)69)
#define FIT_EXD_DESCRIPTORS_GROUND_CONTACT_TIME                                  ((FIT_EXD_DESCRIPTORS)70)
#define FIT_EXD_DESCRIPTORS_LEFT_GROUND_CONTACT_TIME_BALANCE                     ((FIT_EXD_DESCRIPTORS)71)
#define FIT_EXD_DESCRIPTORS_RIGHT_GROUND_CONTACT_TIME_BALANCE                    ((FIT_EXD_DESCRIPTORS)72)
#define FIT_EXD_DESCRIPTORS_STRIDE_LENGTH                                        ((FIT_EXD_DESCRIPTORS)73)
#define FIT_EXD_DESCRIPTORS_RUNNING_CADENCE                                      ((FIT_EXD_DESCRIPTORS)74)
#define FIT_EXD_DESCRIPTORS_PERFORMANCE_CONDITION                                ((FIT_EXD_DESCRIPTORS)75)
#define FIT_EXD_DESCRIPTORS_COURSE_TYPE                                          ((FIT_EXD_DESCRIPTORS)76)
#define FIT_EXD_DESCRIPTORS_TIME_IN_POWER_ZONE                                   ((FIT_EXD_DESCRIPTORS)77)
#define FIT_EXD_DESCRIPTORS_NAVIGATION_TURN                                      ((FIT_EXD_DESCRIPTORS)78)
#define FIT_EXD_DESCRIPTORS_COURSE_LOCATION                                      ((FIT_EXD_DESCRIPTORS)79)
#define FIT_EXD_DESCRIPTORS_NAVIGATION_LOCATION                                  ((FIT_EXD_DESCRIPTORS)80)
#define FIT_EXD_DESCRIPTORS_COMPASS                                              ((FIT_EXD_DESCRIPTORS)81)
#define FIT_EXD_DESCRIPTORS_GEAR_COMBO                                           ((FIT_EXD_DESCRIPTORS)82)
#define FIT_EXD_DESCRIPTORS_MUSCLE_OXYGEN                                        ((FIT_EXD_DESCRIPTORS)83)
#define FIT_EXD_DESCRIPTORS_ICON                                                 ((FIT_EXD_DESCRIPTORS)84)
#define FIT_EXD_DESCRIPTORS_COMPASS_HEADING                                      ((FIT_EXD_DESCRIPTORS)85)
#define FIT_EXD_DESCRIPTORS_GPS_HEADING                                          ((FIT_EXD_DESCRIPTORS)86)
#define FIT_EXD_DESCRIPTORS_GPS_ELEVATION                                        ((FIT_EXD_DESCRIPTORS)87)
#define FIT_EXD_DESCRIPTORS_ANAEROBIC_TRAINING_EFFECT                            ((FIT_EXD_DESCRIPTORS)88)
#define FIT_EXD_DESCRIPTORS_COURSE                                               ((FIT_EXD_DESCRIPTORS)89)
#define FIT_EXD_DESCRIPTORS_OFF_COURSE                                           ((FIT_EXD_DESCRIPTORS)90)
#define FIT_EXD_DESCRIPTORS_GLIDE_RATIO                                          ((FIT_EXD_DESCRIPTORS)91)
#define FIT_EXD_DESCRIPTORS_VERTICAL_DISTANCE                                    ((FIT_EXD_DESCRIPTORS)92)
#define FIT_EXD_DESCRIPTORS_VMG                                                  ((FIT_EXD_DESCRIPTORS)93)
#define FIT_EXD_DESCRIPTORS_AMBIENT_PRESSURE                                     ((FIT_EXD_DESCRIPTORS)94)
#define FIT_EXD_DESCRIPTORS_PRESSURE                                             ((FIT_EXD_DESCRIPTORS)95)
#define FIT_EXD_DESCRIPTORS_VAM                                                  ((FIT_EXD_DESCRIPTORS)96)
#define FIT_EXD_DESCRIPTORS_COUNT                                                97

typedef FIT_UINT32 FIT_AUTO_ACTIVITY_DETECT;
#define FIT_AUTO_ACTIVITY_DETECT_INVALID                                         FIT_UINT32_INVALID
#define FIT_AUTO_ACTIVITY_DETECT_NONE                                            ((FIT_AUTO_ACTIVITY_DETECT)0x00000000)
#define FIT_AUTO_ACTIVITY_DETECT_RUNNING                                         ((FIT_AUTO_ACTIVITY_DETECT)0x00000001)
#define FIT_AUTO_ACTIVITY_DETECT_CYCLING                                         ((FIT_AUTO_ACTIVITY_DETECT)0x00000002)
#define FIT_AUTO_ACTIVITY_DETECT_SWIMMING                                        ((FIT_AUTO_ACTIVITY_DETECT)0x00000004)
#define FIT_AUTO_ACTIVITY_DETECT_WALKING                                         ((FIT_AUTO_ACTIVITY_DETECT)0x00000008)
#define FIT_AUTO_ACTIVITY_DETECT_ELLIPTICAL                                      ((FIT_AUTO_ACTIVITY_DETECT)0x00000020)
#define FIT_AUTO_ACTIVITY_DETECT_SEDENTARY                                       ((FIT_AUTO_ACTIVITY_DETECT)0x00000400)
#define FIT_AUTO_ACTIVITY_DETECT_COUNT                                           7

typedef FIT_UINT32Z FIT_SUPPORTED_EXD_SCREEN_LAYOUTS;
#define FIT_SUPPORTED_EXD_SCREEN_LAYOUTS_INVALID                                 FIT_UINT32Z_INVALID
#define FIT_SUPPORTED_EXD_SCREEN_LAYOUTS_FULL_SCREEN                             ((FIT_SUPPORTED_EXD_SCREEN_LAYOUTS)0x00000001)
#define FIT_SUPPORTED_EXD_SCREEN_LAYOUTS_HALF_VERTICAL                           ((FIT_SUPPORTED_EXD_SCREEN_LAYOUTS)0x00000002)
#define FIT_SUPPORTED_EXD_SCREEN_LAYOUTS_HALF_HORIZONTAL                         ((FIT_SUPPORTED_EXD_SCREEN_LAYOUTS)0x00000004)
#define FIT_SUPPORTED_EXD_SCREEN_LAYOUTS_HALF_VERTICAL_RIGHT_SPLIT               ((FIT_SUPPORTED_EXD_SCREEN_LAYOUTS)0x00000008)
#define FIT_SUPPORTED_EXD_SCREEN_LAYOUTS_HALF_HORIZONTAL_BOTTOM_SPLIT            ((FIT_SUPPORTED_EXD_SCREEN_LAYOUTS)0x00000010)
#define FIT_SUPPORTED_EXD_SCREEN_LAYOUTS_FULL_QUARTER_SPLIT                      ((FIT_SUPPORTED_EXD_SCREEN_LAYOUTS)0x00000020)
#define FIT_SUPPORTED_EXD_SCREEN_LAYOUTS_HALF_VERTICAL_LEFT_SPLIT                ((FIT_SUPPORTED_EXD_SCREEN_LAYOUTS)0x00000040)
#define FIT_SUPPORTED_EXD_SCREEN_LAYOUTS_HALF_HORIZONTAL_TOP_SPLIT               ((FIT_SUPPORTED_EXD_SCREEN_LAYOUTS)0x00000080)
#define FIT_SUPPORTED_EXD_SCREEN_LAYOUTS_COUNT                                   8

typedef FIT_UINT8 FIT_FIT_BASE_TYPE;
#define FIT_FIT_BASE_TYPE_INVALID                                                FIT_UINT8_INVALID
#define FIT_FIT_BASE_TYPE_ENUM                                                   ((FIT_FIT_BASE_TYPE)0)
#define FIT_FIT_BASE_TYPE_SINT8                                                  ((FIT_FIT_BASE_TYPE)1)
#define FIT_FIT_BASE_TYPE_UINT8                                                  ((FIT_FIT_BASE_TYPE)2)
#define FIT_FIT_BASE_TYPE_SINT16                                                 ((FIT_FIT_BASE_TYPE)131)
#define FIT_FIT_BASE_TYPE_UINT16                                                 ((FIT_FIT_BASE_TYPE)132)
#define FIT_FIT_BASE_TYPE_SINT32                                                 ((FIT_FIT_BASE_TYPE)133)
#define FIT_FIT_BASE_TYPE_UINT32                                                 ((FIT_FIT_BASE_TYPE)134)
#define FIT_FIT_BASE_TYPE_STRING                                                 ((FIT_FIT_BASE_TYPE)7)
#define FIT_FIT_BASE_TYPE_FLOAT32                                                ((FIT_FIT_BASE_TYPE)136)
#define FIT_FIT_BASE_TYPE_FLOAT64                                                ((FIT_FIT_BASE_TYPE)137)
#define FIT_FIT_BASE_TYPE_UINT8Z                                                 ((FIT_FIT_BASE_TYPE)10)
#define FIT_FIT_BASE_TYPE_UINT16Z                                                ((FIT_FIT_BASE_TYPE)139)
#define FIT_FIT_BASE_TYPE_UINT32Z                                                ((FIT_FIT_BASE_TYPE)140)
#define FIT_FIT_BASE_TYPE_BYTE                                                   ((FIT_FIT_BASE_TYPE)13)
#define FIT_FIT_BASE_TYPE_SINT64                                                 ((FIT_FIT_BASE_TYPE)142)
#define FIT_FIT_BASE_TYPE_UINT64                                                 ((FIT_FIT_BASE_TYPE)143)
#define FIT_FIT_BASE_TYPE_UINT64Z                                                ((FIT_FIT_BASE_TYPE)144)
#define FIT_FIT_BASE_TYPE_COUNT                                                  17

typedef FIT_ENUM FIT_TURN_TYPE;
#define FIT_TURN_TYPE_INVALID                                                    FIT_ENUM_INVALID
#define FIT_TURN_TYPE_ARRIVING_IDX                                               ((FIT_TURN_TYPE)0)
#define FIT_TURN_TYPE_ARRIVING_LEFT_IDX                                          ((FIT_TURN_TYPE)1)
#define FIT_TURN_TYPE_ARRIVING_RIGHT_IDX                                         ((FIT_TURN_TYPE)2)
#define FIT_TURN_TYPE_ARRIVING_VIA_IDX                                           ((FIT_TURN_TYPE)3)
#define FIT_TURN_TYPE_ARRIVING_VIA_LEFT_IDX                                      ((FIT_TURN_TYPE)4)
#define FIT_TURN_TYPE_ARRIVING_VIA_RIGHT_IDX                                     ((FIT_TURN_TYPE)5)
#define FIT_TURN_TYPE_BEAR_KEEP_LEFT_IDX                                         ((FIT_TURN_TYPE)6)
#define FIT_TURN_TYPE_BEAR_KEEP_RIGHT_IDX                                        ((FIT_TURN_TYPE)7)
#define FIT_TURN_TYPE_CONTINUE_IDX                                               ((FIT_TURN_TYPE)8)
#define FIT_TURN_TYPE_EXIT_LEFT_IDX                                              ((FIT_TURN_TYPE)9)
#define FIT_TURN_TYPE_EXIT_RIGHT_IDX                                             ((FIT_TURN_TYPE)10)
#define FIT_TURN_TYPE_FERRY_IDX                                                  ((FIT_TURN_TYPE)11)
#define FIT_TURN_TYPE_ROUNDABOUT_45_IDX                                          ((FIT_TURN_TYPE)12)
#define FIT_TURN_TYPE_ROUNDABOUT_90_IDX                                          ((FIT_TURN_TYPE)13)
#define FIT_TURN_TYPE_ROUNDABOUT_135_IDX                                         ((FIT_TURN_TYPE)14)
#define FIT_TURN_TYPE_ROUNDABOUT_180_IDX                                         ((FIT_TURN_TYPE)15)
#define FIT_TURN_TYPE_ROUNDABOUT_225_IDX                                         ((FIT_TURN_TYPE)16)
#define FIT_TURN_TYPE_ROUNDABOUT_270_IDX                                         ((FIT_TURN_TYPE)17)
#define FIT_TURN_TYPE_ROUNDABOUT_315_IDX                                         ((FIT_TURN_TYPE)18)
#define FIT_TURN_TYPE_ROUNDABOUT_360_IDX                                         ((FIT_TURN_TYPE)19)
#define FIT_TURN_TYPE_ROUNDABOUT_NEG_45_IDX                                      ((FIT_TURN_TYPE)20)
#define FIT_TURN_TYPE_ROUNDABOUT_NEG_90_IDX                                      ((FIT_TURN_TYPE)21)
#define FIT_TURN_TYPE_ROUNDABOUT_NEG_135_IDX                                     ((FIT_TURN_TYPE)22)
#define FIT_TURN_TYPE_ROUNDABOUT_NEG_180_IDX                                     ((FIT_TURN_TYPE)23)
#define FIT_TURN_TYPE_ROUNDABOUT_NEG_225_IDX                                     ((FIT_TURN_TYPE)24)
#define FIT_TURN_TYPE_ROUNDABOUT_NEG_270_IDX                                     ((FIT_TURN_TYPE)25)
#define FIT_TURN_TYPE_ROUNDABOUT_NEG_315_IDX                                     ((FIT_TURN_TYPE)26)
#define FIT_TURN_TYPE_ROUNDABOUT_NEG_360_IDX                                     ((FIT_TURN_TYPE)27)
#define FIT_TURN_TYPE_ROUNDABOUT_GENERIC_IDX                                     ((FIT_TURN_TYPE)28)
#define FIT_TURN_TYPE_ROUNDABOUT_NEG_GENERIC_IDX                                 ((FIT_TURN_TYPE)29)
#define FIT_TURN_TYPE_SHARP_TURN_LEFT_IDX                                        ((FIT_TURN_TYPE)30)
#define FIT_TURN_TYPE_SHARP_TURN_RIGHT_IDX                                       ((FIT_TURN_TYPE)31)
#define FIT_TURN_TYPE_TURN_LEFT_IDX                                              ((FIT_TURN_TYPE)32)
#define FIT_TURN_TYPE_TURN_RIGHT_IDX                                             ((FIT_TURN_TYPE)33)
#define FIT_TURN_TYPE_UTURN_LEFT_IDX                                             ((FIT_TURN_TYPE)34)
#define FIT_TURN_TYPE_UTURN_RIGHT_IDX                                            ((FIT_TURN_TYPE)35)
#define FIT_TURN_TYPE_ICON_INV_IDX                                               ((FIT_TURN_TYPE)36)
#define FIT_TURN_TYPE_ICON_IDX_CNT                                               ((FIT_TURN_TYPE)37)
#define FIT_TURN_TYPE_COUNT                                                      38

typedef FIT_UINT8 FIT_BIKE_LIGHT_BEAM_ANGLE_MODE;
#define FIT_BIKE_LIGHT_BEAM_ANGLE_MODE_INVALID                                   FIT_UINT8_INVALID
#define FIT_BIKE_LIGHT_BEAM_ANGLE_MODE_MANUAL                                    ((FIT_BIKE_LIGHT_BEAM_ANGLE_MODE)0)
#define FIT_BIKE_LIGHT_BEAM_ANGLE_MODE_AUTO                                      ((FIT_BIKE_LIGHT_BEAM_ANGLE_MODE)1)
#define FIT_BIKE_LIGHT_BEAM_ANGLE_MODE_COUNT                                     2

typedef FIT_UINT16 FIT_FIT_BASE_UNIT;
#define FIT_FIT_BASE_UNIT_INVALID                                                FIT_UINT16_INVALID
#define FIT_FIT_BASE_UNIT_OTHER                                                  ((FIT_FIT_BASE_UNIT)0)
#define FIT_FIT_BASE_UNIT_KILOGRAM                                               ((FIT_FIT_BASE_UNIT)1)
#define FIT_FIT_BASE_UNIT_POUND                                                  ((FIT_FIT_BASE_UNIT)2)
#define FIT_FIT_BASE_UNIT_COUNT                                                  3

typedef FIT_UINT8 FIT_SET_TYPE;
#define FIT_SET_TYPE_INVALID                                                     FIT_UINT8_INVALID
#define FIT_SET_TYPE_REST                                                        ((FIT_SET_TYPE)0)
#define FIT_SET_TYPE_ACTIVE                                                      ((FIT_SET_TYPE)1)
#define FIT_SET_TYPE_COUNT                                                       2

typedef FIT_UINT16 FIT_EXERCISE_CATEGORY;
#define FIT_EXERCISE_CATEGORY_INVALID                                            FIT_UINT16_INVALID
#define FIT_EXERCISE_CATEGORY_BENCH_PRESS                                        ((FIT_EXERCISE_CATEGORY)0)
#define FIT_EXERCISE_CATEGORY_CALF_RAISE                                         ((FIT_EXERCISE_CATEGORY)1)
#define FIT_EXERCISE_CATEGORY_CARDIO                                             ((FIT_EXERCISE_CATEGORY)2)
#define FIT_EXERCISE_CATEGORY_CARRY                                              ((FIT_EXERCISE_CATEGORY)3)
#define FIT_EXERCISE_CATEGORY_CHOP                                               ((FIT_EXERCISE_CATEGORY)4)
#define FIT_EXERCISE_CATEGORY_CORE                                               ((FIT_EXERCISE_CATEGORY)5)
#define FIT_EXERCISE_CATEGORY_CRUNCH                                             ((FIT_EXERCISE_CATEGORY)6)
#define FIT_EXERCISE_CATEGORY_CURL                                               ((FIT_EXERCISE_CATEGORY)7)
#define FIT_EXERCISE_CATEGORY_DEADLIFT                                           ((FIT_EXERCISE_CATEGORY)8)
#define FIT_EXERCISE_CATEGORY_FLYE                                               ((FIT_EXERCISE_CATEGORY)9)
#define FIT_EXERCISE_CATEGORY_HIP_RAISE                                          ((FIT_EXERCISE_CATEGORY)10)
#define FIT_EXERCISE_CATEGORY_HIP_STABILITY                                      ((FIT_EXERCISE_CATEGORY)11)
#define FIT_EXERCISE_CATEGORY_HIP_SWING                                          ((FIT_EXERCISE_CATEGORY)12)
#define FIT_EXERCISE_CATEGORY_HYPEREXTENSION                                     ((FIT_EXERCISE_CATEGORY)13)
#define FIT_EXERCISE_CATEGORY_LATERAL_RAISE                                      ((FIT_EXERCISE_CATEGORY)14)
#define FIT_EXERCISE_CATEGORY_LEG_CURL                                           ((FIT_EXERCISE_CATEGORY)15)
#define FIT_EXERCISE_CATEGORY_LEG_RAISE                                          ((FIT_EXERCISE_CATEGORY)16)
#define FIT_EXERCISE_CATEGORY_LUNGE                                              ((FIT_EXERCISE_CATEGORY)17)
#define FIT_EXERCISE_CATEGORY_OLYMPIC_LIFT                                       ((FIT_EXERCISE_CATEGORY)18)
#define FIT_EXERCISE_CATEGORY_PLANK                                              ((FIT_EXERCISE_CATEGORY)19)
#define FIT_EXERCISE_CATEGORY_PLYO                                               ((FIT_EXERCISE_CATEGORY)20)
#define FIT_EXERCISE_CATEGORY_PULL_UP                                            ((FIT_EXERCISE_CATEGORY)21)
#define FIT_EXERCISE_CATEGORY_PUSH_UP                                            ((FIT_EXERCISE_CATEGORY)22)
#define FIT_EXERCISE_CATEGORY_ROW                                                ((FIT_EXERCISE_CATEGORY)23)
#define FIT_EXERCISE_CATEGORY_SHOULDER_PRESS                                     ((FIT_EXERCISE_CATEGORY)24)
#define FIT_EXERCISE_CATEGORY_SHOULDER_STABILITY                                 ((FIT_EXERCISE_CATEGORY)25)
#define FIT_EXERCISE_CATEGORY_SHRUG                                              ((FIT_EXERCISE_CATEGORY)26)
#define FIT_EXERCISE_CATEGORY_SIT_UP                                             ((FIT_EXERCISE_CATEGORY)27)
#define FIT_EXERCISE_CATEGORY_SQUAT                                              ((FIT_EXERCISE_CATEGORY)28)
#define FIT_EXERCISE_CATEGORY_TOTAL_BODY                                         ((FIT_EXERCISE_CATEGORY)29)
#define FIT_EXERCISE_CATEGORY_TRICEPS_EXTENSION                                  ((FIT_EXERCISE_CATEGORY)30)
#define FIT_EXERCISE_CATEGORY_WARM_UP                                            ((FIT_EXERCISE_CATEGORY)31)
#define FIT_EXERCISE_CATEGORY_RUN                                                ((FIT_EXERCISE_CATEGORY)32)
#define FIT_EXERCISE_CATEGORY_UNKNOWN                                            ((FIT_EXERCISE_CATEGORY)65534)
#define FIT_EXERCISE_CATEGORY_COUNT                                              34

typedef FIT_UINT16 FIT_BENCH_PRESS_EXERCISE_NAME;
#define FIT_BENCH_PRESS_EXERCISE_NAME_INVALID                                    FIT_UINT16_INVALID
#define FIT_BENCH_PRESS_EXERCISE_NAME_ALTERNATING_DUMBBELL_CHEST_PRESS_ON_SWISS_BALL ((FIT_BENCH_PRESS_EXERCISE_NAME)0)
#define FIT_BENCH_PRESS_EXERCISE_NAME_BARBELL_BENCH_PRESS                        ((FIT_BENCH_PRESS_EXERCISE_NAME)1)
#define FIT_BENCH_PRESS_EXERCISE_NAME_BARBELL_BOARD_BENCH_PRESS                  ((FIT_BENCH_PRESS_EXERCISE_NAME)2)
#define FIT_BENCH_PRESS_EXERCISE_NAME_BARBELL_FLOOR_PRESS                        ((FIT_BENCH_PRESS_EXERCISE_NAME)3)
#define FIT_BENCH_PRESS_EXERCISE_NAME_CLOSE_GRIP_BARBELL_BENCH_PRESS             ((FIT_BENCH_PRESS_EXERCISE_NAME)4)
#define FIT_BENCH_PRESS_EXERCISE_NAME_DECLINE_DUMBBELL_BENCH_PRESS               ((FIT_BENCH_PRESS_EXERCISE_NAME)5)
#define FIT_BENCH_PRESS_EXERCISE_NAME_DUMBBELL_BENCH_PRESS                       ((FIT_BENCH_PRESS_EXERCISE_NAME)6)
#define FIT_BENCH_PRESS_EXERCISE_NAME_DUMBBELL_FLOOR_PRESS                       ((FIT_BENCH_PRESS_EXERCISE_NAME)7)
#define FIT_BENCH_PRESS_EXERCISE_NAME_INCLINE_BARBELL_BENCH_PRESS                ((FIT_BENCH_PRESS_EXERCISE_NAME)8)
#define FIT_BENCH_PRESS_EXERCISE_NAME_INCLINE_DUMBBELL_BENCH_PRESS               ((FIT_BENCH_PRESS_EXERCISE_NAME)9)
#define FIT_BENCH_PRESS_EXERCISE_NAME_INCLINE_SMITH_MACHINE_BENCH_PRESS          ((FIT_BENCH_PRESS_EXERCISE_NAME)10)
#define FIT_BENCH_PRESS_EXERCISE_NAME_ISOMETRIC_BARBELL_BENCH_PRESS              ((FIT_BENCH_PRESS_EXERCISE_NAME)11)
#define FIT_BENCH_PRESS_EXERCISE_NAME_KETTLEBELL_CHEST_PRESS                     ((FIT_BENCH_PRESS_EXERCISE_NAME)12)
#define FIT_BENCH_PRESS_EXERCISE_NAME_NEUTRAL_GRIP_DUMBBELL_BENCH_PRESS          ((FIT_BENCH_PRESS_EXERCISE_NAME)13)
#define FIT_BENCH_PRESS_EXERCISE_NAME_NEUTRAL_GRIP_DUMBBELL_INCLINE_BENCH_PRESS  ((FIT_BENCH_PRESS_EXERCISE_NAME)14)
#define FIT_BENCH_PRESS_EXERCISE_NAME_ONE_ARM_FLOOR_PRESS                        ((FIT_BENCH_PRESS_EXERCISE_NAME)15)
#define FIT_BENCH_PRESS_EXERCISE_NAME_WEIGHTED_ONE_ARM_FLOOR_PRESS               ((FIT_BENCH_PRESS_EXERCISE_NAME)16)
#define FIT_BENCH_PRESS_EXERCISE_NAME_PARTIAL_LOCKOUT                            ((FIT_BENCH_PRESS_EXERCISE_NAME)17)
#define FIT_BENCH_PRESS_EXERCISE_NAME_REVERSE_GRIP_BARBELL_BENCH_PRESS           ((FIT_BENCH_PRESS_EXERCISE_NAME)18)
#define FIT_BENCH_PRESS_EXERCISE_NAME_REVERSE_GRIP_INCLINE_BENCH_PRESS           ((FIT_BENCH_PRESS_EXERCISE_NAME)19)
#define FIT_BENCH_PRESS_EXERCISE_NAME_SINGLE_ARM_CABLE_CHEST_PRESS               ((FIT_BENCH_PRESS_EXERCISE_NAME)20)
#define FIT_BENCH_PRESS_EXERCISE_NAME_SINGLE_ARM_DUMBBELL_BENCH_PRESS            ((FIT_BENCH_PRESS_EXERCISE_NAME)21)
#define FIT_BENCH_PRESS_EXERCISE_NAME_SMITH_MACHINE_BENCH_PRESS                  ((FIT_BENCH_PRESS_EXERCISE_NAME)22)
#define FIT_BENCH_PRESS_EXERCISE_NAME_SWISS_BALL_DUMBBELL_CHEST_PRESS            ((FIT_BENCH_PRESS_EXERCISE_NAME)23)
#define FIT_BENCH_PRESS_EXERCISE_NAME_TRIPLE_STOP_BARBELL_BENCH_PRESS            ((FIT_BENCH_PRESS_EXERCISE_NAME)24)
#define FIT_BENCH_PRESS_EXERCISE_NAME_WIDE_GRIP_BARBELL_BENCH_PRESS              ((FIT_BENCH_PRESS_EXERCISE_NAME)25)
#define FIT_BENCH_PRESS_EXERCISE_NAME_ALTERNATING_DUMBBELL_CHEST_PRESS           ((FIT_BENCH_PRESS_EXERCISE_NAME)26)
#define FIT_BENCH_PRESS_EXERCISE_NAME_COUNT                                      27

typedef FIT_UINT16 FIT_CALF_RAISE_EXERCISE_NAME;
#define FIT_CALF_RAISE_EXERCISE_NAME_INVALID                                     FIT_UINT16_INVALID
#define FIT_CALF_RAISE_EXERCISE_NAME_3_WAY_CALF_RAISE                            ((FIT_CALF_RAISE_EXERCISE_NAME)0)
#define FIT_CALF_RAISE_EXERCISE_NAME_3_WAY_WEIGHTED_CALF_RAISE                   ((FIT_CALF_RAISE_EXERCISE_NAME)1)
#define FIT_CALF_RAISE_EXERCISE_NAME_3_WAY_SINGLE_LEG_CALF_RAISE                 ((FIT_CALF_RAISE_EXERCISE_NAME)2)
#define FIT_CALF_RAISE_EXERCISE_NAME_3_WAY_WEIGHTED_SINGLE_LEG_CALF_RAISE        ((FIT_CALF_RAISE_EXERCISE_NAME)3)
#define FIT_CALF_RAISE_EXERCISE_NAME_DONKEY_CALF_RAISE                           ((FIT_CALF_RAISE_EXERCISE_NAME)4)
#define FIT_CALF_RAISE_EXERCISE_NAME_WEIGHTED_DONKEY_CALF_RAISE                  ((FIT_CALF_RAISE_EXERCISE_NAME)5)
#define FIT_CALF_RAISE_EXERCISE_NAME_SEATED_CALF_RAISE                           ((FIT_CALF_RAISE_EXERCISE_NAME)6)
#define FIT_CALF_RAISE_EXERCISE_NAME_WEIGHTED_SEATED_CALF_RAISE                  ((FIT_CALF_RAISE_EXERCISE_NAME)7)
#define FIT_CALF_RAISE_EXERCISE_NAME_SEATED_DUMBBELL_TOE_RAISE                   ((FIT_CALF_RAISE_EXERCISE_NAME)8)
#define FIT_CALF_RAISE_EXERCISE_NAME_SINGLE_LEG_BENT_KNEE_CALF_RAISE             ((FIT_CALF_RAISE_EXERCISE_NAME)9)
#define FIT_CALF_RAISE_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_BENT_KNEE_CALF_RAISE    ((FIT_CALF_RAISE_EXERCISE_NAME)10)
#define FIT_CALF_RAISE_EXERCISE_NAME_SINGLE_LEG_DECLINE_PUSH_UP                  ((FIT_CALF_RAISE_EXERCISE_NAME)11)
#define FIT_CALF_RAISE_EXERCISE_NAME_SINGLE_LEG_DONKEY_CALF_RAISE                ((FIT_CALF_RAISE_EXERCISE_NAME)12)
#define FIT_CALF_RAISE_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_DONKEY_CALF_RAISE       ((FIT_CALF_RAISE_EXERCISE_NAME)13)
#define FIT_CALF_RAISE_EXERCISE_NAME_SINGLE_LEG_HIP_RAISE_WITH_KNEE_HOLD         ((FIT_CALF_RAISE_EXERCISE_NAME)14)
#define FIT_CALF_RAISE_EXERCISE_NAME_SINGLE_LEG_STANDING_CALF_RAISE              ((FIT_CALF_RAISE_EXERCISE_NAME)15)
#define FIT_CALF_RAISE_EXERCISE_NAME_SINGLE_LEG_STANDING_DUMBBELL_CALF_RAISE     ((FIT_CALF_RAISE_EXERCISE_NAME)16)
#define FIT_CALF_RAISE_EXERCISE_NAME_STANDING_BARBELL_CALF_RAISE                 ((FIT_CALF_RAISE_EXERCISE_NAME)17)
#define FIT_CALF_RAISE_EXERCISE_NAME_STANDING_CALF_RAISE                         ((FIT_CALF_RAISE_EXERCISE_NAME)18)
#define FIT_CALF_RAISE_EXERCISE_NAME_WEIGHTED_STANDING_CALF_RAISE                ((FIT_CALF_RAISE_EXERCISE_NAME)19)
#define FIT_CALF_RAISE_EXERCISE_NAME_STANDING_DUMBBELL_CALF_RAISE                ((FIT_CALF_RAISE_EXERCISE_NAME)20)
#define FIT_CALF_RAISE_EXERCISE_NAME_COUNT                                       21

typedef FIT_UINT16 FIT_CARDIO_EXERCISE_NAME;
#define FIT_CARDIO_EXERCISE_NAME_INVALID                                         FIT_UINT16_INVALID
#define FIT_CARDIO_EXERCISE_NAME_BOB_AND_WEAVE_CIRCLE                            ((FIT_CARDIO_EXERCISE_NAME)0)
#define FIT_CARDIO_EXERCISE_NAME_WEIGHTED_BOB_AND_WEAVE_CIRCLE                   ((FIT_CARDIO_EXERCISE_NAME)1)
#define FIT_CARDIO_EXERCISE_NAME_CARDIO_CORE_CRAWL                               ((FIT_CARDIO_EXERCISE_NAME)2)
#define FIT_CARDIO_EXERCISE_NAME_WEIGHTED_CARDIO_CORE_CRAWL                      ((FIT_CARDIO_EXERCISE_NAME)3)
#define FIT_CARDIO_EXERCISE_NAME_DOUBLE_UNDER                                    ((FIT_CARDIO_EXERCISE_NAME)4)
#define FIT_CARDIO_EXERCISE_NAME_WEIGHTED_DOUBLE_UNDER                           ((FIT_CARDIO_EXERCISE_NAME)5)
#define FIT_CARDIO_EXERCISE_NAME_JUMP_ROPE                                       ((FIT_CARDIO_EXERCISE_NAME)6)
#define FIT_CARDIO_EXERCISE_NAME_WEIGHTED_JUMP_ROPE                              ((FIT_CARDIO_EXERCISE_NAME)7)
#define FIT_CARDIO_EXERCISE_NAME_JUMP_ROPE_CROSSOVER                             ((FIT_CARDIO_EXERCISE_NAME)8)
#define FIT_CARDIO_EXERCISE_NAME_WEIGHTED_JUMP_ROPE_CROSSOVER                    ((FIT_CARDIO_EXERCISE_NAME)9)
#define FIT_CARDIO_EXERCISE_NAME_JUMP_ROPE_JOG                                   ((FIT_CARDIO_EXERCISE_NAME)10)
#define FIT_CARDIO_EXERCISE_NAME_WEIGHTED_JUMP_ROPE_JOG                          ((FIT_CARDIO_EXERCISE_NAME)11)
#define FIT_CARDIO_EXERCISE_NAME_JUMPING_JACKS                                   ((FIT_CARDIO_EXERCISE_NAME)12)
#define FIT_CARDIO_EXERCISE_NAME_WEIGHTED_JUMPING_JACKS                          ((FIT_CARDIO_EXERCISE_NAME)13)
#define FIT_CARDIO_EXERCISE_NAME_SKI_MOGULS                                      ((FIT_CARDIO_EXERCISE_NAME)14)
#define FIT_CARDIO_EXERCISE_NAME_WEIGHTED_SKI_MOGULS                             ((FIT_CARDIO_EXERCISE_NAME)15)
#define FIT_CARDIO_EXERCISE_NAME_SPLIT_JACKS                                     ((FIT_CARDIO_EXERCISE_NAME)16)
#define FIT_CARDIO_EXERCISE_NAME_WEIGHTED_SPLIT_JACKS                            ((FIT_CARDIO_EXERCISE_NAME)17)
#define FIT_CARDIO_EXERCISE_NAME_SQUAT_JACKS                                     ((FIT_CARDIO_EXERCISE_NAME)18)
#define FIT_CARDIO_EXERCISE_NAME_WEIGHTED_SQUAT_JACKS                            ((FIT_CARDIO_EXERCISE_NAME)19)
#define FIT_CARDIO_EXERCISE_NAME_TRIPLE_UNDER                                    ((FIT_CARDIO_EXERCISE_NAME)20)
#define FIT_CARDIO_EXERCISE_NAME_WEIGHTED_TRIPLE_UNDER                           ((FIT_CARDIO_EXERCISE_NAME)21)
#define FIT_CARDIO_EXERCISE_NAME_COUNT                                           22

typedef FIT_UINT16 FIT_CARRY_EXERCISE_NAME;
#define FIT_CARRY_EXERCISE_NAME_INVALID                                          FIT_UINT16_INVALID
#define FIT_CARRY_EXERCISE_NAME_BAR_HOLDS                                        ((FIT_CARRY_EXERCISE_NAME)0)
#define FIT_CARRY_EXERCISE_NAME_FARMERS_WALK                                     ((FIT_CARRY_EXERCISE_NAME)1)
#define FIT_CARRY_EXERCISE_NAME_FARMERS_WALK_ON_TOES                             ((FIT_CARRY_EXERCISE_NAME)2)
#define FIT_CARRY_EXERCISE_NAME_HEX_DUMBBELL_HOLD                                ((FIT_CARRY_EXERCISE_NAME)3)
#define FIT_CARRY_EXERCISE_NAME_OVERHEAD_CARRY                                   ((FIT_CARRY_EXERCISE_NAME)4)
#define FIT_CARRY_EXERCISE_NAME_COUNT                                            5

typedef FIT_UINT16 FIT_CHOP_EXERCISE_NAME;
#define FIT_CHOP_EXERCISE_NAME_INVALID                                           FIT_UINT16_INVALID
#define FIT_CHOP_EXERCISE_NAME_CABLE_PULL_THROUGH                                ((FIT_CHOP_EXERCISE_NAME)0)
#define FIT_CHOP_EXERCISE_NAME_CABLE_ROTATIONAL_LIFT                             ((FIT_CHOP_EXERCISE_NAME)1)
#define FIT_CHOP_EXERCISE_NAME_CABLE_WOODCHOP                                    ((FIT_CHOP_EXERCISE_NAME)2)
#define FIT_CHOP_EXERCISE_NAME_CROSS_CHOP_TO_KNEE                                ((FIT_CHOP_EXERCISE_NAME)3)
#define FIT_CHOP_EXERCISE_NAME_WEIGHTED_CROSS_CHOP_TO_KNEE                       ((FIT_CHOP_EXERCISE_NAME)4)
#define FIT_CHOP_EXERCISE_NAME_DUMBBELL_CHOP                                     ((FIT_CHOP_EXERCISE_NAME)5)
#define FIT_CHOP_EXERCISE_NAME_HALF_KNEELING_ROTATION                            ((FIT_CHOP_EXERCISE_NAME)6)
#define FIT_CHOP_EXERCISE_NAME_WEIGHTED_HALF_KNEELING_ROTATION                   ((FIT_CHOP_EXERCISE_NAME)7)
#define FIT_CHOP_EXERCISE_NAME_HALF_KNEELING_ROTATIONAL_CHOP                     ((FIT_CHOP_EXERCISE_NAME)8)
#define FIT_CHOP_EXERCISE_NAME_HALF_KNEELING_ROTATIONAL_REVERSE_CHOP             ((FIT_CHOP_EXERCISE_NAME)9)
#define FIT_CHOP_EXERCISE_NAME_HALF_KNEELING_STABILITY_CHOP                      ((FIT_CHOP_EXERCISE_NAME)10)
#define FIT_CHOP_EXERCISE_NAME_HALF_KNEELING_STABILITY_REVERSE_CHOP              ((FIT_CHOP_EXERCISE_NAME)11)
#define FIT_CHOP_EXERCISE_NAME_KNEELING_ROTATIONAL_CHOP                          ((FIT_CHOP_EXERCISE_NAME)12)
#define FIT_CHOP_EXERCISE_NAME_KNEELING_ROTATIONAL_REVERSE_CHOP                  ((FIT_CHOP_EXERCISE_NAME)13)
#define FIT_CHOP_EXERCISE_NAME_KNEELING_STABILITY_CHOP                           ((FIT_CHOP_EXERCISE_NAME)14)
#define FIT_CHOP_EXERCISE_NAME_KNEELING_WOODCHOPPER                              ((FIT_CHOP_EXERCISE_NAME)15)
#define FIT_CHOP_EXERCISE_NAME_MEDICINE_BALL_WOOD_CHOPS                          ((FIT_CHOP_EXERCISE_NAME)16)
#define FIT_CHOP_EXERCISE_NAME_POWER_SQUAT_CHOPS                                 ((FIT_CHOP_EXERCISE_NAME)17)
#define FIT_CHOP_EXERCISE_NAME_WEIGHTED_POWER_SQUAT_CHOPS                        ((FIT_CHOP_EXERCISE_NAME)18)
#define FIT_CHOP_EXERCISE_NAME_STANDING_ROTATIONAL_CHOP                          ((FIT_CHOP_EXERCISE_NAME)19)
#define FIT_CHOP_EXERCISE_NAME_STANDING_SPLIT_ROTATIONAL_CHOP                    ((FIT_CHOP_EXERCISE_NAME)20)
#define FIT_CHOP_EXERCISE_NAME_STANDING_SPLIT_ROTATIONAL_REVERSE_CHOP            ((FIT_CHOP_EXERCISE_NAME)21)
#define FIT_CHOP_EXERCISE_NAME_STANDING_STABILITY_REVERSE_CHOP                   ((FIT_CHOP_EXERCISE_NAME)22)
#define FIT_CHOP_EXERCISE_NAME_COUNT                                             23

typedef FIT_UINT16 FIT_CORE_EXERCISE_NAME;
#define FIT_CORE_EXERCISE_NAME_INVALID                                           FIT_UINT16_INVALID
#define FIT_CORE_EXERCISE_NAME_ABS_JABS                                          ((FIT_CORE_EXERCISE_NAME)0)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_ABS_JABS                                 ((FIT_CORE_EXERCISE_NAME)1)
#define FIT_CORE_EXERCISE_NAME_ALTERNATING_PLATE_REACH                           ((FIT_CORE_EXERCISE_NAME)2)
#define FIT_CORE_EXERCISE_NAME_BARBELL_ROLLOUT                                   ((FIT_CORE_EXERCISE_NAME)3)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_BARBELL_ROLLOUT                          ((FIT_CORE_EXERCISE_NAME)4)
#define FIT_CORE_EXERCISE_NAME_BODY_BAR_OBLIQUE_TWIST                            ((FIT_CORE_EXERCISE_NAME)5)
#define FIT_CORE_EXERCISE_NAME_CABLE_CORE_PRESS                                  ((FIT_CORE_EXERCISE_NAME)6)
#define FIT_CORE_EXERCISE_NAME_CABLE_SIDE_BEND                                   ((FIT_CORE_EXERCISE_NAME)7)
#define FIT_CORE_EXERCISE_NAME_SIDE_BEND                                         ((FIT_CORE_EXERCISE_NAME)8)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_SIDE_BEND                                ((FIT_CORE_EXERCISE_NAME)9)
#define FIT_CORE_EXERCISE_NAME_CRESCENT_CIRCLE                                   ((FIT_CORE_EXERCISE_NAME)10)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_CRESCENT_CIRCLE                          ((FIT_CORE_EXERCISE_NAME)11)
#define FIT_CORE_EXERCISE_NAME_CYCLING_RUSSIAN_TWIST                             ((FIT_CORE_EXERCISE_NAME)12)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_CYCLING_RUSSIAN_TWIST                    ((FIT_CORE_EXERCISE_NAME)13)
#define FIT_CORE_EXERCISE_NAME_ELEVATED_FEET_RUSSIAN_TWIST                       ((FIT_CORE_EXERCISE_NAME)14)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_ELEVATED_FEET_RUSSIAN_TWIST              ((FIT_CORE_EXERCISE_NAME)15)
#define FIT_CORE_EXERCISE_NAME_HALF_TURKISH_GET_UP                               ((FIT_CORE_EXERCISE_NAME)16)
#define FIT_CORE_EXERCISE_NAME_KETTLEBELL_WINDMILL                               ((FIT_CORE_EXERCISE_NAME)17)
#define FIT_CORE_EXERCISE_NAME_KNEELING_AB_WHEEL                                 ((FIT_CORE_EXERCISE_NAME)18)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_KNEELING_AB_WHEEL                        ((FIT_CORE_EXERCISE_NAME)19)
#define FIT_CORE_EXERCISE_NAME_MODIFIED_FRONT_LEVER                              ((FIT_CORE_EXERCISE_NAME)20)
#define FIT_CORE_EXERCISE_NAME_OPEN_KNEE_TUCKS                                   ((FIT_CORE_EXERCISE_NAME)21)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_OPEN_KNEE_TUCKS                          ((FIT_CORE_EXERCISE_NAME)22)
#define FIT_CORE_EXERCISE_NAME_SIDE_ABS_LEG_LIFT                                 ((FIT_CORE_EXERCISE_NAME)23)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_SIDE_ABS_LEG_LIFT                        ((FIT_CORE_EXERCISE_NAME)24)
#define FIT_CORE_EXERCISE_NAME_SWISS_BALL_JACKKNIFE                              ((FIT_CORE_EXERCISE_NAME)25)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_SWISS_BALL_JACKKNIFE                     ((FIT_CORE_EXERCISE_NAME)26)
#define FIT_CORE_EXERCISE_NAME_SWISS_BALL_PIKE                                   ((FIT_CORE_EXERCISE_NAME)27)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_SWISS_BALL_PIKE                          ((FIT_CORE_EXERCISE_NAME)28)
#define FIT_CORE_EXERCISE_NAME_SWISS_BALL_ROLLOUT                                ((FIT_CORE_EXERCISE_NAME)29)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_SWISS_BALL_ROLLOUT                       ((FIT_CORE_EXERCISE_NAME)30)
#define FIT_CORE_EXERCISE_NAME_TRIANGLE_HIP_PRESS                                ((FIT_CORE_EXERCISE_NAME)31)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_TRIANGLE_HIP_PRESS                       ((FIT_CORE_EXERCISE_NAME)32)
#define FIT_CORE_EXERCISE_NAME_TRX_SUSPENDED_JACKKNIFE                           ((FIT_CORE_EXERCISE_NAME)33)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_TRX_SUSPENDED_JACKKNIFE                  ((FIT_CORE_EXERCISE_NAME)34)
#define FIT_CORE_EXERCISE_NAME_U_BOAT                                            ((FIT_CORE_EXERCISE_NAME)35)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_U_BOAT                                   ((FIT_CORE_EXERCISE_NAME)36)
#define FIT_CORE_EXERCISE_NAME_WINDMILL_SWITCHES                                 ((FIT_CORE_EXERCISE_NAME)37)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_WINDMILL_SWITCHES                        ((FIT_CORE_EXERCISE_NAME)38)
#define FIT_CORE_EXERCISE_NAME_ALTERNATING_SLIDE_OUT                             ((FIT_CORE_EXERCISE_NAME)39)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_ALTERNATING_SLIDE_OUT                    ((FIT_CORE_EXERCISE_NAME)40)
#define FIT_CORE_EXERCISE_NAME_GHD_BACK_EXTENSIONS                               ((FIT_CORE_EXERCISE_NAME)41)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_GHD_BACK_EXTENSIONS                      ((FIT_CORE_EXERCISE_NAME)42)
#define FIT_CORE_EXERCISE_NAME_OVERHEAD_WALK                                     ((FIT_CORE_EXERCISE_NAME)43)
#define FIT_CORE_EXERCISE_NAME_INCHWORM                                          ((FIT_CORE_EXERCISE_NAME)44)
#define FIT_CORE_EXERCISE_NAME_WEIGHTED_MODIFIED_FRONT_LEVER                     ((FIT_CORE_EXERCISE_NAME)45)
#define FIT_CORE_EXERCISE_NAME_COUNT                                             46

typedef FIT_UINT16 FIT_CRUNCH_EXERCISE_NAME;
#define FIT_CRUNCH_EXERCISE_NAME_INVALID                                         FIT_UINT16_INVALID
#define FIT_CRUNCH_EXERCISE_NAME_BICYCLE_CRUNCH                                  ((FIT_CRUNCH_EXERCISE_NAME)0)
#define FIT_CRUNCH_EXERCISE_NAME_CABLE_CRUNCH                                    ((FIT_CRUNCH_EXERCISE_NAME)1)
#define FIT_CRUNCH_EXERCISE_NAME_CIRCULAR_ARM_CRUNCH                             ((FIT_CRUNCH_EXERCISE_NAME)2)
#define FIT_CRUNCH_EXERCISE_NAME_CROSSED_ARMS_CRUNCH                             ((FIT_CRUNCH_EXERCISE_NAME)3)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_CROSSED_ARMS_CRUNCH                    ((FIT_CRUNCH_EXERCISE_NAME)4)
#define FIT_CRUNCH_EXERCISE_NAME_CROSS_LEG_REVERSE_CRUNCH                        ((FIT_CRUNCH_EXERCISE_NAME)5)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_CROSS_LEG_REVERSE_CRUNCH               ((FIT_CRUNCH_EXERCISE_NAME)6)
#define FIT_CRUNCH_EXERCISE_NAME_CRUNCH_CHOP                                     ((FIT_CRUNCH_EXERCISE_NAME)7)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_CRUNCH_CHOP                            ((FIT_CRUNCH_EXERCISE_NAME)8)
#define FIT_CRUNCH_EXERCISE_NAME_DOUBLE_CRUNCH                                   ((FIT_CRUNCH_EXERCISE_NAME)9)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_DOUBLE_CRUNCH                          ((FIT_CRUNCH_EXERCISE_NAME)10)
#define FIT_CRUNCH_EXERCISE_NAME_ELBOW_TO_KNEE_CRUNCH                            ((FIT_CRUNCH_EXERCISE_NAME)11)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_ELBOW_TO_KNEE_CRUNCH                   ((FIT_CRUNCH_EXERCISE_NAME)12)
#define FIT_CRUNCH_EXERCISE_NAME_FLUTTER_KICKS                                   ((FIT_CRUNCH_EXERCISE_NAME)13)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_FLUTTER_KICKS                          ((FIT_CRUNCH_EXERCISE_NAME)14)
#define FIT_CRUNCH_EXERCISE_NAME_FOAM_ROLLER_REVERSE_CRUNCH_ON_BENCH             ((FIT_CRUNCH_EXERCISE_NAME)15)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_FOAM_ROLLER_REVERSE_CRUNCH_ON_BENCH    ((FIT_CRUNCH_EXERCISE_NAME)16)
#define FIT_CRUNCH_EXERCISE_NAME_FOAM_ROLLER_REVERSE_CRUNCH_WITH_DUMBBELL        ((FIT_CRUNCH_EXERCISE_NAME)17)
#define FIT_CRUNCH_EXERCISE_NAME_FOAM_ROLLER_REVERSE_CRUNCH_WITH_MEDICINE_BALL   ((FIT_CRUNCH_EXERCISE_NAME)18)
#define FIT_CRUNCH_EXERCISE_NAME_FROG_PRESS                                      ((FIT_CRUNCH_EXERCISE_NAME)19)
#define FIT_CRUNCH_EXERCISE_NAME_HANGING_KNEE_RAISE_OBLIQUE_CRUNCH               ((FIT_CRUNCH_EXERCISE_NAME)20)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_HANGING_KNEE_RAISE_OBLIQUE_CRUNCH      ((FIT_CRUNCH_EXERCISE_NAME)21)
#define FIT_CRUNCH_EXERCISE_NAME_HIP_CROSSOVER                                   ((FIT_CRUNCH_EXERCISE_NAME)22)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_HIP_CROSSOVER                          ((FIT_CRUNCH_EXERCISE_NAME)23)
#define FIT_CRUNCH_EXERCISE_NAME_HOLLOW_ROCK                                     ((FIT_CRUNCH_EXERCISE_NAME)24)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_HOLLOW_ROCK                            ((FIT_CRUNCH_EXERCISE_NAME)25)
#define FIT_CRUNCH_EXERCISE_NAME_INCLINE_REVERSE_CRUNCH                          ((FIT_CRUNCH_EXERCISE_NAME)26)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_INCLINE_REVERSE_CRUNCH                 ((FIT_CRUNCH_EXERCISE_NAME)27)
#define FIT_CRUNCH_EXERCISE_NAME_KNEELING_CABLE_CRUNCH                           ((FIT_CRUNCH_EXERCISE_NAME)28)
#define FIT_CRUNCH_EXERCISE_NAME_KNEELING_CROSS_CRUNCH                           ((FIT_CRUNCH_EXERCISE_NAME)29)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_KNEELING_CROSS_CRUNCH                  ((FIT_CRUNCH_EXERCISE_NAME)30)
#define FIT_CRUNCH_EXERCISE_NAME_KNEELING_OBLIQUE_CABLE_CRUNCH                   ((FIT_CRUNCH_EXERCISE_NAME)31)
#define FIT_CRUNCH_EXERCISE_NAME_KNEES_TO_ELBOW                                  ((FIT_CRUNCH_EXERCISE_NAME)32)
#define FIT_CRUNCH_EXERCISE_NAME_LEG_EXTENSIONS                                  ((FIT_CRUNCH_EXERCISE_NAME)33)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_LEG_EXTENSIONS                         ((FIT_CRUNCH_EXERCISE_NAME)34)
#define FIT_CRUNCH_EXERCISE_NAME_LEG_LEVERS                                      ((FIT_CRUNCH_EXERCISE_NAME)35)
#define FIT_CRUNCH_EXERCISE_NAME_MCGILL_CURL_UP                                  ((FIT_CRUNCH_EXERCISE_NAME)36)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_MCGILL_CURL_UP                         ((FIT_CRUNCH_EXERCISE_NAME)37)
#define FIT_CRUNCH_EXERCISE_NAME_MODIFIED_PILATES_ROLL_UP_WITH_BALL              ((FIT_CRUNCH_EXERCISE_NAME)38)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_MODIFIED_PILATES_ROLL_UP_WITH_BALL     ((FIT_CRUNCH_EXERCISE_NAME)39)
#define FIT_CRUNCH_EXERCISE_NAME_PILATES_CRUNCH                                  ((FIT_CRUNCH_EXERCISE_NAME)40)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_PILATES_CRUNCH                         ((FIT_CRUNCH_EXERCISE_NAME)41)
#define FIT_CRUNCH_EXERCISE_NAME_PILATES_ROLL_UP_WITH_BALL                       ((FIT_CRUNCH_EXERCISE_NAME)42)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_PILATES_ROLL_UP_WITH_BALL              ((FIT_CRUNCH_EXERCISE_NAME)43)
#define FIT_CRUNCH_EXERCISE_NAME_RAISED_LEGS_CRUNCH                              ((FIT_CRUNCH_EXERCISE_NAME)44)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_RAISED_LEGS_CRUNCH                     ((FIT_CRUNCH_EXERCISE_NAME)45)
#define FIT_CRUNCH_EXERCISE_NAME_REVERSE_CRUNCH                                  ((FIT_CRUNCH_EXERCISE_NAME)46)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_REVERSE_CRUNCH                         ((FIT_CRUNCH_EXERCISE_NAME)47)
#define FIT_CRUNCH_EXERCISE_NAME_REVERSE_CRUNCH_ON_A_BENCH                       ((FIT_CRUNCH_EXERCISE_NAME)48)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_REVERSE_CRUNCH_ON_A_BENCH              ((FIT_CRUNCH_EXERCISE_NAME)49)
#define FIT_CRUNCH_EXERCISE_NAME_REVERSE_CURL_AND_LIFT                           ((FIT_CRUNCH_EXERCISE_NAME)50)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_REVERSE_CURL_AND_LIFT                  ((FIT_CRUNCH_EXERCISE_NAME)51)
#define FIT_CRUNCH_EXERCISE_NAME_ROTATIONAL_LIFT                                 ((FIT_CRUNCH_EXERCISE_NAME)52)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_ROTATIONAL_LIFT                        ((FIT_CRUNCH_EXERCISE_NAME)53)
#define FIT_CRUNCH_EXERCISE_NAME_SEATED_ALTERNATING_REVERSE_CRUNCH               ((FIT_CRUNCH_EXERCISE_NAME)54)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_SEATED_ALTERNATING_REVERSE_CRUNCH      ((FIT_CRUNCH_EXERCISE_NAME)55)
#define FIT_CRUNCH_EXERCISE_NAME_SEATED_LEG_U                                    ((FIT_CRUNCH_EXERCISE_NAME)56)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_SEATED_LEG_U                           ((FIT_CRUNCH_EXERCISE_NAME)57)
#define FIT_CRUNCH_EXERCISE_NAME_SIDE_TO_SIDE_CRUNCH_AND_WEAVE                   ((FIT_CRUNCH_EXERCISE_NAME)58)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_SIDE_TO_SIDE_CRUNCH_AND_WEAVE          ((FIT_CRUNCH_EXERCISE_NAME)59)
#define FIT_CRUNCH_EXERCISE_NAME_SINGLE_LEG_REVERSE_CRUNCH                       ((FIT_CRUNCH_EXERCISE_NAME)60)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_REVERSE_CRUNCH              ((FIT_CRUNCH_EXERCISE_NAME)61)
#define FIT_CRUNCH_EXERCISE_NAME_SKATER_CRUNCH_CROSS                             ((FIT_CRUNCH_EXERCISE_NAME)62)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_SKATER_CRUNCH_CROSS                    ((FIT_CRUNCH_EXERCISE_NAME)63)
#define FIT_CRUNCH_EXERCISE_NAME_STANDING_CABLE_CRUNCH                           ((FIT_CRUNCH_EXERCISE_NAME)64)
#define FIT_CRUNCH_EXERCISE_NAME_STANDING_SIDE_CRUNCH                            ((FIT_CRUNCH_EXERCISE_NAME)65)
#define FIT_CRUNCH_EXERCISE_NAME_STEP_CLIMB                                      ((FIT_CRUNCH_EXERCISE_NAME)66)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_STEP_CLIMB                             ((FIT_CRUNCH_EXERCISE_NAME)67)
#define FIT_CRUNCH_EXERCISE_NAME_SWISS_BALL_CRUNCH                               ((FIT_CRUNCH_EXERCISE_NAME)68)
#define FIT_CRUNCH_EXERCISE_NAME_SWISS_BALL_REVERSE_CRUNCH                       ((FIT_CRUNCH_EXERCISE_NAME)69)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_SWISS_BALL_REVERSE_CRUNCH              ((FIT_CRUNCH_EXERCISE_NAME)70)
#define FIT_CRUNCH_EXERCISE_NAME_SWISS_BALL_RUSSIAN_TWIST                        ((FIT_CRUNCH_EXERCISE_NAME)71)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_SWISS_BALL_RUSSIAN_TWIST               ((FIT_CRUNCH_EXERCISE_NAME)72)
#define FIT_CRUNCH_EXERCISE_NAME_SWISS_BALL_SIDE_CRUNCH                          ((FIT_CRUNCH_EXERCISE_NAME)73)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_SWISS_BALL_SIDE_CRUNCH                 ((FIT_CRUNCH_EXERCISE_NAME)74)
#define FIT_CRUNCH_EXERCISE_NAME_THORACIC_CRUNCHES_ON_FOAM_ROLLER                ((FIT_CRUNCH_EXERCISE_NAME)75)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_THORACIC_CRUNCHES_ON_FOAM_ROLLER       ((FIT_CRUNCH_EXERCISE_NAME)76)
#define FIT_CRUNCH_EXERCISE_NAME_TRICEPS_CRUNCH                                  ((FIT_CRUNCH_EXERCISE_NAME)77)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_BICYCLE_CRUNCH                         ((FIT_CRUNCH_EXERCISE_NAME)78)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_CRUNCH                                 ((FIT_CRUNCH_EXERCISE_NAME)79)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_SWISS_BALL_CRUNCH                      ((FIT_CRUNCH_EXERCISE_NAME)80)
#define FIT_CRUNCH_EXERCISE_NAME_TOES_TO_BAR                                     ((FIT_CRUNCH_EXERCISE_NAME)81)
#define FIT_CRUNCH_EXERCISE_NAME_WEIGHTED_TOES_TO_BAR                            ((FIT_CRUNCH_EXERCISE_NAME)82)
#define FIT_CRUNCH_EXERCISE_NAME_CRUNCH                                          ((FIT_CRUNCH_EXERCISE_NAME)83)
#define FIT_CRUNCH_EXERCISE_NAME_COUNT                                           84

typedef FIT_UINT16 FIT_CURL_EXERCISE_NAME;
#define FIT_CURL_EXERCISE_NAME_INVALID                                           FIT_UINT16_INVALID
#define FIT_CURL_EXERCISE_NAME_ALTERNATING_DUMBBELL_BICEPS_CURL                  ((FIT_CURL_EXERCISE_NAME)0)
#define FIT_CURL_EXERCISE_NAME_ALTERNATING_DUMBBELL_BICEPS_CURL_ON_SWISS_BALL    ((FIT_CURL_EXERCISE_NAME)1)
#define FIT_CURL_EXERCISE_NAME_ALTERNATING_INCLINE_DUMBBELL_BICEPS_CURL          ((FIT_CURL_EXERCISE_NAME)2)
#define FIT_CURL_EXERCISE_NAME_BARBELL_BICEPS_CURL                               ((FIT_CURL_EXERCISE_NAME)3)
#define FIT_CURL_EXERCISE_NAME_BARBELL_REVERSE_WRIST_CURL                        ((FIT_CURL_EXERCISE_NAME)4)
#define FIT_CURL_EXERCISE_NAME_BARBELL_WRIST_CURL                                ((FIT_CURL_EXERCISE_NAME)5)
#define FIT_CURL_EXERCISE_NAME_BEHIND_THE_BACK_BARBELL_REVERSE_WRIST_CURL        ((FIT_CURL_EXERCISE_NAME)6)
#define FIT_CURL_EXERCISE_NAME_BEHIND_THE_BACK_ONE_ARM_CABLE_CURL                ((FIT_CURL_EXERCISE_NAME)7)
#define FIT_CURL_EXERCISE_NAME_CABLE_BICEPS_CURL                                 ((FIT_CURL_EXERCISE_NAME)8)
#define FIT_CURL_EXERCISE_NAME_CABLE_HAMMER_CURL                                 ((FIT_CURL_EXERCISE_NAME)9)
#define FIT_CURL_EXERCISE_NAME_CHEATING_BARBELL_BICEPS_CURL                      ((FIT_CURL_EXERCISE_NAME)10)
#define FIT_CURL_EXERCISE_NAME_CLOSE_GRIP_EZ_BAR_BICEPS_CURL                     ((FIT_CURL_EXERCISE_NAME)11)
#define FIT_CURL_EXERCISE_NAME_CROSS_BODY_DUMBBELL_HAMMER_CURL                   ((FIT_CURL_EXERCISE_NAME)12)
#define FIT_CURL_EXERCISE_NAME_DEAD_HANG_BICEPS_CURL                             ((FIT_CURL_EXERCISE_NAME)13)
#define FIT_CURL_EXERCISE_NAME_DECLINE_HAMMER_CURL                               ((FIT_CURL_EXERCISE_NAME)14)
#define FIT_CURL_EXERCISE_NAME_DUMBBELL_BICEPS_CURL_WITH_STATIC_HOLD             ((FIT_CURL_EXERCISE_NAME)15)
#define FIT_CURL_EXERCISE_NAME_DUMBBELL_HAMMER_CURL                              ((FIT_CURL_EXERCISE_NAME)16)
#define FIT_CURL_EXERCISE_NAME_DUMBBELL_REVERSE_WRIST_CURL                       ((FIT_CURL_EXERCISE_NAME)17)
#define FIT_CURL_EXERCISE_NAME_DUMBBELL_WRIST_CURL                               ((FIT_CURL_EXERCISE_NAME)18)
#define FIT_CURL_EXERCISE_NAME_EZ_BAR_PREACHER_CURL                              ((FIT_CURL_EXERCISE_NAME)19)
#define FIT_CURL_EXERCISE_NAME_FORWARD_BEND_BICEPS_CURL                          ((FIT_CURL_EXERCISE_NAME)20)
#define FIT_CURL_EXERCISE_NAME_HAMMER_CURL_TO_PRESS                              ((FIT_CURL_EXERCISE_NAME)21)
#define FIT_CURL_EXERCISE_NAME_INCLINE_DUMBBELL_BICEPS_CURL                      ((FIT_CURL_EXERCISE_NAME)22)
#define FIT_CURL_EXERCISE_NAME_INCLINE_OFFSET_THUMB_DUMBBELL_CURL                ((FIT_CURL_EXERCISE_NAME)23)
#define FIT_CURL_EXERCISE_NAME_KETTLEBELL_BICEPS_CURL                            ((FIT_CURL_EXERCISE_NAME)24)
#define FIT_CURL_EXERCISE_NAME_LYING_CONCENTRATION_CABLE_CURL                    ((FIT_CURL_EXERCISE_NAME)25)
#define FIT_CURL_EXERCISE_NAME_ONE_ARM_PREACHER_CURL                             ((FIT_CURL_EXERCISE_NAME)26)
#define FIT_CURL_EXERCISE_NAME_PLATE_PINCH_CURL                                  ((FIT_CURL_EXERCISE_NAME)27)
#define FIT_CURL_EXERCISE_NAME_PREACHER_CURL_WITH_CABLE                          ((FIT_CURL_EXERCISE_NAME)28)
#define FIT_CURL_EXERCISE_NAME_REVERSE_EZ_BAR_CURL                               ((FIT_CURL_EXERCISE_NAME)29)
#define FIT_CURL_EXERCISE_NAME_REVERSE_GRIP_WRIST_CURL                           ((FIT_CURL_EXERCISE_NAME)30)
#define FIT_CURL_EXERCISE_NAME_REVERSE_GRIP_BARBELL_BICEPS_CURL                  ((FIT_CURL_EXERCISE_NAME)31)
#define FIT_CURL_EXERCISE_NAME_SEATED_ALTERNATING_DUMBBELL_BICEPS_CURL           ((FIT_CURL_EXERCISE_NAME)32)
#define FIT_CURL_EXERCISE_NAME_SEATED_DUMBBELL_BICEPS_CURL                       ((FIT_CURL_EXERCISE_NAME)33)
#define FIT_CURL_EXERCISE_NAME_SEATED_REVERSE_DUMBBELL_CURL                      ((FIT_CURL_EXERCISE_NAME)34)
#define FIT_CURL_EXERCISE_NAME_SPLIT_STANCE_OFFSET_PINKY_DUMBBELL_CURL           ((FIT_CURL_EXERCISE_NAME)35)
#define FIT_CURL_EXERCISE_NAME_STANDING_ALTERNATING_DUMBBELL_CURLS               ((FIT_CURL_EXERCISE_NAME)36)
#define FIT_CURL_EXERCISE_NAME_STANDING_DUMBBELL_BICEPS_CURL                     ((FIT_CURL_EXERCISE_NAME)37)
#define FIT_CURL_EXERCISE_NAME_STANDING_EZ_BAR_BICEPS_CURL                       ((FIT_CURL_EXERCISE_NAME)38)
#define FIT_CURL_EXERCISE_NAME_STATIC_CURL                                       ((FIT_CURL_EXERCISE_NAME)39)
#define FIT_CURL_EXERCISE_NAME_SWISS_BALL_DUMBBELL_OVERHEAD_TRICEPS_EXTENSION    ((FIT_CURL_EXERCISE_NAME)40)
#define FIT_CURL_EXERCISE_NAME_SWISS_BALL_EZ_BAR_PREACHER_CURL                   ((FIT_CURL_EXERCISE_NAME)41)
#define FIT_CURL_EXERCISE_NAME_TWISTING_STANDING_DUMBBELL_BICEPS_CURL            ((FIT_CURL_EXERCISE_NAME)42)
#define FIT_CURL_EXERCISE_NAME_WIDE_GRIP_EZ_BAR_BICEPS_CURL                      ((FIT_CURL_EXERCISE_NAME)43)
#define FIT_CURL_EXERCISE_NAME_COUNT                                             44

typedef FIT_UINT16 FIT_DEADLIFT_EXERCISE_NAME;
#define FIT_DEADLIFT_EXERCISE_NAME_INVALID                                       FIT_UINT16_INVALID
#define FIT_DEADLIFT_EXERCISE_NAME_BARBELL_DEADLIFT                              ((FIT_DEADLIFT_EXERCISE_NAME)0)
#define FIT_DEADLIFT_EXERCISE_NAME_BARBELL_STRAIGHT_LEG_DEADLIFT                 ((FIT_DEADLIFT_EXERCISE_NAME)1)
#define FIT_DEADLIFT_EXERCISE_NAME_DUMBBELL_DEADLIFT                             ((FIT_DEADLIFT_EXERCISE_NAME)2)
#define FIT_DEADLIFT_EXERCISE_NAME_DUMBBELL_SINGLE_LEG_DEADLIFT_TO_ROW           ((FIT_DEADLIFT_EXERCISE_NAME)3)
#define FIT_DEADLIFT_EXERCISE_NAME_DUMBBELL_STRAIGHT_LEG_DEADLIFT                ((FIT_DEADLIFT_EXERCISE_NAME)4)
#define FIT_DEADLIFT_EXERCISE_NAME_KETTLEBELL_FLOOR_TO_SHELF                     ((FIT_DEADLIFT_EXERCISE_NAME)5)
#define FIT_DEADLIFT_EXERCISE_NAME_ONE_ARM_ONE_LEG_DEADLIFT                      ((FIT_DEADLIFT_EXERCISE_NAME)6)
#define FIT_DEADLIFT_EXERCISE_NAME_RACK_PULL                                     ((FIT_DEADLIFT_EXERCISE_NAME)7)
#define FIT_DEADLIFT_EXERCISE_NAME_ROTATIONAL_DUMBBELL_STRAIGHT_LEG_DEADLIFT     ((FIT_DEADLIFT_EXERCISE_NAME)8)
#define FIT_DEADLIFT_EXERCISE_NAME_SINGLE_ARM_DEADLIFT                           ((FIT_DEADLIFT_EXERCISE_NAME)9)
#define FIT_DEADLIFT_EXERCISE_NAME_SINGLE_LEG_BARBELL_DEADLIFT                   ((FIT_DEADLIFT_EXERCISE_NAME)10)
#define FIT_DEADLIFT_EXERCISE_NAME_SINGLE_LEG_BARBELL_STRAIGHT_LEG_DEADLIFT      ((FIT_DEADLIFT_EXERCISE_NAME)11)
#define FIT_DEADLIFT_EXERCISE_NAME_SINGLE_LEG_DEADLIFT_WITH_BARBELL              ((FIT_DEADLIFT_EXERCISE_NAME)12)
#define FIT_DEADLIFT_EXERCISE_NAME_SINGLE_LEG_RDL_CIRCUIT                        ((FIT_DEADLIFT_EXERCISE_NAME)13)
#define FIT_DEADLIFT_EXERCISE_NAME_SINGLE_LEG_ROMANIAN_DEADLIFT_WITH_DUMBBELL    ((FIT_DEADLIFT_EXERCISE_NAME)14)
#define FIT_DEADLIFT_EXERCISE_NAME_SUMO_DEADLIFT                                 ((FIT_DEADLIFT_EXERCISE_NAME)15)
#define FIT_DEADLIFT_EXERCISE_NAME_SUMO_DEADLIFT_HIGH_PULL                       ((FIT_DEADLIFT_EXERCISE_NAME)16)
#define FIT_DEADLIFT_EXERCISE_NAME_TRAP_BAR_DEADLIFT                             ((FIT_DEADLIFT_EXERCISE_NAME)17)
#define FIT_DEADLIFT_EXERCISE_NAME_WIDE_GRIP_BARBELL_DEADLIFT                    ((FIT_DEADLIFT_EXERCISE_NAME)18)
#define FIT_DEADLIFT_EXERCISE_NAME_COUNT                                         19

typedef FIT_UINT16 FIT_FLYE_EXERCISE_NAME;
#define FIT_FLYE_EXERCISE_NAME_INVALID                                           FIT_UINT16_INVALID
#define FIT_FLYE_EXERCISE_NAME_CABLE_CROSSOVER                                   ((FIT_FLYE_EXERCISE_NAME)0)
#define FIT_FLYE_EXERCISE_NAME_DECLINE_DUMBBELL_FLYE                             ((FIT_FLYE_EXERCISE_NAME)1)
#define FIT_FLYE_EXERCISE_NAME_DUMBBELL_FLYE                                     ((FIT_FLYE_EXERCISE_NAME)2)
#define FIT_FLYE_EXERCISE_NAME_INCLINE_DUMBBELL_FLYE                             ((FIT_FLYE_EXERCISE_NAME)3)
#define FIT_FLYE_EXERCISE_NAME_KETTLEBELL_FLYE                                   ((FIT_FLYE_EXERCISE_NAME)4)
#define FIT_FLYE_EXERCISE_NAME_KNEELING_REAR_FLYE                                ((FIT_FLYE_EXERCISE_NAME)5)
#define FIT_FLYE_EXERCISE_NAME_SINGLE_ARM_STANDING_CABLE_REVERSE_FLYE            ((FIT_FLYE_EXERCISE_NAME)6)
#define FIT_FLYE_EXERCISE_NAME_SWISS_BALL_DUMBBELL_FLYE                          ((FIT_FLYE_EXERCISE_NAME)7)
#define FIT_FLYE_EXERCISE_NAME_COUNT                                             8

typedef FIT_UINT16 FIT_HIP_RAISE_EXERCISE_NAME;
#define FIT_HIP_RAISE_EXERCISE_NAME_INVALID                                      FIT_UINT16_INVALID
#define FIT_HIP_RAISE_EXERCISE_NAME_BARBELL_HIP_THRUST_ON_FLOOR                  ((FIT_HIP_RAISE_EXERCISE_NAME)0)
#define FIT_HIP_RAISE_EXERCISE_NAME_BARBELL_HIP_THRUST_WITH_BENCH                ((FIT_HIP_RAISE_EXERCISE_NAME)1)
#define FIT_HIP_RAISE_EXERCISE_NAME_BENT_KNEE_SWISS_BALL_REVERSE_HIP_RAISE       ((FIT_HIP_RAISE_EXERCISE_NAME)2)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_BENT_KNEE_SWISS_BALL_REVERSE_HIP_RAISE ((FIT_HIP_RAISE_EXERCISE_NAME)3)
#define FIT_HIP_RAISE_EXERCISE_NAME_BRIDGE_WITH_LEG_EXTENSION                    ((FIT_HIP_RAISE_EXERCISE_NAME)4)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_BRIDGE_WITH_LEG_EXTENSION           ((FIT_HIP_RAISE_EXERCISE_NAME)5)
#define FIT_HIP_RAISE_EXERCISE_NAME_CLAM_BRIDGE                                  ((FIT_HIP_RAISE_EXERCISE_NAME)6)
#define FIT_HIP_RAISE_EXERCISE_NAME_FRONT_KICK_TABLETOP                          ((FIT_HIP_RAISE_EXERCISE_NAME)7)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_FRONT_KICK_TABLETOP                 ((FIT_HIP_RAISE_EXERCISE_NAME)8)
#define FIT_HIP_RAISE_EXERCISE_NAME_HIP_EXTENSION_AND_CROSS                      ((FIT_HIP_RAISE_EXERCISE_NAME)9)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_HIP_EXTENSION_AND_CROSS             ((FIT_HIP_RAISE_EXERCISE_NAME)10)
#define FIT_HIP_RAISE_EXERCISE_NAME_HIP_RAISE                                    ((FIT_HIP_RAISE_EXERCISE_NAME)11)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_HIP_RAISE                           ((FIT_HIP_RAISE_EXERCISE_NAME)12)
#define FIT_HIP_RAISE_EXERCISE_NAME_HIP_RAISE_WITH_FEET_ON_SWISS_BALL            ((FIT_HIP_RAISE_EXERCISE_NAME)13)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_HIP_RAISE_WITH_FEET_ON_SWISS_BALL   ((FIT_HIP_RAISE_EXERCISE_NAME)14)
#define FIT_HIP_RAISE_EXERCISE_NAME_HIP_RAISE_WITH_HEAD_ON_BOSU_BALL             ((FIT_HIP_RAISE_EXERCISE_NAME)15)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_HIP_RAISE_WITH_HEAD_ON_BOSU_BALL    ((FIT_HIP_RAISE_EXERCISE_NAME)16)
#define FIT_HIP_RAISE_EXERCISE_NAME_HIP_RAISE_WITH_HEAD_ON_SWISS_BALL            ((FIT_HIP_RAISE_EXERCISE_NAME)17)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_HIP_RAISE_WITH_HEAD_ON_SWISS_BALL   ((FIT_HIP_RAISE_EXERCISE_NAME)18)
#define FIT_HIP_RAISE_EXERCISE_NAME_HIP_RAISE_WITH_KNEE_SQUEEZE                  ((FIT_HIP_RAISE_EXERCISE_NAME)19)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_HIP_RAISE_WITH_KNEE_SQUEEZE         ((FIT_HIP_RAISE_EXERCISE_NAME)20)
#define FIT_HIP_RAISE_EXERCISE_NAME_INCLINE_REAR_LEG_EXTENSION                   ((FIT_HIP_RAISE_EXERCISE_NAME)21)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_INCLINE_REAR_LEG_EXTENSION          ((FIT_HIP_RAISE_EXERCISE_NAME)22)
#define FIT_HIP_RAISE_EXERCISE_NAME_KETTLEBELL_SWING                             ((FIT_HIP_RAISE_EXERCISE_NAME)23)
#define FIT_HIP_RAISE_EXERCISE_NAME_MARCHING_HIP_RAISE                           ((FIT_HIP_RAISE_EXERCISE_NAME)24)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_MARCHING_HIP_RAISE                  ((FIT_HIP_RAISE_EXERCISE_NAME)25)
#define FIT_HIP_RAISE_EXERCISE_NAME_MARCHING_HIP_RAISE_WITH_FEET_ON_A_SWISS_BALL ((FIT_HIP_RAISE_EXERCISE_NAME)26)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_MARCHING_HIP_RAISE_WITH_FEET_ON_A_SWISS_BALL ((FIT_HIP_RAISE_EXERCISE_NAME)27)
#define FIT_HIP_RAISE_EXERCISE_NAME_REVERSE_HIP_RAISE                            ((FIT_HIP_RAISE_EXERCISE_NAME)28)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_REVERSE_HIP_RAISE                   ((FIT_HIP_RAISE_EXERCISE_NAME)29)
#define FIT_HIP_RAISE_EXERCISE_NAME_SINGLE_LEG_HIP_RAISE                         ((FIT_HIP_RAISE_EXERCISE_NAME)30)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_HIP_RAISE                ((FIT_HIP_RAISE_EXERCISE_NAME)31)
#define FIT_HIP_RAISE_EXERCISE_NAME_SINGLE_LEG_HIP_RAISE_WITH_FOOT_ON_BENCH      ((FIT_HIP_RAISE_EXERCISE_NAME)32)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_HIP_RAISE_WITH_FOOT_ON_BENCH ((FIT_HIP_RAISE_EXERCISE_NAME)33)
#define FIT_HIP_RAISE_EXERCISE_NAME_SINGLE_LEG_HIP_RAISE_WITH_FOOT_ON_BOSU_BALL  ((FIT_HIP_RAISE_EXERCISE_NAME)34)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_HIP_RAISE_WITH_FOOT_ON_BOSU_BALL ((FIT_HIP_RAISE_EXERCISE_NAME)35)
#define FIT_HIP_RAISE_EXERCISE_NAME_SINGLE_LEG_HIP_RAISE_WITH_FOOT_ON_FOAM_ROLLER ((FIT_HIP_RAISE_EXERCISE_NAME)36)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_HIP_RAISE_WITH_FOOT_ON_FOAM_ROLLER ((FIT_HIP_RAISE_EXERCISE_NAME)37)
#define FIT_HIP_RAISE_EXERCISE_NAME_SINGLE_LEG_HIP_RAISE_WITH_FOOT_ON_MEDICINE_BALL ((FIT_HIP_RAISE_EXERCISE_NAME)38)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_HIP_RAISE_WITH_FOOT_ON_MEDICINE_BALL ((FIT_HIP_RAISE_EXERCISE_NAME)39)
#define FIT_HIP_RAISE_EXERCISE_NAME_SINGLE_LEG_HIP_RAISE_WITH_HEAD_ON_BOSU_BALL  ((FIT_HIP_RAISE_EXERCISE_NAME)40)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_HIP_RAISE_WITH_HEAD_ON_BOSU_BALL ((FIT_HIP_RAISE_EXERCISE_NAME)41)
#define FIT_HIP_RAISE_EXERCISE_NAME_WEIGHTED_CLAM_BRIDGE                         ((FIT_HIP_RAISE_EXERCISE_NAME)42)
#define FIT_HIP_RAISE_EXERCISE_NAME_COUNT                                        43

typedef FIT_UINT16 FIT_HIP_STABILITY_EXERCISE_NAME;
#define FIT_HIP_STABILITY_EXERCISE_NAME_INVALID                                  FIT_UINT16_INVALID
#define FIT_HIP_STABILITY_EXERCISE_NAME_BAND_SIDE_LYING_LEG_RAISE                ((FIT_HIP_STABILITY_EXERCISE_NAME)0)
#define FIT_HIP_STABILITY_EXERCISE_NAME_DEAD_BUG                                 ((FIT_HIP_STABILITY_EXERCISE_NAME)1)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_DEAD_BUG                        ((FIT_HIP_STABILITY_EXERCISE_NAME)2)
#define FIT_HIP_STABILITY_EXERCISE_NAME_EXTERNAL_HIP_RAISE                       ((FIT_HIP_STABILITY_EXERCISE_NAME)3)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_EXTERNAL_HIP_RAISE              ((FIT_HIP_STABILITY_EXERCISE_NAME)4)
#define FIT_HIP_STABILITY_EXERCISE_NAME_FIRE_HYDRANT_KICKS                       ((FIT_HIP_STABILITY_EXERCISE_NAME)5)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_FIRE_HYDRANT_KICKS              ((FIT_HIP_STABILITY_EXERCISE_NAME)6)
#define FIT_HIP_STABILITY_EXERCISE_NAME_HIP_CIRCLES                              ((FIT_HIP_STABILITY_EXERCISE_NAME)7)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_HIP_CIRCLES                     ((FIT_HIP_STABILITY_EXERCISE_NAME)8)
#define FIT_HIP_STABILITY_EXERCISE_NAME_INNER_THIGH_LIFT                         ((FIT_HIP_STABILITY_EXERCISE_NAME)9)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_INNER_THIGH_LIFT                ((FIT_HIP_STABILITY_EXERCISE_NAME)10)
#define FIT_HIP_STABILITY_EXERCISE_NAME_LATERAL_WALKS_WITH_BAND_AT_ANKLES        ((FIT_HIP_STABILITY_EXERCISE_NAME)11)
#define FIT_HIP_STABILITY_EXERCISE_NAME_PRETZEL_SIDE_KICK                        ((FIT_HIP_STABILITY_EXERCISE_NAME)12)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_PRETZEL_SIDE_KICK               ((FIT_HIP_STABILITY_EXERCISE_NAME)13)
#define FIT_HIP_STABILITY_EXERCISE_NAME_PRONE_HIP_INTERNAL_ROTATION              ((FIT_HIP_STABILITY_EXERCISE_NAME)14)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_PRONE_HIP_INTERNAL_ROTATION     ((FIT_HIP_STABILITY_EXERCISE_NAME)15)
#define FIT_HIP_STABILITY_EXERCISE_NAME_QUADRUPED                                ((FIT_HIP_STABILITY_EXERCISE_NAME)16)
#define FIT_HIP_STABILITY_EXERCISE_NAME_QUADRUPED_HIP_EXTENSION                  ((FIT_HIP_STABILITY_EXERCISE_NAME)17)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_QUADRUPED_HIP_EXTENSION         ((FIT_HIP_STABILITY_EXERCISE_NAME)18)
#define FIT_HIP_STABILITY_EXERCISE_NAME_QUADRUPED_WITH_LEG_LIFT                  ((FIT_HIP_STABILITY_EXERCISE_NAME)19)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_QUADRUPED_WITH_LEG_LIFT         ((FIT_HIP_STABILITY_EXERCISE_NAME)20)
#define FIT_HIP_STABILITY_EXERCISE_NAME_SIDE_LYING_LEG_RAISE                     ((FIT_HIP_STABILITY_EXERCISE_NAME)21)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_SIDE_LYING_LEG_RAISE            ((FIT_HIP_STABILITY_EXERCISE_NAME)22)
#define FIT_HIP_STABILITY_EXERCISE_NAME_SLIDING_HIP_ADDUCTION                    ((FIT_HIP_STABILITY_EXERCISE_NAME)23)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_SLIDING_HIP_ADDUCTION           ((FIT_HIP_STABILITY_EXERCISE_NAME)24)
#define FIT_HIP_STABILITY_EXERCISE_NAME_STANDING_ADDUCTION                       ((FIT_HIP_STABILITY_EXERCISE_NAME)25)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_STANDING_ADDUCTION              ((FIT_HIP_STABILITY_EXERCISE_NAME)26)
#define FIT_HIP_STABILITY_EXERCISE_NAME_STANDING_CABLE_HIP_ABDUCTION             ((FIT_HIP_STABILITY_EXERCISE_NAME)27)
#define FIT_HIP_STABILITY_EXERCISE_NAME_STANDING_HIP_ABDUCTION                   ((FIT_HIP_STABILITY_EXERCISE_NAME)28)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_STANDING_HIP_ABDUCTION          ((FIT_HIP_STABILITY_EXERCISE_NAME)29)
#define FIT_HIP_STABILITY_EXERCISE_NAME_STANDING_REAR_LEG_RAISE                  ((FIT_HIP_STABILITY_EXERCISE_NAME)30)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_STANDING_REAR_LEG_RAISE         ((FIT_HIP_STABILITY_EXERCISE_NAME)31)
#define FIT_HIP_STABILITY_EXERCISE_NAME_SUPINE_HIP_INTERNAL_ROTATION             ((FIT_HIP_STABILITY_EXERCISE_NAME)32)
#define FIT_HIP_STABILITY_EXERCISE_NAME_WEIGHTED_SUPINE_HIP_INTERNAL_ROTATION    ((FIT_HIP_STABILITY_EXERCISE_NAME)33)
#define FIT_HIP_STABILITY_EXERCISE_NAME_COUNT                                    34

typedef FIT_UINT16 FIT_HIP_SWING_EXERCISE_NAME;
#define FIT_HIP_SWING_EXERCISE_NAME_INVALID                                      FIT_UINT16_INVALID
#define FIT_HIP_SWING_EXERCISE_NAME_SINGLE_ARM_KETTLEBELL_SWING                  ((FIT_HIP_SWING_EXERCISE_NAME)0)
#define FIT_HIP_SWING_EXERCISE_NAME_SINGLE_ARM_DUMBBELL_SWING                    ((FIT_HIP_SWING_EXERCISE_NAME)1)
#define FIT_HIP_SWING_EXERCISE_NAME_STEP_OUT_SWING                               ((FIT_HIP_SWING_EXERCISE_NAME)2)
#define FIT_HIP_SWING_EXERCISE_NAME_COUNT                                        3

typedef FIT_UINT16 FIT_HYPEREXTENSION_EXERCISE_NAME;
#define FIT_HYPEREXTENSION_EXERCISE_NAME_INVALID                                 FIT_UINT16_INVALID
#define FIT_HYPEREXTENSION_EXERCISE_NAME_BACK_EXTENSION_WITH_OPPOSITE_ARM_AND_LEG_REACH ((FIT_HYPEREXTENSION_EXERCISE_NAME)0)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_BACK_EXTENSION_WITH_OPPOSITE_ARM_AND_LEG_REACH ((FIT_HYPEREXTENSION_EXERCISE_NAME)1)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_BASE_ROTATIONS                          ((FIT_HYPEREXTENSION_EXERCISE_NAME)2)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_BASE_ROTATIONS                 ((FIT_HYPEREXTENSION_EXERCISE_NAME)3)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_BENT_KNEE_REVERSE_HYPEREXTENSION        ((FIT_HYPEREXTENSION_EXERCISE_NAME)4)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_BENT_KNEE_REVERSE_HYPEREXTENSION ((FIT_HYPEREXTENSION_EXERCISE_NAME)5)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_HOLLOW_HOLD_AND_ROLL                    ((FIT_HYPEREXTENSION_EXERCISE_NAME)6)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_HOLLOW_HOLD_AND_ROLL           ((FIT_HYPEREXTENSION_EXERCISE_NAME)7)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_KICKS                                   ((FIT_HYPEREXTENSION_EXERCISE_NAME)8)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_KICKS                          ((FIT_HYPEREXTENSION_EXERCISE_NAME)9)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_KNEE_RAISES                             ((FIT_HYPEREXTENSION_EXERCISE_NAME)10)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_KNEE_RAISES                    ((FIT_HYPEREXTENSION_EXERCISE_NAME)11)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_KNEELING_SUPERMAN                       ((FIT_HYPEREXTENSION_EXERCISE_NAME)12)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_KNEELING_SUPERMAN              ((FIT_HYPEREXTENSION_EXERCISE_NAME)13)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_LAT_PULL_DOWN_WITH_ROW                  ((FIT_HYPEREXTENSION_EXERCISE_NAME)14)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_MEDICINE_BALL_DEADLIFT_TO_REACH         ((FIT_HYPEREXTENSION_EXERCISE_NAME)15)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_ONE_ARM_ONE_LEG_ROW                     ((FIT_HYPEREXTENSION_EXERCISE_NAME)16)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_ONE_ARM_ROW_WITH_BAND                   ((FIT_HYPEREXTENSION_EXERCISE_NAME)17)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_OVERHEAD_LUNGE_WITH_MEDICINE_BALL       ((FIT_HYPEREXTENSION_EXERCISE_NAME)18)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_PLANK_KNEE_TUCKS                        ((FIT_HYPEREXTENSION_EXERCISE_NAME)19)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_PLANK_KNEE_TUCKS               ((FIT_HYPEREXTENSION_EXERCISE_NAME)20)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_SIDE_STEP                               ((FIT_HYPEREXTENSION_EXERCISE_NAME)21)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_SIDE_STEP                      ((FIT_HYPEREXTENSION_EXERCISE_NAME)22)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_SINGLE_LEG_BACK_EXTENSION               ((FIT_HYPEREXTENSION_EXERCISE_NAME)23)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_BACK_EXTENSION      ((FIT_HYPEREXTENSION_EXERCISE_NAME)24)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_SPINE_EXTENSION                         ((FIT_HYPEREXTENSION_EXERCISE_NAME)25)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_SPINE_EXTENSION                ((FIT_HYPEREXTENSION_EXERCISE_NAME)26)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_STATIC_BACK_EXTENSION                   ((FIT_HYPEREXTENSION_EXERCISE_NAME)27)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_STATIC_BACK_EXTENSION          ((FIT_HYPEREXTENSION_EXERCISE_NAME)28)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_SUPERMAN_FROM_FLOOR                     ((FIT_HYPEREXTENSION_EXERCISE_NAME)29)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_SUPERMAN_FROM_FLOOR            ((FIT_HYPEREXTENSION_EXERCISE_NAME)30)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_SWISS_BALL_BACK_EXTENSION               ((FIT_HYPEREXTENSION_EXERCISE_NAME)31)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_SWISS_BALL_BACK_EXTENSION      ((FIT_HYPEREXTENSION_EXERCISE_NAME)32)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_SWISS_BALL_HYPEREXTENSION               ((FIT_HYPEREXTENSION_EXERCISE_NAME)33)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_SWISS_BALL_HYPEREXTENSION      ((FIT_HYPEREXTENSION_EXERCISE_NAME)34)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_SWISS_BALL_OPPOSITE_ARM_AND_LEG_LIFT    ((FIT_HYPEREXTENSION_EXERCISE_NAME)35)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_WEIGHTED_SWISS_BALL_OPPOSITE_ARM_AND_LEG_LIFT ((FIT_HYPEREXTENSION_EXERCISE_NAME)36)
#define FIT_HYPEREXTENSION_EXERCISE_NAME_COUNT                                   37

typedef FIT_UINT16 FIT_LATERAL_RAISE_EXERCISE_NAME;
#define FIT_LATERAL_RAISE_EXERCISE_NAME_INVALID                                  FIT_UINT16_INVALID
#define FIT_LATERAL_RAISE_EXERCISE_NAME_45_DEGREE_CABLE_EXTERNAL_ROTATION        ((FIT_LATERAL_RAISE_EXERCISE_NAME)0)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_ALTERNATING_LATERAL_RAISE_WITH_STATIC_HOLD ((FIT_LATERAL_RAISE_EXERCISE_NAME)1)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_BAR_MUSCLE_UP                            ((FIT_LATERAL_RAISE_EXERCISE_NAME)2)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_BENT_OVER_LATERAL_RAISE                  ((FIT_LATERAL_RAISE_EXERCISE_NAME)3)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_CABLE_DIAGONAL_RAISE                     ((FIT_LATERAL_RAISE_EXERCISE_NAME)4)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_CABLE_FRONT_RAISE                        ((FIT_LATERAL_RAISE_EXERCISE_NAME)5)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_CALORIE_ROW                              ((FIT_LATERAL_RAISE_EXERCISE_NAME)6)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_COMBO_SHOULDER_RAISE                     ((FIT_LATERAL_RAISE_EXERCISE_NAME)7)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_DUMBBELL_DIAGONAL_RAISE                  ((FIT_LATERAL_RAISE_EXERCISE_NAME)8)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_DUMBBELL_V_RAISE                         ((FIT_LATERAL_RAISE_EXERCISE_NAME)9)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_FRONT_RAISE                              ((FIT_LATERAL_RAISE_EXERCISE_NAME)10)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_LEANING_DUMBBELL_LATERAL_RAISE           ((FIT_LATERAL_RAISE_EXERCISE_NAME)11)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_LYING_DUMBBELL_RAISE                     ((FIT_LATERAL_RAISE_EXERCISE_NAME)12)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_MUSCLE_UP                                ((FIT_LATERAL_RAISE_EXERCISE_NAME)13)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_ONE_ARM_CABLE_LATERAL_RAISE              ((FIT_LATERAL_RAISE_EXERCISE_NAME)14)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_OVERHAND_GRIP_REAR_LATERAL_RAISE         ((FIT_LATERAL_RAISE_EXERCISE_NAME)15)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_PLATE_RAISES                             ((FIT_LATERAL_RAISE_EXERCISE_NAME)16)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_RING_DIP                                 ((FIT_LATERAL_RAISE_EXERCISE_NAME)17)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_WEIGHTED_RING_DIP                        ((FIT_LATERAL_RAISE_EXERCISE_NAME)18)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_RING_MUSCLE_UP                           ((FIT_LATERAL_RAISE_EXERCISE_NAME)19)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_WEIGHTED_RING_MUSCLE_UP                  ((FIT_LATERAL_RAISE_EXERCISE_NAME)20)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_ROPE_CLIMB                               ((FIT_LATERAL_RAISE_EXERCISE_NAME)21)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_WEIGHTED_ROPE_CLIMB                      ((FIT_LATERAL_RAISE_EXERCISE_NAME)22)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_SCAPTION                                 ((FIT_LATERAL_RAISE_EXERCISE_NAME)23)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_SEATED_LATERAL_RAISE                     ((FIT_LATERAL_RAISE_EXERCISE_NAME)24)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_SEATED_REAR_LATERAL_RAISE                ((FIT_LATERAL_RAISE_EXERCISE_NAME)25)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_SIDE_LYING_LATERAL_RAISE                 ((FIT_LATERAL_RAISE_EXERCISE_NAME)26)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_STANDING_LIFT                            ((FIT_LATERAL_RAISE_EXERCISE_NAME)27)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_SUSPENDED_ROW                            ((FIT_LATERAL_RAISE_EXERCISE_NAME)28)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_UNDERHAND_GRIP_REAR_LATERAL_RAISE        ((FIT_LATERAL_RAISE_EXERCISE_NAME)29)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_WALL_SLIDE                               ((FIT_LATERAL_RAISE_EXERCISE_NAME)30)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_WEIGHTED_WALL_SLIDE                      ((FIT_LATERAL_RAISE_EXERCISE_NAME)31)
#define FIT_LATERAL_RAISE_EXERCISE_NAME_COUNT                                    32

typedef FIT_UINT16 FIT_LEG_CURL_EXERCISE_NAME;
#define FIT_LEG_CURL_EXERCISE_NAME_INVALID                                       FIT_UINT16_INVALID
#define FIT_LEG_CURL_EXERCISE_NAME_LEG_CURL                                      ((FIT_LEG_CURL_EXERCISE_NAME)0)
#define FIT_LEG_CURL_EXERCISE_NAME_WEIGHTED_LEG_CURL                             ((FIT_LEG_CURL_EXERCISE_NAME)1)
#define FIT_LEG_CURL_EXERCISE_NAME_GOOD_MORNING                                  ((FIT_LEG_CURL_EXERCISE_NAME)2)
#define FIT_LEG_CURL_EXERCISE_NAME_SEATED_BARBELL_GOOD_MORNING                   ((FIT_LEG_CURL_EXERCISE_NAME)3)
#define FIT_LEG_CURL_EXERCISE_NAME_SINGLE_LEG_BARBELL_GOOD_MORNING               ((FIT_LEG_CURL_EXERCISE_NAME)4)
#define FIT_LEG_CURL_EXERCISE_NAME_SINGLE_LEG_SLIDING_LEG_CURL                   ((FIT_LEG_CURL_EXERCISE_NAME)5)
#define FIT_LEG_CURL_EXERCISE_NAME_SLIDING_LEG_CURL                              ((FIT_LEG_CURL_EXERCISE_NAME)6)
#define FIT_LEG_CURL_EXERCISE_NAME_SPLIT_BARBELL_GOOD_MORNING                    ((FIT_LEG_CURL_EXERCISE_NAME)7)
#define FIT_LEG_CURL_EXERCISE_NAME_SPLIT_STANCE_EXTENSION                        ((FIT_LEG_CURL_EXERCISE_NAME)8)
#define FIT_LEG_CURL_EXERCISE_NAME_STAGGERED_STANCE_GOOD_MORNING                 ((FIT_LEG_CURL_EXERCISE_NAME)9)
#define FIT_LEG_CURL_EXERCISE_NAME_SWISS_BALL_HIP_RAISE_AND_LEG_CURL             ((FIT_LEG_CURL_EXERCISE_NAME)10)
#define FIT_LEG_CURL_EXERCISE_NAME_ZERCHER_GOOD_MORNING                          ((FIT_LEG_CURL_EXERCISE_NAME)11)
#define FIT_LEG_CURL_EXERCISE_NAME_COUNT                                         12

typedef FIT_UINT16 FIT_LEG_RAISE_EXERCISE_NAME;
#define FIT_LEG_RAISE_EXERCISE_NAME_INVALID                                      FIT_UINT16_INVALID
#define FIT_LEG_RAISE_EXERCISE_NAME_HANGING_KNEE_RAISE                           ((FIT_LEG_RAISE_EXERCISE_NAME)0)
#define FIT_LEG_RAISE_EXERCISE_NAME_HANGING_LEG_RAISE                            ((FIT_LEG_RAISE_EXERCISE_NAME)1)
#define FIT_LEG_RAISE_EXERCISE_NAME_WEIGHTED_HANGING_LEG_RAISE                   ((FIT_LEG_RAISE_EXERCISE_NAME)2)
#define FIT_LEG_RAISE_EXERCISE_NAME_HANGING_SINGLE_LEG_RAISE                     ((FIT_LEG_RAISE_EXERCISE_NAME)3)
#define FIT_LEG_RAISE_EXERCISE_NAME_WEIGHTED_HANGING_SINGLE_LEG_RAISE            ((FIT_LEG_RAISE_EXERCISE_NAME)4)
#define FIT_LEG_RAISE_EXERCISE_NAME_KETTLEBELL_LEG_RAISES                        ((FIT_LEG_RAISE_EXERCISE_NAME)5)
#define FIT_LEG_RAISE_EXERCISE_NAME_LEG_LOWERING_DRILL                           ((FIT_LEG_RAISE_EXERCISE_NAME)6)
#define FIT_LEG_RAISE_EXERCISE_NAME_WEIGHTED_LEG_LOWERING_DRILL                  ((FIT_LEG_RAISE_EXERCISE_NAME)7)
#define FIT_LEG_RAISE_EXERCISE_NAME_LYING_STRAIGHT_LEG_RAISE                     ((FIT_LEG_RAISE_EXERCISE_NAME)8)
#define FIT_LEG_RAISE_EXERCISE_NAME_WEIGHTED_LYING_STRAIGHT_LEG_RAISE            ((FIT_LEG_RAISE_EXERCISE_NAME)9)
#define FIT_LEG_RAISE_EXERCISE_NAME_MEDICINE_BALL_LEG_DROPS                      ((FIT_LEG_RAISE_EXERCISE_NAME)10)
#define FIT_LEG_RAISE_EXERCISE_NAME_QUADRUPED_LEG_RAISE                          ((FIT_LEG_RAISE_EXERCISE_NAME)11)
#define FIT_LEG_RAISE_EXERCISE_NAME_WEIGHTED_QUADRUPED_LEG_RAISE                 ((FIT_LEG_RAISE_EXERCISE_NAME)12)
#define FIT_LEG_RAISE_EXERCISE_NAME_REVERSE_LEG_RAISE                            ((FIT_LEG_RAISE_EXERCISE_NAME)13)
#define FIT_LEG_RAISE_EXERCISE_NAME_WEIGHTED_REVERSE_LEG_RAISE                   ((FIT_LEG_RAISE_EXERCISE_NAME)14)
#define FIT_LEG_RAISE_EXERCISE_NAME_REVERSE_LEG_RAISE_ON_SWISS_BALL              ((FIT_LEG_RAISE_EXERCISE_NAME)15)
#define FIT_LEG_RAISE_EXERCISE_NAME_WEIGHTED_REVERSE_LEG_RAISE_ON_SWISS_BALL     ((FIT_LEG_RAISE_EXERCISE_NAME)16)
#define FIT_LEG_RAISE_EXERCISE_NAME_SINGLE_LEG_LOWERING_DRILL                    ((FIT_LEG_RAISE_EXERCISE_NAME)17)
#define FIT_LEG_RAISE_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_LOWERING_DRILL           ((FIT_LEG_RAISE_EXERCISE_NAME)18)
#define FIT_LEG_RAISE_EXERCISE_NAME_WEIGHTED_HANGING_KNEE_RAISE                  ((FIT_LEG_RAISE_EXERCISE_NAME)19)
#define FIT_LEG_RAISE_EXERCISE_NAME_LATERAL_STEPOVER                             ((FIT_LEG_RAISE_EXERCISE_NAME)20)
#define FIT_LEG_RAISE_EXERCISE_NAME_WEIGHTED_LATERAL_STEPOVER                    ((FIT_LEG_RAISE_EXERCISE_NAME)21)
#define FIT_LEG_RAISE_EXERCISE_NAME_COUNT                                        22

typedef FIT_UINT16 FIT_LUNGE_EXERCISE_NAME;
#define FIT_LUNGE_EXERCISE_NAME_INVALID                                          FIT_UINT16_INVALID
#define FIT_LUNGE_EXERCISE_NAME_OVERHEAD_LUNGE                                   ((FIT_LUNGE_EXERCISE_NAME)0)
#define FIT_LUNGE_EXERCISE_NAME_LUNGE_MATRIX                                     ((FIT_LUNGE_EXERCISE_NAME)1)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_LUNGE_MATRIX                            ((FIT_LUNGE_EXERCISE_NAME)2)
#define FIT_LUNGE_EXERCISE_NAME_ALTERNATING_BARBELL_FORWARD_LUNGE                ((FIT_LUNGE_EXERCISE_NAME)3)
#define FIT_LUNGE_EXERCISE_NAME_ALTERNATING_DUMBBELL_LUNGE_WITH_REACH            ((FIT_LUNGE_EXERCISE_NAME)4)
#define FIT_LUNGE_EXERCISE_NAME_BACK_FOOT_ELEVATED_DUMBBELL_SPLIT_SQUAT          ((FIT_LUNGE_EXERCISE_NAME)5)
#define FIT_LUNGE_EXERCISE_NAME_BARBELL_BOX_LUNGE                                ((FIT_LUNGE_EXERCISE_NAME)6)
#define FIT_LUNGE_EXERCISE_NAME_BARBELL_BULGARIAN_SPLIT_SQUAT                    ((FIT_LUNGE_EXERCISE_NAME)7)
#define FIT_LUNGE_EXERCISE_NAME_BARBELL_CROSSOVER_LUNGE                          ((FIT_LUNGE_EXERCISE_NAME)8)
#define FIT_LUNGE_EXERCISE_NAME_BARBELL_FRONT_SPLIT_SQUAT                        ((FIT_LUNGE_EXERCISE_NAME)9)
#define FIT_LUNGE_EXERCISE_NAME_BARBELL_LUNGE                                    ((FIT_LUNGE_EXERCISE_NAME)10)
#define FIT_LUNGE_EXERCISE_NAME_BARBELL_REVERSE_LUNGE                            ((FIT_LUNGE_EXERCISE_NAME)11)
#define FIT_LUNGE_EXERCISE_NAME_BARBELL_SIDE_LUNGE                               ((FIT_LUNGE_EXERCISE_NAME)12)
#define FIT_LUNGE_EXERCISE_NAME_BARBELL_SPLIT_SQUAT                              ((FIT_LUNGE_EXERCISE_NAME)13)
#define FIT_LUNGE_EXERCISE_NAME_CORE_CONTROL_REAR_LUNGE                          ((FIT_LUNGE_EXERCISE_NAME)14)
#define FIT_LUNGE_EXERCISE_NAME_DIAGONAL_LUNGE                                   ((FIT_LUNGE_EXERCISE_NAME)15)
#define FIT_LUNGE_EXERCISE_NAME_DROP_LUNGE                                       ((FIT_LUNGE_EXERCISE_NAME)16)
#define FIT_LUNGE_EXERCISE_NAME_DUMBBELL_BOX_LUNGE                               ((FIT_LUNGE_EXERCISE_NAME)17)
#define FIT_LUNGE_EXERCISE_NAME_DUMBBELL_BULGARIAN_SPLIT_SQUAT                   ((FIT_LUNGE_EXERCISE_NAME)18)
#define FIT_LUNGE_EXERCISE_NAME_DUMBBELL_CROSSOVER_LUNGE                         ((FIT_LUNGE_EXERCISE_NAME)19)
#define FIT_LUNGE_EXERCISE_NAME_DUMBBELL_DIAGONAL_LUNGE                          ((FIT_LUNGE_EXERCISE_NAME)20)
#define FIT_LUNGE_EXERCISE_NAME_DUMBBELL_LUNGE                                   ((FIT_LUNGE_EXERCISE_NAME)21)
#define FIT_LUNGE_EXERCISE_NAME_DUMBBELL_LUNGE_AND_ROTATION                      ((FIT_LUNGE_EXERCISE_NAME)22)
#define FIT_LUNGE_EXERCISE_NAME_DUMBBELL_OVERHEAD_BULGARIAN_SPLIT_SQUAT          ((FIT_LUNGE_EXERCISE_NAME)23)
#define FIT_LUNGE_EXERCISE_NAME_DUMBBELL_REVERSE_LUNGE_TO_HIGH_KNEE_AND_PRESS    ((FIT_LUNGE_EXERCISE_NAME)24)
#define FIT_LUNGE_EXERCISE_NAME_DUMBBELL_SIDE_LUNGE                              ((FIT_LUNGE_EXERCISE_NAME)25)
#define FIT_LUNGE_EXERCISE_NAME_ELEVATED_FRONT_FOOT_BARBELL_SPLIT_SQUAT          ((FIT_LUNGE_EXERCISE_NAME)26)
#define FIT_LUNGE_EXERCISE_NAME_FRONT_FOOT_ELEVATED_DUMBBELL_SPLIT_SQUAT         ((FIT_LUNGE_EXERCISE_NAME)27)
#define FIT_LUNGE_EXERCISE_NAME_GUNSLINGER_LUNGE                                 ((FIT_LUNGE_EXERCISE_NAME)28)
#define FIT_LUNGE_EXERCISE_NAME_LAWNMOWER_LUNGE                                  ((FIT_LUNGE_EXERCISE_NAME)29)
#define FIT_LUNGE_EXERCISE_NAME_LOW_LUNGE_WITH_ISOMETRIC_ADDUCTION               ((FIT_LUNGE_EXERCISE_NAME)30)
#define FIT_LUNGE_EXERCISE_NAME_LOW_SIDE_TO_SIDE_LUNGE                           ((FIT_LUNGE_EXERCISE_NAME)31)
#define FIT_LUNGE_EXERCISE_NAME_LUNGE                                            ((FIT_LUNGE_EXERCISE_NAME)32)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_LUNGE                                   ((FIT_LUNGE_EXERCISE_NAME)33)
#define FIT_LUNGE_EXERCISE_NAME_LUNGE_WITH_ARM_REACH                             ((FIT_LUNGE_EXERCISE_NAME)34)
#define FIT_LUNGE_EXERCISE_NAME_LUNGE_WITH_DIAGONAL_REACH                        ((FIT_LUNGE_EXERCISE_NAME)35)
#define FIT_LUNGE_EXERCISE_NAME_LUNGE_WITH_SIDE_BEND                             ((FIT_LUNGE_EXERCISE_NAME)36)
#define FIT_LUNGE_EXERCISE_NAME_OFFSET_DUMBBELL_LUNGE                            ((FIT_LUNGE_EXERCISE_NAME)37)
#define FIT_LUNGE_EXERCISE_NAME_OFFSET_DUMBBELL_REVERSE_LUNGE                    ((FIT_LUNGE_EXERCISE_NAME)38)
#define FIT_LUNGE_EXERCISE_NAME_OVERHEAD_BULGARIAN_SPLIT_SQUAT                   ((FIT_LUNGE_EXERCISE_NAME)39)
#define FIT_LUNGE_EXERCISE_NAME_OVERHEAD_DUMBBELL_REVERSE_LUNGE                  ((FIT_LUNGE_EXERCISE_NAME)40)
#define FIT_LUNGE_EXERCISE_NAME_OVERHEAD_DUMBBELL_SPLIT_SQUAT                    ((FIT_LUNGE_EXERCISE_NAME)41)
#define FIT_LUNGE_EXERCISE_NAME_OVERHEAD_LUNGE_WITH_ROTATION                     ((FIT_LUNGE_EXERCISE_NAME)42)
#define FIT_LUNGE_EXERCISE_NAME_REVERSE_BARBELL_BOX_LUNGE                        ((FIT_LUNGE_EXERCISE_NAME)43)
#define FIT_LUNGE_EXERCISE_NAME_REVERSE_BOX_LUNGE                                ((FIT_LUNGE_EXERCISE_NAME)44)
#define FIT_LUNGE_EXERCISE_NAME_REVERSE_DUMBBELL_BOX_LUNGE                       ((FIT_LUNGE_EXERCISE_NAME)45)
#define FIT_LUNGE_EXERCISE_NAME_REVERSE_DUMBBELL_CROSSOVER_LUNGE                 ((FIT_LUNGE_EXERCISE_NAME)46)
#define FIT_LUNGE_EXERCISE_NAME_REVERSE_DUMBBELL_DIAGONAL_LUNGE                  ((FIT_LUNGE_EXERCISE_NAME)47)
#define FIT_LUNGE_EXERCISE_NAME_REVERSE_LUNGE_WITH_REACH_BACK                    ((FIT_LUNGE_EXERCISE_NAME)48)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_REVERSE_LUNGE_WITH_REACH_BACK           ((FIT_LUNGE_EXERCISE_NAME)49)
#define FIT_LUNGE_EXERCISE_NAME_REVERSE_LUNGE_WITH_TWIST_AND_OVERHEAD_REACH      ((FIT_LUNGE_EXERCISE_NAME)50)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_REVERSE_LUNGE_WITH_TWIST_AND_OVERHEAD_REACH ((FIT_LUNGE_EXERCISE_NAME)51)
#define FIT_LUNGE_EXERCISE_NAME_REVERSE_SLIDING_BOX_LUNGE                        ((FIT_LUNGE_EXERCISE_NAME)52)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_REVERSE_SLIDING_BOX_LUNGE               ((FIT_LUNGE_EXERCISE_NAME)53)
#define FIT_LUNGE_EXERCISE_NAME_REVERSE_SLIDING_LUNGE                            ((FIT_LUNGE_EXERCISE_NAME)54)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_REVERSE_SLIDING_LUNGE                   ((FIT_LUNGE_EXERCISE_NAME)55)
#define FIT_LUNGE_EXERCISE_NAME_RUNNERS_LUNGE_TO_BALANCE                         ((FIT_LUNGE_EXERCISE_NAME)56)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_RUNNERS_LUNGE_TO_BALANCE                ((FIT_LUNGE_EXERCISE_NAME)57)
#define FIT_LUNGE_EXERCISE_NAME_SHIFTING_SIDE_LUNGE                              ((FIT_LUNGE_EXERCISE_NAME)58)
#define FIT_LUNGE_EXERCISE_NAME_SIDE_AND_CROSSOVER_LUNGE                         ((FIT_LUNGE_EXERCISE_NAME)59)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_SIDE_AND_CROSSOVER_LUNGE                ((FIT_LUNGE_EXERCISE_NAME)60)
#define FIT_LUNGE_EXERCISE_NAME_SIDE_LUNGE                                       ((FIT_LUNGE_EXERCISE_NAME)61)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_SIDE_LUNGE                              ((FIT_LUNGE_EXERCISE_NAME)62)
#define FIT_LUNGE_EXERCISE_NAME_SIDE_LUNGE_AND_PRESS                             ((FIT_LUNGE_EXERCISE_NAME)63)
#define FIT_LUNGE_EXERCISE_NAME_SIDE_LUNGE_JUMP_OFF                              ((FIT_LUNGE_EXERCISE_NAME)64)
#define FIT_LUNGE_EXERCISE_NAME_SIDE_LUNGE_SWEEP                                 ((FIT_LUNGE_EXERCISE_NAME)65)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_SIDE_LUNGE_SWEEP                        ((FIT_LUNGE_EXERCISE_NAME)66)
#define FIT_LUNGE_EXERCISE_NAME_SIDE_LUNGE_TO_CROSSOVER_TAP                      ((FIT_LUNGE_EXERCISE_NAME)67)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_SIDE_LUNGE_TO_CROSSOVER_TAP             ((FIT_LUNGE_EXERCISE_NAME)68)
#define FIT_LUNGE_EXERCISE_NAME_SIDE_TO_SIDE_LUNGE_CHOPS                         ((FIT_LUNGE_EXERCISE_NAME)69)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_SIDE_TO_SIDE_LUNGE_CHOPS                ((FIT_LUNGE_EXERCISE_NAME)70)
#define FIT_LUNGE_EXERCISE_NAME_SIFF_JUMP_LUNGE                                  ((FIT_LUNGE_EXERCISE_NAME)71)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_SIFF_JUMP_LUNGE                         ((FIT_LUNGE_EXERCISE_NAME)72)
#define FIT_LUNGE_EXERCISE_NAME_SINGLE_ARM_REVERSE_LUNGE_AND_PRESS               ((FIT_LUNGE_EXERCISE_NAME)73)
#define FIT_LUNGE_EXERCISE_NAME_SLIDING_LATERAL_LUNGE                            ((FIT_LUNGE_EXERCISE_NAME)74)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_SLIDING_LATERAL_LUNGE                   ((FIT_LUNGE_EXERCISE_NAME)75)
#define FIT_LUNGE_EXERCISE_NAME_WALKING_BARBELL_LUNGE                            ((FIT_LUNGE_EXERCISE_NAME)76)
#define FIT_LUNGE_EXERCISE_NAME_WALKING_DUMBBELL_LUNGE                           ((FIT_LUNGE_EXERCISE_NAME)77)
#define FIT_LUNGE_EXERCISE_NAME_WALKING_LUNGE                                    ((FIT_LUNGE_EXERCISE_NAME)78)
#define FIT_LUNGE_EXERCISE_NAME_WEIGHTED_WALKING_LUNGE                           ((FIT_LUNGE_EXERCISE_NAME)79)
#define FIT_LUNGE_EXERCISE_NAME_WIDE_GRIP_OVERHEAD_BARBELL_SPLIT_SQUAT           ((FIT_LUNGE_EXERCISE_NAME)80)
#define FIT_LUNGE_EXERCISE_NAME_COUNT                                            81

typedef FIT_UINT16 FIT_OLYMPIC_LIFT_EXERCISE_NAME;
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_INVALID                                   FIT_UINT16_INVALID
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_BARBELL_HANG_POWER_CLEAN                  ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)0)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_BARBELL_HANG_SQUAT_CLEAN                  ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)1)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_BARBELL_POWER_CLEAN                       ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)2)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_BARBELL_POWER_SNATCH                      ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)3)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_BARBELL_SQUAT_CLEAN                       ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)4)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_CLEAN_AND_JERK                            ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)5)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_BARBELL_HANG_POWER_SNATCH                 ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)6)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_BARBELL_HANG_PULL                         ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)7)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_BARBELL_HIGH_PULL                         ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)8)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_BARBELL_SNATCH                            ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)9)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_BARBELL_SPLIT_JERK                        ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)10)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_CLEAN                                     ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)11)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_DUMBBELL_CLEAN                            ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)12)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_DUMBBELL_HANG_PULL                        ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)13)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_ONE_HAND_DUMBBELL_SPLIT_SNATCH            ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)14)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_PUSH_JERK                                 ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)15)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_SINGLE_ARM_DUMBBELL_SNATCH                ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)16)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_SINGLE_ARM_HANG_SNATCH                    ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)17)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_SINGLE_ARM_KETTLEBELL_SNATCH              ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)18)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_SPLIT_JERK                                ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)19)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_SQUAT_CLEAN_AND_JERK                      ((FIT_OLYMPIC_LIFT_EXERCISE_NAME)20)
#define FIT_OLYMPIC_LIFT_EXERCISE_NAME_COUNT                                     21

typedef FIT_UINT16 FIT_PLANK_EXERCISE_NAME;
#define FIT_PLANK_EXERCISE_NAME_INVALID                                          FIT_UINT16_INVALID
#define FIT_PLANK_EXERCISE_NAME_45_DEGREE_PLANK                                  ((FIT_PLANK_EXERCISE_NAME)0)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_45_DEGREE_PLANK                         ((FIT_PLANK_EXERCISE_NAME)1)
#define FIT_PLANK_EXERCISE_NAME_90_DEGREE_STATIC_HOLD                            ((FIT_PLANK_EXERCISE_NAME)2)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_90_DEGREE_STATIC_HOLD                   ((FIT_PLANK_EXERCISE_NAME)3)
#define FIT_PLANK_EXERCISE_NAME_BEAR_CRAWL                                       ((FIT_PLANK_EXERCISE_NAME)4)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_BEAR_CRAWL                              ((FIT_PLANK_EXERCISE_NAME)5)
#define FIT_PLANK_EXERCISE_NAME_CROSS_BODY_MOUNTAIN_CLIMBER                      ((FIT_PLANK_EXERCISE_NAME)6)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_CROSS_BODY_MOUNTAIN_CLIMBER             ((FIT_PLANK_EXERCISE_NAME)7)
#define FIT_PLANK_EXERCISE_NAME_ELBOW_PLANK_PIKE_JACKS                           ((FIT_PLANK_EXERCISE_NAME)8)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_ELBOW_PLANK_PIKE_JACKS                  ((FIT_PLANK_EXERCISE_NAME)9)
#define FIT_PLANK_EXERCISE_NAME_ELEVATED_FEET_PLANK                              ((FIT_PLANK_EXERCISE_NAME)10)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_ELEVATED_FEET_PLANK                     ((FIT_PLANK_EXERCISE_NAME)11)
#define FIT_PLANK_EXERCISE_NAME_ELEVATOR_ABS                                     ((FIT_PLANK_EXERCISE_NAME)12)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_ELEVATOR_ABS                            ((FIT_PLANK_EXERCISE_NAME)13)
#define FIT_PLANK_EXERCISE_NAME_EXTENDED_PLANK                                   ((FIT_PLANK_EXERCISE_NAME)14)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_EXTENDED_PLANK                          ((FIT_PLANK_EXERCISE_NAME)15)
#define FIT_PLANK_EXERCISE_NAME_FULL_PLANK_PASSE_TWIST                           ((FIT_PLANK_EXERCISE_NAME)16)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_FULL_PLANK_PASSE_TWIST                  ((FIT_PLANK_EXERCISE_NAME)17)
#define FIT_PLANK_EXERCISE_NAME_INCHING_ELBOW_PLANK                              ((FIT_PLANK_EXERCISE_NAME)18)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_INCHING_ELBOW_PLANK                     ((FIT_PLANK_EXERCISE_NAME)19)
#define FIT_PLANK_EXERCISE_NAME_INCHWORM_TO_SIDE_PLANK                           ((FIT_PLANK_EXERCISE_NAME)20)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_INCHWORM_TO_SIDE_PLANK                  ((FIT_PLANK_EXERCISE_NAME)21)
#define FIT_PLANK_EXERCISE_NAME_KNEELING_PLANK                                   ((FIT_PLANK_EXERCISE_NAME)22)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_KNEELING_PLANK                          ((FIT_PLANK_EXERCISE_NAME)23)
#define FIT_PLANK_EXERCISE_NAME_KNEELING_SIDE_PLANK_WITH_LEG_LIFT                ((FIT_PLANK_EXERCISE_NAME)24)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_KNEELING_SIDE_PLANK_WITH_LEG_LIFT       ((FIT_PLANK_EXERCISE_NAME)25)
#define FIT_PLANK_EXERCISE_NAME_LATERAL_ROLL                                     ((FIT_PLANK_EXERCISE_NAME)26)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_LATERAL_ROLL                            ((FIT_PLANK_EXERCISE_NAME)27)
#define FIT_PLANK_EXERCISE_NAME_LYING_REVERSE_PLANK                              ((FIT_PLANK_EXERCISE_NAME)28)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_LYING_REVERSE_PLANK                     ((FIT_PLANK_EXERCISE_NAME)29)
#define FIT_PLANK_EXERCISE_NAME_MEDICINE_BALL_MOUNTAIN_CLIMBER                   ((FIT_PLANK_EXERCISE_NAME)30)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_MEDICINE_BALL_MOUNTAIN_CLIMBER          ((FIT_PLANK_EXERCISE_NAME)31)
#define FIT_PLANK_EXERCISE_NAME_MODIFIED_MOUNTAIN_CLIMBER_AND_EXTENSION          ((FIT_PLANK_EXERCISE_NAME)32)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_MODIFIED_MOUNTAIN_CLIMBER_AND_EXTENSION ((FIT_PLANK_EXERCISE_NAME)33)
#define FIT_PLANK_EXERCISE_NAME_MOUNTAIN_CLIMBER                                 ((FIT_PLANK_EXERCISE_NAME)34)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_MOUNTAIN_CLIMBER                        ((FIT_PLANK_EXERCISE_NAME)35)
#define FIT_PLANK_EXERCISE_NAME_MOUNTAIN_CLIMBER_ON_SLIDING_DISCS                ((FIT_PLANK_EXERCISE_NAME)36)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_MOUNTAIN_CLIMBER_ON_SLIDING_DISCS       ((FIT_PLANK_EXERCISE_NAME)37)
#define FIT_PLANK_EXERCISE_NAME_MOUNTAIN_CLIMBER_WITH_FEET_ON_BOSU_BALL          ((FIT_PLANK_EXERCISE_NAME)38)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_MOUNTAIN_CLIMBER_WITH_FEET_ON_BOSU_BALL ((FIT_PLANK_EXERCISE_NAME)39)
#define FIT_PLANK_EXERCISE_NAME_MOUNTAIN_CLIMBER_WITH_HANDS_ON_BENCH             ((FIT_PLANK_EXERCISE_NAME)40)
#define FIT_PLANK_EXERCISE_NAME_MOUNTAIN_CLIMBER_WITH_HANDS_ON_SWISS_BALL        ((FIT_PLANK_EXERCISE_NAME)41)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_MOUNTAIN_CLIMBER_WITH_HANDS_ON_SWISS_BALL ((FIT_PLANK_EXERCISE_NAME)42)
#define FIT_PLANK_EXERCISE_NAME_PLANK                                            ((FIT_PLANK_EXERCISE_NAME)43)
#define FIT_PLANK_EXERCISE_NAME_PLANK_JACKS_WITH_FEET_ON_SLIDING_DISCS           ((FIT_PLANK_EXERCISE_NAME)44)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_PLANK_JACKS_WITH_FEET_ON_SLIDING_DISCS  ((FIT_PLANK_EXERCISE_NAME)45)
#define FIT_PLANK_EXERCISE_NAME_PLANK_KNEE_TWIST                                 ((FIT_PLANK_EXERCISE_NAME)46)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_PLANK_KNEE_TWIST                        ((FIT_PLANK_EXERCISE_NAME)47)
#define FIT_PLANK_EXERCISE_NAME_PLANK_PIKE_JUMPS                                 ((FIT_PLANK_EXERCISE_NAME)48)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_PLANK_PIKE_JUMPS                        ((FIT_PLANK_EXERCISE_NAME)49)
#define FIT_PLANK_EXERCISE_NAME_PLANK_PIKES                                      ((FIT_PLANK_EXERCISE_NAME)50)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_PLANK_PIKES                             ((FIT_PLANK_EXERCISE_NAME)51)
#define FIT_PLANK_EXERCISE_NAME_PLANK_TO_STAND_UP                                ((FIT_PLANK_EXERCISE_NAME)52)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_PLANK_TO_STAND_UP                       ((FIT_PLANK_EXERCISE_NAME)53)
#define FIT_PLANK_EXERCISE_NAME_PLANK_WITH_ARM_RAISE                             ((FIT_PLANK_EXERCISE_NAME)54)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_PLANK_WITH_ARM_RAISE                    ((FIT_PLANK_EXERCISE_NAME)55)
#define FIT_PLANK_EXERCISE_NAME_PLANK_WITH_KNEE_TO_ELBOW                         ((FIT_PLANK_EXERCISE_NAME)56)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_PLANK_WITH_KNEE_TO_ELBOW                ((FIT_PLANK_EXERCISE_NAME)57)
#define FIT_PLANK_EXERCISE_NAME_PLANK_WITH_OBLIQUE_CRUNCH                        ((FIT_PLANK_EXERCISE_NAME)58)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_PLANK_WITH_OBLIQUE_CRUNCH               ((FIT_PLANK_EXERCISE_NAME)59)
#define FIT_PLANK_EXERCISE_NAME_PLYOMETRIC_SIDE_PLANK                            ((FIT_PLANK_EXERCISE_NAME)60)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_PLYOMETRIC_SIDE_PLANK                   ((FIT_PLANK_EXERCISE_NAME)61)
#define FIT_PLANK_EXERCISE_NAME_ROLLING_SIDE_PLANK                               ((FIT_PLANK_EXERCISE_NAME)62)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_ROLLING_SIDE_PLANK                      ((FIT_PLANK_EXERCISE_NAME)63)
#define FIT_PLANK_EXERCISE_NAME_SIDE_KICK_PLANK                                  ((FIT_PLANK_EXERCISE_NAME)64)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SIDE_KICK_PLANK                         ((FIT_PLANK_EXERCISE_NAME)65)
#define FIT_PLANK_EXERCISE_NAME_SIDE_PLANK                                       ((FIT_PLANK_EXERCISE_NAME)66)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SIDE_PLANK                              ((FIT_PLANK_EXERCISE_NAME)67)
#define FIT_PLANK_EXERCISE_NAME_SIDE_PLANK_AND_ROW                               ((FIT_PLANK_EXERCISE_NAME)68)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SIDE_PLANK_AND_ROW                      ((FIT_PLANK_EXERCISE_NAME)69)
#define FIT_PLANK_EXERCISE_NAME_SIDE_PLANK_LIFT                                  ((FIT_PLANK_EXERCISE_NAME)70)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SIDE_PLANK_LIFT                         ((FIT_PLANK_EXERCISE_NAME)71)
#define FIT_PLANK_EXERCISE_NAME_SIDE_PLANK_WITH_ELBOW_ON_BOSU_BALL               ((FIT_PLANK_EXERCISE_NAME)72)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SIDE_PLANK_WITH_ELBOW_ON_BOSU_BALL      ((FIT_PLANK_EXERCISE_NAME)73)
#define FIT_PLANK_EXERCISE_NAME_SIDE_PLANK_WITH_FEET_ON_BENCH                    ((FIT_PLANK_EXERCISE_NAME)74)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SIDE_PLANK_WITH_FEET_ON_BENCH           ((FIT_PLANK_EXERCISE_NAME)75)
#define FIT_PLANK_EXERCISE_NAME_SIDE_PLANK_WITH_KNEE_CIRCLE                      ((FIT_PLANK_EXERCISE_NAME)76)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SIDE_PLANK_WITH_KNEE_CIRCLE             ((FIT_PLANK_EXERCISE_NAME)77)
#define FIT_PLANK_EXERCISE_NAME_SIDE_PLANK_WITH_KNEE_TUCK                        ((FIT_PLANK_EXERCISE_NAME)78)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SIDE_PLANK_WITH_KNEE_TUCK               ((FIT_PLANK_EXERCISE_NAME)79)
#define FIT_PLANK_EXERCISE_NAME_SIDE_PLANK_WITH_LEG_LIFT                         ((FIT_PLANK_EXERCISE_NAME)80)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SIDE_PLANK_WITH_LEG_LIFT                ((FIT_PLANK_EXERCISE_NAME)81)
#define FIT_PLANK_EXERCISE_NAME_SIDE_PLANK_WITH_REACH_UNDER                      ((FIT_PLANK_EXERCISE_NAME)82)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SIDE_PLANK_WITH_REACH_UNDER             ((FIT_PLANK_EXERCISE_NAME)83)
#define FIT_PLANK_EXERCISE_NAME_SINGLE_LEG_ELEVATED_FEET_PLANK                   ((FIT_PLANK_EXERCISE_NAME)84)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_ELEVATED_FEET_PLANK          ((FIT_PLANK_EXERCISE_NAME)85)
#define FIT_PLANK_EXERCISE_NAME_SINGLE_LEG_FLEX_AND_EXTEND                       ((FIT_PLANK_EXERCISE_NAME)86)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_FLEX_AND_EXTEND              ((FIT_PLANK_EXERCISE_NAME)87)
#define FIT_PLANK_EXERCISE_NAME_SINGLE_LEG_SIDE_PLANK                            ((FIT_PLANK_EXERCISE_NAME)88)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_SIDE_PLANK                   ((FIT_PLANK_EXERCISE_NAME)89)
#define FIT_PLANK_EXERCISE_NAME_SPIDERMAN_PLANK                                  ((FIT_PLANK_EXERCISE_NAME)90)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SPIDERMAN_PLANK                         ((FIT_PLANK_EXERCISE_NAME)91)
#define FIT_PLANK_EXERCISE_NAME_STRAIGHT_ARM_PLANK                               ((FIT_PLANK_EXERCISE_NAME)92)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_STRAIGHT_ARM_PLANK                      ((FIT_PLANK_EXERCISE_NAME)93)
#define FIT_PLANK_EXERCISE_NAME_STRAIGHT_ARM_PLANK_WITH_SHOULDER_TOUCH           ((FIT_PLANK_EXERCISE_NAME)94)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_STRAIGHT_ARM_PLANK_WITH_SHOULDER_TOUCH  ((FIT_PLANK_EXERCISE_NAME)95)
#define FIT_PLANK_EXERCISE_NAME_SWISS_BALL_PLANK                                 ((FIT_PLANK_EXERCISE_NAME)96)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SWISS_BALL_PLANK                        ((FIT_PLANK_EXERCISE_NAME)97)
#define FIT_PLANK_EXERCISE_NAME_SWISS_BALL_PLANK_LEG_LIFT                        ((FIT_PLANK_EXERCISE_NAME)98)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SWISS_BALL_PLANK_LEG_LIFT               ((FIT_PLANK_EXERCISE_NAME)99)
#define FIT_PLANK_EXERCISE_NAME_SWISS_BALL_PLANK_LEG_LIFT_AND_HOLD               ((FIT_PLANK_EXERCISE_NAME)100)
#define FIT_PLANK_EXERCISE_NAME_SWISS_BALL_PLANK_WITH_FEET_ON_BENCH              ((FIT_PLANK_EXERCISE_NAME)101)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SWISS_BALL_PLANK_WITH_FEET_ON_BENCH     ((FIT_PLANK_EXERCISE_NAME)102)
#define FIT_PLANK_EXERCISE_NAME_SWISS_BALL_PRONE_JACKKNIFE                       ((FIT_PLANK_EXERCISE_NAME)103)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SWISS_BALL_PRONE_JACKKNIFE              ((FIT_PLANK_EXERCISE_NAME)104)
#define FIT_PLANK_EXERCISE_NAME_SWISS_BALL_SIDE_PLANK                            ((FIT_PLANK_EXERCISE_NAME)105)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SWISS_BALL_SIDE_PLANK                   ((FIT_PLANK_EXERCISE_NAME)106)
#define FIT_PLANK_EXERCISE_NAME_THREE_WAY_PLANK                                  ((FIT_PLANK_EXERCISE_NAME)107)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_THREE_WAY_PLANK                         ((FIT_PLANK_EXERCISE_NAME)108)
#define FIT_PLANK_EXERCISE_NAME_TOWEL_PLANK_AND_KNEE_IN                          ((FIT_PLANK_EXERCISE_NAME)109)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_TOWEL_PLANK_AND_KNEE_IN                 ((FIT_PLANK_EXERCISE_NAME)110)
#define FIT_PLANK_EXERCISE_NAME_T_STABILIZATION                                  ((FIT_PLANK_EXERCISE_NAME)111)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_T_STABILIZATION                         ((FIT_PLANK_EXERCISE_NAME)112)
#define FIT_PLANK_EXERCISE_NAME_TURKISH_GET_UP_TO_SIDE_PLANK                     ((FIT_PLANK_EXERCISE_NAME)113)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_TURKISH_GET_UP_TO_SIDE_PLANK            ((FIT_PLANK_EXERCISE_NAME)114)
#define FIT_PLANK_EXERCISE_NAME_TWO_POINT_PLANK                                  ((FIT_PLANK_EXERCISE_NAME)115)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_TWO_POINT_PLANK                         ((FIT_PLANK_EXERCISE_NAME)116)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_PLANK                                   ((FIT_PLANK_EXERCISE_NAME)117)
#define FIT_PLANK_EXERCISE_NAME_WIDE_STANCE_PLANK_WITH_DIAGONAL_ARM_LIFT         ((FIT_PLANK_EXERCISE_NAME)118)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_WIDE_STANCE_PLANK_WITH_DIAGONAL_ARM_LIFT ((FIT_PLANK_EXERCISE_NAME)119)
#define FIT_PLANK_EXERCISE_NAME_WIDE_STANCE_PLANK_WITH_DIAGONAL_LEG_LIFT         ((FIT_PLANK_EXERCISE_NAME)120)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_WIDE_STANCE_PLANK_WITH_DIAGONAL_LEG_LIFT ((FIT_PLANK_EXERCISE_NAME)121)
#define FIT_PLANK_EXERCISE_NAME_WIDE_STANCE_PLANK_WITH_LEG_LIFT                  ((FIT_PLANK_EXERCISE_NAME)122)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_WIDE_STANCE_PLANK_WITH_LEG_LIFT         ((FIT_PLANK_EXERCISE_NAME)123)
#define FIT_PLANK_EXERCISE_NAME_WIDE_STANCE_PLANK_WITH_OPPOSITE_ARM_AND_LEG_LIFT ((FIT_PLANK_EXERCISE_NAME)124)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_MOUNTAIN_CLIMBER_WITH_HANDS_ON_BENCH    ((FIT_PLANK_EXERCISE_NAME)125)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_SWISS_BALL_PLANK_LEG_LIFT_AND_HOLD      ((FIT_PLANK_EXERCISE_NAME)126)
#define FIT_PLANK_EXERCISE_NAME_WEIGHTED_WIDE_STANCE_PLANK_WITH_OPPOSITE_ARM_AND_LEG_LIFT ((FIT_PLANK_EXERCISE_NAME)127)
#define FIT_PLANK_EXERCISE_NAME_COUNT                                            128

typedef FIT_UINT16 FIT_PLYO_EXERCISE_NAME;
#define FIT_PLYO_EXERCISE_NAME_INVALID                                           FIT_UINT16_INVALID
#define FIT_PLYO_EXERCISE_NAME_ALTERNATING_JUMP_LUNGE                            ((FIT_PLYO_EXERCISE_NAME)0)
#define FIT_PLYO_EXERCISE_NAME_WEIGHTED_ALTERNATING_JUMP_LUNGE                   ((FIT_PLYO_EXERCISE_NAME)1)
#define FIT_PLYO_EXERCISE_NAME_BARBELL_JUMP_SQUAT                                ((FIT_PLYO_EXERCISE_NAME)2)
#define FIT_PLYO_EXERCISE_NAME_BODY_WEIGHT_JUMP_SQUAT                            ((FIT_PLYO_EXERCISE_NAME)3)
#define FIT_PLYO_EXERCISE_NAME_WEIGHTED_JUMP_SQUAT                               ((FIT_PLYO_EXERCISE_NAME)4)
#define FIT_PLYO_EXERCISE_NAME_CROSS_KNEE_STRIKE                                 ((FIT_PLYO_EXERCISE_NAME)5)
#define FIT_PLYO_EXERCISE_NAME_WEIGHTED_CROSS_KNEE_STRIKE                        ((FIT_PLYO_EXERCISE_NAME)6)
#define FIT_PLYO_EXERCISE_NAME_DEPTH_JUMP                                        ((FIT_PLYO_EXERCISE_NAME)7)
#define FIT_PLYO_EXERCISE_NAME_WEIGHTED_DEPTH_JUMP                               ((FIT_PLYO_EXERCISE_NAME)8)
#define FIT_PLYO_EXERCISE_NAME_DUMBBELL_JUMP_SQUAT                               ((FIT_PLYO_EXERCISE_NAME)9)
#define FIT_PLYO_EXERCISE_NAME_DUMBBELL_SPLIT_JUMP                               ((FIT_PLYO_EXERCISE_NAME)10)
#define FIT_PLYO_EXERCISE_NAME_FRONT_KNEE_STRIKE                                 ((FIT_PLYO_EXERCISE_NAME)11)
#define FIT_PLYO_EXERCISE_NAME_WEIGHTED_FRONT_KNEE_STRIKE                        ((FIT_PLYO_EXERCISE_NAME)12)
#define FIT_PLYO_EXERCISE_NAME_HIGH_BOX_JUMP                                     ((FIT_PLYO_EXERCISE_NAME)13)
#define FIT_PLYO_EXERCISE_NAME_WEIGHTED_HIGH_BOX_JUMP                            ((FIT_PLYO_EXERCISE_NAME)14)
#define FIT_PLYO_EXERCISE_NAME_ISOMETRIC_EXPLOSIVE_BODY_WEIGHT_JUMP_SQUAT        ((FIT_PLYO_EXERCISE_NAME)15)
#define FIT_PLYO_EXERCISE_NAME_WEIGHTED_ISOMETRIC_EXPLOSIVE_JUMP_SQUAT           ((FIT_PLYO_EXERCISE_NAME)16)
#define FIT_PLYO_EXERCISE_NAME_LATERAL_LEAP_AND_HOP                              ((FIT_PLYO_EXERCISE_NAME)17)
#define FIT_PLYO_EXERCISE_NAME_WEIGHTED_LATERAL_LEAP_AND_HOP                     ((FIT_PLYO_EXERCISE_NAME)18)
#define FIT_PLYO_EXERCISE_NAME_LATERAL_PLYO_SQUATS                               ((FIT_PLYO_EXERCISE_NAME)19)
#define FIT_PLYO_EXERCISE_NAME_WEIGHTED_LATERAL_PLYO_SQUATS                      ((FIT_PLYO_EXERCISE_NAME)20)
#define FIT_PLYO_EXERCISE_NAME_LATERAL_SLIDE                                     ((FIT_PLYO_EXERCISE_NAME)21)
#define FIT_PLYO_EXERCISE_NAME_WEIGHTED_LATERAL_SLIDE                            ((FIT_PLYO_EXERCISE_NAME)22)
#define FIT_PLYO_EXERCISE_NAME_MEDICINE_BALL_OVERHEAD_THROWS                     ((FIT_PLYO_EXERCISE_NAME)23)
#define FIT_PLYO_EXERCISE_NAME_MEDICINE_BALL_SIDE_THROW                          ((FIT_PLYO_EXERCISE_NAME)24)
#define FIT_PLYO_EXERCISE_NAME_MEDICINE_BALL_SLAM                                ((FIT_PLYO_EXERCISE_NAME)25)
#define FIT_PLYO_EXERCISE_NAME_SIDE_TO_SIDE_MEDICINE_BALL_THROWS                 ((FIT_PLYO_EXERCISE_NAME)26)
#define FIT_PLYO_EXERCISE_NAME_SIDE_TO_SIDE_SHUFFLE_JUMP                         ((FIT_PLYO_EXERCISE_NAME)27)
#define FIT_PLYO_EXERCISE_NAME_WEIGHTED_SIDE_TO_SIDE_SHUFFLE_JUMP                ((FIT_PLYO_EXERCISE_NAME)28)
#define FIT_PLYO_EXERCISE_NAME_SQUAT_JUMP_ONTO_BOX                               ((FIT_PLYO_EXERCISE_NAME)29)
#define FIT_PLYO_EXERCISE_NAME_WEIGHTED_SQUAT_JUMP_ONTO_BOX                      ((FIT_PLYO_EXERCISE_NAME)30)
#define FIT_PLYO_EXERCISE_NAME_SQUAT_JUMPS_IN_AND_OUT                            ((FIT_PLYO_EXERCISE_NAME)31)
#define FIT_PLYO_EXERCISE_NAME_WEIGHTED_SQUAT_JUMPS_IN_AND_OUT                   ((FIT_PLYO_EXERCISE_NAME)32)
#define FIT_PLYO_EXERCISE_NAME_COUNT                                             33

typedef FIT_UINT16 FIT_PULL_UP_EXERCISE_NAME;
#define FIT_PULL_UP_EXERCISE_NAME_INVALID                                        FIT_UINT16_INVALID
#define FIT_PULL_UP_EXERCISE_NAME_BANDED_PULL_UPS                                ((FIT_PULL_UP_EXERCISE_NAME)0)
#define FIT_PULL_UP_EXERCISE_NAME_30_DEGREE_LAT_PULLDOWN                         ((FIT_PULL_UP_EXERCISE_NAME)1)
#define FIT_PULL_UP_EXERCISE_NAME_BAND_ASSISTED_CHIN_UP                          ((FIT_PULL_UP_EXERCISE_NAME)2)
#define FIT_PULL_UP_EXERCISE_NAME_CLOSE_GRIP_CHIN_UP                             ((FIT_PULL_UP_EXERCISE_NAME)3)
#define FIT_PULL_UP_EXERCISE_NAME_WEIGHTED_CLOSE_GRIP_CHIN_UP                    ((FIT_PULL_UP_EXERCISE_NAME)4)
#define FIT_PULL_UP_EXERCISE_NAME_CLOSE_GRIP_LAT_PULLDOWN                        ((FIT_PULL_UP_EXERCISE_NAME)5)
#define FIT_PULL_UP_EXERCISE_NAME_CROSSOVER_CHIN_UP                              ((FIT_PULL_UP_EXERCISE_NAME)6)
#define FIT_PULL_UP_EXERCISE_NAME_WEIGHTED_CROSSOVER_CHIN_UP                     ((FIT_PULL_UP_EXERCISE_NAME)7)
#define FIT_PULL_UP_EXERCISE_NAME_EZ_BAR_PULLOVER                                ((FIT_PULL_UP_EXERCISE_NAME)8)
#define FIT_PULL_UP_EXERCISE_NAME_HANGING_HURDLE                                 ((FIT_PULL_UP_EXERCISE_NAME)9)
#define FIT_PULL_UP_EXERCISE_NAME_WEIGHTED_HANGING_HURDLE                        ((FIT_PULL_UP_EXERCISE_NAME)10)
#define FIT_PULL_UP_EXERCISE_NAME_KNEELING_LAT_PULLDOWN                          ((FIT_PULL_UP_EXERCISE_NAME)11)
#define FIT_PULL_UP_EXERCISE_NAME_KNEELING_UNDERHAND_GRIP_LAT_PULLDOWN           ((FIT_PULL_UP_EXERCISE_NAME)12)
#define FIT_PULL_UP_EXERCISE_NAME_LAT_PULLDOWN                                   ((FIT_PULL_UP_EXERCISE_NAME)13)
#define FIT_PULL_UP_EXERCISE_NAME_MIXED_GRIP_CHIN_UP                             ((FIT_PULL_UP_EXERCISE_NAME)14)
#define FIT_PULL_UP_EXERCISE_NAME_WEIGHTED_MIXED_GRIP_CHIN_UP                    ((FIT_PULL_UP_EXERCISE_NAME)15)
#define FIT_PULL_UP_EXERCISE_NAME_MIXED_GRIP_PULL_UP                             ((FIT_PULL_UP_EXERCISE_NAME)16)
#define FIT_PULL_UP_EXERCISE_NAME_WEIGHTED_MIXED_GRIP_PULL_UP                    ((FIT_PULL_UP_EXERCISE_NAME)17)
#define FIT_PULL_UP_EXERCISE_NAME_REVERSE_GRIP_PULLDOWN                          ((FIT_PULL_UP_EXERCISE_NAME)18)
#define FIT_PULL_UP_EXERCISE_NAME_STANDING_CABLE_PULLOVER                        ((FIT_PULL_UP_EXERCISE_NAME)19)
#define FIT_PULL_UP_EXERCISE_NAME_STRAIGHT_ARM_PULLDOWN                          ((FIT_PULL_UP_EXERCISE_NAME)20)
#define FIT_PULL_UP_EXERCISE_NAME_SWISS_BALL_EZ_BAR_PULLOVER                     ((FIT_PULL_UP_EXERCISE_NAME)21)
#define FIT_PULL_UP_EXERCISE_NAME_TOWEL_PULL_UP                                  ((FIT_PULL_UP_EXERCISE_NAME)22)
#define FIT_PULL_UP_EXERCISE_NAME_WEIGHTED_TOWEL_PULL_UP                         ((FIT_PULL_UP_EXERCISE_NAME)23)
#define FIT_PULL_UP_EXERCISE_NAME_WEIGHTED_PULL_UP                               ((FIT_PULL_UP_EXERCISE_NAME)24)
#define FIT_PULL_UP_EXERCISE_NAME_WIDE_GRIP_LAT_PULLDOWN                         ((FIT_PULL_UP_EXERCISE_NAME)25)
#define FIT_PULL_UP_EXERCISE_NAME_WIDE_GRIP_PULL_UP                              ((FIT_PULL_UP_EXERCISE_NAME)26)
#define FIT_PULL_UP_EXERCISE_NAME_WEIGHTED_WIDE_GRIP_PULL_UP                     ((FIT_PULL_UP_EXERCISE_NAME)27)
#define FIT_PULL_UP_EXERCISE_NAME_BURPEE_PULL_UP                                 ((FIT_PULL_UP_EXERCISE_NAME)28)
#define FIT_PULL_UP_EXERCISE_NAME_WEIGHTED_BURPEE_PULL_UP                        ((FIT_PULL_UP_EXERCISE_NAME)29)
#define FIT_PULL_UP_EXERCISE_NAME_JUMPING_PULL_UPS                               ((FIT_PULL_UP_EXERCISE_NAME)30)
#define FIT_PULL_UP_EXERCISE_NAME_WEIGHTED_JUMPING_PULL_UPS                      ((FIT_PULL_UP_EXERCISE_NAME)31)
#define FIT_PULL_UP_EXERCISE_NAME_KIPPING_PULL_UP                                ((FIT_PULL_UP_EXERCISE_NAME)32)
#define FIT_PULL_UP_EXERCISE_NAME_WEIGHTED_KIPPING_PULL_UP                       ((FIT_PULL_UP_EXERCISE_NAME)33)
#define FIT_PULL_UP_EXERCISE_NAME_L_PULL_UP                                      ((FIT_PULL_UP_EXERCISE_NAME)34)
#define FIT_PULL_UP_EXERCISE_NAME_WEIGHTED_L_PULL_UP                             ((FIT_PULL_UP_EXERCISE_NAME)35)
#define FIT_PULL_UP_EXERCISE_NAME_SUSPENDED_CHIN_UP                              ((FIT_PULL_UP_EXERCISE_NAME)36)
#define FIT_PULL_UP_EXERCISE_NAME_WEIGHTED_SUSPENDED_CHIN_UP                     ((FIT_PULL_UP_EXERCISE_NAME)37)
#define FIT_PULL_UP_EXERCISE_NAME_PULL_UP                                        ((FIT_PULL_UP_EXERCISE_NAME)38)
#define FIT_PULL_UP_EXERCISE_NAME_COUNT                                          39

typedef FIT_UINT16 FIT_PUSH_UP_EXERCISE_NAME;
#define FIT_PUSH_UP_EXERCISE_NAME_INVALID                                        FIT_UINT16_INVALID
#define FIT_PUSH_UP_EXERCISE_NAME_CHEST_PRESS_WITH_BAND                          ((FIT_PUSH_UP_EXERCISE_NAME)0)
#define FIT_PUSH_UP_EXERCISE_NAME_ALTERNATING_STAGGERED_PUSH_UP                  ((FIT_PUSH_UP_EXERCISE_NAME)1)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_ALTERNATING_STAGGERED_PUSH_UP         ((FIT_PUSH_UP_EXERCISE_NAME)2)
#define FIT_PUSH_UP_EXERCISE_NAME_ALTERNATING_HANDS_MEDICINE_BALL_PUSH_UP        ((FIT_PUSH_UP_EXERCISE_NAME)3)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_ALTERNATING_HANDS_MEDICINE_BALL_PUSH_UP ((FIT_PUSH_UP_EXERCISE_NAME)4)
#define FIT_PUSH_UP_EXERCISE_NAME_BOSU_BALL_PUSH_UP                              ((FIT_PUSH_UP_EXERCISE_NAME)5)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_BOSU_BALL_PUSH_UP                     ((FIT_PUSH_UP_EXERCISE_NAME)6)
#define FIT_PUSH_UP_EXERCISE_NAME_CLAPPING_PUSH_UP                               ((FIT_PUSH_UP_EXERCISE_NAME)7)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_CLAPPING_PUSH_UP                      ((FIT_PUSH_UP_EXERCISE_NAME)8)
#define FIT_PUSH_UP_EXERCISE_NAME_CLOSE_GRIP_MEDICINE_BALL_PUSH_UP               ((FIT_PUSH_UP_EXERCISE_NAME)9)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_CLOSE_GRIP_MEDICINE_BALL_PUSH_UP      ((FIT_PUSH_UP_EXERCISE_NAME)10)
#define FIT_PUSH_UP_EXERCISE_NAME_CLOSE_HANDS_PUSH_UP                            ((FIT_PUSH_UP_EXERCISE_NAME)11)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_CLOSE_HANDS_PUSH_UP                   ((FIT_PUSH_UP_EXERCISE_NAME)12)
#define FIT_PUSH_UP_EXERCISE_NAME_DECLINE_PUSH_UP                                ((FIT_PUSH_UP_EXERCISE_NAME)13)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_DECLINE_PUSH_UP                       ((FIT_PUSH_UP_EXERCISE_NAME)14)
#define FIT_PUSH_UP_EXERCISE_NAME_DIAMOND_PUSH_UP                                ((FIT_PUSH_UP_EXERCISE_NAME)15)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_DIAMOND_PUSH_UP                       ((FIT_PUSH_UP_EXERCISE_NAME)16)
#define FIT_PUSH_UP_EXERCISE_NAME_EXPLOSIVE_CROSSOVER_PUSH_UP                    ((FIT_PUSH_UP_EXERCISE_NAME)17)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_EXPLOSIVE_CROSSOVER_PUSH_UP           ((FIT_PUSH_UP_EXERCISE_NAME)18)
#define FIT_PUSH_UP_EXERCISE_NAME_EXPLOSIVE_PUSH_UP                              ((FIT_PUSH_UP_EXERCISE_NAME)19)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_EXPLOSIVE_PUSH_UP                     ((FIT_PUSH_UP_EXERCISE_NAME)20)
#define FIT_PUSH_UP_EXERCISE_NAME_FEET_ELEVATED_SIDE_TO_SIDE_PUSH_UP             ((FIT_PUSH_UP_EXERCISE_NAME)21)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_FEET_ELEVATED_SIDE_TO_SIDE_PUSH_UP    ((FIT_PUSH_UP_EXERCISE_NAME)22)
#define FIT_PUSH_UP_EXERCISE_NAME_HAND_RELEASE_PUSH_UP                           ((FIT_PUSH_UP_EXERCISE_NAME)23)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_HAND_RELEASE_PUSH_UP                  ((FIT_PUSH_UP_EXERCISE_NAME)24)
#define FIT_PUSH_UP_EXERCISE_NAME_HANDSTAND_PUSH_UP                              ((FIT_PUSH_UP_EXERCISE_NAME)25)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_HANDSTAND_PUSH_UP                     ((FIT_PUSH_UP_EXERCISE_NAME)26)
#define FIT_PUSH_UP_EXERCISE_NAME_INCLINE_PUSH_UP                                ((FIT_PUSH_UP_EXERCISE_NAME)27)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_INCLINE_PUSH_UP                       ((FIT_PUSH_UP_EXERCISE_NAME)28)
#define FIT_PUSH_UP_EXERCISE_NAME_ISOMETRIC_EXPLOSIVE_PUSH_UP                    ((FIT_PUSH_UP_EXERCISE_NAME)29)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_ISOMETRIC_EXPLOSIVE_PUSH_UP           ((FIT_PUSH_UP_EXERCISE_NAME)30)
#define FIT_PUSH_UP_EXERCISE_NAME_JUDO_PUSH_UP                                   ((FIT_PUSH_UP_EXERCISE_NAME)31)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_JUDO_PUSH_UP                          ((FIT_PUSH_UP_EXERCISE_NAME)32)
#define FIT_PUSH_UP_EXERCISE_NAME_KNEELING_PUSH_UP                               ((FIT_PUSH_UP_EXERCISE_NAME)33)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_KNEELING_PUSH_UP                      ((FIT_PUSH_UP_EXERCISE_NAME)34)
#define FIT_PUSH_UP_EXERCISE_NAME_MEDICINE_BALL_CHEST_PASS                       ((FIT_PUSH_UP_EXERCISE_NAME)35)
#define FIT_PUSH_UP_EXERCISE_NAME_MEDICINE_BALL_PUSH_UP                          ((FIT_PUSH_UP_EXERCISE_NAME)36)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_MEDICINE_BALL_PUSH_UP                 ((FIT_PUSH_UP_EXERCISE_NAME)37)
#define FIT_PUSH_UP_EXERCISE_NAME_ONE_ARM_PUSH_UP                                ((FIT_PUSH_UP_EXERCISE_NAME)38)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_ONE_ARM_PUSH_UP                       ((FIT_PUSH_UP_EXERCISE_NAME)39)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_PUSH_UP                               ((FIT_PUSH_UP_EXERCISE_NAME)40)
#define FIT_PUSH_UP_EXERCISE_NAME_PUSH_UP_AND_ROW                                ((FIT_PUSH_UP_EXERCISE_NAME)41)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_PUSH_UP_AND_ROW                       ((FIT_PUSH_UP_EXERCISE_NAME)42)
#define FIT_PUSH_UP_EXERCISE_NAME_PUSH_UP_PLUS                                   ((FIT_PUSH_UP_EXERCISE_NAME)43)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_PUSH_UP_PLUS                          ((FIT_PUSH_UP_EXERCISE_NAME)44)
#define FIT_PUSH_UP_EXERCISE_NAME_PUSH_UP_WITH_FEET_ON_SWISS_BALL                ((FIT_PUSH_UP_EXERCISE_NAME)45)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_PUSH_UP_WITH_FEET_ON_SWISS_BALL       ((FIT_PUSH_UP_EXERCISE_NAME)46)
#define FIT_PUSH_UP_EXERCISE_NAME_PUSH_UP_WITH_ONE_HAND_ON_MEDICINE_BALL         ((FIT_PUSH_UP_EXERCISE_NAME)47)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_PUSH_UP_WITH_ONE_HAND_ON_MEDICINE_BALL ((FIT_PUSH_UP_EXERCISE_NAME)48)
#define FIT_PUSH_UP_EXERCISE_NAME_SHOULDER_PUSH_UP                               ((FIT_PUSH_UP_EXERCISE_NAME)49)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_SHOULDER_PUSH_UP                      ((FIT_PUSH_UP_EXERCISE_NAME)50)
#define FIT_PUSH_UP_EXERCISE_NAME_SINGLE_ARM_MEDICINE_BALL_PUSH_UP               ((FIT_PUSH_UP_EXERCISE_NAME)51)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_SINGLE_ARM_MEDICINE_BALL_PUSH_UP      ((FIT_PUSH_UP_EXERCISE_NAME)52)
#define FIT_PUSH_UP_EXERCISE_NAME_SPIDERMAN_PUSH_UP                              ((FIT_PUSH_UP_EXERCISE_NAME)53)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_SPIDERMAN_PUSH_UP                     ((FIT_PUSH_UP_EXERCISE_NAME)54)
#define FIT_PUSH_UP_EXERCISE_NAME_STACKED_FEET_PUSH_UP                           ((FIT_PUSH_UP_EXERCISE_NAME)55)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_STACKED_FEET_PUSH_UP                  ((FIT_PUSH_UP_EXERCISE_NAME)56)
#define FIT_PUSH_UP_EXERCISE_NAME_STAGGERED_HANDS_PUSH_UP                        ((FIT_PUSH_UP_EXERCISE_NAME)57)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_STAGGERED_HANDS_PUSH_UP               ((FIT_PUSH_UP_EXERCISE_NAME)58)
#define FIT_PUSH_UP_EXERCISE_NAME_SUSPENDED_PUSH_UP                              ((FIT_PUSH_UP_EXERCISE_NAME)59)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_SUSPENDED_PUSH_UP                     ((FIT_PUSH_UP_EXERCISE_NAME)60)
#define FIT_PUSH_UP_EXERCISE_NAME_SWISS_BALL_PUSH_UP                             ((FIT_PUSH_UP_EXERCISE_NAME)61)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_SWISS_BALL_PUSH_UP                    ((FIT_PUSH_UP_EXERCISE_NAME)62)
#define FIT_PUSH_UP_EXERCISE_NAME_SWISS_BALL_PUSH_UP_PLUS                        ((FIT_PUSH_UP_EXERCISE_NAME)63)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_SWISS_BALL_PUSH_UP_PLUS               ((FIT_PUSH_UP_EXERCISE_NAME)64)
#define FIT_PUSH_UP_EXERCISE_NAME_T_PUSH_UP                                      ((FIT_PUSH_UP_EXERCISE_NAME)65)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_T_PUSH_UP                             ((FIT_PUSH_UP_EXERCISE_NAME)66)
#define FIT_PUSH_UP_EXERCISE_NAME_TRIPLE_STOP_PUSH_UP                            ((FIT_PUSH_UP_EXERCISE_NAME)67)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_TRIPLE_STOP_PUSH_UP                   ((FIT_PUSH_UP_EXERCISE_NAME)68)
#define FIT_PUSH_UP_EXERCISE_NAME_WIDE_HANDS_PUSH_UP                             ((FIT_PUSH_UP_EXERCISE_NAME)69)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_WIDE_HANDS_PUSH_UP                    ((FIT_PUSH_UP_EXERCISE_NAME)70)
#define FIT_PUSH_UP_EXERCISE_NAME_PARALLETTE_HANDSTAND_PUSH_UP                   ((FIT_PUSH_UP_EXERCISE_NAME)71)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_PARALLETTE_HANDSTAND_PUSH_UP          ((FIT_PUSH_UP_EXERCISE_NAME)72)
#define FIT_PUSH_UP_EXERCISE_NAME_RING_HANDSTAND_PUSH_UP                         ((FIT_PUSH_UP_EXERCISE_NAME)73)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_RING_HANDSTAND_PUSH_UP                ((FIT_PUSH_UP_EXERCISE_NAME)74)
#define FIT_PUSH_UP_EXERCISE_NAME_RING_PUSH_UP                                   ((FIT_PUSH_UP_EXERCISE_NAME)75)
#define FIT_PUSH_UP_EXERCISE_NAME_WEIGHTED_RING_PUSH_UP                          ((FIT_PUSH_UP_EXERCISE_NAME)76)
#define FIT_PUSH_UP_EXERCISE_NAME_PUSH_UP                                        ((FIT_PUSH_UP_EXERCISE_NAME)77)
#define FIT_PUSH_UP_EXERCISE_NAME_COUNT                                          78

typedef FIT_UINT16 FIT_ROW_EXERCISE_NAME;
#define FIT_ROW_EXERCISE_NAME_INVALID                                            FIT_UINT16_INVALID
#define FIT_ROW_EXERCISE_NAME_BARBELL_STRAIGHT_LEG_DEADLIFT_TO_ROW               ((FIT_ROW_EXERCISE_NAME)0)
#define FIT_ROW_EXERCISE_NAME_CABLE_ROW_STANDING                                 ((FIT_ROW_EXERCISE_NAME)1)
#define FIT_ROW_EXERCISE_NAME_DUMBBELL_ROW                                       ((FIT_ROW_EXERCISE_NAME)2)
#define FIT_ROW_EXERCISE_NAME_ELEVATED_FEET_INVERTED_ROW                         ((FIT_ROW_EXERCISE_NAME)3)
#define FIT_ROW_EXERCISE_NAME_WEIGHTED_ELEVATED_FEET_INVERTED_ROW                ((FIT_ROW_EXERCISE_NAME)4)
#define FIT_ROW_EXERCISE_NAME_FACE_PULL                                          ((FIT_ROW_EXERCISE_NAME)5)
#define FIT_ROW_EXERCISE_NAME_FACE_PULL_WITH_EXTERNAL_ROTATION                   ((FIT_ROW_EXERCISE_NAME)6)
#define FIT_ROW_EXERCISE_NAME_INVERTED_ROW_WITH_FEET_ON_SWISS_BALL               ((FIT_ROW_EXERCISE_NAME)7)
#define FIT_ROW_EXERCISE_NAME_WEIGHTED_INVERTED_ROW_WITH_FEET_ON_SWISS_BALL      ((FIT_ROW_EXERCISE_NAME)8)
#define FIT_ROW_EXERCISE_NAME_KETTLEBELL_ROW                                     ((FIT_ROW_EXERCISE_NAME)9)
#define FIT_ROW_EXERCISE_NAME_MODIFIED_INVERTED_ROW                              ((FIT_ROW_EXERCISE_NAME)10)
#define FIT_ROW_EXERCISE_NAME_WEIGHTED_MODIFIED_INVERTED_ROW                     ((FIT_ROW_EXERCISE_NAME)11)
#define FIT_ROW_EXERCISE_NAME_NEUTRAL_GRIP_ALTERNATING_DUMBBELL_ROW              ((FIT_ROW_EXERCISE_NAME)12)
#define FIT_ROW_EXERCISE_NAME_ONE_ARM_BENT_OVER_ROW                              ((FIT_ROW_EXERCISE_NAME)13)
#define FIT_ROW_EXERCISE_NAME_ONE_LEGGED_DUMBBELL_ROW                            ((FIT_ROW_EXERCISE_NAME)14)
#define FIT_ROW_EXERCISE_NAME_RENEGADE_ROW                                       ((FIT_ROW_EXERCISE_NAME)15)
#define FIT_ROW_EXERCISE_NAME_REVERSE_GRIP_BARBELL_ROW                           ((FIT_ROW_EXERCISE_NAME)16)
#define FIT_ROW_EXERCISE_NAME_ROPE_HANDLE_CABLE_ROW                              ((FIT_ROW_EXERCISE_NAME)17)
#define FIT_ROW_EXERCISE_NAME_SEATED_CABLE_ROW                                   ((FIT_ROW_EXERCISE_NAME)18)
#define FIT_ROW_EXERCISE_NAME_SEATED_DUMBBELL_ROW                                ((FIT_ROW_EXERCISE_NAME)19)
#define FIT_ROW_EXERCISE_NAME_SINGLE_ARM_CABLE_ROW                               ((FIT_ROW_EXERCISE_NAME)20)
#define FIT_ROW_EXERCISE_NAME_SINGLE_ARM_CABLE_ROW_AND_ROTATION                  ((FIT_ROW_EXERCISE_NAME)21)
#define FIT_ROW_EXERCISE_NAME_SINGLE_ARM_INVERTED_ROW                            ((FIT_ROW_EXERCISE_NAME)22)
#define FIT_ROW_EXERCISE_NAME_WEIGHTED_SINGLE_ARM_INVERTED_ROW                   ((FIT_ROW_EXERCISE_NAME)23)
#define FIT_ROW_EXERCISE_NAME_SINGLE_ARM_NEUTRAL_GRIP_DUMBBELL_ROW               ((FIT_ROW_EXERCISE_NAME)24)
#define FIT_ROW_EXERCISE_NAME_SINGLE_ARM_NEUTRAL_GRIP_DUMBBELL_ROW_AND_ROTATION  ((FIT_ROW_EXERCISE_NAME)25)
#define FIT_ROW_EXERCISE_NAME_SUSPENDED_INVERTED_ROW                             ((FIT_ROW_EXERCISE_NAME)26)
#define FIT_ROW_EXERCISE_NAME_WEIGHTED_SUSPENDED_INVERTED_ROW                    ((FIT_ROW_EXERCISE_NAME)27)
#define FIT_ROW_EXERCISE_NAME_T_BAR_ROW                                          ((FIT_ROW_EXERCISE_NAME)28)
#define FIT_ROW_EXERCISE_NAME_TOWEL_GRIP_INVERTED_ROW                            ((FIT_ROW_EXERCISE_NAME)29)
#define FIT_ROW_EXERCISE_NAME_WEIGHTED_TOWEL_GRIP_INVERTED_ROW                   ((FIT_ROW_EXERCISE_NAME)30)
#define FIT_ROW_EXERCISE_NAME_UNDERHAND_GRIP_CABLE_ROW                           ((FIT_ROW_EXERCISE_NAME)31)
#define FIT_ROW_EXERCISE_NAME_V_GRIP_CABLE_ROW                                   ((FIT_ROW_EXERCISE_NAME)32)
#define FIT_ROW_EXERCISE_NAME_WIDE_GRIP_SEATED_CABLE_ROW                         ((FIT_ROW_EXERCISE_NAME)33)
#define FIT_ROW_EXERCISE_NAME_COUNT                                              34

typedef FIT_UINT16 FIT_SHOULDER_PRESS_EXERCISE_NAME;
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_INVALID                                 FIT_UINT16_INVALID
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_ALTERNATING_DUMBBELL_SHOULDER_PRESS     ((FIT_SHOULDER_PRESS_EXERCISE_NAME)0)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_ARNOLD_PRESS                            ((FIT_SHOULDER_PRESS_EXERCISE_NAME)1)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_BARBELL_FRONT_SQUAT_TO_PUSH_PRESS       ((FIT_SHOULDER_PRESS_EXERCISE_NAME)2)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_BARBELL_PUSH_PRESS                      ((FIT_SHOULDER_PRESS_EXERCISE_NAME)3)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_BARBELL_SHOULDER_PRESS                  ((FIT_SHOULDER_PRESS_EXERCISE_NAME)4)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_DEAD_CURL_PRESS                         ((FIT_SHOULDER_PRESS_EXERCISE_NAME)5)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_DUMBBELL_ALTERNATING_SHOULDER_PRESS_AND_TWIST ((FIT_SHOULDER_PRESS_EXERCISE_NAME)6)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_DUMBBELL_HAMMER_CURL_TO_LUNGE_TO_PRESS  ((FIT_SHOULDER_PRESS_EXERCISE_NAME)7)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_DUMBBELL_PUSH_PRESS                     ((FIT_SHOULDER_PRESS_EXERCISE_NAME)8)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_FLOOR_INVERTED_SHOULDER_PRESS           ((FIT_SHOULDER_PRESS_EXERCISE_NAME)9)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_WEIGHTED_FLOOR_INVERTED_SHOULDER_PRESS  ((FIT_SHOULDER_PRESS_EXERCISE_NAME)10)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_INVERTED_SHOULDER_PRESS                 ((FIT_SHOULDER_PRESS_EXERCISE_NAME)11)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_WEIGHTED_INVERTED_SHOULDER_PRESS        ((FIT_SHOULDER_PRESS_EXERCISE_NAME)12)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_ONE_ARM_PUSH_PRESS                      ((FIT_SHOULDER_PRESS_EXERCISE_NAME)13)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_OVERHEAD_BARBELL_PRESS                  ((FIT_SHOULDER_PRESS_EXERCISE_NAME)14)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_OVERHEAD_DUMBBELL_PRESS                 ((FIT_SHOULDER_PRESS_EXERCISE_NAME)15)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_SEATED_BARBELL_SHOULDER_PRESS           ((FIT_SHOULDER_PRESS_EXERCISE_NAME)16)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_SEATED_DUMBBELL_SHOULDER_PRESS          ((FIT_SHOULDER_PRESS_EXERCISE_NAME)17)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_SINGLE_ARM_DUMBBELL_SHOULDER_PRESS      ((FIT_SHOULDER_PRESS_EXERCISE_NAME)18)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_SINGLE_ARM_STEP_UP_AND_PRESS            ((FIT_SHOULDER_PRESS_EXERCISE_NAME)19)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_SMITH_MACHINE_OVERHEAD_PRESS            ((FIT_SHOULDER_PRESS_EXERCISE_NAME)20)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_SPLIT_STANCE_HAMMER_CURL_TO_PRESS       ((FIT_SHOULDER_PRESS_EXERCISE_NAME)21)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_SWISS_BALL_DUMBBELL_SHOULDER_PRESS      ((FIT_SHOULDER_PRESS_EXERCISE_NAME)22)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_WEIGHT_PLATE_FRONT_RAISE                ((FIT_SHOULDER_PRESS_EXERCISE_NAME)23)
#define FIT_SHOULDER_PRESS_EXERCISE_NAME_COUNT                                   24

typedef FIT_UINT16 FIT_SHOULDER_STABILITY_EXERCISE_NAME;
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_INVALID                             FIT_UINT16_INVALID
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_90_DEGREE_CABLE_EXTERNAL_ROTATION   ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)0)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_BAND_EXTERNAL_ROTATION              ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)1)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_BAND_INTERNAL_ROTATION              ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)2)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_BENT_ARM_LATERAL_RAISE_AND_EXTERNAL_ROTATION ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)3)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_CABLE_EXTERNAL_ROTATION             ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)4)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_DUMBBELL_FACE_PULL_WITH_EXTERNAL_ROTATION ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)5)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_FLOOR_I_RAISE                       ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)6)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_WEIGHTED_FLOOR_I_RAISE              ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)7)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_FLOOR_T_RAISE                       ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)8)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_WEIGHTED_FLOOR_T_RAISE              ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)9)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_FLOOR_Y_RAISE                       ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)10)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_WEIGHTED_FLOOR_Y_RAISE              ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)11)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_INCLINE_I_RAISE                     ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)12)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_WEIGHTED_INCLINE_I_RAISE            ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)13)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_INCLINE_L_RAISE                     ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)14)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_WEIGHTED_INCLINE_L_RAISE            ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)15)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_INCLINE_T_RAISE                     ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)16)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_WEIGHTED_INCLINE_T_RAISE            ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)17)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_INCLINE_W_RAISE                     ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)18)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_WEIGHTED_INCLINE_W_RAISE            ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)19)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_INCLINE_Y_RAISE                     ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)20)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_WEIGHTED_INCLINE_Y_RAISE            ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)21)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_LYING_EXTERNAL_ROTATION             ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)22)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_SEATED_DUMBBELL_EXTERNAL_ROTATION   ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)23)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_STANDING_L_RAISE                    ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)24)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_SWISS_BALL_I_RAISE                  ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)25)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_WEIGHTED_SWISS_BALL_I_RAISE         ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)26)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_SWISS_BALL_T_RAISE                  ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)27)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_WEIGHTED_SWISS_BALL_T_RAISE         ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)28)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_SWISS_BALL_W_RAISE                  ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)29)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_WEIGHTED_SWISS_BALL_W_RAISE         ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)30)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_SWISS_BALL_Y_RAISE                  ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)31)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_WEIGHTED_SWISS_BALL_Y_RAISE         ((FIT_SHOULDER_STABILITY_EXERCISE_NAME)32)
#define FIT_SHOULDER_STABILITY_EXERCISE_NAME_COUNT                               33

typedef FIT_UINT16 FIT_SHRUG_EXERCISE_NAME;
#define FIT_SHRUG_EXERCISE_NAME_INVALID                                          FIT_UINT16_INVALID
#define FIT_SHRUG_EXERCISE_NAME_BARBELL_JUMP_SHRUG                               ((FIT_SHRUG_EXERCISE_NAME)0)
#define FIT_SHRUG_EXERCISE_NAME_BARBELL_SHRUG                                    ((FIT_SHRUG_EXERCISE_NAME)1)
#define FIT_SHRUG_EXERCISE_NAME_BARBELL_UPRIGHT_ROW                              ((FIT_SHRUG_EXERCISE_NAME)2)
#define FIT_SHRUG_EXERCISE_NAME_BEHIND_THE_BACK_SMITH_MACHINE_SHRUG              ((FIT_SHRUG_EXERCISE_NAME)3)
#define FIT_SHRUG_EXERCISE_NAME_DUMBBELL_JUMP_SHRUG                              ((FIT_SHRUG_EXERCISE_NAME)4)
#define FIT_SHRUG_EXERCISE_NAME_DUMBBELL_SHRUG                                   ((FIT_SHRUG_EXERCISE_NAME)5)
#define FIT_SHRUG_EXERCISE_NAME_DUMBBELL_UPRIGHT_ROW                             ((FIT_SHRUG_EXERCISE_NAME)6)
#define FIT_SHRUG_EXERCISE_NAME_INCLINE_DUMBBELL_SHRUG                           ((FIT_SHRUG_EXERCISE_NAME)7)
#define FIT_SHRUG_EXERCISE_NAME_OVERHEAD_BARBELL_SHRUG                           ((FIT_SHRUG_EXERCISE_NAME)8)
#define FIT_SHRUG_EXERCISE_NAME_OVERHEAD_DUMBBELL_SHRUG                          ((FIT_SHRUG_EXERCISE_NAME)9)
#define FIT_SHRUG_EXERCISE_NAME_SCAPTION_AND_SHRUG                               ((FIT_SHRUG_EXERCISE_NAME)10)
#define FIT_SHRUG_EXERCISE_NAME_SCAPULAR_RETRACTION                              ((FIT_SHRUG_EXERCISE_NAME)11)
#define FIT_SHRUG_EXERCISE_NAME_SERRATUS_CHAIR_SHRUG                             ((FIT_SHRUG_EXERCISE_NAME)12)
#define FIT_SHRUG_EXERCISE_NAME_WEIGHTED_SERRATUS_CHAIR_SHRUG                    ((FIT_SHRUG_EXERCISE_NAME)13)
#define FIT_SHRUG_EXERCISE_NAME_SERRATUS_SHRUG                                   ((FIT_SHRUG_EXERCISE_NAME)14)
#define FIT_SHRUG_EXERCISE_NAME_WEIGHTED_SERRATUS_SHRUG                          ((FIT_SHRUG_EXERCISE_NAME)15)
#define FIT_SHRUG_EXERCISE_NAME_WIDE_GRIP_JUMP_SHRUG                             ((FIT_SHRUG_EXERCISE_NAME)16)
#define FIT_SHRUG_EXERCISE_NAME_COUNT                                            17

typedef FIT_UINT16 FIT_SIT_UP_EXERCISE_NAME;
#define FIT_SIT_UP_EXERCISE_NAME_INVALID                                         FIT_UINT16_INVALID
#define FIT_SIT_UP_EXERCISE_NAME_ALTERNATING_SIT_UP                              ((FIT_SIT_UP_EXERCISE_NAME)0)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_ALTERNATING_SIT_UP                     ((FIT_SIT_UP_EXERCISE_NAME)1)
#define FIT_SIT_UP_EXERCISE_NAME_BENT_KNEE_V_UP                                  ((FIT_SIT_UP_EXERCISE_NAME)2)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_BENT_KNEE_V_UP                         ((FIT_SIT_UP_EXERCISE_NAME)3)
#define FIT_SIT_UP_EXERCISE_NAME_BUTTERFLY_SIT_UP                                ((FIT_SIT_UP_EXERCISE_NAME)4)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_BUTTERFLY_SITUP                        ((FIT_SIT_UP_EXERCISE_NAME)5)
#define FIT_SIT_UP_EXERCISE_NAME_CROSS_PUNCH_ROLL_UP                             ((FIT_SIT_UP_EXERCISE_NAME)6)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_CROSS_PUNCH_ROLL_UP                    ((FIT_SIT_UP_EXERCISE_NAME)7)
#define FIT_SIT_UP_EXERCISE_NAME_CROSSED_ARMS_SIT_UP                             ((FIT_SIT_UP_EXERCISE_NAME)8)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_CROSSED_ARMS_SIT_UP                    ((FIT_SIT_UP_EXERCISE_NAME)9)
#define FIT_SIT_UP_EXERCISE_NAME_GET_UP_SIT_UP                                   ((FIT_SIT_UP_EXERCISE_NAME)10)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_GET_UP_SIT_UP                          ((FIT_SIT_UP_EXERCISE_NAME)11)
#define FIT_SIT_UP_EXERCISE_NAME_HOVERING_SIT_UP                                 ((FIT_SIT_UP_EXERCISE_NAME)12)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_HOVERING_SIT_UP                        ((FIT_SIT_UP_EXERCISE_NAME)13)
#define FIT_SIT_UP_EXERCISE_NAME_KETTLEBELL_SIT_UP                               ((FIT_SIT_UP_EXERCISE_NAME)14)
#define FIT_SIT_UP_EXERCISE_NAME_MEDICINE_BALL_ALTERNATING_V_UP                  ((FIT_SIT_UP_EXERCISE_NAME)15)
#define FIT_SIT_UP_EXERCISE_NAME_MEDICINE_BALL_SIT_UP                            ((FIT_SIT_UP_EXERCISE_NAME)16)
#define FIT_SIT_UP_EXERCISE_NAME_MEDICINE_BALL_V_UP                              ((FIT_SIT_UP_EXERCISE_NAME)17)
#define FIT_SIT_UP_EXERCISE_NAME_MODIFIED_SIT_UP                                 ((FIT_SIT_UP_EXERCISE_NAME)18)
#define FIT_SIT_UP_EXERCISE_NAME_NEGATIVE_SIT_UP                                 ((FIT_SIT_UP_EXERCISE_NAME)19)
#define FIT_SIT_UP_EXERCISE_NAME_ONE_ARM_FULL_SIT_UP                             ((FIT_SIT_UP_EXERCISE_NAME)20)
#define FIT_SIT_UP_EXERCISE_NAME_RECLINING_CIRCLE                                ((FIT_SIT_UP_EXERCISE_NAME)21)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_RECLINING_CIRCLE                       ((FIT_SIT_UP_EXERCISE_NAME)22)
#define FIT_SIT_UP_EXERCISE_NAME_REVERSE_CURL_UP                                 ((FIT_SIT_UP_EXERCISE_NAME)23)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_REVERSE_CURL_UP                        ((FIT_SIT_UP_EXERCISE_NAME)24)
#define FIT_SIT_UP_EXERCISE_NAME_SINGLE_LEG_SWISS_BALL_JACKKNIFE                 ((FIT_SIT_UP_EXERCISE_NAME)25)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_SWISS_BALL_JACKKNIFE        ((FIT_SIT_UP_EXERCISE_NAME)26)
#define FIT_SIT_UP_EXERCISE_NAME_THE_TEASER                                      ((FIT_SIT_UP_EXERCISE_NAME)27)
#define FIT_SIT_UP_EXERCISE_NAME_THE_TEASER_WEIGHTED                             ((FIT_SIT_UP_EXERCISE_NAME)28)
#define FIT_SIT_UP_EXERCISE_NAME_THREE_PART_ROLL_DOWN                            ((FIT_SIT_UP_EXERCISE_NAME)29)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_THREE_PART_ROLL_DOWN                   ((FIT_SIT_UP_EXERCISE_NAME)30)
#define FIT_SIT_UP_EXERCISE_NAME_V_UP                                            ((FIT_SIT_UP_EXERCISE_NAME)31)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_V_UP                                   ((FIT_SIT_UP_EXERCISE_NAME)32)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_RUSSIAN_TWIST_ON_SWISS_BALL            ((FIT_SIT_UP_EXERCISE_NAME)33)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_SIT_UP                                 ((FIT_SIT_UP_EXERCISE_NAME)34)
#define FIT_SIT_UP_EXERCISE_NAME_X_ABS                                           ((FIT_SIT_UP_EXERCISE_NAME)35)
#define FIT_SIT_UP_EXERCISE_NAME_WEIGHTED_X_ABS                                  ((FIT_SIT_UP_EXERCISE_NAME)36)
#define FIT_SIT_UP_EXERCISE_NAME_SIT_UP                                          ((FIT_SIT_UP_EXERCISE_NAME)37)
#define FIT_SIT_UP_EXERCISE_NAME_COUNT                                           38

typedef FIT_UINT16 FIT_SQUAT_EXERCISE_NAME;
#define FIT_SQUAT_EXERCISE_NAME_INVALID                                          FIT_UINT16_INVALID
#define FIT_SQUAT_EXERCISE_NAME_LEG_PRESS                                        ((FIT_SQUAT_EXERCISE_NAME)0)
#define FIT_SQUAT_EXERCISE_NAME_BACK_SQUAT_WITH_BODY_BAR                         ((FIT_SQUAT_EXERCISE_NAME)1)
#define FIT_SQUAT_EXERCISE_NAME_BACK_SQUATS                                      ((FIT_SQUAT_EXERCISE_NAME)2)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_BACK_SQUATS                             ((FIT_SQUAT_EXERCISE_NAME)3)
#define FIT_SQUAT_EXERCISE_NAME_BALANCING_SQUAT                                  ((FIT_SQUAT_EXERCISE_NAME)4)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_BALANCING_SQUAT                         ((FIT_SQUAT_EXERCISE_NAME)5)
#define FIT_SQUAT_EXERCISE_NAME_BARBELL_BACK_SQUAT                               ((FIT_SQUAT_EXERCISE_NAME)6)
#define FIT_SQUAT_EXERCISE_NAME_BARBELL_BOX_SQUAT                                ((FIT_SQUAT_EXERCISE_NAME)7)
#define FIT_SQUAT_EXERCISE_NAME_BARBELL_FRONT_SQUAT                              ((FIT_SQUAT_EXERCISE_NAME)8)
#define FIT_SQUAT_EXERCISE_NAME_BARBELL_HACK_SQUAT                               ((FIT_SQUAT_EXERCISE_NAME)9)
#define FIT_SQUAT_EXERCISE_NAME_BARBELL_HANG_SQUAT_SNATCH                        ((FIT_SQUAT_EXERCISE_NAME)10)
#define FIT_SQUAT_EXERCISE_NAME_BARBELL_LATERAL_STEP_UP                          ((FIT_SQUAT_EXERCISE_NAME)11)
#define FIT_SQUAT_EXERCISE_NAME_BARBELL_QUARTER_SQUAT                            ((FIT_SQUAT_EXERCISE_NAME)12)
#define FIT_SQUAT_EXERCISE_NAME_BARBELL_SIFF_SQUAT                               ((FIT_SQUAT_EXERCISE_NAME)13)
#define FIT_SQUAT_EXERCISE_NAME_BARBELL_SQUAT_SNATCH                             ((FIT_SQUAT_EXERCISE_NAME)14)
#define FIT_SQUAT_EXERCISE_NAME_BARBELL_SQUAT_WITH_HEELS_RAISED                  ((FIT_SQUAT_EXERCISE_NAME)15)
#define FIT_SQUAT_EXERCISE_NAME_BARBELL_STEPOVER                                 ((FIT_SQUAT_EXERCISE_NAME)16)
#define FIT_SQUAT_EXERCISE_NAME_BARBELL_STEP_UP                                  ((FIT_SQUAT_EXERCISE_NAME)17)
#define FIT_SQUAT_EXERCISE_NAME_BENCH_SQUAT_WITH_ROTATIONAL_CHOP                 ((FIT_SQUAT_EXERCISE_NAME)18)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_BENCH_SQUAT_WITH_ROTATIONAL_CHOP        ((FIT_SQUAT_EXERCISE_NAME)19)
#define FIT_SQUAT_EXERCISE_NAME_BODY_WEIGHT_WALL_SQUAT                           ((FIT_SQUAT_EXERCISE_NAME)20)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_WALL_SQUAT                              ((FIT_SQUAT_EXERCISE_NAME)21)
#define FIT_SQUAT_EXERCISE_NAME_BOX_STEP_SQUAT                                   ((FIT_SQUAT_EXERCISE_NAME)22)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_BOX_STEP_SQUAT                          ((FIT_SQUAT_EXERCISE_NAME)23)
#define FIT_SQUAT_EXERCISE_NAME_BRACED_SQUAT                                     ((FIT_SQUAT_EXERCISE_NAME)24)
#define FIT_SQUAT_EXERCISE_NAME_CROSSED_ARM_BARBELL_FRONT_SQUAT                  ((FIT_SQUAT_EXERCISE_NAME)25)
#define FIT_SQUAT_EXERCISE_NAME_CROSSOVER_DUMBBELL_STEP_UP                       ((FIT_SQUAT_EXERCISE_NAME)26)
#define FIT_SQUAT_EXERCISE_NAME_DUMBBELL_FRONT_SQUAT                             ((FIT_SQUAT_EXERCISE_NAME)27)
#define FIT_SQUAT_EXERCISE_NAME_DUMBBELL_SPLIT_SQUAT                             ((FIT_SQUAT_EXERCISE_NAME)28)
#define FIT_SQUAT_EXERCISE_NAME_DUMBBELL_SQUAT                                   ((FIT_SQUAT_EXERCISE_NAME)29)
#define FIT_SQUAT_EXERCISE_NAME_DUMBBELL_SQUAT_CLEAN                             ((FIT_SQUAT_EXERCISE_NAME)30)
#define FIT_SQUAT_EXERCISE_NAME_DUMBBELL_STEPOVER                                ((FIT_SQUAT_EXERCISE_NAME)31)
#define FIT_SQUAT_EXERCISE_NAME_DUMBBELL_STEP_UP                                 ((FIT_SQUAT_EXERCISE_NAME)32)
#define FIT_SQUAT_EXERCISE_NAME_ELEVATED_SINGLE_LEG_SQUAT                        ((FIT_SQUAT_EXERCISE_NAME)33)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_ELEVATED_SINGLE_LEG_SQUAT               ((FIT_SQUAT_EXERCISE_NAME)34)
#define FIT_SQUAT_EXERCISE_NAME_FIGURE_FOUR_SQUATS                               ((FIT_SQUAT_EXERCISE_NAME)35)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_FIGURE_FOUR_SQUATS                      ((FIT_SQUAT_EXERCISE_NAME)36)
#define FIT_SQUAT_EXERCISE_NAME_GOBLET_SQUAT                                     ((FIT_SQUAT_EXERCISE_NAME)37)
#define FIT_SQUAT_EXERCISE_NAME_KETTLEBELL_SQUAT                                 ((FIT_SQUAT_EXERCISE_NAME)38)
#define FIT_SQUAT_EXERCISE_NAME_KETTLEBELL_SWING_OVERHEAD                        ((FIT_SQUAT_EXERCISE_NAME)39)
#define FIT_SQUAT_EXERCISE_NAME_KETTLEBELL_SWING_WITH_FLIP_TO_SQUAT              ((FIT_SQUAT_EXERCISE_NAME)40)
#define FIT_SQUAT_EXERCISE_NAME_LATERAL_DUMBBELL_STEP_UP                         ((FIT_SQUAT_EXERCISE_NAME)41)
#define FIT_SQUAT_EXERCISE_NAME_ONE_LEGGED_SQUAT                                 ((FIT_SQUAT_EXERCISE_NAME)42)
#define FIT_SQUAT_EXERCISE_NAME_OVERHEAD_DUMBBELL_SQUAT                          ((FIT_SQUAT_EXERCISE_NAME)43)
#define FIT_SQUAT_EXERCISE_NAME_OVERHEAD_SQUAT                                   ((FIT_SQUAT_EXERCISE_NAME)44)
#define FIT_SQUAT_EXERCISE_NAME_PARTIAL_SINGLE_LEG_SQUAT                         ((FIT_SQUAT_EXERCISE_NAME)45)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_PARTIAL_SINGLE_LEG_SQUAT                ((FIT_SQUAT_EXERCISE_NAME)46)
#define FIT_SQUAT_EXERCISE_NAME_PISTOL_SQUAT                                     ((FIT_SQUAT_EXERCISE_NAME)47)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_PISTOL_SQUAT                            ((FIT_SQUAT_EXERCISE_NAME)48)
#define FIT_SQUAT_EXERCISE_NAME_PLIE_SLIDES                                      ((FIT_SQUAT_EXERCISE_NAME)49)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_PLIE_SLIDES                             ((FIT_SQUAT_EXERCISE_NAME)50)
#define FIT_SQUAT_EXERCISE_NAME_PLIE_SQUAT                                       ((FIT_SQUAT_EXERCISE_NAME)51)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_PLIE_SQUAT                              ((FIT_SQUAT_EXERCISE_NAME)52)
#define FIT_SQUAT_EXERCISE_NAME_PRISONER_SQUAT                                   ((FIT_SQUAT_EXERCISE_NAME)53)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_PRISONER_SQUAT                          ((FIT_SQUAT_EXERCISE_NAME)54)
#define FIT_SQUAT_EXERCISE_NAME_SINGLE_LEG_BENCH_GET_UP                          ((FIT_SQUAT_EXERCISE_NAME)55)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_BENCH_GET_UP                 ((FIT_SQUAT_EXERCISE_NAME)56)
#define FIT_SQUAT_EXERCISE_NAME_SINGLE_LEG_BENCH_SQUAT                           ((FIT_SQUAT_EXERCISE_NAME)57)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_BENCH_SQUAT                  ((FIT_SQUAT_EXERCISE_NAME)58)
#define FIT_SQUAT_EXERCISE_NAME_SINGLE_LEG_SQUAT_ON_SWISS_BALL                   ((FIT_SQUAT_EXERCISE_NAME)59)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_SQUAT_ON_SWISS_BALL          ((FIT_SQUAT_EXERCISE_NAME)60)
#define FIT_SQUAT_EXERCISE_NAME_SQUAT                                            ((FIT_SQUAT_EXERCISE_NAME)61)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_SQUAT                                   ((FIT_SQUAT_EXERCISE_NAME)62)
#define FIT_SQUAT_EXERCISE_NAME_SQUATS_WITH_BAND                                 ((FIT_SQUAT_EXERCISE_NAME)63)
#define FIT_SQUAT_EXERCISE_NAME_STAGGERED_SQUAT                                  ((FIT_SQUAT_EXERCISE_NAME)64)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_STAGGERED_SQUAT                         ((FIT_SQUAT_EXERCISE_NAME)65)
#define FIT_SQUAT_EXERCISE_NAME_STEP_UP                                          ((FIT_SQUAT_EXERCISE_NAME)66)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_STEP_UP                                 ((FIT_SQUAT_EXERCISE_NAME)67)
#define FIT_SQUAT_EXERCISE_NAME_SUITCASE_SQUATS                                  ((FIT_SQUAT_EXERCISE_NAME)68)
#define FIT_SQUAT_EXERCISE_NAME_SUMO_SQUAT                                       ((FIT_SQUAT_EXERCISE_NAME)69)
#define FIT_SQUAT_EXERCISE_NAME_SUMO_SQUAT_SLIDE_IN                              ((FIT_SQUAT_EXERCISE_NAME)70)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_SUMO_SQUAT_SLIDE_IN                     ((FIT_SQUAT_EXERCISE_NAME)71)
#define FIT_SQUAT_EXERCISE_NAME_SUMO_SQUAT_TO_HIGH_PULL                          ((FIT_SQUAT_EXERCISE_NAME)72)
#define FIT_SQUAT_EXERCISE_NAME_SUMO_SQUAT_TO_STAND                              ((FIT_SQUAT_EXERCISE_NAME)73)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_SUMO_SQUAT_TO_STAND                     ((FIT_SQUAT_EXERCISE_NAME)74)
#define FIT_SQUAT_EXERCISE_NAME_SUMO_SQUAT_WITH_ROTATION                         ((FIT_SQUAT_EXERCISE_NAME)75)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_SUMO_SQUAT_WITH_ROTATION                ((FIT_SQUAT_EXERCISE_NAME)76)
#define FIT_SQUAT_EXERCISE_NAME_SWISS_BALL_BODY_WEIGHT_WALL_SQUAT                ((FIT_SQUAT_EXERCISE_NAME)77)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_SWISS_BALL_WALL_SQUAT                   ((FIT_SQUAT_EXERCISE_NAME)78)
#define FIT_SQUAT_EXERCISE_NAME_THRUSTERS                                        ((FIT_SQUAT_EXERCISE_NAME)79)
#define FIT_SQUAT_EXERCISE_NAME_UNEVEN_SQUAT                                     ((FIT_SQUAT_EXERCISE_NAME)80)
#define FIT_SQUAT_EXERCISE_NAME_WEIGHTED_UNEVEN_SQUAT                            ((FIT_SQUAT_EXERCISE_NAME)81)
#define FIT_SQUAT_EXERCISE_NAME_WAIST_SLIMMING_SQUAT                             ((FIT_SQUAT_EXERCISE_NAME)82)
#define FIT_SQUAT_EXERCISE_NAME_WALL_BALL                                        ((FIT_SQUAT_EXERCISE_NAME)83)
#define FIT_SQUAT_EXERCISE_NAME_WIDE_STANCE_BARBELL_SQUAT                        ((FIT_SQUAT_EXERCISE_NAME)84)
#define FIT_SQUAT_EXERCISE_NAME_WIDE_STANCE_GOBLET_SQUAT                         ((FIT_SQUAT_EXERCISE_NAME)85)
#define FIT_SQUAT_EXERCISE_NAME_ZERCHER_SQUAT                                    ((FIT_SQUAT_EXERCISE_NAME)86)
#define FIT_SQUAT_EXERCISE_NAME_COUNT                                            87

typedef FIT_UINT16 FIT_TOTAL_BODY_EXERCISE_NAME;
#define FIT_TOTAL_BODY_EXERCISE_NAME_INVALID                                     FIT_UINT16_INVALID
#define FIT_TOTAL_BODY_EXERCISE_NAME_BURPEE                                      ((FIT_TOTAL_BODY_EXERCISE_NAME)0)
#define FIT_TOTAL_BODY_EXERCISE_NAME_WEIGHTED_BURPEE                             ((FIT_TOTAL_BODY_EXERCISE_NAME)1)
#define FIT_TOTAL_BODY_EXERCISE_NAME_BURPEE_BOX_JUMP                             ((FIT_TOTAL_BODY_EXERCISE_NAME)2)
#define FIT_TOTAL_BODY_EXERCISE_NAME_WEIGHTED_BURPEE_BOX_JUMP                    ((FIT_TOTAL_BODY_EXERCISE_NAME)3)
#define FIT_TOTAL_BODY_EXERCISE_NAME_HIGH_PULL_BURPEE                            ((FIT_TOTAL_BODY_EXERCISE_NAME)4)
#define FIT_TOTAL_BODY_EXERCISE_NAME_MAN_MAKERS                                  ((FIT_TOTAL_BODY_EXERCISE_NAME)5)
#define FIT_TOTAL_BODY_EXERCISE_NAME_ONE_ARM_BURPEE                              ((FIT_TOTAL_BODY_EXERCISE_NAME)6)
#define FIT_TOTAL_BODY_EXERCISE_NAME_SQUAT_THRUSTS                               ((FIT_TOTAL_BODY_EXERCISE_NAME)7)
#define FIT_TOTAL_BODY_EXERCISE_NAME_WEIGHTED_SQUAT_THRUSTS                      ((FIT_TOTAL_BODY_EXERCISE_NAME)8)
#define FIT_TOTAL_BODY_EXERCISE_NAME_SQUAT_PLANK_PUSH_UP                         ((FIT_TOTAL_BODY_EXERCISE_NAME)9)
#define FIT_TOTAL_BODY_EXERCISE_NAME_WEIGHTED_SQUAT_PLANK_PUSH_UP                ((FIT_TOTAL_BODY_EXERCISE_NAME)10)
#define FIT_TOTAL_BODY_EXERCISE_NAME_STANDING_T_ROTATION_BALANCE                 ((FIT_TOTAL_BODY_EXERCISE_NAME)11)
#define FIT_TOTAL_BODY_EXERCISE_NAME_WEIGHTED_STANDING_T_ROTATION_BALANCE        ((FIT_TOTAL_BODY_EXERCISE_NAME)12)
#define FIT_TOTAL_BODY_EXERCISE_NAME_COUNT                                       13

typedef FIT_UINT16 FIT_TRICEPS_EXTENSION_EXERCISE_NAME;
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_INVALID                              FIT_UINT16_INVALID
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_BENCH_DIP                            ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)0)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_WEIGHTED_BENCH_DIP                   ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)1)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_BODY_WEIGHT_DIP                      ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)2)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_CABLE_KICKBACK                       ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)3)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_CABLE_LYING_TRICEPS_EXTENSION        ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)4)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_CABLE_OVERHEAD_TRICEPS_EXTENSION     ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)5)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_DUMBBELL_KICKBACK                    ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)6)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_DUMBBELL_LYING_TRICEPS_EXTENSION     ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)7)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_EZ_BAR_OVERHEAD_TRICEPS_EXTENSION    ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)8)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_INCLINE_DIP                          ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)9)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_WEIGHTED_INCLINE_DIP                 ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)10)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_INCLINE_EZ_BAR_LYING_TRICEPS_EXTENSION ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)11)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_LYING_DUMBBELL_PULLOVER_TO_EXTENSION ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)12)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_LYING_EZ_BAR_TRICEPS_EXTENSION       ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)13)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_LYING_TRICEPS_EXTENSION_TO_CLOSE_GRIP_BENCH_PRESS ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)14)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_OVERHEAD_DUMBBELL_TRICEPS_EXTENSION  ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)15)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_RECLINING_TRICEPS_PRESS              ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)16)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_REVERSE_GRIP_PRESSDOWN               ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)17)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_REVERSE_GRIP_TRICEPS_PRESSDOWN       ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)18)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_ROPE_PRESSDOWN                       ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)19)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_SEATED_BARBELL_OVERHEAD_TRICEPS_EXTENSION ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)20)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_SEATED_DUMBBELL_OVERHEAD_TRICEPS_EXTENSION ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)21)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_SEATED_EZ_BAR_OVERHEAD_TRICEPS_EXTENSION ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)22)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_SEATED_SINGLE_ARM_OVERHEAD_DUMBBELL_EXTENSION ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)23)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_SINGLE_ARM_DUMBBELL_OVERHEAD_TRICEPS_EXTENSION ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)24)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_SINGLE_DUMBBELL_SEATED_OVERHEAD_TRICEPS_EXTENSION ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)25)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_SINGLE_LEG_BENCH_DIP_AND_KICK        ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)26)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_BENCH_DIP_AND_KICK ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)27)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_SINGLE_LEG_DIP                       ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)28)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_WEIGHTED_SINGLE_LEG_DIP              ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)29)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_STATIC_LYING_TRICEPS_EXTENSION       ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)30)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_SUSPENDED_DIP                        ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)31)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_WEIGHTED_SUSPENDED_DIP               ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)32)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_SWISS_BALL_DUMBBELL_LYING_TRICEPS_EXTENSION ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)33)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_SWISS_BALL_EZ_BAR_LYING_TRICEPS_EXTENSION ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)34)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_SWISS_BALL_EZ_BAR_OVERHEAD_TRICEPS_EXTENSION ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)35)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_TABLETOP_DIP                         ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)36)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_WEIGHTED_TABLETOP_DIP                ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)37)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_TRICEPS_EXTENSION_ON_FLOOR           ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)38)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_TRICEPS_PRESSDOWN                    ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)39)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_WEIGHTED_DIP                         ((FIT_TRICEPS_EXTENSION_EXERCISE_NAME)40)
#define FIT_TRICEPS_EXTENSION_EXERCISE_NAME_COUNT                                41

typedef FIT_UINT16 FIT_WARM_UP_EXERCISE_NAME;
#define FIT_WARM_UP_EXERCISE_NAME_INVALID                                        FIT_UINT16_INVALID
#define FIT_WARM_UP_EXERCISE_NAME_QUADRUPED_ROCKING                              ((FIT_WARM_UP_EXERCISE_NAME)0)
#define FIT_WARM_UP_EXERCISE_NAME_NECK_TILTS                                     ((FIT_WARM_UP_EXERCISE_NAME)1)
#define FIT_WARM_UP_EXERCISE_NAME_ANKLE_CIRCLES                                  ((FIT_WARM_UP_EXERCISE_NAME)2)
#define FIT_WARM_UP_EXERCISE_NAME_ANKLE_DORSIFLEXION_WITH_BAND                   ((FIT_WARM_UP_EXERCISE_NAME)3)
#define FIT_WARM_UP_EXERCISE_NAME_ANKLE_INTERNAL_ROTATION                        ((FIT_WARM_UP_EXERCISE_NAME)4)
#define FIT_WARM_UP_EXERCISE_NAME_ARM_CIRCLES                                    ((FIT_WARM_UP_EXERCISE_NAME)5)
#define FIT_WARM_UP_EXERCISE_NAME_BENT_OVER_REACH_TO_SKY                         ((FIT_WARM_UP_EXERCISE_NAME)6)
#define FIT_WARM_UP_EXERCISE_NAME_CAT_CAMEL                                      ((FIT_WARM_UP_EXERCISE_NAME)7)
#define FIT_WARM_UP_EXERCISE_NAME_ELBOW_TO_FOOT_LUNGE                            ((FIT_WARM_UP_EXERCISE_NAME)8)
#define FIT_WARM_UP_EXERCISE_NAME_FORWARD_AND_BACKWARD_LEG_SWINGS                ((FIT_WARM_UP_EXERCISE_NAME)9)
#define FIT_WARM_UP_EXERCISE_NAME_GROINERS                                       ((FIT_WARM_UP_EXERCISE_NAME)10)
#define FIT_WARM_UP_EXERCISE_NAME_INVERTED_HAMSTRING_STRETCH                     ((FIT_WARM_UP_EXERCISE_NAME)11)
#define FIT_WARM_UP_EXERCISE_NAME_LATERAL_DUCK_UNDER                             ((FIT_WARM_UP_EXERCISE_NAME)12)
#define FIT_WARM_UP_EXERCISE_NAME_NECK_ROTATIONS                                 ((FIT_WARM_UP_EXERCISE_NAME)13)
#define FIT_WARM_UP_EXERCISE_NAME_OPPOSITE_ARM_AND_LEG_BALANCE                   ((FIT_WARM_UP_EXERCISE_NAME)14)
#define FIT_WARM_UP_EXERCISE_NAME_REACH_ROLL_AND_LIFT                            ((FIT_WARM_UP_EXERCISE_NAME)15)
#define FIT_WARM_UP_EXERCISE_NAME_SCORPION                                       ((FIT_WARM_UP_EXERCISE_NAME)16)
#define FIT_WARM_UP_EXERCISE_NAME_SHOULDER_CIRCLES                               ((FIT_WARM_UP_EXERCISE_NAME)17)
#define FIT_WARM_UP_EXERCISE_NAME_SIDE_TO_SIDE_LEG_SWINGS                        ((FIT_WARM_UP_EXERCISE_NAME)18)
#define FIT_WARM_UP_EXERCISE_NAME_SLEEPER_STRETCH                                ((FIT_WARM_UP_EXERCISE_NAME)19)
#define FIT_WARM_UP_EXERCISE_NAME_SLIDE_OUT                                      ((FIT_WARM_UP_EXERCISE_NAME)20)
#define FIT_WARM_UP_EXERCISE_NAME_SWISS_BALL_HIP_CROSSOVER                       ((FIT_WARM_UP_EXERCISE_NAME)21)
#define FIT_WARM_UP_EXERCISE_NAME_SWISS_BALL_REACH_ROLL_AND_LIFT                 ((FIT_WARM_UP_EXERCISE_NAME)22)
#define FIT_WARM_UP_EXERCISE_NAME_SWISS_BALL_WINDSHIELD_WIPERS                   ((FIT_WARM_UP_EXERCISE_NAME)23)
#define FIT_WARM_UP_EXERCISE_NAME_THORACIC_ROTATION                              ((FIT_WARM_UP_EXERCISE_NAME)24)
#define FIT_WARM_UP_EXERCISE_NAME_WALKING_HIGH_KICKS                             ((FIT_WARM_UP_EXERCISE_NAME)25)
#define FIT_WARM_UP_EXERCISE_NAME_WALKING_HIGH_KNEES                             ((FIT_WARM_UP_EXERCISE_NAME)26)
#define FIT_WARM_UP_EXERCISE_NAME_WALKING_KNEE_HUGS                              ((FIT_WARM_UP_EXERCISE_NAME)27)
#define FIT_WARM_UP_EXERCISE_NAME_WALKING_LEG_CRADLES                            ((FIT_WARM_UP_EXERCISE_NAME)28)
#define FIT_WARM_UP_EXERCISE_NAME_WALKOUT                                        ((FIT_WARM_UP_EXERCISE_NAME)29)
#define FIT_WARM_UP_EXERCISE_NAME_WALKOUT_FROM_PUSH_UP_POSITION                  ((FIT_WARM_UP_EXERCISE_NAME)30)
#define FIT_WARM_UP_EXERCISE_NAME_COUNT                                          31

typedef FIT_UINT16 FIT_RUN_EXERCISE_NAME;
#define FIT_RUN_EXERCISE_NAME_INVALID                                            FIT_UINT16_INVALID
#define FIT_RUN_EXERCISE_NAME_RUN                                                ((FIT_RUN_EXERCISE_NAME)0)
#define FIT_RUN_EXERCISE_NAME_WALK                                               ((FIT_RUN_EXERCISE_NAME)1)
#define FIT_RUN_EXERCISE_NAME_JOG                                                ((FIT_RUN_EXERCISE_NAME)2)
#define FIT_RUN_EXERCISE_NAME_SPRINT                                             ((FIT_RUN_EXERCISE_NAME)3)
#define FIT_RUN_EXERCISE_NAME_COUNT                                              4

typedef FIT_ENUM FIT_WATER_TYPE;
#define FIT_WATER_TYPE_INVALID                                                   FIT_ENUM_INVALID
#define FIT_WATER_TYPE_FRESH                                                     ((FIT_WATER_TYPE)0)
#define FIT_WATER_TYPE_SALT                                                      ((FIT_WATER_TYPE)1)
#define FIT_WATER_TYPE_EN13319                                                   ((FIT_WATER_TYPE)2)
#define FIT_WATER_TYPE_CUSTOM                                                    ((FIT_WATER_TYPE)3)
#define FIT_WATER_TYPE_COUNT                                                     4

typedef FIT_ENUM FIT_TISSUE_MODEL_TYPE;
#define FIT_TISSUE_MODEL_TYPE_INVALID                                            FIT_ENUM_INVALID
#define FIT_TISSUE_MODEL_TYPE_ZHL_16C                                            ((FIT_TISSUE_MODEL_TYPE)0) // Buhlmann's decompression algorithm, version C
#define FIT_TISSUE_MODEL_TYPE_COUNT                                              1

typedef FIT_ENUM FIT_DIVE_GAS_STATUS;
#define FIT_DIVE_GAS_STATUS_INVALID                                              FIT_ENUM_INVALID
#define FIT_DIVE_GAS_STATUS_DISABLED                                             ((FIT_DIVE_GAS_STATUS)0)
#define FIT_DIVE_GAS_STATUS_ENABLED                                              ((FIT_DIVE_GAS_STATUS)1)
#define FIT_DIVE_GAS_STATUS_BACKUP_ONLY                                          ((FIT_DIVE_GAS_STATUS)2)
#define FIT_DIVE_GAS_STATUS_COUNT                                                3

typedef FIT_ENUM FIT_DIVE_ALARM_TYPE;
#define FIT_DIVE_ALARM_TYPE_INVALID                                              FIT_ENUM_INVALID
#define FIT_DIVE_ALARM_TYPE_DEPTH                                                ((FIT_DIVE_ALARM_TYPE)0)
#define FIT_DIVE_ALARM_TYPE_TIME                                                 ((FIT_DIVE_ALARM_TYPE)1)
#define FIT_DIVE_ALARM_TYPE_COUNT                                                2

typedef FIT_ENUM FIT_DIVE_BACKLIGHT_MODE;
#define FIT_DIVE_BACKLIGHT_MODE_INVALID                                          FIT_ENUM_INVALID
#define FIT_DIVE_BACKLIGHT_MODE_AT_DEPTH                                         ((FIT_DIVE_BACKLIGHT_MODE)0)
#define FIT_DIVE_BACKLIGHT_MODE_ALWAYS_ON                                        ((FIT_DIVE_BACKLIGHT_MODE)1)
#define FIT_DIVE_BACKLIGHT_MODE_COUNT                                            2


///////////////////////////////////////////////////////////////////////
// Message Conversion Structures
///////////////////////////////////////////////////////////////////////

typedef struct
{
   FIT_UINT8 base_type;
   FIT_UINT8 offset_in;
   FIT_UINT8 offset_local;
   FIT_UINT8 size;
   FIT_UINT8 num;
} FIT_FIELD_CONVERT;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_FIELD_CONVERT fields[90];
} FIT_MESG_CONVERT;


///////////////////////////////////////////////////////////////////////
// Messages
///////////////////////////////////////////////////////////////////////


// file_id message

#define FIT_FILE_ID_MESG_SIZE                                                   25//35
#define FIT_FILE_ID_MESG_DEF_SIZE                                               26
#define FIT_FILE_ID_MESG_PRODUCT_NAME_COUNT                                     10//20

typedef struct
{
   FIT_UINT32Z serial_number; //
   FIT_DATE_TIME time_created; // Only set for files that are can be created/erased.
   FIT_STRING product_name[FIT_FILE_ID_MESG_PRODUCT_NAME_COUNT]; // Optional free form string to indicate the devices name or model
   FIT_MANUFACTURER manufacturer; //
   FIT_UINT16 product; //
   FIT_UINT16 number; // Only set for files that are not created/erased.
   FIT_FILE type; //
} FIT_FILE_ID_MESG;

typedef enum
{
   FIT_FILE_ID_FIELD_NUM_SERIAL_NUMBER = 3,
   FIT_FILE_ID_FIELD_NUM_TIME_CREATED = 4,
   FIT_FILE_ID_FIELD_NUM_PRODUCT_NAME = 8,
   FIT_FILE_ID_FIELD_NUM_MANUFACTURER = 1,
   FIT_FILE_ID_FIELD_NUM_PRODUCT = 2,
   FIT_FILE_ID_FIELD_NUM_NUMBER = 5,
   FIT_FILE_ID_FIELD_NUM_TYPE = 0
} FIT_FILE_ID_FIELD_NUM;

typedef enum
{
   FIT_FILE_ID_MESG_SERIAL_NUMBER,
   FIT_FILE_ID_MESG_TIME_CREATED,
   FIT_FILE_ID_MESG_PRODUCT_NAME,
   FIT_FILE_ID_MESG_MANUFACTURER,
   FIT_FILE_ID_MESG_PRODUCT,
   FIT_FILE_ID_MESG_NUMBER,
   FIT_FILE_ID_MESG_TYPE,
   FIT_FILE_ID_MESG_FIELDS
} FIT_FILE_ID_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_FILE_ID_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_FILE_ID_MESG_DEF;

// file_creator message

#define FIT_FILE_CREATOR_MESG_SIZE                                              3
#define FIT_FILE_CREATOR_MESG_DEF_SIZE                                          11

typedef struct
{
   FIT_UINT16 software_version; //
   FIT_UINT8 hardware_version; //
} FIT_FILE_CREATOR_MESG;

typedef enum
{
   FIT_FILE_CREATOR_FIELD_NUM_SOFTWARE_VERSION = 0,
   FIT_FILE_CREATOR_FIELD_NUM_HARDWARE_VERSION = 1
} FIT_FILE_CREATOR_FIELD_NUM;

typedef enum
{
   FIT_FILE_CREATOR_MESG_SOFTWARE_VERSION,
   FIT_FILE_CREATOR_MESG_HARDWARE_VERSION,
   FIT_FILE_CREATOR_MESG_FIELDS
} FIT_FILE_CREATOR_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_FILE_CREATOR_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_FILE_CREATOR_MESG_DEF;



// user_profile message

#define FIT_USER_PROFILE_MESG_SIZE                                              50
#define FIT_USER_PROFILE_MESG_DEF_SIZE                                          80
#define FIT_USER_PROFILE_MESG_FRIENDLY_NAME_COUNT                               16
#define FIT_USER_PROFILE_MESG_GLOBAL_ID_COUNT                                   6

typedef struct
{
   FIT_STRING friendly_name[FIT_USER_PROFILE_MESG_FRIENDLY_NAME_COUNT]; //
   FIT_MESSAGE_INDEX message_index; //
   FIT_UINT16 weight; // 10 * kg + 0,
   FIT_USER_LOCAL_ID local_id; //
   FIT_UINT16 user_running_step_length; // 1000 * m + 0, User defined running step length set to 0 for auto length
   FIT_UINT16 user_walking_step_length; // 1000 * m + 0, User defined walking step length set to 0 for auto length
   FIT_GENDER gender; //
   FIT_UINT8 age; // 1 * years + 0,
   FIT_UINT8 height; // 100 * m + 0,
   FIT_LANGUAGE language; //
   FIT_DISPLAY_MEASURE elev_setting; //
   FIT_DISPLAY_MEASURE weight_setting; //
   FIT_UINT8 resting_heart_rate; // 1 * bpm + 0,
   FIT_UINT8 default_max_running_heart_rate; // 1 * bpm + 0,
   FIT_UINT8 default_max_biking_heart_rate; // 1 * bpm + 0,
   FIT_UINT8 default_max_heart_rate; // 1 * bpm + 0,
   FIT_DISPLAY_HEART hr_setting; //
   FIT_DISPLAY_MEASURE speed_setting; //
   FIT_DISPLAY_MEASURE dist_setting; //
   FIT_DISPLAY_POWER power_setting; //
   FIT_ACTIVITY_CLASS activity_class; //
   FIT_DISPLAY_POSITION position_setting; //
   FIT_DISPLAY_MEASURE temperature_setting; //
   FIT_BYTE global_id[FIT_USER_PROFILE_MESG_GLOBAL_ID_COUNT]; //
   FIT_DISPLAY_MEASURE height_setting; //
} FIT_USER_PROFILE_MESG;

typedef enum
{
   FIT_USER_PROFILE_FIELD_NUM_FRIENDLY_NAME = 0,
   FIT_USER_PROFILE_FIELD_NUM_MESSAGE_INDEX = 254,
   FIT_USER_PROFILE_FIELD_NUM_WEIGHT = 4,
   FIT_USER_PROFILE_FIELD_NUM_LOCAL_ID = 22,
   FIT_USER_PROFILE_FIELD_NUM_USER_RUNNING_STEP_LENGTH = 31,
   FIT_USER_PROFILE_FIELD_NUM_USER_WALKING_STEP_LENGTH = 32,
   FIT_USER_PROFILE_FIELD_NUM_GENDER = 1,
   FIT_USER_PROFILE_FIELD_NUM_AGE = 2,
   FIT_USER_PROFILE_FIELD_NUM_HEIGHT = 3,
   FIT_USER_PROFILE_FIELD_NUM_LANGUAGE = 5,
   FIT_USER_PROFILE_FIELD_NUM_ELEV_SETTING = 6,
   FIT_USER_PROFILE_FIELD_NUM_WEIGHT_SETTING = 7,
   FIT_USER_PROFILE_FIELD_NUM_RESTING_HEART_RATE = 8,
   FIT_USER_PROFILE_FIELD_NUM_DEFAULT_MAX_RUNNING_HEART_RATE = 9,
   FIT_USER_PROFILE_FIELD_NUM_DEFAULT_MAX_BIKING_HEART_RATE = 10,
   FIT_USER_PROFILE_FIELD_NUM_DEFAULT_MAX_HEART_RATE = 11,
   FIT_USER_PROFILE_FIELD_NUM_HR_SETTING = 12,
   FIT_USER_PROFILE_FIELD_NUM_SPEED_SETTING = 13,
   FIT_USER_PROFILE_FIELD_NUM_DIST_SETTING = 14,
   FIT_USER_PROFILE_FIELD_NUM_POWER_SETTING = 16,
   FIT_USER_PROFILE_FIELD_NUM_ACTIVITY_CLASS = 17,
   FIT_USER_PROFILE_FIELD_NUM_POSITION_SETTING = 18,
   FIT_USER_PROFILE_FIELD_NUM_TEMPERATURE_SETTING = 21,
   FIT_USER_PROFILE_FIELD_NUM_GLOBAL_ID = 23,
   FIT_USER_PROFILE_FIELD_NUM_HEIGHT_SETTING = 30
} FIT_USER_PROFILE_FIELD_NUM;

typedef enum
{
   FIT_USER_PROFILE_MESG_FRIENDLY_NAME,
   FIT_USER_PROFILE_MESG_MESSAGE_INDEX,
   FIT_USER_PROFILE_MESG_WEIGHT,
   FIT_USER_PROFILE_MESG_LOCAL_ID,
   FIT_USER_PROFILE_MESG_USER_RUNNING_STEP_LENGTH,
   FIT_USER_PROFILE_MESG_USER_WALKING_STEP_LENGTH,
   FIT_USER_PROFILE_MESG_GENDER,
   FIT_USER_PROFILE_MESG_AGE,
   FIT_USER_PROFILE_MESG_HEIGHT,
   FIT_USER_PROFILE_MESG_LANGUAGE,
   FIT_USER_PROFILE_MESG_ELEV_SETTING,
   FIT_USER_PROFILE_MESG_WEIGHT_SETTING,
   FIT_USER_PROFILE_MESG_RESTING_HEART_RATE,
   FIT_USER_PROFILE_MESG_DEFAULT_MAX_RUNNING_HEART_RATE,
   FIT_USER_PROFILE_MESG_DEFAULT_MAX_BIKING_HEART_RATE,
   FIT_USER_PROFILE_MESG_DEFAULT_MAX_HEART_RATE,
   FIT_USER_PROFILE_MESG_HR_SETTING,
   FIT_USER_PROFILE_MESG_SPEED_SETTING,
   FIT_USER_PROFILE_MESG_DIST_SETTING,
   FIT_USER_PROFILE_MESG_POWER_SETTING,
   FIT_USER_PROFILE_MESG_ACTIVITY_CLASS,
   FIT_USER_PROFILE_MESG_POSITION_SETTING,
   FIT_USER_PROFILE_MESG_TEMPERATURE_SETTING,
   FIT_USER_PROFILE_MESG_GLOBAL_ID,
   FIT_USER_PROFILE_MESG_HEIGHT_SETTING,
   FIT_USER_PROFILE_MESG_FIELDS
} FIT_USER_PROFILE_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_USER_PROFILE_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_USER_PROFILE_MESG_DEF;


// hr_zone message

#define FIT_HR_ZONE_MESG_SIZE                                                   19
#define FIT_HR_ZONE_MESG_DEF_SIZE                                               14
#define FIT_HR_ZONE_MESG_NAME_COUNT                                             16

typedef struct
{
   FIT_STRING name[FIT_HR_ZONE_MESG_NAME_COUNT]; //
   FIT_MESSAGE_INDEX message_index; //
   FIT_UINT8 high_bpm; // 1 * bpm + 0,
} FIT_HR_ZONE_MESG;

typedef enum
{
   FIT_HR_ZONE_FIELD_NUM_NAME = 2,
   FIT_HR_ZONE_FIELD_NUM_MESSAGE_INDEX = 254,
   FIT_HR_ZONE_FIELD_NUM_HIGH_BPM = 1
} FIT_HR_ZONE_FIELD_NUM;

typedef enum
{
   FIT_HR_ZONE_MESG_NAME,
   FIT_HR_ZONE_MESG_MESSAGE_INDEX,
   FIT_HR_ZONE_MESG_HIGH_BPM,
   FIT_HR_ZONE_MESG_FIELDS
} FIT_HR_ZONE_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_HR_ZONE_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_HR_ZONE_MESG_DEF;

// speed_zone message

#define FIT_SPEED_ZONE_MESG_SIZE                                                20
#define FIT_SPEED_ZONE_MESG_DEF_SIZE                                            14
#define FIT_SPEED_ZONE_MESG_NAME_COUNT                                          16

typedef struct
{
   FIT_STRING name[FIT_SPEED_ZONE_MESG_NAME_COUNT]; //
   FIT_MESSAGE_INDEX message_index; //
   FIT_UINT16 high_value; // 1000 * m/s + 0,
} FIT_SPEED_ZONE_MESG;

typedef enum
{
   FIT_SPEED_ZONE_FIELD_NUM_NAME = 1,
   FIT_SPEED_ZONE_FIELD_NUM_MESSAGE_INDEX = 254,
   FIT_SPEED_ZONE_FIELD_NUM_HIGH_VALUE = 0
} FIT_SPEED_ZONE_FIELD_NUM;

typedef enum
{
   FIT_SPEED_ZONE_MESG_NAME,
   FIT_SPEED_ZONE_MESG_MESSAGE_INDEX,
   FIT_SPEED_ZONE_MESG_HIGH_VALUE,
   FIT_SPEED_ZONE_MESG_FIELDS
} FIT_SPEED_ZONE_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_SPEED_ZONE_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_SPEED_ZONE_MESG_DEF;

// cadence_zone message

#define FIT_CADENCE_ZONE_MESG_SIZE                                              19
#define FIT_CADENCE_ZONE_MESG_DEF_SIZE                                          14
#define FIT_CADENCE_ZONE_MESG_NAME_COUNT                                        16

typedef struct
{
   FIT_STRING name[FIT_CADENCE_ZONE_MESG_NAME_COUNT]; //
   FIT_MESSAGE_INDEX message_index; //
   FIT_UINT8 high_value; // 1 * rpm + 0,
} FIT_CADENCE_ZONE_MESG;

typedef enum
{
   FIT_CADENCE_ZONE_FIELD_NUM_NAME = 1,
   FIT_CADENCE_ZONE_FIELD_NUM_MESSAGE_INDEX = 254,
   FIT_CADENCE_ZONE_FIELD_NUM_HIGH_VALUE = 0
} FIT_CADENCE_ZONE_FIELD_NUM;

typedef enum
{
   FIT_CADENCE_ZONE_MESG_NAME,
   FIT_CADENCE_ZONE_MESG_MESSAGE_INDEX,
   FIT_CADENCE_ZONE_MESG_HIGH_VALUE,
   FIT_CADENCE_ZONE_MESG_FIELDS
} FIT_CADENCE_ZONE_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_CADENCE_ZONE_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_CADENCE_ZONE_MESG_DEF;

// power_zone message

#define FIT_POWER_ZONE_MESG_SIZE                                                20
#define FIT_POWER_ZONE_MESG_DEF_SIZE                                            14
#define FIT_POWER_ZONE_MESG_NAME_COUNT                                          16

typedef struct
{
   FIT_STRING name[FIT_POWER_ZONE_MESG_NAME_COUNT]; //
   FIT_MESSAGE_INDEX message_index; //
   FIT_UINT16 high_value; // 1 * watts + 0,
} FIT_POWER_ZONE_MESG;

typedef enum
{
   FIT_POWER_ZONE_FIELD_NUM_NAME = 2,
   FIT_POWER_ZONE_FIELD_NUM_MESSAGE_INDEX = 254,
   FIT_POWER_ZONE_FIELD_NUM_HIGH_VALUE = 1
} FIT_POWER_ZONE_FIELD_NUM;

typedef enum
{
   FIT_POWER_ZONE_MESG_NAME,
   FIT_POWER_ZONE_MESG_MESSAGE_INDEX,
   FIT_POWER_ZONE_MESG_HIGH_VALUE,
   FIT_POWER_ZONE_MESG_FIELDS
} FIT_POWER_ZONE_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_POWER_ZONE_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_POWER_ZONE_MESG_DEF;

// met_zone message

#define FIT_MET_ZONE_MESG_SIZE                                                  6
#define FIT_MET_ZONE_MESG_DEF_SIZE                                              17

typedef struct
{
   FIT_MESSAGE_INDEX message_index; //
   FIT_UINT16 calories; // 10 * kcal / min + 0,
   FIT_UINT8 high_bpm; //
   FIT_UINT8 fat_calories; // 10 * kcal / min + 0,
} FIT_MET_ZONE_MESG;

typedef enum
{
   FIT_MET_ZONE_FIELD_NUM_MESSAGE_INDEX = 254,
   FIT_MET_ZONE_FIELD_NUM_CALORIES = 2,
   FIT_MET_ZONE_FIELD_NUM_HIGH_BPM = 1,
   FIT_MET_ZONE_FIELD_NUM_FAT_CALORIES = 3
} FIT_MET_ZONE_FIELD_NUM;

typedef enum
{
   FIT_MET_ZONE_MESG_MESSAGE_INDEX,
   FIT_MET_ZONE_MESG_CALORIES,
   FIT_MET_ZONE_MESG_HIGH_BPM,
   FIT_MET_ZONE_MESG_FAT_CALORIES,
   FIT_MET_ZONE_MESG_FIELDS
} FIT_MET_ZONE_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_MET_ZONE_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_MET_ZONE_MESG_DEF;


// activity message

#define FIT_ACTIVITY_MESG_SIZE                                                  18
#define FIT_ACTIVITY_MESG_DEF_SIZE                                              29

typedef struct
{
   FIT_DATE_TIME timestamp; //
   FIT_UINT32 total_timer_time; // 1000 * s + 0, Exclude pauses
   FIT_LOCAL_DATE_TIME local_timestamp; // timestamp epoch expressed in local time, used to convert activity timestamps to local time 
   FIT_UINT16 num_sessions; //
   FIT_ACTIVITY type; //
   FIT_EVENT event; //
   FIT_EVENT_TYPE event_type; //
   FIT_UINT8 event_group; //
} FIT_ACTIVITY_MESG;

typedef enum
{
   FIT_ACTIVITY_FIELD_NUM_TIMESTAMP = 253,
   FIT_ACTIVITY_FIELD_NUM_TOTAL_TIMER_TIME = 0,
   FIT_ACTIVITY_FIELD_NUM_LOCAL_TIMESTAMP = 5,
   FIT_ACTIVITY_FIELD_NUM_NUM_SESSIONS = 1,
   FIT_ACTIVITY_FIELD_NUM_TYPE = 2,
   FIT_ACTIVITY_FIELD_NUM_EVENT = 3,
   FIT_ACTIVITY_FIELD_NUM_EVENT_TYPE = 4,
   FIT_ACTIVITY_FIELD_NUM_EVENT_GROUP = 6
} FIT_ACTIVITY_FIELD_NUM;

typedef enum
{
   FIT_ACTIVITY_MESG_TIMESTAMP,
   FIT_ACTIVITY_MESG_TOTAL_TIMER_TIME,
   FIT_ACTIVITY_MESG_LOCAL_TIMESTAMP,
   FIT_ACTIVITY_MESG_NUM_SESSIONS,
   FIT_ACTIVITY_MESG_TYPE,
   FIT_ACTIVITY_MESG_EVENT,
   FIT_ACTIVITY_MESG_EVENT_TYPE,
   FIT_ACTIVITY_MESG_EVENT_GROUP,
   FIT_ACTIVITY_MESG_FIELDS
} FIT_ACTIVITY_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_ACTIVITY_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_ACTIVITY_MESG_DEF;

// session message

#define FIT_SESSION_MESG_SIZE                                                   117//89//207
#define FIT_SESSION_MESG_DEF_SIZE                                               113 // 36*3+5 = 107, 275
#define FIT_SESSION_MESG_TIME_IN_HR_ZONE_COUNT                                  5
//#define FIT_SESSION_MESG_TIME_IN_SPEED_ZONE_COUNT                               1
//#define FIT_SESSION_MESG_TIME_IN_CADENCE_ZONE_COUNT                             1
#define FIT_SESSION_MESG_TIME_IN_POWER_ZONE_COUNT                               7
//#define FIT_SESSION_MESG_STROKE_COUNT_COUNT                                     1
//#define FIT_SESSION_MESG_ZONE_COUNT_COUNT                                       1
//#define FIT_SESSION_MESG_OPPONENT_NAME_COUNT                                    1

typedef struct
{
   FIT_DATE_TIME timestamp; // 1 * s + 0, Sesson end time.
   FIT_DATE_TIME start_time; //
   FIT_SINT32 start_position_lat; // 1 * semicircles + 0,
   FIT_SINT32 start_position_long; // 1 * semicircles + 0,
   FIT_UINT32 total_elapsed_time; // 1000 * s + 0, Time (includes pauses)
   FIT_UINT32 total_timer_time; // 1000 * s + 0, Timer Time (excludes pauses)
   FIT_UINT32 total_distance; // 100 * m + 0,
//   FIT_UINT32 total_cycles; // 1 * cycles + 0,
//   FIT_SINT32 nec_lat; // 1 * semicircles + 0,
//   FIT_SINT32 nec_long; // 1 * semicircles + 0,
//   FIT_SINT32 swc_lat; // 1 * semicircles + 0,
//   FIT_SINT32 swc_long; // 1 * semicircles + 0,   
//   FIT_UINT32 avg_stroke_count; // 10 * strokes/lap + 0,   
//   FIT_UINT32 total_work; // 1 * J + 0,
//   FIT_UINT32 total_moving_time; // 1000 * s + 0,
   FIT_UINT32 time_in_hr_zone[FIT_SESSION_MESG_TIME_IN_HR_ZONE_COUNT]; // 1000 * s + 0,
//   FIT_UINT32 time_in_speed_zone[FIT_SESSION_MESG_TIME_IN_SPEED_ZONE_COUNT]; // 1000 * s + 0,
//   FIT_UINT32 time_in_cadence_zone[FIT_SESSION_MESG_TIME_IN_CADENCE_ZONE_COUNT]; // 1000 * s + 0,
   FIT_UINT32 time_in_power_zone[FIT_SESSION_MESG_TIME_IN_POWER_ZONE_COUNT]; // 1000 * s + 0,
//   FIT_UINT32 avg_lap_time; // 1000 * s + 0,
//   FIT_UINT32 enhanced_avg_speed; // 1000 * m/s + 0, total_distance / total_timer_time
//   FIT_UINT32 enhanced_max_speed; // 1000 * m/s + 0,
//   FIT_UINT32 enhanced_avg_altitude; // 5 * m + 500,
//   FIT_UINT32 enhanced_min_altitude; // 5 * m + 500,
//   FIT_UINT32 enhanced_max_altitude; // 5 * m + 500,
   FIT_MESSAGE_INDEX message_index; // Selected bit is set for the current session.
   FIT_UINT16 total_calories; // 1 * kcal + 0,
//   FIT_UINT16 total_fat_calories; // 1 * kcal + 0,
   FIT_UINT16 avg_speed; // 1000 * m/s + 0, total_distance / total_timer_time
   FIT_UINT16 max_speed; // 1000 * m/s + 0,
   FIT_UINT16 avg_power; // 1 * watts + 0, total_power / total_timer_time if non_zero_avg_power otherwise total_power / total_elapsed_time
   FIT_UINT16 max_power; // 1 * watts + 0,
   FIT_UINT16 total_ascent; // 1 * m + 0,
   FIT_UINT16 total_descent; // 1 * m + 0,
   FIT_UINT16 first_lap_index; //
   FIT_UINT16 num_laps; //
//   FIT_UINT16 normalized_power; // 1 * watts + 0,
//   FIT_UINT16 training_stress_score; // 10 * tss + 0,
//   FIT_UINT16 intensity_factor; // 1000 * if + 0,
//   FIT_LEFT_RIGHT_BALANCE_100 left_right_balance; //
//   FIT_UINT16 avg_stroke_distance; // 100 * m + 0,
//   FIT_UINT16 pool_length; // 100 * m + 0,
//   FIT_UINT16 threshold_power; // 1 * watts + 0,
//   FIT_UINT16 num_active_lengths; // 1 * lengths + 0, # of active lengths of swim pool
   FIT_UINT16 avg_altitude; // 5 * m + 500,
   FIT_UINT16 max_altitude; // 5 * m + 500,
   FIT_SINT16 avg_grade; // 100 * % + 0,
//   FIT_SINT16 avg_pos_grade; // 100 * % + 0,
//   FIT_SINT16 avg_neg_grade; // 100 * % + 0,
//   FIT_SINT16 max_pos_grade; // 100 * % + 0,
//   FIT_SINT16 max_neg_grade; // 100 * % + 0,
//   FIT_SINT16 avg_pos_vertical_speed; // 1000 * m/s + 0,
//   FIT_SINT16 avg_neg_vertical_speed; // 1000 * m/s + 0,
//   FIT_SINT16 max_pos_vertical_speed; // 1000 * m/s + 0,
//   FIT_SINT16 max_neg_vertical_speed; // 1000 * m/s + 0,
//   FIT_UINT16 best_lap_index; //
   FIT_UINT16 min_altitude; // 5 * m + 500,
//   FIT_UINT16 player_score; //
//   FIT_UINT16 opponent_score; //
//   FIT_UINT16 stroke_count[FIT_SESSION_MESG_STROKE_COUNT_COUNT]; // 1 * counts + 0, stroke_type enum used as the index
//   FIT_UINT16 zone_count[FIT_SESSION_MESG_ZONE_COUNT_COUNT]; // 1 * counts + 0, zone number used as the index
//   FIT_UINT16 max_ball_speed; // 100 * m/s + 0,
//   FIT_UINT16 avg_ball_speed; // 100 * m/s + 0,
//   FIT_UINT16 avg_vertical_oscillation; // 10 * mm + 0,
//   FIT_UINT16 avg_stance_time_percent; // 100 * percent + 0,
//   FIT_UINT16 avg_stance_time; // 10 * ms + 0,
//   FIT_UINT16 avg_vam; // 1000 * m/s + 0,
   FIT_EVENT event; // session
   FIT_EVENT_TYPE event_type; // stop
   FIT_SPORT sport; //
   FIT_SUB_SPORT sub_sport; //
   FIT_UINT8 avg_heart_rate; // 1 * bpm + 0, average heart rate (excludes pause time)
   FIT_UINT8 max_heart_rate; // 1 * bpm + 0,
   FIT_UINT8 avg_cadence; // 1 * rpm + 0, total_cycles / total_timer_time if non_zero_avg_cadence otherwise total_cycles / total_elapsed_time
   FIT_UINT8 max_cadence; // 1 * rpm + 0,
//   FIT_UINT8 total_training_effect; //
   FIT_UINT8 event_group; //
   FIT_SESSION_TRIGGER trigger; //
//   FIT_SWIM_STROKE swim_stroke; // 1 * swim_stroke + 0,
//   FIT_DISPLAY_MEASURE pool_length_unit; //
//   FIT_UINT8 gps_accuracy; // 1 * m + 0,
   FIT_SINT8 avg_temperature; // 1 * C + 0,
   FIT_SINT8 max_temperature; // 1 * C + 0,
   FIT_UINT8 min_heart_rate; // 1 * bpm + 0,
//   FIT_STRING opponent_name[FIT_SESSION_MESG_OPPONENT_NAME_COUNT]; //
//   FIT_UINT8 avg_fractional_cadence; // 128 * rpm + 0, fractional part of the avg_cadence
//   FIT_UINT8 max_fractional_cadence; // 128 * rpm + 0, fractional part of the max_cadence
//   FIT_UINT8 total_fractional_cycles; // 128 * cycles + 0, fractional part of the total_cycles
//   FIT_UINT8 sport_index; //
//   FIT_UINT8 total_anaerobic_training_effect; //
} FIT_SESSION_MESG;

typedef enum
{
   FIT_SESSION_FIELD_NUM_TIMESTAMP = 253,
   FIT_SESSION_FIELD_NUM_START_TIME = 2,
   FIT_SESSION_FIELD_NUM_START_POSITION_LAT = 3,
   FIT_SESSION_FIELD_NUM_START_POSITION_LONG = 4,
   FIT_SESSION_FIELD_NUM_TOTAL_ELAPSED_TIME = 7,
   FIT_SESSION_FIELD_NUM_TOTAL_TIMER_TIME = 8,
   FIT_SESSION_FIELD_NUM_TOTAL_DISTANCE = 9,
   FIT_SESSION_FIELD_NUM_TOTAL_CYCLES = 10,
   FIT_SESSION_FIELD_NUM_NEC_LAT = 29,
   FIT_SESSION_FIELD_NUM_NEC_LONG = 30,
   FIT_SESSION_FIELD_NUM_SWC_LAT = 31,
   FIT_SESSION_FIELD_NUM_SWC_LONG = 32,
   FIT_SESSION_FIELD_NUM_AVG_STROKE_COUNT = 41,
   FIT_SESSION_FIELD_NUM_TOTAL_WORK = 48,
   FIT_SESSION_FIELD_NUM_TOTAL_MOVING_TIME = 59,
   FIT_SESSION_FIELD_NUM_TIME_IN_HR_ZONE = 65,
   FIT_SESSION_FIELD_NUM_TIME_IN_SPEED_ZONE = 66,
   FIT_SESSION_FIELD_NUM_TIME_IN_CADENCE_ZONE = 67,
   FIT_SESSION_FIELD_NUM_TIME_IN_POWER_ZONE = 68,
   FIT_SESSION_FIELD_NUM_AVG_LAP_TIME = 69,
   FIT_SESSION_FIELD_NUM_ENHANCED_AVG_SPEED = 124,
   FIT_SESSION_FIELD_NUM_ENHANCED_MAX_SPEED = 125,
   FIT_SESSION_FIELD_NUM_ENHANCED_AVG_ALTITUDE = 126,
   FIT_SESSION_FIELD_NUM_ENHANCED_MIN_ALTITUDE = 127,
   FIT_SESSION_FIELD_NUM_ENHANCED_MAX_ALTITUDE = 128,
   FIT_SESSION_FIELD_NUM_MESSAGE_INDEX = 254,
   FIT_SESSION_FIELD_NUM_TOTAL_CALORIES = 11,
   FIT_SESSION_FIELD_NUM_TOTAL_FAT_CALORIES = 13,
   FIT_SESSION_FIELD_NUM_AVG_SPEED = 14,
   FIT_SESSION_FIELD_NUM_MAX_SPEED = 15,
   FIT_SESSION_FIELD_NUM_AVG_POWER = 20,
   FIT_SESSION_FIELD_NUM_MAX_POWER = 21,
   FIT_SESSION_FIELD_NUM_TOTAL_ASCENT = 22,
   FIT_SESSION_FIELD_NUM_TOTAL_DESCENT = 23,
   FIT_SESSION_FIELD_NUM_FIRST_LAP_INDEX = 25,
   FIT_SESSION_FIELD_NUM_NUM_LAPS = 26,
   FIT_SESSION_FIELD_NUM_NORMALIZED_POWER = 34,
   FIT_SESSION_FIELD_NUM_TRAINING_STRESS_SCORE = 35,
   FIT_SESSION_FIELD_NUM_INTENSITY_FACTOR = 36,
   FIT_SESSION_FIELD_NUM_LEFT_RIGHT_BALANCE = 37,
   FIT_SESSION_FIELD_NUM_AVG_STROKE_DISTANCE = 42,
   FIT_SESSION_FIELD_NUM_POOL_LENGTH = 44,
   FIT_SESSION_FIELD_NUM_THRESHOLD_POWER = 45,
   FIT_SESSION_FIELD_NUM_NUM_ACTIVE_LENGTHS = 47,
   FIT_SESSION_FIELD_NUM_AVG_ALTITUDE = 49,
   FIT_SESSION_FIELD_NUM_MAX_ALTITUDE = 50,
   FIT_SESSION_FIELD_NUM_AVG_GRADE = 52,
   FIT_SESSION_FIELD_NUM_AVG_POS_GRADE = 53,
   FIT_SESSION_FIELD_NUM_AVG_NEG_GRADE = 54,
   FIT_SESSION_FIELD_NUM_MAX_POS_GRADE = 55,
   FIT_SESSION_FIELD_NUM_MAX_NEG_GRADE = 56,
   FIT_SESSION_FIELD_NUM_AVG_POS_VERTICAL_SPEED = 60,
   FIT_SESSION_FIELD_NUM_AVG_NEG_VERTICAL_SPEED = 61,
   FIT_SESSION_FIELD_NUM_MAX_POS_VERTICAL_SPEED = 62,
   FIT_SESSION_FIELD_NUM_MAX_NEG_VERTICAL_SPEED = 63,
   FIT_SESSION_FIELD_NUM_BEST_LAP_INDEX = 70,
   FIT_SESSION_FIELD_NUM_MIN_ALTITUDE = 71,
   FIT_SESSION_FIELD_NUM_PLAYER_SCORE = 82,
   FIT_SESSION_FIELD_NUM_OPPONENT_SCORE = 83,
   FIT_SESSION_FIELD_NUM_STROKE_COUNT = 85,
   FIT_SESSION_FIELD_NUM_ZONE_COUNT = 86,
   FIT_SESSION_FIELD_NUM_MAX_BALL_SPEED = 87,
   FIT_SESSION_FIELD_NUM_AVG_BALL_SPEED = 88,
   FIT_SESSION_FIELD_NUM_AVG_VERTICAL_OSCILLATION = 89,
   FIT_SESSION_FIELD_NUM_AVG_STANCE_TIME_PERCENT = 90,
   FIT_SESSION_FIELD_NUM_AVG_STANCE_TIME = 91,
   FIT_SESSION_FIELD_NUM_AVG_VAM = 139,
   FIT_SESSION_FIELD_NUM_EVENT = 0,
   FIT_SESSION_FIELD_NUM_EVENT_TYPE = 1,
   FIT_SESSION_FIELD_NUM_SPORT = 5,
   FIT_SESSION_FIELD_NUM_SUB_SPORT = 6,
   FIT_SESSION_FIELD_NUM_AVG_HEART_RATE = 16,
   FIT_SESSION_FIELD_NUM_MAX_HEART_RATE = 17,
   FIT_SESSION_FIELD_NUM_AVG_CADENCE = 18,
   FIT_SESSION_FIELD_NUM_MAX_CADENCE = 19,
   FIT_SESSION_FIELD_NUM_TOTAL_TRAINING_EFFECT = 24,
   FIT_SESSION_FIELD_NUM_EVENT_GROUP = 27,
   FIT_SESSION_FIELD_NUM_TRIGGER = 28,
   FIT_SESSION_FIELD_NUM_SWIM_STROKE = 43,
   FIT_SESSION_FIELD_NUM_POOL_LENGTH_UNIT = 46,
   FIT_SESSION_FIELD_NUM_GPS_ACCURACY = 51,
   FIT_SESSION_FIELD_NUM_AVG_TEMPERATURE = 57,
   FIT_SESSION_FIELD_NUM_MAX_TEMPERATURE = 58,
   FIT_SESSION_FIELD_NUM_MIN_HEART_RATE = 64,
   FIT_SESSION_FIELD_NUM_OPPONENT_NAME = 84,
   FIT_SESSION_FIELD_NUM_AVG_FRACTIONAL_CADENCE = 92,
   FIT_SESSION_FIELD_NUM_MAX_FRACTIONAL_CADENCE = 93,
   FIT_SESSION_FIELD_NUM_TOTAL_FRACTIONAL_CYCLES = 94,
   FIT_SESSION_FIELD_NUM_SPORT_INDEX = 111,
   FIT_SESSION_FIELD_NUM_TOTAL_ANAEROBIC_TRAINING_EFFECT = 137
} FIT_SESSION_FIELD_NUM;

typedef enum
{
   FIT_SESSION_MESG_TIMESTAMP,
   FIT_SESSION_MESG_START_TIME,
   FIT_SESSION_MESG_START_POSITION_LAT,
   FIT_SESSION_MESG_START_POSITION_LONG,
   FIT_SESSION_MESG_TOTAL_ELAPSED_TIME,
   FIT_SESSION_MESG_TOTAL_TIMER_TIME,
   FIT_SESSION_MESG_TOTAL_DISTANCE,
//   FIT_SESSION_MESG_TOTAL_CYCLES,
//   FIT_SESSION_MESG_NEC_LAT,
//   FIT_SESSION_MESG_NEC_LONG,
//   FIT_SESSION_MESG_SWC_LAT,
//   FIT_SESSION_MESG_SWC_LONG,
//   FIT_SESSION_MESG_AVG_STROKE_COUNT,
//   FIT_SESSION_MESG_TOTAL_WORK,
   /*
       added by cc 20180510
       在zone区域的时间
   */
//   FIT_SESSION_MESG_TOTAL_MOVING_TIME, 
   FIT_SESSION_MESG_TIME_IN_HR_ZONE,
//   FIT_SESSION_MESG_TIME_IN_SPEED_ZONE,
//   FIT_SESSION_MESG_TIME_IN_CADENCE_ZONE,
   FIT_SESSION_MESG_TIME_IN_POWER_ZONE,

//   FIT_SESSION_MESG_AVG_LAP_TIME,
//   FIT_SESSION_MESG_ENHANCED_AVG_SPEED,
//   FIT_SESSION_MESG_ENHANCED_MAX_SPEED,
//   FIT_SESSION_MESG_ENHANCED_AVG_ALTITUDE,
//   FIT_SESSION_MESG_ENHANCED_MIN_ALTITUDE,
//   FIT_SESSION_MESG_ENHANCED_MAX_ALTITUDE,
   FIT_SESSION_MESG_MESSAGE_INDEX,
   FIT_SESSION_MESG_TOTAL_CALORIES,
//   FIT_SESSION_MESG_TOTAL_FAT_CALORIES,
   FIT_SESSION_MESG_AVG_SPEED,
   FIT_SESSION_MESG_MAX_SPEED,
   FIT_SESSION_MESG_AVG_POWER,
   FIT_SESSION_MESG_MAX_POWER,
   FIT_SESSION_MESG_TOTAL_ASCENT,
   FIT_SESSION_MESG_TOTAL_DESCENT,
   FIT_SESSION_MESG_FIRST_LAP_INDEX,
   FIT_SESSION_MESG_NUM_LAPS,
//   FIT_SESSION_MESG_NORMALIZED_POWER,
//   FIT_SESSION_MESG_TRAINING_STRESS_SCORE,
//   FIT_SESSION_MESG_INTENSITY_FACTOR,
//   FIT_SESSION_MESG_LEFT_RIGHT_BALANCE,
//   FIT_SESSION_MESG_AVG_STROKE_DISTANCE,
//   FIT_SESSION_MESG_POOL_LENGTH,
//   FIT_SESSION_MESG_THRESHOLD_POWER,
//   FIT_SESSION_MESG_NUM_ACTIVE_LENGTHS,
   FIT_SESSION_MESG_AVG_ALTITUDE,
   FIT_SESSION_MESG_MAX_ALTITUDE,
   FIT_SESSION_MESG_AVG_GRADE,
//   FIT_SESSION_MESG_AVG_POS_GRADE,
//   FIT_SESSION_MESG_AVG_NEG_GRADE,
//   FIT_SESSION_MESG_MAX_POS_GRADE,
//   FIT_SESSION_MESG_MAX_NEG_GRADE,
//   FIT_SESSION_MESG_AVG_POS_VERTICAL_SPEED,
//   FIT_SESSION_MESG_AVG_NEG_VERTICAL_SPEED,
//   FIT_SESSION_MESG_MAX_POS_VERTICAL_SPEED,
//   FIT_SESSION_MESG_MAX_NEG_VERTICAL_SPEED,
//   FIT_SESSION_MESG_BEST_LAP_INDEX,
   FIT_SESSION_MESG_MIN_ALTITUDE,
//   FIT_SESSION_MESG_PLAYER_SCORE,
//   FIT_SESSION_MESG_OPPONENT_SCORE,
//   FIT_SESSION_MESG_STROKE_COUNT,
//   FIT_SESSION_MESG_ZONE_COUNT,
//   FIT_SESSION_MESG_MAX_BALL_SPEED,
//   FIT_SESSION_MESG_AVG_BALL_SPEED,
//   FIT_SESSION_MESG_AVG_VERTICAL_OSCILLATION,
//   FIT_SESSION_MESG_AVG_STANCE_TIME_PERCENT,
//   FIT_SESSION_MESG_AVG_STANCE_TIME,
//   FIT_SESSION_MESG_AVG_VAM,
   FIT_SESSION_MESG_EVENT,
   FIT_SESSION_MESG_EVENT_TYPE,
   FIT_SESSION_MESG_SPORT,
   FIT_SESSION_MESG_SUB_SPORT,
   FIT_SESSION_MESG_AVG_HEART_RATE,
   FIT_SESSION_MESG_MAX_HEART_RATE,
   FIT_SESSION_MESG_AVG_CADENCE,
   FIT_SESSION_MESG_MAX_CADENCE,
//   FIT_SESSION_MESG_TOTAL_TRAINING_EFFECT,
   FIT_SESSION_MESG_EVENT_GROUP,
   FIT_SESSION_MESG_TRIGGER,
//   FIT_SESSION_MESG_SWIM_STROKE,
//   FIT_SESSION_MESG_POOL_LENGTH_UNIT,
//   FIT_SESSION_MESG_GPS_ACCURACY,
   FIT_SESSION_MESG_AVG_TEMPERATURE,
   FIT_SESSION_MESG_MAX_TEMPERATURE,
   FIT_SESSION_MESG_MIN_HEART_RATE,
//   FIT_SESSION_MESG_OPPONENT_NAME,
//   FIT_SESSION_MESG_AVG_FRACTIONAL_CADENCE,
//   FIT_SESSION_MESG_MAX_FRACTIONAL_CADENCE,
//   FIT_SESSION_MESG_TOTAL_FRACTIONAL_CYCLES,
//   FIT_SESSION_MESG_SPORT_INDEX,
//   FIT_SESSION_MESG_TOTAL_ANAEROBIC_TRAINING_EFFECT,
   FIT_SESSION_MESG_FIELDS
} FIT_SESSION_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_SESSION_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_SESSION_MESG_DEF;

// lap message

#define FIT_LAP_MESG_SIZE                                                       121//28+93//189
#define FIT_LAP_MESG_DEF_SIZE                                                   113 // 36*3+5 = 107,  254
#define FIT_LAP_MESG_TIME_IN_HR_ZONE_COUNT                                      5
//#define FIT_LAP_MESG_TIME_IN_SPEED_ZONE_COUNT                                   1
//#define FIT_LAP_MESG_TIME_IN_CADENCE_ZONE_COUNT                                 1
#define FIT_LAP_MESG_TIME_IN_POWER_ZONE_COUNT                                   7
//#define FIT_LAP_MESG_STROKE_COUNT_COUNT                                         1
//#define FIT_LAP_MESG_ZONE_COUNT_COUNT                                           1
//#define FIT_LAP_MESG_AVG_TOTAL_HEMOGLOBIN_CONC_COUNT                            1
//#define FIT_LAP_MESG_MIN_TOTAL_HEMOGLOBIN_CONC_COUNT                            1
//#define FIT_LAP_MESG_MAX_TOTAL_HEMOGLOBIN_CONC_COUNT                            1
//#define FIT_LAP_MESG_AVG_SATURATED_HEMOGLOBIN_PERCENT_COUNT                     1
//#define FIT_LAP_MESG_MIN_SATURATED_HEMOGLOBIN_PERCENT_COUNT                     1
//#define FIT_LAP_MESG_MAX_SATURATED_HEMOGLOBIN_PERCENT_COUNT                     1

typedef struct
{
   FIT_DATE_TIME timestamp; // 1 * s + 0, Lap end time.
   FIT_DATE_TIME start_time; //
   FIT_SINT32 start_position_lat; // 1 * semicircles + 0,
   FIT_SINT32 start_position_long; // 1 * semicircles + 0,
   FIT_SINT32 end_position_lat; // 1 * semicircles + 0,
   FIT_SINT32 end_position_long; // 1 * semicircles + 0,
   FIT_UINT32 total_elapsed_time; // 1000 * s + 0, Time (includes pauses)
   FIT_UINT32 total_timer_time; // 1000 * s + 0, Timer Time (excludes pauses)
   FIT_UINT32 total_distance; // 100 * m + 0,
//   FIT_UINT32 total_cycles; // 1 * cycles + 0,
//   FIT_UINT32 total_work; // 1 * J + 0,
//   FIT_UINT32 total_moving_time; // 1000 * s + 0,
   FIT_UINT32 time_in_hr_zone[FIT_LAP_MESG_TIME_IN_HR_ZONE_COUNT]; // 1000 * s + 0,
//   FIT_UINT32 time_in_speed_zone[FIT_LAP_MESG_TIME_IN_SPEED_ZONE_COUNT]; // 1000 * s + 0,
//   FIT_UINT32 time_in_cadence_zone[FIT_LAP_MESG_TIME_IN_CADENCE_ZONE_COUNT]; // 1000 * s + 0,
   FIT_UINT32 time_in_power_zone[FIT_LAP_MESG_TIME_IN_POWER_ZONE_COUNT]; // 1000 * s + 0,
//   FIT_UINT32 enhanced_avg_speed; // 1000 * m/s + 0,
//   FIT_UINT32 enhanced_max_speed; // 1000 * m/s + 0,
//   FIT_UINT32 enhanced_avg_altitude; // 5 * m + 500,
//   FIT_UINT32 enhanced_min_altitude; // 5 * m + 500,
//   FIT_UINT32 enhanced_max_altitude; // 5 * m + 500,
   FIT_MESSAGE_INDEX message_index; //
   FIT_UINT16 total_calories; // 1 * kcal + 0,
//   FIT_UINT16 total_fat_calories; // 1 * kcal + 0, If New Leaf
   FIT_UINT16 avg_speed; // 1000 * m/s + 0,
   FIT_UINT16 max_speed; // 1000 * m/s + 0,
   FIT_UINT16 avg_power; // 1 * watts + 0, total_power / total_timer_time if non_zero_avg_power otherwise total_power / total_elapsed_time
   FIT_UINT16 max_power; // 1 * watts + 0,
   FIT_UINT16 total_ascent; // 1 * m + 0,
   FIT_UINT16 total_descent; // 1 * m + 0,
//   FIT_UINT16 num_lengths; // 1 * lengths + 0, # of lengths of swim pool
//   FIT_UINT16 normalized_power; // 1 * watts + 0,
//   FIT_LEFT_RIGHT_BALANCE_100 left_right_balance; //
//   FIT_UINT16 first_length_index; //
//   FIT_UINT16 avg_stroke_distance; // 100 * m + 0,
//   FIT_UINT16 num_active_lengths; // 1 * lengths + 0, # of active lengths of swim pool
   FIT_UINT16 avg_altitude; // 5 * m + 500,
   FIT_UINT16 max_altitude; // 5 * m + 500,
   FIT_SINT16 avg_grade; // 100 * % + 0,
//   FIT_SINT16 avg_pos_grade; // 100 * % + 0,
//   FIT_SINT16 avg_neg_grade; // 100 * % + 0,
//   FIT_SINT16 max_pos_grade; // 100 * % + 0,
//   FIT_SINT16 max_neg_grade; // 100 * % + 0,
//   FIT_SINT16 avg_pos_vertical_speed; // 1000 * m/s + 0,
//   FIT_SINT16 avg_neg_vertical_speed; // 1000 * m/s + 0,
//   FIT_SINT16 max_pos_vertical_speed; // 1000 * m/s + 0,
//   FIT_SINT16 max_neg_vertical_speed; // 1000 * m/s + 0,
//   FIT_UINT16 repetition_num; //
   FIT_UINT16 min_altitude; // 5 * m + 500,
//   FIT_MESSAGE_INDEX wkt_step_index; //
//   FIT_UINT16 opponent_score; //
//   FIT_UINT16 stroke_count[FIT_LAP_MESG_STROKE_COUNT_COUNT]; // 1 * counts + 0, stroke_type enum used as the index
//   FIT_UINT16 zone_count[FIT_LAP_MESG_ZONE_COUNT_COUNT]; // 1 * counts + 0, zone number used as the index
//   FIT_UINT16 avg_vertical_oscillation; // 10 * mm + 0,
//   FIT_UINT16 avg_stance_time_percent; // 100 * percent + 0,
//   FIT_UINT16 avg_stance_time; // 10 * ms + 0,
//   FIT_UINT16 player_score; //
//   FIT_UINT16 avg_total_hemoglobin_conc[FIT_LAP_MESG_AVG_TOTAL_HEMOGLOBIN_CONC_COUNT]; // 100 * g/dL + 0, Avg saturated and unsaturated hemoglobin
//   FIT_UINT16 min_total_hemoglobin_conc[FIT_LAP_MESG_MIN_TOTAL_HEMOGLOBIN_CONC_COUNT]; // 100 * g/dL + 0, Min saturated and unsaturated hemoglobin
//   FIT_UINT16 max_total_hemoglobin_conc[FIT_LAP_MESG_MAX_TOTAL_HEMOGLOBIN_CONC_COUNT]; // 100 * g/dL + 0, Max saturated and unsaturated hemoglobin
//   FIT_UINT16 avg_saturated_hemoglobin_percent[FIT_LAP_MESG_AVG_SATURATED_HEMOGLOBIN_PERCENT_COUNT]; // 10 * % + 0, Avg percentage of hemoglobin saturated with oxygen
//   FIT_UINT16 min_saturated_hemoglobin_percent[FIT_LAP_MESG_MIN_SATURATED_HEMOGLOBIN_PERCENT_COUNT]; // 10 * % + 0, Min percentage of hemoglobin saturated with oxygen
//   FIT_UINT16 max_saturated_hemoglobin_percent[FIT_LAP_MESG_MAX_SATURATED_HEMOGLOBIN_PERCENT_COUNT]; // 10 * % + 0, Max percentage of hemoglobin saturated with oxygen
//   FIT_UINT16 avg_vam; // 1000 * m/s + 0,
   FIT_EVENT event; //
   FIT_EVENT_TYPE event_type; //
   FIT_UINT8 avg_heart_rate; // 1 * bpm + 0,
   FIT_UINT8 max_heart_rate; // 1 * bpm + 0,
   FIT_UINT8 avg_cadence; // 1 * rpm + 0, total_cycles / total_timer_time if non_zero_avg_cadence otherwise total_cycles / total_elapsed_time
   FIT_UINT8 max_cadence; // 1 * rpm + 0,
//   FIT_INTENSITY intensity; //
   FIT_LAP_TRIGGER lap_trigger; //
   FIT_SPORT sport; //
   FIT_UINT8 event_group; //
//   FIT_SWIM_STROKE swim_stroke; //
   FIT_SUB_SPORT sub_sport; //
//   FIT_UINT8 gps_accuracy; // 1 * m + 0,
   FIT_SINT8 avg_temperature; // 1 * C + 0,
   FIT_SINT8 max_temperature; // 1 * C + 0,
   FIT_UINT8 min_heart_rate; // 1 * bpm + 0,
//   FIT_UINT8 avg_fractional_cadence; // 128 * rpm + 0, fractional part of the avg_cadence
//   FIT_UINT8 max_fractional_cadence; // 128 * rpm + 0, fractional part of the max_cadence
//   FIT_UINT8 total_fractional_cycles; // 128 * cycles + 0, fractional part of the total_cycles
} FIT_LAP_MESG;

typedef enum
{
   FIT_LAP_FIELD_NUM_TIMESTAMP = 253,
   FIT_LAP_FIELD_NUM_START_TIME = 2,
   FIT_LAP_FIELD_NUM_START_POSITION_LAT = 3,
   FIT_LAP_FIELD_NUM_START_POSITION_LONG = 4,
   FIT_LAP_FIELD_NUM_END_POSITION_LAT = 5,
   FIT_LAP_FIELD_NUM_END_POSITION_LONG = 6,
   FIT_LAP_FIELD_NUM_TOTAL_ELAPSED_TIME = 7,
   FIT_LAP_FIELD_NUM_TOTAL_TIMER_TIME = 8,
   FIT_LAP_FIELD_NUM_TOTAL_DISTANCE = 9,
   FIT_LAP_FIELD_NUM_TOTAL_CYCLES = 10,
   FIT_LAP_FIELD_NUM_TOTAL_WORK = 41,
   FIT_LAP_FIELD_NUM_TOTAL_MOVING_TIME = 52,
   FIT_LAP_FIELD_NUM_TIME_IN_HR_ZONE = 57,
   FIT_LAP_FIELD_NUM_TIME_IN_SPEED_ZONE = 58,
   FIT_LAP_FIELD_NUM_TIME_IN_CADENCE_ZONE = 59,
   FIT_LAP_FIELD_NUM_TIME_IN_POWER_ZONE = 60,
   FIT_LAP_FIELD_NUM_ENHANCED_AVG_SPEED = 110,
   FIT_LAP_FIELD_NUM_ENHANCED_MAX_SPEED = 111,
   FIT_LAP_FIELD_NUM_ENHANCED_AVG_ALTITUDE = 112,
   FIT_LAP_FIELD_NUM_ENHANCED_MIN_ALTITUDE = 113,
   FIT_LAP_FIELD_NUM_ENHANCED_MAX_ALTITUDE = 114,
   FIT_LAP_FIELD_NUM_MESSAGE_INDEX = 254,
   FIT_LAP_FIELD_NUM_TOTAL_CALORIES = 11,
   FIT_LAP_FIELD_NUM_TOTAL_FAT_CALORIES = 12,
   FIT_LAP_FIELD_NUM_AVG_SPEED = 13,
   FIT_LAP_FIELD_NUM_MAX_SPEED = 14,
   FIT_LAP_FIELD_NUM_AVG_POWER = 19,
   FIT_LAP_FIELD_NUM_MAX_POWER = 20,
   FIT_LAP_FIELD_NUM_TOTAL_ASCENT = 21,
   FIT_LAP_FIELD_NUM_TOTAL_DESCENT = 22,
   FIT_LAP_FIELD_NUM_NUM_LENGTHS = 32,
   FIT_LAP_FIELD_NUM_NORMALIZED_POWER = 33,
   FIT_LAP_FIELD_NUM_LEFT_RIGHT_BALANCE = 34,
   FIT_LAP_FIELD_NUM_FIRST_LENGTH_INDEX = 35,
   FIT_LAP_FIELD_NUM_AVG_STROKE_DISTANCE = 37,
   FIT_LAP_FIELD_NUM_NUM_ACTIVE_LENGTHS = 40,
   FIT_LAP_FIELD_NUM_AVG_ALTITUDE = 42,
   FIT_LAP_FIELD_NUM_MAX_ALTITUDE = 43,
   FIT_LAP_FIELD_NUM_AVG_GRADE = 45,
   FIT_LAP_FIELD_NUM_AVG_POS_GRADE = 46,
   FIT_LAP_FIELD_NUM_AVG_NEG_GRADE = 47,
   FIT_LAP_FIELD_NUM_MAX_POS_GRADE = 48,
   FIT_LAP_FIELD_NUM_MAX_NEG_GRADE = 49,
   FIT_LAP_FIELD_NUM_AVG_POS_VERTICAL_SPEED = 53,
   FIT_LAP_FIELD_NUM_AVG_NEG_VERTICAL_SPEED = 54,
   FIT_LAP_FIELD_NUM_MAX_POS_VERTICAL_SPEED = 55,
   FIT_LAP_FIELD_NUM_MAX_NEG_VERTICAL_SPEED = 56,
   FIT_LAP_FIELD_NUM_REPETITION_NUM = 61,
   FIT_LAP_FIELD_NUM_MIN_ALTITUDE = 62,
   FIT_LAP_FIELD_NUM_WKT_STEP_INDEX = 71,
   FIT_LAP_FIELD_NUM_OPPONENT_SCORE = 74,
   FIT_LAP_FIELD_NUM_STROKE_COUNT = 75,
   FIT_LAP_FIELD_NUM_ZONE_COUNT = 76,
   FIT_LAP_FIELD_NUM_AVG_VERTICAL_OSCILLATION = 77,
   FIT_LAP_FIELD_NUM_AVG_STANCE_TIME_PERCENT = 78,
   FIT_LAP_FIELD_NUM_AVG_STANCE_TIME = 79,
   FIT_LAP_FIELD_NUM_PLAYER_SCORE = 83,
   FIT_LAP_FIELD_NUM_AVG_TOTAL_HEMOGLOBIN_CONC = 84,
   FIT_LAP_FIELD_NUM_MIN_TOTAL_HEMOGLOBIN_CONC = 85,
   FIT_LAP_FIELD_NUM_MAX_TOTAL_HEMOGLOBIN_CONC = 86,
   FIT_LAP_FIELD_NUM_AVG_SATURATED_HEMOGLOBIN_PERCENT = 87,
   FIT_LAP_FIELD_NUM_MIN_SATURATED_HEMOGLOBIN_PERCENT = 88,
   FIT_LAP_FIELD_NUM_MAX_SATURATED_HEMOGLOBIN_PERCENT = 89,
   FIT_LAP_FIELD_NUM_AVG_VAM = 121,
   FIT_LAP_FIELD_NUM_EVENT = 0,
   FIT_LAP_FIELD_NUM_EVENT_TYPE = 1,
   FIT_LAP_FIELD_NUM_AVG_HEART_RATE = 15,
   FIT_LAP_FIELD_NUM_MAX_HEART_RATE = 16,
   FIT_LAP_FIELD_NUM_AVG_CADENCE = 17,
   FIT_LAP_FIELD_NUM_MAX_CADENCE = 18,
   FIT_LAP_FIELD_NUM_INTENSITY = 23,
   FIT_LAP_FIELD_NUM_LAP_TRIGGER = 24,
   FIT_LAP_FIELD_NUM_SPORT = 25,
   FIT_LAP_FIELD_NUM_EVENT_GROUP = 26,
   FIT_LAP_FIELD_NUM_SWIM_STROKE = 38,
   FIT_LAP_FIELD_NUM_SUB_SPORT = 39,
   FIT_LAP_FIELD_NUM_GPS_ACCURACY = 44,
   FIT_LAP_FIELD_NUM_AVG_TEMPERATURE = 50,
   FIT_LAP_FIELD_NUM_MAX_TEMPERATURE = 51,
   FIT_LAP_FIELD_NUM_MIN_HEART_RATE = 63,
   FIT_LAP_FIELD_NUM_AVG_FRACTIONAL_CADENCE = 80,
   FIT_LAP_FIELD_NUM_MAX_FRACTIONAL_CADENCE = 81,
   FIT_LAP_FIELD_NUM_TOTAL_FRACTIONAL_CYCLES = 82
} FIT_LAP_FIELD_NUM;

typedef enum
{
   FIT_LAP_MESG_TIMESTAMP,
   FIT_LAP_MESG_START_TIME,
   FIT_LAP_MESG_START_POSITION_LAT,
   FIT_LAP_MESG_START_POSITION_LONG,
   FIT_LAP_MESG_END_POSITION_LAT,
   FIT_LAP_MESG_END_POSITION_LONG,
   FIT_LAP_MESG_TOTAL_ELAPSED_TIME,
   FIT_LAP_MESG_TOTAL_TIMER_TIME,
   FIT_LAP_MESG_TOTAL_DISTANCE,
//   FIT_LAP_MESG_TOTAL_CYCLES,
//   FIT_LAP_MESG_TOTAL_WORK,
//   FIT_LAP_MESG_TOTAL_MOVING_TIME,
   FIT_LAP_MESG_TIME_IN_HR_ZONE,
//   FIT_LAP_MESG_TIME_IN_SPEED_ZONE,
//   FIT_LAP_MESG_TIME_IN_CADENCE_ZONE,
   FIT_LAP_MESG_TIME_IN_POWER_ZONE,
//   FIT_LAP_MESG_ENHANCED_AVG_SPEED,
//   FIT_LAP_MESG_ENHANCED_MAX_SPEED,
//   FIT_LAP_MESG_ENHANCED_AVG_ALTITUDE,
//   FIT_LAP_MESG_ENHANCED_MIN_ALTITUDE,
//   FIT_LAP_MESG_ENHANCED_MAX_ALTITUDE,
   FIT_LAP_MESG_MESSAGE_INDEX,
   FIT_LAP_MESG_TOTAL_CALORIES,
//   FIT_LAP_MESG_TOTAL_FAT_CALORIES,
   FIT_LAP_MESG_AVG_SPEED,
   FIT_LAP_MESG_MAX_SPEED,
   FIT_LAP_MESG_AVG_POWER,
   FIT_LAP_MESG_MAX_POWER,
   FIT_LAP_MESG_TOTAL_ASCENT,
   FIT_LAP_MESG_TOTAL_DESCENT,
//   FIT_LAP_MESG_NUM_LENGTHS,
//   FIT_LAP_MESG_NORMALIZED_POWER,
//   FIT_LAP_MESG_LEFT_RIGHT_BALANCE,
//   FIT_LAP_MESG_FIRST_LENGTH_INDEX,
//   FIT_LAP_MESG_AVG_STROKE_DISTANCE,
//   FIT_LAP_MESG_NUM_ACTIVE_LENGTHS,
   FIT_LAP_MESG_AVG_ALTITUDE,
   FIT_LAP_MESG_MAX_ALTITUDE,
   FIT_LAP_MESG_AVG_GRADE,
//   FIT_LAP_MESG_AVG_POS_GRADE,
//   FIT_LAP_MESG_AVG_NEG_GRADE,
//   FIT_LAP_MESG_MAX_POS_GRADE,
//   FIT_LAP_MESG_MAX_NEG_GRADE,
//   FIT_LAP_MESG_AVG_POS_VERTICAL_SPEED,
//   FIT_LAP_MESG_AVG_NEG_VERTICAL_SPEED,
//   FIT_LAP_MESG_MAX_POS_VERTICAL_SPEED,
//   FIT_LAP_MESG_MAX_NEG_VERTICAL_SPEED,
//   FIT_LAP_MESG_REPETITION_NUM,
   FIT_LAP_MESG_MIN_ALTITUDE,
//   FIT_LAP_MESG_WKT_STEP_INDEX,
//   FIT_LAP_MESG_OPPONENT_SCORE,
//   FIT_LAP_MESG_STROKE_COUNT,
//   FIT_LAP_MESG_ZONE_COUNT,
//   FIT_LAP_MESG_AVG_VERTICAL_OSCILLATION,
//   FIT_LAP_MESG_AVG_STANCE_TIME_PERCENT,
//   FIT_LAP_MESG_AVG_STANCE_TIME,
//   FIT_LAP_MESG_PLAYER_SCORE,
//   FIT_LAP_MESG_AVG_TOTAL_HEMOGLOBIN_CONC,
//   FIT_LAP_MESG_MIN_TOTAL_HEMOGLOBIN_CONC,
//   FIT_LAP_MESG_MAX_TOTAL_HEMOGLOBIN_CONC,
//   FIT_LAP_MESG_AVG_SATURATED_HEMOGLOBIN_PERCENT,
//   FIT_LAP_MESG_MIN_SATURATED_HEMOGLOBIN_PERCENT,
//   FIT_LAP_MESG_MAX_SATURATED_HEMOGLOBIN_PERCENT,
//   FIT_LAP_MESG_AVG_VAM,
   FIT_LAP_MESG_EVENT,
   FIT_LAP_MESG_EVENT_TYPE,
   FIT_LAP_MESG_AVG_HEART_RATE,
   FIT_LAP_MESG_MAX_HEART_RATE,
   FIT_LAP_MESG_AVG_CADENCE,
   FIT_LAP_MESG_MAX_CADENCE,
//   FIT_LAP_MESG_INTENSITY,
   FIT_LAP_MESG_LAP_TRIGGER,
   FIT_LAP_MESG_SPORT,
   FIT_LAP_MESG_EVENT_GROUP,
//   FIT_LAP_MESG_SWIM_STROKE,
   FIT_LAP_MESG_SUB_SPORT,
//   FIT_LAP_MESG_GPS_ACCURACY,
   FIT_LAP_MESG_AVG_TEMPERATURE,
   FIT_LAP_MESG_MAX_TEMPERATURE,
   FIT_LAP_MESG_MIN_HEART_RATE,
//   FIT_LAP_MESG_AVG_FRACTIONAL_CADENCE,
//   FIT_LAP_MESG_MAX_FRACTIONAL_CADENCE,
//   FIT_LAP_MESG_TOTAL_FRACTIONAL_CYCLES,
   FIT_LAP_MESG_FIELDS
} FIT_LAP_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_LAP_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_LAP_MESG_DEF;

// record message

#define FIT_RECORD_MESG_SIZE                                                    27//99
#define FIT_RECORD_MESG_DEF_SIZE                                                38//11*3+5 //149 
//#define FIT_RECORD_MESG_COMPRESSED_SPEED_DISTANCE_COUNT                         3
//#define FIT_RECORD_MESG_SPEED_1S_COUNT                                          5

typedef struct
{
   FIT_DATE_TIME timestamp; // 1 * s + 0,
   FIT_SINT32 position_lat; // 1 * semicircles + 0,
   FIT_SINT32 position_long; // 1 * semicircles + 0,
   FIT_UINT32 distance; // 100 * m + 0,
//   FIT_SINT32 time_from_course; // 1000 * s + 0,
//   FIT_UINT32 total_cycles; // 1 * cycles + 0,
//   FIT_UINT32 accumulated_power; // 1 * watts + 0,
//   FIT_UINT32 enhanced_speed; // 1000 * m/s + 0,
//   FIT_UINT32 enhanced_altitude; // 5 * m + 500,
   FIT_UINT16 altitude; //  5 * m + 500,
   FIT_UINT16 speed; // 1000 * m/s + 0,
   FIT_UINT16 power; // 1 * watts + 0,
   FIT_SINT16 grade; // 100 * % + 0,
//   FIT_UINT16 compressed_accumulated_power; // 1 * watts + 0,
//   FIT_SINT16 vertical_speed; // 1000 * m/s + 0,
//   FIT_UINT16 calories; // 1 * kcal + 0,
//   FIT_UINT16 vertical_oscillation; // 10 * mm + 0,
//   FIT_UINT16 stance_time_percent; // 100 * percent + 0,
//   FIT_UINT16 stance_time; // 10 * ms + 0,
//   FIT_UINT16 ball_speed; // 100 * m/s + 0,
//   FIT_UINT16 cadence256; // 256 * rpm + 0, Log cadence and fractional cadence for backwards compatability
//   FIT_UINT16 total_hemoglobin_conc; // 100 * g/dL + 0, Total saturated and unsaturated hemoglobin
//   FIT_UINT16 total_hemoglobin_conc_min; // 100 * g/dL + 0, Min saturated and unsaturated hemoglobin
//   FIT_UINT16 total_hemoglobin_conc_max; // 100 * g/dL + 0, Max saturated and unsaturated hemoglobin
//   FIT_UINT16 saturated_hemoglobin_percent; // 10 * % + 0, Percentage of hemoglobin saturated with oxygen
//   FIT_UINT16 saturated_hemoglobin_percent_min; // 10 * % + 0, Min percentage of hemoglobin saturated with oxygen
//   FIT_UINT16 saturated_hemoglobin_percent_max; // 10 * % + 0, Max percentage of hemoglobin saturated with oxygen
   FIT_UINT8 heart_rate; // 1 * bpm + 0,
   FIT_UINT8 cadence; // 1 * rpm + 0,
//   FIT_BYTE compressed_speed_distance[FIT_RECORD_MESG_COMPRESSED_SPEED_DISTANCE_COUNT]; //
//   FIT_UINT8 resistance; // Relative. 0 is none  254 is Max.
//   FIT_UINT8 cycle_length; // 100 * m + 0,
   FIT_SINT8 temperature; // 1 * C + 0,
//   FIT_UINT8 speed_1s[FIT_RECORD_MESG_SPEED_1S_COUNT]; // 16 * m/s + 0, Speed at 1s intervals.  Timestamp field indicates time of last array element.
//   FIT_UINT8 cycles; // 1 * cycles + 0,
//   FIT_LEFT_RIGHT_BALANCE left_right_balance; //
//   FIT_UINT8 gps_accuracy; // 1 * m + 0,
//   FIT_ACTIVITY_TYPE activity_type; //
//   FIT_UINT8 left_torque_effectiveness; // 2 * percent + 0,
//   FIT_UINT8 right_torque_effectiveness; // 2 * percent + 0,
//   FIT_UINT8 left_pedal_smoothness; // 2 * percent + 0,
//   FIT_UINT8 right_pedal_smoothness; // 2 * percent + 0,
//   FIT_UINT8 combined_pedal_smoothness; // 2 * percent + 0,
//   FIT_UINT8 time128; // 128 * s + 0,
//   FIT_STROKE_TYPE stroke_type; //
//   FIT_UINT8 zone; //
//   FIT_UINT8 fractional_cadence; // 128 * rpm + 0,
//   FIT_DEVICE_INDEX device_index; //
} FIT_RECORD_MESG;

typedef enum
{
   FIT_RECORD_FIELD_NUM_TIMESTAMP = 253,
   FIT_RECORD_FIELD_NUM_POSITION_LAT = 0,
   FIT_RECORD_FIELD_NUM_POSITION_LONG = 1,
   FIT_RECORD_FIELD_NUM_DISTANCE = 5,
   FIT_RECORD_FIELD_NUM_TIME_FROM_COURSE = 11,
   FIT_RECORD_FIELD_NUM_TOTAL_CYCLES = 19,
   FIT_RECORD_FIELD_NUM_ACCUMULATED_POWER = 29,
   FIT_RECORD_FIELD_NUM_ENHANCED_SPEED = 73,
   FIT_RECORD_FIELD_NUM_ENHANCED_ALTITUDE = 78,
   FIT_RECORD_FIELD_NUM_ALTITUDE = 2,
   FIT_RECORD_FIELD_NUM_SPEED = 6,
   FIT_RECORD_FIELD_NUM_POWER = 7,
   FIT_RECORD_FIELD_NUM_GRADE = 9,
   FIT_RECORD_FIELD_NUM_COMPRESSED_ACCUMULATED_POWER = 28,
   FIT_RECORD_FIELD_NUM_VERTICAL_SPEED = 32,
   FIT_RECORD_FIELD_NUM_CALORIES = 33,
   FIT_RECORD_FIELD_NUM_VERTICAL_OSCILLATION = 39,
   FIT_RECORD_FIELD_NUM_STANCE_TIME_PERCENT = 40,
   FIT_RECORD_FIELD_NUM_STANCE_TIME = 41,
   FIT_RECORD_FIELD_NUM_BALL_SPEED = 51,
   FIT_RECORD_FIELD_NUM_CADENCE256 = 52,
   FIT_RECORD_FIELD_NUM_TOTAL_HEMOGLOBIN_CONC = 54,
   FIT_RECORD_FIELD_NUM_TOTAL_HEMOGLOBIN_CONC_MIN = 55,
   FIT_RECORD_FIELD_NUM_TOTAL_HEMOGLOBIN_CONC_MAX = 56,
   FIT_RECORD_FIELD_NUM_SATURATED_HEMOGLOBIN_PERCENT = 57,
   FIT_RECORD_FIELD_NUM_SATURATED_HEMOGLOBIN_PERCENT_MIN = 58,
   FIT_RECORD_FIELD_NUM_SATURATED_HEMOGLOBIN_PERCENT_MAX = 59,
   FIT_RECORD_FIELD_NUM_HEART_RATE = 3,
   FIT_RECORD_FIELD_NUM_CADENCE = 4,
   FIT_RECORD_FIELD_NUM_COMPRESSED_SPEED_DISTANCE = 8,
   FIT_RECORD_FIELD_NUM_RESISTANCE = 10,
   FIT_RECORD_FIELD_NUM_CYCLE_LENGTH = 12,
   FIT_RECORD_FIELD_NUM_TEMPERATURE = 13,
   FIT_RECORD_FIELD_NUM_SPEED_1S = 17,
   FIT_RECORD_FIELD_NUM_CYCLES = 18,
   FIT_RECORD_FIELD_NUM_LEFT_RIGHT_BALANCE = 30,
   FIT_RECORD_FIELD_NUM_GPS_ACCURACY = 31,
   FIT_RECORD_FIELD_NUM_ACTIVITY_TYPE = 42,
   FIT_RECORD_FIELD_NUM_LEFT_TORQUE_EFFECTIVENESS = 43,
   FIT_RECORD_FIELD_NUM_RIGHT_TORQUE_EFFECTIVENESS = 44,
   FIT_RECORD_FIELD_NUM_LEFT_PEDAL_SMOOTHNESS = 45,
   FIT_RECORD_FIELD_NUM_RIGHT_PEDAL_SMOOTHNESS = 46,
   FIT_RECORD_FIELD_NUM_COMBINED_PEDAL_SMOOTHNESS = 47,
   FIT_RECORD_FIELD_NUM_TIME128 = 48,
   FIT_RECORD_FIELD_NUM_STROKE_TYPE = 49,
   FIT_RECORD_FIELD_NUM_ZONE = 50,
   FIT_RECORD_FIELD_NUM_FRACTIONAL_CADENCE = 53,
   FIT_RECORD_FIELD_NUM_DEVICE_INDEX = 62
} FIT_RECORD_FIELD_NUM;

typedef enum
{
   FIT_RECORD_MESG_TIMESTAMP,
   FIT_RECORD_MESG_POSITION_LAT,
   FIT_RECORD_MESG_POSITION_LONG,
   FIT_RECORD_MESG_DISTANCE,
//   FIT_RECORD_MESG_TIME_FROM_COURSE,
//   FIT_RECORD_MESG_TOTAL_CYCLES,
//   FIT_RECORD_MESG_ACCUMULATED_POWER,
//   FIT_RECORD_MESG_ENHANCED_SPEED,
//   FIT_RECORD_MESG_ENHANCED_ALTITUDE,
   FIT_RECORD_MESG_ALTITUDE,
   FIT_RECORD_MESG_SPEED,
   FIT_RECORD_MESG_POWER,
   FIT_RECORD_MESG_GRADE,
//   FIT_RECORD_MESG_COMPRESSED_ACCUMULATED_POWER,
//   FIT_RECORD_MESG_VERTICAL_SPEED,
//   FIT_RECORD_MESG_CALORIES,
//   FIT_RECORD_MESG_VERTICAL_OSCILLATION,
//   FIT_RECORD_MESG_STANCE_TIME_PERCENT,
//   FIT_RECORD_MESG_STANCE_TIME,
//   FIT_RECORD_MESG_BALL_SPEED,
//   FIT_RECORD_MESG_CADENCE256,
//   FIT_RECORD_MESG_TOTAL_HEMOGLOBIN_CONC,
//   FIT_RECORD_MESG_TOTAL_HEMOGLOBIN_CONC_MIN,
//   FIT_RECORD_MESG_TOTAL_HEMOGLOBIN_CONC_MAX,
//   FIT_RECORD_MESG_SATURATED_HEMOGLOBIN_PERCENT,
//   FIT_RECORD_MESG_SATURATED_HEMOGLOBIN_PERCENT_MIN,
//   FIT_RECORD_MESG_SATURATED_HEMOGLOBIN_PERCENT_MAX,
   FIT_RECORD_MESG_HEART_RATE,
   FIT_RECORD_MESG_CADENCE,
//   FIT_RECORD_MESG_COMPRESSED_SPEED_DISTANCE,
//   FIT_RECORD_MESG_RESISTANCE,
//   FIT_RECORD_MESG_CYCLE_LENGTH,
   FIT_RECORD_MESG_TEMPERATURE,
//   FIT_RECORD_MESG_SPEED_1S,
//   FIT_RECORD_MESG_CYCLES,
//   FIT_RECORD_MESG_LEFT_RIGHT_BALANCE,
//   FIT_RECORD_MESG_GPS_ACCURACY,
//   FIT_RECORD_MESG_ACTIVITY_TYPE,
//   FIT_RECORD_MESG_LEFT_TORQUE_EFFECTIVENESS,
//   FIT_RECORD_MESG_RIGHT_TORQUE_EFFECTIVENESS,
//   FIT_RECORD_MESG_LEFT_PEDAL_SMOOTHNESS,
//   FIT_RECORD_MESG_RIGHT_PEDAL_SMOOTHNESS,
//   FIT_RECORD_MESG_COMBINED_PEDAL_SMOOTHNESS,
//   FIT_RECORD_MESG_TIME128,
//   FIT_RECORD_MESG_STROKE_TYPE,
//   FIT_RECORD_MESG_ZONE,
//   FIT_RECORD_MESG_FRACTIONAL_CADENCE,
//   FIT_RECORD_MESG_DEVICE_INDEX,
   FIT_RECORD_MESG_FIELDS
} FIT_RECORD_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_RECORD_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_RECORD_MESG_DEF;

// event message

#define FIT_EVENT_MESG_SIZE                                                     7//21
#define FIT_EVENT_MESG_DEF_SIZE                                                 17//4*3+5 //41

typedef struct
{
   FIT_DATE_TIME timestamp; // 1 * s + 0,
//   FIT_UINT32 data; //
//   FIT_UINT16 data16; //
//   FIT_UINT16 score; // Do not populate directly.  Autogenerated by decoder for sport_point subfield components
//   FIT_UINT16 opponent_score; // Do not populate directly.  Autogenerated by decoder for sport_point subfield components
   FIT_EVENT event; //
   FIT_EVENT_TYPE event_type; //
   FIT_UINT8 event_group; //
//   FIT_UINT8Z front_gear_num; // Do not populate directly.  Autogenerated by decoder for gear_change subfield components.  Front gear number. 1 is innermost.
//   FIT_UINT8Z front_gear; // Do not populate directly.  Autogenerated by decoder for gear_change subfield components.  Number of front teeth.
//   FIT_UINT8Z rear_gear_num; // Do not populate directly.  Autogenerated by decoder for gear_change subfield components.  Rear gear number. 1 is innermost.
//   FIT_UINT8Z rear_gear; // Do not populate directly.  Autogenerated by decoder for gear_change subfield components.  Number of rear teeth.
} FIT_EVENT_MESG;

typedef enum
{
   FIT_EVENT_FIELD_NUM_TIMESTAMP = 253,
   FIT_EVENT_FIELD_NUM_DATA = 3,
   FIT_EVENT_FIELD_NUM_DATA16 = 2,
   FIT_EVENT_FIELD_NUM_SCORE = 7,
   FIT_EVENT_FIELD_NUM_OPPONENT_SCORE = 8,
   FIT_EVENT_FIELD_NUM_EVENT = 0,
   FIT_EVENT_FIELD_NUM_EVENT_TYPE = 1,
   FIT_EVENT_FIELD_NUM_EVENT_GROUP = 4,
   FIT_EVENT_FIELD_NUM_FRONT_GEAR_NUM = 9,
   FIT_EVENT_FIELD_NUM_FRONT_GEAR = 10,
   FIT_EVENT_FIELD_NUM_REAR_GEAR_NUM = 11,
   FIT_EVENT_FIELD_NUM_REAR_GEAR = 12
} FIT_EVENT_FIELD_NUM;

typedef enum
{
   FIT_EVENT_MESG_TIMESTAMP,
//   FIT_EVENT_MESG_DATA,
//   FIT_EVENT_MESG_DATA16,
//   FIT_EVENT_MESG_SCORE,
//   FIT_EVENT_MESG_OPPONENT_SCORE,
   FIT_EVENT_MESG_EVENT,
   FIT_EVENT_MESG_EVENT_TYPE,
   FIT_EVENT_MESG_EVENT_GROUP,
//   FIT_EVENT_MESG_FRONT_GEAR_NUM,
//   FIT_EVENT_MESG_FRONT_GEAR,
//   FIT_EVENT_MESG_REAR_GEAR_NUM,
//   FIT_EVENT_MESG_REAR_GEAR,
   FIT_EVENT_MESG_FIELDS
} FIT_EVENT_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_EVENT_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_EVENT_MESG_DEF;

// device_info message

#define FIT_DEVICE_INFO_MESG_SIZE                                               31//51
#define FIT_DEVICE_INFO_MESG_DEF_SIZE                                           41//12*3+5//59
#define FIT_DEVICE_INFO_MESG_PRODUCT_NAME_COUNT                                 FIT_FILE_ID_MESG_PRODUCT_NAME_COUNT
//#define FIT_DEVICE_INFO_MESG_DESCRIPTOR_COUNT                                   1

typedef struct
{
   FIT_DATE_TIME timestamp; // 1 * s + 0,
   FIT_UINT32Z serial_number; //
//   FIT_UINT32 cum_operating_time; // 1 * s + 0, Reset by new battery or charge.
   FIT_STRING product_name[FIT_DEVICE_INFO_MESG_PRODUCT_NAME_COUNT]; // Optional free form string to indicate the devices name or model
   FIT_MANUFACTURER manufacturer; //
   FIT_UINT16 product; //
   FIT_UINT16 software_version; //100 * 0.01
   FIT_UINT16 battery_voltage; // 256 * V + 0,
//   FIT_UINT16Z ant_device_number; //
   FIT_DEVICE_INDEX device_index; //
   FIT_UINT8 device_type; //
   FIT_UINT8 hardware_version; //
   FIT_BATTERY_STATUS battery_status; //
//   FIT_BODY_LOCATION sensor_position; // Indicates the location of the sensor
//   FIT_STRING descriptor[FIT_DEVICE_INFO_MESG_DESCRIPTOR_COUNT]; // Used to describe the sensor or location
//   FIT_UINT8Z ant_transmission_type; //
//   FIT_ANT_NETWORK ant_network; //
   FIT_SOURCE_TYPE source_type; //
} FIT_DEVICE_INFO_MESG;

typedef enum
{
   FIT_DEVICE_INFO_FIELD_NUM_TIMESTAMP = 253,
   FIT_DEVICE_INFO_FIELD_NUM_SERIAL_NUMBER = 3,
   FIT_DEVICE_INFO_FIELD_NUM_CUM_OPERATING_TIME = 7,
   FIT_DEVICE_INFO_FIELD_NUM_PRODUCT_NAME = 27,
   FIT_DEVICE_INFO_FIELD_NUM_MANUFACTURER = 2,
   FIT_DEVICE_INFO_FIELD_NUM_PRODUCT = 4,
   FIT_DEVICE_INFO_FIELD_NUM_SOFTWARE_VERSION = 5,
   FIT_DEVICE_INFO_FIELD_NUM_BATTERY_VOLTAGE = 10,
   FIT_DEVICE_INFO_FIELD_NUM_ANT_DEVICE_NUMBER = 21,
   FIT_DEVICE_INFO_FIELD_NUM_DEVICE_INDEX = 0,
   FIT_DEVICE_INFO_FIELD_NUM_DEVICE_TYPE = 1,
   FIT_DEVICE_INFO_FIELD_NUM_HARDWARE_VERSION = 6,
   FIT_DEVICE_INFO_FIELD_NUM_BATTERY_STATUS = 11,
   FIT_DEVICE_INFO_FIELD_NUM_SENSOR_POSITION = 18,
   FIT_DEVICE_INFO_FIELD_NUM_DESCRIPTOR = 19,
   FIT_DEVICE_INFO_FIELD_NUM_ANT_TRANSMISSION_TYPE = 20,
   FIT_DEVICE_INFO_FIELD_NUM_ANT_NETWORK = 22,
   FIT_DEVICE_INFO_FIELD_NUM_SOURCE_TYPE = 25
} FIT_DEVICE_INFO_FIELD_NUM;

typedef enum
{
   FIT_DEVICE_INFO_MESG_TIMESTAMP,
   FIT_DEVICE_INFO_MESG_SERIAL_NUMBER,
//   FIT_DEVICE_INFO_MESG_CUM_OPERATING_TIME,
   FIT_DEVICE_INFO_MESG_PRODUCT_NAME,
   FIT_DEVICE_INFO_MESG_MANUFACTURER,
   FIT_DEVICE_INFO_MESG_PRODUCT,
   FIT_DEVICE_INFO_MESG_SOFTWARE_VERSION,
   FIT_DEVICE_INFO_MESG_BATTERY_VOLTAGE,
//   FIT_DEVICE_INFO_MESG_ANT_DEVICE_NUMBER,
   FIT_DEVICE_INFO_MESG_DEVICE_INDEX,
   FIT_DEVICE_INFO_MESG_DEVICE_TYPE,
   FIT_DEVICE_INFO_MESG_HARDWARE_VERSION,
   FIT_DEVICE_INFO_MESG_BATTERY_STATUS,
//   FIT_DEVICE_INFO_MESG_SENSOR_POSITION,
//   FIT_DEVICE_INFO_MESG_DESCRIPTOR,
//   FIT_DEVICE_INFO_MESG_ANT_TRANSMISSION_TYPE,
//   FIT_DEVICE_INFO_MESG_ANT_NETWORK,
   FIT_DEVICE_INFO_MESG_SOURCE_TYPE,
   FIT_DEVICE_INFO_MESG_FIELDS
} FIT_DEVICE_INFO_MESG_FIELD;

typedef struct
{
   FIT_UINT8 reserved_1;
   FIT_UINT8 arch;
   FIT_MESG_NUM global_mesg_num;
   FIT_UINT8 num_fields;
   FIT_UINT8 fields[FIT_DEVICE_INFO_MESG_FIELDS * FIT_FIELD_DEF_SIZE];
} FIT_DEVICE_INFO_MESG_DEF;

//=====================================================================================
//                          以下是用户自定义数据及函数  
//=====================================================================================


typedef enum {
   FIT_MESG_FILE_ID,
   FIT_MESG_FILE_CREATOR,
   FIT_MESG_DEVICE_INFO,
   FIT_MESG_EVENT,
   FIT_MESG_RECORD,
   FIT_MESG_LAP,
   FIT_MESG_SESSION,
   FIT_MESG_ACTIVITY,
   FIT_MESGS,
} FIT_MESG;

typedef const FIT_MESG_DEF * FIT_CONST_MESG_DEF_PTR;
extern const FIT_CONST_MESG_DEF_PTR fit_mesg_defs[FIT_MESGS];




//外部调用函数
FIT_UINT8 check_fit_data_length(void);
void fit_activity_get_fit_file_mesg(FIT_MESG fit_file_mesg_type, void *p_mesg, void *p_arg);

#ifdef __cplusplus
}
#endif

#endif


