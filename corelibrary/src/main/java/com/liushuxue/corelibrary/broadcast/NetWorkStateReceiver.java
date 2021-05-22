package com.liushuxue.corelibrary.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.liushuxue.corelibrary.enums.NetworkType;
import com.liushuxue.corelibrary.util.NetworkUtil;

/**
 * @auther liushuxue
 * @describe 网络状态广播
 * @createTime 2021/5/22
 */
public class NetWorkStateReceiver extends BroadcastReceiver {
    private OnNetworkChangeListener onNetworkChangeListener;

    public NetWorkStateReceiver(OnNetworkChangeListener onNetworkChangeListener) {
        this.onNetworkChangeListener = onNetworkChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            NetworkType networkType = NetworkUtil.getNetworkType(context);
             if (onNetworkChangeListener!=null)
                 onNetworkChangeListener.onNetworkChange(networkType);
        }
    }

    public interface OnNetworkChangeListener{

        void onNetworkChange(NetworkType networkType);
    }
}
