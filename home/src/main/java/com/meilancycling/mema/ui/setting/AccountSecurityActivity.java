package com.meilancycling.mema.ui.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.customview.dialog.NoTitleAskDialog;
import com.meilancycling.mema.databinding.ActivityAccountSecurityBinding;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.QueryWebUrlRequest;
import com.meilancycling.mema.network.bean.response.CommonProblemResponse;
import com.meilancycling.mema.ui.login.LoginActivity;
import com.meilancycling.mema.ui.login.UnbindingEmailActivity;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.SPUtils;

/**
 * 账户安全
 *
 * @author lion
 */
public class AccountSecurityActivity extends BaseActivity implements View.OnClickListener {
    private ActivityAccountSecurityBinding mActivityAccountSecurityBinding;
    private String url;
    private String name;
    private UserInfoEntity userInfoEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAccountSecurityBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_security);
        url = null;
        mActivityAccountSecurityBinding.ctvAccount.setData(getString(R.string.home_account), this);
        mActivityAccountSecurityBinding.llItem1.setOnClickListener(this);
        mActivityAccountSecurityBinding.tvAccountCancel.setOnClickListener(this);
        mActivityAccountSecurityBinding.tvExit.setOnClickListener(this);
        queryContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userInfoEntity = getUserInfoEntity();
        if (userInfoEntity.getRegType() == Config.TYPE_PHONE || userInfoEntity.getRegType() == Config.TYPE_MAILBOX) {
            mActivityAccountSecurityBinding.tvChangePassword.setVisibility(View.VISIBLE);
            mActivityAccountSecurityBinding.tvChangePassword.setText(R.string.change_password);
            mActivityAccountSecurityBinding.tvCancel.setVisibility(View.INVISIBLE);
            mActivityAccountSecurityBinding.tvMail.setVisibility(View.INVISIBLE);
        } else {
            if (TextUtils.isEmpty(userInfoEntity.getMail())) {
                mActivityAccountSecurityBinding.tvChangePassword.setVisibility(View.VISIBLE);
                mActivityAccountSecurityBinding.tvChangePassword.setText(R.string.binding_email);
                mActivityAccountSecurityBinding.tvCancel.setVisibility(View.INVISIBLE);
                mActivityAccountSecurityBinding.tvMail.setVisibility(View.INVISIBLE);
            } else {
                mActivityAccountSecurityBinding.tvChangePassword.setVisibility(View.GONE);
                mActivityAccountSecurityBinding.tvChangePassword.setText(R.string.cancel_binding);
                mActivityAccountSecurityBinding.tvMail.setText(userInfoEntity.getMail());
                mActivityAccountSecurityBinding.tvCancel.setVisibility(View.VISIBLE);
                mActivityAccountSecurityBinding.tvMail.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 查询协议
     */
    private void queryContent() {
        showLoadingDialog();
        QueryWebUrlRequest queryWebUrlRequest = new QueryWebUrlRequest();
        queryWebUrlRequest.setShowType("2");
        RetrofitUtils.getApiUrl().queryContentInfoByType(queryWebUrlRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<CommonProblemResponse>() {
                    @Override
                    public void onSuccess(CommonProblemResponse commonProblemResponse) {
                        hideLoadingDialog();
                        if (AppUtils.isChinese()) {
                            url = commonProblemResponse.getUrl();
                            name = commonProblemResponse.getName();
                        } else {
                            url = commonProblemResponse.getEnUrl();
                            name = commonProblemResponse.getEnName();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                    }
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.ll_item1:
                if (userInfoEntity.getRegType() == Config.TYPE_PHONE || userInfoEntity.getRegType() == Config.TYPE_MAILBOX) {
                    startActivity(new Intent(this, ChangePasswordActivity.class));
                } else {
                    if (TextUtils.isEmpty(userInfoEntity.getMail())) {
                        Intent intent = new Intent(this, EmailActivity.class);
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(this, UnbindingEmailActivity.class));
                    }
                }
                break;
            case R.id.tv_account_cancel:
                if (url != null) {
                    Intent intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra(WebViewActivity.title, name);
                    intent.putExtra(WebViewActivity.loadUrl, url);
                    intent.putExtra(WebViewActivity.confirmText, getString(R.string.agree_cancel));
                    startActivity(intent);
                }
                break;
            case R.id.tv_exit:
                NoTitleAskDialog noTitleAskDialog = new NoTitleAskDialog(this, getString(R.string.logout_confirm), getString(R.string.confirm), getString(R.string.cancel));
                noTitleAskDialog.show();
                noTitleAskDialog.setAskNoTitleDialogListener(new NoTitleAskDialog.AskNoTitleDialogListener() {
                    @Override
                    public void clickLeft() {
                        logout();
                    }

                    @Override
                    public void clickRight() {
                    }
                });
                break;
            default:
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        SPUtils.putLong(this, Config.CURRENT_USER, -1);
        userLogout();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}