package com.meilancycling.mema.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.ui.device.bean.SensorValueBean;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.List;


/**
 * @Description: 传感器列表
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class SensorListAdapter extends RecyclerView.Adapter<SensorListAdapter.SensorHolder> {
    private Context mContext;
    private List<SensorValueBean> sensorList;

    public SensorListAdapter(Context context) {
        this.mContext = context;
    }

    public void setSensorData(List<SensorValueBean> list) {
        sensorList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sensor_list, parent, false);
        return new SensorHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SensorHolder holder, int position) {
        SensorValueBean sensorValueBean = sensorList.get(position);
        holder.tvName.setText(sensorValueBean.getSensorName());
        if (sensorValueBean.isConnect()) {
            holder.ivConnected.setVisibility(View.VISIBLE);
            holder.ivDisconnect.setVisibility(View.GONE);
        } else {
            holder.ivConnected.setVisibility(View.GONE);
            holder.ivDisconnect.setVisibility(View.VISIBLE);
        }
        holder.tvSymbol.setVisibility(View.GONE);
        holder.ivItem2.setVisibility(View.GONE);
        holder.tvItemType2.setVisibility(View.GONE);

        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().wheelSetting(mContext, (double) sensorValueBean.getWheelValue() / 100);
        holder.tvWheel.setText("ws:" + unitBean.getValue() + unitBean.getUnit());
        switch (sensorValueBean.getType()) {
            case 1:
                if (sensorValueBean.isConnect()) {
                    holder.ivItem1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_speed));
                } else {
                    holder.ivItem1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_speed_normal));
                }
                holder.tvItemType1.setText(R.string.speed);
                holder.tvWheel.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.tvWheel.setVisibility(View.GONE);
                if (sensorValueBean.isConnect()) {
                    holder.ivItem1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_cadence));
                } else {
                    holder.ivItem1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_cadence_normal));
                }
                holder.tvItemType1.setText(R.string.cadence);
                break;
            case 3:
                holder.tvWheel.setVisibility(View.GONE);
                if (sensorValueBean.isConnect()) {
                    holder.ivItem1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_heart));
                } else {
                    holder.ivItem1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_heart_normal));
                }
                holder.tvItemType1.setText(R.string.heart_rate);
                break;
            case 4:
                holder.tvWheel.setVisibility(View.GONE);
                if (sensorValueBean.isConnect()) {
                    holder.ivItem1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_power));
                } else {
                    holder.ivItem1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_power_normal));
                }
                holder.tvItemType1.setText(R.string.power);
                break;
            case 5:
                holder.tvSymbol.setVisibility(View.VISIBLE);
                holder.ivItem2.setVisibility(View.VISIBLE);
                holder.tvItemType2.setVisibility(View.VISIBLE);
                if (sensorValueBean.isConnect()) {
                    holder.ivItem1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_speed));
                    holder.ivItem2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_cadence));
                } else {
                    holder.ivItem1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_speed_normal));
                    holder.ivItem2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_cadence_normal));
                }
                holder.tvItemType1.setText(R.string.speed);
                holder.tvItemType2.setText(R.string.cadence);
                holder.tvWheel.setVisibility(View.VISIBLE);
                break;
            case 6:
                holder.tvWheel.setVisibility(View.GONE);
                if (sensorValueBean.isConnect()) {
                    holder.ivItem1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_light));
                } else {
                    holder.ivItem1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_light_normal));
                }
                holder.tvItemType1.setText(R.string.taillight);
                break;
            default:
        }
        holder.itemView.setOnClickListener(v -> {
            if (mSensorItemClickListener != null) {
                mSensorItemClickListener.sensorItemClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return sensorList == null ? 0 : sensorList.size();
    }

    public static class SensorHolder extends RecyclerView.ViewHolder {
        ImageView ivItem1;
        TextView tvItemType1;
        TextView tvSymbol;
        ImageView ivItem2;
        TextView tvItemType2;
        TextView tvName;
        TextView tvWheel;
        ImageView ivConnected;
        ImageView ivDisconnect;

        public SensorHolder(@NonNull View itemView) {
            super(itemView);
            ivItem1 = itemView.findViewById(R.id.iv_item1);
            tvItemType1 = itemView.findViewById(R.id.tv_type_item1);
            tvSymbol = itemView.findViewById(R.id.tv_symbol);
            ivItem2 = itemView.findViewById(R.id.iv_item2);
            tvItemType2 = itemView.findViewById(R.id.tv_type_item2);
            tvName = itemView.findViewById(R.id.tv_name);
            tvWheel = itemView.findViewById(R.id.tv_wheel);
            ivConnected = itemView.findViewById(R.id.iv_connected);
            ivDisconnect = itemView.findViewById(R.id.iv_disconnect);
        }
    }

    public interface SensorItemClickListener {
        /**
         * 条目点击事件
         *
         * @param position
         */
        void sensorItemClick(int position);
    }

    private SensorItemClickListener mSensorItemClickListener;

    public void setSensorItemClickListener(SensorItemClickListener sensorItemClickListener) {
        mSensorItemClickListener = sensorItemClickListener;
    }
}
