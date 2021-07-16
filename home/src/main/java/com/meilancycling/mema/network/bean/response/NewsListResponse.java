package com.meilancycling.mema.network.bean.response;

import java.util.List;

public class NewsListResponse {

    private int endRow;
    private int pageNum;
    private int pageSize;
    private int pages;
    private List<NewsDetailsBean> rows;
    private int startRow;
    private int total;

    public static class NewsDetailsBean {
        private String cnCover;
        private String cnTitle;
        private long createDate;
        private String enCover;
        private String enTitle;
        private int id;
        private String newsType;

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

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
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

        public String getNewsType() {
            return newsType;
        }

        public void setNewsType(String newsType) {
            this.newsType = newsType;
        }
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<NewsDetailsBean> getRows() {
        return rows;
    }

    public void setRows(List<NewsDetailsBean> rows) {
        this.rows = rows;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
