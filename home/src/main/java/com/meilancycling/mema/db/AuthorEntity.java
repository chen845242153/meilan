package com.meilancycling.mema.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2021/1/5 3:37 PM
 */
@Entity
public class AuthorEntity {
    @Id(autoincrement = true)
    private Long id;
    private long userId;
    /**
     * 用户的过期时间
     */
    private String timeOut;
    private String token;
    private String pullToken;
    /**
     * 1.strava
     * 2.komoot
     * 4.TrainingPeaks
     */
    private int platformType;
    @Generated(hash = 320402658)
    public AuthorEntity(Long id, long userId, String timeOut, String token,
            String pullToken, int platformType) {
        this.id = id;
        this.userId = userId;
        this.timeOut = timeOut;
        this.token = token;
        this.pullToken = pullToken;
        this.platformType = platformType;
    }
    @Generated(hash = 1571134052)
    public AuthorEntity() {
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
    public String getTimeOut() {
        return this.timeOut;
    }
    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getPullToken() {
        return this.pullToken;
    }
    public void setPullToken(String pullToken) {
        this.pullToken = pullToken;
    }
    public int getPlatformType() {
        return this.platformType;
    }
    public void setPlatformType(int platformType) {
        this.platformType = platformType;
    }
    
}
