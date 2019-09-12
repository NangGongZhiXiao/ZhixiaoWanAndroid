package com.zhixiao.wanandroid.presenter.login;

import com.zhixiao.wanandroid.base.presenter.BasePresenter;
import com.zhixiao.wanandroid.base.view.BaseView;

/**
 * @ClassName: SignUpContract
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/12
 */
public interface SignUpContract {
    interface View extends BaseView<Presenter> {

        void signUpSuccessful();

        void signUpFailed(String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void signUp(String name, String password, String rePassword);
    }
}
