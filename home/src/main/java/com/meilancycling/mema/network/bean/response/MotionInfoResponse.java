package com.meilancycling.mema.network.bean.response;

import java.util.List;

public class MotionInfoResponse {

    /**
     * pageNum : 1
     * pageSize : 100
     * startRow : 0
     * endRow : 100
     * total : 8
     * pages : 1
     * rows : [{"id":778,"motionType":1,"motionName":"","dataSource":2,"upStatus":0,"userId":171,"isCompetition":"0","motionDate":1591027200000,"activeTime":0,"distance":0,"avgSpeed":0,"avgPace":0,"maxPace":0,"maxSpeed":0,"calories":0,"steps":0,"climb":0,"maxSlope":0,"swolf":0,"ascent":0,"maxAltitude":0},{"id":779,"motionType":2,"motionName":"1","dataSource":2,"upStatus":0,"userId":171,"isCompetition":"0","motionDate":1590941040000,"activeTime":176960,"distance":321868,"avgSpeed":0,"avgPace":0,"maxPace":0,"maxSpeed":0,"calories":0,"steps":0,"climb":0,"maxSlope":0,"swolf":0,"ascent":0,"maxAltitude":0},{"id":781,"motionType":1,"motionName":"qqyshdhdhfhfhfhfhfhjfjfjfjfjfjfjfjgjgjgj","dataSource":2,"upStatus":0,"userId":171,"isCompetition":"0","motionDate":1590761580000,"activeTime":0,"distance":0,"avgSpeed":0,"avgPace":0,"maxPace":0,"maxSpeed":0,"calories":0,"steps":0,"climb":0,"maxSlope":0,"swolf":0,"ascent":0,"maxAltitude":0},{"id":780,"motionType":7,"motionName":"vb","dataSource":2,"upStatus":0,"userId":171,"isCompetition":"0","motionDate":1590688860000,"activeTime":60,"distance":4828,"avgSpeed":0,"avgPace":0,"maxPace":0,"maxSpeed":0,"calories":0,"steps":0,"climb":0,"maxSlope":0,"swolf":0,"ascent":0,"maxAltitude":0},{"id":785,"motionType":1,"dataSource":1,"upStatus":0,"userId":171,"isCompetition":"0","motionDate":1563267247000,"activeTime":12674,"distance":55312,"avgSpeed":207,"avgPace":0,"maxPace":0,"maxSpeed":558,"calories":769,"steps":0,"lap":1,"climb":0,"maxSlope":0,"swolf":0,"ascent":736,"maxAltitude":0},{"id":775,"motionType":1,"dataSource":1,"upStatus":0,"userId":171,"isCompetition":"0","motionDate":1514765607000,"activeTime":1059,"distance":8659,"avgSpeed":304,"avgPace":0,"maxPace":0,"maxSpeed":793,"calories":155,"steps":0,"lap":2,"climb":0,"maxSlope":0,"swolf":0,"ascent":6,"maxAltitude":0},{"id":782,"motionType":1,"motionName":"中文测试","dataSource":2,"upStatus":0,"userId":171,"isCompetition":"0","motionDate":0,"activeTime":60,"distance":20303000,"avgSpeed":0,"avgPace":0,"maxPace":0,"maxSpeed":0,"calories":0,"steps":0,"climb":0,"maxSlope":0,"swolf":0,"ascent":0,"maxAltitude":0},{"id":783,"motionType":2,"motionName":"中文测试","dataSource":2,"upStatus":0,"userId":171,"isCompetition":"0","motionDate":0,"activeTime":7323,"distance":10200000,"avgSpeed":0,"avgPace":0,"maxPace":0,"maxSpeed":0,"calories":0,"steps":0,"climb":0,"maxSlope":0,"swolf":0,"ascent":0,"maxAltitude":0}]
     */

    private int pageNum;
    private int pageSize;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private List<RowsBean> rows;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 778
         * motionType : 1
         * motionName :
         * dataSource : 2
         * upStatus : 0
         * userId : 171
         * isCompetition : 0
         * motionDate : 1591027200000
         * activeTime : 0
         * distance : 0
         * avgSpeed : 0
         * avgPace : 0
         * maxPace : 0
         * maxSpeed : 0
         * calories : 0
         * steps : 0
         * climb : 0
         * maxSlope : 0
         * swolf : 0
         * ascent : 0
         * maxAltitude : 0
         * lap : 1
         */

        private int id;
        private int motionType;
        private String motionName;
        private int dataSource;
        private int upStatus;
        private int userId;
        private String isCompetition;
        private long motionDate;
        private int activeTime;
        private int distance;
        private int avgSpeed;
        private int avgPace;
        private int maxPace;
        private int maxSpeed;
        private int calories;
        private int steps;
        private int climb;
        private int maxSlope;
        private int swolf;
        private String timeType;
        private int ascent;
        private int maxAltitude;
        private int lap;
        private String equipmentSource;

        public String getTimeType() {
            return timeType;
        }

        public void setTimeType(String timeType) {
            this.timeType = timeType;
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

        public int getMotionType() {
            return motionType;
        }

        public void setMotionType(int motionType) {
            this.motionType = motionType;
        }

        public String getMotionName() {
            return motionName;
        }

        public void setMotionName(String motionName) {
            this.motionName = motionName;
        }

        public int getDataSource() {
            return dataSource;
        }

        public void setDataSource(int dataSource) {
            this.dataSource = dataSource;
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

        public String getIsCompetition() {
            return isCompetition;
        }

        public void setIsCompetition(String isCompetition) {
            this.isCompetition = isCompetition;
        }

        public long getMotionDate() {
            return motionDate;
        }

        public void setMotionDate(long motionDate) {
            this.motionDate = motionDate;
        }

        public int getActiveTime() {
            return activeTime;
        }

        public void setActiveTime(int activeTime) {
            this.activeTime = activeTime;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getAvgSpeed() {
            return avgSpeed;
        }

        public void setAvgSpeed(int avgSpeed) {
            this.avgSpeed = avgSpeed;
        }

        public int getAvgPace() {
            return avgPace;
        }

        public void setAvgPace(int avgPace) {
            this.avgPace = avgPace;
        }

        public int getMaxPace() {
            return maxPace;
        }

        public void setMaxPace(int maxPace) {
            this.maxPace = maxPace;
        }

        public int getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(int maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        public int getCalories() {
            return calories;
        }

        public void setCalories(int calories) {
            this.calories = calories;
        }

        public int getSteps() {
            return steps;
        }

        public void setSteps(int steps) {
            this.steps = steps;
        }

        public int getClimb() {
            return climb;
        }

        public void setClimb(int climb) {
            this.climb = climb;
        }

        public int getMaxSlope() {
            return maxSlope;
        }

        public void setMaxSlope(int maxSlope) {
            this.maxSlope = maxSlope;
        }

        public int getSwolf() {
            return swolf;
        }

        public void setSwolf(int swolf) {
            this.swolf = swolf;
        }

        public int getAscent() {
            return ascent;
        }

        public void setAscent(int ascent) {
            this.ascent = ascent;
        }

        public int getMaxAltitude() {
            return maxAltitude;
        }

        public void setMaxAltitude(int maxAltitude) {
            this.maxAltitude = maxAltitude;
        }

        public int getLap() {
            return lap;
        }

        public void setLap(int lap) {
            this.lap = lap;
        }
    }
}
