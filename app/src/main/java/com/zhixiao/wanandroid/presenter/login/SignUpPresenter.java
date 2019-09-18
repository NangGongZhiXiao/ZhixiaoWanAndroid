package com.zhixiao.wanandroid.presenter.login;

import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.app.APP;
import com.zhixiao.wanandroid.base.presenter.AbstractBasePresenter;
import com.zhixiao.wanandroid.model.bean.ResponseBody;
import com.zhixiao.wanandroid.model.bean.login.LoginData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName: SignUpPresenter
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/12
 */
public class SignUpPresenter extends AbstractBasePresenter<SignUpContract.View>
        implements SignUpContract.Presenter {

    @Override
    public void detachView() {

    }

    @Override
    public void signUp(String name, String password, String rePassword) {
        addDisposable(APP.getInstance().getDataRepository()
                .postSignUpData(name, password, rePassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody<LoginData>>() {
                    @Override
                    public void accept(ResponseBody<LoginData> loginDataResponseBody) throws Exception {
                        if(loginDataResponseBody.getErrorCode() == 0){
                            view.signUpSuccessful();
                        }else{
                            view.signUpFailed(loginDataResponseBody.getErrorMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.signUpFailed(APP.getInstance().getResources().getString(R.string.sign_up_failed));
                    }
                })
        );
    }
}
