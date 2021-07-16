package com.meilancycling.mema.ui.record;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.network.bean.request.MotionRequest;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.FragmentHomeRecordBinding;
import com.meilancycling.mema.inf.RecordDataChange;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ToastUtils;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

/**
 * @Description:
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class RecordHomeFragment extends BaseFragment implements RecordDataChange {
    FragmentHomeRecordBinding mFragmentHomeRecordBinding;
    private TotalRecordFragment mTotalRecordFragment = new TotalRecordFragment();
    private WeekRecordFragment mWeekRecordFragment = new WeekRecordFragment();
    private MonthRecordFragment mMonthRecordFragment = new MonthRecordFragment();
    private YearRecordFragment mYearRecordFragment = new YearRecordFragment();

    private TotalTopFragment mTotalTopFragment = new TotalTopFragment();
    private WeekTopFragment mWeekTopFragment = new WeekTopFragment(this);
    private MonthTopFragment mMonthTopFragment = new MonthTopFragment(this);
    private YearTopFragment mYearTopFragment = new YearTopFragment(this);
    private int position;
    public static final long ONE_DAY = 24 * 60 * 60 * 1000;
    public static final int MAX_NUM = 3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentHomeRecordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_record, container, false);
        initMagicIndicator();
        fromFragment = mTotalRecordFragment;
        topFromFragment = mTotalTopFragment;
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_record_details, mTotalRecordFragment, mTotalRecordFragment.getClass().getSimpleName());
        transaction.commit();
        FragmentTransaction transactionTop = getChildFragmentManager().beginTransaction();
        transactionTop.replace(R.id.fl_record_top, mTotalTopFragment, mTotalTopFragment.getClass().getSimpleName());
        transactionTop.commit();
        position = 0;
        mActivity.registerReceiver(mReceiver, new IntentFilter(BroadcastConstant.ACTION_DELETE_RECORD));
        return mFragmentHomeRecordBinding.getRoot();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mTotalTopFragment.deleteRecord();
            mTotalRecordFragment.deleteRecord();
            mWeekTopFragment.deleteRecord();
            mMonthTopFragment.deleteRecord();
            mYearTopFragment.deleteRecord();
        }
    };

    /**
     * 更新页面
     */
    public void updateTotalUi() {
        mTotalTopFragment.deleteRecord();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(mReceiver);
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return getResources().getStringArray(R.array.record_list).length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(getResources().getStringArray(R.array.record_list)[index]);
                clipPagerTitleView.setTextColor(R.color.black_6);
                clipPagerTitleView.setClipColor(R.color.black_3);
                clipPagerTitleView.setTextSize(AppUtils.spToPx(context, 14));
                clipPagerTitleView.setOnClickListener(v -> {
                    mFragmentHomeRecordBinding.miRecord.onPageSelected(index);
                    mFragmentHomeRecordBinding.miRecord.onPageScrolled(index, 0.0F, 0);
                    position = index;
                    changeFragment();
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setLineHeight(AppUtils.dipToPx(context, 5));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setYOffset(0);
                indicator.setXOffset(AppUtils.dipToPx(context, -2.5f));
                indicator.setColors(Color.parseColor("#FF6FB92C"));
                return indicator;
            }
        });
        mFragmentHomeRecordBinding.miRecord.setNavigator(commonNavigator);
    }

    private Fragment fromFragment;
    private Fragment topFromFragment;

    private void changeFragment() {
        Fragment fragCategory = null;
        Fragment topFragCategory = null;
        switch (position) {
            case 0:
                fragCategory = mTotalRecordFragment;
                topFragCategory = mTotalTopFragment;
                break;
            case 1:
                fragCategory = mWeekRecordFragment;
                topFragCategory = mWeekTopFragment;
                break;
            case 2:
                fragCategory = mMonthRecordFragment;
                topFragCategory = mMonthTopFragment;
                break;
            case 3:
                fragCategory = mYearRecordFragment;
                topFragCategory = mYearTopFragment;
                break;
            default:
        }
        switchFragment(fromFragment, fragCategory);
        fromFragment = fragCategory;
        changeFragment(topFromFragment, topFragCategory);
        topFromFragment = topFragCategory;
    }

    private void switchFragment(Fragment from, Fragment to) {
        if (from != to && to != null) {
            FragmentManager manger = getChildFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!to.isAdded()) {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.add(R.id.fl_record_details, to, to.getClass().getName()).commit();
            } else {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.show(to).commit();
            }
        }
    }

    private void changeFragment(Fragment from, Fragment to) {
        if (from != to && to != null) {
            FragmentManager manger = getChildFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!to.isAdded()) {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.add(R.id.fl_record_top, to, to.getClass().getName()).commit();
            } else {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.show(to).commit();
            }
        }
    }

    /**
     * 获取数据
     */
    public void getRecordDate(int type, long startTime, long endTime) {
        mActivity.showLoadingDialog();
        MotionRequest motionRequest = new MotionRequest();
        motionRequest.setMotionType(0);
        motionRequest.setSession(mActivity.getUserInfoEntity().getSession());
        motionRequest.setTimeType(6);
        switch (type) {
            case RecordDataChange.RECORD_TYPE_WEEK:
            case RecordDataChange.RECORD_TYPE_MONTH:
                motionRequest.setDataType(3);
                motionRequest.setEndDate(AppUtils.timeToString(endTime, Config.DEFAULT_PATTERN));
                motionRequest.setStartDate(AppUtils.timeToString(startTime, Config.DEFAULT_PATTERN));
                break;
            case RecordDataChange.RECORD_TYPE_YEAR:
                motionRequest.setDataType(2);
                motionRequest.setEndDate(AppUtils.timeToString(endTime, Config.DEFAULT_PATTERN_YEAR));
                motionRequest.setStartDate(AppUtils.timeToString(startTime, Config.DEFAULT_PATTERN_YEAR));
                break;
            default:
        }
        RetrofitUtils.getApiUrl()
                .queryMotionSumData(motionRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<MotionSumResponse>() {
                    @Override
                    public void onSuccess(MotionSumResponse motionSumResponse) {
                        mActivity.hideLoadingDialog();
                        switch (type) {
                            case RecordDataChange.RECORD_TYPE_WEEK:
                                mWeekTopFragment.updateData(motionSumResponse, startTime, endTime);
                                break;
                            case RecordDataChange.RECORD_TYPE_MONTH:
                                mMonthTopFragment.updateData(motionSumResponse, startTime, endTime);
                                break;
                            case RecordDataChange.RECORD_TYPE_YEAR:
                                mYearTopFragment.updateData(motionSumResponse, startTime, endTime);
                                break;
                            default:
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        mActivity.hideLoadingDialog();
                        ToastUtils.showError(mActivity, resultCode);
                        switch (type) {
                            case RecordDataChange.RECORD_TYPE_WEEK:
                                mWeekTopFragment.updateData(null, startTime, endTime);
                                break;
                            case RecordDataChange.RECORD_TYPE_MONTH:
                                mMonthTopFragment.updateData(null, startTime, endTime);
                                break;
                            case RecordDataChange.RECORD_TYPE_YEAR:
                                mYearTopFragment.updateData(null, startTime, endTime);
                                break;
                            default:
                        }
                    }
                });
    }

    /**
     * 获取数据
     */
    public void getFirstRecordDate(int type, long startTime, long endTime) {
        MotionRequest motionRequest = new MotionRequest();
        motionRequest.setMotionType(0);
        motionRequest.setSession(mActivity.getUserInfoEntity().getSession());
        motionRequest.setTimeType(6);
        switch (type) {
            case RecordDataChange.RECORD_TYPE_WEEK:
            case RecordDataChange.RECORD_TYPE_MONTH:
                motionRequest.setDataType(3);
                motionRequest.setEndDate(AppUtils.timeToString(endTime, Config.DEFAULT_PATTERN));
                motionRequest.setStartDate(AppUtils.timeToString(startTime, Config.DEFAULT_PATTERN));
                break;
            case RecordDataChange.RECORD_TYPE_YEAR:
                motionRequest.setDataType(2);
                motionRequest.setEndDate(AppUtils.timeToString(endTime, Config.DEFAULT_PATTERN_YEAR));
                motionRequest.setStartDate(AppUtils.timeToString(startTime, Config.DEFAULT_PATTERN_YEAR));
                break;
            default:
        }
        RetrofitUtils.getApiUrl()
                .queryMotionSumData(motionRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<MotionSumResponse>() {
                    @Override
                    public void onSuccess(MotionSumResponse motionSumResponse) {
                        switch (type) {
                            case RecordDataChange.RECORD_TYPE_WEEK:
                                mWeekTopFragment.updateData(motionSumResponse, startTime, endTime);
                                break;
                            case RecordDataChange.RECORD_TYPE_MONTH:
                                mMonthTopFragment.updateData(motionSumResponse, startTime, endTime);
                                break;
                            case RecordDataChange.RECORD_TYPE_YEAR:
                                mYearTopFragment.updateData(motionSumResponse, startTime, endTime);
                                break;
                            default:
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        mActivity.hideLoadingDialog();
                        ToastUtils.showError(mActivity, resultCode);
                    }
                });
    }

    @Override
    public void changeData(MotionSumResponse motionSumResponse, int type) {
        switch (type) {
            case RecordDataChange.RECORD_TYPE_WEEK:
                mWeekRecordFragment.updateUi(motionSumResponse);
                break;
            case RecordDataChange.RECORD_TYPE_MONTH:
                mMonthRecordFragment.updateUi(motionSumResponse);
                break;
            case RecordDataChange.RECORD_TYPE_YEAR:
                mYearRecordFragment.updateUi(motionSumResponse);
                break;
            default:
        }
    }
}
