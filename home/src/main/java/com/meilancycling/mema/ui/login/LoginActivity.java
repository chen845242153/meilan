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
 * 登陆界面
 *
 * @author lion
 */
public class LoginActivity extends BaseActivity {
    public CommonProblemResponse value1;
    public CommonProblemResponse value2;
    /**
     * 查询注册协议
     */
    private String showType1 = "1";
    /**
     * 隐私政策查询
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
         *facebook 授权
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
     * 微信获取用户信息
     */
    private void getUserInfo(String accessToken, final String openId) {
        // 获取用户个人信息（UnionID机制）
        if (!TextUtils.isEmpty(accessToken) && !TextUtils.isEmpty(openId)) {
            // 获取授权
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
     * 显示fragment
     */
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_login, fragment, fragment.getClass().getSimpleName()).commit();
    }

    /**
     * 查询协议
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
     * 正则表达
     * 手机号码由11位数字组成，
     * 匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
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
     * 获取RSA公钥+私钥缓存KEY
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
     * 密码加密
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
     * 第三方登录
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
     * 登录
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
     * 对字符串进行MD5加密
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
     * 查询手机号与邮箱是否已注册
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
     * 发送短信或者邮箱
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
     * 重置登录密码
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
     * 用户注册
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
     * 微信登录
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
        req.state = "wechat_sdk_微信登录";
        MyApplication.api.sendReq(req);
    }

    private Tencent mTencent;
    private IUiListener loginListener;
    /**
     * qq openId
     */
    private String mOpenId;

    /**
     * qq登录
     */
    public void qqLogin() {
        showLoadingDialog();
        //初始化Tencent对象
        if (mTencent == null) {
            mTencent = Tencent.createInstance(MyApplication.QQ_APPID, this.getApplicationContext());
        }
        if (loginListener == null) {
            loginListener = new LoginListener();
        }
        mTencent.login(this, "all", loginListener);
    }

    /**
     * qq登陆结果回调
     */
    private class LoginListener implements IUiListener {

        @Override
        public void onComplete(Object o) { //登录成功
            parseResult(o);
        }

        @Override
        public void onError(UiError uiError) { //登录失败
            hideLoadingDialog();
            ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
        }

        @Override
        public void onCancel() { //取消登陆
            hideLoadingDialog();
            ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
        }
    }

    /**
     * 解析返回的Json串
     */
    private void parseResult(Object o) {
        JSONObject jsonObject = (JSONObject) o;
        try {
            //用户标识
            mOpenId = jsonObject.getString("openid");
            //登录信息
            String accessToken = jsonObject.getString("access_token");
            //token有效期
            String expires = jsonObject.getString("expires_in");
            //配置token
            mTencent.setOpenId(mOpenId);
            mTencent.setAccessToken(accessToken, expires);
            setUserInfo();
        } catch (JSONException e) {
            hideLoadingDialog();
            ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
        }
    }

    /**
     * 用户信息获取与展示
     */
    private void setUserInfo() {
        QQToken qqToken = mTencent.getQQToken();
        UserInfo userInfo = new UserInfo(this, qqToken);
        userInfo.getUserInfo(new GetInfoListener());
    }

    /**
     * QQ用户信息回调
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
        public void onError(UiError uiError) { //获取失败
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
     * facebook 登录
     */
    public void facebookLogin() {
        /*查看用户是否已登录*/
        showLoadingDialog();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (!isLoggedIn) {
            /*跳转登入*/
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
        } else {
            getUserInfoByFacebook(accessToken);
        }
    }

    /**
     * 获取用户信息
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
                    //获取用户头像
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
     * Google登录返回code
     */
    private static final int RC_SIGN_IN = 0x99;

    /**
     * google 登录
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
     * Google 授权回调
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
            // 注意如果返回错误码:12500可能是GooglePLay服务版本太旧,请到google商店更新最新版本
            hideLoadingDialog();
            ToastUtils.show(LoginActivity.this, getString(R.string.login_failed));
        }
    }

    /**
     * 登陆成功
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
     * 获取授权
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
        //新用户
        if (newUser == 0) {
            userInfoEntity.setGuideFlag(255);
            startActivity(new Intent(this, PerfectInformationActivity.class));
        } else {
            startActivity(new Intent(this, HomeActivity.class));
        }
        finish();
    }

    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* QQ授权登*/
        if (loginListener != null) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
                Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
            }
            /* Google 登入成功结果返回*/
        } else if (requestCode == RC_SIGN_IN) {
            super.onActivityResult(requestCode, resultCode, data);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            /* facebook登入返回的结果是通过 onActivityResult 获取的(CallbackManager 弄成全局的在onActivityResult 调用其方法)
             * 在 onActivityResult 方法中调用 callbackManager.onActivityResult，通过 callbackManager 将登录结果传递至 LoginManager。
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