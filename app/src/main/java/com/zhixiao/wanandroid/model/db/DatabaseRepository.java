package com.zhixiao.wanandroid.model.db;

import com.zhixiao.wanandroid.model.dao.DaoSession;

/**
 * @ClassName: DatabaseRepository
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/5
 */
public class DatabaseRepository implements DatabaseSource {
    private DaoSession daoSession;

    public DatabaseRepository(DaoSession daoSession) {
        this.daoSession = daoSession;
    }
}
