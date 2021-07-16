package com.meilancycling.mema.db.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.meilancycling.mema.db.AuthorEntity;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.db.FileUploadEntity;
import com.meilancycling.mema.db.HeartZoneEntity;
import com.meilancycling.mema.db.LanguageEntity;
import com.meilancycling.mema.db.PowerZoneEntity;
import com.meilancycling.mema.db.SensorEntity;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.db.WarningEntity;
import com.meilancycling.mema.db.WheelEntity;

import com.meilancycling.mema.db.greendao.AuthorEntityDao;
import com.meilancycling.mema.db.greendao.DeviceInformationEntityDao;
import com.meilancycling.mema.db.greendao.FileUploadEntityDao;
import com.meilancycling.mema.db.greendao.HeartZoneEntityDao;
import com.meilancycling.mema.db.greendao.LanguageEntityDao;
import com.meilancycling.mema.db.greendao.PowerZoneEntityDao;
import com.meilancycling.mema.db.greendao.SensorEntityDao;
import com.meilancycling.mema.db.greendao.UserInfoEntityDao;
import com.meilancycling.mema.db.greendao.WarningEntityDao;
import com.meilancycling.mema.db.greendao.WheelEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig authorEntityDaoConfig;
    private final DaoConfig deviceInformationEntityDaoConfig;
    private final DaoConfig fileUploadEntityDaoConfig;
    private final DaoConfig heartZoneEntityDaoConfig;
    private final DaoConfig languageEntityDaoConfig;
    private final DaoConfig powerZoneEntityDaoConfig;
    private final DaoConfig sensorEntityDaoConfig;
    private final DaoConfig userInfoEntityDaoConfig;
    private final DaoConfig warningEntityDaoConfig;
    private final DaoConfig wheelEntityDaoConfig;

    private final AuthorEntityDao authorEntityDao;
    private final DeviceInformationEntityDao deviceInformationEntityDao;
    private final FileUploadEntityDao fileUploadEntityDao;
    private final HeartZoneEntityDao heartZoneEntityDao;
    private final LanguageEntityDao languageEntityDao;
    private final PowerZoneEntityDao powerZoneEntityDao;
    private final SensorEntityDao sensorEntityDao;
    private final UserInfoEntityDao userInfoEntityDao;
    private final WarningEntityDao warningEntityDao;
    private final WheelEntityDao wheelEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        authorEntityDaoConfig = daoConfigMap.get(AuthorEntityDao.class).clone();
        authorEntityDaoConfig.initIdentityScope(type);

        deviceInformationEntityDaoConfig = daoConfigMap.get(DeviceInformationEntityDao.class).clone();
        deviceInformationEntityDaoConfig.initIdentityScope(type);

        fileUploadEntityDaoConfig = daoConfigMap.get(FileUploadEntityDao.class).clone();
        fileUploadEntityDaoConfig.initIdentityScope(type);

        heartZoneEntityDaoConfig = daoConfigMap.get(HeartZoneEntityDao.class).clone();
        heartZoneEntityDaoConfig.initIdentityScope(type);

        languageEntityDaoConfig = daoConfigMap.get(LanguageEntityDao.class).clone();
        languageEntityDaoConfig.initIdentityScope(type);

        powerZoneEntityDaoConfig = daoConfigMap.get(PowerZoneEntityDao.class).clone();
        powerZoneEntityDaoConfig.initIdentityScope(type);

        sensorEntityDaoConfig = daoConfigMap.get(SensorEntityDao.class).clone();
        sensorEntityDaoConfig.initIdentityScope(type);

        userInfoEntityDaoConfig = daoConfigMap.get(UserInfoEntityDao.class).clone();
        userInfoEntityDaoConfig.initIdentityScope(type);

        warningEntityDaoConfig = daoConfigMap.get(WarningEntityDao.class).clone();
        warningEntityDaoConfig.initIdentityScope(type);

        wheelEntityDaoConfig = daoConfigMap.get(WheelEntityDao.class).clone();
        wheelEntityDaoConfig.initIdentityScope(type);

        authorEntityDao = new AuthorEntityDao(authorEntityDaoConfig, this);
        deviceInformationEntityDao = new DeviceInformationEntityDao(deviceInformationEntityDaoConfig, this);
        fileUploadEntityDao = new FileUploadEntityDao(fileUploadEntityDaoConfig, this);
        heartZoneEntityDao = new HeartZoneEntityDao(heartZoneEntityDaoConfig, this);
        languageEntityDao = new LanguageEntityDao(languageEntityDaoConfig, this);
        powerZoneEntityDao = new PowerZoneEntityDao(powerZoneEntityDaoConfig, this);
        sensorEntityDao = new SensorEntityDao(sensorEntityDaoConfig, this);
        userInfoEntityDao = new UserInfoEntityDao(userInfoEntityDaoConfig, this);
        warningEntityDao = new WarningEntityDao(warningEntityDaoConfig, this);
        wheelEntityDao = new WheelEntityDao(wheelEntityDaoConfig, this);

        registerDao(AuthorEntity.class, authorEntityDao);
        registerDao(DeviceInformationEntity.class, deviceInformationEntityDao);
        registerDao(FileUploadEntity.class, fileUploadEntityDao);
        registerDao(HeartZoneEntity.class, heartZoneEntityDao);
        registerDao(LanguageEntity.class, languageEntityDao);
        registerDao(PowerZoneEntity.class, powerZoneEntityDao);
        registerDao(SensorEntity.class, sensorEntityDao);
        registerDao(UserInfoEntity.class, userInfoEntityDao);
        registerDao(WarningEntity.class, warningEntityDao);
        registerDao(WheelEntity.class, wheelEntityDao);
    }
    
    public void clear() {
        authorEntityDaoConfig.clearIdentityScope();
        deviceInformationEntityDaoConfig.clearIdentityScope();
        fileUploadEntityDaoConfig.clearIdentityScope();
        heartZoneEntityDaoConfig.clearIdentityScope();
        languageEntityDaoConfig.clearIdentityScope();
        powerZoneEntityDaoConfig.clearIdentityScope();
        sensorEntityDaoConfig.clearIdentityScope();
        userInfoEntityDaoConfig.clearIdentityScope();
        warningEntityDaoConfig.clearIdentityScope();
        wheelEntityDaoConfig.clearIdentityScope();
    }

    public AuthorEntityDao getAuthorEntityDao() {
        return authorEntityDao;
    }

    public DeviceInformationEntityDao getDeviceInformationEntityDao() {
        return deviceInformationEntityDao;
    }

    public FileUploadEntityDao getFileUploadEntityDao() {
        return fileUploadEntityDao;
    }

    public HeartZoneEntityDao getHeartZoneEntityDao() {
        return heartZoneEntityDao;
    }

    public LanguageEntityDao getLanguageEntityDao() {
        return languageEntityDao;
    }

    public PowerZoneEntityDao getPowerZoneEntityDao() {
        return powerZoneEntityDao;
    }

    public SensorEntityDao getSensorEntityDao() {
        return sensorEntityDao;
    }

    public UserInfoEntityDao getUserInfoEntityDao() {
        return userInfoEntityDao;
    }

    public WarningEntityDao getWarningEntityDao() {
        return warningEntityDao;
    }

    public WheelEntityDao getWheelEntityDao() {
        return wheelEntityDao;
    }

}