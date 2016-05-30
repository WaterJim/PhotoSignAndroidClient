package com.waterchen.android_photosignapp.presenter;

import com.waterchen.android_photosignapp.extra.mvp.BasePresenter;
import com.waterchen.android_photosignapp.model.RecordModel;
import com.waterchen.android_photosignapp.model.impl.RecordModelImpl;
import com.waterchen.android_photosignapp.presenter.listener.OnLoadTotalRecordListener;
import com.waterchen.android_photosignapp.ui.view.TotalRecordView;

/**
 * Created by 橘子哥 on 2016/5/28.
 */
public class TotalRecordPresenter extends BasePresenter<TotalRecordView> implements OnLoadTotalRecordListener {

    private RecordModel mRecordModel;

    public TotalRecordPresenter() {
        mRecordModel = new RecordModelImpl();
    }

    public void loadRecords(String account) {

    }

    @Override
    public void loadTotalRecordSuccess() {

    }

    @Override
    public void loadTotalRecordError() {

    }
}

