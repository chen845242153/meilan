package com.meilancycling.mema.ui.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.ble.bean.CommandEntity;
import com.meilancycling.mema.ble.command.BleCommandManager;
import com.meilancycling.mema.databinding.ActivityPowerBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.PowerZoneEntity;
import com.meilancycling.mema.ui.device.view.ZoneDialog;
import com.meilancycling.mema.utils.StatusAppUtils;

/**
 * 功率区间
 *
 * @author lion
 */
public class PowerActivity extends BaseActivity implements View.OnClickListener {
    private ActivityPowerBinding mActivityPowerBinding;
    private PowerZoneEntity mPowerZoneEntity;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusAppUtils.setColor(this, ContextCompat.getColor(this, R.color.white));
        mActivityPowerBinding = DataBindingUtil.setContentView(this, R.layout.activity_power);
        initView();
        mActivityPowerBinding.llHeartBack.setOnClickListener(this);
        mActivityPowerBinding.llPowerReset.setOnClickListener(this);
        mActivityPowerBinding.tvPowerValue.setOnClickListener(this);
        mActivityPowerBinding.tvValue1.setOnClickListener(this);
        mActivityPowerBinding.tvValue2.setOnClickListener(this);
        mActivityPowerBinding.tvValue3.setOnClickListener(this);
        mActivityPowerBinding.tvValue4.setOnClickListener(this);
        mActivityPowerBinding.tvValue5.setOnClickListener(this);
        mActivityPowerBinding.tvValue6.setOnClickListener(this);
        mActivityPowerBinding.tvValue7.setOnClickListener(this);
        mActivityPowerBinding.tvThresholdPower.setText(getString(R.string.threshold_power) + ":");
    }

    private void initView() {
        mPowerZoneEntity = DbUtils.getInstance().queryPowerZoneEntity(getUserId());
        updateUiData();
    }

    @SuppressLint("SetTextI18n")
    private void updateUiData() {
        mActivityPowerBinding.tvPowerValue.setText(String.valueOf(mPowerZoneEntity.getValue()));
        mActivityPowerBinding.tvValue1.setText(getString(R.string.less_equal) + mPowerZoneEntity.getZoneValue1());
        mActivityPowerBinding.tvValue2.setText(String.valueOf(mPowerZoneEntity.getZoneValue2()));
        mActivityPowerBinding.tvValueEnd2.setText(String.valueOf(mPowerZoneEntity.getZoneValue3() - 1));
        mActivityPowerBinding.tvValue3.setText(String.valueOf(mPowerZoneEntity.getZoneValue3()));
        mActivityPowerBinding.tvValueEnd3.setText(String.valueOf(mPowerZoneEntity.getZoneValue4() - 1));
        mActivityPowerBinding.tvValue4.setText(String.valueOf(mPowerZoneEntity.getZoneValue4()));
        mActivityPowerBinding.tvValueEnd4.setText(String.valueOf(mPowerZoneEntity.getZoneValue5() - 1));
        mActivityPowerBinding.tvValue5.setText(String.valueOf(mPowerZoneEntity.getZoneValue5()));
        mActivityPowerBinding.tvValueEnd5.setText(String.valueOf(mPowerZoneEntity.getZoneValue6() - 1));
        mActivityPowerBinding.tvValue6.setText(String.valueOf(mPowerZoneEntity.getZoneValue6()));
        mActivityPowerBinding.tvValueEnd6.setText(String.valueOf(mPowerZoneEntity.getZoneValue7() - 1));
        mActivityPowerBinding.tvValue7.setText(getString(R.string.greater_equal) + mPowerZoneEntity.getZoneValue7());
    }

    private long currentTime;

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_heart_back:
                finish();
                break;
            case R.id.ll_power_reset:
                int diff = 500;
                if (System.currentTimeMillis() - currentTime > diff) {
                    currentTime = System.currentTimeMillis();
                    mPowerZoneEntity = mPowerZoneEntity.resetPower(mPowerZoneEntity);
                    updateUiData();
                }
                break;
            case R.id.tv_power_value:
                int current = Integer.parseInt(mActivityPowerBinding.tvPowerValue.getText().toString().trim());
                int minValue = 100;
                int maxValue = 2000;
                ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.threshold_power));
                zoneDialog.show();
                zoneDialog.setZoneDialogListener(value -> {
                    mPowerZoneEntity = mPowerZoneEntity.modifyValue(mPowerZoneEntity, value);
                    updateUiData();
                    sendData2Device();
                });
                break;
            case R.id.tv_value_1:
                modifyZone1();
                break;
            case R.id.tv_value_2:
                modifyZone2();
                break;
            case R.id.tv_value_3:
                modifyZone3();
                break;
            case R.id.tv_value_4:
                modifyZone4();
                break;
            case R.id.tv_value_5:
                modifyZone5();
                break;
            case R.id.tv_value_6:
                modifyZone6();
                break;
            case R.id.tv_value_7:
                modifyZone7();
                break;
            default:
        }
    }

    private void modifyZone1() {
        int current = Integer.parseInt(mActivityPowerBinding.tvValue1.getText().toString().trim().replace(getString(R.string.less_equal), ""));
        int minValue = 1;
        int maxValue = Integer.parseInt(mActivityPowerBinding.tvValueEnd2.getText().toString().trim()) - 2;
        ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.zone_1));
        zoneDialog.show();
        zoneDialog.setZoneDialogListener(value -> {
            mPowerZoneEntity.setZoneValue1(value);
            mPowerZoneEntity.setZoneValue2(value + 1);
            updateUiData();
            DbUtils.getInstance().updatePowerZoneEntity(mPowerZoneEntity);
            sendData2Device();
        });
    }

    private void modifyZone2() {
        int current = Integer.parseInt(mActivityPowerBinding.tvValue2.getText().toString().trim());
        int minValue = 2;
        int maxValue = Integer.parseInt(mActivityPowerBinding.tvValueEnd2.getText().toString().trim()) - 1;
        ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.zone_2));
        zoneDialog.show();
        zoneDialog.setZoneDialogListener(value -> {
            mPowerZoneEntity.setZoneValue1(value - 1);
            mPowerZoneEntity.setZoneValue2(value);
            updateUiData();
            DbUtils.getInstance().updatePowerZoneEntity(mPowerZoneEntity);
            sendData2Device();
        });
    }

    private void modifyZone3() {
        int current = Integer.parseInt(mActivityPowerBinding.tvValue3.getText().toString().trim());
        int minValue = Integer.parseInt(mActivityPowerBinding.tvValue2.getText().toString().trim()) + 2;
        int maxValue = Integer.parseInt(mActivityPowerBinding.tvValueEnd3.getText().toString().trim()) - 1;
        ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.zone_3));
        zoneDialog.show();
        zoneDialog.setZoneDialogListener(value -> {
            mPowerZoneEntity.setZoneValue3(value);
            updateUiData();
            DbUtils.getInstance().updatePowerZoneEntity(mPowerZoneEntity);
            sendData2Device();
        });
    }

    private void modifyZone4() {
        int current = Integer.parseInt(mActivityPowerBinding.tvValue4.getText().toString().trim());
        int minValue = Integer.parseInt(mActivityPowerBinding.tvValue3.getText().toString().trim()) + 2;
        int maxValue = Integer.parseInt(mActivityPowerBinding.tvValueEnd4.getText().toString().trim()) - 1;
        ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.zone_4));
        zoneDialog.show();
        zoneDialog.setZoneDialogListener(value -> {
            mPowerZoneEntity.setZoneValue4(value);
            updateUiData();
            DbUtils.getInstance().updatePowerZoneEntity(mPowerZoneEntity);
            sendData2Device();
        });
    }

    private void modifyZone5() {
        int current = Integer.parseInt(mActivityPowerBinding.tvValue5.getText().toString().trim());
        int minValue = Integer.parseInt(mActivityPowerBinding.tvValue4.getText().toString().trim()) + 2;
        int maxValue = Integer.parseInt(mActivityPowerBinding.tvValueEnd5.getText().toString().trim()) - 1;
        ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.zone_5));
        zoneDialog.show();
        zoneDialog.setZoneDialogListener(value -> {
            mPowerZoneEntity.setZoneValue5(value);
            updateUiData();
            DbUtils.getInstance().updatePowerZoneEntity(mPowerZoneEntity);
            sendData2Device();
        });
    }

    private void modifyZone6() {
        int current = Integer.parseInt(mActivityPowerBinding.tvValue6.getText().toString().trim());
        int minValue = Integer.parseInt(mActivityPowerBinding.tvValue5.getText().toString().trim()) + 2;
        int maxValue = Integer.parseInt(mActivityPowerBinding.tvValueEnd6.getText().toString().trim()) - 1;
        ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.zone_6));
        zoneDialog.show();
        zoneDialog.setZoneDialogListener(value -> {
            mPowerZoneEntity.setZoneValue6(value);
            updateUiData();
            DbUtils.getInstance().updatePowerZoneEntity(mPowerZoneEntity);
            sendData2Device();
        });
    }

    private void modifyZone7() {
        int current = Integer.parseInt(mActivityPowerBinding.tvValue7.getText().toString().trim().replace(getString(R.string.greater_equal), ""));
        int minValue = Integer.parseInt(mActivityPowerBinding.tvValue6.getText().toString().trim()) + 2;
        int maxValue = mPowerZoneEntity.getValue() * 2;
        ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.zone_7));
        zoneDialog.show();
        zoneDialog.setZoneDialogListener(value -> {
            mPowerZoneEntity.setZoneValue7(value);
            updateUiData();
            DbUtils.getInstance().updatePowerZoneEntity(mPowerZoneEntity);
            sendData2Device();
        });
    }

    private void sendData2Device() {
        CommandEntity commandEntity = BleCommandManager.getInstance().powerSettings(
                mPowerZoneEntity.getValue()
                , 0
                , mPowerZoneEntity.getZoneValue2()
                , mPowerZoneEntity.getZoneValue3()
                , mPowerZoneEntity.getZoneValue4()
                , mPowerZoneEntity.getZoneValue5()
                , mPowerZoneEntity.getZoneValue6()
                , mPowerZoneEntity.getZoneValue7());
        sendCommandData(commandEntity);

    }
}