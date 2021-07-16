package com.meilancycling.mema.ui.device;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.clj.fastble.BleManager;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.databinding.ActivityDeviceManageBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.adapter.DeviceManageAdapter;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.RxSchedulers.SimpleObserver;
import com.meilancycling.mema.utils.StatusAppUtils;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 设备管理
 *
 * @author lion
 */
public class DeviceManageActivity extends BaseActivity {
    private ActivityDeviceManageBinding mActivityDeviceManageBinding;
    private DeviceManageAdapter mDeviceManageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusAppUtils.setColor(this, ContextCompat.getColor(this, R.color.white));
        mActivityDeviceManageBinding = DataBindingUtil.setContentView(this, R.layout.activity_device_manage);
        mActivityDeviceManageBinding.ctvDeviceManage.setData(getString(R.string.device_manage), v -> finish());
        mActivityDeviceManageBinding.srvDeviceManage.setLayoutManager(new LinearLayoutManager(this));
        mActivityDeviceManageBinding.srvDeviceManage.setSwipeMenuCreator(mSwipeMenuCreator);
        mActivityDeviceManageBinding.srvDeviceManage.setOnItemMenuClickListener(mItemMenuClickListener);
        mDeviceManageAdapter = new DeviceManageAdapter(this);
        mActivityDeviceManageBinding.srvDeviceManage.setOnItemClickListener((view, adapterPosition) -> {
            if (adapterPosition == 0) {
                if (DeviceControllerService.currentDevice.getDeviceUpdate() == Device.DEVICE_UPDATE_UNDONE) {
                    DeviceSettingActivity.intoDeviceSetting(this, 1);
                } else {
                    DeviceSettingActivity.intoDeviceSetting(this, 0);
                }
            } else {
                List<DeviceInformationEntity> deviceInformationEntities = DbUtils.getInstance().deviceInfoList(getUserId());
                Observable.just(deviceInformationEntities.get(adapterPosition))
                        .map(deviceInformationEntity -> {
                            DeviceInformationEntity device = DeviceControllerService.currentDevice;
                            int newNumber = device.getDeviceSerialNumber();
                            int deviceSerialNumber = deviceInformationEntity.getDeviceSerialNumber();
                            deviceInformationEntity.setDeviceSerialNumber(newNumber);
                            device.setDeviceSerialNumber(deviceSerialNumber);
                            DbUtils.getInstance().updateDevice(device);
                            DbUtils.getInstance().updateDevice(deviceInformationEntity);
                            return deviceInformationEntity;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SimpleObserver<DeviceInformationEntity>() {
                            @Override
                            public void onNext(DeviceInformationEntity deviceInformationEntity) {
                                changeDevice();
                                updateList();
                                DeviceSettingActivity.intoDeviceSetting(DeviceManageActivity.this, 0);

                            }
                        });
            }
        });
        mActivityDeviceManageBinding.cbvDeviceManage.setBottomView(R.string.add_device, R.color.main_color, v -> startActivity(new Intent(DeviceManageActivity.this, SelectDeviceActivity.class)));
        mActivityDeviceManageBinding.srvDeviceManage.setAdapter(mDeviceManageAdapter);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastConstant.ACTION_DEVICE_STATUS);
        intentFilter.addAction(BroadcastConstant.ACTION_POWER_VALUE);
        intentFilter.addAction(BroadcastConstant.ACTION_BLE_STATUS);
        registerReceiver(mReceiver, intentFilter);
        updateList();
        updateBleStatus();
        mDeviceManageAdapter.updateDeviceStatus(DeviceControllerService.deviceStatus);
        mDeviceManageAdapter.updateDevicePower(DeviceControllerService.currentPower);

    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())) {
                case BroadcastConstant.ACTION_DEVICE_STATUS:
                    List<DeviceInformationEntity> deviceList = DbUtils.getInstance().deviceInfoList(getUserId());
                    if (deviceList != null && deviceList.size() > 0) {
                        mDeviceManageAdapter.updateDeviceStatus(DeviceControllerService.deviceStatus);
                    } else {
                        mActivityDeviceManageBinding.llBleClose.setVisibility(View.GONE);
                    }
                    break;
                case BroadcastConstant.ACTION_POWER_VALUE:
                    mDeviceManageAdapter.updateDevicePower(DeviceControllerService.currentPower);
                    break;
                case BroadcastConstant.ACTION_BLE_STATUS:
                    updateBleStatus();
                    break;
                default:
            }
        }
    };

    private void updateBleStatus() {
        if (!BleManager.getInstance().isBlueEnable()) {
            mActivityDeviceManageBinding.llBleClose.setVisibility(View.VISIBLE);
        } else {
            mActivityDeviceManageBinding.llBleClose.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        List<DeviceInformationEntity> deviceList = DbUtils.getInstance().deviceInfoList(getUserId());
        if (deviceList != null && deviceList.size() > 0) {
            mActivityDeviceManageBinding.slNoDevice.setVisibility(View.GONE);
            mDeviceManageAdapter.setDeviceList(deviceList);
        } else {
            mActivityDeviceManageBinding.slNoDevice.setVisibility(View.VISIBLE);
        }
    }

    /**
     * RecyclerView的Item中的Menu点击监听。
     */
    private final OnItemMenuClickListener mItemMenuClickListener = (menuBridge, position) -> {
        menuBridge.closeMenu();
        List<DeviceInformationEntity> deviceList = DbUtils.getInstance().deviceInfoList(getUserId());
        if (deviceList != null && deviceList.size() > 0) {
            DeviceInformationEntity deviceInformationEntity = deviceList.get(position);
            DbUtils.getInstance().deleteDevice(deviceInformationEntity);
            mDeviceManageAdapter.deleteDevice(position);
            if (position == 0) {
                DeviceControllerService.mDeletingDeviceMacList.add(deviceInformationEntity.getMacAddress());
                changeDevice();
            }
            deviceList = DbUtils.getInstance().deviceInfoList(getUserId());
            if (deviceList == null || deviceList.size() == 0) {
                mActivityDeviceManageBinding.slNoDevice.setVisibility(View.VISIBLE);
            }
        }
    };
    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private final SwipeMenuCreator mSwipeMenuCreator = (swipeLeftMenu, swipeRightMenu, position) -> {
        List<DeviceInformationEntity> deviceList = DbUtils.getInstance().deviceInfoList(getUserId());
        if (deviceList != null && deviceList.size() > 0) {
            DeviceInformationEntity deviceInformationEntity = deviceList.get(position);
            if (deviceInformationEntity.getDeviceUpdate() != Device.DEVICE_UPDATE_UNDONE) {
                SwipeMenuItem competitionItem = new SwipeMenuItem(this)
                        .setWidth(AppUtils.dipToPx(this, 94))
                        .setBackgroundColor(Color.parseColor("#FFB91919"))
                        .setText(getResources().getString(R.string.delete_device))
                        .setTextColor(ContextCompat.getColor(this, R.color.white))
                        .setTextSize(13)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                swipeRightMenu.addMenuItem(competitionItem);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}