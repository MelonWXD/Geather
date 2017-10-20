package com.dongua.geather.ui.presenter;

import com.dongua.geather.App;
import com.dongua.geather.AppManager;
import com.dongua.geather.bean.weather.HourlyWeather;
import com.dongua.geather.bean.weather.Weather;
import com.dongua.geather.ui.base.BasePresenter;
import com.dongua.geather.ui.listener.OnNetworkListener;
import com.dongua.geather.ui.model.LocationModel;
import com.dongua.geather.ui.model.WeatherModel;
import com.dongua.geather.ui.view.WeatherView;
import com.dongua.geather.utils.LogUtil;
import com.dongua.geather.utils.SharedPreferenceUtil;

import java.util.List;

import static com.dongua.geather.utils.Constant.MSG_HOURLY_WEATHER_DATA;
import static com.dongua.geather.utils.Constant.MSG_WEATHER_DATA;
import static com.dongua.geather.utils.Constant.SP_LOCDB;

/**
 * Created by dongua on 17-7-30.
 */

public class WeatherPresenter implements BasePresenter<WeatherView>, OnNetworkListener {

    WeatherModel mWeatherModel;
    WeatherView mWeatherView;
    LocationModel mLocModel;

    public WeatherPresenter(WeatherView mWeatherView) {
        this.mWeatherView = mWeatherView;
        mWeatherView.setPresenter(this);
        mWeatherModel = new WeatherModel();
        mWeatherModel.setNetworkListener(this);

        mLocModel = new LocationModel();
    }

    public void showWeatherInfo(String cityID) {
        if (cityID == null) {
            mWeatherModel.getWeatherByIp();
//        mWeatherModel.getWeatherJson(ipOrName);
        } else {
            mWeatherModel.getCityWeatherByID(cityID);
            mWeatherModel.getHourlyByID(cityID);
        }
    }


    @Override
    public WeatherView getView() {
        return mWeatherView;
    }

//
//    @Override
//    public void successed(Object bean) {
////        if(bean.getClass()==Weather.class)
////        Weather weather = (Weather)bean;
//        LogUtil.I("successed："+bean.toString());
//        mWeatherView.update(bean);
//    }

    @Override
    public void successed(Weather weather) {
        mWeatherView.update(weather, MSG_WEATHER_DATA);
    }

    @Override
    public void successed(List<HourlyWeather> hourlyWeathers) {
        mWeatherView.update(hourlyWeathers, MSG_HOURLY_WEATHER_DATA);

    }

    @Override
    public void failed() {

    }

    public void makeLocDB() {
        new locDBThread().start();
    }

    public void checkUpdate() {
    }


    class locDBThread extends Thread {
        @Override
        public void run() {
            if (mLocModel.saveLocDB()) {
                SharedPreferenceUtil.putSharedPreferences(AppManager.getInstance().getAppContext(), SP_LOCDB, true);
                LogUtil.I("loc db save successful");
            } else {
                LogUtil.I("loc db save fail");
            }
        }
    }
}
