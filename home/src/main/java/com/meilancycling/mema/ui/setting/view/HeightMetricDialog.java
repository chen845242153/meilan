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
import com.meilancycling.mema.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 身高公制
 *
 * @author sorelion qq 571135591
 */
public class HeightMetricDialog extends Dialog implements View.OnClickListener {
    private int currentHeight;
    private int mPosition;

    public HeightMetricDialog(Context context, int height) {
        super(context, R.style.dialog_style);
        currentHeight = height;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_height_metric);
        setCanceledOnTouchOutside(false);
        int maxValue = 250;
        int minValue = 50;
        currentHeight = currentHeight / 100;
        List<Integer> mList = new ArrayList<>();

        if (currentHeight < minValue) {
            currentHeight = minValue;
        } else if (currentHeight > maxValue) {
            currentHeight = maxValue;
        }
        for (int i = minValue; i <= maxValue; i++) {
            mList.add(i);
            if (currentHeight == i) {
                mPosition = i - minValue;
            }
        }
        TextView title = findViewById(R.id.title);
        title.setText(getContext().getString(R.string.height) + "(" + getContext().getString(R.string.unit_cm) + ")");
        View cancel = findViewById(R.id.view_cancel);
        View finish = findViewById(R.id.view_finish);
        cancel.setOnClickListener(this);
        finish.setOnClickListener(this);
        WheelPicker wheelPicker = findViewById(R.id.wp_height);
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
        wheelPicker.setOnItemSelectedListener((picker, data, position) -> currentHeight = mList.get(position)
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
                if (mHeightCallback != null) {
                    mHeightCallback.heightCallback(currentHeight * 100, currentHeight);
                }
                dismiss();
                break;
            default:
        }
    }

    public interface HeightCallback {
        /**
         * 身高回调
         *
         * @param result 数据库数据
         * @param value  身高
         */
        void heightCallback(int result, int value);
    }

    private HeightCallback mHeightCallback;

    public void setHeightCallback(HeightCallback heightCallback) {
        mHeightCallback = heightCallback;
    }
}

