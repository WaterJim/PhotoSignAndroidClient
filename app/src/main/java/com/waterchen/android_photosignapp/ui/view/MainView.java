package com.waterchen.android_photosignapp.ui.view;

import com.waterchen.android_photosignapp.extra.db.beans.UserBean;
import com.waterchen.android_photosignapp.extra.mvp.MvpView;
import com.waterchen.android_photosignapp.model.entity.LessonInfoEntity;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public interface MainView extends MvpView {

    void loadLessonListSuccess(List<LessonInfoEntity.LessonInfo> lessonList);

    void loadLessonListError();

    void loadUserInfoSuccess(UserBean userInfo);

    void loadUserInfoError();

    void afterLogout();

}
