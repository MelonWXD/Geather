package com.dongua.geather.ui.listener;

import com.dongua.geather.bean.weather.HourlyWeather;
import com.dongua.geather.bean.weather.Weather;

import java.util.List;

/**
 * Created by dongua on 17-8-3.
 */

public interface OnNetworkListener{

//    void successed(Object bean);
    void successed(Weather bean);
    void successed(List<HourlyWeather> bean);
    void failed();
}
