package com.liushuxue.corelibrary.http;

import com.liushuxue.corelibrary.constant.Config;

import java.io.IOException;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 动态配置 url 拦截器
 */
public class RequestUrlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request.Builder builder = oldRequest.newBuilder();
        HttpUrl oldUrl = oldRequest.url();
        List<String> headers = oldRequest.headers(Config.HttpConfig.URL_NAME);
        if (!headers.isEmpty()) {
            //如果存在自定义 url 请求头 则先删除请求头中的配置
            builder.removeHeader(Config.HttpConfig.URL_NAME);
            String urlName = headers.get(0);
            HttpUrl newBaseUrl = null;
            // 根据请求头配置 设置新的请求地址
            if (Config.HttpConfig.URL_NAME_HOME.equals(urlName)) {
                newBaseUrl = HttpUrl.parse(Config.HttpConfig.BASE_URL_HOME);
            }
            if (Config.HttpConfig.URL_NAME_FILE.equals(urlName)) {
                newBaseUrl = HttpUrl.parse(Config.HttpConfig.BASE_URL_HOME);
            }
            if (null != newBaseUrl) {
                HttpUrl newHttpUrl = oldUrl.newBuilder()
                        .scheme(newBaseUrl.scheme())
                        .host(newBaseUrl.host())
                        .port(newBaseUrl.port())
                        .build();
                builder.url(newHttpUrl);
            }
        }

        return chain.proceed(builder.build());
    }
}
