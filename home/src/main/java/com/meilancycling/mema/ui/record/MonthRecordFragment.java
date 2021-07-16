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
import com.meilancycling.mema.databinding.FragmentMonthRecordBinding;
import com.meilancycling.mema.ui.adapter.ItemCommonDataAdapter;

import java.util.ArrayList;
import java.util.List;



/**
 * @Description: 锻炼
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class MonthRecordFragment extends BaseFragment {
    FragmentMonthRecordBinding mFragmentMonthRecordBinding;

    private List<MotionSumResponse.MotionListBean> mList = new ArrayList<>();
    private ItemCommonDataAdapter mItemCommonDataAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentMonthRecordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_month_record, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mFragmentMonthRecordBinding.rvWeek.setLayoutManager(linearLayoutManager);
        mItemCommonDataAdapter = new ItemCommonDataAdapter(getContext(), mList);
        mFragmentMonthRecordBinding.rvWeek.setAdapter(mItemCommonDataAdapter);
        return mFragmentMonthRecordBinding.getRoot();
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
