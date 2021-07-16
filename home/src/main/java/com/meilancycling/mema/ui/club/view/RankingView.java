package com.meilancycling.mema.ui.club.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseCustomView;
import com.meilancycling.mema.databinding.RankingItemViewBinding;
import com.meilancycling.mema.databinding.RankingViewBinding;
import com.meilancycling.mema.ui.club.model.RankingItemModel;
import com.meilancycling.mema.ui.club.model.RankingTopModel;


/**
 * @Description: 记录顶部导航
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class RankingView extends BaseCustomView<RankingViewBinding, RankingTopModel> {

    public RankingView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ranking_view;
    }

    @Override
    public void onRootClicked(View view) {

    }

    @Override
    protected void setDataToView(RankingTopModel rankingTopModel) {
        binding.setModel(rankingTopModel);
    }
}
