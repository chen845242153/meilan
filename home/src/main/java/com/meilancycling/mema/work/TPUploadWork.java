package com.meilancycling.mema.work;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Base64;

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
import com.meilancycling.mema.network.BaseResponse;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.bean.request.AddSensorRequest;
import com.meilancycling.mema.ui.setting.StravaBean;
import com.meilancycling.mema.ui.setting.TpBean;
import com.meilancycling.mema.utils.AppUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
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
public class TPUploadWork extends Worker {

    private final WorkerParameters mWorkerParameters;
    private final Context mContext;

    public TPUploadWork(@NonNull Context context, @NonNull WorkerParameters parameters) {
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
        AuthorEntity authorEntity = DbUtils.getInstance().queryAuthorEntity(userId, 4);
        if (authorEntity != null) {
            FileUploadEntity fileUploadEntity = DbUtils.getInstance().queryFileUploadEntityByName(userId, filePath);
            byte[] bytesByFile = AppUtils.getBytesByFile(filePath);
            if (fileUploadEntity != null && bytesByFile != null) {
                if (System.currentTimeMillis() > Long.parseLong(authorEntity.getTimeOut()) * 1000) {
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
                    String paramsStr =
                            "client_id=shenzhen-meilan-technology-colimited" +
                                    "&grant_type=refresh_token" +
                                    "&refresh_token=" + authorEntity.getPullToken() +
                                    "&client_secret=NJKjZkusPWEau6nUzJL5KyYvmDGpKG1SPD88DU";
                    MediaType form = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
                    RequestBody requestBody = RequestBody.create(form, paramsStr);
                    Request request = new Request.Builder().url("https://oauth.trainingpeaks.com/oauth/token")
                            .post(requestBody)
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.code() == 200) {
                                TpBean tp = new Gson().fromJson(response.body().string(), TpBean.class);
                                authorEntity.setToken(tp.getToken_type() + " " + tp.getAccess_token());
                                authorEntity.setPullToken(tp.getRefresh_token());
                                authorEntity.setTimeOut(String.valueOf(System.currentTimeMillis() / 1000 + tp.getExpires_in()));
                                DbUtils.getInstance().updateAuthorEntity(authorEntity);
                                uploadTrainingPeaks(authorEntity.getToken(), bytesByFile);
                                uploadAuthWork(session, userId, 4);
                            }
                        }
                    });
                } else {
                    uploadTrainingPeaks(authorEntity.getToken(), bytesByFile);
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

    private void uploadTrainingPeaks(String token, byte[] bytesByFile) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("UploadClient", "upload_shenzhen_meilanapp")
                .add("Filename", "fileUploadEntity.getFileName()")
                .add("Data", Base64.encodeToString(bytesByFile, Base64.DEFAULT))
                .build();
        Request request = new Request.Builder()
                .addHeader("Authorization", token)
                .url("https://api.trainingpeaks.com/v1/file")
                .post(formBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                //  CrashReport.postCatchedException(new Throwable("trainingPeaks 上传成功=" + response.body().string()));
            }
        });
    }
}
