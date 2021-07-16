package com.meilancycling.mema.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.R;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 3/26/21 11:35 AM
 */
public class LapAdapter extends RecyclerView.Adapter<LapAdapter.ViewHolder> {

    private Context mContext;
    private int lapNumber;

    public LapAdapter(int number) {
        lapNumber = number;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lap, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int number = position + 1;
        holder.tvLap.setText("" + number);
    }

    @Override
    public int getItemCount() {
        return lapNumber;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLap;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLap = itemView.findViewById(R.id.tv_lap);
        }
    }
}
