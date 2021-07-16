package com.meilancycling.mema.network.bean.request;

import java.util.List;

/**
 * @Description: 作用描述
 * @Author: lion 571135591
 * @CreateDate: 2021/6/11 3:09 下午
 */
public class AddSensorRequest {

    /**
     * sensorList : [{"sensorKey":"string","sensorName":"string","sensorNo":"string","sensorType":"string","userEquipmentId":0}]
     * session : 1234567890123456789012345678901234567890123
     */

    private String session;
    /**
     * sensorKey : string
     * sensorName : string
     * sensorNo : string
     * sensorType : string
     * userEquipmentId : 0
     */

    private List<SensorListBean> sensorList;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public List<SensorListBean> getSensorList() {
        return sensorList;
    }

    public void setSensorList(List<SensorListBean> sensorList) {
        this.sensorList = sensorList;
    }

    public static class SensorListBean {
        private String sensorKey;
        private String sensorName;
        private String sensorType;


        public String getSensorKey() {
            return sensorKey;
        }

        public void setSensorKey(String sensorKey) {
            this.sensorKey = sensorKey;
        }

        public String getSensorName() {
            return sensorName;
        }

        public void setSensorName(String sensorName) {
            this.sensorName = sensorName;
        }

        public String getSensorType() {
            return sensorType;
        }

        public void setSensorType(String sensorType) {
            this.sensorType = sensorType;
        }

    }
}
