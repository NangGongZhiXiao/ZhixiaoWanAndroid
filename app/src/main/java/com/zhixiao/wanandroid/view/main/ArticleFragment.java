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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.adapter.ArticleListAdapter;
import com.zhixiao.wanandroid.base.view.MVPBaseFragmentWithoutSwipeBack;
import com.zhixiao.wanandroid.model.bean.home.HomeArticleListData;
import com.zhixiao.wanandroid.model.bean.knowlegetree.KnowledgeHierarchyData;
import com.zhixiao.wanandroid.presenter.main.ArticleContract;
import com.zhixiao.wanandroid.presenter.main.ArticlePresenter;
import com.zhixiao.wanandroid.utils.LogUtil;
import com.zhixiao.wanandroid.widget.DropDownTabLayout;

import java.lang.ref.WeakReference;
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
    private SparseArray<WeakReference<SmartRefreshLayout>> refreshLayoutRefs;

    @BindView(R.id.tl_article)
    public DropDownTabLayout dropDownTabLayout;
    @BindView(R.id.vp_article)
    public ViewPager vpArticle;

    @Override
    protected void initEventAndData() {
        articleListAdapters = new SparseArray<>();
        refreshLayoutRefs = new SparseArray<>();
    }

    @Override
    protected void initView() {
        dropDownTabLayout.setupWithViewPager(vpArticle);
        dropDownTabLayout.setOnTabSelectedListener(this);
        vpArticle.setAdapter(getViewPagerAdapter());
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
        pagerAdapterNotifyDataSetChanged();
    }



    private void pagerAdapterNotifyDataSetChanged() {
        viewPagerAdapter.notifyDataSetChanged();
        // notifyDataSetChanged之后会造成TabLayout显示位置的错误
        // 需要重新选择使其回到正确的位置
        TabLayout tabLayout = dropDownTabLayout.getTabLayout();
        tabLayout.post(() -> {
            TabLayout.Tab selectedTab = tabLayout.getTabAt(
                    tabLayout.getSelectedTabPosition());
            if (selectedTab != null) {
                selectedTab.select();
            }
        });
    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void updateArticleList(HomeArticleListData data, int position) {
        LogUtil.i("update article list " + data);
        if(data == null) return;
        SmartRefreshLayout refreshLayout = refreshLayoutRefs.get(position).get();
        ArticleListAdapter adapter = articleListAdapters.get(position);
        if(adapter != null){
            if(data.getCurPage() == 1) {
                adapter.setNewData(data.getDatas());
                if(refreshLayout != null){
                    refreshLayout.finishRefresh();
                    if(data.getCurPage() >= data.getPageCount()){
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }else{
                adapter.addData(data.getDatas());
                if(refreshLayout != null){
                    refreshLayout.finishLoadMore();
                    if(data.getCurPage() >= data.getPageCount()){
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
            pagerAdapterNotifyDataSetChanged();
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
            SmartRefreshLayout refreshLayout = getItemView(position);
            container.addView(refreshLayout);
            return refreshLayout;
        }

        private SmartRefreshLayout getItemView(int position) {
            RecyclerView recyclerView = new RecyclerView(getContext());
            // adapter
            ArticleListAdapter articleListAdapter;
            recyclerView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if(articleListAdapters.get(position) == null){
                articleListAdapter = new ArticleListAdapter(null);
                articleListAdapters.append(position, articleListAdapter);
            }else{
                articleListAdapter = articleListAdapters.get(position);
            }
            articleListAdapter.setEnableLoadMore(true);
            articleListAdapter.setUpFetchEnable(true);
//            articleListAdapter.setOnLoadMoreListener(() -> {
//                int page = articleListAdapter.getData().size() / 20;
//                presenter.loadArticleList(articleListName.getChildren().get(position).getId(), page, position);
//            }, recyclerView);
            // recyclerView
            recyclerView.setAdapter(articleListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            // refreshLayout
            SmartRefreshLayout refreshLayout = new SmartRefreshLayout(getContext());
            refreshLayout.addView(recyclerView);
            refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
                int page = articleListAdapter.getData().size() / 20;
                presenter.loadArticleList(articleListName.getChildren().get(position).getId(), page, position);
            });
            refreshLayout.setOnRefreshListener(refreshLayout1 ->
                    presenter.loadArticleList(articleListName.getChildren().get(position).getId(), 0, position));
            refreshLayoutRefs.put(position, new WeakReference<>(refreshLayout));
            return refreshLayout;
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
