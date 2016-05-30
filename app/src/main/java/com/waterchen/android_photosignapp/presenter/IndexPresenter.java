package com.waterchen.android_photosignapp.presenter;

import com.waterchen.android_photosignapp.extra.mvp.BasePresenter;
import com.waterchen.android_photosignapp.model.AppInfoModel;
import com.waterchen.android_photosignapp.model.impl.AppInfoModelImpl;
import com.waterchen.android_photosignapp.presenter.listener.OnAppInfoListener;
import com.waterchen.android_photosignapp.ui.view.IndexView;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class IndexPresenter extends BasePresenter<IndexView> implements OnAppInfoListener {

    private AppInfoModel mAppInfoModel;

    public IndexPresenter() {
        mAppInfoModel = new AppInfoModelImpl();
    }


    public void initAppFromServer() {
        mAppInfoModel.getLeastAppInfo(this);
    }

    @Override
    public void onSuccess() {
        getMvpView().finishLoadAppInfo();
    }

    @Override
    public void onError() {
        getMvpView().loadAppInfoError();
    }
}
