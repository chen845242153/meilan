package com.meilancycling.mema.network.download;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * 文件观察者
 *
 * @author lion
 */
public class FileDownLoadObserver<T> implements Observer<T> {

    private FileDownloadCallback fileDownloadCallback;

    public FileDownLoadObserver(FileDownloadCallback fileDownloadCallback) {

        this.fileDownloadCallback = fileDownloadCallback;
    }

    @Override
    public void onNext(T t) {
        fileDownloadCallback.onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        fileDownloadCallback.onFail(e);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    /**
     * 将文件写入本地
     *
     * @param responseBody 请求结果全体
     * @param filePath     文件路径（文件路径+文件名+后缀） 例如xxx/xxx/xxx/test.txt
     * @return 写入完成的文件
     * @throws IOException IO异常
     */
    public File saveFile(ResponseBody responseBody, String filePath) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = responseBody.byteStream();
            final long total = responseBody.contentLength();
            long sum = 0;
            File file = new File(filePath);
            if (file != null)
                file.createNewFile();
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(() -> fileDownloadCallback.onProgress(finalSum, total));
            }
            fos.flush();
            return file;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

