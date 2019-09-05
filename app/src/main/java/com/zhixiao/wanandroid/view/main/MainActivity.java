package com.zhixiao.wanandroid.view.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.app.APP;
import com.zhixiao.wanandroid.base.view.AbstractBaseActivity;
import com.zhixiao.wanandroid.model.bean.SearchHistoryData;
import com.zhixiao.wanandroid.model.dao.SearchHistoryDataDao;
import com.zhixiao.wanandroid.presenter.main.MainContract;
import com.zhixiao.wanandroid.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AbstractBaseActivity implements MainContract.MainView {

    @BindView(R.id.hello)
    public TextView hello;
    @OnClick(R.id.hello)
    public void onclick(){
        startActivity(new Intent(this, MainActivity.class));
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
}
