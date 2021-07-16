package com.meilancycling.mema.ui.setting.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.network.bean.UnitBean;

import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 体重弹窗
 *
 * @author sorelion qq 571135591
 */
public class WeightDialog extends Dialog implements View.OnClickListener {
    private int currentWeight;
    private int mPosition;

    public WeightDialog(Context context, int weight) {
        super(context, R.style.dialog_style);
        currentWeight = weight;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_weight);
        setCanceledOnTouchOutside(false);
        int maxValue;
        int minValue;
        List<Integer> mList = new ArrayList<>();
        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().weightSetting(getContext(), currentWeight);
        currentWeight = Integer.parseInt(unitBean.getValue());
        if (Config.unit == Unit.METRIC.value) {
            maxValue = 250;
            minValue = 30;
        } else {
            maxValue = 550;
            minValue = 67;
        }
        if (currentWeight < minValue) {
            currentWeight = minValue;
        } else if (currentWeight > maxValue) {
            currentWeight = maxValue;
        }
        for (int i = minValue; i <= maxValue; i++) {
            mList.add(i);
            if (currentWeight == i) {
                mPosition = i - minValue;
            }
        }
        TextView title = findViewById(R.id.title);
        title.setText(getContext().getString(R.string.weight) + "(" + unitBean.getUnit() + ")");
        View cancel = findViewById(R.id.view_cancel);
        View finish = findViewById(R.id.view_finish);
        cancel.setOnClickListener(this);
        finish.setOnClickListener(this);
        WheelPicker wheelPicker = findViewById(R.id.wp_weight);
        wheelPicker.setData(mList);
        wheelPicker.setSelectedItemPosition(mPosition);
        wheelPicker.setVisibleItemCount(5);
        wheelPicker.setIndicator(true);
        wheelPicker.setIndicatorColor(Color.parseColor("#FFD2D2D2"));
        wheelPicker.setIndicatorSize(AppUtils.dipToPx(getContext(), 1));
        // 设置是否有空气感，设置后上下边缘会渐变为透明，默认false
        wheelPicker.setAtmospheric(true);
        // 设置是否有卷曲感，不能微调卷曲幅度，默认false
        wheelPicker.setCurved(true);
        wheelPicker.post(() -> wheelPicker.setSelectedItemPosition(mPosition));
        wheelPicker.setOnItemSelectedListener((picker, data, position) -> currentWeight = mList.get(position)
        );
    }

    @Override
    public void show() {
        super.show();
        /*
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_cancel:
                dismiss();
                break;
            case R.id.view_finish:
                if (mWeightCallback != null) {
                    if (Config.unit == Unit.METRIC.value) {
                        mWeightCallback.weightCallback(currentWeight * 100, currentWeight);
                    } else {
                        mWeightCallback.weightCallback((int) Math.round(AppUtils.multiplyDouble(currentWeight * 100, 0.4536)), currentWeight);
                    }
                }
                dismiss();
                break;
            default:
        }
    }

    public interface WeightCallback {
        /**
         * 体重回调
         *
         * @param result 数据库数据
         * @param value  体重
         */
        void weightCallback(int result, int value);
    }

    private WeightCallback mWeightCallback;

    public void setWeightCallback(WeightCallback weightCallback) {
        mWeightCallback = weightCallback;
    }
}

