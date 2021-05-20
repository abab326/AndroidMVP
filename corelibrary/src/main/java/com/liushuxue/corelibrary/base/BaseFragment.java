package com.liushuxue.corelibrary.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.liushuxue.corelibrary.mvp.IPresenter;
import com.liushuxue.corelibrary.mvp.IView;

public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IView {
    private View rootView;
    // 是否首次初始化
    private boolean isFirstInit = true;
    protected P presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isFirstInit) {
            isFirstInit = false;
            presenter = createPresenter();
            initView();
        }
    }

    protected abstract void initView();

    protected abstract P createPresenter();

    protected abstract int getLayoutId();
}
