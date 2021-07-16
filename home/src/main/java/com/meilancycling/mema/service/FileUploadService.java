package com.meilancycling.mema.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Base64;

import com.google.gson.Gson;
import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.db.AuthorEntity;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.FileUploadEntity;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.db.greendao.UserInfoEntityDao;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.ui.setting.KomootBean;
import com.meilancycling.mema.ui.setting.StravaBean;
import com.meilancycling.mema.ui.setting.TpBean;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.SPUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 文件上传服务
 *
 * @author lion
 */
public class FileUploadService extends Service {
    private long userId;
    private final int notUploaded = 0;
    private boolean isUpload;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isUpload = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isUpload = false;
        userId = SPUtils.getLong(this, Config.CURRENT_USER);
        startUpload();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 上传文件
     */
    private void startUpload() {
        if (isUpload) {
            return;
        }
        isUpload = true;
        List<FileUploadEntity> fileList = DbUtils.getInstance().queryFileUploadEntity(userId);
        if (null == fileList || fileList.size() == 0) {
            stopSelf();
        } else {
            FileUploadEntity fileUploadEntity = fileList.get(0);
            uploadService(fileUploadEntity);
        }
    }

    private final int serviceFlag = 0x01;
    private final int stravaFlag = 0x02;
    private final int komootFlag = 0x04;
    private final int trainingPeaks = 0x08;

    /**
     * 一条数据处理完成
     */
    private void fileFinish(FileUploadEntity fileUploadEntity) {
        isUpload = false;
        DbUtils.getInstance().deleteFileUploadEntity(fileUploadEntity);
        startUpload();
    }

    /**
     * 上传服务器
     */
    private void uploadService(FileUploadEntity fileUploadEntity) {
//        if ((fileUploadEntity.getUploadStatus() & serviceFlag) == notUploaded) {
//            uploadFitFile(fileUploadEntity);
//        } else {
//            uploadStrava(fileUploadEntity);
//        }
    }

    /**
     * 上传strava
     */
    private void uploadStrava(FileUploadEntity fileUploadEntity) {
//        if (((fileUploadEntity.getUploadStatus() & stravaFlag) >> 1) == notUploaded) {
//            checkStravaToken(fileUploadEntity);
//        } else {
//            uploadkomoot(fileUploadEntity);
//        }
    }

    /**
     * 上传komoot
     */
    private void uploadkomoot(FileUploadEntity fileUploadEntity) {
//        if (((fileUploadEntity.getUploadStatus() & komootFlag) >> 2) == notUploaded) {
//            checkKomootToken(fileUploadEntity);
//        } else {
//            uploadTrainingPeaks(fileUploadEntity);
//        }
    }

    /**
     * 上传trainingPeaks
     */
    private void uploadTrainingPeaks(FileUploadEntity fileUploadEntity) {
//        if (((fileUploadEntity.getUploadStatus() & trainingPeaks) >> 3) == notUploaded) {
//            checkTrainingPeaks(fileUploadEntity);
//        } else {
//            fileFinish(fileUploadEntity);
//        }
    }

    /**
     * 上传历史记录文件
     */
    public void uploadFitFile(FileUploadEntity fileUploadEntity) {
        UserInfoEntity userInfoEntity = MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().queryBuilder()
                .where(UserInfoEntityDao.Properties.UserId.eq(userId))
                .unique();
        Map<String, RequestBody> mapParams = new HashMap<>(8);
        RequestBody sessionBody = RequestBody.create(MediaType.parse("multipart/form-data"), userInfoEntity.getSession());
        mapParams.put("session", sessionBody);
        if (fileUploadEntity.getProductNo() != null) {
            RequestBody equipmentSourceBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileUploadEntity.getProductNo());
            mapParams.put("equipmentSource", equipmentSourceBody);
        }
        RequestBody motionTypeBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(fileUploadEntity.getSportsType()));
        mapParams.put("motionType", motionTypeBody);
        RequestBody dataSource = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(1));
        mapParams.put("dataSource", dataSource);
        RequestBody mDataTypeBody;
        String fileName = fileUploadEntity.getFileName();
        if (fileName.toLowerCase().endsWith(".fit")) {
            mDataTypeBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(1));
        } else {
            mDataTypeBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(2));
        }
        mapParams.put("dataType", mDataTypeBody);
        RequestBody timeZone = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(AppUtils.getTimeZone()));
        mapParams.put("timeZone", timeZone);
//        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileUploadEntity.getBytes());
//        MultipartBody.Part myBody = MultipartBody.Part.createFormData("uploadFile", fileName, fileBody);

