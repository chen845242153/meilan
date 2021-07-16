package com.meilancycling.mema.inf;

import com.meilancycling.mema.network.bean.response.MotionSumResponse;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/16 10:51 AM
 */
public interface RecordDataChange {
    int RECORD_TYPE_WEEK = 0;
    int RECORD_TYPE_MONTH = 1;
    int RECORD_TYPE_YEAR = 2;

    /**
     * 界面修改
     *
     * @param motionSumResponse 数据
     * @param type              类型
     */
    void changeData(MotionSumResponse motionSumResponse, int type);
}
