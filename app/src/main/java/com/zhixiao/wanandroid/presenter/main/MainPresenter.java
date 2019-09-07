package com.zhixiao.wanandroid.presenter.main;


import android.Manifest;
import android.app.Activity;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhixiao.wanandroid.app.APP;
import com.zhixiao.wanandroid.base.presenter.AbstractBasePresenter;
import com.zhixiao.wanandroid.model.bean.ResponseBody;
import com.zhixiao.wanandroid.model.bean.home.HomeArticleListData;
import com.zhixiao.wanandroid.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName: MainPresenter
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/4
 */
public class MainPresenter extends AbstractBasePresenter<MainContract.View> implements MainContract.Presenter{

    @Override
    public void changeModeNight(boolean night) {

    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
        addDisposable(APP.getInstance().getDataRepository().HomeArticleListData(0)
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
                }));

    }
}
