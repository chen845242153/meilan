package com.meilancycling.mema.ui.sensor.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;

import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Device;

import java.util.Objects;


/**
 * 设备升级
 *
 * @author sorelion qq 571135591
 */
public class TipDialog extends Dialog implements View.OnClickListener {

    public TipDialog(Context context) {
        super(context, R.style.dialog_style);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip);
        setCanceledOnTouchOutside(false);
        TextView confirm = findViewById(R.id.tv_confirm);
        confirm.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                dismiss();
                break;

            default:
                break;
        }
    }

}

