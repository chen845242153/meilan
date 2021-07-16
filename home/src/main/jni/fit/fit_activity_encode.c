/***********************************************************************
*                               include files
************************************************************************
*/
#include <string.h>
#include "fit_activity.h"
#include "fit_activity_encode.h"
#include "fit_encode.h"
#include "../jniError.h"
#include "fileOperate.h"
#include "../sensor/common/sensorAPI.h"
#include "../stddef.h"
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
static bool m_message_def_writed_flag[FIT_MESGS] = {false};//每种类型的messagedefinition只能写一次

/***********************************************************************
*                               static function
************************************************************************
*/
static void write_device_info_message(void);
static void write_file_creator_message(void);
static void write_file_id_message(void);
static void write_crc_header_when_end_activity(void);

/***********************************************************************
*                                   end
************************************************************************
*/
/*************************************************************************   
**	function name:	 fit_activity_init
**	description:	 开始运动后建立.fit文件                 
**	input para:		 
**                  	
**	return:			                                       
**************************************************************************/
uint32_t fit_activity_init(void)
{
    if(!check_fit_data_length())
    {
        return JNI_ERROR_INVALID_PARAM;
    }
    
    file_init();

    
    set_fit_crc(0);
    sensor_memset((void*)m_message_def_writed_flag, 0, FIT_MESGS * sizeof(bool)); 
    
    WriteFileHeader();

    //写入.fit文件以及设备的基本信息
    write_file_id_message();
    write_file_creator_message();
    write_device_info_message();
}

/*************************************************************************   
**	function name:	 write_activity_message
**	description:	 保存.fit文件的crc值及文件包头，在结束运动(所有数据都保存好以后)后调用一次
**	input para:		 	
**	return:					                                         
**************************************************************************/
static void write_crc_header_when_end_activity(void)
{
    WriteCRC();
    WriteFileHeader();
}

/*************************************************************************   
**	function name:	 write_file_id_message
**	description:	 .fit文件头信息，用于识别属于什么类型的fit文件(只调用一次)
**	input para:		 	
**	return:					                                         
**************************************************************************/
static void write_file_id_message(void)
{
    FIT_FILE_ID_MESG data_message;


    //get Data Message
    fit_activity_get_fit_file_mesg(FIT_MESG_FILE_ID, &data_message, NULL);
    
    //write Definition Message
    if(!m_message_def_writed_flag[FIT_MESG_FILE_ID])
    {
        m_message_def_writed_flag[FIT_MESG_FILE_ID] = true;
        WriteMessageDefinition(FIT_MESG_FILE_ID, fit_mesg_defs[FIT_MESG_FILE_ID], FIT_FILE_ID_MESG_DEF_SIZE);
    }
    //write Data Message
    WriteMessage(FIT_MESG_FILE_ID, &data_message, FIT_FILE_ID_MESG_SIZE);
}
/*************************************************************************   
**	function name:	 write_file_creator_message
**	description:	 .fit文件建立信息(只调用一次)
**	input para:		 	
**	return:					                                         
**************************************************************************/
static void write_file_creator_message(void)
{
    FIT_FILE_CREATOR_MESG data_message;
    
    //get Data Message
    fit_activity_get_fit_file_mesg(FIT_MESG_FILE_CREATOR, &data_message, NULL);
    
    //write Definition Message
    if(!m_message_def_writed_flag[FIT_MESG_FILE_CREATOR])
    {
        m_message_def_writed_flag[FIT_MESG_FILE_CREATOR] = true;
        WriteMessageDefinition(FIT_MESG_FILE_CREATOR, fit_mesg_defs[FIT_MESG_FILE_CREATOR], FIT_FILE_CREATOR_MESG_DEF_SIZE);

    }
    //write Data Message
    WriteMessage(FIT_MESG_FILE_CREATOR, &data_message, FIT_FILE_CREATOR_MESG_SIZE);

}
/*************************************************************************   
**	function name:	 write_device_info_message
**	description:	 设备信息(如果有多种设备，需要保存多次、但本程序只保存码表信息。可以保存连接的sensor数据，目前不兼容)
**	input para:		 	
**	return:					                                         
**************************************************************************/
static void write_device_info_message(void)
{
    FIT_DEVICE_INFO_MESG data_message;

    //get Data Message
    fit_activity_get_fit_file_mesg(FIT_MESG_DEVICE_INFO, &data_message, NULL);
    
    //write Definition Message
    if(!m_message_def_writed_flag[FIT_MESG_DEVICE_INFO])
    {
        m_message_def_writed_flag[FIT_MESG_DEVICE_INFO] = true;
        WriteMessageDefinition(FIT_MESG_DEVICE_INFO, fit_mesg_defs[FIT_MESG_DEVICE_INFO], FIT_DEVICE_INFO_MESG_DEF_SIZE);
    }
    //write Data Message
    WriteMessage(FIT_MESG_DEVICE_INFO, &data_message, FIT_DEVICE_INFO_MESG_SIZE);
}

