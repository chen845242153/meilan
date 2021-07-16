package com.meilancycling.mema.ui.record;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.request.SessionRequest;
import com.meilancycling.mema.network.bean.response.MostMotionResponse;
import com.meilancycling.mema.databinding.FragmentTotalTopBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.ui.details.DetailsHomeActivity;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

/**
 * @Description: 锻炼
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class TotalTopFragment extends BaseFragment {
    FragmentTotalTopBinding mFragmentTotalTopBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentTotalTopBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_total_top, container, false);
        getTopData();
        setDistance(0);
        setSumData(0, 0, 0);
        setTime(0);
        return mFragmentTotalTopBinding.getRoot();
    }

    public void deleteRecord() {
        getTopData();
    }

    private void getTopData() {
        SessionRequest sessionRequest = new SessionRequest();
        sessionRequest.setSession(mActivity.getUserInfoEntity().getSession());
        RetrofitUtils.getApiUrl().queryUserMotionSum(sessionRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<MostMotionResponse>() {
                    @Override
                    public void onSuccess(MostMotionResponse mostMotionResponse) {
                        setDistance(mostMotionResponse.getMaxDistance());
                        setTime(mostMotionResponse.getMaxTime());
                        setSpeed(mostMotionResponse.getMaxSpeed());
                        setRise(mostMotionResponse.getMaxAscent());
                        setSumData(mostMotionResponse.getSumDistance(), mostMotionResponse.getSumTime(), mostMotionResponse.getSumMotion());
                        mFragmentTotalTopBinding.llLongDistance.setOnClickListener(v -> DetailsHomeActivity.enterDetailsHome(getContext(), mostMotionResponse.getMotionDistanceId()));
                        mFragmentTotalTopBinding.llLongTime.setOnClickListener(v -> DetailsHomeActivity.enterDetailsHome(getContext(), mostMotionResponse.getMotionTimeId()));
                        mFragmentTotalTopBinding.llMaxSpeed.setOnClickListener(v -> DetailsHomeActivity.enterDetailsHome(getContext(), mostMotionResponse.getMotionMaxSpeedId()));
                        mFragmentTotalTopBinding.llMaxRise.setOnClickListener(v -> DetailsHomeActivity.enterDetailsHome(getContext(), mostMotionResponse.getMotionAscentId()));
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        ToastUtils.showError(mActivity, resultCode);
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void setDistance(double data) {
        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), (int) data);
        mFragmentTotalTopBinding.ctvLongDistance.setText(unitBean.getValue());
        mFragmentTotalTopBinding.tvLongDistanceUnit.setText(getString(R.string.farthest_distance) + "(" + unitBean.getUnit() + ")");
    }

    @SuppressLint("SetTextI18n")
    private void setTime(int data) {
        String timeData = UnitConversionUtil.getUnitConversionUtil().timeFormat(data);
        mFragmentTotalTopBinding.ctvLongTime.setText(timeData);
        mFragmentTotalTopBinding.tvTimeUnit.setText(getString(R.string.longest_time));
    }

    @SuppressLint("SetTextI18n")
    private void setSpeed(int data) {
        UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) data / 10);
        mFragmentTotalTopBinding.ctvMaxSpeed.setText(speedSetting.getValue());
        mFragmentTotalTopBinding.tvSpeedUnit.setText(getString(R.string.maximum_speed) + "(" + speedSetting.getUnit() + ")");
    }

    @SuppressLint("SetTextI18n")
    private void setRise(int data) {
        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(getContext(), data);
        mFragmentTotalTopBinding.ctvAscent.setText(unitBean.getValue());
        mFragmentTotalTopBinding.tvAscent.setText(getString(R.string.max_rise) + "(" + unitBean.getUnit() + ")");
    }

    @SuppressLint("SetTextI18n")
    private void setSumData(double distance, int time, int num) {
        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), (int) distance);
        String timeData = UnitConversionUtil.getUnitConversionUtil().timeFormat(time);
        mFragmentTotalTopBinding.tvDistanceValue.setText(unitBean.getValue());
        mFragmentTotalTopBinding.tvDistanceUnit.setText(getString(R.string.distance) + "(" + unitBean.getUnit() + ")");
        mFragmentTotalTopBinding.tvDuringValue.setText(timeData);
        mFragmentTotalTopBinding.tvDuringUnit.setText(getString(R.string.total_time));
        mFragmentTotalTopBinding.tvNumberValue.setText(String.valueOf(num));
        mFragmentTotalTopBinding.tvNumberUnit.setText(getString(R.string.times));
    }
}
