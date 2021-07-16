package com.meilancycling.mema.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.clj.fastble.BleManager;
import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.ui.device.view.DevicePowerView;

import java.util.List;

/**
 * @Description: 设备管理
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class DeviceManageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<DeviceInformationEntity> deviceList;
    private int powerValue;
    private int deviceStatus;

    public DeviceManageAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.device_manager_top, parent, false);
            return new TopDeviceViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_device_manager, parent, false);
            return new DeviceManageViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DeviceInformationEntity deviceInformationEntity = deviceList.get(position);
        if (position == 0) {
            TopDeviceViewHolder viewHolder = (TopDeviceViewHolder) holder;
            updateFirstDevice(viewHolder, deviceInformationEntity);
        } else {
            DeviceManageViewHolder viewHolder = (DeviceManageViewHolder) holder;
            if(deviceInformationEntity.getProductNo()==null){
                return;
            }
            switch (deviceInformationEntity.getProductNo()) {
                case Device.PRODUCT_NO_M1:
                    viewHolder.device.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.device_m1));
                    viewHolder.deviceName.setText(mContext.getString(R.string.device_m1));
                    break;
                case Device.PRODUCT_NO_M2:
                    viewHolder.device.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.device_m2));
                    viewHolder.deviceName.setText(mContext.getString(R.string.device_m2));
                    break;
                case Device.PRODUCT_NO_M4:
                    viewHolder.device.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.device_m4));
                    viewHolder.deviceName.setText(mContext.getString(R.string.device_m4));
                    break;
                default:
            }
            switch (deviceInformationEntity.getDeviceUpdate()) {
                case Device.DEVICE_UPDATE_NORMAL:
                    viewHolder.deviceStatus.setText(mContext.getString(R.string.not_connected));
                    break;
                case Device.DEVICE_UPDATE:
                    viewHolder.deviceStatus.setText(mContext.getString(R.string.update_available));
                    break;
                case Device.DEVICE_UPDATE_UNDONE:
                    viewHolder.deviceStatus.setText(mContext.getString(R.string.upgrade_incomplete));
                    break;
                default:
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateFirstDevice(TopDeviceViewHolder viewHolder, DeviceInformationEntity deviceInformationEntity) {
        switch (deviceInformationEntity.getProductNo()) {
            case Device.PRODUCT_NO_M1:
                viewHolder.device.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.device_m1));
                viewHolder.deviceName.setText(mContext.getString(R.string.device_m1));
                break;
            case Device.PRODUCT_NO_M2:
                viewHolder.device.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.device_m2));
                viewHolder.deviceName.setText(mContext.getString(R.string.device_m2));
                break;
            case Device.PRODUCT_NO_M4:
                viewHolder.device.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.device_m4));
                viewHolder.deviceName.setText(mContext.getString(R.string.device_m4));
                break;
            default:
        }
        switch (deviceInformationEntity.getDeviceUpdate()) {
            case Device.DEVICE_UPDATE_NORMAL:
                viewHolder.upgrade.setVisibility(View.GONE);
                viewHolder.update.setVisibility(View.GONE);
                break;
            case Device.DEVICE_UPDATE:
                viewHolder.upgrade.setVisibility(View.GONE);
                viewHolder.update.setVisibility(View.VISIBLE);
                break;
            case Device.DEVICE_UPDATE_UNDONE:
                viewHolder.upgrade.setVisibility(View.VISIBLE);
                viewHolder.update.setVisibility(View.VISIBLE);
                break;
            default:
        }
        if (deviceStatus == Device.DEVICE_CONNECTED) {
            viewHolder.power.setVisibility(View.VISIBLE);
            viewHolder.power.setPowerData(powerValue);
            viewHolder.tvPower.setVisibility(View.VISIBLE);
            viewHolder.tvPower.setText(powerValue + mContext.getString(R.string.percent));
            viewHolder.ivConnecting.setVisibility(View.GONE);
            viewHolder.tvConnecting.setVisibility(View.GONE);
            viewHolder.bleClose.setVisibility(View.GONE);
        } else {
            if (!BleManager.getInstance().isBlueEnable()) {
                viewHolder.bleClose.setVisibility(View.VISIBLE);
                viewHolder.ivConnecting.setVisibility(View.GONE);
                viewHolder.tvConnecting.setVisibility(View.GONE);
            } else {
                if (deviceInformationEntity.getDeviceUpdate() == Device.DEVICE_UPDATE_UNDONE) {
                    viewHolder.ivConnecting.setVisibility(View.GONE);
                    viewHolder.tvConnecting.setVisibility(View.GONE);
                    viewHolder.bleClose.setVisibility(View.GONE);
                } else {
                    viewHolder.ivConnecting.setVisibility(View.VISIBLE);
                    viewHolder.tvConnecting.setVisibility(View.VISIBLE);
                    Animation rotateAnimation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_anim);
                    LinearInterpolator lin = new LinearInterpolator();
                    rotateAnimation.setInterpolator(lin);
                    viewHolder.ivConnecting.startAnimation(rotateAnimation);
                    viewHolder.bleClose.setVisibility(View.GONE);
                }
            }
            viewHolder.power.setVisibility(View.GONE);
            viewHolder.tvPower.setVisibility(View.GONE);
            viewHolder.update.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return deviceList == null ? 0 : deviceList.size();
    }

    static class DeviceManageViewHolder extends RecyclerView.ViewHolder {
        ImageView device;
        TextView deviceName;
        TextView deviceStatus;


        DeviceManageViewHolder(View itemView) {
            super(itemView);
            device = itemView.findViewById(R.id.iv_device);
            deviceName = itemView.findViewById(R.id.tv_device_name);
            deviceStatus = itemView.findViewById(R.id.tv_device_status);
        }
    }

    static class TopDeviceViewHolder extends RecyclerView.ViewHolder {
        ImageView device;
        TextView deviceName;
        DevicePowerView power;
        TextView tvPower;
        TextView update;
        ImageView ivConnecting;
        TextView tvConnecting;
        TextView upgrade;
        TextView bleClose;

        TopDeviceViewHolder(View itemView) {
            super(itemView);
            device = itemView.findViewById(R.id.iv_device);
            deviceName = itemView.findViewById(R.id.tv_device_name);
            power = itemView.findViewById(R.id.dpv);
            tvPower = itemView.findViewById(R.id.tv_power);
            update = itemView.findViewById(R.id.tv_update);
            ivConnecting = itemView.findViewById(R.id.iv_connecting);
            tvConnecting = itemView.findViewById(R.id.tv_connecting);
            upgrade = itemView.findViewById(R.id.tv_continue_upgrade);
            bleClose = itemView.findViewById(R.id.tv_ble_close);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void deleteDevice(int position) {
        if (deviceList != null && deviceList.size() > 0) {
            deviceList.remove(position);
            notifyDataSetChanged();
        }
    }

    public void updateDeviceStatus(int status) {
        deviceStatus = status;
        notifyDataSetChanged();
    }

    public void updateDevicePower(int power) {
        powerValue = power;
        notifyDataSetChanged();
    }

    public void setDeviceList(List<DeviceInformationEntity> list) {
        deviceList = list;
        notifyDataSetChanged();
    }
}
