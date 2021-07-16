package com.meilancycling.mema.ui.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.ble.bean.CommandEntity;
import com.meilancycling.mema.ble.command.BleCommandManager;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.customview.dialog.NoTitleAskDialog;
import com.meilancycling.mema.customview.dialog.SelectUnitDialog;
import com.meilancycling.mema.databinding.ActivitySettingBinding;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.login.LoginActivity;
import com.meilancycling.mema.utils.SPUtils;
import com.meilancycling.mema.utils.StatusAppUtils;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/12/10 11:10 AM
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySettingBinding mActivitySettingBinding;
    private UserInfoEntity mUserInfoEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusAppUtils.setColor(this, ContextCompat.getColor(this, R.color.white));
        mActivitySettingBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        initView();
    }

    private void initView() {
        mActivitySettingBinding.ctvSetting.setData(getString(R.string.set_up), this);
        mUserInfoEntity = getUserInfoEntity();
        if (mUserInfoEntity.getUnit() == Unit.IMPERIAL.value) {
            mActivitySettingBinding.dsvUnit.setItemData(getString(R.string.unit_setting), getString(R.string.imperial));
        } else {
            mActivitySettingBinding.dsvUnit.setItemData(getString(R.string.unit_setting), getString(R.string.metric));
        }
        mActivitySettingBinding.dsvHr.setItemData(getString(R.string.heart_rate_zone), "");
        mActivitySettingBinding.dsvPowerZone.setItemData(getString(R.string.power_range), "");
        mActivitySettingBinding.dsvExerciseGoal.setItemData(getString(R.string.exercise_goal), "");
        mActivitySettingBinding.dsvClearCache.setItemData(getString(R.string.clear_cache), "");
        mActivitySettingBinding.cbv.setBottomView(R.string.drop_out, R.color.delete_red, this);

        mActivitySettingBinding.llShareSetting.setOnClickListener(this);
        mActivitySettingBinding.dsvHr.setOnClickListener(this);
        mActivitySettingBinding.dsvPowerZone.setOnClickListener(this);
        mActivitySettingBinding.dsvExerciseGoal.setOnClickListener(this);
        mActivitySettingBinding.dsvUnit.setOnClickListener(this);
        if (((mUserInfoEntity.getGuideFlag() & 8) >> 3) == Config.NEED_GUIDE) {
            StatusAppUtils.setColor(this, ContextCompat.getColor(this, R.color.guide_bg));
            mActivitySettingBinding.groupGuide.setVisibility(View.VISIBLE);
        }
        mActivitySettingBinding.guideBg.setOnClickListener(v -> {
            StatusAppUtils.setColor(SettingActivity.this, ContextCompat.getColor(SettingActivity.this, R.color.white));
            mActivitySettingBinding.groupGuide.setVisibility(View.GONE);
            int guideFlag = mUserInfoEntity.getGuideFlag();
            mUserInfoEntity.setGuideFlag(guideFlag & 0xF7);
            updateUserInfoEntity(mUserInfoEntity);
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.ll_share_setting:
                startActivity(new Intent(this, ActivityShareActivity.class));
                break;
            case R.id.dsv_hr:
                startActivity(new Intent(this, HeartActivity.class));
                break;
            case R.id.dsv_power_zone:
                startActivity(new Intent(this, PowerActivity.class));
                break;
            case R.id.dsv_exercise_goal:
                startActivity(new Intent(this, WarningActivity.class));
                break;
            case R.id.dsv_unit:
                SelectUnitDialog selectUnitDialog = new SelectUnitDialog(this, Config.unit);
                selectUnitDialog.show();
                selectUnitDialog.setSelectUnitClickListener((unitString, unit) -> {
                    Config.unit = unit;
                    mUserInfoEntity.setUnit(unit);
                    updateUserInfoEntity(mUserInfoEntity);
                    mActivitySettingBinding.dsvUnit.setItemData(getString(R.string.unit_setting), unitString);
                    if (DeviceControllerService.deviceStatus == Device.DEVICE_CONNECTED) {
                        CommandEntity commandEntity = BleCommandManager.getInstance().unitSetting(Config.unit, DeviceControllerService.mDeviceInformation.getUnitType());
                        sendCommandData(commandEntity);
                    }
                });
                break;
            case R.id.tv_title:
                NoTitleAskDialog noTitleAskDialog = new NoTitleAskDialog(this, getString(R.string.logout_confirm), getString(R.string.confirm), getString(R.string.cancel));
                noTitleAskDialog.show();
                noTitleAskDialog.setAskNoTitleDialogListener(new NoTitleAskDialog.AskNoTitleDialogListener() {
                    @Override
                    public void clickLeft() {
                        logout();
                    }

                    @Override
                    public void clickRight() {

                    }
                });
                break;
            default:
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        SPUtils.putLong(this, Config.CURRENT_USER, -1);
        userLogout();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
