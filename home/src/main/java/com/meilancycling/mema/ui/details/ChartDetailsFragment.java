package com.meilancycling.mema.ui.details;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.bean.response.MotionDetailsResponse;
import com.meilancycling.mema.customview.ChartDetailsView;
import com.meilancycling.mema.databinding.FragmentChartDetailsBinding;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 2:17 PM
 */
public class ChartDetailsFragment extends BaseFragment {
    FragmentChartDetailsBinding mFragmentChartDetailsBinding;
    DetailsHomeActivity baseActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentChartDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chart_details, container, false);
        baseActivity = (DetailsHomeActivity) getActivity();
        updateUi();
        UserInfoEntity userInfoEntity =mActivity.getUserInfoEntity();
        if (((userInfoEntity.getGuideFlag() & 32) >> 5) == Config.NEED_GUIDE) {
            mFragmentChartDetailsBinding.groupGuide.setVisibility(View.VISIBLE);
            baseActivity.showGuide();
        }
        mFragmentChartDetailsBinding.guideBg.setOnClickListener(v -> {
            baseActivity.hideGuide();
            mFragmentChartDetailsBinding.groupGuide.setVisibility(View.GONE);
            int guideFlag = userInfoEntity.getGuideFlag();
            userInfoEntity.setGuideFlag(guideFlag & 0xDF);
            mActivity.updateUserInfoEntity(userInfoEntity);
        });
        mFragmentChartDetailsBinding.guideBgBottom.setOnClickListener(v -> {
            baseActivity.hideGuide();
            mFragmentChartDetailsBinding.groupGuide.setVisibility(View.GONE);
            int guideFlag = userInfoEntity.getGuideFlag();
            userInfoEntity.setGuideFlag(guideFlag & 0xDF);
            mActivity.updateUserInfoEntity(userInfoEntity);
        });
        mFragmentChartDetailsBinding.guideContent.setOnClickListener(v -> {
            baseActivity.hideGuide();
            mFragmentChartDetailsBinding.groupGuide.setVisibility(View.GONE);
            int guideFlag = userInfoEntity.getGuideFlag();
            userInfoEntity.setGuideFlag(guideFlag & 0xDF);
            mActivity.updateUserInfoEntity(userInfoEntity);
        });
        return mFragmentChartDetailsBinding.getRoot();
    }

    public static List<List<Long>> speedChart = new ArrayList<>();
    public static List<List<Long>> cadenceChart = new ArrayList<>();
    public static List<List<Long>> heartRateChart = new ArrayList<>();
    public static List<List<Long>> powerChart = new ArrayList<>();
    public static List<List<Long>> altitudeChart = new ArrayList<>();
    public static List<List<Long>> temperatureChart = new ArrayList<>();

    public static float limitSpeed;
    public static int limitCadence;
    public static int limitHeartRate;
    public static int limitPower;
    public static int limitAltitude;
    public static int limitTemperature;

    public static float maxSpeed;
    public static int maxCadence;
    public static int maxHeartRate;
    public static int maxPower;
    public static int maxAltitude;
    public static int maxTemperature;

    public static float minSpeed;
    public static float minCadence;
    public static float minHeartRate;
    public static float minPower;
    public static float minAltitude;
    public static float minTemperature;

    public static int minSpeedDiff;
    public static int minCadenceDiff;
    public static int minHeartRateDiff;
    public static int minPowerDiff;
    public static int minAltitudeDiff;
    public static int minTemperatureDiff;

    private void updateUi() {
        if (DetailsHomeActivity.mMotionDetailsResponse != null && DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo() != null && DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo().getMotionCyclingRecordPo() != null) {
            MotionDetailsResponse.MotionCyclingResponseVoBean motionCyclingResponseVo = DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo();
            //速度
            if (TextUtils.isEmpty(motionCyclingResponseVo.getMotionCyclingRecordPo().getSpeedVos())) {
                mFragmentChartDetailsBinding.ccvChartSpeed.setVisibility(View.GONE);
            } else {
                List<List<Long>> data = new Gson().fromJson(motionCyclingResponseVo.getMotionCyclingRecordPo().getSpeedVos(), new TypeToken<List<List<Long>>>() {
                }.getType());
                if (data.size() > 2 && motionCyclingResponseVo.getMaxSpeed() != 0) {
                    speedChart = data;
                    limitSpeed = AppUtils.stringToFloat(UnitConversionUtil.getUnitConversionUtil().speedSetting(mActivity, (double) motionCyclingResponseVo.getAvgSpeed() / 10).getValue());
                    maxSpeed = AppUtils.stringToFloat(UnitConversionUtil.getUnitConversionUtil().speedSetting(mActivity, (double) motionCyclingResponseVo.getMaxSpeed() / 10).getValue());
                    mFragmentChartDetailsBinding.ccvChartSpeed.setChartData(data, motionCyclingResponseVo.getMaxSpeed(), 0, motionCyclingResponseVo.getAvgSpeed(), ChartDetailsView.CHART_SPEED);
                } else {
                    mFragmentChartDetailsBinding.ccvChartSpeed.setVisibility(View.GONE);
                }
            }
            //踏频
            if (TextUtils.isEmpty(motionCyclingResponseVo.getMotionCyclingRecordPo().getCadenceVos())) {
                mFragmentChartDetailsBinding.ccvChartCadence.setVisibility(View.GONE);
            } else {
                List<List<Long>> data = new Gson().fromJson(motionCyclingResponseVo.getMotionCyclingRecordPo().getCadenceVos(), new TypeToken<List<List<Long>>>() {
                }.getType());
                if (data.size() > 2 && motionCyclingResponseVo.getMaxCadence() != 0) {
                    cadenceChart = data;
                    limitCadence = motionCyclingResponseVo.getAvgCadence();
                    maxCadence = motionCyclingResponseVo.getMaxCadence();
                    mFragmentChartDetailsBinding.ccvChartCadence.setChartData(data, motionCyclingResponseVo.getMaxCadence(), 0, motionCyclingResponseVo.getAvgCadence(), ChartDetailsView.CHART_CADENCE);
                } else {
                    mFragmentChartDetailsBinding.ccvChartCadence.setVisibility(View.GONE);
                }
            }
            //海拔
            if (TextUtils.isEmpty(motionCyclingResponseVo.getMotionCyclingRecordPo().getAltitudeVos())) {
                mFragmentChartDetailsBinding.ccvChartAltitude.setVisibility(View.GONE);
            } else {
                List<List<Long>> data = new Gson().fromJson(motionCyclingResponseVo.getMotionCyclingRecordPo().getAltitudeVos(), new TypeToken<List<List<Long>>>() {
                }.getType());
                if (data.size() > 2 && motionCyclingResponseVo.getMinAltitude() != null && motionCyclingResponseVo.getMaxAltitude() != null&& motionCyclingResponseVo.getAvgAltitude() != null) {
                    altitudeChart = data;
                    limitAltitude = Integer.parseInt(UnitConversionUtil.getUnitConversionUtil().altitudeSetting(mActivity, motionCyclingResponseVo.getMinAltitude()).getValue());
                    maxAltitude = Integer.parseInt(UnitConversionUtil.getUnitConversionUtil().altitudeSetting(mActivity, motionCyclingResponseVo.getMaxAltitude()).getValue());
                    mFragmentChartDetailsBinding.ccvChartAltitude.setChartData(data, motionCyclingResponseVo.getMaxAltitude(), motionCyclingResponseVo.getMinAltitude(), motionCyclingResponseVo.getAvgAltitude(), ChartDetailsView.CHART_ALTITUDE);
                } else {
                    mFragmentChartDetailsBinding.ccvChartAltitude.setVisibility(View.GONE);
                }
            }
            //心率
            if (TextUtils.isEmpty(motionCyclingResponseVo.getMotionCyclingRecordPo().getHrmVos())) {
                mFragmentChartDetailsBinding.ccvChartHeartRate.setVisibility(View.GONE);
            } else {
                List<List<Long>> data = new Gson().fromJson(motionCyclingResponseVo.getMotionCyclingRecordPo().getHrmVos(), new TypeToken<List<List<Long>>>() {
                }.getType());
                if (data.size() > 2 && motionCyclingResponseVo.getMaxHrm() != 0) {
                    heartRateChart = data;
                    limitHeartRate = motionCyclingResponseVo.getAvgHrm();
                    maxHeartRate = motionCyclingResponseVo.getMaxHrm();
                    mFragmentChartDetailsBinding.ccvChartHeartRate.setChartData(data, motionCyclingResponseVo.getMaxHrm(), motionCyclingResponseVo.getMinHrm(), motionCyclingResponseVo.getAvgHrm(), ChartDetailsView.CHART_HEART_RATE);
                } else {
                    mFragmentChartDetailsBinding.ccvChartHeartRate.setVisibility(View.GONE);
                }
            }
            //功率
            if (TextUtils.isEmpty(motionCyclingResponseVo.getMotionCyclingRecordPo().getPowerVos())) {
                mFragmentChartDetailsBinding.ccvChartPower.setVisibility(View.GONE);
            } else {
                List<List<Long>> data = new Gson().fromJson(motionCyclingResponseVo.getMotionCyclingRecordPo().getPowerVos(), new TypeToken<List<List<Long>>>() {
                }.getType());
                if (data.size() > 2 && motionCyclingResponseVo.getMaxPower() != 0) {
                    powerChart = data;
                    limitPower = motionCyclingResponseVo.getAvgPower();
                    maxPower = motionCyclingResponseVo.getMaxPower();
                    mFragmentChartDetailsBinding.ccvChartPower.setChartData(data, motionCyclingResponseVo.getMaxPower(), 0, motionCyclingResponseVo.getAvgPower(), ChartDetailsView.CHART_POWER);
                } else {
                    mFragmentChartDetailsBinding.ccvChartPower.setVisibility(View.GONE);
                }
            }
            //温度
            if (TextUtils.isEmpty(motionCyclingResponseVo.getMotionCyclingRecordPo().getTemperatureVos())) {
                mFragmentChartDetailsBinding.ccvChartTemperature.setVisibility(View.GONE);
            } else {
                List<List<Long>> data = new Gson().fromJson(motionCyclingResponseVo.getMotionCyclingRecordPo().getTemperatureVos(), new TypeToken<List<List<Long>>>() {
                }.getType());
                if (data.size() > 2) {
                    temperatureChart = data;
                    limitTemperature = Integer.parseInt(UnitConversionUtil.getUnitConversionUtil().temperatureSetting(mActivity, motionCyclingResponseVo.getAvgTemperature()).getValue());
                    maxTemperature = Integer.parseInt(UnitConversionUtil.getUnitConversionUtil().temperatureSetting(mActivity, motionCyclingResponseVo.getMaxTemperature()).getValue());
                    mFragmentChartDetailsBinding.ccvChartTemperature.setChartData(data, motionCyclingResponseVo.getMaxTemperature(), 0, motionCyclingResponseVo.getAvgTemperature(), ChartDetailsView.CHART_TEMPERATURE);
                } else {
                    mFragmentChartDetailsBinding.ccvChartTemperature.setVisibility(View.GONE);
                }
            }

            /*
             * 爬坡分析
             */
            String slope = motionCyclingResponseVo.getSlope();
            if (!TextUtils.isEmpty(slope)) {
                List<Long> slopeList = new Gson().fromJson(slope, new TypeToken<List<Long>>() {
                }.getType());
                long total = slopeList.get(0) + slopeList.get(1) + slopeList.get(2);
                if (total == 0) {
                    mFragmentChartDetailsBinding.svSlope.setVisibility(View.GONE);
                } else {
                    mFragmentChartDetailsBinding.svSlope.updateSlope(slopeList);
                }
            } else {
                mFragmentChartDetailsBinding.svSlope.setVisibility(View.GONE);
            }
            /*
             *心率
             */
            String hrmZone = motionCyclingResponseVo.getHrmZone();
            if (TextUtils.isEmpty(hrmZone)) {
                mFragmentChartDetailsBinding.hrZone.setVisibility(View.GONE);
            } else {
                List<Long> hrmList = new Gson().fromJson(hrmZone, new TypeToken<List<Long>>() {
                }.getType());
                long hrmTotal = hrmList.get(0) + hrmList.get(1) + hrmList.get(2) + hrmList.get(3) + hrmList.get(4);
                if (hrmTotal == 0) {
                    mFragmentChartDetailsBinding.hrZone.setVisibility(View.GONE);
                } else {
                    mFragmentChartDetailsBinding.hrZone.updateHeartChart(hrmList, motionCyclingResponseVo.getHrTypeZone());
                }
            }
            /*
             *功率
             */
            String powerZone = motionCyclingResponseVo.getPowerZone();
            if (TextUtils.isEmpty(powerZone)) {
                mFragmentChartDetailsBinding.powerZone.setVisibility(View.GONE);
            } else {
                List<Long> powerZoneList = new Gson().fromJson(powerZone, new TypeToken<List<Long>>() {
                }.getType());
                if (Collections.max(powerZoneList) == 0) {
                    mFragmentChartDetailsBinding.powerZone.setVisibility(View.GONE);
                } else {
                    mFragmentChartDetailsBinding.powerZone.updatePowerChart(powerZoneList, motionCyclingResponseVo.getPowerTypeZone());
                }
            }
        }
    }
}
