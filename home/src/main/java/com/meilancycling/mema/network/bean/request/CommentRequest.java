package com.meilancycling.mema.network.bean.request;

public class CommentRequest {


    /**
     * externalId : 1
     * pageNum : 1
     * pageSize : 10
     * session : session_pw:5351&b3e8f79e9d80448aa82ace4985de0cb0
     */

    private int externalId;
    private int pageNum;
    private int pageSize;
    private String session;

    public int getExternalId() {
        return externalId;
    }

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
