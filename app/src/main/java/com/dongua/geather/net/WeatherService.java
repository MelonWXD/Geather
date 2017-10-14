package com.dongua.geather.net;


import com.dongua.geather.bean.weather.Future;
import com.dongua.geather.bean.weather.Weather;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dongua on 17-8-6.
 */

public interface WeatherService {
//    @GET("weather/now")
//    Call<Weather> getWeatherInfo(@Query("cityid") String cityID);
//
//    @GET("weather/now")
//    Observable<Weather> getWeatherInfoByRx(@Query("cityid") String cityID);
//
//    //rxjava
//    @GET("weather/ip")
//    Call<ResponseBody> getCityIP();

    @GET("weather/ip")
    Observable<Response<ResponseBody>> getCurCityIP();

    @GET("weather/cityid")
    Observable<ResponseBody> getCityID(@Query("location") String ipOrName);

    @GET("weather/now")
    Observable<ResponseBody> getCityWeather(@Query("cityid")String cityID);

    @GET("weather/future24h")
    Observable<Future> getCity24H(@Query("cityid")String cityID);

    @GET("weather/future24h")
    Observable<ResponseBody> getCity24(@Query("cityid")String cityID);
}