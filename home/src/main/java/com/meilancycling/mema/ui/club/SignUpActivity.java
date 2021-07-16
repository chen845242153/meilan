package com.meilancycling.mema.ui.club;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.lion.common.eventbus.MessageEvent;
import com.lion.common.eventbus.club.ChallengesBean;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.ActivitySignUpBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.RegistryActivityRequest;
import com.meilancycling.mema.network.bean.request.SelectRegRequest;
import com.meilancycling.mema.network.bean.request.UpdateRegRequest;
import com.meilancycling.mema.network.bean.response.SelectRegResponse;
import com.meilancycling.mema.ui.setting.ChooseCountryActivity;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.StatusAppUtils;
import com.meilancycling.mema.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySignUpBinding mActivitySignUpBinding;
    private long ageLimitDate;
    private int activityId;
    private SelectRegResponse mSelectRegResponse;
    private final String flag = "&*&";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusAppUtils.setTranslucentForImageView(this, 0, null);
        mActivitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        ageLimitDate = getIntent().getLongExtra("ageLimitDate", 0);
        activityId = getIntent().getIntExtra("activityId", 0);
        selectRegActivity();
        mActivitySignUpBinding.tvNumber.setOnClickListener(this);
        mActivitySignUpBinding.tvBirthday.setOnClickListener(this);
        mActivitySignUpBinding.viewAdd.setOnClickListener(this);
        mActivitySignUpBinding.viewReduce.setOnClickListener(this);
        mActivitySignUpBinding.tvSubmit.setOnClickListener(this);
        mActivitySignUpBinding.ivBack.setOnClickListener(this);
        mActivitySignUpBinding.tvCancel.setOnClickListener(this);
    }

    /**
     * 查询报名信息
     */
    private void selectRegActivity() {
        showLoadingDialog();
        SelectRegRequest selectRegRequest = new SelectRegRequest();
        selectRegRequest.setSession(getUserInfoEntity().getSession());
        selectRegRequest.setActivityId(activityId);
        RetrofitUtils.getApiUrl()
                .selectRegActivity(selectRegRequest)
                .compose(RxHelper.observableToMain(SignUpActivity.this))
                .subscribe(new MyObserver<SelectRegResponse>() {
                    @Override
                    public void onSuccess(SelectRegResponse result) {
                        hideLoadingDialog();
                        updateUi(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        updateUi(null);
                    }
                });
    }

    @SuppressLint({"StringFormatInvalid", "SetTextI18n"})
    private void updateUi(SelectRegResponse result) {
        mSelectRegResponse = result;
        long diff = System.currentTimeMillis() - ageLimitDate;
        if (diff > 0) {
            String tips = getString(R.string.birthday_tips);
            long oneYear = 365 * 24 * 3600 * 1000L;
            mActivitySignUpBinding.tvBirthdayTips.setText(String.format(tips, (int) Math.ceil((double) diff / oneYear)));
        }
        if (result == null) {
            mActivitySignUpBinding.tvCancel.setVisibility(View.GONE);
            mActivitySignUpBinding.ivReduce.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.club_reduce));
            mActivitySignUpBinding.ivAdd.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.club_add_main));
            mActivitySignUpBinding.tvPerson.setText(String.valueOf(1));
            mActivitySignUpBinding.tvNumber.setText("+86");
        } else {
            mActivitySignUpBinding.tvBirthday.setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.black_3));
            mActivitySignUpBinding.etName.setText(result.getUserName());
            String[] split = result.getPhone().replace(flag, "lion").split("lion");
            mActivitySignUpBinding.tvNumber.setText(split[0]);
            mActivitySignUpBinding.etPhone.setText(split[1]);
            mActivitySignUpBinding.tvBirthday.setText(result.getAge());
            mActivitySignUpBinding.etEmail.setText(result.getMail());
            mActivitySignUpBinding.etProblem.setText(result.getRemarks());
            showNumber(result.getNumber());
            mActivitySignUpBinding.tvPerson.setText(String.valueOf(result.getNumber()));
        }
    }

    private void showNumber(int number) {
        switch (number) {
            case 1:
                mActivitySignUpBinding.ivReduce.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.club_reduce));
                mActivitySignUpBinding.ivAdd.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.club_add_main));
                break;
            case 2:
                mActivitySignUpBinding.ivReduce.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.club_reduce_main));
                mActivitySignUpBinding.ivAdd.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.club_add_main));
                break;
            case 3:
                mActivitySignUpBinding.ivReduce.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.club_reduce_main));
                mActivitySignUpBinding.ivAdd.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.club_add));
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_number:
                startActivityForResult(new Intent(this, ChooseCountryActivity.class), 1);
                break;
            case R.id.tv_birthday:
                long current = ageLimitDate;
                long endTime = ageLimitDate;
                long startTime = AppUtils.timeToLong("1900-01-01", Config.DEFAULT_PATTERN);
                dateDialog(this, current, startTime, endTime, listener);
                break;
            case R.id.view_add:
                String data = mActivitySignUpBinding.tvPerson.getText().toString().trim();
                if (!TextUtils.isEmpty(data)) {
                    int number = Integer.parseInt(data);
                    if (number < 3) {
                        ++number;
                    }
                    mActivitySignUpBinding.tvPerson.setText(String.valueOf(number));
                    showNumber(number);
                }
                break;
            case R.id.view_reduce:
                data = mActivitySignUpBinding.tvPerson.getText().toString().trim();
                if (!TextUtils.isEmpty(data)) {
                    int number = Integer.parseInt(data);
                    if (number > 1) {
                        --number;
                    }
                    mActivitySignUpBinding.tvPerson.setText(String.valueOf(number));
                    showNumber(number);
                }
                break;
            case R.id.tv_submit:
                submitData();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_cancel:
                dleRegActivity();
                break;
            default:
        }
    }

    @SuppressLint("SimpleDateFormat")
    private final OnTimeSelectListener listener = new OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            SimpleDateFormat formatter = new SimpleDateFormat(Config.DEFAULT_PATTERN);
            mActivitySignUpBinding.tvBirthday.setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.black_3));
            mActivitySignUpBinding.tvBirthday.setText(formatter.format(date));
        }
    };
    private TimePickerView mTimePickerView;

    /**
     * 时间选择器
     */
    public void dateDialog(Context context, long time, long startTime, long endTime, OnTimeSelectListener listener) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTimeInMillis(time);
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(startTime);
        //设置终止年份
        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE));

        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(endTime);
        //设置终止年份
        endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DATE));

        mTimePickerView = new TimePickerBuilder(context, listener)
                .setLayoutRes(R.layout.pickerview_custom, v -> {
                    TextView title = v.findViewById(R.id.tv_cancel);
                    TextView confirm = v.findViewById(R.id.tv_finish);
                    confirm.setOnClickListener(v1 -> {
                        mTimePickerView.dismiss();
                        mTimePickerView.returnData();
                    });
                    title.setOnClickListener(v12 -> mTimePickerView.dismiss());
                })
                //设置空字符串以隐藏单位提示
                .setLabel("", "", "", "", "", "")
                .setType(new boolean[]{true, true, true, false, false, false})
                //滚轮文字大小
                .setContentTextSize(18)
                //滚轮背景颜色 Night mode
                .setBgColor(ContextCompat.getColor(SignUpActivity.this, R.color.white))
                //设置分割线
                .setDividerColor(Color.parseColor("#FFDCDCDC"))
                .setTextColorCenter(ContextCompat.getColor(SignUpActivity.this, R.color.black_3))
                .setOutSideCancelable(false)
                .setItemVisibleCount(5)
                .setLineSpacingMultiplier(2)
                // 如果不设置的话，默认是系统时间*/
                .setDate(selectedDate)
                //起始终止年月日设定
                .setRangDate(startDate, endDate)
                //是否显示为对话框样式
                .isDialog(false)
                .setGravity(Gravity.CENTER)
                .build();
        mTimePickerView.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String phone = data.getStringExtra("phone");
            mActivitySignUpBinding.tvNumber.setText(phone);
        }
    }

    private void submitData() {
        String name = mActivitySignUpBinding.etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.show(this, getString(R.string.input_name));
            return;
        }
        String phone = mActivitySignUpBinding.etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.show(this, getString(R.string.input_phone));
            return;
        }
        String birthday = mActivitySignUpBinding.tvBirthday.getText().toString().trim();
        if (TextUtils.isEmpty(birthday)) {
            ToastUtils.show(this, getString(R.string.input_birthday));
            return;
        }
        String email = mActivitySignUpBinding.etEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            ToastUtils.show(this, getString(R.string.enter_email));
            return;
        }
        if (mSelectRegResponse == null) {
            registryActivity();
        } else {
            updateRegActivity();
        }


    }

    /**
     * 修改报名信息
     */
    private void updateRegActivity() {
        showLoadingDialog();
        UpdateRegRequest updateRegRequest = new UpdateRegRequest();
        updateRegRequest.setActivityId(activityId);
        updateRegRequest.setActivityType("2");
        updateRegRequest.setAge(mActivitySignUpBinding.tvBirthday.getText().toString().trim());
        updateRegRequest.setMail(mActivitySignUpBinding.etEmail.getText().toString().trim());
        updateRegRequest.setNumber(Integer.parseInt(mActivitySignUpBinding.tvPerson.getText().toString().trim()));
        updateRegRequest.setPhone(mActivitySignUpBinding.tvNumber.getText().toString().trim() + flag +
                mActivitySignUpBinding.etPhone.getText().toString().trim());
        updateRegRequest.setRemarks(mActivitySignUpBinding.etProblem.getText().toString().trim());
        updateRegRequest.setSession(getUserInfoEntity().getSession());
        updateRegRequest.setUserName(mActivitySignUpBinding.etName.getText().toString().trim());
        RetrofitUtils.getApiUrl()
                .updateRegActivity(updateRegRequest)
                .compose(RxHelper.observableToMain(SignUpActivity.this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadingDialog();
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.show(SignUpActivity.this, resultCode);
                    }
                });
    }

    /**
     * 活动报名
     */
    private void registryActivity() {
        showLoadingDialog();
        RegistryActivityRequest registryActivityRequest = new RegistryActivityRequest();
        registryActivityRequest.setActivityId(activityId);
        registryActivityRequest.setActivityType("2");
        registryActivityRequest.setAge(mActivitySignUpBinding.tvBirthday.getText().toString().trim());
        registryActivityRequest.setMail(mActivitySignUpBinding.etEmail.getText().toString().trim());
        registryActivityRequest.setNumber(Integer.parseInt(mActivitySignUpBinding.tvPerson.getText().toString().trim()));
        registryActivityRequest.setPhone(mActivitySignUpBinding.tvNumber.getText().toString().trim() + flag +
                mActivitySignUpBinding.etPhone.getText().toString().trim());
        registryActivityRequest.setRemarks(mActivitySignUpBinding.etProblem.getText().toString().trim());
        registryActivityRequest.setSession(getUserInfoEntity().getSession());
        registryActivityRequest.setUserName(mActivitySignUpBinding.etName.getText().toString().trim());
        RetrofitUtils.getApiUrl()
                .registryActivity(registryActivityRequest)
                .compose(RxHelper.observableToMain(SignUpActivity.this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadingDialog();
                        ChallengesBean challengesBean = new ChallengesBean();
                        challengesBean.setId(activityId);
                        challengesBean.setActivityStatus(2);
                        EventBus.getDefault().post(new MessageEvent(challengesBean, 0));
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(SignUpActivity.this, resultCode);
                    }
                });
    }

    /**
     * 取消报名
     */
    private void dleRegActivity() {
        showLoadingDialog();
        RegistryActivityRequest registryActivityRequest = new RegistryActivityRequest();
        registryActivityRequest.setSession(getUserInfoEntity().getSession());
        registryActivityRequest.setActivityId(activityId);
        RetrofitUtils.getApiUrl()
                .dleRegActivity(registryActivityRequest)
                .compose(RxHelper.observableToMain(SignUpActivity.this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadingDialog();
                        ChallengesBean challengesBean = new ChallengesBean();
                        challengesBean.setId(activityId);
                        challengesBean.setActivityStatus(1);
                        EventBus.getDefault().post(new MessageEvent(challengesBean, 0));
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(SignUpActivity.this, resultCode);
                    }
                });
    }
}