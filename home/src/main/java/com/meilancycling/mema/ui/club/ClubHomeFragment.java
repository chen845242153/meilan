package com.meilancycling.mema.ui.club;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.databinding.FragmentClubHomeBinding;

public class ClubHomeFragment extends BaseFragment {
    private FragmentClubHomeBinding mFragmentClubHomeBinding;
    private boolean mIsHavenNew;
    private String mSession;
    private Fragment fromFragment = null;
    private static ClubHomeFragment mClubHomeFragment;

    private NewsFragment mNewsFragment;
    private ChallengesFragment mChallengesFragment;
    private RankFragment mRankFragment;

    private ClubHomeFragment(boolean isHavenNew, String session) {
        mIsHavenNew = isHavenNew;
        mSession = session;
    }

    public static ClubHomeFragment getInstance(boolean isHavenNew, String session) {
        if (mClubHomeFragment == null) {
            mClubHomeFragment = new ClubHomeFragment(isHavenNew, session);
        }
        return mClubHomeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentClubHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_club_home, container, false);
        return mFragmentClubHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNewsFragment = new NewsFragment(mSession);
        mChallengesFragment = new ChallengesFragment(mSession);
        mRankFragment = new RankFragment(mSession);
        switchFragment(mNewsFragment);
        mFragmentClubHomeBinding.titleView.registerClub(mIsHavenNew, new ClubHomeTitleView.ClubHomeTitleClickListener() {

            @Override
            public void challengesClick() {
                switchFragment(mChallengesFragment);
            }

            @Override
            public void leaderBoardClick() {
                switchFragment(mRankFragment);
            }

            @Override
            public void feedClick() {
                switchFragment(mNewsFragment);
            }
        });
    }

    private void switchFragment(Fragment fragCategory) {
        if (fromFragment != fragCategory && fragCategory != null) {
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
            if (fromFragment != null) {
                fragmentTransaction.hide(fromFragment);
            }
            if (!fragCategory.isAdded()) {
                fragmentTransaction.add(R.id.fl_club, fragCategory, fragCategory.getClass().getSimpleName()).commit();
            } else {
                fragmentTransaction.show(fragCategory).commit();
            }
            fromFragment = fragCategory;
        }
    }
}
