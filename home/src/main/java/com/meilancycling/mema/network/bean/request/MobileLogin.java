package com.meilancycling.mema.network.bean.request;

public class MobileLogin {
    /**
     * cacheKey : string
     * loginName : string
     * password : string
     * phoneType : 0
     */

    private String cacheKey;
    private String loginName;
    private String password;
    private int phoneType;

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(int phoneType) {
        this.phoneType = phoneType;
    }
}
