package com.meilancycling.mema;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.db.LanguageEntity;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.db.WheelEntity;
import com.meilancycling.mema.db.greendao.UserInfoEntityDao;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.response.CommonData;
import com.meilancycling.mema.ui.home.HomeActivity;
import com.meilancycling.mema.ui.home.WelcomeActivity;
import com.meilancycling.mema.ui.login.LoginActivity;
import com.meilancycling.mema.utils.SPUtils;
import com.meilancycling.mema.utils.StatusAppUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author lion
 * 欢迎界面
 */

public class SplashActivity extends RxAppCompatActivity implements EasyPermissions.PermissionCallbacks {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusAppUtils.setTranslucentForImageView(this, 0, null);
        initData();
        checkPermission();
    }

    private void checkLoginStatus() {
        new Handler().postDelayed(() -> {
            boolean aBoolean = SPUtils.getBoolean(SplashActivity.this, Config.IS_FIRST);
            if (aBoolean) {
                long userId = SPUtils.getLong(SplashActivity.this, Config.CURRENT_USER);
                if (userId != -1) {
                    UserInfoEntity userInfoEntity = MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().queryBuilder()
                            .where(UserInfoEntityDao.Properties.UserId.eq(userId))
                            .unique();
                    if (userInfoEntity == null) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            } else {
                startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            }
            finish();
        }, 2000);
    }

    private void initData() {
        RetrofitUtils.getApiUrl().getCommonData("password/cache/v101/select")
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<CommonData>() {
                    @Override
                    public void onSuccess(CommonData commonData) {
                        /*
                         * 语言列表
                         */
                        List<CommonData.LanguageListBean> languageList = commonData.getLanguageList();
                        List<LanguageEntity> languageEntityList = MyApplication.mInstance.getDaoSession().getLanguageEntityDao().queryBuilder().list();
                        if (null == languageEntityList || languageEntityList.size() != 0) {
                            MyApplication.mInstance.getDaoSession().getLanguageEntityDao().deleteAll();
                        }
                        for (CommonData.LanguageListBean languageListBean : languageList) {
                            LanguageEntity languageEntity = new LanguageEntity();
                            languageEntity.setLanguageName(languageListBean.getLanguageName());
                            languageEntity.setNum(languageListBean.getNum());
                            languageEntity.setRemark(languageListBean.getRemark());
                            MyApplication.mInstance.getDaoSession().getLanguageEntityDao().insertOrReplace(languageEntity);
                        }
                        /*
                         * 轮径值
                         */
                        List<WheelEntity> dateList = MyApplication.mInstance.getDaoSession().getWheelEntityDao().queryBuilder().list();
                        if (null == dateList || dateList.size() != 0) {
                            MyApplication.mInstance.getDaoSession().getWheelEntityDao().deleteAll();
                        }
                        List<CommonData.RinglistBean> ringlist = commonData.getRinglist();
                        for (CommonData.RinglistBean ringlistBean : ringlist) {
                            WheelEntity wheelEntity = new WheelEntity();
                            wheelEntity.setNorm(ringlistBean.getTypeO());
                            wheelEntity.setWheelValue(ringlistBean.getPerimeter());
                            MyApplication.mInstance.getDaoSession().getWheelEntityDao().insertOrReplace(wheelEntity);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                    }
                });
    }

    /**
     * 权限申请
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /*定位服务打开*/
            String[] perms = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION};
            if (EasyPermissions.hasPermissions(this, perms)) {
                checkLoginStatus();
            } else {
                //第二个参数是被拒绝后再次申请该权限的解释
                //第三个参数是请求码
                //第四个参数是要申请的权限
                EasyPermissions.requestPermissions(this, getString(R.string.authorization), 0, perms);
            }
        } else {
            checkLoginStatus();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        String[] per = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, per)) {
            checkLoginStatus();
        } else {
            finish();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkPermission();
    }
}