package com.zhixiao.wanandroid.app;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatDelegate;

import com.zhixiao.wanandroid.component.event.ModeNightEvent;
import com.zhixiao.wanandroid.model.DataRepository;
import com.zhixiao.wanandroid.model.bean.SearchHistoryData;
import com.zhixiao.wanandroid.model.dao.DaoMaster;
import com.zhixiao.wanandroid.model.dao.DaoSession;
import com.zhixiao.wanandroid.model.dao.SearchHistoryDataDao;
import com.zhixiao.wanandroid.utils.DataRepositoryUtils;
import com.zhixiao.wanandroid.utils.LogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * @ClassName: APP
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/4
 */
public class APP extends Application {
    private static APP instance;
    private DaoSession daoSession;
    private DataRepository dataRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 各部分初始化
        LogUtil.init();
        initGreenDao();
        dataRepository = DataRepositoryUtils.provideDataRepository();
        initModeNight();
    }

    private void initModeNight() {
        dataRepository.getModeNight().subscribe(new Observer<ModeNightEvent>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(ModeNightEvent modeNightEvent) {
                AppCompatDelegate.setDefaultNightMode(modeNightEvent.isNight() ?
                        AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                disposable.dispose();
            }
            @Override
            public void onError(Throwable e) { }
            @Override
            public void onComplete() { }
        });
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

    public DataRepository getDataRepository() {
        return dataRepository;
    }
}
