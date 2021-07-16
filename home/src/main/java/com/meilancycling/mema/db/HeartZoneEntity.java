package com.meilancycling.mema.db;

import com.meilancycling.mema.utils.AppUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 心率区间
 *
 * @author lion 571135591
 */
@Entity
public class HeartZoneEntity {
    @Id(autoincrement = true)
    private Long id;

    private long userId;
    /**
     * 0 最大心率
     * 1 储备心率
     */
    private int select;
    /**
     * 区间左边的值
     */
    private int maxValue;
    private int maxZoneValue1;
    private int maxZoneValue2;
    private int maxZoneValue3;
    private int maxZoneValue4;
    private int maxZoneValue5;

    private int reserveMaxValue;
    private int reserveValue;
    private int reserveZoneValue1;
    private int reserveZoneValue2;
    private int reserveZoneValue3;
    private int reserveZoneValue4;
    private int reserveZoneValue5;

    @Generated(hash = 2026696216)
    public HeartZoneEntity(Long id, long userId, int select, int maxValue,
                           int maxZoneValue1, int maxZoneValue2, int maxZoneValue3,
                           int maxZoneValue4, int maxZoneValue5, int reserveMaxValue,
                           int reserveValue, int reserveZoneValue1, int reserveZoneValue2,
                           int reserveZoneValue3, int reserveZoneValue4, int reserveZoneValue5) {
        this.id = id;
        this.userId = userId;
        this.select = select;
        this.maxValue = maxValue;
        this.maxZoneValue1 = maxZoneValue1;
        this.maxZoneValue2 = maxZoneValue2;
        this.maxZoneValue3 = maxZoneValue3;
        this.maxZoneValue4 = maxZoneValue4;
        this.maxZoneValue5 = maxZoneValue5;
        this.reserveMaxValue = reserveMaxValue;
        this.reserveValue = reserveValue;
        this.reserveZoneValue1 = reserveZoneValue1;
        this.reserveZoneValue2 = reserveZoneValue2;
        this.reserveZoneValue3 = reserveZoneValue3;
        this.reserveZoneValue4 = reserveZoneValue4;
        this.reserveZoneValue5 = reserveZoneValue5;
    }

