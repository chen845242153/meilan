package com.meilancycling.mema.network.bean.response;

import java.util.List;

public class ActivityListResponse {

    private int pageNum;
    private int pageSize;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    /**
     * id : 1
     * activityType : 1
     * userSignstatus : 5
     * cnTitle : kfjdk
     * enTitle : kjkfdj
     * cnCover : http://cnweb.meilancycling.com/images/news/20210508/38610e3b3f624fffbf5d9c61a958da7e.png?Expires=2251159190&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=leLJsxUgq2C5N8U%2FJPeU1em%2F%2FcI%3D
     * enCover : http://cnweb.meilancycling.com/images/news/20210508/483be135ffb2438082d0e602b8c8ea1c.png?Expires=2251159190&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=FB%2Fasjr%2B3RqajpKE5CuxL3Kuqkc%3D
     * enAddress : bkk
     * cnAddress : bi
     * activityStartDate : 1621180800000
     * activityEndDate : 1621353600000
     */

    private List<RowsBean> rows;

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

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private int id;
        private String activityType;
        private int userSignstatus;
        private String cnTitle;
        private String enTitle;
        private String cnCover;
        private String enCover;
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

        public String getActivityType() {
            return activityType;
        }

        public void setActivityType(String activityType) {
            this.activityType = activityType;
        }

        public int getUserSignstatus() {
            return userSignstatus;
        }

        public void setUserSignstatus(int userSignstatus) {
            this.userSignstatus = userSignstatus;
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

