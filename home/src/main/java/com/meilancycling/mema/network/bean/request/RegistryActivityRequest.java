package com.meilancycling.mema.network.bean.request;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2021/5/27 3:05 下午
 */
public class RegistryActivityRequest {


    /**
     * activityId : 0
     * activityType : string
     * address : string
     * age : string
     * mail : string
     * number : 0
     * phone : string
     * remarks : string
     * session : 1234567890123456789012345678901234567890123
     * userName : string
     */

    private int activityId;
    private String activityType;
    private String address;
    private String age;
    private String mail;
    private int number;
    private String phone;
    private String remarks;
    private String session;
    private String userName;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
