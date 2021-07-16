package com.meilancycling.mema.network.bean.response;

public class MotionDetailsResponse {
    /**
     * motionCyclingResponseVo : {"activityDate":"2020-06-24T10:09:28.932Z","activityTime":0,"ascent":0,"avgAltitude":0,"avgCadence":0,"avgHrm":0,"avgPower":0,"avgSpeed":0,"avgTemperature":0,"descent":0,"distance":0,"hrTypeZone":0,"hrmZone":"string","maxAltitude":0,"maxCadence":0,"maxHrm":0,"maxPower":0,"maxSpeed":0,"maxTemperature":0,"minAltitude":0,"minHrm":0,"motionCyclingRecordPo":{"altitudeVos":"string","cadenceVos":"string","hrmVos":"string","id":0,"lap":"string","latLonVos":"string","motionId":0,"powerVos":"string","speedVos":"string","temperatureVos":"string"},"motionId":0,"powerTypeZone":0,"powerZone":"string","slope":"string","totalCalories":0,"totalLaps":0,"totalTime":0}
     * otherMotion : {"activeTime":0,"ascent":0,"avgSpeed":0,"distance":0,"id":0,"isCompetition":"string","motionDate":"2020-06-24T10:09:28.932Z","motionType":0,"totalCalories":0}
     */
    private MotionCyclingResponseVoBean motionCyclingResponseVo;


    public MotionCyclingResponseVoBean getMotionCyclingResponseVo() {
        return motionCyclingResponseVo;
    }

    public void setMotionCyclingResponseVo(MotionCyclingResponseVoBean motionCyclingResponseVo) {
        this.motionCyclingResponseVo = motionCyclingResponseVo;
    }


    public static class MotionCyclingResponseVoBean {
        /**
         * activityDate : 2020-06-24T10:09:28.932Z
         * activityTime : 0
         * ascent : 0
         * avgAltitude : 0
         * avgCadence : 0
         * avgHrm : 0
         * avgPower : 0
         * avgSpeed : 0
         * avgTemperature : 0
         * descent : 0
         * distance : 0
         * hrTypeZone : 0
         * hrmZone : string
         * maxAltitude : 0
         * maxCadence : 0
         * maxHrm : 0
         * maxPower : 0
         * maxSpeed : 0
         * maxTemperature : 0
         * minAltitude : 0
         * minHrm : 0
         * motionCyclingRecordPo : {"altitudeVos":"string","cadenceVos":"string","hrmVos":"string","id":0,"lap":"string","latLonVos":"string","motionId":0,"powerVos":"string","speedVos":"string","temperatureVos":"string"}
         * motionId : 0
         * powerTypeZone : 0
         * powerZone : string
         * slope : string
         * totalCalories : 0
         * totalLaps : 0
         * totalTime : 0
         */

        private long activityDate;
        private int activityTime;
        private Integer ascent;
        private Integer avgAltitude;
        private int avgCadence;
        private int motionType;
        private String isCompetition;
        private int avgHrm;
        private int avgPower;
        private int avgSpeed;
        private Integer avgTemperature;
        private Integer descent;
        private int distance;
        private int hrTypeZone;
        private String hrmZone;
        private Integer maxAltitude;
        private int maxCadence;
        private int maxHrm;
        private int maxPower;
        private int maxSpeed;
        private Integer maxTemperature;
        private Integer minAltitude;
        private int minHrm;
        private MotionCyclingRecordPoBean motionCyclingRecordPo;
        private int motionId;
        private int powerTypeZone;
        private String powerZone;
        private String slope;
        private String timeType;
        private int totalCalories;
        private int totalLaps;
        private int totalTime;
        private String fitUrl;

        public String getFitUrl() {
            return fitUrl;
        }

        public void setFitUrl(String fitUrl) {
            this.fitUrl = fitUrl;
        }

        public String getTimeType() {
            return timeType;
        }

        public void setTimeType(String timeType) {
            this.timeType = timeType;
        }

        public int getMotionType() {
            return motionType;
        }

        public void setMotionType(int motionType) {
            this.motionType = motionType;
        }

        public String getIsCompetition() {
            return isCompetition;
        }

        public void setIsCompetition(String isCompetition) {
            this.isCompetition = isCompetition;
        }

        public long getActivityDate() {
            return activityDate;
        }

        public void setActivityDate(long activityDate) {
            this.activityDate = activityDate;
        }

        public int getActivityTime() {
            return activityTime;
        }

        public void setActivityTime(int activityTime) {
            this.activityTime = activityTime;
        }

        public Integer getAscent() {
            return ascent;
        }

        public void setAscent(Integer ascent) {
            this.ascent = ascent;
        }

        public Integer getAvgAltitude() {
            return avgAltitude;
        }

        public void setAvgAltitude(Integer avgAltitude) {
            this.avgAltitude = avgAltitude;
        }

        public int getAvgCadence() {
            return avgCadence;
        }

        public void setAvgCadence(int avgCadence) {
            this.avgCadence = avgCadence;
        }

        public int getAvgHrm() {
            return avgHrm;
        }

        public void setAvgHrm(int avgHrm) {
            this.avgHrm = avgHrm;
        }

        public int getAvgPower() {
            return avgPower;
        }

