package com.meilancycling.mema.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.ActivityWelcomeBinding;
import com.meilancycling.mema.ui.adapter.WelcomePagerAdapter;
import com.meilancycling.mema.ui.login.LoginActivity;
import com.meilancycling.mema.utils.SPUtils;
import com.meilancycling.mema.utils.StatusAppUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * 欢迎界面
 *
 * @author lion
 */
public class WelcomeActivity extends RxAppCompatActivity implements View.OnClickListener {
    private ActivityWelcomeBinding mActivityWelcomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusAppUtils.setTranslucentForImageView(this, 0, null);
        mActivityWelcomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        WelcomePagerAdapter mWelcomePagerAdapter = new WelcomePagerAdapter();
        mActivityWelcomeBinding.tvConfirm.setOnClickListener(this);
        mActivityWelcomeBinding.ivNext.setOnClickListener(this);
        mActivityWelcomeBinding.tvSkip.setOnClickListener(this);
        mActivityWelcomeBinding.vpWelcome.setAdapter(mWelcomePagerAdapter);
        mActivityWelcomeBinding.vpWelcome.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mActivityWelcomeBinding.viewItem1.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_main));
                        mActivityWelcomeBinding.viewItem2.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_e2));
                        mActivityWelcomeBinding.viewItem3.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_e2));
                        mActivityWelcomeBinding.viewItem4.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_e2));
                        mActivityWelcomeBinding.tvConfirm.setVisibility(View.INVISIBLE);
                        mActivityWelcomeBinding.ivNext.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mActivityWelcomeBinding.viewItem1.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_e2));
                        mActivityWelcomeBinding.viewItem2.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_main));
                        mActivityWelcomeBinding.viewItem3.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_e2));
                        mActivityWelcomeBinding.viewItem4.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_e2));
                        mActivityWelcomeBinding.tvConfirm.setVisibility(View.INVISIBLE);
                        mActivityWelcomeBinding.ivNext.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        mActivityWelcomeBinding.viewItem1.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_e2));
                        mActivityWelcomeBinding.viewItem2.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_e2));
                        mActivityWelcomeBinding.viewItem3.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_main));
                        mActivityWelcomeBinding.viewItem4.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_e2));
                        mActivityWelcomeBinding.tvConfirm.setVisibility(View.INVISIBLE);
                        mActivityWelcomeBinding.ivNext.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        mActivityWelcomeBinding.viewItem1.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_e2));
                        mActivityWelcomeBinding.viewItem2.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_e2));
                        mActivityWelcomeBinding.viewItem3.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_e2));
                        mActivityWelcomeBinding.viewItem4.setBackground(ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.shape_circle_main));
                        mActivityWelcomeBinding.tvConfirm.setVisibility(View.VISIBLE);
                        mActivityWelcomeBinding.ivNext.setVisibility(View.INVISIBLE);
                        break;
                    default:
                }
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_skip:
            case R.id.tv_confirm:
                SPUtils.putBoolean(this, Config.IS_FIRST, true);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.iv_next:
                int currentItem = mActivityWelcomeBinding.vpWelcome.getCurrentItem();
                mActivityWelcomeBinding.vpWelcome.setCurrentItem(currentItem + 1);
                break;
            default:
        }
    }


    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}