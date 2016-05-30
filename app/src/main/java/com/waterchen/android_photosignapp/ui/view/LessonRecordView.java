package com.waterchen.android_photosignapp.ui.view;

import com.waterchen.android_photosignapp.extra.mvp.MvpView;
import com.waterchen.android_photosignapp.model.entity.RecordEntity;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public interface LessonRecordView extends MvpView {

    void loadLessonRecordSuccess();

    void loadLessonRecordError();

    void loadPieChartData(int signCount, int totalCount);

    void loadBarChartData(List<RecordEntity.Record> recordList);

}
