package com.meilancycling.mema.ui.club;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2021/6/1 2:11 下午
 */
public interface ClubSignStatus {
    /**
     * 报名
     */
    int SIGN_UP = 1;
    /**
     * 已报名
     */
    int HAVE_SIGN_UP = 2;
    /**
     * 报名已满
     */
    int REGISTRATION_FULL = 3;
    /**
     * 截止报名
     */
    int CLOSE_SIGN_UP = 4;
    /**
     * 结束
     */
    int FINISH_SIGN_UP = 5;

}
