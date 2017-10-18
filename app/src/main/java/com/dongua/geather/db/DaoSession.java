package com.dongua.geather.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.dongua.geather.bean.state.Region;
import com.dongua.geather.bean.state.City;
import com.dongua.geather.bean.state.State;
import com.dongua.geather.bean.weather.Suggestion;
import com.dongua.geather.bean.weather.Future;
import com.dongua.geather.bean.weather.Weather;
import com.dongua.geather.bean.weather.HourlyWeather;
import com.dongua.geather.bean.weather.AirQuality;

import com.dongua.geather.db.RegionDao;
import com.dongua.geather.db.CityDao;
import com.dongua.geather.db.StateDao;
import com.dongua.geather.db.SuggestionDao;
import com.dongua.geather.db.FutureDao;
import com.dongua.geather.db.WeatherDao;
import com.dongua.geather.db.HourlyWeatherDao;
import com.dongua.geather.db.AirQualityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig regionDaoConfig;
    private final DaoConfig cityDaoConfig;
    private final DaoConfig stateDaoConfig;
    private final DaoConfig suggestionDaoConfig;
    private final DaoConfig futureDaoConfig;
    private final DaoConfig weatherDaoConfig;
    private final DaoConfig hourlyWeatherDaoConfig;
    private final DaoConfig airQualityDaoConfig;

    private final RegionDao regionDao;
    private final CityDao cityDao;
    private final StateDao stateDao;
    private final SuggestionDao suggestionDao;
    private final FutureDao futureDao;
    private final WeatherDao weatherDao;
    private final HourlyWeatherDao hourlyWeatherDao;
    private final AirQualityDao airQualityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        regionDaoConfig = daoConfigMap.get(RegionDao.class).clone();
        regionDaoConfig.initIdentityScope(type);

        cityDaoConfig = daoConfigMap.get(CityDao.class).clone();
        cityDaoConfig.initIdentityScope(type);

        stateDaoConfig = daoConfigMap.get(StateDao.class).clone();
        stateDaoConfig.initIdentityScope(type);

        suggestionDaoConfig = daoConfigMap.get(SuggestionDao.class).clone();
        suggestionDaoConfig.initIdentityScope(type);

        futureDaoConfig = daoConfigMap.get(FutureDao.class).clone();
        futureDaoConfig.initIdentityScope(type);

        weatherDaoConfig = daoConfigMap.get(WeatherDao.class).clone();
        weatherDaoConfig.initIdentityScope(type);

        hourlyWeatherDaoConfig = daoConfigMap.get(HourlyWeatherDao.class).clone();
        hourlyWeatherDaoConfig.initIdentityScope(type);

        airQualityDaoConfig = daoConfigMap.get(AirQualityDao.class).clone();
        airQualityDaoConfig.initIdentityScope(type);

        regionDao = new RegionDao(regionDaoConfig, this);
        cityDao = new CityDao(cityDaoConfig, this);
        stateDao = new StateDao(stateDaoConfig, this);
        suggestionDao = new SuggestionDao(suggestionDaoConfig, this);
        futureDao = new FutureDao(futureDaoConfig, this);
        weatherDao = new WeatherDao(weatherDaoConfig, this);
        hourlyWeatherDao = new HourlyWeatherDao(hourlyWeatherDaoConfig, this);
        airQualityDao = new AirQualityDao(airQualityDaoConfig, this);

        registerDao(Region.class, regionDao);
        registerDao(City.class, cityDao);
        registerDao(State.class, stateDao);
        registerDao(Suggestion.class, suggestionDao);
        registerDao(Future.class, futureDao);
        registerDao(Weather.class, weatherDao);
        registerDao(HourlyWeather.class, hourlyWeatherDao);
        registerDao(AirQuality.class, airQualityDao);
    }
    
    public void clear() {
        regionDaoConfig.clearIdentityScope();
        cityDaoConfig.clearIdentityScope();
        stateDaoConfig.clearIdentityScope();
        suggestionDaoConfig.clearIdentityScope();
        futureDaoConfig.clearIdentityScope();
        weatherDaoConfig.clearIdentityScope();
        hourlyWeatherDaoConfig.clearIdentityScope();
        airQualityDaoConfig.clearIdentityScope();
    }

    public RegionDao getRegionDao() {
        return regionDao;
    }

    public CityDao getCityDao() {
        return cityDao;
    }

    public StateDao getStateDao() {
        return stateDao;
    }

    public SuggestionDao getSuggestionDao() {
        return suggestionDao;
    }

    public FutureDao getFutureDao() {
        return futureDao;
    }

    public WeatherDao getWeatherDao() {
        return weatherDao;
    }

    public HourlyWeatherDao getHourlyWeatherDao() {
        return hourlyWeatherDao;
    }

    public AirQualityDao getAirQualityDao() {
        return airQualityDao;
    }

}
