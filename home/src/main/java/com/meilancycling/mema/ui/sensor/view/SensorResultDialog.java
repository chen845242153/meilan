package com.meilancycling.mema.ui.sensor.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.meilancycling.mema.R;

import java.util.Objects;


/**
 * sensor结果弹窗
 *
 * @author sorelion qq 571135591
 */
public class SensorResultDialog extends Dialog {

    public SensorResultDialog(Context context) {
        super(context, R.style.dialog_style);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sensor_result);
        setCanceledOnTouchOutside(false);
        findViewById(R.id.view_delete).setOnClickListener(v -> {
            dismiss();
            if (null != mSensorResultDialogClickListener) {
                mSensorResultDialogClickListener.deleteClickListener();
            }
        });
        findViewById(R.id.view_save).setOnClickListener(v -> {
            dismiss();
            if (null != mSensorResultDialogClickListener) {
                mSensorResultDialogClickListener.saveClickListener();
            }
        });
        findViewById(R.id.view_continue).setOnClickListener(v -> {
            dismiss();
            if (null != mSensorResultDialogClickListener) {
                mSensorResultDialogClickListener.carryOnClickListener();
            }
        });
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

    public interface SensorResultDialogClickListener {
        /**
         * 放弃运动
         */
        void deleteClickListener();

        /**
         * 保存运动
         */
        void saveClickListener();

        /**
         * 继续运动
         */
        void carryOnClickListener();
    }

    private SensorResultDialogClickListener mSensorResultDialogClickListener;

    public void setSensorResultDialogClickListener(SensorResultDialogClickListener sensorResultDialogClickListener) {
        mSensorResultDialogClickListener = sensorResultDialogClickListener;
    }
}