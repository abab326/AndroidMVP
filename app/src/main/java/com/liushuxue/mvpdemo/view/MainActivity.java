package com.liushuxue.mvpdemo.view;

import com.liushuxue.corelibrary.mvp.IPresenter;
import com.liushuxue.mvpdemo.R;
import com.liushuxue.mvpdemo.contract.IMainContract;
import com.liushuxue.corelibrary.base.BaseActivity;
import com.liushuxue.mvpdemo.presenter.MainPresenter;

/**
 * @author sirXu
 * @describe
 * @date 2021/5/19  15:16
 * - generate by MvpAutoCodePlus plugin.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements IMainContract.View {

    @Override
    protected void initView() {

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}

