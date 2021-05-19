package com.liushuxue.corelibrary.mvp;

import java.lang.ref.WeakReference;

public interface IPresenter<V extends IView> {


    void attachView(V view);

    void destroyed();
}
