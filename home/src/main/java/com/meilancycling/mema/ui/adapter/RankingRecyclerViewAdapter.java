package com.meilancycling.mema.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.base.BaseViewHolder;
import com.meilancycling.mema.ui.club.model.RankingItemModel;
import com.meilancycling.mema.ui.club.model.RankingTopModel;
import com.meilancycling.mema.ui.club.view.RankingItemView;
import com.meilancycling.mema.ui.club.view.RankingView;

import java.util.List;

public class RankingRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<RankingItemModel> mDataList;
    private final int VIEW_TYPE_CONTENT = 1;
    private final int VIEW_TYPE_TITLE = 2;
    private RankingTopModel mRankingTopModel;


    public void setData(RankingTopModel rankingTopModel, List<RankingItemModel> items) {
        mRankingTopModel = rankingTopModel;
        mDataList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_TITLE;
        }
        return VIEW_TYPE_CONTENT;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_CONTENT) {
            return new BaseViewHolder(new RankingItemView(parent.getContext()));
        } else if (viewType == VIEW_TYPE_TITLE) {
            return new BaseViewHolder(new RankingView(parent.getContext()));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (position == 0) {
            holder.bind(mRankingTopModel);
        } else {
            holder.bind(mDataList.get(position - 1));
        }

    }

    @Override
    public int getItemCount() {
        if (mRankingTopModel == null || mRankingTopModel.getUrl1() == null) {
            return 0;
        }
        return mDataList == null || mDataList.size() == 0 ? 1 : mDataList.size() + 1;
    }
}
