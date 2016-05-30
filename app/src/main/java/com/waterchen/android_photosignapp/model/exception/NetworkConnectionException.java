package com.waterchen.android_photosignapp.model.exception;

/**
 * Created by 橘子哥 on 2016/5/22.
 */
public class NetworkConnectionException extends Exception {

    private final String MSG = "NetworkConnectionException";

    public NetworkConnectionException() {
    }

    public NetworkConnectionException(String detailMessage) {
        super(detailMessage);
    }

    public NetworkConnectionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NetworkConnectionException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getMessage() {
        return MSG;
    }
}
