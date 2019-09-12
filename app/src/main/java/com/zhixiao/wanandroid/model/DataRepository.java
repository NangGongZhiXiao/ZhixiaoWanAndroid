package com.zhixiao.wanandroid.model;


import androidx.annotation.NonNull;

import com.zhixiao.wanandroid.component.event.LoginStatusEvent;
import com.zhixiao.wanandroid.component.event.ModeNightEvent;
import com.zhixiao.wanandroid.model.bean.ResponseBody;
import com.zhixiao.wanandroid.model.bean.banner.HomePageBannerModel;
import com.zhixiao.wanandroid.model.bean.collect.CollectData;
import com.zhixiao.wanandroid.model.bean.collect.CollectListData;
import com.zhixiao.wanandroid.model.bean.frienduser.CommonWebData;
import com.zhixiao.wanandroid.model.bean.home.HomeArticleData;
import com.zhixiao.wanandroid.model.bean.home.HomeArticleListData;
import com.zhixiao.wanandroid.model.bean.knowlegetree.KnowledgeHierarchyData;
import com.zhixiao.wanandroid.model.bean.login.LoginData;
import com.zhixiao.wanandroid.model.bean.navigation.NavigationListData;
import com.zhixiao.wanandroid.model.bean.project.ProjectClassifyData;
import com.zhixiao.wanandroid.model.bean.project.ProjectListData;
import com.zhixiao.wanandroid.model.bean.search.HotKeyData;
import com.zhixiao.wanandroid.model.bean.webmark.WebBookMark;
import com.zhixiao.wanandroid.model.db.DatabaseSource;
import com.zhixiao.wanandroid.model.http.HttpRemoteSource;
import com.zhixiao.wanandroid.model.sp.ShredPreferenceSource;

import java.util.List;

import io.reactivex.Observable;


/**
 * @ClassName: DataRepository
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/4
 */
public class DataRepository implements DatabaseSource, ShredPreferenceSource, HttpRemoteSource {
    private DatabaseSource databaseSource;
    private ShredPreferenceSource sharedPreferenceSource;
    private HttpRemoteSource httpRemoteSource;
    private static DataRepository instance;

    private DataRepository(@NonNull DatabaseSource databaseSource,
                           @NonNull ShredPreferenceSource sharedPreferenceSource,
                           @NonNull HttpRemoteSource httpRemoteSource) {
        this.databaseSource = databaseSource;
        this.sharedPreferenceSource = sharedPreferenceSource;
        this.httpRemoteSource = httpRemoteSource;
    }

    public static DataRepository getInstance(DatabaseSource databaseSource,
                                      ShredPreferenceSource shredPreferenceSource,
                                      HttpRemoteSource httpRemoteSource){
        if(instance == null) {
            instance = new DataRepository(databaseSource, shredPreferenceSource, httpRemoteSource);
        }
        return instance;
    }

    @Override
    public Observable<ResponseBody<HomeArticleListData>> HomeArticleListData(int pageNum) {
        return httpRemoteSource.HomeArticleListData(pageNum);
    }

    @Override
    public Observable<ResponseBody<List<HomePageBannerModel>>> GetHomePageBannerData() {
        return httpRemoteSource.GetHomePageBannerData();
    }

    @Override
    public Observable<ResponseBody<HomeArticleListData>> HomeArticleListProjectData(int pageNum) {
        return httpRemoteSource.HomeArticleListProjectData(pageNum);
    }

    @Override
    public Observable<ResponseBody<List<CommonWebData>>> GetFriendUseWebData() {
        return httpRemoteSource.GetFriendUseWebData();
    }

    @Override
    public Observable<ResponseBody<List<HotKeyData>>> GetHotKeyData() {
        return httpRemoteSource.GetHotKeyData();
    }

    @Override
    public Observable<ResponseBody<List<HomeArticleData>>> HomeTopArticleData() {
        return httpRemoteSource.HomeTopArticleData();
    }

    @Override
    public Observable<ResponseBody<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData() {
        return httpRemoteSource.getKnowledgeHierarchyData();
    }

