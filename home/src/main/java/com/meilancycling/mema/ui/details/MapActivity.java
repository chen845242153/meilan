package com.meilancycling.mema.ui.details;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

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
import com.mapbox.mapboxsdk.style.layers.FillExtrusionLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.network.bean.LapBean;
import com.meilancycling.mema.network.bean.MapBean;
import com.meilancycling.mema.databinding.ActivityMapBinding;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.StatusAppUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillExtrusionColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillExtrusionHeight;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillExtrusionOpacity;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/24 2:16 PM
 */
public class MapActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMapBinding mActivityMapBinding;
    private MapboxMap mapboxMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        mActivityMapBinding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        StatusAppUtils.setTranslucentForImageView(this, 0, null);
        mActivityMapBinding.mapView.onCreate(savedInstanceState);
        mActivityMapBinding.mapView.getMapAsync(mapboxMap -> {
            MapActivity.this.mapboxMap = mapboxMap;
            showType1Map();
        });

        mActivityMapBinding.ivMapClose.setOnClickListener(this);
        mActivityMapBinding.ivMapSelect.setOnClickListener(this);
        mActivityMapBinding.mbvSelectMap.initView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mActivityMapBinding.mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityMapBinding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActivityMapBinding.mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mActivityMapBinding.mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mActivityMapBinding.mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mActivityMapBinding.mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityMapBinding.mapView.onDestroy();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_map_close:
                finish();
                break;
            case R.id.iv_map_select:
                if (mActivityMapBinding.mbvSelectMap.getVisibility() == View.VISIBLE) {
                    mActivityMapBinding.mbvSelectMap.setVisibility(View.GONE);
                } else {
                    mActivityMapBinding.mbvSelectMap.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_map_item1:
                mActivityMapBinding.mbvSelectMap.setSelect(0);
                mActivityMapBinding.mbvSelectMap.setVisibility(View.GONE);
                showType1Map();
                break;
            case R.id.ll_map_item2:
                mActivityMapBinding.mbvSelectMap.setSelect(1);
                mActivityMapBinding.mbvSelectMap.setVisibility(View.GONE);
                showType2Map();
                break;
            case R.id.ll_map_item3:
                mActivityMapBinding.mbvSelectMap.setSelect(2);
                mActivityMapBinding.mbvSelectMap.setVisibility(View.GONE);
                showType3Map();
                break;
            case R.id.ll_map_item4:
                mActivityMapBinding.mbvSelectMap.setSelect(3);
                mActivityMapBinding.mbvSelectMap.setVisibility(View.GONE);
                showType4Map();
                break;
        }
    }

    /**
     * 显示标准地图
     */
    private void showType1Map() {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, this::mapSetting);
    }

    /**
     * 显示卫星地图
     */
    private void showType2Map() {
        mapboxMap.setStyle(Style.SATELLITE_STREETS, this::mapSetting);
    }

    /**
     * 显示混合地图
     */
    private void showType3Map() {
        mapboxMap.setStyle(Style.TRAFFIC_DAY, this::mapSetting);
    }

    /**
     * 显示海拔地图
     */
    private void showType4Map() {
        mapboxMap.setStyle(Style.SATELLITE, style -> {
            UiSettings uiSettings = mapboxMap.getUiSettings();
            uiSettings.setCompassEnabled(false);
            uiSettings.setAttributionEnabled(false);
            //设置本地化
            LocalizationPlugin localizationPlugin = new LocalizationPlugin(mActivityMapBinding.mapView, mapboxMap, style);

            //将地图与设备语言匹配
            localizationPlugin.setMapLanguage(MapLocale.LOCAL_NAME);
            String latLonVos = DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo().getMotionCyclingRecordPo().getLatLonVos();
            List<List<String>> coordinateList = new Gson().fromJson(latLonVos, new TypeToken<List<List<String>>>() {
            }.getType());
            if (coordinateList != null && coordinateList.size() > 0 & coordinateList.get(0).size() > 2) {
                int minAltitude = DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo().getMinAltitude();
                int maxAltitude = DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo().getMaxAltitude();
                int targetHeight = 1000;
                int zoom = 0;
                if (maxAltitude != minAltitude) {
                    zoom = targetHeight / (maxAltitude - minAltitude);
                }

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                List<LatLng> data = new ArrayList<>();
                MapBean mapBean = new MapBean();
                List<MapBean.FeaturesBean> altData = new ArrayList<>();
                mapBean.setType("FeatureCollection");
                double diff = 0.00005;
                for (int j = 1; j < coordinateList.size(); j++) {
                    MapBean.FeaturesBean featuresBean = new MapBean.FeaturesBean();
                    featuresBean.setType("Feature");

                    MapBean.FeaturesBean.GeometryBean geometryBean = new MapBean.FeaturesBean.GeometryBean();
                    geometryBean.setType("Polygon");

                    List<List<List<Double>>> coordinates = new ArrayList<>();
                    List<Double> lastPoint = new ArrayList<>();
                    lastPoint.add(AppUtils.stringToDouble (coordinateList.get(j - 1).get(1)));
                    lastPoint.add(AppUtils.stringToDouble (coordinateList.get(j - 1).get(0)));

                    List<Double> lastPointLeft = new ArrayList<>();
                    lastPointLeft.add(AppUtils.stringToDouble(coordinateList.get(j - 1).get(1)));
                    lastPointLeft.add(AppUtils.stringToDouble(coordinateList.get(j - 1).get(0)) - diff);

                    List<Double> lastPointBottom = new ArrayList<>();
                    lastPointBottom.add(AppUtils.stringToDouble(coordinateList.get(j - 1).get(1)) - diff);
                    lastPointBottom.add(AppUtils.stringToDouble(coordinateList.get(j - 1).get(0)));

                    List<Double> currentPoint = new ArrayList<>();
                    currentPoint.add(AppUtils.stringToDouble(coordinateList.get(j).get(1)));
                    currentPoint.add(AppUtils.stringToDouble(coordinateList.get(j).get(0)));

                    List<Double> currentPointPointLeft = new ArrayList<>();
                    currentPointPointLeft.add(AppUtils.stringToDouble(coordinateList.get(j).get(1)));
                    currentPointPointLeft.add(AppUtils.stringToDouble(coordinateList.get(j).get(0)) - diff);

                    List<Double> currentPointPointBottom = new ArrayList<>();
                    currentPointPointBottom.add(AppUtils.stringToDouble(coordinateList.get(j).get(1)) - diff);
                    currentPointPointBottom.add(AppUtils.stringToDouble(coordinateList.get(j).get(0)));
                    List<List<Double>> lists = new ArrayList<>();

                    lists.add(lastPoint);
                    lists.add(lastPointLeft);
                    lists.add(lastPointBottom);
                    lists.add(currentPoint);
                    lists.add(currentPointPointLeft);
                    lists.add(currentPointPointBottom);
                    coordinates.add(lists);

                    geometryBean.setType("Polygon");
                    geometryBean.setCoordinates(coordinates);

                    featuresBean.setGeometry(geometryBean);

                    MapBean.FeaturesBean.PropertiesBean propertiesBean = new MapBean.FeaturesBean.PropertiesBean();
                    propertiesBean.setE((AppUtils.stringToDouble(coordinateList.get(j).get(3)) - minAltitude) * zoom);
                    featuresBean.setProperties(propertiesBean);


                    data.add(new LatLng(AppUtils.stringToDouble(coordinateList.get(j).get(0)), AppUtils.stringToDouble(coordinateList.get(j).get(1))));
                    builder.include(new LatLng(AppUtils.stringToDouble(coordinateList.get(j).get(0)), AppUtils.stringToDouble(coordinateList.get(j).get(1))));

                    altData.add(featuresBean);
                }
                mapBean.setFeatures(altData);

                Bitmap startBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_start), AppUtils.dipToPx(MapActivity.this, 25), AppUtils.dipToPx(MapActivity.this, 36), true);
                Bitmap endBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_end), AppUtils.dipToPx(MapActivity.this, 25), AppUtils.dipToPx(MapActivity.this, 36), true);

                IconFactory iconFactory = IconFactory.getInstance(MapActivity.this);
                Icon startIcon = iconFactory.fromBitmap(startBitmap);
                Icon endIcon = iconFactory.fromBitmap(endBitmap);

                mapboxMap.addMarker(new MarkerOptions().position(new LatLng(AppUtils.stringToDouble(coordinateList.get(0).get(0)), AppUtils.stringToDouble(coordinateList.get(0).get(1)))).icon(startIcon));
                mapboxMap.addMarker(new MarkerOptions().position(new LatLng(AppUtils.stringToDouble(coordinateList.get(coordinateList.size() - 1).get(0)), AppUtils.stringToDouble(coordinateList.get(coordinateList.size() - 1).get(1)))).icon(endIcon));
                mapboxMap.addPolyline(new PolylineOptions()
                        .addAll(data)
                        .color(getResources().getColor(R.color.main_color))
                        .width(4));
                LatLngBounds latLngBounds = builder.build();
                mapboxMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, AppUtils.dipToPx(MapActivity.this, 20), 100, AppUtils.dipToPx(MapActivity.this, 20)));
                GeoJsonSource courseRouteGeoJson = new GeoJsonSource("coursedata", new Gson().toJson(mapBean));
                style.addSource(courseRouteGeoJson);
                // Add FillExtrusion layer to map using GeoJSON data
                style.addLayer(new FillExtrusionLayer("course", "coursedata").withProperties(
                        fillExtrusionColor(Color.parseColor("#928F09")),
                        fillExtrusionOpacity(0.9f),
                        fillExtrusionHeight(get("e"))));
            }
        });
    }

    public Bitmap drawTextToBitmap(String gText) {
        float scale = getResources().getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map_flag);
        Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(61, 61, 61));
        paint.setTextSize((int) (12 * scale));
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;
        canvas.drawText(gText, x, y, paint);
        return bitmap;
    }

    private void mapSetting(Style style) {
        UiSettings uiSettings = mapboxMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setAttributionEnabled(false);
        //设置本地化
        LocalizationPlugin localizationPlugin = new LocalizationPlugin(mActivityMapBinding.mapView, mapboxMap, style);
        //将地图与设备语言匹配
        localizationPlugin.setMapLanguage(MapLocale.LOCAL_NAME);
        String latLonVos = DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo().getMotionCyclingRecordPo().getLatLonVos();
        List<List<String>> coordinateList = new Gson().fromJson(latLonVos, new TypeToken<List<List<String>>>() {
        }.getType());
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        List<LatLng> data = new ArrayList<>();

        for (List<String> strings : coordinateList) {
            data.add(new LatLng(AppUtils.stringToDouble(strings.get(0)),AppUtils.stringToDouble(strings.get(1))));
            builder.include(new LatLng(AppUtils.stringToDouble(strings.get(0)),AppUtils.stringToDouble(strings.get(1))));
        }
        Bitmap startBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_start), AppUtils.dipToPx(MapActivity.this, 25), AppUtils.dipToPx(MapActivity.this, 36), true);
        Bitmap endBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_end), AppUtils.dipToPx(MapActivity.this, 25), AppUtils.dipToPx(MapActivity.this, 36), true);

        IconFactory iconFactory = IconFactory.getInstance(MapActivity.this);
        Icon startIcon = iconFactory.fromBitmap(startBitmap);
        Icon endIcon = iconFactory.fromBitmap(endBitmap);

        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(AppUtils.stringToDouble(coordinateList.get(0).get(0)), AppUtils.stringToDouble(coordinateList.get(0).get(1)))).icon(startIcon));
        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(AppUtils.stringToDouble(coordinateList.get(coordinateList.size() - 1).get(0)), AppUtils.stringToDouble(coordinateList.get(coordinateList.size() - 1).get(1)))).icon(endIcon));
        String lap = DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo().getMotionCyclingRecordPo().getLap();
        if (!TextUtils.isEmpty(lap)) {
            List<LapBean> lapList = new Gson().fromJson(lap, new TypeToken<List<LapBean>>() {
            }.getType());
            if (lapList != null && lapList.size() > 1) {
                for (int i = lapList.size() - 1; i > 1; i--) {
                    Bitmap bitmap = drawTextToBitmap(String.valueOf(i));
                    if (lapList.get(i).getsLatitude()!=null&&lapList.get(i).getsLongitude()!=null){
                        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(AppUtils.stringToDouble(lapList.get(i).getsLatitude()), AppUtils.stringToDouble(lapList.get(i).getsLongitude()))).icon(iconFactory.fromBitmap(bitmap)));
                    }
                }
            }
        }
        mapboxMap.addPolyline(new PolylineOptions()
                .addAll(data)
                .color(getResources().getColor(R.color.main_color))
                .width(4));
        LatLngBounds latLngBounds = builder.build();
        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 30, 30, AppUtils.dipToPx(MapActivity.this, 20)));
    }
}
