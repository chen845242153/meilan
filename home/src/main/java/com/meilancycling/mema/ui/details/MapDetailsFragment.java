package com.meilancycling.mema.ui.details;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.databinding.FragmentMapDetailsBinding;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.bean.LapBean;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.response.MotionDetailsResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.ui.adapter.RecordLapAdapter;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 2:17 PM
 */
public class MapDetailsFragment extends BaseFragment {
    private FragmentMapDetailsBinding mFragmentMapDetailsBinding;
    private DetailsHomeActivity baseActivity;
    private List<LapBean> lapList;
    private int clickFlag = 0;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentMapDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_details, container, false);
        return mFragmentMapDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseActivity = (DetailsHomeActivity) getActivity();
        mFragmentMapDetailsBinding.fvDetails.setDataUnit();
        mFragmentMapDetailsBinding.dtvDetails.setDataUnit();
        mFragmentMapDetailsBinding.mvDetails.onCreate(savedInstanceState);
        mFragmentMapDetailsBinding.ivPlayMap.setOnClickListener((View v) -> {
            Intent intent = new Intent(getContext(), MapLineActivity.class);
            intent.putExtra("motionType", baseActivity.motionType);
            startActivity(intent);
        });
        mFragmentMapDetailsBinding.viewMap.setOnClickListener(v -> startActivity(new Intent(getContext(), MapActivity.class)));
        updateType();
        updateUi();

        mFragmentMapDetailsBinding.llType.setOnClickListener(v -> baseActivity.changeType());
    }


    public void updateType() {
        switch (baseActivity.motionType) {
            case Config.SPORT_OUTDOOR:
                mFragmentMapDetailsBinding.ivExerciseType.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.details_item1));
                break;
            case Config.SPORT_INDOOR:
                mFragmentMapDetailsBinding.ivExerciseType.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.details_item2));
                break;
            case Config.SPORT_COMPETITION:
                mFragmentMapDetailsBinding.ivExerciseType.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.details_item3));
                break;
            default:
        }
    }

    private void autoIncrement(final TextView target, final float end) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private FloatEvaluator evalutor = new FloatEvaluator();
            private DecimalFormat format = new DecimalFormat("####0.0#");

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                float currentValue = evalutor.evaluate(fraction, 0, end);
                target.setText(format.format(currentValue));
            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    private int flag;

    private void updateUi() {
        if (DetailsHomeActivity.mMotionDetailsResponse != null && DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo() != null) {
            MotionDetailsResponse.MotionCyclingResponseVoBean motionCyclingResponseVo = DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo();
            if (motionCyclingResponseVo.getAscent() == null) {
                flag = 1;
                mFragmentMapDetailsBinding.dtvDetails.setVisibility(View.VISIBLE);
                mFragmentMapDetailsBinding.fvDetails.setVisibility(View.GONE);
                mFragmentMapDetailsBinding.dtvDetails.setDetailsData(motionCyclingResponseVo.getActivityTime(), motionCyclingResponseVo.getAvgSpeed(), motionCyclingResponseVo.getTotalCalories());
            } else {
                flag = 2;
                mFragmentMapDetailsBinding.dtvDetails.setVisibility(View.GONE);
                mFragmentMapDetailsBinding.fvDetails.setVisibility(View.VISIBLE);
                mFragmentMapDetailsBinding.fvDetails.setDetailsTopData(motionCyclingResponseVo.getActivityTime(), motionCyclingResponseVo.getAvgSpeed(), motionCyclingResponseVo.getTotalCalories(), motionCyclingResponseVo.getAscent());
            }
            if ("0".equals(motionCyclingResponseVo.getTimeType())) {
                mFragmentMapDetailsBinding.tvDetailsTime.setText(AppUtils.timeToString(motionCyclingResponseVo.getActivityDate(), Config.TIME_PATTERN));
            } else {
                mFragmentMapDetailsBinding.tvDetailsTime.setText(AppUtils.zeroTimeToString(motionCyclingResponseVo.getActivityDate(), Config.TIME_PATTERN));
            }
            UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), motionCyclingResponseVo.getDistance());
            mFragmentMapDetailsBinding.tvDistanceUnit.setText(unitBean.getUnit());
            autoIncrement(mFragmentMapDetailsBinding.ctvDetailsTotalDistance, AppUtils.stringToFloat(unitBean.getValue().replace(",", ".")));
            if (motionCyclingResponseVo.getMotionCyclingRecordPo() != null) {
                String lap = motionCyclingResponseVo.getMotionCyclingRecordPo().getLap();
                if (!TextUtils.isEmpty(lap)) {
                    try {
                        lapList = new Gson().fromJson(lap, new TypeToken<List<LapBean>>() {
                        }.getType());
                        RecordLapAdapter recordLapAdapter = new RecordLapAdapter(getContext(), lapList);
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        manager.setOrientation(RecyclerView.VERTICAL);
                        mFragmentMapDetailsBinding.mrvLap.setLayoutManager(manager);
                        mFragmentMapDetailsBinding.mrvLap.setAdapter(recordLapAdapter);
                        recordLapAdapter.setLapItemClickListener(() -> {
                            Intent intent = new Intent(mActivity, LapDetailsActivity.class);
                            intent.putExtra("lap", lap);
                            startActivity(intent);
                        });
                    } catch (Exception e) {
                    }
                }
            }
            if (DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo().getMotionCyclingRecordPo() != null) {
                String latLonVos = DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo().getMotionCyclingRecordPo().getLatLonVos();
                List<List<String>> coordinateList = new Gson().fromJson(latLonVos, new TypeToken<List<List<String>>>() {
                }.getType());
                if (coordinateList.size() > 3) {
                    baseActivity.haveMap = true;
                    mFragmentMapDetailsBinding.mvDetails.getMapAsync(mapboxMap -> showMap(mapboxMap, coordinateList));
                    mFragmentMapDetailsBinding.viewLineMap.setVisibility(View.GONE);
                } else {
                    baseActivity.haveMap = false;
                    mFragmentMapDetailsBinding.rlMap.setVisibility(View.GONE);
                    mFragmentMapDetailsBinding.viewLineMap.setVisibility(View.VISIBLE);
                }
            } else {
                baseActivity.haveMap = false;
                mFragmentMapDetailsBinding.rlMap.setVisibility(View.GONE);
                mFragmentMapDetailsBinding.viewLineMap.setVisibility(View.VISIBLE);
            }
        }
        UserInfoEntity userInfoEntity = mActivity.getUserInfoEntity();
        if (((userInfoEntity.getGuideFlag() & 128) >> 7) == Config.NEED_GUIDE) {
            mFragmentMapDetailsBinding.groupGuide1.setVisibility(View.VISIBLE);
            mFragmentMapDetailsBinding.viewBg.setVisibility(View.VISIBLE);
            baseActivity.showGuide();
            ConstraintLayout.LayoutParams mapParams = (ConstraintLayout.LayoutParams) mFragmentMapDetailsBinding.tvGuideMap.getLayoutParams();
            if (baseActivity.haveMap) {
                if (flag == 1) {
                    mapParams.topMargin = AppUtils.dipToPx(mActivity, 280);
                } else {
                    mapParams.topMargin = AppUtils.dipToPx(mActivity, 355);
                }
            } else {
                if (flag == 1) {
                    mapParams.topMargin = AppUtils.dipToPx(mActivity, 80);
                } else {
                    mapParams.topMargin = AppUtils.dipToPx(mActivity, 160);
                }
            }
            mFragmentMapDetailsBinding.tvGuideMap.setLayoutParams(mapParams);

            switch (baseActivity.motionType) {
                case Config.SPORT_OUTDOOR:
                    mFragmentMapDetailsBinding.ivGuide1.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.record_guide_sw));
                    break;
                case Config.SPORT_INDOOR:
                    mFragmentMapDetailsBinding.ivGuide1.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.record_guide_sn));
                    break;
                case Config.SPORT_COMPETITION:
                    mFragmentMapDetailsBinding.ivGuide1.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.record_guide_js));
                    break;
                default:
            }

        }

        mFragmentMapDetailsBinding.viewBg.setOnClickListener(v -> {
            if (clickFlag == 0) {
                clickFlag = 1;
                mFragmentMapDetailsBinding.groupGuide1.setVisibility(View.GONE);
                mFragmentMapDetailsBinding.groupGuide.setVisibility(View.VISIBLE);
            } else {
                mFragmentMapDetailsBinding.groupGuide.setVisibility(View.GONE);
                mFragmentMapDetailsBinding.viewBg.setVisibility(View.GONE);
                baseActivity.hideGuide();
                int guideFlag = userInfoEntity.getGuideFlag();
                userInfoEntity.setGuideFlag(guideFlag & 0x7F);
                mActivity.updateUserInfoEntity(userInfoEntity);
            }

        });
    }

    /**
     * 显示标准地图
     */
    private void showMap(MapboxMap mapboxMap, List<List<String>> listList) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
            UiSettings uiSettings = mapboxMap.getUiSettings();
            uiSettings.setCompassEnabled(false);
            uiSettings.setAttributionEnabled(false);
            uiSettings.setScrollGesturesEnabled(false);
            uiSettings.setZoomGesturesEnabled(false);
            uiSettings.setRotateGesturesEnabled(false);
            //设置本地化
            LocalizationPlugin localizationPlugin = new LocalizationPlugin(mFragmentMapDetailsBinding.mvDetails, mapboxMap, style);
            //将地图与设备语言匹配
            localizationPlugin.setMapLanguage(MapLocale.LOCAL_NAME);
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            List<LatLng> data = new ArrayList<>();
            for (List<String> strings : listList) {
                data.add(new LatLng(AppUtils.stringToDouble(strings.get(0)), AppUtils.stringToDouble(strings.get(1))));
                builder.include(new LatLng(AppUtils.stringToDouble(strings.get(0)), AppUtils.stringToDouble(strings.get(1))));
            }
            Bitmap startBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_start), AppUtils.dipToPx(getContext(), 25), AppUtils.dipToPx(getContext(), 36), true);
            Bitmap endBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_end), AppUtils.dipToPx(getContext(), 25), AppUtils.dipToPx(getContext(), 36), true);

            IconFactory iconFactory = IconFactory.getInstance(getContext());
            Icon startIcon = iconFactory.fromBitmap(startBitmap);
            Icon endIcon = iconFactory.fromBitmap(endBitmap);
            mapboxMap.addMarker(new MarkerOptions().position(new LatLng(AppUtils.stringToDouble(listList.get(0).get(0)), AppUtils.stringToDouble(listList.get(0).get(1)))).icon(startIcon));
            mapboxMap.addMarker(new MarkerOptions().position(new LatLng(AppUtils.stringToDouble(listList.get(listList.size() - 1).get(0)), AppUtils.stringToDouble(listList.get(listList.size() - 1).get(1)))).icon(endIcon));
            if (lapList != null && lapList.size() > 1) {
                for (int i = lapList.size() - 1; i > 1; i--) {
                    Bitmap bitmap = drawTextToBitmap(String.valueOf(i));
                    if (lapList.get(i).getsLatitude() != null && lapList.get(i).getsLongitude() != null) {
                        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(AppUtils.stringToDouble(lapList.get(i).getsLatitude()), AppUtils.stringToDouble(lapList.get(i).getsLongitude()))).icon(iconFactory.fromBitmap(bitmap)));
                    }
                }
            }
            mapboxMap.addPolyline(new PolylineOptions()
                    .addAll(data)
                    .color(getResources().getColor(R.color.main_color))
                    .width(4));
            LatLngBounds latLngBounds = builder.build();
            mapboxMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, AppUtils.dipToPx(getContext(), 10)));
        });
    }

    public Bitmap getBitmap() {
        return getBitmapFromView(mFragmentMapDetailsBinding.getRoot());
    }

    private Bitmap getBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(c);
        } else {
            c.drawColor(Color.WHITE);
        }
        // Draw view to canvas
        v.draw(c);
        return b;
    }

    public Bitmap drawTextToBitmap(String gText) {
        float scale = getResources().getDisplayMetrics().density;
        Bitmap bitmap =
                BitmapFactory.decodeResource(getResources(), R.drawable.map_flag);

        Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        paint.setTextSize((int) (12 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw text to the Canvas center
        Rect bounds = new Rect();
//	    paint.setTextAlign(Align.CENTER);

        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;

//	    canvas.drawText(gText, x * scale, y * scale, paint);
        canvas.drawText(gText, x, y, paint);

        return bitmap;
    }

    @Override
    public void onStart() {
        super.onStart();
        mFragmentMapDetailsBinding.mvDetails.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentMapDetailsBinding.mvDetails.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mFragmentMapDetailsBinding.mvDetails.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFragmentMapDetailsBinding.mvDetails.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mFragmentMapDetailsBinding.mvDetails.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mFragmentMapDetailsBinding.mvDetails.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFragmentMapDetailsBinding.mvDetails.onDestroy();
    }

}
