package com.zhixiao.wanandroid.view.main;

import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jaeger.library.StatusBarUtil;
import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.base.view.MVPBaseActivity;
import com.zhixiao.wanandroid.presenter.main.MainContract;
import com.zhixiao.wanandroid.presenter.main.MainPresenter;
import com.zhixiao.wanandroid.view.login.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends MVPBaseActivity<MainContract.Presenter>
        implements MainContract.View {

    @BindView(R.id.tb_main)
    public Toolbar toolbar;
    @BindView(R.id.vp_main)
    public ViewPager viewPager;

    @Override
    public MainContract.Presenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_background2), 0);
        StatusBarUtil.setLightMode(this);
        setSwipeBackEnable(false);
        setSupportActionBar(toolbar);

        viewPager.setAdapter(fragmentPagerAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void startLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private Class[] fragmentClazzs = new Class[]{ArticleFragment.class, ProjectFragment.class,
            NavigationFragment.class, MeFragment.class};
    private FragmentPagerAdapter fragmentPagerAdapter =
            new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            try {
                return (Fragment) fragmentClazzs[position].newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                return new ArticleFragment();
            }
        }

        @Override
        public int getCount() {
            return fragmentClazzs.length;
        }
    };

}
