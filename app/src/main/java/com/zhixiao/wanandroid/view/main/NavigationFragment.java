package com.zhixiao.wanandroid.view.main;

import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.base.view.MVPBaseFragmentWithoutSwipeBack;
import com.zhixiao.wanandroid.presenter.main.NavigationContract;
import com.zhixiao.wanandroid.presenter.main.NavigationPresenter;

/**
 * @ClassName: NavigationFragment
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/18
 */
public class NavigationFragment extends MVPBaseFragmentWithoutSwipeBack<NavigationContract.Presenter>
        implements NavigationContract.View {
    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    public NavigationContract.Presenter createPresenter() {
        return new NavigationPresenter();
    }
}
