package com.liushuxue.corelibrary.base;

import com.liushuxue.corelibrary.http.ResultObserver;
import com.liushuxue.corelibrary.mvp.IModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public abstract class BaseModel implements IModel {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();


    public <T> void httpRequest(Observable<BaseResultBean<T>> requestObservable, OnHttpRequestCallback<T> callback) {


        requestObservable.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new ResultObserver<T>() {
            @Override
            protected void onStart() {
                super.onStart();
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

    @Override
    public void clearAllRequest() {
        if (compositeDisposable != null && compositeDisposable.size() > 0) {
            try {
                compositeDisposable.dispose();
            } catch (RuntimeException e) {

            }
        }

    }
}
