package com.meilancycling.mema.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.base.BaseViewHolder;
import com.meilancycling.mema.ui.club.model.CommentModel;
import com.meilancycling.mema.ui.club.view.CommentItemView;

import java.util.List;

/**
 * 评论列表
 */
public class CommentAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<CommentModel> mDataList;

    public void setData(List<CommentModel> items) {
        mDataList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(new CommentItemView(parent.getContext()));
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
