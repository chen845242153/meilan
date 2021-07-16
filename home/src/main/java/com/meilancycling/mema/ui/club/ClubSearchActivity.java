package com.meilancycling.mema.ui.club;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.ActivityClubSearchBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.ClubSearchRequest;
import com.meilancycling.mema.network.bean.response.ClubSearchResponse;
import com.meilancycling.mema.ui.adapter.ClubSearchRecyclerViewAdapter;
import com.meilancycling.mema.ui.club.model.ClubSearchModel;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class ClubSearchActivity extends BaseActivity {
    private ActivityClubSearchBinding mActivityClubSearchBinding;
    private ClubSearchRecyclerViewAdapter mClubSearchAdapter;
    private List<ClubSearchModel> adapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityClubSearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_club_search);
        mActivityClubSearchBinding.viewBack.setOnClickListener(view -> finish());

        mActivityClubSearchBinding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId == 0 || actionId == 3) && event != null) {
                String trim = mActivityClubSearchBinding.etSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)) {
                    clubSearch(trim);
                } else {
                    clubSearch("");
                }
            }
            return false;
        });
        mClubSearchAdapter = new ClubSearchRecyclerViewAdapter();
        mActivityClubSearchBinding.rvSearch.setLayoutManager(new LinearLayoutManager(this));
        mActivityClubSearchBinding.rvSearch.setAdapter(mClubSearchAdapter);
        adapterData = new ArrayList<>();
    }

    /**
     * 社区搜索
     */
    public void clubSearch(String commentContent) {
        ClubSearchRequest clubSearchRequest = new ClubSearchRequest();
        if (AppUtils.isChinese()) {
            clubSearchRequest.setCnTitle(commentContent);
        } else {
            clubSearchRequest.setEnTitle(commentContent);
        }
        clubSearchRequest.setSession(getUserInfoEntity().getSession());
        RetrofitUtils.getApiUrl()
                .clubSearch(clubSearchRequest)
                .compose(RxHelper.observableToMain(ClubSearchActivity.this))
                .subscribe(new MyObserver<ClubSearchResponse>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(ClubSearchResponse clubSearchResponse) {
                        if (clubSearchResponse != null) {
                            adapterData.clear();
                            if (clubSearchResponse.getNewsList() != null && clubSearchResponse.getNewsList().size() > 0) {
                                if (AppUtils.isChinese()) {
                                    for (int i = 0; i < clubSearchResponse.getNewsList().size(); i++) {
                                        ClubSearchModel clubSearchModel = new ClubSearchModel();
                                        if (i == 0) {
                                            clubSearchModel.headFlag = 1;
                                        } else {
                                            clubSearchModel.headFlag = 0;
                                        }
                                        ClubSearchResponse.NewsListBean newsListBean = clubSearchResponse.getNewsList().get(i);
                                        clubSearchModel.id = newsListBean.getId();
                                        clubSearchModel.picturePath = newsListBean.getCnCover();
                                        clubSearchModel.title = newsListBean.getCnTitle();
                                        clubSearchModel.date = AppUtils.localTimeToString(newsListBean.getCreateDate(), Config.TIME_RECORD);
                                        clubSearchModel.searchType = 1;
                                        adapterData.add(clubSearchModel);
                                    }
                                } else {
                                    for (int i = 0; i < clubSearchResponse.getNewsList().size(); i++) {
                                        ClubSearchModel clubSearchModel = new ClubSearchModel();
                                        if (i == 0) {
                                            clubSearchModel.headFlag = 1;
                                        } else {
                                            clubSearchModel.headFlag = 0;
                                        }
                                        ClubSearchResponse.NewsListBean newsListBean = clubSearchResponse.getNewsList().get(i);
                                        clubSearchModel.id = newsListBean.getId();
                                        clubSearchModel.picturePath = newsListBean.getEnCover();
                                        clubSearchModel.title = newsListBean.getEnTitle();
                                        clubSearchModel.date = AppUtils.localTimeToString(newsListBean.getCreateDate(), Config.TIME_RECORD);
                                        clubSearchModel.searchType = 1;
                                        adapterData.add(clubSearchModel);
                                    }
                                }
                            }

                            if (clubSearchResponse.getActivityList() != null && clubSearchResponse.getActivityList().size() > 0) {
                                if (AppUtils.isChinese()) {
                                    for (int i = 0; i < clubSearchResponse.getActivityList().size(); i++) {
                                        ClubSearchResponse.ActivityListBean activityListBean = clubSearchResponse.getActivityList().get(i);
                                        ClubSearchModel clubSearchModel = new ClubSearchModel();
                                        if (i == 0) {
                                            clubSearchModel.headFlag = 2;
                                        } else {
                                            clubSearchModel.headFlag = 0;
                                        }
                                        clubSearchModel.id = activityListBean.getId();
                                        clubSearchModel.picturePath = activityListBean.getCnCover();
                                        clubSearchModel.title = activityListBean.getCnTitle();
                                        clubSearchModel.date = AppUtils.localTimeToString(activityListBean.getActivityStartDate(), Config.TIME_RECORD)
                                                + "-" + AppUtils.localTimeToString(activityListBean.getActivityEndDate(), Config.TIME_RECORD);
                                        clubSearchModel.searchType = 2;
                                        clubSearchModel.contentType = Integer.parseInt(activityListBean.getContentType());
                                        clubSearchModel.titleFlag = activityListBean.getCnAddress();
                                        adapterData.add(clubSearchModel);
                                    }
                                } else {
                                    for (int i = 0; i < clubSearchResponse.getActivityList().size(); i++) {
                                        ClubSearchResponse.ActivityListBean activityListBean = clubSearchResponse.getActivityList().get(i);
                                        ClubSearchModel clubSearchModel = new ClubSearchModel();
                                        if (i == 0) {
                                            clubSearchModel.headFlag = 2;
                                        } else {
                                            clubSearchModel.headFlag = 0;
                                        }
                                        clubSearchModel.id = activityListBean.getId();
                                        clubSearchModel.picturePath = activityListBean.getEnCover();
                                        clubSearchModel.title = activityListBean.getEnTitle();
                                        clubSearchModel.date = AppUtils.localTimeToString(activityListBean.getActivityStartDate(), Config.TIME_RECORD)
                                                + "-" + AppUtils.localTimeToString(activityListBean.getActivityEndDate(), Config.TIME_RECORD);
                                        clubSearchModel.searchType = 2;
                                        clubSearchModel.contentType = Integer.parseInt(activityListBean.getContentType());
                                        clubSearchModel.titleFlag = activityListBean.getEnAddress();
                                        adapterData.add(clubSearchModel);
                                    }
                                }
                            }
                            mClubSearchAdapter.setData(adapterData);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        ToastUtils.showError(ClubSearchActivity.this, resultCode);
                    }
                });
    }

}