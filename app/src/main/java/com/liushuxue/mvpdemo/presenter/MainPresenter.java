package com.liushuxue.mvpdemo.presenter;

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
}

