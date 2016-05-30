package com.waterchen.android_photosignapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.extra.ConstantValues;
import com.waterchen.android_photosignapp.extra.tool.ToastUtil;
import com.waterchen.android_photosignapp.presenter.IndexPresenter;
import com.waterchen.android_photosignapp.ui.common.BaseActivity;
import com.waterchen.android_photosignapp.ui.view.IndexView;

import net.grandcentrix.tray.AppPreferences;

/**
 * Created by 橘子哥 on 2016/5/22.
 */
public class IndexActivity extends BaseActivity implements IndexView {

    private final int MES_INTRODUCE_PAGE = 0x00;
    private final int MES_MAIN_PAGE = 0x01;
    private final int MES_ERROR_FINISH = 0x02;
    private IndexPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        mPresenter = new IndexPresenter();
        mPresenter.attachView(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.initAppFromServer();
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void finishLoadAppInfo() {
        AppPreferences appPreferences = new AppPreferences(this);
        int times = appPreferences.getInt(ConstantValues.PREFERENCE_KEY_APP_LAUCH, ConstantValues.APP_LAUCH_DEFAULT);
        if (times <= 0) {
            //第一次启动APP
            mHandler.sendEmptyMessageDelayed(MES_INTRODUCE_PAGE, 3000);
        } else {
            mHandler.sendEmptyMessageDelayed(MES_MAIN_PAGE, 3000);
        }
    }

    @Override
    public void loadAppInfoError() {
        ToastUtil.showToast(this, getString(R.string.index_network_error));
        mHandler.sendEmptyMessageDelayed(MES_ERROR_FINISH, 3000);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int mes = msg.what;
            if (mes == MES_INTRODUCE_PAGE) {
                startActivity(new Intent(IndexActivity.this, IntroduceActivity.class));
            } else if (mes == MES_MAIN_PAGE) {
                startActivity(new Intent(IndexActivity.this, MainActivity.class));
            } else if (mes == MES_ERROR_FINISH) {

            }
            finish();
        }
    };
}
