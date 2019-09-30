package com.zhixiao.wanandroid.view.main;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.adapter.ArticleListAdapter;
import com.zhixiao.wanandroid.base.view.MVPBaseFragmentWithoutSwipeBack;
import com.zhixiao.wanandroid.model.bean.home.HomeArticleListData;
import com.zhixiao.wanandroid.model.bean.knowlegetree.KnowledgeHierarchyData;
import com.zhixiao.wanandroid.presenter.main.ArticleContract;
import com.zhixiao.wanandroid.presenter.main.ArticlePresenter;
import com.zhixiao.wanandroid.utils.LogUtil;
import com.zhixiao.wanandroid.widget.DropDownTabLayout;

import java.util.Map;

import butterknife.BindView;

/**
 * @ClassName: ArticleFragment
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/18
 */
public class ArticleFragment extends MVPBaseFragmentWithoutSwipeBack<ArticleContract.Presenter>
        implements ArticleContract.View, DropDownTabLayout.OnTabSelectedListener {

    private DropDownTabLayout.Adapter viewPagerAdapter;
    private KnowledgeHierarchyData articleListName;
    private Map<Integer, ? extends CharSequence> artLNGroupTitle;
    private SparseArray<ArticleListAdapter> articleListAdapters;

    @BindView(R.id.tl_article)
    public DropDownTabLayout dropDownTabLayout;
    @BindView(R.id.vp_article)
    public ViewPager vpArticle;

    @Override
    protected void initEventAndData() {
        articleListAdapters = new SparseArray<>();
    }

    @Override
    protected void initView() {
        dropDownTabLayout.setupWithViewPager(vpArticle);
        dropDownTabLayout.setOnTabSelectedListener(this);
        vpArticle.setAdapter(viewPagerAdapter = getViewPagerAdapter());
    }

    private DropDownTabLayout.Adapter getViewPagerAdapter() {
        return viewPagerAdapter = new ArticleListNamesAdapter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article;
    }

    @Override
    public ArticleContract.Presenter createPresenter() {
        return new ArticlePresenter();
    }

    @Override
    public void showNotData() {
        Toast.makeText(getActivity(), "not data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateArticleListNames(KnowledgeHierarchyData data, Map<Integer, ? extends CharSequence> groupTitle) {
        articleListName = data;
        artLNGroupTitle = groupTitle;
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void updateArticleList(HomeArticleListData data, int position) {
        LogUtil.i("update article list " + data);
        ArticleListAdapter adapter = articleListAdapters.get(position);
        if(adapter != null){
            adapter.setNewData(data.getDatas());
            viewPagerAdapter.notifyDataSetChanged();
            TabLayout tabLayout = dropDownTabLayout.getTabLayout();
            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    TabLayout.Tab selectedTab = tabLayout.getTabAt(
                            tabLayout.getSelectedTabPosition());
                    if (selectedTab != null) {
                        selectedTab.select();
                    }
                }
            });
        }
    }

    @Override
    public void onTabSelectedListener(TextView view, int position) {
        dropDownTabLayout.setDropDown(false);
        if(articleListAdapters.get(position) == null || articleListAdapters.get(position).getData().isEmpty()) {
            presenter.loadArticleList(articleListName.getChildren().get(position).getId(), 0, position);
        }
    }

    class ArticleListNamesAdapter extends DropDownTabLayout.Adapter{
        @Override
        protected CharSequence getGroupTitle(int position) {
            if(artLNGroupTitle != null){
                CharSequence title;
                if((title = artLNGroupTitle.get(position)) != null){
                    return title;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return articleListName == null ? 0 :
                    (articleListName.getChildren() == null ? 0 :
                            articleListName.getChildren().size());
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//                TextView textView = new TextView(getActivity());
//                textView.setText(articleListName.getChildren().get(position).getName());
//                container.addView(textView);
            RecyclerView recyclerView = new RecyclerView(getContext());
            ArticleListAdapter articleListAdapter;
            recyclerView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if(articleListAdapters.get(position) == null){
                articleListAdapter = new ArticleListAdapter(null);
                articleListAdapters.append(position, articleListAdapter);
            }else{
                articleListAdapter = articleListAdapters.get(position);
            }
            recyclerView.setAdapter(articleListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            container.addView(recyclerView);
            return recyclerView;
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
            return articleListName.getChildren().get(position).getName();
        }
    }
}
