package com.meilancycling.mema.work;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.meilancycling.mema.db.AuthorEntity;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.FileUploadEntity;

import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.ui.setting.StravaBean;
import com.meilancycling.mema.utils.AppUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.IOException;

import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * @Description: 传感器上传任务
 * @Author: lion 571135591
 * @CreateDate: 2021/6/11 2:51 下午
 */
public class StravaUploadWork extends Worker {

    private final WorkerParameters mWorkerParameters;
    private final Context mContext;

    public StravaUploadWork(@NonNull Context context, @NonNull WorkerParameters parameters) {
        super(context, parameters);
        mWorkerParameters = parameters;
        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        String session = mWorkerParameters.getInputData().getString(FileUploadWork.SESSION_FLAG);
        long userId = mWorkerParameters.getInputData().getLong(FileUploadWork.DATA_USER_ID, 0);
        String filePath = mWorkerParameters.getInputData().getString(FileUploadWork.DATA_FILE_PATH);
        AuthorEntity authorEntity = DbUtils.getInstance().queryAuthorEntity(userId, 1);
        if (authorEntity != null) {
            FileUploadEntity fileUploadEntity = DbUtils.getInstance().queryFileUploadEntityByName(userId, filePath);
            byte[] bytesByFile = AppUtils.getBytesByFile(filePath);
            if (fileUploadEntity != null && bytesByFile != null) {
                if (System.currentTimeMillis() > Long.parseLong(authorEntity.getTimeOut()) * 1000) {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("client_id", "54706")
                            .add("client_secret", "02361fefe4813ddc67f3288c555f80eae85bb448")
                            .add("grant_type", "refresh_token")
                            .add("refresh_token", authorEntity.getPullToken())
                            .build();
                    Request request = new Request.Builder().url("https://www.strava.com/api/v3/oauth/token")
                            .post(requestBody)
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.code() == 200) {
                                StravaBean stravaBean = new Gson().fromJson(response.body().string(), StravaBean.class);
                                authorEntity.setToken(stravaBean.getToken_type() + " " + stravaBean.getAccess_token());
                                authorEntity.setPullToken(stravaBean.getRefresh_token());
                                authorEntity.setTimeOut(String.valueOf(stravaBean.getExpires_at()));
                                DbUtils.getInstance().updateAuthorEntity(authorEntity);
                                strUpload(authorEntity.getToken(), bytesByFile);
                                uploadAuthWork(session, userId, 1);
                            }
                        }
                    });
                } else {
                    strUpload(authorEntity.getToken(), bytesByFile);
                }
            }
        }
        return Result.success();
    }

    @SuppressLint("RestrictedApi")
    private void uploadAuthWork(String session, long userId, int platformType) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data sendData = new Data.Builder().
                putInt(UpdateAuthWork.PLATFORM_TYPE, platformType).
                putLong(FileUploadWork.DATA_USER_ID, userId).
                putString(SensorUploadWork.SESSION_FLAG, session).
                build();
        WorkManager.getInstance(mContext)
                .enqueue(new OneTimeWorkRequest.Builder(UpdateAuthWork.class)
                        .setConstraints(constraints)
                        .setInputData(sendData)
                        .build());
    }

    /**
     * strava 文件上传
     */
    private void strUpload(String token, byte[] bytesByFile) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //打印一次请求的全部信息
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //设置读取超时时间
                .readTimeout(RetrofitUtils.DEFAULT_TIME, TimeUnit.SECONDS)
                //设置请求超时时间
                .connectTimeout(RetrofitUtils.DEFAULT_TIME, TimeUnit.SECONDS)
                //设置写入超时时间
                .writeTimeout(RetrofitUtils.DEFAULT_TIME, TimeUnit.SECONDS)
                //添加打印拦截器
                .addInterceptor(loggingInterceptor)
                //设置出现错误进行重新连接。
                .retryOnConnectionFailure(true)
                .build();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),bytesByFile);
        requestBody.addFormDataPart("file", "upload.fit", body);
        requestBody.addFormDataPart("data_type", "fit");
        Request request = new Request.Builder()
                .url("https://www.strava.com/api/v3/uploads")
                .addHeader("Authorization", token)
                .post(requestBody.build())
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                CrashReport.postCatchedException(new Throwable("strava 失败"));

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
