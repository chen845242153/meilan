//
// Created by Administrator on 2020/8/28.
//

#ifndef MEILAN_FILEOPERATE_H
#define MEILAN_FILEOPERATE_H

#include "../stdint.h"

void file_init(void);

void file_write(void *p, uint32_t length);

uint32_t file_seek(uint32_t pos);

uint32_t file_size(void);

#endif //MEILAN_FILEOPERATE_H
