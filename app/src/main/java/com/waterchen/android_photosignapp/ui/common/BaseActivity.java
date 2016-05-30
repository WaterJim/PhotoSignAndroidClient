package com.waterchen.android_photosignapp.ui.common;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by 橘子哥 on 2016/5/22.
 */
public class BaseActivity extends AppCompatActivity {

    protected <T extends View> T findView(int id) {

        return (T) findViewById(id);
    }

}
