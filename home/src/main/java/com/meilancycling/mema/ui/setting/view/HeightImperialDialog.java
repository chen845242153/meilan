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
 * 身高英制
 *
 * @author sorelion qq 571135591
 */
public class HeightImperialDialog extends Dialog implements View.OnClickListener {
    private double currentHeight;
    private int leftPosition;
    private int rightPosition;
    private WheelPicker rightWheelPicker;
    private List<Integer> rightList;
    private List<Integer> rightMinList;
    private List<Integer> rightMaxList;
    private int right;
    private List<Integer> leftList;
    private int left;

    public HeightImperialDialog(Context context, int height) {
        super(context, R.style.dialog_style);
        currentHeight = height;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_height_imperial);
        setCanceledOnTouchOutside(false);
        currentHeight = AppUtils.multiplyDouble(currentHeight, 0.00328084);
        int data = (int) Math.round(currentHeight);
        int minValue = 16;
        int maxValue = 82;
        if (data > maxValue) {
            data = maxValue;
        } else if (data < minValue) {
            data = minValue;
        }
        left = (int) Math.floor((double) data / 10);
        leftList = new ArrayList<>();
        int leftMinValue = 1;
        int leftMaxValue = 8;
        for (int i = leftMinValue; i <= leftMaxValue; i++) {
            leftList.add(i);
            if (left == i) {
                leftPosition = i - leftMinValue;
            }
        }
        right = data - left * 10;

        rightList = new ArrayList<>();
        rightMinList = new ArrayList<>();
        rightMaxList = new ArrayList<>();

        int rightMinValue = 0;
        int rightMaxValue = 9;
        for (int i = rightMinValue; i <= rightMaxValue; i++) {
            rightList.add(i);
        }
        rightMinList.add(6);
        rightMinList.add(7);
        rightMinList.add(8);
        rightMinList.add(9);

        rightMaxList.add(0);
        rightMaxList.add(1);
        rightMaxList.add(2);

        TextView title = findViewById(R.id.title);
        title.setText(getContext().getString(R.string.height) + "(" + getContext().getString(R.string.unit_feet) + ")");
        View cancel = findViewById(R.id.view_cancel);
        View finish = findViewById(R.id.view_finish);
        cancel.setOnClickListener(this);
        finish.setOnClickListener(this);

        WheelPicker leftWheelPicker = findViewById(R.id.wp_height_left);
        leftWheelPicker.setData(leftList);
        leftWheelPicker.setVisibleItemCount(5);
        leftWheelPicker.setIndicator(true);
        leftWheelPicker.setIndicatorColor(Color.parseColor("#FFD2D2D2"));
        leftWheelPicker.setIndicatorSize(AppUtils.dipToPx(getContext(), 1));
        // 设置是否有空气感，设置后上下边缘会渐变为透明，默认false
        leftWheelPicker.setAtmospheric(true);
        // 设置是否有卷曲感，不能微调卷曲幅度，默认false
        leftWheelPicker.setCurved(true);
        leftWheelPicker.post(() -> leftWheelPicker.setSelectedItemPosition(leftPosition, false));

        rightWheelPicker = findViewById(R.id.wp_height_right);
        rightWheelPicker.setVisibleItemCount(5);
        rightWheelPicker.setIndicator(true);
        rightWheelPicker.setIndicatorColor(Color.parseColor("#FFD2D2D2"));
        rightWheelPicker.setIndicatorSize(AppUtils.dipToPx(getContext(), 1));
        // 设置是否有空气感，设置后上下边缘会渐变为透明，默认false
        rightWheelPicker.setAtmospheric(true);
        // 设置是否有卷曲感，不能微调卷曲幅度，默认false
        rightWheelPicker.setCurved(true);
        showRightUi();
        leftWheelPicker.setOnItemSelectedListener(leftListener);
        rightWheelPicker.setOnItemSelectedListener(rightListener);
    }

    private WheelPicker.OnItemSelectedListener leftListener = new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            leftPosition = position;
            left = leftList.get(position);
            showRightUi();
        }
    };
    private WheelPicker.OnItemSelectedListener rightListener = new WheelPicker.OnItemSelectedListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            if (leftPosition == 0) {
                right = rightMinList.get(position);
            } else if (leftPosition == leftList.size() - 1) {
                right = rightMaxList.get(position);
            } else {
                right = rightList.get(position);
            }
        }
    };

    private void showRightUi() {
        if (leftPosition == 0) {
            rightWheelPicker.setData(rightMinList);
            rightPosition = 0;
            for (int i = 0; i < rightMinList.size(); i++) {
                if (rightMinList.get(i) == right) {
                    rightPosition = i;
                }
            }
        } else if (leftPosition == leftList.size() - 1) {
            rightWheelPicker.setData(rightMaxList);
            rightPosition = rightMaxList.size() - 1;
            for (int i = 0; i < rightMaxList.size(); i++) {
                if (rightMaxList.get(i) == right) {
                    rightPosition = i;
                }
            }
        } else {
            for (int i = 0; i < rightList.size(); i++) {
                if (rightList.get(i) == right) {
                    rightPosition = i;
                }
            }
            rightWheelPicker.setData(rightList);
        }
        rightWheelPicker.post(() -> rightWheelPicker.setSelectedItemPosition(rightPosition, false));
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


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_cancel:
                dismiss();
                break;
            case R.id.view_finish:
                if (mHeightImperialCallback != null) {
                    double value = AppUtils.stringToDouble(left + "." + right);
                    mHeightImperialCallback.heightCallback((int) Math.round(AppUtils.multiplyDouble(value, 3048)), value);
                }
                dismiss();
                break;
            default:
        }
    }

    public interface HeightImperialCallback {
        /**
         * 身高回调
         *
         * @param result 数据库数据
         * @param value  身高
         */
        void heightCallback(int result, double value);
    }

    private HeightImperialCallback mHeightImperialCallback;

    public void setHeightImperialCallback(HeightImperialCallback heightImperialCallback) {
        mHeightImperialCallback = heightImperialCallback;
    }
}

