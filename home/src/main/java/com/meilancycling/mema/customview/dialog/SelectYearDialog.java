package com.meilancycling.mema.customview.dialog;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * 年份选择
 *
 * @author sorelion qq 571135591
 */
public class SelectYearDialog extends Dialog implements View.OnClickListener {
    private int year;
    int position;

    public SelectYearDialog(Context context, int year) {
        super(context, R.style.dialog_style);
        this.year = year;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_year);
        TextView cancel = findViewById(R.id.tv_year_cancel);
        TextView finish = findViewById(R.id.tv_year_finish);
        cancel.setOnClickListener(this);
        finish.setOnClickListener(this);
        WheelPicker wheelPicker = findViewById(R.id.wp_select_year);
        List<Integer> mData = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = calendar.get(Calendar.YEAR); i >= Config.START_YEAR; i--) {
            if (year == i) {
                position = calendar.get(Calendar.YEAR) - i;
            }
            mData.add(i);
        }
        wheelPicker.setData(mData);
        wheelPicker.setSelectedItemPosition(position);
        wheelPicker.setVisibleItemCount(7);
        // 设置是否有幕布，设置后选中项会被指定的颜色覆盖，默认false
        wheelPicker.setCurtain(true);
        wheelPicker.setCurtainColor(Color.parseColor("#80D6D6D6"));
        // 设置是否有空气感，设置后上下边缘会渐变为透明，默认false
        wheelPicker.setAtmospheric(true);
        // 设置是否有卷曲感，不能微调卷曲幅度，默认false
        wheelPicker.setCurved(true);
        wheelPicker.post(() -> wheelPicker.setSelectedItemPosition(position));
        wheelPicker.setOnItemSelectedListener((picker, data, position) -> year = mData.get(position));
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
        int id = v.getId();
        if (id == R.id.tv_year_cancel) {
            dismiss();
        } else if (id == R.id.tv_year_finish) {
            if (mSelectYearClickListener != null) {
                mSelectYearClickListener.confirm(year);
            }
            dismiss();
        }
    }

    public interface SelectYearClickListener {
        /**
         * 年份确定
         *
         * @param value 年份
         */
        void confirm(int value);
    }

    private SelectYearClickListener mSelectYearClickListener;

    public void setSelectYearClickListener(SelectYearClickListener selectYearClickListener) {
        mSelectYearClickListener = selectYearClickListener;
    }
}

