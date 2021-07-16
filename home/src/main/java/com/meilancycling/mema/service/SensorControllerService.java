package com.meilancycling.mema.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.lion.common.eventbus.MessageEvent;
import com.lion.common.eventbus.sensor.SensorMessageType;
import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.ble.sensor.HrmZone;
import com.meilancycling.mema.ble.sensor.JniBleController;
import com.meilancycling.mema.ble.sensor.PowerZone;
import com.meilancycling.mema.ble.sensor.RealtimeBean;
import com.meilancycling.mema.ble.sensor.StatisticalBean;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.HeartZoneEntity;
import com.meilancycling.mema.db.PowerZoneEntity;
import com.meilancycling.mema.db.SensorEntity;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.db.WarningEntity;
import com.meilancycling.mema.db.greendao.UserInfoEntityDao;
import com.meilancycling.mema.ui.setting.WarningActivity;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 传感器服务
 *
 * @author lion
 */
public class SensorControllerService extends Service implements LocationListener {
    private final String TAG = "SensorControllerService";
    public static final String heartRateService = "0000180D-0000-1000-8000-00805f9b34fb";
    public static final String powerService = "00001818-0000-1000-8000-00805f9b34fb";
    public static final String cadenceService = "00001816-0000-1000-8000-00805f9b34fb";