//        RetrofitUtils.getApiUrl().uploadFitFile(mapParams, myBody)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MyObserver<String>() {
//                    @Override
//                    public void onSuccess(String result) {
//                        Intent intent = new Intent(BroadcastConstant.ACTION_DELETE_RECORD);
//                        sendBroadcast(intent);
//                        fileUploadEntity.setUploadStatus(fileUploadEntity.getUploadStatus() | serviceFlag);
//                        DbUtils.getInstance().updateFileUploadEntity(fileUploadEntity);
//                        uploadStrava(fileUploadEntity);
//                    }
//
//                    @Override
//                    public void onFailure(Throwable e, String resultCode) {
//                        if ("-1".equals(resultCode)) {
//                            isUpload = false;
//                        } else {
//                            fileFinish(fileUploadEntity);
//                        }
//                    }
//                });
    }

    /**
     * 检查strava token
     */
    private void checkStravaToken(FileUploadEntity fileUploadEntity) {
        AuthorEntity authorEntity = DbUtils.getInstance().queryAuthorEntity(userId, 1);
        if (authorEntity == null) {
            uploadkomoot(fileUploadEntity);
        } else {
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
                        uploadkomoot(fileUploadEntity);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.code() == 200) {
                            StravaBean stravaBean = new Gson().fromJson(response.body().string(), StravaBean.class);
                            authorEntity.setToken(stravaBean.getToken_type() + " " + stravaBean.getAccess_token());
                            authorEntity.setPullToken(stravaBean.getRefresh_token());
                            authorEntity.setTimeOut(String.valueOf(stravaBean.getExpires_at()));
                            DbUtils.getInstance().updateAuthorEntity(authorEntity);
                            strUpload(fileUploadEntity, authorEntity.getToken());
                        } else {
                            uploadkomoot(fileUploadEntity);
                        }
                    }
                });
            } else {
                strUpload(fileUploadEntity, authorEntity.getToken());
            }
        }
    }

    /**
     * strava 文件上传
     */
    private void strUpload(FileUploadEntity fileUploadEntity, String token) {
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
//        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), fileUploadEntity.getBytes());
//        requestBody.addFormDataPart("file", "upload.fit", body);
//        requestBody.addFormDataPart("data_type", "fit");
        Request request = new Request.Builder()
                .url("https://www.strava.com/api/v3/uploads")
                .addHeader("Authorization", token)
                .post(requestBody.build())
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                CrashReport.postCatchedException(new Throwable("strava token更新失败"));
                uploadkomoot(fileUploadEntity);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                fileUploadEntity.setUploadStatus(fileUploadEntity.getUploadStatus() | stravaFlag);
//                DbUtils.getInstance().updateFileUploadEntity(fileUploadEntity);
//                uploadkomoot(fileUploadEntity);
            }
        });
    }

    /**
     * komoot 文件上传
     */
    private void checkKomootToken(FileUploadEntity fileUploadEntity) {
        AuthorEntity authorEntity = DbUtils.getInstance().queryAuthorEntity(userId, 2);
        if (authorEntity == null) {
            uploadTrainingPeaks(fileUploadEntity);
        } else {
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
                        uploadTrainingPeaks(fileUploadEntity);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.code() == 200) {
                            KomootBean komootBean = new Gson().fromJson(response.body().string(), KomootBean.class);
                            authorEntity.setToken(komootBean.getToken_type() + " " + komootBean.getAccess_token());
                            authorEntity.setPullToken(komootBean.getRefresh_token());
                            authorEntity.setTimeOut(String.valueOf(System.currentTimeMillis() / 1000 + komootBean.getExpires_in()));
                            DbUtils.getInstance().updateAuthorEntity(authorEntity);
                            uploadKom(fileUploadEntity, authorEntity.getToken());
                        } else {
                            uploadTrainingPeaks(fileUploadEntity);
                        }
                    }
                });
            } else {
                uploadKom(fileUploadEntity, authorEntity.getToken());
            }
        }
    }

    private void uploadKom(FileUploadEntity fileUploadEntity, String token) {
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
//        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), fileUploadEntity.getBytes());
        Request request = new Request.Builder()
                .addHeader("Authorization", token)
                .url("https://external-api.komoot.de/v007/tours/?data_type=fit")
//                .post(fileBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                uploadTrainingPeaks(fileUploadEntity);
            }

            @Override
            public void onResponse(Call call, Response response) {
//                fileUploadEntity.setUploadStatus(fileUploadEntity.getUploadStatus() | komootFlag);
//                DbUtils.getInstance().updateFileUploadEntity(fileUploadEntity);
//                uploadTrainingPeaks(fileUploadEntity);
            }
        });
    }

    /**
     * trainingPeaks 文件上传
     */
    private void checkTrainingPeaks(FileUploadEntity fileUploadEntity) {
        AuthorEntity authorEntity = DbUtils.getInstance().queryAuthorEntity(userId, 4);
        if (authorEntity == null) {
            fileFinish(fileUploadEntity);
        } else {
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
                        fileFinish(fileUploadEntity);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.code() == 200) {
                            TpBean tp = new Gson().fromJson(response.body().string(), TpBean.class);
                            authorEntity.setToken(tp.getToken_type() + " " + tp.getAccess_token());
                            authorEntity.setPullToken(tp.getRefresh_token());
                            authorEntity.setTimeOut(String.valueOf(System.currentTimeMillis() / 1000 + tp.getExpires_in()));
                            DbUtils.getInstance().updateAuthorEntity(authorEntity);
                            uploadTrainingPeaks(fileUploadEntity, authorEntity.getToken());
                        } else {
                            fileFinish(fileUploadEntity);
                        }
                    }
                });
            } else {
                uploadTrainingPeaks(fileUploadEntity, authorEntity.getToken());
            }
        }
    }

    private void uploadTrainingPeaks(FileUploadEntity fileUploadEntity, String token) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        RequestBody formBody = new FormBody.Builder()
//                .add("UploadClient", "upload_shenzhen_meilanapp")
//                .add("Filename", "fileUploadEntity.getFileName()")
//                .add("Data", Base64.encodeToString(fileUploadEntity.getBytes(), Base64.DEFAULT))
//                .build();
//        Request request = new Request.Builder()
//                .addHeader("Authorization", token)
//                .url("https://api.trainingpeaks.com/v1/file")
//                .post(formBody)
//                .build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                fileFinish(fileUploadEntity);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//           //  CrashReport.postCatchedException(new Throwable("trainingPeaks 上传成功=" + response.body().string()));
//                fileUploadEntity.setUploadStatus(fileUploadEntity.getUploadStatus() | trainingPeaks);
//                DbUtils.getInstance().updateFileUploadEntity(fileUploadEntity);
//                fileFinish(fileUploadEntity);
//            }
//        });
    }

}
