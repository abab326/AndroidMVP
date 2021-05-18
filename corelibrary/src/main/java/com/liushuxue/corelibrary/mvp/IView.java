package com.liushuxue.corelibrary.mvp;

// 通用View 封装
public interface IView {
    void showLoading();

    void showLoading(String message);

    void closeLoading();

    void onError();
}
