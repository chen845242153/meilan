package com.meilancycling.mema.ble.computer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.R;
import com.meilancycling.mema.ble.bean.CommandEntity;
import com.meilancycling.mema.ble.bean.DeviceInformation;
import com.meilancycling.mema.ble.bean.DeviceSettingBean;
import com.meilancycling.mema.ble.command.BleCommandManager;
import com.meilancycling.mema.ble.command.FileCommandManager;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.inf.ComputerConnectCallback;
import com.meilancycling.mema.inf.DeviceConnectInf;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.service.DfuService;
import com.meilancycling.mema.ui.device.SearchDeviceActivity;
import com.meilancycling.mema.ui.device.bean.ScanSensorBean;
import com.meilancycling.mema.ui.device.bean.SensorValueBean;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.DeviceUtils;
import com.meilancycling.mema.utils.SPUtils;
import com.meilancycling.mema.utils.ToastUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                            O\ = /O
 * //                        ____/`---'\____
 * //                      .   ' \\| |// `.
 * //                       / \\||| : |||// \
 * //                     / _||||| -:- |||||- \
 * //                       | | \\\ - /// | |
 * //                     | \_| ''\---/'' | |
 * //                      \ .-\__ `-` ___/-. /
 * //                   ___`. .' /--.--\ `. . __
 * //                ."" '< `.___\_<|>_/___.' >'"".
 * //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //                 \ \ `-. \_ __\ /__ _/ .-` / /
 * //	    ======`-.____`-.___\_____/___.-`____.-*======
 * //
 * ————————————————
 */

/**
 * @Description: 码表连接实现类
 * @Author: sore_lion
 * @CreateDate: 3/9/21 10:14 AM
 */
public class ComputerConnectImp implements DeviceConnectInf {
    private final String TAG = "ComputerConnectImp";
    /**
     * Service UUID
     * APP扫描,扫描到之后进行连接
     * 用于设备Notify 数据给APP
     * 用于APP 写数据到设备
     */
    private final String SERVICE_UUID = "0000a000-0000-1000-8000-00805f9b34fb";
    private final String WRITE_UUID = "0000a001-0000-1000-8000-00805f9b34fb";
    private final String NOTIFY_READ_UUID = "0000a002-0000-1000-8000-00805f9b34fb";
    private final String FILE_WRITE_UUID = "0000a003-0000-1000-8000-00805f9b34fb";
    private final String FILE_NOTIFY_READ_UUID = "0000a004-0000-1000-8000-00805f9b34fb";
    /**
     * 设备信息 UUID
     */
    private final String DEVICE_SERVICE_UUID = "0000180a-0000-1000-8000-00805f9b34fb";
    /**
     * 硬件版本
     */
    private final String DEVICE_HARDWARE_NOTIFY_READ_UUID = "00002a27-0000-1000-8000-00805f9b34fb";
    /**
     * 软件版本
     */
    private final String DEVICE_SOFTWARE_NOTIFY_READ_UUID = "00002a28-0000-1000-8000-00805f9b34fb";
    /**
     * 电池 UUID
     */
    private final String BATTERY_SERVICE_UUID = "0000180f-0000-1000-8000-00805f9b34fb";
    /**
     * 电池电量
     */
    private final String BATTERY_NOTIFY_READ_UUID = "00002a19-0000-1000-8000-00805f9b34fb";

    private String currentMac;

    private ComputerConnectCallback mComputerConnectCallback;
    /**
     * 文件缓存
     */
    private final HashMap<Integer, CommandEntity> fileCache = new HashMap();

    private int orderNumber;
    /**
     * 0 订阅文件通道
     * 1 订阅设置通道
     * 2 获取软件版本
     * 3 获取硬件版本
     * 4 获取电量
     */
    private final int NOTIFY_FILE = 0;
    private final int NOTIFY_SETTING = 1;
    private final int SOFTWARE = 2;
    private final int HARDWARE = 3;
    private final int BATTERY = 4;
    private final int FINISH = 5;

    /**
     * 运动类型
     */
    private int mSportsType = 0;
    /**
     * 文件名
     */
    private String mFileName = null;
    /**
     * 文件总大小
     */
    private int mFileTotalSize;
    /**
     * 文件数据集合
     */
    private final List<Byte> fileList = new ArrayList<>();
    private final List<byte[]> sendingList = new ArrayList<>();
    /**
     * 序列号
     */
    private int serialNumber;
    private boolean isIdle;
    /**
     * 设置命令
     */
    public final static int SETTING_COMMAND = 1;
    /**
     * 传感器命令
     */
    public final static int SENSOR_COMMAND = 2;
    /**
     * 消息通知
     */
    public final static int INFORMATION_COMMAND = 3;

    /**
     * 设置命令
     */
    private final List<CommandEntity> settingCommand = new ArrayList<>();
    /**
     * 传感器命令
     */
    private final List<CommandEntity> sensorCommand = new ArrayList<>();
    /**
     * 消息通知
     */
    private final List<CommandEntity> notifyCommand = new ArrayList<>();

    /**
     * 文件命令序列号
     */
    public static int fileNumber;
    /**
     * 转义字符标记
     */
    private boolean isEffective;
    private final int startData = (byte) 0x01;
    private final int endData = (byte) 0x04;
    /**
     * 单包数据集合
     */
    private final List<Byte> packetData = new ArrayList<>();
    /**
     * 上一包命令序列号
     */
    private int lastResponseNumber;
    /**
     * 一包最多18个字节
     */
    private final int maxLength = 18;
    /**
     * 预留前面两个字节
     */
    private final int reserved = 2;

    /**
     * 蓝牙返回的数据
     */
    private final List<List<Integer>> dataList = new ArrayList<>();
    private final List<Integer> dataReceive = new ArrayList<>();

    private final int letTime = 3000;
    private BleDevice connectDevice;
    private ComputerBleGattCallback mComputerBleGattCallback;

    @Override
    public void init(ComputerConnectCallback computerConnectCallback) {
        mComputerConnectCallback = computerConnectCallback;
        mComputerBleGattCallback = new ComputerBleGattCallback();
    }

    private void connectToClear() {
        settingCommand.clear();
        sensorCommand.clear();
        notifyCommand.clear();
        sendingList.clear();
        serialNumber = -1;
        isIdle = false;
        dataList.clear();
        dataReceive.clear();
        lastResponseNumber = -1;
        fileNumber = 0;
        isEffective = false;
        mFileName = null;
        mSportsType = 0;
        fileList.clear();
        fileCache.clear();
    }

    @Override
    public void computerConnect(String macAddress) {
        currentMac = macAddress;
        connect();
    }

    /**
     * 检查连接
     */
    private boolean checkConnectStatus() {
        return BleManager.getInstance().isBlueEnable() && currentMac != null && !DeviceControllerService.otaFlag;
    }

