package com.meilancycling.mema.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.HeartRateViewBinding;
import com.meilancycling.mema.ui.details.ChartDetailsFragment;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.List;

/**
 * @Description: 汇总view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class HeartRateChartView extends LinearLayout {

    private HeartRateViewBinding mHeartRateViewBinding;

    public HeartRateChartView(Context context) {
        super(context);
        init();
    }

    public HeartRateChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeartRateChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mHeartRateViewBinding = DataBindingUtil.inflate(inflater, R.layout.heart_rate_view, this, false);
            this.addView(mHeartRateViewBinding.getRoot());
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void updateHeartChart(List<Long> hrmList, int hrType) {
        if (hrType != 1) {
            mHeartRateViewBinding.tvHrValue1.setVisibility(INVISIBLE);
            mHeartRateViewBinding.tvHrValue2.setVisibility(INVISIBLE);
            mHeartRateViewBinding.tvHrValue3.setVisibility(INVISIBLE);
            mHeartRateViewBinding.tvHrValue4.setVisibility(INVISIBLE);
            mHeartRateViewBinding.tvHrValue5.setVisibility(INVISIBLE);
        }
        List<Integer> percentage = AppUtils.percentage(hrmList.get(0),
                hrmList.get(1),
                hrmList.get(2),
                hrmList.get(3),
                hrmList.get(4));

        mHeartRateViewBinding.iv1.setIntervalView(
                getContext().getDrawable(R.drawable.shape_circle_b7),
                getContext().getString(R.string.zone_5),
                UnitConversionUtil.getUnitConversionUtil().timeToHMS(Integer.parseInt(String.valueOf(hrmList.get(4)))),
                percentage.get(4) + "%");
        mHeartRateViewBinding.iv2.setIntervalView(
                getContext().getDrawable(R.drawable.shape_circle_ee),
                getContext().getString(R.string.zone_4),
                UnitConversionUtil.getUnitConversionUtil().timeToHMS(Integer.parseInt(String.valueOf(hrmList.get(3)))),
                percentage.get(3) + "%");
        mHeartRateViewBinding.iv3.setIntervalView(
                getContext().getDrawable(R.drawable.shape_circle_main),
                getContext().getString(R.string.zone_3),
                UnitConversionUtil.getUnitConversionUtil().timeToHMS(Integer.parseInt(String.valueOf(hrmList.get(2)))),
                percentage.get(2) + "%");
        mHeartRateViewBinding.iv4.setIntervalView(
                getContext().getDrawable(R.drawable.shape_circle_57),
                getContext().getString(R.string.zone_2),
                UnitConversionUtil.getUnitConversionUtil().timeToHMS(Integer.parseInt(String.valueOf(hrmList.get(1)))),
                percentage.get(1) + "%");
        mHeartRateViewBinding.iv5.setIntervalView(
                getContext().getDrawable(R.drawable.shape_circle_99),
                getContext().getString(R.string.zone_1),
                UnitConversionUtil.getUnitConversionUtil().timeToHMS(Integer.parseInt(String.valueOf(hrmList.get(0)))),
                percentage.get(0) + "%");

        mHeartRateViewBinding.heartZone.setProgress(percentage);
    }

}
