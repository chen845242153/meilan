package com.meilancycling.mema.db.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.meilancycling.mema.db.WarningEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WARNING_ENTITY".
*/
public class WarningEntityDao extends AbstractDao<WarningEntity, Long> {

    public static final String TABLENAME = "WARNING_ENTITY";

    /**
     * Properties of entity WarningEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, long.class, "userId", false, "USER_ID");
        public final static Property WarningInterval = new Property(2, int.class, "warningInterval", false, "WARNING_INTERVAL");
        public final static Property TimeValue = new Property(3, int.class, "timeValue", false, "TIME_VALUE");
        public final static Property TimeSwitch = new Property(4, int.class, "timeSwitch", false, "TIME_SWITCH");
        public final static Property DistanceValue = new Property(5, int.class, "distanceValue", false, "DISTANCE_VALUE");
        public final static Property DistanceSwitch = new Property(6, int.class, "distanceSwitch", false, "DISTANCE_SWITCH");
        public final static Property MinSpeedValue = new Property(7, int.class, "minSpeedValue", false, "MIN_SPEED_VALUE");
        public final static Property MinSpeedSwitch = new Property(8, int.class, "minSpeedSwitch", false, "MIN_SPEED_SWITCH");
        public final static Property MaxSpeedValue = new Property(9, int.class, "maxSpeedValue", false, "MAX_SPEED_VALUE");
        public final static Property MaxSpeedSwitch = new Property(10, int.class, "maxSpeedSwitch", false, "MAX_SPEED_SWITCH");
        public final static Property MinCadenceValue = new Property(11, int.class, "minCadenceValue", false, "MIN_CADENCE_VALUE");
        public final static Property MinCadenceSwitch = new Property(12, int.class, "minCadenceSwitch", false, "MIN_CADENCE_SWITCH");
        public final static Property MaxCadenceValue = new Property(13, int.class, "maxCadenceValue", false, "MAX_CADENCE_VALUE");
        public final static Property MaxCadenceSwitch = new Property(14, int.class, "maxCadenceSwitch", false, "MAX_CADENCE_SWITCH");
        public final static Property MinHeartValue = new Property(15, int.class, "minHeartValue", false, "MIN_HEART_VALUE");
        public final static Property MinHeartSwitch = new Property(16, int.class, "minHeartSwitch", false, "MIN_HEART_SWITCH");
        public final static Property MaxHeartValue = new Property(17, int.class, "maxHeartValue", false, "MAX_HEART_VALUE");
        public final static Property MaxHeartSwitch = new Property(18, int.class, "maxHeartSwitch", false, "MAX_HEART_SWITCH");
        public final static Property MinPowerValue = new Property(19, int.class, "minPowerValue", false, "MIN_POWER_VALUE");
        public final static Property MinPowerSwitch = new Property(20, int.class, "minPowerSwitch", false, "MIN_POWER_SWITCH");
        public final static Property MaxPowerValue = new Property(21, int.class, "maxPowerValue", false, "MAX_POWER_VALUE");
        public final static Property MaxPowerSwitch = new Property(22, int.class, "maxPowerSwitch", false, "MAX_POWER_SWITCH");
        public final static Property VoiceSwitch = new Property(23, int.class, "voiceSwitch", false, "VOICE_SWITCH");
        public final static Property BrightScreen = new Property(24, int.class, "brightScreen", false, "BRIGHT_SCREEN");
    }


    public WarningEntityDao(DaoConfig config) {
        super(config);
    }
    
    public WarningEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WARNING_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USER_ID\" INTEGER NOT NULL ," + // 1: userId
                "\"WARNING_INTERVAL\" INTEGER NOT NULL ," + // 2: warningInterval
                "\"TIME_VALUE\" INTEGER NOT NULL ," + // 3: timeValue
                "\"TIME_SWITCH\" INTEGER NOT NULL ," + // 4: timeSwitch
                "\"DISTANCE_VALUE\" INTEGER NOT NULL ," + // 5: distanceValue
                "\"DISTANCE_SWITCH\" INTEGER NOT NULL ," + // 6: distanceSwitch
                "\"MIN_SPEED_VALUE\" INTEGER NOT NULL ," + // 7: minSpeedValue
                "\"MIN_SPEED_SWITCH\" INTEGER NOT NULL ," + // 8: minSpeedSwitch
                "\"MAX_SPEED_VALUE\" INTEGER NOT NULL ," + // 9: maxSpeedValue
                "\"MAX_SPEED_SWITCH\" INTEGER NOT NULL ," + // 10: maxSpeedSwitch
                "\"MIN_CADENCE_VALUE\" INTEGER NOT NULL ," + // 11: minCadenceValue
                "\"MIN_CADENCE_SWITCH\" INTEGER NOT NULL ," + // 12: minCadenceSwitch
                "\"MAX_CADENCE_VALUE\" INTEGER NOT NULL ," + // 13: maxCadenceValue
                "\"MAX_CADENCE_SWITCH\" INTEGER NOT NULL ," + // 14: maxCadenceSwitch
                "\"MIN_HEART_VALUE\" INTEGER NOT NULL ," + // 15: minHeartValue
                "\"MIN_HEART_SWITCH\" INTEGER NOT NULL ," + // 16: minHeartSwitch
                "\"MAX_HEART_VALUE\" INTEGER NOT NULL ," + // 17: maxHeartValue
                "\"MAX_HEART_SWITCH\" INTEGER NOT NULL ," + // 18: maxHeartSwitch
                "\"MIN_POWER_VALUE\" INTEGER NOT NULL ," + // 19: minPowerValue
                "\"MIN_POWER_SWITCH\" INTEGER NOT NULL ," + // 20: minPowerSwitch
                "\"MAX_POWER_VALUE\" INTEGER NOT NULL ," + // 21: maxPowerValue
                "\"MAX_POWER_SWITCH\" INTEGER NOT NULL ," + // 22: maxPowerSwitch
                "\"VOICE_SWITCH\" INTEGER NOT NULL ," + // 23: voiceSwitch
                "\"BRIGHT_SCREEN\" INTEGER NOT NULL );"); // 24: brightScreen
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WARNING_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, WarningEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUserId());
        stmt.bindLong(3, entity.getWarningInterval());
        stmt.bindLong(4, entity.getTimeValue());
        stmt.bindLong(5, entity.getTimeSwitch());
        stmt.bindLong(6, entity.getDistanceValue());
        stmt.bindLong(7, entity.getDistanceSwitch());
        stmt.bindLong(8, entity.getMinSpeedValue());
        stmt.bindLong(9, entity.getMinSpeedSwitch());
        stmt.bindLong(10, entity.getMaxSpeedValue());
        stmt.bindLong(11, entity.getMaxSpeedSwitch());
        stmt.bindLong(12, entity.getMinCadenceValue());
        stmt.bindLong(13, entity.getMinCadenceSwitch());
        stmt.bindLong(14, entity.getMaxCadenceValue());
        stmt.bindLong(15, entity.getMaxCadenceSwitch());
        stmt.bindLong(16, entity.getMinHeartValue());
        stmt.bindLong(17, entity.getMinHeartSwitch());
        stmt.bindLong(18, entity.getMaxHeartValue());
        stmt.bindLong(19, entity.getMaxHeartSwitch());
        stmt.bindLong(20, entity.getMinPowerValue());
        stmt.bindLong(21, entity.getMinPowerSwitch());
        stmt.bindLong(22, entity.getMaxPowerValue());
        stmt.bindLong(23, entity.getMaxPowerSwitch());
        stmt.bindLong(24, entity.getVoiceSwitch());
        stmt.bindLong(25, entity.getBrightScreen());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, WarningEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUserId());
        stmt.bindLong(3, entity.getWarningInterval());
        stmt.bindLong(4, entity.getTimeValue());
        stmt.bindLong(5, entity.getTimeSwitch());
        stmt.bindLong(6, entity.getDistanceValue());
        stmt.bindLong(7, entity.getDistanceSwitch());
        stmt.bindLong(8, entity.getMinSpeedValue());
        stmt.bindLong(9, entity.getMinSpeedSwitch());
        stmt.bindLong(10, entity.getMaxSpeedValue());
        stmt.bindLong(11, entity.getMaxSpeedSwitch());
        stmt.bindLong(12, entity.getMinCadenceValue());
        stmt.bindLong(13, entity.getMinCadenceSwitch());
        stmt.bindLong(14, entity.getMaxCadenceValue());
        stmt.bindLong(15, entity.getMaxCadenceSwitch());
        stmt.bindLong(16, entity.getMinHeartValue());
        stmt.bindLong(17, entity.getMinHeartSwitch());
        stmt.bindLong(18, entity.getMaxHeartValue());
        stmt.bindLong(19, entity.getMaxHeartSwitch());
        stmt.bindLong(20, entity.getMinPowerValue());
        stmt.bindLong(21, entity.getMinPowerSwitch());
        stmt.bindLong(22, entity.getMaxPowerValue());
        stmt.bindLong(23, entity.getMaxPowerSwitch());
        stmt.bindLong(24, entity.getVoiceSwitch());
        stmt.bindLong(25, entity.getBrightScreen());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public WarningEntity readEntity(Cursor cursor, int offset) {
        WarningEntity entity = new WarningEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // userId
            cursor.getInt(offset + 2), // warningInterval
            cursor.getInt(offset + 3), // timeValue
            cursor.getInt(offset + 4), // timeSwitch
            cursor.getInt(offset + 5), // distanceValue
            cursor.getInt(offset + 6), // distanceSwitch
            cursor.getInt(offset + 7), // minSpeedValue
            cursor.getInt(offset + 8), // minSpeedSwitch
            cursor.getInt(offset + 9), // maxSpeedValue
            cursor.getInt(offset + 10), // maxSpeedSwitch
            cursor.getInt(offset + 11), // minCadenceValue
            cursor.getInt(offset + 12), // minCadenceSwitch
            cursor.getInt(offset + 13), // maxCadenceValue
            cursor.getInt(offset + 14), // maxCadenceSwitch
            cursor.getInt(offset + 15), // minHeartValue
            cursor.getInt(offset + 16), // minHeartSwitch
            cursor.getInt(offset + 17), // maxHeartValue
            cursor.getInt(offset + 18), // maxHeartSwitch
            cursor.getInt(offset + 19), // minPowerValue
            cursor.getInt(offset + 20), // minPowerSwitch
            cursor.getInt(offset + 21), // maxPowerValue
            cursor.getInt(offset + 22), // maxPowerSwitch
            cursor.getInt(offset + 23), // voiceSwitch
            cursor.getInt(offset + 24) // brightScreen
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, WarningEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.getLong(offset + 1));
        entity.setWarningInterval(cursor.getInt(offset + 2));
        entity.setTimeValue(cursor.getInt(offset + 3));
        entity.setTimeSwitch(cursor.getInt(offset + 4));
        entity.setDistanceValue(cursor.getInt(offset + 5));
        entity.setDistanceSwitch(cursor.getInt(offset + 6));
        entity.setMinSpeedValue(cursor.getInt(offset + 7));
        entity.setMinSpeedSwitch(cursor.getInt(offset + 8));
        entity.setMaxSpeedValue(cursor.getInt(offset + 9));
        entity.setMaxSpeedSwitch(cursor.getInt(offset + 10));
        entity.setMinCadenceValue(cursor.getInt(offset + 11));
        entity.setMinCadenceSwitch(cursor.getInt(offset + 12));
        entity.setMaxCadenceValue(cursor.getInt(offset + 13));
        entity.setMaxCadenceSwitch(cursor.getInt(offset + 14));
        entity.setMinHeartValue(cursor.getInt(offset + 15));
        entity.setMinHeartSwitch(cursor.getInt(offset + 16));
        entity.setMaxHeartValue(cursor.getInt(offset + 17));
        entity.setMaxHeartSwitch(cursor.getInt(offset + 18));
        entity.setMinPowerValue(cursor.getInt(offset + 19));
        entity.setMinPowerSwitch(cursor.getInt(offset + 20));
        entity.setMaxPowerValue(cursor.getInt(offset + 21));
        entity.setMaxPowerSwitch(cursor.getInt(offset + 22));
        entity.setVoiceSwitch(cursor.getInt(offset + 23));
        entity.setBrightScreen(cursor.getInt(offset + 24));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(WarningEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(WarningEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(WarningEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}