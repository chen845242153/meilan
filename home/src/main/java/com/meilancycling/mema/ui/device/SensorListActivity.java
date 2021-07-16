package com.meilancycling.mema.ui.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.ble.bean.CommandEntity;
import com.meilancycling.mema.ble.command.BleCommandManager;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.databinding.ActivitySensorListBinding;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.adapter.RecycleViewDivider;
import com.meilancycling.mema.ui.adapter.SensorListAdapter;
import com.meilancycling.mema.ui.device.bean.SensorValueBean;
import com.meilancycling.mema.ui.device.view.ModifySensorNameDialog;
import com.meilancycling.mema.ui.device.view.SensorDialog;
import com.meilancycling.mema.ui.sensor.WheelValueActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 传感器列表
 *
 * @author lion
 */
public class SensorListActivity extends BaseActivity {
    private ActivitySensorListBinding mActivitySensorListBinding;
    private SensorListAdapter mSensorListAdapter;
    private DeviceInformationEntity deviceInformationEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySensorListBinding = DataBindingUtil.setContentView(this, R.layout.activity_sensor_list);
        mActivitySensorListBinding.llCommonBack.setOnClickListener(v -> finish());
        mSensorListAdapter = new SensorListAdapter(this);
        mActivitySensorListBinding.rvSensor.setLayoutManager(new LinearLayoutManager(this));
        mActivitySensorListBinding.rvSensor.setAdapter(mSensorListAdapter);
        mActivitySensorListBinding.rvSensor.addItemDecoration(new RecycleViewDivider());
        mActivitySensorListBinding.cbvSensorList.setBottomView(R.string.add_sensor, R.color.main_color, v -> startActivity(new Intent(SensorListActivity.this, ScanSensorActivity.class)));
        mSensorListAdapter.setSensorItemClickListener(position -> {
            List<SensorValueBean> sensorList = DeviceControllerService.mSensorList;
            SensorValueBean sensorValueBean = sensorList.get(position);
            SensorDialog sensorDialog = new SensorDialog(SensorListActivity.this, sensorValueBean.getType(), 0);
            sensorDialog.show();
            sensorDialog.setSensorDialogClickListener(new SensorDialog.SensorDialogClickListener() {
                @Override
                public void sensorModifyName() {
                    ModifySensorNameDialog modifySensorNameDialog = new ModifySensorNameDialog(SensorListActivity.this, sensorValueBean.getSensorName(), true);
                    modifySensorNameDialog.show();
                    modifySensorNameDialog.setModifySensorNameCallback(sensorName -> {
                        sensorValueBean.setSensorName(sensorName);
                        updateSensorList(sensorValueBean, position);
                        mSensorListAdapter.setSensorData(DeviceControllerService.mSensorList);
                        CommandEntity commandEntity = BleCommandManager.getInstance().setSensorName(sensorValueBean.getSensorKey(), sensorName);
                        sendCommandData(commandEntity);
                    });
                }

                @Override
                public void sensorSetWheel() {
                    currentPosition = position;
                    Intent intent = new Intent(SensorListActivity.this, WheelValueActivity.class);
                    intent.putExtra("wheel", sensorValueBean.getWheelValue());
                    startActivityForResult(intent, 1);
                }

                @Override
                public void sensorDelete() {
                    List<Integer> intList = new ArrayList<>();
                    intList.add(sensorValueBean.getSensorKey());
                    CommandEntity commandEntity = BleCommandManager.getInstance().deleteSensor(intList);
                    sendCommandData(commandEntity);
                    deleteSensorList(sensorValueBean);
                    mSensorListAdapter.setSensorData(DeviceControllerService.mSensorList);
                }
            });
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastConstant.ACTION_SENSOR_UPDATE);
        intentFilter.addAction(BroadcastConstant.ACTION_DEVICE_STATUS);
        registerReceiver(mReceiver, intentFilter);
        deviceInformationEntity =DeviceControllerService.currentDevice;
        initView();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BroadcastConstant.ACTION_DEVICE_STATUS.equals(action)) {
                if (DeviceControllerService.deviceStatus == Device.DEVICE_CONNECTING) {
                    DeviceSettingActivity.intoDeviceSetting(SensorListActivity.this, 0);
                    finish();
                }
            } else {
                mSensorListAdapter.setSensorData(DeviceControllerService.mSensorList);
            }
        }
    };


    private void initView() {
        switch (deviceInformationEntity.getProductNo()) {
            case Device.PRODUCT_NO_M1:
                mActivitySensorListBinding.tvDeviceName.setText(R.string.device_m1);
                break;
            case Device.PRODUCT_NO_M2:
                mActivitySensorListBinding.tvDeviceName.setText(R.string.device_m2);
                break;
            case Device.PRODUCT_NO_M4:
                mActivitySensorListBinding.tvDeviceName.setText(R.string.device_m4);
                break;
            default:
        }
        mSensorListAdapter.setSensorData(DeviceControllerService.mSensorList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private int currentPosition;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            int wheel = data.getIntExtra("wheel", 0);
            SensorValueBean sensorValueBean = DeviceControllerService.mSensorList.get(currentPosition);
            sensorValueBean.setWheelValue(wheel);
            updateSensorList(sensorValueBean, currentPosition);
            mSensorListAdapter.setSensorData(DeviceControllerService.mSensorList);
            CommandEntity commandEntity = BleCommandManager.getInstance().SetSensorValue(sensorValueBean.getSensorKey(), wheel);
            sendCommandData(commandEntity);
        }
    }
}