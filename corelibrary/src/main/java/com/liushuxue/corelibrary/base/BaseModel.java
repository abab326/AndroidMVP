package com.liushuxue.corelibrary.base;

import android.util.Log;

import com.liushuxue.corelibrary.http.ResultObserver;
import com.liushuxue.corelibrary.mvp.IModel;
import com.liushuxue.corelibrary.util.ImageUtils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public abstract class BaseModel implements IModel {
    public static final String TAG = "BaseModel";
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected Disposable currentDisposable;

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
        requestObservable.observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
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


    public void downloadFile(Observable<ResponseBody> responseBodyObservable, String filePath) {
        final Disposable disposable = responseBodyObservable.observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(responseBody -> ImageUtils.saveInputStreamAsFile(responseBody.byteStream(), filePath))
                .subscribe(aBoolean -> Log.d(TAG, "accept: "));
        compositeDisposable.add(disposable);

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
