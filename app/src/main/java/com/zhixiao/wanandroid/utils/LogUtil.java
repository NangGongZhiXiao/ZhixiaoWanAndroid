package com.zhixiao.wanandroid.utils;

import android.util.Log;

import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogStrategy;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zhixiao.wanandroid.BuildConfig;
import com.zhixiao.wanandroid.app.APP;

/**
 * @ClassName: LogUtil
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/4
 */
public class LogUtil {
    private static final String TAG = "zhixiao";

    /**
     * 初始化log
     * 只在debug的时候打印
     */
    public static void init(){
        FormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(1)
                .methodOffset(0)
                .tag(TAG)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    public static void v(String s, Object... args){
        Logger.v(s, args);
    }

    public static void d(String s, Object... args){
        Logger.d(s, args);
    }

    public static void i(String s, Object... args){
        Logger.i(s, args);
    }

    public static void w(String s, Object... args){
        Logger.w(s, args);
    }

    public static void e(String s, Object... args){
        Logger.e(s, args);
    }

    public static void a(String s, Throwable throwable){
        Logger.log(Logger.ASSERT, TAG, s, throwable);
    }
}
