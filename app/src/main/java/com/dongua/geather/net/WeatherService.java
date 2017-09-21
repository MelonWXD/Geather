package com.dongua.geather.net;


import com.dongua.geather.bean.weather.Weather;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dongua on 17-8-6.
 */

public interface WeatherService {
    @GET("weather/now")
    Call<Weather> getWeatherInfo(@Query("cityid") String cityID);

    @GET("weather/now")
    Observable<Weather> getWeatherInfoByRx(@Query("cityid") String cityID);

    @GET("weather/cityid")
    Call<ResponseBody> getCityID(@Query("location") String cityName);

    //rxjava
    @GET("weather/ip")
    Call<ResponseBody> getCityIP();
}