package com.waterchen.android_photosignapp.presenter.listener;

import com.waterchen.android_photosignapp.extra.db.beans.OfflineBean;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/25.
 */
public interface OnLoadRecordsListener {

    void loadRecordSuccess(List<OfflineBean> records);

    void loadRecordError();
}
