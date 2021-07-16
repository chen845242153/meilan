package com.meilancycling.mema.db;

import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.db.greendao.AuthorEntityDao;
import com.meilancycling.mema.db.greendao.DeviceInformationEntityDao;
import com.meilancycling.mema.db.greendao.FileUploadEntityDao;
import com.meilancycling.mema.db.greendao.HeartZoneEntityDao;
import com.meilancycling.mema.db.greendao.LanguageEntityDao;
import com.meilancycling.mema.db.greendao.PowerZoneEntityDao;
import com.meilancycling.mema.db.greendao.SensorEntityDao;
import com.meilancycling.mema.db.greendao.WarningEntityDao;
import com.meilancycling.mema.ui.setting.WarningActivity;

import java.util.List;

/**
 * @Description: 数据库工具
 * @Author: lion
 * @CreateDate: 2020/11/10 5:54 PM
 */
public class DbUtils {
    private static DbUtils mDbUtils;

    private DbUtils() {
    }

    public static DbUtils getInstance() {
        if (null == mDbUtils) {
            mDbUtils = new DbUtils();
        }
        return mDbUtils;
    }

    /**
     * 删除用户表
     */
    public void deleteUserInfoEntity(UserInfoEntity userInfoEntity) {
        MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().delete(userInfoEntity);
    }

    /**
     * 添加设备
     */
    public void addDevice(DeviceInformationEntity deviceInformationEntity) {
        DeviceInformationEntity deviceInfo = MyApplication.mInstance.getDaoSession().getDeviceInformationEntityDao().queryBuilder()
                .where(DeviceInformationEntityDao.Properties.UserId.eq(deviceInformationEntity.getUserId()),
                        DeviceInformationEntityDao.Properties.MacAddress.eq(deviceInformationEntity.getMacAddress())).unique();
        if (null == deviceInfo) {
            MyApplication.mInstance.getDaoSession().getDeviceInformationEntityDao().insertOrReplace(deviceInformationEntity);
        } else {
            deviceInformationEntity.setId(deviceInfo.getId());
            MyApplication.mInstance.getDaoSession().getDeviceInformationEntityDao().update(deviceInformationEntity);
        }
    }

    /**
     * 更新设备
     */
    public void updateDevice(DeviceInformationEntity deviceInformationEntity) {
        MyApplication.mInstance.getDaoSession().getDeviceInformationEntityDao().update(deviceInformationEntity);
    }

    /**
     * 删除设备
     */
    public void deleteDevice(long userId, String macAddress) {
        DeviceInformationEntity deviceInfo = queryDeviceInfo(userId, macAddress);
        if (null != deviceInfo) {
            MyApplication.mInstance.getDaoSession().getDeviceInformationEntityDao().delete(deviceInfo);
        }
    }

    /**
     * 删除设备
     */
    public void deleteDevice(DeviceInformationEntity deviceInformationEntity) {
        MyApplication.mInstance.getDaoSession().getDeviceInformationEntityDao().delete(deviceInformationEntity);
    }

    /**
     * 设备列表
     */
    public List<DeviceInformationEntity> deviceInfoList(long userId) {
        return MyApplication.mInstance.getDaoSession().getDeviceInformationEntityDao().queryBuilder()
                .where(DeviceInformationEntityDao.Properties.UserId.eq(userId))
                .orderDesc(DeviceInformationEntityDao.Properties.DeviceSerialNumber)
                .list();
    }

    /**
     * 查询设备
     */
    public DeviceInformationEntity queryDeviceInfo(long userId, String macAddress) {
        return MyApplication.mInstance.getDaoSession().getDeviceInformationEntityDao().queryBuilder()
                .where(DeviceInformationEntityDao.Properties.UserId.eq(userId),
                        DeviceInformationEntityDao.Properties.MacAddress.eq(macAddress)).unique();
    }

    /**
     * 查询语言列表
     */
    public LanguageEntity getLanguageEntity(int languageNum) {
        return MyApplication.mInstance.getDaoSession().getLanguageEntityDao().queryBuilder().where(LanguageEntityDao.Properties.Num.eq(languageNum)).unique();
    }

    /**
     * 查询心率区间
     */
    public HeartZoneEntity queryHeartZoneEntity(long userId) {
        HeartZoneEntity unique = MyApplication.mInstance.getDaoSession().getHeartZoneEntityDao().queryBuilder().where(HeartZoneEntityDao.Properties.UserId.eq(userId)).unique();
        if (unique == null) {
            unique = new HeartZoneEntity();
            unique.setUserId(userId);
            unique.createHeartZoneEntity(unique);
        }
        return unique;
    }

