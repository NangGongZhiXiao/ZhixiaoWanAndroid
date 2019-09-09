package com.zhixiao.wanandroid.base.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;


import com.zhixiao.wanandroid.base.presenter.BasePresenter;
import com.zhixiao.wanandroid.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;


/**
 * @ClassName: AbstractBaseActivity
 * @Description: 实现了滑动返回，ButterKnife的生命周期绑定
 * @Author: zhixiao
 * @CreateDate: 2019/9/4
 */
public abstract class AbstractBaseActivity extends SwipeBackActivity {
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initView();
        initEventAndData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        unbinder = null;
    }

    /**
     * 初始化数据
     */
    protected abstract void initEventAndData();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 得到布局文件的id
     * @return
     */
    protected abstract int getLayoutId();

    public final void setNightMode(boolean night){
        LogUtil.i("set night mode " + night);
        AppCompatDelegate.setDefaultNightMode(night ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        recreate();
    }

    public final boolean getNightMode(){
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    }
}
