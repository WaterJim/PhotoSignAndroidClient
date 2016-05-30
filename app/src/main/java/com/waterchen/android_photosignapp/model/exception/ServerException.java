package com.waterchen.android_photosignapp.model.exception;

/**
 * Created by 橘子哥 on 2016/5/22.
 * Exception : 服务器出现不知名错误
 */
public class ServerException extends Exception {

    private final String MSG = "ServerException";

    public ServerException() {
    }

    public ServerException(String detailMessage) {
        super(detailMessage);
    }

    public ServerException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ServerException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getMessage() {
        return MSG;
    }
}
