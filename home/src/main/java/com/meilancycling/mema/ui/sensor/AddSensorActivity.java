package com.meilancycling.mema.ui.sensor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanRecord;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.ble.sensor.JniBleController;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.databinding.ActivityAddSensorBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.SensorEntity;
import com.meilancycling.mema.service.SensorControllerService;
import com.meilancycling.mema.ui.adapter.AddSensorAdapter;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.work.SensorUploadWork;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 添加传感器
 *
 * @author lion
 */
public class AddSensorActivity extends BaseActivity implements View.OnClickListener {
    private ActivityAddSensorBinding mActivityAddSensorBinding;
    private AddSensorAdapter mAddSensorAdapter;
    private boolean isScan;
    /**
     * 默认轮径
     */
    private final int defaultWheelValue = 205000;
    private final int locationCode = 1;
    private final int bleCode = 2;
    private long clickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddSensorBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_sensor);
        clickTime = 0;
        initView();
        checkBlePermission();
    }

    private void initView() {
        isScan = false;
        mActivityAddSensorBinding.viewBack.setOnClickListener(this);
        mActivityAddSensorBinding.tvScan.setOnClickListener(this);
        mActivityAddSensorBinding.cbvConfirm.setBottomView(R.string.confirm, R.color.main_color, this);
        rotationAnimation();
        List<SensorEntity> sensorEntities = DbUtils.getInstance().querySensorEntityList(getUserId());
        mAddSensorAdapter = new AddSensorAdapter();
        mAddSensorAdapter.setSensorList(sensorEntities);
        mActivityAddSensorBinding.rvSensor.setLayoutManager(new GridLayoutManager(this, 2));
        mActivityAddSensorBinding.rvSensor.setAdapter(mAddSensorAdapter);
    }

    private void checkBlePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!AppUtils.isOpenLocationService(AddSensorActivity.this)) {
                new AlertDialog.Builder(AddSensorActivity.this)
                        .setTitle(R.string.prompt)
                        .setMessage(R.string.scan_device_permission)
                        .setNegativeButton(R.string.cancel,
                                (dialog, which) -> {
                                    dialog.dismiss();
                                    ToastUtils.show(AddSensorActivity.this, getString(R.string.openGPSFailure));
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

    private void checkBleStatus() {
        mAddSensorAdapter.clearSensor();
        if (BleManager.getInstance().isBlueEnable()) {
            scanDevice();
        } else {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, bleCode);
        }
    }

    private void scanDevice() {
        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
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
                int deviceType = getDeviceType(bleDevice);
                if (getDeviceType(bleDevice) != -1) {
                    SensorScanBean sensorScanBean = new SensorScanBean();
                    JniBleController bleController = JniBleController.getBleController();
                    if (deviceType == bleController.E_SENSOR_TYPE_SPD || deviceType == bleController.E_SENSOR_TYPE_CSC) {
                        sensorScanBean.setWheelValue(defaultWheelValue);
                    } else {
                        sensorScanBean.setWheelValue(-1);
                    }
                    sensorScanBean.setIsCheck(0);
                    sensorScanBean.setMacAddress(bleDevice.getMac());
                    sensorScanBean.setSensorName(bleDevice.getName());
                    sensorScanBean.setSensorType(getDeviceType(bleDevice));
                    mAddSensorAdapter.addSensor(sensorScanBean);
                }
            }
        });
    }

    /**
     * 旋转动画
     */
    private void rotationAnimation() {
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        mActivityAddSensorBinding.ivSensorScan.startAnimation(rotateAnimation);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_back:
                finish();
                break;
            case R.id.tv_scan:
                if (System.currentTimeMillis() - clickTime >= 3000) {
                    clickTime = System.currentTimeMillis();
                    if (isScan) {
                        mAddSensorAdapter.clearSensor();
                        try {
                            BleManager.getInstance().cancelScan();
                        } catch (Exception e) {
                        }
                    } else {
                        checkBlePermission();
                    }
                } else {
                    ToastUtils.show(this, getString(R.string.click_too_busy));
                }
                break;
            case R.id.tv_title:
                List<SensorScanBean> selectedDevice = mAddSensorAdapter.getSelectedDevice();
                if (selectedDevice.size() > 0) {
                    for (SensorScanBean sensorScanBean : selectedDevice) {
                        SensorEntity sensorEntity = new SensorEntity();
                        sensorEntity.setUserId(getUserId());
                        sensorEntity.setMacAddress(sensorScanBean.getMacAddress());
                        sensorEntity.setSensorName(sensorScanBean.getSensorName());
                        sensorEntity.setSensorType(sensorScanBean.getSensorType());
                        sensorEntity.setWheelValue(sensorScanBean.getWheelValue());
                        sensorEntity.setConnectStatus(SensorControllerService.SENSOR_STATUS_DISCONNECT);
                        DbUtils.getInstance().addSensorEntity(sensorEntity);
                        Intent intent = new Intent(BroadcastConstant.ACTION_ADD_SENSOR);
                        intent.putExtra(BroadcastConstant.BROADCAST_KEY, sensorScanBean.getMacAddress());
                        sendBroadcast(intent);
                        uploadSensorWork(selectedDevice);
                    }
                }
                finish();
                break;
            default:
        }
    }

    @SuppressLint("RestrictedApi")
    private void uploadSensorWork(List<SensorScanBean> sensorList) {
        String[] macAddress = new String[sensorList.size()];
        String[] sensorName = new String[sensorList.size()];
        Integer[] sensorType = new Integer[sensorList.size()];
        for (int i = 0; i < sensorList.size(); i++) {
            SensorScanBean sensorScanBean = sensorList.get(i);
            macAddress[i] = sensorScanBean.getMacAddress();
            sensorName[i] = sensorScanBean.getSensorName();
            sensorType[i] = sensorScanBean.getSensorType();
        }
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data sendData = new Data.Builder().
                put(SensorUploadWork.DATA_ADDRESS, macAddress).
                put(SensorUploadWork.DATA_NAME, sensorName).
                put(SensorUploadWork.DATA_TYPE, sensorType).
                putString(SensorUploadWork.SESSION_FLAG, getUserInfoEntity().getSession()).
                build();
        WorkManager.getInstance(this)
                .enqueue(new OneTimeWorkRequest.Builder(SensorUploadWork.class)
                        .setConstraints(constraints)
                        .setInputData(sendData)
                        .build());
    }

    /**
     * 获取设备类型
     */
    private int getDeviceType(BleDevice bleDevice) {
        JniBleController bleController = JniBleController.getBleController();
        int serviceFlag = -1;
        try {
            Class scanRecord = Class.forName("android.bluetooth.le.ScanRecord");
            Method parseFromBytes = scanRecord.getDeclaredMethod("parseFromBytes", byte[].class);
            ScanRecord scanData = (ScanRecord) parseFromBytes.invoke(scanRecord, bleDevice.getScanRecord());
            List<ParcelUuid> serviceUuids = scanData.getServiceUuids();
            if (null != serviceUuids && serviceUuids.size() > 0) {
                for (ParcelUuid serviceUuid : serviceUuids) {
                    if (SensorControllerService.heartRateService.equalsIgnoreCase(serviceUuid.getUuid().toString())) {
                        serviceFlag = bleController.E_SENSOR_TYPE_HRM;
                        break;
                    }
                    if (SensorControllerService.powerService.equalsIgnoreCase(serviceUuid.getUuid().toString())) {
                        serviceFlag = bleController.E_SENSOR_TYPE_POWER;
                    }
                    if (SensorControllerService.cadenceService.equalsIgnoreCase(serviceUuid.getUuid().toString())
                            && (serviceFlag != bleController.E_SENSOR_TYPE_POWER)) {
                        int appearance = getAppearance(bleDevice.getScanRecord());
                        int speedAndCadenceAppearance = 1157;
                        int speedAppearance = 1154;
                        int cadenceAppearance = 1155;
                        if (appearance == 0 || appearance == speedAndCadenceAppearance) {
                            serviceFlag = bleController.E_SENSOR_TYPE_CSC;
                        } else if (appearance == speedAppearance) {
                            serviceFlag = bleController.E_SENSOR_TYPE_SPD;
                        } else if (appearance == cadenceAppearance) {
                            serviceFlag = bleController.E_SENSOR_TYPE_CAD;
                        }
                    }
                }
                return serviceFlag;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceFlag;
    }

    private int getAppearance(byte[] data) {
        int appearance = 0;
        if (null == data) {
            return 0;
        }
        int currentPos = 0;
        int number = 0;
        while (number < data.length) {
            number++;
            byte datum = data[currentPos];
            if (datum == 0) {
                break;
            }
            byte datum1 = data[currentPos + 1];
            /*
             * appearance对应的type为0x19
             */
            if (datum1 == 25) {
                appearance = ((data[currentPos + 3] & 0xFF) << 8) + (data[currentPos + 2] & 0xFF);
                break;
            }
            currentPos = currentPos + datum + 1;
        }
        return appearance;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == locationCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (AppUtils.isOpenLocationService(AddSensorActivity.this)) {
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
        mActivityAddSensorBinding.ivSensorScan.clearAnimation();

        if (isScan) {
            try {
                BleManager.getInstance().cancelScan();
            } catch (Exception e) {
            }
        }
    }
}