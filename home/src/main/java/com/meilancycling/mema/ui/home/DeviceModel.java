package com.meilancycling.mema.ui.home;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.databinding.BaseObservable;

import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Device;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2021/6/3 5:09 下午
 */
public class DeviceModel extends BaseObservable {
    public boolean bleStatus;
    public int power;
    public int progress;
    public int deviceStatus;
    public String productNo;
    public int updateStatus;


    public boolean isBleStatus() {
        return bleStatus;
    }

    public int getPower() {
        return power;
    }

    public int getProgress() {
        return progress;
    }

    public int getDeviceStatus() {
        return deviceStatus;
    }

    public String getProductNo() {
        return productNo;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setBleStatus(boolean bleStatus) {
        this.bleStatus = bleStatus;

    }

    public void setPower(int power) {
        this.power = power;
    }


    public void setProgress(int progress) {
        this.progress = progress;

    }

    public void setDeviceStatus(int deviceStatus) {
        this.deviceStatus = deviceStatus;

    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;

    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;

    }

    public boolean isShowBleStatus(String productNo, boolean bleStatus) {
        return !(TextUtils.isEmpty(productNo) || bleStatus);
    }

    public boolean isShowPower(String productNo, int deviceStatus, boolean bleStatus) {
        if (TextUtils.isEmpty(productNo)) {
            return false;
        }
        if (!bleStatus) {
            return false;
        }
        return deviceStatus == Device.DEVICE_CONNECTED;
    }

    public Drawable getPowerDraw(int power) {
        if (power > 30) {
            return ContextCompat.getDrawable(MyApplication.mInstance, R.drawable.height_power_progress_drawable);
        } else if (power > 15) {
            return ContextCompat.getDrawable(MyApplication.mInstance, R.drawable.power_progress_drawable);
        } else {
            return ContextCompat.getDrawable(MyApplication.mInstance, R.drawable.low_power_progress_drawable);
        }
    }

    public boolean isShowLowPower(String productNo, int deviceStatus, int power) {
        if (TextUtils.isEmpty(productNo)) {
            return false;
        }
        if (deviceStatus != Device.DEVICE_CONNECTED) {
            return false;
        }
        return power <= 15;
    }

    public boolean isShowConnecting(String productNo, boolean bleStatus, int deviceStatus, int updateStatus) {
        if (TextUtils.isEmpty(productNo)) {
            return false;
        }
        if (!bleStatus) {
            return false;
        }
        if (updateStatus == Device.DEVICE_UPDATE_UNDONE) {
            return false;
        }
        return deviceStatus == Device.DEVICE_CONNECTING;

    }

    public boolean isShowConnected(String productNo, boolean bleStatus, int deviceStatus, int progress, int power) {
        if (TextUtils.isEmpty(productNo)) {
            return false;
        }
        if (!bleStatus) {
            return false;
        }
        if (progress != 0) {
            return false;
        }
        if (power <= 15) {
            return false;
        }
        return deviceStatus == Device.DEVICE_CONNECTED;
    }

    public boolean isShowUpload(String productNo, int deviceStatus, int progress) {
        if (TextUtils.isEmpty(productNo)) {
            return false;
        }
        if (deviceStatus != Device.DEVICE_CONNECTED) {
            return false;
        }
        return progress > 0;
    }

    public boolean isShowUpgradeIncomplete(String productNo, boolean bleStatus, int deviceStatus, int updateStatus) {
        if (TextUtils.isEmpty(productNo)) {
            return false;
        }
        if (!bleStatus) {
            return false;
        }
        if (deviceStatus == Device.DEVICE_CONNECTED) {
            return false;
        }
        return updateStatus == Device.DEVICE_UPDATE_UNDONE;
    }
}
