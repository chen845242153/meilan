package com.meilancycling.mema.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.ActivityPerfectInformationBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.EditUserInformation;
import com.meilancycling.mema.service.DeviceControllerService;
import com.meilancycling.mema.ui.home.HomeActivity;
import com.meilancycling.mema.ui.setting.ChooseCountryActivity;

/**
 * 完善信息
 *
 * @author lion 571135591
 */
public class PerfectInformationActivity extends BaseActivity implements View.OnClickListener {
    private ActivityPerfectInformationBinding mActivityPerfectInformationBinding;
    private int number;
    public UserInfoEntity mUserInfoEntity;
    private boolean isClick;
    public String nationalFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityPerfectInformationBinding = DataBindingUtil.setContentView(this, R.layout.activity_perfect_information);
        number = 0;
        mActivityPerfectInformationBinding.tvNext.setOnClickListener(this);
        mActivityPerfectInformationBinding.ivBack.setOnClickListener(this);
        mUserInfoEntity = getUserInfoEntity();
        showUi();
    }

    public void isClick(boolean flag) {
        isClick = flag;
        if (isClick) {
            mActivityPerfectInformationBinding.tvNext.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_main_23));
        } else {
            mActivityPerfectInformationBinding.tvNext.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_f2_23));
        }
    }

    private void showUi() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (number) {
            case 0:
                mActivityPerfectInformationBinding.ivBack.setVisibility(View.GONE);
                mActivityPerfectInformationBinding.tvNext.setText(R.string.next_step);
                CountriesFragment countriesFragment = new CountriesFragment();
                transaction.replace(R.id.perfect_information, countriesFragment, countriesFragment.getClass().getSimpleName());
                mActivityPerfectInformationBinding.view1.setVisibility(View.INVISIBLE);
                mActivityPerfectInformationBinding.view2.setVisibility(View.INVISIBLE);
                mActivityPerfectInformationBinding.view3.setVisibility(View.INVISIBLE);
                mActivityPerfectInformationBinding.view4.setVisibility(View.INVISIBLE);
                mActivityPerfectInformationBinding.view5.setVisibility(View.INVISIBLE);
                break;
            case 1:
                mActivityPerfectInformationBinding.ivBack.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.tvNext.setText(R.string.next_step);
                GenderFragment genderFragment = new GenderFragment();
                transaction.replace(R.id.perfect_information, genderFragment, genderFragment.getClass().getSimpleName());
                mActivityPerfectInformationBinding.view1.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view2.setVisibility(View.INVISIBLE);
                mActivityPerfectInformationBinding.view3.setVisibility(View.INVISIBLE);
                mActivityPerfectInformationBinding.view4.setVisibility(View.INVISIBLE);
                mActivityPerfectInformationBinding.view5.setVisibility(View.INVISIBLE);
                break;
            case 2:
                mActivityPerfectInformationBinding.ivBack.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.tvNext.setText(R.string.next_step);
                UnitFragment unitFragment = new UnitFragment();
                transaction.replace(R.id.perfect_information, unitFragment, unitFragment.getClass().getSimpleName());
                mActivityPerfectInformationBinding.view1.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view2.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view3.setVisibility(View.INVISIBLE);
                mActivityPerfectInformationBinding.view4.setVisibility(View.INVISIBLE);
                mActivityPerfectInformationBinding.view5.setVisibility(View.INVISIBLE);
                break;
            case 3:
                mActivityPerfectInformationBinding.ivBack.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.tvNext.setText(R.string.next_step);
                HeightFragment heightFragment = new HeightFragment();
                transaction.replace(R.id.perfect_information, heightFragment, heightFragment.getClass().getSimpleName());
                mActivityPerfectInformationBinding.view1.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view2.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view3.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view4.setVisibility(View.INVISIBLE);
                mActivityPerfectInformationBinding.view5.setVisibility(View.INVISIBLE);
                break;
            case 4:
                mActivityPerfectInformationBinding.ivBack.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.tvNext.setText(R.string.next_step);
                WeightFragment weightFragment = new WeightFragment();
                transaction.replace(R.id.perfect_information, weightFragment, weightFragment.getClass().getSimpleName());
                mActivityPerfectInformationBinding.view1.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view2.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view3.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view4.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view5.setVisibility(View.INVISIBLE);
                break;
            case 5:
                mActivityPerfectInformationBinding.ivBack.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.tvNext.setText(R.string.finish);
                BirthdayFragment birthdayFragment = new BirthdayFragment();
                transaction.replace(R.id.perfect_information, birthdayFragment, birthdayFragment.getClass().getSimpleName());
                mActivityPerfectInformationBinding.view1.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view2.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view3.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view4.setVisibility(View.VISIBLE);
                mActivityPerfectInformationBinding.view5.setVisibility(View.VISIBLE);
                break;
            default:
        }
        transaction.commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                --number;
                showUi();
                break;
            case R.id.tv_next:
                if (isClick) {
                    if (number == 4) {
                        saveDataAndUpload();
                    } else {
                        ++number;
                        showUi();
                    }
                }

                break;
            default:
        }
    }

    /**
     * 保存数据并且上传
     */
    private void saveDataAndUpload() {
        updateUserInfoEntity(mUserInfoEntity);
        EditUserInformation editUserInformation = new EditUserInformation();
        editUserInformation.setSession(mUserInfoEntity.getSession());
        editUserInformation.setCountry(mUserInfoEntity.getCountry());
        editUserInformation.setNationalFlag(nationalFlag);
        editUserInformation.setBirthday(mUserInfoEntity.getBirthday());
        editUserInformation.setHeight(String.valueOf(mUserInfoEntity.getHeight()));
        editUserInformation.setNickName(mUserInfoEntity.getNickname());
        editUserInformation.setSex(String.valueOf(mUserInfoEntity.getGender()));
        editUserInformation.setWeight(String.valueOf(mUserInfoEntity.getWeight()));
        RetrofitUtils.getApiUrl()
                .editUserInformation(editUserInformation)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        if (mUserInfoEntity.getRegType() != Config.TYPE_PHONE && mUserInfoEntity.getRegType() != Config.TYPE_MAILBOX) {
                            startActivity(new Intent(PerfectInformationActivity.this, BindingEmailActivity.class));
                        } else {
                            startActivity(new Intent(PerfectInformationActivity.this, HomeActivity.class));
                        }
                        finish();
                    }
                });
    }

    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}