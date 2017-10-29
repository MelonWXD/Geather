package com.dongua.geather.ui.model;

import android.util.Log;

import com.dongua.geather.App;
import com.dongua.geather.bean.weather.AirQuality;
import com.dongua.geather.bean.weather.Future;
import com.dongua.geather.bean.weather.HourlyWeather;
import com.dongua.geather.bean.weather.Suggestion;
import com.dongua.geather.bean.weather.Weather;
import com.dongua.geather.db.AirQualityDao;
import com.dongua.geather.db.FutureDao;
import com.dongua.geather.db.HourlyWeatherDao;
import com.dongua.geather.db.SuggestionDao;
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
    private static final String TAG = "WeatherModel";

    OnNetworkListener mListener;
    NetApi mNetClient = NetApi.getInstance();


    public void setNetworkListener(OnNetworkListener listener) {
        mListener = listener;
    }

    //http get
    public Weather getWeather(String cityID) {
        return null;
    }


    public void getWeatherByIP() {
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

                        getCityWeatherByID(cityID, null);
                        getHourlyByID(cityID);
                    }
                });

    }

    public void getCityWeatherByID(String cityID, String updateTime) {
        mNetClient.getCityWeather(cityID)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Weather weather = parseResponse2Weather(responseBody, cityID, updateTime);

                        mListener.successed(weather);

                    }
                });
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


    public void checkUpdate(String cityID, String lastUpdateTime) {
        getCityWeatherByID(cityID, lastUpdateTime);
    }


    //解析response 更新数据库并返回
    private Weather parseResponse2Weather(ResponseBody responseBody, String cityID, String updateTime) {

        Weather weather = null;
        boolean dataExist = true;
        //数据库查找cityID
        if (updateTime == null) {
            // !App.getDaoSession().getWeatherDao().queryBuilder().where(WeatherDao.Properties.City_id.eq(cityID)).list().isEmpty()
            dataExist = false;
        }
        try {

            String content = responseBody.string();//string只能调用一次 完了输入流即关闭
//        LogUtil.I(content);
            JsonObject jsonObject = new JsonParser().parse(content).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("weather");

            JsonObject weatherJson = jsonArray.get(0).getAsJsonObject();
            String cityName = weatherJson.get("city_name").getAsString();
//            String cityId = weatherJson.get("city_id").getAsString();//这里的cityid是错的 妈的
            String last_update = weatherJson.get("last_update").getAsString();
            if (updateTime != null && last_update.equals(updateTime)) {
                return null;
            }

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
                    now_code, now_text, temperature, wind_dir, wind_speed, visibility, pressure);


            //sql中的数据
            List<Suggestion> suggestions = App.getDaoSession().getSuggestionDao().queryBuilder()
                    .where(SuggestionDao.Properties.City_id.eq(cityID)).list();
            //存Suggestion
            List<Suggestion> suggestionList = new ArrayList<>();

            JsonObject suggest = today.get("suggestion").getAsJsonObject();
            String[] suggestName = {"dressing", "uv", "car_washing", "travel", "flu", "sport"};
            for (int i = 0; i < suggestName.length; i++) {
                String brief = suggest.get(suggestName[i]).getAsJsonObject().get("brief").getAsString();
                String details = suggest.get(suggestName[i]).getAsJsonObject().get("details").getAsString();
                Suggestion suggestion = new Suggestion(null, cityID, suggestName[i], brief, details);

                if (!suggestions.isEmpty() && i < suggestions.size()) {
                    Long id = suggestions.get(i).getId();
                    suggestion.setId(id);
                    App.getDaoSession().getSuggestionDao().update(suggestion);
                } else {
                    App.getDaoSession().getSuggestionDao().save(suggestion);
                }


                suggestionList.add(suggestion);

            }

            weather.setSuggestions(suggestionList);


            //sql中的数据
            List<Future> futures = App.getDaoSession().getFutureDao().queryBuilder()
                    .where(FutureDao.Properties.City_id.eq(cityID)).list();
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
                Future future = new Future(null, cityID, futuredate, day, high, low, text, code, wind);


                if (!futures.isEmpty() && i < futures.size()) {
                    Long id = futures.get(i).getId();
                    future.setId(id);
                    App.getDaoSession().getFutureDao().update(future);
                } else {
                    App.getDaoSession().getFutureDao().save(future);
                }


                futureList.add(future);

            }

            weather.setFuture(futureList);

            //sql中的数据
            List<AirQuality> airQualities = App.getDaoSession().getAirQualityDao().queryBuilder()
                    .where(AirQualityDao.Properties.City_id.eq(cityID)).list();
            //存AirQuality
            JsonObject aqiJO = nowJson.get("air_quality").getAsJsonObject().get("city").getAsJsonObject();
            AirQuality airQuality = new Gson().fromJson(aqiJO, AirQuality.class);
            airQuality.setLast_update(aqiJO.get("last_update").getAsString().substring(11, 16));
            airQuality.setCity_id(cityID);

            if (!airQualities.isEmpty()) {
                Long id = futures.get(0).getId();
                airQuality.setId(id);
                App.getDaoSession().getAirQualityDao().update(airQuality);
            } else {
                App.getDaoSession().getAirQualityDao().save(airQuality);
            }


//            weather.setAirQuality(airQuality);
            List<Weather> weathers = App.getDaoSession().getWeatherDao().queryBuilder()
                    .where(WeatherDao.Properties.City_id.eq(cityID)).list();
            if (!weathers.isEmpty()) {
                weather.setId(weathers.get(0).getId());
                App.getDaoSession().getWeatherDao().update(weather);
            } else {
                App.getDaoSession().getWeatherDao().save(weather);
            }


        } catch (IOException e) {
            LogUtil.E(e.getMessage());
        }


        return weather;
    }


    //解析response 存数据库并返回
    private List<HourlyWeather> parseResponse2Hourly(ResponseBody responseBody, String cityID) {
        List<HourlyWeather> hourlyWeatherList = new ArrayList<>();


        List<HourlyWeather> hourlyWeathers = App.getDaoSession().getHourlyWeatherDao().queryBuilder()
                .where(HourlyWeatherDao.Properties.City_id.eq(cityID)).list();

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
                if (!hourlyWeathers.isEmpty() && i < hourlyWeathers.size()) {
                    hourly.setId(hourlyWeathers.get(i).getId());
                    App.getDaoSession().getHourlyWeatherDao().update(hourly);
                }
                App.getDaoSession().getHourlyWeatherDao().save(hourly);

                hourlyWeatherList.add(hourly);

            }

        } catch (IOException e) {

        }
        return hourlyWeatherList;
    }


}
