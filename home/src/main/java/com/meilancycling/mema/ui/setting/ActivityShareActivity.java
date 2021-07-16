package com.meilancycling.mema.ui.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.customview.dialog.AskDialog;
import com.meilancycling.mema.databinding.ActivityShareBinding;
import com.meilancycling.mema.db.AuthorEntity;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.AuthorRequest;
import com.meilancycling.mema.utils.ToastUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 第三方分享
 *
 * @author lion
 */
public class ActivityShareActivity extends BaseActivity implements View.OnClickListener {
    private ActivityShareBinding mActivityShareBinding;
    private AuthorEntity stravaBean;
    private AuthorEntity komBean;
    private AuthorEntity tpBean;
    /**
     * 0 strava
     * 1 komoot
     * 2 trainingpeaks
     */
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = -1;
        mActivityShareBinding = DataBindingUtil.setContentView(this, R.layout.activity_share);
        mActivityShareBinding.ctvActivityShare.setData(getString(R.string.activity_sharing), this);
        stravaBean = DbUtils.getInstance().queryAuthorEntity(getUserId(), 1);
        komBean = DbUtils.getInstance().queryAuthorEntity(getUserId(), 2);
        tpBean = DbUtils.getInstance().queryAuthorEntity(getUserId(), 4);
        updateStravaUi();
        updateKomUi();
        updateTpUi();
        mActivityShareBinding.llStrava.setOnClickListener(this);
        mActivityShareBinding.llKomoot.setOnClickListener(this);
        mActivityShareBinding.llTp.setOnClickListener(this);
        type = 0;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (null != intent && intent.getData() != null) {
            String code = intent.getData().getQueryParameter("code");
            switch (type) {
                case 0:
                    String scope = intent.getData().getQueryParameter("scope");
                    strAuth(code, scope);
                    break;
                case 1:
                    komAuth(code);
                    break;
                case 2:
                    trainingPeaksAuth(code);
                    break;
                default:
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.ll_strava:
                if (stravaBean == null) {
                    Uri intentUri = Uri.parse("https://www.strava.com/oauth/mobile/authorize")
                            .buildUpon()
                            .appendQueryParameter("client_id", "54706")
                            .appendQueryParameter("redirect_uri", "meilan://www.meilancycling.com")
                            .appendQueryParameter("response_type", "code")
                            .appendQueryParameter("approval_prompt", "auto")
                            .appendQueryParameter("scope", "activity:write")
                            .build();
                    type = 0;
                    Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
                    startActivity(intent);
                } else {
                    AskDialog askDialog = new AskDialog(this, getString(R.string.deauthorize), getString(R.string.deauthorize_strava));
                    askDialog.show();
                    askDialog.setAskDialogListener(new AskDialog.AskDialogListener() {
                        @Override
                        public void clickCancel() {
                        }

                        @Override
                        public void clickConfirm() {
                            showLoadingDialog();
                            cancelStr();
                        }
                    });
                }
                break;
            case R.id.ll_komoot:
                if (komBean == null) {
                    Uri intentUri = Uri.parse("https://auth.komoot.de/oauth/authorize")
                            .buildUpon()
                            .appendQueryParameter("client_id", "meilan-k9v8js")
                            .appendQueryParameter("redirect_uri", "meilan://www.meilancycling.com")
                            .appendQueryParameter("response_type", "code")
                            .appendQueryParameter("scope", "profile")
                            .build();
                    type = 1;
                    Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
                    startActivity(intent);
                } else {
                    AskDialog askDialog = new AskDialog(this, getString(R.string.deauthorize), getString(R.string.deauthorize_komoot));
                    askDialog.show();
                    askDialog.setAskDialogListener(new AskDialog.AskDialogListener() {
                        @Override
                        public void clickCancel() {
                        }

                        @Override
                        public void clickConfirm() {
                            showLoadingDialog();
                            cancelKom();
                        }
                    });
                }
                break;
            case R.id.ll_tp:
                if (tpBean == null) {
                    Uri intentUri = Uri.parse("https://oauth.trainingpeaks.com/OAuth/Authorize")
                            .buildUpon()
                            .appendQueryParameter("response_type", "code")
                            .appendQueryParameter("client_id", "shenzhen-meilan-technology-colimited")
                            .appendQueryParameter("scope", "file:write")
                            .appendQueryParameter("redirect_uri", "meilan://www.meilancycling.com")
                            .build();
                    type = 2;
                    Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
                    startActivity(intent);
                } else {
                    AskDialog askDialog = new AskDialog(this, getString(R.string.deauthorize), getString(R.string.deauthorize_training));
                    askDialog.show();
                    askDialog.setAskDialogListener(new AskDialog.AskDialogListener() {
                        @Override
                        public void clickCancel() {
                        }

                        @Override
                        public void clickConfirm() {
                            showLoadingDialog();
                            cancelTrainingPeaks();
                        }
                    });
                }
                break;
            default:
        }
    }

