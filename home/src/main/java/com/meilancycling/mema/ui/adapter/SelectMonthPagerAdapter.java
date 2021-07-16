package com.meilancycling.mema.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/6/9 4:43
 */
public class SelectMonthPagerAdapter extends RecyclerView.Adapter<SelectMonthPagerAdapter.MonthHolder> {
    private List<MotionSumResponse> mList = new ArrayList<>();
    private List<Long> startTimeList = new ArrayList<>();
    private List<Long> endTimeList = new ArrayList<>();
    private Context mContext;

    public SelectMonthPagerAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(int position, MotionSumResponse motionSumResponse, long startTime, long endTime) {
        mList.add(position, motionSumResponse);
        startTimeList.add(position, startTime);
        endTimeList.add(position, endTime);
    }

    public void setData(MotionSumResponse motionSumResponse, long startTime, long endTime) {
        mList.add(motionSumResponse);
        startTimeList.add(startTime);
        endTimeList.add(endTime);
    }

    public void clearData() {
        mList.clear();
        startTimeList.clear();
        endTimeList.clear();
    }

    public long getStartTime(int position) {
        return startTimeList.get(position);
    }

    public long getEndTime(int position) {
        return endTimeList.get(position);
    }

    @NonNull
    @Override
    public MonthHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_month, parent, false);
        return new MonthHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthHolder holder, int position) {
        MotionSumResponse motionSumResponse = mList.get(position);
        if (motionSumResponse != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            holder.itemSelect.setLayoutManager(linearLayoutManager);
            ItemCommonDataAdapter mItemCommonDataAdapter = new ItemCommonDataAdapter(mContext, motionSumResponse.getMotionList());
            holder.itemSelect.setAdapter(mItemCommonDataAdapter);
            if (motionSumResponse.getMotionList() == null || motionSumResponse.getMotionList().size() == 0) {
                holder.ivEmpty.setVisibility(View.VISIBLE);
                holder.tvEmpty.setVisibility(View.VISIBLE);
            } else {
                holder.ivEmpty.setVisibility(View.GONE);
                holder.tvEmpty.setVisibility(View.GONE);
            }
        } else {
            holder.ivEmpty.setVisibility(View.VISIBLE);
            holder.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class MonthHolder extends RecyclerView.ViewHolder {
        RecyclerView itemSelect;
        ImageView ivEmpty;
        TextView tvEmpty;

        MonthHolder(View itemView) {
            super(itemView);
            itemSelect = itemView.findViewById(R.id.rv_item_select);
            ivEmpty = itemView.findViewById(R.id.iv_empty);
            tvEmpty = itemView.findViewById(R.id.tv_empty);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return PagerAdapter.POSITION_NONE;
    }
}
