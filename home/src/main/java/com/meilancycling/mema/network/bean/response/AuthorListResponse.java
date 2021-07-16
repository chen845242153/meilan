package com.meilancycling.mema.network.bean.response;


/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/10/12 1:54 PM
 */
public class AuthorListResponse {

    /**
     * id : 27
     * timeOut : 1602578983
     * token : Bearer dcb11c2abab30fabb6fd5a2955d98e196f2a9f02
     * pullToken : b8291bd7b603bfe3934bbb7f737fd0dc79596ec0
     * platformType : 1
     */

    private int id;
    private String timeOut;
    private String token;
    private String pullToken;
    private int platformType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPullToken() {
        return pullToken;
    }

    public void setPullToken(String pullToken) {
        this.pullToken = pullToken;
    }

    public int getPlatformType() {
        return platformType;
    }

    public void setPlatformType(int platformType) {
        this.platformType = platformType;
    }

}
