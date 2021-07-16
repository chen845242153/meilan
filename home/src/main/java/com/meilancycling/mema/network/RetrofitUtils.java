package com.meilancycling.mema.network;

import androidx.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.meilancycling.mema.network.download.DownloadRequest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit封装
 *
 * @author lion
 */
public class RetrofitUtils {
    public static final String TAG = "okHttp";
    private static ApiService mApiService;
    /**
     * 接口地址
     * 测试环境
     * BASE_URL = "https://test.meilancycling.com/password/"
     * 正式环境
     * BASE_URL = "https://api.meilancycling.com/password/"
     */
    //  private final static String BASE_URL = "http://120.79.67.142:7002/";
//    private final static String BASE_URL = "https://api.meilancycling.com/";
    private final static String BASE_URL = "https://test.meilancycling.com/";
    //  private final static String BASE_URL = "https://test.meilancycling.com/interactive/";

    /**
     * 设置默认超时时间
     */
    public static final int DEFAULT_TIME = 600;

    /**
     * 单例模式
     */
    public static ApiService getApiUrl() {
        if (mApiService == null) {
            synchronized (RetrofitUtils.class) {
                if (mApiService == null) {
                    mApiService = new RetrofitUtils().getRetrofit();
                }
            }
        }
        return mApiService;
    }

    private RetrofitUtils() {
    }

    public ApiService getRetrofit() {
        // 初始化Retrofit
        return initRetrofit(initOkHttp()).create(ApiService.class);
    }

    /**
     * 初始化Retrofit
     */
    @NonNull
    private Retrofit initRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 初始化okhttp
     */
    @NonNull
    private OkHttpClient initOkHttp() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //打印一次请求的全部信息
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()
                //设置读取超时时间
                .readTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                //设置请求超时时间
                .connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                //设置写入超时时间
                .writeTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                //添加打印拦截器
                .addInterceptor(loggingInterceptor)
                //设置出现错误进行重新连接。
                .retryOnConnectionFailure(true)
                .build();
    }

    public static DownloadRequest downloadUrl(String url) {
        return new DownloadRequest(url, mApiService);
    }
}

