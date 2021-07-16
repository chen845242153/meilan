/***********************************************************************
*                               include files
************************************************************************
*/
#include "fit_crc.h"
#include "../sensor/common/sensorAPI.h"
#include "fileOperate.h"
/***********************************************************************
*                               macro define
************************************************************************
*/

/***********************************************************************
*                               my type
************************************************************************
*/


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
static uint16_t data_crc;
/***********************************************************************
*                               static function
************************************************************************
*/

/***********************************************************************
*                                   end
************************************************************************
*/
/*************************************************************************   
**	function name:	 WriteData
**	description:	 
**	input para:		 	
**	return:					                                         
**************************************************************************/
static void WriteData(const void *data, uint8_t data_size) {
    uint8_t offset;

    file_write(data, data_size);

    for (offset = 0; offset < data_size; offset++)
        data_crc = FitCRC_Get16(data_crc, *((uint8_t *) data + offset));
}

/*************************************************************************   
**	function name:	 WriteData
**	description:	 
**	input para:		 	
**	return:					                                         
**************************************************************************/
void set_fit_crc(uint16_t crc) {
    data_crc = crc;
}

/*************************************************************************   
**	function name:	 WriteFileHeader
**	description:	 
**	input para:		 	
**	return:					                                         
**************************************************************************/
void WriteFileHeader(void) {
    FIT_FILE_HDR file_header;

    file_header.header_size = FIT_FILE_HDR_SIZE;
    file_header.profile_version = FIT_PROFILE_VERSION;
    file_header.protocol_version = FIT_PROTOCOL_VERSION_20;
    sensor_memcpy((uint8_t *) &file_header.data_type, ".FIT", 4);

    //移动文件指针位置到文件末尾
    file_header.data_size = file_size() - FIT_FILE_HDR_SIZE - sizeof(FIT_UINT16);

    file_header.crc = FitCRC_Calc16(&file_header, FIT_STRUCT_OFFSET(crc, FIT_FILE_HDR));

    file_seek(0);
    file_write((void *) &file_header, FIT_FILE_HDR_SIZE);
}

/*************************************************************************   
**	function name:	 WriteMessageDefinition
**	description:	 
**	input para:		 	
**	return:					                                         
**************************************************************************/
void WriteMessageDefinition(uint8_t local_mesg_number, const void *mesg_def_pointer,
                            uint8_t mesg_def_size) {
    uint8_t header = local_mesg_number | FIT_HDR_TYPE_DEF_BIT;
    WriteData(&header, FIT_HDR_SIZE);
    WriteData(mesg_def_pointer, mesg_def_size);
}

/*************************************************************************   
**	function name:	 WriteMessage
**	description:	 
**	input para:		 	
**	return:					                                         
**************************************************************************/
void WriteMessage(uint8_t local_mesg_number, const void *mesg_pointer, uint8_t mesg_size) {
    WriteData(&local_mesg_number, FIT_HDR_SIZE);
    WriteData(mesg_pointer, mesg_size);
}

/*************************************************************************   
**	function name:	 WriteMessage
**	description:	 
**	input para:		 	
**	return:					                                         
**************************************************************************/
void WriteCRC(void) {
    file_write(&data_crc, sizeof(uint16_t));
}

