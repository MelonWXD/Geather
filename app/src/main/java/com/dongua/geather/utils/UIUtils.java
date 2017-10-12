package com.dongua.geather.utils;

import android.content.Context;
import android.widget.Toast;

import com.dongua.geather.App;
import com.dongua.geather.AppManager;

/**
 * Created by dongua on 17-8-16.
 */

public class UIUtils {
    public static void showToast(Context context, String msg){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();

    }
    public static void showToast(String msg){
        Toast.makeText(AppManager.getInstance().getAppContext(),msg, Toast.LENGTH_SHORT).show();
    }
}
