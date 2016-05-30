package com.waterchen.android_photosignapp.presenter;

import com.waterchen.android_photosignapp.extra.mvp.BasePresenter;
import com.waterchen.android_photosignapp.model.UserModel;
import com.waterchen.android_photosignapp.model.impl.UserModelImpl;
import com.waterchen.android_photosignapp.presenter.listener.OnUserRegisterListener;
import com.waterchen.android_photosignapp.ui.view.RegisterView;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public class RegisterPresenter extends BasePresenter<RegisterView> implements OnUserRegisterListener {


    private UserModel mUserModel;

    public RegisterPresenter() {
        mUserModel = new UserModelImpl();
    }

    public void register(String account, String passsword, String email, String phone) {
        mUserModel.userRegister(account, passsword, email, phone, this);
    }

    @Override
    public void userRegisterSuccess() {
        getMvpView().registerSuccess();
    }

    @Override
    public void userRegisterError() {
        getMvpView().registerError();
    }
}
