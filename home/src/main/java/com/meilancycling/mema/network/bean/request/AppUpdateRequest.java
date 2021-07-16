package com.meilancycling.mema.network.bean.request;

public class AppUpdateRequest {

    /**
     * clientType : 1
     * clientVersion : 1
     */

    private String clientType;
    private String clientVersion;

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }
}
