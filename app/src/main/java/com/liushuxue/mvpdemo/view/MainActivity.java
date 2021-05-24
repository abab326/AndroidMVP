package com.liushuxue.mvpdemo.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.liushuxue.corelibrary.mvp.IPresenter;
import com.liushuxue.mvpdemo.R;
import com.liushuxue.mvpdemo.contract.IMainContract;
import com.liushuxue.corelibrary.base.BaseActivity;
import com.liushuxue.mvpdemo.presenter.MainPresenter;

import butterknife.OnClick;

/**
 * @author sirXu
 * @describe
 * @date 2021/5/19  15:16
 * - generate by MvpAutoCodePlus plugin.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements IMainContract.View {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        notifyStatusBar(Color.TRANSPARENT, true);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void getImageSuccess(Bitmap bitmap) {
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
    }

    @OnClick(R.id.button)
    void downLoadImage() {
        presenter.getImage();
    }

}

