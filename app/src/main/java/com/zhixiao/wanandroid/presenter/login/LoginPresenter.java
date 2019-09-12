package com.zhixiao.wanandroid.presenter.login;

import com.zhixiao.wanandroid.app.APP;
import com.zhixiao.wanandroid.base.presenter.AbstractBasePresenter;
import com.zhixiao.wanandroid.component.event.LoginStatusEvent;
import com.zhixiao.wanandroid.model.DataRepository;
import com.zhixiao.wanandroid.model.bean.ResponseBody;
import com.zhixiao.wanandroid.model.bean.login.LoginData;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName: LoginPresenter
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/9
 */
public class LoginPresenter extends AbstractBasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(String name, String password) {
        DataRepository dataRepository = APP.getInstance().getDataRepository();
        addDisposable(dataRepository.postLoginData(name, password)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody<LoginData>, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(ResponseBody<LoginData> loginDataResponseBody) throws Exception {
                        return Observable.just(loginDataResponseBody.getErrorCode()==0);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        dataRepository.setLoginStatus(new LoginStatusEvent(aBoolean));
                        if (aBoolean) {
                            view.loginSucceed();
                        } else {
                            view.loginFailed();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.networkError();
                    }
                })
        );
    }
}
