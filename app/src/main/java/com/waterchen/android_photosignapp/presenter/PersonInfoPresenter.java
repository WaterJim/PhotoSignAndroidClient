package com.waterchen.android_photosignapp.presenter;

import android.content.Context;

import com.waterchen.android_photosignapp.extra.ConstantValues;
import com.waterchen.android_photosignapp.extra.mvp.BasePresenter;
import com.waterchen.android_photosignapp.model.UserModel;
import com.waterchen.android_photosignapp.model.entity.UserEnetity;
import com.waterchen.android_photosignapp.model.impl.UserModelImpl;
import com.waterchen.android_photosignapp.presenter.listener.OnUpdateUserInfoListener;
import com.waterchen.android_photosignapp.ui.view.PersonInfoView;

import net.grandcentrix.tray.AppPreferences;

/**
 * Created by 橘子哥 on 2016/5/28.
 */
public class PersonInfoPresenter extends BasePresenter<PersonInfoView> implements OnUpdateUserInfoListener {

    private UserModel mUserModel;

    public PersonInfoPresenter() {
        mUserModel = new UserModelImpl();
    }

    public void updateUserInfo(String token, String newName, String newTag) {
        mUserModel.update(token, newName, newTag, this);
    }

    @Override
    public void updateSuccess(UserEnetity.User userInfo) {
        //重新存储用户数据
        getMvpView().updateSuccess(userInfo);
    }

    @Override
    public void updateError() {
        getMvpView().updateError();
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
}
