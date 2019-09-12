package com.zhixiao.wanandroid.presenter.main;


import android.Manifest;
import android.app.Activity;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhixiao.wanandroid.app.APP;
import com.zhixiao.wanandroid.base.presenter.AbstractBasePresenter;
import com.zhixiao.wanandroid.component.RxBus;
import com.zhixiao.wanandroid.component.event.LoginStatusEvent;
import com.zhixiao.wanandroid.component.event.ModeNightEvent;
import com.zhixiao.wanandroid.model.DataRepository;
import com.zhixiao.wanandroid.model.bean.ResponseBody;
import com.zhixiao.wanandroid.model.bean.home.HomeArticleListData;
import com.zhixiao.wanandroid.utils.LogUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName: MainPresenter
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/4
 */
public class MainPresenter extends AbstractBasePresenter<MainContract.View> implements MainContract.Presenter{
    private DataRepository dataRepository;

    @Override
    public void changeModeNight(boolean night) {

    }

    @Override
    public void setModeNight(boolean checked) {
        ModeNightEvent event = new ModeNightEvent(checked);
        RxBus.getInstance().post(event);
        dataRepository.setModeNight(event);
    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
        dataRepository = APP.getInstance().getDataRepository();
        addDisposable(dataRepository.HomeArticleListData(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody<HomeArticleListData>>() {
                    @Override
                    public void accept(ResponseBody<HomeArticleListData> homeArticleListDataResponseBody) throws Exception {
                        LogUtil.i(homeArticleListDataResponseBody.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.i(throwable.toString());
                    }
                })
        );
    }
}
