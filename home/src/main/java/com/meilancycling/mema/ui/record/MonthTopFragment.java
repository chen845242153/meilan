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
import com.meilancycling.mema.databinding.FragmentMonthTopBinding;
import com.meilancycling.mema.inf.RecordDataChange;
import com.meilancycling.mema.ui.adapter.MonthRecordPagerAdapter;
import com.meilancycling.mema.utils.AppUtils;

import java.util.Calendar;


/**
 * @Description: 锻炼
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class MonthTopFragment extends BaseFragment {
    FragmentMonthTopBinding mFragmentMonthTopBinding;
    RecordDataChange mRecordDataChange;
    private MonthRecordPagerAdapter mMonthRecordPagerAdapter;
    RecordHomeFragment parentFragment;
    /**
     * 是否重新加载
     */
    private boolean isStart;
    /**
     * 是否加载左边
     */
    private boolean isLeft;
    private int currentNum;
    private long monthEndTime;

    private Calendar mCalendar;

    public MonthTopFragment(RecordDataChange recordDataChange) {
        mRecordDataChange = recordDataChange;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentMonthTopBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_month_top, container, false);
        mMonthRecordPagerAdapter = new MonthRecordPagerAdapter(getContext());
        mFragmentMonthTopBinding.vpMonth.setAdapter(mMonthRecordPagerAdapter);
        mFragmentMonthTopBinding.vpMonth.registerOnPageChangeCallback(mOnPageChangeCallback);
        parentFragment = (RecordHomeFragment) getParentFragment();
        mCalendar = Calendar.getInstance();
        getData(System.currentTimeMillis());
        mFragmentMonthTopBinding.viewMonthLeft.setOnClickListener(v -> mFragmentMonthTopBinding.vpMonth.setCurrentItem(mFragmentMonthTopBinding.vpMonth.getCurrentItem() - 1));
        mFragmentMonthTopBinding.viewMonthRight.setOnClickListener(v -> mFragmentMonthTopBinding.vpMonth.setCurrentItem(mFragmentMonthTopBinding.vpMonth.getCurrentItem() + 1));
        return mFragmentMonthTopBinding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void getData(long selectTime) {
        isStart = true;
        if (parentFragment != null) {
            mActivity.showLoadingDialog();
            mCalendar.setTimeInMillis(selectTime);
            mCalendar.add(Calendar.MONTH, -1);
            mCalendar.set(Calendar.DAY_OF_MONTH, 1);
            long start = mCalendar.getTimeInMillis();
            mCalendar.set(Calendar.DAY_OF_MONTH, 0);
            mCalendar.add(Calendar.MONTH, 1);
            long end = mCalendar.getTimeInMillis();
            parentFragment.getFirstRecordDate(RecordDataChange.RECORD_TYPE_MONTH, start, end);
        }
        if (Config.unit == Unit.METRIC.value) {
            mFragmentMonthTopBinding.tvMonthUnit.setText("(" + getString(R.string.unit_km) + ")");
        } else {
            mFragmentMonthTopBinding.tvMonthUnit.setText("(" + getString(R.string.unit_mile) + ")");
        }
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private void initTitle(long monthStart, long monthEnd) {
        mFragmentMonthTopBinding.tvMonthTime.setText(AppUtils.timeToString(monthStart, Config.TIME_RECORD) + "-" + AppUtils.timeToString(monthEnd, Config.TIME_RECORD));
        if (AppUtils.timeToString(monthEnd, Config.TIME_RECORD_YEAR).equals(AppUtils.timeToString(System.currentTimeMillis(), Config.TIME_RECORD_YEAR))) {
            mFragmentMonthTopBinding.ivMonthRight.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right));
        } else {
            mFragmentMonthTopBinding.ivMonthRight.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_main));
        }
    }

    private ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            long monthStartTime = mMonthRecordPagerAdapter.getStartTime(position);
            monthEndTime = mMonthRecordPagerAdapter.getEndTime(position);
            initTitle(mMonthRecordPagerAdapter.getStartTime(position), mMonthRecordPagerAdapter.getEndTime(position));
            mRecordDataChange.changeData(mMonthRecordPagerAdapter.getData(position), RecordDataChange.RECORD_TYPE_MONTH);
            if (mMonthRecordPagerAdapter.getData(position).getMotionList() == null || mMonthRecordPagerAdapter.getData(position).getMotionList().size() == 0) {
                mFragmentMonthTopBinding.tvMonthUnit.setVisibility(View.GONE);
            } else {
                mFragmentMonthTopBinding.tvMonthUnit.setVisibility(View.VISIBLE);
            }
            if (position == 0) {
                isLeft = true;
                parentFragment.getRecordDate(RecordDataChange.RECORD_TYPE_MONTH, lastMonthStartTime(monthStartTime), lastMonthEndTime(monthEndTime));
            } else if (position == mMonthRecordPagerAdapter.getItemCount() - 1) {
                if (!AppUtils.timeToString(monthEndTime, Config.TIME_RECORD_YEAR).equals(AppUtils.timeToString(System.currentTimeMillis(), Config.TIME_RECORD_YEAR))) {
                    isLeft = false;
                    parentFragment.getRecordDate(RecordDataChange.RECORD_TYPE_MONTH, nextMonthStartTime(monthStartTime), nextMonthEndTime(monthEndTime));
                }
            }
        }
    };

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void updateData(MotionSumResponse motionSumResponse, long startTime, long endTime) {
        mCalendar.setTimeInMillis(startTime);
        if (isStart) {
            ++currentNum;
            mMonthRecordPagerAdapter.setData(motionSumResponse, startTime, endTime);
            if (AppUtils.timeToString(endTime, Config.TIME_RECORD_YEAR).equals(AppUtils.timeToString(System.currentTimeMillis(), Config.TIME_RECORD_YEAR)) || currentNum >= RecordHomeFragment.MAX_NUM) {
                isStart = false;
                mActivity.hideLoadingDialog();
                mMonthRecordPagerAdapter.notifyDataSetChanged();
                mFragmentMonthTopBinding.vpMonth.setCurrentItem(1, false);
                mRecordDataChange.changeData(mMonthRecordPagerAdapter.getData(1), RecordDataChange.RECORD_TYPE_MONTH);
            } else {
                parentFragment.getFirstRecordDate(RecordDataChange.RECORD_TYPE_MONTH, nextMonthStartTime(startTime), nextMonthEndTime(endTime));
            }
        } else {
            mMonthRecordPagerAdapter.notifyDataSetChanged();
            if (isLeft) {
                mMonthRecordPagerAdapter.setData(0, motionSumResponse, startTime, endTime);
                mFragmentMonthTopBinding.vpMonth.setCurrentItem(mFragmentMonthTopBinding.vpMonth.getCurrentItem() + 1, false);
            } else {
                mMonthRecordPagerAdapter.setData(motionSumResponse, startTime, endTime);
                mFragmentMonthTopBinding.vpMonth.setCurrentItem(mFragmentMonthTopBinding.vpMonth.getCurrentItem(), false);
            }
        }
    }

    public void deleteRecord() {
        isStart = true;
        currentNum = 0;
        if (mMonthRecordPagerAdapter != null) {
            mMonthRecordPagerAdapter.clearData();
            getData(monthEndTime);
        }
    }

    /**
     * 获取下个月月初
     */
    private long nextMonthStartTime(long time) {
        mCalendar.setTimeInMillis(time);
        mCalendar.add(Calendar.MONTH, 1);
        mCalendar.set(Calendar.DATE, 1);
        return mCalendar.getTimeInMillis();
    }

    /**
     * 获取下个月月底
     */
    private long nextMonthEndTime(long time) {
        mCalendar.setTimeInMillis(time);
        mCalendar.add(Calendar.MONTH, 2);
        mCalendar.set(Calendar.DATE, 1);
        mCalendar.add(Calendar.DATE, -1);
        return mCalendar.getTimeInMillis();
    }

    /**
     * 获取上个月月初
     */
    private long lastMonthStartTime(long time) {
        mCalendar.setTimeInMillis(time);
        mCalendar.add(Calendar.MONTH, -1);
        mCalendar.set(Calendar.DATE, 1);
        return mCalendar.getTimeInMillis();
    }

    /**
     * 获取上个月月底
     */
    private long lastMonthEndTime(long time) {
        mCalendar.setTimeInMillis(time);
        mCalendar.set(Calendar.DATE, 1);
        mCalendar.add(Calendar.DATE, -1);
        return mCalendar.getTimeInMillis();
    }
}
