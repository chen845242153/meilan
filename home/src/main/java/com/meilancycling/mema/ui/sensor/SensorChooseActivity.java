package com.meilancycling.mema.ui.sensor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivitySensorChooseBinding;

/**
 * sensor 类型选择
 *
 * @author lion
 */

public class SensorChooseActivity extends BaseActivity implements View.OnClickListener {
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySensorChooseBinding activitySensorChooseBinding = DataBindingUtil.setContentView(this, R.layout.activity_sensor_choose);
        mPosition = getIntent().getIntExtra("position", 0);
       activitySensorChooseBinding.viewBack.setOnClickListener(this);
       activitySensorChooseBinding.tvSportTime.setOnClickListener(this);
       activitySensorChooseBinding.tvTotalTime.setOnClickListener(this);
       activitySensorChooseBinding.tvSportDistance.setOnClickListener(this);
       activitySensorChooseBinding.tvSpeed.setOnClickListener(this);
       activitySensorChooseBinding.tvAverageSpeed.setOnClickListener(this);
       activitySensorChooseBinding.tvMaxSpeed.setOnClickListener(this);
       activitySensorChooseBinding.tvCadence.setOnClickListener(this);
       activitySensorChooseBinding.tvAverageCadence.setOnClickListener(this);
       activitySensorChooseBinding.tvMaxCadence.setOnClickListener(this);
       activitySensorChooseBinding.tvHeartRate.setOnClickListener(this);
       activitySensorChooseBinding.tvAverageHeart.setOnClickListener(this);
       activitySensorChooseBinding.tvMaxHeartRate.setOnClickListener(this);
       activitySensorChooseBinding.tvPower.setOnClickListener(this);
       activitySensorChooseBinding.tvLeftRightBalance.setOnClickListener(this);
       activitySensorChooseBinding.tvAvgLeftRightBalance.setOnClickListener(this);
       activitySensorChooseBinding.tvAveragePower.setOnClickListener(this);
       activitySensorChooseBinding.tvMaximumPower.setOnClickListener(this);
       activitySensorChooseBinding.tvCalories.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("position", mPosition);
        switch (v.getId()) {
            case R.id.view_back:
                finish();
                break;
            case R.id.tv_sport_time:
                intent.putExtra("type", 0);
                break;
            case R.id.tv_total_time:
                intent.putExtra("type", 1);
                break;
            case R.id.tv_sport_distance:
                intent.putExtra("type", 2);
                break;
            case R.id.tv_speed:
                intent.putExtra("type", 3);
                break;
            case R.id.tv_average_speed:
                intent.putExtra("type", 4);
                break;
            case R.id.tv_max_speed:
                intent.putExtra("type", 5);
                break;
            case R.id.tv_cadence:
                intent.putExtra("type", 6);
                break;
            case R.id.tv_average_cadence:
                intent.putExtra("type", 7);
                break;
            case R.id.tv_max_cadence:
                intent.putExtra("type", 8);
                break;
            case R.id.tv_heart_rate:
                intent.putExtra("type", 9);
                break;
            case R.id.tv_average_heart:
                intent.putExtra("type", 10);
                break;
            case R.id.tv_max_heart_rate:
                intent.putExtra("type", 11);
                break;
            case R.id.tv_power:
                intent.putExtra("type", 12);
                break;
            case R.id.tv_left_right_balance:
                intent.putExtra("type", 13);
                break;
            case R.id.tv_avg_left_right_balance:
                intent.putExtra("type", 14);
                break;
            case R.id.tv_average_power:
                intent.putExtra("type", 15);
                break;
            case R.id.tv_maximum_power:
                intent.putExtra("type", 16);
                break;
            case R.id.tv_calories:
                intent.putExtra("type", 17);
                break;
            default:
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}