    /**
     * 更新心率区间
     */
    public void updateHeartZoneEntity(HeartZoneEntity heartZoneEntity) {
        if (heartZoneEntity.getId() == null) {
            HeartZoneEntity entity = queryHeartZoneEntity(heartZoneEntity.getUserId());
            heartZoneEntity.setId(entity.getId());
        }
        MyApplication.mInstance.getDaoSession().getHeartZoneEntityDao().update(heartZoneEntity);
    }

    /**
     * 新增心率区间
     */
    public void addHeartZoneEntity(HeartZoneEntity heartZoneEntity) {
        MyApplication.mInstance.getDaoSession().getHeartZoneEntityDao().insertOrReplace(heartZoneEntity);
    }

    /**
     * 查询功率区间
     */
    public PowerZoneEntity queryPowerZoneEntity(long userId) {
        PowerZoneEntity unique = MyApplication.mInstance.getDaoSession().getPowerZoneEntityDao().queryBuilder().where(PowerZoneEntityDao.Properties.UserId.eq(userId)).unique();
        if (unique == null) {
            unique = new PowerZoneEntity();
            unique.setUserId(userId);
            unique.createPowerZoneEntity(unique);
        }
        return unique;
    }

    /**
     * 更新功率区间
     */
    public void updatePowerZoneEntity(PowerZoneEntity powerZoneEntity) {
        if (powerZoneEntity.getId() == null) {
            PowerZoneEntity entity = queryPowerZoneEntity(powerZoneEntity.getUserId());
            powerZoneEntity.setId(entity.getId());
        }
        MyApplication.mInstance.getDaoSession().getPowerZoneEntityDao().update(powerZoneEntity);
    }

    /**
     * 新增功率区间
     */
    public void addPowerZoneEntity(PowerZoneEntity powerZoneEntity) {
        MyApplication.mInstance.getDaoSession().getPowerZoneEntityDao().insertOrReplace(powerZoneEntity);
    }

    /**
     * 查询预警值
     */
    public WarningEntity queryWarningEntity(long userId) {
        WarningEntity unique = MyApplication.mInstance.getDaoSession().getWarningEntityDao().queryBuilder().where(WarningEntityDao.Properties.UserId.eq(userId)).unique();
        if (unique == null) {
            unique = new WarningEntity();
            unique.setUserId(userId);
            unique.setWarningInterval(WarningActivity.INTERVAL_DEFAULT);
            unique.setTimeValue(WarningActivity.TIME_MIN);
            unique.setDistanceValue(WarningActivity.DISTANCE_MIN);
            unique.setMinSpeedValue(WarningActivity.SPEED_MIN);
            unique.setMaxSpeedValue(WarningActivity.SPEED_MAX);
            unique.setMinCadenceValue(WarningActivity.CADENCE_MIN);
            unique.setMaxCadenceValue(WarningActivity.CADENCE_MAX);
            unique.setMinHeartValue(WarningActivity.HEART_MIN);
            unique.setMaxHeartValue(WarningActivity.HEART_MAX);
            unique.setMinPowerValue(WarningActivity.POWER_MIN);
            unique.setMaxPowerValue(WarningActivity.POWER_MAX);
            addWarningEntity(unique);
        }
        return unique;
    }

    /**
     * 更新预警值
     */
    public void updateWarningEntity(WarningEntity warningEntity) {
        if (warningEntity.getId() == null) {
            WarningEntity entity = queryWarningEntity(warningEntity.getUserId());
            warningEntity.setId(entity.getId());
        }
        MyApplication.mInstance.getDaoSession().getWarningEntityDao().update(warningEntity);
    }

    /**
     * 新增预警值
     */
    public void addWarningEntity(WarningEntity warningEntity) {
        MyApplication.mInstance.getDaoSession().getWarningEntityDao().insertOrReplace(warningEntity);
    }

    /**
     * 新增缓存文件
     */
    public void addFileUploadEntity(FileUploadEntity fileUploadEntity) {
        MyApplication.mInstance.getDaoSession().getFileUploadEntityDao().insertOrReplace(fileUploadEntity);
    }

    /**
     * 查询缓存文件
     */
    public List<FileUploadEntity> queryFileUploadEntity(long userId) {
        return MyApplication.mInstance.getDaoSession().getFileUploadEntityDao().queryBuilder()
                .where(FileUploadEntityDao.Properties.UserId.eq(userId)).list();
    }

    /**
     * 查询缓存文件通过名字
     */
    public FileUploadEntity queryFileUploadEntityByName(long userId, String filePath) {
        return MyApplication.mInstance.getDaoSession().getFileUploadEntityDao().queryBuilder()
                .where(FileUploadEntityDao.Properties.UserId.eq(userId),
                        FileUploadEntityDao.Properties.FilePath.eq(filePath)).unique();

    }

    /**
     * 更新缓存文件
     */
    public void updateFileUploadEntity(FileUploadEntity fileUploadEntity) {
        MyApplication.mInstance.getDaoSession().getFileUploadEntityDao().update(fileUploadEntity);
    }

