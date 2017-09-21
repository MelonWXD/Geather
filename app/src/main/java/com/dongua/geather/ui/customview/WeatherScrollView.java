package com.dongua.geather.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.dongua.geather.utils.LogUtil;

/**
 * Created by dongua on 17-8-26.
 */

public class WeatherScrollView extends ScrollView {

    public void setScrollListener(OnScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    private OnScrollListener scrollListener;


    public WeatherScrollView(Context context) {
        super(context);
    }

    public WeatherScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeatherScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WeatherScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        LogUtil.I("onOverScrolled: scrollY="+scrollY);
    }

    public interface OnScrollListener {
        void onScrolled(int scrollY);
    }

}
