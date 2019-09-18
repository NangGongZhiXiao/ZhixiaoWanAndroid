package com.zhixiao.wanandroid.base.presenter;

import androidx.appcompat.app.AppCompatDelegate;

import com.zhixiao.wanandroid.base.view.BaseView;
import com.zhixiao.wanandroid.component.RxBus;
import com.zhixiao.wanandroid.component.event.ModeNightEvent;
import com.zhixiao.wanandroid.utils.LogUtil;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @ClassName: AbstractBasePresenter
 * @Description: 实现对disposable的取消订阅防止内存泄漏,对view的绑定
 * @Author: zhixiao
 * @CreateDate: 2019/9/6
 */
public abstract class AbstractBasePresenter<T extends BaseView> implements BasePresenter<T> {
    /**
     * 一个disposable的容器，可以容纳多个disposable 防止订阅之后没有取消订阅的内存泄漏
     */
    private CompositeDisposable compositeDisposable;
    protected T view;



    protected void addDisposable(Disposable disposable){
        if(compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void attachView(T view) {
        this.view = view;
        // 注册夜间模式的监听
        addDisposable(RxBus.getInstance().toObservable(ModeNightEvent.class)
                .subscribe(new Consumer<ModeNightEvent>() {
            @Override
            public void accept(ModeNightEvent modeNightEvent) throws Exception {
                LogUtil.i("accept night mode event " + modeNightEvent);
                view.setNightMode(modeNightEvent.isNight());
            }
        }));
    }

    @Override
    public void detachView() {
        view = null;
        if(compositeDisposable != null){
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    protected T getView() {
        return view;
    }
}
