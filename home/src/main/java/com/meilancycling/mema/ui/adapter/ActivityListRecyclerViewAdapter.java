package com.meilancycling.mema.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.base.BaseViewHolder;
import com.meilancycling.mema.ui.club.model.ActivityItemModel;
import com.meilancycling.mema.ui.club.view.ActivityItemView;

import java.util.List;

public class ActivityListRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<ActivityItemModel> mDataList;

    public void setData(List<ActivityItemModel> items) {
        mDataList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(new ActivityItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }
}
