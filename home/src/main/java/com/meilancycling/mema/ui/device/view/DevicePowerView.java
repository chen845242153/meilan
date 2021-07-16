package com.meilancycling.mema.ui.device.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.DevicePowerDeviceBinding;


/**
 * @Description: 电量view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class DevicePowerView extends LinearLayout {
    private DevicePowerDeviceBinding mDevicePowerDeviceBinding;

    public DevicePowerView(Context context) {
        super(context);
        init();
    }

    public DevicePowerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DevicePowerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mDevicePowerDeviceBinding = DataBindingUtil.inflate(inflater, R.layout.device_power_device, this, false);
            this.addView(mDevicePowerDeviceBinding.getRoot());
        }
    }

    /**
     * 设置数据
     */
    public void setPowerData(int powerValue) {
        LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT, powerValue);
        mDevicePowerDeviceBinding.viewPower.setLayoutParams(lp);
        lp = new LayoutParams(0, LayoutParams.MATCH_PARENT, 100 - powerValue);
        mDevicePowerDeviceBinding.viewEmpty.setLayoutParams(lp);
    }

}
