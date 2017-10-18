package com.dongua.geather.bean.weather;

import com.dongua.geather.db.DaoSession;
import com.dongua.geather.db.FutureDao;
import com.dongua.geather.db.SuggestionDao;
import com.dongua.geather.db.WeatherDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import com.dongua.geather.db.DaoSession;
import com.dongua.geather.db.SuggestionDao;
import com.dongua.geather.db.FutureDao;
import com.dongua.geather.db.WeatherDao;
import com.dongua.geather.db.HourlyWeatherDao;


@Entity
public class Weather {


    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String city_name;
    @Unique
    private String city_id;
    private String update_date;
    private String date;
    private String sunrise;
    private String sunset;
    private String code;


    //now
    private String text_now;
    private String temperature;
    private String wind_dir;
    private String wind_speed;
    private String visibility;
    private String pressure;

    @Override
    public String toString() {
        return "Weather{" +
                "city_name='" + city_name + '\'' +
                ", city_id='" + city_id + '\'' +
                ", date='" + date + '\'' +
                ", temperature='" + temperature + '\'' +
                '}';
    }

    public void setFuture(List<Future> future) {
        this.future = future;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    @ToMany(referencedJoinProperty = "city_id")
    private List<HourlyWeather> hourlyWeathers;

    @ToMany(referencedJoinProperty = "city_id")
    private List<Future> future;
    @ToMany(referencedJoinProperty = "city_id")
    private List<Suggestion> suggestions;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1275646288)
    private transient WeatherDao myDao;
    @Generated(hash = 1837347540)
    public Weather(Long id, String city_name, String city_id, String update_date, String date,
            String sunrise, String sunset, String code, String text_now, String temperature,
            String wind_dir, String wind_speed, String visibility, String pressure) {
        this.id = id;
        this.city_name = city_name;
        this.city_id = city_id;
        this.update_date = update_date;
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.code = code;
        this.text_now = text_now;
        this.temperature = temperature;
        this.wind_dir = wind_dir;
        this.wind_speed = wind_speed;
        this.visibility = visibility;
        this.pressure = pressure;
    }

    @Generated(hash = 556711069)
    public Weather() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCity_name() {
        return this.city_name;
    }
    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
    public String getCity_id() {
        return this.city_id;
    }
    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }
    public String getUpdate_date() {
        return this.update_date;
    }
    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getSunrise() {
        return this.sunrise;
    }
    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }
    public String getSunset() {
        return this.sunset;
    }
    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
    public String getText_now() {
        return this.text_now;
    }
    public void setText_now(String text_now) {
        this.text_now = text_now;
    }
    public String getTemperature() {
        return this.temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
    public String getWind_dir() {
        return this.wind_dir;
    }
    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }
    public String getWind_speed() {
        return this.wind_speed;
    }
    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }
    public String getVisibility() {
        return this.visibility;
    }
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    public String getPressure() {
        return this.pressure;
    }
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Keep
    public List<HourlyWeather> getHourlyWeathers() {
        if (hourlyWeathers == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            HourlyWeatherDao targetDao = daoSession.getHourlyWeatherDao();
            List<HourlyWeather> hourlyWeathersNew = targetDao._queryWeather_HourlyWeathers(city_id);
            synchronized (this) {
                if (hourlyWeathers == null) {
                    hourlyWeathers = hourlyWeathersNew;
                }
            }
        }
        return hourlyWeathers;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 129288623)
    public synchronized void resetHourlyWeathers() {
        hourlyWeathers = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Keep
    public List<Future> getFuture() {
        if (future == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FutureDao targetDao = daoSession.getFutureDao();
            List<Future> futureNew = targetDao._queryWeather_Future(city_id);
            synchronized (this) {
                if (future == null) {
                    future = futureNew;
                }
            }
        }
        return future;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 348398106)
    public synchronized void resetFuture() {
        future = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Keep
    public List<Suggestion> getSuggestions() {
        if (suggestions == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SuggestionDao targetDao = daoSession.getSuggestionDao();
            List<Suggestion> suggestionsNew = targetDao._queryWeather_Suggestions(city_id);
            synchronized (this) {
                if (suggestions == null) {
                    suggestions = suggestionsNew;
                }
            }
        }
        return suggestions;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1736818365)
    public synchronized void resetSuggestions() {
        suggestions = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1342418174)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getWeatherDao() : null;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
