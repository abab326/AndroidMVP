package com.liushuxue.mvpdemo.contract;

import android.graphics.Bitmap;

import com.liushuxue.corelibrary.mvp.IView;
import com.liushuxue.corelibrary.mvp.IPresenter;
import com.liushuxue.corelibrary.mvp.IModel;

/**
 * @author sirXu
 * @describe
 * @date 2021/5/19  15:16
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IMainContract {
    interface View extends IView {
        void getImageSuccess(Bitmap bitmap);
    }

    interface Presenter extends IPresenter<View> {
        void getImage();
    }

    interface Model extends IModel {
        void getImage(OnHttpRequestCallback<Bitmap> callback);
    }
}
