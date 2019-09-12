package com.zhixiao.wanandroid.model.sp;

import com.zhixiao.wanandroid.component.event.LoginStatusEvent;
import com.zhixiao.wanandroid.component.event.ModeNightEvent;
import com.zhixiao.wanandroid.model.DataSource;

import io.reactivex.Observable;

/**
 * @ClassName: ShredPreferenceSource
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/5
 */
public interface ShredPreferenceSource extends DataSource {
    /**
     * 获得本地的登录状态
     * @return
     */
    Observable<LoginStatusEvent> getLoginStatus();

    /**
     * 设置本地的登录状态
     * @param event
     */
    void setLoginStatus(LoginStatusEvent event);

    /**
     * 获得配置的夜间模式
     * @return
     */
    Observable<ModeNightEvent> getModeNight();

    /**
     * 配置是否夜间模式
     * @param event
     */
    void setModeNight(ModeNightEvent event);
}
