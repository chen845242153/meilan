package com.meilancycling.mema.ui.club;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.databinding.FragmentNewsBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.NewsListRequest;
import com.meilancycling.mema.network.bean.request.SessionRequest;
import com.meilancycling.mema.network.bean.response.NewsListResponse;
import com.meilancycling.mema.network.bean.response.NewsRecommendResponse;
import com.meilancycling.mema.ui.adapter.NewsBannerAdapter;
import com.meilancycling.mema.ui.adapter.NewsListAdapter;
import com.meilancycling.mema.utils.ToastUtils;
import com.yanzhenjie.recyclerview.widget.DefaultLoadMoreView;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AlphaPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends BaseFragment {
    private FragmentNewsBinding mFragmentNewsBinding;
    private String mSession;
    private int pageNum = 1;
    private int pageSize = 10;
    private NewsListAdapter mAdapter;
    private List<NewsListResponse.NewsDetailsBean> adapterData;

    public NewsFragment(String session) {
        mSession = session;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentNewsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
        return mFragmentNewsBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterData = new ArrayList<>();
        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(getContext());
        mFragmentNewsBinding.rvNews.setLoadMoreView(defaultLoadMoreView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mFragmentNewsBinding.rvNews.setLayoutManager(manager);
        mAdapter = new NewsListAdapter();
        mFragmentNewsBinding.rvNews.setAdapter(mAdapter);
        updateUi(mSession);

        mFragmentNewsBinding.newsRefresh.setColorSchemeColors(ContextCompat.getColor(mActivity, R.color.main_color));
        mFragmentNewsBinding.newsRefresh.setOnRefreshListener(refreshListener);
    }

    public void updateUi(String session) {
        pageNum = 1;
        requestNewsRecommend(session);
        requestNewsList(pageNum, pageSize, session);
    }

    /**
     * 获取推荐新闻
     */
    private void requestNewsRecommend(String session) {
        SessionRequest sessionRequest = new SessionRequest();
        sessionRequest.setSession(session);
        RetrofitUtils.getApiUrl()
                .recommend(sessionRequest)
                .compose(RxHelper.observableToMain(NewsFragment.this))
                .subscribe(new MyObserver<List<NewsRecommendResponse>>() {

                    @Override
                    public void onSuccess(List<NewsRecommendResponse> result) {
                        if (result != null && result.size() > 0) {
                            mFragmentNewsBinding.banner.setVisibility(View.VISIBLE);
                            mFragmentNewsBinding.indicator.setVisibility(View.VISIBLE);
                            NewsBannerAdapter newsBannerAdapter = new NewsBannerAdapter(result);
                            mFragmentNewsBinding.banner.setAdapter(newsBannerAdapter);
                            mFragmentNewsBinding.banner.setBannerGalleryEffect(18, 10);
                            mFragmentNewsBinding.banner.addPageTransformer(new AlphaPageTransformer());
                            mFragmentNewsBinding.banner.setIndicator(mFragmentNewsBinding.indicator, false);
                            mFragmentNewsBinding.banner.setIndicatorSelectedColor(ContextCompat.getColor(getContext(), R.color.main_color));
                            mFragmentNewsBinding.banner.setIndicatorNormalColor(ContextCompat.getColor(getContext(), R.color.band_normal));
                            mFragmentNewsBinding.banner.setOnBannerListener((OnBannerListener<NewsRecommendResponse>) (data, position) -> {
                                if (data.getType() == 1) {
                                    NewsDetailsActivity.enterNewsDetails(data.getId(), getContext());
                                } else {
                                    ActivityDetailsActivity.enterActivityDetails(data.getId(), getContext());
                                }
                            });
                        } else {
                            mFragmentNewsBinding.banner.setVisibility(View.GONE);
                            mFragmentNewsBinding.indicator.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        mFragmentNewsBinding.banner.setVisibility(View.GONE);
                        mFragmentNewsBinding.indicator.setVisibility(View.GONE);
                    }
                });

    }

    /**
     * 获取新闻列表
     */
    private void requestNewsList(int pageNum, int pageSize, String session) {
        NewsListRequest newsListRequest = new NewsListRequest();
        newsListRequest.setPageNum(pageNum);
        newsListRequest.setPageSize(pageSize);
        newsListRequest.setSession(session);
        RetrofitUtils.getApiUrl()
                .newsList(newsListRequest)
                .compose(RxHelper.observableToMain(NewsFragment.this))
                .subscribe(new MyObserver<NewsListResponse>() {
                    @Override
                    public void onSuccess(NewsListResponse result) {
                        mFragmentNewsBinding.newsRefresh.setRefreshing(false);
                        if (result.getRows() != null && result.getRows().size() > 0) {
                            adapterData.addAll(result.getRows());
                        }
                        mAdapter.setNewsListData(adapterData);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        mFragmentNewsBinding.newsRefresh.setRefreshing(false);
                        ToastUtils.show(getContext(), resultCode);
                    }
                });
    }

    private final SwipeRefreshLayout.OnRefreshListener refreshListener = () -> {
        adapterData.clear();
        updateUi(mSession);
    };

}
