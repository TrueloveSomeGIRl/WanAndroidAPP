package com.cxw.wanandroidapp_system;




import com.trello.rxlifecycle2.LifecycleTransformer;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.https.Observer.RxObserver;
import cxw.com.commonapp.https.RetrofitManager;
import cxw.com.commonapp.https.transformer.DefaultTransformer;

import cxw.com.commonapp.mvp.presenter.LoadMoreOrRefreshPresenter;

public class systemPresenter extends LoadMoreOrRefreshPresenter {

    public void getSystemTree(LifecycleTransformer<List<SystemTree>> lifecycleTransformer) {
        RetrofitManager.createService(FactoryInterface.class)
                .systemTree()
                .compose(DefaultTransformer.create())
                .compose(lifecycleTransformer)
                .subscribe(new RxObserver<List<SystemTree>>() {
                    @Override
                    public void onNext(List<SystemTree> systemTrees) {
                        EventBus.getDefault().post(new EventTarget<>(systemTrees, "systemTree"));
                    }
                });
    }
}
