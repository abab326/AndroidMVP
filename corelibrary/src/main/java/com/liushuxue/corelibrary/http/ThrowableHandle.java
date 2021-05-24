package com.liushuxue.corelibrary.http;

import android.accounts.NetworkErrorException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.HttpException;

public class ThrowableHandle {

    //异常处理
    public static String handleException(Throwable e) {
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
        return errorMessage;
    }

}
