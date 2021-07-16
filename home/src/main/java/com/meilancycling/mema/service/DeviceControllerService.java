package com.meilancycling.mema.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.clj.fastble.BleManager;
import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.ble.bean.CommandEntity;
import com.meilancycling.mema.ble.bean.DeviceInformation;
import com.meilancycling.mema.ble.bean.DeviceSettingBean;
import com.meilancycling.mema.ble.computer.ComputerConnectImp;
import com.meilancycling.mema.ble.command.BleCommandManager;
import com.meilancycling.mema.ble.command.FileCommandManager;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.db.FileUploadEntity;
import com.meilancycling.mema.db.HeartZoneEntity;
import com.meilancycling.mema.db.PowerZoneEntity;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.db.WarningEntity;
import com.meilancycling.mema.db.greendao.UserInfoEntityDao;
import com.meilancycling.mema.inf.ComputerConnectCallback;
import com.meilancycling.mema.inf.DeviceConnectInf;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.bean.request.DeviceUpdateRequest;
import com.meilancycling.mema.network.bean.response.DeviceUpdateResponse;
import com.meilancycling.mema.ui.device.bean.ScanSensorBean;
import com.meilancycling.mema.ui.device.bean.SensorValueBean;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.SPUtils;
import com.meilancycling.mema.work.FileUploadWork;
import com.meilancycling.mema.work.KomootUploadWork;
import com.meilancycling.mema.work.SensorUploadWork;
import com.meilancycling.mema.work.StravaUploadWork;
import com.meilancycling.mema.work.TPUploadWork;

import java.io.File;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: 设备蓝牙操作服务
 * @Author: sore_lion
 * @CreateDate: 2020/8/27 10:32 AM
 */

public class DeviceControllerService {
    private final String TAG = "DeviceControllerService";
    private static DeviceControllerService singleton;  //静态变量

    private DeviceControllerService() {
    }  //私有构造函数

    private Context mContext;

    public static DeviceControllerService getInstance() {

        if (singleton == null) {  //第一层校验

            synchronized (DeviceControllerService.class) {
                if (singleton == null) {  //第二层校验
                    singleton = new DeviceControllerService();
                }
            }
        }
        return singleton;
    }

    private long mUserId;
    /**
     * 设备设置
     */
    public static DeviceSettingBean mDeviceSetUp;
    /**
     * 电量值
     * 设备状态
     */
    public static int currentPower;
    /**
     * 设备状态
     */
    public static int deviceStatus;
    public static DeviceInformationEntity currentDevice;
    public static String software;
    public static String hardware;
    /**
     * 设备支持
     */
    public static DeviceInformation mDeviceInformation;
    /**
     * 传感器相关
     */
    public static List<SensorValueBean> mSensorList;
    public static List<ScanSensorBean> mScanList;
    /**
     * 是否处于ota升级中
     * true 处于升级中
     * false 正常状态
     */
    public static boolean otaFlag;

    private long getUserId() {
        if (mUserId == -1) {
            mUserId = SPUtils.getLong(mContext, Config.CURRENT_USER);
        }
        return mUserId;
    }


    /**
     * 蓝牙管理
     */
    private DeviceConnectInf mDeviceConnectInf;
    /**
     * 正在删除设备中
     */
    public static List<String> mDeletingDeviceMacList=new ArrayList<>();
    public void init(Context context) {
        mContext = context;
        ComputerConnectImp computerConnectImp = new ComputerConnectImp();
        mDeviceConnectInf = (DeviceConnectInf) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{DeviceConnectInf.class},
                (proxy, method, args) -> method.invoke(computerConnectImp, args));
        mDeviceConnectInf.init(mComputerConnectCallback);

