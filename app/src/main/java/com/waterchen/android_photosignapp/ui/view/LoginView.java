package com.waterchen.android_photosignapp.ui.view;

import com.waterchen.android_photosignapp.extra.mvp.MvpView;
import com.waterchen.android_photosignapp.model.entity.UserEnetity;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public interface LoginView extends MvpView {

    void loginSuccess(UserEnetity.User userInfo);

    void loginError();
}
