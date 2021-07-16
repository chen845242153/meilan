package com.meilancycling.mema.ui.sensor;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.meilancycling.mema.BR;

/**
 * @Description: 作用描述
 * @Author: lion 571135591
 * @CreateDate: 2021/6/17 10:03 上午
 */
public class SensorBottomModel extends BaseObservable {
    /**
     * 运动状态
     * 0 运动未开始
     * 1 运动中
     * 2 运动暂停
     */
    private int sportStatus;
    /**
     * 圈数
     */
    private int lapNumber;
    /**
     * 运动类型
     * 0 室内
     * 1 室外
     */
    private int sportType;
    /**
     * 是否上锁
     */
    private boolean isLock;

    @Bindable
    public int getSportStatus() {
        return sportStatus;
    }

    public void setSportStatus(int sportStatus) {
        this.sportStatus = sportStatus;
        notifyPropertyChanged(BR.sportStatus);
    }

    @Bindable
    public int getLapNumber() {
        return lapNumber;
    }

    public void setLapNumber(int lapNumber) {
        this.lapNumber = lapNumber;
        notifyPropertyChanged(BR.lapNumber);
    }

    @Bindable
    public int getSportType() {
        return sportType;
    }

    public void setSportType(int sportType) {
        this.sportType = sportType;
        notifyPropertyChanged(BR.sportType);
    }

    @Bindable
    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
        notifyPropertyChanged(BR.lock);
    }
}
