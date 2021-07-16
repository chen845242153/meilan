package com.meilancycling.mema.network.bean.response;

import java.util.List;

public class ClubSearchResponse {

    /**
     * id : 5
     * contentType : 1
     * cnTitle : 自行车的进展你知道那些自行车的进展你知道那些
     * enTitle : 12
     * cnCover : http://cnweb.meilancycling.com/images/news/20210422/e10adb5b38fd4c7ea91a117bbd5ecc4b.png?Expires=2249775424&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=E6bbyZPfMuMvrngx7n0klSK0JYA%3D
     * enCover : http://cnweb.meilancycling.com/images/news/20210422/b0b507e3fe81432ca263f98be6dfdee8.png?Expires=2249775424&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=YjKtYE42DSSauZVUG3dPqpiCQQ0%3D
     * createDate : 1619055425000
     */

    private List<NewsListBean> newsList;
    /**
     * id : 9
     * contentType : 1
     * cnTitle : 中文名称
     * enTitle : 英文名称
     * cnCover : http://cnweb.meilancycling.com/images/activity/20210520/6797aac54d6e40a296fdfd0b95c0edf0.png?Expires=2252211925&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=BtVBYYHltYiKzg6sars2cQTiDgs%3D
     * enCover : http://cnweb.meilancycling.com/images/activity/20210520/726182ed02944d85ad6fbf969add56e1.png?Expires=2252211941&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=7JxQZ7KnmLkdjpGyF%2Bi%2BUUA4FiE%3D
     * topCnCover : http://cnweb.meilancycling.com/images/activity/20210520/8d1baed564744cfca6086015b8ce9189.png?Expires=2252211925&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=29DRcgDe50sZohqtwRfSQ9zAXps%3D
     * topEnCover : http://cnweb.meilancycling.com/images/activity/20210520/8dc0d6f4828945e38402544298cb9f30.png?Expires=2252211925&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=fDZQ9vxgEtT9%2BeJqXd7qXTBTRtc%3D
     * enAddress : 英文地址
     * cnAddress : 中文地址
     * activityStartDate : 1620748800000
     * activityEndDate : 1620316800000
     */

    private List<ActivityListBean> activityList;

    public List<NewsListBean> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsListBean> newsList) {
        this.newsList = newsList;
    }

    public List<ActivityListBean> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityListBean> activityList) {
        this.activityList = activityList;
    }

    public static class NewsListBean {
        private int id;
        private String contentType;
        private String cnTitle;
        private String enTitle;
        private String cnCover;
        private String enCover;
        private long createDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getCnTitle() {
            return cnTitle;
        }

        public void setCnTitle(String cnTitle) {
            this.cnTitle = cnTitle;
        }

        public String getEnTitle() {
            return enTitle;
        }

        public void setEnTitle(String enTitle) {
            this.enTitle = enTitle;
        }

        public String getCnCover() {
            return cnCover;
        }

        public void setCnCover(String cnCover) {
            this.cnCover = cnCover;
        }

        public String getEnCover() {
            return enCover;
        }

        public void setEnCover(String enCover) {
            this.enCover = enCover;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }
    }

    public static class ActivityListBean {
        private int id;
        private String contentType;
        private String cnTitle;
        private String enTitle;
        private String cnCover;
        private String enCover;
        private String topCnCover;
        private String topEnCover;
        private String enAddress;
        private String cnAddress;
        private long activityStartDate;
        private long activityEndDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getCnTitle() {
            return cnTitle;
        }

        public void setCnTitle(String cnTitle) {
            this.cnTitle = cnTitle;
        }

        public String getEnTitle() {
            return enTitle;
        }

        public void setEnTitle(String enTitle) {
            this.enTitle = enTitle;
        }

        public String getCnCover() {
            return cnCover;
        }

        public void setCnCover(String cnCover) {
            this.cnCover = cnCover;
        }

        public String getEnCover() {
            return enCover;
        }

        public void setEnCover(String enCover) {
            this.enCover = enCover;
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

        public String getEnAddress() {
            return enAddress;
        }

        public void setEnAddress(String enAddress) {
            this.enAddress = enAddress;
        }

        public String getCnAddress() {
            return cnAddress;
        }

        public void setCnAddress(String cnAddress) {
            this.cnAddress = cnAddress;
        }

        public long getActivityStartDate() {
            return activityStartDate;
        }

        public void setActivityStartDate(long activityStartDate) {
            this.activityStartDate = activityStartDate;
        }

        public long getActivityEndDate() {
            return activityEndDate;
        }

        public void setActivityEndDate(long activityEndDate) {
            this.activityEndDate = activityEndDate;
        }
    }
}

