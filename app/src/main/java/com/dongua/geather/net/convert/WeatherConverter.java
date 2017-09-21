package com.dongua.geather.net.convert;

import com.dongua.geather.App;
import com.dongua.geather.bean.weather.Future;
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

public class WeatherConverter implements Converter<ResponseBody, Weather> {

    private static WeatherConverter INSTANCE = new WeatherConverter();

    public static WeatherConverter create() {
        return INSTANCE;
    }

    @Override
    public Weather convert(ResponseBody value) throws IOException {
        JsonObject jsonObject = new JsonParser().parse(value.string()).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("weather");

        JsonObject weatherJson = jsonArray.get(0).getAsJsonObject();
        String cityName = weatherJson.get("city_name").getAsString();
        String cityId = weatherJson.get("city_id").getAsString();
        String last_update = weatherJson.get("last_update").getAsString();

        JsonObject nowJson = weatherJson.get("now").getAsJsonObject();
        String now_text = nowJson.get("text").getAsString();
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
        Weather weather = new Weather(null,cityName,cityId,last_update,dateStr,sunrise,sunset,
                now_text,temperature,wind_dir,wind_speed,visibility,pressure);
        App.getDaoSession().getWeatherDao().save(weather);

        List<Weather> list = App.getDaoSession().getWeatherDao().queryBuilder().where(WeatherDao.Properties.City_id.eq(cityId)).list();
        if(list == null){
            logger.error("数据没有找到");
            return null;
        }
        weather = list.get(0);
        Long citySqlID = weather.getId();


        List<Suggestion> suggestionList = new ArrayList<>();
        JsonObject suggest = today.get("suggestion").getAsJsonObject();
        String[] suggestName = {"dressing", "uv", "car_washing", "travel", "flu", "sport"};
        for (int i = 0; i < suggestName.length; i++) {
            String brief = suggest.get(suggestName[i]).getAsJsonObject().get("brief").getAsString();
            String details = suggest.get(suggestName[i]).getAsJsonObject().get("details").getAsString();
            App.getDaoSession().getSuggestionDao().save(new Suggestion(null,citySqlID,suggestName[i],brief,details));
        }
        //---

        JsonArray future = weatherJson.get("future").getAsJsonArray();
        for (int i = 0; i < 3; i++) {
            String futuredate = future.get(i).getAsJsonObject().get("date").getAsString();
            String high = future.get(i).getAsJsonObject().get("high").getAsString();
            String low = future.get(i).getAsJsonObject().get("low").getAsString();
            String day = future.get(i).getAsJsonObject().get("day").getAsString();
            String text = future.get(i).getAsJsonObject().get("text").getAsString();
            String wind = future.get(i).getAsJsonObject().get("wind").getAsString();
            App.getDaoSession().getFutureDao().save(new Future(null,citySqlID,futuredate,day,high,low,text,wind));
        }




        return weather;
    }


}
