package com.dongua.geather.ui.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongua.geather.ui.adapter.PickerAdapter;

/**
 * Created by dongua on 17-8-12.
 */

public class PickerView extends RecyclerView {

    private int curCenterPosition;
    private int realDataSize = 0;

    private boolean isFirst = true;

    private boolean isLoop = false;
    //防止不断滑动  在滑动时设置为false  smooth2center时设置为true
    private boolean isScrolling2Center = false;

    private PickerAdapter mAdapter;

    public void setDataType(int dataType) {
        this.mDataType = dataType;
    }

    private int mDataType = -1;
//    private OnPosChangedListener mListener;

//

    public PickerView(Context context) {
        super(context);
    }

    public PickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PickerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {


        super.onScrollChanged(l, t, oldl, oldt);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            float h1 = child.getHeight() * 0.5f;
            float y1 = child.getY() + h1;
            float h2 = getHeight() * 0.5f;
            float abs = Math.abs(y1 - h2);
            float scale = 1 - abs * 0.001f;


            RelativeLayout rl = (RelativeLayout) child;
            TextView tv = (TextView) rl.getChildAt(0);
            String s = tv.getText().toString();

            ViewCompat.setScaleX(child, scale);
            ViewCompat.setScaleY(child, scale);
        }
        isScrolling2Center = false;

    }


    int oldPos = -1;

    @Override
    public void onScrollStateChanged(int state) {

        super.onScrollStateChanged(state);

        if (state == 0 && !isScrolling2Center) {
            isScrolling2Center = true;//防止无脑滑动
            View centerView = findViewAtCenter();
            smoothScrollToView(centerView);
            LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
            curCenterPosition += manager.findFirstVisibleItemPosition();
            if (realDataSize > 0) {
                curCenterPosition %= realDataSize;
            }
//            LogUtil.I("curCenterPosition:" + curCenterPosition);

//            if (curCenterPosition != oldPos) {
//                mListener.onChanged(curCenterPosition, mDataType);
//                oldPos = curCenterPosition;
//            }
        }


    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);


        if (!isLoop) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
            if (layoutManager == null)
                return;
//                setLayoutManager(new LinearLayoutManager(getContext()));
            if (layoutManager.canScrollHorizontally())
                setPadding(getWidth() / 2, 0, getWidth() / 2, 0);
            else if (layoutManager.canScrollVertically())
                setPadding(0, getHeight() / 2, 0, getHeight() / 2);
            setClipToPadding(false);
            setClipChildren(false);
            if (getChildCount() > 0) {
                if (isFirst) {
                    smoothScrollToView(getChildAt(0));
                    isFirst = false;
                } else {
                    smoothScrollToView(findViewAtCenter());
                }
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof PickerAdapter) {
            mAdapter = (PickerAdapter) adapter;
            isLoop = mAdapter.isLoop();
            realDataSize = mAdapter.getRealCount();
            if (isLoop) {
                scrollToPosition(realDataSize * 200);
            }
        }
    }

    public View findViewAt(int x, int y) {
        final int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            final View v = getChildAt(i);
            final int x0 = v.getLeft();
            final int y0 = v.getTop();
            final int x1 = v.getWidth() + x0;
            final int y1 = v.getHeight() + y0;
            if (x >= x0 && x <= x1 && y >= y0 && y <= y1) {
                curCenterPosition = i;
                RelativeLayout rl = (RelativeLayout) v;
                TextView tv = (TextView) rl.getChildAt(0);
                String s = tv.getText().toString();
                return v;
            }
        }
        return null;
    }


    public View findViewAtCenter() {
        if (getLayoutManager().canScrollVertically()) {
            return findViewAt(0, getHeight() / 2);
        } else if (getLayoutManager().canScrollHorizontally()) {
            return findViewAt(getWidth() / 2, 0);
        }
        return null;
    }


    public void smoothScrollToView(View v) {
        int distance = 0;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            if (getLayoutManager().canScrollVertically()) {
                final float y = v.getY() + v.getHeight() * 0.5f;
                final float halfHeight = getHeight() * 0.5f;
                distance = (int) (y - halfHeight);
            } else if (getLayoutManager().canScrollHorizontally()) {
                final float x = v.getX() + v.getWidth() * 0.5f;
                final float halfWidth = getWidth() * 0.5f;
                distance = (int) (x - halfWidth);
            }

        }
        smoothScrollBy(distance, distance);


//            onScrollChanged(0,0,0,0);//// FIXME: 17-8-22


    }

    public void scroll2Center() {
//        smoothScrollToView(findViewAtCenter());
        onScrollChanged(0, 0, 0, 0);
    }


    public void setLoop(boolean loop) {
        isLoop = loop;
    }


}
