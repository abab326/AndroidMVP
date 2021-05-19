package com.liushuxue.corelibrary.mvp;

public interface IModel {
    // 清空所有请求
    void clearAllRequest();

    interface OnHttpRequestCallback<T> {

        void onSuccess(T t);

        void onError(String message);
    }
}
