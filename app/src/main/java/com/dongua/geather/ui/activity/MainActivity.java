package com.dongua.geather.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.dongua.geather.AppManager;
import com.dongua.geather.R;
import com.dongua.geather.bean.weather.Weather;
import com.dongua.geather.ui.adapter.FragAdapter;
import com.dongua.geather.ui.fragment.BaseFragment;
import com.dongua.geather.ui.fragment.WeatherFragment;
import com.dongua.geather.ui.listener.IWindowFocusChangedListener;
import com.dongua.geather.ui.presenter.LocationPresenter;
import com.dongua.geather.ui.view.LocationView;
import com.dongua.geather.utils.LogUtil;
import com.dongua.geather.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import static com.dongua.geather.utils.Constant.SP_CITY_PREFIX;
import static com.dongua.geather.utils.Constant.SP_CITY_SIZE;
import static com.dongua.geather.utils.Constant.SP_LOCDB;

/**
 * Created by duoyi on 17-10-20.
 */

public class MainActivity extends BaseActivity implements LocationView {

    private LocationPresenter mPresenter;

    ViewPager mViewPager;
    List<BaseFragment> windowFocusListenerList = new ArrayList<>();
    List<Fragment> fragmentList = new ArrayList<>();
    FragAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppManager.getInstance().addActivity(this);


        initPresent();
        initCityDB();

        initFragment();

    }

    public void initPresent() {
        mPresenter = new LocationPresenter(this);
    }


    @Override
    public void setPresenter(LocationPresenter presenter) {
        mPresenter = presenter;
    }


    private void initFragment() {
        List<String> cityIDList = getSharedCityID();
        if (cityIDList == null) {
            WeatherFragment weatherFragment = new WeatherFragment();
            windowFocusListenerList.add(weatherFragment);
            fragmentList.add(weatherFragment);
            adapter = new FragAdapter(getSupportFragmentManager(), fragmentList);

        }else {
            for (String cityID:cityIDList){
                WeatherFragment weatherFragment = new WeatherFragment();
                weatherFragment.setCityID(cityID);
                windowFocusListenerList.add(weatherFragment);
                fragmentList.add(weatherFragment);
            }
            adapter = new FragAdapter(getSupportFragmentManager(), fragmentList);

        }


//        weatherFragment2 = new WeatherFragment();
//        windowFocusListenerList.add(weatherFragment2);
//        weatherFragment2.setCityID("WX4FBXXFKE4F");

//        fragmentList.add(weatherFragment2);

        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        for (IWindowFocusChangedListener listener : windowFocusListenerList) {
            listener.onWindowFocusChanged(hasFocus);
        }
    }


    private void initCityDB() {
        if (!(boolean) SharedPreferenceUtil.getSharedPreferences(SP_LOCDB, false)) {
            LogUtil.I("SP不存在 生成城市db");
            mPresenter.makeLocDB();
        } else {
            LogUtil.I("SP存在 ");
        }
    }


    private List<String> getSharedCityID() {
        List<String> cityIDList = null;
        //如果有保存城市
        int size = (int) SharedPreferenceUtil.getSharedPreferences(SP_CITY_SIZE, -1);
        if (size > 0) {
            cityIDList = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                String cityID = (String) SharedPreferenceUtil.getSharedPreferences(SP_CITY_PREFIX + i, "NoCityID");
                cityIDList.add(cityID);
            }

        }
        return cityIDList;

    }

}
