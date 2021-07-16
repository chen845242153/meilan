package com.meilancycling.mema.network.bean.request;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2/1/21 4:00 PM
 */
public class UserBindingRequest {

    /**
     * mobile : string
     * password : string
     * receiveType : 0
     * session : 1234567890123456789012345678901234567890123
     * smsCode : string
     */

    private String mobile;
    private String password;
    private int receiveType;
    private String session;
    private String smsCode;

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

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
