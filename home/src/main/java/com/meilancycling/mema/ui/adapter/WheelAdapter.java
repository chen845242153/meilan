package com.meilancycling.mema.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.R;
import com.meilancycling.mema.db.WheelEntity;
import com.meilancycling.mema.utils.ToastUtils;

import java.util.List;

/**
 * @Description:
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class WheelAdapter extends RecyclerView.Adapter<WheelAdapter.WheelHolder> {
    private Context mContext;
    private List<WheelEntity> dateList;

    public WheelAdapter(Context context) {
        this.mContext = context;
        dateList = MyApplication.mInstance.getDaoSession().getWheelEntityDao().queryBuilder().list();
    }

    @NonNull
    @Override
    public WheelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itme_wheel, parent, false);
        return new WheelHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull WheelHolder holder, int position) {
        WheelEntity wheelEntity = dateList.get(position);
        holder.tvLeft.setText(String.valueOf(wheelEntity.getWheelValue()));
        holder.tvRight.setText(wheelEntity.getNorm());
        holder.itemView.setOnClickListener(v -> {
            if (mWheelClickListener != null) {
                mWheelClickListener.wheelClick(dateList.get(position).getWheelValue(), dateList.get(position).getNorm());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateList == null ? 0 : dateList.size();
    }

    static class WheelHolder extends RecyclerView.ViewHolder {
        TextView tvLeft;
        TextView tvRight;

        WheelHolder(View itemView) {
            super(itemView);
            tvLeft = itemView.findViewById(R.id.tv_left);
            tvRight = itemView.findViewById(R.id.tv_right);
        }
    }

    public interface WheelClickListener {
        /**
         * 轮径点击
         *
         * @param value 轮径值
         * @param norm  标记
         */
        void wheelClick(int value, String norm);

    }

    private WheelClickListener mWheelClickListener;

    public void setWheelClickListener(WheelClickListener wheelClickListener) {
        mWheelClickListener = wheelClickListener;
    }
}
