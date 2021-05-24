package com.liushuxue.corelibrary.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.liushuxue.corelibrary.http.ResultObserver;
import com.liushuxue.corelibrary.http.RetrofitHelper;
import com.liushuxue.corelibrary.http.ThrowableHandle;
import com.liushuxue.corelibrary.mvp.IModel;
import com.liushuxue.corelibrary.util.ImageUtils;
import com.liushuxue.corelibrary.util.ToastUtils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public abstract class BaseModel implements IModel {
    public static final String TAG = "BaseModel";
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected Disposable currentDisposable;

    protected <Api extends BaseServiceApi> Api getHttp(Class<Api> apiClass) {
        return RetrofitHelper.getInstance().serviceApi(apiClass);
    }


    /**
     * 网络请求 通用类
     *
     * @param requestObservable 请求对象
     * @param callback          回调方法
     * @param <T>               返回实体对象
     */
    public <T> void httpRequest(Observable<BaseResultBean<T>> requestObservable, OnHttpRequestCallback<T> callback) {
        this.httpRequest(requestObservable, callback, true);
    }

    /**
     * @param requestObservable 请求对象
     * @param callback          回调方法
     * @param autoShowErrorMsg  是否自动显示错误信息
     * @param <T>               返回实体对象
     */
    public <T> void httpRequest(Observable<BaseResultBean<T>> requestObservable, OnHttpRequestCallback<T> callback, boolean autoShowErrorMsg) {
        requestObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResultObserver<T>(autoShowErrorMsg) {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        currentDisposable = this;
                        compositeDisposable.add(this);
                    }

                    @Override
                    public void onSuccess(T data) {
                        callback.onSuccess(data);
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        callback.onError(errorMessage);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        compositeDisposable.remove(this);
                    }
                });

    }

    /**
     * 图片下载
     *
     * @param responseBodyObservable 下载源
     * @param callback               回调接口
     * @param autoShowErrorMsg       是否自动显示错误
     */
    public void downloadFile(Observable<ResponseBody> responseBodyObservable, OnHttpRequestCallback<Bitmap> callback, boolean autoShowErrorMsg) {
        responseBodyObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        compositeDisposable.add(this);
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        if (callback != null) {
                            Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                            callback.onSuccess(bitmap);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        String errorMsg = ThrowableHandle.handleException(e);
                        if (autoShowErrorMsg) {
                            ToastUtils.show(errorMsg);
                        }
                        if (callback != null) {
                            callback.onError(errorMsg);
                        }
                    }

                    @Override
                    public void onComplete() {
                        compositeDisposable.remove(this);
                    }
                });


    }

    @Override
    public void clearAllRequest() {
        if (compositeDisposable != null && compositeDisposable.size() > 0) {
            try {
                compositeDisposable.dispose();
            } catch (RuntimeException e) {

            }
        }
    }

    @Override
    public void cancelCurrentRequest() {
        if (currentDisposable != null && compositeDisposable.isDisposed()) {
            currentDisposable.dispose();
            currentDisposable = null;
        }
    }
}
