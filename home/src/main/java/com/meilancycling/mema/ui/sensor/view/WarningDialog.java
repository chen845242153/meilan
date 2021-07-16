package com.meilancycling.mema.ui.sensor.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.Objects;

/**
 * @author sorelion qq 571135591
 */
public class WarningDialog extends Dialog {
    /**
     * 0 速度预警
     * 1 心率预警
     * 2 踏频预警
     * 3 功率预警
     */
    private int mWarningType;
    private double mCurrentValue;
    private double mWarningValue;
    private boolean mIsMax;

    public WarningDialog(Context context, int warningType, double currentValue, double warningValue, boolean isMax) {
        super(context, R.style.dialog_style);
        mWarningType = warningType;
        mCurrentValue = currentValue;
        mWarningValue = warningValue;
        mIsMax = isMax;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_warning);
        setCanceledOnTouchOutside(true);
        ImageView type = findViewById(R.id.iv_type);
        TextView stvLeft = findViewById(R.id.stv_left);
        TextView leftUnit = findViewById(R.id.tv_left_unit);
        TextView stvRight = findViewById(R.id.stv_right);
        TextView rightUnit = findViewById(R.id.tv_right_unit);
        if (mIsMax) {
            stvRight.setTextColor(ContextCompat.getColor(getContext(), R.color.delete_red));
            switch (mWarningType) {
                case 0:
                    type.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sensor_speed_h));
                    UnitBean currentBean = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), mCurrentValue);
                    UnitBean waringBean = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), mWarningValue);
                    stvLeft.setText(currentBean.getValue());
                    leftUnit.setText(currentBean.getUnit());
                    stvRight.setText(waringBean.getValue());
                    rightUnit.setText(waringBean.getUnit());
                    break;
                case 1:
                    type.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sensor_hr_h));
                    stvLeft.setText(String.valueOf((int) (mCurrentValue)));
                    leftUnit.setText(R.string.heart_unit);
                    stvRight.setText(String.valueOf((int) (mWarningValue)));
                    rightUnit.setText(R.string.heart_unit);
                    break;
                case 2:
                    type.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sensor_cadence_h));
                    stvLeft.setText(String.valueOf((int) (mCurrentValue)));
                    leftUnit.setText(R.string.cadence_unit);
                    stvRight.setText(String.valueOf((int) (mWarningValue)));
                    rightUnit.setText(R.string.cadence_unit);
                    break;
                case 3:
                    type.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sensor_power_h));
                    stvLeft.setText(String.valueOf((int) (mCurrentValue)));
                    leftUnit.setText(R.string.unit_w);
                    stvRight.setText(String.valueOf((int) (mWarningValue)));
                    rightUnit.setText(R.string.unit_w);
                    break;
                default:
            }
        } else {
            stvRight.setTextColor(Color.parseColor("#FFEE8D1F"));
            switch (mWarningType) {
                case 0:
                    type.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sensor_speed_l));
                    UnitBean currentBean = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), mCurrentValue);
                    UnitBean waringBean = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), mWarningValue);
                    stvLeft.setText(currentBean.getValue());
                    leftUnit.setText(currentBean.getUnit());
                    stvRight.setText(waringBean.getValue());
                    rightUnit.setText(waringBean.getUnit());
                    break;
                case 1:
                    type.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sensor_hr_l));
                    stvLeft.setText(String.valueOf((int) (mCurrentValue)));
                    leftUnit.setText(R.string.heart_unit);
                    stvRight.setText(String.valueOf((int) (mWarningValue)));
                    rightUnit.setText(R.string.heart_unit);
                    break;
                case 2:
                    type.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sensor_cadence_l));
                    stvLeft.setText(String.valueOf((int) (mCurrentValue)));
                    leftUnit.setText(R.string.cadence_unit);
                    stvRight.setText(String.valueOf((int) (mWarningValue)));
                    rightUnit.setText(R.string.cadence_unit);
                    break;
                case 3:
                    type.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sensor_power_l));
                    stvLeft.setText(String.valueOf((int) (mCurrentValue)));
                    leftUnit.setText(R.string.unit_w);
                    stvRight.setText(String.valueOf((int) (mWarningValue)));
                    rightUnit.setText(R.string.unit_w);
                    break;
                default:
            }
        }
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

}