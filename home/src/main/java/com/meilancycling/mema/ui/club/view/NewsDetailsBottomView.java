package com.meilancycling.mema.ui.club.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.NewsDetailsBottomBinding;
import com.meilancycling.mema.ui.club.model.NewsBottomModel;

public class NewsDetailsBottomView extends LinearLayout implements View.OnClickListener {
    private NewsDetailsBottomBinding mNewsDetailsBottomBinding;
    private NewsBottomModel mNewsBottomModel;

    public NewsDetailsBottomView(Context context) {
        this(context, null);
    }

    public NewsDetailsBottomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewsDetailsBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (mNewsBottomModel == null) {
            mNewsBottomModel = new NewsBottomModel();
        }
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mNewsDetailsBottomBinding = DataBindingUtil.inflate(inflater, R.layout.news_details_bottom, this, false);
            this.addView(mNewsDetailsBottomBinding.getRoot());
        }
        mNewsDetailsBottomBinding.viewLike.setOnClickListener(this);
        mNewsDetailsBottomBinding.tvContent.setOnClickListener(this);
    }

    public void updateUi(String avatarUrl, boolean isLike, int likeNumber) {
        mNewsBottomModel.avatarUrl = avatarUrl;
        mNewsBottomModel.isLike = isLike;
        mNewsBottomModel.likeNumber = likeNumber;
        mNewsDetailsBottomBinding.setModel(mNewsBottomModel);
    }

    public void addLike() {
        mNewsBottomModel.isLike = true;
        mNewsBottomModel.likeNumber = mNewsBottomModel.likeNumber + 1;
        mNewsDetailsBottomBinding.setModel(mNewsBottomModel);
    }

    public void removeLike() {
        mNewsBottomModel.isLike = false;
        mNewsBottomModel.likeNumber = mNewsBottomModel.likeNumber - 1;
        mNewsDetailsBottomBinding.setModel(mNewsBottomModel);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_like:
                if (mNewsBottomModel.isLike) {
                    mNewsDetailsBottomViewCallback.dleNewsLikes();
                } else {
                    mNewsDetailsBottomViewCallback.addNewsLikes();
                }
                break;
            case R.id.tv_content:
                mNewsDetailsBottomViewCallback.clickComment();
                break;
            default:
        }
    }

    public interface NewsDetailsBottomViewCallback {
        void addNewsLikes();

        void dleNewsLikes();

        void clickComment();
    }

    private NewsDetailsBottomViewCallback mNewsDetailsBottomViewCallback;

    public void setNewsDetailsBottomViewCallback(NewsDetailsBottomViewCallback mNewsDetailsBottomViewCallback) {
        this.mNewsDetailsBottomViewCallback = mNewsDetailsBottomViewCallback;
    }
}
