package com.meilancycling.mema.utils.RxSchedulers;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2021/1/5 9:45 AM
 */
public abstract class SimpleObserver<T> implements Observer<T> {
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
    }

    @Override
    public void onComplete() {
        if (d.isDisposed()) {
            d.dispose();
        }
    }

    /**
     * 时间回调
     *
     * @param t 回调对象
     */
    @Override
    public abstract void onNext(T t);

}
