package com.meilancycling.mema.ui.record;

import android.annotation.SuppressLint;
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
import com.meilancycling.mema.databinding.ActivitySelectDayBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.ui.adapter.SelectDayPagerAdapter;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.StatusAppUtils;

/**
 * @author lion
 * 月份选择
 */
public class SelectDayActivity extends BaseActivity implements View.OnClickListener {
    ActivitySelectDayBinding mActivitySelectDayBinding;
    private SelectDayPagerAdapter mSelectDayPagerAdapter;
    private int selectPosition;
    private String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusAppUtils.setColor(this, getResources().getColor(R.color.white));
        mActivitySelectDayBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_day);
        mActivitySelectDayBinding.ctvSelectDay.setData(getString(R.string.record), this);
        mActivitySelectDayBinding.tvSelectDayRight.setOnClickListener(this);
        mActivitySelectDayBinding.tvSelectDayLeft.setOnClickListener(this);
        String time = getIntent().getStringExtra("time");
        getData(time);

        mSelectDayPagerAdapter = new SelectDayPagerAdapter(this);
        mActivitySelectDayBinding.vpSelectDay.setAdapter(mSelectDayPagerAdapter);
        mActivitySelectDayBinding.vpSelectDay.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentTime = AppUtils.timeToString(mSelectDayPagerAdapter.getTime(position), Config.DEFAULT_PATTERN);
                initTitle(mSelectDayPagerAdapter.getTime(position));
                if (position == mSelectDayPagerAdapter.getItemCount() - 1) {
                    if (!isEndTime(mSelectDayPagerAdapter.getTime(position))) {
                        showLoadingDialog();
                        getDayDate(mSelectDayPagerAdapter.getTime(position) + RecordHomeFragment.ONE_DAY, mSelectDayPagerAdapter.getTime(position) + RecordHomeFragment.ONE_DAY, true, 2);
                    }
                } else if (position == 0) {
                    showLoadingDialog();
                    getDayDate(mSelectDayPagerAdapter.getTime(position) - RecordHomeFragment.ONE_DAY, mSelectDayPagerAdapter.getTime(position) - RecordHomeFragment.ONE_DAY, false, 2);
                }
            }
        });
        registerReceiver(mReceiver, new IntentFilter(BroadcastConstant.ACTION_DELETE_RECORD));
    }

    private void getData(String time) {
        showLoadingDialog();
        long timeData;
        if (isEndTime(AppUtils.timeToLong(time, Config.DEFAULT_PATTERN))) {
            selectPosition = 2;
            timeData = AppUtils.timeToLong(time, Config.DEFAULT_PATTERN) - 2 * RecordHomeFragment.ONE_DAY;
        } else {
            selectPosition = 1;
            timeData = AppUtils.timeToLong(time, Config.DEFAULT_PATTERN) - RecordHomeFragment.ONE_DAY;
        }
        getDayDate(timeData, timeData, true, 0);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mSelectDayPagerAdapter.clearData();
            getData(currentTime);
        }
    };

    /**
     * 头部设置
     */
    @SuppressLint("SetTextI18n")
    private void initTitle(long time) {
        if (isEndTime(time)) {
            mActivitySelectDayBinding.tvSelectDayRight.setText("");
        } else {
            mActivitySelectDayBinding.tvSelectDayRight.setText(AppUtils.timeToString(time + RecordHomeFragment.ONE_DAY, Config.DEFAULT_PATTERN).split("-")[2] + getString(R.string.day));
        }
        mActivitySelectDayBinding.tvSelectDayCenter.setText(AppUtils.timeToString(time, Config.DEFAULT_PATTERN).split("-")[2] + getString(R.string.day));
        mActivitySelectDayBinding.tvSelectDayLeft.setText(AppUtils.timeToString(time - RecordHomeFragment.ONE_DAY, Config.DEFAULT_PATTERN).split("-")[2] + getString(R.string.day));
        String[] t = AppUtils.timeToString(time, Config.DEFAULT_PATTERN).split("-");
        String title = "";
        switch (Integer.parseInt(t[1])) {
            case 1:
                title = getString(R.string.January);
                break;
            case 2:
                title = getString(R.string.February);
                break;
            case 3:
                title = getString(R.string.March);
                break;
            case 4:
                title = getString(R.string.April);
                break;
            case 5:
                title = getString(R.string.May);
                break;
            case 6:
                title = getString(R.string.June);
                break;
            case 7:
                title = getString(R.string.July);
                break;
            case 8:
                title = getString(R.string.August);
                break;
            case 9:
                title = getString(R.string.September);
                break;
            case 10:
                title = getString(R.string.October);
                break;
            case 11:
                title = getString(R.string.November);
                break;
            case 12:
                title = getString(R.string.December);
                break;
            default:
        }
        mActivitySelectDayBinding.ctvSelectDay.changeTitle(title + " " + t[0]);
    }

    /**
     * 跳转当前界面
     */
    public static void enterSelectDay(Context context, String time) {
        Intent mIntent = new Intent(context, SelectDayActivity.class);
        mIntent.putExtra("time", time);
        context.startActivity(mIntent);
    }

    private boolean isEndTime(long end) {
        return AppUtils.timeToString(end, Config.DEFAULT_PATTERN).equals(AppUtils.timeToString(System.currentTimeMillis(), Config.DEFAULT_PATTERN));
    }

    /**
     * 获取数据
     *
     * @param start   开始时间
     * @param end     结束时间
     * @param isRight 添加在右边
     * @param number  是否第一次请求数据
     */
    public void getDayDate(long start, long end, boolean isRight, int number) {
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
            mSelectDayPagerAdapter.setData(motionSumResponse, start, end);
        } else {
            mSelectDayPagerAdapter.setData(0, motionSumResponse, start, end);
        }
        if (number != 2) {
            getDayDate(start + RecordHomeFragment.ONE_DAY, end + RecordHomeFragment.ONE_DAY, true, number + 1);
        } else {
            hideLoadingDialog();
            if (selectPosition != 0) {
                mActivitySelectDayBinding.vpSelectDay.setCurrentItem(selectPosition, false);
                selectPosition = 0;
            }
            if (!isRight) {
                mActivitySelectDayBinding.vpSelectDay.setCurrentItem(1, false);
            }
            mSelectDayPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_common_back) {
            finish();
        } else if (id == R.id.tv_select_day_right) {
            if (!TextUtils.isEmpty(mActivitySelectDayBinding.tvSelectDayRight.getText().toString())) {
                mActivitySelectDayBinding.vpSelectDay.setCurrentItem(mActivitySelectDayBinding.vpSelectDay.getCurrentItem() + 1);
            }
        } else if (id == R.id.tv_select_day_left) {
            mActivitySelectDayBinding.vpSelectDay.setCurrentItem(mActivitySelectDayBinding.vpSelectDay.getCurrentItem() - 1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}