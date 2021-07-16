package com.meilancycling.mema.ui.club.view;

import android.content.Context;
import android.view.View;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseCustomView;
import com.meilancycling.mema.databinding.ActivityItemViewBinding;
import com.meilancycling.mema.ui.club.ActivityDetailsActivity;
import com.meilancycling.mema.ui.club.model.ActivityItemModel;

public class ActivityItemView extends BaseCustomView<ActivityItemViewBinding, ActivityItemModel> {
    public ActivityItemView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_item_view;
    }

    @Override
    public void onRootClicked(View view) {
        ActivityDetailsActivity.enterActivityDetails(data.id, view.getContext());
    }

    @Override
    protected void setDataToView(ActivityItemModel activityItemModel) {
        binding.setModel(activityItemModel);
    }


}
