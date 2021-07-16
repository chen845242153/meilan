package com.meilancycling.mema.ui.club;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.lion.common.eventbus.MessageEvent;
import com.lion.common.eventbus.club.ChallengesBean;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.FragmentChallengesBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.NewsListRequest;
import com.meilancycling.mema.network.bean.response.ActivityListResponse;
import com.meilancycling.mema.ui.adapter.ActivityListRecyclerViewAdapter;
import com.meilancycling.mema.ui.club.model.ActivityItemModel;
import com.meilancycling.mema.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ChallengesFragment extends BaseFragment {
    private FragmentChallengesBinding mFragmentChallengesBinding;
    private String mSession;
    private int pageNum = 1;
    private int pageSize = 10;
    private ActivityListRecyclerViewAdapter mAdapter;
    private List<ActivityItemModel> adapterData;

    public ChallengesFragment(String session) {
        mSession = session;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getMessage() instanceof ChallengesBean) {
            ChallengesBean bean = (ChallengesBean) event.getMessage();
            if (adapterData != null && adapterData.size() > 0) {
                for (ActivityItemModel adapterDatum : adapterData) {
                    if (adapterDatum.getId() == bean.getId()) {
                        adapterDatum.activityStatus = bean.getActivityStatus();
                        break;
                    }
                }
            }
            mAdapter.setData(adapterData);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentChallengesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_challenges, container, false);
        return mFragmentChallengesBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterData = new ArrayList<>();
        requestChallenges();
        mAdapter = new ActivityListRecyclerViewAdapter();
        mFragmentChallengesBinding.rvChallenges.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mFragmentChallengesBinding.rvChallenges.setAdapter(mAdapter);
    }

    /**
     * 获取活动列表
     */
    public void requestChallenges() {
        NewsListRequest newsListRequest = new NewsListRequest();
        newsListRequest.setPageNum(pageNum);
        newsListRequest.setPageSize(pageSize);
        newsListRequest.setSession(mSession);
        RetrofitUtils.getApiUrl()
                .activityList(newsListRequest)
                .compose(RxHelper.observableToMain(ChallengesFragment.this))
                .subscribe(new MyObserver<ActivityListResponse>() {
                    @Override
                    public void onSuccess(ActivityListResponse result) {
                        List<ActivityListResponse.RowsBean> rows = result.getRows();
                        if (rows != null && rows.size() > 0) {
                            if (AppUtils.isChinese()) {
                                for (ActivityListResponse.RowsBean row : rows) {
                                    ActivityItemModel activityItemModel = new ActivityItemModel();
                                    activityItemModel.id = row.getId();
                                    activityItemModel.pictureUrl = row.getCnCover();
                                    activityItemModel.activityName = row.getCnTitle();
                                    activityItemModel.activityType = row.getActivityType();
                                    activityItemModel.activityTitle = row.getCnAddress();
                                    activityItemModel.activityStatus = row.getUserSignstatus();
                                    activityItemModel.activityTime = AppUtils.localTimeToString(row.getActivityStartDate(), Config.TIME_RECORD) +
                                            "-" + AppUtils.localTimeToString(row.getActivityEndDate(), Config.TIME_RECORD);
                                    adapterData.add(activityItemModel);
                                }
                            } else {
                                for (ActivityListResponse.RowsBean row : rows) {
                                    ActivityItemModel activityItemModel = new ActivityItemModel();
                                    activityItemModel.pictureUrl = row.getEnCover();
                                    activityItemModel.activityName = row.getEnTitle();
                                    activityItemModel.activityType = row.getActivityType();
                                    activityItemModel.activityTitle = row.getEnAddress();
                                    activityItemModel.activityStatus = row.getUserSignstatus();
                                    activityItemModel.activityTime = AppUtils.localTimeToString(row.getActivityStartDate(), Config.TIME_RECORD) +
                                            "-" + AppUtils.localTimeToString(row.getActivityEndDate(), Config.TIME_RECORD);
                                    adapterData.add(activityItemModel);
                                }
                            }
                        }
                        mAdapter.setData(adapterData);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {

                    }
                });
    }

}
