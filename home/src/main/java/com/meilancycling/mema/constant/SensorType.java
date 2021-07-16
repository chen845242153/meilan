package com.meilancycling.mema.constant;

/**
 * @Description: 传感器类型
 * @Author: sore_lion
 * @CreateDate: 2020/11/12 2:41 PM
 */
public enum SensorType {
    /**
     * 时间
     */
    sport_time("运动时间"),
    total_time("总时间"),
    last_sport_time("上一段运动时间"),
    current_sport_time("当前段运动时间"),
    /**
     * 距离
     */
    sport_distance("运动距离"),
    last_sport_distance("上一段运动距离"),
    /**
     * 速度
     */
    speed("速度"),
    avg_speed("平均速度"),
    max_speed("最大速速"),
    current_avg_speed("当前段平均速度"),
    current_max_speed("当前段最大速度"),
    last_avg_speed("上一段平均速度"),
    last_max_speed("上一段最大速度"),

    /**
     * 踏频
     */
    cadence("踏频"),
    avg_cadence("平均踏频"),
    max_cadence("最大踏频"),
    current_avg_cadence("当前段平均踏频"),
    current_max_cadence("当前段最大踏频"),
    last_avg_cadence("上一段平均踏频"),
    last_max_cadence("上一段最大踏频"),
    /**
     * 心率
     */
    hrm("心率"),
    avg_hrm("平均心率"),
    max_hrm("最大心率"),
    current_avg_hrm("当前段平均心率"),
    current_max_hrm("当前段最大心率"),
    last_avg_hrm("上一段平均心率"),
    last_max_hrm("上一段最大心率"),
    /**
     * 功率
     */
    power("功率"),
    left_right_balance("左右平衡"),
    avg_left_right_balance("平均左右平衡"),
    avg_power("平均功率"),
    max_power("最大功率"),
    current_avg_power("当前段平均功率"),
    current_max_power("当前段最大功率"),
    last_avg_power("上一段平均功率"),
    last_max_power("上一段最大功率"),
    /**
     * 环境
     */
    calories("卡路里");

    public String name;

    SensorType(String name) {
        this.name = name;
    }

}
