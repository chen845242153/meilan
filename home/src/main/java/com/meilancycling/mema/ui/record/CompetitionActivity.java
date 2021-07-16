package com.meilancycling.mema.ui.record;

import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivityCompetitionBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.MotionInfoRequest;
import com.meilancycling.mema.network.bean.response.MotionInfoResponse;
import com.meilancycling.mema.ui.adapter.CompetitionAdapter;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultLoadMoreView;
import java.util.ArrayList;
import java.util.List;
/**
 * 竞赛数据
 *
 * @author lion
 */

public class CompetitionActivity extends BaseActivity {
    private ActivityCompetitionBinding mActivityCompetitionBinding;
    private CompetitionAdapter mCompetitionAdapter;
    private List<MotionInfoResponse.RowsBean> adapterList = new ArrayList<>();
    private int pageNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCompetitionBinding = DataBindingUtil.setContentView(this, R.layout.activity_competition);
        mActivityCompetitionBinding.ctvCompetition.setData(getResources().getString(R.string.competition), v -> finish());
        mCompetitionAdapter = new CompetitionAdapter();
        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(this);
        mActivityCompetitionBinding.srvCtvCompetition.setLoadMoreView(defaultLoadMoreView);
        mActivityCompetitionBinding.srvCtvCompetition.setLayoutManager(new LinearLayoutManager(this));
        mActivityCompetitionBinding.srvCtvCompetition.setLoadMoreListener(mLoadMoreListener);
        mActivityCompetitionBinding.srvCtvCompetition.setAdapter(mCompetitionAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNum = 1;
        adapterList.clear();
        queryCompetitionData();
    }

    private SwipeRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeRecyclerView.LoadMoreListener() {
        @Override
        public void onLoadMore() {
            ++pageNum;
            queryCompetitionData();
        }
    };

    private void queryCompetitionData() {
        showLoadingDialog();
        int pageSize = 10;
        MotionInfoRequest motionInfoRequest = new MotionInfoRequest();
        motionInfoRequest.setIsCompetition("0");
        motionInfoRequest.setMotionType(3);
        motionInfoRequest.setSession(getUserInfoEntity().getSession());
        motionInfoRequest.setPageNum(pageNum);
        motionInfoRequest.setPageSize(pageSize);
        motionInfoRequest.setTimeType(5);

        RetrofitUtils.getApiUrl().listMotionInfo(motionInfoRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<MotionInfoResponse>() {
                    @Override
                    public void onSuccess(MotionInfoResponse result) {
                        hideLoadingDialog();
                        if (result != null) {
                            if (result.getRows() != null && result.getRows().size() != 0) {
                                mActivityCompetitionBinding.srvCtvCompetition.setVisibility(View.VISIBLE);
                                mActivityCompetitionBinding.groupEmpty.setVisibility(View.GONE);
                                adapterList.addAll(result.getRows());
                                mCompetitionAdapter.setAdapterData(adapterList);
                            } else {
                                mActivityCompetitionBinding.srvCtvCompetition.setVisibility(View.GONE);
                                mActivityCompetitionBinding.groupEmpty.setVisibility(View.VISIBLE);
                            }
                            if (result.getPages() == pageNum) {
                                mActivityCompetitionBinding.srvCtvCompetition.loadMoreFinish(false, false);
                            } else {
                                mActivityCompetitionBinding.srvCtvCompetition.loadMoreFinish(false, true);
                            }
                        } else {
                            mActivityCompetitionBinding.srvCtvCompetition.loadMoreFinish(true, true);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        mActivityCompetitionBinding.srvCtvCompetition.setVisibility(View.GONE);
                        mActivityCompetitionBinding.groupEmpty.setVisibility(View.VISIBLE);
                    }
                });
    }
}