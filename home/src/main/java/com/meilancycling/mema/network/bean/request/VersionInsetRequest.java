package com.meilancycling.mema.network.bean.request;

public class VersionInsetRequest {


    /**
     * fileOUrl : string
     * fileTUrl : string
     * mac : string
     * product : string
     * session : 1234567890123456789012345678901234567890123
     * status : string
     * twoMes : string
     * twoMesEn : string
     * version : string
     */

    private String fileOUrl;
    private String fileTUrl;
    private String mac;
    private String product;
    private String session;
    private String status;
    private String twoMes;
    private String twoMesEn;
    private String version;

    public String getFileOUrl() {
        return fileOUrl;
    }

    public void setFileOUrl(String fileOUrl) {
        this.fileOUrl = fileOUrl;
    }

    public String getFileTUrl() {
        return fileTUrl;
    }

    public void setFileTUrl(String fileTUrl) {
        this.fileTUrl = fileTUrl;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTwoMes() {
        return twoMes;
    }

    public void setTwoMes(String twoMes) {
        this.twoMes = twoMes;
    }

    public String getTwoMesEn() {
        return twoMesEn;
    }

    public void setTwoMesEn(String twoMesEn) {
        this.twoMesEn = twoMesEn;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
