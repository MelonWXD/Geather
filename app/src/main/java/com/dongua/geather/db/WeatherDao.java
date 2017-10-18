package com.dongua.geather.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.dongua.geather.bean.weather.Weather;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WEATHER".
*/
public class WeatherDao extends AbstractDao<Weather, Long> {

    public static final String TABLENAME = "WEATHER";

    /**
     * Properties of entity Weather.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property City_name = new Property(1, String.class, "city_name", false, "CITY_NAME");
        public final static Property City_id = new Property(2, String.class, "city_id", false, "CITY_ID");
        public final static Property Update_date = new Property(3, String.class, "update_date", false, "UPDATE_DATE");
        public final static Property Date = new Property(4, String.class, "date", false, "DATE");
        public final static Property Sunrise = new Property(5, String.class, "sunrise", false, "SUNRISE");
        public final static Property Sunset = new Property(6, String.class, "sunset", false, "SUNSET");
        public final static Property Code = new Property(7, String.class, "code", false, "CODE");
        public final static Property Text_now = new Property(8, String.class, "text_now", false, "TEXT_NOW");
        public final static Property Temperature = new Property(9, String.class, "temperature", false, "TEMPERATURE");
        public final static Property Wind_dir = new Property(10, String.class, "wind_dir", false, "WIND_DIR");
        public final static Property Wind_speed = new Property(11, String.class, "wind_speed", false, "WIND_SPEED");
        public final static Property Visibility = new Property(12, String.class, "visibility", false, "VISIBILITY");
        public final static Property Pressure = new Property(13, String.class, "pressure", false, "PRESSURE");
    }

    private DaoSession daoSession;


    public WeatherDao(DaoConfig config) {
        super(config);
    }
    
    public WeatherDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WEATHER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CITY_NAME\" TEXT UNIQUE ," + // 1: city_name
                "\"CITY_ID\" TEXT UNIQUE ," + // 2: city_id
                "\"UPDATE_DATE\" TEXT," + // 3: update_date
                "\"DATE\" TEXT," + // 4: date
                "\"SUNRISE\" TEXT," + // 5: sunrise
                "\"SUNSET\" TEXT," + // 6: sunset
                "\"CODE\" TEXT," + // 7: code
                "\"TEXT_NOW\" TEXT," + // 8: text_now
                "\"TEMPERATURE\" TEXT," + // 9: temperature
                "\"WIND_DIR\" TEXT," + // 10: wind_dir
                "\"WIND_SPEED\" TEXT," + // 11: wind_speed
                "\"VISIBILITY\" TEXT," + // 12: visibility
                "\"PRESSURE\" TEXT);"); // 13: pressure
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WEATHER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Weather entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String city_name = entity.getCity_name();
        if (city_name != null) {
            stmt.bindString(2, city_name);
        }
 
        String city_id = entity.getCity_id();
        if (city_id != null) {
            stmt.bindString(3, city_id);
        }
 
        String update_date = entity.getUpdate_date();
        if (update_date != null) {
            stmt.bindString(4, update_date);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(5, date);
        }
 
        String sunrise = entity.getSunrise();
        if (sunrise != null) {
            stmt.bindString(6, sunrise);
        }
 
        String sunset = entity.getSunset();
        if (sunset != null) {
            stmt.bindString(7, sunset);
        }
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(8, code);
        }
 
        String text_now = entity.getText_now();
        if (text_now != null) {
            stmt.bindString(9, text_now);
        }
 
        String temperature = entity.getTemperature();
        if (temperature != null) {
            stmt.bindString(10, temperature);
        }
 
        String wind_dir = entity.getWind_dir();
        if (wind_dir != null) {
            stmt.bindString(11, wind_dir);
        }
 
        String wind_speed = entity.getWind_speed();
        if (wind_speed != null) {
            stmt.bindString(12, wind_speed);
        }
 
        String visibility = entity.getVisibility();
        if (visibility != null) {
            stmt.bindString(13, visibility);
        }
 
        String pressure = entity.getPressure();
        if (pressure != null) {
            stmt.bindString(14, pressure);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Weather entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String city_name = entity.getCity_name();
        if (city_name != null) {
            stmt.bindString(2, city_name);
        }
 
        String city_id = entity.getCity_id();
        if (city_id != null) {
            stmt.bindString(3, city_id);
        }
 
        String update_date = entity.getUpdate_date();
        if (update_date != null) {
            stmt.bindString(4, update_date);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(5, date);
        }
 
        String sunrise = entity.getSunrise();
        if (sunrise != null) {
            stmt.bindString(6, sunrise);
        }
 
        String sunset = entity.getSunset();
        if (sunset != null) {
            stmt.bindString(7, sunset);
        }
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(8, code);
        }
 
        String text_now = entity.getText_now();
        if (text_now != null) {
            stmt.bindString(9, text_now);
        }
 
        String temperature = entity.getTemperature();
        if (temperature != null) {
            stmt.bindString(10, temperature);
        }
 
        String wind_dir = entity.getWind_dir();
        if (wind_dir != null) {
            stmt.bindString(11, wind_dir);
        }
 
        String wind_speed = entity.getWind_speed();
        if (wind_speed != null) {
            stmt.bindString(12, wind_speed);
        }
 
        String visibility = entity.getVisibility();
        if (visibility != null) {
            stmt.bindString(13, visibility);
        }
 
        String pressure = entity.getPressure();
        if (pressure != null) {
            stmt.bindString(14, pressure);
        }
    }

    @Override
    protected final void attachEntity(Weather entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Weather readEntity(Cursor cursor, int offset) {
        Weather entity = new Weather( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // city_name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // city_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // update_date
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // date
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // sunrise
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // sunset
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // code
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // text_now
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // temperature
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // wind_dir
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // wind_speed
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // visibility
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13) // pressure
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Weather entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCity_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCity_id(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUpdate_date(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDate(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSunrise(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSunset(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCode(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setText_now(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setTemperature(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setWind_dir(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setWind_speed(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setVisibility(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setPressure(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Weather entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Weather entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Weather entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
