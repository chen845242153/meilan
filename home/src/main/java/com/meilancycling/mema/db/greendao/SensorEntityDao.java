package com.meilancycling.mema.db.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.meilancycling.mema.db.SensorEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SENSOR_ENTITY".
*/
public class SensorEntityDao extends AbstractDao<SensorEntity, Long> {

    public static final String TABLENAME = "SENSOR_ENTITY";

    /**
     * Properties of entity SensorEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, long.class, "userId", false, "USER_ID");
        public final static Property MacAddress = new Property(2, String.class, "macAddress", false, "MAC_ADDRESS");
        public final static Property SensorName = new Property(3, String.class, "sensorName", false, "SENSOR_NAME");
        public final static Property SensorType = new Property(4, int.class, "sensorType", false, "SENSOR_TYPE");
        public final static Property WheelValue = new Property(5, int.class, "wheelValue", false, "WHEEL_VALUE");
        public final static Property ConnectStatus = new Property(6, int.class, "connectStatus", false, "CONNECT_STATUS");
    }


    public SensorEntityDao(DaoConfig config) {
        super(config);
    }
    
    public SensorEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SENSOR_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USER_ID\" INTEGER NOT NULL ," + // 1: userId
                "\"MAC_ADDRESS\" TEXT," + // 2: macAddress
                "\"SENSOR_NAME\" TEXT," + // 3: sensorName
                "\"SENSOR_TYPE\" INTEGER NOT NULL ," + // 4: sensorType
                "\"WHEEL_VALUE\" INTEGER NOT NULL ," + // 5: wheelValue
                "\"CONNECT_STATUS\" INTEGER NOT NULL );"); // 6: connectStatus
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SENSOR_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SensorEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUserId());
 
        String macAddress = entity.getMacAddress();
        if (macAddress != null) {
            stmt.bindString(3, macAddress);
        }
 
        String sensorName = entity.getSensorName();
        if (sensorName != null) {
            stmt.bindString(4, sensorName);
        }
        stmt.bindLong(5, entity.getSensorType());
        stmt.bindLong(6, entity.getWheelValue());
        stmt.bindLong(7, entity.getConnectStatus());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SensorEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUserId());
 
        String macAddress = entity.getMacAddress();
        if (macAddress != null) {
            stmt.bindString(3, macAddress);
        }
 
        String sensorName = entity.getSensorName();
        if (sensorName != null) {
            stmt.bindString(4, sensorName);
        }
        stmt.bindLong(5, entity.getSensorType());
        stmt.bindLong(6, entity.getWheelValue());
        stmt.bindLong(7, entity.getConnectStatus());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SensorEntity readEntity(Cursor cursor, int offset) {
        SensorEntity entity = new SensorEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // macAddress
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // sensorName
            cursor.getInt(offset + 4), // sensorType
            cursor.getInt(offset + 5), // wheelValue
            cursor.getInt(offset + 6) // connectStatus
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SensorEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.getLong(offset + 1));
        entity.setMacAddress(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSensorName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSensorType(cursor.getInt(offset + 4));
        entity.setWheelValue(cursor.getInt(offset + 5));
        entity.setConnectStatus(cursor.getInt(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SensorEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SensorEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SensorEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}