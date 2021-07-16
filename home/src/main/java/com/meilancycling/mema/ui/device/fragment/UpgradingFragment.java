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
import com.meilancycling.mema.databinding.FragmentUpgradingBinding;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.download.FileDownloadCallback;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.device.DeviceSettingActivity;
import com.meilancycling.mema.utils.ToastUtils;

import java.io.File;

/**
 * @Description: 正在升级
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class UpgradingFragment extends BaseFragment {
    private FragmentUpgradingBinding mFragmentUpgradingBinding;
    private DeviceSettingActivity mDeviceSettingActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentUpgradingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_upgrading, container, false);
        mDeviceSettingActivity = (DeviceSettingActivity) getActivity();
        if (mDeviceSettingActivity != null) {
            mDeviceSettingActivity.showAndHideBack(false);
            mDeviceSettingActivity.showImageUi(mFragmentUpgradingBinding.ivDevice, mFragmentUpgradingBinding.ivBottomBg, mFragmentUpgradingBinding.ivTopBg);
            downOta(DeviceControllerService.currentDevice.getOtaUrl());
        }
        return mFragmentUpgradingBinding.getRoot();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mDeviceSettingActivity.showAndHideBack(false);
            downOta(DeviceControllerService.currentDevice.getOtaUrl());
        }
    }

    /**
     * 升级进度
     */
    public void upgradeProgress(int progress) {
        if (mFragmentUpgradingBinding != null) {
            mFragmentUpgradingBinding.tvBleStatus.setText(getString(R.string.equipment_upgrading));
            mFragmentUpgradingBinding.duv.updateProgress(progress);
        }
    }

    /**
     * 下载ota
     */
    private void downOta(String url) {
        mFragmentUpgradingBinding.duv.updateProgress(0);
        mFragmentUpgradingBinding.tvBleStatus.setText(getString(R.string.downloading));
        String filePath = mActivity.getExternalFilesDir("") + File.separator + "ota.zip";
        RetrofitUtils.downloadUrl(url).execute(filePath, new FileDownloadCallback<File>() {
            @Override
            public void onSuccess(File file) {
                mFragmentUpgradingBinding.tvBleStatus.setText(getString(R.string.equipment_upgrading));
                mFragmentUpgradingBinding.duv.updateProgress(0);
                mDeviceSettingActivity.otaUpgrade();
            }

            @Override
            public void onFail(Throwable throwable) {
                ToastUtils.show(mActivity, getString(R.string.file_failed));
                mDeviceSettingActivity.otaFail();
            }

            @Override
            public void onProgress(long current, long total) {
                mFragmentUpgradingBinding.duv.updateProgress((int) ((float) current / total * 100));
            }
        });
    }
}
