package com.meilancycling.mema.db.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.meilancycling.mema.db.FileUploadEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FILE_UPLOAD_ENTITY".
*/
public class FileUploadEntityDao extends AbstractDao<FileUploadEntity, Long> {

    public static final String TABLENAME = "FILE_UPLOAD_ENTITY";

    /**
     * Properties of entity FileUploadEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, long.class, "userId", false, "USER_ID");
        public final static Property SportsType = new Property(2, int.class, "sportsType", false, "SPORTS_TYPE");
        public final static Property FileName = new Property(3, String.class, "fileName", false, "FILE_NAME");
        public final static Property ProductNo = new Property(4, String.class, "productNo", false, "PRODUCT_NO");
        public final static Property TimeZone = new Property(5, int.class, "timeZone", false, "TIME_ZONE");
        public final static Property FilePath = new Property(6, String.class, "filePath", false, "FILE_PATH");
    }


    public FileUploadEntityDao(DaoConfig config) {
        super(config);
    }
    
    public FileUploadEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FILE_UPLOAD_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USER_ID\" INTEGER NOT NULL ," + // 1: userId
                "\"SPORTS_TYPE\" INTEGER NOT NULL ," + // 2: sportsType
                "\"FILE_NAME\" TEXT," + // 3: fileName
                "\"PRODUCT_NO\" TEXT," + // 4: productNo
                "\"TIME_ZONE\" INTEGER NOT NULL ," + // 5: timeZone
                "\"FILE_PATH\" TEXT);"); // 6: filePath
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FILE_UPLOAD_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FileUploadEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUserId());
        stmt.bindLong(3, entity.getSportsType());
 
        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(4, fileName);
        }
 
        String productNo = entity.getProductNo();
        if (productNo != null) {
            stmt.bindString(5, productNo);
        }
        stmt.bindLong(6, entity.getTimeZone());
 
        String filePath = entity.getFilePath();
        if (filePath != null) {
            stmt.bindString(7, filePath);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FileUploadEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUserId());
        stmt.bindLong(3, entity.getSportsType());
 
        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(4, fileName);
        }
 
        String productNo = entity.getProductNo();
        if (productNo != null) {
            stmt.bindString(5, productNo);
        }
        stmt.bindLong(6, entity.getTimeZone());
 
        String filePath = entity.getFilePath();
        if (filePath != null) {
            stmt.bindString(7, filePath);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public FileUploadEntity readEntity(Cursor cursor, int offset) {
        FileUploadEntity entity = new FileUploadEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // userId
            cursor.getInt(offset + 2), // sportsType
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // fileName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // productNo
            cursor.getInt(offset + 5), // timeZone
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // filePath
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FileUploadEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.getLong(offset + 1));
        entity.setSportsType(cursor.getInt(offset + 2));
        entity.setFileName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setProductNo(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTimeZone(cursor.getInt(offset + 5));
        entity.setFilePath(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(FileUploadEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(FileUploadEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(FileUploadEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
