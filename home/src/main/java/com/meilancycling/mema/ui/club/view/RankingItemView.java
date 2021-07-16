package com.meilancycling.mema.ui.club.view;

import android.content.Context;
import android.view.View;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseCustomView;
import com.meilancycling.mema.databinding.RankingItemViewBinding;
import com.meilancycling.mema.ui.club.model.RankingItemModel;

/**
 * @Description: 记录顶部导航
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class RankingItemView extends BaseCustomView<RankingItemViewBinding, RankingItemModel> {

    public RankingItemView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ranking_item_view;
    }

    @Override
    public void onRootClicked(View view) {

    }

    @Override
    protected void setDataToView(RankingItemModel rankingItemModel) {
        binding.setModel(rankingItemModel);
    }
}
