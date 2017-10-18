package com.dongua.geather.ui.model;

import com.dongua.geather.App;
import com.dongua.geather.bean.weather.AirQuality;
import com.dongua.geather.bean.weather.Future;
import com.dongua.geather.bean.weather.HourlyWeather;
import com.dongua.geather.bean.weather.Suggestion;
import com.dongua.geather.bean.weather.Weather;
import com.dongua.geather.db.WeatherDao;
import com.dongua.geather.net.NetApi;
import com.dongua.geather.ui.listener.OnNetworkListener;
import com.dongua.geather.utils.LogUtil;
import com.dongua.geather.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
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

//        Call<ResponseBody> call = mNetClient.getCityID(ip);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                String resp = null;
//                try {
//                    resp = response.body().string();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                JsonObject jo = new JsonParser().parse(resp).getAsJsonObject();
//                JsonArray ja = jo.getAsJsonArray("results");
//                String id = ja.get(0).getAsJsonObject().get("id").getAsString();
//                logger.info("id=" + id);
//                getWeatherJson(id);
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                logger.info(t.getMessage());
//            }
//        });
    }

    public void getIPJson() {

//        Call<ResponseBody> call = mNetClient.getCityIP();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                String resp = null;
//                try {
//                    resp = response.body().string();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                logger.info(resp);
//                JsonObject jo = Utils.string2Json(resp);
//                String ip = jo.get("data").getAsString();
//                logger.info("ip=" + ip);
//                getCityID(ip);
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                logger.info(t.getMessage());
//            }
//        });

    }

    public void getWeatherJson(String ipOrName) {


//        mNetClient.getWeatherInfoByRx(ipOrName)
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<Weather>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        LogUtil.I(d.toString());
//                    }
//
//                    @Override
//                    public void onNext(Weather value) {
//                        Weather resp = value;
//                        mListener.successed(resp);
//                        LogUtil.I(resp.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.I(e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        LogUtil.I("RXJAVA结束");
//                    }
//                });


    }

    //    String ipAddr = null;
