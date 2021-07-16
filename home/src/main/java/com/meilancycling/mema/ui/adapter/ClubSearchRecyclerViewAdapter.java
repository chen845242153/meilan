package com.meilancycling.mema.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.base.BaseViewHolder;
import com.meilancycling.mema.ui.club.model.ClubSearchModel;
import com.meilancycling.mema.ui.club.view.ClubSearchView;

import java.util.List;

public class ClubSearchRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<ClubSearchModel> mDataList;

    public void setData(List<ClubSearchModel> items) {
        mDataList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(new ClubSearchView(parent.getContext()));
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
