package com.meilancycling.mema.network.bean.request;

public class UpdateMotionRequest {


    /**
     * id : 0
     * mapType : string
     * motionName : string
     * motionRecord : string
     * motionType : 0
     * session : 1234567890123456789012345678901234567890123
     */

    private int id;
    private String mapType;
    private String motionName;
    private String motionRecord;
    private Integer motionType;
    private String session;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public String getMotionName() {
        return motionName;
    }

    public void setMotionName(String motionName) {
        this.motionName = motionName;
    }

    public String getMotionRecord() {
        return motionRecord;
    }

    public void setMotionRecord(String motionRecord) {
        this.motionRecord = motionRecord;
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
