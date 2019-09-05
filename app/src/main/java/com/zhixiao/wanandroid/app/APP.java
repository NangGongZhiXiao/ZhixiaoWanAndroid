package com.zhixiao.wanandroid.app;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.zhixiao.wanandroid.model.bean.SearchHistoryData;
import com.zhixiao.wanandroid.model.dao.DaoMaster;
import com.zhixiao.wanandroid.model.dao.DaoSession;
import com.zhixiao.wanandroid.model.dao.SearchHistoryDataDao;
import com.zhixiao.wanandroid.utils.LogUtil;


/**
 * @ClassName: APP
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/4
 */
public class APP extends Application {
    private static APP instance;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtil.init();
        initGreenDao();
    }

    public static APP getInstance() {
        return instance;
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
