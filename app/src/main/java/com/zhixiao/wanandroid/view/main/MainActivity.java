package com.zhixiao.wanandroid.view.main;

import android.content.Intent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.app.APP;
import com.zhixiao.wanandroid.base.view.AbstractBaseActivity;
import com.zhixiao.wanandroid.base.view.MVPBaseActivity;
import com.zhixiao.wanandroid.component.RxBus;
import com.zhixiao.wanandroid.component.event.ModeNightEvent;
import com.zhixiao.wanandroid.model.bean.ResponseBody;
import com.zhixiao.wanandroid.model.bean.home.HomeArticleListData;
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
        RxBus.getInstance().post(new ModeNightEvent(aSwitch.isChecked()));
    }
    @OnClick(R.id.hello)
    public void onclick(View v){
        startActivity(new Intent(this, MainActivity.class));
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
