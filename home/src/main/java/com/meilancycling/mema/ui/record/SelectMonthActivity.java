package com.meilancycling.mema.ui.record;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.network.bean.request.MotionRequest;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.ActivitySelectMonthBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.ui.adapter.SelectMonthPagerAdapter;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.StatusAppUtils;

import java.util.Calendar;

/**
 * @author lion
 * 月份选择
 */
public class SelectMonthActivity extends BaseActivity implements View.OnClickListener {
    ActivitySelectMonthBinding mActivitySelectMonthBinding;
    private Calendar mCalendar;
    private SelectMonthPagerAdapter mSelectMonthPagerAdapter;
    private int selectPosition;
    private String startTime;
    private String endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusAppUtils.setColor(this, getResources().getColor(R.color.white));
        mActivitySelectMonthBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_month);
        mActivitySelectMonthBinding.ctvSelectMonth.setData(getString(R.string.record), this);
        mActivitySelectMonthBinding.tvSelectMonthRight.setOnClickListener(this);
        mActivitySelectMonthBinding.tvSelectMonthLeft.setOnClickListener(this);
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");
        getData();
        mSelectMonthPagerAdapter = new SelectMonthPagerAdapter(this);
        mActivitySelectMonthBinding.vpSelectMonth.setAdapter(mSelectMonthPagerAdapter);
        mActivitySelectMonthBinding.vpSelectMonth.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                startTime = AppUtils.timeToString(mSelectMonthPagerAdapter.getStartTime(position), Config.DEFAULT_PATTERN);
                endTime = AppUtils.timeToString(mSelectMonthPagerAdapter.getEndTime(position), Config.DEFAULT_PATTERN);
                initTitle(mSelectMonthPagerAdapter.getStartTime(position), mSelectMonthPagerAdapter.getEndTime(position));
                if (position == mSelectMonthPagerAdapter.getItemCount() - 1) {
                    if (!isEndTime(mSelectMonthPagerAdapter.getEndTime(position))) {
                        long startTime = mSelectMonthPagerAdapter.getStartTime(position);
                        mCalendar.setTimeInMillis(startTime);
                        mCalendar.add(Calendar.MONTH, 1);
                        startTime = mCalendar.getTimeInMillis();
                        mCalendar.add(Calendar.MONTH, 1);
                        long entTime = mCalendar.getTimeInMillis() - RecordHomeFragment.ONE_DAY;
                        getMonthDate(startTime, entTime, true, 2);
                    }
                } else if (position == 0) {
                    long startTime = mSelectMonthPagerAdapter.getStartTime(position);
                    long entTime = mSelectMonthPagerAdapter.getEndTime(position);
                    mCalendar.setTimeInMillis(entTime);
                    mCalendar.add(Calendar.MONTH, -1);
                    entTime = startTime - RecordHomeFragment.ONE_DAY;
                    mCalendar.setTimeInMillis(startTime);
                    mCalendar.add(Calendar.MONTH, -1);
                    startTime = mCalendar.getTimeInMillis();
                    showLoadingDialog();
                    getMonthDate(startTime, entTime, false, 2);
                }
            }
        });
        registerReceiver(mReceiver, new IntentFilter(BroadcastConstant.ACTION_DELETE_RECORD));
    }

    private void getData() {
        mCalendar = Calendar.getInstance();
        showLoadingDialog();
        long timeStart;
        long timeEnd;
        if (isEndTime(AppUtils.timeToLong(endTime, Config.DEFAULT_PATTERN))) {
            selectPosition = 2;
            mCalendar.setTimeInMillis(AppUtils.timeToLong(startTime, Config.DEFAULT_PATTERN));
            mCalendar.add(Calendar.MONTH, -2);
            timeStart = mCalendar.getTimeInMillis();
            mCalendar.setTimeInMillis(AppUtils.timeToLong(startTime, Config.DEFAULT_PATTERN));
            mCalendar.add(Calendar.MONTH, -1);
        } else {
            selectPosition = 1;
            mCalendar.setTimeInMillis(AppUtils.timeToLong(startTime, Config.DEFAULT_PATTERN));
            mCalendar.add(Calendar.MONTH, -1);
            timeStart = mCalendar.getTimeInMillis();
            mCalendar.setTimeInMillis(AppUtils.timeToLong(startTime, Config.DEFAULT_PATTERN));
        }
        timeEnd = mCalendar.getTimeInMillis() - RecordHomeFragment.ONE_DAY;
        initTitle(AppUtils.timeToLong(startTime, Config.DEFAULT_PATTERN), AppUtils.timeToLong(endTime, Config.DEFAULT_PATTERN));

        getMonthDate(timeStart, timeEnd, true, 0);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mSelectMonthPagerAdapter.clearData();
            getData();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    /**
     * 头部设置
     */
    private void initTitle(long startTime, long endTime) {
        if (isEndTime(endTime)) {
            mActivitySelectMonthBinding.tvSelectMonthRight.setText("");
        } else {
            mActivitySelectMonthBinding.tvSelectMonthRight.setText(AppUtils.timeToString(endTime + RecordHomeFragment.ONE_DAY, Config.TIME_RECORD_YEAR));
        }
        mActivitySelectMonthBinding.tvSelectMonthCenter.setText(AppUtils.timeToString(endTime, Config.TIME_RECORD_YEAR));
        mCalendar.setTimeInMillis(startTime);
        mCalendar.add(Calendar.MONTH, -1);
        mActivitySelectMonthBinding.tvSelectMonthLeft.setText(AppUtils.timeToString(mCalendar.getTimeInMillis(), Config.TIME_RECORD_YEAR));

        mCalendar.add(Calendar.MONTH, -1);
    }


    /**
     * 跳转当前界面
     */
    public static void enterSelectMonth(Context context, String startTime, String endTime) {
        Intent mIntent = new Intent(context, SelectMonthActivity.class);
        mIntent.putExtra("startTime", startTime);
        mIntent.putExtra("endTime", endTime);
        context.startActivity(mIntent);
    }

    private boolean isEndTime(long end) {
        return AppUtils.timeToString(end, Config.TIME_RECORD_YEAR).equals(AppUtils.timeToString(System.currentTimeMillis(), Config.TIME_RECORD_YEAR));
    }

    /**
     * 获取数据
     *
     * @param start   开始时间
     * @param end     结束时间
     * @param isRight 添加在右边
     * @param number  是否第一次请求数据
     */
    public void getMonthDate(long start, long end, boolean isRight, int number) {
        MotionRequest motionRequest = new MotionRequest();
        motionRequest.setMotionType(0);
        motionRequest.setSession(getUserInfoEntity().getSession());
        motionRequest.setTimeType(6);
        motionRequest.setDataType(3);
        motionRequest.setEndDate(AppUtils.timeToString(end, Config.DEFAULT_PATTERN));
        motionRequest.setStartDate(AppUtils.timeToString(start, Config.DEFAULT_PATTERN));

        RetrofitUtils.getApiUrl()
                .queryMotionSumData(motionRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<MotionSumResponse>() {
                    @Override
                    public void onSuccess(MotionSumResponse motionSumResponse) {
                        checkData(start, end, isRight, number, motionSumResponse);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        checkData(start, end, isRight, number, null);
                    }
                });
    }

    private void checkData(long start, long end, boolean isRight, int number, MotionSumResponse motionSumResponse) {
        if (isRight) {
            mSelectMonthPagerAdapter.setData(motionSumResponse, start, end);
        } else {
            mSelectMonthPagerAdapter.setData(0, motionSumResponse, start, end);
        }
        if (number != 2) {
            mCalendar.setTimeInMillis(start);
            mCalendar.add(Calendar.MONTH, 1);
            long startTime = mCalendar.getTimeInMillis();
            mCalendar.setTimeInMillis(start);
            mCalendar.add(Calendar.MONTH, 2);
            long endTime = mCalendar.getTimeInMillis() - RecordHomeFragment.ONE_DAY;
            getMonthDate(startTime, endTime, true, number + 1);
        } else {
            hideLoadingDialog();
            if (selectPosition != 0) {
                mActivitySelectMonthBinding.vpSelectMonth.setCurrentItem(selectPosition, false);
                selectPosition = 0;
            }
            if (!isRight) {
                mActivitySelectMonthBinding.vpSelectMonth.setCurrentItem(1, false);
            }
            mSelectMonthPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_common_back) {
            finish();
        } else if (id == R.id.tv_select_month_right) {
            if (!TextUtils.isEmpty(mActivitySelectMonthBinding.tvSelectMonthRight.getText().toString())) {
                mActivitySelectMonthBinding.vpSelectMonth.setCurrentItem(mActivitySelectMonthBinding.vpSelectMonth.getCurrentItem() + 1);
            }

        } else if (id == R.id.tv_select_month_left) {
            mActivitySelectMonthBinding.vpSelectMonth.setCurrentItem(mActivitySelectMonthBinding.vpSelectMonth.getCurrentItem() - 1);
        }
    }
}