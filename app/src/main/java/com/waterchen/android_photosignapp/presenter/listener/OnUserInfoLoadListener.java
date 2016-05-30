package com.waterchen.android_photosignapp.presenter.listener;

import com.waterchen.android_photosignapp.extra.db.beans.UserBean;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public interface OnUserInfoLoadListener {

    void loadUserInfoSuccess(UserBean userInfo);

    void loadUserInfoError();
}
