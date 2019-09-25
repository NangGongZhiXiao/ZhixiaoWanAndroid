package com.zhixiao.wanandroid.view.main;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.base.view.MVPBaseFragmentWithoutSwipeBack;
import com.zhixiao.wanandroid.presenter.main.ArticleContract;
import com.zhixiao.wanandroid.presenter.main.ArticlePresenter;
import com.zhixiao.wanandroid.widget.DropDownTabLayout;

import butterknife.BindView;

/**
 * @ClassName: ArticleFragment
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/18
 */
public class ArticleFragment extends MVPBaseFragmentWithoutSwipeBack<ArticleContract.Presenter>
        implements ArticleContract.View{

    @BindView(R.id.tl_article)
    public DropDownTabLayout tbArticle;
    @BindView(R.id.vp_article)
    public ViewPager vpArticle;

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {
        tbArticle.setupWithViewPager(vpArticle);
        vpArticle.setAdapter(new DropDownTabLayout.Adapter() {
            @Override
            protected CharSequence getGroupTitle(int position) {
                if(position == 0 || position == 3){
                    return "group title" + position;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 9;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TextView textView = new TextView(getActivity());
                textView.setText("i am " + position);
                container.addView(textView);
                return textView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view.equals(object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return position==2 ? "title" + position : "long title " + position;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article;
    }

    @Override
    public ArticleContract.Presenter createPresenter() {
        return new ArticlePresenter();
    }
}
