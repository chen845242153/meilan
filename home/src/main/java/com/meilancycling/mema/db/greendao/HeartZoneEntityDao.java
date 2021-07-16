package com.meilancycling.mema.db.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.meilancycling.mema.db.HeartZoneEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HEART_ZONE_ENTITY".
*/
public class HeartZoneEntityDao extends AbstractDao<HeartZoneEntity, Long> {

    public static final String TABLENAME = "HEART_ZONE_ENTITY";

    /**
     * Properties of entity HeartZoneEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, long.class, "userId", false, "USER_ID");
        public final static Property Select = new Property(2, int.class, "select", false, "SELECT");
        public final static Property MaxValue = new Property(3, int.class, "maxValue", false, "MAX_VALUE");
        public final static Property MaxZoneValue1 = new Property(4, int.class, "maxZoneValue1", false, "MAX_ZONE_VALUE1");
        public final static Property MaxZoneValue2 = new Property(5, int.class, "maxZoneValue2", false, "MAX_ZONE_VALUE2");
        public final static Property MaxZoneValue3 = new Property(6, int.class, "maxZoneValue3", false, "MAX_ZONE_VALUE3");
        public final static Property MaxZoneValue4 = new Property(7, int.class, "maxZoneValue4", false, "MAX_ZONE_VALUE4");
        public final static Property MaxZoneValue5 = new Property(8, int.class, "maxZoneValue5", false, "MAX_ZONE_VALUE5");
        public final static Property ReserveMaxValue = new Property(9, int.class, "reserveMaxValue", false, "RESERVE_MAX_VALUE");
        public final static Property ReserveValue = new Property(10, int.class, "reserveValue", false, "RESERVE_VALUE");
        public final static Property ReserveZoneValue1 = new Property(11, int.class, "reserveZoneValue1", false, "RESERVE_ZONE_VALUE1");
        public final static Property ReserveZoneValue2 = new Property(12, int.class, "reserveZoneValue2", false, "RESERVE_ZONE_VALUE2");
        public final static Property ReserveZoneValue3 = new Property(13, int.class, "reserveZoneValue3", false, "RESERVE_ZONE_VALUE3");
        public final static Property ReserveZoneValue4 = new Property(14, int.class, "reserveZoneValue4", false, "RESERVE_ZONE_VALUE4");
        public final static Property ReserveZoneValue5 = new Property(15, int.class, "reserveZoneValue5", false, "RESERVE_ZONE_VALUE5");
    }


    public HeartZoneEntityDao(DaoConfig config) {
        super(config);
    }
    
    public HeartZoneEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HEART_ZONE_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USER_ID\" INTEGER NOT NULL ," + // 1: userId
                "\"SELECT\" INTEGER NOT NULL ," + // 2: select
                "\"MAX_VALUE\" INTEGER NOT NULL ," + // 3: maxValue
                "\"MAX_ZONE_VALUE1\" INTEGER NOT NULL ," + // 4: maxZoneValue1
                "\"MAX_ZONE_VALUE2\" INTEGER NOT NULL ," + // 5: maxZoneValue2
                "\"MAX_ZONE_VALUE3\" INTEGER NOT NULL ," + // 6: maxZoneValue3
                "\"MAX_ZONE_VALUE4\" INTEGER NOT NULL ," + // 7: maxZoneValue4
                "\"MAX_ZONE_VALUE5\" INTEGER NOT NULL ," + // 8: maxZoneValue5
                "\"RESERVE_MAX_VALUE\" INTEGER NOT NULL ," + // 9: reserveMaxValue
                "\"RESERVE_VALUE\" INTEGER NOT NULL ," + // 10: reserveValue
                "\"RESERVE_ZONE_VALUE1\" INTEGER NOT NULL ," + // 11: reserveZoneValue1
                "\"RESERVE_ZONE_VALUE2\" INTEGER NOT NULL ," + // 12: reserveZoneValue2
                "\"RESERVE_ZONE_VALUE3\" INTEGER NOT NULL ," + // 13: reserveZoneValue3
                "\"RESERVE_ZONE_VALUE4\" INTEGER NOT NULL ," + // 14: reserveZoneValue4
                "\"RESERVE_ZONE_VALUE5\" INTEGER NOT NULL );"); // 15: reserveZoneValue5
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HEART_ZONE_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, HeartZoneEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUserId());
        stmt.bindLong(3, entity.getSelect());
        stmt.bindLong(4, entity.getMaxValue());
        stmt.bindLong(5, entity.getMaxZoneValue1());
        stmt.bindLong(6, entity.getMaxZoneValue2());
        stmt.bindLong(7, entity.getMaxZoneValue3());
        stmt.bindLong(8, entity.getMaxZoneValue4());
        stmt.bindLong(9, entity.getMaxZoneValue5());
        stmt.bindLong(10, entity.getReserveMaxValue());
        stmt.bindLong(11, entity.getReserveValue());
        stmt.bindLong(12, entity.getReserveZoneValue1());
        stmt.bindLong(13, entity.getReserveZoneValue2());
        stmt.bindLong(14, entity.getReserveZoneValue3());
        stmt.bindLong(15, entity.getReserveZoneValue4());
        stmt.bindLong(16, entity.getReserveZoneValue5());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, HeartZoneEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUserId());
        stmt.bindLong(3, entity.getSelect());
        stmt.bindLong(4, entity.getMaxValue());
        stmt.bindLong(5, entity.getMaxZoneValue1());
        stmt.bindLong(6, entity.getMaxZoneValue2());
        stmt.bindLong(7, entity.getMaxZoneValue3());
        stmt.bindLong(8, entity.getMaxZoneValue4());
        stmt.bindLong(9, entity.getMaxZoneValue5());
        stmt.bindLong(10, entity.getReserveMaxValue());
        stmt.bindLong(11, entity.getReserveValue());
        stmt.bindLong(12, entity.getReserveZoneValue1());
        stmt.bindLong(13, entity.getReserveZoneValue2());
        stmt.bindLong(14, entity.getReserveZoneValue3());
        stmt.bindLong(15, entity.getReserveZoneValue4());
        stmt.bindLong(16, entity.getReserveZoneValue5());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public HeartZoneEntity readEntity(Cursor cursor, int offset) {
        HeartZoneEntity entity = new HeartZoneEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // userId
            cursor.getInt(offset + 2), // select
            cursor.getInt(offset + 3), // maxValue
            cursor.getInt(offset + 4), // maxZoneValue1
            cursor.getInt(offset + 5), // maxZoneValue2
            cursor.getInt(offset + 6), // maxZoneValue3
            cursor.getInt(offset + 7), // maxZoneValue4
            cursor.getInt(offset + 8), // maxZoneValue5
            cursor.getInt(offset + 9), // reserveMaxValue
            cursor.getInt(offset + 10), // reserveValue
            cursor.getInt(offset + 11), // reserveZoneValue1
            cursor.getInt(offset + 12), // reserveZoneValue2
            cursor.getInt(offset + 13), // reserveZoneValue3
            cursor.getInt(offset + 14), // reserveZoneValue4
            cursor.getInt(offset + 15) // reserveZoneValue5
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, HeartZoneEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.getLong(offset + 1));
        entity.setSelect(cursor.getInt(offset + 2));
        entity.setMaxValue(cursor.getInt(offset + 3));
        entity.setMaxZoneValue1(cursor.getInt(offset + 4));
        entity.setMaxZoneValue2(cursor.getInt(offset + 5));
        entity.setMaxZoneValue3(cursor.getInt(offset + 6));
        entity.setMaxZoneValue4(cursor.getInt(offset + 7));
        entity.setMaxZoneValue5(cursor.getInt(offset + 8));
        entity.setReserveMaxValue(cursor.getInt(offset + 9));
        entity.setReserveValue(cursor.getInt(offset + 10));
        entity.setReserveZoneValue1(cursor.getInt(offset + 11));
        entity.setReserveZoneValue2(cursor.getInt(offset + 12));
        entity.setReserveZoneValue3(cursor.getInt(offset + 13));
        entity.setReserveZoneValue4(cursor.getInt(offset + 14));
        entity.setReserveZoneValue5(cursor.getInt(offset + 15));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(HeartZoneEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(HeartZoneEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(HeartZoneEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}