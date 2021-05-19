package com.liushuxue.corelibrary.base;

import androidx.lifecycle.LifecycleObserver;

import com.liushuxue.corelibrary.mvp.IModel;
import com.liushuxue.corelibrary.mvp.IPresenter;
import com.liushuxue.corelibrary.mvp.IView;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends IView, M extends IModel> implements IPresenter<V> {
    protected WeakReference<V> weakReference;
    protected M model;

    @Override

    public void attachView(V view) {
        weakReference = new WeakReference<V>(view);
        if (model == null) {
            model = createModel();
        }
    }

    protected abstract M createModel();

    @Override
    public void destroyed() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
    }

    protected V getView() {
        return weakReference == null ? null : weakReference.get();
    }
}
