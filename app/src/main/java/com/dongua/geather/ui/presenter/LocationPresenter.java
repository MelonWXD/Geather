package com.dongua.geather.ui.presenter;

import com.dongua.geather.AppManager;
import com.dongua.geather.ui.model.LocationModel;
import com.dongua.geather.ui.view.LocationView;
import com.dongua.geather.utils.LogUtil;
import com.dongua.geather.utils.SharedPreferenceUtil;

import static com.dongua.geather.utils.Constant.SP_LOCDB;

/**
 * Created by duoyi on 17-10-21.
 */

public class LocationPresenter implements BasePresenter {

    LocationModel mLocModel;
    LocationView mLocView;

    public LocationPresenter(LocationView locationView) {
        this.mLocModel = new LocationModel();
        mLocView = locationView;
        mLocView.setPresenter(this);

    }

    @Override
    public Object getView() {
        return mLocView;
    }


    public void makeLocDB() {
        new locDBThread().start();
    }

    class locDBThread extends Thread {
        @Override
        public void run() {
            if (mLocModel.saveLocDB())

            {
                SharedPreferenceUtil.putSharedPreferences(SP_LOCDB, true);
                LogUtil.I("loc db save successful");
            } else

            {
                LogUtil.I("loc db save fail");
            }
        }
    }


}