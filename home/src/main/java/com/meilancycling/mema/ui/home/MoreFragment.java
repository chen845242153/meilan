package com.meilancycling.mema.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.ble.bean.CommandEntity;
import com.meilancycling.mema.ble.command.BleCommandManager;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.customview.dialog.SelectUnitDialog;
import com.meilancycling.mema.databinding.FragmentMoreBinding;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.record.AddRecordActivity;
import com.meilancycling.mema.ui.record.CompetitionActivity;
import com.meilancycling.mema.ui.record.FavoritesActivity;
import com.meilancycling.mema.ui.setting.AboutUsActivity;
import com.meilancycling.mema.ui.setting.AccountSecurityActivity;
import com.meilancycling.mema.ui.setting.ActivityShareActivity;
import com.meilancycling.mema.ui.setting.HeartActivity;
import com.meilancycling.mema.ui.setting.HelpCenterActivity;
import com.meilancycling.mema.ui.setting.PowerActivity;
import com.meilancycling.mema.ui.setting.WarningActivity;
import com.meilancycling.mema.ui.user.PersonalInfoActivity;
import com.meilancycling.mema.utils.GlideUtils;

/**
 * @Description: 设置
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class MoreFragment extends BaseFragment implements View.OnClickListener {
    FragmentMoreBinding mFragmentMoreBinding;
    private HomeActivity activity;
    private int flag = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentMoreBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        activity = (HomeActivity) getActivity();
        initView();
        return mFragmentMoreBinding.getRoot();
    }

    private void initView() {
        mFragmentMoreBinding.mivHrm.setDataAndListener(ContextCompat.getDrawable(mActivity, R.drawable.home_hrm), R.string.heart_rate_zone);
        mFragmentMoreBinding.mivPower.setDataAndListener(ContextCompat.getDrawable(mActivity, R.drawable.home_power), R.string.power_range);
        mFragmentMoreBinding.mivTarget.setDataAndListener(ContextCompat.getDrawable(mActivity, R.drawable.home_tager), R.string.exercise_goal);
        mFragmentMoreBinding.mivAccount.setDataAndListener(ContextCompat.getDrawable(mActivity, R.drawable.home_account), R.string.home_account);
        mFragmentMoreBinding.mivHelp.setDataAndListener(ContextCompat.getDrawable(mActivity, R.drawable.home_problem), R.string.help_center);
        mFragmentMoreBinding.mivAboutUs.setDataAndListener(ContextCompat.getDrawable(mActivity, R.drawable.home_about), R.string.about_us);

        mFragmentMoreBinding.ivFavorites.setOnClickListener(this);
        mFragmentMoreBinding.ivCompetition.setOnClickListener(this);
        mFragmentMoreBinding.ivAddActivity.setOnClickListener(this);
        mFragmentMoreBinding.tvFavorites.setOnClickListener(this);
        mFragmentMoreBinding.tvCompetition.setOnClickListener(this);
        mFragmentMoreBinding.tvAddActivity.setOnClickListener(this);

        mFragmentMoreBinding.llUnit.setOnClickListener(this);
        mFragmentMoreBinding.llShare.setOnClickListener(this);

        mFragmentMoreBinding.mivHrm.setOnClickListener(this);
        mFragmentMoreBinding.mivPower.setOnClickListener(this);
        mFragmentMoreBinding.mivTarget.setOnClickListener(this);
        mFragmentMoreBinding.mivAccount.setOnClickListener(this);
        mFragmentMoreBinding.mivHelp.setOnClickListener(this);
        mFragmentMoreBinding.mivAboutUs.setOnClickListener(this);
        mFragmentMoreBinding.ivMoreAvatar.setOnClickListener(this);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        UserInfoEntity userInfoEntity = mActivity.getUserInfoEntity();
        if (userInfoEntity != null) {
            GlideUtils.loadCircleImage(userInfoEntity.getHeadUrl(), mActivity, mFragmentMoreBinding.ivMoreAvatar);
            mFragmentMoreBinding.tvMoreNickName.setText(userInfoEntity.getNickname());
            mFragmentMoreBinding.tvMoreId.setText("ID:" + userInfoEntity.getUserCode());
            switch (userInfoEntity.getRegType()) {
                case Config.TYPE_PHONE:
                    mFragmentMoreBinding.ivLoginType.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.more_phone));
                    break;
                case Config.TYPE_MAILBOX:
                    mFragmentMoreBinding.ivLoginType.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.more_mail));
                    break;
                case Config.TYPE_WX:
                    mFragmentMoreBinding.ivLoginType.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.more_wx));
                    break;
                case Config.TYPE_QQ:
                    mFragmentMoreBinding.ivLoginType.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.more_qq));
                    break;
                case Config.TYPE_GOOGLE:
                    mFragmentMoreBinding.ivLoginType.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.more_google));
                    break;
                case Config.TYPE_FACEBOOK:
                    mFragmentMoreBinding.ivLoginType.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.more_facebook));
                    break;
                default:
            }
            if (userInfoEntity.getUnit() == Unit.IMPERIAL.value) {
                mFragmentMoreBinding.tvUnitValue.setText(getString(R.string.imperial));
            } else {
                mFragmentMoreBinding.tvUnitValue.setText(getString(R.string.metric));
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_more_avatar:
                startActivity(new Intent(mActivity, PersonalInfoActivity.class));
                break;
            case R.id.tv_favorites:
            case R.id.iv_favorites:
                startActivity(new Intent(mActivity, FavoritesActivity.class));
                break;
            case R.id.tv_competition:
            case R.id.iv_competition:
                startActivity(new Intent(mActivity, CompetitionActivity.class));
                break;
            case R.id.tv_add_activity:
            case R.id.iv_add_activity:
                startActivity(new Intent(mActivity, AddRecordActivity.class));
                break;
            case R.id.ll_unit:
                SelectUnitDialog selectUnitDialog = new SelectUnitDialog(mActivity, Config.unit);
                selectUnitDialog.show();
                selectUnitDialog.setSelectUnitClickListener((unitString, unit) -> {
                    Config.unit = unit;
                    mActivity.getUserInfoEntity().setUnit(unit);
                    mActivity.updateUserInfoEntity(mActivity.getUserInfoEntity());
                    if (Config.unit == Unit.IMPERIAL.value) {
                        mFragmentMoreBinding.tvUnitValue.setText(getString(R.string.imperial));
                    } else {
                        mFragmentMoreBinding.tvUnitValue.setText(getString(R.string.metric));
                    }
                    activity.notifyAdapter();
                    if (DeviceControllerService.deviceStatus == Device.DEVICE_CONNECTED) {
                        CommandEntity commandEntity = BleCommandManager.getInstance().unitSetting(Config.unit, DeviceControllerService.mDeviceInformation.getUnitType());
                        Intent intent = new Intent(BroadcastConstant.ACTION_SEND_COMMAND);
                        intent.putExtra(BroadcastConstant.BROADCAST_KEY, commandEntity);
                        mActivity.sendBroadcast(intent);
                    }
                });
                break;
            case R.id.ll_share:
                startActivity(new Intent(mActivity, ActivityShareActivity.class));
                break;
            case R.id.miv_hrm:
                startActivity(new Intent(mActivity, HeartActivity.class));
                break;
            case R.id.miv_power:
                startActivity(new Intent(mActivity, PowerActivity.class));
                break;
            case R.id.miv_target:
                startActivity(new Intent(mActivity, WarningActivity.class));
                break;
            case R.id.miv_account:
                startActivity(new Intent(mActivity, AccountSecurityActivity.class));
                break;
            case R.id.miv_help:
                startActivity(new Intent(mActivity, HelpCenterActivity.class));
                break;
            case R.id.miv_about_us:
                startActivity(new Intent(mActivity, AboutUsActivity.class));
                break;
            default:
        }
    }
}
