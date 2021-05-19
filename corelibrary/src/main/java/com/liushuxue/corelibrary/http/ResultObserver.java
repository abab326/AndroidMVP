package com.liushuxue.corelibrary.http;

import android.accounts.NetworkErrorException;

import com.liushuxue.corelibrary.base.BaseResultBean;
import com.liushuxue.corelibrary.event.LoginEvent;

import org.greenrobot.eventbus.EventBus;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;
import retrofit2.HttpException;

public abstract class ResultObserver<T> extends DisposableObserver<BaseResultBean<T>> {

    @Override
    public void onNext(@NonNull BaseResultBean<T> tBaseResultBean) {
        if (tBaseResultBean.getCode() == 0) {
            // 业务正常返回数据
            onSuccess(tBaseResultBean.getData());
        } else if (tBaseResultBean.getCode() == -2) {
            // 业务正常返回数据
            EventBus.getDefault().post(new LoginEvent(false));
        } else {
            onFailed(tBaseResultBean.getMessage());
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        handleException(e);
    }


    @Override
    public void onComplete() {

    }

    //异常处理
    private void handleException(Throwable e) {
        String errorMessage;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorMessage = httpException.message();
        } else if (e instanceof NetworkErrorException
                || e instanceof SocketTimeoutException
                || e instanceof SocketException
                || e instanceof UnknownHostException
                || e instanceof TimeoutException) {
            errorMessage = "网络错误";
        } else {
            errorMessage = e.getMessage();
        }
        onFailed(errorMessage);
    }

    public abstract void onSuccess(T data);

    public abstract void onFailed(String errorMessage);
}
