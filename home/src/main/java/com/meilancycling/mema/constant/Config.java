package com.meilancycling.mema.constant;


/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/12 2:35 PM
 */
public class Config {
    /**
     * Service UUID
     * APP扫描,扫描到之后进行连接
     * 用于设备Notify 数据给APP
     * 用于APP 写数据到设备
     */
    public static final String SERVICE_UUID = "0000a000-0000-1000-8000-00805f9b34fb";

    public static String CHINESE = "zh";
    public final static int TYPE_PHONE = 1;
    public final static int TYPE_MAILBOX = 2;
    public final static int TYPE_WX = 3;
    public final static int TYPE_QQ = 4;
    public final static int TYPE_GOOGLE = 5;
    public final static int TYPE_FACEBOOK = 6;
    public final static String CURRENT_USER = "current_user";
    public final static String IS_FIRST = "isFirst";
    public final static String DISCONNECT_NETWORK = "-1";
    public static int START_YEAR = 2013;
    public static String DEFAULT_PATTERN_COMPLETE = "yyyy-MM-dd HH:mm:ss";
    public static String DEFAULT_PATTERN = "yyyy-MM-dd";
    public static String DEFAULT_PATTERN_YEAR = "yyyy-MM";
    public static String TIME_PATTERN = "yyyy/MM/dd HH:mm";
    public static String TIME_RECORD = "yyyy/MM/dd";
    public static String TIME_YEAR = "yyyy";
    public static String TIME_RECORD_YEAR = "yyyy/MM";
    public static String TIME_RECORD_CHART = "MM/dd";
    public static String TIME_RECORD_CHART_YEAR = "MM";
    /**
     * 1骑车，2骑行台，3骑行竞赛
     */
    public static final int SPORT_OUTDOOR = 1;
    public static final int SPORT_INDOOR = 2;
    public static final int SPORT_COMPETITION = 3;
    public volatile static int unit;
    public static final int G_MAX_HEIGHT = 250;
    public static final int G_MIN_HEIGHT = 50;
    public static final float Y_MAX_HEIGHT = 8.2f;
    public static final float Y_MIN_HEIGHT = 1.6f;
    public static final int G_MAX_WEIGHT = 250;
    public static final int G_MIN_WEIGHT = 30;
    public static final float Y_MAX_WEIGHT = 550;
    public static final float Y_MIN_WEIGHT = 67;
    public static final int NEED_GUIDE = 1;

    /**
     * 运动类型
     */
    public static final int OUTDOOR_CYCLING = 1;
    public static final int INDOOR_CYCLING = 2;
    public static final int COMPETITION = 3;
    /**
     * 单位 米
     */
    public static final int TOTAL_DISTANCE = 300000;
    /**
     * 单位 小时
     */
    public static final int TOTAL_TIME = 100;
    public static final int TOTAL_CAL = 20000;
}
