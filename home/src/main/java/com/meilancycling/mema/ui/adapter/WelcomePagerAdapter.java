package com.meilancycling.mema.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.meilancycling.mema.R;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/6/9 4:43
 */
public class WelcomePagerAdapter extends RecyclerView.Adapter<WelcomePagerAdapter.WelcomeHolder> {
    private Context mContext;

    @NonNull
    @Override
    public WelcomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_welcome, parent, false);
        return new WelcomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WelcomeHolder holder, int position) {
        switch (position) {
            case 0:
                holder.mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_1));
                holder.top.setText(mContext.getString(R.string.welcome_1_1));
                holder.center.setText(mContext.getString(R.string.welcome_1_2));
                holder.bottom.setText(mContext.getString(R.string.welcome_1_3));
                break;
            case 1:
                holder.mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_2));
                holder.top.setText(mContext.getString(R.string.welcome_2_1));
                holder.center.setText("");
                holder.bottom.setText("");
                break;
            case 2:
                holder.mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_3));
                holder.top.setText(mContext.getString(R.string.welcome_3_1));
                holder.center.setText(mContext.getString(R.string.welcome_3_2));
                holder.bottom.setText("");
                break;
            case 3:
                holder.mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_4));
                holder.top.setText(mContext.getString(R.string.welcome_4_1));
                holder.center.setText("");
                holder.bottom.setText("");
                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    static class WelcomeHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView top;
        TextView center;
        TextView bottom;

        WelcomeHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_image);
            bottom = itemView.findViewById(R.id.tv_bottom);
            center = itemView.findViewById(R.id.tv_center);
            top = itemView.findViewById(R.id.tv_top);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return PagerAdapter.POSITION_NONE;
    }
}
