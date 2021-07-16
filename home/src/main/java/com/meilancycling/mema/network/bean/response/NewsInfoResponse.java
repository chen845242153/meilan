package com.meilancycling.mema.network.bean.response;

public class NewsInfoResponse {

    private String cnCover;
    private String cnUrl;
    private String commentSwitch;
    private String enCover;
    private String enUrl;
    private int giveSum;
    private int id;
    private int likeStatus;
    private String newsType;
    private String styleType;

    public String getCnCover() {
        return cnCover;
    }

    public void setCnCover(String cnCover) {
        this.cnCover = cnCover;
    }

    public String getCnUrl() {
        return cnUrl;
    }

    public void setCnUrl(String cnUrl) {
        this.cnUrl = cnUrl;
    }

    public String getCommentSwitch() {
        return commentSwitch;
    }

    public void setCommentSwitch(String commentSwitch) {
        this.commentSwitch = commentSwitch;
    }

    public String getEnCover() {
        return enCover;
    }

    public void setEnCover(String enCover) {
        this.enCover = enCover;
    }

    public String getEnUrl() {
        return enUrl;
    }

    public void setEnUrl(String enUrl) {
        this.enUrl = enUrl;
    }

    public int getGiveSum() {
        return giveSum;
    }

    public void setGiveSum(int giveSum) {
        this.giveSum = giveSum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(int likeStatus) {
        this.likeStatus = likeStatus;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getStyleType() {
        return styleType;
    }

    public void setStyleType(String styleType) {
        this.styleType = styleType;
    }

}
