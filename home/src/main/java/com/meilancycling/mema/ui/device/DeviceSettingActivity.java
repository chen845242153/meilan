package com.meilancycling.mema.ui.device;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clj.fastble.BleManager;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.customview.dialog.AskDialog;
import com.meilancycling.mema.databinding.ActivityDeviceSettingBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.VersionInsetRequest;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.device.fragment.BleCloseFragment;
import com.meilancycling.mema.ui.device.fragment.ConnectingFragment;
import com.meilancycling.mema.ui.device.fragment.ContinueUpgradeFragment;
import com.meilancycling.mema.ui.device.fragment.SettingFragment;
import com.meilancycling.mema.ui.device.fragment.UpgradeFinishFragment;
import com.meilancycling.mema.ui.device.fragment.UpgradingFragment;
import com.meilancycling.mema.utils.StatusAppUtils;

import java.util.Objects;

import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/12/9 2:06 PM
 */
public class DeviceSettingActivity extends BaseActivity {
    private ActivityDeviceSettingBinding mActivityDeviceSettingBinding;
    private int type;
    private Fragment fromFragment;

    /**
     * 进入设备设置
     *
     * @param type 0 设备设置
     *             1 升级设备
     */
    public static void intoDeviceSetting(Context context, int type) {
        Intent intent = new Intent(context, DeviceSettingActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDeviceSettingBinding = DataBindingUtil.setContentView(this, R.layout.activity_device_setting);
        type = getIntent().getIntExtra("type", 0);
        mActivityDeviceSettingBinding.viewBack.setOnClickListener(v -> {
            if (mActivityDeviceSettingBinding.ivBack.getVisibility() == View.VISIBLE) {
                finish();
            }
        });
        mUpgradingFragment = new UpgradingFragment();
        mSettingFragment = new SettingFragment();
        mConnectingFragment = new ConnectingFragment();
        mBleCloseFragment = new BleCloseFragment();
        mContinueUpgradeFragment = new ContinueUpgradeFragment();
        mUpgradeFinishFragment = new UpgradeFinishFragment();
        DfuServiceListenerHelper.registerProgressListener(this, mDfuProgressListener);
        if (DeviceControllerService.currentDevice != null) {
            switch (DeviceControllerService.currentDevice.getProductNo()) {
                case Device.PRODUCT_NO_M1:
                    mActivityDeviceSettingBinding.tvTitle.setText(getString(R.string.device_m1));
                    break;
                case Device.PRODUCT_NO_M2:
                    mActivityDeviceSettingBinding.tvTitle.setText(getString(R.string.device_m2));
                    break;
                case Device.PRODUCT_NO_M4:
                    mActivityDeviceSettingBinding.tvTitle.setText(getString(R.string.device_m4));
                    break;
                default:
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mUpgradingFragment = new UpgradingFragment();
        mSettingFragment = new SettingFragment();
        mConnectingFragment = new ConnectingFragment();
        mBleCloseFragment = new BleCloseFragment();
        mContinueUpgradeFragment = new ContinueUpgradeFragment();
        mUpgradeFinishFragment = new UpgradeFinishFragment();
    }


    public void showGuide() {
        StatusAppUtils.setColor(this, ContextCompat.getColor(this, R.color.guide_bg));
        mActivityDeviceSettingBinding.fakeStatusBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.guide_bg));
        mActivityDeviceSettingBinding.viewGuide.setVisibility(View.VISIBLE);
    }

    public void hideGuide() {
        StatusAppUtils.setColor(this, ContextCompat.getColor(this, R.color.white));
        mActivityDeviceSettingBinding.fakeStatusBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        mActivityDeviceSettingBinding.viewGuide.setVisibility(View.GONE);
    }

    private UpgradingFragment mUpgradingFragment;
    private SettingFragment mSettingFragment;
    private ConnectingFragment mConnectingFragment;
    private BleCloseFragment mBleCloseFragment;
    private ContinueUpgradeFragment mContinueUpgradeFragment;
    private UpgradeFinishFragment mUpgradeFinishFragment;

    private void initView(DeviceInformationEntity device, int deviceStatus) {
        if (device == null) {
            return;
        }
        if (BleManager.getInstance().isBlueEnable()) {
            if (type == 1) {
                showAndHideBack(false);
                switchFragment(mUpgradingFragment);
            } else {
                if (deviceStatus == Device.DEVICE_CONNECTED) {
                    if (fromFragment == mSettingFragment) {
                        mSettingFragment.setSettingData();
                        mSettingFragment.updateStatus();
                        mSettingFragment.updatePower(DeviceControllerService.currentPower);
                    } else {
                        switchFragment(mSettingFragment);
                    }
                } else {
                    if (device.getDeviceUpdate() == Device.DEVICE_UPDATE_UNDONE) {
                        switchFragment(mContinueUpgradeFragment);
                    } else {
                        switchFragment(mConnectingFragment);
                    }
                }
            }
        } else {
            switchFragment(mBleCloseFragment);
        }
    }

    /**
     * 开始ota升级
     */
    public void startOta() {
        type = 1;
        switchFragment(mUpgradingFragment);
    }

    /**
     * 升级失败
     */
    public void otaFail() {
        type = 0;
        if (BleManager.getInstance().isBlueEnable()) {
            switchFragment(mContinueUpgradeFragment);
        } else {
            switchFragment(mBleCloseFragment);
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())) {
                case BroadcastConstant.ACTION_BLE_STATUS:
                case BroadcastConstant.ACTION_DEVICE_STATUS:
                    initView(DeviceControllerService.currentDevice, DeviceControllerService.deviceStatus);
                    break;
                case BroadcastConstant.ACTION_DATA_PROGRESS:
                    mSettingFragment.updateProgress(intent.getIntExtra("total", 100), intent.getIntExtra("current", 0));
                    break;
                case BroadcastConstant.ACTION_DEVICE_UPDATE:
                    mSettingFragment.updateStatus();
                    break;
                case BroadcastConstant.ACTION_POWER_VALUE:
                    mSettingFragment.updatePower(DeviceControllerService.currentPower);
                    break;
                default:
            }
        }
    };


    public void showAndHideBack(boolean isShow) {
        if (isShow) {
            mActivityDeviceSettingBinding.groupBack.setVisibility(View.VISIBLE);
        } else {
            mActivityDeviceSettingBinding.groupBack.setVisibility(View.GONE);
        }
    }

    public void deleteDevice() {
        String deviceName = null;
        switch (DeviceControllerService.currentDevice.getProductNo()) {
            case Device.PRODUCT_NO_M1:
                deviceName = getString(R.string.device_m1);
                break;
            case Device.PRODUCT_NO_M2:
                deviceName = getString(R.string.device_m2);
                break;
            case Device.PRODUCT_NO_M4:
                deviceName = getString(R.string.device_m4);
                break;
            default:
        }
        AskDialog askDialog = new AskDialog(this, getString(R.string.delete_device), getString(R.string.whether_delete_device) + " " + deviceName);
        askDialog.show();
        askDialog.setAskDialogListener(new AskDialog.AskDialogListener() {
            @Override
            public void clickCancel() {
            }

            @Override
            public void clickConfirm() {
                DeviceInformationEntity device = DeviceControllerService.currentDevice;
                DbUtils.getInstance().deleteDevice(device);
                DeviceControllerService.mDeletingDeviceMacList.add(device.getMacAddress());
                changeDevice();
                finish();
            }
        });
    }


    /**
     * dfu升级监听
     */
    @SuppressLint("LogNotTimber")
    private final DfuProgressListener mDfuProgressListener = new DfuProgressListener() {
        @Override
        public void onDeviceConnecting(@NonNull String deviceAddress) {
            Log.i("lion", "onDeviceConnecting");
        }

        @Override
        public void onDeviceConnected(@NonNull String deviceAddress) {
            Log.i("lion", "onDeviceConnected");
        }

        @Override
        public void onDfuProcessStarting(@NonNull String deviceAddress) {
            Log.i("lion", "onDfuProcessStarting");
        }

        @Override
        public void onDfuProcessStarted(@NonNull String deviceAddress) {
            Log.i("lion", "onDfuProcessStarted");
            if (DeviceControllerService.currentDevice.getDeviceUpdate() != Device.DEVICE_UPDATE_UNDONE) {
                DeviceControllerService.currentDevice.setDeviceUpdate(Device.DEVICE_UPDATE_UNDONE);
                DbUtils.getInstance().updateDevice(DeviceControllerService.currentDevice);
                updateCurrentDevice();
                insertUpgrade(DeviceControllerService.currentDevice);
                updateCurrentDevice();
            }
        }

        @Override
        public void onEnablingDfuMode(@NonNull String deviceAddress) {
            Log.i("lion", "onEnablingDfuMode");
        }

        @Override
        public void onProgressChanged(@NonNull String deviceAddress, int percent, float speed, float avgSpeed, int currentPart, int partsTotal) {
            mUpgradingFragment.upgradeProgress(percent);
        }

        @Override
        public void onFirmwareValidating(@NonNull String deviceAddress) {
            Log.i("lion", "onFirmwareValidating");
        }

        @Override
        public void onDeviceDisconnecting(String deviceAddress) {
            Log.i("lion", "onDeviceDisconnecting");
        }

        @Override
        public void onDeviceDisconnected(@NonNull String deviceAddress) {
            Log.i("lion", "onDeviceDisconnected");
        }

        @Override
        public void onDfuCompleted(@NonNull String deviceAddress) {
            Log.i("lion", "onDfuCompleted");
            DeviceControllerService.currentDevice.setDeviceUpdate(Device.DEVICE_UPDATE_NORMAL);
            DeviceControllerService.currentDevice.setMessageCh(null);
            DeviceControllerService.currentDevice.setMessageEn(null);
            DeviceControllerService.currentDevice.setOtaUrl(null);
            DeviceControllerService.currentDevice.setShowTime(0);
            DbUtils.getInstance().updateDevice(DeviceControllerService.currentDevice);
            updateUpgrade(DeviceControllerService.currentDevice.getMacAddress());
            DeviceControllerService.otaFlag = false;
            updateCurrentDevice();
            type = 0;
            switchFragment(mUpgradeFinishFragment);
            new Handler().postDelayed(() -> changeDevice(), 2000);
        }

        @Override
        public void onDfuAborted(@NonNull String deviceAddress) {
            Log.i("lion", "onDfuAborted");
        }

        @Override
        public void onError(@NonNull String deviceAddress, int error, int errorType, String message) {
            Log.i("lion", "onError");
            type = 0;
            DeviceControllerService.otaFlag = false;
            otaFail();
        }
    };

    /**
     * 插入升级信息
     */
    private void insertUpgrade(DeviceInformationEntity deviceInformationEntity) {
        VersionInsetRequest versionInsetRequest = new VersionInsetRequest();
        versionInsetRequest.setFileTUrl(deviceInformationEntity.getOtaUrl());
        versionInsetRequest.setMac(deviceInformationEntity.getMacAddress());
        versionInsetRequest.setProduct(deviceInformationEntity.getProductNo());
        versionInsetRequest.setSession(getUserInfoEntity().getSession());
        versionInsetRequest.setStatus(String.valueOf(3));
        RetrofitUtils.getApiUrl().versionUpdateInset(versionInsetRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                    }
                });
    }

    /**
     * 修改升级状态
     */
    private void updateUpgrade(String macAddress) {
        VersionInsetRequest versionInsetRequest = new VersionInsetRequest();
        versionInsetRequest.setMac(macAddress);
        versionInsetRequest.setSession(getUserInfoEntity().getSession());
        versionInsetRequest.setStatus(String.valueOf(4));
        RetrofitUtils.getApiUrl().versionUpdateUpdate(versionInsetRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                    }
                });
    }

    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mActivityDeviceSettingBinding.groupBack.getVisibility() == View.VISIBLE) {
            finish();
        }
        return false;
    }

    private void switchFragment(Fragment toFragment) {
        try {
            if (fromFragment != toFragment && toFragment != null) {
                FragmentManager manger = getSupportFragmentManager();
                FragmentTransaction transaction = manger.beginTransaction();
                if (!toFragment.isAdded()) {
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.fl_device_setting, toFragment, toFragment.getClass().getName()).commit();
                } else {
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(toFragment).commit();
                }
            }
            fromFragment = toFragment;
        } catch (Exception e) {

        }
    }

    public void showImageUi(ImageView ivDevice, ImageView ivBottomBg, ImageView ivTopBg) {
        switch (DeviceControllerService.currentDevice.getProductNo()) {
            case Device.PRODUCT_NO_M1:
                ivDevice.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.device_m1));
                ivBottomBg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.m1_bg));
                ivTopBg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.m1_bg_top));
                break;
            case Device.PRODUCT_NO_M2:
                ivDevice.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.device_m2));
                ivBottomBg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.m2_bg));
                ivTopBg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.m2_bg_top));
                break;
            case Device.PRODUCT_NO_M4:
                ivDevice.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.device_m4));
                ivBottomBg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.m4_bg));
                ivTopBg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.m4_bg_top));
                break;
            default:
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastConstant.ACTION_DEVICE_UPDATE);
        intentFilter.addAction(BroadcastConstant.ACTION_DATA_PROGRESS);
        intentFilter.addAction(BroadcastConstant.ACTION_BLE_STATUS);
        intentFilter.addAction(BroadcastConstant.ACTION_POWER_VALUE);
        intentFilter.addAction(BroadcastConstant.ACTION_DEVICE_STATUS);
        registerReceiver(mReceiver, intentFilter);
        initView(DeviceControllerService.currentDevice, DeviceControllerService.deviceStatus);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DfuServiceListenerHelper.unregisterProgressListener(this, mDfuProgressListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSettingFragment != null) {
            mSettingFragment.checkInformation();
        }
    }
}
