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
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @ClassName: MVPBaseFragmentWithoutSwipeBack
 * @Description:
 *              在不需要swipeBack的地方使用mvp模式的fragment
 *              例如在viewPager使用时fragment的view会被swipeLayout替换，过宽的view会遮盖左边的页
 * @Author: zhixiao
 * @CreateDate: 2019/9/18
 */
public abstract class MVPBaseFragmentWithoutSwipeBack<T extends BasePresenter>
        extends SupportFragment implements BaseView<T>  {
    private Unbinder unbinder;
    protected View view;
    protected T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initEventAndData();
        return view;
    }

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
        presenter.detachView();
    }

    /**
     * 返回布局的id
     * @return
     */
    protected abstract int getLayoutId();

    public final void setNightMode(boolean night){
    }

    public final boolean getNightMode(){
        return false;
    }
}
