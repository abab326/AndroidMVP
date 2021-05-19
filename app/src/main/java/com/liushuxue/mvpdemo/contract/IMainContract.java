package com.liushuxue.mvpdemo.contract;

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
    }

    interface Presenter extends IPresenter<View> {
    }

    interface Model extends IModel {
    }
}
