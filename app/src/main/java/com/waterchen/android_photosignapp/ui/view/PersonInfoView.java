package com.waterchen.android_photosignapp.ui.view;

import com.waterchen.android_photosignapp.extra.mvp.MvpView;
import com.waterchen.android_photosignapp.model.UserModel;
import com.waterchen.android_photosignapp.model.entity.UserEnetity;

/**
 * Created by 橘子哥 on 2016/5/28.
 */
public interface PersonInfoView extends MvpView {


    void updateSuccess(UserEnetity.User userInfo);

    void updateError();
}
