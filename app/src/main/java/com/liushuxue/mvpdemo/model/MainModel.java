package com.liushuxue.mvpdemo.model;

import android.graphics.Bitmap;

import com.liushuxue.corelibrary.http.RetrofitHelper;
import com.liushuxue.mvpdemo.api.ServiceApi;
import com.liushuxue.mvpdemo.contract.IMainContract;
import com.liushuxue.mvpdemo.R;
import com.liushuxue.corelibrary.base.BaseModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;

/**
 * @author sirXu
 * @describe
 * @date 2021/5/19  15:16
 * - generate by MvpAutoCodePlus plugin.
 */

public class MainModel extends BaseModel implements IMainContract.Model {


    @Override
    public void getImage(OnHttpRequestCallback<Bitmap> callback) {
        Observable<ResponseBody> observable = RetrofitHelper.getInstance().serviceApi(ServiceApi.class).getImage();
        downloadFile(observable,callback,true);
    }
}

