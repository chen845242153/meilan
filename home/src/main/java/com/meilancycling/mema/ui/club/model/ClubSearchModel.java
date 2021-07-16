package com.meilancycling.mema.ui.club.model;

import com.meilancycling.mema.base.BaseCustomViewModel;

public class ClubSearchModel extends BaseCustomViewModel {
    public int id;
    public String picturePath;
    public String title;
    public String date;
    public int searchType;
    public int contentType;
    public String titleFlag;
    //0 隐藏 1 显示新闻  2 显示挑战
    public int headFlag;
}
