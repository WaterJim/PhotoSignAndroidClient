package com.waterchen.android_photosignapp.extra.tool;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public class ToastUtil {

    public static void showToast(Context context, String content) {
        if (content != null) {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToast(Context context, int resString) {
        showToast(context, context.getString(resString));

    }
}
