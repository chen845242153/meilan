package com.meilancycling.mema.ui.club.view;

import android.content.Context;
import android.view.View;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseCustomView;
import com.meilancycling.mema.databinding.ActivityItemViewBinding;
import com.meilancycling.mema.databinding.ActivityRankingBinding;
import com.meilancycling.mema.databinding.ActivityRankingViewBinding;
import com.meilancycling.mema.ui.club.ActivityDetailsActivity;
import com.meilancycling.mema.ui.club.model.ActivityItemModel;
import com.meilancycling.mema.ui.club.model.ActivityRankingItemModel;

public class ActivityRankingItemView extends BaseCustomView<ActivityRankingViewBinding, ActivityRankingItemModel> {
    public ActivityRankingItemView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ranking_view;
    }

    @Override
    public void onRootClicked(View view) {

    }

    @Override
    protected void setDataToView(ActivityRankingItemModel activityRankingItemModel) {
        binding.setModel(activityRankingItemModel);
    }


}
