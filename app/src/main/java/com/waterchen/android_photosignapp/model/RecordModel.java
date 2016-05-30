package com.waterchen.android_photosignapp.model;

import com.waterchen.android_photosignapp.extra.db.beans.OfflineBean;
import com.waterchen.android_photosignapp.presenter.listener.OnLoadLessonRecordListener;
import com.waterchen.android_photosignapp.presenter.listener.OnLoadRecordsListener;
import com.waterchen.android_photosignapp.presenter.listener.OnLoadTotalRecordListener;
import com.waterchen.android_photosignapp.presenter.listener.OnRecordCheckListener;
import com.waterchen.android_photosignapp.presenter.listener.OnRecordUploadListener;

/**
 * Created by 橘子哥 on 2016/5/25.
 */
public interface RecordModel {

    void loadRecords(String token, OnLoadRecordsListener loadRecordsListener);

    void uploadRecord(String token, int classId, String students, OnRecordUploadListener uploadListener);

    void updateRecord(int classId, String date);

    void checkExistAndUpload(int classId, String date, OnRecordCheckListener checkListener);

    void saveRecord(String account, String date, String path, int classId, String className, int photoCount);

    void loadTotalRecord(String account, OnLoadTotalRecordListener totalRecordListener);

    void loadLessonRecord(String account, int classId, OnLoadLessonRecordListener lessonRecordListener);

    void releaseModel();
}
