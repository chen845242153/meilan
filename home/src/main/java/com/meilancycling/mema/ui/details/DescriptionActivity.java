package com.meilancycling.mema.ui.details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.network.bean.request.QueryMotionInfoRequest;
import com.meilancycling.mema.network.bean.request.UpdateMotionRequest;
import com.meilancycling.mema.network.bean.response.SportsRemarkResponse;
import com.meilancycling.mema.databinding.ActivityDescriptionBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.utils.StatusAppUtils;
import com.meilancycling.mema.utils.ToastUtils;


/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/11/19 5:05 PM
 */
public class DescriptionActivity extends BaseActivity implements View.OnClickListener {

    ActivityDescriptionBinding mActivityDescriptionBinding;
    private int mMotionId;
    private String title = "";
    private String content = "";
    private boolean isChange;
    private int textLength;

    public static void enterDescription(Context context, int motionId) {
        Intent intent = new Intent(context, DescriptionActivity.class);
        intent.putExtra("motionId", motionId);
        context.startActivity(intent);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDescriptionBinding = DataBindingUtil.setContentView(this, R.layout.activity_description);
        StatusAppUtils.setColor(this, getResources().getColor(R.color.white));
        mActivityDescriptionBinding.ctvDescription.setData(getString(R.string.edit), this);
        mMotionId = getIntent().getIntExtra("motionId", 0);
        isChange = false;
        textLength = 0;
        mActivityDescriptionBinding.tvDescriptionConfirm.setOnClickListener(this);
        updateConfirm();
        showLoadingDialog();
        QueryMotionInfoRequest queryMotionInfoRequest = new QueryMotionInfoRequest();
        queryMotionInfoRequest.setMotionId(mMotionId);
        queryMotionInfoRequest.setSession(getUserInfoEntity().getSession());
        RetrofitUtils.getApiUrl().selectMotionRemarks(queryMotionInfoRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<SportsRemarkResponse>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(SportsRemarkResponse result) {
                        title = result.getMotionName();
                        content = result.getMotionRecord();
                        mActivityDescriptionBinding.tvDescriptionTitle.setText(title);
                        mActivityDescriptionBinding.tvDescriptionContent.setText(content);
                        if (TextUtils.isEmpty(content)) {
                            content = "";
                        }
                        if (TextUtils.isEmpty(title)) {
                            title = "";
                        }
                        textLength = content.length();
                        mActivityDescriptionBinding.tvDescriptionNumber.setText(textLength + getString(R.string.edit_length));
                        hideLoadingDialog();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                    }
                });

        mActivityDescriptionBinding.tvDescriptionTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                isChange = !charSequence.toString().trim().equals(title) || !mActivityDescriptionBinding.tvDescriptionContent.getText().toString().trim().equals(content);
                updateConfirm();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mActivityDescriptionBinding.tvDescriptionContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                isChange = !charSequence.toString().trim().equals(content) || !mActivityDescriptionBinding.tvDescriptionTitle.getText().toString().trim().equals(title);
                updateConfirm();
                textLength = charSequence.length();
                mActivityDescriptionBinding.tvDescriptionNumber.setText(textLength + getString(R.string.edit_length));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_description_confirm) {
            if (isChange) {
                changeDescription();
            }

        } else if (id == R.id.ll_common_back) {
            finish();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void updateConfirm() {
        if (isChange) {
            mActivityDescriptionBinding.tvDescriptionConfirm.setTextColor(getResources().getColor(R.color.white));
            mActivityDescriptionBinding.tvDescriptionConfirm.setBackground(getResources().getDrawable(R.drawable.shape_shadow));
        } else {
            mActivityDescriptionBinding.tvDescriptionConfirm.setTextColor(getResources().getColor(R.color.main_color));
            mActivityDescriptionBinding.tvDescriptionConfirm.setBackground(getResources().getDrawable(R.drawable.shape_line_main));
        }
    }

    /**
     * 修改描述信息
     */
    private void changeDescription() {
        showLoadingDialog();
        UpdateMotionRequest updateMotionRequest = new UpdateMotionRequest();
        int motionId = getIntent().getIntExtra("motionId", 0);
        updateMotionRequest.setId(motionId);
        updateMotionRequest.setSession(getUserInfoEntity().getSession());
        updateMotionRequest.setMotionRecord(mActivityDescriptionBinding.tvDescriptionContent.getText().toString().trim());
        updateMotionRequest.setMotionName(mActivityDescriptionBinding.tvDescriptionTitle.getText().toString().trim());
        RetrofitUtils.getApiUrl().updateMotion(updateMotionRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        Intent intent = new Intent(BroadcastConstant.ACTION_DELETE_RECORD);
                        sendBroadcast(intent);
                        title = mActivityDescriptionBinding.tvDescriptionTitle.getText().toString().trim();
                        content = mActivityDescriptionBinding.tvDescriptionContent.getText().toString().trim();
                        if (TextUtils.isEmpty(title)) {
                            title = "";
                        }
                        if (TextUtils.isEmpty(content)) {
                            content = "";
                        }
                        isChange = false;
                        updateConfirm();
                        hideLoadingDialog();
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(DescriptionActivity.this, resultCode);
                    }
                });
        showLoadingDialog();
    }
}
