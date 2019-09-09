package com.zhixiao.wanandroid.model.sp;

import com.zhixiao.wanandroid.component.event.LoginStatusEvent;
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
     * 获的本地的登录状态
     * @return
     */
    Observable<LoginStatusEvent> getLoginStatus();

    void setLoginStatus(LoginStatusEvent event);
}
