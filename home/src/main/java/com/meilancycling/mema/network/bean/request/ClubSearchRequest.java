package com.meilancycling.mema.network.bean.request;

public class ClubSearchRequest {

    /**
     * cnTitle : string
     * enTitle : string
     * recommend : 0
     * session : 1234567890123456789012345678901234567890123
     */

    private String cnTitle;
    private String enTitle;
    private int recommend;
    private String session;

    public String getCnTitle() {
        return cnTitle;
    }

    public void setCnTitle(String cnTitle) {
        this.cnTitle = cnTitle;
    }

    public String getEnTitle() {
        return enTitle;
    }

    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
