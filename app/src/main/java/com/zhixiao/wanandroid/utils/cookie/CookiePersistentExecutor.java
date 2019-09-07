package com.zhixiao.wanandroid.utils.cookie;

import android.content.SharedPreferences;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @ClassName: CookiePersistentExecutor
 * @Description: 用于持久化存储cookie的处理
 * @Author: zhixiao
 * @CreateDate: 2019/9/6
 */
public class CookiePersistentExecutor implements CookieJar {
    private final PersistentCookieStore persistentCookieStore;
    private static class Holder{
        static CookiePersistentExecutor instance = new CookiePersistentExecutor();
    }
    public static CookiePersistentExecutor getInstance(){
        return Holder.instance;
    }

    public CookiePersistentExecutor() {
        persistentCookieStore = new PersistentCookieStore();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        persistentCookieStore.add(url, cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return persistentCookieStore.get(url);
    }

    public void cleanAllCookie(){
        persistentCookieStore.removeAll();
    }
}
