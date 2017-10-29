package com.dongua.geather.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongua.geather.App;
import com.dongua.geather.R;
import com.dongua.geather.bean.state.City;
import com.dongua.geather.bean.state.Region;
import com.dongua.geather.bean.state.State;
import com.dongua.geather.bean.weather.AirQuality;
import com.dongua.geather.bean.weather.Future;
import com.dongua.geather.bean.weather.HourlyWeather;
import com.dongua.geather.bean.weather.Weather;
import com.dongua.geather.db.AirQualityDao;
import com.dongua.geather.db.HourlyWeatherDao;
import com.dongua.geather.db.WeatherDao;
import com.dongua.geather.ui.customview.HourlyForecastView;
import com.dongua.geather.ui.customview.RoundRatioBar;
import com.dongua.geather.ui.customview.ScrollWatched;
import com.dongua.geather.ui.customview.ScrollWatcher;
import com.dongua.geather.ui.presenter.WeatherPresenter;
import com.dongua.geather.ui.view.WeatherView;
import com.dongua.geather.utils.LogUtil;
import com.dongua.geather.utils.UIUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.dongua.geather.utils.Constant.MSG_HOURLY_WEATHER_DATA;
import static com.dongua.geather.utils.Constant.MSG_WEATHER_DATA;

/**
 * Created by dongua on 17-7-30.
 */

public class WeatherFragment extends BaseFragment implements WeatherView {

    private WeatherPresenter mPresenter;
    private Context mContext;

    //    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.weather_scroll)
    ScrollView rootScrollView;

    @BindView(R.id.realtime_temperature)
    TextView cur_Temperature;
    @BindView(R.id.realtime_cityname)
    TextView cur_CityName;
    @BindView(R.id.realtime_text)
    TextView cur_CityText;


    @BindView(R.id.header_image)
    ImageView header_image;
    @BindView(R.id.header_cityname)
    TextView header_cityname;
    @BindView(R.id.header_temperature)
    TextView header_temperature;

    @BindView(R.id.header_area)
    LinearLayout header;

    @BindView(R.id.realtime)
    RelativeLayout realtimeLayout;

    @BindView(R.id.footer_area)
    LinearLayout footer;
    @BindView(R.id.realtime_wind)
    LinearLayout cur_Wind;
    @BindView(R.id.realtime_humidity)
    LinearLayout cur_Visibility;
    @BindView(R.id.realtime_pollution)
    LinearLayout cur_Pressure;


    @BindView(R.id.forecast_info1)
    LinearLayout forecast_day1;
    @BindView(R.id.forecast_info2)
    LinearLayout forecast_day2;
    @BindView(R.id.forecast_info3)
    LinearLayout forecast_day3;

    @BindView(R.id.hourly_forecast_hs)
    HorizontalScrollView hourlyScrollView;
    @BindView(R.id.hourly_forecast)
    HourlyForecastView hourlyForecastView;


    @BindView(R.id.aqi_bar)
    RoundRatioBar aqiRatioBar;
    @BindView(R.id.pm25_bar)
    RoundRatioBar pm25RatioBar;

    @BindView(R.id.iv_toolbar_right)
    ImageView ivToolbarRight;


    private List<ScrollWatcher> watcherList = new ArrayList<>();
    //init observer
    ScrollWatched watched = new ScrollWatched() {
        @Override
        public void addWatcher(ScrollWatcher watcher) {
            watcherList.add(watcher);
        }

        @Override
        public void removeWatcher(ScrollWatcher watcher) {
            watcherList.remove(watcher);
        }

        @Override
        public void notifyWatcher(int x) {
            for (ScrollWatcher watcher : watcherList) {
                watcher.update(x);
            }
        }
    };


    List<String> adapterData;
    List<State> stateList;
    List<City> cityList;
    List<Region> regionList;
    int curShowType = 0;


    float headerDistance = 0;
    int headerHeight = 0;
    int footerHeight = 0;
    int footerTop = 0;

    private int tempHigh = 0;
    private int tempLow = 0;

    private TextView mDateText;
    private TextView mDayText;
    private TextView mNigthText;
    private TextView mHighText;
    private TextView mLowText;
    private TextView mWindDirText;
    private TextView mWindSpdText;


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_WEATHER_DATA:
                    Weather weather = (Weather) msg.obj;
                    updateWeahterView(weather);
                    updateAirQualityView(weather);
                    break;
                case MSG_HOURLY_WEATHER_DATA:
                    List<HourlyWeather> hourlyWeathers = (List<HourlyWeather>) msg.obj;
                    updateHourlyWeatherView(hourlyWeathers);
                    break;
                default:
                    break;
            }
        }
    };


    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    String cityID;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, view);


        initPresent();

        initView();

