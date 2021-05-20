package com.liushuxue.corelibrary.http;

import android.accounts.NetworkErrorException;
import android.widget.Toast;

import com.liushuxue.corelibrary.base.BaseApplication;
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
    // 是否自动显示错误信息
    private boolean autoShowErrorMsg;

    public ResultObserver(boolean autoShowErrorMsg) {
        this.autoShowErrorMsg = autoShowErrorMsg;
    }

    @Override
    public void onNext(@NonNull BaseResultBean<T> tBaseResultBean) {
        if (tBaseResultBean.getCode() == 0) {
            // 业务正常返回数据
            onSuccess(tBaseResultBean.getData());
        } else if (tBaseResultBean.getCode() == -2) {
            // 业务正常返回数据
            EventBus.getDefault().post(new LoginEvent(false));
        } else {
            showErrorMsg(tBaseResultBean.getMessage());
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
        showErrorMsg(errorMessage);
        onFailed(errorMessage);
    }

    public void showErrorMsg(String message) {
        if (autoShowErrorMsg)
            Toast.makeText(BaseApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public abstract void onSuccess(T data);

    public abstract void onFailed(String errorMessage);
}
