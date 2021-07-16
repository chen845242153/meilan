package com.meilancycling.mema.ui.club.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.meilancycling.mema.BR;

public class ClubHomeTitleModel extends BaseObservable {
    /**
     * 页面选择
     * 0 新闻
     * 1 挑战
     * 2 排名
     */
    private int itemSelect;

    /**
     * 是否显示红点
     * 0 表示没有
     * 1 表示有
     */
    private boolean isShowRedPoint;

    @Bindable
    public int getItemSelect() {
        return itemSelect;
    }

    public void setItemSelect(int itemSelect) {
        this.itemSelect = itemSelect;
        notifyPropertyChanged(BR.itemSelect);
    }

    @Bindable
    public boolean isShowRedPoint() {
        return isShowRedPoint;
    }

    public void setShowRedPoint(boolean showRedPoint) {
        isShowRedPoint = showRedPoint;
        notifyPropertyChanged(BR.showRedPoint);
    }
}
