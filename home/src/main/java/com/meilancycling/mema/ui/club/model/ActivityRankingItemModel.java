package com.meilancycling.mema.ui.club.model;

import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.base.BaseCustomViewModel;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.utils.UnitConversionUtil;

public class ActivityRankingItemModel extends BaseCustomViewModel {
    public int ranking;
    public String userName;
    public String countries;
    public String userPath;
    public int odo;

    public String getOdoValue(int odo) {
        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(MyApplication.mInstance, odo);
        return unitBean.getValue() + unitBean.getUnit();
    }
}
