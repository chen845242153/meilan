package com.meilancycling.mema.network.bean.response;

import java.util.List;

public class CommentResponse {

    private int pageNum;
    private int pageSize;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    /**
     * id : 44
     * commentUser : 11041
     * nickName : 10036687
     * headerUrl : /images/user/header/11041/20210512/38912c389beb4a5f92f64d909479087b.png?Expires=2251528218&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=hsouudYVbLg0tBWc1FOJ8%2B%2FpxhI%3D
     * content : 阿萨德自行车艰苦卓绝喜欢吃客户回访都是覅uaskhfczkjxczkjxhckajhsdfwefoisdfoashdfkasjh哦IE房都is大佛舞蹈服哦违法我诶房间爱搜ID房间爱上了快递费看了我的假发是框架的回复
     * createDate : 1620954843000
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
        private int commentUser;
        private String nickName;
        private String headerUrl;
        private String content;
        private long createDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCommentUser() {
            return commentUser;
        }

        public void setCommentUser(int commentUser) {
            this.commentUser = commentUser;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeaderUrl() {
            return headerUrl;
        }

        public void setHeaderUrl(String headerUrl) {
            this.headerUrl = headerUrl;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }
    }

}
