package com.waterchen.android_photosignapp.presenter;

import com.waterchen.android_photosignapp.extra.mvp.BasePresenter;
import com.waterchen.android_photosignapp.model.LessonModel;
import com.waterchen.android_photosignapp.model.impl.LessonModelImpl;
import com.waterchen.android_photosignapp.presenter.listener.OnLessonCreateListener;
import com.waterchen.android_photosignapp.ui.view.LessonCreateView;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public class LessonCreatePresenter extends BasePresenter<LessonCreateView> implements OnLessonCreateListener {

    private LessonModel mLessonModel;

    public LessonCreatePresenter(){
        mLessonModel = new LessonModelImpl();
    }

    public void createLesson(String token,String lessonName, String lessonWeek){
        mLessonModel.createLesson(token,lessonName,lessonWeek,this);
    }

    @Override
    public void createLessonSuccess() {
        getMvpView().createLessonSuccess();
    }

    @Override
    public void createLessonError() {
        getMvpView().createLessonError();
    }
}
