package com.zhixiao.wanandroid.view.main;

import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.base.view.MVPBaseFragmentWithoutSwipeBack;
import com.zhixiao.wanandroid.presenter.main.ArticleContract;
import com.zhixiao.wanandroid.presenter.main.ArticlePresenter;

/**
 * @ClassName: ArticleFragment
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/18
 */
public class ArticleFragment extends MVPBaseFragmentWithoutSwipeBack<ArticleContract.Presenter>
        implements ArticleContract.View{
    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article;
    }

    @Override
    public ArticleContract.Presenter createPresenter() {
        return new ArticlePresenter();
    }
}
