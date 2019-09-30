package com.zhixiao.wanandroid.presenter.main;

import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.app.APP;
import com.zhixiao.wanandroid.base.presenter.AbstractBasePresenter;
import com.zhixiao.wanandroid.model.DataRepository;
import com.zhixiao.wanandroid.model.bean.ResponseBody;
import com.zhixiao.wanandroid.model.bean.home.HomeArticleListData;
import com.zhixiao.wanandroid.model.bean.knowlegetree.KnowledgeHierarchyData;
import com.zhixiao.wanandroid.utils.LogUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName: ArticlePresenter
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/18
 */
public class ArticlePresenter extends AbstractBasePresenter<ArticleContract.View>
        implements ArticleContract.Presenter {

    private KnowledgeHierarchyData articleListNames;
    private Map<Integer, String> groupTitles;

    @Override
    public void attachView(ArticleContract.View view) {
        super.attachView(view);

        getArticleNames();
    }

    private void getArticleNames() {
        DataRepository dataRepository = APP.getInstance().getDataRepository();
        // 添加最新博文的信息到列表
        articleListNames = new KnowledgeHierarchyData();
        groupTitles = new HashMap<>();
        KnowledgeHierarchyData homepage = new KnowledgeHierarchyData();
        List<KnowledgeHierarchyData> list = new LinkedList<>();
        String title = APP.getInstance().getResources().getString(R.string.latest_article);
        homepage.setName(title);
        homepage.setId(KnowledgeHierarchyData.ID_LATEST_ARTICLE);
        list.add(homepage);
        articleListNames.setChildren(list);
        groupTitles.put(0, title);
        view.updateArticleListNames(articleListNames, groupTitles);

        addDisposable(dataRepository.getWxArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody<List<KnowledgeHierarchyData>>>() {
                    @Override
                    public void accept(ResponseBody<List<KnowledgeHierarchyData>> listResponseBody) throws Exception {
                        if(listResponseBody.getErrorCode() != 0){
                            view.showError(listResponseBody.getErrorMsg());
                            return;
                        }
                        list.addAll(1, listResponseBody.getData());
                        int offset = listResponseBody.getData().size();
                        for(Map.Entry<Integer, String> entry : groupTitles.entrySet()){
                            if(entry.getKey() != 0){
                                entry.setValue(entry.getValue() + offset);
                            }
                        }
                        groupTitles.put(1, APP.getInstance().getResources().getString(R.string.wx_article));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        view.showError(null);
                    }
                }));

        addDisposable(dataRepository.getKnowledgeHierarchyData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody<List<KnowledgeHierarchyData>>>() {
                    @Override
                    public void accept(ResponseBody<List<KnowledgeHierarchyData>> listResponseBody) throws Exception {
                        if(listResponseBody.getErrorCode() != 0){
                            view.showError(listResponseBody.getErrorMsg());
                            return;
                        }
                        List<KnowledgeHierarchyData> list = articleListNames.getChildren();
                        int offset = list.size();
                        for(KnowledgeHierarchyData group : listResponseBody.getData()){
                            groupTitles.put(offset, group.getName());
                            if(group.getChildren() != null) {
                                offset += group.getChildren().size();
                                list.addAll(group.getChildren());
                            }
                        }
                        LogUtil.i(articleListNames.toString());
                        view.updateArticleListNames(articleListNames, groupTitles);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        view.showError(null);
                    }
                }));
    }

    @Override
    public void loadArticleList(int id, int page, int position) {
        DataRepository dataRepository = APP.getInstance().getDataRepository();
        // 根据id来判断从哪个接口获取数据
        Observable<ResponseBody<HomeArticleListData>> observable =
                id == KnowledgeHierarchyData.ID_LATEST_ARTICLE ?
                        dataRepository.HomeArticleListData(page) :
                        dataRepository.getKnowledgeTreeDetailData(page, id);
        addDisposable(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody<HomeArticleListData>>() {
                    @Override
                    public void accept(ResponseBody<HomeArticleListData> homeArticleListDataResponseBody) throws Exception {
                        if(homeArticleListDataResponseBody.getErrorCode() != 0){
                            view.showError(homeArticleListDataResponseBody.getErrorMsg());
                            return;
                        }
                        view.updateArticleList(homeArticleListDataResponseBody.getData(), position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        view.showError(null);
                    }
                }));
    }
}
