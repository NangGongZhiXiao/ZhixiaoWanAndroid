package com.zhixiao.wanandroid.presenter.login;

import com.zhixiao.wanandroid.base.presenter.BasePresenter;
import com.zhixiao.wanandroid.base.view.BaseView;

/**
 * @ClassName: LoginContract
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/9
 */
public interface LoginContract {
    interface View extends BaseView<Presenter>{

        void loginSucceed();

        void loginFailed();

        void networkError();
    }

    interface Presenter extends BasePresenter<View>{
        void login(String name, String password);
    }
}
