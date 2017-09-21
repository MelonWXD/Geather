package com.dongua.geather.ui.view;

import com.dongua.geather.ui.base.BaseView;
import com.dongua.geather.ui.presenter.WeatherPresenter;

/**
 * Created by dongua on 17-7-30.
 */

public interface WeatherView extends BaseView<WeatherPresenter> {

    void setCity_name(String city_name);

    void setDate(String date);

    void setText_dat(String text_dat);

    void setText_night(String text_night);

    void setHigh(String high);

    void setLow(String low);

    void setWind_dir(String wind_dir);

    void setWind_speed(String wind_speed);

}
