package com.meilancycling.mema.ui.home.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.HomeDeviceViewBinding;
import com.meilancycling.mema.ui.device.DeviceManageActivity;
import com.meilancycling.mema.ui.device.DeviceSettingActivity;
import com.meilancycling.mema.ui.device.SelectDeviceActivity;
import com.meilancycling.mema.ui.home.DeviceModel;
import com.meilancycling.mema.ui.home.HomeViewModel;


/**
 * @Description: 首页目标进度条
 * @Author: sore_lion
 * @CreateDate: 2020/11/11 17:51 PM
 */
public class HomeDeviceView extends LinearLayout {
    private HomeDeviceViewBinding mHomeDeviceViewBinding;
    private DeviceModel mDeviceModel;
    private Context mContext;

    public HomeDeviceView(Context context) {
        this(context, null);
    }

    public HomeDeviceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeDeviceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mHomeDeviceViewBinding = DataBindingUtil.inflate(inflater, R.layout.home_device_view, this, false);
            this.addView(mHomeDeviceViewBinding.getRoot());
        }

        mHomeDeviceViewBinding.getRoot().setOnClickListener(v -> {
            if (TextUtils.isEmpty(mDeviceModel.getProductNo())) {
                mContext.startActivity(new Intent(mContext, SelectDeviceActivity.class));
            } else {
                mContext.startActivity(new Intent(mContext, DeviceSettingActivity.class));
            }
        });

        mHomeDeviceViewBinding.viewMyDevice.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, DeviceManageActivity.class)));

//        Animation rotateAnimation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_anim);
//        LinearInterpolator lin = new LinearInterpolator();
//        rotateAnimation.setInterpolator(lin);
//        mHomeDeviceViewBinding.iv3.startAnimation(rotateAnimation);
    }

    public void bindData(HomeViewModel homeViewModel) {
        mDeviceModel = homeViewModel.deviceData.getValue();
        mHomeDeviceViewBinding.setModel(homeViewModel.deviceData.getValue());
        if (mDeviceModel != null) {
            boolean showConnecting = mDeviceModel.isShowConnecting(mDeviceModel.getProductNo(), mDeviceModel.isBleStatus(), mDeviceModel.getDeviceStatus(), mDeviceModel.getUpdateStatus());
            if (showConnecting) {
                Animation rotateAnimation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_anim);
                LinearInterpolator lin = new LinearInterpolator();
                rotateAnimation.setInterpolator(lin);
                mHomeDeviceViewBinding.iv3.startAnimation(rotateAnimation);
            } else {
                mHomeDeviceViewBinding.iv3.clearAnimation();
            }
        }
    }


}
