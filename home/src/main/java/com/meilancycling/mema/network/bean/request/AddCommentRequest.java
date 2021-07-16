package com.meilancycling.mema.network.bean.request;

public class AddCommentRequest {

    /**
     * comment : string
     * commentType : 0
     * externalId : 0
     * session : 1234567890123456789012345678901234567890123
     */

    private String comment;

    private int externalId;
    private String session;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public int getExternalId() {
        return externalId;
    }

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
