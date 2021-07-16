package com.meilancycling.mema.ui.club.model;

import com.meilancycling.mema.base.BaseCustomViewModel;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.utils.AppUtils;

public class CommentModel extends BaseCustomViewModel {
    public String content;
    public long createDate;
    public String headerUrl;
    public String nickName;

    public String getCommentData(long createDate) {
        long currentTimeMillis = System.currentTimeMillis();
        long diffTime = currentTimeMillis - createDate;
        if (diffTime < 60 * 1000) {
            long time = diffTime / 1000;
            return time + "秒以前";
        } else if (diffTime < 3600 * 1000) {
            long time = diffTime / (1000 * 60);
            return time + "分钟以前";
        } else if (diffTime < 24 * 3600 * 1000) {
            long time = diffTime / (1000 * 3600);
            return time + "小时以前";
        } else if (diffTime < 7 * 24 * 3600 * 1000) {
            long time = diffTime / (1000 * 3600 * 24);
            return time + "天以前";
        } else {
            long commentTime = createDate - 8 * 3600 * 1000;
            return AppUtils.timeToString(commentTime, Config.TIME_RECORD);
        }
    }
}
