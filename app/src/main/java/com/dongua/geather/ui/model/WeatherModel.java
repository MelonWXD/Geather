package com.dongua.geather.ui.model;

import com.dongua.geather.bean.weather.Weather;
import com.dongua.geather.net.NetApi;
import com.dongua.geather.ui.listener.OnNetworkListener;
import com.dongua.geather.utils.LogUtil;
import com.dongua.geather.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dongua.geather.utils.Utils.logger;

/**
 * Created by dongua on 17-7-30.
 */

public class WeatherModel {


    OnNetworkListener mListener;
    NetApi mNetClient = NetApi.getInstance();


    public void setNetworkListener(OnNetworkListener listener) {
        mListener = listener;
    }

    //http get
    public Weather getWeather(String cityID) {

        return null;
    }

    public void getCityID(String ip) {

        Call<ResponseBody> call = mNetClient.getCityID(ip);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String resp = null;
                try {
                    resp = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JsonObject jo = new JsonParser().parse(resp).getAsJsonObject();
                JsonArray ja = jo.getAsJsonArray("results");
                String id = ja.get(0).getAsJsonObject().get("id").getAsString();
                logger.info("id=" + id);
                getWeatherJson(id);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                logger.info(t.getMessage());
            }
        });
    }

    public void getIPJson() {

        Call<ResponseBody> call = mNetClient.getCityIP();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String resp = null;
                try {
                    resp = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.info(resp);
                JsonObject jo = Utils.string2Json(resp);
                String ip = jo.get("data").getAsString();
                logger.info("ip=" + ip);
                getCityID(ip);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                logger.info(t.getMessage());
            }
        });

    }

    public void getWeatherJson(String ipOrName) {

//        Call<Weather> call = mNetClient.getWeatherInfo(ipOrName);
//        call.enqueue(new Callback<Weather>() {
//            @Override
//            public void onResponse(Call<Weather> call, Response<Weather> response) {
//                Weather resp = response.body();
//                logger.info(resp.toString());
//            }
//            @Override
//            public void onFailure(Call<Weather> call, Throwable t) {
//                logger.info(t.getMessage());
//            }
//        });

        mNetClient.getWeatherInfoByRx(ipOrName)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.I(d.toString());
                    }

                    @Override
                    public void onNext(Weather value) {
                        Weather resp = value;
                        mListener.successed(resp);
                        LogUtil.I(resp.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.I(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.I("RXJAVA结束");
                    }
                });


    }


}
