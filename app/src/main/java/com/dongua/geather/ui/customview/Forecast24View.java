package com.dongua.geather.ui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dongua.geather.R;
import com.dongua.geather.utils.Utils;

/**
 * Created by dongua on 17-8-27.
 */

public class Forecast24View extends View {

    private Context mContext;


    private int defWidth = 80;
    private int defHeight = 150;

    private int lowestTempture;
    private int highestTempture;
    private int[] myTemptures;
    private String[] myTime;

    private Paint linePaint;
    private Paint dashPaint;
    private Paint textPaint;
    private Paint circlePaint;
    private int textSp = 20;

    private float textWidth = 0;
    private float textHeight = 0;

    private int lowCirclePosition = 100;
    private int tempCircleRange = 60;

    private int lastTemptures = 999;
    private int nextTemptures = 999;

    private int paddingL = 0;
    private int paddingT = 0;
    private int paddingR = 0;
    private int paddingB = 0;




    public Forecast24View(Context context) {
        super(context);
        mContext = context;
        init();
    }


    public Forecast24View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        mContext = context;

    }

    public Forecast24View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        mContext = context;

    }

    public Forecast24View(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        mContext = context;

    }

    public void setLowestTempture(int lowestTempture) {
        this.lowestTempture = lowestTempture;
    }

    public void setHighestTempture(int highestTempture) {
        this.highestTempture = highestTempture;
    }

    public void setMyTemptures(int[] myTemptures) {
        this.myTemptures = myTemptures;
    }

    public void setMyTime(String[] myTime) {
        this.myTime = myTime;
    }




    public void setLastTemptures(int lastTemptures) {
        this.lastTemptures = lastTemptures;
    }

    public void setNextTemptures(int nextTemptures) {
        this.nextTemptures = nextTemptures;
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);


        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(getResources().getColor(R.color.royalblue));

        dashPaint = new Paint();
        dashPaint.setAntiAlias(true);
        dashPaint.setColor(getResources().getColor(R.color.gray));
        dashPaint.setStyle(Paint.Style.STROKE);
        dashPaint.setStrokeWidth(2);
        DashPathEffect pathEffect = new DashPathEffect(new float[]{8, 8}, 1);
        dashPaint.setPathEffect(pathEffect);

        circlePaint = new Paint();
        circlePaint.setStrokeWidth(3);
        circlePaint.setColor(getResources().getColor(R.color.aqua));
        circlePaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setColor(getResources().getColor(R.color.slategray));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(Utils.sp2px(mContext, textSp));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        paddingL = getPaddingLeft();
        paddingT = getPaddingTop();
        paddingR = getPaddingRight();
        paddingB = getPaddingBottom();
        Log.i("padding", "onMeasure: "+paddingL+","+paddingT+","+paddingR+","+paddingB);
        setMeasuredDimension(Utils.dp2px(mContext, defWidth * (myTemptures.length-1))+paddingL+paddingR,
                Utils.dp2px(mContext, defHeight)+paddingT+paddingB);

    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect r = new Rect();
        textPaint.getTextBounds(myTime[0], 0, myTime[0].length(), r);
        textWidth = r.width();
        textHeight = r.height();


        drawText(canvas, textPaint);

        drawCircle(canvas, circlePaint);
//        drawLine(canvas,linePaint);
    }

    private void drawText(Canvas canvas, Paint textPaint) {
        for (int i = 0; i < myTime.length; i++) {
            String text = myTime[i];
            canvas.drawText(text, paddingL+Utils.dp2px(mContext, defWidth * i), paddingT+Utils.dp2px(mContext, defHeight) - 20, textPaint);
        }
    }



    private void drawCircle(Canvas canvas, Paint circlePaint) {

        Path p = new Path();
        float startW=0,startH=0;
        float endW=0,endH=0;
        for (int i = 0; i < myTemptures.length; i++) {
            float temp = (float) myTemptures[i];

            float w = (float) (Utils.dp2px(mContext, defWidth * i)  )+paddingL;
            float h = tempture2Height(temp)+paddingT;

            canvas.drawCircle(w, h, 8, circlePaint);
            Log.i("wxx", "drawCircle: "+w+","+h);

            if(i == 0){
                p.moveTo(w,h);
                startW = w;
                startH = h;
            }else {
                p.lineTo(w,h);
            }

            endW = w;
            endH = h;

            if (i == 0 || i == myTemptures.length - 1) {
                canvas.drawLine(w, h + 8, w, Utils.dp2px(mContext, defHeight) - textHeight - 20, dashPaint);
            }
        }

        canvas.drawPath(p,linePaint);
        if(lastTemptures != 999){
            canvas.drawLine(startW,startH,startW-Utils.dp2px(mContext, defWidth), tempture2Height(lastTemptures),linePaint);
        }
        if(nextTemptures != 999){
            canvas.drawLine(endW,endH,endW+Utils.dp2px(mContext, defWidth), tempture2Height(nextTemptures),linePaint);

        }

        Log.i("wxd", "drawCircle: " + textWidth);
    }

    public float tempture2Height(float h){
        return ((h - lowestTempture) / (highestTempture - lowestTempture)) * (tempCircleRange) + lowCirclePosition;
    }


}
