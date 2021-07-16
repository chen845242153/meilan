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
import com.meilancycling.mema.databinding.FragmentUpgradeFinishBinding;
import com.meilancycling.mema.ui.device.DeviceSettingActivity;


/**
 * @Description: 升级完成
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class UpgradeFinishFragment extends BaseFragment {
    private DeviceSettingActivity mDeviceSettingActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentUpgradeFinishBinding fragmentUpgradeFinishBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_upgrade_finish, container, false);
        mDeviceSettingActivity = (DeviceSettingActivity) getActivity();
        if (mDeviceSettingActivity != null) {
            mDeviceSettingActivity.showImageUi(fragmentUpgradeFinishBinding.ivDevice, fragmentUpgradeFinishBinding.ivBottomBg, fragmentUpgradeFinishBinding.ivTopBg);
            mDeviceSettingActivity.showAndHideBack(true);
        }
        return fragmentUpgradeFinishBinding.getRoot();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            mDeviceSettingActivity.showAndHideBack(true);
        }
    }
}
