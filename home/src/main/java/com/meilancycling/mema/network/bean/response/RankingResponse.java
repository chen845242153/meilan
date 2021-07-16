package com.meilancycling.mema.network.bean.response;

import java.util.List;

public class RankingResponse {
    /**
     * userId : 11079
     * nickName : 秋雨冬暖
     * headerUrl : http://thirdqq.qlogo.cn/g?b=oidb&k=eJ53mvYjr7vqMyicic8rU0Cw&s=100&t=1490122355
     * nationalFlag : null
     * distance : 1995
     * rank : 1
     */

    private int distance;
    private int userRanking;

    private List<ListBean> list;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public int getUserRanking() {
        return userRanking;
    }

    public void setUserRanking(int userRanking) {
        this.userRanking = userRanking;
    }

    public static class ListBean {
        private String userId;
        private String nickName;
        private String headerUrl;
        private String nationalFlag;
        private int distance;
        private int rank;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeaderUrl() {
            return headerUrl;
        }

        public void setHeaderUrl(String headerUrl) {
            this.headerUrl = headerUrl;
        }

        public String getNationalFlag() {
            return nationalFlag;
        }

        public void setNationalFlag(String nationalFlag) {
            this.nationalFlag = nationalFlag;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
}
