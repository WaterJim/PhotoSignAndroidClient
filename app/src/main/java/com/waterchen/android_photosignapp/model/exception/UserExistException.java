package com.waterchen.android_photosignapp.model.exception;

/**
 * Created by 橘子哥 on 2016/5/22.
 * Exception : 指定的用户账号已经存在
 */
public class UserExistException extends Exception {
    public UserExistException() {
    }

    public UserExistException(String detailMessage) {
        super(detailMessage);
    }

    public UserExistException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UserExistException(Throwable throwable) {
        super(throwable);
    }
}
