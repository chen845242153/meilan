package com.meilancycling.mema.ui.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.databinding.FragmentFullLoadingBinding;

/**
 * @Description: 全屏加载框
 * @Author: sore_lion
 * @CreateDate: 3/29/21 9:16 AM
 */
public class FullLoadingFragment extends BaseFragment {
    private FragmentFullLoadingBinding mFragmentFullLoadingBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentFullLoadingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_full_loading, container, false);
        rotationAnimation();
        return mFragmentFullLoadingBinding.getRoot();
    }

    /**
     * 旋转动画
     */
    private void rotationAnimation() {
        Animation rotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.loading_anim);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        mFragmentFullLoadingBinding.ivLoading.startAnimation(rotateAnimation);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentFullLoadingBinding.ivLoading.clearAnimation();
    }
}
