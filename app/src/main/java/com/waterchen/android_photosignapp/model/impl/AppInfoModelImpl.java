package com.waterchen.android_photosignapp.model.impl;

import com.orhanobut.logger.Logger;
import com.waterchen.android_photosignapp.extra.network.EasyRetrofit;
import com.waterchen.android_photosignapp.model.AppInfoModel;
import com.waterchen.android_photosignapp.model.entity.AppInfoEntity;
import com.waterchen.android_photosignapp.presenter.listener.OnAppInfoListener;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by 橘子哥 on 2016/5/22.
 */
public class AppInfoModelImpl implements AppInfoModel {


    public void getLeastAppInfo(final OnAppInfoListener listener) {

        EasyRetrofit.getInstance().getAPIService()
                .getAppInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AppInfoEntity.AppInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        listener.onError();
                    }

                    @Override
                    public void onNext(AppInfoEntity.AppInfo appInfo) {
                        listener.onSuccess();
                    }
                });
    }
}
