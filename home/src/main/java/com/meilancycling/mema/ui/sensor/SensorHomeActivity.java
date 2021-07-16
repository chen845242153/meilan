package com.meilancycling.mema.ui.sensor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivitySensorHomeBinding;
import com.meilancycling.mema.service.SensorControllerService;

/**
 * 传感器首页
 *
 * @author lion
 */
public class SensorHomeActivity extends BaseActivity {
    private ActivitySensorHomeBinding mActivitySensorHomeBinding;
    private Fragment fromFragment;
    private Fragment fragCategory;
    private SensorMapFragment mSensorMapFragment;
    private SensorFirstFragment mSensorFirstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, SensorControllerService.class));
        mActivitySensorHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_sensor_home);
        mSensorMapFragment = new SensorMapFragment();
        mSensorFirstFragment = new SensorFirstFragment();
        selectHomeFragment();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    private void switchFragment() {
        if (fromFragment != fragCategory && fragCategory != null) {
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (fromFragment != null) {
                transaction.hide(fromFragment);
            }
            if (!fragCategory.isAdded()) {
                transaction.add(R.id.sensor_fragment, fragCategory, fragCategory.getClass().getName()).commit();
            } else {
                transaction.show(fragCategory).commit();
            }
            fromFragment = fragCategory;
        }
    }

    public void selectHomeFragment() {
        fragCategory = mSensorFirstFragment;
        switchFragment();
    }

    public void selectMapFragment() {
        fragCategory = mSensorMapFragment;
        switchFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, SensorControllerService.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSensorFirstFragment.activityResult(requestCode,resultCode,data);
    }
}