package com.waterchen.android_photosignapp.presenter;

import com.waterchen.android_photosignapp.extra.mvp.BasePresenter;
import com.waterchen.android_photosignapp.model.RecordModel;
import com.waterchen.android_photosignapp.model.entity.RecordEntity;
import com.waterchen.android_photosignapp.model.impl.RecordModelImpl;
import com.waterchen.android_photosignapp.presenter.listener.OnLoadLessonRecordListener;
import com.waterchen.android_photosignapp.ui.view.LessonRecordView;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/28.
 */
public class LessonRecordPresenter extends BasePresenter<LessonRecordView> implements OnLoadLessonRecordListener {

    private RecordModel mRecordModel;

    public LessonRecordPresenter() {
        mRecordModel = new RecordModelImpl();
    }

    public void loadLessonRecord(String account, int classId) {
        mRecordModel.loadLessonRecord(account, classId, this);
    }

    @Override
    public void loadLessonRecordSuccess(List<RecordEntity.Record> recordList) {
        //将数据进行处理
        int recordCount = recordList.size();
        if (recordCount > 0) {
            getMvpView().loadLessonRecordSuccess();
            //先处理最近一次签到数据
            getMvpView().loadPieChartData(recordList.get(0).count, recordList.get(0).totalcount);
            //处理所有数据
            getMvpView().loadBarChartData(recordList);
        } else {
            getMvpView().loadLessonRecordError();
        }


    }

    @Override
    public void loadLessonRecordError() {
        getMvpView().loadLessonRecordError();
    }

    public void releasePresenter() {
        if (mRecordModel != null) {
            mRecordModel.releaseModel();
        }
    }
}
