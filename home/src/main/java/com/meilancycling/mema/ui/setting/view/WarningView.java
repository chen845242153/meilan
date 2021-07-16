package com.meilancycling.mema.ui.setting.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.WarningViewBinding;
import com.meilancycling.mema.ui.setting.WarningActivity;

/**
 * @Description: 预警view
 * @Author: lion
 */
public class WarningView extends LinearLayout {
    private WarningViewBinding mWarningViewBinding;

    public WarningView(Context context) {
        super(context);
        init();
    }

    public WarningView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WarningView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mWarningViewBinding = DataBindingUtil.inflate(inflater, R.layout.warning_view, this, false);
            this.addView(mWarningViewBinding.getRoot());
        }
        mWarningViewBinding.tbSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (mWarningViewListener != null) {
                mWarningViewListener.warningToggleCallback(isChecked);
            }
            mWarningViewBinding.wpv.setProgressToggle(isChecked);
        });
    }

    /**
     * 设置数据
     */
    public void setWarningData(int current, int maxValue, int minValue, int toggle) {
        boolean toggleButton = toggle == WarningActivity.WARNING_OPEN;
        mWarningViewBinding.wpv.setProgressValue(current, maxValue, minValue, toggleButton);
        mWarningViewBinding.tbSwitch.setChecked(toggleButton);
    }

    /**
     * 设置title
     */
    public void setTitle(String title, WarningViewListener warningViewListener) {
        mWarningViewListener = warningViewListener;
        mWarningViewBinding.tvTitle.setText(title);
        mWarningViewBinding.wpv.setWarningProgressChangeListener(value -> {
            if (mWarningViewListener != null) {
                mWarningViewListener.warningValueCallback(value);
            }
        });
    }

    public interface WarningViewListener {
        /**
         * 预警值回调
         *
         * @param value 当前值
         */
        void warningValueCallback(int value);

        /**
         * 预警开关回调
         *
         * @param toggle 当前开关
         */
        void warningToggleCallback(boolean toggle);
    }

    private WarningViewListener mWarningViewListener;

}
