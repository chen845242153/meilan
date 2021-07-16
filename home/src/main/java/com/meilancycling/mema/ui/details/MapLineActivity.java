package com.meilancycling.mema.ui.details;

import android.Manifest;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.UiSettings;
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin;
import com.mapbox.mapboxsdk.plugins.localization.MapLocale;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.response.MotionDetailsResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.ActivityMapLineBinding;
import com.meilancycling.mema.service.RecordService;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @Description: 地图轨迹绘画
 * @Author: sore_lion
 * @CreateDate: 2020/11/24 2:16 PM
 */
public class MapLineActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private ActivityMapLineBinding mActivityMapLineBinding;
    private MapboxMap mapboxMap;
    private int motionType;
    private float mChange = 0.8f;
    private UnitBean distanceBean;
    private int dpiValue;
    private RecordService binder;
    /**
     * 0 未录制
     * 1 正在录制
     * 2 录制完成
     */
    private int isSave;

    private int totalSize;
    private int totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mActivityMapLineBinding = DataBindingUtil.setContentView(this, R.layout.activity_map_line);
        motionType = getIntent().getIntExtra("motionType", 0);
        initView();
        mActivityMapLineBinding.mapView.onCreate(savedInstanceState);
        mActivityMapLineBinding.mapView.getMapAsync(mapboxMap -> {
            MapLineActivity.this.mapboxMap = mapboxMap;
            showMap();
        });
        mActivityMapLineBinding.ivMapBack.setOnClickListener(this);
        mActivityMapLineBinding.ivStart.setOnClickListener(this);
        mActivityMapLineBinding.tvSave.setOnClickListener(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        dpiValue = dm.densityDpi;
        isSave = 0;
        if (AppUtils.isChinese()) {
            mActivityMapLineBinding.llQq.setVisibility(View.VISIBLE);
            mActivityMapLineBinding.ivFacebook.setVisibility(View.GONE);
        } else {
            mActivityMapLineBinding.llQq.setVisibility(View.GONE);
            mActivityMapLineBinding.ivFacebook.setVisibility(View.VISIBLE);
        }
        mActivityMapLineBinding.ivFriend.setOnClickListener(v -> toWeChatScan());
        mActivityMapLineBinding.ivWx.setOnClickListener(v -> toWeChatScan());
        mActivityMapLineBinding.ivQq.setOnClickListener(v -> toQQScan());

        mActivityMapLineBinding.ivFacebook.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            String facebookUrl = "https://www.facebook.com/Google/";
            intent.setData(Uri.parse(facebookUrl));
            startActivity(intent);
        });
    }


    private void toWeChatScan() {
        try {
            Uri uri = Uri.parse("weixin://");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {

        }
    }

    private void toQQScan() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
        startActivity(intent);
    }

    public static void enterMapLineActivity(Context context, int type) {
        Intent intent = new Intent(context, MapLineActivity.class);
        intent.putExtra("motionType", type);
        context.startActivity(intent);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void initView() {
        mActivityMapLineBinding.viewBg.setAlpha(0.45f);
        switch (motionType) {
            case Config.SPORT_OUTDOOR:
                mActivityMapLineBinding.ivType.setImageDrawable(getResources().getDrawable(R.drawable.details_item1_white));
                break;
            case Config.SPORT_INDOOR:
                mActivityMapLineBinding.ivType.setImageDrawable(getResources().getDrawable(R.drawable.details_item2_white));
                break;
            case Config.SPORT_COMPETITION:
                mActivityMapLineBinding.ivType.setImageDrawable(getResources().getDrawable(R.drawable.details_item3_white));
                break;
            default:
        }
        if (DetailsHomeActivity.mMotionDetailsResponse != null && DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo() != null) {
            MotionDetailsResponse.MotionCyclingResponseVoBean motionCyclingResponseVo = DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo();
            if ("0".equals(motionCyclingResponseVo.getTimeType())) {
                mActivityMapLineBinding.tvMapDate.setText(AppUtils.timeToString(motionCyclingResponseVo.getActivityDate(), Config.TIME_PATTERN));
            } else {
                mActivityMapLineBinding.tvMapDate.setText(AppUtils.zeroTimeToString(motionCyclingResponseVo.getActivityDate(), Config.TIME_PATTERN));
            }
            distanceBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(MapLineActivity.this, motionCyclingResponseVo.getDistance());
            mActivityMapLineBinding.ctvDistance.setText(distanceBean.getValue());
            mActivityMapLineBinding.tvDistanceUnit.setText(distanceBean.getUnit());
            mActivityMapLineBinding.tvTime.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(motionCyclingResponseVo.getActivityTime()));
            UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(MapLineActivity.this, (double) motionCyclingResponseVo.getAvgSpeed() / 10);
            mActivityMapLineBinding.tvSpeed.setText(speedSetting.getValue() + speedSetting.getUnit());
            mActivityMapLineBinding.tvCal.setText(motionCyclingResponseVo.getTotalCalories() + getString(R.string.unit_cal));
            if (motionCyclingResponseVo.getAscent() != null) {
                UnitBean altitudeSetting = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(MapLineActivity.this, motionCyclingResponseVo.getAscent());
                mActivityMapLineBinding.tvAltitude.setText(altitudeSetting.getValue() + altitudeSetting.getUnit());
            }

            String latLonVos = motionCyclingResponseVo.getMotionCyclingRecordPo().getLatLonVos();
            List<List<String>> coordinateList = new Gson().fromJson(latLonVos, new TypeToken<List<List<String>>>() {
            }.getType());
            totalSize = coordinateList.size();
            mActivityMapLineBinding.mapElevation.updateView(coordinateList, motionCyclingResponseVo.getMinAltitude(), motionCyclingResponseVo.getMaxAltitude());
        }
    }

    private List<List<String>> coordinateList;
    private Icon startIcon;
    private Icon endIcon;
    private LatLngBounds latLngBounds;
    private List<LatLng> mData;
    private List<LatLng> allData;

    private void showMap() {
        mapboxMap.setStyle(Style.SATELLITE, style -> {
            UiSettings uiSettings = mapboxMap.getUiSettings();
            uiSettings.setCompassEnabled(false);
            uiSettings.setAttributionEnabled(false);
            uiSettings.setScrollGesturesEnabled(false);
            uiSettings.setZoomGesturesEnabled(false);
            uiSettings.setRotateGesturesEnabled(false);
            //设置本地化
            LocalizationPlugin localizationPlugin = new LocalizationPlugin(mActivityMapLineBinding.mapView, mapboxMap, style);
            //将地图与设备语言匹配
            localizationPlugin.setMapLanguage(MapLocale.LOCAL_NAME);

            String latLonVos = DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo().getMotionCyclingRecordPo().getLatLonVos();
            coordinateList = new Gson().fromJson(latLonVos, new TypeToken<List<List<String>>>() {
            }.getType());

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            allData = new ArrayList<>();
            for (List<String> strings : coordinateList) {
                allData.add(new LatLng(AppUtils.stringToDouble(strings.get(0)), AppUtils.stringToDouble(strings.get(1))));
                builder.include(new LatLng(AppUtils.stringToDouble(strings.get(0)), AppUtils.stringToDouble(strings.get(1))));
            }
            Bitmap startBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_start), AppUtils.dipToPx(MapLineActivity.this, 25), AppUtils.dipToPx(MapLineActivity.this, 36), true);
            Bitmap endBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_end), AppUtils.dipToPx(MapLineActivity.this, 25), AppUtils.dipToPx(MapLineActivity.this, 36), true);

            IconFactory iconFactory = IconFactory.getInstance(MapLineActivity.this);
            startIcon = iconFactory.fromBitmap(startBitmap);
            endIcon = iconFactory.fromBitmap(endBitmap);

            mapboxMap.addMarker(new MarkerOptions().position(new LatLng(AppUtils.stringToDouble(coordinateList.get(0).get(0)), AppUtils.stringToDouble(coordinateList.get(0).get(1)))).icon(startIcon));
            mapboxMap.addMarker(new MarkerOptions().position(new LatLng(AppUtils.stringToDouble(coordinateList.get(coordinateList.size() - 1).get(0)), AppUtils.stringToDouble(coordinateList.get(coordinateList.size() - 1).get(1)))).icon(endIcon));

            mapboxMap.addPolyline(new PolylineOptions()
                    .addAll(allData)
                    .color(getResources().getColor(R.color.main_color))
                    .width(4));
            latLngBounds = builder.build();
            mapboxMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 30, 30, AppUtils.dipToPx(MapLineActivity.this, 20)));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mActivityMapLineBinding.mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityMapLineBinding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActivityMapLineBinding.mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mActivityMapLineBinding.mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mActivityMapLineBinding.mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mActivityMapLineBinding.mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityMapLineBinding.mapView.onDestroy();
        if (mMapConnection != null) {
            unbindService(mMapConnection);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_start:
                startView();
                break;
            case R.id.iv_map_back:
                finish();
                break;
            case R.id.tv_save:
                if (isSave == 2) {
                    startView();
                } else {
                    String[] perms = new String[]{
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                    };
                    if (EasyPermissions.hasPermissions(MapLineActivity.this, perms)) {
                        start();
                    } else {
                        //第二个参数是被拒绝后再次申请该权限的解释
                        //第三个参数是请求码
                        //第四个参数是要申请的权限
                        EasyPermissions.requestPermissions(MapLineActivity.this, getResources().getString(R.string.authorization), 0, perms);
                    }
                }
                break;
        }
    }

    private void startView() {
        if (coordinateList == null || coordinateList.size() == 0) {
            ToastUtils.show(this, getString(R.string.map_loading));
            return;
        }
        mActivityMapLineBinding.ivMapBack.setVisibility(View.INVISIBLE);
        mActivityMapLineBinding.ivStart.setVisibility(View.GONE);
        mActivityMapLineBinding.tvSave.setVisibility(View.GONE);
        mActivityMapLineBinding.mapElevation.setVisibility(View.VISIBLE);
        mActivityMapLineBinding.mapElevation.endView();
        mActivityMapLineBinding.llVideoShare.setVisibility(View.GONE);
        alpha();
        move();
        moveTime();
        moveLeft();
        autoIncrement(mActivityMapLineBinding.ctvDistance, AppUtils.stringToFloat(mActivityMapLineBinding.ctvDistance.getText().toString().replace(",", ".")), 0, 2000);
        mapboxMap.removeAnnotations();
        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(AppUtils.stringToDouble(coordinateList.get(0).get(0)), AppUtils.stringToDouble(coordinateList.get(0).get(1))), 15), 2000);
        mData = new ArrayList<>();
        mData.clear();
        mHandler.postDelayed(() -> {
            if (totalSize > 3000) {
                totalTime = totalSize / 400 * 10;
            } else if (totalSize > 2000) {
                totalTime = totalSize / 250 * 10;
            } else if (totalSize > 1000) {
                totalTime = totalSize / 250 * 10;
            } else {
                totalTime = 8 * 10;
            }
            mActivityMapLineBinding.mapElevation.startView(totalTime);
            Message message = mHandler.obtainMessage();
            message.what = 0;
            message.arg1 = 0;
            mHandler.sendMessage(message);
        }, 2000);

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int progress = msg.arg1;
            if (msg.arg1 != totalTime) {
                UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(MapLineActivity.this, DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo().getDistance() / totalTime * progress);
                mActivityMapLineBinding.ctvDistanceBottom.setText(unitBean.getValue());
                if (progress == 0) {
                    mapboxMap.addMarker(new MarkerOptions().position(new LatLng(AppUtils.stringToDouble(coordinateList.get(0).get(0)), AppUtils.stringToDouble(coordinateList.get(0).get(1)))).icon(startIcon));
                }
                int start = totalSize * progress / totalTime;
                int end = totalSize * (progress + 1) / totalTime;

                for (int i = start; i < end; i++) {
                    mData.add(new LatLng(AppUtils.stringToDouble(coordinateList.get(i).get(0)), AppUtils.stringToDouble(coordinateList.get(+i).get(1))));
                }
                mapboxMap.addPolyline(new PolylineOptions()
                        .addAll(mData)
                        .color(getResources().getColor(R.color.main_color))
                        .width(4));
                mapboxMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, progress * mChange, progress * mChange * 2, AppUtils.dipToPx(MapLineActivity.this, 20)));

                mHandler.postDelayed(() -> {
                    Message message = mHandler.obtainMessage();
                    message.what = 0;
                    message.arg1 = progress + 1;
                    mHandler.sendMessage(message);
                }, 100);

            } else {
                mActivityMapLineBinding.ctvDistanceBottom.setText(distanceBean.getValue());
                mapboxMap.addMarker(new MarkerOptions().position(new LatLng(AppUtils.stringToDouble(coordinateList.get(coordinateList.size() - 1).get(0)), AppUtils.stringToDouble(coordinateList.get(coordinateList.size() - 1).get(1)))).icon(endIcon));
//                for (int i = 0; i < coordinateList.size() - progress * 99; i++) {
//                    mData.add(new LatLng(AppUtils.stringToDouble(coordinateList.get(progress * 99 + i).get(0)), AppUtils.stringToDouble(coordinateList.get(progress * 99 + i).get(1))));
//                }
                mapboxMap.addPolyline(new PolylineOptions()
                        .addAll(allData)
                        .color(getResources().getColor(R.color.main_color))
                        .width(4));
                mapboxMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 30, 30, AppUtils.dipToPx(MapLineActivity.this, 20)), 1000);
                mHandler.postDelayed(() -> {
                    mActivityMapLineBinding.llDistance.setVisibility(View.VISIBLE);
                    mActivityMapLineBinding.llDistanceBottom.setVisibility(View.GONE);
                    mActivityMapLineBinding.ctvDistance.setText(distanceBean.getValue());
                    mActivityMapLineBinding.tvDistanceUnitBottom.setText(distanceBean.getUnit());
                    moveRight();
                    mActivityMapLineBinding.ivMapBack.setVisibility(View.VISIBLE);
                    returnAlpha();
                    returnMoveTime();
                }, 500);
                mHandler.postDelayed(() -> {
                    if (isSave == 1) {
                        binder.stop();
                        unbindService(mMapConnection);
                        mMapConnection = null;
                        binder.stopSelf();
                        isSave = 2;
                        AppUtils.insertVideoToMediaStore(MapLineActivity.this, filePath);
                    }
                    mActivityMapLineBinding.ivStart.setVisibility(View.VISIBLE);
                    mActivityMapLineBinding.mapElevation.setVisibility(View.GONE);
                    if (isSave == 2) {
                        mActivityMapLineBinding.tvSave.setVisibility(View.GONE);
                        mActivityMapLineBinding.llVideoShare.setVisibility(View.VISIBLE);
                    }
                    if (isSave == 0) {
                        mActivityMapLineBinding.llVideoShare.setVisibility(View.GONE);
                        mActivityMapLineBinding.tvSave.setVisibility(View.VISIBLE);
                    }
                }, 1500);
            }
        }
    };

    private void alpha() {
        //第一个参数开始的透明度，第二个参数结束的透明度
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0);
        //多长时间完成这个动作
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setFillEnabled(true);
        mActivityMapLineBinding.viewBg.startAnimation(alphaAnimation);
    }

    private void returnAlpha() {
        //第一个参数开始的透明度，第二个参数结束的透明度
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1f);
        //多长时间完成这个动作
        alphaAnimation.setDuration(100);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setFillEnabled(true);
        mActivityMapLineBinding.viewBg.startAnimation(alphaAnimation);
    }


    private void move() {
        int[] viewLocation = new int[2];
        mActivityMapLineBinding.llDistance.getLocationInWindow(viewLocation);
        // y 坐标
        int lldownY = viewLocation[1];
        int[] view_Location = new int[2];
        mActivityMapLineBinding.llDistanceBottom.getLocationInWindow(view_Location);
        // y 坐标
        int addressitTeaddY = view_Location[1];
        int Height = addressitTeaddY - lldownY;
        //平移
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, Height);
        //前两个参数是设置x轴的起止位置，后两个参数设置y轴的起止位置
        animation.setDuration(2000);
        mActivityMapLineBinding.llDistance.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mActivityMapLineBinding.llDistance.setVisibility(View.GONE);
                mActivityMapLineBinding.llDistanceBottom.setVisibility(View.VISIBLE);
                mActivityMapLineBinding.tvDistanceUnitBottom.setText(distanceBean.getUnit());
                mActivityMapLineBinding.ctvDistanceBottom.setText("0.00");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void moveTime() {
        //平移
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -AppUtils.dipToPx(this, 50));
        //前两个参数是设置x轴的起止位置，后两个参数设置y轴的起止位置
        animation.setDuration(2000);
        animation.setFillAfter(true);
        animation.setFillEnabled(true);
        mActivityMapLineBinding.llType.startAnimation(animation);
    }

    private void returnMoveTime() {
        //平移
        TranslateAnimation animation = new TranslateAnimation(0, 0, -AppUtils.dipToPx(this, 50), 0);
        //前两个参数是设置x轴的起止位置，后两个参数设置y轴的起止位置
        animation.setDuration(100);
        animation.setFillAfter(true);
        animation.setFillEnabled(true);
        mActivityMapLineBinding.llType.startAnimation(animation);
    }

    private void moveLeft() {
        //平移
        TranslateAnimation animation = new TranslateAnimation(0, -mActivityMapLineBinding.llTime.getMeasuredWidth(), 0, 0);
        //前两个参数是设置x轴的起止位置，后两个参数设置y轴的起止位置
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setFillEnabled(true);
        mActivityMapLineBinding.llTime.startAnimation(animation);
        //平移
        animation = new TranslateAnimation(0, -mActivityMapLineBinding.llCal.getMeasuredWidth(), 0, 0);
        //前两个参数是设置x轴的起止位置，后两个参数设置y轴的起止位置
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setFillEnabled(true);
        mActivityMapLineBinding.llCal.startAnimation(animation);
        //平移
        animation = new TranslateAnimation(0, -mActivityMapLineBinding.llSpeed.getMeasuredWidth(), 0, 0);
        //前两个参数是设置x轴的起止位置，后两个参数设置y轴的起止位置
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setFillEnabled(true);
        mActivityMapLineBinding.llSpeed.startAnimation(animation);
        //平移
        animation = new TranslateAnimation(0, -mActivityMapLineBinding.llAltitude.getMeasuredWidth(), 0, 0);
        //前两个参数是设置x轴的起止位置，后两个参数设置y轴的起止位置
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setFillEnabled(true);
        mActivityMapLineBinding.llAltitude.startAnimation(animation);
    }

    private void moveRight() {
        //平移
        TranslateAnimation animation = new TranslateAnimation(-mActivityMapLineBinding.llSpeed.getMeasuredWidth(), 0, 0, 0);
        //前两个参数是设置x轴的起止位置，后两个参数设置y轴的起止位置
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setFillEnabled(true);
        mActivityMapLineBinding.llTime.startAnimation(animation);
        mActivityMapLineBinding.llCal.startAnimation(animation);
        mActivityMapLineBinding.llSpeed.startAnimation(animation);
        mActivityMapLineBinding.llAltitude.startAnimation(animation);
    }

    private void autoIncrement(final TextView target, final float start, final float end, int during) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private FloatEvaluator evalutor = new FloatEvaluator();
            private DecimalFormat format = new DecimalFormat("####0.0#");

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                float currentValue = evalutor.evaluate(fraction, start, end);
                target.setText(format.format(currentValue));
            }
        });
        animator.setDuration(during);
        animator.start();
    }

    public void start() {
        MediaProjectionManager projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        Intent captureIntent = projectionManager.createScreenCaptureIntent();
        startActivityForResult(captureIntent, 1);
    }

    private String filePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            mActivityMapLineBinding.ivStart.setVisibility(View.GONE);
            mActivityMapLineBinding.tvSave.setVisibility(View.GONE);
            filePath = getExternalFilesDir("") + File.separator + AppUtils.timeToString(System.currentTimeMillis(), Config.DEFAULT_PATTERN_COMPLETE) + ".mp4";
            Intent intent = new Intent(MapLineActivity.this, RecordService.class);
            intent.putExtra("code", Activity.RESULT_OK);
            intent.putExtra("data", data);
            intent.putExtra("path", filePath);
            intent.putExtra("dpi", dpiValue);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
            mMapConnection = new MapConnection();
            bindService(intent, mMapConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private MapConnection mMapConnection;

    private class MapConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isSave = 1;
            RecordService.RecordBinder recordBinder = (RecordService.RecordBinder) service;
            binder = recordBinder.getService();
            try {
                binder.startRecord();
                startView();
            } catch (Exception e) {
                ToastUtils.show(MapLineActivity.this, getString(R.string.failed_obtain_permission));
                finish();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
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
        start();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        finish();
    }
}
