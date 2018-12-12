package cxw.com.wanandroidapp_home.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cxw.com.commonapp.base.RouterPath;
import cxw.com.commonapp.utils.ToastUtils;
import cxw.com.wanandroidapp_home.Entity.ArticleBean;
import cxw.com.wanandroidapp_home.R;

public class ArticleAdapter extends BaseQuickAdapter<ArticleBean.DatasBean, BaseViewHolder> {

    private final static int AUTHOR = 0x1111;
    private final static int CLASSIFY = 0x222;
    private final static int PUBLISH_TIME = 0x333;

    public ArticleAdapter(@Nullable List<ArticleBean.DatasBean> data) {
        super(R.layout.article_adapter_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean.DatasBean item) {

        if (item.tags.size() > 0) {
            helper.setGone(R.id.article_project, true);
        } else {
            helper.setGone(R.id.article_project, false);
        }
        if (item.fresh) {
            helper.setGone(R.id.article_new, true);
        } else {
            helper.setGone(R.id.article_new, false);
        }
        if (item.collect) {
            helper.getView(R.id.article_love).setBackgroundResource(R.drawable.love);
        } else {
            helper.getView(R.id.article_love).setBackgroundResource(R.drawable.unlove);
        }
        helper.setText(R.id.article_author, stringForTime(item.author, "", AUTHOR, helper.getView(R.id.article_author)))
                .setText(R.id.article_title, item.title)
                .setText(R.id.article_classify, stringForTime(item.superChapterName, item.chapterName, CLASSIFY, helper.getView(R.id.article_classify)))
                .setText(R.id.article_publish_time, stringForTime(item.niceDate, "", PUBLISH_TIME, null))
                .addOnClickListener(R.id.article_love);

    }

    /**
     * 这里面看下怎么抽离
     */
    private SpannableStringBuilder stringForTime(String firstTitle, String twoTitle, int tag, TextView textView) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        int firstTitleLength = firstTitle.length();
        int twoTitleLength = twoTitle.length();
        switch (tag) {
            case AUTHOR:
                stringBuilder.append("作者: " + firstTitle);
                stringBuilder.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.parseColor("#999999"));
                        ds.setUnderlineText(false);
                    }

                    @Override
                    public void onClick(View widget) {
                        ARouter.getInstance()
                                .build(RouterPath.AUTHOR_ARTICLE)
                                .withString("author", firstTitle)
                                .navigation();
                    }
                }, 4, 4 + firstTitleLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case CLASSIFY:
                stringBuilder.append("分类: " + firstTitle + "/" + twoTitle);
                stringBuilder.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.parseColor("#999999"));
                        ds.setUnderlineText(false);
                    }

                    @Override
                    public void onClick(View widget) {

                    }

                }, 4, 4 + firstTitleLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                stringBuilder.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.parseColor("#999999"));
                        ds.setUnderlineText(false);
                    }

                    @Override
                    public void onClick(View widget) {

                    }
                }, 5 + firstTitleLength, 5 + firstTitleLength + twoTitleLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case PUBLISH_TIME:
                stringBuilder.append("时间: " + firstTitle);
                break;
            default:
        }
        return stringBuilder;
    }

}
