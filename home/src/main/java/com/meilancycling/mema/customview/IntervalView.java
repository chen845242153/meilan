package com.meilancycling.mema.customview;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.IntervalViewBinding;


/**
 * @Description: 汇总view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class IntervalView extends LinearLayout {

    private IntervalViewBinding mIntervalViewBinding;

    public IntervalView(Context context) {
        super(context);
        init();
    }

    public IntervalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IntervalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mIntervalViewBinding = DataBindingUtil.inflate(inflater, R.layout.interval_view, this, false);

            this.addView(mIntervalViewBinding.getRoot());
        }
    }

    /**
     * 设置数据
     */
    public void setIntervalView(Drawable drawable, String name, String time, String progress) {
        mIntervalViewBinding.viewInterval.setBackground(drawable);
        mIntervalViewBinding.tvIntervalName.setText(name);
        mIntervalViewBinding.tvIntervalDuring.setText(time);
        mIntervalViewBinding.tvIntervalProgress.setText(progress);

    }

}
