package com.zhixiao.wanandroid.view.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.base.presenter.BasePresenter;
import com.zhixiao.wanandroid.base.view.AbstractBaseActivity;
import com.zhixiao.wanandroid.base.view.BaseView;

import butterknife.BindView;

public class LoginActivity extends AbstractBaseActivity implements BaseView {

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {
        loadRootFragment(R.id.fra_login_container, new LoginFragment());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
}
