
package com.waterchen.android_photosignapp.extra.mvp;

/**
 * Description：Presenter
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
