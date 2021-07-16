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
import com.meilancycling.mema.network.bean.response.NewsRecommendResponse;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.GlideUtils;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class NewsBannerAdapter extends BannerAdapter<NewsRecommendResponse, NewsBannerAdapter.BannerViewHolder> {
    private Context mContext;

    public NewsBannerAdapter(List<NewsRecommendResponse> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        return new BannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.banner_item, parent, false));
    }

    @Override
    public void onBindView(BannerViewHolder holder, NewsRecommendResponse data, int position, int size) {
        if (AppUtils.isChinese()) {
            holder.title.setText(data.getCnTitle());
            GlideUtils.loadImageRounder(data.getCnCover(), mContext, holder.imageView, AppUtils.dipToPx(mContext, 5f));
        } else {
            holder.title.setText(data.getEnTitle());
            GlideUtils.loadImageRounder(data.getEnCover(), mContext, holder.imageView, AppUtils.dipToPx(mContext, 5f));
        }
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
        }
    }
}
