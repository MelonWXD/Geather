package com.dongua.geather.db;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import com.dongua.geather.bean.weather.Future;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FUTURE".
*/
public class FutureDao extends AbstractDao<Future, Long> {

    public static final String TABLENAME = "FUTURE";

    /**
     * Properties of entity Future.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property City_id = new Property(1, String.class, "city_id", false, "CITY_ID");
        public final static Property Date = new Property(2, String.class, "date", false, "DATE");
        public final static Property Day = new Property(3, String.class, "day", false, "DAY");
        public final static Property High = new Property(4, String.class, "high", false, "HIGH");
        public final static Property Low = new Property(5, String.class, "low", false, "LOW");
        public final static Property Text = new Property(6, String.class, "text", false, "TEXT");
        public final static Property Wind = new Property(7, String.class, "wind", false, "WIND");
    }

    private Query<Future> weather_FutureQuery;

    public FutureDao(DaoConfig config) {
        super(config);
    }
    
    public FutureDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FUTURE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CITY_ID\" TEXT," + // 1: city_id
                "\"DATE\" TEXT," + // 2: date
                "\"DAY\" TEXT," + // 3: day
                "\"HIGH\" TEXT," + // 4: high
                "\"LOW\" TEXT," + // 5: low
                "\"TEXT\" TEXT," + // 6: text
                "\"WIND\" TEXT);"); // 7: wind
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FUTURE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Future entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String city_id = entity.getCity_id();
        if (city_id != null) {
            stmt.bindString(2, city_id);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(3, date);
        }
 
        String day = entity.getDay();
        if (day != null) {
            stmt.bindString(4, day);
        }
 
        String high = entity.getHigh();
        if (high != null) {
            stmt.bindString(5, high);
        }
 
        String low = entity.getLow();
        if (low != null) {
            stmt.bindString(6, low);
        }
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(7, text);
        }
 
        String wind = entity.getWind();
        if (wind != null) {
            stmt.bindString(8, wind);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Future entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String city_id = entity.getCity_id();
        if (city_id != null) {
            stmt.bindString(2, city_id);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(3, date);
        }
 
        String day = entity.getDay();
        if (day != null) {
            stmt.bindString(4, day);
        }
 
        String high = entity.getHigh();
        if (high != null) {
            stmt.bindString(5, high);
        }
 
        String low = entity.getLow();
        if (low != null) {
            stmt.bindString(6, low);
        }
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(7, text);
        }
 
        String wind = entity.getWind();
        if (wind != null) {
            stmt.bindString(8, wind);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Future readEntity(Cursor cursor, int offset) {
        Future entity = new Future( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // city_id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // date
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // day
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // high
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // low
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // text
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // wind
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Future entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCity_id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDate(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDay(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setHigh(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLow(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setText(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setWind(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Future entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Future entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Future entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "future" to-many relationship of Weather. */
    public List<Future> _queryWeather_Future(String city_id) {
        synchronized (this) {
            if (weather_FutureQuery == null) {
                QueryBuilder<Future> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.City_id.eq(null));
                weather_FutureQuery = queryBuilder.build();
            }
        }
        Query<Future> query = weather_FutureQuery.forCurrentThread();
        query.setParameter(0, city_id);
        return query.list();
    }

}
