package com.meilancycling.mema.ui.device.fragment;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.databinding.FragmentContinueUpgradeBinding;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.device.DeviceSettingActivity;
import com.meilancycling.mema.ui.home.view.DeviceUpgradeDialog;

/**
 * @Description: 继续升级
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class ContinueUpgradeFragment extends BaseFragment {

    private DeviceSettingActivity mDeviceSettingActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentContinueUpgradeBinding fragmentContinueUpgradeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_continue_upgrade, container, false);
        mDeviceSettingActivity = (DeviceSettingActivity) getActivity();
        if (mDeviceSettingActivity != null) {
            mDeviceSettingActivity.showImageUi(fragmentContinueUpgradeBinding.ivDevice, fragmentContinueUpgradeBinding.ivBottomBg, fragmentContinueUpgradeBinding.ivTopBg);
            mDeviceSettingActivity.showAndHideBack(true);
        }
        //继续升级
        fragmentContinueUpgradeBinding.viewContinueBg.setOnClickListener(v -> {
            int lowPower = 20;
            BatteryManager manager = (BatteryManager) mActivity.getSystemService(Context.BATTERY_SERVICE);
            if (manager != null) {
                int currentLevel = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                if (currentLevel <= lowPower) {
                    DeviceUpgradeDialog deviceUpgradeDialog = new DeviceUpgradeDialog(mActivity, DeviceControllerService.currentDevice.getProductNo(), 100);
                    deviceUpgradeDialog.show();
                } else {
                    mDeviceSettingActivity.startOta();
                }
            } else {
                mDeviceSettingActivity.startOta();
            }
        });
        return fragmentContinueUpgradeBinding.getRoot();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            mDeviceSettingActivity.showAndHideBack(true);
        }
    }
}