    public static final String heartRateNotifyService = "00002A37-0000-1000-8000-00805f9b34fb";
    public static final String powerNotifyService = "00002A63-0000-1000-8000-00805f9b34fb";
    public static final String cadenceNotifyService = "00002A5B-0000-1000-8000-00805f9b34fb";
    /**
     * realtimeData 当前实时数据
     * sessionData  本次数据（平均速度）
     * lapData      本圈数据
     * lastLapData  上一圈
     */
    public static RealtimeBean mRealtimeBean = new RealtimeBean();
    public static StatisticalBean sessionData= new StatisticalBean();
    public static StatisticalBean lapData = new StatisticalBean();
    public static StatisticalBean lastLapData = new StatisticalBean();
    private long userId;
    public static List<Double> latitudeList;
    public static List<Double> longitudeList;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0.5f, this);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new SensorServiceBinder();
    }

    public static Location mLocation = null;

    public static double startLatitude = 0;
    public static double startLongitude = 0;

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        if (motionState != 0 && motionType == bleController.E_CYCLING_MODE_OUTDOOR) {
            longitudeList.add(location.getLongitude());
            latitudeList.add(location.getLatitude());
        }
        EventBus.getDefault().post(new MessageEvent(location, SensorMessageType.ACTION_LOCATION_CHANGE));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private byte[] getGpsData(double latitude, double longitude, float speed, float baring) {
        byte[] bytes = new byte[12];
        long l = Math.round(AppUtils.multiplyDouble(longitude, 10000000));
        long lat = Math.round(AppUtils.multiplyDouble(latitude, 10000000));
        long sp = Math.round(AppUtils.multiplyDouble(speed, 10));
        int bar = Math.round(baring);
        bytes[0] = (byte) (l & 0Xff);
        bytes[1] = (byte) ((l >> 8) & 0Xff);
        bytes[2] = (byte) ((l >> 16) & 0Xff);
        bytes[3] = (byte) ((l >> 24) & 0Xff);
        bytes[4] = (byte) (lat & 0Xff);
        bytes[5] = (byte) ((lat >> 8) & 0Xff);
        bytes[6] = (byte) ((lat >> 16) & 0Xff);
        bytes[7] = (byte) ((lat >> 24) & 0Xff);
        bytes[8] = (byte) (sp & 0Xff);
        bytes[9] = (byte) ((sp >> 8) & 0Xff);
        bytes[10] = (byte) (bar & 0Xff);
        bytes[11] = (byte) ((bar >> 8) & 0Xff);

        return bytes;
    }

    private byte[] getAltitudeData(double altitude) {
        long alt = Math.round(AppUtils.multiplyDouble(altitude, 10));
        byte[] bytes = new byte[10];
        bytes[0] = (byte) (32767 & 0Xff);
        bytes[1] = (byte) ((32767 >> 8) & 0Xff);
        bytes[2] = (byte) (alt & 0Xff);
        bytes[3] = (byte) ((alt >> 8) & 0Xff);
        bytes[4] = (byte) ((alt >> 16) & 0Xff);
        bytes[5] = (byte) ((alt >> 24) & 0Xff);
        bytes[6] = (byte) 0Xff;
        bytes[7] = (byte) 0Xff;
        bytes[8] = (byte) 0Xff;
        bytes[9] = (byte) 0Xff;
        return bytes;
    }

    public class SensorServiceBinder extends Binder {
        public SensorControllerService getService() {
            return SensorControllerService.this;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler tickHandler;
    @SuppressLint("HandlerLeak")
    private Handler mHandler;
    private final int initWhat = 0;
    private final int unInitWhat = 1;
    private final int everyWhat = 2;
    private final int heartRateWhat = 3;
    private final int speedWhat = 4;
    private final int cadenceWhat = 5;
    private final int powerWhat = 6;
    private final int speedAndCadenceWhat = 7;
    private final int sensorStatusWhat = 8;
    private final int wheelWhat = 9;
    private final int typeWhat = 10;
    private WarningEntity mWarningEntity;
    private JniBleController bleController;
    private ConnectCallback mConnectCallback;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        latitudeList = new ArrayList<>();
        longitudeList = new ArrayList<>();

        mConnectCallback = new ConnectCallback();
        isStart = true;
        initData();
        HandlerThread sensor = new HandlerThread("sensor");
        sensor.start();
        bleController = JniBleController.getBleController();
        HandlerThread tick = new HandlerThread("tick");
        tick.start();
        //心跳包
        tickHandler = new Handler(tick.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                JniBleController.getBleController().sensorCIITickUpdate(20);
                tickHandler.sendEmptyMessageDelayed(0, 20);
            }
        };
        //sensor数据
        mHandler = new Handler(sensor.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case initWhat:
                        motionType = bleController.E_CYCLING_MODE_INDOOR;
                        bleController.sensorInit();
                        bleController.sensorProcessEverySecond();
                        mHandler.sendEmptyMessageDelayed(everyWhat, 1000);
                        UserInfoEntity userInfoEntity = MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().queryBuilder()
                                .where(UserInfoEntityDao.Properties.UserId.eq(userId))
                                .unique();
                        if (userInfoEntity != null) {
                            bleController.sensorParametersConfigWeight(userInfoEntity.getWeight());
                        } else {
                            bleController.sensorParametersConfigWeight(60 * 100);
                        }
                        JniBleController.getBleController().sensorSetMode(bleController.E_CYCLING_MODE_INDOOR);
                        bleController.sensorGetAllData(mRealtimeBean, sessionData, lapData, lastLapData);
                        break;
                    case unInitWhat:
                        JniBleController.getBleController().sensorUnInit();
                        sensor.quit();
                        tick.quit();
                        break;
                    case everyWhat:
                        bleController.sensorProcessEverySecond();
                        bleController.sensorGetAllData(mRealtimeBean, sessionData, lapData, lastLapData);
                        if (motionType == bleController.E_CYCLING_MODE_OUTDOOR) {
                            if (SensorControllerService.mRealtimeBean.getSpeed() == 0xFFFF) {
                                SensorControllerService.mRealtimeBean.setSpeed(0);
                            }
                            if (motionState != 0) {
                                if (SensorControllerService.sessionData.getAvg_speed() == 0xFFFF) {
                                    SensorControllerService.sessionData.setAvg_speed(0);
                                }
                                if (SensorControllerService.sessionData.getMax_speed() == 0xFFFF) {
                                    SensorControllerService.sessionData.setMax_speed(0);
                                }
                            }

                            if (SensorControllerService.lapData.getMax_speed() == 0xFFFF) {
                                SensorControllerService.lapData.setMax_speed(0);
                            }
                            if (SensorControllerService.lastLapData.getMax_speed() == 0xFFFF) {
                                SensorControllerService.lastLapData.setMax_speed(0);
                            }
                        }
                        mHandler.sendEmptyMessageDelayed(everyWhat, 1000);
                        if (motionState == 1) {
                            showWarningPopup();
                        }
                        if (null != mLocation && motionType == bleController.E_CYCLING_MODE_OUTDOOR) {
                            byte[] gpsData = getGpsData(mLocation.getLatitude(), mLocation.getLongitude(), mLocation.getSpeed(), mLocation.getBearing());
                            JniBleController.getBleController().sensorDataTransfer(JniBleController.getBleController().E_SENSOR_TYPE_GPS, gpsData, gpsData.length);
                            byte[] altitudeData = getAltitudeData(mLocation.getAltitude());
                            JniBleController.getBleController().sensorDataTransfer(JniBleController.getBleController().E_SENSOR_TYPE_ENVIRONMENT, altitudeData, altitudeData.length);
                        }
                        break;
                    case heartRateWhat:
                        byte[] data = (byte[]) msg.obj;
                        bleController.sensorDataTransfer(bleController.E_SENSOR_TYPE_HRM, data, data.length);
                        break;
                    case speedWhat:
                        data = (byte[]) msg.obj;
                        bleController.sensorDataTransfer(bleController.E_SENSOR_TYPE_SPD, data, data.length);
                        break;
                    case cadenceWhat:
                        data = (byte[]) msg.obj;
                        bleController.sensorDataTransfer(bleController.E_SENSOR_TYPE_CAD, data, data.length);
                        break;
                    case powerWhat:
                        data = (byte[]) msg.obj;
                        bleController.sensorDataTransfer(bleController.E_SENSOR_TYPE_POWER, data, data.length);
                        break;
                    case speedAndCadenceWhat:
                        data = (byte[]) msg.obj;
                        bleController.sensorDataTransfer(bleController.E_SENSOR_TYPE_CSC, data, data.length);
                        break;
                    case sensorStatusWhat:
                        JniBleController.getBleController().sensorStatusUpdate(msg.arg1, msg.arg2 != 0);
                        break;
                    case wheelWhat:
                        if (msg.arg1 != 0) {
                            JniBleController.getBleController().sensorParametersConfigCircumference(msg.arg1 / 100);
                        }
                        break;
                    case typeWhat:
                        JniBleController.getBleController().sensorSetMode(msg.arg1);
                        break;
                    default:
                }
            }
        };
        mHandler.sendEmptyMessage(initWhat);
        tickHandler.sendEmptyMessage(0);
        registerReceiver(baseReceiver, sensorFilter());
        initSensor();
        mWarningEntity = DbUtils.getInstance().queryWarningEntity(userId);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 连接sensor
     */
    private void initSensor() {
        deviceMap.clear();
        if (BleManager.getInstance().isBlueEnable()) {
            List<SensorEntity> deviceList = DbUtils.getInstance().querySensorEntityList(userId);
            if (null != deviceList && deviceList.size() > 0) {
                for (SensorEntity sensorEntity : deviceList) {
                    //初始化设备状态
                    int type = setSensorType(0, sensorEntity.getSensorType());
                    deviceMap.put(sensorEntity.getMacAddress(), settSensorConnectStatus(type, SENSOR_STATUS_DISCONNECT));
                    sensorEntity.setConnectStatus(SENSOR_STATUS_DISCONNECT);
                    DbUtils.getInstance().updateSensor(sensorEntity);
                }
            }
            connectSensor();
        }
    }

    /**
     * 添加传感器
     */
    public void addSensor(String macAddress) {
        if (!TextUtils.isEmpty(macAddress)) {
            SensorEntity sensorEntity = DbUtils.getInstance().querySensorEntity(userId, macAddress);
            int type = setSensorType(0, sensorEntity.getSensorType());
            deviceMap.put(sensorEntity.getMacAddress(), settSensorConnectStatus(type, SENSOR_STATUS_DISCONNECT));
            if (getSensorConnectStatus(type) == SENSOR_STATUS_DISCONNECT
                    && BleManager.getInstance().isBlueEnable()
                    && !checkIsConnect(getSensorType(type))) {
                BleManager.getInstance().connect(macAddress, mConnectCallback);
            }
        }
    }

    /**
     * 删除传感器
     */
    public void deleteSensor(String mac) {
        Integer integer = deviceMap.get(mac);
        if (integer != null) {
            if (getSensorConnectStatus(integer) == SENSOR_STATUS_CONNECT) {
                disconnectDevice(mac);
                Message message = mHandler.obtainMessage();
                message.what = sensorStatusWhat;
                message.arg1 = getSensorType(integer);
                message.arg2 = 1;
                mHandler.sendMessage(message);
            }
            deviceMap.remove(mac);
        }
        connectSensor();
    }

    /**
     * 修改sensor轮径
     */
    public void changeSensorWheel(String mac, int wheel) {
        Integer integer = deviceMap.get(mac);
        if (getSensorConnectStatus(integer) == SENSOR_STATUS_CONNECT) {
            Message message = mHandler.obtainMessage();
            message.what = wheelWhat;
            message.arg1 = wheel;
            mHandler.sendMessage(message);
        }
    }

    /**
     * 连接传感器
     */
    private void connectSensor() {
        for (Map.Entry<String, Integer> stringIntegerEntry : deviceMap.entrySet()) {
            Integer value = stringIntegerEntry.getValue();
            if (getSensorConnectStatus(value) == SENSOR_STATUS_DISCONNECT
                    && BleManager.getInstance().isBlueEnable()
                    && !checkIsConnect(getSensorType(value))) {
                BleManager.getInstance().connect(stringIntegerEntry.getKey(), mConnectCallback);
            }
        }
    }

    public class ConnectCallback extends BleGattCallback {
        @Override
        public void onStartConnect() {
            Log.i(TAG, "onStartConnect");
        }

        @Override
        public void onConnectFail(BleDevice bleDevice, BleException exception) {
            Log.i(TAG, "onConnectFail");
            Integer data = deviceMap.get(bleDevice.getMac());
            if (data != null && getSensorConnectStatus(data) == SENSOR_STATUS_DISCONNECT
                    && BleManager.getInstance().isBlueEnable()
                    && !checkIsConnect(getSensorType(data))) {
                Message message = mMainHandler.obtainMessage();
                message.what = connectWhat;
                message.obj = bleDevice.getMac();
                mMainHandler.sendMessageDelayed(message, 3000);
            }
        }

        @Override
        public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
            Log.i(TAG, "onConnectSuccess");
            Integer integer = deviceMap.get(bleDevice.getMac());
            if (integer != null) {
                switch (getSensorType(integer)) {
                    case 0:
                        if (checkIsConnect(bleController.E_SENSOR_TYPE_HRM)) {
                            disconnectDevice(bleDevice.getMac());
                        } else {
                            heartRateNotify(bleDevice);
                        }
                        break;
                    case 4:
                        if (checkIsConnect(bleController.E_SENSOR_TYPE_POWER)) {
                            disconnectDevice(bleDevice.getMac());
                        } else {
                            powerNotify(bleDevice);
                        }
                        break;
                    case 3:
                        if (checkIsConnect(bleController.E_SENSOR_TYPE_SPD)) {
                            disconnectDevice(bleDevice.getMac());
                        } else {
                            speedNotify(bleDevice);
                        }
                        break;
                    case 2:
                        if (checkIsConnect(bleController.E_SENSOR_TYPE_CAD)) {
                            disconnectDevice(bleDevice.getMac());
                        } else {
                            cadenceNotify(bleDevice);
                        }
                        break;
                    case 1:
                        if (checkIsConnect(bleController.E_SENSOR_TYPE_CSC)) {
                            disconnectDevice(bleDevice.getMac());
                        } else {
                            cadenceAndSpeedNotify(bleDevice);
                        }
                        break;
                    default:
                }
            }

        }

        @Override
        public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
            String disConnectMac = device.getMac();
            if (disConnectMac != null) {
                Integer integer = deviceMap.get(disConnectMac);
                if (integer != null) {
                    Message message = mHandler.obtainMessage();
                    message.what = sensorStatusWhat;
                    message.arg1 = getSensorType(integer);
                    message.arg2 = 0;
                    mHandler.sendMessage(message);
                    deviceMap.put(disConnectMac, settSensorConnectStatus(integer, SENSOR_STATUS_DISCONNECT));
                }
                DbUtils.getInstance().updateSensorStatus(userId, disConnectMac, SENSOR_STATUS_DISCONNECT);
                noticeSensorChange();
            }
        }
    }

    /**
     * 传感器广播
     */
    private final BroadcastReceiver baseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())) {
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    mMainHandler.removeCallbacksAndMessages(null);
                    noticeSensorChange();
                    mMainHandler.sendEmptyMessageDelayed(statusWhat, 3000);
                    break;
                case BroadcastConstant.ACTION_ADD_SENSOR:
                    addSensor(intent.getStringExtra(BroadcastConstant.BROADCAST_KEY));
                    break;
                default:
            }
        }
    };
    private boolean isStart;
    private final int statusWhat = 1;
    private final int connectWhat = 2;
    @SuppressLint("HandlerLeak")
    private final Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == statusWhat) {
                mHandler.removeCallbacksAndMessages(null);
                if (BleManager.getInstance().isBlueEnable() && isStart) {
                    mMainHandler.removeMessages(statusWhat);
                    connectSensor();
                }
            } else if (msg.what == connectWhat) {
                String mac = (String) msg.obj;
                if (BleManager.getInstance().isBlueEnable() && !TextUtils.isEmpty(mac) && isStart) {
                    BleManager.getInstance().connect(mac, mConnectCallback);
                }
            }
        }
    };

    /**
     * 检查是否已经连接
     */
    private boolean checkIsConnect(int targetType) {
        boolean isConnect = false;
        for (Map.Entry<String, Integer> stringIntegerEntry : deviceMap.entrySet()) {
            Integer value = stringIntegerEntry.getValue();
            if (getSensorType(value) == targetType && getSensorConnectStatus(value) == SENSOR_STATUS_CONNECT) {
                isConnect = true;
                break;
            }
        }
        return isConnect;
    }

    /**
     * 通知传感器发生变化
     */
    private void noticeSensorChange() {
        sendBroadcast(new Intent(BroadcastConstant.ACTION_SENSOR_CHANGE));
    }

    /**
     * 获取传感器连接状态
     */
    private int getSensorConnectStatus(Integer data) {
        return (data & 0xF0) >> 4;
    }

    /**
     * 设置传感连接状态
     */
    private int settSensorConnectStatus(Integer data, int status) {
        return (data & 0x0F) | (status << 4);
    }

    /**
     * 获取传感器类型
     */
    private int getSensorType(Integer data) {
        return data & 0x0F;
    }

    /**
     * 设置传感器类型
     */
    private int setSensorType(Integer data, int type) {
        return (data & 0xF0) | type;
    }

    /**
     * 运动状态
     * 0 运动未开始
     * 1 正在运动
     * 2 暂停运动
     */
    private int motionState;

    /**
     * 运动类型
     */
    private int motionType;

    /***
     * 传感器列表
     * 设备类型 bit0-bit3
     * 设备状态 bit4-bit7
     */
    public static HashMap<String, Integer> deviceMap = new HashMap<>();
    /**
     * 传感器连接状态
     * 0 未连接
     * 1 已连接
     */
    public static final int SENSOR_STATUS_DISCONNECT = 0;
    public static final int SENSOR_STATUS_CONNECT = 1;

    private void initData() {
        motionState = 0;
        JniBleController.mList.clear();
        userId = SPUtils.getLong(this, Config.CURRENT_USER);

    }

    /**
     * 显示预警弹窗
     */
    private void showWarningPopup() {
        //时间目标
        if (mWarningEntity.getTimeSwitch() == WarningActivity.WARNING_OPEN &&
                sessionData.getActivity_time() >= mWarningEntity.getTimeValue() * 60) {
            EventBus.getDefault().post(new MessageEvent(sessionData.getActivity_time(), SensorMessageType.ACTION_TIME_WARMING));
        }
        //距离目标
        if (mWarningEntity.getDistanceSwitch() == WarningActivity.WARNING_OPEN &&
                sessionData.getDist_travelled() > mWarningEntity.getDistanceValue()) {
            EventBus.getDefault().post(new MessageEvent(sessionData.getDist_travelled(), SensorMessageType.ACTION_DISTANCE_WARMING));
        }
        //速度预警
        //最高值
        if (mWarningEntity.getMaxSpeedSwitch() == WarningActivity.WARNING_OPEN &&
                mWarningEntity.getMaxSpeedValue() < mRealtimeBean.getSpeed() * 100 &&
                mRealtimeBean.getSpeed() != 0 &&
                mRealtimeBean.getSpeed() != 0xFFFF) {
            EventBus.getDefault().post(new MessageEvent((double) mRealtimeBean.getSpeed() / 10, SensorMessageType.ACTION_SPEED_WARMING));
        }
        //最小值
        if (mWarningEntity.getMinSpeedSwitch() == WarningActivity.WARNING_OPEN &&
                mWarningEntity.getMinSpeedValue() > mRealtimeBean.getSpeed() * 100 &&
                mRealtimeBean.getSpeed() != 0 &&
                mRealtimeBean.getSpeed() != 0xFFFF) {
            EventBus.getDefault().post(new MessageEvent((double) mRealtimeBean.getSpeed() / 10, SensorMessageType.ACTION_SPEED_LOW_WARMING));
        }
        //踏频预警
        //最高值
        if (mWarningEntity.getMaxCadenceSwitch() == WarningActivity.WARNING_OPEN &&
                mWarningEntity.getMaxCadenceValue() < mRealtimeBean.getCadence() &&
                mRealtimeBean.getCadence() != 0 &&
                mRealtimeBean.getCadence() != 0xFFFF) {
            EventBus.getDefault().post(new MessageEvent(mRealtimeBean.getCadence(), SensorMessageType.ACTION_CADENCE_WARMING));
        }
        //最小值
        if (mWarningEntity.getMinCadenceSwitch() == WarningActivity.WARNING_OPEN &&
                mWarningEntity.getMinCadenceValue() > mRealtimeBean.getCadence() &&
                mRealtimeBean.getCadence() != 0 &&
                mRealtimeBean.getCadence() != 0xFFFF) {
            EventBus.getDefault().post(new MessageEvent(mRealtimeBean.getCadence(), SensorMessageType.ACTION_CADENCE_LOW_WARMING));
        }
        //心率预警
        //最高值
        if (mWarningEntity.getMaxHeartSwitch() == WarningActivity.WARNING_OPEN &&
                mWarningEntity.getMaxHeartValue() < mRealtimeBean.getHrm() &&
                mRealtimeBean.getHrm() != 0 &&
                mRealtimeBean.getHrm() != 0xFFFF) {
            EventBus.getDefault().post(new MessageEvent(mRealtimeBean.getHrm(), SensorMessageType.ACTION_HRM_WARMING));
        }
        //最小值
        if (mWarningEntity.getMinHeartSwitch() == WarningActivity.WARNING_OPEN &&
                mWarningEntity.getMinHeartValue() > mRealtimeBean.getCadence() &&
                mRealtimeBean.getHrm() != 0 &&
                mRealtimeBean.getHrm() != 0xFFFF) {
            EventBus.getDefault().post(new MessageEvent(mRealtimeBean.getHrm(), SensorMessageType.ACTION_HRM_LOW_WARMING));
        }
        //功率预警
        //最高值
        if (mWarningEntity.getMaxPowerSwitch() == WarningActivity.WARNING_OPEN &&
                mWarningEntity.getMaxPowerValue() < mRealtimeBean.getPower() &&
                mRealtimeBean.getPower() != 0 &&
                mRealtimeBean.getPower() != 0xFFFF) {
            EventBus.getDefault().post(new MessageEvent(mRealtimeBean.getPower(), SensorMessageType.ACTION_POWER_WARMING));
        }
        //最小值
        if (mWarningEntity.getMinPowerSwitch() == WarningActivity.WARNING_OPEN &&
                mWarningEntity.getMinPowerValue() > mRealtimeBean.getPower() &&
                mRealtimeBean.getPower() != 0 &&
                mRealtimeBean.getPower() != 0xFFFF) {
            EventBus.getDefault().post(new MessageEvent(mRealtimeBean.getPower(), SensorMessageType.ACTION_POWER_LOW_WARMING));
        }
    }

    private IntentFilter sensorFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BroadcastConstant.ACTION_CONNECT_SUCCESS);
        intentFilter.addAction(BroadcastConstant.ACTION_DISCONNECTED);
        intentFilter.addAction(BroadcastConstant.ACTION_ADD_SENSOR);
        return intentFilter;
    }

    /**
     * 检查数据是否需要搜索
     * 四种类型传感器 二合一当成两个传感器处理
     */
    private boolean checkConnectData() {
        // 0 不存在   1 已连接  2 未连接
        int hrm = 0;
        int power = 0;
        int speed = 0;
        int cadence = 0;
        for (Map.Entry<String, Integer> stringIntegerEntry : deviceMap.entrySet()) {
            Integer value = stringIntegerEntry.getValue();
            switch (getSensorType(value)) {
                case 0:
                    if (getSensorConnectStatus(value) == SENSOR_STATUS_DISCONNECT) {
                        if (hrm != 1) {
                            hrm = 2;
                        }
                    } else {
                        hrm = 1;
                    }
                    break;
                case 1:
                    if (getSensorConnectStatus(value) == SENSOR_STATUS_DISCONNECT) {
                        if (speed != 1) {
                            speed = 2;
                        }
                        if (cadence != 1) {
                            cadence = 2;
                        }
                    } else {
                        speed = 1;
                        cadence = 1;
                    }
                    break;
                case 2:
                    if (getSensorConnectStatus(value) == SENSOR_STATUS_DISCONNECT) {
                        if (cadence != 1) {
                            cadence = 2;
                        }
                    } else {
                        cadence = 1;
                    }
                    break;
                case 3:
                    if (getSensorConnectStatus(value) == SENSOR_STATUS_DISCONNECT) {
                        if (speed != 1) {
                            speed = 2;
                        }
                    } else {
                        speed = 1;
                    }
                    break;
                case 4:
                    if (getSensorConnectStatus(value) == SENSOR_STATUS_DISCONNECT) {
                        if (power != 1) {
                            power = 2;
                        }
                    } else {
                        power = 1;
                    }
                    break;
                default:
            }
        }
        return hrm == 2 || power == 2 || speed == 2 || cadence == 2;
    }

    /**
     * 订阅成功
     */
    private void notifySuccess(BleDevice bleDevice) {
        Integer integer = deviceMap.get(bleDevice.getMac());
        if (integer != null) {
            deviceMap.put(bleDevice.getMac(), settSensorConnectStatus(integer, SENSOR_STATUS_CONNECT));
            DbUtils.getInstance().updateSensorStatus(userId, bleDevice.getMac(), SENSOR_STATUS_CONNECT);
            noticeSensorChange();
        }
    }

    /**
     * 订阅心率
     */
    private void heartRateNotify(BleDevice bleDevice) {
        BleManager.getInstance().notify(
                bleDevice,
                heartRateService,
                heartRateNotifyService,
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        Message message = mHandler.obtainMessage();
                        message.what = sensorStatusWhat;
                        message.arg1 = bleController.E_SENSOR_TYPE_HRM;
                        message.arg2 = 1;
                        mHandler.sendMessage(message);
                        if (checkIsConnect(bleController.E_SENSOR_TYPE_HRM)) {
                            BleManager.getInstance().disconnect(bleDevice);
                        } else {
                            notifySuccess(bleDevice);
                        }
                        connectSensor();
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        BleManager.getInstance().disconnect(bleDevice);
                        connectSensor();
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        Integer integer = deviceMap.get(bleDevice.getMac());
                        if (null != integer) {
                            Message message = mHandler.obtainMessage();
                            message.what = heartRateWhat;
                            message.obj = data;
                            mHandler.sendMessage(message);
                        }
                    }
                });
    }

    /**
     * 订阅功率
     */
    private void powerNotify(BleDevice bleDevice) {
        BleManager.getInstance().notify(
                bleDevice,
                powerService,
                powerNotifyService,
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        Message message = mHandler.obtainMessage();
                        message.what = sensorStatusWhat;
                        message.arg1 = bleController.E_SENSOR_TYPE_POWER;
                        message.arg2 = 1;
                        mHandler.sendMessage(message);
                        if (checkIsConnect(bleController.E_SENSOR_TYPE_POWER)) {
                            BleManager.getInstance().disconnect(bleDevice);
                        } else {
                            notifySuccess(bleDevice);
                        }
                        connectSensor();
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        BleManager.getInstance().disconnect(bleDevice);
                        connectSensor();
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        Integer integer = deviceMap.get(bleDevice.getMac());
                        if (null != integer) {
                            Message message = mHandler.obtainMessage();
                            message.what = powerWhat;
                            message.obj = data;
                            mHandler.sendMessage(message);
                        }
                    }
                });
    }

    /**
     * 订阅速度
     */
    private void speedNotify(BleDevice bleDevice) {
        BleManager.getInstance().notify(
                bleDevice,
                cadenceService,
                cadenceNotifyService,
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        Message message = mHandler.obtainMessage();
                        message.what = sensorStatusWhat;
                        message.arg1 = bleController.E_SENSOR_TYPE_SPD;
                        message.arg2 = 1;
                        mHandler.sendMessage(message);
                        if (checkIsConnect(bleController.E_SENSOR_TYPE_SPD) || checkIsConnect(bleController.E_SENSOR_TYPE_CSC)) {
                            BleManager.getInstance().disconnect(bleDevice);
                        } else {
                            notifySuccess(bleDevice);
                            SensorEntity sensorEntity = DbUtils.getInstance().querySensorEntity(userId, bleDevice.getMac());
                            message = mHandler.obtainMessage();
                            message.what = wheelWhat;
                            message.arg1 = sensorEntity.getWheelValue();
                            mHandler.sendMessage(message);
                        }
                        connectSensor();
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        BleManager.getInstance().disconnect(bleDevice);
                        connectSensor();
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        Integer integer = deviceMap.get(bleDevice.getMac());
                        if (null != integer) {
                            int sensorTypeBaseData = JniBleController.getBleController().getSensorTypeBaseData(bleController.E_SENSOR_TYPE_SPD, data, data.length);
                            if (sensorTypeBaseData != bleController.E_SENSOR_TYPE_SPD) {
                                int type = setSensorType(integer, sensorTypeBaseData);
                                deviceMap.put(bleDevice.getMac(), type);
                                DbUtils.getInstance().updateSensorType(userId, bleDevice.getMac(), sensorTypeBaseData);
                                noticeSensorChange();
                                BleManager.getInstance().disconnect(bleDevice);
                                connectSensor();
                            } else {
                                Message message = mHandler.obtainMessage();
                                message.what = speedWhat;
                                message.obj = data;
                                mHandler.sendMessage(message);
                            }
                        }
                    }
                });
    }

    /**
     * 订阅踏频
     */
    private void cadenceNotify(BleDevice bleDevice) {
        BleManager.getInstance().notify(
                bleDevice,
                cadenceService,
                cadenceNotifyService,
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        Message message = mHandler.obtainMessage();
                        message.what = sensorStatusWhat;
                        message.arg1 = bleController.E_SENSOR_TYPE_CAD;
                        message.arg2 = 1;
                        mHandler.sendMessage(message);
                        if (checkIsConnect(bleController.E_SENSOR_TYPE_CAD) || checkIsConnect(bleController.E_SENSOR_TYPE_CSC)) {
                            BleManager.getInstance().disconnect(bleDevice);
                        } else {
                            notifySuccess(bleDevice);
                        }
                        connectSensor();
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        BleManager.getInstance().disconnect(bleDevice);
                        connectSensor();
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        Integer integer = deviceMap.get(bleDevice.getMac());
                        if (null != integer) {
                            int sensorTypeBaseData = JniBleController.getBleController().getSensorTypeBaseData(bleController.E_SENSOR_TYPE_CAD, data, data.length);
                            if (sensorTypeBaseData != bleController.E_SENSOR_TYPE_CAD) {
                                int type = setSensorType(integer, sensorTypeBaseData);
                                deviceMap.put(bleDevice.getMac(), type);
                                DbUtils.getInstance().updateSensorType(userId, bleDevice.getMac(), sensorTypeBaseData);
                                noticeSensorChange();
                                BleManager.getInstance().disconnect(bleDevice);
                                connectSensor();
                            } else {
                                Message message = mHandler.obtainMessage();
                                message.what = cadenceWhat;
                                message.obj = data;
                                mHandler.sendMessage(message);
                            }
                        }
                    }
                });
    }

    /**
     * 订阅速度和踏频
     */
    private void cadenceAndSpeedNotify(BleDevice bleDevice) {
        BleManager.getInstance().notify(
                bleDevice,
                cadenceService,
                cadenceNotifyService,
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        Message message = mHandler.obtainMessage();
                        message.what = sensorStatusWhat;
                        message.arg1 = bleController.E_SENSOR_TYPE_CSC;
                        message.arg2 = 1;
                        mHandler.sendMessage(message);
                        for (Map.Entry<String, Integer> stringIntegerEntry : deviceMap.entrySet()) {
                            Integer value = stringIntegerEntry.getValue();
                            if (getSensorType(value) == bleController.E_SENSOR_TYPE_CAD && getSensorConnectStatus(value) == SENSOR_STATUS_CONNECT) {
                                disconnectDevice(stringIntegerEntry.getKey());
                            }
                            if (getSensorType(value) == bleController.E_SENSOR_TYPE_SPD && getSensorConnectStatus(value) == SENSOR_STATUS_CONNECT) {
                                disconnectDevice(stringIntegerEntry.getKey());
                            }
                        }
                        if (checkIsConnect(bleController.E_SENSOR_TYPE_CSC)) {
                            BleManager.getInstance().disconnect(bleDevice);
                        } else {
                            notifySuccess(bleDevice);
                            SensorEntity sensorEntity = DbUtils.getInstance().querySensorEntity(userId, bleDevice.getMac());
                            message = mHandler.obtainMessage();
                            message.what = wheelWhat;
                            message.arg1 = sensorEntity.getWheelValue();
                            mHandler.sendMessage(message);
                        }
                        connectSensor();
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        BleManager.getInstance().disconnect(bleDevice);
                        connectSensor();
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        Integer integer = deviceMap.get(bleDevice.getMac());
                        if (null != integer) {
                            int sensorTypeBaseData = JniBleController.getBleController().getSensorTypeBaseData(bleController.E_SENSOR_TYPE_CSC, data, data.length);
                            if (sensorTypeBaseData != bleController.E_SENSOR_TYPE_CSC) {
                                int type = setSensorType(integer, sensorTypeBaseData);
                                deviceMap.put(bleDevice.getMac(), type);
                                DbUtils.getInstance().updateSensorType(userId, bleDevice.getMac(), sensorTypeBaseData);
                                noticeSensorChange();
                                BleManager.getInstance().disconnect(bleDevice);
                                connectSensor();
                            } else {
                                Message message = mHandler.obtainMessage();
                                message.what = speedAndCadenceWhat;
                                message.obj = data;
                                mHandler.sendMessage(message);
                            }
                        }
                    }
                });
    }

    /**
     * 开始运动
     */
    public void setInMotion(PowerZoneEntity powerZoneEntity, HeartZoneEntity heartZoneEntity) {
        PowerZone powerZone = new PowerZone();
        powerZone.setFtp(powerZoneEntity.getValue());
        powerZone.setZone2(powerZoneEntity.getZoneValue2());
        powerZone.setZone3(powerZoneEntity.getZoneValue3());
        powerZone.setZone4(powerZoneEntity.getZoneValue4());
        powerZone.setZone5(powerZoneEntity.getZoneValue5());
        powerZone.setZone6(powerZoneEntity.getZoneValue6());
        powerZone.setZone7(powerZoneEntity.getZoneValue7());
        JniBleController.getBleController().sensorParametersConfigPZ(powerZone);
        HrmZone hrmZone = new HrmZone();
        if (heartZoneEntity.getSelect() == 0) {
            hrmZone.setZone2(heartZoneEntity.getMaxZoneValue2());
            hrmZone.setZone3(heartZoneEntity.getMaxZoneValue3());
            hrmZone.setZone4(heartZoneEntity.getMaxZoneValue4());
            hrmZone.setZone5(heartZoneEntity.getMaxZoneValue5());
        } else {
            hrmZone.setZone2(heartZoneEntity.getReserveZoneValue2());
            hrmZone.setZone3(heartZoneEntity.getReserveZoneValue3());
            hrmZone.setZone4(heartZoneEntity.getReserveZoneValue4());
            hrmZone.setZone5(heartZoneEntity.getReserveZoneValue5());
        }
        motionState = 1;
        JniBleController.getBleController().sensorParametersConfigHZ(hrmZone);
        JniBleController.getBleController().sensorCtrl(JniBleController.getBleController().E_CTRL_OP_CODE_CYCLING_START);
    }

    /**
     * 暂停运动
     */
    public void suspendMovement() {
        motionState = 2;
        JniBleController.getBleController().sensorCtrl(JniBleController.getBleController().E_CTRL_OP_CODE_CYCLING_PAUSE);
    }

    /**
     * 继续运动
     */
    public void carryOnMovement() {
        motionState = 1;
        JniBleController.getBleController().sensorCtrl(JniBleController.getBleController().E_CTRL_OP_CODE_CYCLING_START);
    }

    /**
     * 结束运动
     */
    public void endMovement() {
        JniBleController.getBleController().sensorCtrl(JniBleController.getBleController().E_CTRL_OP_CODE_CYCLING_END);
    }

    /**
     * 运动分圈
     */
    public void lapMovement() {
        JniBleController.getBleController().sensorCtrl(JniBleController.getBleController().E_CTRL_OP_CODE_CYCLING_LAP);
    }

    /**
     * 断开设备
     */
    public void disconnectDevice(String macAddress) {
        List<BleDevice> allConnectedDevice = BleManager.getInstance().getAllConnectedDevice();
        if (allConnectedDevice != null) {
            for (BleDevice bleDevice : allConnectedDevice) {
                if (bleDevice.getMac().equals(macAddress)) {
                    BleManager.getInstance().clearCharacterCallback(bleDevice);
                    BleManager.getInstance().disconnect(bleDevice);
                    break;
                }
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getType()) {
            //继续运动
            case SensorMessageType.ACTION_CARRY_ON_MOVEMENT:
                motionState = 1;
                JniBleController.getBleController().sensorCtrl(JniBleController.getBleController().E_CTRL_OP_CODE_CYCLING_START);
                break;
            //运动分圈
            case SensorMessageType.ACTION_LAP_MOVEMENT:
                JniBleController.getBleController().sensorCtrl(JniBleController.getBleController().E_CTRL_OP_CODE_CYCLING_LAP);
                break;
            //运动暂停
            case SensorMessageType.ACTION_SUSPEND_MOVEMENT:
                motionState = 2;
                JniBleController.getBleController().sensorCtrl(JniBleController.getBleController().E_CTRL_OP_CODE_CYCLING_PAUSE);
                break;
            //开始锻炼
            case SensorMessageType.ACTION_START_MOTION:
                long userId = (long) event.getMessage();
                PowerZoneEntity powerZoneEntity = DbUtils.getInstance().queryPowerZoneEntity(userId);
                HeartZoneEntity heartZoneEntity = DbUtils.getInstance().queryHeartZoneEntity(userId);
                PowerZone powerZone = new PowerZone();
                powerZone.setFtp(powerZoneEntity.getValue());
                powerZone.setZone2(powerZoneEntity.getZoneValue2());
                powerZone.setZone3(powerZoneEntity.getZoneValue3());
                powerZone.setZone4(powerZoneEntity.getZoneValue4());
                powerZone.setZone5(powerZoneEntity.getZoneValue5());
                powerZone.setZone6(powerZoneEntity.getZoneValue6());
                powerZone.setZone7(powerZoneEntity.getZoneValue7());
                JniBleController.getBleController().sensorParametersConfigPZ(powerZone);
                HrmZone hrmZone = new HrmZone();
                if (heartZoneEntity.getSelect() == 0) {
                    hrmZone.setZone2(heartZoneEntity.getMaxZoneValue2());
                    hrmZone.setZone3(heartZoneEntity.getMaxZoneValue3());
                    hrmZone.setZone4(heartZoneEntity.getMaxZoneValue4());
                    hrmZone.setZone5(heartZoneEntity.getMaxZoneValue5());
                } else {
                    hrmZone.setZone2(heartZoneEntity.getReserveZoneValue2());
                    hrmZone.setZone3(heartZoneEntity.getReserveZoneValue3());
                    hrmZone.setZone4(heartZoneEntity.getReserveZoneValue4());
                    hrmZone.setZone5(heartZoneEntity.getReserveZoneValue5());
                }
                motionState = 1;
                if (null != mLocation && motionType == bleController.E_CYCLING_MODE_OUTDOOR) {
                    startLatitude = mLocation.getLatitude();
                    startLongitude = mLocation.getLongitude();
                }
                JniBleController.getBleController().sensorParametersConfigHZ(hrmZone);
                JniBleController.getBleController().sensorCtrl(JniBleController.getBleController().E_CTRL_OP_CODE_CYCLING_START);
                break;
            //结束运动
            case SensorMessageType.ACTION_END_MOVEMENT:
                JniBleController.getBleController().sensorCtrl(JniBleController.getBleController().E_CTRL_OP_CODE_CYCLING_END);
                break;
            //切换运动模式
            case SensorMessageType.ACTION_TYPE_ACTIVITY:
                bleController.sensorInit();
                int message = (int) event.getMessage();
                Message obtainMessage = mHandler.obtainMessage();
                motionType = message;
                obtainMessage.arg1 = message;
                obtainMessage.what = typeWhat;
                mHandler.sendMessage(obtainMessage);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (null != locationManager) {
            locationManager.removeUpdates(this);
        }
        latitudeList = null;
        longitudeList = null;
        EventBus.getDefault().unregister(this);

        isStart = false;
        if (tickHandler!=null) {
            tickHandler.removeCallbacksAndMessages(null);
        }
        if (mHandler!=null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessage(unInitWhat);
        }
        for (Map.Entry<String, Integer> stringIntegerEntry : deviceMap.entrySet()) {
            if (getSensorConnectStatus(stringIntegerEntry.getValue()) == SENSOR_STATUS_CONNECT) {
                if (stringIntegerEntry.getKey() != null) {
                    disconnectDevice(stringIntegerEntry.getKey());
                }
            }
        }
        try {
            unregisterReceiver(baseReceiver);
        } catch (
                Exception e) {
        }
        mMainHandler.removeCallbacksAndMessages(null);
    }

}
