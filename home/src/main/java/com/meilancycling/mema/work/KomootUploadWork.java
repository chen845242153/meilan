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
import com.meilancycling.mema.ui.setting.KomootBean;
import com.meilancycling.mema.utils.AppUtils;

import java.io.IOException;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.MediaType;
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
public class KomootUploadWork extends Worker {

    private final WorkerParameters mWorkerParameters;
    private final Context mContext;

    public KomootUploadWork(@NonNull Context context, @NonNull WorkerParameters parameters) {
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
        AuthorEntity authorEntity = DbUtils.getInstance().queryAuthorEntity(userId, 2);
        if (authorEntity != null) {
            FileUploadEntity fileUploadEntity = DbUtils.getInstance().queryFileUploadEntityByName(userId, filePath);
            byte[] bytesByFile = AppUtils.getBytesByFile(filePath);
            if (fileUploadEntity != null && bytesByFile != null) {
                if (System.currentTimeMillis() > Long.parseLong(authorEntity.getTimeOut()) * 1000) {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String credential = Credentials.basic("meilan-k9v8js", "rahiezah8ha0thae4li3aepei");
                    RequestBody requestBody = new FormBody.Builder()
                            .add("refresh_token", authorEntity.getPullToken())
                            .add("grant_type", "refresh_token")
                            .build();
                    Request request = new Request.Builder().url("https://auth.komoot.de/oauth/token")
                            .header("Authorization", credential)
                            .post(requestBody)
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.code() == 200) {
                                KomootBean komootBean = new Gson().fromJson(response.body().string(), KomootBean.class);
                                authorEntity.setToken(komootBean.getToken_type() + " " + komootBean.getAccess_token());
                                authorEntity.setPullToken(komootBean.getRefresh_token());
                                authorEntity.setTimeOut(String.valueOf(System.currentTimeMillis() / 1000 + komootBean.getExpires_in()));
                                DbUtils.getInstance().updateAuthorEntity(authorEntity);
                                uploadKom(authorEntity.getToken(), bytesByFile);
                                uploadAuthWork(session, userId, 2);
                            }
                        }
                    });
                } else {
                    uploadKom(authorEntity.getToken(), bytesByFile);
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

    private void uploadKom(String token, byte[] bytesByFile) {
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
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), bytesByFile);
        Request request = new Request.Builder()
                .addHeader("Authorization", token)
                .url("https://external-api.komoot.de/v007/tours/?data_type=fit")
                .post(fileBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
            }
        });
    }
}
