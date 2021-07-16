package com.meilancycling.mema.network.download;

public interface FileDownloadCallback<T> {
    void onSuccess(T t);
    void onFail(Throwable throwable);
    void onProgress(long current, long total);
}
