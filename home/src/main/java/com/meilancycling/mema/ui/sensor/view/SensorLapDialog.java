package com.meilancycling.mema.ui.sensor.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.meilancycling.mema.R;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.Objects;

/**
 * 传感器分圈
 *
 * @author sorelion qq 571135591
 */
public class SensorLapDialog extends Dialog {
    private String mMessage;
    private int mTime;
    private int mDistance;
    private double mSpeed;

    public SensorLapDialog(Context context, String titleMessage, int time, int distance, double speed) {
        super(context, R.style.dialog_style);
        mMessage = titleMessage;
        mTime = time;
        mDistance = distance;
        mSpeed = speed;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sensor_lap);
        setCanceledOnTouchOutside(true);
        TextView title = findViewById(R.id.tv_title);
        title.setText(mMessage);
        TextView time = findViewById(R.id.tv_time);
        time.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(mTime));
        TextView distance = findViewById(R.id.tv_distance);
        TextView speed = findViewById(R.id.tv_speed);
        if (mSpeed * 10 == 0xFFFF) {
            mSpeed = 0;
        }
        distance.setText(UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), mDistance).getValue());
        speed.setText(UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), mSpeed).getValue());
        findViewById(R.id.tv_close).setOnClickListener(v -> dismiss());
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