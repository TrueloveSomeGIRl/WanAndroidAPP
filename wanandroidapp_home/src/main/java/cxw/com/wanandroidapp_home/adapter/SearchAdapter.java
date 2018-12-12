package cxw.com.wanandroidapp_home.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cxw.com.commonapp.utils.ToastUtils;
import cxw.com.wanandroidapp_home.Entity.ArticleBean;
import cxw.com.wanandroidapp_home.R;

public class SearchAdapter extends BaseQuickAdapter<ArticleBean.DatasBean,BaseViewHolder> {

    public SearchAdapter(@Nullable List<ArticleBean.DatasBean> data) {
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
        if (item.collect){
            helper.getView(R.id.article_love).setBackgroundResource(R.drawable.love);
        }else {
            helper.getView(R.id.article_love).setBackgroundResource(R.drawable.unlove);
        }
        helper.setText(R.id.article_author, stringForTime(item.author, "",1,helper.getView(R.id.article_author)))
                .setText(R.id.article_title, Html.fromHtml(item.title))
                .setText(R.id.article_classify, stringForTime(item.superChapterName, item.chapterName, 2,helper.getView(R.id.article_classify)))
                .setText(R.id.article_publish_time, stringForTime(item.niceDate, "",3,null))
                .addOnClickListener(R.id.article_love);

    }

    private SpannableStringBuilder stringForTime(String firstTitle, String twoTitle, int tag, TextView textView) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        int firstTitleLength=firstTitle.length();
        int twoTitleLength=twoTitle.length();
        switch (tag) {
            case 1:
                stringBuilder.append("作者: "+firstTitle);
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
                }, 4, 4+firstTitleLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case 2:
                stringBuilder.append("分类: "+firstTitle+"/"+twoTitle);
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

                }, 4, 4+firstTitleLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
                },5+firstTitleLength ,5+firstTitleLength+twoTitleLength , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case 3:
                stringBuilder.append("时间: "+firstTitle);
                break;

            default:
        }
        return stringBuilder;
    }

}
