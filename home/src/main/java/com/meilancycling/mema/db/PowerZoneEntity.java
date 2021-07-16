package com.meilancycling.mema.db;

import com.meilancycling.mema.utils.AppUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 功率区间
 *
 * @author lion 571135591
 */
@Entity
public class PowerZoneEntity {
    @Id(autoincrement = true)
    private Long id;
    private long userId;
    /**
     * 区间左边的值
     */
    private int value;
    private int zoneValue1;
    private int zoneValue2;
    private int zoneValue3;
    private int zoneValue4;
    private int zoneValue5;
    private int zoneValue6;
    private int zoneValue7;

    @Generated(hash = 1355445769)
    public PowerZoneEntity(Long id, long userId, int value, int zoneValue1,
                           int zoneValue2, int zoneValue3, int zoneValue4, int zoneValue5,
                           int zoneValue6, int zoneValue7) {
        this.id = id;
        this.userId = userId;
        this.value = value;
        this.zoneValue1 = zoneValue1;
        this.zoneValue2 = zoneValue2;
        this.zoneValue3 = zoneValue3;
        this.zoneValue4 = zoneValue4;
        this.zoneValue5 = zoneValue5;
        this.zoneValue6 = zoneValue6;
        this.zoneValue7 = zoneValue7;
    }

    @Generated(hash = 861493563)
    public PowerZoneEntity() {
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

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getZoneValue1() {
        return this.zoneValue1;
    }

    public void setZoneValue1(int zoneValue1) {
        this.zoneValue1 = zoneValue1;
    }

    public int getZoneValue2() {
        return this.zoneValue2;
    }

    public void setZoneValue2(int zoneValue2) {
        this.zoneValue2 = zoneValue2;
    }

    public int getZoneValue3() {
        return this.zoneValue3;
    }

    public void setZoneValue3(int zoneValue3) {
        this.zoneValue3 = zoneValue3;
    }

    public int getZoneValue4() {
        return this.zoneValue4;
    }

    public void setZoneValue4(int zoneValue4) {
        this.zoneValue4 = zoneValue4;
    }

    public int getZoneValue5() {
        return this.zoneValue5;
    }

    public void setZoneValue5(int zoneValue5) {
        this.zoneValue5 = zoneValue5;
    }

    public int getZoneValue6() {
        return this.zoneValue6;
    }

    public void setZoneValue6(int zoneValue6) {
        this.zoneValue6 = zoneValue6;
    }

    public int getZoneValue7() {
        return this.zoneValue7;
    }

    public void setZoneValue7(int zoneValue7) {
        this.zoneValue7 = zoneValue7;
    }

    /**
     * 创建初始值
     */
    public void createPowerZoneEntity(PowerZoneEntity powerZoneEntity) {
        int defaultValue = 200;

        powerZoneEntity.setValue(defaultValue);

        powerZoneEntity.setZoneValue1((int) Math.round(AppUtils.multiplyDouble(defaultValue, 0.55)));
        powerZoneEntity.setZoneValue2((int) Math.round(AppUtils.multiplyDouble(defaultValue, 0.55)) + 1);
        powerZoneEntity.setZoneValue3((int) Math.round(AppUtils.multiplyDouble(defaultValue, 0.75)) + 1);
        powerZoneEntity.setZoneValue4((int) Math.round(AppUtils.multiplyDouble(defaultValue, 0.9)) + 1);
        powerZoneEntity.setZoneValue5((int) Math.round(AppUtils.multiplyDouble(defaultValue, 1.05)) + 1);
        powerZoneEntity.setZoneValue6((int) Math.round(AppUtils.multiplyDouble(defaultValue, 1.2)) + 1);
        powerZoneEntity.setZoneValue7((int) Math.round(AppUtils.multiplyDouble(defaultValue, 1.5)) + 1);
        DbUtils.getInstance().addPowerZoneEntity(powerZoneEntity);
    }

    /**
     * 重置心率数据
     */
    public PowerZoneEntity resetPower(PowerZoneEntity powerZoneEntity) {
        int defaultValue = 200;
        powerZoneEntity.setUserId(userId);
        powerZoneEntity.setValue(defaultValue);

        powerZoneEntity.setZoneValue1((int) Math.round(AppUtils.multiplyDouble(defaultValue, 0.55)));
        powerZoneEntity.setZoneValue2((int) Math.round(AppUtils.multiplyDouble(defaultValue, 0.55)) + 1);
        powerZoneEntity.setZoneValue3((int) Math.round(AppUtils.multiplyDouble(defaultValue, 0.75)) + 1);
        powerZoneEntity.setZoneValue4((int) Math.round(AppUtils.multiplyDouble(defaultValue, 0.9)) + 1);
        powerZoneEntity.setZoneValue5((int) Math.round(AppUtils.multiplyDouble(defaultValue, 1.05)) + 1);
        powerZoneEntity.setZoneValue6((int) Math.round(AppUtils.multiplyDouble(defaultValue, 1.2)) + 1);
        powerZoneEntity.setZoneValue7((int) Math.round(AppUtils.multiplyDouble(defaultValue, 1.5)) + 1);

        DbUtils.getInstance().updatePowerZoneEntity(powerZoneEntity);
        return powerZoneEntity;
    }

    /**
     * 修改值
     */
    public PowerZoneEntity modifyValue(PowerZoneEntity powerZoneEntity, int powerValue) {
        powerZoneEntity.setValue(powerValue);
        powerZoneEntity.setZoneValue1((int) Math.round(AppUtils.multiplyDouble(powerValue, 0.55)));
        powerZoneEntity.setZoneValue2((int) Math.round(AppUtils.multiplyDouble(powerValue, 0.55)) + 1);
        powerZoneEntity.setZoneValue3((int) Math.round(AppUtils.multiplyDouble(powerValue, 0.75)) + 1);
        powerZoneEntity.setZoneValue4((int) Math.round(AppUtils.multiplyDouble(powerValue, 0.9)) + 1);
        powerZoneEntity.setZoneValue5((int) Math.round(AppUtils.multiplyDouble(powerValue, 1.05)) + 1);
        powerZoneEntity.setZoneValue6((int) Math.round(AppUtils.multiplyDouble(powerValue, 1.2)) + 1);
        powerZoneEntity.setZoneValue7((int) Math.round(AppUtils.multiplyDouble(powerValue, 1.5)) + 1);

        DbUtils.getInstance().updatePowerZoneEntity(powerZoneEntity);
        return powerZoneEntity;
    }
}
