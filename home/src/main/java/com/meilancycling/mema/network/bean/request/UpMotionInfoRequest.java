package com.meilancycling.mema.network.bean.request;

public class UpMotionInfoRequest {

    /**
     * id : 0
     * session : 1234567890123456789012345678901234567890123
     * upStatus : 0
     */

    private int id;
    private String session;
    private int upStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getUpStatus() {
        return upStatus;
    }

    public void setUpStatus(int upStatus) {
        this.upStatus = upStatus;
    }
}
