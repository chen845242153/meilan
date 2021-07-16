package com.meilancycling.mema.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.databinding.CustomChartBinding;
import com.meilancycling.mema.ui.details.HorizontalScreenActivity;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.List;


/**
 * @Description: 汇总view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class CustomChartView extends LinearLayout {

    private CustomChartBinding mCustomChartBinding;

    public CustomChartView(Context context) {
        super(context);
        init();
    }

    public CustomChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mCustomChartBinding = DataBindingUtil.inflate(inflater, R.layout.custom_chart, this, false);
            this.addView(mCustomChartBinding.getRoot());
        }
    }

    /**
     * 设置表格数据
     */
    @SuppressLint("SetTextI18n")
    public void setChartData(List<List<Long>> data, Integer maxValue, Integer minValue, Integer avgData, int type) {
        switch (type) {
            case ChartDetailsView.CHART_SPEED:
                mCustomChartBinding.tvChartTitle.setText(getContext().getString(R.string.speed));
                UnitBean speedData1 = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) maxValue / 10);
                UnitBean speedData2 = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) avgData / 10);
                mCustomChartBinding.tvChartUnit.setText("(" + speedData1.getUnit() + ")");
                mCustomChartBinding.tvChartItem1.setText(getContext().getString(R.string.maximum) + speedData1.getValue());
                mCustomChartBinding.tvChartItem2.setText(getContext().getString(R.string.average) + speedData2.getValue());
                break;
            case ChartDetailsView.CHART_CADENCE:
                mCustomChartBinding.tvChartTitle.setText(getContext().getString(R.string.cadence));
                mCustomChartBinding.tvChartUnit.setText("(" + getContext().getString(R.string.cadence_unit) + ")");
                mCustomChartBinding.tvChartItem1.setText(getContext().getString(R.string.maximum) + maxValue);
                mCustomChartBinding.tvChartItem2.setText(getContext().getString(R.string.average) + avgData);
                break;
            case ChartDetailsView.CHART_ALTITUDE:
                mCustomChartBinding.tvChartTitle.setText(getContext().getString(R.string.altitude));
                UnitBean altitudeData1 = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(getContext(), maxValue);
                UnitBean altitudeData2 = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(getContext(), minValue);
                mCustomChartBinding.tvChartUnit.setText("(" + altitudeData1.getUnit() + ")");
                mCustomChartBinding.tvChartItem1.setText(getContext().getString(R.string.maximum) + altitudeData1.getValue());
                mCustomChartBinding.tvChartItem2.setText(getContext().getString(R.string.minimum) + altitudeData2.getValue());
                break;
            case ChartDetailsView.CHART_HEART_RATE:
                mCustomChartBinding.tvChartTitle.setText(getContext().getString(R.string.heart_rate));
                mCustomChartBinding.tvChartUnit.setText("(" + getContext().getString(R.string.heart_unit) + ")");
                mCustomChartBinding.tvChartItem1.setText(getContext().getString(R.string.maximum) + maxValue);
                mCustomChartBinding.tvChartItem2.setText(getContext().getString(R.string.average) + avgData);
                break;
            case ChartDetailsView.CHART_POWER:
                mCustomChartBinding.tvChartTitle.setText(getContext().getString(R.string.power));
                mCustomChartBinding.tvChartUnit.setText("(" + getContext().getString(R.string.unit_w) + ")");
                mCustomChartBinding.tvChartItem1.setText(getContext().getString(R.string.maximum) + maxValue);
                mCustomChartBinding.tvChartItem2.setText(getContext().getString(R.string.average) + avgData);
                break;
            case ChartDetailsView.CHART_TEMPERATURE:
                mCustomChartBinding.tvChartTitle.setText(getContext().getString(R.string.temperature));
                UnitBean temperatureData1 = UnitConversionUtil.getUnitConversionUtil().temperatureSetting(getContext(), maxValue);
                UnitBean temperatureData2 = UnitConversionUtil.getUnitConversionUtil().temperatureSetting(getContext(), avgData);
                mCustomChartBinding.tvChartUnit.setText("(" + temperatureData1.getUnit() + ")");
                mCustomChartBinding.tvChartItem1.setText(getContext().getString(R.string.maximum) + temperatureData1.getValue());
                mCustomChartBinding.tvChartItem2.setText(getContext().getString(R.string.average) + temperatureData2.getValue());
                break;
            default:
        }
        mCustomChartBinding.cdvChart.setData(type, data, avgData, maxValue, minValue);

        mCustomChartBinding.slChart.setOnClickListener(v -> {
            HorizontalScreenActivity.LINE_CHART_TYPE = type;
            getContext().startActivity(new Intent(getContext(), HorizontalScreenActivity.class));
        });
    }

}
