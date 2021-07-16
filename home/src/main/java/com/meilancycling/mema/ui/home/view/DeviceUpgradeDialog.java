package com.meilancycling.mema.ui.home.view;

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
public class DeviceUpgradeDialog extends Dialog implements View.OnClickListener {
    private String mProduct;
    private int mPower;

    public DeviceUpgradeDialog(Context context, String product, int power) {
        super(context, R.style.dialog_style);
        mProduct = product;
        mPower = power;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_device_upgrade);
        setCanceledOnTouchOutside(false);
        ImageView device = findViewById(R.id.iv_device);
        ImageView deviceBg = findViewById(R.id.iv_bottom_bg);
        TextView tvTips = findViewById(R.id.tv_tips);
        int lowPower = 20;
        if (mPower > lowPower) {
            BatteryManager manager = (BatteryManager) getContext().getSystemService(Context.BATTERY_SERVICE);
            if (manager != null) {
                int currentLevel = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                if (currentLevel <= lowPower) {
                    Group group = findViewById(R.id.group_low_batter);
                    group.setVisibility(View.VISIBLE);
                    tvTips.setText(R.string.low_batter_phone_tips);
                    TextView confirm = findViewById(R.id.tv_confirm);
                    confirm.setOnClickListener(this);
                } else {
                    TextView tvUpgrade = findViewById(R.id.tv_upgrade);
                    tvUpgrade.setVisibility(View.VISIBLE);
                    tvUpgrade.setOnClickListener(this);
                }
            } else {
                TextView tvUpgrade = findViewById(R.id.tv_upgrade);
                tvUpgrade.setVisibility(View.VISIBLE);
                tvUpgrade.setOnClickListener(this);
            }
        } else {
            Group group = findViewById(R.id.group_low_batter);
            group.setVisibility(View.VISIBLE);
            tvTips.setText(R.string.low_batter_tips);
            TextView confirm = findViewById(R.id.tv_confirm);
            confirm.setOnClickListener(this);
        }
        switch (mProduct) {
            case Device.PRODUCT_NO_M1:
                device.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.device_m1));
                deviceBg.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.upgrade_m1));
                break;
            case Device.PRODUCT_NO_M2:
                device.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.device_m2));
                deviceBg.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.upgrade_m2));
                break;
            case Device.PRODUCT_NO_M4:
                device.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.device_m4));
                deviceBg.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.upgrade_m4));
                break;
            default:
        }
        View close = findViewById(R.id.view_close);
        close.setOnClickListener(this);
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
            case R.id.view_close:
            case R.id.tv_confirm:
                dismiss();
                break;
            case R.id.tv_upgrade:
                dismiss();
                if (mDeviceUpgradeCallback != null) {
                    mDeviceUpgradeCallback.clickUpgrade();
                }
                break;
            default:
        }
    }

    public interface DeviceUpgradeCallback {
        /**
         * 点击升级
         */
        void clickUpgrade();
    }

    private DeviceUpgradeCallback mDeviceUpgradeCallback;

    public void setDeviceUpgradeCallback(DeviceUpgradeCallback deviceUpgradeCallback) {
        mDeviceUpgradeCallback = deviceUpgradeCallback;
    }
}

