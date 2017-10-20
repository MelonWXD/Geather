package com.dongua.geather.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.dongua.geather.AppManager;
import com.dongua.geather.R;
import com.dongua.geather.ui.adapter.FragAdapter;
import com.dongua.geather.ui.base.BaseActivity;
import com.dongua.geather.ui.base.BaseFragment;
import com.dongua.geather.ui.listener.IWindowFocusChangedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duoyi on 17-10-20.
 */

public class MainActivity extends BaseActivity {


    ViewPager mViewPager;
    List<BaseFragment> windowFocusListenerList = new ArrayList<>();
    List<Fragment> fragmentList = new ArrayList<>();

    WeatherFragment weatherFragment1;
    WeatherFragment weatherFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppManager.getInstance().addActivity(this);

        init();
    }

    private void init() {
        weatherFragment1 = new WeatherFragment();
        windowFocusListenerList.add(weatherFragment1);

        weatherFragment2 = new WeatherFragment();
        windowFocusListenerList.add(weatherFragment2);
        weatherFragment2.setCityID("WX4FBXXFKE4F");
        fragmentList.add(weatherFragment1);
        fragmentList.add(weatherFragment2);
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragmentList);

        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        for(IWindowFocusChangedListener listener:windowFocusListenerList){
            listener.onWindowFocusChanged(hasFocus);
        }
    }
}
