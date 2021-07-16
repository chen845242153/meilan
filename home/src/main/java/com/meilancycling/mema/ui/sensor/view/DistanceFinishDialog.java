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
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.Objects;

/**
 * @author sorelion qq 571135591
 */
public class DistanceFinishDialog extends Dialog implements View.OnClickListener {
    private TextView tvValue;
    private int mDistanceValue;

    public DistanceFinishDialog(Context context, int distanceValue) {
        super(context, R.style.dialog_style);
        mDistanceValue = distanceValue;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_distance_finish);
        setCanceledOnTouchOutside(true);
        tvValue = findViewById(R.id.stv_distance);
        TextView distanceUnit = findViewById(R.id.tv_distance_unit);
        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), mDistanceValue);
        tvValue.setText(unitBean.getValue());
        distanceUnit.setText(unitBean.getUnit());
        findViewById(R.id.iv_stop).setOnClickListener(this);
        findViewById(R.id.iv_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_stop:
                if (mDistanceFinishClickListener != null) {
                    mDistanceFinishClickListener.stopClick();
                }
                break;
            case R.id.iv_start:
                if (mDistanceFinishClickListener != null) {
                    mDistanceFinishClickListener.continueClick();
                }
                break;
            default:
        }
    }

    /**
     * 更新时间
     */
    public void updateDistance(int distance) {
        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), distance);
        tvValue.setText(unitBean.getValue());
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


    public interface DistanceFinishClickListener {
        /**
         * 点击结束按钮
         */
        void stopClick();

        /**
         * 点击继续按钮
         */
        void continueClick();
    }

    private DistanceFinishClickListener mDistanceFinishClickListener;

    public void setDistanceFinishClickListener(DistanceFinishClickListener distanceFinishClickListener) {
        mDistanceFinishClickListener = distanceFinishClickListener;
    }
}