package com.waterchen.android_photosignapp.model.handle.common;


import android.content.Context;

import com.waterchen.android_photosignapp.model.exception.ErrorMessageFactory;

import rx.Subscriber;

/**
 * Created by 橘子哥 on 2016/5/22.
 */
public class BaseSubscriber<T> extends Subscriber<T> {



    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }


}
