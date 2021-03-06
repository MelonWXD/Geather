package com.dongua.geather.net.convert;

import com.dongua.geather.App;
import com.dongua.geather.bean.weather.Future;
import com.dongua.geather.bean.weather.Suggestion;
import com.dongua.geather.bean.weather.Weather;
import com.dongua.geather.db.WeatherDao;
import com.dongua.geather.utils.LogUtil;
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
import retrofit2.Response;

import static com.dongua.geather.utils.Utils.logger;

/**
 * Created by dongua on 17-8-10.
 */

public class WeatherConverter implements Converter<ResponseBody, Weather> {

    private static WeatherConverter INSTANCE = new WeatherConverter();

    public static WeatherConverter create() {
        return INSTANCE;
    }

    @Override
    public Weather convert(ResponseBody responseBody) throws IOException {
            return null;
    }


}
