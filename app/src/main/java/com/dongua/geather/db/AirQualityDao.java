package com.dongua.geather.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.dongua.geather.bean.weather.AirQuality;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "AIR_QUALITY".
*/
public class AirQualityDao extends AbstractDao<AirQuality, Long> {

    public static final String TABLENAME = "AIR_QUALITY";

    /**
     * Properties of entity AirQuality.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property City_id = new Property(1, String.class, "city_id", false, "CITY_ID");
        public final static Property Aqi = new Property(2, String.class, "aqi", false, "AQI");
        public final static Property Pm25 = new Property(3, String.class, "pm25", false, "PM25");
        public final static Property Pm10 = new Property(4, String.class, "pm10", false, "PM10");
        public final static Property So2 = new Property(5, String.class, "so2", false, "SO2");
        public final static Property No2 = new Property(6, String.class, "no2", false, "NO2");
        public final static Property Co = new Property(7, String.class, "co", false, "CO");
        public final static Property O3 = new Property(8, String.class, "o3", false, "O3");
        public final static Property Last_update = new Property(9, String.class, "last_update", false, "LAST_UPDATE");
        public final static Property Quality = new Property(10, String.class, "quality", false, "QUALITY");
    }


    public AirQualityDao(DaoConfig config) {
        super(config);
    }
    
    public AirQualityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"AIR_QUALITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CITY_ID\" TEXT," + // 1: city_id
                "\"AQI\" TEXT," + // 2: aqi
                "\"PM25\" TEXT," + // 3: pm25
                "\"PM10\" TEXT," + // 4: pm10
                "\"SO2\" TEXT," + // 5: so2
                "\"NO2\" TEXT," + // 6: no2
                "\"CO\" TEXT," + // 7: co
                "\"O3\" TEXT," + // 8: o3
                "\"LAST_UPDATE\" TEXT," + // 9: last_update
                "\"QUALITY\" TEXT);"); // 10: quality
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"AIR_QUALITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AirQuality entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String city_id = entity.getCity_id();
        if (city_id != null) {
            stmt.bindString(2, city_id);
        }
 
        String aqi = entity.getAqi();
        if (aqi != null) {
            stmt.bindString(3, aqi);
        }
 
        String pm25 = entity.getPm25();
        if (pm25 != null) {
            stmt.bindString(4, pm25);
        }
 
        String pm10 = entity.getPm10();
        if (pm10 != null) {
            stmt.bindString(5, pm10);
        }
 
        String so2 = entity.getSo2();
        if (so2 != null) {
            stmt.bindString(6, so2);
        }
 
        String no2 = entity.getNo2();
        if (no2 != null) {
            stmt.bindString(7, no2);
        }
 
        String co = entity.getCo();
        if (co != null) {
            stmt.bindString(8, co);
        }
 
        String o3 = entity.getO3();
        if (o3 != null) {
            stmt.bindString(9, o3);
        }
 
        String last_update = entity.getLast_update();
        if (last_update != null) {
            stmt.bindString(10, last_update);
        }
 
        String quality = entity.getQuality();
        if (quality != null) {
            stmt.bindString(11, quality);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AirQuality entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String city_id = entity.getCity_id();
        if (city_id != null) {
            stmt.bindString(2, city_id);
        }
 
        String aqi = entity.getAqi();
        if (aqi != null) {
            stmt.bindString(3, aqi);
        }
 
        String pm25 = entity.getPm25();
        if (pm25 != null) {
            stmt.bindString(4, pm25);
        }
 
        String pm10 = entity.getPm10();
        if (pm10 != null) {
            stmt.bindString(5, pm10);
        }
 
        String so2 = entity.getSo2();
        if (so2 != null) {
            stmt.bindString(6, so2);
        }
 
        String no2 = entity.getNo2();
        if (no2 != null) {
            stmt.bindString(7, no2);
        }
 
        String co = entity.getCo();
        if (co != null) {
            stmt.bindString(8, co);
        }
 
        String o3 = entity.getO3();
        if (o3 != null) {
            stmt.bindString(9, o3);
        }
 
        String last_update = entity.getLast_update();
        if (last_update != null) {
            stmt.bindString(10, last_update);
        }
 
        String quality = entity.getQuality();
        if (quality != null) {
            stmt.bindString(11, quality);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AirQuality readEntity(Cursor cursor, int offset) {
        AirQuality entity = new AirQuality( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // city_id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // aqi
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // pm25
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // pm10
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // so2
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // no2
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // co
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // o3
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // last_update
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // quality
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AirQuality entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCity_id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAqi(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPm25(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPm10(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSo2(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setNo2(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCo(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setO3(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setLast_update(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setQuality(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AirQuality entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AirQuality entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AirQuality entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}