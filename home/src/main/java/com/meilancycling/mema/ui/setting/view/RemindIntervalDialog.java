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

import com.aigestudio.wheelpicker.WheelPicker;
import com.meilancycling.mema.R;
import com.meilancycling.mema.ui.setting.WarningActivity;
import com.meilancycling.mema.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 提醒间隔
 *
 * @author sorelion qq 571135591
 */
public class RemindIntervalDialog extends Dialog {
    private int currentTime;
    private int mPosition;

    public RemindIntervalDialog(Context context, int time) {
        super(context, R.style.dialog_style);
        currentTime = time;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_remind_interval);
        setCanceledOnTouchOutside(false);
        List<Integer> mList = new ArrayList<>();
        for (int i = WarningActivity.INTERVAL_MIN; i <= WarningActivity.INTERVAL_MAX; i++) {
            mList.add(i);
            if (currentTime == i) {
                mPosition = i - WarningActivity.INTERVAL_MIN;
            }
        }
        View finish = findViewById(R.id.view_finish);
        finish.setOnClickListener(v -> {
            dismiss();
            if (mRemindIntervalClickCallback != null) {
                mRemindIntervalClickCallback.confirm(currentTime);
            }
        });

        WheelPicker wheelPicker = findViewById(R.id.wp_time);
        wheelPicker.setData(mList);
        wheelPicker.setSelectedItemPosition(mPosition);
        wheelPicker.setVisibleItemCount(3);
        wheelPicker.setIndicator(true);
        wheelPicker.setIndicatorColor(Color.parseColor("#FFD2D2D2"));
        wheelPicker.setIndicatorSize(AppUtils.dipToPx(getContext(), 1));
        // 设置是否有空气感，设置后上下边缘会渐变为透明，默认false
        wheelPicker.setAtmospheric(true);
        // 设置是否有卷曲感，不能微调卷曲幅度，默认false
        wheelPicker.setCurved(true);
        wheelPicker.post(() -> wheelPicker.setSelectedItemPosition(mPosition));
        wheelPicker.setOnItemSelectedListener((picker, data, position) -> currentTime = mList.get(position));
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

    public interface RemindIntervalClickCallback {
        /**
         * 确定
         *
         * @param result
         */
        void confirm(int result);
    }

    private RemindIntervalClickCallback mRemindIntervalClickCallback;

    public void setRemindIntervalClickCallback(RemindIntervalClickCallback remindIntervalClickCallback) {
        mRemindIntervalClickCallback = remindIntervalClickCallback;
    }
}

