# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        lib_ble #.so库名 可自定义

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        home/src/main/jni/sensor/android_interface/lib_ble.c
        home/src/main/jni/sensor/ios_interface/sensorCInputInterface.c
        home/src/main/jni/sensor/common/sensorTick.c
        home/src/main/jni/sensor/common/sensorAPI.c
        home/src/main/jni/sensorCallback.c
        home/src/main/jni/sensor/common/sensorModule.c
        home/src/main/jni/sensor/common/config/sensorParametersConfig.c
        home/src/main/jni/sensor/common/hrm/sensorHRM.c
        home/src/main/jni/sensor/common/csc/sensorSPD.c
        home/src/main/jni/sensor/common/csc/sensorCAD.c
        home/src/main/jni/sensor/common/csc/sensorCSC.c
        home/src/main/jni/sensor/common/sensorBaseCalculateProcess.c
        home/src/main/jni/sensor/common/gps/sensorGPS.c
        home/src/main/jni/sensor/common/environment/sensorEnvironment.c
        home/src/main/jni/sensor/common/power/sensorPower.c
        home/src/main/jni/sensor/android_interface/androidOutputInterface.c
        home/src/main/jni/fit/fileOperate.c
        home/src/main/jni/fit/fit_activity.c
        home/src/main/jni/fit/fit_activity_encode.c
        home/src/main/jni/fit/fit_crc.c
        home/src/main/jni/fit/fit_encode.c
        home/src/main/jni/fit/fit.c
        ) #源文件所在目录，多文件的话就都加进来
# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.
find_library( # Sets the name of the path variable.
        log-lib
        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)
# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.
target_link_libraries( # Specifies the target library.
        lib_ble #.so库名 可自定义
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})
add_library(ble SHARED IMPORTED)
set_target_properties(ble PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/app/src/main/jniLibs/${ANDROID_ABI}/libble.so)