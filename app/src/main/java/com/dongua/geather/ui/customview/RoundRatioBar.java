package com.dongua.geather.ui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.dongua.geather.R;
import com.dongua.geather.utils.Utils;

/**
 * Created by dongua on 17-9-2.
 */

public class RoundRatioBar extends View {

    String TAG = "RoundRatioBar";
    private Context mContext;

    private double percentValue;
    private String desc_text = "空气质量指数";
    private String value_text = "34";
    private String abbr_text = "AQI";

    private int desc_size;
    private int value_size;
    private int abbr_size;


    private Paint mCirclePaint;
    private Paint mTextPaint;

    private int circleWidth = 10;


    private int angleRange = 240;
    private int startAngle = 150;
    private int endAngle = 390;
    private int valueAngle = 180;

    //视图的宽和高
    private int mWidth;
    private int mHeight;
    //圆的半径
    private int mRadius = 140;

    public RoundRatioBar(Context context) {
        super(context);
        init(context);

    }

    public RoundRatioBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public RoundRatioBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public RoundRatioBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    void init(Context context) {
        Log.i(TAG, "init: ");
        mContext = context;
        mCirclePaint = new Paint();
        mTextPaint = new Paint();

        //初始默认值
        desc_size = Utils.sp2px(mContext, 14);
        value_size = Utils.sp2px(mContext, 20);
        abbr_size = Utils.sp2px(mContext, 11);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //获取测量大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int a = getPaddingTop();
        ViewGroup.LayoutParams lp = getLayoutParams();
        //如果为确定大小值，则圆的半径为宽度/2
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
            mHeight = heightSize;
            mRadius = Math.min(mWidth, mHeight) / 2 - 10;

            //如果设置了值 则字体相应增大
            desc_size = Utils.sp2px(mContext, 14f * mRadius / 140);
            value_size = Utils.sp2px(mContext, 20f * mRadius / 140);
            abbr_size = Utils.sp2px(mContext, 11f * mRadius / 140);

        }

        //如果为wrap_content 那么View大小为圆的半径大小*2
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            mWidth = (mRadius + 10) * 2;
            mHeight = (mRadius + 10) * 2;
        }

        //根据当前值计算角度
        valueAngle = Double.valueOf(percentValue * angleRange + startAngle).intValue();


        //设置视图的大小
        setMeasuredDimension(mWidth, mHeight);
    }


    Rect textBound = new Rect();

    @SuppressWarnings("ResourceType")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(getResources().getColor(R.color.gainsboro));
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(circleWidth);
        int r = mRadius;
        //计算centerX和centerY的位置要考虑好canvas坐标系和View的坐标系转换
        int centerX = (getRight() - getLeft()) / 2;
        int centerY = (getBottom() - getTop()) / 2;
        RectF rectF = new RectF(centerX - r, centerY - r, centerX + r, centerY + r);
        canvas.drawArc(rectF, startAngle, endAngle - startAngle, false, mCirclePaint);


        mCirclePaint.reset();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(getResources().getColor(R.color.limegreen));
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(circleWidth);
        canvas.drawArc(rectF, startAngle, valueAngle - startAngle, false, mCirclePaint);


        mTextPaint.reset();
        mTextPaint.setTextSize(value_size);
        mTextPaint.getTextBounds(value_text, 0, value_text.length(), textBound);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(getResources().getColor(R.color.limegreen));
        canvas.drawText(value_text, centerX - textBound.width() / 2
                , centerY
                , mTextPaint);


        mTextPaint.reset();
        mTextPaint.setTextSize(abbr_size);
        mTextPaint.getTextBounds(abbr_text, 0, abbr_text.length(), textBound);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(R.color.gray);
        canvas.drawText(abbr_text, centerX - textBound.width() / 2
                , centerY + textBound.height() * 2
                , mTextPaint);


        mTextPaint.setTextSize(desc_size);
        mTextPaint.getTextBounds(desc_text, 0, desc_text.length(), textBound);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(R.color.gray);
        canvas.drawText(desc_text, centerX - textBound.width() / 2
                , centerY + mRadius / 2 + textBound.height()
                , mTextPaint);


    }


    public void setDesc_text(String desc_text) {
        this.desc_text = desc_text;
    }

    public void setValue_text(String value_text) {
        this.value_text = value_text;
    }

    public void setAbbr_text(String abbr_text) {
        this.abbr_text = abbr_text;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public void setEndAngle(int endAngle) {
        this.endAngle = endAngle;
    }

    public void setDesc_size(int desc_size) {
        this.desc_size = desc_size;
    }

    public void setValue_size(int value_size) {
        this.value_size = value_size;
    }

    public void setAbbr_size(int abbr_size) {
        this.abbr_size = abbr_size;
    }

    public void setValueAngle(int valueAngle) {
        this.valueAngle = valueAngle;
    }

    public void setPercentValue(double percentValue) {
        this.percentValue = percentValue;
    }
}
