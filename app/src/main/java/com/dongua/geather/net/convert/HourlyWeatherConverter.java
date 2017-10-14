package com.dongua.geather.net.convert;

import com.dongua.geather.App;
import com.dongua.geather.bean.weather.Future;
import com.dongua.geather.bean.weather.HourlyWeather;
import com.dongua.geather.bean.weather.Suggestion;
import com.dongua.geather.bean.weather.Weather;
import com.dongua.geather.db.WeatherDao;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import static com.dongua.geather.utils.Utils.logger;

/**
 * Created by dongua on 17-8-10.
 */

public class HourlyWeatherConverter implements Converter<ResponseBody, HourlyWeather> {

    private static HourlyWeatherConverter INSTANCE = new HourlyWeatherConverter();

    public static HourlyWeatherConverter create() {
        return INSTANCE;
    }

    @Override
    public HourlyWeather convert(ResponseBody responseBody) throws IOException {





        return new HourlyWeather("","","","");
    }


}
