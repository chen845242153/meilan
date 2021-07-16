package com.meilancycling.mema.constant;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/12 2:41 PM
 */
public enum Unit {
    /**
     * 英制单位
     */
    METRIC("公制", 0),
    /**
     * 英制单位
     */
    IMPERIAL("英制", 1);

    public int value;
    public String name;

    Unit(String name, int value) {
        this.value = value;
        this.name = name;
    }
}
