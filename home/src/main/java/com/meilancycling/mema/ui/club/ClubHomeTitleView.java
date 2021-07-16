package com.meilancycling.mema.ui.club;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.ClubHomeTitleBinding;
import com.meilancycling.mema.ui.club.model.ClubHomeTitleModel;


public class ClubHomeTitleView extends LinearLayout implements View.OnClickListener {
    private ClubHomeTitleBinding mClubHomeTitleBinding;
    private ClubHomeTitleModel mClubHomeTitleModel;

    public ClubHomeTitleView(Context context) {
        this(context, null);
    }

    public ClubHomeTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClubHomeTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mClubHomeTitleBinding = DataBindingUtil.inflate(inflater, R.layout.club_home_title, this, false);
        mClubHomeTitleModel = new ClubHomeTitleModel();
        this.addView(mClubHomeTitleBinding.getRoot());
        mClubHomeTitleBinding.tvFeed.setOnClickListener(this);
        mClubHomeTitleBinding.tvChallenges.setOnClickListener(this);
        mClubHomeTitleBinding.tvLeaderBoard.setOnClickListener(this);
        mClubHomeTitleBinding.viewSearch.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_feed:
                mClubHomeTitleClickListener.feedClick();
                mClubHomeTitleModel.setItemSelect(0);
                break;
            case R.id.tv_challenges:
                mClubHomeTitleClickListener.challengesClick();
                mClubHomeTitleModel.setItemSelect(1);
                break;
            case R.id.tv_leader_board:
                mClubHomeTitleClickListener.leaderBoardClick();
                mClubHomeTitleModel.setItemSelect(2);
                break;
            case R.id.view_search:
                getContext().startActivity(new Intent(getContext(), ClubSearchActivity.class));
                break;
        }
    }

    public void registerClub(boolean redPosition, ClubHomeTitleClickListener clubHomeTitleClickListener) {
        mClubHomeTitleModel.setShowRedPoint(redPosition);
        mClubHomeTitleBinding.setModel(mClubHomeTitleModel);
        mClubHomeTitleClickListener = clubHomeTitleClickListener;
    }

    private ClubHomeTitleClickListener mClubHomeTitleClickListener;

    public interface ClubHomeTitleClickListener {
        void feedClick();

        void challengesClick();

        void leaderBoardClick();
    }

}
