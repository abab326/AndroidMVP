package com.liushuxue.corelibrary.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.liushuxue.corelibrary.mvp.IPresenter;
import com.liushuxue.corelibrary.mvp.IView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView {
    private LoadingPopupView loadingDialog;
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (null == presenter) {
            presenter = createPresenter();
            presenter.attachView(this);
        }
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destroyed();
        }
    }

    protected abstract void initView();

    protected abstract P createPresenter();

    protected abstract int getLayoutId();

    @Override
    public void showLoading() {
        showLoading("");
    }

    @Override
    public void showLoading(String message) {
        if (isFinishing()) {
            return;
        }
        if (null == loadingDialog) {
            loadingDialog = new XPopup.Builder(this).asLoading(message).;
        } else {
            loadingDialog.setTitle(message);
        }
        loadingDialog.show();

    }

    @Override
    public void closeLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onError() {

    }
}