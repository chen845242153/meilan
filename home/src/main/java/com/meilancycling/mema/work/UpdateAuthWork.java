package com.meilancycling.mema.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.meilancycling.mema.db.AuthorEntity;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.network.BaseResponse;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.bean.request.AddSensorRequest;
import com.meilancycling.mema.network.bean.request.AuthorRequest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * @Description: 传感器上传任务
 * @Author: lion 571135591
 * @CreateDate: 2021/6/11 2:51 下午
 */
public class UpdateAuthWork extends Worker {

    public final static String PLATFORM_TYPE = "platform_type";

    private final WorkerParameters mWorkerParameters;

    public UpdateAuthWork(@NonNull Context context, @NonNull WorkerParameters parameters) {
        super(context, parameters);
        mWorkerParameters = parameters;
    }

    @NonNull
    @Override
    public Result doWork() {
        String session = mWorkerParameters.getInputData().getString(FileUploadWork.SESSION_FLAG);
        long userId = mWorkerParameters.getInputData().getLong(FileUploadWork.DATA_USER_ID, 0);
        int platformType = mWorkerParameters.getInputData().getInt(PLATFORM_TYPE, -1);
        if (platformType != -1) {
            AuthorEntity authorEntity = DbUtils.getInstance().queryAuthorEntity(userId, platformType);
            if (authorEntity != null) {
                AuthorRequest authorRequest = new AuthorRequest();
                authorRequest.setPlatformType(authorEntity.getPlatformType());
                authorRequest.setPullToken(authorEntity.getPullToken());
                authorRequest.setSession(session);
                authorRequest.setTimeOut(authorEntity.getTimeOut());
                authorRequest.setToken(authorEntity.getToken());
                RetrofitUtils.getApiUrl()
                        .updateAuthorize(authorRequest)
                        .subscribe(new Observer<BaseResponse<Object>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                            }

                            @Override
                            public void onNext(@NonNull BaseResponse<Object> objectBaseResponse) {
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
            }
        }
        return Result.success();
    }
}
