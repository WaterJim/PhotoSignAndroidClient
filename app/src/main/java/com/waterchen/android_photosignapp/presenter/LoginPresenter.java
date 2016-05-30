package com.waterchen.android_photosignapp.presenter;

import android.content.Context;

import com.waterchen.android_photosignapp.extra.ConstantValues;
import com.waterchen.android_photosignapp.extra.mvp.BasePresenter;
import com.waterchen.android_photosignapp.model.UserModel;
import com.waterchen.android_photosignapp.model.entity.UserEnetity;
import com.waterchen.android_photosignapp.model.impl.UserModelImpl;
import com.waterchen.android_photosignapp.presenter.listener.OnUserLoginListener;
import com.waterchen.android_photosignapp.ui.view.LoginView;

import net.grandcentrix.tray.AppPreferences;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public class LoginPresenter extends BasePresenter<LoginView> implements OnUserLoginListener {

    private UserModel mUserModel;

    public LoginPresenter() {
        mUserModel = new UserModelImpl();
    }

    public void login(String account, String password) {
        mUserModel.userLogin(account, password, this);
    }

    public void saveUserInfo(Context context, UserEnetity.User userInfo) {
        if (userInfo != null) {
            AppPreferences appPreferences = new AppPreferences(context);
            appPreferences.put(ConstantValues.PREFERENCE_KEY_USER_ACCOUNT, userInfo.account);
            appPreferences.put(ConstantValues.PREFERENCE_KEY_USER_PASSWORD, userInfo.password);
            appPreferences.put(ConstantValues.PREFERENCE_KEY_USER_NAME, userInfo.nickname);
            appPreferences.put(ConstantValues.PREFERENCE_KEY_USER_TAG, userInfo.tag);
            appPreferences.put(ConstantValues.PREFERENCE_KEY_USER_EMAIL, userInfo.email);
            appPreferences.put(ConstantValues.PREFERENCE_KEY_USER_PHONE, userInfo.phone);
            appPreferences.put(ConstantValues.PREFERENCE_KEY_LOGIN_FLAG, true);
        }
    }


    @Override
    public void userLoginSuccess(UserEnetity.User userInfo) {
        //保存已经登录的账号信息
        getMvpView().loginSuccess(userInfo);
    }

    @Override
    public void userLoginError() {
        getMvpView().loginError();
    }
}
