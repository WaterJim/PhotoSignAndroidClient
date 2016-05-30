package com.waterchen.android_photosignapp.presenter.listener;

import com.waterchen.android_photosignapp.model.entity.UserEnetity;

/**
 * Created by 橘子哥 on 2016/5/28.
 */
public interface OnUpdateUserInfoListener {

    void updateSuccess(UserEnetity.User userInfo);

    void updateError();
}
