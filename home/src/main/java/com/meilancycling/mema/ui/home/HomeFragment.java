package com.meilancycling.mema.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.clj.fastble.BleManager;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.Device;
import com.meilancycling.mema.databinding.FragmentHomeBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.bean.request.MotionRequest;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.adapter.HomeDataAdapter;
import com.meilancycling.mema.ui.adapter.HomeDecoration;
import com.meilancycling.mema.ui.device.DeviceSettingActivity;
import com.meilancycling.mema.ui.home.view.DeviceUpgradeDialog;
import com.meilancycling.mema.utils.ToastUtils;

/**
 * @Description: 首页
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class HomeFragment extends BaseFragment {
    FragmentHomeBinding mFragmentHomeBinding;
    private HomeDataAdapter mHomeDataAdapter;
    private HomeActivity activity;
    private UserInfoEntity mUserInfoEntity;
    private HomeViewModel homeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mHomeDataAdapter = new HomeDataAdapter(mActivity);
        mFragmentHomeBinding.rvHome.setLayoutManager(new LinearLayoutManager(mActivity));
        mFragmentHomeBinding.rvHome.addItemDecoration(new HomeDecoration());
        mFragmentHomeBinding.rvHome.setAdapter(mHomeDataAdapter);
        mHomeDataAdapter.setAdapterData(null);
        activity = (HomeActivity) getActivity();
        mActivity.showLoadingDialog();
        getSevenDate();

        mHomeDataAdapter.setHomeUpdateTop(position -> {
            if (position == 0) {
                mFragmentHomeBinding.slFirstShow.setVisibility(View.VISIBLE);
                mFragmentHomeBinding.slContentShow.setVisibility(View.GONE);
            } else {
                mFragmentHomeBinding.slFirstShow.setVisibility(View.GONE);
                mFragmentHomeBinding.slContentShow.setVisibility(View.VISIBLE);
            }
        });

        mUserInfoEntity = mActivity.getUserInfoEntity();
//        if ((mUserInfoEntity.getGuideFlag() & 1) == Config.NEED_GUIDE) {
//            mFragmentHomeBinding.guide.setVisibility(View.VISIBLE);
//            activity.showGuide();
//        } else {
//            mFragmentHomeBinding.guide.setVisibility(View.GONE);
//            activity.hideGuide();
//        }
//        mFragmentHomeBinding.viewBg.setOnClickListener(v -> {
//            mFragmentHomeBinding.guide.setVisibility(View.GONE);
//            activity.hideGuide();
//            int guideFlag = activity.mUserInfoEntity.getGuideFlag();
//            activity.mUserInfoEntity.setGuideFlag(guideFlag & 0xFE);
//            mActivity.updateUserInfoEntity(mUserInfoEntity);
//        });
        mFragmentHomeBinding.srHomeRecord.setColorSchemeColors(ContextCompat.getColor(mActivity, R.color.main_color));
        mFragmentHomeBinding.srHomeRecord.setOnRefreshListener(mRefreshListener);

        if (BleManager.getInstance().isBlueEnable()) {
            if (DeviceControllerService.deviceStatus == Device.DEVICE_CONNECTED) {
                deviceConnected(DeviceControllerService.currentDevice);
            } else {
                deviceConnecting(DeviceControllerService.currentDevice);
            }
        } else {
            bleNotTurnOn(DeviceControllerService.currentDevice);
        }
        return mFragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setBleStatus(BleManager.getInstance().isBlueEnable());
        homeViewModel.deviceData.postValue(deviceModel);
        mFragmentHomeBinding.homeDevice.bindData(homeViewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentHomeBinding.homeDevice.bindData(homeViewModel);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mFragmentHomeBinding.homeDevice.bindData(homeViewModel);
        }
    }

    /**
     * 刷新。
     */
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = () -> getSevenDate();


    /**
     * 获取七天数据
     */
    public void getSevenDate() {
        MotionRequest motionRequest = new MotionRequest();
        motionRequest.setDataType(3);
        motionRequest.setMotionType(0);
        motionRequest.setSession(mActivity.getUserInfoEntity().getSession());
        motionRequest.setTimeType(2);
        RetrofitUtils.getApiUrl()
                .queryMotionSumData(motionRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<MotionSumResponse>() {
                    @Override
                    public void onSuccess(MotionSumResponse motionSumResponse) {
                        mFragmentHomeBinding.srHomeRecord.setRefreshing(false);
                        mActivity.hideLoadingDialog();
                        mHomeDataAdapter.setProgress(motionSumResponse.getSumDistance(), motionSumResponse.getSumTime(), motionSumResponse.getSumKcal(), mActivity.getUserInfoEntity());
                        mHomeDataAdapter.setAdapterData(motionSumResponse.getMotionList());
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        mFragmentHomeBinding.srHomeRecord.setRefreshing(false);
                        mActivity.hideLoadingDialog();
                        mHomeDataAdapter.setProgress(0, 0, 0, mActivity.getUserInfoEntity());
                        mHomeDataAdapter.setAdapterData(null);
                        ToastUtils.showError(mActivity, resultCode);
                    }
                });
    }

    private DeviceUpgradeDialog mDeviceUpgradeDialog;

    /**
     * 设备升级
     */
    public void showDeviceUpgrade(DeviceInformationEntity deviceInformationEntity, int power) {
        if (deviceInformationEntity != null) {
            mDeviceUpgradeDialog = new DeviceUpgradeDialog(mActivity, deviceInformationEntity.getProductNo(), power);
            mDeviceUpgradeDialog.show();
            deviceInformationEntity.setShowTime(System.currentTimeMillis());
            DbUtils.getInstance().updateDevice(deviceInformationEntity);
            mDeviceUpgradeDialog.setDeviceUpgradeCallback(() -> {
                if (DeviceControllerService.deviceStatus == Device.DEVICE_CONNECTED) {
                    DeviceSettingActivity.intoDeviceSetting(mActivity, 1);
                } else {
                    mDeviceUpgradeDialog.dismiss();
                }
            });
        }
    }

    /**
     * 取消升级
     */
    public void hideUpgrade() {
        if (mDeviceUpgradeDialog != null) {
            mDeviceUpgradeDialog.dismiss();
        }
    }

    /**
     * 正在连接
     */
    public void deviceConnecting(DeviceInformationEntity device) {
        if (homeViewModel != null && homeViewModel.deviceData.getValue() != null) {
            if (device == null) {
                homeViewModel.deviceData.getValue().setProductNo(null);
            } else {
                homeViewModel.deviceData.getValue().setBleStatus(BleManager.getInstance().isBlueEnable());
                homeViewModel.deviceData.getValue().setProductNo(device.getProductNo());
                homeViewModel.deviceData.getValue().setUpdateStatus(device.getDeviceUpdate());
                homeViewModel.deviceData.getValue().setProgress(0);
                homeViewModel.deviceData.getValue().setPower(0);
                homeViewModel.deviceData.getValue().setDeviceStatus(Device.DEVICE_CONNECTING);
            }
            mFragmentHomeBinding.homeDevice.bindData(homeViewModel);
        }
    }

    /**
     * 已连接
     */
    public void deviceConnected(DeviceInformationEntity device) {
        if (homeViewModel != null && homeViewModel.deviceData.getValue() != null && device != null) {
            homeViewModel.deviceData.getValue().setProductNo(device.getProductNo());
            homeViewModel.deviceData.getValue().setUpdateStatus(device.getDeviceUpdate());
            homeViewModel.deviceData.getValue().setDeviceStatus(Device.DEVICE_CONNECTED);
            mFragmentHomeBinding.homeDevice.bindData(homeViewModel);
        }
    }

    /**
     * 蓝牙未打开
     */
    public void bleNotTurnOn(DeviceInformationEntity device) {
        if (homeViewModel != null && homeViewModel.deviceData.getValue() != null) {
            if (device == null) {
                homeViewModel.deviceData.getValue().setProductNo(null);
            } else {
                homeViewModel.deviceData.getValue().setBleStatus(false);
                homeViewModel.deviceData.getValue().setBleStatus(BleManager.getInstance().isBlueEnable());
                homeViewModel.deviceData.getValue().setProductNo(device.getProductNo());
                homeViewModel.deviceData.getValue().setUpdateStatus(device.getDeviceUpdate());
                homeViewModel.deviceData.getValue().setProgress(0);
                homeViewModel.deviceData.getValue().setPower(0);
                homeViewModel.deviceData.getValue().setDeviceStatus(Device.DEVICE_CONNECTING);
            }
            mFragmentHomeBinding.homeDevice.bindData(homeViewModel);
        }
    }

    /**
     * 电量更新
     */
    public void updatePower(int power) {
        if (homeViewModel != null && homeViewModel.deviceData.getValue() != null) {
            homeViewModel.deviceData.getValue().setPower(power);
            mFragmentHomeBinding.homeDevice.bindData(homeViewModel);
        }
    }

    /**
     * 数据上传中
     */
    public void dataUploaded(int total, int current) {
        if (homeViewModel != null && homeViewModel.deviceData.getValue() != null) {
            if (current == total) {
                homeViewModel.deviceData.getValue().setProgress(0);
            } else {
//                homeViewModel.deviceData.getValue().setProgress(current * 100 / total);
                int progress = current * 100 / total;
                if (progress == 0) {
                    progress = 1;
                }
                homeViewModel.deviceData.getValue().setProgress(progress);
            }
            mFragmentHomeBinding.homeDevice.bindData(homeViewModel);
        }
    }

    public void notifyAdapter() {
        mHomeDataAdapter.notifyDataSetChanged();
    }
}
