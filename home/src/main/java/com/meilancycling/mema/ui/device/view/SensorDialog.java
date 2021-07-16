package com.meilancycling.mema.ui.device.view;

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
import com.meilancycling.mema.constant.Device;

import java.util.Objects;

/**
 * 传感器弹窗
 *
 * @author lion qq 571135591
 */
public class SensorDialog extends Dialog implements View.OnClickListener {
    private int mSensorType;
    /**
     * 0 设备
     * 1 传感器
     */
    private int mType;

    public SensorDialog(Context context, int sensorType, int type) {
        super(context, R.style.dialog_style);
        mSensorType = sensorType;
        mType = type;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sensor);
        TextView modifyName = findViewById(R.id.tv_modify_name);
        TextView wheel = findViewById(R.id.tv_set_wheel);
        TextView delete = findViewById(R.id.tv_delete);
        TextView cancel = findViewById(R.id.tv_cancel);
        setCanceledOnTouchOutside(false);
        modifyName.setOnClickListener(this);
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);
        if (mType == 0) {
            if (mSensorType == 1 || mSensorType == 5) {
                wheel.setOnClickListener(this);
            } else {
                wheel.setVisibility(View.GONE);
            }
        } else {

            if (mSensorType == Device.SENSOR_SPEED || mSensorType == Device.SENSOR_SPEED_CADENCE) {
                wheel.setOnClickListener(this);
            } else {
                wheel.setVisibility(View.GONE);
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
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }


    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.tv_modify_name:
                if (mSensorDialogClickListener != null) {
                    mSensorDialogClickListener.sensorModifyName();
                }
                break;
            case R.id.tv_set_wheel:
                if (mSensorDialogClickListener != null) {
                    mSensorDialogClickListener.sensorSetWheel();
                }
                break;
            case R.id.tv_delete:
                if (mSensorDialogClickListener != null) {
                    mSensorDialogClickListener.sensorDelete();
                }
                break;
            default:
        }
    }

    public interface SensorDialogClickListener {
        /**
         * 修改名称
         */
        void sensorModifyName();

        /**
         * 设置轮径值
         */
        void sensorSetWheel();

        /**
         * 删除
         */
        void sensorDelete();
    }

    private SensorDialogClickListener mSensorDialogClickListener;

    public void setSensorDialogClickListener(SensorDialogClickListener sensorDialogClickListener) {
        mSensorDialogClickListener = sensorDialogClickListener;
    }
}

