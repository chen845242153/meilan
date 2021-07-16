package com.meilancycling.mema.ui.device.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.databinding.FragmentBleCloseBinding;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.device.DeviceSettingActivity;

/**
 * @Description: 蓝牙关闭
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class BleCloseFragment extends BaseFragment {
    private DeviceSettingActivity mDeviceSettingActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBleCloseBinding fragmentBleCloseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ble_close, container, false);
        mDeviceSettingActivity = (DeviceSettingActivity) getActivity();
        if (mDeviceSettingActivity != null) {
            mDeviceSettingActivity.showImageUi(fragmentBleCloseBinding.ivDevice, fragmentBleCloseBinding.ivBottomBg, fragmentBleCloseBinding.ivTopBg);
            if (DeviceControllerService.currentDevice.getDeviceUpdate() == Device.DEVICE_UPDATE_UNDONE) {
                fragmentBleCloseBinding.tvDeleteDevice.setVisibility(View.GONE);
            }
            fragmentBleCloseBinding.tvDeleteDevice.setOnClickListener(v -> mDeviceSettingActivity.deleteDevice());
            mDeviceSettingActivity.showAndHideBack(true);
        }
        return fragmentBleCloseBinding.getRoot();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mDeviceSettingActivity.showAndHideBack(true);
        }
    }
}
