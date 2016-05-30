package com.waterchen.android_photosignapp.ui.view;

import com.waterchen.android_photosignapp.extra.mvp.MvpView;
import com.waterchen.android_photosignapp.model.entity.StudentEntity;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public interface LessonInfoView extends MvpView {

    void loadStudentsSuccesss(List<StudentEntity.LessonStudent> students);

    void loadStudentsError();

    void ckeckRecordExist(boolean existAndUpload, int count);
}
