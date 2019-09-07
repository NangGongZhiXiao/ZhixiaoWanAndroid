package com.zhixiao.wanandroid.component.event;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * @ClassName: ModeNightEvent
 * @Description: 是否为黑夜模式的事件
 * @Author: zhixiao
 * @CreateDate: 2019/9/5
 */
public class ModeNightEvent {
    private boolean night;

    public ModeNightEvent(boolean night) {
        this.night = night;
    }

    public int getMode(){
        return night ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }
}
