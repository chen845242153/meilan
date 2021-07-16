package com.meilancycling.mema.network.bean.request;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/10/12 1:46 PM
 */
public class AuthorRequest {


    /**
     * id : 0
     * platformType : 0
     * pullToken : string
     * session : 1234567890123456789012345678901234567890123
     * timeOut : string
     * token : string
     */

    private int platformType;
    private String pullToken;
    private String session;
    private String timeOut;
    private String token;



    public int getPlatformType() {
        return platformType;
    }

    public void setPlatformType(int platformType) {
        this.platformType = platformType;
    }

    public String getPullToken() {
        return pullToken;
    }

    public void setPullToken(String pullToken) {
        this.pullToken = pullToken;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
