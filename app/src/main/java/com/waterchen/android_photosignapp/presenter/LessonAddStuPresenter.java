package com.waterchen.android_photosignapp.presenter;

import com.waterchen.android_photosignapp.extra.mvp.BasePresenter;
import com.waterchen.android_photosignapp.model.LessonModel;
import com.waterchen.android_photosignapp.model.impl.LessonModelImpl;
import com.waterchen.android_photosignapp.presenter.listener.OnAddStudentListener;
import com.waterchen.android_photosignapp.ui.view.LessonAddStudentView;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public class LessonAddStuPresenter extends BasePresenter<LessonAddStudentView> implements OnAddStudentListener {

    private LessonModel mLessonModel;

    public LessonAddStuPresenter() {
        mLessonModel = new LessonModelImpl();
    }

    public void addStudent(String token, String stuNumber, int classId) {
        mLessonModel.addStudentToLesson(token, stuNumber, classId, this);
    }

    @Override
    public void addStundentSuccess() {
        getMvpView().addStudentSuccess();
    }

    @Override
    public void addStudentError() {
        getMvpView().addStudentError();
    }
}
