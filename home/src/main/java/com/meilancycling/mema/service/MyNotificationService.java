package com.meilancycling.mema.service;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Device;
import java.nio.charset.StandardCharsets;

/**
 * 消息通知
 *
 * @author lion
 */
public class MyNotificationService extends NotificationListenerService {
    /**
     * 来电
     */
    private static final String CALL_SAMSUNG = "com.samsung.android.incallui";
    private static final String INCALLUI = "com.android.incallui";
    /**
     * 未接来电
     */
    private static final String HW_INCALLUI = "com.android.contacts";
    private static final String TELECOM = "com.android.server.telecom";
    /**
     * EMAIL
     */
    private static final String EMAIL = "";
    /**
     * 短信
     */
    private static final String SAMSUNG_MSG_PACK_NAME = "com.samsung.android.messaging";
    private static final String SAMSUNG_MSG_SRVERPCK_NAME = "com.samsung.android.communicationservice";
    private static final String MSG_PACKAGE_NAME = "com.android.mms";//短信--- 小米
    private static final String SYS_SMS = "com.android.mms.service";//短信 --- vivo Y85A
    private static final String SYS_SMS_NOTE = "com.google.android.apps.messaging";//note 10
    /**
     * WhatsApp
     */
    private static final String WHATS_APP_PACKAGE_NAME = "com.whatsapp";
    /**
     * twitter
     */
    private static final String TWITTER_PACKAGE_NAME = "com.twitter.android";
    /**
     * Facebook
     */
    private static final String FACEBOOK_PACKAGE_NAME = "com.facebook.katana";
    private static final String FACEBOOK_PACKAGE_NAME1 = "com.facebook.orca";
    /**
     * Skype
     */
    private static final String SKYPE_PACKAGE_NAME = "com.skype.raider";
    private static final String SKYPE_PACK_NAME = "com.skype.rover";
    /**
     * 微信
     */
    private static final String WECHAT_PACKAGE_NAME = "com.tencent.mm";
    /**
     * QQ
     */
    private static final String QQ_PACKAGE_NAME = "com.tencent.mobileqq";


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // TODO Auto-generated method stub
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
        Log.i("sore", "notificationPkg=" + notificationPkg + "==notificationTitle=" + notificationTitle + "===notificationText" + notificationText);
        if (TextUtils.isEmpty(notificationPkg) || TextUtils.isEmpty(notificationTitle) || TextUtils.isEmpty(notificationText)) {
            return;
        }
        if (DeviceControllerService.mDeviceSetUp != null && DeviceControllerService.mDeviceSetUp.getInformationSwitch() == Device.SWITCH_OPEN) {
            Intent intent = new Intent(BroadcastConstant.ACTION_NOTIFICATION);
            switch (notificationPkg) {
                case CALL_SAMSUNG:
                case INCALLUI:
                    intent.putExtra("type", 0);
                    break;
                case TELECOM:
                case HW_INCALLUI:
                    intent.putExtra("type", 1);
                    break;
                case SAMSUNG_MSG_PACK_NAME:
                case SAMSUNG_MSG_SRVERPCK_NAME:
                case SYS_SMS_NOTE:
                case MSG_PACKAGE_NAME:
                case SYS_SMS:
                    intent.putExtra("type", 4);
                    break;
                case WECHAT_PACKAGE_NAME:
                    intent.putExtra("type", 9);
                    break;
                case QQ_PACKAGE_NAME:
                    intent.putExtra("type", 10);
                    break;
                case FACEBOOK_PACKAGE_NAME:
                case FACEBOOK_PACKAGE_NAME1:
                    intent.putExtra("type", 7);
                    break;
                case TWITTER_PACKAGE_NAME:
                    intent.putExtra("type", 6);
                    break;
                case WHATS_APP_PACKAGE_NAME:
                    intent.putExtra("type", 5);
                    break;
                case SKYPE_PACK_NAME:
                case SKYPE_PACKAGE_NAME:
                    intent.putExtra("type", 8);
                    break;
                default:
            }
            intent.putExtra("title", parseContent(notificationTitle, 31));
            intent.putExtra("content", parseContent(notificationText, 53));
            sendBroadcast(intent);
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        toggleNotificationListenerService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        toggleNotificationListenerService();
        return START_STICKY;
    }

    /**
     * 某些国产手机app被杀死后，再打开app无法监听。要重启服务才行。
     */
    private void toggleNotificationListenerService() {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName(this, MyNotificationService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(
                new ComponentName(this, MyNotificationService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }


    private String parseContent(String content, int num) {
        if (content.getBytes(StandardCharsets.UTF_8).length > num) {
            content = content.substring(0, content.length() - 1);
            content = parseContent(content, num);
        }
        return content;
    }

}
