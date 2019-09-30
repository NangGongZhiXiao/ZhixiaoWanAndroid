package com.zhixiao.wanandroid.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nex3z.flowlayout.FlowLayout;
import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.utils.LogUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: DropDownTabLayout
 * @Description: 带下拉列表功能的TabLayout
 * @Author: zhixiao
 * @CreateDate: 2019/9/19
 */
public class DropDownTabLayout extends LinearLayout {
    private TabLayout tabLayout;
    private ImageView imageView;
    private FlowLayout flowLayout;
    private NestedScrollView scrollView;
    private Adapter pagerAdapter;
    private DataSetObserver pagerAdapterObserver;
    private Context context;
    private ViewPager viewPager;
    private View selectedView;
    private TabLayout.OnTabSelectedListener onTabSelectedListener;
    private ViewPager.OnAdapterChangeListener onAdapterChangeListener;
    private OnDropDownListener onDropDownListener;
    private boolean isDropDown;
    private int tabBackgroundResId, moreImgResId, lessImgResId;
    private int tabPaddingTop, tabPaddingBottom, tabPaddingStart, tabPaddingEnd;
    private OnTabSelectedListener tabSelectedListener;
    private List<Integer> tabRealPosition; // 根据位置得出textview在flowlayout中的实际位置的映射数组

    public DropDownTabLayout(Context context) {
        this(context, null);
    }

    public DropDownTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.DropDownTabLayout, defStyleAttr, 0);
        tabBackgroundResId = a.getResourceId(R.styleable.DropDownTabLayout_ddtl_tabbackgroud, 0);
        tabPaddingTop = tabPaddingBottom = tabPaddingStart = tabPaddingEnd =
                a.getResourceId(R.styleable.DropDownTabLayout_ddtl_tabpadding, 0);
        tabPaddingTop = a.getDimensionPixelSize(R.styleable.DropDownTabLayout_ddtl_tabpadding_top, tabPaddingTop);
        tabPaddingBottom = a.getDimensionPixelSize(R.styleable.DropDownTabLayout_ddtl_tabpadding_bottom, tabPaddingBottom);
        tabPaddingStart = a.getDimensionPixelSize(R.styleable.DropDownTabLayout_ddtl_tabpadding_start, tabPaddingStart);
        tabPaddingEnd = a.getDimensionPixelSize(R.styleable.DropDownTabLayout_ddtl_tabpadding_end, tabPaddingEnd);
        moreImgResId = a.getResourceId(R.styleable.DropDownTabLayout_ddtl_more_icon, R.drawable.ic_down_triangle);
        lessImgResId = a.getResourceId(R.styleable.DropDownTabLayout_ddtl_less_icon, R.drawable.ic_up_triangle);
        a.recycle();

        setOrientation(HORIZONTAL);
