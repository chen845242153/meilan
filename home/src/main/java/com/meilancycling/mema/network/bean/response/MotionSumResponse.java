package com.meilancycling.mema.network.bean.response;

import java.util.HashMap;
import java.util.List;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/12 2:11 PM
 */
public class MotionSumResponse {

    /**
     * calendarMap : {}
     * dayMap : {}
     * maxDistance : 0
     * maxTime : 0
     * motionList : [{"activeTime":0,"ascent":0,"avgPace":0,"avgSpeed":0,"calories":0,"climb":0,"coverImg":"string","createDate":"2020-11-12T06:03:24.195Z","dataSource":0,"distance":0,"equipmentSource":"string","id":0,"isCompetition":"string","lap":0,"mapRecord":"string","maxAltitude":0,"maxPace":0,"maxSlope":0,"maxSpeed":0,"motionDate":"2020-11-12T06:03:24.195Z","motionName":"string","motionRecord":"string","motionType":0,"steps":0,"swolf":0,"upDate":"2020-11-12T06:03:24.195Z","upStatus":0,"userId":0}]
     * sumDistance : 0
     * sumKcal : 0
     * sumMotion : 0
     * sumTime : 0
     * yearListData : [{"distance":0,"kcal":0,"motionDate":0}]
     */

    private HashMap<Long, String> calendarMap;
    private HashMap<Long, Long> dayMap;
    private int maxDistance;
    private int maxTime;
    private int sumDistance;
    private int sumKcal;
    private int sumMotion;
    private int sumTime;
    private List<MotionListBean> motionList;
    private List<YearListDataBean> yearListData;

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getSumDistance() {
        return sumDistance;
    }

    public void setSumDistance(int sumDistance) {
        this.sumDistance = sumDistance;
    }

    public int getSumKcal() {
        return sumKcal;
    }

    public void setSumKcal(int sumKcal) {
        this.sumKcal = sumKcal;
    }

    public int getSumMotion() {
        return sumMotion;
    }

    public void setSumMotion(int sumMotion) {
        this.sumMotion = sumMotion;
    }

    public int getSumTime() {
        return sumTime;
    }

    public void setSumTime(int sumTime) {
        this.sumTime = sumTime;
    }

    public List<MotionListBean> getMotionList() {
        return motionList;
    }

    public void setMotionList(List<MotionListBean> motionList) {
        this.motionList = motionList;
    }

    public List<YearListDataBean> getYearListData() {
        return yearListData;
    }

    public void setYearListData(List<YearListDataBean> yearListData) {
        this.yearListData = yearListData;
    }

    public static class CalendarMapBean {

    }

    public static class DayMapBean {

    }

    public HashMap<Long, String> getCalendarMap() {
        return calendarMap;
    }

    public void setCalendarMap(HashMap<Long, String> calendarMap) {
        this.calendarMap = calendarMap;
    }

    public HashMap<Long, Long> getDayMap() {
        return dayMap;
    }

    public void setDayMap(HashMap<Long, Long> dayMap) {
        this.dayMap = dayMap;
    }

    public static class MotionListBean {
        /**
         * activeTime : 0
         * ascent : 0
         * avgPace : 0
         * avgSpeed : 0
         * calories : 0
         * climb : 0
         * coverImg : string
         * createDate : 2020-11-12T06:03:24.195Z
         * dataSource : 0
         * distance : 0
         * equipmentSource : string
         * id : 0
         * isCompetition : string
         * lap : 0
         * mapRecord : string
         * maxAltitude : 0
         * maxPace : 0
         * maxSlope : 0
         * maxSpeed : 0
         * motionDate : 2020-11-12T06:03:24.195Z
         * motionName : string
         * motionRecord : string
         * motionType : 0
         * steps : 0
         * swolf : 0
         * upDate : 2020-11-12T06:03:24.195Z
         * upStatus : 0
         * userId : 0
         */

