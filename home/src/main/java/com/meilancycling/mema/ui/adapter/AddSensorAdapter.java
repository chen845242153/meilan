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
import com.meilancycling.mema.db.SensorEntity;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.ui.sensor.SensorScanBean;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 添加传感器
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class AddSensorAdapter extends RecyclerView.Adapter<AddSensorAdapter.AddSensorHolder> {
    private List<SensorEntity> sensorEntityList;
    private Context mContext;
    private List<SensorScanBean> sensorScanList;

    public void setSensorList(List<SensorEntity> sensorList) {
        sensorEntityList = sensorList;
    }

    /**
     * 添加sensor
     */
    public void addSensor(SensorScanBean sensorScanBean) {
        boolean isAdd = false;
        if (sensorEntityList != null && sensorEntityList.size() > 0) {
            for (SensorEntity sensorEntity : sensorEntityList) {
                if (sensorEntity.getMacAddress().equals(sensorScanBean.getMacAddress())) {
                    isAdd = true;
                    break;
                }
            }
        }
        if (!isAdd) {
            if (sensorScanList == null) {
                sensorScanList = new ArrayList<>();
            }
            for (SensorScanBean scanBean : sensorScanList) {
                if (scanBean.getMacAddress().equals(sensorScanBean.getMacAddress())) {
                    isAdd = true;
                    break;
                }
            }
            if (!isAdd) {
                sensorScanList.add(sensorScanBean);
                notifyDataSetChanged();
            }
        }
    }


    /**
     * 清空列表
     */
    public void clearSensor() {
        if (sensorScanList != null) {
            sensorScanList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 获取选择的设备
     */
    public List<SensorScanBean> getSelectedDevice() {
        List<SensorScanBean> dataList = new ArrayList<>();
        if (sensorScanList != null) {
            for (SensorScanBean sensorScanBean : sensorScanList) {
                if (sensorScanBean.getIsCheck() == 1) {
                    dataList.add(sensorScanBean);
                }
            }
        }
        return dataList;
    }

    @NonNull
    @Override
    public AddSensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_sensor, parent, false);
        return new AddSensorHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AddSensorHolder holder, int position) {
        SensorScanBean sensorScanBean = sensorScanList.get(position);
        if (sensorScanBean.getIsCheck() == 1) {
            holder.select.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_select));
        } else {
            holder.select.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_normal));
        }
        switch (sensorScanBean.getSensorType()) {
            case Device.SENSOR_SPEED:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                UnitBean wheelBean = UnitConversionUtil.getUnitConversionUtil().wheelSetting(mContext, (double) sensorScanBean.getWheelValue() / 100);
                holder.wheel.setVisibility(View.VISIBLE);
                holder.wheel.setText(wheelBean.getValue() + wheelBean.getUnit());
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_speed));
                break;
            case Device.SENSOR_CADENCE:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                holder.wheel.setVisibility(View.GONE);
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_cadence));
                break;
            case Device.SENSOR_HEART_RATE:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                holder.wheel.setVisibility(View.GONE);
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_heart));
                break;
            case Device.SENSOR_POWER:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                holder.wheel.setVisibility(View.GONE);
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_power));
                break;
            case Device.SENSOR_SPEED_CADENCE:
                holder.speed.setVisibility(View.VISIBLE);
                holder.cadence.setVisibility(View.VISIBLE);
                holder.wheel.setVisibility(View.VISIBLE);
                holder.sensor.setVisibility(View.GONE);
                wheelBean = UnitConversionUtil.getUnitConversionUtil().wheelSetting(mContext, (double) sensorScanBean.getWheelValue() / 100);
                holder.wheel.setVisibility(View.VISIBLE);
                holder.wheel.setText(wheelBean.getValue() + wheelBean.getUnit());
                break;
            case Device.SENSOR_TAILLIGHT:
                holder.speed.setVisibility(View.GONE);
                holder.cadence.setVisibility(View.GONE);
                holder.wheel.setVisibility(View.GONE);
                holder.sensor.setVisibility(View.VISIBLE);
                holder.sensor.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sensor_light));
                break;
            default:
        }
        holder.name.setText(sensorScanBean.getSensorName());
        holder.itemView.setOnClickListener(v -> {
            SensorScanBean sensorScanBean1 = sensorScanList.get(position);
            if (sensorScanBean1.getIsCheck() == 0) {
                sensorScanBean1.setIsCheck(1);
            } else {
                sensorScanBean1.setIsCheck(0);
            }
            notifyDataSetChanged();
        });
    }


    @Override
    public int getItemCount() {
        return sensorScanList == null ? 0 : sensorScanList.size();
    }

    public static class AddSensorHolder extends RecyclerView.ViewHolder {
        ImageView select;
        ImageView speed;
        ImageView cadence;
        ImageView sensor;
        TextView wheel;
        TextView name;

        public AddSensorHolder(@NonNull View itemView) {
            super(itemView);
            select = itemView.findViewById(R.id.iv_select);
            speed = itemView.findViewById(R.id.iv_speed);
            cadence = itemView.findViewById(R.id.iv_cadence);
            sensor = itemView.findViewById(R.id.iv_sensor);
            wheel = itemView.findViewById(R.id.tv_wheel);
            name = itemView.findViewById(R.id.tv_name);
        }
    }
}