//        initCityDB();

        initViewData();

        initListener();
//        return setContentWithToolBar(view);
        return view;


    }

    private void initListener() {
        ivToolbarRight.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initViewData() {

//        mPresenter.checkUpdate("WX4FBXXFKE4F", "a");

        if (cityID != null) {
            Weather weather = App.getDaoSession().getWeatherDao().queryBuilder()
                    .where(WeatherDao.Properties.City_id.eq(cityID))
                    .list().get(0);

            initWeather(weather);
            mPresenter.checkUpdate(cityID, weather.getUpdate_date());


        } else {
            mPresenter.showWeatherInfo(null);
        }
//        List<Weather> weatherList = App.getDaoSession().getWeatherDao().loadAll();
//        if (weatherList == null || weatherList.size() == 0) {
//
//                mPresenter.showWeatherInfo(cityID);//设置当前城市
//
//        } else {
////            LogUtil.I(weatherList.get(0).getAirQuality().getAqi());
//
//            for (Weather w : weatherList) {
//                initWeather(w);
//            }
//        }
    }


    private void initWeather(Weather w) {
        updateWeahterView(w);
        List<HourlyWeather> hourlyWeathers = App.getDaoSession().getHourlyWeatherDao().queryBuilder()
                .where(HourlyWeatherDao.Properties.City_id.eq(w.getCity_id()))
                .list();

        updateHourlyWeatherView(hourlyWeathers);

        updateAirQualityView(w);
//        List<AirQuality> airQualityList = App.getDaoSession().getAirQualityDao().queryBuilder()
//                .where(AirQualityDao.Properties.City_id.eq(w.getCity_id())).list();
//        if (!airQualityList.isEmpty()) {
//            updateAirQualityView(airQualityList.get(0));
//        }

//        mPresenter.checkUpdate();
    }

    private void updateAirQualityView(Weather w) {

        List<AirQuality> airQualityList = App.getDaoSession().getAirQualityDao().queryBuilder()
                .where(AirQualityDao.Properties.City_id.eq(w.getCity_id())).list();
        if (!airQualityList.isEmpty()) {
            AirQuality airQuality = airQualityList.get(0);
            updateRatioBar(airQuality);
            LogUtil.I(airQuality.toString());
        }
    }


    private void updateHourlyWeatherView(List<HourlyWeather> hourlyWeathers) {
        LogUtil.I("updateHourlyWeatherView: hourly" + hourlyWeathers.size());
        int max = 0;
        int min = 99;
        for (HourlyWeather hourly : hourlyWeathers) {
            int tmp = Integer.parseInt(hourly.getTemperature());
            if (tmp > max) {
                max = tmp;
            }
            if (min > tmp) {
                min = tmp;
            }

        }
//        hourlyWeathers.forEach(hourlyWeather ->  LogUtil.I("hourly:"+hourlyWeather.toString()));
        updateHourlyView(hourlyWeathers, max, min);
    }

    private void updateWeahterView(Weather weather) {

        LogUtil.I("updateHourlyWeatherView:" + weather.toString());


        String tempture = String.format(getResources().getString(R.string.cur_tempture), weather.getTemperature());
        String cityName = weather.getCity_name();
        String cityText = weather.getText_now();
        cur_Temperature.setText(tempture);
        cur_CityName.setText(cityName);
        cur_CityText.setText(cityText);

        header_cityname.setText(cityName);
        header_temperature.setText(tempture);
        Glide.with(this).load(UIUtils.getImageResID(Integer.parseInt(weather.getCode()))).into(header_image);


        //风向
        TextView titleWind = (TextView) cur_Wind.findViewById(R.id.title);
        titleWind.setText(String.format(getResources().getString(R.string.wind), weather.getWind_dir()));
        TextView descWind = (TextView) cur_Wind.findViewById(R.id.desc);

        int flv = (int) Float.parseFloat(weather.getWind_speed());
        descWind.setText(String.format(getResources().getString(R.string.level), flv));
        //可见度
        TextView titleVisibility = (TextView) cur_Visibility.findViewById(R.id.title);
        titleVisibility.setText(R.string.text_visibility);
        TextView descVisibility = (TextView) cur_Visibility.findViewById(R.id.desc);
        descVisibility.setText(weather.getVisibility());
        //气压值
        TextView titlePressure = (TextView) cur_Pressure.findViewById(R.id.title);
        titlePressure.setText(R.string.text_pressure);
        TextView descPressure = (TextView) cur_Pressure.findViewById(R.id.desc);
        descPressure.setText(weather.getPressure());


        List<Future> futureList = weather.getFuture();
        if (futureList == null || futureList.size() == 0) {
            return;
        }

        ImageView forecastImg1 = (ImageView) forecast_day1.findViewById(R.id.forecast_icon);
        TextView forecastDate1 = (TextView) forecast_day1.findViewById(R.id.forecast_date);
        TextView forecastText1 = (TextView) forecast_day1.findViewById(R.id.forecast_text);
        TextView forecastTemp1 = (TextView) forecast_day1.findViewById(R.id.forecast_temp);
        Future day1 = futureList.get(0);
        Glide.with(this).load(UIUtils.getImageResID(Integer.parseInt(day1.getCode()))).into(forecastImg1);
        forecastDate1.setText(day1.getDate());
        forecastText1.setText(day1.getText().replace("/", "转"));
        forecastTemp1.setText(String.format(getResources().getString(R.string.forecast_temp), day1.getLow(), day1.getHigh()));
        tempHigh = Integer.parseInt(day1.getHigh());//今日最高温度
        tempLow = Integer.parseInt(day1.getLow());//今日最低温度

        ImageView forecastImg2 = (ImageView) forecast_day2.findViewById(R.id.forecast_icon);
        TextView forecastDate2 = (TextView) forecast_day2.findViewById(R.id.forecast_date);
        TextView forecastText2 = (TextView) forecast_day2.findViewById(R.id.forecast_text);
        TextView forecastTemp2 = (TextView) forecast_day2.findViewById(R.id.forecast_temp);

        Future day2 = futureList.get(1);
        Glide.with(this).load(UIUtils.getImageResID(Integer.parseInt(day1.getCode()))).into(forecastImg2);
        forecastDate2.setText(day2.getDate());
        forecastText2.setText(day2.getText().replace("/", "转"));
        forecastTemp2.setText(String.format(getResources().getString(R.string.forecast_temp), day2.getLow(), day2.getHigh()));


        ImageView forecastImg3 = (ImageView) forecast_day3.findViewById(R.id.forecast_icon);
        TextView forecastDate3 = (TextView) forecast_day3.findViewById(R.id.forecast_date);
        TextView forecastText3 = (TextView) forecast_day3.findViewById(R.id.forecast_text);
        TextView forecastTemp3 = (TextView) forecast_day3.findViewById(R.id.forecast_temp);

        Future day3 = futureList.get(2);
        Glide.with(this).load(UIUtils.getImageResID(Integer.parseInt(day3.getCode()))).into(forecastImg3);
        forecastDate3.setText(day3.getDate());
        forecastText3.setText(day3.getText().replace("/", "转"));
        forecastTemp3.setText(String.format(getResources().getString(R.string.forecast_temp), day3.getLow(), day3.getHigh()));


    }


    private void initView() {
        initRealtimeLayout();
//        initRefreshLayout();
        initScrollView();
        initHourlyView();
        initRatioBar();


    }

    private void initRealtimeLayout() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        realtimeLayout.setBackground(getResources().getDrawable(R.drawable.bg_realtime));

//        if (hour <= 6 && hour >= 19){
//        }
    }

    private void initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                requestRefresh();
            }
        });
    }

    private void requestRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);

            }
        }).start();
    }

    private void initScrollView() {
        rootScrollView.setSystemUiVisibility(View.INVISIBLE);
        rootScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > footerTop + headerDistance) {
                    header.setVisibility(View.VISIBLE);
                    header.setAlpha(1);
//                    Log.i("wxdtg", "onScrollChange: 显示" + header.getVisibility());

                } else if (scrollY > footerTop) {
                    header.setVisibility(View.VISIBLE);
                    float alpha = (scrollY - footerTop) / headerDistance;
                    header.setAlpha(alpha);
//                    Log.i("wxdtg", "onScrollChange: 渐变");

                } else {
                    header.setVisibility(View.INVISIBLE);
//                    Log.i("wxdtg", "onScrollChange: 不显示");

                }
            }
        });
    }

    private void initRatioBar() {


        updateRatioBar(null);


    }

    private void updateRatioBar(AirQuality airQuality) {
        if (airQuality == null) {
            aqiRatioBar.setPercentValue(0);
            aqiRatioBar.setValue_text("0");
            pm25RatioBar.setPercentValue(0);
            pm25RatioBar.setValue_text("0");
        } else {
            String aqiVal = airQuality.getAqi();
            Float aqiPer = Float.parseFloat(aqiVal) / 500;
            String pm25Val = airQuality.getPm25();
            Float pm25Per = Float.parseFloat(pm25Val) / 500;
            aqiRatioBar.setPercentValue(aqiPer);
            aqiRatioBar.setValue_text(aqiVal);
            pm25RatioBar.setPercentValue(pm25Per);
            pm25RatioBar.setValue_text(pm25Val);
        }


        pm25RatioBar.setAbbr_text("PM2.5");
        pm25RatioBar.setDesc_text("首要污染物");
        aqiRatioBar.setAbbr_text("AQI");
        aqiRatioBar.setDesc_text("空气质量指数");

        aqiRatioBar.requestLayout();
        pm25RatioBar.requestLayout();
    }


    private void initHourlyView() {

        watched.addWatcher(hourlyForecastView);

        hourlyScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                watched.notifyWatcher(scrollX);
            }
        });


