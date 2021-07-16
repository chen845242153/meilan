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
import com.meilancycling.mema.databinding.FragmentYearRecordBinding;
import com.meilancycling.mema.ui.adapter.RecordYearAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 锻炼
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class YearRecordFragment extends BaseFragment {
    FragmentYearRecordBinding mFragmentYearRecordBinding;
    RecordHomeFragment parentFragment;
    private List<MotionSumResponse.YearListDataBean> mList = new ArrayList<>();
    private RecordYearAdapter mRecordYearAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentYearRecordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_year_record, container, false);
        parentFragment = (RecordHomeFragment) getParentFragment();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mFragmentYearRecordBinding.rvYear.setLayoutManager(linearLayoutManager);
        mRecordYearAdapter = new RecordYearAdapter(getContext(), mList);
        mFragmentYearRecordBinding.rvYear.setAdapter(mRecordYearAdapter);
        return mFragmentYearRecordBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRecordYearAdapter != null) {
            mRecordYearAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (mRecordYearAdapter != null) {
                mRecordYearAdapter.notifyDataSetChanged();
            }
        }
    }

    public void updateUi(MotionSumResponse motionSumResponse) {
        mList.clear();
        if (motionSumResponse != null && motionSumResponse.getYearListData() != null) {
            mList.addAll(motionSumResponse.getYearListData());
        }
        if (mRecordYearAdapter != null) {
            mRecordYearAdapter.notifyDataSetChanged();
        }
    }
}
