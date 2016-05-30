package com.waterchen.android_photosignapp.presenter;

import android.content.Context;

import com.waterchen.android_photosignapp.extra.ConstantValues;
import com.waterchen.android_photosignapp.extra.db.beans.UserBean;
import com.waterchen.android_photosignapp.extra.mvp.BasePresenter;
import com.waterchen.android_photosignapp.model.LessonModel;
import com.waterchen.android_photosignapp.model.UserModel;
import com.waterchen.android_photosignapp.model.entity.LessonInfoEntity;
import com.waterchen.android_photosignapp.model.impl.LessonModelImpl;
import com.waterchen.android_photosignapp.model.impl.UserModelImpl;
import com.waterchen.android_photosignapp.presenter.listener.OnLessonInfoListener;
import com.waterchen.android_photosignapp.presenter.listener.OnUserInfoLoadListener;
import com.waterchen.android_photosignapp.ui.view.MainView;

import net.grandcentrix.tray.AppPreferences;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public class MainPresenter extends BasePresenter<MainView> implements OnLessonInfoListener, OnUserInfoLoadListener {

    private LessonModel mLessonModel;
    private UserModel mUserModel;

    public MainPresenter() {
        mLessonModel = new LessonModelImpl();
        mUserModel = new UserModelImpl();
    }

    public void loadUserInfo(Context context) {
        mUserModel.loadUserInfo(context, this);
    }

    public void loadLessonList(String token) {
        mLessonModel.loadLseeonList(token, this);
    }

    @Override
    public void loadLessonListSuccess(List<LessonInfoEntity.LessonInfo> lessonList) {
        getMvpView().loadLessonListSuccess(lessonList);
    }

    @Override
    public void loadLessonListError() {
        getMvpView().loadLessonListError();
    }


    @Override
    public void loadUserInfoSuccess(UserBean userInfo) {
        getMvpView().loadUserInfoSuccess(userInfo);
    }

    @Override
    public void loadUserInfoError() {
        getMvpView().loadUserInfoError();
    }

    /**
     * 用户登出
     */
    public void logoutUser(Context context) {
        //将存储的数据全部置空
        AppPreferences appPreferences = new AppPreferences(context);
        appPreferences.put(ConstantValues.PREFERENCE_KEY_USER_ACCOUNT, ConstantValues.USER_ACCOUNT_NULL);
        appPreferences.put(ConstantValues.PREFERENCE_KEY_USER_PASSWORD, ConstantValues.USER_PASSWORD_NULL);
        appPreferences.put(ConstantValues.PREFERENCE_KEY_USER_NAME, ConstantValues.USER_NAME_NULL);
        appPreferences.put(ConstantValues.PREFERENCE_KEY_USER_TAG, ConstantValues.USER_TAG_NULL);
        appPreferences.put(ConstantValues.PREFERENCE_KEY_USER_EMAIL, ConstantValues.USER_EMAIL_NULL);
        appPreferences.put(ConstantValues.PREFERENCE_KEY_USER_PHONE, ConstantValues.USER_PHONE_NULL);
        getMvpView().afterLogout();
    }
}
