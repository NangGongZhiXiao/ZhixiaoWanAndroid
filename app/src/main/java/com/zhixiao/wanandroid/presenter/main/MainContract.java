package com.zhixiao.wanandroid.presenter.main;

import com.zhixiao.wanandroid.base.presenter.BasePresenter;
import com.zhixiao.wanandroid.base.view.BaseView;

/**
 * @ClassName: MainContract
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/4
 */
public interface MainContract {
    interface View extends BaseView<Presenter>{

        void startLogin();
    }

    interface Presenter extends BasePresenter<View>{
        void changeModeNight(boolean night);

        void setModeNight(boolean checked);
    }
}
