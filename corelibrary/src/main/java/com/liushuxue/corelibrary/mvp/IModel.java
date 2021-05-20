package com.liushuxue.corelibrary.mvp;

public interface IModel {
    // 清空所有请求
    void clearAllRequest();

    // 取消当前网络请求
    void cancelCurrentRequest();

    interface OnHttpRequestCallback<T> {

        void onSuccess(T t);

        void onError(String message);
    }
}
