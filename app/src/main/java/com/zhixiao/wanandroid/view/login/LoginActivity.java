package com.zhixiao.wanandroid.view.login;

import com.jaeger.library.StatusBarUtil;
import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.base.view.AbstractBaseActivity;

public class LoginActivity extends AbstractBaseActivity {

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {
        loadRootFragment(R.id.fra_login_container, new LoginFragment());
        StatusBarUtil.setColorForSwipeBack(this, getResources().getColor(R.color.color_background), 60);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
}
