package com.cxw.wanandroidapp_project;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cxw.com.commonapp.base.RouterPath;
import cxw.com.commonapp.eventbus.Action;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.factory.CreatePresenter;
import cxw.com.commonapp.mvp.view.LazyLoadFragment;

@CreatePresenter(ProjectPresenter.class)
public class ProjectTreeFragment extends LazyLoadFragment<ProjectPresenter> {
    public static final String CID = "cid";
    private List<ProjectTreeBean.ProjectTree> list = new ArrayList<>();

    public static ProjectTreeFragment newInstance(int cid) {
        ProjectTreeFragment fragment = new ProjectTreeFragment();
        Bundle args = new Bundle();
        args.putInt(CID, cid);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void fetchData() {
        getMvpPresenter().doRefresh();
    }



    @Override
    protected void onCreateViewBinder() {
        super.onCreateViewBinder();
        getMvpPresenter().cid = getArguments().getInt(CID);
        adapter = new ProjectTreeAdpater(list);
        Log.d("cxw", "newInstance: "+getArguments().getInt(CID));
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        recyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener((adapter, view1, position) -> ARouter.getInstance()
                .build(RouterPath.X5WEBVIEW_ACTIVITY)
                .withString("webUrl", list.get(position).link)
                .withString("webTitle", list.get(position).title)
                .navigation());
    }

    @Override
    protected <T> boolean eventBusHandle(EventTarget<T> event) {
        if (event.action == Action.SUCCESS) {
            T object = event.obj;
            if (event.tag.equals("projectTree")) {
                ProjectTreeBean projectTreeBean = (ProjectTreeBean) object;
                if (projectTreeBean.curPage == projectTreeBean.pageCount) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                adapter.addData(projectTreeBean.datas);
                refreshLayout.finishLoadMore();
            }
            return true;
        }
        return false;
    }
}
