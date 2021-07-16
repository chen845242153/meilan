/***********************************************************************
*                               include files
************************************************************************
*/
#include "sensorAPI.h"
#include "../../stdint.h"
#include "../../stddef.h"
#include "../../math.h"
/***********************************************************************
*                               macro define
************************************************************************
*/

#define PI                                                3.1415926
#define PI180                                             (PI / 180)
#define EARTH_R                                           637100400//mm
#define LONGTITUDE_LATITUDE_ENLARGE_TIMES                 10000000//10^7
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

/***********************************************************************
*                               static function
************************************************************************
*/

/***********************************************************************
*                                   end
************************************************************************
*/


/*************************************************************************   
**	function name:	calculate_avg_value_base_sum_divid_count
**	description:     此函数用于计算平均值 calculate_ave_value函数计算平均值有缺陷，
                     当count值大于cur值时，就会出现不在累加情况，
                     此函数就算是1s计算一次平均值，(2^32-1) / 86400 = 49710
                     就算每次计算的值都是49710这么大，也要累计一天sum才会溢出     
**	input para:		
**	return:				                                         
**************************************************************************/
uint32_t calculate_avg_value_base_sum_divid_count(uint32_t cur, uint32_t *p_sum, uint32_t *p_count)
{
    uint32_t avg, sum, count;
    
    if(*p_count == 0)
    {
        *p_sum   = cur;
        *p_count = 1;
        avg      = cur;
    }
    else
    {
        sum   = *p_sum;
        count = *p_count;
        
        count++;        
        sum += cur;

        avg = sum / count;

        *p_sum   = sum;
        *p_count = count;
    }

    return avg;    
}
/*************************************************************************   
**	function name:	calculate_signed_avg_value_base_sum_divid_count
**	description:     此函数用于计算平均值 calculate_ave_value函数计算平均值有缺陷，
                     当count值大于cur值时，就会出现不在累加情况，
                     此函数就算是1s计算一次平均值，(2^31-1) / 86400 = 24855
                     就算每次计算的值都是49710这么大，也要累计一天sum才会溢出
**	input para:		
**	return:				                                         
**************************************************************************/
int32_t calculate_signed_avg_value_base_sum_divid_count(int32_t cur, int32_t *p_sum, uint32_t *p_count)
{
    int32_t avg, sum;
    uint32_t count;
    
    if(*p_count == 0)
    {
        *p_sum   = cur;
        *p_count = 1;
        avg      = cur;
    }
    else
    {
        sum   = *p_sum;
        count = *p_count;
        
        count++;        
        sum += cur;
        
        // 必须将count转换为int32_t类型
        avg = sum / ((int32_t)count);

        *p_sum   = sum;
        *p_count = count;
    }

    return avg;    
}

/*************************************************************************   
**	function name:	calculate_max_value_expect_invalid_data
**	description:	
**	input para:		
**	return:				                                         
**************************************************************************/
uint32_t calculate_max_value_expect_invalid_data(uint32_t cur, uint32_t max, uint32_t invalid)
{
    uint32_t new_max;
    
    if(max == invalid)
    {
        new_max = cur;
    }
    else
    {
        new_max = SENSOR_MAX_DATA(cur, max);
    }

    return new_max;
}
/*************************************************************************   
**	function name:	calculate_min_value_expect_invalid_data
**	description:	
**	input para:		
**	return:				                                         
**************************************************************************/
uint32_t calculate_min_value_expect_invalid_data(uint32_t cur, uint32_t min, uint32_t invalid)
{
    uint32_t new_min;
    
    if(min == invalid)
    {
        new_min = cur;
    }
    else
    {
        new_min = SENSOR_MIN_DATA(cur, min);
    }

    return new_min;
}