        public void setAvgPower(int avgPower) {
            this.avgPower = avgPower;
        }

        public int getAvgSpeed() {
            return avgSpeed;
        }

        public void setAvgSpeed(int avgSpeed) {
            this.avgSpeed = avgSpeed;
        }

        public Integer getAvgTemperature() {
            return avgTemperature;
        }

        public void setAvgTemperature(Integer avgTemperature) {
            this.avgTemperature = avgTemperature;
        }

        public Integer getDescent() {
            return descent;
        }

        public void setDescent(Integer descent) {
            this.descent = descent;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getHrTypeZone() {
            return hrTypeZone;
        }

        public void setHrTypeZone(int hrTypeZone) {
            this.hrTypeZone = hrTypeZone;
        }

        public String getHrmZone() {
            return hrmZone;
        }

        public void setHrmZone(String hrmZone) {
            this.hrmZone = hrmZone;
        }

        public Integer getMaxAltitude() {
            return maxAltitude;
        }

        public void setMaxAltitude(Integer maxAltitude) {
            this.maxAltitude = maxAltitude;
        }

        public int getMaxCadence() {
            return maxCadence;
        }

        public void setMaxCadence(int maxCadence) {
            this.maxCadence = maxCadence;
        }

        public int getMaxHrm() {
            return maxHrm;
        }

        public void setMaxHrm(int maxHrm) {
            this.maxHrm = maxHrm;
        }

        public int getMaxPower() {
            return maxPower;
        }

        public void setMaxPower(int maxPower) {
            this.maxPower = maxPower;
        }

        public int getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(int maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        public Integer getMaxTemperature() {
            return maxTemperature;
        }

        public void setMaxTemperature(Integer maxTemperature) {
            this.maxTemperature = maxTemperature;
        }

        public Integer getMinAltitude() {
            return minAltitude;
        }

        public void setMinAltitude(Integer minAltitude) {
            this.minAltitude = minAltitude;
        }

        public int getMinHrm() {
            return minHrm;
        }

        public void setMinHrm(int minHrm) {
            this.minHrm = minHrm;
        }

        public MotionCyclingRecordPoBean getMotionCyclingRecordPo() {
            return motionCyclingRecordPo;
        }

        public void setMotionCyclingRecordPo(MotionCyclingRecordPoBean motionCyclingRecordPo) {
            this.motionCyclingRecordPo = motionCyclingRecordPo;
        }

        public int getMotionId() {
            return motionId;
        }

        public void setMotionId(int motionId) {
            this.motionId = motionId;
        }

        public int getPowerTypeZone() {
            return powerTypeZone;
        }

        public void setPowerTypeZone(int powerTypeZone) {
            this.powerTypeZone = powerTypeZone;
        }

        public String getPowerZone() {
            return powerZone;
        }

        public void setPowerZone(String powerZone) {
            this.powerZone = powerZone;
        }

        public String getSlope() {
            return slope;
        }

        public void setSlope(String slope) {
            this.slope = slope;
        }

        public int getTotalCalories() {
            return totalCalories;
        }

        public void setTotalCalories(int totalCalories) {
            this.totalCalories = totalCalories;
        }

        public int getTotalLaps() {
            return totalLaps;
        }

        public void setTotalLaps(int totalLaps) {
            this.totalLaps = totalLaps;
        }

        public int getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(int totalTime) {
            this.totalTime = totalTime;
        }

        public static class MotionCyclingRecordPoBean {
            /**
             * altitudeVos : string
             * cadenceVos : string
             * hrmVos : string
             * id : 0
             * lap : string
             * latLonVos : string
             * motionId : 0
             * powerVos : string
             * speedVos : string
             * temperatureVos : string
             */

            private String altitudeVos;
            private String cadenceVos;
            private String hrmVos;
            private int id;
            private String lap;
            private String latLonVos;
            private int motionId;
            private String powerVos;
            private String speedVos;
            private String temperatureVos;

            public String getAltitudeVos() {
                return altitudeVos;
            }

            public void setAltitudeVos(String altitudeVos) {
                this.altitudeVos = altitudeVos;
            }

            public String getCadenceVos() {
                return cadenceVos;
            }

            public void setCadenceVos(String cadenceVos) {
                this.cadenceVos = cadenceVos;
            }

            public String getHrmVos() {
                return hrmVos;
            }

            public void setHrmVos(String hrmVos) {
                this.hrmVos = hrmVos;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLap() {
                return lap;
            }

            public void setLap(String lap) {
                this.lap = lap;
            }

            public String getLatLonVos() {
                return latLonVos;
            }

            public void setLatLonVos(String latLonVos) {
                this.latLonVos = latLonVos;
            }

            public int getMotionId() {
                return motionId;
            }

            public void setMotionId(int motionId) {
                this.motionId = motionId;
            }

            public String getPowerVos() {
                return powerVos;
            }

            public void setPowerVos(String powerVos) {
                this.powerVos = powerVos;
            }

            public String getSpeedVos() {
                return speedVos;
            }

            public void setSpeedVos(String speedVos) {
                this.speedVos = speedVos;
            }

            public String getTemperatureVos() {
                return temperatureVos;
            }

            public void setTemperatureVos(String temperatureVos) {
                this.temperatureVos = temperatureVos;
            }
        }
    }
}

    
            
