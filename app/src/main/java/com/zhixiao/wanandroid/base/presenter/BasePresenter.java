package com.zhixiao.wanandroid.base.presenter;

import com.zhixiao.wanandroid.base.view.BaseView;

/**
 * @ClassName: BasePresenter
 * @Description: 实现了MVP架构的VP绑定
 * @Author: zhixiao
 * @CreateDate: 2019/9/4
 */
public interface BasePresenter<T extends BaseView> {
    /**
     * 绑定View
     * @param view
     */
    void attachView(T view);

    /**
     * 解绑View
     */
    void detachView();
}
