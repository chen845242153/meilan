package com.meilancycling.mema.network.bean.request;

public class RankingRequest {
    /**
     * session : 1234567890123456789012345678901234567890123
     * timeType : 0
     */

    private String session;
    private int timeType;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }
}
