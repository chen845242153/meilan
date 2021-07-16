#ifndef __SENSOR_API_H__
#define __SENSOR_API_H__

#include "stdint.h"

#define SENSOR_MAX_DATA(a,b)                              ((a)>(b) ? (a) : (b))
#define SENSOR_MIN_DATA(a,b)                              ((a)>(b) ? (b) : (a))

/**@brief Function for decoding a uint16 value.
 *
 * @param[in]   p_encoded_data   Buffer where the encoded data is stored.
 *
 * @return      Decoded value.
 */
static __inline uint16_t uint16_decode(const uint8_t * p_encoded_data)
{
    return ( (((uint16_t)((uint8_t *)p_encoded_data)[0])) |
             (((uint16_t)((uint8_t *)p_encoded_data)[1]) << 8 ));
}
/**@brief Function for decoding a uint32 value.
 *
 * @param[in]   p_encoded_data   Buffer where the encoded data is stored.
 *
 * @return      Decoded value.
 */
static __inline uint32_t uint32_decode(const uint8_t * p_encoded_data)
{
    return ( (((uint32_t)((uint8_t *)p_encoded_data)[0]) << 0)  |
             (((uint32_t)((uint8_t *)p_encoded_data)[1]) << 8)  |
             (((uint32_t)((uint8_t *)p_encoded_data)[2]) << 16) |
             (((uint32_t)((uint8_t *)p_encoded_data)[3]) << 24 ));
}

uint32_t    calculate_avg_value_base_sum_divid_count(uint32_t cur, uint32_t *p_sum, uint32_t *p_count);
int32_t     calculate_signed_avg_value_base_sum_divid_count(int32_t cur, int32_t *p_sum, uint32_t *p_count);
uint32_t    calculate_max_value_expect_invalid_data(uint32_t cur, uint32_t max, uint32_t invalid);
uint32_t    calculate_min_value_expect_invalid_data(uint32_t cur, uint32_t min, uint32_t invalid);
void        calculate_signed_max_min(int32_t cur, int32_t *p_max, int32_t *p_min, int32_t invalid);
uint32_t    sensor_abs(uint32_t a, uint32_t b);
void        sensor_decode32_data(uint32_t data, uint8_t *pdata);
void        sensor_decode16_data(uint16_t data, uint8_t *pdata);
uint32_t    sensor_encode32_data(const uint8_t *pdata);
uint16_t    sensor_encode16_data(const uint8_t *pdata);
uint32_t    calculate_2point_long_lat_distance(int32_t longitude1, int32_t latitude1, int32_t longitude2, int32_t latitude2);
void*       sensor_memset(void *p, uint8_t v, uint32_t len);
void*       sensor_memcpy(void *dest, const void *src, uint32_t size);





#endif

