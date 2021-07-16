package com.meilancycling.mema.ui.device.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.ble.bean.CommandEntity;
import com.meilancycling.mema.ble.bean.DeviceInformation;
import com.meilancycling.mema.ble.bean.DeviceSettingBean;
import com.meilancycling.mema.ble.command.BleCommandManager;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.constant.Unit;

import com.meilancycling.mema.databinding.FragmentSettingBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.db.LanguageEntity;
import com.meilancycling.mema.db.LanguageSelect;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.device.DeviceSettingActivity;
import com.meilancycling.mema.ui.device.SensorListActivity;
import com.meilancycling.mema.ui.device.view.AltitudeDialog;
import com.meilancycling.mema.ui.device.view.DistanceDialog;
import com.meilancycling.mema.ui.device.view.SelectLanguageDialog;
import com.meilancycling.mema.ui.home.view.DeviceUpgradeDialog;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener {
    FragmentSettingBinding mFragmentSettingBinding;
    private DeviceSettingActivity mDeviceSettingActivity;
    private List<String> languageNameList;
    private List<String> languageNumList;
    private String currentLanguage;
    private DeviceInformationEntity device;
    private UserInfoEntity userInfoEntity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentSettingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        mDeviceSettingActivity = (DeviceSettingActivity) getActivity();
        languageNameList = new ArrayList<>();
        languageNumList = new ArrayList<>();
        initListener();
        setSettingData();
        updatePower(DeviceControllerService.currentPower);
        userInfoEntity = mActivity.getUserInfoEntity();
        if (((userInfoEntity.getGuideFlag() & 16) >> 4) == Config.NEED_GUIDE) {
            switch (DeviceControllerService.currentDevice.getProductNo()) {
                case Device.PRODUCT_NO_M1:
                    mFragmentSettingBinding.groupM1.setVisibility(View.VISIBLE);
                    break;
                case Device.PRODUCT_NO_M2:
                    mFragmentSettingBinding.groupM2.setVisibility(View.VISIBLE);
                    break;
                case Device.PRODUCT_NO_M4:
                    mFragmentSettingBinding.groupM3.setVisibility(View.VISIBLE);
                    break;
                default:
            }
            mDeviceSettingActivity.showGuide();
        }
        mFragmentSettingBinding.guideBgM1.setOnClickListener(this);
        mFragmentSettingBinding.guideBgM2.setOnClickListener(this);
        mFragmentSettingBinding.guideBgM3.setOnClickListener(this);
        return mFragmentSettingBinding.getRoot();
    }

    private void initListener() {
        mFragmentSettingBinding.dsLanguage.setOnClickListener(this);
        mFragmentSettingBinding.tbTimeSetting.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                DeviceControllerService.mDeviceSetUp.setTimeType(1);
            } else {
                DeviceControllerService.mDeviceSetUp.setTimeType(0);
            }
            CommandEntity commandEntity = BleCommandManager.getInstance().timeFormat(DeviceControllerService.mDeviceSetUp.getTimeType());
            mDeviceSettingActivity.sendCommandData(commandEntity);
        });

        mFragmentSettingBinding.tbDeviceSettingSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                DeviceControllerService.mDeviceSetUp.setSound(1);
            } else {
                DeviceControllerService.mDeviceSetUp.setSound(0);
            }
            CommandEntity commandEntity = BleCommandManager.getInstance().soundSettings(DeviceControllerService.mDeviceSetUp.getSound());
            mDeviceSettingActivity.sendCommandData(commandEntity);
        });

        mFragmentSettingBinding.dsOdo.setOnClickListener(this);
        mFragmentSettingBinding.dsAltitude.setOnClickListener(this);
        mFragmentSettingBinding.tbDeleteRecord.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                device.setDeleteSwitch(Device.SWITCH_OPEN);
            } else {
                device.setDeleteSwitch(Device.SWITCH_CLOSE);
            }
            DbUtils.getInstance().updateDevice(device);
        });
        mFragmentSettingBinding.dsSensor.setOnClickListener(this);
        mFragmentSettingBinding.tvDeleteDevice.setOnClickListener(this);
        mFragmentSettingBinding.llDeviceUpgrade.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    public void setSettingData() {
        if (mFragmentSettingBinding != null) {
            DeviceSettingBean deviceSettingBean = DeviceControllerService.mDeviceSetUp;
            mFragmentSettingBinding.slItem1.setVisibility(View.VISIBLE);
            mFragmentSettingBinding.slItem2.setVisibility(View.VISIBLE);
            mFragmentSettingBinding.slItem5.setVisibility(View.VISIBLE);
            mFragmentSettingBinding.slItem4.setVisibility(View.VISIBLE);
            showImage(DeviceControllerService.currentDevice);
            mFragmentSettingBinding.tvVersion.setText(getString(R.string.version) + DeviceControllerService.software);
            if (DeviceControllerService.deviceStatus == Device.DEVICE_CONNECTED && deviceSettingBean != null) {
                //语言
                showLanguage(DeviceControllerService.mDeviceInformation, deviceSettingBean);
                //时间格式
                mFragmentSettingBinding.tbTimeSetting.setChecked(deviceSettingBean.getTimeType() == 1);
                //声音
                mFragmentSettingBinding.tbDeviceSettingSound.setChecked(deviceSettingBean.getSound() != 0);
                //odo
                int odoValue = UnitConversionUtil.getUnitConversionUtil().distance2Int(deviceSettingBean.getOdo());
                if (Config.unit == Unit.METRIC.value) {
                    mFragmentSettingBinding.dsOdo.setItemData(getString(R.string.odo), odoValue + getString(R.string.unit_km));
                } else {
                    mFragmentSettingBinding.dsOdo.setItemData(getString(R.string.odo), odoValue + getString(R.string.unit_mile));
                }
                //海拔
                UnitBean altitudeBean = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(mActivity, (double) deviceSettingBean.getAltitude() / 100);
                mFragmentSettingBinding.dsAltitude.setItemData(getString(R.string.altitude), altitudeBean.getValue() + altitudeBean.getUnit());
                //传感器
                mFragmentSettingBinding.dsSensor.setItemData(getString(R.string.sensor), "");
                //信息提醒
                if (deviceSettingBean.getInformationSwitch() == Device.SWITCH_OPEN) {
                    mFragmentSettingBinding.tbInformationReminder.setChecked(isNotificationListenerEnabled(mActivity));
                } else {
                    mFragmentSettingBinding.tbInformationReminder.setChecked(false);
                }
                mFragmentSettingBinding.tbInformationReminder.setOnCheckedChangeListener(mOnCheckedChangeListener);
                //删除历史记录
                device =DeviceControllerService.currentDevice;
                mFragmentSettingBinding.tbDeleteRecord.setChecked(device.getDeleteSwitch() == Device.SWITCH_OPEN);
            } else {
                mFragmentSettingBinding.slItem1.setVisibility(View.GONE);
                mFragmentSettingBinding.slItem2.setVisibility(View.GONE);
                mFragmentSettingBinding.slItem5.setVisibility(View.GONE);
                mFragmentSettingBinding.slItem4.setVisibility(View.GONE);
            }
            if (DeviceControllerService.currentDevice.getDeviceUpdate() == Device.DEVICE_UPDATE) {
                mFragmentSettingBinding.llDeviceUpgrade.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 关闭消息通知
     */
    public void checkInformation() {
        if (DeviceControllerService.mDeviceSetUp.getInformationSwitch() == Device.SWITCH_OPEN) {
            if (isNotificationListenerEnabled(mActivity)) {
                mFragmentSettingBinding.tbInformationReminder.setChecked(true);
            } else {
                mFragmentSettingBinding.tbInformationReminder.setChecked(false);
            }
        } else {
            mFragmentSettingBinding.tbInformationReminder.setChecked(false);
        }
    }

    private void showLanguage(DeviceInformation deviceInformation, DeviceSettingBean deviceSettingBean) {
        LanguageEntity languageEntity = DbUtils.getInstance().getLanguageEntity(deviceInformation.getLanguage());
        if (null != languageEntity) {
            Gson gson = new Gson();
            JsonArray jsonElements = JsonParser.parseString(languageEntity.getLanguageName()).getAsJsonArray();
            for (JsonElement bean : jsonElements) {
                LanguageSelect languageSelect = gson.fromJson(bean, LanguageSelect.class);
                languageNameList.add(languageSelect.getLanguageName());
                languageNumList.add(languageSelect.getLanguageNum());
            }
            for (int i = 0; i < languageNumList.size(); i++) {
                if (Integer.parseInt(languageNumList.get(i)) == deviceSettingBean.getSelectLanguage()) {
                    currentLanguage = languageNameList.get(i);
                    mFragmentSettingBinding.dsLanguage.setItemData(getString(R.string.device_language), currentLanguage);
                    break;
                }
            }
        }
    }

    private void showImage(DeviceInformationEntity deviceInformationEntity) {
        switch (deviceInformationEntity.getProductNo()) {
            case Device.PRODUCT_NO_M1:
                mFragmentSettingBinding.ivDevice.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.device_m1));
                mFragmentSettingBinding.ivDeviceBg.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.m1_bg_top));
                break;
            case Device.PRODUCT_NO_M2:
                mFragmentSettingBinding.ivDevice.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.device_m2));
                mFragmentSettingBinding.ivDeviceBg.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.m2_bg_top));
                //语言
                mFragmentSettingBinding.dsLanguage.setVisibility(View.GONE);
                mFragmentSettingBinding.viewLanguage.setVisibility(View.GONE);
                //时间格式
                mFragmentSettingBinding.llTime.setVisibility(View.GONE);
                mFragmentSettingBinding.viewTime.setVisibility(View.GONE);
                mFragmentSettingBinding.slItem5.setVisibility(View.GONE);
                break;
            case Device.PRODUCT_NO_M4:
                mFragmentSettingBinding.ivDevice.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.device_m4));
                mFragmentSettingBinding.ivDeviceBg.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.m4_bg_top));
                mFragmentSettingBinding.slItem1.setVisibility(View.GONE);
                mFragmentSettingBinding.slItem4.setVisibility(View.GONE);
                mFragmentSettingBinding.dsAltitude.setVisibility(View.GONE);
                mFragmentSettingBinding.viewAltitude.setVisibility(View.GONE);
                mFragmentSettingBinding.slItem5.setVisibility(View.GONE);
                break;
            default:
        }
    }

    public void updateProgress(int total, int current) {
        if (mFragmentSettingBinding != null) {
            if (total == current) {
                mFragmentSettingBinding.llProgress.setVisibility(View.GONE);
                DeviceInformationEntity device =DeviceControllerService.currentDevice;
                if (device.getDeviceUpdate() == Device.DEVICE_UPDATE) {
                    mFragmentSettingBinding.llDeviceUpgrade.setVisibility(View.VISIBLE);
                }
            } else {
                mFragmentSettingBinding.llDeviceUpgrade.setVisibility(View.GONE);
                mFragmentSettingBinding.llProgress.setVisibility(View.VISIBLE);
                mFragmentSettingBinding.pbProgress.setMax(total);
                mFragmentSettingBinding.pbProgress.setProgress(current);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void updatePower(int power) {
        if (mFragmentSettingBinding != null) {
            mFragmentSettingBinding.dpv.setPowerData(power);
            mFragmentSettingBinding.tvPower.setText(power + getString(R.string.percent));
        }
    }

    public void updateStatus() {
        if (mFragmentSettingBinding != null) {
            if (DeviceControllerService.currentDevice.getDeviceUpdate() == Device.DEVICE_UPDATE) {
                mFragmentSettingBinding.llDeviceUpgrade.setVisibility(View.VISIBLE);
            } else {
                mFragmentSettingBinding.llDeviceUpgrade.setVisibility(View.GONE);
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ds_language:
                if (languageNameList != null && languageNameList.size() >= 0) {
                    SelectLanguageDialog selectLanguageDialog = new SelectLanguageDialog(mActivity, languageNameList, currentLanguage);
                    selectLanguageDialog.show();
                    selectLanguageDialog.setSelectLanguageClickListener((value, position) -> {
                        currentLanguage = languageNameList.get(position);
                        mFragmentSettingBinding.dsLanguage.setItemData(getString(R.string.device_language), currentLanguage);
                        CommandEntity commandEntity = BleCommandManager.getInstance().languageSettings(Integer.parseInt(languageNumList.get(position)));
                        DeviceControllerService.mDeviceSetUp.setSelectLanguage(Integer.parseInt(languageNumList.get(position)));
                        mDeviceSettingActivity.sendCommandData(commandEntity);
                    });
                }
                break;
            case R.id.ds_odo:
                int odoValue = UnitConversionUtil.getUnitConversionUtil().distance2Int(DeviceControllerService.mDeviceSetUp.getOdo());
                String unitValue;
                if (Config.unit == Unit.METRIC.value) {
                    unitValue = getString(R.string.unit_km);
                } else {
                    unitValue = getString(R.string.unit_mile);
                }
                DistanceDialog distanceDialog = new DistanceDialog(mActivity, odoValue);
                distanceDialog.show();
                distanceDialog.setDistanceDialogListener(distance -> {
                    mFragmentSettingBinding.dsOdo.setItemData(getString(R.string.odo), distance + unitValue);
                    int value = UnitConversionUtil.getUnitConversionUtil().distance2m(distance);
                    CommandEntity commandEntity = BleCommandManager.getInstance().odoSettings(value);
                    mDeviceSettingActivity.sendCommandData(commandEntity);
                    DeviceControllerService.mDeviceSetUp.setOdo(value);
                });
                break;
            case R.id.ds_altitude:
                UnitBean altitudeBean = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(mActivity, (float) DeviceControllerService.mDeviceSetUp.getAltitude() / 100);
                AltitudeDialog altitudeDialog = new AltitudeDialog(mActivity, Integer.parseInt(altitudeBean.getValue()));
                altitudeDialog.show();
                altitudeDialog.setAltitudeDialogListener(altitude -> {
                    mFragmentSettingBinding.dsAltitude.setItemData(getString(R.string.altitude), altitude + altitudeBean.getUnit());
                    int value = UnitConversionUtil.getUnitConversionUtil().altitude2Int(altitude);
                    CommandEntity commandEntity = BleCommandManager.getInstance().altitudeCorrection(value);
                    mDeviceSettingActivity.sendCommandData(commandEntity);
                    DeviceControllerService.mDeviceSetUp.setAltitude(value);
                });
                break;
            case R.id.ds_sensor:
                startActivity(new Intent(mActivity, SensorListActivity.class));
                break;
            case R.id.tv_delete_device:
                mDeviceSettingActivity.deleteDevice();
                break;
            case R.id.ll_device_upgrade:
                upgradeDialog();
                break;
            case R.id.guide_bg_m1:
            case R.id.guide_bg_m2:
            case R.id.guide_bg_m3:
                mFragmentSettingBinding.groupM1.setVisibility(View.GONE);
                mFragmentSettingBinding.groupM2.setVisibility(View.GONE);
                mFragmentSettingBinding.groupM3.setVisibility(View.GONE);
                int guideFlag = userInfoEntity.getGuideFlag();
                userInfoEntity.setGuideFlag(guideFlag & 0xEF);
                mActivity.updateUserInfoEntity(userInfoEntity);
                mDeviceSettingActivity.hideGuide();
                break;
            default:
        }
    }

    private void upgradeDialog() {
        BatteryManager manager = (BatteryManager) mDeviceSettingActivity.getSystemService(Context.BATTERY_SERVICE);
        if (manager != null) {
            int currentLevel = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            if (currentLevel <= 20 || DeviceControllerService.currentPower <= 20) {
                DeviceUpgradeDialog deviceUpgradeDialog = new DeviceUpgradeDialog(mDeviceSettingActivity, DeviceControllerService.currentDevice.getProductNo(), DeviceControllerService.currentPower);
                deviceUpgradeDialog.show();
            } else {
                mDeviceSettingActivity.startOta();
            }
        } else {
            mDeviceSettingActivity.startOta();
        }
    }

    /**
     * 检测通知监听服务是否被授权
     */
    public boolean isNotificationListenerEnabled(Context context) {
        Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(mActivity);
        return packageNames.contains(context.getPackageName());
    }

    private final CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (isNotificationListenerEnabled(mActivity)) {
                    DeviceControllerService.mDeviceSetUp.setInformationSwitch(Device.SWITCH_OPEN);
                    CommandEntity commandEntity = BleCommandManager.getInstance().messageReminderSwitch(DeviceControllerService.mDeviceSetUp.getInformationSwitch());
                    mDeviceSettingActivity.sendCommandData(commandEntity);
                } else {
                    mFragmentSettingBinding.tbInformationReminder.setChecked(false);
                    openNotificationListenSettings();
                }
            } else {
                DeviceControllerService.mDeviceSetUp.setInformationSwitch(Device.SWITCH_CLOSE);
                CommandEntity commandEntity = BleCommandManager.getInstance().messageReminderSwitch(DeviceControllerService.mDeviceSetUp.getInformationSwitch());
                mDeviceSettingActivity.sendCommandData(commandEntity);
            }
        }
    };

    /**
     * 打开通知监听设置页面
     */
    private void openNotificationListenSettings() {
        try {
            Intent intent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            } else {
                intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            }
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mDeviceSettingActivity.showAndHideBack(true);
            if (((userInfoEntity.getGuideFlag() & 16) >> 4) == Config.NEED_GUIDE) {
                mDeviceSettingActivity.showGuide();
            }
            setSettingData();
            updatePower(DeviceControllerService.currentPower);
        } else {
            mDeviceSettingActivity.hideGuide();
        }
    }
}
