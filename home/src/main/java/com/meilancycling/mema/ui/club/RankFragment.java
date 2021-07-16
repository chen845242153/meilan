package com.meilancycling.mema.ui.club;

import android.annotation.SuppressLint;
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
import com.meilancycling.mema.databinding.FragmentActivityRankingBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.request.RankingRequest;
import com.meilancycling.mema.network.bean.response.RankingResponse;
import com.meilancycling.mema.ui.adapter.RankingRecyclerViewAdapter;
import com.meilancycling.mema.ui.club.model.RankingItemModel;
import com.meilancycling.mema.ui.club.model.RankingModel;
import com.meilancycling.mema.ui.club.model.RankingTopModel;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.ArrayList;
import java.util.List;

public class RankFragment extends BaseFragment implements View.OnClickListener {
    private String mSession;
    private FragmentActivityRankingBinding mFragmentActivityRankingBinding;
    private RankingModel mRankingModel;

    private RankingRecyclerViewAdapter mRankingRecyclerViewAdapter;
    private List<RankingItemModel> adapterList;

    public RankFragment(String session) {
        mSession = session;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentActivityRankingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity_ranking, container, false);
        return mFragmentActivityRankingBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentActivityRankingBinding.tvLeft.setOnClickListener(this);
        mFragmentActivityRankingBinding.tvCentral.setOnClickListener(this);
        mFragmentActivityRankingBinding.tvRight.setOnClickListener(this);

        mRankingModel = new RankingModel();
        mRankingModel.setGrade("-");
        mRankingModel.setItemSelect(0);
        mRankingModel.setRanking("-");
        mFragmentActivityRankingBinding.setModel(mRankingModel);

        adapterList = new ArrayList<>();
        mRankingRecyclerViewAdapter = new RankingRecyclerViewAdapter();
        mFragmentActivityRankingBinding.rvRanking.setLayoutManager(new LinearLayoutManager(mActivity));
        mFragmentActivityRankingBinding.rvRanking.setAdapter(mRankingRecyclerViewAdapter);

        getRankingList(1);
    }

    /**
     * 获取排名
     */
    private void getRankingList(int type) {
        mActivity.showLoadingDialog();
        RankingRequest rankingRequest = new RankingRequest();
        rankingRequest.setSession(mSession);
        rankingRequest.setTimeType(type);
        RetrofitUtils.getApiUrl()
                .getRankingList(rankingRequest)
                .compose(RxHelper.observableToMain(mActivity))
                .subscribe(new MyObserver<RankingResponse>() {
                    @Override
                    public void onSuccess(RankingResponse result) {
                        adapterList.clear();
                        mActivity.hideLoadingDialog();
                        if (result.getUserRanking() != 0) {
                            mRankingModel.setRanking(String.valueOf(result.getUserRanking()));
                            UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().rankDistanceSetting(mActivity, result.getDistance());
                            mRankingModel.setGrade(unitBean.getValue() + unitBean.getUnit());
                        } else {
                            mRankingModel.setGrade("-");
                            mRankingModel.setRanking("-");
                        }
                        List<RankingResponse.ListBean> list = result.getList();
                        if (list != null) {
                            RankingTopModel rankingTopModel = new RankingTopModel();
                            for (int i = 0; i < list.size(); i++) {
                                RankingResponse.ListBean listBean = list.get(i);
                                if (i == 0) {
                                    rankingTopModel.setName2(listBean.getNickName());
                                    rankingTopModel.setUrl2(listBean.getHeaderUrl());
                                    UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().rankDistanceSetting(mActivity, listBean.getDistance());
                                    rankingTopModel.setValue2(unitBean.getValue() + unitBean.getUnit());
                                    rankingTopModel.setNationalFlag2(listBean.getNationalFlag());
                                } else if (i == 1) {
                                    rankingTopModel.setName1(listBean.getNickName());
                                    rankingTopModel.setUrl1(listBean.getHeaderUrl());
                                    UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().rankDistanceSetting(mActivity, listBean.getDistance());
                                    rankingTopModel.setValue1(unitBean.getValue() + unitBean.getUnit());
                                    rankingTopModel.setNationalFlag1(listBean.getNationalFlag());
                                } else if (i == 2) {
                                    rankingTopModel.setName3(listBean.getNickName());
                                    rankingTopModel.setUrl3(listBean.getHeaderUrl());
                                    UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().rankDistanceSetting(mActivity, listBean.getDistance());
                                    rankingTopModel.setValue3(unitBean.getValue() + unitBean.getUnit());
                                    rankingTopModel.setNationalFlag3(listBean.getNationalFlag());
                                } else {
                                    RankingItemModel rankingItemModel = new RankingItemModel();
                                    rankingItemModel.setName(listBean.getNickName());
                                    rankingItemModel.setNationalFlag(listBean.getNationalFlag());
                                    rankingItemModel.setPosition(String.valueOf(i + 1));
                                    rankingItemModel.setUrl(listBean.getHeaderUrl());
                                    UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().rankDistanceSetting(mActivity, listBean.getDistance());
                                    rankingItemModel.setValue(unitBean.getValue() + unitBean.getUnit());
                                    adapterList.add(rankingItemModel);
                                }
                                mFragmentActivityRankingBinding.rvRanking.scrollToPosition(0);
                                mRankingRecyclerViewAdapter.setData(rankingTopModel, adapterList);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        adapterList.clear();
                        RankingTopModel rankingTopModel = new RankingTopModel();
                        mRankingRecyclerViewAdapter.setData(rankingTopModel, adapterList);
                        mActivity.hideLoadingDialog();
                        mRankingModel.setGrade("-");
                        mRankingModel.setRanking("-");
                        ToastUtils.showError(mActivity, resultCode);
                    }
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                if (mRankingModel.getItemSelect() != 0) {
                    mRankingModel.setItemSelect(0);
                    getRankingList(1);
                }
                break;
            case R.id.tv_central:
                if (mRankingModel.getItemSelect() != 1) {
                    mRankingModel.setItemSelect(1);
                    getRankingList(2);
                }
                break;
            case R.id.tv_right:
                if (mRankingModel.getItemSelect() != 2) {
                    mRankingModel.setItemSelect(2);
                    getRankingList(3);
                }
                break;
        }

    }
}
