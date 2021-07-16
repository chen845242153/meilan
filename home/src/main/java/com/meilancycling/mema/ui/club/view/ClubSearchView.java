package com.meilancycling.mema.ui.club.view;

import android.content.Context;
import android.view.View;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseCustomView;
import com.meilancycling.mema.databinding.ClubSearchViewBinding;
import com.meilancycling.mema.ui.club.ActivityDetailsActivity;
import com.meilancycling.mema.ui.club.NewsDetailsActivity;
import com.meilancycling.mema.ui.club.model.ClubSearchModel;

public class ClubSearchView extends BaseCustomView<ClubSearchViewBinding, ClubSearchModel> {
    public ClubSearchView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.club_search_view;
    }

    @Override
    public void onRootClicked(View view) {
        if (data.searchType == 1) {
            NewsDetailsActivity.enterNewsDetails(data.id, getContext());
        } else {
            ActivityDetailsActivity.enterActivityDetails(data.id, getContext());
        }
    }

    @Override
    protected void setDataToView(ClubSearchModel clubSearchModel) {
        binding.setModel(clubSearchModel);
    }


}
