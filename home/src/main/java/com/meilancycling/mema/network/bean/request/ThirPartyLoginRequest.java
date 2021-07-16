package com.meilancycling.mema.network.bean.request;

public class ThirPartyLoginRequest {


    /**
     * headerUrl : string
     * nickName : string
     * openId : string
     * phoneType : 0
     * regType : string
     * unionId : string
     */

    private String headerUrl;
    private String nickName;
    private String openId;
    private int phoneType;
    private String regType;
    private String unionId;

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(int phoneType) {
        this.phoneType = phoneType;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
