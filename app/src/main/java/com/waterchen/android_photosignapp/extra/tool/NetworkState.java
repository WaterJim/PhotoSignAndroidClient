package com.waterchen.android_photosignapp.extra.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by 橘子哥 on 2016/3/8.
 * 网络状态检查工具类
 */
public class NetworkState {

    public static final int NETTYPE_NULL = 0x00;
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static int getNetworkType(Context context) {
        int type = NETTYPE_NULL;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String extraInfo = networkInfo.getExtraInfo();
                if (extraInfo != null && !extraInfo.isEmpty()) {
                    if (extraInfo.toLowerCase().equals("cmnet")) {
                        type = NETTYPE_CMNET;
                    } else {
                        type = NETTYPE_CMWAP;
                    }
                }
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                type = NETTYPE_WIFI;
            }
        }
        return type;
    }

}
