package com.meilancycling.mema.network.bean.request;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2021/6/1 6:44 下午
 */
public class SelectRegRequest {

    /**
     * activityId : 4
     * session : session_pw:11077&1bf241512c774377a65af95006a245da
     */

    private int activityId;
    private String session;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }


    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
