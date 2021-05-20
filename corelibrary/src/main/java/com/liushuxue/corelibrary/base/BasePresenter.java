package com.liushuxue.corelibrary.base;

import androidx.lifecycle.LifecycleObserver;

import com.liushuxue.corelibrary.mvp.IModel;
import com.liushuxue.corelibrary.mvp.IPresenter;
import com.liushuxue.corelibrary.mvp.IView;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends IView, M extends BaseModel> implements IPresenter<V> {
    protected WeakReference<V> weakReference;
    protected M baseModel;

    @Override

    public void attachView(V view) {
        weakReference = new WeakReference<V>(view);
        if (baseModel == null) {
            baseModel = createModel();
        }
    }

    protected abstract M createModel();

    protected abstract void initPresenter();

    @Override
    public void destroyed() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
        if (baseModel != null) {
            baseModel.clearAllRequest();
        }
    }

    protected V getView() {
        return weakReference == null ? null : weakReference.get();
    }
}
