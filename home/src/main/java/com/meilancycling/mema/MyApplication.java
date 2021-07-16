package com.meilancycling.mema;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.multidex.MultiDex;
import com.clj.fastble.BleManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.meilancycling.mema.db.greendao.DaoMaster;
import com.meilancycling.mema.db.greendao.DaoSession;
import com.meilancycling.mema.utils.ScreenAdapterUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author lion qq 571135591
 */
public class MyApplication extends Application {
    public static MyApplication mInstance;
    public static String APP_ID = "wx7ba32005bdb0cfeb";
    public static String WX_AppSecret = "848c4ffb2dbcf55f5ec76b8ad87c950b";
    public static String QQ_APPID = "1109515481";
    public static IWXAPI api;
    public static final String DB_NAME = "meilan.db";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initScreenAdapter();
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        CrashReport.initCrashReport(getApplicationContext(), "5306d0870a", true);
        initGreenDao();
        initBle();
        //登录初始化
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    /**
     * 重写attachBaseContext()方法：解决方法数 65536 (65k) 限制
     * 同时解决友盟分析问题Could not find class 'com.umeng.analytics.d'
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化屏幕适配方案
     */
    private void initScreenAdapter() {
        float width = 375f;
        ScreenAdapterUtils.setDensity(this, width);
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    /**
     * 初始化ble
     */
    private void initBle() {
        BleManager.getInstance().init(this);
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setOperateTimeout(5000);

        ;
    }
}
