package com.dongua.geather.ui.view;

import com.dongua.geather.ui.presenter.WeatherPresenter;

/**
 * Created by dongua on 17-7-30.
 */

public interface WeatherView extends BaseView<WeatherPresenter> {

    void update(Object bean, int msgHourlyWeatherData);
}
