package com.meilancycling.mema.network;


import android.content.Context;

import androidx.fragment.app.Fragment;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lion
 * 调度累
 */
public class RxHelper {

    public static <T> ObservableTransformer<T, T> observableToMain(final Context context) {

        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> observableToMain(final Fragment fragment) {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
