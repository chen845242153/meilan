#ifndef __FIT_ACTIVITY_ENCODE_H__
#define __FIT_ACTIVITY_ENCODE_H__

#ifdef __cplusplus
extern "C"{
#endif

#include "fit_activity.h"
#include "../stdbool.h"

typedef enum
{
     E_ACTIVITY_START,
     E_ACTIVITY_PAUSE,
     E_ACTIVITY_STOP_OLD_LAP,
     E_ACTIVITY_START_NEW_LAP,
     E_ACTIVITY_STOP_ALL,
}fit_activity_event_t;


uint32_t fit_activity_init(void);
void write_event_message(fit_activity_event_t event);
void write_record_message(void);
void write_lap_message(uint32_t lap_addr);
void write_session_message(uint32_t session_addr);
void write_activity_message(uint32_t session_addr);

#ifdef __cplusplus
}
#endif

#endif

