package com.meilancycling.mema.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.meilancycling.mema.R;


/**
 * @author lion
 * toast 工具类
 */
public class ToastUtils {
    private static Toast instance;

    public static Toast show(Context context, String content) {
        if (instance != null) {
            instance.cancel();
        }
        instance = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
        instance.show();
        return instance;
    }

    public static void showError(Context context, String resultCode) {
        if (!TextUtils.isEmpty(resultCode)) {
            int code = Integer.parseInt(resultCode);
            String content = "";
            switch (code) {
                case 991:
                    content = context.getString(R.string.CODE_991);
                    break;
                case 120000:
                    content = context.getString(R.string.CODE_120000);
                    break;
                case 120001:
                    content = context.getString(R.string.CODE_120001);
                    break;
                case 120002:
                    content = context.getString(R.string.CODE_120002);
                    break;
                case 120003:
                    content = context.getString(R.string.CODE_120003);
                    break;
                case 120004:
                    content = context.getString(R.string.CODE_120004);
                    break;
                case 120005:
                    content = context.getString(R.string.CODE_120005);
                    break;
                case 120006:
                    content = context.getString(R.string.CODE_120006);
                    break;
                case 20501:
                    content = context.getString(R.string.CODE_20501);
                    break;
                case 20503:
                    content = context.getString(R.string.CODE_20503);
                    break;
                case 20504:
                    content = context.getString(R.string.CODE_20504);
                    break;
                case 20505:
                    content = context.getString(R.string.CODE_20505);
                    break;
                case 20506:
                    content = context.getString(R.string.CODE_20506);
                    break;
                case 20508:
                    content = context.getString(R.string.CODE_20508);
                    break;
                case 20513:
                    content = context.getString(R.string.CODE_20513);
                    break;
                case 20530:
                    content = context.getString(R.string.CODE_20530);
                    break;
                case 205302:
                    content = context.getString(R.string.CODE_205302);
                    break;
                case 20550:
                    content = context.getString(R.string.CODE_20550);
                    break;
                case 20560:
                    content = context.getString(R.string.CODE_20560);
                    break;
                case 130001:
                    content = context.getString(R.string.CODE_130001);
                    break;
                case -1:
                    content = context.getString(R.string.no_internet_connection);
                    break;

            }
            if (!TextUtils.isEmpty(content)) {
                Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
