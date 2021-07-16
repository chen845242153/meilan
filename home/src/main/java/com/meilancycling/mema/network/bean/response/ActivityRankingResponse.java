package com.meilancycling.mema.network.bean.response;

import java.util.List;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2021/5/28 10:33 上午
 */
public class ActivityRankingResponse {
    private int signNum;
    private int userRanking;
    /**
     * nickName : 10006033ooo
     * headerUrl : http://cnweb.meilancycling.com/images/user/header/6053/20210326/7981f4bacbba4bf4a64fb82198ee344e.png?Expires=2247465870&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=FlAhzlLP5WI4Wa8K3FXmr96huS8%3D
     * nationalFlag : http://enweb.meilancycling.com/images/country/ET.png?Expires=2251442225&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=oCWcX7nXEhxi78ITatgCNQaTBfU%3D
     * odo : 0
     */

    private List<ListBean> list;

    public int getSignNum() {
        return signNum;
    }

    public void setSignNum(int signNum) {
        this.signNum = signNum;
    }

    public int getUserRanking() {
        return userRanking;
    }

    public void setUserRanking(int userRanking) {
        this.userRanking = userRanking;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String nickName;
        private String headerUrl;
        private String nationalFlag;
        private int odo;

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

        public int getOdo() {
            return odo;
        }

        public void setOdo(int odo) {
            this.odo = odo;
        }
    }

}
