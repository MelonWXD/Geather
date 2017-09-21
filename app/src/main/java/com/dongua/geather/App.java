package com.dongua.geather;

import android.app.Application;
import android.content.Context;

import com.dongua.geather.db.DaoMaster;
import com.dongua.geather.db.DaoSession;

import org.apache.log4j.BasicConfigurator;
import org.greenrobot.greendao.database.Database;

import static com.dongua.geather.utils.Constant.DATABASE_WEATHER;

/**
 * Created by dongua on 17-8-6.
 */

public class App extends Application {

    private static DaoSession mDaoSession;

    private static App INSTANCE;

    private static Context mContext;

    public static App getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new App();
        }
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        setupDatabase(this);
        setupLog4j();

    }

    public static Context getContext() {
        return mContext;
    }

    private void setupDatabase(Context context) {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, DATABASE_WEATHER);
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    private void setupLog4j() {
        BasicConfigurator.configure();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }





}
