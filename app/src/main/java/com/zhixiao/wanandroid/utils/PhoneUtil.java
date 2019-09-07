package com.zhixiao.wanandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.zhixiao.wanandroid.app.APP;

/**
 * @ClassName: PhoneUtil
 * @Description: 手机的工具类
 * @Author: zhixiao
 * @CreateDate: 2019/9/6
 */
public class PhoneUtil {
    /**
     * 检查是否有可用网络
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) APP.getInstance()
                .getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
