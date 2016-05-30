package com.waterchen.android_photosignapp.model;

import android.content.Context;

import com.waterchen.android_photosignapp.presenter.listener.OnUpdateUserInfoListener;
import com.waterchen.android_photosignapp.presenter.listener.OnUserInfoLoadListener;
import com.waterchen.android_photosignapp.presenter.listener.OnUserLoginListener;
import com.waterchen.android_photosignapp.presenter.listener.OnUserRegisterListener;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public interface UserModel {

    void userLogin(String account, String password, OnUserLoginListener loginListener);

    void userRegister(String account, String passsword, String email, String phone, OnUserRegisterListener registerListener);

    void loadUserInfo(Context context, OnUserInfoLoadListener loadListener);

    void update(String account, String name, String tag, OnUpdateUserInfoListener updateUserInfoListener);
}
