package com.meilancycling.mema.network.bean.request;

public class QueryMotionInfoRequest {

    /**
     * motionId : 162
     * motionType : 1
     * session : session_pw:c51d7d1a10c94d618acc6aed5e08fd56
     */

    private int motionId;

    private String session;

    public int getMotionId() {
        return motionId;
    }

    public void setMotionId(int motionId) {
        this.motionId = motionId;
    }



    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
