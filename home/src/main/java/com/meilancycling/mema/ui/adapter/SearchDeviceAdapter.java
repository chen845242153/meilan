package com.meilancycling.mema.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clj.fastble.data.BleDevice;
import com.lihang.ShadowLayout;
import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.db.DeviceInformationEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 搜索设备
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class SearchDeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<BleDevice> scanList;
    private List<DeviceInformationEntity> mDeviceList;

    public void setCurrentDeviceList(List<DeviceInformationEntity> deviceInformationEntityList) {
        mDeviceList = deviceInformationEntityList;
    }

    public SearchDeviceAdapter(Context context) {
        this.mContext = context;
        scanList = new ArrayList<>();
    }

    public void addSearchDevice(BleDevice bleDevice) {
        if (bleDevice != null) {
            boolean addDevice = isAddDevice(bleDevice);
            if (!addDevice) {
                boolean scanDevice = isScanDevice(bleDevice);
                if (!scanDevice) {
                    scanList.add(bleDevice);
                    notifyDataSetChanged();
                }
            }
        }
    }

    private boolean isAddDevice(BleDevice bleDevice) {
        boolean isHave = false;
        if (mDeviceList != null && mDeviceList.size() > 0) {
            for (DeviceInformationEntity deviceInformationEntity : mDeviceList) {
                if (deviceInformationEntity.getMacAddress().equals(bleDevice.getMac())) {
                    isHave = true;
                    break;
                }
            }
        }
        return isHave;
    }

    private boolean isScanDevice(BleDevice bleDevice) {
        boolean isHave = false;
        if (scanList != null && scanList.size() > 0) {
            for (BleDevice device : scanList) {
                if (device.getMac().equals(bleDevice.getMac())) {
                    isHave = true;
                    break;
                }
            }
        }
        return isHave;
    }

    public void clearData() {
        scanList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_device_view, parent, false);
        return new RecyclerHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerHolder recyclerHolder = (RecyclerHolder) holder;
        String name = scanList.get(position).getName();
        switch (name) {
            case Device.NAME_M1:
                recyclerHolder.deviceName.setText(R.string.device_m1);
                recyclerHolder.device.setImageDrawable(mContext.getDrawable(R.drawable.device_m1));
                break;
            case Device.NAME_M2:
                recyclerHolder.deviceName.setText(R.string.device_m2);
                recyclerHolder.device.setImageDrawable(mContext.getDrawable(R.drawable.device_m2));
                break;
            case Device.NAME_M4:
                recyclerHolder.deviceName.setText(R.string.device_m4);
                recyclerHolder.device.setImageDrawable(mContext.getDrawable(R.drawable.device_m4));
                break;
            default:
        }
        int rssi = scanList.get(position).getRssi();
        int rssiZone1 = -20;
        int rssiZone2 = -40;
        int rssiZone3 = -60;
        int rssiZone4 = -80;
        if (rssi >= rssiZone1) {
            recyclerHolder.deviceRssi.setImageDrawable(mContext.getDrawable(R.drawable.device_rssi_4));
        } else if (rssi >= rssiZone2) {
            recyclerHolder.deviceRssi.setImageDrawable(mContext.getDrawable(R.drawable.device_rssi_3));
        } else if (rssi >= rssiZone3) {
            recyclerHolder.deviceRssi.setImageDrawable(mContext.getDrawable(R.drawable.device_rssi_2));
        } else if (rssi >= rssiZone4) {
            recyclerHolder.deviceRssi.setImageDrawable(mContext.getDrawable(R.drawable.device_rssi_1));
        } else {
            recyclerHolder.deviceRssi.setImageDrawable(mContext.getDrawable(R.drawable.device_rssi_0));
        }
        recyclerHolder.slSearchDevice.setOnClickListener(v -> {
            if (mSearchDeviceClickListener != null) {
                mSearchDeviceClickListener.itemClick(position, scanList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return scanList == null ? 0 : scanList.size();
    }

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        ShadowLayout slSearchDevice;
        ImageView device;
        TextView deviceName;
        ImageView deviceRssi;
        ImageView deviceType;

        RecyclerHolder(View itemView) {
            super(itemView);
            device = itemView.findViewById(R.id.iv_device);
            deviceName = itemView.findViewById(R.id.tv_device_name);
            deviceRssi = itemView.findViewById(R.id.iv_device_rssi);
            deviceType = itemView.findViewById(R.id.iv_device_type);
            slSearchDevice = itemView.findViewById(R.id.sl_search_device);

        }
    }

    public interface SearchDeviceClickListener {
        /**
         * 条目点击
         *
         * @param bleDevice 设备对象
         * @param position  当前条目
         */
        void itemClick(int position, BleDevice bleDevice);
    }

    private SearchDeviceClickListener mSearchDeviceClickListener;

    public void setSearchDeviceClickListener(SearchDeviceClickListener searchDeviceClickListener) {
        mSearchDeviceClickListener = searchDeviceClickListener;
    }
}
