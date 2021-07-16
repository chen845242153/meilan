package com.meilancycling.mema.ui.club.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;


/**
 * @Description: 作用描述
 * @Author: lion 571135591
 * @CreateDate: 2021/6/9 4:32 下午
 */
public class RankingModel extends BaseObservable {
    /**
     * 0 月
     * 1 年
     * 2 累计
     */
    private int itemSelect;
    /**
     * 排名
     */
    private String ranking;
    /**
     * 成绩
     */
    private String grade;

    @Bindable
    public int getItemSelect() {
        return itemSelect;
    }

    public void setItemSelect(int itemSelect) {
        this.itemSelect = itemSelect;
        notifyPropertyChanged(BR.itemSelect);
    }

    @Bindable
    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
        notifyPropertyChanged(BR.ranking);
    }

    @Bindable
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
        notifyPropertyChanged(BR.grade);
    }
}