//    String cityID = null;
//    String updateTime = null;

    public void getWeatherByIp() {
        mNetClient.getCurCityIP()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .concatMap(new Function<Response<ResponseBody>, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(Response<ResponseBody> response) throws Exception {

                        JsonObject jo = Utils.string2Json(response.body().string());
                        String ipAddr = jo.get("data").getAsString();
                        return mNetClient.getCityID(ipAddr);
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        JsonObject jo = new JsonParser().parse(responseBody.string()).getAsJsonObject();
                        JsonArray ja = jo.getAsJsonArray("results");
                        String cityID = ja.get(0).getAsJsonObject().get("id").getAsString();
                        LogUtil.I("id=" + cityID);

                        getCityWeatherByID(cityID);
                        getHourlyByID(cityID);
                    }
                });

    }

    private void getCityWeatherByID(String cityID) {
        mNetClient.getCityWeather(cityID)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        mListener.successed(parseResponse2Weather(responseBody, cityID));
                    }
                });
    }


    //解析response 存数据库并返回
    private Weather parseResponse2Weather(ResponseBody responseBody, String cityID) {
        Weather weather = null;
        try {

            String content = responseBody.string();//string只能调用一次 完了输入流即关闭
//        LogUtil.I(content);
            JsonObject jsonObject = new JsonParser().parse(content).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("weather");

            JsonObject weatherJson = jsonArray.get(0).getAsJsonObject();
            String cityName = weatherJson.get("city_name").getAsString();
            String cityId = weatherJson.get("city_id").getAsString();
            String last_update = weatherJson.get("last_update").getAsString();

            JsonObject nowJson = weatherJson.get("now").getAsJsonObject();
            String now_text = nowJson.get("text").getAsString();
            String now_code = nowJson.get("code").getAsString();
            String temperature = nowJson.get("temperature").getAsString();
            String wind_dir = nowJson.get("wind_direction").getAsString();
            String wind_speed = nowJson.get("wind_speed").getAsString();
            String visibility = nowJson.get("visibility").getAsString();
            String pressure = nowJson.get("pressure").getAsString();


            JsonObject today = weatherJson.get("today").getAsJsonObject();
            String sunrise = today.get("sunrise").getAsString();
            String sunset = today.get("sunset").getAsString();

            //save weather null,cityName,cityId,last_update
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = formatter.format(date);


//            weather = new Weather(null, cityName, cityId, last_update, dateStr, sunrise, sunset,
//                    now_text, temperature, wind_dir, wind_speed, visibility, pressure);
            //查询得到的cityID和返回的cityId不同
            weather = new Weather(null, cityName, cityID, last_update, dateStr, sunrise, sunset,
                    now_code,now_text, temperature, wind_dir, wind_speed, visibility, pressure);

            //存Suggestion
            List<Suggestion> suggestionList = new ArrayList<>();
            JsonObject suggest = today.get("suggestion").getAsJsonObject();
            String[] suggestName = {"dressing", "uv", "car_washing", "travel", "flu", "sport"};
            for (int i = 0; i < suggestName.length; i++) {
                String brief = suggest.get(suggestName[i]).getAsJsonObject().get("brief").getAsString();
                String details = suggest.get(suggestName[i]).getAsJsonObject().get("details").getAsString();
                Suggestion suggestion = new Suggestion(null, cityID, suggestName[i], brief, details);
                suggestionList.add(suggestion);
                App.getDaoSession().getSuggestionDao().save(suggestion);
            }

            weather.setSuggestions(suggestionList);

            //存Future
            List<Future> futureList = new ArrayList<>();
            JsonArray futureJA = weatherJson.get("future").getAsJsonArray();
            for (int i = 0; i < futureJA.size(); i++) {
                String futuredate = futureJA.get(i).getAsJsonObject().get("date").getAsString();
                String high = futureJA.get(i).getAsJsonObject().get("high").getAsString();
                String low = futureJA.get(i).getAsJsonObject().get("low").getAsString();
                String day = futureJA.get(i).getAsJsonObject().get("day").getAsString();
                String text = futureJA.get(i).getAsJsonObject().get("text").getAsString();
                String wind = futureJA.get(i).getAsJsonObject().get("wind").getAsString();
                String code = futureJA.get(i).getAsJsonObject().get("code1").getAsString();
                Future future = new Future(null, cityID, futuredate, day, high, low, text, code,wind);
                futureList.add(future);
                App.getDaoSession().getFutureDao().save(future);
            }

            weather.setFuture(futureList);

            //存AirQuality
            JsonObject aqiJO = nowJson.get("air_quality").getAsJsonObject().get("city").getAsJsonObject();
            AirQuality airQuality = new Gson().fromJson(aqiJO,AirQuality.class);
            airQuality.setLast_update(aqiJO.get("last_update").getAsString().substring(11,16));
            airQuality.setCity_id(cityID);
            App.getDaoSession().getAirQualityDao().save(airQuality);

//            weather.setAirQuality(airQuality);

            App.getDaoSession().getWeatherDao().save(weather);

        } catch (IOException e) {
            LogUtil.E(e.getMessage());
        }


        return weather;
    }

    public void getHourlyByID(String cityID) {
        mNetClient.getCity24H(cityID)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        mListener.successed(parseResponse2Hourly(responseBody, cityID));
                    }
                });
    }

    //解析response 存数据库并返回
    private List<HourlyWeather> parseResponse2Hourly(ResponseBody responseBody, String cityID) {
        List<HourlyWeather> hourlyWeatherList = new ArrayList<>();
        try {

            String content = responseBody.string();//string只能调用一次 完了输入流即关闭
            JsonObject jsonObject = new JsonParser().parse(content).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("hourly");
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject hourlyJson = jsonArray.get(i).getAsJsonObject();
                String text = hourlyJson.get("text").getAsString();
                String code = hourlyJson.get("code").getAsString();
                String temperature = hourlyJson.get("temperature").getAsString();
                String time = hourlyJson.get("time").getAsString().substring(11, 16);
                HourlyWeather hourly = new HourlyWeather(null, cityID, text, code, temperature, time);
                hourlyWeatherList.add(hourly);
                App.getDaoSession().getHourlyWeatherDao().save(hourly);
            }

        } catch (IOException e) {

        }
        return hourlyWeatherList;
    }


}
