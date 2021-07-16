package com.meilancycling.mema.network;

import android.content.Intent;
import android.content.res.Resources;

import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Config;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author lion
 * 数据返回统一处理
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    @Override
    public void onNext(BaseResponse<T> response) {
        String successCode = "200";
        String timeoutCode = "20201";
        String otherCode = "20200";
        if (timeoutCode.equals(response.getStatus()) || otherCode.equals(response.getStatus())) {
            Intent intent = new Intent(BroadcastConstant.ACTION_LOGIN);
            if (timeoutCode.equals(response.getStatus())) {
                intent.putExtra("message", MyApplication.mInstance.getString(R.string.CODE_20201));
            } else {
                intent.putExtra("message", MyApplication.mInstance.getString(R.string.CODE_20200));
            }
            MyApplication.mInstance.sendBroadcast(intent);
            onFailure(new Throwable(), response.getStatus());
        } else if (successCode.equals(response.getStatus())) {
            onSuccess(response.getData());
        } else {
            Resources resources = MyApplication.mInstance.getResources();
            switch (Integer.parseInt(response.getStatus())) {
                case 991:
                    onFailure(new Throwable(resources.getString(R.string.CODE_991)), response.getStatus());
                    break;
                case 9999:
                    onFailure(new Throwable(resources.getString(R.string.CODE_9999)), response.getStatus());
                    break;
                case 120000:
                    onFailure(new Throwable(resources.getString(R.string.CODE_120000)), response.getStatus());
                    break;
                case 120001:
                    onFailure(new Throwable(resources.getString(R.string.CODE_120001)), response.getStatus());
                    break;
                case 120002:
                    onFailure(new Throwable(resources.getString(R.string.CODE_120002)), response.getStatus());
                    break;
                case 120003:
                    onFailure(new Throwable(resources.getString(R.string.CODE_120003)), response.getStatus());
                    break;
                case 120004:
                    onFailure(new Throwable(resources.getString(R.string.CODE_120004)), response.getStatus());
                    break;
                case 120005:
                    onFailure(new Throwable(resources.getString(R.string.CODE_120005)), response.getStatus());
                    break;
                case 120006:
                    onFailure(new Throwable(resources.getString(R.string.CODE_120006)), response.getStatus());
                    break;
                case 20501:
                    onFailure(new Throwable(resources.getString(R.string.CODE_20501)), response.getStatus());
                    break;
                case 20503:
                    onFailure(new Throwable(resources.getString(R.string.CODE_20503)), response.getStatus());
                    break;
                case 20504:
                    onFailure(new Throwable(resources.getString(R.string.CODE_20504)), response.getStatus());
                    break;
                case 20505:
                    onFailure(new Throwable(resources.getString(R.string.CODE_20505)), response.getStatus());
                    break;
                case 20506:
                    onFailure(new Throwable(resources.getString(R.string.CODE_20506)), response.getStatus());
                    break;
                case 20508:
                    onFailure(new Throwable(resources.getString(R.string.CODE_20508)), response.getStatus());
                    break;
                case 20513:
                    onFailure(new Throwable(resources.getString(R.string.CODE_20513)), response.getStatus());
                    break;
                case 20530:
                    onFailure(new Throwable(resources.getString(R.string.CODE_20530)), response.getStatus());
                    break;
                case 205302:
                    onFailure(new Throwable(resources.getString(R.string.CODE_205302)), response.getStatus());
                    break;
                case 20550:
                    onFailure(new Throwable(resources.getString(R.string.CODE_20550)), response.getStatus());
                    break;
                case 20560:
                    onFailure(new Throwable(resources.getString(R.string.CODE_20560)), response.getStatus());
                    break;
                default:
                    onFailure(new Throwable(resources.getString(R.string.CODE_9999)), response.getStatus());
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof UnknownHostException || e instanceof SocketTimeoutException || e instanceof ConnectException) {
            //连接错误或者网络错误
            onFailure(e, Config.DISCONNECT_NETWORK);
        } else {
            onFailure(e, null);
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    /**
     * 响应成功
     *
     * @param result 成功数据
     */
    public abstract void onSuccess(T result);

    /**
     * 响应失败
     *
     * @param e          异常
     * @param resultCode 失败的code
     */
    public abstract void onFailure(Throwable e, String resultCode);

}
