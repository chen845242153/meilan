package com.meilancycling.mema.ui.record;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivityRecordHomeBinding;
import com.meilancycling.mema.utils.StatusAppUtils;

import java.util.Calendar;

/**
 * @author lion
 * 记录统计
 */
public class RecordHomeActivity extends BaseActivity implements View.OnClickListener {
    private ActivityRecordHomeBinding mActivityRecordHomeBinding;
    private RecordHomeFragment mRecordHomeFragment = new RecordHomeFragment();
    private CalendarFragment mCalendarFragment = new CalendarFragment();
    private int type;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusAppUtils.setColor(this, getResources().getColor(R.color.white));
        mActivityRecordHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_record_home);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.record_home_content, mRecordHomeFragment, mRecordHomeFragment.getClass().getSimpleName());
        transaction.commit();
        type = 0;
        mActivityRecordHomeBinding.tvRecordTitle.setText(R.string.record);
        mActivityRecordHomeBinding.ivHome.setVisibility(View.VISIBLE);
        mActivityRecordHomeBinding.tvDate.setVisibility(View.VISIBLE);
        mActivityRecordHomeBinding.ivRecordType.setVisibility(View.GONE);
        Calendar now = Calendar.getInstance();
        mActivityRecordHomeBinding.tvDate.setText(String.valueOf(now.get(Calendar.DAY_OF_MONTH)));
        mActivityRecordHomeBinding.llRecordHomeBack.setOnClickListener(this);
        mActivityRecordHomeBinding.llRecordType.setOnClickListener(this);
    }

    private void switchFragment(Fragment from, Fragment to) {
        if (from != to && to != null) {
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!to.isAdded()) {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.add(R.id.record_home_content, to, to.getClass().getName()).commit();
            } else {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.show(to).commit();
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_record_home_back) {
            finish();
        } else {
            if (type == 0) {
                mActivityRecordHomeBinding.ivHome.setVisibility(View.GONE);
                mActivityRecordHomeBinding.tvDate.setVisibility(View.GONE);
                mActivityRecordHomeBinding.ivRecordType.setVisibility(View.VISIBLE);
                mActivityRecordHomeBinding.tvRecordTitle.setText(R.string.calendar);
                switchFragment(mRecordHomeFragment, mCalendarFragment);
                type = 1;
            } else {
                mActivityRecordHomeBinding.ivHome.setVisibility(View.VISIBLE);
                mActivityRecordHomeBinding.tvDate.setVisibility(View.VISIBLE);
                mActivityRecordHomeBinding.ivRecordType.setVisibility(View.GONE);
                mActivityRecordHomeBinding.tvRecordTitle.setText(R.string.record);
                switchFragment(mCalendarFragment, mRecordHomeFragment);
                type = 0;
            }
        }
    }
}