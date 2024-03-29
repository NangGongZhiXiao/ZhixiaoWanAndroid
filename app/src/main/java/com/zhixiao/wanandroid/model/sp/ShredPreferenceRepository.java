package com.zhixiao.wanandroid.model.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhixiao.wanandroid.app.APP;
import com.zhixiao.wanandroid.app.Constants;
import com.zhixiao.wanandroid.component.event.LoginStatusEvent;
import com.zhixiao.wanandroid.component.event.ModeNightEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @ClassName: ShredPreferenceRepository
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/5
 */
public class ShredPreferenceRepository implements ShredPreferenceSource {
    private static final String LOGIN_STATUS = "login_status";
    private static final String MODE_NIGHT_STATUS = "mode_night_status";

    private SharedPreferences sharedPreferences;

    public ShredPreferenceRepository() {
        sharedPreferences = APP.getInstance()
                .getSharedPreferences(Constants.SHARED_PREFERENCE_REPOSITORY, Context.MODE_PRIVATE);
    }

    @Override
    public Observable<LoginStatusEvent> getLoginStatus() {
        return Observable.create(new ObservableOnSubscribe<LoginStatusEvent>() {
            @Override
            public void subscribe(ObservableEmitter<LoginStatusEvent> emitter) throws Exception {
                LoginStatusEvent event = new LoginStatusEvent(
                        sharedPreferences.getBoolean(LOGIN_STATUS, false));
                emitter.onNext(event);
            }
        });
    }

    @Override
    public void setLoginStatus(LoginStatusEvent event) {
        sharedPreferences.edit().putBoolean(LOGIN_STATUS, event.isLogin()).apply();
    }

    @Override
    public Observable<ModeNightEvent> getModeNight() {
        return Observable.just(new ModeNightEvent(
                sharedPreferences.getBoolean(MODE_NIGHT_STATUS, false)));
    }

    @Override
    public void setModeNight(ModeNightEvent event) {
        sharedPreferences.edit().putBoolean(MODE_NIGHT_STATUS, event.isNight()).apply();
    }
}
