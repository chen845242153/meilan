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
import com.meilancycling.mema.databinding.SlopeViewBinding;
import com.meilancycling.mema.ui.details.ChartDetailsFragment;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.List;

/**
 * @Description: 汇总view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class SlopeView extends LinearLayout {

    private SlopeViewBinding mSlopeViewBinding;

    public SlopeView(Context context) {
        super(context);
        init();
    }

    public SlopeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlopeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mSlopeViewBinding = DataBindingUtil.inflate(inflater, R.layout.slope_view, this, false);
            mSlopeViewBinding.spvUp.initView(SlopeProgressView.UPHILL);
            mSlopeViewBinding.spvLevel.initView(SlopeProgressView.LEVEL);
            mSlopeViewBinding.spvDownhill.initView(SlopeProgressView.DOWNHILL);
            this.addView(mSlopeViewBinding.getRoot());
        }
    }

    /**
     * 更新爬坡进度
     * 爬坡分析区间(下坡，平地，上坡)
     */
    @SuppressLint("SetTextI18n")
    public void updateSlope(List<Long> slopeList) {
        List<Integer> percentage = AppUtils.percentage(slopeList.get(0),slopeList.get(1),slopeList.get(2));
        mSlopeViewBinding.tvDownProgress.setText(percentage.get(0) + "%");
        UnitBean downBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), Integer.parseInt(String.valueOf(slopeList.get(0))));
        mSlopeViewBinding.tvDownValue.setText(downBean.getValue() + downBean.getUnit());
        mSlopeViewBinding.spvDownhill.setProgress((double) percentage.get(0) / 100);

        mSlopeViewBinding.tvLevelProgress.setText(percentage.get(1) + "%");
        UnitBean levelBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), Integer.parseInt(String.valueOf(slopeList.get(1))));
        mSlopeViewBinding.tvLevelValue.setText(levelBean.getValue() + levelBean.getUnit());
        mSlopeViewBinding.spvLevel.setProgress((double) percentage.get(1) / 100);

        mSlopeViewBinding.tvUpProgress.setText(percentage.get(2) + "%");
        UnitBean upBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), Integer.parseInt(String.valueOf(slopeList.get(2))));
        mSlopeViewBinding.tvUpValue.setText(upBean.getValue() + upBean.getUnit());
        mSlopeViewBinding.spvUp.setProgress((double) percentage.get(2) / 100);

    }


}
