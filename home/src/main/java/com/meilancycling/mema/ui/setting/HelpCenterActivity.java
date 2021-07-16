package com.meilancycling.mema.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivityHelpCenterBinding;
import com.meilancycling.mema.utils.StatusAppUtils;

/**
 * 帮助中心
 *
 * @author lion
 */
public class HelpCenterActivity extends BaseActivity implements View.OnClickListener {
    private ActivityHelpCenterBinding mActivityHelpCenterBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusAppUtils.setColor(this, ContextCompat.getColor(this, R.color.white));
        mActivityHelpCenterBinding = DataBindingUtil.setContentView(this, R.layout.activity_help_center);
        mActivityHelpCenterBinding.ctvHelpCenter.setData(getString(R.string.help_center), this);
        mActivityHelpCenterBinding.tvFeedback.setOnClickListener(this);
        mActivityHelpCenterBinding.tvCommonProblem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tv_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.tv_common_problem:
                startActivity(new Intent(this, CommonProblemActivity.class));
                break;
            default:
        }
    }
}