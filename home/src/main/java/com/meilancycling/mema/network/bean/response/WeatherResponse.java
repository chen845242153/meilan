package com.meilancycling.mema.network.bean.response;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/6/29 9:34 AM
 */
public class WeatherResponse {

    /**
     * city : string
     * phraseImg : string
     * temperature : string
     */

    private String city;
    private String phraseImg;
    private String temperature;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhraseImg() {
        return phraseImg;
    }

    public void setPhraseImg(String phraseImg) {
        this.phraseImg = phraseImg;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
