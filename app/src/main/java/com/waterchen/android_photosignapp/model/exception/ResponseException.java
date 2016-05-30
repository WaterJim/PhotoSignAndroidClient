package com.waterchen.android_photosignapp.model.exception;

/**
 * Created by 橘子哥 on 2016/5/22.
 */
public class ResponseException extends Exception {

    public static final int STATUS_CODE_SUCCESS = 0;

    public ResponseException() {
    }

    public ResponseException(String detailMessage) {
        super(detailMessage);
    }

    public ResponseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ResponseException(Throwable throwable) {
        super(throwable);
    }
}
