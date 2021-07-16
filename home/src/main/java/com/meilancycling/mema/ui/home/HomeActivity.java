package com.meilancycling.mema.ui.home;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clj.fastble.BleManager;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.customview.dialog.AppUpdateDialog;
import com.meilancycling.mema.databinding.ActivityHomeBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.BaseObserver;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.AppUpdateRequest;
import com.meilancycling.mema.network.bean.request.SessionRequest;
import com.meilancycling.mema.network.bean.request.VersionInsetRequest;
import com.meilancycling.mema.network.bean.response.AppUpdateResponse;
import com.meilancycling.mema.network.download.FileDownloadCallback;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.club.RankFragment;
import com.meilancycling.mema.ui.sensor.SensorHomeActivity;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.SPUtils;
import com.meilancycling.mema.utils.ToastUtils;

import java.io.File;
import java.util.List;

/**
 * @author lion
 * 主界面
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private ActivityHomeBinding homeBinding;
    private HomeFragment mHomeFragment;
    private MoreFragment mMoreFragment;
    private RankFragment mClubHomeFragment;
    private Fragment fromFragment;
    private Fragment fragCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        homeBinding.bvHomeItem1.setOnClickListener(this);
        homeBinding.bvHomeItem2.setOnClickListener(this);
        homeBinding.bvHomeItem3.setOnClickListener(this);
        homeBinding.bvHomeItem4.setOnClickListener(this);
//        homeBinding.viewBottom.setOnClickListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastConstant.ACTION_DEVICE_UPDATE);
        intentFilter.addAction(BroadcastConstant.ACTION_DATA_PROGRESS);
        intentFilter.addAction(BroadcastConstant.ACTION_BLE_STATUS);
        intentFilter.addAction(BroadcastConstant.ACTION_POWER_VALUE);
        intentFilter.addAction(BroadcastConstant.ACTION_DEVICE_STATUS);
        intentFilter.addAction(BroadcastConstant.ACTION_DELETE_RECORD);
        registerReceiver(mReceiver, intentFilter);
        getAppVersion();
        init();

        String filePath = getExternalFilesDir("") + File.separator + ".myfit";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public void notifyAdapter() {
        if (mHomeFragment != null) {
            mHomeFragment.notifyAdapter();
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                switch (intent.getAction()) {
                    case BroadcastConstant.ACTION_BLE_STATUS:
                        if (BleManager.getInstance().isBlueEnable()) {
                            mHomeFragment.deviceConnecting(DeviceControllerService.currentDevice);
                        } else {
                            mHomeFragment.bleNotTurnOn(DeviceControllerService.currentDevice);
                        }
                        break;
                    case BroadcastConstant.ACTION_POWER_VALUE:
                        if (DeviceControllerService.currentDevice != null) {
                            mHomeFragment.updatePower(DeviceControllerService.currentPower);
                        }
                        break;
                    case BroadcastConstant.ACTION_DEVICE_STATUS:
                        if (BleManager.getInstance().isBlueEnable()) {
                            if (DeviceControllerService.deviceStatus == Device.DEVICE_CONNECTED) {
                                mHomeFragment.deviceConnected(DeviceControllerService.currentDevice);
                            } else {
                                mHomeFragment.deviceConnecting(DeviceControllerService.currentDevice);
                            }
                        } else {
                            mHomeFragment.bleNotTurnOn(DeviceControllerService.currentDevice);
                        }
                        mHomeFragment.hideUpgrade();
                        break;
                    case BroadcastConstant.ACTION_DATA_PROGRESS:
                        mHomeFragment.dataUploaded(intent.getIntExtra("total", 100), intent.getIntExtra("current", 0));
                        break;
                    case BroadcastConstant.ACTION_DEVICE_UPDATE:
                        if (fragCategory instanceof HomeFragment) {
                            mHomeFragment.showDeviceUpgrade(DeviceControllerService.currentDevice, DeviceControllerService.currentPower);
                        }
                        break;
                    case BroadcastConstant.ACTION_DELETE_RECORD:
                        showLoadingDialog();
                        mHomeFragment.getSevenDate();
                        break;
                    default:
                }
            }
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        init();
    }

    public UserInfoEntity mUserInfoEntity;

    private void init() {
        setUserId(null);
        updateUserInfoEntity(null);
        mUserInfoEntity = getUserInfoEntity();
        Config.unit = mUserInfoEntity.getUnit();

        mHomeFragment = new HomeFragment();
        mMoreFragment = new MoreFragment();
        mClubHomeFragment = new RankFragment(mUserInfoEntity.getSession());

        fragCategory = mHomeFragment;
        switchFragment();
        homeBinding.bvHomeItem1.setCheckData(true, 0);
        homeBinding.bvHomeItem2.setCheckData(false, 1);
        homeBinding.bvHomeItem3.setCheckData(false, 2);
        homeBinding.bvHomeItem4.setCheckData(false, 3);
        DeviceControllerService.getInstance().init(this);
        changeDevice();
        upgradeList();
//        startService(new Intent(this, DeviceControllerService.class));


    }

    private void upgradeList() {
        SessionRequest sessionRequest = new SessionRequest();
        sessionRequest.setSession(getUserInfoEntity().getSession());
        RetrofitUtils.getApiUrl()
                .upgradeList(sessionRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<List<VersionInsetRequest>>() {
                    @Override
                    public void onSuccess(List<VersionInsetRequest> list) {
                        if (list != null && list.size() > 0) {
                            List<DeviceInformationEntity> deviceInformationEntities = DbUtils.getInstance().deviceInfoList(getUserId());
                            if (deviceInformationEntities != null && deviceInformationEntities.size() > 0) {
                                for (DeviceInformationEntity deviceInformationEntity : deviceInformationEntities) {
                                    for (VersionInsetRequest versionInsetRequest : list) {
                                        if (versionInsetRequest.getMac().equals(deviceInformationEntity.getMacAddress())) {
                                            DbUtils.getInstance().deleteDevice(deviceInformationEntity);
                                        }
                                    }
                                }
                            }
                            deviceInformationEntities = DbUtils.getInstance().deviceInfoList(getUserId());
                            int deviceSerialNumber = 0;
                            if (deviceInformationEntities != null && deviceInformationEntities.size() > 0) {
                                DeviceInformationEntity deviceInformationEntity = deviceInformationEntities.get(deviceInformationEntities.size() - 1);
                                deviceSerialNumber = deviceInformationEntity.getDeviceSerialNumber() + 1;
                            }
                            for (int i = 0; i < list.size(); i++) {
                                DeviceInformationEntity deviceInformationEntity = new DeviceInformationEntity();
                                deviceInformationEntity.setMacAddress(list.get(i).getMac());
                                deviceInformationEntity.setUserId(getUserId());
                                deviceInformationEntity.setProductNo(list.get(i).getProduct());
                                deviceInformationEntity.setDeviceUpdate(Device.DEVICE_UPDATE_UNDONE);
                                deviceInformationEntity.setMessageCh(list.get(i).getTwoMes());
                                deviceInformationEntity.setMessageEn(list.get(i).getTwoMesEn());
                                deviceInformationEntity.setOtaUrl(list.get(i).getFileTUrl());
                                deviceInformationEntity.setDeviceSerialNumber(deviceSerialNumber + i + 1);
                                DbUtils.getInstance().addDevice(deviceInformationEntity);
                            }
                        }
                        changeDevice();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {

                    }
                });
    }

    /**
     * 获取app版本
     */
    private void getAppVersion() {
        showLoadingDialog();
        String clientType = "1";
        AppUpdateRequest appUpdateRequest = new AppUpdateRequest();
        appUpdateRequest.setClientType(clientType);
        appUpdateRequest.setClientVersion(AppUtils.getVersionName(this));
        RetrofitUtils.getApiUrl()
                .appUpdate(appUpdateRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new BaseObserver<AppUpdateResponse>() {
                    @Override
                    public void onSuccess(AppUpdateResponse result) {
                        long appTime = SPUtils.getLong(HomeActivity.this, SPUtils.APP_TIMESTAMP);
                        if (result.getIsUpgrade() == 2) {
                            String content;
                            if (AppUtils.isChinese()) {
                                content = result.getContent();
                            } else {
                                content = result.getEnContent();
                            }
                            AppUpdateDialog appUpdateDialog = new AppUpdateDialog(HomeActivity.this, result.getIsUpgrade(), content);
                            appUpdateDialog.show();
                            appUpdateDialog.setAppUpdateConfirmListener(() -> downApp(result.getDownloadUrl(), appUpdateDialog));
                            SPUtils.putLong(HomeActivity.this, SPUtils.APP_TIMESTAMP, System.currentTimeMillis());
                        } else {
                            if (appTime + 24 * 60 * 60 * 1000 < System.currentTimeMillis()) {
                                String content;
                                if (AppUtils.isChinese()) {
                                    content = result.getContent();
                                } else {
                                    content = result.getEnContent();
                                }
                                AppUpdateDialog appUpdateDialog = new AppUpdateDialog(HomeActivity.this, result.getIsUpgrade(), content);
                                appUpdateDialog.show();
                                appUpdateDialog.setAppUpdateConfirmListener(() -> downApp(result.getDownloadUrl(), appUpdateDialog));
                                SPUtils.putLong(HomeActivity.this, SPUtils.APP_TIMESTAMP, System.currentTimeMillis());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                    }
                });
    }

    /**
     * 下载ota
     */
    private void downApp(String url, AppUpdateDialog appUpdateDialog) {
        String filePath = getExternalFilesDir("") + File.separator + "meilan.apk";
        RetrofitUtils.downloadUrl(url).execute(filePath, new FileDownloadCallback<File>() {
            @Override
            public void onSuccess(File file) {
                installApp(filePath);
            }

            @Override
            public void onFail(Throwable throwable) {
                ToastUtils.show(HomeActivity.this, getString(R.string.file_failed));
                appUpdateDialog.updateFail();
            }

            @Override
            public void onProgress(long current, long total) {
                appUpdateDialog.updateProgress((int) ((float) current / total * 100));
            }
        });
    }
//
//    /**
//     * 显示引导
//     */
//    public void showGuide() {
//        StatusAppUtils.setColor(this, ContextCompat.getColor(this, R.color.guide_bg));
//        homeBinding.viewBottom.setVisibility(View.VISIBLE);
//        homeBinding.fakeStatusBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.guide_bg));
//    }
//
//    /**
//     * 隐藏引导
//     */
//    public void hideGuide() {
//        StatusAppUtils.setColor(this, ContextCompat.getColor(this, R.color.white));
//        homeBinding.fakeStatusBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
//        homeBinding.viewBottom.setVisibility(View.INVISIBLE);
//    }


    private void switchFragment() {
        if (fromFragment != fragCategory && fragCategory != null) {
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (fromFragment != null) {
                transaction.hide(fromFragment);
            }
            if (!fragCategory.isAdded()) {
                transaction.add(R.id.home_container, fragCategory, fragCategory.getClass().getName()).commit();
            } else {
                transaction.show(fragCategory).commit();
            }
            fromFragment = fragCategory;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bv_home_item1:
                fragCategory = mHomeFragment;
                homeBinding.bvHomeItem1.setCheckData(true, 0);
                homeBinding.bvHomeItem2.setCheckData(false, 1);
                homeBinding.bvHomeItem3.setCheckData(false, 2);
                homeBinding.bvHomeItem4.setCheckData(false, 3);
                break;
            case R.id.bv_home_item2:
                startActivity(new Intent(this, SensorHomeActivity.class));
                break;
            case R.id.bv_home_item3:
                fragCategory = mClubHomeFragment;
                homeBinding.bvHomeItem1.setCheckData(false, 0);
                homeBinding.bvHomeItem2.setCheckData(false, 1);
                homeBinding.bvHomeItem3.setCheckData(true, 2);
                homeBinding.bvHomeItem4.setCheckData(false, 3);
                break;
            case R.id.bv_home_item4:
                fragCategory = mMoreFragment;
                homeBinding.bvHomeItem1.setCheckData(false, 0);
                homeBinding.bvHomeItem2.setCheckData(false, 1);
                homeBinding.bvHomeItem3.setCheckData(false, 2);
                homeBinding.bvHomeItem4.setCheckData(true, 3);
                break;
            default:
        }
        switchFragment();
    }

    private int installAppCode = 1;
    private String filePath;

    /**
     * apk 安装
     */
    private void installApp(String filePath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean b = getPackageManager().canRequestPackageInstalls();
            if (b) {
                AppUtils.isInstallApk(HomeActivity.this, filePath);
            } else {
                Uri uri = Uri.parse("package:" + getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri);
                startActivityForResult(intent, installAppCode);
                this.filePath = filePath;
            }
        } else {
            AppUtils.isInstallApk(HomeActivity.this, filePath);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == installAppCode) {
            if (TextUtils.isEmpty(filePath)) {
                filePath = getExternalFilesDir("") + File.separator + "meilan.apk";
            }
            if (getPackageManager().canRequestPackageInstalls() && !TextUtils.isEmpty(filePath)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean b = getPackageManager().canRequestPackageInstalls();
                    if (b) {
                        AppUtils.isInstallApk(HomeActivity.this, filePath);
                    }
                } else {
                    AppUtils.isInstallApk(HomeActivity.this, filePath);
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        //super.onSaveInstanceState(outState);   //将这一行注释掉，阻止activity保存fragment的状态
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        DeviceControllerService.getInstance().onDestroy();
    }
}