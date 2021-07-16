package com.meilancycling.mema.ui.device;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.ble.bean.CommandEntity;
import com.meilancycling.mema.ble.command.BleCommandManager;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.databinding.ActivityScanSensorBinding;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.adapter.SensorSearchAdapter;

/**
 * 传感器搜索
 *
 * @author lion
 */
public class ScanSensorActivity extends BaseActivity implements View.OnClickListener {
    private ActivityScanSensorBinding mActivityScanSensorBinding;
    private SensorSearchAdapter mSensorSearchAdapter;
    private DeviceInformationEntity deviceInformationEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityScanSensorBinding = DataBindingUtil.setContentView(this, R.layout.activity_scan_sensor);
        mActivityScanSensorBinding.llCommonBack.setOnClickListener(this);
        mActivityScanSensorBinding.tvSensor.setOnClickListener(this);
        mSensorSearchAdapter = new SensorSearchAdapter(this);
        mActivityScanSensorBinding.rvSensorScan.setLayoutManager(new GridLayoutManager(this, 2));
        mActivityScanSensorBinding.rvSensorScan.setAdapter(mSensorSearchAdapter);
        rotationAnimation();
        mActivityScanSensorBinding.cbvSensorScan.setBottomView(R.string.confirm, R.color.main_color, this);
        mSensorSearchAdapter.setSensorSearchItemClick(sensorKey -> mSensorSearchAdapter.updateSelect(sensorKey));
        registerReceiver(mReceiver, new IntentFilter(BroadcastConstant.ACTION_SCAN_SENSOR_UPDATE));
        CommandEntity commandEntity = BleCommandManager.getInstance().searchSensor();
        sendCommandData(commandEntity);
        mSensorSearchAdapter.setSensorData(DeviceControllerService.mScanList);
        deviceInformationEntity =DeviceControllerService.currentDevice;
        switch (deviceInformationEntity.getProductNo()) {
            case Device.PRODUCT_NO_M1:
                mActivityScanSensorBinding.tvDeviceName.setText(R.string.device_m1);
                break;
            case Device.PRODUCT_NO_M2:
                mActivityScanSensorBinding.tvDeviceName.setText(R.string.device_m2);
                break;
            case Device.PRODUCT_NO_M4:
                mActivityScanSensorBinding.tvDeviceName.setText(R.string.device_m4);
                break;
            default:
        }
    }

    /**
     * 旋转动画
     */
    private void rotationAnimation() {
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        mActivityScanSensorBinding.ivSensorScan.startAnimation(rotateAnimation);
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mSensorSearchAdapter.setSensorData(DeviceControllerService.mScanList);
        }
    };

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_common_back:
                clearScanList();
                CommandEntity commandEntity = BleCommandManager.getInstance().addSensor(null);
                sendCommandData(commandEntity);
                finish();
                break;
            case R.id.tv_sensor:
                mSensorSearchAdapter.clearData();
                clearScanList();
                commandEntity = BleCommandManager.getInstance().searchSensor();
                sendCommandData(commandEntity);
                break;
            case R.id.tv_title:
                clearScanList();
                commandEntity = BleCommandManager.getInstance().addSensor(mSensorSearchAdapter.getSelectList());
                sendCommandData(commandEntity);
                finish();
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}