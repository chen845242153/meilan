package com.meilancycling.mema.ble.command;

import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.constant.BroadcastConstant;

import java.util.List;


/**
 * @Description: 蓝牙连接类
 * @Author: sore_lion
 * @CreateDate: 2020/10/20 9:36 AM
 */
public final class BleConnect {
    private static BleConnect mBleConnect;

    public static BleConnect getInstance() {
        if (mBleConnect == null) {

        }
        return mBleConnect;
    }


    public void connectDevice(String macAddress) {
        boolean connected = BleManager.getInstance().isConnected(macAddress);
        if (connected) {
            BleDevice currentDevice = null;
            List<BleDevice> allConnectedDevice = BleManager.getInstance().getAllConnectedDevice();
            if (allConnectedDevice != null) {
                for (BleDevice bleDevice : allConnectedDevice) {
                    if (bleDevice.getMac().equals(macAddress)) {
                        currentDevice = bleDevice;
                        break;
                    }
                }
            }
            if (currentDevice != null) {
                Intent intent = new Intent(BroadcastConstant.ACTION_CONNECT_SUCCESS);
                intent.putExtra(BroadcastConstant.ACTION_CONNECT_KEY, currentDevice);
                MyApplication.mInstance.sendBroadcast(intent);
            } else {
                connectByMac(macAddress);
            }
        } else {
            connectByMac(macAddress);
        }
    }

    private void connectByMac(String macAddress) {
        BleManager.getInstance().connect(macAddress, new BleGattCallback() {
            @Override
            public void onStartConnect() {
                Log.i("sore", "onStartConnect=" + macAddress);
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                Log.i("sore", "onConnectFail=" + macAddress + bleDevice.getName());
                Intent intent = new Intent(BroadcastConstant.ACTION_CONNECT_FAIL);
                intent.putExtra(BroadcastConstant.ACTION_CONNECT_KEY, bleDevice.getMac());
                new Handler().postDelayed(() -> MyApplication.mInstance.sendBroadcast(intent), 1000);
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                Log.i("sore", "onConnectSuccess=" + macAddress + bleDevice.getName());
                Intent intent = new Intent(BroadcastConstant.ACTION_CONNECT_SUCCESS);
                intent.putExtra(BroadcastConstant.ACTION_CONNECT_KEY, bleDevice);
                MyApplication.mInstance.sendBroadcast(intent);
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
                Intent intent = new Intent(BroadcastConstant.ACTION_DISCONNECTED);
                intent.putExtra(BroadcastConstant.ACTION_CONNECT_KEY, device.getMac());
                MyApplication.mInstance.sendBroadcast(intent);
            }
        });
    }


}
