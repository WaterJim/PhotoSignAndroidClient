package com.waterchen.android_photosignapp.ui.view;

import com.waterchen.android_photosignapp.extra.mvp.MvpView;

/**
 * Created by 橘子哥 on 2016/5/22.
 */
public interface IndexView extends MvpView {

    void finishLoadAppInfo();
    void loadAppInfoError();
}
