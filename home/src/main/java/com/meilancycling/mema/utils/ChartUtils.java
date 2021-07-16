package com.meilancycling.mema.utils;

/**
 * @Description: 作用描述
 * @Author: lion
 * @CreateDate: 2020/11/10 5:54 PM
 */
public class ChartUtils {
    /**
     * 计算表格差值
     */
    public static int getDistanceDifference(double max) {
        if (max < 5) {
            return 1;
        } else if (max < 10) {
            return 2;
        } else if (max < 20) {
            return 4;
        } else {
            return getDifference(max);
        }
    }

    /**
     * 获取差值
     */
    public static int getDifference(double max) {
        double diff = max / 5;
        if (diff < 100) {
            if (diff == 0) {
                return 1;
            } else {
                return ((int) Math.ceil(diff / 5)) * 5;
            }
        } else if (diff < 500) {
            return ((int) Math.ceil(diff / 20)) * 20;
        } else if (diff < 1000) {
            return ((int) Math.ceil(diff / 50)) * 50;
        } else if (diff < 5000) {
            return ((int) Math.ceil(diff / 100)) * 100;
        } else {
            return ((int) Math.ceil(diff / 500)) * 500;
        }

    }

    /**
     * 海拔 获取差值
     * 0 间隔
     * 1 最小值
     */
    public static int[] getAltitudeDifference(double max, double min) {
        int[] array = new int[2];
        int diff = getDistanceDifference(max - min);
        int minValue = ((int) Math.floor(min / diff)) * diff;
        while (true) {
            if (minValue + 5 * diff < max) {
                diff = getDistanceDifference(max - minValue);
                minValue = ((int) Math.floor(min / diff)) * diff;
            } else {
                break;
            }
        }
        array[0] = diff;
        array[1] = minValue;
        return array;
    }

}
