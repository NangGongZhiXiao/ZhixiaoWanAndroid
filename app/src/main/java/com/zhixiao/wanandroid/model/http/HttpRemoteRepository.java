package com.zhixiao.wanandroid.model.http;

import com.zhixiao.wanandroid.BuildConfig;
import com.zhixiao.wanandroid.app.Constants;
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
import com.zhixiao.wanandroid.utils.PhoneUtil;
import com.zhixiao.wanandroid.utils.cookie.CookiePersistentExecutor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @ClassName: HttpRemoteRepository
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/5
 */
public class HttpRemoteRepository implements HttpRemoteSource {
    private HttpApi api;
    public HttpRemoteRepository() {
        api = new Retrofit.Builder()
                .client(getOkHttpClient())
                // 使用rxjava的方式调用
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpApi.HOST)
                .build().create(HttpApi.class);
    }

    private OkHttpClient getOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // debug模式下添加日志拦截器
        if(BuildConfig.DEBUG){
            builder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BASIC));
        }
        Interceptor netCacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!PhoneUtil.isNetworkConnected()) {
                    // 网络不可用，强制使用缓存
                    request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);
                if (PhoneUtil.isNetworkConnected()) {
                    // 有网络，设置超时设为0，即始终从网络读取
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=0")
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 没有网络,缓存一周
                    int maxStale = 60 * 60 * 24 * 7;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        // 添加缓存拦截器
        // todo 不知为何该缓存拦截器没有效果
//        builder.addInterceptor(netCacheInterceptor);
        // 最大50M的缓存容量
        builder.cache(new Cache(new File(Constants.PATH_HTTP_CACHE), 1024*1024*50))
                .callTimeout(Constants.HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(Constants.HTTP_CONNECT_TIME_OUT, TimeUnit.MINUTES)
                .readTimeout(Constants.HTTP_RW_TIME_OUT, TimeUnit.MINUTES)
                .writeTimeout(Constants.HTTP_RW_TIME_OUT, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                // cookie持久化的处理
                .cookieJar(CookiePersistentExecutor.getInstance());
        return builder.build();
    }

    @Override
    public Observable<ResponseBody<HomeArticleListData>> HomeArticleListData(int pageNum) {
        return api.HomeArticleListData(pageNum);
    }

    @Override
    public Observable<ResponseBody<List<HomePageBannerModel>>> GetHomePageBannerData() {
        return api.GetHomePageBannerData();
    }

    @Override
    public Observable<ResponseBody<HomeArticleListData>> HomeArticleListProjectData(int pageNum) {
        return api.HomeArticleListProjectData(pageNum);
    }

    @Override
    public Observable<ResponseBody<List<CommonWebData>>> GetFriendUseWebData() {
        return api.GetFriendUseWebData();
    }

    @Override
    public Observable<ResponseBody<List<HotKeyData>>> GetHotKeyData() {
        return api.GetHotKeyData();
    }

    @Override
    public Observable<ResponseBody<List<HomeArticleData>>> HomeTopArticleData() {
        return api.HomeTopArticleData();
    }

    @Override
    public Observable<ResponseBody<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData() {
        return api.getKnowledgeHierarchyData();
    }

    @Override
    public Observable<ResponseBody<HomeArticleListData>> getKnowledgeTreeDetailData(int pageNum, int cid) {
        return api.getKnowledgeTreeDetailData(pageNum, cid);
    }

    @Override
    public Observable<ResponseBody<List<NavigationListData>>> getNavigationListData() {
        return api.getNavigationListData();
    }

    @Override
    public Observable<ResponseBody<List<ProjectClassifyData>>> getProjectClassifyData() {
        return api.getProjectClassifyData();
    }

    @Override
    public Observable<ResponseBody<ProjectListData>> getProjectListData(int pageNum, int cid) {
        return api.getProjectListData(pageNum, cid);
    }

    @Override
    public Observable<ResponseBody<LoginData>> postLoginData(String username, String password) {
        return api.postLoginData(username, password);
    }

    @Override
    public Observable<ResponseBody<LoginData>> postSignUpData(String username, String password, String repassword) {
        return api.postSignUpData(username, password, repassword);
    }

    @Override
    public Observable<ResponseBody<String>> getSignOut() {
        return api.getSignOut();
    }

    @Override
    public Observable<ResponseBody<CollectListData>> getCollectListData(int pageNum) {
        return api.getCollectListData(pageNum);
    }

    @Override
    public Observable<ResponseBody<String>> addCollectInsideListData(int articleId) {
        return api.getCollectInsideListData(articleId);
    }

    @Override
    public Observable<ResponseBody<CollectData>> addCollectOutsideListData(String title, String author, String link) {
        return api.getCollectOutsideListData(title, author, link);
    }

    @Override
    public Observable<ResponseBody<String>> cancelCollectArticleListData(int articleId) {
        return api.cancelCollectArticleListData(articleId);
    }

    @Override
    public Observable<ResponseBody<String>> cancelCollectArticlePageData(int articleId, int originId) {
        return api.cancelCollectArticlePageData(articleId, originId);
    }

    @Override
    public Observable<ResponseBody<List<WebBookMark>>> getWebBookMark() {
        return api.getWebBookMark();
    }

    @Override
    public Observable<ResponseBody<WebBookMark>> addWebBookMark(String name, String link) {
        return api.addWebBookMark(name, link);
    }

    @Override
    public Observable<ResponseBody<WebBookMark>> updateWebBookMark(int id, String name, String link) {
        return api.updateWebBookMark(id, name, link);
    }

    @Override
    public Observable<ResponseBody<String>> deleteWebBookMark(int id) {
        return api.deleteWebBookMark(id);
    }

    @Override
    public Observable<ResponseBody<HomeArticleListData>> getSearchKeyWordData(int pageNum, String keyWord) {
        return api.getSearchKeyWordData(pageNum, keyWord);
    }

    @Override
    public Observable<ResponseBody<List<KnowledgeHierarchyData>>> getWxArticle() {
        return api.getWxArticle();
    }

    @Override
    public Observable<ResponseBody<HomeArticleListData>> getWxArticleHistory(int id, int pageNum) {
        return api.getWxArticleHistory(id, pageNum);
    }

    @Override
    public Observable<ResponseBody<HomeArticleListData>> getWxArticleHistoryByKey(int id, int pageNum, String key) {
        return api.getWxArticleHistoryByKey(id, pageNum, key);
    }
}
