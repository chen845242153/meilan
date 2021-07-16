package com.meilancycling.mema.ui.device;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Device;

import com.meilancycling.mema.databinding.ActivitySearchDeviceBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.adapter.SearchDeviceAdapter;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ClsUtils;
import com.meilancycling.mema.utils.RxSchedulers.SimpleObserver;
import com.meilancycling.mema.utils.ToastUtils;

import java.util.List;
import java.util.Set;

import io.reactivex.Observable;

/**
 * @Description: 搜索设备
 * @Author: sore_lion
 * @CreateDate: 2020/12/8 9:59 AM
 */
public class SearchDeviceActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySearchDeviceBinding mActivitySearchDeviceBinding;
    private SearchDeviceAdapter mSearchDeviceAdapter;
    private boolean isScan;
    private long clickTime;
    private final int locationCode = 1;
    private final int bleCode = 2;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySearchDeviceBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_device);
        clickTime = 0;
        name = getIntent().getStringExtra("name");
        initView();
        checkPermission();
        switch (name) {
            case Device.NAME_M1:
                mActivitySearchDeviceBinding.ivDevice.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.search_m1));
                break;
            case Device.NAME_M2:
                mActivitySearchDeviceBinding.ivDevice.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.search_m2));
                break;
            case Device.NAME_M4:
                mActivitySearchDeviceBinding.ivDevice.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.search_m4));
                break;
            case Device.NAME_M5:
                mActivitySearchDeviceBinding.ivDevice.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.search_m5));
                break;
        }
    }

    /**
     * 页面初始化
     */
    private void initView() {
        isScan = false;
        mActivitySearchDeviceBinding.tvSearchDevice.setOnClickListener(this);
        mActivitySearchDeviceBinding.viewBack.setOnClickListener(this);
        mSearchDeviceAdapter = new SearchDeviceAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mActivitySearchDeviceBinding.rvSearch.setLayoutManager(gridLayoutManager);
        mActivitySearchDeviceBinding.rvSearch.setAdapter(mSearchDeviceAdapter);
        List<DeviceInformationEntity> deviceInfoList = DbUtils.getInstance().deviceInfoList(getUserId());
        mSearchDeviceAdapter.setCurrentDeviceList(deviceInfoList);
        mSearchDeviceAdapter.setSearchDeviceClickListener((position, bleDevice) -> checkBonded(bleDevice));
        registerReceiver(mReceiver, new IntentFilter(BroadcastConstant.ACTION_BLE_STATUS));
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mSearchDeviceAdapter.clearData();
            if (BleManager.getInstance().isBlueEnable()) {
                mActivitySearchDeviceBinding.groupBleClose.setVisibility(View.GONE);
                mActivitySearchDeviceBinding.groupBleOpen.setVisibility(View.VISIBLE);
                Animation rotateAnimation = AnimationUtils.loadAnimation(SearchDeviceActivity.this, R.anim.rotate_anim);
                LinearInterpolator lin = new LinearInterpolator();
                rotateAnimation.setInterpolator(lin);
                mActivitySearchDeviceBinding.ivSearchDevice.startAnimation(rotateAnimation);
            } else {
                mActivitySearchDeviceBinding.groupBleClose.setVisibility(View.VISIBLE);
                mActivitySearchDeviceBinding.groupBleOpen.setVisibility(View.GONE);
                mActivitySearchDeviceBinding.ivSearchDevice.clearAnimation();
            }
        }
    };

    /**
     * 权限申请
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!AppUtils.isOpenLocationService(SearchDeviceActivity.this)) {
                new AlertDialog.Builder(SearchDeviceActivity.this)
                        .setTitle(R.string.prompt)
                        .setMessage(R.string.scan_device_permission)
                        .setNegativeButton(R.string.cancel,
                                (dialog, which) -> {
                                    dialog.dismiss();
                                    ToastUtils.show(SearchDeviceActivity.this, getString(R.string.openGPSFailure));
                                })
                        .setPositiveButton(R.string.confirm,
                                (dialog, which) -> {
                                    dialog.dismiss();
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivityForResult(intent, locationCode);
                                })
                        .setCancelable(false)
                        .show();
            } else {
                checkBleStatus();
            }
        } else {
            checkBleStatus();
        }
    }

    private void scanDevice() {
        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setDeviceName(true, name)
                .setAutoConnect(false)
                .setScanTimeOut(15 * 1000)
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);

        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                isScan = false;
            }

            @Override
            public void onScanStarted(boolean success) {
                isScan = true;
            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                mSearchDeviceAdapter.addSearchDevice(bleDevice);
            }
        });

    }

    private void checkBleStatus() {
        mSearchDeviceAdapter.clearData();
        if (BleManager.getInstance().isBlueEnable()) {
            mActivitySearchDeviceBinding.groupBleClose.setVisibility(View.GONE);
            mActivitySearchDeviceBinding.groupBleOpen.setVisibility(View.VISIBLE);
            Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
            LinearInterpolator lin = new LinearInterpolator();
            rotateAnimation.setInterpolator(lin);
            mActivitySearchDeviceBinding.ivSearchDevice.startAnimation(rotateAnimation);
            scanDevice();
        } else {
            mActivitySearchDeviceBinding.groupBleClose.setVisibility(View.VISIBLE);
            mActivitySearchDeviceBinding.groupBleOpen.setVisibility(View.GONE);
            mActivitySearchDeviceBinding.ivSearchDevice.clearAnimation();
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, bleCode);
        }
    }

    private void checkBonded(BleDevice scanDevice) {
        addDevice(scanDevice.getMac(), scanDevice.getName());

        Set<BluetoothDevice> devices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        boolean isBonded = false;
        if (devices != null) {
            for (BluetoothDevice device : devices) {
                if (scanDevice.getMac().equals(device.getAddress())) {
                    isBonded = true;
                    break;
                }
            }
        }
        if (!isBonded) {
            try {
                BluetoothDevice device = scanDevice.getDevice();
                ClsUtils.createBond(device.getClass(), device);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 添加设备
     */
    private void addDevice(String macAddress, String name) {
        DeviceInformationEntity deviceInformationEntity = new DeviceInformationEntity();
        deviceInformationEntity.setMacAddress(macAddress);
        String productNo = null;
        switch (name) {
            case Device.NAME_M1:
                deviceInformationEntity.setProductNo(Device.PRODUCT_NO_M1);
                productNo = Device.PRODUCT_NO_M1;
                break;
            case Device.NAME_M2:
                deviceInformationEntity.setProductNo(Device.PRODUCT_NO_M2);
                productNo = Device.PRODUCT_NO_M2;
                break;
            case Device.NAME_M4:
                deviceInformationEntity.setProductNo(Device.PRODUCT_NO_M4);
                productNo = Device.PRODUCT_NO_M4;
                break;
            default:
        }
        deviceInformationEntity.setUserId(getUserId());
        deviceInformationEntity.setProductNo(productNo);
        deviceInformationEntity.setDeviceUpdate(Device.DEVICE_UPDATE_NORMAL);

        Observable.just(deviceInformationEntity)
                .map(deviceInfo -> {
                    DeviceInformationEntity device = DeviceControllerService.currentDevice;
                    if (device == null) {
                        deviceInfo.setDeviceSerialNumber(0);
                    } else {
                        deviceInfo.setDeviceSerialNumber(device.getDeviceSerialNumber() + 1);
                    }
                    DbUtils.getInstance().addDevice(deviceInfo);
                    return deviceInfo;
                })
                .subscribe(new SimpleObserver<DeviceInformationEntity>() {
                    @Override
                    public void onNext(DeviceInformationEntity deviceInformationEntity) {
                        changeDevice();
                        DeviceSettingActivity.intoDeviceSetting(SearchDeviceActivity.this, 0);
                        finish();
                    }
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_back:
                finish();
                break;
            case R.id.tv_search_device:
                if (System.currentTimeMillis() - clickTime >= 3000) {
                    clickTime = System.currentTimeMillis();
                    if (isScan) {
                        try {
                            BleManager.getInstance().cancelScan();
                            mSearchDeviceAdapter.clearData();
                        } catch (Exception e) {
                        }
                    }
                    checkPermission();
                } else {
                    ToastUtils.show(this, getString(R.string.click_too_busy));
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == locationCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (AppUtils.isOpenLocationService(SearchDeviceActivity.this)) {
                    checkBleStatus();
                }
            } else {
                checkBleStatus();
            }
        } else if (requestCode == bleCode) {
            if (BleManager.getInstance().isBlueEnable()) {
                scanDevice();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isScan) {
            try {
                BleManager.getInstance().cancelScan();
            } catch (Exception e) {
            }
        }
        mActivitySearchDeviceBinding.ivSearchDevice.clearAnimation();
        unregisterReceiver(mReceiver);
    }
}
