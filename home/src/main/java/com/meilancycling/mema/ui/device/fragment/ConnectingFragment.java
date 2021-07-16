package com.meilancycling.mema.ui.device.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.databinding.FragmentDeviceConnectingBinding;
import com.meilancycling.mema.ui.device.DeviceSettingActivity;


/**
 * @Description: 正在连接
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class ConnectingFragment extends BaseFragment {

    private DeviceSettingActivity mDeviceSettingActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentDeviceConnectingBinding fragmentDeviceConnectingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_device_connecting, container, false);
        mDeviceSettingActivity = (DeviceSettingActivity) getActivity();
        if (mDeviceSettingActivity != null) {
            mDeviceSettingActivity.showImageUi(fragmentDeviceConnectingBinding.ivDevice, fragmentDeviceConnectingBinding.ivBottomBg, fragmentDeviceConnectingBinding.ivTopBg);
            fragmentDeviceConnectingBinding.tvDeleteDevice.setOnClickListener(v -> mDeviceSettingActivity.deleteDevice());
            mDeviceSettingActivity.showAndHideBack(true);
        }
        return fragmentDeviceConnectingBinding.getRoot();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mDeviceSettingActivity.showAndHideBack(true);
        }
    }
}
