package com.meilancycling.mema.ui.club;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.lion.common.eventbus.MessageEvent;
import com.lion.common.eventbus.club.ChallengesBean;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.ActivityDetailsBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.NewsInfoRequest;
import com.meilancycling.mema.network.bean.request.RegistryActivityRequest;
import com.meilancycling.mema.network.bean.response.ActivityDetailsResponse;
import com.meilancycling.mema.ui.club.view.NewsDetailsBottomView;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.GlideUtils;
import com.meilancycling.mema.utils.StatusAppUtils;
import com.meilancycling.mema.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ActivityDetailsActivity extends BaseActivity {
    private ActivityDetailsBinding mActivityDetailsBinding;
    private int activityId;
    private String statusContent;
    private String path;
    private String title;
    private int activityType;
    private int activityStatus;
    private long ageLimitDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        StatusAppUtils.setTranslucentForImageView(this, 0, null);
        activityId = getIntent().getIntExtra("activityId", 0);
        queryActivity();
        mActivityDetailsBinding.ivBack.setOnClickListener(view -> finish());
        mActivityDetailsBinding.activityBottom.setNewsDetailsBottomViewCallback(new NewsDetailsBottomView.NewsDetailsBottomViewCallback() {
            @Override
            public void addNewsLikes() {
                addActivityLikes(getUserInfoEntity().getSession(), activityId);
            }

            @Override
            public void dleNewsLikes() {
                dleNewsLikesRequest(getUserInfoEntity().getSession(), activityId);
            }

            @Override
            public void clickComment() {
                CommentListActivity.enterCommentList(ActivityDetailsActivity.this, activityId);
            }
        });

        mActivityDetailsBinding.tvActivityStatus.setOnClickListener(v -> {
            if (activityType == 1) {
                if (activityStatus == 1) {
                    registryActivity();
                } else {
                    ActivityRankingActivity.enterActivityRanking(ActivityDetailsActivity.this, activityId, statusContent, path, title);
                }
            } else {
                if (activityStatus < ClubSignStatus.REGISTRATION_FULL) {
                    Intent intent = new Intent(ActivityDetailsActivity.this, SignUpActivity.class);
                    intent.putExtra("ageLimitDate", ageLimitDate);
                    intent.putExtra("activityId", activityId);
                    startActivity(intent);
                }
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getMessage() instanceof ChallengesBean) {
            ChallengesBean bean = (ChallengesBean) event.getMessage();
            if (activityId == bean.getId()) {
                activityStatus = bean.getActivityStatus();
                updateUi();
            }
        }
    }

    public static void enterActivityDetails(int activityId, Context context) {
        Intent intent = new Intent(context, ActivityDetailsActivity.class);
        intent.putExtra("activityId", activityId);
        context.startActivity(intent);
    }

    /**
     * 获取活动详情
     */
    public void queryActivity() {
        showLoadingDialog();
        NewsInfoRequest newsInfoRequest = new NewsInfoRequest();
        newsInfoRequest.setSession(getUserInfoEntity().getSession());
        newsInfoRequest.setId(activityId);
        RetrofitUtils.getApiUrl()
                .queryActivity(newsInfoRequest)
                .compose(RxHelper.observableToMain(ActivityDetailsActivity.this))
                .subscribe(new MyObserver<ActivityDetailsResponse>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(ActivityDetailsResponse activityDetailsResponse) {
                        initView(activityDetailsResponse);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        ToastUtils.showError(ActivityDetailsActivity.this, resultCode);
                    }
                });
    }

    /**
     * 页面初始化
     */
    @SuppressLint("SetTextI18n")
    private void initView(ActivityDetailsResponse activityDetailsResponse) {
        ageLimitDate = activityDetailsResponse.getAgeLimitDate();
        mActivityDetailsBinding.activityBottom.updateUi(getUserInfoEntity().getHeadUrl(), activityDetailsResponse.getLikeStatus() == 1, activityDetailsResponse.getGiveSum());
        if (AppUtils.isChinese()) {
            path = activityDetailsResponse.getTopCnCover();
            title = activityDetailsResponse.getCnTitle();
            GlideUtils.loadImageWelcome(activityDetailsResponse.getCnCover(), this, mActivityDetailsBinding.ivTitleTop);
            initWebView(activityDetailsResponse.getCnUrl());
            mActivityDetailsBinding.tvTitle.setText(activityDetailsResponse.getCnTitle());
        } else {
            path = activityDetailsResponse.getTopEnCover();
            title = activityDetailsResponse.getEnTitle();
            GlideUtils.loadImageWelcome(activityDetailsResponse.getEnCover(), this, mActivityDetailsBinding.ivTitleTop);
            initWebView(activityDetailsResponse.getEnUrl());
            mActivityDetailsBinding.tvTitle.setText(activityDetailsResponse.getEnTitle());
        }

        mActivityDetailsBinding.tvPerson.setText(String.valueOf(activityDetailsResponse.getSignNum()));
        mActivityDetailsBinding.tvCalendar.setText(AppUtils.localTimeToString(activityDetailsResponse.getActivityStartDate(), Config.TIME_RECORD) + "-" + AppUtils.localTimeToString(activityDetailsResponse.getActivityEndDate(), Config.TIME_RECORD));
        if ("1".equals(activityDetailsResponse.getActivityType())) {
            activityType = 1;
        } else {
            activityType = 2;
        }
        activityStatus = activityDetailsResponse.getUserSignstatus();
        updateUi();

        long currentTimeMillis = System.currentTimeMillis();
        long activityStartDate = activityDetailsResponse.getActivityStartDate();
        long activityEndDate = activityDetailsResponse.getActivityEndDate();
        long startDate = activityStartDate - 8 * 3600 * 1000;
        long endDate = activityEndDate - 8 * 3600 * 1000;
        long oneDay = 24 * 3600 * 1000;
        if (startDate >= currentTimeMillis) {
            long interval = startDate - currentTimeMillis;
            int day = (int) Math.ceil(interval / oneDay);
            statusContent = "离活动开始还有" + day + "天";
        } else if (endDate >= currentTimeMillis) {
            long interval = endDate - currentTimeMillis;
            int day = (int) Math.ceil(interval / oneDay);
            statusContent = "离活动结束还有" + day + "天";
        } else {
            statusContent = "活动已结束";
        }
        mActivityDetailsBinding.tvStatus.setText(statusContent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(String url) {
        //支持javascript
        mActivityDetailsBinding.activityWebView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        mActivityDetailsBinding.activityWebView.getSettings().setSupportZoom(false);
        // 设置出现缩放工具
        mActivityDetailsBinding.activityWebView.getSettings().setBuiltInZoomControls(false);
        //扩大比例的缩放
        mActivityDetailsBinding.activityWebView.getSettings().setUseWideViewPort(false);
        //自适应屏幕
        mActivityDetailsBinding.activityWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mActivityDetailsBinding.activityWebView.getSettings().setLoadWithOverviewMode(true);
        mActivityDetailsBinding.activityWebView.loadUrl(url);

        mActivityDetailsBinding.activityWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mActivityDetailsBinding.activityWebView.loadUrl(url);
                return true;
            }
        });

        mActivityDetailsBinding.activityWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                int finish = 100;
                if (newProgress == finish) {
                    hideLoadingDialog();
                }
            }
        });
    }

    /**
     * 活动点赞
     */
    private void addActivityLikes(String session, int newsId) {
        showLoadingDialog();
        NewsInfoRequest newsInfoRequest = new NewsInfoRequest();
        newsInfoRequest.setSession(session);
        newsInfoRequest.setId(newsId);
        RetrofitUtils.getApiUrl()
                .addActivityLikes(newsInfoRequest)
                .compose(RxHelper.observableToMain(ActivityDetailsActivity.this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadingDialog();
                        mActivityDetailsBinding.activityBottom.addLike();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(ActivityDetailsActivity.this, resultCode);
                    }
                });
    }

    /**
     * 新闻取消点赞
     */
    private void dleNewsLikesRequest(String session, int newsId) {
        showLoadingDialog();
        NewsInfoRequest newsInfoRequest = new NewsInfoRequest();
        newsInfoRequest.setSession(session);
        newsInfoRequest.setId(newsId);
        RetrofitUtils.getApiUrl()
                .dleActivityLikes(newsInfoRequest)
                .compose(RxHelper.observableToMain(ActivityDetailsActivity.this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadingDialog();
                        mActivityDetailsBinding.activityBottom.removeLike();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(ActivityDetailsActivity.this, resultCode);
                    }
                });
    }

    /**
     * 活动报名
     */
    private void registryActivity() {
        showLoadingDialog();
        RegistryActivityRequest registryActivityRequest = new RegistryActivityRequest();
        registryActivityRequest.setSession(getUserInfoEntity().getSession());
        registryActivityRequest.setActivityType("1");
        registryActivityRequest.setActivityId(activityId);
        RetrofitUtils.getApiUrl()
                .registryActivity(registryActivityRequest)
                .compose(RxHelper.observableToMain(ActivityDetailsActivity.this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadingDialog();
                        activityStatus = 2;
                        mActivityDetailsBinding.tvActivityStatus.setText(R.string.leader_board);
                        mActivityDetailsBinding.tvActivityStatus.setBackground(ContextCompat.getDrawable(ActivityDetailsActivity.this, R.drawable.shape_999_18));
                        ChallengesBean challengesBean = new ChallengesBean();
                        challengesBean.setId(activityId);
                        challengesBean.setActivityStatus(2);
                        EventBus.getDefault().post(new MessageEvent(challengesBean, 0));
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(ActivityDetailsActivity.this, resultCode);
                    }
                });
    }

    private void updateUi() {
        //官方
        if (activityType == 1) {
            switch (activityStatus) {
                case ClubSignStatus.SIGN_UP:
                    mActivityDetailsBinding.tvActivityStatus.setText(R.string.enter_nama);
                    mActivityDetailsBinding.tvActivityStatus.setBackground(ContextCompat.getDrawable(ActivityDetailsActivity.this, R.drawable.shape_main_18));
                    break;
                case ClubSignStatus.HAVE_SIGN_UP:
                    mActivityDetailsBinding.tvActivityStatus.setText(R.string.leader_board);
                    mActivityDetailsBinding.tvActivityStatus.setBackground(ContextCompat.getDrawable(ActivityDetailsActivity.this, R.drawable.shape_orange_18));
                    break;
                case ClubSignStatus.FINISH_SIGN_UP:
                    mActivityDetailsBinding.tvActivityStatus.setText(R.string.leader_board);
                    mActivityDetailsBinding.tvActivityStatus.setBackground(ContextCompat.getDrawable(ActivityDetailsActivity.this, R.drawable.shape_999_18));
                    break;
            }
        } else {
            switch (activityStatus) {
                case ClubSignStatus.SIGN_UP:
                    mActivityDetailsBinding.tvActivityStatus.setText(R.string.enter_nama);
                    mActivityDetailsBinding.tvActivityStatus.setBackground(ContextCompat.getDrawable(ActivityDetailsActivity.this, R.drawable.shape_main_18));
                    break;
                case ClubSignStatus.HAVE_SIGN_UP:
                    mActivityDetailsBinding.tvActivityStatus.setText(R.string.entered_nama);
                    mActivityDetailsBinding.tvActivityStatus.setBackground(ContextCompat.getDrawable(ActivityDetailsActivity.this, R.drawable.shape_club_18));
                    break;
                case ClubSignStatus.REGISTRATION_FULL:
                    mActivityDetailsBinding.tvActivityStatus.setText(R.string.enter_nama_full);
                    mActivityDetailsBinding.tvActivityStatus.setBackground(ContextCompat.getDrawable(ActivityDetailsActivity.this, R.drawable.shape_999_18));
                    break;
                case ClubSignStatus.CLOSE_SIGN_UP:
                    mActivityDetailsBinding.tvActivityStatus.setText(R.string.deadline_enter_nama);
                    mActivityDetailsBinding.tvActivityStatus.setBackground(ContextCompat.getDrawable(ActivityDetailsActivity.this, R.drawable.shape_999_18));
                    break;
                case ClubSignStatus.FINISH_SIGN_UP:
                    mActivityDetailsBinding.tvActivityStatus.setText(R.string.the_end);
                    mActivityDetailsBinding.tvActivityStatus.setBackground(ContextCompat.getDrawable(ActivityDetailsActivity.this, R.drawable.shape_999_18));
                    break;
            }
        }
    }
}