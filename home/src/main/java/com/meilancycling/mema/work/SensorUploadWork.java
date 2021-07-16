package com.meilancycling.mema.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.meilancycling.mema.network.BaseResponse;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.bean.request.AddSensorRequest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * @Description: 传感器上传任务
 * @Author: lion 571135591
 * @CreateDate: 2021/6/11 2:51 下午
 */
public class SensorUploadWork extends Worker {

    public final static String SESSION_FLAG = "session_flag";
    public final static String DATA_ADDRESS = "data_address";
    public final static String DATA_NAME = "data_name";
    public final static String DATA_TYPE = "data_type";
    private final WorkerParameters mWorkerParameters;

    public SensorUploadWork(@NonNull Context context, @NonNull WorkerParameters parameters) {
        super(context, parameters);
        mWorkerParameters = parameters;
    }

    @NonNull
    @Override
    public Result doWork() {
        String session = mWorkerParameters.getInputData().getString(SESSION_FLAG);
        String[] addressList = (String[]) mWorkerParameters.getInputData().getKeyValueMap().get(DATA_ADDRESS);
        String[] nameList = (String[]) mWorkerParameters.getInputData().getKeyValueMap().get(DATA_NAME);
        Integer[] typeList = (Integer[]) mWorkerParameters.getInputData().getKeyValueMap().get(DATA_TYPE);

        AddSensorRequest addSensorRequest = new AddSensorRequest();
        List<AddSensorRequest.SensorListBean> sensorList = new ArrayList<>();
        for (int i = 0; i < addressList.length; i++) {
            AddSensorRequest.SensorListBean sensorListBean = new AddSensorRequest.SensorListBean();
            sensorListBean.setSensorType(String.valueOf(typeList[i]));
            sensorListBean.setSensorName(nameList[i]);
            sensorListBean.setSensorKey(addressList[i]);
            sensorList.add(sensorListBean);
        }
        addSensorRequest.setSensorList(sensorList);
        addSensorRequest.setSession(session);
        RetrofitUtils.getApiUrl()
                .addSensorList(addSensorRequest)
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
        return Result.success();
    }
}
