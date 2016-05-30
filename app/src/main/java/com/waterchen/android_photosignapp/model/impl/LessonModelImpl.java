package com.waterchen.android_photosignapp.model.impl;

import com.orhanobut.logger.Logger;
import com.waterchen.android_photosignapp.extra.network.EasyRetrofit;
import com.waterchen.android_photosignapp.model.LessonModel;
import com.waterchen.android_photosignapp.model.entity.DefaultEntity;
import com.waterchen.android_photosignapp.model.entity.LessonInfoEntity;
import com.waterchen.android_photosignapp.model.entity.StudentEntity;
import com.waterchen.android_photosignapp.presenter.listener.OnAddStudentListener;
import com.waterchen.android_photosignapp.presenter.listener.OnLessonCreateListener;
import com.waterchen.android_photosignapp.presenter.listener.OnLessonInfoListener;
import com.waterchen.android_photosignapp.presenter.listener.OnLoadStudentsListener;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public class LessonModelImpl implements LessonModel {

    @Override
    public void loadLseeonList(String token, final OnLessonInfoListener lessonInfoListener) {
        EasyRetrofit.getInstance().getAPIService()
                .loadLesson(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<LessonInfoEntity.LessonInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("error : " + e.getMessage());
                        lessonInfoListener.loadLessonListError();
                    }

                    @Override
                    public void onNext(List<LessonInfoEntity.LessonInfo> lessonInfos) {
                        lessonInfoListener.loadLessonListSuccess(lessonInfos);
                    }
                });
    }


    @Override
    public void loadStudentList(String token, int classId, final OnLoadStudentsListener loadStudentsListener) {
        EasyRetrofit.getInstance().getAPIService()
                .loadStudentList(token, classId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<StudentEntity.LessonStudent>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        loadStudentsListener.loadStudentsError();
                    }

                    @Override
                    public void onNext(List<StudentEntity.LessonStudent> lessonStudents) {
                        loadStudentsListener.loadStudentsSuccesss(lessonStudents);
                    }
                });
    }

    @Override
    public void createLesson(String token, String lessonName, String lessonWeek, final OnLessonCreateListener createListener) {
        EasyRetrofit.getInstance().getAPIService()
                .createLesson(token, lessonName, lessonWeek)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<LessonInfoEntity.LessonInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        createListener.createLessonError();
                    }

                    @Override
                    public void onNext(List<LessonInfoEntity.LessonInfo> lessonInfos) {
                        createListener.createLessonSuccess();
                    }
                });
    }

    @Override
    public void addStudentToLesson(String token, String stuNumber, int classId, final OnAddStudentListener addStudentListener) {
        EasyRetrofit.getInstance().getAPIService()
                .addStidentToLesson(token, stuNumber, classId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DefaultEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        addStudentListener.addStudentError();
                    }

                    @Override
                    public void onNext(DefaultEntity defaultEntity) {
                        addStudentListener.addStundentSuccess();
                    }
                });
//                .subscribe(new Observer<List<StudentEntity.LessonStudent>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Logger.e(e.getMessage());
//                        addStudentListener.addStudentError();
//                    }
//
//                    @Override
//                    public void onNext(List<StudentEntity.LessonStudent> lessonStudents) {
//                        addStudentListener.addStundentSuccess();
//                    }
//                });
    }

}
