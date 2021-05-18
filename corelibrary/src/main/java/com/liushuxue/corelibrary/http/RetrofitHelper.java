package com.liushuxue.corelibrary.http;

import com.liushuxue.corelibrary.constant.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static RetrofitHelper instance;
    private Retrofit retrofit;

    private RetrofitHelper() {
        createRetrofit();
    }

    public static RetrofitHelper getInstance() {
        synchronized (RetrofitHelper.class) {
            if (instance == null) {
                synchronized (RetrofitHelper.class) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    private void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(createClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .callTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new RequestInterceptor())
                .build();
    }

    public <T> T serviceApi(Class<T> service) {
        return retrofit.create(service);
    }
}
