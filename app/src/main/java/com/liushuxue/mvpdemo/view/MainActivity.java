package com.liushuxue.mvpdemo.view;

import android.content.ContentProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.liushuxue.corelibrary.mvp.IPresenter;
import com.liushuxue.corelibrary.util.DisplayUtils;
import com.liushuxue.mvpdemo.BuildConfig;
import com.liushuxue.mvpdemo.R;
import com.liushuxue.mvpdemo.contract.IMainContract;
import com.liushuxue.corelibrary.base.BaseActivity;
import com.liushuxue.mvpdemo.presenter.MainPresenter;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;

import static com.liushuxue.mvpdemo.R.id.imageView2;

/**
 * @author sirXu
 * @describe
 * @date 2021/5/19  15:16
 * - generate by MvpAutoCodePlus plugin.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements IMainContract.View {
    @BindView(R.id.imageView2)
    ImageView imageView2;


    @Override
    protected void initView() {
        notifyStatusBar(getResources().getColor(R.color.teal_200), false);
        RoundedCorners roundedCorners = new RoundedCorners(DisplayUtils.dip2px(this, 8));
        Glide.with(this)
                .load("https://t7.baidu.com/it/u=1819248061,230866778&fm=193&f=GIF")
                .transform(roundedCorners)
                .into(imageView2);
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
     Logger.d(bitmap.getWidth()+"   "+bitmap.getHeight());
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
   }

    @OnClick(R.id.button)
    void downLoadImage() {
        presenter.getImage();
    }

}

