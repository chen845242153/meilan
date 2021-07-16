package com.meilancycling.mema.network.bean.request;

/**
 * @Description: java类作用描述
 * @Author: sorelion qq 571135591
 * @CreateDate: 2020/5/9 16:58
 */
public class CountryInfoRequest {

    /**
     * dataType : 2
     * pageNum : 1
     * pageSize : 1000
     */

    private int dataType;
    private int pageNum;
    private int pageSize;

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
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
}