    /**
     * 连接设备
     */
    @SuppressLint("LogNotTimber")
    private synchronized void connect() {
        //蓝牙已打开并且要连接的设备不为null
        if (DeviceControllerService.mDeletingDeviceMacList != null && !DeviceControllerService.mDeletingDeviceMacList.isEmpty()) {
            String needcurrentMac = null;
            for (int i = 0; i < DeviceControllerService.mDeletingDeviceMacList.size(); i++) {
                Log.i(TAG, "connect DeviceControllerService.mDeletingDeviceMacList =" + DeviceControllerService.mDeletingDeviceMacList.get(i) + "  i=" + i);
                if (currentMac.equals(DeviceControllerService.mDeletingDeviceMacList.get(i))) {
                    needcurrentMac = currentMac;
                } else {

                }

            }
            if (needcurrentMac != null) {
                List<BleDevice> allConnectedDevice = BleManager.getInstance().getAllConnectedDevice();
                for (BleDevice bleDevice : allConnectedDevice) {
                    Log.i(TAG, "connect del needcurrentMac=" + needcurrentMac + "  getAllConnectedDevice==" + bleDevice.getMac());
                    if (bleDevice.getMac().equals(needcurrentMac)) {
                        BleManager.getInstance().disconnect(bleDevice);
                        Iterator<String> it = DeviceControllerService.mDeletingDeviceMacList.iterator();
                        while (it.hasNext()) {
                            String x = it.next();
                            if (x.equals(needcurrentMac)) {
                                it.remove();
                            }
                        }
                        break;
                    }

                }

            } else {
                if (checkConnectStatus()) {
                    BleManager.getInstance().connect(currentMac, mComputerBleGattCallback);
                }
            }
        } else {
            Log.i(TAG, "connect DeviceControllerService.mDeletingDeviceMacList= isEmpty");
            if (checkConnectStatus()) {
                BleManager.getInstance().connect(currentMac, mComputerBleGattCallback);
            }
        }
    }

    public class ComputerBleGattCallback extends BleGattCallback {
        @Override
        public void onStartConnect() {
            Log.i(TAG, "onStartConnect=" + "  currentMac==" + currentMac);
        }

        @Override
        public void onConnectFail(BleDevice bleDevice, BleException exception) {
            Log.i(TAG, "onConnectFail");
            if (checkConnectStatus()) {
                connect();
            }
        }

        @Override
        public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
            Log.i(TAG, "onConnectSuccess=" + bleDevice.getMac() + "  currentMac==" + currentMac);
            if (currentMac != null && bleDevice.getMac().equals(currentMac)) {
                orderNumber = NOTIFY_FILE;
                notifyAndVersion(bleDevice);
            }
            if (DeviceControllerService.mDeletingDeviceMacList != null && !DeviceControllerService.mDeletingDeviceMacList.isEmpty()) {

                for (int i = 0; i < DeviceControllerService.mDeletingDeviceMacList.size(); i++) {
                    Log.i(TAG, "onConnectSuccess DeviceControllerService.mDeletingDeviceMacList =" + DeviceControllerService.mDeletingDeviceMacList.get(i) + "  i=" + i +"bleDevice.getMac()=="+bleDevice.getMac());
                    if (bleDevice.getMac().equals(DeviceControllerService.mDeletingDeviceMacList.get(i))) {
                        BleManager.getInstance().disconnect(bleDevice);
                        Iterator<String> it = DeviceControllerService.mDeletingDeviceMacList.iterator();
                        while (it.hasNext()) {
                            String x = it.next();
                            if (x.equals(bleDevice.getMac())) {
                                it.remove();
                            }
                        }
                        break;
                    } else {

                    }

                }

            }else {
                Log.i(TAG, "onConnectSuccess DeviceControllerService.mDeletingDeviceMacList= isEmpty");
            }
        }

