package com.cxw.wanandroidapp_project;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.https.Observer.RxObserver;
import cxw.com.commonapp.https.RetrofitManager;
import cxw.com.commonapp.https.transformer.DefaultTransformer;
import cxw.com.commonapp.mvp.presenter.LoadMoreOrRefreshPresenter;

public class ProjectPresenter extends LoadMoreOrRefreshPresenter {
    int page, cid;

    @Override
    public void doRefresh() {
        super.doRefresh();
        page = 1;
        getProjectTree();
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        getProjectTree();
    }

    public void getProjectName() {
        RetrofitManager.createService(FactoryInterface.class)
                .projectName()
                .compose(DefaultTransformer.create())
                .subscribe(new RxObserver<List<ProjectTabNameBean>>() {
                    @Override
                    public void onNext(List<ProjectTabNameBean> projectNameBeans) {
                        EventBus.getDefault().post(new EventTarget<>(projectNameBeans, "projectName"));
                    }
                });
    }

    public void getProjectTree() {
        RetrofitManager.createService(FactoryInterface.class)
                .treeProject(page, cid)
                .compose(DefaultTransformer.create())
                .subscribe(new RxObserver<ProjectTreeBean>() {
                    @Override
                    public void onNext(ProjectTreeBean projectTreeBean) {
                        EventBus.getDefault().post(new EventTarget<>(projectTreeBean, "projectTree"));
                    }
                });

        Log.d("cxw", "访问数据");
    }
}
