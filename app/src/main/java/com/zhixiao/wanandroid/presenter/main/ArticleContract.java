package com.zhixiao.wanandroid.presenter.main;

import com.zhixiao.wanandroid.base.presenter.BasePresenter;
import com.zhixiao.wanandroid.base.view.BaseView;
import com.zhixiao.wanandroid.model.bean.home.HomeArticleListData;
import com.zhixiao.wanandroid.model.bean.knowlegetree.KnowledgeHierarchyData;

import java.util.Map;

/**
 * @ClassName: ArticleContract
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/18
 */
public interface ArticleContract {
    interface View extends BaseView<Presenter>{
        void showNotData();
        void updateArticleListNames(KnowledgeHierarchyData data, Map<Integer, ? extends CharSequence> groupTitle);

        void showError(String errorMsg);

        void updateArticleList(HomeArticleListData data, int position);
    }

    interface Presenter extends BasePresenter<View>{

        void loadArticleList(int id, int page, int position);
    }
}
