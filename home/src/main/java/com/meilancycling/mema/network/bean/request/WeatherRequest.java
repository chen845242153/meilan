package com.meilancycling.mema.network.bean.request;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/6/29 9:30 AM
 */
public class WeatherRequest {


    /**
     * language : string
     * queryll : string
     */

    private String language;
    private String queryll;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getQueryll() {
        return queryll;
    }

    public void setQueryll(String queryll) {
        this.queryll = queryll;
    }
}
