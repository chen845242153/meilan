package com.meilancycling.mema.ui.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.databinding.FragmentNoNetworkBinding;
import com.meilancycling.mema.ui.details.DetailsHomeActivity;

/**
 * @Description: 无网络加载
 * @Author: sore_lion
 * @CreateDate: 3/29/21 9:15 AM
 */
public class NoNetworkFragment extends BaseFragment {
    private DetailsHomeActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentNoNetworkBinding fragmentNoNetworkBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_no_network, container, false);
        activity = (DetailsHomeActivity) getActivity();
        fragmentNoNetworkBinding.tvRefresh.setOnClickListener(v -> activity.getDetailsData());
        return fragmentNoNetworkBinding.getRoot();
    }

}
