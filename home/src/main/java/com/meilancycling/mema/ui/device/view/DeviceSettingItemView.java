package com.meilancycling.mema.ui.device.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.DeviceSettingItemBinding;

/**
 * @Description: 汇总view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class DeviceSettingItemView extends LinearLayout {
    private DeviceSettingItemBinding mDeviceSettingItemBinding;

    public DeviceSettingItemView(Context context) {
        super(context);
        init();
    }

    public DeviceSettingItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DeviceSettingItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mDeviceSettingItemBinding = DataBindingUtil.inflate(inflater, R.layout.device_setting_item, this, false);
            this.addView(mDeviceSettingItemBinding.getRoot());
        }
    }

    /**
     * 设置数据
     */
    public void setItemData(String title, String value) {
        mDeviceSettingItemBinding.tvTitle.setText(title);
        mDeviceSettingItemBinding.tvValue.setText(value);
    }
}
