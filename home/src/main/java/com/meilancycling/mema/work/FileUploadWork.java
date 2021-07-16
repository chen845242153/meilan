package com.meilancycling.mema.work;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.FileUploadEntity;
import com.meilancycling.mema.network.BaseResponse;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Description: 传感器上传任务
 * @Author: lion 571135591
 * @CreateDate: 2021/6/11 2:51 下午
 */
public class FileUploadWork extends Worker {
    public final static String SESSION_FLAG = "session_flag";
    public final static String DATA_USER_ID = "data_user_id";
    public final static String DATA_FILE_PATH = "data_file_path";
    private final WorkerParameters mWorkerParameters;
    private Context mContext;

    public FileUploadWork(@NonNull Context context, @NonNull WorkerParameters parameters) {
        super(context, parameters);
        mContext = context;
        mWorkerParameters = parameters;
    }

    @NonNull
    @Override
    public Result doWork() {
        String session = mWorkerParameters.getInputData().getString(SESSION_FLAG);
        long userId = mWorkerParameters.getInputData().getLong(DATA_USER_ID, 0);
        String filePath = mWorkerParameters.getInputData().getString(DATA_FILE_PATH);
        FileUploadEntity fileUploadEntity = DbUtils.getInstance().queryFileUploadEntityByName(userId, filePath);
        byte[] bytesByFile = AppUtils.getBytesByFile(filePath);
        if (fileUploadEntity != null && bytesByFile != null) {
            Map<String, RequestBody> mapParams = new HashMap<>(8);
            RequestBody sessionBody = RequestBody.create(MediaType.parse("multipart/form-data"), session);
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

            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), bytesByFile);
            MultipartBody.Part myBody = MultipartBody.Part.createFormData("uploadFile", fileName, fileBody);

            RetrofitUtils.getApiUrl().
                    uploadFitFile(mapParams, myBody)
                    .subscribe(new Observer<BaseResponse<String>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                        }

                        @Override
                        public void onNext(@NonNull BaseResponse<String> objectBaseResponse) {
                            Intent intent = new Intent(BroadcastConstant.ACTION_DELETE_RECORD);
                            mContext.sendBroadcast(intent);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
        return Result.success();
    }
}