package com.waterchen.android_photosignapp.presenter.listener;

import com.waterchen.android_photosignapp.model.entity.RecordEntity;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/28.
 */
public interface OnLoadLessonRecordListener {

    void loadLessonRecordSuccess(List<RecordEntity.Record> recordList);

    void loadLessonRecordError();
}

