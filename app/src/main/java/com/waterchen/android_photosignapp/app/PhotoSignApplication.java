package com.waterchen.android_photosignapp.app;

import android.app.Application;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;
import com.waterchen.android_photosignapp.BuildConfig;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by 橘子哥 on 2016/5/19.
 */
public class PhotoSignApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this).build());

        Logger.initialize(Settings.getInstance()
                .isShowMethodLink(true)
                .isShowThreadInfo(false)
                .setMethodOffset(0)
                .setLogPriority(BuildConfig.DEBUG ? Log.VERBOSE : Log.ASSERT));

    }
}
