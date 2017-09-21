package com.dongua.geather.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongua.geather.R;
import com.dongua.geather.ui.listener.OnRecyclerItemClick;

import java.util.ArrayList;
import java.util.List;

import static com.dongua.geather.utils.Constant.DATA_CITY;
import static com.dongua.geather.utils.Constant.DATA_REGION;
import static com.dongua.geather.utils.Constant.DATA_STATE;

/**
 * Created by dongua on 17-8-13.
 */

public class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.NamePickerHolder>{

    Context mContext;
    List<String> dataList = new ArrayList<>();

    public void setOnRecyclerItemClick(OnRecyclerItemClick onRecyclerItemClick) {
        this.onRecyclerItemClick = onRecyclerItemClick;
    }

    OnRecyclerItemClick onRecyclerItemClick;

    public boolean isLoop() {
        return isLoop;
    }

    private boolean isLoop = false;


    public void setLoop(boolean loop) {
        isLoop = loop;
    }

    public PickerAdapter(Context context) {
        super();
        mContext = context;
    }

    public void setData(List<String> data) {
        dataList = data;
    }

    public void dataChanged(int id, int type) {
        switch (type) {
            case DATA_STATE:
                break;
            case DATA_CITY:
                break;
            case DATA_REGION:
                break;
            default:
                break;
        }
    }


    public int getRealCount() {
        return dataList.size();
    }

    @Override
    public NamePickerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        NamePickerHolder holder = new NamePickerHolder(inflater.inflate(R.layout.pickerview_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NamePickerHolder holder, int position) {
        if (isLoop) {
            int realPos = position % dataList.size();
            holder.tv_name.setText(dataList.get(realPos));
        } else {
            holder.tv_name.setText(dataList.get(position));

        }
        holder.setItemClick(onRecyclerItemClick);
    }

    @Override
    public int getItemCount() {
        return isLoop ? Integer.MAX_VALUE : dataList.size();
    }



//    public int getId(int curCenterPosition) {
//        return dataList.get(curCenterPosition);
//    }

    class NamePickerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_name;
        View header;
        View footer;

        public void setItemClick(OnRecyclerItemClick itemClick) {
            this.itemClick = itemClick;
        }

        OnRecyclerItemClick itemClick;
        public NamePickerHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.name);
            tv_name.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            itemClick.onItemClikc(v,getAdapterPosition());
        }
    }
}
