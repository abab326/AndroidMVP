package com.liushuxue.corelibrary.base;

import androidx.lifecycle.LifecycleObserver;

import com.liushuxue.corelibrary.mvp.IPresenter;
import com.liushuxue.corelibrary.mvp.IView;

import java.lang.ref.WeakReference;

public class BasePresenter<V extends IView> implements IPresenter<V>{
    protected WeakReference<V> weakReference ;
    @Override
    public void attachView(V view) {
        weakReference =  new WeakReference<V>(view);
    }

    @Override
    public void destroyed() {
        if (weakReference!=null){
            weakReference.clear();
            weakReference = null;
        }

    }
}
