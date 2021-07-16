package com.meilancycling.mema.ui.sensor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.SensorBottomViewBinding;
import com.meilancycling.mema.ui.sensor.SensorBottomModel;

/**
 * @Description: 传感器条目view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class SensorBottomView extends LinearLayout implements View.OnClickListener {
    private SensorBottomViewBinding mSensorBottomViewBinding;
    private SensorBottomModel mSensorBottomModel;

    public SensorBottomView(Context context) {
        super(context);
        init();
    }

    public SensorBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SensorBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mSensorBottomViewBinding = DataBindingUtil.inflate(inflater, R.layout.sensor_bottom_view, this, false);
            this.addView(mSensorBottomViewBinding.getRoot());
        }
        mSensorBottomViewBinding.viewBack.setOnClickListener(this);
        mSensorBottomViewBinding.tvStart.setOnClickListener(this);
        mSensorBottomViewBinding.viewSportType.setOnClickListener(this);
        mSensorBottomViewBinding.tvLapNumber.setOnClickListener(this);
        mSensorBottomViewBinding.ivSensorPause.setOnClickListener(this);
        mSensorBottomViewBinding.ivSensorStart.setOnClickListener(this);
        mSensorBottomViewBinding.ivStop.setOnClickListener(this);
        mSensorBottomViewBinding.ivSensorLock.setOnClickListener(this);
        mSensorBottomViewBinding.suv.setSlideUnlockViewCallback(() -> {
            mSensorBottomModel.setLock(false);
            mSensorBottomViewClickListener.clickLock(false);
        });

    }

    public void bindData(SensorBottomModel sensorBottomModel) {
        mSensorBottomModel = sensorBottomModel;
        mSensorBottomViewBinding.setModel(sensorBottomModel);
    }

    public SensorBottomModel getData() {
        return mSensorBottomModel;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_back:
                mSensorBottomViewClickListener.clickBack();
                break;
            case R.id.tv_start:
                mSensorBottomModel.setSportStatus(1);
                mSensorBottomViewClickListener.clickStart();
                break;
            case R.id.iv_sensor_start:
                mSensorBottomModel.setSportStatus(1);
                mSensorBottomViewClickListener.clickCarryOn();
                break;
            case R.id.view_sport_type:
                if (mSensorBottomModel.getSportType() == 0) {
                    mSensorBottomModel.setSportType(1);
                } else {
                    mSensorBottomModel.setSportType(0);
                }
                mSensorBottomViewClickListener.clickChangeType(mSensorBottomModel.getSportType());
                break;
            case R.id.tv_lap_number:
                mSensorBottomModel.setLapNumber(mSensorBottomModel.getLapNumber() + 1);
                mSensorBottomViewClickListener.clickLapNumber(mSensorBottomModel.getLapNumber());
                break;
            case R.id.iv_sensor_pause:
                mSensorBottomModel.setSportStatus(2);
                mSensorBottomViewClickListener.clickPause();
                break;
            case R.id.iv_stop:
                mSensorBottomViewClickListener.clickStop();
                break;
            case R.id.iv_sensor_lock:
                mSensorBottomModel.setLock(true);
                mSensorBottomViewClickListener.clickLock(true);
                break;
        }
    }

    public interface SensorBottomViewClickListener {
        void clickBack();

        void clickStart();

        void clickCarryOn();

        void clickChangeType(int type);

        void clickLapNumber(int number);

        void clickPause();

        void clickStop();

        void clickLock(boolean status);

    }

    private SensorBottomViewClickListener mSensorBottomViewClickListener;

    public void setSensorBottomViewClickListener(SensorBottomViewClickListener sensorBottomViewClickListener) {
        mSensorBottomViewClickListener = sensorBottomViewClickListener;
    }
}
