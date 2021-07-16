package com.meilancycling.mema.network.download;


import com.meilancycling.mema.network.ApiService;


import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 文件下载请求
 *
 * @author lion
 */
public class DownloadRequest {
    private String url;
    private ApiService apiService;

    public DownloadRequest(String url, ApiService apiService) {
        this.url = url;
        this.apiService = apiService;
    }

    public String execute(final String filePath, FileDownloadCallback<File> fileFileDownloadCallback) {
        String taskId = String.valueOf(System.currentTimeMillis());
        final FileDownLoadObserver fileDownLoadObserver = new FileDownLoadObserver(fileFileDownloadCallback);
        apiService.download(url)
                .subscribeOn(Schedulers.io())
                /*用于计算任务*/
                .observeOn(Schedulers.computation())
                .map(responseBody -> fileDownLoadObserver.saveFile(responseBody, filePath))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileDownLoadObserver);
        return taskId;
    }
}
