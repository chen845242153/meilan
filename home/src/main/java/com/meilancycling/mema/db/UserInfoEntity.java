package com.meilancycling.mema.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * @Description: 用户信息
 * @Author: sore_lion
 * @CreateDate: 2020/12/9 5:35 PM
 */
@Entity
public class UserInfoEntity {
    @Id(autoincrement = true)
    private Long id;
    /**
     * 用户id
     */
    private long userId;

    private String session;
    private int userCode;
    /**
     * 登录 1:手机，2邮箱，3微信，4QQ，5Google 6facebook ,
     */
    private int regType;
    private String nickname;
    private String headUrl;
    /**
     * 性别 1：男；2：女；
     */
    private int gender;
    private int height;
    private int weight;
    private String birthday;
    private String mail;
    private String phone;
    private int unit;
    private String country;
    /**
     * 目标数据
     */
    private int distanceTarget = 100 * 1000;
    private int timeTarget = 20;
    private int calTarget = 8000;

    /**
     * 引导标记
     * 0 代表不需要引导
     * 1 代表需要引导
     * bit 0 首页引导
     * bit 1 更多
     * bit 2 传感器
     * bit 3 设置
     * bit 4 设备
     * bit 5 详情
     * bit 6 放大
     * bit 7 圈数
     */
    private int guideFlag = 0;

    @Generated(hash = 902324792)
    public UserInfoEntity(Long id, long userId, String session, int userCode,
                          int regType, String nickname, String headUrl, int gender, int height,
                          int weight, String birthday, String mail, String phone, int unit,
                          String country, int distanceTarget, int timeTarget, int calTarget,
                          int guideFlag) {
        this.id = id;
        this.userId = userId;
        this.session = session;
        this.userCode = userCode;
        this.regType = regType;
        this.nickname = nickname;
        this.headUrl = headUrl;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.birthday = birthday;
        this.mail = mail;
        this.phone = phone;
        this.unit = unit;
        this.country = country;
        this.distanceTarget = distanceTarget;
        this.timeTarget = timeTarget;
        this.calTarget = calTarget;
        this.guideFlag = guideFlag;
    }

    @Generated(hash = 2042969639)
    public UserInfoEntity() {
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

    public String getSession() {
        return this.session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getUserCode() {
        return this.userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getRegType() {
        return this.regType;
    }

    public void setRegType(int regType) {
        this.regType = regType;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadUrl() {
        return this.headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUnit() {
        return this.unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDistanceTarget() {
        return this.distanceTarget;
    }

    public void setDistanceTarget(int distanceTarget) {
        this.distanceTarget = distanceTarget;
    }

    public int getTimeTarget() {
        return this.timeTarget;
    }

    public void setTimeTarget(int timeTarget) {
        this.timeTarget = timeTarget;
    }

    public int getCalTarget() {
        return this.calTarget;
    }

    public void setCalTarget(int calTarget) {
        this.calTarget = calTarget;
    }

    public int getGuideFlag() {
        return this.guideFlag;
    }

    public void setGuideFlag(int guideFlag) {
        this.guideFlag = guideFlag;
    }


}
