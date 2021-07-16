package com.meilancycling.mema.ui.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivityModifyNickBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.service.DeviceControllerService;


/**
 * 修改昵称
 *
 * @author lion
 */
public class ModifyNickActivity extends BaseActivity implements View.OnClickListener {
    private ActivityModifyNickBinding mActivityModifyNickBinding;
    private UserInfoEntity mUserInfoEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityModifyNickBinding = DataBindingUtil.setContentView(this, R.layout.activity_modify_nick);
        mUserInfoEntity = getUserInfoEntity();
        mActivityModifyNickBinding.etNickname.setText(mUserInfoEntity.getNickname());
        mActivityModifyNickBinding.ivFinish.setOnClickListener(this);
        mActivityModifyNickBinding.tvSave.setOnClickListener(this);

        mActivityModifyNickBinding.tvSave.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_f2_4));
        mActivityModifyNickBinding.tvSave.setTextColor(Color.parseColor("#FFB5B5B5"));

        mActivityModifyNickBinding.etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String content = charSequence.toString().trim();
                if (TextUtils.isEmpty(content)) {
                    mActivityModifyNickBinding.tvSave.setBackground(ContextCompat.getDrawable(ModifyNickActivity.this, R.drawable.shape_f2_4));
                    mActivityModifyNickBinding.tvSave.setTextColor(Color.parseColor("#FFB5B5B5"));
                } else {
                    int maxLength = 50;
                    if (content.length() > maxLength) {
                        mActivityModifyNickBinding.etNickname.setText(content.substring(0, maxLength - 1));
                    } else {
                        if (content.equals(mUserInfoEntity.getNickname())) {
                            mActivityModifyNickBinding.tvSave.setBackground(ContextCompat.getDrawable(ModifyNickActivity.this, R.drawable.shape_f2_4));
                            mActivityModifyNickBinding.tvSave.setTextColor(Color.parseColor("#FFB5B5B5"));
                        } else {
                            mActivityModifyNickBinding.tvSave.setBackground(ContextCompat.getDrawable(ModifyNickActivity.this, R.drawable.shape_main_4));
                            mActivityModifyNickBinding.tvSave.setTextColor(ContextCompat.getColor(ModifyNickActivity.this, R.color.white));
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:
                finish();
                break;
            case R.id.tv_save:
                String trim = mActivityModifyNickBinding.etNickname.getText().toString().trim();
                String nickname = mUserInfoEntity.getNickname();
                if (!TextUtils.isEmpty(trim) && !nickname.equals(trim)) {
                    Intent intent = new Intent();
                    intent.putExtra("result", trim);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            default:
        }
    }
}