/*************************************************************************   
**	function name:	 write_event_message
**	description:	 运动过程中的事件存储(可能出现很多次事件)，比如暂停、开始、结束、分圈
**	input para:		 	
**	return:					                                         
**************************************************************************/
void write_event_message(fit_activity_event_t event)
{
    FIT_EVENT_MESG data_message;
    fit_activity_event_t evt;
        
    evt = event;
    
    //get Data Message
    fit_activity_get_fit_file_mesg(FIT_MESG_EVENT, &data_message, &evt);
    
    //write Definition Message
    if(!m_message_def_writed_flag[FIT_MESG_EVENT])
    {
        m_message_def_writed_flag[FIT_MESG_EVENT] = true;
        WriteMessageDefinition(FIT_MESG_EVENT, fit_mesg_defs[FIT_MESG_EVENT], FIT_EVENT_MESG_DEF_SIZE);
    }
    
    
    //write Data Message
    WriteMessage(FIT_MESG_EVENT, &data_message, FIT_EVENT_MESG_SIZE);
}
/*************************************************************************   
**	function name:	 write_record_message
**	description:	 运动过程中的细分数据。比如1s、4s记录一次，用于细分运动数据分析
**	input para:		 	
**	return:					                                         
**************************************************************************/
void write_record_message(void)
{
    FIT_RECORD_MESG data_message;
    
    //get Data Message
    fit_activity_get_fit_file_mesg(FIT_MESG_RECORD, &data_message, NULL);
    
    //write Definition Message
    if(!m_message_def_writed_flag[FIT_MESG_RECORD])
    {
        m_message_def_writed_flag[FIT_MESG_RECORD] = true;
        WriteMessageDefinition(FIT_MESG_RECORD, fit_mesg_defs[FIT_MESG_RECORD], FIT_RECORD_MESG_DEF_SIZE);
    }    
    //write Data Message
    WriteMessage(FIT_MESG_RECORD, &data_message, FIT_RECORD_MESG_SIZE); 
}
/*************************************************************************   
**	function name:	 write_lap_message
**	description:	 分圈后记录本圈骑行的总数据。每分一次圈记录一次
**	input para:		 	
**	return:		     	                                         
**************************************************************************/
void write_lap_message(uint32_t lap_addr)
{
    FIT_LAP_MESG data_message;
        
    //get Data Message
    fit_activity_get_fit_file_mesg(FIT_MESG_LAP, &data_message, (void *)lap_addr);
    
    //write Definition Message
    if(!m_message_def_writed_flag[FIT_MESG_LAP])
    {
        m_message_def_writed_flag[FIT_MESG_LAP] = true;
        WriteMessageDefinition(FIT_MESG_LAP, fit_mesg_defs[FIT_MESG_LAP], FIT_LAP_MESG_DEF_SIZE);
    }
    
    //write Data Message
    WriteMessage(FIT_MESG_LAP, &data_message, FIT_LAP_MESG_SIZE); 
}
/*************************************************************************   
**	function name:	 write_session_message
**	description:	 在当前运动结束后写入一次session
                     运动可能分为 :平路骑行、爬坡、山地骑行
**	input para:		 	
**	return:					                                         
**************************************************************************/
void write_session_message(uint32_t session_addr)
{
    FIT_SESSION_MESG data_message;
    
        //get Data Message
    fit_activity_get_fit_file_mesg(FIT_MESG_SESSION, &data_message, (void *)session_addr);
    
    //write Definition Message
    if(!m_message_def_writed_flag[FIT_MESG_SESSION])
    {
        m_message_def_writed_flag[FIT_MESG_SESSION] = true;
        WriteMessageDefinition(FIT_MESG_SESSION, fit_mesg_defs[FIT_MESG_SESSION], FIT_SESSION_MESG_DEF_SIZE);
    }
    
    //write Data Message
    WriteMessage(FIT_MESG_SESSION, &data_message, FIT_SESSION_MESG_SIZE); 
}
/*************************************************************************   
**	function name:	 write_activity_message
**	description:	 在所有运动结束后写入一次activity
**	input para:		 	
**	return:					                                         
**************************************************************************/
void write_activity_message(uint32_t session_addr) {
    FIT_ACTIVITY_MESG data_message;

    //get Data Message
    fit_activity_get_fit_file_mesg(FIT_MESG_ACTIVITY, &data_message, (void *) session_addr);

    //write Definition Message
    if (!m_message_def_writed_flag[FIT_MESG_ACTIVITY]) {
        m_message_def_writed_flag[FIT_MESG_ACTIVITY] = true;
        WriteMessageDefinition(FIT_MESG_ACTIVITY, fit_mesg_defs[FIT_MESG_ACTIVITY],
                               FIT_ACTIVITY_MESG_DEF_SIZE);
    }

    //write Data Message
    WriteMessage(FIT_MESG_ACTIVITY, &data_message, FIT_ACTIVITY_MESG_SIZE);

    //保存.fit文件的crc值后包头
    write_crc_header_when_end_activity();

}



