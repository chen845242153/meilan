package com.meilancycling.mema.network.bean.request;

public class GetCodeRequest {

    /**
     * mesType : string
     * mobile : string
     * receiveType : string
     */

    private String mesType;
    private String mobile;
    private String receiveType;

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }
}
