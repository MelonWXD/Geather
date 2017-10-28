package com.dongua.geather.ui.presenter;

import com.dongua.geather.bean.weather.HourlyWeather;
import com.dongua.geather.bean.weather.Weather;
import com.dongua.geather.ui.listener.OnNetworkListener;
import com.dongua.geather.ui.model.WeatherModel;
import com.dongua.geather.ui.view.WeatherView;

import java.util.List;

import static com.dongua.geather.utils.Constant.MSG_HOURLY_WEATHER_DATA;
import static com.dongua.geather.utils.Constant.MSG_WEATHER_DATA;

/**
 * Created by dongua on 17-7-30.
 */

public class WeatherPresenter implements BasePresenter<WeatherView>, OnNetworkListener {

    WeatherModel mWeatherModel;
    WeatherView mWeatherView;


    public WeatherPresenter(WeatherView mWeatherView) {
        this.mWeatherView = mWeatherView;
        mWeatherView.setPresenter(this);
        mWeatherModel = new WeatherModel();
        mWeatherModel.setNetworkListener(this);

    }

    public void showWeatherInfo(String cityID) {
        if (cityID == null) {
            mWeatherModel.getWeatherByIp();
//        mWeatherModel.getWeatherJson(ipOrName);
        } else {
            mWeatherModel.getCityWeatherByID(cityID,null);
            mWeatherModel.getHourlyByID(cityID);
        }
    }


    public void checkUpdate(String cityID, String lastUpdateTime) {
        mWeatherModel.checkUpdate(cityID, lastUpdateTime);
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
        if (weather != null) {
            mWeatherView.update(weather, MSG_WEATHER_DATA);
        }
    }

    @Override
    public void successed(List<HourlyWeather> hourlyWeathers) {
        mWeatherView.update(hourlyWeathers, MSG_HOURLY_WEATHER_DATA);

    }

    @Override
    public void failed() {

    }


}
