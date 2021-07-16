package com.meilancycling.mema.ui.sensor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.SensorType;
import com.meilancycling.mema.databinding.FragmentSensorHomeBinding;
import com.meilancycling.mema.ui.sensor.view.SensorItemView;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.SPUtils;

import java.util.List;

/**
 * @Description: 锻炼
 * @Author: sore_lion
 * @CreateDate: 2/20/21 2:05 PM
 */
public class SensorHomeFragment extends BaseFragment {
    private FragmentSensorHomeBinding mFragmentSensorHomeBinding;
    /**
     * 当前页面的总高端
     */
    private int totalHeight;

    private SensorHomeActivity activity;
    private int intervalHeight;

    private List<SensorType> showSensorTypeList;

    public SensorHomeFragment(List<SensorType> list) {
        showSensorTypeList = list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentSensorHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sensor_home, container, false);
        activity = (SensorHomeActivity) getActivity();
        assert activity != null;

        intervalHeight = AppUtils.dipToPx(activity, 8);
        mFragmentSensorHomeBinding.clRoot.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);

        initListener();
        return mFragmentSensorHomeBinding.getRoot();
    }


    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int sensorNumber = SPUtils.getInt(getContext(), SPUtils.SENSOR_TYPE, -1);
            totalHeight = mFragmentSensorHomeBinding.clRoot.getHeight();
            changeType(sensorNumber, showSensorTypeList);
            mFragmentSensorHomeBinding.clRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    };

    /**
     * 页面置顶
     */
    public void placedTop() {
        mFragmentSensorHomeBinding.sv.scrollTo(0, 0);
    }

    /**
     * 设备显示的个数
     * 0 模式一 两个
     * 1 模式二 四个
     * 2 模式三 六个
     * 3 模式三 十个
     */
    public void changeType(int sensorType, List<SensorType> showSensorTypeList) {
        if (totalHeight == 0) {
            return;
        }
        int itemHeight;
        switch (sensorType) {
            case 0:
                itemHeight = (totalHeight - 2 * intervalHeight) / 2;
                showSingleUi(itemHeight, showSensorTypeList);
                break;
            case 1:
                itemHeight = (totalHeight - 2 * intervalHeight) / 4;
                showSingleUi(itemHeight, showSensorTypeList);
                break;
            case 2:
                itemHeight = (totalHeight - 2 * intervalHeight) / 3;
                showDoubleUi(itemHeight, showSensorTypeList);
                break;
            case 3:
                itemHeight = (totalHeight - 2 * intervalHeight) / 5;
                showDoubleUi(itemHeight, showSensorTypeList);
                break;
            default:
        }
    }

    /**
     * 更新数据
     */
    public void updateUi(List<SensorType> showSensorTypeList) {
        if (mFragmentSensorHomeBinding != null) {
            mFragmentSensorHomeBinding.sensorSingleItem1.setSensorItemViewData(showSensorTypeList.get(0));
            mFragmentSensorHomeBinding.sensorSingleItem2.setSensorItemViewData(showSensorTypeList.get(1));
            mFragmentSensorHomeBinding.sensorSingleItem3.setSensorItemViewData(showSensorTypeList.get(2));
            mFragmentSensorHomeBinding.sensorSingleItem4.setSensorItemViewData(showSensorTypeList.get(3));
            mFragmentSensorHomeBinding.sensorSingleItem5.setSensorItemViewData(showSensorTypeList.get(4));
            mFragmentSensorHomeBinding.sensorSingleItem6.setSensorItemViewData(showSensorTypeList.get(5));
            mFragmentSensorHomeBinding.sensorSingleItem7.setSensorItemViewData(showSensorTypeList.get(6));
            mFragmentSensorHomeBinding.sensorSingleItem8.setSensorItemViewData(showSensorTypeList.get(7));
            mFragmentSensorHomeBinding.sensorSingleItem9.setSensorItemViewData(showSensorTypeList.get(8));
            mFragmentSensorHomeBinding.sensorSingleItem10.setSensorItemViewData(showSensorTypeList.get(9));

            mFragmentSensorHomeBinding.sensorItem1.setSensorItemViewData(showSensorTypeList.get(0));
            mFragmentSensorHomeBinding.sensorItem2.setSensorItemViewData(showSensorTypeList.get(1));
            mFragmentSensorHomeBinding.sensorItem3.setSensorItemViewData(showSensorTypeList.get(2));
            mFragmentSensorHomeBinding.sensorItem4.setSensorItemViewData(showSensorTypeList.get(3));
            mFragmentSensorHomeBinding.sensorItem5.setSensorItemViewData(showSensorTypeList.get(4));
            mFragmentSensorHomeBinding.sensorItem6.setSensorItemViewData(showSensorTypeList.get(5));
            mFragmentSensorHomeBinding.sensorItem7.setSensorItemViewData(showSensorTypeList.get(6));
            mFragmentSensorHomeBinding.sensorItem8.setSensorItemViewData(showSensorTypeList.get(7));
            mFragmentSensorHomeBinding.sensorItem9.setSensorItemViewData(showSensorTypeList.get(8));
            mFragmentSensorHomeBinding.sensorItem10.setSensorItemViewData(showSensorTypeList.get(9));
        }
    }

    /**
     * 显示单排
     */
    private void showSingleUi(int itemHeight, List<SensorType> showSensorTypeList) {
        mFragmentSensorHomeBinding.groupSingle.setVisibility(View.VISIBLE);
        mFragmentSensorHomeBinding.group.setVisibility(View.GONE);
        changeParams(mFragmentSensorHomeBinding.sensorSingleItem1, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorSingleItem2, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorSingleItem3, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorSingleItem4, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorSingleItem5, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorSingleItem6, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorSingleItem7, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorSingleItem8, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorSingleItem9, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorSingleItem10, itemHeight);
        mFragmentSensorHomeBinding.sensorSingleItem1.setSensorItemViewData(showSensorTypeList.get(0));
        mFragmentSensorHomeBinding.sensorSingleItem2.setSensorItemViewData(showSensorTypeList.get(1));
        mFragmentSensorHomeBinding.sensorSingleItem3.setSensorItemViewData(showSensorTypeList.get(2));
        mFragmentSensorHomeBinding.sensorSingleItem4.setSensorItemViewData(showSensorTypeList.get(3));
        mFragmentSensorHomeBinding.sensorSingleItem5.setSensorItemViewData(showSensorTypeList.get(4));
        mFragmentSensorHomeBinding.sensorSingleItem6.setSensorItemViewData(showSensorTypeList.get(5));
        mFragmentSensorHomeBinding.sensorSingleItem7.setSensorItemViewData(showSensorTypeList.get(6));
        mFragmentSensorHomeBinding.sensorSingleItem8.setSensorItemViewData(showSensorTypeList.get(7));
        mFragmentSensorHomeBinding.sensorSingleItem9.setSensorItemViewData(showSensorTypeList.get(8));
        mFragmentSensorHomeBinding.sensorSingleItem10.setSensorItemViewData(showSensorTypeList.get(9));
    }

    /**
     * 显示双排
     */
    private void showDoubleUi(int itemHeight, List<SensorType> showSensorTypeList) {
        mFragmentSensorHomeBinding.groupSingle.setVisibility(View.GONE);
        mFragmentSensorHomeBinding.group.setVisibility(View.VISIBLE);
        changeParams(mFragmentSensorHomeBinding.sensorItem1, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorItem2, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorItem3, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorItem4, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorItem5, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorItem6, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorItem7, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorItem8, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorItem9, itemHeight);
        changeParams(mFragmentSensorHomeBinding.sensorItem10, itemHeight);
        mFragmentSensorHomeBinding.sensorItem1.setSensorItemViewData(showSensorTypeList.get(0));
        mFragmentSensorHomeBinding.sensorItem2.setSensorItemViewData(showSensorTypeList.get(1));
        mFragmentSensorHomeBinding.sensorItem3.setSensorItemViewData(showSensorTypeList.get(2));
        mFragmentSensorHomeBinding.sensorItem4.setSensorItemViewData(showSensorTypeList.get(3));
        mFragmentSensorHomeBinding.sensorItem5.setSensorItemViewData(showSensorTypeList.get(4));
        mFragmentSensorHomeBinding.sensorItem6.setSensorItemViewData(showSensorTypeList.get(5));
        mFragmentSensorHomeBinding.sensorItem7.setSensorItemViewData(showSensorTypeList.get(6));
        mFragmentSensorHomeBinding.sensorItem8.setSensorItemViewData(showSensorTypeList.get(7));
        mFragmentSensorHomeBinding.sensorItem9.setSensorItemViewData(showSensorTypeList.get(8));
        mFragmentSensorHomeBinding.sensorItem10.setSensorItemViewData(showSensorTypeList.get(9));
    }

    private void changeParams(SensorItemView sensorItemView, int itemHeight) {
        ViewGroup.LayoutParams layoutParams = sensorItemView.getLayoutParams();
        layoutParams.height = itemHeight;
        sensorItemView.setLayoutParams(layoutParams);
    }


    private void initListener() {
        mFragmentSensorHomeBinding.sensorItem1.setSensorItemListener(activity, 0);
        mFragmentSensorHomeBinding.sensorItem2.setSensorItemListener(activity, 1);
        mFragmentSensorHomeBinding.sensorItem3.setSensorItemListener(activity, 2);
        mFragmentSensorHomeBinding.sensorItem4.setSensorItemListener(activity, 3);
        mFragmentSensorHomeBinding.sensorItem5.setSensorItemListener(activity, 4);
        mFragmentSensorHomeBinding.sensorItem6.setSensorItemListener(activity, 5);
        mFragmentSensorHomeBinding.sensorItem7.setSensorItemListener(activity, 6);
        mFragmentSensorHomeBinding.sensorItem8.setSensorItemListener(activity, 7);
        mFragmentSensorHomeBinding.sensorItem9.setSensorItemListener(activity, 8);
        mFragmentSensorHomeBinding.sensorItem10.setSensorItemListener(activity, 9);

        mFragmentSensorHomeBinding.sensorSingleItem1.setSensorItemListener(activity, 0);
        mFragmentSensorHomeBinding.sensorSingleItem2.setSensorItemListener(activity, 1);
        mFragmentSensorHomeBinding.sensorSingleItem3.setSensorItemListener(activity, 2);
        mFragmentSensorHomeBinding.sensorSingleItem4.setSensorItemListener(activity, 3);
        mFragmentSensorHomeBinding.sensorSingleItem5.setSensorItemListener(activity, 4);
        mFragmentSensorHomeBinding.sensorSingleItem6.setSensorItemListener(activity, 5);
        mFragmentSensorHomeBinding.sensorSingleItem7.setSensorItemListener(activity, 6);
        mFragmentSensorHomeBinding.sensorSingleItem8.setSensorItemListener(activity, 7);
        mFragmentSensorHomeBinding.sensorSingleItem9.setSensorItemListener(activity, 8);
        mFragmentSensorHomeBinding.sensorSingleItem10.setSensorItemListener(activity, 9);
    }
}
