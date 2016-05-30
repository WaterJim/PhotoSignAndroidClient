package com.waterchen.android_photosignapp.model.impl;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.waterchen.android_photosignapp.extra.ConstantValues;
import com.waterchen.android_photosignapp.extra.db.beans.UserBean;
import com.waterchen.android_photosignapp.extra.network.EasyRetrofit;
import com.waterchen.android_photosignapp.model.UserModel;
import com.waterchen.android_photosignapp.model.entity.DefaultEntity;
import com.waterchen.android_photosignapp.model.entity.UserEnetity;
import com.waterchen.android_photosignapp.presenter.listener.OnUpdateUserInfoListener;
import com.waterchen.android_photosignapp.presenter.listener.OnUserInfoLoadListener;
import com.waterchen.android_photosignapp.presenter.listener.OnUserLoginListener;
import com.waterchen.android_photosignapp.presenter.listener.OnUserRegisterListener;

import net.grandcentrix.tray.AppPreferences;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public class UserModelImpl implements UserModel {
    @Override
    public void userLogin(String account, String password, final OnUserLoginListener loginListener) {
        EasyRetrofit.getInstance().getAPIService()
                .userLogin(account, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserEnetity.User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        loginListener.userLoginError();
                    }

                    @Override
                    public void onNext(UserEnetity.User user) {
                        loginListener.userLoginSuccess(user);
                    }
                });
    }

    @Override
    public void userRegister(String account, String passsword, String email, String phone, final OnUserRegisterListener registerListener) {
        EasyRetrofit.getInstance().getAPIService()
                .userRegiste(account, passsword, email, phone)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DefaultEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        registerListener.userRegisterError();
                    }

                    @Override
                    public void onNext(DefaultEntity response) {
                        registerListener.userRegisterSuccess();
                    }
                });
    }

    @Override
    public void loadUserInfo(Context context, OnUserInfoLoadListener loadListener) {
        //尝试获取已经登陆的账号信息
        AppPreferences appPreferences = new AppPreferences(context);
        String account = appPreferences.getString(ConstantValues.PREFERENCE_KEY_USER_ACCOUNT, ConstantValues.USER_ACCOUNT_NULL);
        if (account.equals(ConstantValues.USER_ACCOUNT_NULL)) {
            //不存在已经登陆的用户
            loadListener.loadUserInfoError();
        } else {
            UserBean userInfo = new UserBean();
            userInfo.account = account;
            userInfo.password = appPreferences.getString(ConstantValues.PREFERENCE_KEY_USER_PASSWORD, ConstantValues.USER_PASSWORD_NULL);
            userInfo.nickname = appPreferences.getString(ConstantValues.PREFERENCE_KEY_USER_NAME, ConstantValues.USER_NAME_NULL);
            userInfo.tag = appPreferences.getString(ConstantValues.PREFERENCE_KEY_USER_TAG, ConstantValues.USER_TAG_NULL);
            loadListener.loadUserInfoSuccess(userInfo);
        }
    }

    @Override
    public void update(String account, String name, String tag, final OnUpdateUserInfoListener updateUserInfoListener) {
        EasyRetrofit.getInstance().getAPIService()
                .userUpdate(account, name, tag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserEnetity.User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        updateUserInfoListener.updateError();
                    }

                    @Override
                    public void onNext(UserEnetity.User user) {
                        updateUserInfoListener.updateSuccess(user);
                    }
                });
    }
}
