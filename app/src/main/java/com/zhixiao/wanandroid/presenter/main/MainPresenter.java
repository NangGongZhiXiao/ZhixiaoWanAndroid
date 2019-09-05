package com.zhixiao.wanandroid.presenter.main;


/**
 * @ClassName: MainPresenter
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/4
 */
public class MainPresenter implements MainContract.MainPresenter{
    private MainContract.MainView mView;

    @Override
    public void attachView(MainContract.MainView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
