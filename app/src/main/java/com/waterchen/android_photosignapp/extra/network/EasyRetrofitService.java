package com.waterchen.android_photosignapp.extra.network;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.waterchen.android_photosignapp.model.entity.AppInfoEntity;
import com.waterchen.android_photosignapp.model.entity.BaseResponse;
import com.waterchen.android_photosignapp.model.entity.DefaultEntity;
import com.waterchen.android_photosignapp.model.entity.LessonInfoEntity;
import com.waterchen.android_photosignapp.model.entity.RecordEntity;
import com.waterchen.android_photosignapp.model.entity.StudentEntity;
import com.waterchen.android_photosignapp.model.entity.UserEnetity;
import com.waterchen.android_photosignapp.model.exception.NetworkConnectionException;
import com.waterchen.android_photosignapp.model.exception.ResponseException;
import com.waterchen.android_photosignapp.model.exception.ServerException;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 橘子哥 on 2016/5/22.
 * 定义与Server通讯的API接口
 */
public class EasyRetrofitService {

    private APIService mAPIService;

    public EasyRetrofitService(APIService service) {
        this.mAPIService = service;
    }

    public Observable<AppInfoEntity.AppInfo> getAppInfo() {
        return extractData(mAPIService.getLeastAppInfo());
    }

    public Observable<UserEnetity.User> userLogin(String account, String password) {
        return extractData(mAPIService.userLogin(account, password));
    }

    public Observable<DefaultEntity> userRegiste(String account, String passsword, String email, String phone) {
        return extractDefaultData(mAPIService.userRegister(account, passsword, email, phone));
    }

    public Observable<UserEnetity.User> userUpdate(String account, String name, String tag) {
        return extractData(mAPIService.userUpdate(account, name, tag));
    }


    public Observable<List<LessonInfoEntity.LessonInfo>> loadLesson(String token) {
        return extractData(mAPIService.getLeastClassList(token));
    }

    public Observable<List<LessonInfoEntity.LessonInfo>> createLesson(String token, String lessonName, String lessonWeek) {
        return extractData(mAPIService.doCreateLesson(token, lessonName, lessonWeek));
    }

    public Observable<List<StudentEntity.LessonStudent>> loadStudentList(String token, int classId) {
        return extractData(mAPIService.doGetStudentList(token, classId));
    }

    public Observable<DefaultEntity> addStidentToLesson(String token, String stuNumber, int classId) {
        return extractDefaultData(mAPIService.doAddStudent(token, stuNumber, classId));
    }

    public Observable<DefaultEntity> uploadRecord(String account, int classid, String students) {
        return extractDefaultData(mAPIService.uploadRecord(account, classid, students));
    }

    public Observable<List<RecordEntity.Record>> loadLessonRecord(String account, int classId) {
        return extractData(mAPIService.loadLessonRecord(classId));
    }


    private <T, K extends BaseResponse<T>> Observable<T> extractData(Observable<K> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<K, Observable<T>>() {
                    @Override
                    public Observable<T> call(K response) {
                        if (response == null) {
                            return Observable.error(new NetworkConnectionException());
                        } else if (response.statuscode == ResponseException.STATUS_CODE_SUCCESS) {
                            return Observable.just(response.data);
                        } else {
                            return Observable.error(new ServerException());
                        }
                    }
                });

    }

    private Observable<DefaultEntity> extractDefaultData(Observable<DefaultEntity> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<DefaultEntity, Observable<DefaultEntity>>() {
                    @Override
                    public Observable<DefaultEntity> call(DefaultEntity response) {
                        if (response == null) {
                            return Observable.error(new NetworkConnectionException());
                        } else if (response.statuscode == ResponseException.STATUS_CODE_SUCCESS) {
                            return Observable.just(response);
                        } else {
                            return Observable.error(new ServerException());
                        }
                    }
                });
    }


}
