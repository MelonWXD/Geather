package com.dongua.geather.net;

import com.dongua.geather.bean.weather.Future;
import com.dongua.geather.bean.weather.Weather;
import com.dongua.geather.net.convert.CustomConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.dongua.geather.utils.Constant.WEATHER_BASE_URL;

/**
 * Created by dongua on 17-8-6.
 */

public class NetApi {
    private WeatherService mWeatherService;
    private static NetApi mNetClient;

    private NetApi() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        mWeatherService = new Retrofit.Builder()
                .baseUrl(WEATHER_BASE_URL)
                .addConverterFactory(CustomConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WeatherService.class);
    }

    public static NetApi getInstance() {
        if (mNetClient == null) {
            synchronized (NetApi.class) {
                if (mNetClient == null) {
                    mNetClient = new NetApi();
                }
            }
        }
        return mNetClient;
    }


//
//    public Observable<Weather> getWeatherInfoByRx(String cityID) {
//        return mWeatherService.getWeatherInfoByRx(cityID);
//    }
//
//
//    public Call<Weather> getWeatherInfo(String cityID) {
//        return mWeatherService.getWeatherInfo(cityID);
//    }

//    public Call<ResponseBody> getCityID(String name) {
//        return mWeatherService.getCityID(name);
//    }

//    public Call<ResponseBody> getCityIP() {
//        return mWeatherService.getCityIP();
//    }


    public Observable<Response<ResponseBody>> getCurCityIP() {
        return mWeatherService.getCurCityIP();
    }


    public Observable<ResponseBody> getCityID(String ip) {
        return mWeatherService.getCityID(ip);
    }


    public Observable<ResponseBody> getCityWeather(String cityID) {
        return mWeatherService.getCityWeather(cityID);
    }


    public Observable<ResponseBody> getCity24H(String cityID) {
        return mWeatherService.getCity24(cityID);
    }

    public Observable<Response<ResponseBody>> getWeatherUpdate(String cityID) {
        return mWeatherService.getWeatherUpdateTime(cityID);
    }


}
