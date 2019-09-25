package com.zhixiao.wanandroid.view.main;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.jaeger.library.StatusBarUtil;
import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.base.view.MVPBaseActivity;
import com.zhixiao.wanandroid.presenter.main.MainContract;
import com.zhixiao.wanandroid.presenter.main.MainPresenter;
import com.zhixiao.wanandroid.view.login.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends MVPBaseActivity<MainContract.Presenter>
        implements MainContract.View, ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.tb_main)
    public Toolbar toolbar;
    @BindView(R.id.vp_main)
    public ViewPager viewPager;
    @BindView(R.id.bnv_main)
    public BottomNavigationView navigationView;

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
        viewPager.addOnPageChangeListener(this);
        navigationView.setOnNavigationItemSelectedListener(this);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
    @Override
    public void onPageSelected(int position) {
        navigationView.getMenu().getItem(position).setChecked(true);
    }
    @Override
    public void onPageScrollStateChanged(int state) { }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.item_article:
                viewPager.setCurrentItem(0);
                break;
            case R.id.item_project:
                viewPager.setCurrentItem(1);
                break;
            case R.id.item_navigation:
                viewPager.setCurrentItem(2);
                break;
            case R.id.item_me:
                viewPager.setCurrentItem(3);
                break;
        }
        return true;
    }
}