        private int activeTime;
        private int ascent;
        private int avgPace;
        private int avgSpeed;
        private int calories;
        private int climb;
        private String coverImg;
        private String createDate;
        private int dataSource;
        private int distance;
        private String equipmentSource;
        private int id;
        private String isCompetition;
        private int lap;
        private String mapRecord;
        private int maxAltitude;
        private int maxPace;
        private int maxSlope;
        private int maxSpeed;
        private String motionDate;
        private String motionName;
        private String motionRecord;
        private int motionType;
        private String timeType;
        private int steps;
        private int swolf;
        private String upDate;
        private int upStatus;
        private int userId;

        public String getTimeType() {
            return timeType;
        }

        public void setTimeType(String timeType) {
            this.timeType = timeType;
        }

        public int getActiveTime() {
            return activeTime;
        }

        public void setActiveTime(int activeTime) {
            this.activeTime = activeTime;
        }

        public int getAscent() {
            return ascent;
        }

        public void setAscent(int ascent) {
            this.ascent = ascent;
        }

        public int getAvgPace() {
            return avgPace;
        }

        public void setAvgPace(int avgPace) {
            this.avgPace = avgPace;
        }

        public int getAvgSpeed() {
            return avgSpeed;
        }

        public void setAvgSpeed(int avgSpeed) {
            this.avgSpeed = avgSpeed;
        }

        public int getCalories() {
            return calories;
        }

        public void setCalories(int calories) {
            this.calories = calories;
        }

        public int getClimb() {
            return climb;
        }

        public void setClimb(int climb) {
            this.climb = climb;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getDataSource() {
            return dataSource;
        }

        public void setDataSource(int dataSource) {
            this.dataSource = dataSource;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getEquipmentSource() {
            return equipmentSource;
        }

        public void setEquipmentSource(String equipmentSource) {
            this.equipmentSource = equipmentSource;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIsCompetition() {
            return isCompetition;
        }

        public void setIsCompetition(String isCompetition) {
            this.isCompetition = isCompetition;
        }

        public int getLap() {
            return lap;
        }

        public void setLap(int lap) {
            this.lap = lap;
        }

        public String getMapRecord() {
            return mapRecord;
        }

        public void setMapRecord(String mapRecord) {
            this.mapRecord = mapRecord;
        }

        public int getMaxAltitude() {
            return maxAltitude;
        }

        public void setMaxAltitude(int maxAltitude) {
            this.maxAltitude = maxAltitude;
        }

        public int getMaxPace() {
            return maxPace;
        }

        public void setMaxPace(int maxPace) {
            this.maxPace = maxPace;
        }

        public int getMaxSlope() {
            return maxSlope;
        }

        public void setMaxSlope(int maxSlope) {
            this.maxSlope = maxSlope;
        }

        public int getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(int maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        public String getMotionDate() {
            return motionDate;
        }

        public void setMotionDate(String motionDate) {
            this.motionDate = motionDate;
        }

        public String getMotionName() {
            return motionName;
        }

        public void setMotionName(String motionName) {
            this.motionName = motionName;
        }

        public String getMotionRecord() {
            return motionRecord;
        }

        public void setMotionRecord(String motionRecord) {
            this.motionRecord = motionRecord;
        }

        public int getMotionType() {
            return motionType;
        }

        public void setMotionType(int motionType) {
            this.motionType = motionType;
        }

        public int getSteps() {
            return steps;
        }

        public void setSteps(int steps) {
            this.steps = steps;
        }

        public int getSwolf() {
            return swolf;
        }

        public void setSwolf(int swolf) {
            this.swolf = swolf;
        }

        public String getUpDate() {
            return upDate;
        }

        public void setUpDate(String upDate) {
            this.upDate = upDate;
        }

        public int getUpStatus() {
            return upStatus;
        }

        public void setUpStatus(int upStatus) {
            this.upStatus = upStatus;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    public static class YearListDataBean {
        /**
         * distance : 0
         * kcal : 0
         * motionDate : 0
         */

        private int distance;
        private int kcal;
        private int motionDate;

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getKcal() {
            return kcal;
        }

        public void setKcal(int kcal) {
            this.kcal = kcal;
        }

        public int getMotionDate() {
            return motionDate;
        }

        public void setMotionDate(int motionDate) {
            this.motionDate = motionDate;
        }
    }

}
