package com.meilancycling.mema.ui.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.customview.dialog.AskDialog;
import com.meilancycling.mema.databinding.ActivityWebViewBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.DeviceInformationEntity;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.SessionRequest;
import com.meilancycling.mema.ui.login.LoginActivity;
import com.meilancycling.mema.utils.SPUtils;
import com.meilancycling.mema.utils.ToastUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * h5加载
 *
 * @author lion
 */
public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    private ActivityWebViewBinding mActivityWebViewBinding;
    /**
     * 标题
     */
    public static String title = "title";
    /**
     * 地址
     */
    public static String loadUrl = "loadType";
    /**
     * 按钮文字
     */
    public static String confirmText = "confirmText";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityWebViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onResume() {
        super.onResume();
        String extraTitle = getIntent().getStringExtra(title);
        String extraLoadUrl = getIntent().getStringExtra(loadUrl);
        String extraConfirmText = getIntent().getStringExtra(confirmText);
        mActivityWebViewBinding.ctvWeb.setData(extraTitle, this);
        //支持javascript
        mActivityWebViewBinding.webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        mActivityWebViewBinding.webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mActivityWebViewBinding.webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mActivityWebViewBinding.webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        mActivityWebViewBinding.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mActivityWebViewBinding.webView.getSettings().setLoadWithOverviewMode(true);
        mActivityWebViewBinding.webView.loadUrl(extraLoadUrl);
        mActivityWebViewBinding.cbvWeb.setBottomView(extraConfirmText, ContextCompat.getColor(this, R.color.delete_red), this);

        mActivityWebViewBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mActivityWebViewBinding.webView.loadUrl(url);
                return true;
            }
        });

        mActivityWebViewBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                int finish = 100;
                if (newProgress == finish) {
                    new Handler().postDelayed(() -> {
                        mActivityWebViewBinding.groupLoad.setVisibility(View.GONE);
                        mActivityWebViewBinding.ivLoading.clearAnimation();
                        if (!TextUtils.isEmpty(extraConfirmText)) {
                            mActivityWebViewBinding.cbvWeb.setVisibility(View.VISIBLE);
                            mActivityWebViewBinding.viewWeb.setVisibility(View.VISIBLE);
                        }
                    }, 500);
                }
            }
        });
        rotationAnimation();
    }

    /**
     * 旋转动画
     */
    private void rotationAnimation() {
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.loading_anim);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        mActivityWebViewBinding.ivLoading.startAnimation(rotateAnimation);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tv_title:
                AskDialog askDialog = new AskDialog(this, getString(R.string.prompt), getString(R.string.sure_log_off));
                askDialog.show();
                askDialog.setAskDialogListener(new AskDialog.AskDialogListener() {
                    @Override
                    public void clickCancel() {
                        askDialog.dismiss();
                    }

                    @Override
                    public void clickConfirm() {
                        cancellation();
                    }
                });
                break;
            default:
        }
    }

    /**
     * 注销账号
     */
    public void cancellation() {
        showLoadingDialog();
        SessionRequest sessionRequest = new SessionRequest();
        sessionRequest.setSession(getUserInfoEntity().getSession());
        RetrofitUtils.getApiUrl().cancellation(sessionRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        Observable.just(getUserId())
                                .map(aLong -> {
                                    //删除设备信息
                                    List<DeviceInformationEntity> deviceInfoList = DbUtils.getInstance().deviceInfoList(getUserId());
                                    if (deviceInfoList != null && deviceInfoList.size() > 0) {
                                        for (DeviceInformationEntity deviceInformationEntity : deviceInfoList) {
                                            DbUtils.getInstance().deleteDevice(deviceInformationEntity);
                                        }
                                    }
                                    UserInfoEntity userInfoEntity = getUserInfoEntity();
                                    DbUtils.getInstance().deleteUserInfoEntity(userInfoEntity);
                                    SPUtils.putLong(WebViewActivity.this, Config.CURRENT_USER, -1);
                                    setUserId(null);
                                    updateUserInfoEntity(null);
                                    return aLong;
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Long>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(Long aLong) {

                                        sendBroadcast(new Intent(BroadcastConstant.ACTION_USER_LOGOUT));
                                        startActivity(new Intent(WebViewActivity.this, LoginActivity.class));
                                        finish();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        String message = e.getMessage();
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        ToastUtils.show(WebViewActivity.this, getString(R.string.CODE_9999));
                    }
                });
    }
}