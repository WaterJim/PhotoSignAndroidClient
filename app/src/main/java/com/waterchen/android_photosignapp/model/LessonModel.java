package com.waterchen.android_photosignapp.model;

import com.waterchen.android_photosignapp.model.entity.LessonInfoEntity;
import com.waterchen.android_photosignapp.model.entity.StudentEntity;
import com.waterchen.android_photosignapp.presenter.listener.OnAddStudentListener;
import com.waterchen.android_photosignapp.presenter.listener.OnLessonCreateListener;
import com.waterchen.android_photosignapp.presenter.listener.OnLessonInfoListener;
import com.waterchen.android_photosignapp.presenter.listener.OnLoadStudentsListener;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public interface LessonModel {

    void loadLseeonList(String token, OnLessonInfoListener lessonInfoListener);

    void loadStudentList(String token, int classId, OnLoadStudentsListener studentsListener);

    void createLesson(String token,
                      String lessonName,
                      String lessonWeek,
                      OnLessonCreateListener createListener);

    void addStudentToLesson(String token,
                            String stuNumber,
                            int classId,
                            OnAddStudentListener addStudentListener);

}
