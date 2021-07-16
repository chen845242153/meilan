package com.meilancycling.mema.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.LapBean;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.List;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class RecordLapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LapBean> mList;
    private Context mContext;

    public RecordLapAdapter(Context context, List<LapBean> list) {
        this.mList = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_record_lap, parent, false);
        return new RecyclerHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LapBean lapBean = mList.get(position);
        RecyclerHolder recyclerHolder = (RecyclerHolder) holder;
        recyclerHolder.turns.setText(String.valueOf(position + 1));
        recyclerHolder.time.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(lapBean.getActivityTime()));
        UnitBean distanceSetting = UnitConversionUtil.getUnitConversionUtil().distanceSetting(mContext, lapBean.getDistance());
        recyclerHolder.distance.setText(distanceSetting.getValue() + distanceSetting.getUnit());
        UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(mContext, (double) lapBean.getAvgSpeed() / 10);
        recyclerHolder.speed.setText(speedSetting.getValue() + speedSetting.getUnit());
        recyclerHolder.root.setOnClickListener(v -> mLapItemClickListener.clickItem());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView turns;
        TextView time;
        TextView distance;
        TextView speed;
        LinearLayout root;

        RecyclerHolder(View itemView) {
            super(itemView);
            turns = itemView.findViewById(R.id.tv_record_lap_turns);
            time = itemView.findViewById(R.id.tv_record_lap_time);
            distance = itemView.findViewById(R.id.tv_record_lap_distance);
            speed = itemView.findViewById(R.id.tv_record_lap_speed);
            root = itemView.findViewById(R.id.ll_root);
        }
    }

    public interface LapItemClickListener {
        /**
         * 点击事件
         */
        void clickItem();
    }

    private LapItemClickListener mLapItemClickListener;

    public void setLapItemClickListener(LapItemClickListener lapItemClickListener) {
        mLapItemClickListener = lapItemClickListener;
    }
}
