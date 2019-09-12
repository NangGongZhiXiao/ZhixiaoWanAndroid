package com.zhixiao.wanandroid.view.main;

import android.content.Intent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;

import com.jaeger.library.StatusBarUtil;
import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.base.view.MVPBaseActivity;
import com.zhixiao.wanandroid.presenter.main.MainContract;
import com.zhixiao.wanandroid.presenter.main.MainPresenter;
import com.zhixiao.wanandroid.view.login.LoginActivity;


import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends MVPBaseActivity<MainContract.Presenter>
        implements MainContract.View {

    @BindView(R.id.hello)
    public TextView hello;
    @BindView(R.id.switch_1)
    public Switch aSwitch;

    @OnClick(R.id.switch_1)
    public void nightSwitch(){
        presenter.setModeNight(aSwitch.isChecked());
    }
    @OnClick(R.id.hello)
    public void onclick(View v){
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initEventAndData() {
        setSwipeBackEnable(false);
    }

    @Override
    protected void initView() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_background2), 60);
        aSwitch.setChecked(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void startLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