    /**
     * 取消strava
     */
    private void cancelStr() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("access_token", stravaBean.getToken())
                .build();
        Request request = new Request.Builder().url("https://www.strava.com/api/v3/oauth/deauthorize")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hideLoadingDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                AuthorRequest authorRequest = new AuthorRequest();
                authorRequest.setPlatformType(1);
                authorRequest.setPullToken(stravaBean.getPullToken());
                authorRequest.setSession(getUserInfoEntity().getSession());
                authorRequest.setTimeOut(stravaBean.getTimeOut());
                authorRequest.setToken(stravaBean.getToken());
                RetrofitUtils.getApiUrl().cancelAuthorize(authorRequest)
                        .compose(RxHelper.observableToMain(ActivityShareActivity.this))
                        .subscribe(new MyObserver<Object>() {
                            @Override
                            public void onSuccess(Object result) {
                                DbUtils.getInstance().deleteAuthorEntity(stravaBean);
                                stravaBean = null;
                                updateStravaUi();
                                hideLoadingDialog();
                            }

                            @Override
                            public void onFailure(Throwable e, String resultCode) {
                                hideLoadingDialog();
                            }
                        });
            }
        });
    }

    private void updateStravaUi() {
        if (stravaBean == null) {
            mActivityShareBinding.llStrava.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            mActivityShareBinding.ivStravaArrow.setVisibility(View.VISIBLE);
            mActivityShareBinding.ivStravaSelect.setVisibility(View.GONE);
        } else {
            mActivityShareBinding.ivStravaArrow.setVisibility(View.GONE);
            mActivityShareBinding.ivStravaSelect.setVisibility(View.VISIBLE);
            mActivityShareBinding.llStrava.setBackgroundColor(Color.parseColor("#FFE8E8E8"));
        }
    }

    private void updateKomUi() {
        if (komBean == null) {
            mActivityShareBinding.llKomoot.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            mActivityShareBinding.ivKomootArrow.setVisibility(View.VISIBLE);
            mActivityShareBinding.ivKomootSelect.setVisibility(View.GONE);
        } else {
            mActivityShareBinding.ivKomootArrow.setVisibility(View.GONE);
            mActivityShareBinding.ivKomootSelect.setVisibility(View.VISIBLE);
            mActivityShareBinding.llKomoot.setBackgroundColor(Color.parseColor("#FFE8E8E8"));
        }
    }

    private void updateTpUi() {
        if (tpBean == null) {
            mActivityShareBinding.llTp.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            mActivityShareBinding.ivTpArrow.setVisibility(View.VISIBLE);
            mActivityShareBinding.ivTpSelect.setVisibility(View.GONE);
        } else {
            mActivityShareBinding.ivTpArrow.setVisibility(View.GONE);
            mActivityShareBinding.ivTpSelect.setVisibility(View.VISIBLE);
            mActivityShareBinding.llTp.setBackgroundColor(Color.parseColor("#FFE8E8E8"));
        }
    }

    /**
     * strava 授权
     */
    private void strAuth(String code, String scope) {
        if (!TextUtils.isEmpty(scope)) {
            if (scope.contains("write")) {
                showLoadingDialog();
                sGetToken(code);
            } else {
                ToastUtils.show(this, getResources().getString(R.string.authorization_failed));
            }
        } else {
            ToastUtils.show(this, getResources().getString(R.string.authorization_failed));
        }
    }


    private void sGetToken(String code) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("client_id", "54706")
                .add("client_secret", "02361fefe4813ddc67f3288c555f80eae85bb448")
                .add("code", code)
                .add("grant_type", "authorization_code")
                .build();

        Request request = new Request.Builder().url("https://www.strava.com/api/v3/oauth/token")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hideLoadingDialog();
                ToastUtils.show(ActivityShareActivity.this, getResources().getString(R.string.authorization_failed));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    StravaBean strava = new Gson().fromJson(response.body().string(), StravaBean.class);
                    AuthorRequest authorRequest = new AuthorRequest();
                    authorRequest.setPlatformType(1);
                    authorRequest.setPullToken(strava.getRefresh_token());
                    authorRequest.setSession(getUserInfoEntity().getSession());
                    authorRequest.setTimeOut(String.valueOf(strava.getExpires_at()));
                    authorRequest.setToken(strava.getToken_type() + " " + strava.getAccess_token());

                    RetrofitUtils.getApiUrl().addAuthorize(authorRequest)
                            .compose(RxHelper.observableToMain(ActivityShareActivity.this))
                            .subscribe(new MyObserver<Object>() {
                                @Override
                                public void onSuccess(Object result) {
                                    hideLoadingDialog();
                                    AuthorEntity authorEntity = new AuthorEntity();
                                    authorEntity.setPlatformType(1);
                                    authorEntity.setPullToken(authorRequest.getPullToken());
                                    authorEntity.setTimeOut(authorRequest.getTimeOut());
                                    authorEntity.setToken(authorRequest.getToken());
                                    authorEntity.setUserId(getUserId());
                                    stravaBean = authorEntity;
                                    DbUtils.getInstance().addAuthorEntity(authorEntity);
                                    updateStravaUi();
                                }

                                @Override
                                public void onFailure(Throwable e, String resultCode) {
                                    hideLoadingDialog();
                                    ToastUtils.show(ActivityShareActivity.this, getResources().getString(R.string.authorization_failed));
                                }
                            });
                } else {
                    hideLoadingDialog();
                    ToastUtils.show(ActivityShareActivity.this, getResources().getString(R.string.authorization_failed));
                }
            }
        });
    }

    /**
     * komoot 授权
     */
    private void komAuth(String code) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String credential = Credentials.basic("meilan-k9v8js", "rahiezah8ha0thae4li3aepei");
        RequestBody requestBody = new FormBody.Builder()
                .add("redirect_uri", "meilan://www.meilancycling.com")
                .add("code", code)
                .add("grant_type", "authorization_code")
                .build();
        Request request = new Request.Builder().url("https://auth.komoot.de/oauth/token")
                .header("Authorization", credential)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hideLoadingDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    KomootBean komootBean = new Gson().fromJson(response.body().string(), KomootBean.class);
                    AuthorRequest authorRequest = new AuthorRequest();
                    authorRequest.setPlatformType(2);
                    authorRequest.setPullToken(komootBean.getRefresh_token());
                    authorRequest.setSession(getUserInfoEntity().getSession());
                    authorRequest.setTimeOut(String.valueOf(System.currentTimeMillis() / 1000 + komootBean.getExpires_in()));
                    authorRequest.setToken(komootBean.getToken_type() + " " + komootBean.getAccess_token());

                    RetrofitUtils.getApiUrl().addAuthorize(authorRequest)
                            .compose(RxHelper.observableToMain(ActivityShareActivity.this))
                            .subscribe(new MyObserver<Object>() {
                                @Override
                                public void onSuccess(Object result) {
                                    hideLoadingDialog();
                                    AuthorEntity authorEntity = new AuthorEntity();
                                    authorEntity.setPlatformType(2);
                                    authorEntity.setPullToken(authorRequest.getPullToken());
                                    authorEntity.setTimeOut(authorRequest.getTimeOut());
                                    authorEntity.setToken(authorRequest.getToken());
                                    authorEntity.setUserId(getUserId());
                                    komBean = authorEntity;
                                    DbUtils.getInstance().addAuthorEntity(authorEntity);
                                    updateKomUi();
                                }

                                @Override
                                public void onFailure(Throwable e, String resultCode) {
                                    hideLoadingDialog();
                                    ToastUtils.show(ActivityShareActivity.this, getResources().getString(R.string.authorization_failed));
                                }
                            });
                } else {
                    hideLoadingDialog();
                    ToastUtils.show(ActivityShareActivity.this, getResources().getString(R.string.authorization_failed));
                }
            }
        });
    }

    /**
     * 取消komoot
     */
    private void cancelKom() {
        OkHttpClient okHttpClient = new OkHttpClient();
        String credential = Credentials.basic("meilan-k9v8js", "rahiezah8ha0thae4li3aepei");
        String url = "https://auth.komoot.de/v1/clients/meilan-k9v8js/refresh_tokens/?refresh_token=" + komBean.getPullToken();
        Request request = new Request.Builder().url(url)
                .header("Authorization", credential)
                .delete()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hideLoadingDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                AuthorRequest authorRequest = new AuthorRequest();
                authorRequest.setPlatformType(2);
                authorRequest.setPullToken(komBean.getPullToken());
                authorRequest.setSession(getUserInfoEntity().getSession());
                authorRequest.setTimeOut(komBean.getTimeOut());
                authorRequest.setToken(komBean.getToken());
                RetrofitUtils.getApiUrl().cancelAuthorize(authorRequest)
                        .compose(RxHelper.observableToMain(ActivityShareActivity.this))
                        .subscribe(new MyObserver<Object>() {
                            @Override
                            public void onSuccess(Object result) {
                                DbUtils.getInstance().deleteAuthorEntity(komBean);
                                komBean = null;
                                updateKomUi();
                                hideLoadingDialog();
                            }

                            @Override
                            public void onFailure(Throwable e, String resultCode) {
                                hideLoadingDialog();
                            }
                        });
            }
        });
    }

    /**
     * trainingPeaks 授权
     */
    private void trainingPeaksAuth(String code) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String paramsStr =
                "client_id=shenzhen-meilan-technology-colimited" +
                        "&grant_type=authorization_code" +
                        "&code=" + code +
                        "&redirect_uri=meilan://www.meilancycling.com" +
                        "&client_secret=NJKjZkusPWEau6nUzJL5KyYvmDGpKG1SPD88DU";
        MediaType form = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        RequestBody requestBody = RequestBody.create(form, paramsStr);
        Request request = new Request.Builder().url("https://oauth.trainingpeaks.com/oauth/token")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hideLoadingDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    TpBean tp = new Gson().fromJson(response.body().string(), TpBean.class);
                    AuthorRequest authorRequest = new AuthorRequest();
                    authorRequest.setPlatformType(4);
                    authorRequest.setPullToken(tp.getRefresh_token());
                    authorRequest.setSession(getUserInfoEntity().getSession());
                    authorRequest.setTimeOut(String.valueOf(System.currentTimeMillis() / 1000 + tp.getExpires_in()));
                    authorRequest.setToken(tp.getToken_type() + " " + tp.getAccess_token());

                    RetrofitUtils.getApiUrl().addAuthorize(authorRequest)
                            .compose(RxHelper.observableToMain(ActivityShareActivity.this))
                            .subscribe(new MyObserver<Object>() {
                                @Override
                                public void onSuccess(Object result) {
                                    hideLoadingDialog();
                                    AuthorEntity authorEntity = new AuthorEntity();
                                    authorEntity.setPlatformType(authorRequest.getPlatformType());
                                    authorEntity.setPullToken(authorRequest.getPullToken());
                                    authorEntity.setTimeOut(authorRequest.getTimeOut());
                                    authorEntity.setToken(authorRequest.getToken());
                                    authorEntity.setUserId(getUserId());
                                    tpBean = authorEntity;
                                    DbUtils.getInstance().addAuthorEntity(authorEntity);
                                    updateTpUi();
                                }

                                @Override
                                public void onFailure(Throwable e, String resultCode) {
                                    hideLoadingDialog();
                                    ToastUtils.show(ActivityShareActivity.this, getResources().getString(R.string.authorization_failed));
                                }
                            });
                } else {
                    hideLoadingDialog();
                    ToastUtils.show(ActivityShareActivity.this, getResources().getString(R.string.authorization_failed));
                }
            }
        });
    }

    /**
     * 取消trainingPeaks
     */
    private void cancelTrainingPeaks() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().build();
        Request request = new Request.Builder()
                .addHeader("Authorization", tpBean.getToken())
                .url("https://oauth.trainingpeaks.com/oauth/deauthorize")
                .post(formBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hideLoadingDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                AuthorRequest authorRequest = new AuthorRequest();
                authorRequest.setPlatformType(4);
                authorRequest.setPullToken(tpBean.getPullToken());
                authorRequest.setSession(getUserInfoEntity().getSession());
                authorRequest.setTimeOut(tpBean.getTimeOut());
                authorRequest.setToken(tpBean.getToken());
                RetrofitUtils.getApiUrl().cancelAuthorize(authorRequest)
                        .compose(RxHelper.observableToMain(ActivityShareActivity.this))
                        .subscribe(new MyObserver<Object>() {
                            @Override
                            public void onSuccess(Object result) {
                                DbUtils.getInstance().deleteAuthorEntity(tpBean);
                                tpBean = null;
                                updateTpUi();
                                hideLoadingDialog();
                            }

                            @Override
                            public void onFailure(Throwable e, String resultCode) {
                                hideLoadingDialog();
                            }
                        });
            }
        });
    }
}