    @Override
    public Observable<ResponseBody<HomeArticleListData>> getKnowledgeTreeDetailData(int pageNum, int cid) {
        return httpRemoteSource.getKnowledgeTreeDetailData(pageNum, cid);
    }

    @Override
    public Observable<ResponseBody<List<NavigationListData>>> getNavigationListData() {
        return httpRemoteSource.getNavigationListData();
    }

    @Override
    public Observable<ResponseBody<List<ProjectClassifyData>>> getProjectClassifyData() {
        return httpRemoteSource.getProjectClassifyData();
    }

    @Override
    public Observable<ResponseBody<ProjectListData>> getProjectListData(int pageNum, int cid) {
        return httpRemoteSource.getProjectListData(pageNum, cid);
    }

    @Override
    public Observable<ResponseBody<LoginData>> postLoginData(String username, String password) {
        return httpRemoteSource.postLoginData(username, password);
    }

    @Override
    public Observable<ResponseBody<LoginData>> postSignUpData(String username, String password, String repassword) {
        return httpRemoteSource.postSignUpData(username, password, repassword);
    }

    @Override
    public Observable<ResponseBody<String>> getSignOut() {
        return httpRemoteSource.getSignOut();
    }

    @Override
    public Observable<ResponseBody<CollectListData>> getCollectListData(int pageNum) {
        return httpRemoteSource.getCollectListData(pageNum);
    }

    @Override
    public Observable<ResponseBody<String>> addCollectInsideListData(int articleId) {
        return httpRemoteSource.addCollectInsideListData(articleId);
    }

    @Override
    public Observable<ResponseBody<CollectData>> addCollectOutsideListData(String title, String author, String link) {
        return httpRemoteSource.addCollectOutsideListData(title, author, link);
    }

    @Override
    public Observable<ResponseBody<String>> cancelCollectArticleListData(int articleId) {
        return httpRemoteSource.cancelCollectArticleListData(articleId);
    }

    @Override
    public Observable<ResponseBody<String>> cancelCollectArticlePageData(int articleId, int originId) {
        return httpRemoteSource.cancelCollectArticlePageData(articleId, originId);
    }

    @Override
    public Observable<ResponseBody<List<WebBookMark>>> getWebBookMark() {
        return httpRemoteSource.getWebBookMark();
    }

    @Override
    public Observable<ResponseBody<WebBookMark>> addWebBookMark(String name, String link) {
        return httpRemoteSource.addWebBookMark(name, link);
    }

    @Override
    public Observable<ResponseBody<WebBookMark>> updateWebBookMark(int id, String name, String link) {
        return httpRemoteSource.updateWebBookMark(id, name, link);
    }

    @Override
    public Observable<ResponseBody<String>> deleteWebBookMark(int id) {
        return httpRemoteSource.deleteWebBookMark(id);
    }

    @Override
    public Observable<ResponseBody<HomeArticleListData>> getSearchKeyWordData(int pageNum, String keyWord) {
        return httpRemoteSource.getSearchKeyWordData(pageNum, keyWord);
    }

    @Override
    public Observable<ResponseBody<List<KnowledgeHierarchyData>>> getWxArticle() {
        return httpRemoteSource.getWxArticle();
    }

    @Override
    public Observable<ResponseBody<HomeArticleListData>> getWxArticleHistory(int id, int pageNum) {
        return httpRemoteSource.getWxArticleHistory(id, pageNum);
    }

    @Override
    public Observable<ResponseBody<HomeArticleListData>> getWxArticleHistoryByKey(int id, int pageNum, String key) {
        return httpRemoteSource.getWxArticleHistoryByKey(id, pageNum, key);
    }

    @Override
    public Observable<LoginStatusEvent> getLoginStatus() {
        return sharedPreferenceSource.getLoginStatus();
    }

    @Override
    public void setLoginStatus(LoginStatusEvent event) { }

    @Override
    public Observable<ModeNightEvent> getModeNight() {
        return sharedPreferenceSource.getModeNight();
    }

    @Override
    public void setModeNight(ModeNightEvent event) {
        sharedPreferenceSource.setModeNight(event);
    }
}