//        addTabLayout(context, attrs);
//        addFlowLayout(context, attrs);
//        addImageView(context, attrs);
        View view  = View.inflate(context, R.layout.view_drop_down_tablayout, this);
        tabLayout = view.findViewById(R.id.tl_drop_down_tablayout);
        flowLayout = view.findViewById(R.id.fl_drop_down_tablayout);
        imageView = view.findViewById(R.id.img_drop_down_tablayout);
        scrollView = view.findViewById(R.id.nsv_drop_down_tablayout);
        imageView.setOnClickListener(getImageClickListener());
    }

    private void addFlowLayout(Context context,  @Nullable AttributeSet attrs) {
        flowLayout = new FlowLayout(context);
        LayoutParams flowParams = new LayoutParams(context, attrs);
        flowParams.width = 0;
        flowParams.weight = 1;
        flowLayout.setLayoutParams(flowParams);
        flowLayout.setFlow(true);
        flowLayout.setRowSpacing(dip2px(context, 8));
        flowLayout.setChildSpacingForLastRow(FlowLayout.SPACING_ALIGN);
        flowLayout.setVisibility(GONE);
        addView(flowLayout);
    }

    private void addTabLayout(Context context, @Nullable AttributeSet attrs) {
        tabLayout = new TabLayout(context, attrs);
        LayoutParams tabParams = new LayoutParams(context, attrs);
        tabParams.width = 0;
        tabParams.weight = 1;
        tabLayout.setLayoutParams(tabParams);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        addView(tabLayout);
    }

    private void addImageView(Context context, @Nullable AttributeSet attrs) {
        imageView = new ImageView(context);
        ViewGroup.LayoutParams imgParams = new ViewGroup.LayoutParams(context, attrs);
        imgParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        imgParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        imageView.setLayoutParams(imgParams);
        imageView.setImageResource(moreImgResId);
        imageView.setOnClickListener(getImageClickListener());
        int padding = dip2px(context, 10);
        imageView.setPadding(padding,padding, padding, padding);
        addView(imageView);
    }

    private OnClickListener getImageClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                isDropDown = !isDropDown;
                if(onDropDownListener != null){
                    onDropDownListener.onDropDown(isDropDown);
                }
                setDropDown(isDropDown);
            }
        };
    }

    public boolean isDropDown() {
        return isDropDown;
    }

    public void setupWithViewPager(ViewPager viewPager){
        this.setupWithViewPager(viewPager, true);
    }

    public void setupWithViewPager(ViewPager viewPager, boolean autoRefresh){
        this.viewPager = viewPager;
        tabLayout.setupWithViewPager(viewPager, autoRefresh);
        if(viewPager != null){
            PagerAdapter adapter = viewPager.getAdapter();
            if(adapter != null){
                setPagerAdapter(adapter, autoRefresh);
            }
            if(onAdapterChangeListener != null){
                viewPager.removeOnAdapterChangeListener(onAdapterChangeListener);
            }
            viewPager.addOnAdapterChangeListener(getOnAdapterChangeListener());
        }
        if(onTabSelectedListener != null){
            tabLayout.removeOnTabSelectedListener(onTabSelectedListener);
        }
        tabLayout.addOnTabSelectedListener(getOnTabSelectedListener());
    }

    private ViewPager.OnAdapterChangeListener getOnAdapterChangeListener() {
        if(onAdapterChangeListener == null) {
            onAdapterChangeListener = new ViewPager.OnAdapterChangeListener() {
                @Override
                public void onAdapterChanged(@NonNull ViewPager viewPager,
                                             @Nullable PagerAdapter oldAdapter,
                                             @Nullable PagerAdapter newAdapter) {
                    setPagerAdapter(newAdapter, true);
                }
            };
        }
        return onAdapterChangeListener;
    }

    /**
     * 得到tab的监听
     * @return
     */
    private TabLayout.BaseOnTabSelectedListener getOnTabSelectedListener() {
        if(onTabSelectedListener == null) {
            onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int position;
                    View curView = flowLayout.getChildAt(getRealPosition(
                            position = tabLayout.getSelectedTabPosition()));
                    LogUtil.d("OnTabSelectedListener " + position);
                    if(curView != null) {
                        setSelectedView(curView);
                        if(tabSelectedListener != null){
                            tabSelectedListener.onTabSelectedListener((TextView) curView, position);
                        }
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            };
        }
        return onTabSelectedListener;
    }

    public void setPagerAdapter(PagerAdapter pagerAdapter, boolean addObserver) {
        if(!(pagerAdapter instanceof Adapter)){
            throw new ClassCastException("ViewPager have to setAdapter() with DropDownTabLayout.Adapter!");

        }
        this.pagerAdapter = (Adapter) pagerAdapter;
        if(pagerAdapterObserver != null){
            pagerAdapter.unregisterDataSetObserver(pagerAdapterObserver);
        }
        if(addObserver){
            if(pagerAdapterObserver == null){
                pagerAdapterObserver = new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        populateFromPagerAdapter();
                    }

                    @Override
                    public void onInvalidated() {
                        populateFromPagerAdapter();
                    }
                };
            }
            pagerAdapter.registerDataSetObserver(pagerAdapterObserver);
        }
        populateFromPagerAdapter();
    }

    /**
     * 从pagerAdapter更新数据
     */
    private void populateFromPagerAdapter() {
        if(pagerAdapter != null){
            flowLayout.removeAllViews();
            int curItem;
            for (curItem = 0; curItem < pagerAdapter.getCount(); curItem++) {
                // 添加组标题
                TextView groupTextView = getGroupTitle(pagerAdapter.getGroupTitle(curItem), curItem);
                if(groupTextView != null){
                    flowLayout.addView(groupTextView);
                }
                setRealPosition(curItem, groupTextView != null);
                flowLayout.addView(getTextView(pagerAdapter.getPageTitle(curItem), curItem));
            }
            curItem = viewPager.getCurrentItem();
            if(viewPager != null && tabRealPosition != null && curItem < tabRealPosition.size()){
                setSelectedView(flowLayout.getChildAt(getRealPosition(curItem)));
            }

            // tablayout添加自定义textView
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null && pagerAdapter != null) {
                    tab.setCustomView(getTextView(pagerAdapter.getPageTitle(i), i));
                }
            }
        }
    }

    private void setSelectedView(View view) {
        if(selectedView != null) {
            selectedView.setSelected(false);
        }
        selectedView = view;
        selectedView.setSelected(true);
    }

    /**
     * 在此对textView的显示设置
     * @param pageTitle
     * @param position
     * @return
     */
    private TextView getTextView(CharSequence pageTitle, int position) {
        TextView textView = createTextView(pageTitle, position);
        if(textView == null) {
            textView = new TextView(context);
            textView.setText(pageTitle);
            textView.setPadding(tabPaddingStart, tabPaddingTop, tabPaddingEnd, tabPaddingBottom);
            textView.setBackgroundResource(tabBackgroundResId==0 ?
                    R.drawable.bg_drop_down_tablayout_textview : tabBackgroundResId);
        }
        textView.setTag(position);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab((Integer) v.getTag());
            }
        });
        return textView;
    }

    private void selectTab(int position) {
        viewPager.setCurrentItem(position);
    }

    protected TextView createTextView(CharSequence pageTitle, int position){
        return null;
    }

    /**
     * 获得组标题
     * @param groupTitle
     * @param position 组里第一个tab的位置
     * @return
     */
    private TextView getGroupTitle(CharSequence groupTitle, int position){
        if(groupTitle == null){
            return null;
        }
        TextView textView = createGroupTitle(groupTitle, position);
        if(textView == null){
            textView = new TextView(context);
            textView.setText(groupTitle);
            textView.getPaint().setFakeBoldText(true);
            textView.setPadding(tabPaddingStart, tabPaddingTop, tabPaddingEnd, tabPaddingBottom);
        }
        ViewGroup.LayoutParams params = textView.getLayoutParams();
        if(params == null){
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        textView.setLayoutParams(params);
        return textView;
    }

    /**
     * 构建组标题的textview
     * @param groupTitle
     * @param position 组里第一个tab的位置
     * @return
     */
    protected TextView createGroupTitle(CharSequence groupTitle, int position) {
        return null;
    }

    public void addOnTabSelectedListener(TabLayout.BaseOnTabSelectedListener listener){
        if(tabLayout != null){
            tabLayout.addOnTabSelectedListener(listener);
        }
    }

    public void removeOnTabSelectedListener(TabLayout.BaseOnTabSelectedListener listener){
        if(tabLayout != null){
            tabLayout.removeOnTabSelectedListener(listener);
        }
    }

    public void setDropDownImageResource(int resid){
        imageView.setImageResource(resid);
    }

    public void setDropDownImageBitmap(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface OnDropDownListener{
        void onDropDown(boolean drop);
    }

    public void setOnDropDownListener(OnDropDownListener onDropDownListener) {
        this.onDropDownListener = onDropDownListener;
    }

    public interface OnTabSelectedListener{
        void onTabSelectedListener(TextView view, int position);
    }

    public void setOnTabSelectedListener(OnTabSelectedListener tabSelectedListener) {
        this.tabSelectedListener = tabSelectedListener;
    }

    public void setTabBackgroundResId(int tabBackgroundResId) {
        this.tabBackgroundResId = tabBackgroundResId;
    }

    private void setRealPosition(int relative, boolean addOne){
        if(relative == 0){
            tabRealPosition = new LinkedList<>();
        }
        if(tabRealPosition != null) {
            int last = tabRealPosition.size()==0 ? -1 : tabRealPosition.get(tabRealPosition.size()-1);
            tabRealPosition.add(relative, addOne ? last + 2 : last + 1);
        }
    }

    private int getRealPosition(int relative){
        if(tabRealPosition != null){
            return tabRealPosition.get(relative);
        }
        return 0;
    }

    public static abstract class Adapter extends PagerAdapter{
        protected abstract CharSequence getGroupTitle(int position);
    }

    public void setDropDown(boolean dropDown){
        this.isDropDown = dropDown;
        imageView.setImageResource(isDropDown ? lessImgResId : moreImgResId);
        tabLayout.setVisibility(isDropDown ? GONE : VISIBLE);
        scrollView.setVisibility(isDropDown ? VISIBLE : GONE);
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }
}
