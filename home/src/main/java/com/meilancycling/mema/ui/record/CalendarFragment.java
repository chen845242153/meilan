package com.meilancycling.mema.ui.record;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.request.MotionRequest;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.FragmentCalendarBinding;
import com.meilancycling.mema.customview.dialog.SelectYearDialog;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: 锻炼
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class CalendarFragment extends BaseFragment {
    FragmentCalendarBinding mFragmentCalendarBinding;
    private int year;
    private int month;
    private int day;

    @SuppressLint("UseRequireInsteadOfGet")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentCalendarBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false);
        setData(0, 0, 0, 0);
        java.util.Calendar current = java.util.Calendar.getInstance();
        year = current.get(java.util.Calendar.YEAR);
        month = current.get(java.util.Calendar.MONTH) + 1;
        day = current.get(java.util.Calendar.DATE);
        mFragmentCalendarBinding.calendarView.setRange(1990, 1, 1, year, 12, 30);
        mFragmentCalendarBinding.calendarView.scrollToCalendar(year, month, day);
        mFragmentCalendarBinding.tvCalendarYear.setText(String.valueOf(year));
        initMagicIndicator();
        mFragmentCalendarBinding.miCalendar.onPageSelected(month - 1);
        mFragmentCalendarBinding.miCalendar.onPageScrolled(month - 1, 0.0F, 0);

        mFragmentCalendarBinding.calendarView.setOnMonthChangeListener((year, month) -> {
            if (!(year == CalendarFragment.this.year && month == CalendarFragment.this.month)) {
                CalendarFragment.this.year = year;
                CalendarFragment.this.month = month;
                mFragmentCalendarBinding.miCalendar.onPageSelected(month - 1);
                mFragmentCalendarBinding.miCalendar.onPageScrolled(month - 1, 0.0F, 0);
                mFragmentCalendarBinding.tvCalendarYear.setText(String.valueOf(year));
                queryCalendarData();
            }
        });

        mFragmentCalendarBinding.llCalendarYear.setOnClickListener(v -> {
            SelectYearDialog selectYearDialog = new SelectYearDialog(getContext(), year);
            selectYearDialog.show();
            selectYearDialog.setSelectYearClickListener(value -> {
                if (year != value) {
                    year = value;
                    mFragmentCalendarBinding.calendarView.scrollToCalendar(year, month, day);
                    queryCalendarData();
                    mFragmentCalendarBinding.tvCalendarYear.setText(String.valueOf(year));
                }
            });
        });
        queryCalendarData();
        mFragmentCalendarBinding.calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {
            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                if (isClick) {
                    long timeInMillis = calendar.getTimeInMillis();
                    if (timeInMillis <= System.currentTimeMillis()) {
                        String format = AppUtils.timeToString(timeInMillis, Config.DEFAULT_PATTERN);
                        SelectDayActivity.enterSelectDay(getContext(), format);
                    }
                }
            }
        });
        Objects.requireNonNull(getContext()).registerReceiver(mReceiver, new IntentFilter(BroadcastConstant.ACTION_DELETE_RECORD));
        return mFragmentCalendarBinding.getRoot();
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            queryCalendarData();
        }
    };

    public void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return getResources().getStringArray(R.array.month_list).length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(getResources().getStringArray(R.array.month_list)[index]);
                clipPagerTitleView.setTextColor(R.color.black_6);
                clipPagerTitleView.setClipColor(R.color.black_3);
                clipPagerTitleView.setOnClickListener(v -> {
                    mFragmentCalendarBinding.miCalendar.onPageSelected(index);
                    mFragmentCalendarBinding.miCalendar.onPageScrolled(index, 0.0F, 0);
                    CalendarFragment.this.month = index + 1;
                    mFragmentCalendarBinding.calendarView.scrollToCalendar(year, month, day);
                    queryCalendarData();
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setLineHeight(AppUtils.dipToPx(context, 5));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setYOffset(0);
                indicator.setColors(Color.parseColor("#FF6FB92C"));
                return indicator;
            }

        });
        mFragmentCalendarBinding.miCalendar.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                if (getContext() != null) {
                    return UIUtil.dip2px(getContext(), 10);
                } else {
                    return 0;
                }
            }
        });
    }

    private void setData(int distance, int time, int cal, int num) {
        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), distance);
        mFragmentCalendarBinding.ctvDistance.setData(unitBean.getValue(), getString(R.string.distance), unitBean.getUnit());
        String timeFormat = UnitConversionUtil.getUnitConversionUtil().timeFormat(time);
        mFragmentCalendarBinding.ctvTime.setData(timeFormat, getString(R.string.time), getString(R.string.time_format));
        mFragmentCalendarBinding.ctvCal.setData(String.valueOf(cal), getString(R.string.consume), getString(R.string.unit_cal));
        mFragmentCalendarBinding.ctvNumber.setData(String.valueOf(num), getString(R.string.times), null);
    }

    private void queryCalendarData() {
        mActivity.showLoadingDialog();
        setData(0, 0, 0, 0);
        MotionRequest motionRequest = new MotionRequest();
        motionRequest.setStartDate(getStartTime(year, month));
        motionRequest.setDataType(3);
        motionRequest.setEndDate(getEndTime(year, month));
        motionRequest.setMotionType(0);
        motionRequest.setSession(mActivity.getUserInfoEntity().getSession());
        motionRequest.setTimeType(6);
        RetrofitUtils.getApiUrl()
                .queryMotionSumData(motionRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<MotionSumResponse>() {
                    @Override
                    public void onSuccess(MotionSumResponse motionSumResponse) {
                        mActivity.hideLoadingDialog();
                        setData(motionSumResponse.getSumDistance(), motionSumResponse.getSumTime(), motionSumResponse.getSumKcal(), motionSumResponse.getSumMotion());
                        List<MotionSumResponse.MotionListBean> motionList = motionSumResponse.getMotionList();
                        if (motionList != null && motionList.size() > 0) {
                            Map<String, Calendar> map = new HashMap<>();
                            for (MotionSumResponse.MotionListBean motionListBean : motionList) {
                                String format;
                                if ("0".equals(motionListBean.getTimeType())) {
                                    format = AppUtils.timeToString(Long.parseLong(motionListBean.getMotionDate()), Config.DEFAULT_PATTERN);
                                } else {
                                    format = AppUtils.zeroTimeToString(Long.parseLong(motionListBean.getMotionDate()), Config.DEFAULT_PATTERN);
                                }
                                Calendar calendar = new Calendar();
                                String[] split = format.split("-");
                                calendar.setYear(Integer.parseInt(split[0]));
                                calendar.setMonth(Integer.parseInt(split[1]));
                                calendar.setDay(Integer.parseInt(split[2]));
                                Calendar value = map.get(calendar.toString());
                                if (value != null) {
                                    switch (motionListBean.getMotionType()) {
                                        case Config.SPORT_INDOOR:
                                            value.addScheme(Color.parseColor("#FF5DA2C7"), String.valueOf(Config.SPORT_INDOOR));
                                            break;
                                        case Config.SPORT_COMPETITION:
                                            value.addScheme(Color.parseColor("#FFFFBB12"), String.valueOf(Config.SPORT_COMPETITION));
                                            break;
                                        default:
                                            value.addScheme(Color.parseColor("#FF6FB92C"), String.valueOf(Config.SPORT_OUTDOOR));
                                    }
                                    map.put(calendar.toString(), value);
                                } else {
                                    switch (motionListBean.getMotionType()) {
                                        case Config.SPORT_INDOOR:
                                            calendar.addScheme(Color.parseColor("#FF5DA2C7"), String.valueOf(Config.SPORT_INDOOR));
                                            break;
                                        case Config.SPORT_COMPETITION:
                                            calendar.addScheme(Color.parseColor("#FFFFBB12"), String.valueOf(Config.SPORT_COMPETITION));
                                            break;
                                        default:
                                            calendar.addScheme(Color.parseColor("#FF6FB92C"), String.valueOf(Config.SPORT_OUTDOOR));
                                    }
                                    map.put(calendar.toString(), calendar);
                                }
                            }
                            mFragmentCalendarBinding.calendarView.setSchemeDate(map);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        mActivity.hideLoadingDialog();
                    }
                });
    }

    private String getStartTime(int year, int month) {
        if (month < 10) {
            return year + "-0" + month + "-01";
        } else {
            return year + "-" + month + "-01";
        }
    }

    private String getEndTime(int year, int month) {
        String result = "";
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
                result = year + "-0" + month + "-31";
                break;
            case 10:
            case 12:
                result = year + "-" + month + "-31";
                break;
            case 2:
                if (year % 4 == 0) {
                    result = year + "-0" + month + "-29";
                } else {
                    result = year + "-0" + month + "-28";
                }
                break;
            case 4:
            case 6:
            case 9:
                result = year + "-0" + month + "-30";
                break;
            case 11:
                result = year + "-" + month + "-30";
                break;
            default:
        }
        return result;
    }

    @SuppressLint("UseRequireInsteadOfGet")
    @Override
    public void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(getContext()).unregisterReceiver(mReceiver);
    }
}
