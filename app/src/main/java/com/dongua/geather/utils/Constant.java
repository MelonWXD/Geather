package com.dongua.geather.utils;

/**
 * Created by dongua on 17-8-1.
 */

public class Constant {

    public static final String API_KEY = "ryualdb6d8cgv8zd";
    //https://api.seniverse.com/v3/weather/daily.json?
    // key=ryualdb6d8cgv8zd&location=beijing&language=zh-Hans&unit=c&start=0&days=5
    public static final String WEATHER_API_ID = "ryualdb6d8cgv8zd";
    public static final String WEATHER_JSON = "https://api.seniverse.com/v3/weather/daily.json?";
    public static final String WEATHER_JSON_URL ="https://api.seniverse.com/v3/weather/daily.json?" +
            "key=ryualdb6d8cgv8zd&location=101010100&language=zh-Hans&unit=c&start=0&days=5";

    public static final String WEATHER_BASE_URL ="http://weixin.jirengu.com/";


    public static final String DATABASE_WEATHER = "weather";


    public static final String SP_LOCDB = "SP_LOCDB";


    public static final int DATA_STATE = 0x01;
    public static final int DATA_CITY = 0x02;
    public static final int DATA_REGION = 0x03;
}

