package com.liushuxue.corelibrary.event;

public class LoginEvent {
    // 是否登录
    private boolean isLogin;

    public LoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

}
