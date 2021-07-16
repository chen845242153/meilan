//
// Created by Administrator on 2020/8/24.
//

#ifndef MEILAN_JNIERROR_H
#define MEILAN_JNIERROR_H

#define JNI_ERROR_BASE_NUM                    0
#define JNI_SUCCESS                           (JNI_ERROR_BASE_NUM + 0)  ///< Successful command
#define JNI_ERROR_INTERNAL                    (JNI_ERROR_BASE_NUM + 3)  ///< Internal Error
#define JNI_ERROR_NO_MEM                      (JNI_ERROR_BASE_NUM + 4)  ///< No Memory for operation
#define JNI_ERROR_NOT_FOUND                   (JNI_ERROR_BASE_NUM + 5)  ///< Not found
#define JNI_ERROR_NOT_SUPPORTED               (JNI_ERROR_BASE_NUM + 6)  ///< Not supported
#define JNI_ERROR_INVALID_PARAM               (JNI_ERROR_BASE_NUM + 7)  ///< Invalid Parameter
#define JNI_ERROR_INVALID_STATE               (JNI_ERROR_BASE_NUM + 8)  ///< Invalid state, operation disallowed in this state
#define JNI_ERROR_INVALID_LENGTH              (JNI_ERROR_BASE_NUM + 9)  ///< Invalid Length
#define JNI_ERROR_INVALID_FLAGS               (JNI_ERROR_BASE_NUM + 10) ///< Invalid Flags
#define JNI_ERROR_INVALID_DATA                (JNI_ERROR_BASE_NUM + 11) ///< Invalid Data
#define JNI_ERROR_DATA_SIZE                   (JNI_ERROR_BASE_NUM + 12) ///< Data size exceeds limit
#define JNI_ERROR_TIMEOUT                     (JNI_ERROR_BASE_NUM + 13) ///< Operation timed out
#define JNI_ERROR_NULL                        (JNI_ERROR_BASE_NUM + 14) ///< Null Pointer
#define JNI_ERROR_FORBIDDEN                   (JNI_ERROR_BASE_NUM + 15) ///< Forbidden Operation
#define JNI_ERROR_INVALID_ADDR                (JNI_ERROR_BASE_NUM + 16) ///< Bad Memory Address
#define JNI_ERROR_BUSY                        (JNI_ERROR_BASE_NUM + 17) ///< Busy



#endif //MEILAN_JNIERROR_H
