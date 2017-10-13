package com.dongua.geather.ui.presenter;

import com.dongua.geather.App;
import com.dongua.geather.AppManager;
import com.dongua.geather.bean.weather.Weather;
import com.dongua.geather.ui.base.BasePresenter;
import com.dongua.geather.ui.listener.OnNetworkListener;
import com.dongua.geather.ui.model.LocationModel;
import com.dongua.geather.ui.model.WeatherModel;
import com.dongua.geather.ui.view.WeatherView;
import com.dongua.geather.utils.LogUtil;
import com.dongua.geather.utils.SharedPreferenceUtil;

import static com.dongua.geather.utils.Constant.SP_LOCDB;

/**
 * Created by dongua on 17-7-30.
 */

public class WeatherPresenter implements BasePresenter<WeatherView>,OnNetworkListener {

    WeatherModel mWeatherModel;
    WeatherView mWeatherView;
    LocationModel mLocModel;

    public WeatherPresenter(WeatherView mWeatherView) {
        this.mWeatherView = mWeatherView;
        mWeatherView.setPresenter(this);
        mWeatherModel = new WeatherModel();
        mWeatherModel.setNetworkListener(this);

        mLocModel = new LocationModel();
    }

    public void showWeatherInfo(){
        mWeatherModel.getWeatherByIp();
//        mWeatherModel.getWeatherJson(ipOrName);
    }


    @Override
    public WeatherView getView() {
        return mWeatherView;
    }

    @Override
    public void successed(String resp) {
        mWeatherView.setCity_name(resp);
    }

    @Override
    public void successed(Object bean) {
        Weather weather = (Weather)bean;
        LogUtil.I("获得weather："+weather.toString());

    }

    @Override
    public void failed() {

    }

    public void makeLocDB() {
        new locDBThread().start();
    }


    class locDBThread extends Thread {
        @Override
        public void run() {
            if(mLocModel.saveLocDB()){
                SharedPreferenceUtil.putSharedPreferences(AppManager.getInstance().getAppContext(),SP_LOCDB,true);
                LogUtil.I("loc db save successful");
            }else {
                LogUtil.I("loc db save fail");
            }
        }
    }
}
