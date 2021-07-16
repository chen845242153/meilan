package com.meilancycling.mema.ui.record;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.databinding.FragmentWeekTopBinding;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.inf.RecordDataChange;
import com.meilancycling.mema.ui.adapter.WeekRecordPagerAdapter;
import com.meilancycling.mema.utils.AppUtils;

import java.util.Calendar;


/**
 * @Description:
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class WeekTopFragment extends BaseFragment {
    FragmentWeekTopBinding mFragmentWeekTopBinding;
    private WeekRecordPagerAdapter mWeekRecordPagerAdapter;
    RecordHomeFragment parentFragment;
    RecordDataChange mRecordDataChange;
    /**
     * 是否重新加载
     */
    private boolean isStart;
    /**
     * 是否加载左边
     */
    private boolean isLeft;
    private final long weekDiff = 7 * 24 * 60 * 60 * 1000;
    private int currentNum;
    private long weekEndTime;
    private Calendar mCalendar;

    public WeekTopFragment(RecordDataChange recordDataChange) {
        mRecordDataChange = recordDataChange;
    }

    public WeekTopFragment() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentWeekTopBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_week_top, container, false);
        mWeekRecordPagerAdapter = new WeekRecordPagerAdapter(getContext());
        mFragmentWeekTopBinding.vpWeek.setAdapter(mWeekRecordPagerAdapter);
        mFragmentWeekTopBinding.vpWeek.registerOnPageChangeCallback(mOnPageChangeCallback);
        parentFragment = (RecordHomeFragment) getParentFragment();
        mCalendar = Calendar.getInstance();
        getData(System.currentTimeMillis());
        mFragmentWeekTopBinding.viewWeekLeft.setOnClickListener(v -> mFragmentWeekTopBinding.vpWeek.setCurrentItem(mFragmentWeekTopBinding.vpWeek.getCurrentItem() - 1));
        mFragmentWeekTopBinding.viewWeekRight.setOnClickListener(v -> mFragmentWeekTopBinding.vpWeek.setCurrentItem(mFragmentWeekTopBinding.vpWeek.getCurrentItem() + 1));
        return mFragmentWeekTopBinding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void getData(long selectTime) {
        isStart = true;
        if (parentFragment != null) {
            mActivity.showLoadingDialog();
            mCalendar.setTimeInMillis(selectTime);
            int day = mCalendar.get(Calendar.DAY_OF_WEEK);
            if (day == 1) {
                mCalendar.add(Calendar.DATE, -6);
            } else {
                mCalendar.add(Calendar.DATE, -1 * (day - 1));
            }
            long start = mCalendar.getTimeInMillis();
            parentFragment.getFirstRecordDate(RecordDataChange.RECORD_TYPE_WEEK, start - weekDiff + RecordHomeFragment.ONE_DAY, start);
        }
        if (Config.unit == Unit.METRIC.value) {
            mFragmentWeekTopBinding.tvWeekUnit.setText("(" + getString(R.string.unit_km) + ")");
        } else {
            mFragmentWeekTopBinding.tvWeekUnit.setText("(" + getString(R.string.unit_mile) + ")");
        }
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private void initTitle(String weekStart, String weekEnd) {
        mFragmentWeekTopBinding.tvWeekTime.setText(weekStart + "-" + weekEnd);
        long endTime = AppUtils.timeToLong(weekEnd, Config.TIME_RECORD);
        if (System.currentTimeMillis() > endTime) {
            mFragmentWeekTopBinding.ivWeekRight.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_main));
        } else {
            mFragmentWeekTopBinding.ivWeekRight.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right));
        }
    }

    private ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            long weekStartTime = mWeekRecordPagerAdapter.getStartTime(position);
            weekEndTime = mWeekRecordPagerAdapter.getEndTime(position);
            mRecordDataChange.changeData(mWeekRecordPagerAdapter.getData(position), RecordDataChange.RECORD_TYPE_WEEK);
            if (mWeekRecordPagerAdapter.getData(position).getMotionList() == null || mWeekRecordPagerAdapter.getData(position).getMotionList().size() == 0) {
                mFragmentWeekTopBinding.tvWeekUnit.setVisibility(View.GONE);
            } else {
                mFragmentWeekTopBinding.tvWeekUnit.setVisibility(View.VISIBLE);
            }
            initTitle(AppUtils.timeToString(weekStartTime, Config.TIME_RECORD), AppUtils.timeToString(weekEndTime, Config.TIME_RECORD));
            if (position == 0) {
                isLeft = true;
                parentFragment.getRecordDate(RecordDataChange.RECORD_TYPE_WEEK, mWeekRecordPagerAdapter.getStartTime(position) - weekDiff, mWeekRecordPagerAdapter.getEndTime(position) - weekDiff);
            } else if (position == mWeekRecordPagerAdapter.getItemCount() - 1) {
                if (System.currentTimeMillis() > mWeekRecordPagerAdapter.getEndTime(position)) {
                    isLeft = false;
                    parentFragment.getRecordDate(RecordDataChange.RECORD_TYPE_WEEK, mWeekRecordPagerAdapter.getStartTime(position) + weekDiff, mWeekRecordPagerAdapter.getEndTime(position) + weekDiff);
                }
            }
        }
    };

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void updateData(MotionSumResponse motionSumResponse, long startTime, long endTime) {
        if (isStart) {
            ++currentNum;
            mWeekRecordPagerAdapter.setData(motionSumResponse, startTime, endTime);
            if (System.currentTimeMillis() <= endTime || currentNum >= RecordHomeFragment.MAX_NUM) {
                isStart = false;
                mActivity.hideLoadingDialog();
                mWeekRecordPagerAdapter.notifyDataSetChanged();
                mFragmentWeekTopBinding.vpWeek.setCurrentItem(1, false);
                mRecordDataChange.changeData(mWeekRecordPagerAdapter.getData(1), RecordDataChange.RECORD_TYPE_WEEK);
            } else {
                parentFragment.getFirstRecordDate(RecordDataChange.RECORD_TYPE_WEEK, startTime + weekDiff, endTime + weekDiff);
            }
        } else {
            mWeekRecordPagerAdapter.notifyDataSetChanged();
            if (isLeft) {
                mWeekRecordPagerAdapter.setData(0, motionSumResponse, startTime, endTime);
                mFragmentWeekTopBinding.vpWeek.setCurrentItem(mFragmentWeekTopBinding.vpWeek.getCurrentItem() + 1, false);
            } else {
                mWeekRecordPagerAdapter.setData(motionSumResponse, startTime, endTime);
                mFragmentWeekTopBinding.vpWeek.setCurrentItem(mFragmentWeekTopBinding.vpWeek.getCurrentItem(), false);
            }
        }
    }

    public void deleteRecord() {
        isStart = true;
        currentNum = 0;
        if (mWeekRecordPagerAdapter != null) {
            mWeekRecordPagerAdapter.clearData();
            getData(weekEndTime);
        }
    }
}
