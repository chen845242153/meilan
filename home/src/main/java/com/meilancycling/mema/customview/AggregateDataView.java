package com.meilancycling.mema.customview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.AggregateDataBinding;

/**
 * @Description: 汇总view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class AggregateDataView extends LinearLayout {

    private AggregateDataBinding mAggregateDataBinding;

    public AggregateDataView(Context context) {
        super(context);
        init();
    }

    public AggregateDataView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AggregateDataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mAggregateDataBinding = DataBindingUtil.inflate(inflater, R.layout.aggregate_data, this, false);
            this.addView(mAggregateDataBinding.getRoot());
        }
    }

    /**
     * 设置数据
     */
    public void setAggregateData(String distanceValue, String distanceUnit, String time, String kal, String num) {
        mAggregateDataBinding.tvDistanceValue.setText(distanceValue);
        mAggregateDataBinding.tvDistanceUnit.setText(distanceUnit);
        mAggregateDataBinding.tvTimeValue.setText(time);

        mAggregateDataBinding.tvNumValue.setText(num);
        if (kal == null) {
            mAggregateDataBinding.llCal.setVisibility(GONE);
            mAggregateDataBinding.viewCal.setVisibility(GONE);
        } else {
            mAggregateDataBinding.tvCalValue.setText(kal);
        }
    }

}
