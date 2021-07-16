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
import com.meilancycling.mema.databinding.ActivityHeartBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.HeartZoneEntity;
import com.meilancycling.mema.ui.device.view.ZoneDialog;
import com.meilancycling.mema.utils.StatusAppUtils;
/**
 * 心率区间
 *
 * @author lion
 */
public class HeartActivity extends BaseActivity implements View.OnClickListener {
    private ActivityHeartBinding mActivityHeartBinding;
    private HeartZoneEntity mHeartZoneEntity;
    /**
     * 0 最大心率
     * 1 储备心率
     */
    private final int mMaxHeart = 0;
    private final int mReserveHeart = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusAppUtils.setColor(this, ContextCompat.getColor(this, R.color.white));
        mActivityHeartBinding = DataBindingUtil.setContentView(this, R.layout.activity_heart);
        initView();
        mActivityHeartBinding.llHeartBack.setOnClickListener(this);
        mActivityHeartBinding.llHeartReset.setOnClickListener(this);
        mActivityHeartBinding.tvHeartMax.setOnClickListener(this);
        mActivityHeartBinding.tvHeartReserve.setOnClickListener(this);
        mActivityHeartBinding.tvHeartMaxValue.setOnClickListener(this);
        mActivityHeartBinding.tvHeartRestingValue.setOnClickListener(this);
        mActivityHeartBinding.tvValue1.setOnClickListener(this);
        mActivityHeartBinding.tvValue2.setOnClickListener(this);
        mActivityHeartBinding.tvValue3.setOnClickListener(this);
        mActivityHeartBinding.tvValue4.setOnClickListener(this);
        mActivityHeartBinding.tvValue5.setOnClickListener(this);
    }

    private void initView() {
        mHeartZoneEntity = DbUtils.getInstance().queryHeartZoneEntity(getUserId());
        updateUiData();
    }

    @SuppressLint("SetTextI18n")
    private void updateUiData() {
        if (mHeartZoneEntity.getSelect() == mMaxHeart) {
            mActivityHeartBinding.tvResting.setVisibility(View.GONE);
            mActivityHeartBinding.tvHeartRestingValue.setVisibility(View.GONE);
            mActivityHeartBinding.tvRestingUnit.setVisibility(View.GONE);
            mActivityHeartBinding.tvHeartMax.setTextColor(ContextCompat.getColor(this, R.color.black_3));
            mActivityHeartBinding.tvHeartReserve.setTextColor(ContextCompat.getColor(this, R.color.black_9));
            mActivityHeartBinding.viewMax.setVisibility(View.VISIBLE);
            mActivityHeartBinding.viewReserve.setVisibility(View.GONE);
            mActivityHeartBinding.tvHeartMaxValue.setText(String.valueOf(mHeartZoneEntity.getMaxValue()));
            mActivityHeartBinding.tvValue1.setText(getString(R.string.less_equal) + mHeartZoneEntity.getMaxZoneValue1());
            mActivityHeartBinding.tvValue2.setText(String.valueOf(mHeartZoneEntity.getMaxZoneValue2()));
            mActivityHeartBinding.tvValueEnd2.setText(String.valueOf(mHeartZoneEntity.getMaxZoneValue3() - 1));
            mActivityHeartBinding.tvValue3.setText(String.valueOf(mHeartZoneEntity.getMaxZoneValue3()));
            mActivityHeartBinding.tvValueEnd3.setText(String.valueOf(mHeartZoneEntity.getMaxZoneValue4() - 1));
            mActivityHeartBinding.tvValue4.setText(String.valueOf(mHeartZoneEntity.getMaxZoneValue4()));
            mActivityHeartBinding.tvValueEnd4.setText(String.valueOf(mHeartZoneEntity.getMaxZoneValue5() - 1));
            mActivityHeartBinding.tvValue5.setText(getString(R.string.greater_equal) + mHeartZoneEntity.getMaxZoneValue5());
        } else {
            mActivityHeartBinding.tvResting.setVisibility(View.VISIBLE);
            mActivityHeartBinding.tvHeartRestingValue.setVisibility(View.VISIBLE);
            mActivityHeartBinding.tvRestingUnit.setVisibility(View.VISIBLE);
            mActivityHeartBinding.tvHeartMax.setTextColor(ContextCompat.getColor(this, R.color.black_9));
            mActivityHeartBinding.tvHeartReserve.setTextColor(ContextCompat.getColor(this, R.color.black_3));
            mActivityHeartBinding.viewMax.setVisibility(View.GONE);
            mActivityHeartBinding.viewReserve.setVisibility(View.VISIBLE);
            mActivityHeartBinding.tvHeartMaxValue.setText(String.valueOf(mHeartZoneEntity.getReserveMaxValue()));
            mActivityHeartBinding.tvHeartRestingValue.setText(String.valueOf(mHeartZoneEntity.getReserveValue()));
            mActivityHeartBinding.tvValue1.setText(getString(R.string.less_equal) + mHeartZoneEntity.getReserveZoneValue1());
            mActivityHeartBinding.tvValue2.setText(String.valueOf(mHeartZoneEntity.getReserveZoneValue2()));
            mActivityHeartBinding.tvValueEnd2.setText(String.valueOf(mHeartZoneEntity.getReserveZoneValue3() - 1));
            mActivityHeartBinding.tvValue3.setText(String.valueOf(mHeartZoneEntity.getReserveZoneValue3()));
            mActivityHeartBinding.tvValueEnd3.setText(String.valueOf(mHeartZoneEntity.getReserveZoneValue4() - 1));
            mActivityHeartBinding.tvValue4.setText(String.valueOf(mHeartZoneEntity.getReserveZoneValue4()));
            mActivityHeartBinding.tvValueEnd4.setText(String.valueOf(mHeartZoneEntity.getReserveZoneValue5() - 1));
            mActivityHeartBinding.tvValue5.setText(getString(R.string.greater_equal) + mHeartZoneEntity.getReserveZoneValue5());
        }
    }

    private long maxCurrentTime;
    private long reserveCurrentTime;

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_heart_back:
                finish();
                break;
            case R.id.ll_heart_reset:
                int diff = 500;
                if (mHeartZoneEntity.getSelect() == mReserveHeart) {
                    if (System.currentTimeMillis() - reserveCurrentTime > diff) {
                        reserveCurrentTime = System.currentTimeMillis();
                        mHeartZoneEntity = mHeartZoneEntity.resetHeartRate(mHeartZoneEntity);
                        updateUiData();
                        sendData2Device();
                    }
                } else {
                    if (System.currentTimeMillis() - maxCurrentTime > diff) {
                        maxCurrentTime = System.currentTimeMillis();
                        mHeartZoneEntity = mHeartZoneEntity.resetHeartRate(mHeartZoneEntity);
                        updateUiData();
                        sendData2Device();
                    }
                }
                break;
            case R.id.tv_heart_max:
                mHeartZoneEntity.setSelect(mMaxHeart);
                DbUtils.getInstance().updateHeartZoneEntity(mHeartZoneEntity);
                updateUiData();
                sendData2Device();
                break;
            case R.id.tv_heart_reserve:
                mHeartZoneEntity.setSelect(mReserveHeart);
                updateUiData();
                DbUtils.getInstance().updateHeartZoneEntity(mHeartZoneEntity);
                sendData2Device();
                break;
            case R.id.tv_heart_max_value:
                int current;
                int minValue;
                int maxValue;
                if (mHeartZoneEntity.getSelect() == mMaxHeart) {
                    current = mHeartZoneEntity.getMaxValue();
                    minValue = 50;
                } else {
                    current = mHeartZoneEntity.getReserveMaxValue();
                    minValue = Math.max(50, mHeartZoneEntity.getReserveValue() + 20);
                }
                maxValue = 255;
                ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.maximum_heart_rate));
                zoneDialog.show();
                zoneDialog.setZoneDialogListener(value -> {
                    mHeartZoneEntity = mHeartZoneEntity.modifyValue(mHeartZoneEntity, value, mHeartZoneEntity.getReserveValue());
                    updateUiData();
                    sendData2Device();
                });
                break;
            case R.id.tv_heart_resting_value:
                current = mHeartZoneEntity.getReserveValue();
                minValue = 30;
                maxValue = Math.min(235, mHeartZoneEntity.getReserveMaxValue() - 20);
                zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.heart_reserve));
                zoneDialog.show();
                zoneDialog.setZoneDialogListener(value -> {
                    mHeartZoneEntity = mHeartZoneEntity.modifyValue(mHeartZoneEntity, mHeartZoneEntity.getReserveMaxValue(), value);
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
            default:
        }
    }

    private void modifyZone1() {
        int current = Integer.parseInt(mActivityHeartBinding.tvValue1.getText().toString().trim().replace(getString(R.string.less_equal), ""));
        int minValue = 1;
        int maxValue = Integer.parseInt(mActivityHeartBinding.tvValueEnd2.getText().toString().trim()) - 2;
        ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.zone_1));
        zoneDialog.show();
        zoneDialog.setZoneDialogListener(value -> {
            if (mHeartZoneEntity.getSelect() == mMaxHeart) {
                mHeartZoneEntity.setMaxZoneValue1(value);
                mHeartZoneEntity.setMaxZoneValue2(value + 1);
            } else {
                mHeartZoneEntity.setReserveZoneValue1(value);
                mHeartZoneEntity.setReserveZoneValue2(value + 1);
            }
            updateUiData();
            DbUtils.getInstance().updateHeartZoneEntity(mHeartZoneEntity);
            sendData2Device();
        });
    }

    private void modifyZone2() {
        int current = Integer.parseInt(mActivityHeartBinding.tvValue2.getText().toString().trim());
        int minValue = 2;
        int maxValue = Integer.parseInt(mActivityHeartBinding.tvValueEnd2.getText().toString().trim()) - 1;
        ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.zone_2));
        zoneDialog.show();
        zoneDialog.setZoneDialogListener(value -> {
            if (mHeartZoneEntity.getSelect() == mMaxHeart) {
                mHeartZoneEntity.setMaxZoneValue1(value - 1);
                mHeartZoneEntity.setMaxZoneValue2(value);
            } else {
                mHeartZoneEntity.setReserveZoneValue1(value - 1);
                mHeartZoneEntity.setReserveZoneValue2(value);
            }
            updateUiData();
            DbUtils.getInstance().updateHeartZoneEntity(mHeartZoneEntity);
            sendData2Device();
        });
    }

    private void modifyZone3() {
        int current = Integer.parseInt(mActivityHeartBinding.tvValue3.getText().toString().trim());
        int minValue = Integer.parseInt(mActivityHeartBinding.tvValue2.getText().toString().trim()) + 2;
        int maxValue = Integer.parseInt(mActivityHeartBinding.tvValueEnd3.getText().toString().trim()) - 1;
        ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.zone_3));
        zoneDialog.show();
        zoneDialog.setZoneDialogListener(value -> {
            if (mHeartZoneEntity.getSelect() == mMaxHeart) {
                mHeartZoneEntity.setMaxZoneValue3(value);
            } else {
                mHeartZoneEntity.setReserveZoneValue3(value);
            }
            updateUiData();
            DbUtils.getInstance().updateHeartZoneEntity(mHeartZoneEntity);
            sendData2Device();
        });
    }

    private void modifyZone4() {
        int current = Integer.parseInt(mActivityHeartBinding.tvValue4.getText().toString().trim());
        int minValue = Integer.parseInt(mActivityHeartBinding.tvValue3.getText().toString().trim()) + 2;
        int maxValue = Integer.parseInt(mActivityHeartBinding.tvValueEnd4.getText().toString().trim()) - 1;
        ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.zone_4));
        zoneDialog.show();
        zoneDialog.setZoneDialogListener(value -> {
            if (mHeartZoneEntity.getSelect() == mMaxHeart) {
                mHeartZoneEntity.setMaxZoneValue4(value);
            } else {
                mHeartZoneEntity.setReserveZoneValue4(value);
            }
            updateUiData();
            DbUtils.getInstance().updateHeartZoneEntity(mHeartZoneEntity);
            sendData2Device();
        });
    }

    private void modifyZone5() {
        int current = Integer.parseInt(mActivityHeartBinding.tvValue5.getText().toString().trim().replace(getString(R.string.greater_equal), ""));
        int minValue = Integer.parseInt(mActivityHeartBinding.tvValue4.getText().toString().trim()) + 2;
        int maxValue;
        if (mHeartZoneEntity.getSelect() == mMaxHeart) {
            maxValue = mHeartZoneEntity.getMaxValue();
        } else {
            maxValue = mHeartZoneEntity.getReserveMaxValue();
        }
        ZoneDialog zoneDialog = new ZoneDialog(this, current, maxValue, minValue, getString(R.string.zone_5));
        zoneDialog.show();
        zoneDialog.setZoneDialogListener(value -> {
            if (mHeartZoneEntity.getSelect() == mMaxHeart) {
                mHeartZoneEntity.setMaxZoneValue5(value);
            } else {
                mHeartZoneEntity.setReserveZoneValue5(value);
            }
            updateUiData();
            DbUtils.getInstance().updateHeartZoneEntity(mHeartZoneEntity);
            sendData2Device();
        });
    }

    private void sendData2Device() {
        if (mHeartZoneEntity.getSelect() == mMaxHeart) {
            CommandEntity commandEntity = BleCommandManager.getInstance().hrSetting(2
                    , mHeartZoneEntity.getSelect()
                    , mHeartZoneEntity.getMaxValue()
                    , mHeartZoneEntity.getReserveValue()
                    , 0
                    , mHeartZoneEntity.getMaxZoneValue1()
                    , mHeartZoneEntity.getMaxZoneValue3() - 1
                    , mHeartZoneEntity.getMaxZoneValue4() - 1
                    , mHeartZoneEntity.getMaxZoneValue5() - 1);
            sendCommandData(commandEntity);
        } else {
            CommandEntity commandEntity = BleCommandManager.getInstance().hrSetting(2
                    , mHeartZoneEntity.getSelect()
                    , mHeartZoneEntity.getReserveMaxValue()
                    , mHeartZoneEntity.getReserveValue()
                    , 0
                    , mHeartZoneEntity.getReserveZoneValue1()
                    , mHeartZoneEntity.getReserveZoneValue3() - 1
                    , mHeartZoneEntity.getReserveZoneValue4() - 1
                    , mHeartZoneEntity.getReserveZoneValue5() - 1);
            sendCommandData(commandEntity);
        }
    }
}