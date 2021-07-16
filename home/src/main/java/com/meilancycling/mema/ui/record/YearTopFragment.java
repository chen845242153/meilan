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
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.databinding.FragmentYearTopBinding;
import com.meilancycling.mema.inf.RecordDataChange;
import com.meilancycling.mema.ui.adapter.YearRecordPagerAdapter;
import com.meilancycling.mema.utils.AppUtils;

import java.util.Calendar;

/**
 * @Description:
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class YearTopFragment extends BaseFragment {
    FragmentYearTopBinding mFragmentYearTopBinding;
    private YearRecordPagerAdapter mYearRecordPagerAdapter;
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
    private int currentNum;
    private long yearEndTime;
    private Calendar mCalendar;

    public YearTopFragment(RecordDataChange recordDataChange) {
        mRecordDataChange = recordDataChange;
    }

    public YearTopFragment() {
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentYearTopBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_year_top, container, false);
        mYearRecordPagerAdapter = new YearRecordPagerAdapter(getContext());
        mFragmentYearTopBinding.vpYear.setAdapter(mYearRecordPagerAdapter);
        mFragmentYearTopBinding.vpYear.registerOnPageChangeCallback(mOnPageChangeCallback);
        parentFragment = (RecordHomeFragment) getParentFragment();
        mCalendar = Calendar.getInstance();
        getData(System.currentTimeMillis());
        mFragmentYearTopBinding.viewYearLeft.setOnClickListener(v -> mFragmentYearTopBinding.vpYear.setCurrentItem(mFragmentYearTopBinding.vpYear.getCurrentItem() - 1));
        mFragmentYearTopBinding.viewYearRight.setOnClickListener(v -> mFragmentYearTopBinding.vpYear.setCurrentItem(mFragmentYearTopBinding.vpYear.getCurrentItem() + 1));
        return mFragmentYearTopBinding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void getData(long selectTime) {
        isStart = true;
        mCalendar.setTimeInMillis(selectTime);
        mCalendar.set(Calendar.YEAR, mCalendar.get(Calendar.YEAR) - 1);
        mCalendar.set(Calendar.MONTH, 0);
        mCalendar.set(Calendar.DATE, 1);
        long startTime = mCalendar.getTimeInMillis();
        mCalendar.set(Calendar.MONTH, 11);
        mCalendar.set(Calendar.DATE, 31);
        long endTime = mCalendar.getTimeInMillis();
        if (parentFragment != null) {
            mActivity.showLoadingDialog();
            parentFragment.getFirstRecordDate(RecordDataChange.RECORD_TYPE_YEAR, startTime, endTime);
        }
        if (Config.unit == Unit.METRIC.value) {
            mFragmentYearTopBinding.tvYearUnit.setText("(" + getString(R.string.unit_km) + ")");
        } else {
            mFragmentYearTopBinding.tvYearUnit.setText("(" + getString(R.string.unit_mile) + ")");
        }
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private void initTitle(long yearStart, long yearEnd) {
        mFragmentYearTopBinding.tvYearTime.setText(AppUtils.timeToString(yearStart, Config.TIME_RECORD_YEAR) + "-" + AppUtils.timeToString(yearEnd, Config.TIME_RECORD_YEAR));
        if (AppUtils.timeToString(yearEnd, Config.TIME_YEAR).equals(AppUtils.timeToString(System.currentTimeMillis(), Config.TIME_YEAR))) {
            mFragmentYearTopBinding.ivYearRight.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right));
        } else {
            mFragmentYearTopBinding.ivYearRight.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_main));
        }
    }

    private ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            long yearStartTime = mYearRecordPagerAdapter.getStartTime(position);
            yearEndTime = mYearRecordPagerAdapter.getEndTime(position);
            mRecordDataChange.changeData(mYearRecordPagerAdapter.getData(position), RecordDataChange.RECORD_TYPE_YEAR);
            if (mYearRecordPagerAdapter.getData(position).getYearListData() == null || mYearRecordPagerAdapter.getData(position).getYearListData().size() == 0) {
                mFragmentYearTopBinding.tvYearUnit.setVisibility(View.GONE);
            } else {
                mFragmentYearTopBinding.tvYearUnit.setVisibility(View.VISIBLE);
            }
            initTitle(yearStartTime, yearEndTime);
            if (position == 0) {
                isLeft = true;
                mCalendar.setTimeInMillis(yearStartTime);
                mCalendar.add(Calendar.YEAR, -1);
                yearStartTime = mCalendar.getTimeInMillis();
                mCalendar.setTimeInMillis(yearEndTime);
                mCalendar.add(Calendar.YEAR, -1);
                parentFragment.getRecordDate(RecordDataChange.RECORD_TYPE_YEAR, yearStartTime, mCalendar.getTimeInMillis());
            } else if (position == mYearRecordPagerAdapter.getItemCount() - 1) {
                if (!AppUtils.timeToString(yearEndTime, Config.TIME_YEAR).equals(AppUtils.timeToString(System.currentTimeMillis(), Config.TIME_YEAR))) {
                    isLeft = false;
                    mCalendar.setTimeInMillis(yearStartTime);
                    mCalendar.add(Calendar.YEAR, 1);
                    yearStartTime = mCalendar.getTimeInMillis();
                    mCalendar.setTimeInMillis(yearEndTime);
                    mCalendar.add(Calendar.YEAR, 1);
                    parentFragment.getRecordDate(RecordDataChange.RECORD_TYPE_YEAR, yearStartTime, mCalendar.getTimeInMillis());
                }
            }
        }
    };

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void updateData(MotionSumResponse motionSumResponse, long startTime, long endTime) {
        if (isStart) {
            ++currentNum;
            mYearRecordPagerAdapter.setData(motionSumResponse, startTime, endTime);
            if (AppUtils.timeToString(endTime, Config.TIME_YEAR).equals(AppUtils.timeToString(System.currentTimeMillis(), Config.TIME_YEAR)) || currentNum >= RecordHomeFragment.MAX_NUM) {
                isStart = false;
                mActivity.hideLoadingDialog();
                mYearRecordPagerAdapter.notifyDataSetChanged();
                mFragmentYearTopBinding.vpYear.setCurrentItem(1, false);
                mRecordDataChange.changeData(mYearRecordPagerAdapter.getData(1), RecordDataChange.RECORD_TYPE_YEAR);
            } else {
                mCalendar.setTimeInMillis(startTime);
                mCalendar.add(Calendar.YEAR, 1);
                startTime = mCalendar.getTimeInMillis();
                mCalendar.setTimeInMillis(endTime);
                mCalendar.add(Calendar.YEAR, 1);
                endTime = mCalendar.getTimeInMillis();
                parentFragment.getFirstRecordDate(RecordDataChange.RECORD_TYPE_YEAR, startTime, endTime);
            }
        } else {
            mYearRecordPagerAdapter.notifyDataSetChanged();
            if (isLeft) {
                mYearRecordPagerAdapter.setData(0, motionSumResponse, startTime, endTime);
                mFragmentYearTopBinding.vpYear.setCurrentItem(mFragmentYearTopBinding.vpYear.getCurrentItem() + 1, false);
            } else {
                mYearRecordPagerAdapter.setData(motionSumResponse, startTime, endTime);
                mFragmentYearTopBinding.vpYear.setCurrentItem(mFragmentYearTopBinding.vpYear.getCurrentItem(), false);
            }
        }
    }

    public void deleteRecord() {
        isStart = true;
        currentNum = 0;
        if (mYearRecordPagerAdapter != null) {
            mYearRecordPagerAdapter.clearData();
            getData(yearEndTime);
        }
    }
}
