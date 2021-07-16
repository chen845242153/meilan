package com.lion.common.eventbus.sensor;

/**
 * @Description: 作用描述
 * @Author: lion 571135591
 * @CreateDate: 2021/6/18 11:45 上午
 */
public class SensorMessageType {
    public static final int ACTION_TIME_WARMING = 1000;
    public static final int ACTION_DISTANCE_WARMING = 1001;
    public static final int ACTION_SPEED_WARMING = 1002;
    public static final int ACTION_SPEED_LOW_WARMING = 1003;
    public static final int ACTION_CADENCE_WARMING = 1004;
    public static final int ACTION_CADENCE_LOW_WARMING = 1005;
    public static final int ACTION_HRM_WARMING = 1006;
    public static final int ACTION_HRM_LOW_WARMING = 1007;
    public static final int ACTION_POWER_WARMING = 1008;
    public static final int ACTION_POWER_LOW_WARMING = 1009;

    public static final int ACTION_CARRY_ON_MOVEMENT = 1010;
    public static final int ACTION_LAP_MOVEMENT = 1011;
    public static final int ACTION_SUSPEND_MOVEMENT = 1012;

    public static final int ACTION_START_MOTION = 1013;
    public static final int ACTION_END_MOVEMENT = 1014;
    public static final int ACTION_TYPE_ACTIVITY = 1015;
    public static final int ACTION_LOCATION_CHANGE = 1016;

}
