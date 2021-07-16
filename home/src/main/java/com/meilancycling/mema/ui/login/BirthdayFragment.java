package com.meilancycling.mema.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.FragmentInitBirthdayBinding;
import com.meilancycling.mema.utils.AppUtils;

/**
 * @Description: 出生日期
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 2:17 PM
 */
public class BirthdayFragment extends BaseFragment {
    private PerfectInformationActivity activity;
    private FragmentInitBirthdayBinding mFragmentInitBirthdayBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentInitBirthdayBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_init_birthday, container, false);
        activity = (PerfectInformationActivity) getActivity();
        assert activity != null;
        long current = AppUtils.timeToLong(activity.mUserInfoEntity.getBirthday(), Config.DEFAULT_PATTERN);
        long endTime = System.currentTimeMillis();
        long startTime = AppUtils.timeToLong("1900-01-01", Config.DEFAULT_PATTERN);
        mFragmentInitBirthdayBinding.tvValue.setText(activity.mUserInfoEntity.getBirthday());
        showData(current, startTime, endTime);
        return mFragmentInitBirthdayBinding.getRoot();
    }

    private void showData(long current, long startTime, long endTime) {
        mFragmentInitBirthdayBinding.dp.setMaxDate(endTime);
        mFragmentInitBirthdayBinding.dp.setMinDate(startTime);
        String[] result = AppUtils.timeToString(current, Config.DEFAULT_PATTERN).split("-");
        mFragmentInitBirthdayBinding.dp.init(Integer.parseInt(result[0]), Integer.parseInt(result[1]), Integer.parseInt(result[2]), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String value = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                activity.mUserInfoEntity.setBirthday(value);
                mFragmentInitBirthdayBinding.tvValue.setText(activity.mUserInfoEntity.getBirthday());
            }
        });
    }
}
