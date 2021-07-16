package com.meilancycling.mema.network.bean.response;

public class NewsRecommendResponse {
    private  long activityEndDate;
    private  long activityStartDate;
    private  String cnAddress;
    private String cnCover;
    private String cnTitle;
    private  String contentType;
    private  long createDate;
    private  String enAddress;
    private String enCover;
    private String  enTitle;
    private  int id;
    private  String topCnCover;
    private  String topEnCover;
    private int type;

    public long getActivityEndDate() {
        return activityEndDate;
    }

    public void setActivityEndDate(long activityEndDate) {
        this.activityEndDate = activityEndDate;
    }

    public long getActivityStartDate() {
        return activityStartDate;
    }

    public void setActivityStartDate(long activityStartDate) {
        this.activityStartDate = activityStartDate;
    }

    public String getCnAddress() {
        return cnAddress;
    }

    public void setCnAddress(String cnAddress) {
        this.cnAddress = cnAddress;
    }

    public String getCnCover() {
        return cnCover;
    }

    public void setCnCover(String cnCover) {
        this.cnCover = cnCover;
    }

    public String getCnTitle() {
        return cnTitle;
    }

    public void setCnTitle(String cnTitle) {
        this.cnTitle = cnTitle;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getEnAddress() {
        return enAddress;
    }

    public void setEnAddress(String enAddress) {
        this.enAddress = enAddress;
    }

    public String getEnCover() {
        return enCover;
    }

    public void setEnCover(String enCover) {
        this.enCover = enCover;
    }

    public String getEnTitle() {
        return enTitle;
    }

    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopCnCover() {
        return topCnCover;
    }

    public void setTopCnCover(String topCnCover) {
        this.topCnCover = topCnCover;
    }

    public String getTopEnCover() {
        return topEnCover;
    }

    public void setTopEnCover(String topEnCover) {
        this.topEnCover = topEnCover;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
