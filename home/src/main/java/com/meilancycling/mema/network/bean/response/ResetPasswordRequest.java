package com.meilancycling.mema.network.bean.response;

public class ResetPasswordRequest {

    /**
     * cacheKey : string
     * mobile : string
     * password : string
     * receiveType : string
     * smsCode : string
     */

    private String cacheKey;
    private String mobile;
    private String password;
    private String receiveType;
    private String smsCode;

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
