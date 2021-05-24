package com.liushuxue.mvpdemo.api;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface ServiceApi {

    @GET("https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF")
  Observable<ResponseBody> getImage();
}
