package com.dongua.geather.ui.fragment;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dongua.geather.R;
import com.dongua.geather.ui.customview.CommomDialog;
import com.dongua.geather.ui.listener.IWindowFocusChangedListener;
import com.dongua.geather.utils.LogUtil;
import com.dongua.geather.utils.UIUtils;

import static com.dongua.geather.R.style.dialog;

/**
 * Created by duoyi on 17-10-20.
 */

public abstract class BaseFragment extends Fragment implements IWindowFocusChangedListener, View.OnClickListener {

    private LinearLayout mRootLayout;
    PopupWindow mPopupWindow;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootLayout = (LinearLayout) inflater.inflate(R.layout.fragment_base, container, false);

        return mRootLayout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_toolbar_right:
                showPopUpWindow(v);
                break;
            case R.id.iv_toolbar_close:
                mPopupWindow.dismiss();
                break;
            case R.id.tv_toolbar_add:
//                showPopUpWindow(v);
                break;
            case R.id.tv_toolbar_share:
//                showPopUpWindow(v);
                break;
            case R.id.tv_toolbar_config:
//                showPopUpWindow(v);
                break;
            default:
                ;
        }
    }

    protected void showPopUpWindow(View view) {
        View popupView = this.getActivity().getLayoutInflater().inflate(R.layout.popup_menu, null);

        popupView.findViewById(R.id.iv_toolbar_close).setOnClickListener(this);
        popupView.findViewById(R.id.tv_toolbar_add).setOnClickListener(this);
        popupView.findViewById(R.id.tv_toolbar_share).setOnClickListener(this);
        popupView.findViewById(R.id.tv_toolbar_config).setOnClickListener(this);

        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setAnimationStyle(R.style.popup_window_anim);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);

        mPopupWindow.update();

        mPopupWindow.showAtLocation(mRootLayout, Gravity.TOP,0, 0);
    }

    public View setContentWithToolBar(View view) {
        if (mRootLayout == null) {
            return null;
        }
        mRootLayout.addView(view,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return mRootLayout;
    }
}
