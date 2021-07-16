package com.meilancycling.mema.network.bean.response;

public class AppUpdateResponse {


    /**
     * content : fedffdsf
     * enContent : enenContentenContent1
     * downloadUrl : //
     * md5 : 0
     * clientType : 1
     * clientVersion : 2
     * versionName : fsdf
     * newVersion : 5
     * publishTime : 1584603242000
     * fileSize : 0
     * isUpgrade : 2
     */

    private String content;
    private String enContent;
    private String downloadUrl;
    private String md5;
    private int clientType;
    private String clientVersion;
    private String versionName;
    private String newVersion;
    private long publishTime;
    private int fileSize;
    private int isUpgrade;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEnContent() {
        return enContent;
    }

    public void setEnContent(String enContent) {
        this.enContent = enContent;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getIsUpgrade() {
        return isUpgrade;
    }

    public void setIsUpgrade(int isUpgrade) {
        this.isUpgrade = isUpgrade;
    }
}
