package com.zhixiao.wanandroid.base.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zhixiao.wanandroid.base.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * @ClassName: AbstractBaseFragment
 * @Description: 实现了滑动返回，ButterKnife的生命周期绑定
 * @Author: zhixiao
 * @CreateDate: 2019/9/4
 */
public abstract class AbstractBaseFragment<T extends BasePresenter> extends SwipeBackFragment {
    private Unbinder unbinder;
    private View view;
    protected T presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initEventAndData();
        presenter = createPresenter();
        presenter.attachView((BaseView) this);
        return attachToSwipeBack(view);
    }

    protected abstract T createPresenter();

    /**
     * 初始化数据
     */
    protected abstract void initEventAndData();

    /**
     * 初始化view
     */
    protected abstract void initView();

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        unbinder = null;
    }

    /**
     * 返回布局的id
     * @return
     */
    protected abstract int getLayoutId();
}
