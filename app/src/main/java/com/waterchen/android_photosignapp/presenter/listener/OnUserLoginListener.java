package com.waterchen.android_photosignapp.presenter.listener;

import com.waterchen.android_photosignapp.model.entity.UserEnetity;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public interface OnUserLoginListener {

    void userLoginSuccess(UserEnetity.User userInfo);

    void userLoginError();

}