    @Generated(hash = 664767901)
    public HeartZoneEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getSelect() {
        return this.select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMaxZoneValue1() {
        return this.maxZoneValue1;
    }

    public void setMaxZoneValue1(int maxZoneValue1) {
        this.maxZoneValue1 = maxZoneValue1;
    }

    public int getMaxZoneValue2() {
        return this.maxZoneValue2;
    }

    public void setMaxZoneValue2(int maxZoneValue2) {
        this.maxZoneValue2 = maxZoneValue2;
    }

    public int getMaxZoneValue3() {
        return this.maxZoneValue3;
    }

    public void setMaxZoneValue3(int maxZoneValue3) {
        this.maxZoneValue3 = maxZoneValue3;
    }

    public int getMaxZoneValue4() {
        return this.maxZoneValue4;
    }

    public void setMaxZoneValue4(int maxZoneValue4) {
        this.maxZoneValue4 = maxZoneValue4;
    }

    public int getMaxZoneValue5() {
        return this.maxZoneValue5;
    }

    public void setMaxZoneValue5(int maxZoneValue5) {
        this.maxZoneValue5 = maxZoneValue5;
    }

    public int getReserveMaxValue() {
        return this.reserveMaxValue;
    }

    public void setReserveMaxValue(int reserveMaxValue) {
        this.reserveMaxValue = reserveMaxValue;
    }

    public int getReserveValue() {
        return this.reserveValue;
    }

    public void setReserveValue(int reserveValue) {
        this.reserveValue = reserveValue;
    }

    public int getReserveZoneValue1() {
        return this.reserveZoneValue1;
    }

    public void setReserveZoneValue1(int reserveZoneValue1) {
        this.reserveZoneValue1 = reserveZoneValue1;
    }

    public int getReserveZoneValue2() {
        return this.reserveZoneValue2;
    }

    public void setReserveZoneValue2(int reserveZoneValue2) {
        this.reserveZoneValue2 = reserveZoneValue2;
    }

    public int getReserveZoneValue3() {
        return this.reserveZoneValue3;
    }

    public void setReserveZoneValue3(int reserveZoneValue3) {
        this.reserveZoneValue3 = reserveZoneValue3;
    }

    public int getReserveZoneValue4() {
        return this.reserveZoneValue4;
    }

    public void setReserveZoneValue4(int reserveZoneValue4) {
        this.reserveZoneValue4 = reserveZoneValue4;
    }

    public int getReserveZoneValue5() {
        return this.reserveZoneValue5;
    }

    public void setReserveZoneValue5(int reserveZoneValue5) {
        this.reserveZoneValue5 = reserveZoneValue5;
    }

    /**
     * 创建初始值
     */
    public void createHeartZoneEntity(HeartZoneEntity heartZoneEntity) {
        int defaultMaxValue = 180;
        int defaultReserveValue = 60;
        int diff = defaultMaxValue - defaultReserveValue;
        heartZoneEntity.setUserId(userId);
        heartZoneEntity.setSelect(0);

        heartZoneEntity.setMaxValue(defaultMaxValue);
        heartZoneEntity.setMaxZoneValue1((int) Math.round(AppUtils.multiplyDouble(defaultMaxValue, 0.6)) - 1);
        heartZoneEntity.setMaxZoneValue2((int) Math.round(AppUtils.multiplyDouble(defaultMaxValue, 0.6)));
        heartZoneEntity.setMaxZoneValue3((int) Math.round(AppUtils.multiplyDouble(defaultMaxValue, 0.7)));
        heartZoneEntity.setMaxZoneValue4((int) Math.round(AppUtils.multiplyDouble(defaultMaxValue, 0.8)));
        heartZoneEntity.setMaxZoneValue5((int) Math.round(AppUtils.multiplyDouble(defaultMaxValue, 0.9)));

        heartZoneEntity.setReserveMaxValue(defaultMaxValue);
        heartZoneEntity.setReserveValue(defaultReserveValue);
        heartZoneEntity.setReserveZoneValue1((int) Math.round(AppUtils.multiplyDouble(diff, 0.65) + defaultReserveValue) - 1);
        heartZoneEntity.setReserveZoneValue2((int) Math.round(AppUtils.multiplyDouble(diff, 0.65) + defaultReserveValue));
        heartZoneEntity.setReserveZoneValue3((int) Math.round(AppUtils.multiplyDouble(diff, 0.75) + defaultReserveValue));
        heartZoneEntity.setReserveZoneValue4((int) Math.round(AppUtils.multiplyDouble(diff, 0.85) + defaultReserveValue));
        heartZoneEntity.setReserveZoneValue5((int) Math.round(AppUtils.multiplyDouble(diff, 0.95) + defaultReserveValue));

        DbUtils.getInstance().addHeartZoneEntity(heartZoneEntity);

    }

    /**
     * 重置心率数据
     */
    public HeartZoneEntity resetHeartRate(HeartZoneEntity heartZoneEntity) {
        int defaultMaxValue = 180;
        int defaultReserveValue = 60;
        int diff = defaultMaxValue - defaultReserveValue;
        //0 最大心率  1 储备心率
        if (heartZoneEntity.getSelect() == 0) {
            heartZoneEntity.setMaxValue(defaultMaxValue);
            heartZoneEntity.setMaxZoneValue1((int) Math.round(AppUtils.multiplyDouble(defaultMaxValue, 0.6)));
            heartZoneEntity.setMaxZoneValue2((int) Math.round(AppUtils.multiplyDouble(defaultMaxValue, 0.6)) + 1);
            heartZoneEntity.setMaxZoneValue3((int) Math.round(AppUtils.multiplyDouble(defaultMaxValue, 0.7)) + 1);
            heartZoneEntity.setMaxZoneValue4((int) Math.round(AppUtils.multiplyDouble(defaultMaxValue, 0.8)) + 1);
            heartZoneEntity.setMaxZoneValue5((int) Math.round(AppUtils.multiplyDouble(defaultMaxValue, 0.9)) + 1);
        } else {
            heartZoneEntity.setReserveMaxValue(defaultMaxValue);
            heartZoneEntity.setReserveValue(defaultReserveValue);
            heartZoneEntity.setReserveZoneValue1((int) Math.round(AppUtils.multiplyDouble(diff, 0.65)) + defaultReserveValue);
            heartZoneEntity.setReserveZoneValue2((int) Math.round(AppUtils.multiplyDouble(diff, 0.65)) + defaultReserveValue + 1);
            heartZoneEntity.setReserveZoneValue3((int) Math.round(AppUtils.multiplyDouble(diff, 0.75)) + defaultReserveValue + 1);
            heartZoneEntity.setReserveZoneValue4((int) Math.round(AppUtils.multiplyDouble(diff, 0.85)) + defaultReserveValue + 1);
            heartZoneEntity.setReserveZoneValue5((int) Math.round(AppUtils.multiplyDouble(diff, 0.95)) + defaultReserveValue + 1);
        }
        DbUtils.getInstance().updateHeartZoneEntity(heartZoneEntity);
        return heartZoneEntity;
    }

    /**
     * 修改值
     */
    public HeartZoneEntity modifyValue(HeartZoneEntity heartZoneEntity, int maxValue, int reserveValue) {
        if (heartZoneEntity.getSelect() == 0) {
            heartZoneEntity.setMaxValue(maxValue);
            heartZoneEntity.setMaxZoneValue1((int) Math.round(AppUtils.multiplyDouble(maxValue, 0.6)));
            heartZoneEntity.setMaxZoneValue2((int) Math.round(AppUtils.multiplyDouble(maxValue, 0.6)) + 1);
            heartZoneEntity.setMaxZoneValue3((int) Math.round(AppUtils.multiplyDouble(maxValue, 0.7)) + 1);
            heartZoneEntity.setMaxZoneValue4((int) Math.round(AppUtils.multiplyDouble(maxValue, 0.8)) + 1);
            heartZoneEntity.setMaxZoneValue5((int) Math.round(AppUtils.multiplyDouble(maxValue, 0.9)) + 1);
        } else {
            heartZoneEntity.setReserveMaxValue(maxValue);
            heartZoneEntity.setReserveValue(reserveValue);
            int diff = maxValue - reserveValue;
            heartZoneEntity.setReserveZoneValue1((int) Math.round(AppUtils.multiplyDouble(diff, 0.65)) + reserveValue);
            heartZoneEntity.setReserveZoneValue2((int) Math.round(AppUtils.multiplyDouble(diff, 0.65)) + reserveValue + 1);
            heartZoneEntity.setReserveZoneValue3((int) Math.round(AppUtils.multiplyDouble(diff, 0.75)) + reserveValue + 1);
            heartZoneEntity.setReserveZoneValue4((int) Math.round(AppUtils.multiplyDouble(diff, 0.85)) + reserveValue + 1);
            heartZoneEntity.setReserveZoneValue5((int) Math.round(AppUtils.multiplyDouble(diff, 0.95)) + reserveValue + 1);
        }
        DbUtils.getInstance().updateHeartZoneEntity(heartZoneEntity);
        return heartZoneEntity;
    }
}
