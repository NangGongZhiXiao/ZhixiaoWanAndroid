package com.zhixiao.wanandroid.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.zhixiao.wanandroid.app.APP;
import com.zhixiao.wanandroid.model.DataRepository;
import com.zhixiao.wanandroid.model.db.DatabaseRepository;
import com.zhixiao.wanandroid.model.http.HttpRemoteRepository;
import com.zhixiao.wanandroid.model.sp.ShredPreferenceRepository;

/**
 * @ClassName: DataRepositoryUtils
 * @Description: 提供数据仓库的生成
 * @Author: zhixiao
 * @CreateDate: 2019/9/5
 */
public class DataRepositoryUtils {
    public static DataRepository provideDataRepository(){
        return DataRepository.getInstance(new DatabaseRepository(APP.getInstance().getDaoSession()),
                new ShredPreferenceRepository(),
                new HttpRemoteRepository());
    }
}
