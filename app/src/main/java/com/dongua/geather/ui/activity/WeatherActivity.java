package com.dongua.geather.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dongua.geather.AppManager;
import com.dongua.geather.R;
import com.dongua.geather.bean.state.City;
import com.dongua.geather.bean.state.Region;
import com.dongua.geather.bean.state.State;
import com.dongua.geather.bean.weather.HourlyWeatherBean;
import com.dongua.geather.ui.base.BaseActivity;
import com.dongua.geather.ui.customview.CommomDialog;
import com.dongua.geather.ui.customview.HourlyForecastView;
import com.dongua.geather.ui.customview.RoundRatioBar;
import com.dongua.geather.ui.customview.ScrollWatched;
import com.dongua.geather.ui.customview.ScrollWatcher;
import com.dongua.geather.ui.presenter.WeatherPresenter;
import com.dongua.geather.ui.view.WeatherView;
import com.dongua.geather.utils.LogUtil;
import com.dongua.geather.utils.SharedPreferenceUtil;
import com.dongua.geather.utils.UIUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dongua.geather.R.style.dialog;
import static com.dongua.geather.utils.Constant.SP_LOCDB;

/**
 * Created by dongua on 17-7-30.
 */

public class WeatherActivity extends BaseActivity implements WeatherView {

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

    @BindView(R.id.header_area)
    LinearLayout header;

    @BindView(R.id.realtime)
    RelativeLayout realtimeLayout;

    @BindView(R.id.footer_area)
    LinearLayout footer;
    @BindView(R.id.realtime_wind)
    LinearLayout cur_Wind;
    @BindView(R.id.realtime_humidity)
    LinearLayout cur_Humidity;
    @BindView(R.id.realtime_pollution)
    LinearLayout cur_Pollution;


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

    private TextView mDateText;
    private TextView mDayText;
    private TextView mNigthText;
    private TextView mHighText;
    private TextView mLowText;
    private TextView mWindDirText;
    private TextView mWindSpdText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AppManager.getInstance().addActivity(this);
        mContext = this;
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);


        initPresent();

        initView();

        initDB();


        mPresenter.showWeatherInfo();
//        mPresenter.showWeatherInfo();
    }


    private void initDB() {
        if (!(boolean) SharedPreferenceUtil.getSharedPreferences(this, SP_LOCDB, false)) {
            LogUtil.I("SP不存在 生成城市db");
            mPresenter.makeLocDB();

        }
    }

    private void initView() {
        initRealtimeLayout();
//        initRefreshLayout();
        initScrollView();
        initHourlyView(data);
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
                    Log.i("wxdtg", "onScrollChange: 显示" + header.getVisibility());
                } else if (scrollY > footerTop) {
                    header.setVisibility(View.VISIBLE);
                    float alpha = (scrollY - footerTop) / headerDistance;
                    header.setAlpha(alpha);
                    Log.i("wxdtg", "onScrollChange: 渐变");

                } else {
                    header.setVisibility(View.INVISIBLE);
                    Log.i("wxdtg", "onScrollChange: 不显示");

                }
            }
        });
    }

    private void initRatioBar() {

        RoundRatioBar aqiRatioBar = (RoundRatioBar) findViewById(R.id.aqi_bar);

        RoundRatioBar pm25RatioBar = (RoundRatioBar) findViewById(R.id.pm25_bar);

        aqiRatioBar.setPercentValue(0.6);
        aqiRatioBar.setAbbr_text("AQI");
        aqiRatioBar.setDesc_text("空气质量指数");
        aqiRatioBar.setValue_text("98");

        pm25RatioBar.setPercentValue(0.3);
        pm25RatioBar.setAbbr_text("PM2.5");
        pm25RatioBar.setDesc_text("首要污染物");
        pm25RatioBar.setValue_text("36");

    }


    private void initHourlyView(String jsonData) {


        List<HourlyWeatherBean> hourlyWeatherList = new ArrayList<>();
        Gson gson = new Gson();
        JsonObject jo = new JsonParser().parse(jsonData).getAsJsonObject();
        JsonArray ja = jo.getAsJsonArray("hourly");

        for (JsonElement element : ja) {
            HourlyWeatherBean bean = gson.fromJson(element, HourlyWeatherBean.class);
            hourlyWeatherList.add(bean);
        }


        hourlyForecastView.setHighestTemp(27);
        hourlyForecastView.setLowestTemp(16);
        hourlyForecastView.initData(hourlyWeatherList);
        watched.addWatcher(hourlyForecastView);

        hourlyScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                watched.notifyWatcher(scrollX);
            }
        });

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        header.setVisibility(View.VISIBLE);

        headerHeight = header.getHeight();
        footerTop = footer.getTop();
        footerHeight = footer.getHeight();
        headerDistance = Math.abs(footerHeight - headerHeight);
