package com.meilancycling.mema.ui.club;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivityNewsDetailsBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.NewsInfoRequest;
import com.meilancycling.mema.network.bean.response.NewsInfoResponse;
import com.meilancycling.mema.ui.club.view.NewsDetailsBottomView;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.GlideUtils;
import com.meilancycling.mema.utils.StatusAppUtils;
import com.meilancycling.mema.utils.ToastUtils;

public class NewsDetailsActivity extends BaseActivity {
    private ActivityNewsDetailsBinding mActivityNewsDetailsBinding;
    private int mNewsId;

    public static void enterNewsDetails(int newsId, Context context) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra("newsId", newsId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNewsDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_details);
        Intent intent = getIntent();
        mNewsId = intent.getIntExtra("newsId", 0);
        selectNewsInfo(mNewsId);
        mActivityNewsDetailsBinding.ivBack.setOnClickListener(view -> finish());
        mActivityNewsDetailsBinding.ivBackImage.setOnClickListener(view -> finish());
        mActivityNewsDetailsBinding.newsBottom.setNewsDetailsBottomViewCallback(new NewsDetailsBottomView.NewsDetailsBottomViewCallback() {
            @Override
            public void addNewsLikes() {
                addNewsLikesRequest(getUserInfoEntity().getSession(), mNewsId);
            }

            @Override
            public void dleNewsLikes() {
                dleNewsLikesRequest(getUserInfoEntity().getSession(), mNewsId);
            }

            @Override
            public void clickComment() {
                CommentListActivity.enterCommentList(NewsDetailsActivity.this, mNewsId);
            }
        });
    }

    /**
     * ????????????
     */
    private void addNewsLikesRequest(String session, int newsId) {
        showLoadingDialog();
        NewsInfoRequest newsInfoRequest = new NewsInfoRequest();
        newsInfoRequest.setSession(session);
        newsInfoRequest.setId(newsId);
        RetrofitUtils.getApiUrl()
                .addNewsLikes(newsInfoRequest)
                .compose(RxHelper.observableToMain(NewsDetailsActivity.this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadingDialog();
                        mActivityNewsDetailsBinding.newsBottom.addLike();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(NewsDetailsActivity.this, resultCode);
                    }
                });
    }

    /**
     * ??????????????????
     */
    private void dleNewsLikesRequest(String session, int newsId) {
        showLoadingDialog();
        NewsInfoRequest newsInfoRequest = new NewsInfoRequest();
        newsInfoRequest.setSession(session);
        newsInfoRequest.setId(newsId);
        RetrofitUtils.getApiUrl()
                .dleNewsLikes(newsInfoRequest)
                .compose(RxHelper.observableToMain(NewsDetailsActivity.this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadingDialog();
                        mActivityNewsDetailsBinding.newsBottom.removeLike();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(NewsDetailsActivity.this, resultCode);
                    }
                });
    }


    /**
     * ??????????????????
     */
    private void selectNewsInfo(int newsId) {
        showLoadingDialog();
        NewsInfoRequest newsInfoRequest = new NewsInfoRequest();
        newsInfoRequest.setSession(getUserInfoEntity().getSession());
        newsInfoRequest.setId(newsId);
        RetrofitUtils.getApiUrl()
                .selectNewsInfo(newsInfoRequest)
                .compose(RxHelper.observableToMain(NewsDetailsActivity.this))
                .subscribe(new MyObserver<NewsInfoResponse>() {
                    @Override
                    public void onSuccess(NewsInfoResponse result) {
                        if (AppUtils.isChinese()) {
                            initWebView(result.getCnUrl());
                        } else {
                            initWebView(result.getEnUrl());
                        }
                        if ("0".equals(result.getStyleType())) {
                            mActivityNewsDetailsBinding.ivTopImage.setVisibility(View.GONE);
                            mActivityNewsDetailsBinding.ivBackImage.setVisibility(View.GONE);
                        } else {
                            mActivityNewsDetailsBinding.viewTitle.setVisibility(View.GONE);
                            mActivityNewsDetailsBinding.ivBack.setVisibility(View.GONE);
                            StatusAppUtils.setTranslucentForImageView(NewsDetailsActivity.this, 0, null);
                            if (AppUtils.isChinese()) {
                                GlideUtils.loadImageWelcome(result.getCnCover(), NewsDetailsActivity.this, mActivityNewsDetailsBinding.ivTopImage);
                            } else {
                                GlideUtils.loadImageWelcome(result.getEnCover(), NewsDetailsActivity.this, mActivityNewsDetailsBinding.ivTopImage);
                            }
                        }
                        mActivityNewsDetailsBinding.newsBottom.updateUi(getUserInfoEntity().getHeadUrl(), result.getLikeStatus() == 1, result.getGiveSum());
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(NewsDetailsActivity.this, resultCode);
                    }
                });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(String url) {
        //??????javascript
        mActivityNewsDetailsBinding.newsWebView.getSettings().setJavaScriptEnabled(true);
        // ????????????????????????
        mActivityNewsDetailsBinding.newsWebView.getSettings().setSupportZoom(false);
        // ????????????????????????
        mActivityNewsDetailsBinding.newsWebView.getSettings().setBuiltInZoomControls(false);
        //?????????????????????
        mActivityNewsDetailsBinding.newsWebView.getSettings().setUseWideViewPort(false);
        //???????????????
        mActivityNewsDetailsBinding.newsWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mActivityNewsDetailsBinding.newsWebView.getSettings().setLoadWithOverviewMode(true);
        mActivityNewsDetailsBinding.newsWebView.loadUrl(url);

        mActivityNewsDetailsBinding.newsWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mActivityNewsDetailsBinding.newsWebView.loadUrl(url);
                return true;
            }
        });

        mActivityNewsDetailsBinding.newsWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                int finish = 100;
                if (newProgress == finish) {
                    hideLoadingDialog();
                    mActivityNewsDetailsBinding.viewBg.setVisibility(View.GONE);
                }
            }
        });
    }
}