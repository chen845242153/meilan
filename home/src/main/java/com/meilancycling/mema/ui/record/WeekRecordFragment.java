package com.meilancycling.mema.ui.record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.databinding.FragmentWeekRecordBinding;
import com.meilancycling.mema.ui.adapter.ItemCommonDataAdapter;

import java.util.ArrayList;
import java.util.List;



/**
 * @Description:
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class WeekRecordFragment extends BaseFragment {
    FragmentWeekRecordBinding mFragmentWeekRecordBinding;
    RecordHomeFragment parentFragment;
    private List<MotionSumResponse.MotionListBean> mList = new ArrayList<>();
    private ItemCommonDataAdapter mItemCommonDataAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentWeekRecordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_week_record, container, false);
        parentFragment = (RecordHomeFragment) getParentFragment();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mFragmentWeekRecordBinding.rvWeek.setLayoutManager(linearLayoutManager);
        mItemCommonDataAdapter = new ItemCommonDataAdapter(getContext(), mList);
        mFragmentWeekRecordBinding.rvWeek.setAdapter(mItemCommonDataAdapter);
        return mFragmentWeekRecordBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mItemCommonDataAdapter != null) {
            mItemCommonDataAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (mItemCommonDataAdapter != null) {
                mItemCommonDataAdapter.notifyDataSetChanged();
            }
        }
    }

    public void updateUi(MotionSumResponse motionSumResponse) {
        mList.clear();
        if (motionSumResponse != null && motionSumResponse.getMotionList() != null) {
            mList.addAll(motionSumResponse.getMotionList());
        }
        if (mItemCommonDataAdapter != null) {
            mItemCommonDataAdapter.notifyDataSetChanged();
        }
    }
}