package com.waterchen.android_photosignapp.model.exception;

/**
 * Created by 橘子哥 on 2016/5/22.
 * Exception : 不存在指定用户
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
    }

    public UserNotFoundException(String detailMessage) {
        super(detailMessage);
    }

    public UserNotFoundException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UserNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
