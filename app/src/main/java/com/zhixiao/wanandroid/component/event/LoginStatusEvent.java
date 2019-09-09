package com.zhixiao.wanandroid.component.event;

/**
 * @ClassName: LoginStatusEvent
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/9
 */
public class LoginStatusEvent {
    private boolean login;

    public LoginStatusEvent(boolean login) {
        this.login = login;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
