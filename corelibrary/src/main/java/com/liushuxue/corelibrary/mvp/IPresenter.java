package com.liushuxue.corelibrary.mvp;

public interface IPresenter<V extends IView> {

    void attachView(V view);

    void destroyed();
}