        initData();
        registerServiceReceiver();
    }


    /**
     * 初始化值
     */
    private void initData() {
        mDeviceSetUp = null;
        currentPower = -1;
        deviceStatus = Device.DEVICE_CONNECTING;
        software = null;
        hardware = null;
        mDeviceInformation = null;
        if (mSensorList == null) {
            mSensorList = new ArrayList<>();
        } else {
            mSensorList.clear();
        }
        if (mScanList == null) {
            mScanList = new ArrayList<>();
        } else {
            mScanList.clear();
        }
        mUserId = -1;
        otaFlag = false;
    }

    private final int connectWhat = 1;
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == connectWhat) {
                mHandler.removeCallbacksAndMessages(null);
                if (BleManager.getInstance().isBlueEnable()) {
                    changeDevice();
                }
            }
        }
    };

    private final BroadcastReceiver deviceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())) {
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    initData();
                    BleManager.getInstance().destroy();
                    mContext.sendBroadcast(new Intent(BroadcastConstant.ACTION_BLE_STATUS));
                    mHandler.sendEmptyMessageDelayed(connectWhat, connectWhat);
                    break;
                case BroadcastConstant.ACTION_NOTIFICATION:
                    if (deviceStatus == Device.DEVICE_CONNECTED) {
                        int type = intent.getIntExtra("type", -1);
                        String title = intent.getStringExtra("title");
                        String content = intent.getStringExtra("content");
                        if (type != -1 && title != null && content != null) {
                            CommandEntity commandEntity = BleCommandManager.getInstance().sendNotificationMessage(type, title, content);
                            mDeviceConnectInf.sendComputerData(commandEntity);
                        }
                    }
                    break;
                case BroadcastConstant.ACTION_CHANGE_DEVICE:
                    changeDevice();
                    break;
                case BroadcastConstant.ACTION_USER_LOGOUT:
                    userLogout();
                    break;
                case BroadcastConstant.ACTION_UPDATE_CURRENT_DEVICE:
                    List<DeviceInformationEntity> deviceInfoList = DbUtils.getInstance().deviceInfoList(getUserId());
                    if (deviceInfoList == null || deviceInfoList.size() == 0) {
                        currentDevice = null;
                    } else {
                        currentDevice = deviceInfoList.get(0);
                    }
                    break;
                case BroadcastConstant.ACTION_SEND_COMMAND:
                    if (deviceStatus == Device.DEVICE_CONNECTED) {
                        CommandEntity commandEntity = (CommandEntity) intent.getSerializableExtra(BroadcastConstant.BROADCAST_KEY);
                        mDeviceConnectInf.sendComputerData(commandEntity);
                    }
                    break;
                case BroadcastConstant.ACTION_OTA_UPGRADE:
                    otaUpgrade();
                    break;
                default:
            }
        }
    };

    private void registerServiceReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BroadcastConstant.ACTION_NOTIFICATION);
        intentFilter.addAction(BroadcastConstant.ACTION_CHANGE_DEVICE);
        intentFilter.addAction(BroadcastConstant.ACTION_USER_LOGOUT);
        intentFilter.addAction(BroadcastConstant.ACTION_UPDATE_CURRENT_DEVICE);
        intentFilter.addAction(BroadcastConstant.ACTION_SEND_COMMAND);
        intentFilter.addAction(BroadcastConstant.ACTION_OTA_UPGRADE);
        mContext.registerReceiver(deviceReceiver, intentFilter);
    }

    private final ComputerConnectCallback mComputerConnectCallback = new ComputerConnectCallback() {
        @Override
        public void powerChange(int powerValue) {
            currentPower = powerValue;
            Intent intent = new Intent(BroadcastConstant.ACTION_POWER_VALUE);
            mContext.sendBroadcast(intent);
        }

        @Override
        public void computerConnected(String macAddress) {
            if (currentDevice != null && currentDevice.getMacAddress().equals(macAddress)) {
                mDeviceSetUp = null;
                mDeviceInformation = null;
                CommandEntity deviceInformation = BleCommandManager.getInstance().getDeviceInformation();
                mDeviceConnectInf.sendComputerData(deviceInformation);
                if (currentDevice.getDeviceUpdate() == Device.DEVICE_UPDATE_UNDONE) {
                    currentDevice.setDeviceUpdate(Device.DEVICE_UPDATE_NORMAL);
                    currentDevice.setOtaUrl(null);
                    currentDevice.setMessageEn(null);
                    currentDevice.setMessageCh(null);
                    DbUtils.getInstance().updateDevice(currentDevice);
                }
            } else {
                mDeviceConnectInf.computerDisconnect(macAddress);
            }
        }

        @Override
        public void deviceInformation() {
            //设备已连接
            deviceStatus = Device.DEVICE_CONNECTED;
            Log.i(TAG, "正在连接中");
            Intent intent = new Intent(BroadcastConstant.ACTION_DEVICE_STATUS);
            mContext.sendBroadcast(intent);
            // 检查升级
            checkDeviceUpgrade(mDeviceInformation, currentDevice, software, hardware);
        }

        @Override
        public void disconnectComputer(String macAddress) {
            if (currentDevice != null && currentDevice.getMacAddress().equals(macAddress)) {
                disconnectToClearData();
                Intent intent = new Intent(BroadcastConstant.ACTION_DEVICE_STATUS);
                mContext.sendBroadcast(intent);
                currentPower = 0;
                intent = new Intent(BroadcastConstant.ACTION_POWER_VALUE);
                mContext.sendBroadcast(intent);
            }
        }

        @Override
        public void otaCallback(int status) {
            if (status == 0) {
                if (currentDevice != null) {
                    otaFlag = true;
                    mDeviceConnectInf.otaUpgrade(mContext, currentDevice.getMacAddress());
                    Intent intent = new Intent(BroadcastConstant.ACTION_DEVICE_STATUS);
                    mContext.sendBroadcast(intent);
                }
            } else {
                otaFlag = false;
                mContext.sendBroadcast(new Intent(BroadcastConstant.ACTION_OTA_FAIL));
            }
        }

        @Override
        public void settingFinish() {
            mSensorList.clear();
            mScanList.clear();
            //设备已连接
            deviceStatus = Device.DEVICE_CONNECTED;
            Intent intent = new Intent(BroadcastConstant.ACTION_DEVICE_STATUS);
            mContext.sendBroadcast(intent);
            //发送数据
            sendFirstData();
        }

        @Override
        public void scanSensorUpdate() {
            Intent intent = new Intent(BroadcastConstant.ACTION_SCAN_SENSOR_UPDATE);
            mContext.sendBroadcast(intent);
        }

        @Override
        public void deviceSensorUpdate() {
            Intent intent = new Intent(BroadcastConstant.ACTION_SENSOR_UPDATE);
            mContext.sendBroadcast(intent);
        }

        @Override
        public void recordProgress(int total, int current) {
            Intent intent = new Intent(BroadcastConstant.ACTION_DATA_PROGRESS);
            intent.putExtra("total", total);
            intent.putExtra("current", current);
            mContext.sendBroadcast(intent);
        }

        @Override
        public void fileFinish(String fileName, int motionType, byte[] files) {
            String path = mContext.getExternalFilesDir("") + File.separator + ".myfit/" + getUserId() + fileName;
            FileUploadEntity fileUploadEntity = DbUtils.getInstance().queryFileUploadEntityByName(getUserId(), path);
            if (fileUploadEntity == null) {
                fileUploadEntity = new FileUploadEntity();
                fileUploadEntity.setUserId(getUserId());
                fileUploadEntity.setSportsType(motionType);
                fileUploadEntity.setFileName(fileName);
                if (currentDevice != null) {
                    fileUploadEntity.setProductNo(currentDevice.getProductNo());
                } else {
                    fileUploadEntity.setProductNo(null);
                }
                fileUploadEntity.setTimeZone(AppUtils.getTimeZone());
                fileUploadEntity.setFilePath(path);
                DbUtils.getInstance().addFileUploadEntity(fileUploadEntity);
            } else {
                fileUploadEntity.setUserId(getUserId());
                fileUploadEntity.setSportsType(motionType);
                fileUploadEntity.setFileName(fileName);
                if (currentDevice != null) {
                    fileUploadEntity.setProductNo(currentDevice.getProductNo());
                } else {
                    fileUploadEntity.setProductNo(null);
                }
                fileUploadEntity.setTimeZone(AppUtils.getTimeZone());
                fileUploadEntity.setFilePath(path);
                DbUtils.getInstance().updateFileUploadEntity(fileUploadEntity);
            }
            AppUtils.saveBytesToFile(path, files);
            UserInfoEntity userInfoEntity = MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().queryBuilder()
                    .where(UserInfoEntityDao.Properties.UserId.eq(getUserId()))
                    .unique();
            StrWork(userInfoEntity.getSession(), getUserId(), path);
            KomWork(userInfoEntity.getSession(), getUserId(), path);
            TPWork(userInfoEntity.getSession(), getUserId(), path);
            fileWork(userInfoEntity.getSession(), getUserId(), path);
        }
    };


    @SuppressLint("RestrictedApi")
    private void StrWork(String session, long userId, String filePath) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data sendData = new Data.Builder().
                putString(SensorUploadWork.SESSION_FLAG, session).
                putLong(FileUploadWork.DATA_USER_ID, userId).
                putString(FileUploadWork.DATA_FILE_PATH, filePath).
                build();
        WorkManager.getInstance(mContext)
                .enqueue(new OneTimeWorkRequest.Builder(StravaUploadWork.class)
                        .setConstraints(constraints)
                        .setInputData(sendData)
                        .build());
    }

    @SuppressLint("RestrictedApi")
    private void KomWork(String session, long userId, String filePath) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data sendData = new Data.Builder().
                putString(SensorUploadWork.SESSION_FLAG, session).
                putLong(FileUploadWork.DATA_USER_ID, userId).
                putString(FileUploadWork.DATA_FILE_PATH, filePath).
                build();
        WorkManager.getInstance(mContext)
                .enqueue(new OneTimeWorkRequest.Builder(KomootUploadWork.class)
                        .setConstraints(constraints)
                        .setInputData(sendData)
                        .build());
    }

    @SuppressLint("RestrictedApi")
    private void TPWork(String session, long userId, String filePath) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data sendData = new Data.Builder().
                putString(SensorUploadWork.SESSION_FLAG, session).
                putLong(FileUploadWork.DATA_USER_ID, userId).
                putString(FileUploadWork.DATA_FILE_PATH, filePath).
                build();
        WorkManager.getInstance(mContext)
                .enqueue(new OneTimeWorkRequest.Builder(TPUploadWork.class)
                        .setConstraints(constraints)
                        .setInputData(sendData)
                        .build());
    }

    @SuppressLint("RestrictedApi")
    private void fileWork(String session, long userId, String filePath) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data sendData = new Data.Builder().
                putString(SensorUploadWork.SESSION_FLAG, session).
                putLong(FileUploadWork.DATA_USER_ID, userId).
                putString(FileUploadWork.DATA_FILE_PATH, filePath).
                build();
        WorkManager.getInstance(mContext)
                .enqueue(new OneTimeWorkRequest.Builder(FileUploadWork.class)
                        .setConstraints(constraints)
                        .setInputData(sendData)
                        .build());
    }

    /**
     * ota升级
     */
    private void otaUpgrade() {
        otaFlag = true;
        if (deviceStatus == Device.DEVICE_CONNECTED) {
            CommandEntity commandEntity = BleCommandManager.getInstance().initOta();
            mDeviceConnectInf.sendComputerData(commandEntity);
        } else {
            mDeviceConnectInf.otaUpgrade(mContext, currentDevice.getMacAddress());
        }
    }

    /**
     * 发送首次数据
     */
    private void sendFirstData() {
        //发送时间戳
        CommandEntity commandEntity = BleCommandManager.getInstance().currentTime(System.currentTimeMillis() / 1000);
        mDeviceConnectInf.sendComputerData(commandEntity);
        UserInfoEntity userInfoEntity = MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().queryBuilder()
                .where(UserInfoEntityDao.Properties.UserId.eq(getUserId()))
                .unique();
        String birthday = userInfoEntity.getBirthday();
        String[] split = birthday.split("-");
        commandEntity = BleCommandManager.getInstance().userInfo(userInfoEntity.getGender(), userInfoEntity.getHeight(), userInfoEntity.getWeight(), Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        mDeviceConnectInf.sendComputerData(commandEntity);
        //单位
        commandEntity = BleCommandManager.getInstance().unitSetting(Config.unit, mDeviceInformation.getUnitType());
        mDeviceConnectInf.sendComputerData(commandEntity);
        //心率区间
        HeartZoneEntity heartZoneEntity = DbUtils.getInstance().queryHeartZoneEntity(getUserId());
        CommandEntity maxHr = BleCommandManager.getInstance().hrSetting(2
                , heartZoneEntity.getSelect()
                , heartZoneEntity.getMaxValue()
                , heartZoneEntity.getReserveValue()
                , 0
                , heartZoneEntity.getMaxZoneValue1()
                , heartZoneEntity.getMaxZoneValue3() - 1
                , heartZoneEntity.getMaxZoneValue4() - 1
                , heartZoneEntity.getMaxZoneValue5() - 1);
        CommandEntity reserveHr = BleCommandManager.getInstance().hrSetting(2
                , heartZoneEntity.getSelect()
                , heartZoneEntity.getReserveMaxValue()
                , heartZoneEntity.getReserveValue()
                , 0
                , heartZoneEntity.getReserveZoneValue1()
                , heartZoneEntity.getReserveZoneValue3() - 1
                , heartZoneEntity.getReserveZoneValue4() - 1
                , heartZoneEntity.getReserveZoneValue5() - 1);
        mDeviceConnectInf.sendComputerData(maxHr);
//        /*
//         * 0 最大心率
//         * 1 储备心率
//         */
//        if (heartZoneEntity.getSelect() == 0) {
//            sendCommandData(maxHr);
//        } else {
//            sendCommandData(reserveHr);
//        }
        //功率区间
        PowerZoneEntity powerZoneEntity = DbUtils.getInstance().queryPowerZoneEntity(getUserId());
        commandEntity = BleCommandManager.getInstance().powerSettings(
                powerZoneEntity.getValue()
                , 0
                , powerZoneEntity.getZoneValue2()
                , powerZoneEntity.getZoneValue3()
                , powerZoneEntity.getZoneValue4()
                , powerZoneEntity.getZoneValue5()
                , powerZoneEntity.getZoneValue6()
                , powerZoneEntity.getZoneValue7());
        mDeviceConnectInf.sendComputerData(commandEntity);
        //预警值
        WarningEntity warningEntity = DbUtils.getInstance().queryWarningEntity(getUserId());

        commandEntity = BleCommandManager.getInstance().warningReminder(warningEntity.getTimeSwitch(), 1, warningEntity.getTimeValue() * 60);
        mDeviceConnectInf.sendComputerData(commandEntity);
        commandEntity = BleCommandManager.getInstance().warningReminder(warningEntity.getDistanceSwitch(), 2, warningEntity.getDistanceValue());
        mDeviceConnectInf.sendComputerData(commandEntity);

        commandEntity = BleCommandManager.getInstance().warningReminder(warningEntity.getMaxSpeedSwitch(), 3, warningEntity.getMaxSpeedValue());
        mDeviceConnectInf.sendComputerData(commandEntity);

        commandEntity = BleCommandManager.getInstance().warningReminder(warningEntity.getMaxCadenceSwitch(), 4, warningEntity.getMaxCadenceValue());
        mDeviceConnectInf.sendComputerData(commandEntity);

        commandEntity = BleCommandManager.getInstance().warningReminder(warningEntity.getMaxHeartSwitch(), 5, warningEntity.getMaxHeartValue());
        mDeviceConnectInf.sendComputerData(commandEntity);

        commandEntity = BleCommandManager.getInstance().warningReminder(warningEntity.getMaxPowerSwitch(), 6, warningEntity.getMaxPowerValue());
        mDeviceConnectInf.sendComputerData(commandEntity);

        //获取历史文件
        CommandEntity historicalData = FileCommandManager.getInstance().getHistoricalData();
        new Handler().postDelayed(() -> mDeviceConnectInf.sendFileData(historicalData), 2000);
    }

    /**
     * 检查设备升级状态
     */
    private void checkDeviceUpgrade(DeviceInformation deviceInformation, DeviceInformationEntity deviceInformationEntity, String deviceSoftware, String deviceHardware) {
        long oneDay = 24 * 60 * 60 * 1000;
        if (deviceInformationEntity.getShowTime() + oneDay < System.currentTimeMillis() || deviceInformationEntity.getDeviceUpdate() == Device.DEVICE_UPDATE_NORMAL) {
            DeviceUpdateRequest deviceUpdateRequest = new DeviceUpdateRequest();
            deviceUpdateRequest.setBluetoothId(deviceInformationEntity.getMacAddress());
            deviceUpdateRequest.setAgreement(String.valueOf(deviceInformation.getProtocolVer()));
            deviceUpdateRequest.setAttachPara(String.valueOf(deviceInformation.getAttachPara()));
            deviceUpdateRequest.setBootTV(String.valueOf(deviceInformation.getBTVer()));
            deviceUpdateRequest.setDeviceSoftwareTV(String.valueOf(deviceInformation.getDevSoftInfo()));
            deviceUpdateRequest.setFlib(String.valueOf(deviceInformation.getFontLibVer()));
            deviceUpdateRequest.setHardwareTV(deviceHardware);
            deviceUpdateRequest.setPlib(String.valueOf(deviceInformation.getPicLibVer()));
            int devModel = deviceInformation.getDevModel();
            switch (devModel) {
                case 0:
                    deviceUpdateRequest.setProduct(Device.PRODUCT_NO_M1);
                    break;
                case 1:
                    deviceUpdateRequest.setProduct(Device.PRODUCT_NO_M2);
                    break;
                case 2:
                    deviceUpdateRequest.setProduct(Device.PRODUCT_NO_M4);
                    break;
                default:
                    break;
            }
            deviceUpdateRequest.setSdkTV(String.valueOf(deviceInformation.getSdk()));
            deviceUpdateRequest.setSoftTV(String.valueOf(deviceInformation.getSd()));
            deviceUpdateRequest.setSoftwareTV(deviceSoftware);
            UserInfoEntity userInfoEntity = MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().queryBuilder()
                    .where(UserInfoEntityDao.Properties.UserId.eq(getUserId()))
                    .unique();
            deviceUpdateRequest.setSession(userInfoEntity.getSession());
            RetrofitUtils.getApiUrl().deviceUpdate(deviceUpdateRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MyObserver<DeviceUpdateResponse>() {
                        @Override
                        public void onSuccess(DeviceUpdateResponse deviceUpdateResponse) {
                            if (deviceUpdateResponse != null && !TextUtils.isEmpty(deviceUpdateResponse.getTwoUrl())) {
                                deviceInformationEntity.setDeviceUpdate(Device.DEVICE_UPDATE);
                                deviceInformationEntity.setMessageCh(deviceUpdateResponse.getTwoMes());
                                deviceInformationEntity.setMessageEn(deviceUpdateResponse.getTwoMesEn());
                                deviceInformationEntity.setOtaUrl(deviceUpdateResponse.getTwoUrl());
                                DbUtils.getInstance().updateDevice(deviceInformationEntity);
                                mContext.sendBroadcast(new Intent(BroadcastConstant.ACTION_DEVICE_UPDATE));
                            }
                        }

                        @Override
                        public void onFailure(Throwable e, String resultCode) {
                        }
                    });
        }
    }

    /**
     * 切换,连接设备
     */
    private void changeDevice() {
        DeviceInformationEntity device;
        List<DeviceInformationEntity> deviceInfoList = DbUtils.getInstance().deviceInfoList(getUserId());
        if (deviceInfoList == null || deviceInfoList.size() == 0) {
            device = null;
        } else {
            device = deviceInfoList.get(0);
        }
        if (currentDevice != null && device != null && currentDevice.getMacAddress().equals(device.getMacAddress()) && deviceStatus == Device.DEVICE_CONNECTED) {
            return;
        }
        if (currentDevice != null) {
            mDeviceConnectInf.computerDisconnect(currentDevice.getMacAddress());
        }
        currentPower = 0;
        if (device == null) {
            currentDevice = null;
            deviceStatus = Device.DEVICE_CONNECTING;
        } else {
            currentDevice = device;
            deviceStatus = Device.DEVICE_CONNECTING;
            mDeviceConnectInf.computerConnect(currentDevice.getMacAddress());
            Log.i(TAG, "正在连接中"+currentDevice.getMacAddress());
        }

        Intent intent = new Intent(BroadcastConstant.ACTION_DEVICE_STATUS);
        mContext.sendBroadcast(intent);
    }

    /**
     * 用户退出
     */
    private void userLogout() {
        if (currentDevice != null) {
            mDeviceConnectInf.computerDisconnect(currentDevice.getMacAddress());
        }
        mUserId = -1;
        currentDevice = null;
        currentPower = 0;
        deviceStatus = Device.DEVICE_CONNECTING;
        Intent intent = new Intent(BroadcastConstant.ACTION_DEVICE_STATUS);
        mContext.sendBroadcast(intent);
    }

    private void disconnectToClearData() {
        mDeviceSetUp = null;
        currentPower = -1;
        deviceStatus = Device.DEVICE_CONNECTING;
        software = null;
        hardware = null;
        mDeviceInformation = null;
        mSensorList.clear();
        mScanList.clear();
    }

    public void onDestroy() {
        mContext.unregisterReceiver(deviceReceiver);
    }
}
