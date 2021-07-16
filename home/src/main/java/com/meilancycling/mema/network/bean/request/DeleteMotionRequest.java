package com.meilancycling.mema.network.bean.request;

public class DeleteMotionRequest {

    /**
     * motionId : 0
     * motionType : 0
     * session : 1234567890123456789012345678901234567890123
     */

    private int motionId;
    private int motionType;
    private String session;

    public int getMotionId() {
        return motionId;
    }

    public void setMotionId(int motionId) {
        this.motionId = motionId;
    }

    public int getMotionType() {
        return motionType;
    }

    public void setMotionType(int motionType) {
        this.motionType = motionType;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
