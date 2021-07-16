package com.meilancycling.mema.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 轮径值
 *
 * @author lion 571135591
 */
@Entity
public class WheelEntity {
    @Id(autoincrement = true)
    private Long id;
    /**
     * 轮径值
     */
    private int wheelValue;
    private String norm;
    @Generated(hash = 404995642)
    public WheelEntity(Long id, int wheelValue, String norm) {
        this.id = id;
        this.wheelValue = wheelValue;
        this.norm = norm;
    }
    @Generated(hash = 345352155)
    public WheelEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getWheelValue() {
        return this.wheelValue;
    }
    public void setWheelValue(int wheelValue) {
        this.wheelValue = wheelValue;
    }
    public String getNorm() {
        return this.norm;
    }
    public void setNorm(String norm) {
        this.norm = norm;
    }
}
