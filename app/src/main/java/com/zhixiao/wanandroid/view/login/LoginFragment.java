package com.zhixiao.wanandroid.view.login;

import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.base.presenter.BasePresenter;
import com.zhixiao.wanandroid.base.view.AbstractBaseFragment;
import com.zhixiao.wanandroid.presenter.login.LoginContract;
import com.zhixiao.wanandroid.presenter.login.LoginPresenter;

/**
 * @ClassName: LoginFragment
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/9
 */
public class LoginFragment extends AbstractBaseFragment<LoginContract.Presenter> implements LoginContract.View {

    @Override
    protected LoginContract.Presenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void setNightMode(boolean night) {

    }

    @Override
    public boolean getNightMode() {
        return false;
    }
}
