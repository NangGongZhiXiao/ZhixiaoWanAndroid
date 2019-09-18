package com.zhixiao.wanandroid.view.main;

import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.base.view.MVPBaseFragmentWithoutSwipeBack;
import com.zhixiao.wanandroid.presenter.main.MeContract;
import com.zhixiao.wanandroid.presenter.main.MePresenter;

/**
 * @ClassName: MeFragment
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/18
 */
public class MeFragment extends MVPBaseFragmentWithoutSwipeBack<MeContract.Presenter>
        implements MeContract.View {
    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public MeContract.Presenter createPresenter() {
        return new MePresenter();
    }
}
