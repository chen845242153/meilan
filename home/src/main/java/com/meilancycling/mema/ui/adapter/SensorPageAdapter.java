package com.meilancycling.mema.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/8/31 9:50 AM
 */
public class SensorPageAdapter extends FragmentStateAdapter {
    private List<Fragment> mList;

    public SensorPageAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments) {
        super(fragmentActivity);
        this.mList = fragments;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
