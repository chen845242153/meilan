package com.meilancycling.mema.ui.record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.network.bean.request.MotionInfoRequest;
import com.meilancycling.mema.network.bean.response.MotionInfoResponse;
import com.meilancycling.mema.databinding.FragmentTotalRecordBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.ui.adapter.TotalRecordAdapter;
import com.meilancycling.mema.utils.ToastUtils;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultLoadMoreView;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 锻炼
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 2:35 PM
 */
public class TotalRecordFragment extends BaseFragment {
    private FragmentTotalRecordBinding mFragmentTotalRecordBinding;
    private int pageNum;
    private List<MotionInfoResponse.RowsBean> mList = new ArrayList<>();
    private TotalRecordAdapter mTotalRecordAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentTotalRecordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_total_record, container, false);
        pageNum = 1;
        initRecyclerview();
        listMotionInfo();
        mFragmentTotalRecordBinding.sr.setColorSchemeColors(ContextCompat.getColor(mActivity, R.color.main_color));
        mFragmentTotalRecordBinding.sr.setOnRefreshListener(() -> {
            pageNum = 1;
            mList.clear();
            listMotionInfo();
            RecordHomeFragment parentFragment = (RecordHomeFragment) getParentFragment();
            if (parentFragment != null) {
                parentFragment.updateTotalUi();
            }
        });
        return mFragmentTotalRecordBinding.getRoot();
    }

    public void deleteRecord() {
        pageNum = 1;
        mList.clear();
        listMotionInfo();
    }

    private void initRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mFragmentTotalRecordBinding.rv.setLoadMoreListener(mLoadMoreListener);
        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(getContext());
        mFragmentTotalRecordBinding.rv.setLoadMoreView(defaultLoadMoreView);
        mFragmentTotalRecordBinding.rv.setLayoutManager(linearLayoutManager);
        mTotalRecordAdapter = new TotalRecordAdapter(getContext(), mList);
        mFragmentTotalRecordBinding.rv.setAdapter(mTotalRecordAdapter);
    }

    private SwipeRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeRecyclerView.LoadMoreListener() {
        @Override
        public void onLoadMore() {
            ++pageNum;
            listMotionInfo();
        }
    };

    private void listMotionInfo() {
        int pageSize = 10;
        MotionInfoRequest motionInfoRequest = new MotionInfoRequest();
        motionInfoRequest.setMotionType(0);
        motionInfoRequest.setSession(mActivity.getUserInfoEntity().getSession());
        motionInfoRequest.setTimeType(5);
        motionInfoRequest.setPageNum(pageNum);
        motionInfoRequest.setPageSize(pageSize);
        motionInfoRequest.setIsCompetition("0");
        RetrofitUtils.getApiUrl().listMotionInfo(motionInfoRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<MotionInfoResponse>() {
                    @Override
                    public void onSuccess(MotionInfoResponse result) {
                        mFragmentTotalRecordBinding.sr.setRefreshing(false);
                        if (result != null) {
                            if (result.getRows() != null) {
                                mList.addAll(result.getRows());
                            }
                            if (pageNum >= result.getPages()) {
                                mFragmentTotalRecordBinding.rv.loadMoreFinish(false, false);
                            } else {
                                mFragmentTotalRecordBinding.rv.loadMoreFinish(false, true);
                            }
                        } else {
                            mFragmentTotalRecordBinding.rv.loadMoreFinish(true, true);
                        }
                        mTotalRecordAdapter.notifyDataSetChanged();
                        // 第一次加载数据：一定要掉用这个方法。
                        // 第一个参数：表示此次数据是否为空，假如你请求到的list为空(== null || list.size == 0)，那么这里就要true。
                        // 第二个参数：表示是否还有更多数据，根据服务器返回给你的page等信息判断是否还有更多，这样可以提供性能，如果不能判断则传true。
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        mFragmentTotalRecordBinding.sr.setRefreshing(false);
                        ToastUtils.showError(mActivity, resultCode);
                        mTotalRecordAdapter.notifyDataSetChanged();
                    }
                });
    }
}
