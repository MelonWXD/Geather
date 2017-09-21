package com.dongua.geather.ui.listener;

/**
 * Created by dongua on 17-8-3.
 */

public interface OnNetworkListener{
    void successed(String resp);
    void successed(Object bean);
    void failed();
}
