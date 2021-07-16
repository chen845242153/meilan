package com.meilancycling.mema.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.network.bean.response.NewsListResponse;
import com.meilancycling.mema.ui.club.NewsDetailsActivity;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.GlideUtils;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListHolder> {
    private Context mContext;
    private List<NewsListResponse.NewsDetailsBean> newsList;

    public void setNewsListData(List<NewsListResponse.NewsDetailsBean> data) {
        newsList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        return new NewsListHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListHolder holder, int position) {
        NewsListResponse.NewsDetailsBean newsDetailsBean = newsList.get(position);
        holder.newsTime.setText(AppUtils.zeroTimeToString(newsDetailsBean.getCreateDate(), Config.TIME_RECORD));
        if (AppUtils.isChinese()) {
            holder.newsTitle.setText(newsDetailsBean.getCnTitle());
            GlideUtils.loadImageRounder(newsDetailsBean.getCnCover(), mContext, holder.newsImage, AppUtils.dipToPx(mContext, 5f));
        } else {
            holder.newsTitle.setText(newsDetailsBean.getEnTitle());
            GlideUtils.loadImageRounder(newsDetailsBean.getEnCover(), mContext, holder.newsImage, AppUtils.dipToPx(mContext, 5f));
        }
        holder.itemView.setOnClickListener(v -> NewsDetailsActivity.enterNewsDetails(newsList.get(position).getId(), mContext));
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    static class NewsListHolder extends RecyclerView.ViewHolder {
        private ImageView newsImage;
        private TextView newsTitle;
        private TextView newsTime;

        public NewsListHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.iv_news);
            newsTitle = itemView.findViewById(R.id.tv_title);
            newsTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
