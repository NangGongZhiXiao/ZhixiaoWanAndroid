package com.zhixiao.wanandroid.base.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zhixiao.wanandroid.base.presenter.BasePresenter;

/**
 * @ClassName: MVPBaseFragment
 * @Description: 支持mvp的baseFragment
 * @Author: zhixiao
 * @CreateDate: 2019/9/16
 */
public abstract class MVPBaseFragment<T extends BasePresenter> extends AbstractBaseFragment implements BaseView<T>{
    protected T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
