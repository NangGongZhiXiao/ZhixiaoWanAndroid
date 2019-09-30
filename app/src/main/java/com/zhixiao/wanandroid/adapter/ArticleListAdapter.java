package com.zhixiao.wanandroid.adapter;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixiao.wanandroid.model.bean.home.HomeArticleData;
import com.zhixiao.wanandroid.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName: ArticleListAdapter
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/27
 */
public class ArticleListAdapter extends BaseQuickAdapter<HomeArticleData, BaseViewHolder> {

    public ArticleListAdapter(@Nullable List<HomeArticleData> data) {
        super(R.layout.item_article_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeArticleData item) {
        String tag = null;
        if(item.getTags() != null && !item.getTags().isEmpty() && item.getTags().get(0) != null){
            tag = item.getTags().get(0).getName();
        }
        helper.setText(R.id.tv_article_item_author, Html.fromHtml(
                    item.getAuthor()==null||item.getAuthor().isEmpty() ?
                            item.getShareUser() : item.getAuthor()))
                .setText(R.id.tv_article_item_tag, tag)
                .setText(R.id.tv_article_item_title, item.getTitle())
                .setText(R.id.tv_article_item_time, item.getNiceDate())
                .addOnClickListener(R.id.tv_article_item_tag, R.id.img_article_item_collect);
    }
}
