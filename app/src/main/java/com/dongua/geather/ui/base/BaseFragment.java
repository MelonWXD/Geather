package com.dongua.geather.ui.base;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dongua.geather.R;
import com.dongua.geather.ui.activity.WeatherFragment;
import com.dongua.geather.ui.customview.CommomDialog;
import com.dongua.geather.ui.listener.IWindowFocusChangedListener;
import com.dongua.geather.utils.UIUtils;

import static com.dongua.geather.R.style.dialog;

/**
 * Created by duoyi on 17-10-20.
 */

public abstract class BaseFragment extends Fragment implements IWindowFocusChangedListener, View.OnClickListener {

    private LinearLayout mRootLayout;

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
            default:
                ;
        }
    }

    protected  void showPopUpWindow(View view){
        View popupView = this.getActivity().getLayoutInflater().inflate(R.layout.popup_menu, null);
        Context c = this.getContext();
        TextView tv = (TextView) popupView.findViewById(R.id.add_city);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommomDialog(c, dialog, "您确定删除此信息？", new CommomDialog.OnCloseListener() {

                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            UIUtils.showToast(dialog.getContext(), "confirm");
                            dialog.dismiss();
                        }
                    }
                })
                        .setTitle("提示").show();
            }
        });

        PopupWindow window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window.setAnimationStyle(R.style.popup_window_anim);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        window.setFocusable(true);
        window.setOutsideTouchable(true);

        window.update();

        window.showAsDropDown(view, 0, 20);
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
