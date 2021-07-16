package com.meilancycling.mema.ui.sensor.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.meilancycling.mema.R;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.Objects;

/**
 * 传感器预警
 *
 * @author sorelion qq 571135591
 */
public class TimeFinishDialog extends Dialog implements View.OnClickListener {
    private int mTimeValue;
    private TextView mStvValue;

    public TimeFinishDialog(Context context, int timeValue) {
        super(context, R.style.dialog_style);
        mTimeValue = timeValue;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time_finish);
        setCanceledOnTouchOutside(true);
        mStvValue= findViewById(R.id.stv_value);
        mStvValue.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(mTimeValue));
        findViewById(R.id.iv_stop).setOnClickListener(this);
        findViewById(R.id.iv_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_start:
                if (mTimeFinishClickListener != null) {
                    mTimeFinishClickListener.continueClick();
                }
                break;
            case R.id.iv_stop:
                if (mTimeFinishClickListener != null) {
                    mTimeFinishClickListener.stopClick();
                }
                break;
            default:
        }
    }

    /**
     * 更新时间
     */
    public void updateTime(int time) {
        mStvValue.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(time));
    }

    @Override
    public void show() {
        super.show();
        /*
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    public interface TimeFinishClickListener {
        /**
         * 点击结束按钮
         */
        void stopClick();

        /**
         * 点击继续按钮
         */
        void continueClick();
    }

    private TimeFinishClickListener mTimeFinishClickListener;

    public void setTimeFinishClickListener(TimeFinishClickListener timeFinishClickListener) {
        mTimeFinishClickListener = timeFinishClickListener;
    }
}