package com.dongua.geather.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.widget.ImageView;
import android.widget.Toast;

import com.dongua.geather.App;
import com.dongua.geather.AppManager;
import com.dongua.geather.R;

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


    public static int getImageResID(int weatherCode) {
        int resID = 0;
        switch (weatherCode) {
            case 0:
                resID = R.mipmap.daily_forecast_sunny;
                break;
            case 1:
                resID = R.mipmap.daily_forecast_sunny_night;
                break;
            case 2:
                resID = R.mipmap.daily_forecast_sunny;
                break;
            case 3:
                resID = R.mipmap.daily_forecast_sunny_night;
                break;
            case 4:
                resID = R.mipmap.daily_forecast_cloudy;
                break;
            case 5:
                resID = R.mipmap.daily_forecast_cloudy;
                break;
            case 6:
                resID = R.mipmap.daily_forecast_cloudy_night;
                break;
            case 7:
                resID = R.mipmap.daily_forecast_cloudy;
                break;
            case 8:
                resID = R.mipmap.daily_forecast_cloudy_night;
                break;
            case 9:
                resID = R.mipmap.daily_forecast_overcast;
                break;
            case 10:
                resID = R.mipmap.daily_forecast_light_rain;
                break;
            case 11:
                resID = R.mipmap.daily_forecast_t_storm;
                break;
            case 12:
                resID = R.mipmap.daily_forecast_t_storm;
                break;
            case 13:
                resID = R.mipmap.daily_forecast_light_rain;
                break;
            case 14:
                resID = R.mipmap.daily_forecast_heavy_rain;
                break;
            case 15:
                resID = R.mipmap.daily_forecast_heavy_rain;
                break;
            case 16:
                resID = R.mipmap.daily_forecast_heavy_rain;
                break;
            case 17:
                resID = R.mipmap.daily_forecast_t_storm;
                break;
            case 18:
                resID = R.mipmap.daily_forecast_cloudy_night;
                break;
            case 19:
                resID = R.mipmap.daily_forecast_rain_snow;
                break;
            case 20:
                resID = R.mipmap.daily_forecast_rain_snow;
                break;
            case 21:
                resID = R.mipmap.daily_forecast_light_snow;
                break;
            case 22:
                resID = R.mipmap.daily_forecast_light_snow;
                break;
            case 23:
                resID = R.mipmap.daily_forecast_heavy_snow;
                break;
            case 24:
                resID = R.mipmap.daily_forecast_heavy_snow;
                break;
            case 25:
                resID = R.mipmap.daily_forecast_heavy_snow;
                break;
            case 26:
                resID = R.mipmap.daily_forecast_sandy;
                break;
            case 27:
                resID = R.mipmap.daily_forecast_sandy;
                break;
            case 28:
                resID = R.mipmap.daily_forecast_sandy;
                break;
            case 29:
                resID = R.mipmap.daily_forecast_sandy;
                break;
            case 30:
                resID = R.mipmap.daily_forecast_sandy;
                break;
            case 31:
                resID = R.mipmap.daily_forecast_sandy;
                break;
            case 32:
                resID = R.mipmap.daily_forecast_foggy;
                break;
            case 33:
                resID = R.mipmap.daily_forecast_foggy;
                break;
            case 34:
                resID = R.mipmap.daily_forecast_foggy;
                break;
            case 35:
                resID = R.mipmap.daily_forecast_foggy;
                break;
            case 36:
                resID = R.mipmap.daily_forecast_foggy;
                break;
            default:
                resID = R.mipmap.daily_forecast_sunny;
                break;
        }

        return resID;
    }


}
