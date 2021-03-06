package com.meilancycling.mema.ui.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.db.AuthorEntity;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.db.greendao.UserInfoEntityDao;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.CheckPhoneOrMailRequest;
import com.meilancycling.mema.network.bean.request.Encryption;
import com.meilancycling.mema.network.bean.request.GetCodeRequest;
import com.meilancycling.mema.network.bean.request.MobileLogin;
import com.meilancycling.mema.network.bean.request.PhoneRegisterRequest;
import com.meilancycling.mema.network.bean.request.QueryWebUrlRequest;
import com.meilancycling.mema.network.bean.request.SessionRequest;
import com.meilancycling.mema.network.bean.request.ThirPartyLoginRequest;
import com.meilancycling.mema.network.bean.response.AuthorListResponse;
import com.meilancycling.mema.network.bean.response.CommonProblemResponse;
import com.meilancycling.mema.network.bean.response.GetKeyBean;
import com.meilancycling.mema.network.bean.response.LoginBean;
import com.meilancycling.mema.network.bean.response.ResetPasswordRequest;
import com.meilancycling.mema.ui.home.HomeActivity;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.SPUtils;
import com.meilancycling.mema.utils.StatusAppUtils;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.wxapi.WXEntryActivity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ????????????
 *
 * @author lion
 */
