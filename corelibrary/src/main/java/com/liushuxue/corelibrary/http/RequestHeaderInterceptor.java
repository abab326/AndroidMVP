package com.liushuxue.corelibrary.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求头统一处理 拦截器
 *
 */
public class RequestHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request.Builder builder = oldRequest.newBuilder();
        builder.addHeader("token", "");
        builder.addHeader("sign", "");
        return chain.proceed(builder.build());
    }
}
