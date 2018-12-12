package com.cxw.wanandroidapp_project;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ProjectTreeAdpater extends BaseQuickAdapter<ProjectTreeBean.ProjectTree, BaseViewHolder> {
    public ProjectTreeAdpater(@Nullable List<ProjectTreeBean.ProjectTree> data) {
        super(R.layout.project_adapter_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectTreeBean.ProjectTree item) {
        helper.setText(R.id.project_title_tv, item.title)
                .setText(R.id.project_desc_tv, item.desc);
        Glide.with(mContext).load(item.envelopePic).into((ImageView) helper.getView(R.id.project_iv));
    }
}
