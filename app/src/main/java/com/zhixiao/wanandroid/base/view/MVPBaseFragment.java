package com.zhixiao.wanandroid.base.view;

import android.os.Bundle;

import com.zhixiao.wanandroid.base.presenter.BasePresenter;

/**
 * @ClassName: MVPBaseFragment
 * @Description: 支持mvp的baseFragment
 * @Author: zhixiao
 * @CreateDate: 2019/9/16
 */
public abstract class MVPBaseFragment<T extends BasePresenter> extends AbstractBaseFragment implements BaseView<T>{
    protected T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
