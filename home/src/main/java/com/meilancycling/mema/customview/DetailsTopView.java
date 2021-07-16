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
import com.meilancycling.mema.databinding.DetailsTopBinding;
import com.meilancycling.mema.utils.UnitConversionUtil;


/**
 * @Description: 汇总view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class DetailsTopView extends LinearLayout {

    private DetailsTopBinding mDetailsTopBinding;

    public DetailsTopView(Context context) {
        super(context);
        init();
    }

    public DetailsTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetailsTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mDetailsTopBinding = DataBindingUtil.inflate(inflater, R.layout.details_top, this, false);
            this.addView(mDetailsTopBinding.getRoot());
        }
    }

    /**
     * 设置数据
     */
    public void setDetailsData(int during, int avgSpeed, int kal) {
        mDetailsTopBinding.tvTimeValue.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(during));
        UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) avgSpeed / 10);
        mDetailsTopBinding.tvSpeedValue.setText(speedSetting.getValue());
        mDetailsTopBinding.tvCalValue.setText(String.valueOf(kal));
    }

    /**
     * 设置单位
     */
    @SuppressLint("SetTextI18n")
    public void setDataUnit() {
        mDetailsTopBinding.tvTimeUnit.setText(R.string.total_times);
        if (Config.unit == Unit.METRIC.value) {
            mDetailsTopBinding.tvSpeedUnit.setText(getContext().getString(R.string.average_speed) + "(" + getContext().getString(R.string.unit_kmh) + ")");
        } else {
            mDetailsTopBinding.tvSpeedUnit.setText(getContext().getString(R.string.average_speed) + "(" + getContext().getString(R.string.unit_mph) + ")");
        }

    }
}
