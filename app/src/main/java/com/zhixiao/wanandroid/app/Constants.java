package com.zhixiao.wanandroid.app;

import java.io.File;

/**
 * @ClassName: Constants
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/4
 */
public class Constants {
    /**
     * 数据库名
     */
    public static final String DATABASE_NAME = "zhixiao_wanandroid.db";
    /**
     * 数据缓存文件夹路径
     */
    public static final String PATH_DATA = APP.getInstance().getCacheDir().getAbsolutePath() + "/data";
    /**
     * http缓存的路径
     */
    public static final String PATH_HTTP_CACHE = PATH_DATA + "/HttpCache";
    /**
     * http超时时间，ms
     */
    public static final long HTTP_TIME_OUT = 5000;
    /**
     * http连接超时时间，m
     */
    public static final long HTTP_CONNECT_TIME_OUT = 30;
    /**
     * http读写超时时间，m
     */
    public static final long HTTP_RW_TIME_OUT = 30;
}
