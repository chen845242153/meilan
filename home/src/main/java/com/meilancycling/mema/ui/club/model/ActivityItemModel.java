package com.meilancycling.mema.ui.club.model;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseCustomViewModel;

public class ActivityItemModel extends BaseCustomViewModel {
    public String pictureUrl;
    public String activityName;
    public String activityType;
    public String activityTitle;
    public String activityTime;
    // 报名状态(官方1.报名 2已报名 5结束 6活动结束 已参加)(代理商1.报名 2已报名 3报名已满 4.截止报名 5结束 6活动结束 已参加 7活动进行中)
    public int activityStatus;
    public int id;

    public boolean isOfficial(String type) {
        return "1".equals(type);
    }

    public String getStatusName(int activityStatus) {
        String statusName = "";
        switch (activityStatus) {
            case 1:
                statusName = MyApplication.mInstance.getString(R.string.enter_nama);
                break;
            case 2:
                statusName = MyApplication.mInstance.getString(R.string.entered_nama);
                break;
            case 3:
                statusName = MyApplication.mInstance.getString(R.string.enter_nama_full);
                break;
            case 4:
                statusName = MyApplication.mInstance.getString(R.string.deadline_enter_nama);
                break;
            case 5:
                statusName = MyApplication.mInstance.getString(R.string.the_end);
                break;
            case 6:
                statusName = MyApplication.mInstance.getString(R.string.have_attend);
                break;
            case 7:
                statusName = MyApplication.mInstance.getString(R.string.activities_progress);
                break;

            default:
        }
        return statusName;
    }

    public Drawable getStatusDraw(int activityStatus) {
        switch (activityStatus) {
            case 1:
                return ContextCompat.getDrawable(MyApplication.mInstance, R.drawable.shape_line_main_15);
            case 2:
                return ContextCompat.getDrawable(MyApplication.mInstance, R.drawable.shape_line_blue_15);
            default:
                return ContextCompat.getDrawable(MyApplication.mInstance, R.drawable.shape_line_99_15);
        }
    }

    public int getId() {
        return id;
    }
}
