package com.meilancycling.mema.ui.sensor;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.lion.common.eventbus.MessageEvent;
import com.lion.common.eventbus.sensor.SensorMessageType;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.UiSettings;
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin;
import com.mapbox.mapboxsdk.plugins.localization.MapLocale;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.databinding.FragmentSensorMapBinding;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.service.SensorControllerService;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 作用描述
 * @Author: lion 571135591
 * @CreateDate: 2021/6/17 3:08 下午
 */
public class SensorMapFragment extends BaseFragment {

    private FragmentSensorMapBinding mFragmentSensorMapBinding;
    private MapboxMap mMapboxMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mapbox.getInstance(getContext(), getString(R.string.mapbox_access_token));
        mFragmentSensorMapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sensor_map, container, false);
        return mFragmentSensorMapBinding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSensorMapBinding.sensorMap.onCreate(savedInstanceState);
        mFragmentSensorMapBinding.sensorMap.getMapAsync(mapboxMap -> {
            mMapboxMap = mapboxMap;
            mapboxMap.setStyle(Style.MAPBOX_STREETS, this::mapSetting);
        });

        if (Config.unit == Unit.METRIC.value) {
            mFragmentSensorMapBinding.tvDistanceUnit.setText("(" + getString(R.string.unit_km) + ")");
            mFragmentSensorMapBinding.tvSpeedUnit.setText("(" + getString(R.string.unit_kmh) + ")");
        } else {
            mFragmentSensorMapBinding.tvDistanceUnit.setText("(" + getString(R.string.unit_mile) + ")");
            mFragmentSensorMapBinding.tvSpeedUnit.setText("(" + getString(R.string.unit_mph) + ")");
        }

        mFragmentSensorMapBinding.viewBack.setOnClickListener(v -> {
            try {
                ((SensorHomeActivity) getActivity()).selectHomeFragment();
            } catch (Exception e) {

            }
            // Navigation.findNavController(view).navigate(R.id.sensor_home);
        });
        EventBus.getDefault().register(this);
    }

    private void mapSetting(Style style) {
        UiSettings uiSettings = mMapboxMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setAttributionEnabled(false);

        //设置本地化
        LocalizationPlugin localizationPlugin = new LocalizationPlugin(mFragmentSensorMapBinding.sensorMap, mMapboxMap, style);
        //将地图与设备语言匹配
        localizationPlugin.setMapLanguage(MapLocale.LOCAL_NAME);
        mMapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.6313401522802,
                113.86232987140626), 16));
        if (SensorControllerService.mLocation != null) {
            showCurrentLocation(new LatLng(SensorControllerService.mLocation.getLatitude(), SensorControllerService.mLocation.getLongitude()));
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mFragmentSensorMapBinding.sensorMap.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentSensorMapBinding.sensorMap.onResume();
        mHandler.sendEmptyMessage(0);
    }

    @Override
    public void onPause() {
        super.onPause();
        mFragmentSensorMapBinding.sensorMap.onPause();
        mHandler.removeMessages(0);
    }

    @Override
    public void onStop() {
        super.onStop();
        mFragmentSensorMapBinding.sensorMap.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mFragmentSensorMapBinding.sensorMap.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mFragmentSensorMapBinding.sensorMap.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFragmentSensorMapBinding.sensorMap.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getType() == SensorMessageType.ACTION_LOCATION_CHANGE) {
            Location message = (Location) event.getMessage();
            mMapboxMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(message.getLatitude(), message.getLongitude())));
        }
    }

    private Bitmap currentBitmap;
    private Bitmap startBitmap;
    private List<LatLng> data;

    private void showCurrentLocation(LatLng latLng) {
        if (mMapboxMap == null) {
            return;
        }
        mMapboxMap.removeAnnotations();
        if (currentBitmap == null) {
            currentBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sensor_location), AppUtils.dipToPx(mActivity, 26), AppUtils.dipToPx(mActivity, 26), true);
        }

        IconFactory iconFactory = IconFactory.getInstance(mActivity);
        Icon currentIcon = iconFactory.fromBitmap(currentBitmap);
        mMapboxMap.addMarker(new MarkerOptions().position(latLng).icon(currentIcon));

        if (SensorControllerService.startLatitude != 0 && SensorControllerService.startLongitude != 0) {
            if (startBitmap == null) {
                startBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_start), AppUtils.dipToPx(mActivity, 25), AppUtils.dipToPx(mActivity, 36), true);
            }
            iconFactory = IconFactory.getInstance(mActivity);
            Icon startIcon = iconFactory.fromBitmap(startBitmap);
            mMapboxMap.addMarker(new MarkerOptions().position(new LatLng(SensorControllerService.startLatitude, SensorControllerService.startLongitude)).icon(startIcon));
        }
        if (data == null) {
            data = new ArrayList<>();
        }
        data.clear();
        if (SensorControllerService.latitudeList != null && SensorControllerService.latitudeList.size() > 0) {
            for (int i = 0; i < SensorControllerService.latitudeList.size(); i++) {
                data.add(new LatLng(SensorControllerService.latitudeList.get(i), SensorControllerService.longitudeList.get(i)));
            }
        }
        mMapboxMap.addPolyline(new PolylineOptions()
                .addAll(data)
                .color(getResources().getColor(R.color.main_color))
                .width(4));
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            mHandler.sendEmptyMessageDelayed(0, 500);
            if (SensorControllerService.mLocation != null) {
                showCurrentLocation(new LatLng(SensorControllerService.mLocation.getLatitude(), SensorControllerService.mLocation.getLongitude()));
            }
            if (SensorControllerService.mRealtimeBean.getSpeed() != 0xFFFF) {
                UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) SensorControllerService.mRealtimeBean.getSpeed() / 10);
                mFragmentSensorMapBinding.ctvSpeed.setText(speedSetting.getValue());
            } else {
                mFragmentSensorMapBinding.ctvSpeed.setText("0.0");
            }

            UnitBean distanceSetting = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), SensorControllerService.sessionData.getDist_travelled());
            mFragmentSensorMapBinding.ctvDistance.setText(distanceSetting.getValue());

            mFragmentSensorMapBinding.ctvTime.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(SensorControllerService.sessionData.getActivity_time()));
        }
    };
}