//        List<HourlyWeather> hourlyWeatherList = new ArrayList<>();
//        Gson gson = new Gson();
//        JsonObject jo = new JsonParser().parse(data).getAsJsonObject();
//        JsonArray ja = jo.getAsJsonArray("hourly");
//
//        for (JsonElement element : ja) {
//            HourlyWeather bean = gson.fromJson(element, HourlyWeather.class);
//            hourlyWeatherList.add(bean);
//        }
//        updateHourlyWeatherView(hourlyWeatherList, 16, 27);
    }

    private void updateHourlyView(List<HourlyWeather> hourlyWeatherList, int high, int low) {

//
//        List<HourlyWeather> hourlyWeatherList = new ArrayList<>();
//        Gson gson = new Gson();
//        JsonObject jo = new JsonParser().parse(jsonData).getAsJsonObject();
//        JsonArray ja = jo.getAsJsonArray("hourly");
//
//        for (JsonElement element : ja) {
//            HourlyWeather bean = gson.fromJson(element, HourlyWeather.class);
//            hourlyWeatherList.add(bean);
//        }

        LogUtil.I("hourlyForecastView Init: high=" + high + ",low=" + low);
        hourlyForecastView.setHighestTemp(high);
        hourlyForecastView.setLowestTemp(low);
        hourlyForecastView.initData(hourlyWeatherList);

        hourlyForecastView.requestLayout();


    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

//        header.setVisibility(View.VISIBLE);

        headerHeight = header.getHeight();
        footerTop = footer.getTop();
        footerHeight = footer.getHeight();
        headerDistance = Math.abs(footerHeight - headerHeight);


    }

    //todo addto BaseAct
