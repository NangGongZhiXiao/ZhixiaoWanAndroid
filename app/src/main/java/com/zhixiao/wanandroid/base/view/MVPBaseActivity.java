package com.zhixiao.wanandroid.base.view;

import android.os.Bundle;

import com.zhixiao.wanandroid.base.presenter.BasePresenter;

/**
 * @ClassName: MVPBaseActivity
 * @Description: 支持mvp的baseActivity
 * @Author: zhixiao
 * @CreateDate: 2019/9/9
 */
public abstract class MVPBaseActivity<T extends BasePresenter> extends AbstractBaseActivity implements BaseView<T>{
    protected T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
