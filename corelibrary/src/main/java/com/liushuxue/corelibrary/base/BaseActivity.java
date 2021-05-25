package com.liushuxue.corelibrary.base;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.liushuxue.corelibrary.R;
import com.liushuxue.corelibrary.broadcast.NetWorkStateReceiver;
import com.liushuxue.corelibrary.enums.NetworkType;
import com.liushuxue.corelibrary.mvp.IPresenter;
import com.liushuxue.corelibrary.mvp.IView;
import com.liushuxue.corelibrary.util.SPUtils;
import com.liushuxue.corelibrary.util.StatusBarUtils;
import com.liushuxue.corelibrary.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView, NetWorkStateReceiver.OnNetworkChangeListener {
    protected final String TAG = this.getClass().getName();
    private ConstraintLayout baseContainer;
    private FrameLayout baseContentView;
    private LinearLayout networkErrorView;
    private LoadingPopupView loadingDialog;
    private NetWorkStateReceiver netWorkStateReceiver;
    // 状态栏颜色
    private int statusBarColor;
    // 是否状态栏为黑色文字
    private boolean isBlackStatusBarText = false;
    protected P presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        baseContainer = findViewById(R.id.base_container);
        baseContentView = findViewById(R.id.base_content);
        networkErrorView = findViewById(R.id.base_network_error);
        fitsSystemWindowsAble(false);
        int dataValue = (int) SPUtils.get(this, "aa", 12);
        netWorkStateReceiver = new NetWorkStateReceiver(this);
        //状态栏设置
        statusBarColor = getResources().getColor(R.color.statusColor);
        StatusBarUtils.setStatusBarColor(this, statusBarColor, isBlackStatusBarText);
        if (getLayoutId() > 0) {
            baseContentView.removeAllViews();
            View contentView = LayoutInflater.from(this).inflate(getLayoutId(), baseContentView, false);
            baseContentView.addView(contentView);
        }
        ButterKnife.bind(this);
        if (null == presenter) {
            presenter = createPresenter();
            presenter.attachView(this);
        }
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerNetworkReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unRegisterNetworkReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destroyed();
        }
    }

    public void registerNetworkReceiver() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, intentFilter);
    }

    public void unRegisterNetworkReceiver() {
        unregisterReceiver(netWorkStateReceiver);
    }

    /**
     * 设置页面晃否空出状态栏高度
     */
    void fitsSystemWindowsAble(boolean fitSystemWindows) {
        baseContainer.setFitsSystemWindows(fitSystemWindows);
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
            loadingDialog = new XPopup.Builder(this).asLoading(message);
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

    @Override
    public void onNetworkChange(NetworkType networkType) {
        if (networkType == NetworkType.NETWORK_UNKNOWN) {
            networkErrorView.setVisibility(View.VISIBLE);
        } else {
            networkErrorView.setVisibility(View.GONE);
            if (networkType == NetworkType.NETWORK_2G) {
                ToastUtils.show("当前网络不佳");
            }
        }
    }

    public int getStatusBarColor() {
        return statusBarColor;
    }

    public void setStatusBarColor(int statusBarColor) {
        this.statusBarColor = statusBarColor;
        notifyStatusBar(this.statusBarColor, this.isBlackStatusBarText);
    }

    public boolean isBlackStatusBarText() {
        return isBlackStatusBarText;

    }

    public void setBlackStatusBarText(boolean blackStatusBarText) {
        isBlackStatusBarText = blackStatusBarText;
        notifyStatusBar(this.statusBarColor, this.isBlackStatusBarText);
    }

    /**
     * 更新状态栏
     *
     * @param statusBarColor       状态栏颜色
     * @param isBlackStatusBarText 是否为黑色字
     */
    public void notifyStatusBar(int statusBarColor, boolean isBlackStatusBarText) {
        this.statusBarColor = statusBarColor;
        this.isBlackStatusBarText = isBlackStatusBarText;
        StatusBarUtils.setStatusBarColor(this, this.statusBarColor, this.isBlackStatusBarText);
    }
}