public class LoginActivity extends BaseActivity {
    public CommonProblemResponse value1;
    public CommonProblemResponse value2;
    /**
     * ??????????????????
     */
    private String showType1 = "1";
    /**
     * ??????????????????
     */
    private String showType2 = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusAppUtils.setTranslucentForImageView(this, null);
        showFragment(new LoginHomeFragment());
        queryContent(showType1);
        queryContent(showType2);
        registerReceiver(mReceiver, getIntents());
        initListener();
    }

    private CallbackManager mCallbackManager;

    private void initListener() {
        /*
         *facebook ??????
         */
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                try {
                    AccessToken accessToken = loginResult.getAccessToken();
                    if (accessToken != null) {
                        getUserInfoByFacebook(accessToken);
                    } else {
                        hideLoadingDialog();
                        ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
                    }
                } catch (Exception e) {
                    hideLoadingDialog();
                    ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
                }
            }

            @Override
            public void onCancel() {
                hideLoadingDialog();
                ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
            }

            @Override
            public void onError(FacebookException error) {
                hideLoadingDialog();
                ToastUtils.show(LoginActivity.this, error.getMessage());
            }
        });
    }

    private IntentFilter getIntents() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WXEntryActivity.WX_LOGIN_SUCCESS);
        intentFilter.addAction(WXEntryActivity.WX_LOGIN_FAIL);
        return intentFilter;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                switch (intent.getAction()) {
                    case WXEntryActivity.WX_LOGIN_SUCCESS:
                        String code = intent.getStringExtra("code");
                        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?" + "appid=%s&secret=%s&code=%s&grant_type=authorization_code", MyApplication.APP_ID, MyApplication.WX_AppSecret, code);
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Request request = new Request.Builder().url(url)
                                .get()
                                .build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                hideLoadingDialog();
                                ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
                            }

                            @Override
                            public void onResponse(Call call, Response response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    String openId, accessToken;
                                    openId = jsonObject.getString("openid");
                                    accessToken = jsonObject.getString("access_token");
                                    getUserInfo(accessToken, openId);
                                } catch (JSONException | IOException e) {
                                    hideLoadingDialog();
                                    ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
                                }

                            }
                        });
                        break;
                    case WXEntryActivity.WX_LOGIN_FAIL:
                        hideLoadingDialog();
                        ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
                        break;
                    default:
                }
            }
        }
    };

    /**
     * ????????????????????????
     */
    private void getUserInfo(String accessToken, final String openId) {
        // ???????????????????????????UnionID?????????
        if (!TextUtils.isEmpty(accessToken) && !TextUtils.isEmpty(openId)) {
            // ????????????
            String url = String.format("https://api.weixin.qq.com/sns/userinfo?" + "access_token=%s&openid=%s", accessToken, openId);
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(url)
                    .get()
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    hideLoadingDialog();
                    ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
                }

                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        final String nickname, headimgurl, unionId;
                        nickname = jsonObject.getString("nickname");
                        headimgurl = jsonObject.getString("headimgurl");
                        unionId = jsonObject.getString("unionid");
                        threePartyLogin(headimgurl, nickname, openId, Config.TYPE_WX, unionId);
                    } catch (JSONException | IOException e) {
                        hideLoadingDialog();
                        ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
                    }
                }
            });
        } else {
            hideLoadingDialog();
            ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
        }
    }

    /**
     * ??????fragment
     */
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_login, fragment, fragment.getClass().getSimpleName()).commit();
    }

    /**
     * ????????????
     */
    private void queryContent(String showType) {
        QueryWebUrlRequest queryWebUrlRequest = new QueryWebUrlRequest();
        queryWebUrlRequest.setShowType(showType);
        RetrofitUtils.getApiUrl().queryContentInfoByType(queryWebUrlRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<CommonProblemResponse>() {
                    @Override
                    public void onSuccess(CommonProblemResponse commonProblemResponse) {
                        hideLoadingDialog();
                        if (showType1.equals(showType)) {
                            value1 = commonProblemResponse;
                        }
                        if (showType2.equals(showType)) {
                            value2 = commonProblemResponse;
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                    }
                });
    }


    /**
     * ????????????
     * ???????????????11??????????????????
     * ????????????????????????????????????+???8????????????
     * ?????????????????????????????????
     * 13+?????????
     * 15+???4????????????
     * 18+???1???4????????????
     * 17+???9????????????
     * 147
     */
    public boolean isNumLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public boolean isEmail(String strEmail) {
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return strEmail.matches(regEx1);
    }

    /**
     * ??????RSA??????+????????????KEY
     */
    public void getKey(BaseFragment fragment) {
        RetrofitUtils.getApiUrl().getKey("password/security/getkey")
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<GetKeyBean>() {
                    @Override
                    public void onSuccess(GetKeyBean getKeyBean) {
                        String publicKey = getKeyBean.getPublicKey();
                        String cacheKey = getKeyBean.getCacheKey();
                        if (fragment instanceof LoginFragment) {
                            ((LoginFragment) fragment).keyCallback(publicKey, cacheKey);
                        } else if (fragment instanceof ForgetPasswordFragment) {
                            ((ForgetPasswordFragment) fragment).keyCallback(publicKey, cacheKey);
                        } else if (fragment instanceof RegisteredFragment) {
                            ((RegisteredFragment) fragment).keyCallback(publicKey, cacheKey);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        if (fragment instanceof LoginFragment) {
                            ((LoginFragment) fragment).keyCallback(null, resultCode);
                        } else if (fragment instanceof ForgetPasswordFragment) {
                            ((ForgetPasswordFragment) fragment).keyCallback(null, resultCode);
                        } else if (fragment instanceof RegisteredFragment) {
                            ((RegisteredFragment) fragment).keyCallback(null, resultCode);
                        }
                    }
                });
    }

    /**
     * ????????????
     */
    public void encryption(BaseFragment fragment, String password, String publicKey) {
        Encryption encryption = new Encryption();
        encryption.setPassword(stringMd5(password));
        encryption.setPublicKey(publicKey);
        RetrofitUtils.getApiUrl().encryption(encryption)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<String>() {
                    @Override
                    public void onSuccess(String string) {
                        if (fragment instanceof LoginFragment) {
                            ((LoginFragment) fragment).encryptionCallback(string, null);
                        } else if (fragment instanceof ForgetPasswordFragment) {
                            ((ForgetPasswordFragment) fragment).encryptionCallback(string, null);
                        } else if (fragment instanceof RegisteredFragment) {
                            ((RegisteredFragment) fragment).encryptionCallback(string, null);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        if (fragment instanceof LoginFragment) {
                            ((LoginFragment) fragment).encryptionCallback(null, resultCode);
                        } else if (fragment instanceof ForgetPasswordFragment) {
                            ((ForgetPasswordFragment) fragment).encryptionCallback(null, resultCode);
                        } else if (fragment instanceof RegisteredFragment) {
                            ((RegisteredFragment) fragment).encryptionCallback(null, resultCode);
                        }
                    }
                });
    }

    /**
     * ???????????????
     */
    public void threePartyLogin(String headerUrl, String nickName, String openId, int regType, String unionId) {
        ThirPartyLoginRequest thirPartyLoginRequest = new ThirPartyLoginRequest();
        thirPartyLoginRequest.setHeaderUrl(headerUrl);
        thirPartyLoginRequest.setNickName(nickName);
        thirPartyLoginRequest.setOpenId(openId);
        thirPartyLoginRequest.setRegType(String.valueOf(regType));
        thirPartyLoginRequest.setUnionId(unionId);
        //android
        thirPartyLoginRequest.setPhoneType(1);
        RetrofitUtils.getApiUrl().threePartyLogin(thirPartyLoginRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean result) {
                        loginSuccess(result, regType);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        ToastUtils.showError(LoginActivity.this, resultCode);
                    }
                });
    }

    /**
     * ??????
     */
    public void mobileLogin(BaseFragment fragment, MobileLogin mobileLogin) {
        RetrofitUtils.getApiUrl().mobileLogin(mobileLogin)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean result) {
                        if (isEmail(mobileLogin.getLoginName())) {
                            loginSuccess(result, Config.TYPE_MAILBOX);
                        } else if (isNumLegal(mobileLogin.getLoginName())) {
                            loginSuccess(result, Config.TYPE_PHONE);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        if (fragment instanceof LoginFragment) {
                            ((LoginFragment) fragment).loginFail(resultCode);
                        }
                    }
                });
    }

    /**
     * ??????????????????MD5??????
     */
    private String stringMd5(String plainText) {
        if (TextUtils.isEmpty(plainText)) {
            return "";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte value : b) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i & 0xff));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * ???????????????????????????????????????
     */
    public void checkPhoneOrMail(BaseFragment fragment, String username) {
        CheckPhoneOrMailRequest checkPhoneOrMailRequest = new CheckPhoneOrMailRequest();
        checkPhoneOrMailRequest.setAccountNumber(username);
        RetrofitUtils.getApiUrl().checkPhoneOrMail(checkPhoneOrMailRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (fragment instanceof ForgetPasswordFragment) {
                            ((ForgetPasswordFragment) fragment).phoneOrMailCallback(result, null);
                        } else if (fragment instanceof RegisteredFragment) {
                            ((RegisteredFragment) fragment).phoneOrMailCallback(result, null);
                        } else if (fragment instanceof LoginFragment) {
                            ((LoginFragment) fragment).phoneOrMailCallback(result, null);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        if (fragment instanceof ForgetPasswordFragment) {
                            ((ForgetPasswordFragment) fragment).phoneOrMailCallback(null, resultCode);
                        } else if (fragment instanceof RegisteredFragment) {
                            ((RegisteredFragment) fragment).phoneOrMailCallback(null, resultCode);
                        } else if (fragment instanceof LoginFragment) {
                            ((LoginFragment) fragment).phoneOrMailCallback(null, resultCode);
                        }
                    }
                });
    }

    /**
     * ????????????????????????
     */
    public void getCode(BaseFragment fragment, GetCodeRequest getCodeRequest) {
        RetrofitUtils.getApiUrl().getVerificationCode(getCodeRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        if (fragment instanceof ForgetPasswordFragment) {
                            ((ForgetPasswordFragment) fragment).codeCallback(null);
                        } else if (fragment instanceof RegisteredFragment) {
                            ((RegisteredFragment) fragment).codeCallback(null);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        if (fragment instanceof ForgetPasswordFragment) {
                            ((ForgetPasswordFragment) fragment).codeCallback(resultCode);
                        }
                    }
                });
    }

    /**
     * ??????????????????
     */
    public void resetPassword(BaseFragment fragment, ResetPasswordRequest resetPasswordRequest) {
        RetrofitUtils.getApiUrl().resetPassword(resetPasswordRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        if (fragment instanceof ForgetPasswordFragment) {
                            ((ForgetPasswordFragment) fragment).resetPasswordCallback(null);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        if (fragment instanceof ForgetPasswordFragment) {
                            ((ForgetPasswordFragment) fragment).resetPasswordCallback(resultCode);
                        }
                    }
                });
    }

    /**
     * ????????????
     */
    public void mobileRegister(BaseFragment fragment, PhoneRegisterRequest phoneRegisterRequest) {
        RetrofitUtils.getApiUrl().phoneOrMailRegister(phoneRegisterRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean result) {
                        if (fragment instanceof RegisteredFragment) {
                            ((RegisteredFragment) fragment).registerCallback(result, null);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        if (fragment instanceof RegisteredFragment) {
                            ((RegisteredFragment) fragment).registerCallback(null, resultCode);
                        }
                    }
                });
    }

    /**
     * ????????????
     */
    public void wxLogin() {
        showLoadingDialog();
        if (MyApplication.api == null) {
            MyApplication.api = WXAPIFactory.createWXAPI(this, MyApplication.APP_ID, true);
        }
        if (!MyApplication.api.isWXAppInstalled()) {
            hideLoadingDialog();
            ToastUtils.show(LoginActivity.this, getString(R.string.no_WeChat));
            return;
        }
        MyApplication.api.registerApp(MyApplication.APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_????????????";
        MyApplication.api.sendReq(req);
    }

    private Tencent mTencent;
    private IUiListener loginListener;
    /**
     * qq openId
     */
    private String mOpenId;

    /**
     * qq??????
     */
    public void qqLogin() {
        showLoadingDialog();
        //?????????Tencent??????
        if (mTencent == null) {
            mTencent = Tencent.createInstance(MyApplication.QQ_APPID, this.getApplicationContext());
        }
        if (loginListener == null) {
            loginListener = new LoginListener();
        }
        mTencent.login(this, "all", loginListener);
    }

    /**
     * qq??????????????????
     */
    private class LoginListener implements IUiListener {

        @Override
        public void onComplete(Object o) { //????????????
            parseResult(o);
        }

        @Override
        public void onError(UiError uiError) { //????????????
            hideLoadingDialog();
            ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
        }

        @Override
        public void onCancel() { //????????????
            hideLoadingDialog();
            ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
        }
    }

    /**
     * ???????????????Json???
     */
    private void parseResult(Object o) {
        JSONObject jsonObject = (JSONObject) o;
        try {
            //????????????
            mOpenId = jsonObject.getString("openid");
            //????????????
            String accessToken = jsonObject.getString("access_token");
            //token?????????
            String expires = jsonObject.getString("expires_in");
            //??????token
            mTencent.setOpenId(mOpenId);
            mTencent.setAccessToken(accessToken, expires);
            setUserInfo();
        } catch (JSONException e) {
            hideLoadingDialog();
            ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
        }
    }

    /**
     * ???????????????????????????
     */
    private void setUserInfo() {
        QQToken qqToken = mTencent.getQQToken();
        UserInfo userInfo = new UserInfo(this, qqToken);
        userInfo.getUserInfo(new GetInfoListener());
    }

    /**
     * QQ??????????????????
     */
    private class GetInfoListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            JSONObject jsonObject = (JSONObject) o;
            try {
                threePartyLogin(jsonObject.getString("figureurl_qq_2"), jsonObject.getString("nickname"), mOpenId, Config.TYPE_QQ, mOpenId);
            } catch (Exception e) {
                hideLoadingDialog();
                ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
            }
        }

        @Override
        public void onError(UiError uiError) { //????????????
            hideLoadingDialog();
            ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
        }

        @Override
        public void onCancel() {
            hideLoadingDialog();
            ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
        }
    }

    /**
     * facebook ??????
     */
    public void facebookLogin() {
        /*???????????????????????????*/
        showLoadingDialog();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (!isLoggedIn) {
            /*????????????*/
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
        } else {
            getUserInfoByFacebook(accessToken);
        }
    }

    /**
     * ??????????????????
     */
    private void getUserInfoByFacebook(AccessToken accessToken) {
        String userId = accessToken.getUserId();
        GraphRequest.newMeRequest(accessToken, (object, response) -> {
            try {
                if (object != null) {
                    String id = object.optString("id");
                    if (TextUtils.isEmpty(id)) {
                        id = userId;
                    }
                    String name = object.optString("name");
                    //??????????????????
                    String photo = "";
                    JSONObject objectPic = object.optJSONObject("picture");
                    if (objectPic != null && objectPic.length() > 0) {
                        JSONObject objectData = objectPic.optJSONObject("data");
                        if (objectData != null && objectData.length() > 0) {
                            photo = objectData.optString("url");
                        }
                    }
                    threePartyLogin(photo, name, id, Config.TYPE_FACEBOOK, id);
                } else {
                    ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
                }
            } catch (Exception e) {
                ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
            }
        }).executeAsync();
    }

    /**
     * Google????????????code
     */
    private static final int RC_SIGN_IN = 0x99;

    /**
     * google ??????
     */
    public void googleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestServerAuthCode(getResources().getString(R.string.server_web_client_id))
                .build();
        GoogleSignInClient mGoogleClient = GoogleSignIn.getClient(MyApplication.mInstance, gso);
        Intent signInIntent = mGoogleClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Google ????????????
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        if (completedTask == null) {
            hideLoadingDialog();
            ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
            return;
        }
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                Uri personPhoto = account.getPhotoUrl();
                String headUrl = null;
                if (personPhoto != null) {
                    headUrl = personPhoto.toString();
                }
                threePartyLogin(headUrl, account.getDisplayName(), account.getId(), Config.TYPE_GOOGLE, account.getId());
            } else {
                hideLoadingDialog();
                ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
            }
        } catch (ApiException e) {
            e.printStackTrace();
            // ???????????????????????????:12500?????????GooglePLay??????????????????,??????google????????????????????????
            hideLoadingDialog();
            ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
        }
    }

    /**
     * ????????????
     */
    public void loginSuccess(LoginBean loginBean, int regType) {
        SPUtils.putLong(this, Config.CURRENT_USER, loginBean.getId());
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUserId(loginBean.getId());
        userInfoEntity.setSession(loginBean.getSession());
        userInfoEntity.setUserCode(loginBean.getUserCode());
        userInfoEntity.setRegType(regType);
        userInfoEntity.setNickname(loginBean.getNickName());
        userInfoEntity.setHeadUrl(loginBean.getHeaderUrl());

        int gender = Integer.parseInt(loginBean.getSex());
        int otherGender = 3;
        if (gender == otherGender) {
            gender = 1;
        }
        userInfoEntity.setGender(gender);
        if (loginBean.getWeight() == null) {
            userInfoEntity.setWeight(6000);
        } else {
            userInfoEntity.setWeight(Integer.parseInt(loginBean.getWeight()));
        }
        if (loginBean.getHeight() == null) {
            userInfoEntity.setHeight(17500);
        } else {
            userInfoEntity.setHeight(Integer.parseInt(loginBean.getHeight()));
        }
        if (loginBean.getBirthday() == null) {
            userInfoEntity.setBirthday("2010-01-01");
        } else {
            userInfoEntity.setBirthday(loginBean.getBirthday());
        }
        userInfoEntity.setMail(loginBean.getMail());
        userInfoEntity.setPhone(loginBean.getPhone());
        if (AppUtils.isChinese()) {
            userInfoEntity.setUnit(Unit.METRIC.value);
        } else {
            userInfoEntity.setUnit(Unit.IMPERIAL.value);
        }
        Config.unit = userInfoEntity.getUnit();
        userInfoEntity.setCountry(loginBean.getCountry());

        UserInfoEntity userInfo = MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().queryBuilder()
                .where(UserInfoEntityDao.Properties.UserId.eq(getUserId()))
                .unique();

        if (userInfo != null) {
            userInfoEntity.setId(userInfo.getId());
            userInfoEntity.setGuideFlag(userInfo.getGuideFlag());
            MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().update(userInfoEntity);
        } else {
            MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().insertOrReplace(userInfoEntity);
        }
        getAuthorization(loginBean.getSession(), loginBean.getNewUser(), userInfoEntity);

    }

    /**
     * ????????????
     */
    private void getAuthorization(String session, int newUser, UserInfoEntity userInfoEntity) {
        SessionRequest sessionRequest = new SessionRequest();
        sessionRequest.setSession(session);
        RetrofitUtils.getApiUrl().authList(sessionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<List<AuthorListResponse>>() {
                    @Override
                    public void onSuccess(List<AuthorListResponse> result) {
                        hideLoadingDialog();
                        List<AuthorEntity> authorEntities = DbUtils.getInstance().queryAuthorList(getUserId());
                        if (authorEntities != null && authorEntities.size() > 0) {
                            for (AuthorEntity authorEntity : authorEntities) {
                                DbUtils.getInstance().deleteAuthorEntity(authorEntity);
                            }
                        }
                        if (result != null && result.size() > 0) {
                            for (AuthorListResponse authorListResponse : result) {
                                AuthorEntity authorEntity = new AuthorEntity();
                                authorEntity.setPlatformType(authorListResponse.getPlatformType());
                                authorEntity.setPullToken(authorListResponse.getPullToken());
                                authorEntity.setTimeOut(authorListResponse.getTimeOut());
                                authorEntity.setToken(authorListResponse.getToken());
                                authorEntity.setUserId(getUserId());
                                DbUtils.getInstance().addAuthorEntity(authorEntity);
                            }
                        }
                        checkActivity(newUser, userInfoEntity);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        checkActivity(newUser, userInfoEntity);
                    }
                });
    }

    private void checkActivity(int newUser, UserInfoEntity userInfoEntity) {
        //?????????
        if (newUser == 0) {
            userInfoEntity.setGuideFlag(255);
            startActivity(new Intent(this, PerfectInformationActivity.class));
        } else {
            startActivity(new Intent(this, HomeActivity.class));
        }
        finish();
    }

    /**
     * ???????????????
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* QQ?????????*/
        if (loginListener != null) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
                Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
            }
            /* Google ????????????????????????*/
        } else if (requestCode == RC_SIGN_IN) {
            super.onActivityResult(requestCode, resultCode, data);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            /* facebook?????????????????????????????? onActivityResult ?????????(CallbackManager ??????????????????onActivityResult ???????????????)
             * ??? onActivityResult ??????????????? callbackManager.onActivityResult????????? callbackManager ???????????????????????? LoginManager???
             */
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

}