package com.zhixiao.wanandroid.presenter.main;

import com.zhixiao.wanandroid.base.presenter.BasePresenter;
import com.zhixiao.wanandroid.base.view.BaseView;

/**
 * @ClassName: MeContract
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/18
 */
public interface MeContract {
    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter<View>{

    }
}