        @Override
        public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
            Log.i(TAG, "onDisConnected=" + device.getMac() + "  currentMac==" + currentMac);
            mComputerConnectCallback.disconnectComputer(device.getMac());
            if (currentMac != null && device.getMac().equals(currentMac)) {
                connectDevice = null;
                if (checkConnectStatus()) {
                    connect();
                }
            }
        }
    }

    private synchronized void notifyAndVersion(BleDevice bleDevice) {
        Log.i(TAG, "notifyAndVersion orderNumber=" + orderNumber + "  bleDevice.getMac()==" + bleDevice.getMac() + "  currentMac==" + currentMac);
        if (currentMac != null && bleDevice.getMac().equals(currentMac)) {
            switch (orderNumber) {
                case NOTIFY_FILE:
                    fileNotify(bleDevice);
                    break;
                case NOTIFY_SETTING:
                    setNotify(bleDevice);
                    break;
                case SOFTWARE:
                    getVersion(bleDevice);
                    break;
                case HARDWARE:
                    getVersionHardware(bleDevice);
                    break;
                case BATTERY:
                    getBattery(bleDevice);
                    break;
                case FINISH:
                    connectDevice = bleDevice;
                    connectToClear();
                    mComputerConnectCallback.computerConnected(currentMac);
                    notifyBattery(bleDevice);
                    break;
                default:
            }
        }
    }


    private void fileNotify(BleDevice bleDevice) {
        List<BluetoothGattService> bluetoothGattServices = BleManager.getInstance().getBluetoothGattServices(bleDevice);
        boolean isHave = false;
        try {
            for (BluetoothGattService bluetoothGattService : bluetoothGattServices) {
                if (SERVICE_UUID.equals(bluetoothGattService.getUuid().toString())) {
                    List<BluetoothGattCharacteristic> bluetoothGattCharacteristics = BleManager.getInstance().getBluetoothGattCharacteristics(bluetoothGattService);
                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattCharacteristics) {
                        if (FILE_NOTIFY_READ_UUID.equals(bluetoothGattCharacteristic.getUuid().toString())) {
                            isHave = true;
                        }
                        if (FILE_WRITE_UUID.equals(bluetoothGattCharacteristic.getUuid().toString())) {
                            bluetoothGattCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            isHave = false;
        }
        if (isHave) {
            BleManager.getInstance().notify(
                    bleDevice,
                    SERVICE_UUID,
                    FILE_NOTIFY_READ_UUID,
                    new BleNotifyCallback() {
                        @Override
                        public void onNotifySuccess() {
                            ++orderNumber;
                            new Handler().postDelayed(() -> notifyAndVersion(bleDevice), 300);
                        }

                        @Override
                        public void onNotifyFailure(BleException exception) {
                            computerDisconnect(currentMac);
                        }

                        @Override
                        public void onCharacteristicChanged(byte[] data) {
                            Log.i("sore", "文件数据" + DeviceUtils.bytesToHex(data));
                            receivedFileData(data);
                        }
                    });
        } else {
            ++orderNumber;
            new Handler().postDelayed(() -> notifyAndVersion(bleDevice), 500);
        }
    }

    private void setNotify(BleDevice bleDevice) {
        List<BluetoothGattService> bluetoothGattServices = BleManager.getInstance().getBluetoothGattServices(bleDevice);
        boolean isHave = false;
        try {
            for (BluetoothGattService bluetoothGattService : bluetoothGattServices) {
                if (SERVICE_UUID.equals(bluetoothGattService.getUuid().toString())) {
                    List<BluetoothGattCharacteristic> bluetoothGattCharacteristics = BleManager.getInstance().getBluetoothGattCharacteristics(bluetoothGattService);
                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattCharacteristics) {
                        if (NOTIFY_READ_UUID.equals(bluetoothGattCharacteristic.getUuid().toString())) {
                            isHave = true;
                        }
                        if (WRITE_UUID.equals(bluetoothGattCharacteristic.getUuid().toString())) {
                            bluetoothGattCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            isHave = false;
        }
        if (isHave) {
            BleManager.getInstance().notify(
                    bleDevice,
                    SERVICE_UUID,
                    NOTIFY_READ_UUID,
                    new BleNotifyCallback() {
                        @Override
                        public void onNotifySuccess() {
                            ++orderNumber;
                            new Handler().postDelayed(() -> notifyAndVersion(bleDevice), 300);
                        }

                        @Override
                        public void onNotifyFailure(BleException exception) {
                            computerDisconnect(currentMac);
                        }

                        @Override
                        public void onCharacteristicChanged(byte[] data) {
                            Log.i(TAG, "接收数据" + DeviceUtils.bytesToHex(data));
                            getSettingData(data);
                        }
                    });
        } else {
            new Handler().postDelayed(() -> notifyAndVersion(bleDevice), 500);
        }
    }

    /**
     * 获取软件版本
     */
    private void getVersion(BleDevice bleDevice) {
        BleManager.getInstance().read(bleDevice, DEVICE_SERVICE_UUID, DEVICE_SOFTWARE_NOTIFY_READ_UUID, new BleReadCallback() {
            @Override
            public void onReadSuccess(byte[] data) {
                DeviceControllerService.software = (DeviceUtils.convertHexToString(DeviceUtils.bytesToHex(data)).replace("V", ""));
                ++orderNumber;
                new Handler().postDelayed(() -> notifyAndVersion(bleDevice), 300);
            }

            @Override
            public void onReadFailure(BleException exception) {
                computerDisconnect(currentMac);
            }
        });
    }

    /**
     * 获取硬件版本
     */
    private void getVersionHardware(BleDevice bleDevice) {
        BleManager.getInstance().read(bleDevice, DEVICE_SERVICE_UUID, DEVICE_HARDWARE_NOTIFY_READ_UUID, new BleReadCallback() {
            @Override
            public void onReadSuccess(byte[] data) {
                DeviceControllerService.hardware = (DeviceUtils.convertHexToString(DeviceUtils.bytesToHex(data)).replace("V", ""));
                ++orderNumber;
                new Handler().postDelayed(() -> notifyAndVersion(bleDevice), 300);
            }

            @Override
            public void onReadFailure(BleException exception) {
                computerDisconnect(currentMac);
            }
        });
    }

    /**
     * 订阅电量
     */
    private void notifyBattery(BleDevice bleDevice) {
        BleManager.getInstance().notify(bleDevice, BATTERY_SERVICE_UUID, BATTERY_NOTIFY_READ_UUID, new BleNotifyCallback() {
            @Override
            public void onNotifySuccess() {
            }

            @Override
            public void onNotifyFailure(BleException exception) {
            }

            @Override
            public void onCharacteristicChanged(byte[] data) {
                int battery = Integer.parseInt(DeviceUtils.bytesToHex(data), 16);
                mComputerConnectCallback.powerChange(battery);
            }
        });
    }

    /**
     * 获取电量
     */
    private void getBattery(BleDevice bleDevice) {
        BleManager.getInstance().read(bleDevice, BATTERY_SERVICE_UUID, BATTERY_NOTIFY_READ_UUID, new BleReadCallback() {
            @Override
            public void onReadSuccess(byte[] data) {
                int battery = Integer.parseInt(DeviceUtils.bytesToHex(data), 16);
                mComputerConnectCallback.powerChange(battery);
                ++orderNumber;
                new Handler().postDelayed(() -> notifyAndVersion(bleDevice), 300);
            }

            @Override
            public void onReadFailure(BleException exception) {
                computerDisconnect(currentMac);
            }
        });
    }


    @Override
    public synchronized void computerDisconnect(String macAddress) {
        Log.i(TAG, "computerDisconnect macAddress=" + macAddress + "  currentMac==" + currentMac);

        if (currentMac != null && macAddress.equals(currentMac)) {
            if (DeviceControllerService.mDeletingDeviceMacList != null && !DeviceControllerService.mDeletingDeviceMacList.isEmpty()) {
                currentMac = null;
            } else {
            }
        } else {
            currentMac = null;
        }

        if (DeviceControllerService.mDeletingDeviceMacList != null && !DeviceControllerService.mDeletingDeviceMacList.isEmpty()) {
            Log.i(TAG, "computerDisconnect DeviceControllerService.mDeletingDeviceMacList=" + DeviceControllerService.mDeletingDeviceMacList.size());

            List<BleDevice> allConnectedDevice = BleManager.getInstance().getAllConnectedDevice();
            if (allConnectedDevice != null) {
                Log.i(TAG, "computerDisconnect allConnectedDevice.size=" + allConnectedDevice.size());

                Observable.from(allConnectedDevice)
                        .filter(new Func1<BleDevice, Boolean>() {
                            @Override
                            public Boolean call(BleDevice bleDevice) {
                                BleDevice needDelDevice = null;
                                for (String mac : DeviceControllerService.mDeletingDeviceMacList) {
                                    Log.i(TAG, "mac macAddress=" + mac);

                                    if (bleDevice.getMac().equals(mac)) {
                                        Log.i(TAG, "computerDisconnect disconnect bleDevice.getMac()==mac" + mac);
                                        needDelDevice = bleDevice;
                                    }
                                }
                                return needDelDevice != null;

                            }
                        })
                        .subscribe(new Action1<BleDevice>() {
                            @Override
                            public void call(BleDevice needDelDevice) {
                                BleManager.getInstance().disconnect(needDelDevice);
                                Iterator<String> it = DeviceControllerService.mDeletingDeviceMacList.iterator();
                                while (it.hasNext()) {
                                    String x = it.next();
                                    if (x.equals(needDelDevice.getMac())) {
                                        it.remove();
                                    }
                                }
                            }
                        });
//
//                for (int i = 0; i < DeviceControllerService.mDeletingDeviceMacList.size(); i++) {
//                Log.i(TAG, "computerDisconnect DeviceControllerService.mDeletingDeviceMacList ="  + DeviceControllerService.mDeletingDeviceMacList.get(i)+ "  i=" + i);
//                    BleDevice needDelDevice = null;
//                    for (BleDevice bleDevice : allConnectedDevice) {
//                        Log.i(TAG, "computerDisconnect macAddress=" + macAddress + "  getAllConnectedDevice==" + bleDevice.getMac());
//
//                        if (bleDevice.getMac().equals(macAddress)) {
//                            BleManager.getInstance().disconnect(bleDevice);
//                        }
//                        if (bleDevice.getMac().equals(DeviceControllerService.mDeletingDeviceMacList.get(i))) {
//                            Log.i(TAG, "computerDisconnect disconnect DeviceControllerService.mDeletingDeviceMacList.get(i)=" + DeviceControllerService.mDeletingDeviceMacList.get(i));
//                            needDelDevice = bleDevice;
//                        }
//                    }
//                    if (needDelDevice != null) {
//                        BleManager.getInstance().disconnect(needDelDevice);
//                        Iterator<String> it = DeviceControllerService.mDeletingDeviceMacList.iterator();
//                        while (it.hasNext()) {
//                            String x = it.next();
//                            if (x.equals(needDelDevice.getMac())) {
//                                it.remove();
//                            }
//                        }
//
//
//                    } else {
//
//
//                    }
//
//
//                }

            } else {

            }


        } else {
            List<BleDevice> allConnectedDevice = BleManager.getInstance().getAllConnectedDevice();
            Log.i(TAG, "computerDisconnect DeviceControllerService.mDeletingDeviceMacList= isEmpty");
            if (allConnectedDevice != null) {
                for (BleDevice bleDevice : allConnectedDevice) {
                    Log.i(TAG, "computerDisconnect macAddress=" + macAddress + "  getAllConnectedDevice==" + bleDevice.getMac());
                    if (bleDevice.getMac().equals(macAddress)) {
                        BleManager.getInstance().disconnect(bleDevice);
                    }

                }
            }
        }
    }

    /***
     *
     * 发送设置命令
     *
     */
    @Override
    public void sendComputerData(CommandEntity commandEntity) {
        switch (commandEntity.getCommandType()) {
            case SETTING_COMMAND:
                if (settingCommand.size() > 0) {
                    int position = -1;
                    for (int i = 0; i < settingCommand.size(); i++) {
                        if (settingCommand.get(i).getCommandId() == commandEntity.getCommandId() &&
                                settingCommand.get(i).getCommandType() == commandEntity.getCommandType() &&
                                settingCommand.get(i).getType() == commandEntity.getType()) {
                            position = i;
                            break;
                        }
                    }
                    if (position != -1) {
                        settingCommand.remove(position);
                    }
                }
                settingCommand.add(commandEntity);
                break;
            case SENSOR_COMMAND:
                sensorCommand.add(commandEntity);
                break;
            case INFORMATION_COMMAND:
                notifyCommand.add(commandEntity);
                break;
            default:
                sendBleSettingData(commandEntity.getData(), false);
        }
        resendData();

    }


    /**
     * 再次发送数据
     */
    private synchronized void resendData() {
        if (isIdle) {
            isIdle = false;
            if (settingCommand.size() > 0) {
                sendingList.clear();
                List<CommandEntity> delete = new ArrayList<>();
                for (CommandEntity commandEntity : settingCommand) {
                    if (mergeData(commandEntity.getData())) {
                        delete.add(commandEntity);
                    } else {
                        break;
                    }
                }
                settingCommand.removeAll(delete);
                addCommandSerialNumber(4);
            } else if (sensorCommand.size() > 0) {
                sendingList.clear();
                List<CommandEntity> delete = new ArrayList<>();
                for (CommandEntity commandEntity : sensorCommand) {
                    if (mergeData(commandEntity.getData())) {
                        delete.add(commandEntity);
                    } else {
                        break;
                    }
                }
                sensorCommand.removeAll(delete);
                addCommandSerialNumber(5);
            } else if (notifyCommand.size() > 0) {
                sendingList.clear();
                byte[] data = notifyCommand.get(0).getData();
                int ceil = (int) Math.ceil((double) data.length / maxLength);
                for (int i = 0; i < ceil; i++) {
                    //不是最后一包
                    if (i != ceil - 1) {
                        byte[] newBytes = new byte[maxLength + reserved];
                        for (int j = 2; j < newBytes.length; j++) {
                            newBytes[j] = data[maxLength * i + j - 2];
                        }
//                        System.arraycopy(data, ceil * maxLength, newBytes, reserved, newBytes.length - reserved);
                        sendingList.add(newBytes);
                    } else {
                        byte[] newBytes = new byte[data.length - i * maxLength + reserved];
                        for (int j = 2; j < newBytes.length; j++) {
                            newBytes[j] = data[maxLength * i + j - 2];
                        }
//                        System.arraycopy(data, ceil * maxLength, newBytes, reserved, newBytes.length - reserved);
                        sendingList.add(newBytes);
                    }
                }
                notifyCommand.remove(0);
                addCommandSerialNumber(6);
            } else {
                isIdle = true;
            }
        }
    }

    /**
     * 添加命令序列号
     * 字段 1
     * Bit7-bit6
     * 命令序列号
     * Bit5-bit0
     * 命令号
     * 字段 2
     * Bit7
     * 标识符 0:结束包 1:继续包
     * Bit6-bit5 包序号
     * Bit4-bit0
     * 数据字段长度
     */
    private void addCommandSerialNumber(int type) {
        if (serialNumber < 0 || serialNumber == 3) {
            serialNumber = 0;
        } else {
            serialNumber = serialNumber + 1;
        }
        for (int i = 0; i < sendingList.size(); i++) {
            sendingList.get(i)[0] = (byte) ((serialNumber << 6) | type);
            //结束包
            if (i == sendingList.size() - 1) {
                if (i == 4) {
                    sendingList.get(i)[1] = (byte) (((5 << 5) & (0x60)) | (sendingList.get(i).length - 2));
                } else {
                    sendingList.get(i)[1] = (byte) (((i << 5) & (0x60)) | (sendingList.get(i).length - 2));
                }
            } else {
                sendingList.get(i)[1] = (byte) ((1 << 7) | ((i << 5) & (0x60)) | (sendingList.get(i).length - 2));
            }
        }
        for (byte[] bytes : sendingList) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendBleSettingData(bytes, false);
        }
        mHandler.sendEmptyMessageDelayed(0, letTime);
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (sendingList != null) {
                    for (byte[] bytes : sendingList) {
                        sendDataToDevice(bytes);
                    }
                    mHandler.sendEmptyMessageDelayed(0, letTime);
                }
            } else {
                if (connectDevice == null) {
                    fileCache.clear();
                } else {
                    CommandEntity commandEntity = fileCache.get(ComputerConnectImp.fileNumber);
                    if (commandEntity != null) {
                        sendFileData(commandEntity);
                        mHandler.sendEmptyMessageDelayed(1, letTime);
                    }
                }
            }
        }
    };

    /**
     * 发送数据到设备
     */
    public void sendBleSettingData(byte[] bytes, boolean isReply) {
        if (!isReply) {
            isIdle = false;
        }
        sendDataToDevice(bytes);
    }


    /**
     * 打包数据，留出前面两个字节用于命令和序列号
     * 最多只能打5包
     *
     * @return 返回是否成功打包
     */
    private boolean mergeData(byte[] data) {
        //最多只能拼5包
        int max = 5;
        if (sendingList.size() == 0) {
            byte[] newBytes = new byte[data.length + reserved];
            System.arraycopy(data, 0, newBytes, reserved, data.length);
            sendingList.add(newBytes);
            return true;
        } else {
            /*
             * 取出最后一条
             */
            byte[] bytes = sendingList.get(sendingList.size() - 1);
            //不能拼在一包里面
            if (bytes.length + data.length > maxLength + reserved) {
                if (sendingList.size() >= max) {
                    return false;
                } else {
                    byte[] newBytes = new byte[data.length + reserved];
                    System.arraycopy(data, 0, newBytes, reserved, data.length);
                    sendingList.add(newBytes);
                    return true;
                }
            } else {
                byte[] newBytes = new byte[data.length + bytes.length];
                System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
                System.arraycopy(data, 0, newBytes, bytes.length, data.length);
                sendingList.set(sendingList.size() - 1, newBytes);
                return true;
            }
        }
    }

    private void sendDataToDevice(byte[] data) {
        if (connectDevice != null) {
            BleManager.getInstance().write(
                    connectDevice,
                    SERVICE_UUID,
                    WRITE_UUID,
                    data,
                    new BleWriteCallback() {
                        @Override
                        public void onWriteSuccess(int current, int total, byte[] justWrite) {
                            Log.i("sore", "发送数据成功" + DeviceUtils.bytesToHex(data));
                        }

                        @Override
                        public void onWriteFailure(BleException exception) {
                            // 发送数据到设备失败
                            Log.e("lion", "发送数据失败" + DeviceUtils.bytesToHex(data));
                            String initSend = "0100";
                            if (initSend.equals(DeviceUtils.bytesToHex(data))) {
                                new Handler().postDelayed(() -> BleManager.getInstance().disconnect(connectDevice), 500);
                            }
                        }
                    });
        }

    }


    /**
     * 发送文件数据
     */
    @Override
    public void sendFileData(CommandEntity commandEntity) {
        byte[] bytes = commandEntity.getData();
        List<Byte> mList = new ArrayList<>();
        for (int i = 0; i < bytes.length; i++) {
            if (i != 0 && i != bytes.length - 1) {
                if (bytes[i] == 0x01 || bytes[i] == 0x04 || bytes[i] == 0x1b) {
                    mList.add((byte) 0x1b);
                }
            }
            mList.add(bytes[i]);
        }
        byte[] data = new byte[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            data[i] = mList.get(i);
        }
        for (int i = 0; i < ((int) Math.ceil((float) data.length / 20)); i++) {
            byte[] dataList;
            if (20 * (i + 1) > data.length) {
                dataList = new byte[data.length - 20 * i];
            } else {
                dataList = new byte[20];
            }
            System.arraycopy(data, 20 * i, dataList, 0, dataList.length);
            sendFileToDevice(dataList);
        }
    }


    private void sendFileToDevice(byte[] data) {
        if (connectDevice != null) {
            BleManager.getInstance().write(
                    connectDevice,
                    SERVICE_UUID,
                    FILE_WRITE_UUID,
                    data,
                    new BleWriteCallback() {
                        @Override
                        public void onWriteSuccess(int current, int total, byte[] justWrite) {
                            Log.i("sore", "文件通道发送数据成功" + DeviceUtils.bytesToHex(data));
                        }

                        @Override
                        public void onWriteFailure(BleException exception) {
                            Log.e("lion", "文件通道发送数据失败" + DeviceUtils.bytesToHex(data));
                        }
                    });
        }
    }


    /**
     * 接受数据
     * byte0 Bit7-bit6 命令序列号
     * byte1 Bit7 标识符 0:结束包 1:继续包   Bit6-bit5包序号  Bit4-bit0 数据字段长度
     * byte2 数据字段
     */
    private void getSettingData(byte[] data) {
        /*
         * 设备信息
         */
        final int deviceInformation = 0xFE;
        /*
         * 传感器控制
         */
        final int sensorController = 0x01;
        /*
         * 同步设备信息
         */
        final int synchronizationInformation = 0x02;
        /*
         * 应答命令
         */
        final int reply = 0x3D;
        List<Integer> mList = bytesToArrayList(data);
        //命令号
        int commandNumber = mList.get(0) & 0x3F;
        //标识符  0 结束包 1 继续包
        int identifier = (mList.get(1) >> 7) & 0x01;
        if (mList.get(0) == deviceInformation) {
            if (DeviceControllerService.otaFlag) {
                mComputerConnectCallback.otaCallback(mList.get(3));
            } else {
                dataList.add(mList);
                if (identifier == 0) {
                    saveDeviceSupportInformation(dataList);
                    mComputerConnectCallback.deviceInformation();
                    dataList.clear();
                }
            }
        }
        switch (commandNumber) {
            case synchronizationInformation:
                for (int i = 2; i < mList.size(); i++) {
                    dataReceive.add(mList.get(i));
                }
                if (identifier == 0) {
                    replyCommand(mList);
                    getDeviceInformation(dataReceive);
                    dataList.clear();
                    isIdle = true;
                }
                break;
            case sensorController:
                dataList.add(mList);
                if (identifier == 0) {
                    replyCommand(mList);
                    parseSensorData(dataList);
                    dataList.clear();
                }
                break;
            case reply:
                int serial = mList.get(2);
                if (serialNumber == serial) {
                    isIdle = true;
                    sendingList.clear();
                    mHandler.removeMessages(0);
                    resendData();
                }
                break;
            default:
        }
    }

    /**
     * 字节数组转化成集合
     */
    private List<Integer> bytesToArrayList(byte[] bytes) {
        List<Integer> data = new ArrayList<>();
        if (bytes != null) {
            for (byte aByte : bytes) {
                data.add(aByte & 0xff);
            }
        }
        return data;
    }

    private void saveDeviceSupportInformation(List<List<Integer>> dataList) {
        DeviceInformation deviceInformation = new DeviceInformation();
        try {
            for (List<Integer> data : dataList) {
                //包序号
                int packetNumber = (data.get(1) >> 5) & 0x03;
                switch (packetNumber) {
                    case 0:
                        deviceInformation.setProtocolVer(data.get(3) & (0x0F));
                        deviceInformation.setDevModel(data.get(4) & (0x1F));
                        break;
                    case 1:
                        deviceInformation.setSdk(data.get(4) >> 4 & (0x0F));
                        deviceInformation.setSd(data.get(4) & (0x0F));
                        deviceInformation.setPicLibVer((data.get(5) >> 5) & 0x07);
                        deviceInformation.setFontLibVer((data.get(5) >> 3) & 0x03);
                        deviceInformation.setDevSoftInfo((data.get(5) >> 2) & 0x01);
                        deviceInformation.setBTVer(data.get(5) & 0x03);
                        deviceInformation.setAttachPara(data.get(6));
                        break;
                    case 2:
                        deviceInformation.setSportsSupportRide(data.get(4) >> 7 & (0x01));
                        deviceInformation.setSportsSupportRun(data.get(4) >> 6 & (0x01));
                        deviceInformation.setSportsSupportCommon(data.get(4) >> 5 & (0x01));
                        deviceInformation.setPowerRangeRide(data.get(5) >> 7 & (0x01));
                        deviceInformation.setPowerRangeRun(data.get(5) >> 6 & (0x01));
                        deviceInformation.setPowerRangeCommon(data.get(5) >> 5 & (0x01));
                        deviceInformation.setHeartRateRide(data.get(6) >> 7 & (0x01));
                        deviceInformation.setHeartRateRun(data.get(6) >> 6 & (0x01));
                        deviceInformation.setHeartRateCommon(data.get(6) >> 5 & (0x01));
                        deviceInformation.setHeartRateReserve(data.get(6) >> 4 & (0x01));
                        deviceInformation.setTimeFormat(data.get(7) >> 7 & (0x01));
                        deviceInformation.setSound(data.get(7) >> 6 & (0x01));
                        deviceInformation.setLanguageSupport(data.get(7) >> 5 & (0x01));
                        deviceInformation.setLanguage(data.get(7) & (0x0F));
                        deviceInformation.setMessageNotification(data.get(8) >> 7 & (0x01));
                        deviceInformation.setUnitType(data.get(8) >> 1 & (0x01));
                        deviceInformation.setAltitude(data.get(9) >> 7 & (0x01));
                        deviceInformation.setOxygen(data.get(9) >> 6 & (0x01));
                        if (deviceInformation.getProtocolVer() == 1) {
                            deviceInformation.setEarlyWarningTime(data.get(10) >> 7 & (0x01));
                            deviceInformation.setEarlyWarningDistance(data.get(10) >> 6 & (0x01));
                            deviceInformation.setEarlyWarningSpeed(data.get(10) >> 5 & (0x01));
                            deviceInformation.setEarlyWarningCadence(data.get(10) >> 4 & (0x01));
                            deviceInformation.setEarlyWarningHR(data.get(10) >> 3 & (0x01));
                            deviceInformation.setEarlyWarningPower(data.get(10) >> 2 & (0x01));
                        }
                        break;
                    default:
                }
            }
            DeviceControllerService.mDeviceInformation = deviceInformation;
        } catch (Exception e) {
            computerDisconnect(currentMac);
        }
    }

    /**
     * 获取设备信息
     */
    public void getDeviceInformation(List<Integer> list) {
        DeviceSettingBean deviceSettingBean = new DeviceSettingBean();
        int position = 0;
        //最多循环20次
        int number = 0;
        try {
            while (true) {
                //数据类型  0表示结束
                int dataType = (list.get(position) & 0x7F);
                if (number > 20 || dataType == 0) {
                    isIdle = true;
                    mComputerConnectCallback.settingFinish();
                    break;
                } else {
                    ++number;
                    switch (dataType) {
                        //时间格式
                        case 1:
                            deviceSettingBean.setTimeType((list.get(position) >> 7) & 0x01);
                            position = position + 1;
                            break;
                        //声音
                        case 2:
                            deviceSettingBean.setSound((list.get(position) >> 7) & 0x01);
                            position = position + 1;
                            break;
                        //语言
                        case 3:
                            position = position + 1;
                            deviceSettingBean.setSelectLanguage(list.get(position));
                            position = position + 1;
                            break;
                        //odo
                        case 4:
                            position = position + 1;
                            int odo = ((list.get(position) & 0xFF) << 24)
                                    + ((list.get(position + 1) & 0xFF) << 16)
                                    + ((list.get(position + 2) & 0xFF) << 8)
                                    + (list.get(position + 3) & 0xFF);
                            deviceSettingBean.setOdo(odo * 10);
                            position = position + 4;
                            break;
                        //海拔
                        case 5:
                            position = position + 1;
                            int altitude = ((list.get(position) & 0xFF) << 24)
                                    + ((list.get(position + 1) & 0xFF) << 16)
                                    + ((list.get(position + 2) & 0xFF) << 8)
                                    + (list.get(position + 3) & 0xFF);
                            deviceSettingBean.setAltitude(altitude);
                            position = position + 4;
                            break;
                        //来电提醒
                        case 6:
                            deviceSettingBean.setInformationSwitch((list.get(position) >> 7) & 0x01);
                            position = position + 1;
                            break;
                        //时间预警
                        case 7:
//                            deviceSettingBean.setTimeSwitch((list.get(position) >> 7) & 0x01);
                            position = position + 1;
                            int time = ((list.get(position) & 0xFF) << 16)
                                    + ((list.get(position + 1) & 0xFF) << 8)
                                    + (list.get(position + 2) & 0xFF);
//                            deviceSettingBean.setTime(time);
                            position = position + 3;
                            break;
                        //距离预警
                        case 8:
//                            deviceSettingBean.setDistanceSwitch((list.get(position) >> 7) & 0x01);
                            position = position + 1;
                            int distance = ((list.get(position) & 0xFF) << 24)
                                    + ((list.get(position + 1) & 0xFF) << 16)
                                    + ((list.get(position + 2) & 0xFF) << 8)
                                    + (list.get(position + 3) & 0xFF);
//                            deviceSettingBean.setDistance(distance * 10);
                            position = position + 4;
                            break;
                        //速度预警
                        case 9:
//                            deviceSettingBean.setSpeedSwitch((list.get(position) >> 7) & 0x01);
                            position = position + 1;
                            int speed = ((list.get(position) & 0xFF) << 8)
                                    + (list.get(position + 1) & 0xFF);
//                            deviceSettingBean.setSpeed(speed * 10);
                            position = position + 2;
                            break;
                        //踏频预警
                        case 10:
//                            deviceSettingBean.setCadenceSwitch((list.get(position) >> 7) & 0x01);
                            position = position + 1;
//                            deviceSettingBean.setCadence(list.get(position));
                            position = position + 1;
                            break;
                        //心率
                        case 11:
//                            deviceSettingBean.setHeartRateSwitch((list.get(position) >> 7) & 0x01);
                            position = position + 1;
//                            deviceSettingBean.setHeartRate(list.get(position));
                            position = position + 1;
                            break;
                        //功率
                        case 12:
//                            deviceSettingBean.setPowerSwitch((list.get(position) >> 7) & 0x01);
                            position = position + 1;
                            int power = ((list.get(position) & 0xFF) << 8) + (list.get(position + 1) & 0xFF);
//                            deviceSettingBean.setPower(power);
                            position = position + 2;
                            break;
                        //心率区间- 最大心率计算法
                        case 13:
                            if (((list.get(position) >> 7) & 0x01) == 0) {
//                                deviceSettingBean.setMaxHeartRateRegion("");
                                position = position + 1;
                            } else {
                                position = position + 1;
                                int max = list.get(position);
                                int data1 = list.get(position + 1);
                                int data2 = list.get(position + 2);
                                int data3 = list.get(position + 3);
                                int data4 = list.get(position + 4);
//                                deviceSettingBean.setMaxHeartRateRegion(max + "-" + data1 + "-" + data2 + "-" + data3 + "-" + data4);
                                position = position + 5;
                            }
                            break;
                        //心率区间- 储备心率计算法
                        case 14:
                            if (((list.get(position) >> 7) & 0x01) == 0) {
//                                deviceSettingBean.setHeartRateRegion("");
                                position = position + 1;
                            } else {
                                position = position + 1;
                                int max = list.get(position);
                                int reserve = list.get(position + 1);
                                int data1 = list.get(position + 2);
                                int data2 = list.get(position + 3);
                                int data3 = list.get(position + 4);
                                int data4 = list.get(position + 5);
//                                deviceSettingBean.setHeartRateRegion(max + "-" + reserve + "-" + data1 + "-" + data2 + "-" + data3 + "-" + data4);
                                position = position + 5;
                            }
                            break;
                        //心率区间
                        case 15:
                            if (((list.get(position) >> 7) & 0x01) == 0) {
//                                deviceSettingBean.setPowerRegion("");
                                position = position + 1;
                            } else {
                                position = position + 1;
                                int value = ((list.get(position) & 0xFF) << 8) + (list.get(position + 1) & 0xFF);
                                int data1 = ((list.get(position + 2) & 0xFF) << 8) + (list.get(position + 3) & 0xFF);
                                int data2 = ((list.get(position + 4) & 0xFF) << 8) + (list.get(position + 5) & 0xFF);
                                int data3 = ((list.get(position + 6) & 0xFF) << 8) + (list.get(position + 7) & 0xFF);
                                int data4 = ((list.get(position + 8) & 0xFF) << 8) + (list.get(position + 9) & 0xFF);
                                int data5 = ((list.get(position + 10) & 0xFF) << 8) + (list.get(position + 11) & 0xFF);
                                int data6 = ((list.get(position + 12) & 0xFF) << 8) + (list.get(position + 13) & 0xFF);
//                                deviceSettingBean.setPowerRegion(value + "-" + data1 + "-" + data2 + "-" + data3 + "-" + data4 + "-" + data5 + "-" + data6);
                                position = position + 14;
                            }
                            break;
                        default:
                    }
                }
            }
            DeviceControllerService.mDeviceSetUp = deviceSettingBean;
        } catch (Exception e) {
            computerDisconnect(currentMac);
        }
    }

    /**
     * sensor 数据
     */
    public void parseSensorData(List<List<Integer>> mList) {
        try {
            // 删除传感器
            final int deleteSensor = 0x00;
            // 上传传感器
            final int uploadSensor = 0x01;
            // 删除搜索传感器
            final int deleteScanSensor = 0x02;
            // 上传搜索传感器
            final int uploadScanSensor = 0x03;
            int type = mList.get(0).get(2);
            switch (type) {
                case deleteSensor:
                    for (List<Integer> data : mList) {
                        int deviceId = (data.get(3) << 16) | (data.get(4) << 8) | (data.get(5));
                        //最多操作的传感器个数
                        int maxId = 24;
                        for (int i = 0; i < maxId; i++) {
                            if (((1 << i) & deviceId) != 0) {
                                for (SensorValueBean sensorValueBean : DeviceControllerService.mSensorList) {
                                    if (sensorValueBean.getSensorKey() == i) {
                                        DeviceControllerService.mSensorList.remove(sensorValueBean);
                                        break;
                                    }
                                }
                            }
                        }
                        mComputerConnectCallback.deviceSensorUpdate();
                    }
                    break;
                case uploadSensor:
                    for (List<Integer> data : mList) {
                        SensorValueBean sensor = new SensorValueBean();
                        sensor.setConnect(((data.get(3) >> 5) & 0x01) != 0);
                        sensor.setType(data.get(3) & 0x0F);
                        sensor.setSensorKey(data.get(4));
                        sensor.setWheelValue(((data.get(5) << 8) + data.get(6)) * 100);
                        byte[] bytes = new byte[data.size() - 8];
                        for (int i = 0; i < data.size() - 8; i++) {
                            bytes[i] = (byte) (data.get(i + 7) & 0xff);
                        }
                        sensor.setSensorName(new String(bytes, StandardCharsets.UTF_8));
                        int position = -1;
                        for (int i = 0; i < DeviceControllerService.mSensorList.size(); i++) {
                            if (DeviceControllerService.mSensorList.get(i).getSensorKey() == sensor.getSensorKey()) {
                                position = i;
                                break;
                            }
                        }
                        if (position == -1) {
                            DeviceControllerService.mSensorList.add(sensor);
                        } else {
                            DeviceControllerService.mSensorList.set(position, sensor);
                        }
                    }
                    mComputerConnectCallback.deviceSensorUpdate();
                    break;
                case deleteScanSensor:
                    for (List<Integer> data : mList) {
                        int deviceId = data.get(3);
                        int sensorNumber = 8;
                        for (int i = 0; i < sensorNumber; i++) {
                            if (((deviceId >> i) & 0x01) == 1) {
                                for (ScanSensorBean scanSensorBean : DeviceControllerService.mScanList) {
                                    if (scanSensorBean.getSensorKey() == i) {
                                        DeviceControllerService.mScanList.remove(scanSensorBean);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    mComputerConnectCallback.scanSensorUpdate();
                    break;
                case uploadScanSensor:
                    for (List<Integer> data : mList) {
                        ScanSensorBean scanSensorBean = new ScanSensorBean();
                        scanSensorBean.setSensorType(data.get(3) & 0x0F);
                        scanSensorBean.setBle(((data.get(3) >> 4) & 0x01) == 0);
                        scanSensorBean.setSensorKey(data.get(4));
                        scanSensorBean.setValue(((data.get(5) << 8) + data.get(6)) * 100);
                        byte[] bytes = new byte[data.size() - 8];
                        for (int i = 0; i < data.size() - 8; i++) {
                            bytes[i] = (byte) (data.get(i + 7) & 0xff);
                        }
                        scanSensorBean.setName(new String(bytes, StandardCharsets.UTF_8));
                        boolean isHave = false;
                        for (ScanSensorBean sensorBean : DeviceControllerService.mScanList) {
                            if (sensorBean.getSensorKey() == scanSensorBean.getSensorKey()) {
                                isHave = true;
                                break;
                            }
                        }
                        if (!isHave) {
                            DeviceControllerService.mScanList.add(scanSensorBean);
                        }
                    }
                    mComputerConnectCallback.scanSensorUpdate();
                    break;
                default:
            }
        } catch (Exception e) {
            computerDisconnect(currentMac);
        }
    }

    /**
     * 回复命令
     **/
    private void replyCommand(List<Integer> mList) {
        //命令序列号
        int serialNumber = (mList.get(0) >> 6) & 0x03;
        byte[] bytes = BleCommandManager.getInstance().answerCommand(serialNumber);
        sendBleSettingData(bytes, true);
    }

    /**
     * 文件通道接受数据
     */
    public void receivedFileData(byte[] data) {
        // 一个大包数据的开始
        if (!isEffective && data[0] == startData) {
            packetData.clear();
        }
        removeEscapeCharacters(data);
    }

    /**
     * 去除1b转义字符
     */
    private void removeEscapeCharacters(byte[] data) {
        List<Byte> result = new ArrayList<>();
        boolean isEnd = false;
        for (byte datum : data) {
            if (isEffective) {
                result.add(datum);
                isEffective = false;
            } else {
                //转义字符
                int flagData = (byte) 0x1b;
                if (datum == flagData) {
                    isEffective = true;
                } else {
                    result.add(datum);
                    if (datum == endData) {
                        isEnd = true;
                    }
                }
            }
        }
        packetData.addAll(result);
        if (isEnd) {
            packetDataParse(packetData);
        }
    }

    /**
     * 处理大包数据
     * <p>
     * 格式如下
     * 1 起始符
     * 2 数据长度
     * 3 数据命令（bit7-bit4 序列号 bit3-bit0 命令）
     * 4 数据内容
     * 5 结束符
     */
    private void packetDataParse(List<Byte> packetData) {
        if (packetData.size() >= 4
                && packetData.get(0) == startData
                && packetData.get(packetData.size() - 1) == endData
                && (packetData.get(1) & 0xFF) == (packetData.size() - 3)) {
            int responseNumber = ((packetData.get(2) >> 4) & 0x0F);
            int command = ((packetData.get(2)) & 0x0F);
            if (command != 0) {
                CommandEntity commandEntity = FileCommandManager.getInstance().historicalDataResponse(responseNumber);
                sendFileData(commandEntity);
            }
            if (command != 0 && responseNumber == lastResponseNumber) {
                Log.e("sore", "重传的数据");
            } else {
                /*
                 * command id
                 * 0x00 应答
                 * 0x01 通知有新的record
                 * 0x02 数据传输
                 */
                switch (command) {
                    case 0:
                        int response = packetData.get(4);
                        CommandEntity commandEntity = fileCache.get(response);
                        if (commandEntity != null) {
                            fileCache.clear();
                            mHandler.removeMessages(1);
                            //删除历史数据
                            if (commandEntity.getData().length != 5) {
                                commandEntity = FileCommandManager.getInstance().getHistoricalData();
                                fileCache.put(ComputerConnectImp.fileNumber, commandEntity);
                                mHandler.sendEmptyMessageDelayed(1, 1000);
                            }
                        }
                        break;
                    case 1:
                        commandEntity = FileCommandManager.getInstance().getHistoricalData();
                        sendFileData(commandEntity);
                        fileCache.put(ComputerConnectImp.fileNumber, commandEntity);
                        mHandler.sendEmptyMessageDelayed(1, letTime);
                        break;
                    case 2:
                        dataTransmission(packetData);
                        lastResponseNumber = responseNumber;
                        break;
                    default:
                }
            }
        } else {
            Log.e("sore", "文件格式错误");
        }
    }

    /**
     * command Id 等于2 数据传输
     * subcommand id  0 摘要信息 1 文件传输 2传输完毕
     */
    private void dataTransmission(List<Byte> packetData) {
        // 起始符 数据长度 数据命令
        int offset = 3;
        int number = 0;
        while (offset < packetData.size() - 5 && number < 20) {
            ++number;
            offset = fetchData(packetData, offset);
        }
    }

    /**
     * 取出数据保存，并且返回偏移位置
     */
    private int fetchData(List<Byte> packetData, int offset) {
        switch (packetData.get(offset)) {
            case 0:
                List<Byte> informationList = new ArrayList<>();
                ++offset;// 子命令
                informationList.add(packetData.get(offset));
                ++offset;// 运动类型
                int fileNameLenth = packetData.get(offset);
                if (fileNameLenth < 0) {
                    fileNameLenth = fileNameLenth + 256;
                }
                ++offset;// 文件名长度
                for (int i = 0; i < fileNameLenth; i++) {
                    informationList.add(packetData.get(offset));
                    ++offset;
                }
                byte[] name = new byte[informationList.size() - 2];
                for (int i = 0; i < name.length; i++) {
                    name[i] = informationList.get(i + 1);
                }
                /*
                 * 运动类型
                 * 1 record_outdoor_riding
                 * 2 跑步
                 * 3 登山
                 * 4 游泳
                 * 5 徒步
                 * 6 骑行台
                 * 7 铁人三项
                 * 8 other_black
                 */
                switch (informationList.get(0)) {
                    case 1:
                        mSportsType = 1;
                        break;
                    case 6:
                        mSportsType = 2;
                        break;
                    default:
                        mSportsType = 7;
                }
                mFileName = new String(name, StandardCharsets.UTF_8);
                mFileTotalSize = lengthOffset(packetData, offset);
                //文件总大小
                offset = offset + 4;
                int currentLen = lengthOffset(packetData, offset);
                //起始位置
                offset = offset + 4;
                break;
            case 1:
                ++offset;// 子命令
                int dataLength = (int) packetData.get(offset);
                if (dataLength < 0) {
                    dataLength = dataLength + 256;
                }
                ++offset;// 数据长度
                for (int i = 0; i < dataLength; i++) {
                    fileList.add(packetData.get(offset));
                    ++offset;
                }
                currentLen = lengthOffset(packetData, offset);
                //已传输的大小
                offset = offset + 4;
                mComputerConnectCallback.recordProgress(mFileTotalSize, currentLen);
                break;
            case 2:
                ++offset;
                byte[] files = new byte[fileList.size()];
                for (int i = 0; i < fileList.size(); i++) {
                    files[i] = fileList.get(i);
                }
                if (files.length != 0 && mFileName != null && mFileName.length() > 0) {
                    mComputerConnectCallback.fileFinish(mFileName, mSportsType, files);
                    String nameStr = mFileName;
                    long userId = SPUtils.getLong(MyApplication.mInstance, Config.CURRENT_USER);
                    List<DeviceInformationEntity> deviceInfoList = DbUtils.getInstance().deviceInfoList(userId);
                    if (deviceInfoList != null && deviceInfoList.get(0) != null && deviceInfoList.get(0).getDeleteSwitch() == Device.SWITCH_OPEN) {
                        CommandEntity commandEntity = FileCommandManager.getInstance().deleteHistoricalData(nameStr);
                        sendFileData(commandEntity);
                        fileCache.put(ComputerConnectImp.fileNumber, commandEntity);
                        mHandler.sendEmptyMessageDelayed(1, letTime);
                    } else {
                        CommandEntity historicalData = FileCommandManager.getInstance().getHistoricalData();
                        sendFileData(historicalData);
                        fileCache.put(ComputerConnectImp.fileNumber, historicalData);
                        mHandler.sendEmptyMessageDelayed(1, letTime);
                    }
                }
                mFileName = null;
                fileList.clear();
                mSportsType = 0;
                break;
            default:
                break;
        }
        return offset;
    }

    /**
     * 长度计算
     */
    private int lengthOffset(List<Byte> packetData, int offset) {
        return ((packetData.get(offset) & 0xFF) << 24)
                + ((packetData.get(offset + 1) & 0xFF) << 16)
                + ((packetData.get(offset + 2) & 0xFF) << 8)
                + (packetData.get(offset + 3) & 0xFF);
    }

    @Override
    public void otaUpgrade(Context context, String macAddress) {
        if (macAddress != null) {
//            unpairDevice(macAddress);
            String[] split = macAddress.split(":");
            String lastNum = Integer.toHexString((Integer.valueOf(split[5], 16) + 1));
            if (lastNum.length() == 1) {
                lastNum = "0" + lastNum;
            }
            String mOtaMac = (split[0] + ":" + split[1] + ":" + split[2] + ":" + split[3] + ":" + split[4] + ":" + lastNum).toUpperCase();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!AppUtils.isOpenLocationService(context)) {
                    dfuStart(context, mOtaMac);
                } else {
                    BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                            .build();
                    BleManager.getInstance().initScanRule(scanRuleConfig);
                    BleManager.getInstance().scan(new BleScanCallback() {
                        @Override
                        public void onScanFinished(List<BleDevice> scanResultList) {
                            dfuStart(context, mOtaMac);
                        }

                        @Override
                        public void onScanStarted(boolean success) {
                        }

                        @Override
                        public void onScanning(BleDevice bleDevice) {
                            if (bleDevice.getMac().equals(mOtaMac)) {
                                BleManager.getInstance().cancelScan();
                            }
                        }
                    });
                }
            } else {
                dfuStart(context, mOtaMac);
            }


        }
    }

    private void dfuStart(Context context, String macAddress) {
        DfuServiceInitiator starter = new DfuServiceInitiator(macAddress);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            starter.setForeground(false);
            starter.setDisableNotification(true);
        }
        String filePath = MyApplication.mInstance.getExternalFilesDir("") + File.separator + "ota.zip";
        Uri fileUri = Uri.fromFile(new File(filePath));
        starter.setZip(fileUri, Environment.getExternalStorageState() + "ota.zip");
        starter.start(context, DfuService.class);
    }

    private void unpairDevice(String macAddress) {
        Set<BluetoothDevice> devices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        BluetoothDevice currentDevice = null;
        for (BluetoothDevice bluetoothDevice : devices) {
            if (bluetoothDevice.getAddress().equals(macAddress)) {
                currentDevice = bluetoothDevice;
                break;
            }
        }
        if (currentDevice != null) {
            try {
                Method m = currentDevice.getClass()
                        .getMethod("removeBond", (Class[]) null);
                m.invoke(currentDevice, (Object[]) null);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

    }
}
