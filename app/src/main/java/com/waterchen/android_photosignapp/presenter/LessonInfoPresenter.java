package com.waterchen.android_photosignapp.presenter;

import com.waterchen.android_photosignapp.extra.mvp.BasePresenter;
import com.waterchen.android_photosignapp.model.LessonModel;
import com.waterchen.android_photosignapp.model.RecordModel;
import com.waterchen.android_photosignapp.model.entity.StudentEntity;
import com.waterchen.android_photosignapp.model.impl.LessonModelImpl;
import com.waterchen.android_photosignapp.model.impl.RecordModelImpl;
import com.waterchen.android_photosignapp.presenter.listener.OnLoadStudentsListener;
import com.waterchen.android_photosignapp.presenter.listener.OnRecordCheckListener;
import com.waterchen.android_photosignapp.ui.view.LessonInfoView;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public class LessonInfoPresenter extends BasePresenter<LessonInfoView> implements OnLoadStudentsListener, OnRecordCheckListener {

    private LessonModel mLessonModel;
    private RecordModel mRecordModel;

    public LessonInfoPresenter() {
        mLessonModel = new LessonModelImpl();
        mRecordModel = new RecordModelImpl();
    }

    public void loadStudentList(String token, int classId) {
        mLessonModel.loadStudentList(token, classId, this);
    }


    /**
     * 检查是否已经存在当前签到记录并且提交成功
     *
     * @param classId
     * @param date
     * @return 若存在相应记录并且提交成功，返回true，否则，返回false
     */
    public void checkExistRecordAndUpload(int classId, String date) {
        mRecordModel.checkExistAndUpload(classId, date, this);
    }

    public void saveRecord(String account, String date, String path, int classId, String className, int photoCount) {
        mRecordModel.saveRecord(account, date, path, classId, className, photoCount);
    }

    @Override
    public void loadStudentsSuccesss(List<StudentEntity.LessonStudent> students) {
        getMvpView().loadStudentsSuccesss(students);
    }

    @Override
    public void loadStudentsError() {
        getMvpView().loadStudentsError();
    }

    @Override
    public void existRecord(int count) {
        getMvpView().ckeckRecordExist(false, count);
    }

    @Override
    public void existNullRecord() {
        getMvpView().ckeckRecordExist(true, 0);
    }

    public void destroyPresenter() {
        mRecordModel.releaseModel();
    }

}
