package com.meilancycling.mema.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.PowerChartViewBinding;
import com.meilancycling.mema.ui.details.ChartDetailsFragment;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.List;

/**
 * @Description: 汇总view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class PowerChartView extends LinearLayout {

    private PowerChartViewBinding mPowerChartViewBinding;

    public PowerChartView(Context context) {
        super(context);
        init();
    }

    public PowerChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PowerChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mPowerChartViewBinding = DataBindingUtil.inflate(inflater, R.layout.power_chart_view, this, false);
            this.addView(mPowerChartViewBinding.getRoot());
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void updatePowerChart(List<Long> powerList, int powerTypeZone) {
        if (powerTypeZone != 1) {
            mPowerChartViewBinding.tvPowerValue1.setVisibility(INVISIBLE);
            mPowerChartViewBinding.tvPowerValue2.setVisibility(INVISIBLE);
            mPowerChartViewBinding.tvPowerValue3.setVisibility(INVISIBLE);
            mPowerChartViewBinding.tvPowerValue4.setVisibility(INVISIBLE);
            mPowerChartViewBinding.tvPowerValue5.setVisibility(INVISIBLE);
            mPowerChartViewBinding.tvPowerValue6.setVisibility(INVISIBLE);
            mPowerChartViewBinding.tvPowerValue7.setVisibility(INVISIBLE);
        }
        List<Integer> percentage = AppUtils.percentage(powerList.get(0),
                powerList.get(1),
                powerList.get(2),
                powerList.get(3),
                powerList.get(4),
                powerList.get(5),
                powerList.get(6));

        mPowerChartViewBinding.iv1.setIntervalView(
                getContext().getDrawable(R.drawable.shape_circle_66),
                getContext().getString(R.string.zone_7),
                UnitConversionUtil.getUnitConversionUtil().timeToHMS(Integer.parseInt(String.valueOf(powerList.get(6)))),
                percentage.get(6) + "%");
        mPowerChartViewBinding.iv2.setIntervalView(
                getContext().getDrawable(R.drawable.shape_circle_30),
                getContext().getString(R.string.zone_6),
                UnitConversionUtil.getUnitConversionUtil().timeToHMS(Integer.parseInt(String.valueOf(powerList.get(5)))),
                percentage.get(5) + "%");
        mPowerChartViewBinding.iv3.setIntervalView(
                getContext().getDrawable(R.drawable.shape_circle_ec),
                getContext().getString(R.string.zone_5),
                UnitConversionUtil.getUnitConversionUtil().timeToHMS(Integer.parseInt(String.valueOf(powerList.get(4)))),
                percentage.get(4) + "%");
        mPowerChartViewBinding.iv4.setIntervalView(
                getContext().getDrawable(R.drawable.shape_circle_f7),
                getContext().getString(R.string.zone_4),
                UnitConversionUtil.getUnitConversionUtil().timeToHMS(Integer.parseInt(String.valueOf(powerList.get(3)))),
                percentage.get(3) + "%");
        mPowerChartViewBinding.iv5.setIntervalView(
                getContext().getDrawable(R.drawable.shape_circle_ff),
                getContext().getString(R.string.zone_3),
                UnitConversionUtil.getUnitConversionUtil().timeToHMS(Integer.parseInt(String.valueOf(powerList.get(2)))),
                percentage.get(2) + "%");
        mPowerChartViewBinding.iv6.setIntervalView(
                getContext().getDrawable(R.drawable.shape_circle_main),
                getContext().getString(R.string.zone_2),
                UnitConversionUtil.getUnitConversionUtil().timeToHMS(Integer.parseInt(String.valueOf(powerList.get(1)))),
                percentage.get(1) + "%");
        mPowerChartViewBinding.iv7.setIntervalView(
                getContext().getDrawable(R.drawable.shape_circle_29),
                getContext().getString(R.string.zone_1),
                UnitConversionUtil.getUnitConversionUtil().timeToHMS(Integer.parseInt(String.valueOf(powerList.get(0)))),
                percentage.get(0) + "%");


        mPowerChartViewBinding.powerZone.setProgress(percentage);
    }

}
