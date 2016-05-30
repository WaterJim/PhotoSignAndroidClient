package com.waterchen.android_photosignapp.ui.view;

import com.waterchen.android_photosignapp.extra.db.beans.OfflineBean;
import com.waterchen.android_photosignapp.extra.mvp.MvpView;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public interface OfflineView extends MvpView {

    void incrementProgress(int process);

    void showProgressDialog(int count);

    void loadRecordListSuccess(List<OfflineBean> recordList);

    void loadRecordListError();

    void uploadRecordSuccess();

    void uploadRecordError();
}
