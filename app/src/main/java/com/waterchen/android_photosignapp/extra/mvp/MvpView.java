
package com.waterchen.android_photosignapp.extra.mvp;

/**
 * Description：MvpView
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
public interface MvpView {
    /**
     * 发生错误
     *
     * @param e e
     */
    void onFailure(Throwable e);
}
