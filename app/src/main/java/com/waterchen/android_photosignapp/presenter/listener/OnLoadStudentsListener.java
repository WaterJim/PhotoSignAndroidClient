package com.waterchen.android_photosignapp.presenter.listener;

import com.waterchen.android_photosignapp.model.entity.StudentEntity;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public interface OnLoadStudentsListener {

    void loadStudentsSuccesss(List<StudentEntity.LessonStudent> students);

    void loadStudentsError();
}
