package com.meilancycling.mema.network;

import io.reactivex.disposables.Disposable;

/**
 * @author lion
 * 有界面的观察者
 */
public abstract class MyObserver<T> extends BaseObserver<T> {
    private Disposable d;

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onError(Throwable e) {
        if (d.isDisposed()) {
            d.dispose();
        }
        super.onError(e);
    }

    @Override
    public void onComplete() {
        if (d.isDisposed()) {
            d.dispose();
        }
        super.onComplete();
    }


    /**
     * 取消订阅
     */
    public void cancelRequest() {
        if (d != null && d.isDisposed()) {
            d.dispose();
        }
    }

}

