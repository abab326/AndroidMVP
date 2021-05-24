package com.liushuxue.mvpdemo.presenter;

import android.graphics.Bitmap;

import com.liushuxue.corelibrary.mvp.IModel;
import com.liushuxue.mvpdemo.contract.IMainContract;
import com.liushuxue.mvpdemo.R;
import com.liushuxue.corelibrary.base.BasePresenter;
import com.liushuxue.mvpdemo.model.MainModel;

/**
 * @author sirXu
 * @describe
 * @date 2021/5/19  15:16
 * - generate by MvpAutoCodePlus plugin.
 */

public class MainPresenter extends BasePresenter<IMainContract.View, MainModel> implements IMainContract.Presenter {

    @Override
    protected MainModel createModel() {
        return new MainModel();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void getImage() {
        getView().showLoading();
        baseModel.getImage(new IModel.OnHttpRequestCallback<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                getView().closeLoading();
                getView().getImageSuccess(bitmap);
            }

            @Override
            public void onError(String message) {
                getView().closeLoading();
            }
        });
    }
}

