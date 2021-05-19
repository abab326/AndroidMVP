package com.liushuxue.corelibrary.base;

import android.util.Log;

import com.liushuxue.corelibrary.http.ResultObserver;
import com.liushuxue.corelibrary.mvp.IModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public abstract class BaseModel implements IModel {
    public static final String TAG ="BaseModel";
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * 网络请求 通用类
     *
     * @param requestObservable 请求对象
     * @param callback          回调方法
     * @param <T>               返回实体对象
     */
    public <T> void httpRequest(Observable<BaseResultBean<T>> requestObservable, OnHttpRequestCallback<T> callback) {
        requestObservable.observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver<T>() {
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


    public void downloadFile(Observable<ResponseBody> responseBodyObservable,String filePath) {
    final Disposable disposable =  responseBodyObservable.observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(responseBody -> saveFileByResponseBody(responseBody, filePath))
                .subscribe(aBoolean -> Log.d(TAG, "accept: "));
          compositeDisposable.add(disposable);

    }

    /**
     * 下载文件保存到本地
     *
     * @param responseBody 请求返回数据
     * @param filePath     保存文件路径
     */
    protected Boolean saveFileByResponseBody(ResponseBody responseBody, String filePath) {
        try {
            File futureStudioIconFile = new File(filePath);
            InputStream inputStream = responseBody.byteStream();
            OutputStream outputStream = new FileOutputStream(futureStudioIconFile);
            byte[] fileReader = new byte[4096];
            while (true) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    break;
                }
                outputStream.write(fileReader, 0, read);
            }
            outputStream.flush();
            inputStream.close();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {

            return false;
        } catch (IOException e) {
            return false;
        }


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