//    @OnClick({R.id.toolbar_lefticon, R.id.toolbar_righticon})
//    public void onToolBarClick(View view) {
//        switch (view.getId()) {
//            case R.id.toolbar_lefticon:
//                AppManager.getInstance().finishActivity();
//                break;
//            case R.id.toolbar_righticon:
//                showPoupWindow(view);
//                break;
//            default:
//                break;
//        }
//    }


//    private void showPoupWindow(View view) {
//
//        View popupView = WeatherFragment.this.getActivity().getLayoutInflater().inflate(R.layout.popup_menu, null);
//
//        TextView tv = (TextView) popupView.findViewById(R.id.add_city);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new CommomDialog(mContext, dialog, "您确定删除此信息？", new CommomDialog.OnCloseListener() {
//
//                    @Override
//                    public void onClick(Dialog dialog, boolean confirm) {
//                        if (confirm) {
//                            UIUtils.showToast(dialog.getContext(), "confirm");
//                            dialog.dismiss();
//                        }
//                    }
//                })
//                        .setTitle("提示").show();
//            }
//        });
//
//        PopupWindow window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        window.setAnimationStyle(R.style.popup_window_anim);
//        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
//        window.setFocusable(true);
//        window.setOutsideTouchable(true);
//
//        window.update();
//
//        window.showAsDropDown(view, 0, 20);
//    }


    public void initPresent() {
        mPresenter = new WeatherPresenter(this);
    }


    @Override
    public void setPresenter(WeatherPresenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void update(Object bean, int msgType) {
        Message message = new Message();
        message.obj = bean;
        message.what = msgType;
        mHandler.sendMessage(message);
    }


}