//        LogUtil.I(footerTop + "," + footerHeight + "," + headerHeight);

        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

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


    private void showPoupWindow(View view) {

        View popupView = WeatherActivity.this.getLayoutInflater().inflate(R.layout.popup_menu, null);

        TextView tv = (TextView) popupView.findViewById(R.id.add_city);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommomDialog(mContext, dialog, "您确定删除此信息？", new CommomDialog.OnCloseListener() {

                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            UIUtils.showToast(dialog.getContext(), "confirm");
                            dialog.dismiss();
                        }
                    }
                })
                        .setTitle("提示").show();
            }
        });

        PopupWindow window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window.setAnimationStyle(R.style.popup_window_anim);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        window.setFocusable(true);
        window.setOutsideTouchable(true);

        window.update();

        window.showAsDropDown(view, 0, 20);
    }


    public void initPresent() {
        mPresenter = new WeatherPresenter(this);
    }


    @Override
    public void setPresenter(WeatherPresenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void setCity_name(String city_name) {

    }


    @Override
    public void setDate(String date) {

    }

    @Override
    public void setText_dat(String text_dat) {

    }

    @Override
    public void setText_night(String text_night) {

    }

    @Override
    public void setHigh(String high) {

    }

    @Override
    public void setLow(String low) {

    }

    @Override
    public void setWind_dir(String wind_dir) {

    }

    @Override
    public void setWind_speed(String wind_speed) {

    }


    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);

    }

    String data = " {\"hourly\":[" +
            "{\"text\":\"晴\",\"code\":\"1\",\"temperature\":\"17\",\"time\":\"02:00\"}," +
            "{\"text\":\"晴\",\"code\":\"1\",\"temperature\":\"17\",\"time\":\"03:00\"}," +
            "{\"text\":\"晴\",\"code\":\"1\",\"temperature\":\"17\",\"time\":\"04:00\"}," +
            "{\"text\":\"多云\",\"code\":\"4\",\"temperature\":\"16\",\"time\":\"05:00\"}," +
            "{\"text\":\"晴\",\"code\":\"0\",\"temperature\":\"17\",\"time\":\"06:00\"}," +
            "{\"text\":\"晴\",\"code\":\"0\",\"temperature\":\"18\",\"time\":\"07:00\"}," +
            "{\"text\":\"晴\",\"code\":\"0\",\"temperature\":\"19\",\"time\":\"08:00\"}," +
            "{\"text\":\"晴\",\"code\":\"0\",\"temperature\":\"20\",\"time\":\"09:00\"}," +
            "{\"text\":\"晴\",\"code\":\"0\",\"temperature\":\"22\",\"time\":\"10:00\"}," +
            "{\"text\":\"晴\",\"code\":\"0\",\"temperature\":\"24\",\"time\":\"11:00\"}," +
            "{\"text\":\"晴\",\"code\":\"0\",\"temperature\":\"25\",\"time\":\"12:00\"}," +
            "{\"text\":\"晴\",\"code\":\"0\",\"temperature\":\"26\",\"time\":\"13:00\"}," +
            "{\"text\":\"晴\",\"code\":\"0\",\"temperature\":\"27\",\"time\":\"14:00\"}," +
            "{\"text\":\"晴\",\"code\":\"0\",\"temperature\":\"26\",\"time\":\"15:00\"}," +
            "{\"text\":\"多云\",\"code\":\"4\",\"temperature\":\"26\",\"time\":\"16:00\"}," +
            "{\"text\":\"多云\",\"code\":\"4\",\"temperature\":\"25\",\"time\":\"17:00\"}," +
            "{\"text\":\"多云\",\"code\":\"4\",\"temperature\":\"24\",\"time\":\"18:00\"}," +
            "{\"text\":\"晴\",\"code\":\"1\",\"temperature\":\"23\",\"time\":\"19:00\"}," +
            "{\"text\":\"晴\",\"code\":\"1\",\"temperature\":\"21\",\"time\":\"20:00\"}," +
            "{\"text\":\"晴\",\"code\":\"1\",\"temperature\":\"21\",\"time\":\"21:00\"}," +
            "{\"text\":\"晴\",\"code\":\"1\",\"temperature\":\"21\",\"time\":\"22:00\"}," +
            "{\"text\":\"晴\",\"code\":\"1\",\"temperature\":\"21\",\"time\":\"23:00\"}," +
            "{\"text\":\"晴\",\"code\":\"1\",\"temperature\":\"21\",\"time\":\"00:00\"}," +
            "{\"text\":\"晴\",\"code\":\"1\",\"temperature\":\"20\",\"time\":\"01:00\"}]}";
}
