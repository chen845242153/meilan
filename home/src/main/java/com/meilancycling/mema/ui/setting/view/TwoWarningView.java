package com.meilancycling.mema.ui.setting.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.TwoWarningViewBinding;
import com.meilancycling.mema.ui.setting.WarningActivity;


/**
 * @Description: 预警view
 * @Author: lion
 */
public class TwoWarningView extends LinearLayout {
    private TwoWarningViewBinding mTwoWarningViewBinding;

    public TwoWarningView(Context context) {
        super(context);
        init();
    }

    public TwoWarningView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TwoWarningView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mTwoWarningViewBinding = DataBindingUtil.inflate(inflater, R.layout.two_warning_view, this, false);
            this.addView(mTwoWarningViewBinding.getRoot());
        }
        mTwoWarningViewBinding.tbSwitchMin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (mTwoWarningViewListener != null) {
                mTwoWarningViewListener.warningMinToggleCallback(isChecked);
            }
            mTwoWarningViewBinding.tpv.setMinProgressToggle(isChecked);
        });

        mTwoWarningViewBinding.tbSwitchMax.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (mTwoWarningViewListener != null) {
                mTwoWarningViewListener.warningMaxToggleCallback(isChecked);
            }
            mTwoWarningViewBinding.tpv.setMaxProgressToggle(isChecked);
        });
    }

    /**
     * 设置数据
     */
    public void setWarningData(int minCurrent, int macCurrent, int maxValue, int minValue, int minToggle, int maxToggle) {
        boolean min = minToggle == WarningActivity.WARNING_OPEN;
        boolean max = maxToggle == WarningActivity.WARNING_OPEN;
        mTwoWarningViewBinding.tpv.setProgressValue(minCurrent, macCurrent, maxValue, minValue, min, max);
        mTwoWarningViewBinding.tbSwitchMin.setChecked(min);
        mTwoWarningViewBinding.tbSwitchMax.setChecked(max);
    }

    /**
     * 设置title
     */
    public void setTitle(String title, TwoWarningViewListener twoWarningViewListener) {
        mTwoWarningViewListener = twoWarningViewListener;
        mTwoWarningViewBinding.tvTitle.setText(title);
        mTwoWarningViewBinding.tpv.setTwoWarningProgressChangeListener(new TwoWarningProgressView.TwoWarningProgressChangeListener() {
            @Override
            public void minValueCallback(int value) {
                if (mTwoWarningViewListener != null) {
                    mTwoWarningViewListener.warningMinValueCallback(value);
                }
            }

            @Override
            public void maxValueCallback(int value) {
                if (mTwoWarningViewListener != null) {
                    mTwoWarningViewListener.warningMaxValueCallback(value);
                }
            }
        });

    }

    public interface TwoWarningViewListener {
        /**
         * 最小值预警值回调
         *
         * @param value 当前值
         */
        void warningMinValueCallback(int value);

        /**
         * 最小值预警开关
         *
         * @param toggle 当前开关
         */
        void warningMinToggleCallback(boolean toggle);

        /**
         * 最大值预警值回调
         *
         * @param value 当前值
         */
        void warningMaxValueCallback(int value);

        /**
         * 最大值预警开关
         *
         * @param toggle 当前开关
         */
        void warningMaxToggleCallback(boolean toggle);
    }

    private TwoWarningViewListener mTwoWarningViewListener;
}
