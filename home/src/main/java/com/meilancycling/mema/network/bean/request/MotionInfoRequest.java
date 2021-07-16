package com.meilancycling.mema.network.bean.request;
/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/12 2:14 PM
 */
public class MotionInfoRequest {
    /**
     * endDate : string
     * isCompetition : string
     * motionType : 0
     * pageNum : 1
     * pageSize : 1000
     * session : session_pw:1d5d3be492ff4f2b91d1758bc6fd29b7
     * startDate : string
     * timeType : 0
     */

    private String endDate;
    private String isCompetition;
    private Integer motionType;
    private Integer pageNum;
    private Integer pageSize;
    private String session;
    private String startDate;
    private Integer timeType;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIsCompetition() {
        return isCompetition;
    }

    public void setIsCompetition(String isCompetition) {
        this.isCompetition = isCompetition;
    }

    public Integer getMotionType() {
        return motionType;
    }

    public void setMotionType(Integer motionType) {
        this.motionType = motionType;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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