/*************************************************************************   
**	function name:	 calculate_signed_max_min()
**	description:	 
**                                      
**	input para:		
**                  	
**	return:			                                       
**************************************************************************/
void calculate_signed_max_min(int32_t cur, int32_t *p_max, int32_t *p_min, int32_t invalid)
{
    if(p_max != NULL)
    {
        if(*p_max == invalid)
        {
            *p_max = cur;
        }
        else 
        {
            *p_max = SENSOR_MAX_DATA(*p_max, cur);
        }
    }
    
    if(p_min != NULL)
    {
        if(*p_min == invalid)
        {
            *p_min = cur;
        }
        else 
        {
            *p_min = SENSOR_MIN_DATA(*p_min, cur);
        }
    }
}
/*************************************************************************   
**	function name:	my_abs 
**	description:	                     
**	input para:		
**						
**	return:			                                         
**************************************************************************/
uint32_t sensor_abs(uint32_t a, uint32_t b)
{
    if (a >= b) 
        return(a - b); 
    else 
        return (b - a);
}
/*************************************************************************   
**	function name:	decode32_data 
**	description:	                  
**	input para:		 	
**	return:					                                         
**************************************************************************/
void sensor_decode32_data(uint32_t data, uint8_t *pdata)
{
    pdata[0] = (uint8_t)(data >> 0);
    pdata[1] = (uint8_t)(data >> 8);
    pdata[2] = (uint8_t)(data >> 16);
    pdata[3] = (uint8_t)(data >> 24);
}
/*************************************************************************   
**	function name:	decode16_data 
**	description:	                  
**	input para:		 	
**	return:					                                         
**************************************************************************/
void sensor_decode16_data(uint16_t data, uint8_t *pdata)
{
    pdata[0] = (uint8_t)(data >> 16);
    pdata[1] = (uint8_t)(data >> 8);
}
/*************************************************************************   
**	function name:	encode32_data 
**	description:	                  
**	input para:		 	
**	return:					                                         
**************************************************************************/
uint32_t sensor_encode32_data(const uint8_t *pdata)
{
    uint32_t data;

    data = (uint32_t)(pdata[3]<<24) + (uint32_t)(pdata[2]<<16) +
           (uint32_t)(pdata[1]<<8) + (uint32_t)(pdata[0]<<0);

    return data;
}
/*************************************************************************   
**	function name:	encode16_data 
**	description:	                  
**	input para:		 	
**	return:					                                         
**************************************************************************/
uint16_t sensor_encode16_data(const uint8_t *pdata)
{
    uint16_t data;

    data = (uint16_t)(pdata[1]<<8) + (uint16_t)(pdata[0]<<0);

    return data;
}
/*************************************************************************   
**	function name:	calculate_2point_long_lat_distance 
**	description:	                  
**	input para:		 	
**	return:			1mm		                                         
**************************************************************************/
uint32_t calculate_2point_long_lat_distance(int32_t longitude1, int32_t latitude1, int32_t longitude2, int32_t latitude2)
{
    double ew1, ns1, ew2, ns2,angle;
    uint32_t distance;//mm

    ew1 = (double)longitude1 / LONGTITUDE_LATITUDE_ENLARGE_TIMES * PI180;
    ns1 = (double)latitude1 / LONGTITUDE_LATITUDE_ENLARGE_TIMES * PI180;

    ew2 = (double)longitude2 / LONGTITUDE_LATITUDE_ENLARGE_TIMES * PI180;
    ns2 = (double)latitude2 / LONGTITUDE_LATITUDE_ENLARGE_TIMES * PI180;
    
    angle=cos(ns1)*cos(ns2)*cos(ew1-ew2)+sin(ns1)*sin(ns2);

    if(angle > 1.0)
    {
        angle = 1.0;
    }
    else if(angle < -1.0)
    {
        angle = -1.0;
    }

    distance = (uint32_t)(acos(angle)*EARTH_R);
    return distance;
}

void* sensor_memset(void *p, uint8_t v, uint32_t len)
{
    if(p == NULL)
    {
        return NULL;
    }
    
    uint8_t *pp;
    uint32_t i;
    
    pp = (uint8_t *)p;
    for(i=0; i<len; i++)
    {
        pp[i] = v;    
    }
    
    return p;
}


void *sensor_memcpy(void *dest, const void *src, uint32_t size)
{
    if((dest == NULL) || (src == NULL))
    {
        return NULL;
    }
    uint8_t *pDest, *pSrc;
    uint32_t i;
    
    pDest = (uint8_t *)dest;
    pSrc  = (uint8_t *)src;
    
    for(i=0; i<size; i++){
        pDest[i] = pSrc[i];
    }
    return dest;
}
