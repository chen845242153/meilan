package com.meilancycling.mema.ui.club;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lion.common.eventbus.MessageEvent;
import com.lion.common.eventbus.club.ChallengesBean;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.customview.dialog.NoTitleAskDialog;
import com.meilancycling.mema.databinding.ActivityRankingBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.NewsInfoRequest;
import com.meilancycling.mema.network.bean.request.RegistryActivityRequest;
import com.meilancycling.mema.network.bean.response.ActivityRankingResponse;
import com.meilancycling.mema.ui.adapter.ActivityRankingRecyclerViewAdapter;
import com.meilancycling.mema.ui.club.model.ActivityRankingItemModel;
import com.meilancycling.mema.utils.GlideUtils;
import com.meilancycling.mema.utils.StatusAppUtils;
import com.meilancycling.mema.utils.ToastUtils;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ActivityRankingActivity extends BaseActivity implements View.OnClickListener {
    private ActivityRankingBinding mActivityRankingBinding;
    private int activityId;
    private ActivityRankingRecyclerViewAdapter activityRankingRecyclerViewAdapter;
    private List<ActivityRankingItemModel> adapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRankingBinding = DataBindingUtil.setContentView(this, R.layout.activity_ranking);
        StatusAppUtils.setTranslucentForImageView(this, 0, null);
        adapterData = new ArrayList<>();
        Intent intent = getIntent();
        activityId = intent.getIntExtra("activityId", 0);
        String statusContent = intent.getStringExtra("statusContent");
        String picturePath = intent.getStringExtra("path");
        String title = intent.getStringExtra("title");
        mActivityRankingBinding.ivBack.setOnClickListener(this);
        mActivityRankingBinding.viewExit.setOnClickListener(this);
        mActivityRankingBinding.tvTitle.setText(title);
        GlideUtils.loadImageWelcome(picturePath, this, mActivityRankingBinding.ivBg);
        mActivityRankingBinding.tvStatus.setText(statusContent);

        activityRankingRecyclerViewAdapter = new ActivityRankingRecyclerViewAdapter();
        mActivityRankingBinding.rvRankingList.setLayoutManager(new LinearLayoutManager(this));
        mActivityRankingBinding.rvRankingList.setAdapter(activityRankingRecyclerViewAdapter);

        activityRanking();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static void enterActivityRanking(Context context, int activityId, String statusContent, String path, String title) {
        Intent intent = new Intent(context, ActivityRankingActivity.class);
        intent.putExtra("activityId", activityId);
        intent.putExtra("statusContent", statusContent);
        intent.putExtra("path", path);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    /**
     * 活动排名
     */
    private void activityRanking() {
        showLoadingDialog();
        NewsInfoRequest newsInfoRequest = new NewsInfoRequest();
        newsInfoRequest.setSession(getUserInfoEntity().getSession());
        newsInfoRequest.setId(activityId);
        RetrofitUtils.getApiUrl()
                .activityRanking(newsInfoRequest)
                .compose(RxHelper.observableToMain(ActivityRankingActivity.this))
                .subscribe(new MyObserver<ActivityRankingResponse>() {
                    @Override
                    public void onSuccess(ActivityRankingResponse result) {
                        hideLoadingDialog();
                        mActivityRankingBinding.ctvPerson.setText(String.valueOf(result.getSignNum()));
                        mActivityRankingBinding.ctvRanking.setText(String.valueOf(result.getUserRanking()));
                        if (result.getList() != null && result.getList().size() > 0) {
                            for (int i = 0; i < result.getList().size(); i++) {
                                ActivityRankingResponse.ListBean listBean = result.getList().get(i);
                                ActivityRankingItemModel activityRankingItemModel = new ActivityRankingItemModel();
                                activityRankingItemModel.ranking = i;
                                activityRankingItemModel.userName = listBean.getNickName();
                                activityRankingItemModel.countries = listBean.getNationalFlag();
                                activityRankingItemModel.userPath = listBean.getHeaderUrl();
                                activityRankingItemModel.odo = listBean.getOdo();
                                adapterData.add(activityRankingItemModel);
                            }
                        }
                        activityRankingRecyclerViewAdapter.setData(adapterData);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(ActivityRankingActivity.this, resultCode);
                    }
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.view_exit:
                NoTitleAskDialog noTitleAskDialog = new NoTitleAskDialog(this, getString(R.string.ask_exit_activity), getString(R.string.confirm), getString(R.string.cancel));
                noTitleAskDialog.show();
                noTitleAskDialog.setAskNoTitleDialogListener(new NoTitleAskDialog.AskNoTitleDialogListener() {
                    @Override
                    public void clickLeft() {
                        dleRegActivity();
                    }

                    @Override
                    public void clickRight() {

                    }
                });
                break;
            default:
        }
    }

    /**
     * 取消报名
     */
    private void dleRegActivity() {
        showLoadingDialog();
        RegistryActivityRequest registryActivityRequest = new RegistryActivityRequest();
        registryActivityRequest.setSession(getUserInfoEntity().getSession());
        registryActivityRequest.setActivityId(activityId);
        RetrofitUtils.getApiUrl()
                .dleRegActivity(registryActivityRequest)
                .compose(RxHelper.observableToMain(ActivityRankingActivity.this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadingDialog();
                        ChallengesBean challengesBean = new ChallengesBean();
                        challengesBean.setId(activityId);
                        challengesBean.setActivityStatus(1);

                        EventBus.getDefault().post(new MessageEvent(challengesBean, 0));
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(ActivityRankingActivity.this, resultCode);
                    }
                });
    }
}