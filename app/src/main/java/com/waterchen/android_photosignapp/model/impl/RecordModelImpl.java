package com.waterchen.android_photosignapp.model.impl;

import com.orhanobut.logger.Logger;
import com.waterchen.android_photosignapp.extra.db.beans.OfflineBean;
import com.waterchen.android_photosignapp.extra.network.EasyRetrofit;
import com.waterchen.android_photosignapp.extra.tool.StorageUtil;
import com.waterchen.android_photosignapp.model.RecordModel;
import com.waterchen.android_photosignapp.model.entity.DefaultEntity;
import com.waterchen.android_photosignapp.model.entity.RecordEntity;
import com.waterchen.android_photosignapp.presenter.listener.OnLoadLessonRecordListener;
import com.waterchen.android_photosignapp.presenter.listener.OnLoadRecordsListener;
import com.waterchen.android_photosignapp.presenter.listener.OnLoadTotalRecordListener;
import com.waterchen.android_photosignapp.presenter.listener.OnRecordCheckListener;
import com.waterchen.android_photosignapp.presenter.listener.OnRecordUploadListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public class RecordModelImpl implements RecordModel {

    private Realm mRealm;

    public RecordModelImpl() {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void loadRecords(String token, OnLoadRecordsListener loadRecordsListener) {
        //查询数据库获取记录列表
        RealmQuery<OfflineBean> query = mRealm.where(OfflineBean.class);
        query.equalTo("account", token);
        query.equalTo("upload", false);
        RealmResults<OfflineBean> result = query.findAll();
        loadRecordsListener.loadRecordSuccess(result.subList(0, result.size()));
    }

    @Override
    public void uploadRecord(String token, int classId, String students, final OnRecordUploadListener uploadListener) {
        EasyRetrofit.getInstance().getAPIService()
                .uploadRecord(token, classId, students)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DefaultEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        uploadListener.uploadRecordError();
                    }

                    @Override
                    public void onNext(DefaultEntity response) {
                        uploadListener.uploadRecordSuccesss();
                    }
                });
    }

    /**
     * 更新记录状态为以上传
     *
     */
    @Override
    public void updateRecord(int classId, String date) {
        RealmQuery<OfflineBean> query = mRealm.where(OfflineBean.class);
        query.equalTo("date", date);
        query.equalTo("classid", classId);
        final OfflineBean result = query.findFirst();
        if(result != null){
            mRealm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {
                    result.upload = true;
                }
            });
        }
    }

    @Override
    public void checkExistAndUpload(int classId, String date, OnRecordCheckListener checkListener) {
        RealmQuery<OfflineBean> query = mRealm.where(OfflineBean.class);
        query.equalTo("date", date);
        query.equalTo("classid", classId);
        OfflineBean result = query.findFirst();
        if (result != null) {
            if (result.upload) {
                checkListener.existNullRecord();
            } else {
                checkListener.existRecord(result.count);
            }
        } else {
            checkListener.existRecord(0);
        }
    }

    @Override
    public void saveRecord(final String account, String date, String path, final int classId, final String className, final int photoCount) {
        //查询是否存在记录
        RealmQuery<OfflineBean> query = mRealm.where(OfflineBean.class);
        query.equalTo("date", date);
        query.equalTo("classid", classId);
        final OfflineBean record = query.findFirst();

        mRealm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                if (record != null) {
                    record.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    record.account = account;
                    record.path = StorageUtil.getRootFilePath(classId);
                    record.classid = classId;
                    record.classname = className;
                    record.count += photoCount;
                    record.upload = false;
                } else {
                    OfflineBean newRecord = mRealm.createObject(OfflineBean.class);
                    newRecord.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    newRecord.account = account;
                    newRecord.path = StorageUtil.getRootFilePath(classId);
                    newRecord.classid = classId;
                    newRecord.classname = className;
                    newRecord.count = photoCount;
                    newRecord.upload = false;
                }
            }
        });
    }

    @Override
    public void loadTotalRecord(String account, OnLoadTotalRecordListener totalRecordListener) {

    }

    @Override
    public void loadLessonRecord(String account, int classId, final OnLoadLessonRecordListener lessonRecordListener) {
        EasyRetrofit.getInstance().getAPIService()
                .loadLessonRecord(account,classId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<RecordEntity.Record>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        lessonRecordListener.loadLessonRecordError();
                    }

                    @Override
                    public void onNext(List<RecordEntity.Record> records) {
                        lessonRecordListener.loadLessonRecordSuccess(records);
                    }
                });
    }


    @Override
    public void releaseModel() {
        if (!mRealm.isClosed()) {
            mRealm.close();
        }
    }
}
