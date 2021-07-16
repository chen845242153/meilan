package com.meilancycling.mema.network.bean.request;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/12 2:14 PM
 */
public class MotionRequest {

    /**
     * dataType : 0
     * endDate : string
     * motionType : 0
     * session : 1234567890123456789012345678901234567890123
     * startDate : string
     * timeType : 0
     */

    private Integer dataType;
    private String endDate;
    private Integer motionType;
    private String session;
    private String startDate;
    private Integer timeType;

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getMotionType() {
        return motionType;
    }

    public void setMotionType(Integer motionType) {
        this.motionType = motionType;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }
}
