package com.meilancycling.mema.ui.sensor;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.clj.fastble.BleManager;
import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.databinding.ActivitySensorListStatusBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.SensorEntity;
import com.meilancycling.mema.service.SensorControllerService;
import com.meilancycling.mema.ui.adapter.SensorListStatusAdapter;
import com.meilancycling.mema.ui.device.view.ModifySensorNameDialog;
import com.meilancycling.mema.ui.device.view.SensorDialog;
import com.meilancycling.mema.ui.sensor.view.TipDialog;

import java.util.List;

/**
 * 传感器列表
 *
 * @author lion
 */
public class SensorListStatusActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySensorListStatusBinding mActivitySensorListStatusBinding;
    private SensorListStatusAdapter mSensorListStatusAdapter;
    private SensorListServiceConn mSensorListServiceConn;
    private SensorControllerService mSensorControllerService;
    private SensorEntity currentSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySensorListStatusBinding = DataBindingUtil.setContentView(this, R.layout.activity_sensor_list_status);
        mActivitySensorListStatusBinding.viewBack.setOnClickListener(this);
        mActivitySensorListStatusBinding.cbvAddSensor.setBottomView(R.string.add_sensor, R.color.main_color, this);
        mSensorListStatusAdapter = new SensorListStatusAdapter();
        mActivitySensorListStatusBinding.rvSensor.setLayoutManager(new LinearLayoutManager(this));
        mActivitySensorListStatusBinding.rvSensor.setAdapter(mSensorListStatusAdapter);
        registerReceiver(sensorListReceiver, new IntentFilter(BroadcastConstant.ACTION_SENSOR_CHANGE));
        mSensorListStatusAdapter.setSensorClickListener(sensorEntity -> {
            SensorDialog sensorDialog = new SensorDialog(SensorListStatusActivity.this, sensorEntity.getSensorType(), 1);
            sensorDialog.show();
            sensorDialog.setSensorDialogClickListener(new SensorDialog.SensorDialogClickListener() {
                @Override
                public void sensorModifyName() {
                    ModifySensorNameDialog modifySensorNameDialog = new ModifySensorNameDialog(SensorListStatusActivity.this, sensorEntity.getSensorName(), false);
                    modifySensorNameDialog.show();
                    modifySensorNameDialog.setModifySensorNameCallback(sensorName -> {
                        sensorEntity.setSensorName(sensorName);
                        DbUtils.getInstance().updateSensor(sensorEntity);
                        showUi();
                    });
                }

                @Override
                public void sensorSetWheel() {
                    currentSensor = sensorEntity;
                    Intent intent = new Intent(SensorListStatusActivity.this, WheelValueActivity.class);
                    intent.putExtra("wheel", currentSensor.getWheelValue());
                    startActivityForResult(intent, 1);
                }

                @Override
                public void sensorDelete() {
                    if (mSensorControllerService != null) {
                        mSensorControllerService.deleteSensor(sensorEntity.getMacAddress());
                        MyApplication.mInstance.getDaoSession().getSensorEntityDao().delete(sensorEntity);
                        showUi();
                    }
                }
            });
        });
        List<SensorEntity> deviceList = DbUtils.getInstance().querySensorEntityList(getUserId());
        if (deviceList == null || deviceList.isEmpty()) {
            new TipDialog(this).show();
        }
        mSensorListServiceConn = new SensorListServiceConn();
        bindService(new Intent(this, SensorControllerService.class), mSensorListServiceConn, BIND_AUTO_CREATE);
    }

    private class SensorListServiceConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SensorControllerService.SensorServiceBinder sensorServiceBinder = (SensorControllerService.SensorServiceBinder) service;
            mSensorControllerService = sensorServiceBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    /**
     * 传感器广播
     */
    private final BroadcastReceiver sensorListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showUi();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sensorListReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showUi();
    }

    /**
     * 显示界面ui
     */
    private void showUi() {
        if (BleManager.getInstance().isBlueEnable()) {
            mActivitySensorListStatusBinding.groupBleClose.setVisibility(View.GONE);
        } else {
            mActivitySensorListStatusBinding.groupBleClose.setVisibility(View.VISIBLE);
        }
        List<SensorEntity> deviceList = DbUtils.getInstance().querySensorEntityList(getUserId());
        mSensorListStatusAdapter.setSensorData(deviceList);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_back:
                finish();
                break;
            case R.id.tv_title:
                startActivity(new Intent(this, AddSensorActivity.class));
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            int wheel = data.getIntExtra("wheel", 0);
            currentSensor.setWheelValue(wheel);
            DbUtils.getInstance().updateSensor(currentSensor);
            showUi();
            mSensorControllerService.changeSensorWheel(currentSensor.getMacAddress(), wheel);
        }
    }
}