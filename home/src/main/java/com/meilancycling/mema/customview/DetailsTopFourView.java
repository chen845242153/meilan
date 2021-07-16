package com.meilancycling.mema.customview;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.databinding.DetailsTopFourBinding;
import com.meilancycling.mema.utils.UnitConversionUtil;


/**
 * @Description: 汇总view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class DetailsTopFourView extends LinearLayout {

    private DetailsTopFourBinding mDetailsTopFourBinding;

    public DetailsTopFourView(Context context) {
        super(context);
        init();
    }

    public DetailsTopFourView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetailsTopFourView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mDetailsTopFourBinding = DataBindingUtil.inflate(inflater, R.layout.details_top_four, this, false);
            this.addView(mDetailsTopFourBinding.getRoot());
        }
    }

    /**
     * 设置数据
     */
    public void setDetailsTopData(int during, int avgSpeed, int kal, int rise) {
        mDetailsTopFourBinding.tvTimeValue.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(during));
        UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) avgSpeed/10);
        mDetailsTopFourBinding.tvSpeedValue.setText(speedSetting.getValue());
        mDetailsTopFourBinding.tvCalValue.setText(String.valueOf(kal));
        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(getContext(), rise);
        mDetailsTopFourBinding.tvRiseValue.setText(unitBean.getValue());
    }

    /**
     * 设置单位
     */
    @SuppressLint("SetTextI18n")
    public void setDataUnit() {
        mDetailsTopFourBinding.tvTimeUnit.setText(R.string.total_times);
        if (Config.unit == Unit.METRIC.value) {
            mDetailsTopFourBinding.tvSpeedUnit.setText(getContext().getString(R.string.average_speed) + "(" + getContext().getString(R.string.unit_kmh) + ")");
            mDetailsTopFourBinding.tvRiseUnit.setText(getContext().getString(R.string.rise) + "(" + getContext().getString(R.string.unit_m) + ")");
        } else {
            mDetailsTopFourBinding.tvSpeedUnit.setText(getContext().getString(R.string.average_speed) + "(" + getContext().getString(R.string.unit_mph) + ")");
            mDetailsTopFourBinding.tvRiseUnit.setText(getContext().getString(R.string.rise) + "(" + getContext().getString(R.string.unit_feet) + ")");
        }

    }
}
