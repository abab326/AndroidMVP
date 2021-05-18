package com.liushuxue.corelibrary.http;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class  RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
      Request.Builder builder =  oldRequest.newBuilder();

        return chain.proceed(builder.build());
    }
}
