package com.meilancycling.mema.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.meilancycling.mema.constant.Config;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/10 5:54 PM
 */
public class AppUtils {
    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    public static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp转换px
     */
    public static int spToPx(Context context, int spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private static final int DECIMAL_PLACE = 8;

    /**
     * 两个数相乘
     */
    public static double multiplyDouble(double val1, double val2) {
        String var1 = Double.toString(val1);
        String var2 = Double.toString(val2);
        BigDecimal bigDecimal1 = new BigDecimal(var1);
        BigDecimal bigDecimal2 = new BigDecimal(var2);
        return bigDecimal1.multiply(bigDecimal2).setScale(DECIMAL_PLACE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 时间转换成字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String timeToString(long time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(time);
    }

    /**
     * 时间转换成字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String zeroTimeToString(long time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return formatter.format(time);
    }

    /**
     * 时间转换成字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String localTimeToString(long time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return formatter.format(time);
    }

    /**
     * 时间转成毫秒值
     */
    @SuppressLint("SimpleDateFormat")
    public static long timeToLong(String time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(time).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static boolean saveImageToGallery(Context context, Bitmap bitmap) {
        long mImageTime = System.currentTimeMillis();
        String imageDate = timeToString(mImageTime, Config.DEFAULT_PATTERN_COMPLETE);
        String mImageFileName = imageDate + ".png";
        final ContentValues values = new ContentValues();
        //Environment.DIRECTORY_SCREENSHOTS:截图,图库中显示的文件夹名。"dh"
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "meilan");
        values.put(MediaStore.MediaColumns.DATE_EXPIRES, (mImageTime + DateUtils.DAY_IN_MILLIS) / 1000);
        values.put(MediaStore.MediaColumns.IS_PENDING, 1);
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, mImageFileName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
        values.put(MediaStore.MediaColumns.DATE_ADDED, mImageTime / 1000);
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, mImageTime / 1000);
        ContentResolver resolver = context.getContentResolver();
        final Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        try {
            // First, write the actual data for our screenshot
            try (OutputStream out = resolver.openOutputStream(uri)) {
                if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                    return false;
                }
            }
            // Everything went well above, publish it!
            values.clear();
            values.put(MediaStore.MediaColumns.IS_PENDING, 0);
            values.putNull(MediaStore.MediaColumns.DATE_EXPIRES);
            resolver.update(uri, values, null, null);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean saveImage(Context context, Bitmap bmp) {
        File appDir = new File(context.getExternalFilesDir("") + File.separator + "meilan");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            return false;
        } // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            return false;
        } // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" +context.getExternalFilesDir("")+ File.separator)));
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file); //out is your output file
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
        return true;
    }

    public static void insertVideoToMediaStore(Context context, String filePath) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, filePath);
        // video/*
        values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
        context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
    }

    /**
     * 判断位置服务是否打开
     *
     * @return
     */
    public static boolean isOpenLocationService(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            return gps || network;
        }
        return false;
    }

    /**
     * 判断是否是中文环境
     */
    public static boolean isChinese() {
        return Config.CHINESE.equals(Locale.getDefault().getLanguage());
    }

    /**
     * 获取时区
     */
    public static int getTimeZone() {
        Calendar cal = Calendar.getInstance();
        int offset = cal.get(Calendar.ZONE_OFFSET);
        cal.add(Calendar.MILLISECOND, -offset);
        long timeStampUTC = cal.getTimeInMillis();
        long timeStamp = (timeStampUTC + TimeZone.getDefault().getOffset(timeStampUTC));
        long timeZone = (timeStamp - timeStampUTC) / 1000;
        return Integer.parseInt(String.valueOf(timeZone));
    }

    /**
     * 获取版本名称
     *
     * @param context 上下文
     * @return 版本名称
     */
    public static String getVersionName(Context context) {

        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将文件转换成Byte数组
     */
    public static byte[] getBytesByFile(String pathStr) {
        File file = new File(pathStr);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveBytesToFile(String filePath, byte[] data) {
        File file = new File(filePath);
        BufferedOutputStream outStream = null;
        try {
            outStream = new BufferedOutputStream(new FileOutputStream(file));
            outStream.write(data);
            outStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outStream) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 计算百分比
     */
    public static List<Integer> percentage(long... data) {
        /*
         * 小数集合
         */
        List<Double> percentageList = new ArrayList<>();
        /*
         * 整数集合
         */
        List<Integer> result = new ArrayList<>();

        long total = 0L;
        for (Long datum : data) {
            total = total + datum;
        }
        if (total != 0) {
            for (Long datum : data) {
                double value = ((double) datum / total) * 100;
                double floor = Math.floor(value);
                result.add((int) floor);
                percentageList.add(value - (int) floor);
            }
            while (true) {
                int resultTotal = 0;
                for (Integer integer : result) {
                    resultTotal = resultTotal + integer;
                }
                if (resultTotal == 100) {
                    break;
                } else {
                    Double max = Collections.max(percentageList);
                    int position = percentageList.indexOf(max);
                    percentageList.remove(position);
                    percentageList.add(position, 0.0);
                    result.add(position, result.remove(position) + 1);
                }
            }
        } else {
            for (Long datum : data) {
                result.add(0);
            }
        }
        return result;
    }

    public static void isInstallApk(Context mContext, String apkPath) {
        if (!apkPath.isEmpty()) {
            //提升读写权限,否则可能出现解析异常
            setPermission(apkPath);
            installNormal(mContext, apkPath);
        } else {
            Log.e("DownloadFinishReceiver", "apkPath is null");
        }
    }

    /**
     * 提升读写权限
     *
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    public static void setPermission(String filePath) {
        String command = "chmod " + "777" + " " + filePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 普通安装
     */
    private static void installNormal(Context context, String apkPath) {
        File file = (new File(apkPath));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.meilancycling.mema.fileProvider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        } else {
            Intent intent1 = new Intent();
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.setAction(Intent.ACTION_VIEW);
            intent1.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            context.startActivity(intent1);
        }
    }

    public static double stringToDouble(String value) {
        try {
            return Double.parseDouble(value.replace(",", "."));
        } catch (Exception e) {
            return 0;
        }
    }

    public static float stringToFloat(String value) {
        try {
            return Float.parseFloat(value.replace(",", "."));
        } catch (Exception e) {
            return 0;
        }

    }
}
