package com.meilancycling.mema.network.bean.response;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020-05-13 08:53
 */
public class CommonProblemResponse {

    /**
     * id : 28
     * name : FINDER
     * url : http://cnweb.meilancycling.com/htmls/html/20210121/9698117186ad490f87ac4fd7e37f17c7.html?Expires=2241934173&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=O6YQ3%2FrPuKapU741yzVR8EAsjE0%3D
     * enName : FINDER
     * enUrl : http://cnweb.meilancycling.com/htmls/html/20210121/8eb1ce3acd284782bc63f1b6d49eb02b.html?Expires=2241934173&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=Dvy3zmIwp8SXc%2BbM8uuTfqddYvA%3D
     * esName : FINDER
     * esUrl : http://cnweb.meilancycling.com/htmls/html/20210121/295ee4c9c6c846969bbfb75721c292f0.html?Expires=2241934173&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=IfFbkSvlA03o3Wz0bMJPpNUXFHY%3D
     */

    private int id;
    private String name;
    private String url;
    private String enName;
    private String enUrl;
    private String esName;
    private String esUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getEnUrl() {
        return enUrl;
    }

    public void setEnUrl(String enUrl) {
        this.enUrl = enUrl;
    }

    public String getEsName() {
        return esName;
    }

    public void setEsName(String esName) {
        this.esName = esName;
    }

    public String getEsUrl() {
        return esUrl;
    }

    public void setEsUrl(String esUrl) {
        this.esUrl = esUrl;
    }

}
