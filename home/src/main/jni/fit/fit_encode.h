#ifndef __FIT_ENCODE_DEBUG__
#define __FIT_ENCODE_DEBUG__

#include "../stdint.h"


void set_fit_crc(uint16_t crc);
void WriteFileHeader(void);
void WriteMessageDefinition(uint8_t local_mesg_number, const void *mesg_def_pointer, uint8_t mesg_def_size);
void WriteMessage(uint8_t local_mesg_number, const void *mesg_pointer, uint8_t mesg_size);
void WriteCRC(void);





#endif

