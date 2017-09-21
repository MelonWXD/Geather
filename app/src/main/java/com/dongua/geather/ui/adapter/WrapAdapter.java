package com.dongua.geather.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dongua on 17-8-17.
 */

public class WrapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    enum ITEM_TYPE{
        HEADER,
        NORMAL,
        FOOTER
    }

    private PickerAdapter mAdapter;
    private View headerView;
    private View footerView;


    public WrapAdapter(PickerAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE.HEADER.ordinal())
            return new RecyclerView.ViewHolder(headerView){};
        else if(viewType == ITEM_TYPE.FOOTER.ordinal())
            return new RecyclerView.ViewHolder(footerView){};
        return mAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position ==0)
            return ;
        else if(position == getItemCount()-1)
            return ;
        mAdapter.onBindViewHolder((PickerAdapter.NamePickerHolder)holder,position);
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount()+2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position ==0)
            return ITEM_TYPE.HEADER.ordinal();
        else if(position == getItemCount()-1)
            return ITEM_TYPE.FOOTER.ordinal();
        return ITEM_TYPE.NORMAL.ordinal();
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }
}
