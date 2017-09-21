package com.dongua.geather.net.convert;

import android.support.annotation.Nullable;

import com.dongua.geather.bean.weather.Weather;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * Created by dongua on 17-8-10.
 */

public class WeatherConverterFactory extends Converter.Factory {

    private static WeatherConverterFactory INSTANCE = new WeatherConverterFactory();

    public static WeatherConverterFactory create() {
        return INSTANCE;
    }


    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == Weather.class) {
            return WeatherConverter.create();
        }
        return null;
    }


}
