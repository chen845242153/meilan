package com.meilancycling.mema.base;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.R;
import com.meilancycling.mema.ble.bean.CommandEntity;

import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.customview.dialog.DialogLoading;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.db.greendao.UserInfoEntityDao;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.device.bean.SensorValueBean;
import com.meilancycling.mema.ui.login.LoginActivity;
import com.meilancycling.mema.utils.SPUtils;
import com.meilancycling.mema.utils.StatusAppUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * 基类
 *
 * @author lion qq 571135591
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    private Long userId;
    public boolean isShow;
    private UserInfoEntity userInfoEntity;

    /**
     * 加载对话框
     */
    private DialogLoading loading;

    public void showLoadingDialog() {
        if (loading == null) {
            loading = new DialogLoading(this);
        }
        loading.show();
    }

    public void hideLoadingDialog() {
        if (loading != null) {
            loading.hideDialog();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusAppUtils.setColor(this, ContextCompat.getColor(this, R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(baseReceiver, new IntentFilter(BroadcastConstant.ACTION_LOGIN));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(baseReceiver);
    }

    /**
     * 接受数据
     */
    private final BroadcastReceiver baseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isShow) {
                isShow = true;
                hideLoadingDialog();
                String message = intent.getStringExtra("message");
                AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
                builder.setCancelable(false);
                builder.setMessage(message);
                builder.setPositiveButton(getString(R.string.confirm), (dialog, which) -> {
                    dialog.dismiss();
                    isShow = false;
                    SPUtils.putLong(BaseActivity.this, Config.CURRENT_USER, -1);
                    sendBroadcast(new Intent(BroadcastConstant.ACTION_USER_LOGOUT));
                    startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                });
                builder.show();
            }
        }
    };

    public Long getUserId() {
        if (userId == null) {
            userId = SPUtils.getLong(this, Config.CURRENT_USER);
        }
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 切换设备
     */
    protected void changeDevice() {
        sendBroadcast(new Intent(BroadcastConstant.ACTION_CHANGE_DEVICE));
    }

    /**
     * 发送数据
     */
    public void sendCommandData(CommandEntity commandEntity) {
        Intent intent = new Intent(BroadcastConstant.ACTION_SEND_COMMAND);
        intent.putExtra(BroadcastConstant.BROADCAST_KEY, commandEntity);
        sendBroadcast(intent);
    }

    /**
     * 更新当前设备
     */
    protected void updateCurrentDevice() {
        sendBroadcast(new Intent(BroadcastConstant.ACTION_UPDATE_CURRENT_DEVICE));
    }

    /**
     * 退出当前账号
     */
    protected void userLogout() {
        sendBroadcast(new Intent(BroadcastConstant.ACTION_USER_LOGOUT));
    }

    /**
     * 更新传感器集合
     */
    protected void updateSensorList(SensorValueBean sensorValueBean, int position) {
        DeviceControllerService.mSensorList.set(position, sensorValueBean);
    }

    /**
     * 删除传感器
     */
    protected void deleteSensorList(SensorValueBean sensorValueBean) {
        DeviceControllerService.mSensorList.remove(sensorValueBean);
    }

    /**
     * 清除搜索传感器
     */
    protected void clearScanList() {
        DeviceControllerService.mScanList.clear();
    }

    /**
     * ota升级
     */
    public void otaUpgrade() {
        Intent intent = new Intent(BroadcastConstant.ACTION_OTA_UPGRADE);
        sendBroadcast(intent);
    }

    /**
     * 获取用户信息
     */
    public UserInfoEntity getUserInfoEntity() {
        if (userInfoEntity == null) {
            userInfoEntity = MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().queryBuilder()
                    .where(UserInfoEntityDao.Properties.UserId.eq(getUserId()))
                    .unique();
        }
        return userInfoEntity;
    }

    public void updateUserInfoEntity(UserInfoEntity userInfoEntity) {
        this.userInfoEntity = userInfoEntity;
        if (userInfoEntity != null) {
            MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().update(userInfoEntity);
        }
    }
}
