package com.waterchen.android_photosignapp.model.exception;

import android.content.Context;
import android.content.ServiceConnection;

import com.orhanobut.logger.Logger;

/**
 * Created by 橘子哥 on 2016/5/22.
 * 根据Exception的类型返回错误信息
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {
    }

    public static String create(Context context, Exception exception) {
        if (exception.getMessage() != null &&
                !exception.getMessage().isEmpty()) {
            Logger.e(exception.getMessage());
        }

        String errorMsg = context.getString(0);
        if (exception instanceof ServiceConnection) {

        } else if (exception instanceof UserExistException) {

        } else if (exception instanceof UserNotFoundException) {

        }

        return errorMsg;
    }
}