    /**
     * 删除缓存文件
     */
    public void deleteFileUploadEntity(FileUploadEntity fileUploadEntity) {
        MyApplication.mInstance.getDaoSession().getFileUploadEntityDao().delete(fileUploadEntity);
    }

    /**
     * 查询第三方授权
     */
    public AuthorEntity queryAuthorEntity(long userId, int type) {
        return MyApplication.mInstance.getDaoSession().getAuthorEntityDao().queryBuilder()
                .where(AuthorEntityDao.Properties.UserId.eq(userId),
                        AuthorEntityDao.Properties.PlatformType.eq(type)).unique();
    }

    /**
     * 更新第三方授权
     */
    public void updateAuthorEntity(AuthorEntity authorEntity) {
        MyApplication.mInstance.getDaoSession().getAuthorEntityDao().update(authorEntity);
    }

    /**
     * 查询第三方授权列表
     */
    public List<AuthorEntity> queryAuthorList(long userId) {
        return MyApplication.mInstance.getDaoSession().getAuthorEntityDao()
                .queryBuilder()
                .where(AuthorEntityDao.Properties.UserId.eq(userId))
                .list();
    }

    /**
     * 删除第三方授权
     */
    public void deleteAuthorEntity(AuthorEntity authorEntity) {
        MyApplication.mInstance.getDaoSession().getAuthorEntityDao().delete(authorEntity);
    }

    /**
     * 添加第三方授权
     */
    public void addAuthorEntity(AuthorEntity authorEntity) {
        AuthorEntity author = queryAuthorEntity(authorEntity.getUserId(), authorEntity.getPlatformType());
        if (author == null) {
            MyApplication.mInstance.getDaoSession().getAuthorEntityDao().insertOrReplace(authorEntity);
        } else {
            authorEntity.setId(author.getId());
            MyApplication.mInstance.getDaoSession().getAuthorEntityDao().update(authorEntity);
        }
    }

    /**
     * 添加sensor
     */
    public void addSensorEntity(SensorEntity sensorEntity) {
        MyApplication.mInstance.getDaoSession().getSensorEntityDao().insertOrReplace(sensorEntity);
    }

    /**
     * 查询sensor集合
     */
    public List<SensorEntity> querySensorEntityList(long userId) {
        return MyApplication.mInstance.getDaoSession().getSensorEntityDao().queryBuilder().where(SensorEntityDao.Properties.UserId.eq(userId)).list();
    }

    /**
     * 查询sensor
     */
    public SensorEntity querySensorEntity(long userId, String macAddress) {
        return MyApplication.mInstance.getDaoSession().getSensorEntityDao().queryBuilder().
                where(SensorEntityDao.Properties.UserId.eq(userId), SensorEntityDao.Properties.MacAddress.eq(macAddress)).
                unique();
    }

    /**
     * 更新sensor状态
     */
    public void updateSensorStatus(long userId, String macAddress, int status) {
        SensorEntity sensorEntity = MyApplication.mInstance.getDaoSession().getSensorEntityDao().queryBuilder().
                where(SensorEntityDao.Properties.UserId.eq(userId), SensorEntityDao.Properties.MacAddress.eq(macAddress)).
                unique();
        if (sensorEntity != null) {
            sensorEntity.setConnectStatus(status);
            MyApplication.mInstance.getDaoSession().getSensorEntityDao().update(sensorEntity);
        }
    }

    /**
     * 更新sensor状态
     */
    public void updateSensorType(long userId, String macAddress, int type) {
        SensorEntity sensorEntity = MyApplication.mInstance.getDaoSession().getSensorEntityDao().queryBuilder().
                where(SensorEntityDao.Properties.UserId.eq(userId), SensorEntityDao.Properties.MacAddress.eq(macAddress)).
                unique();
        if (sensorEntity != null) {
            sensorEntity.setSensorType(type);
            MyApplication.mInstance.getDaoSession().getSensorEntityDao().update(sensorEntity);
        }
    }

    /**
     * 更新sensor轮径
     */
    public void updateSensorWheel(long userId, String macAddress, int wheel) {
        SensorEntity sensorEntity = MyApplication.mInstance.getDaoSession().getSensorEntityDao().queryBuilder().
                where(SensorEntityDao.Properties.UserId.eq(userId), SensorEntityDao.Properties.MacAddress.eq(macAddress)).
                unique();
        if (sensorEntity != null) {
            sensorEntity.setWheelValue(wheel);
            MyApplication.mInstance.getDaoSession().getSensorEntityDao().update(sensorEntity);
        }
    }

    /**
     * 更新sensor
     */
    public void updateSensor(SensorEntity sensorEntity) {
        MyApplication.mInstance.getDaoSession().getSensorEntityDao().update(sensorEntity);
    }
}
