package com.meilancycling.mema.ui.device;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.utils.StatusAppUtils;

public class SelectDeviceActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusAppUtils.setTranslucentForImageView(this, 0, null);
        setContentView(R.layout.activity_selecr_device);
        findViewById(R.id.view_back).setOnClickListener(this);
        findViewById(R.id.iv_m1).setOnClickListener(this);
        findViewById(R.id.iv_m2).setOnClickListener(this);
        findViewById(R.id.iv_m4).setOnClickListener(this);
        findViewById(R.id.iv_m5).setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_back:
                finish();
                break;
            case R.id.iv_m1:
                Intent intent = new Intent(this, SearchDeviceActivity.class);
                intent.putExtra("name", Device.NAME_M1);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_m2:
                intent = new Intent(this, SearchDeviceActivity.class);
                intent.putExtra("name", Device.NAME_M2);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_m4:
                intent = new Intent(this, SearchDeviceActivity.class);
                intent.putExtra("name", Device.NAME_M4);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_m5:
                intent = new Intent(this, SearchDeviceActivity.class);
                intent.putExtra("name", Device.NAME_M5);
                startActivity(intent);
                finish();
                break;
        }
    }
}