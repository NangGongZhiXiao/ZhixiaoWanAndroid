package com.zhixiao.wanandroid.view.main;

import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.base.view.MVPBaseFragmentWithoutSwipeBack;
import com.zhixiao.wanandroid.presenter.main.ProjectContract;
import com.zhixiao.wanandroid.presenter.main.ProjectPresenter;

/**
 * @ClassName: ProjectFragment
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/18
 */
public class ProjectFragment extends MVPBaseFragmentWithoutSwipeBack<ProjectContract.Presenter>
        implements ProjectContract.View {
    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public ProjectContract.Presenter createPresenter() {
        return new ProjectPresenter();
    }
}
