package com.meilancycling.mema.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.meilancycling.mema.ui.device.bean.ScanSensorBean;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 传感器搜索
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class SensorSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ScanSensorBean> sensorList;
    private int LEFT = 0;
    private int RIGHT = 1;
    private List<Integer> selectList;

    public void clearData() {
        sensorList.clear();
        selectList.clear();
        notifyDataSetChanged();
    }

    public SensorSearchAdapter(Context context) {
        this.mContext = context;
        selectList = new ArrayList<>();
    }

    public void setSensorData(List<ScanSensorBean> list) {
        sensorList = list;
        notifyDataSetChanged();
    }

    public void updateSelect(int sensorKey) {
        int position = -1;
        for (int i = 0; i < selectList.size(); i++) {
            if (sensorKey == selectList.get(i)) {
                position = i;
                break;
            }
        }
        if (position == -1) {
            selectList.add(sensorKey);
        } else {
            selectList.remove(position);
        }
        notifyDataSetChanged();
    }

    public List<Integer> getSelectList() {
        return selectList;
    }

    @Override
    public int getItemViewType(int position) {
        int typeNumber = 2;
        if (position % typeNumber == 0) {
            return LEFT;
        } else {
            return RIGHT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LEFT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_sensor_search_left, parent, false);
            return new SensorSearchLeftHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_sensor_search_right, parent, false);
            return new SensorSearchRightHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ScanSensorBean scanSensorBean = sensorList.get(position);
        if (getItemViewType(position) == LEFT) {
            leftUi((SensorSearchLeftHolder) holder, scanSensorBean);
        } else {
            rightUi((SensorSearchRightHolder) holder, scanSensorBean);
        }
        holder.itemView.setOnClickListener(v -> {
            if (mSensorSearchItemClick != null) {
                mSensorSearchItemClick.itemClick(scanSensorBean.getSensorKey());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void leftUi(SensorSearchLeftHolder holder, ScanSensorBean scanSensorBean) {
        if (scanSensorBean.isBle()) {
            holder.typeBle.setVisibility(View.VISIBLE);
            holder.typeAnt.setVisibility(View.GONE);
        } else {
            holder.typeBle.setVisibility(View.GONE);
            holder.typeAnt.setVisibility(View.VISIBLE);
        }

        boolean isSelect = false;
        for (Integer integer : selectList) {
            if (scanSensorBean.getSensorKey() == integer) {
                isSelect = true;
                break;
            }
        }
        if (isSelect) {
            holder.select.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_select));
        } else {
            holder.select.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_normal));
        }
        switch (scanSensorBean.getSensorType()) {
            case 1:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                UnitBean wheelBean = UnitConversionUtil.getUnitConversionUtil().wheelSetting(mContext, (double) scanSensorBean.getValue() / 100);
                holder.wheel.setVisibility(View.VISIBLE);
                holder.wheel.setText(wheelBean.getValue() + wheelBean.getUnit());
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_speed));
                break;
            case 2:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                holder.wheel.setVisibility(View.GONE);
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_cadence));
                break;
            case 3:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                holder.wheel.setVisibility(View.GONE);
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_heart));
                break;
            case 4:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                holder.wheel.setVisibility(View.GONE);
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_power));
                break;
            case 5:
                holder.speed.setVisibility(View.VISIBLE);
                holder.cadence.setVisibility(View.VISIBLE);
                holder.wheel.setVisibility(View.VISIBLE);
                holder.sensor.setVisibility(View.GONE);
                wheelBean = UnitConversionUtil.getUnitConversionUtil().wheelSetting(mContext, (double) scanSensorBean.getValue() / 100);
                holder.wheel.setVisibility(View.VISIBLE);
                holder.wheel.setText(wheelBean.getValue() + wheelBean.getUnit());
                break;
            case 6:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                holder.wheel.setVisibility(View.GONE);
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_light));
                break;
            default:
        }
        holder.name.setText(scanSensorBean.getName());
    }

    @SuppressLint("SetTextI18n")
    private void rightUi(SensorSearchRightHolder holder, ScanSensorBean scanSensorBean) {
        if (scanSensorBean.isBle()) {
            holder.typeBle.setVisibility(View.VISIBLE);
            holder.typeAnt.setVisibility(View.GONE);
        } else {
            holder.typeBle.setVisibility(View.GONE);
            holder.typeAnt.setVisibility(View.VISIBLE);
        }

        boolean isSelect = false;
        for (Integer integer : selectList) {
            if (scanSensorBean.getSensorKey() == integer) {
                isSelect = true;
                break;
            }
        }
        if (isSelect) {
            holder.select.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_select));
        } else {
            holder.select.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_normal));
        }
        switch (scanSensorBean.getSensorType()) {
            case 1:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                UnitBean wheelBean = UnitConversionUtil.getUnitConversionUtil().wheelSetting(mContext, (double) scanSensorBean.getValue() / 100);
                holder.wheel.setVisibility(View.VISIBLE);
                holder.wheel.setText(wheelBean.getValue() + wheelBean.getUnit());
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_speed));
                break;
            case 2:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                holder.wheel.setVisibility(View.GONE);
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_cadence));
                break;
            case 3:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                holder.wheel.setVisibility(View.GONE);
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_heart));
                break;
            case 4:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                holder.wheel.setVisibility(View.GONE);
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_power));
                break;
            case 5:
                holder.speed.setVisibility(View.VISIBLE);
                holder.cadence.setVisibility(View.VISIBLE);
                holder.wheel.setVisibility(View.VISIBLE);
                holder.sensor.setVisibility(View.GONE);
                wheelBean = UnitConversionUtil.getUnitConversionUtil().wheelSetting(mContext, (double) scanSensorBean.getValue() / 100);
                holder.wheel.setVisibility(View.VISIBLE);
                holder.wheel.setText(wheelBean.getValue() + wheelBean.getUnit());
                break;
            case 6:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                holder.wheel.setVisibility(View.GONE);
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_light));
                break;
            default:
        }
        holder.name.setText(scanSensorBean.getName());

    }

    @Override
    public int getItemCount() {
        return sensorList == null ? 0 : sensorList.size();
    }

    public static class SensorSearchLeftHolder extends RecyclerView.ViewHolder {
        ImageView typeBle;
        ImageView typeAnt;
        ImageView select;
        ImageView speed;
        ImageView cadence;
        ImageView sensor;
        TextView wheel;
        TextView name;

        public SensorSearchLeftHolder(@NonNull View itemView) {
            super(itemView);
            typeBle = itemView.findViewById(R.id.iv_type_ble);
            typeAnt = itemView.findViewById(R.id.iv_type_ant);
            select = itemView.findViewById(R.id.iv_select);
            speed = itemView.findViewById(R.id.iv_speed);
            cadence = itemView.findViewById(R.id.iv_cadence);
            sensor = itemView.findViewById(R.id.iv_sensor);
            wheel = itemView.findViewById(R.id.tv_wheel);
            name = itemView.findViewById(R.id.tv_name);
        }
    }

    public static class SensorSearchRightHolder extends RecyclerView.ViewHolder {
        ImageView typeBle;
        ImageView typeAnt;
        ImageView select;
        ImageView speed;
        ImageView cadence;
        ImageView sensor;
        TextView wheel;
        TextView name;

        public SensorSearchRightHolder(@NonNull View itemView) {
            super(itemView);
            typeBle = itemView.findViewById(R.id.iv_type_ble);
            typeAnt = itemView.findViewById(R.id.iv_type_ant);
            select = itemView.findViewById(R.id.iv_select);
            speed = itemView.findViewById(R.id.iv_speed);
            cadence = itemView.findViewById(R.id.iv_cadence);
            sensor = itemView.findViewById(R.id.iv_sensor);
            wheel = itemView.findViewById(R.id.tv_wheel);
            name = itemView.findViewById(R.id.tv_name);
        }
    }

    public interface SensorSearchItemClick {
        /**
         * 条目点击
         *
         * @param sensorKey 传感器类型
         */
        void itemClick(int sensorKey);
    }

    private SensorSearchItemClick mSensorSearchItemClick;


    public void setSensorSearchItemClick(SensorSearchItemClick sensorSearchItemClick) {
        mSensorSearchItemClick = sensorSearchItemClick;
    }
}
