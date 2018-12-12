package com.cxw.wanandroidapp_system;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.alibaba.android.arouter.facade.annotation.Route;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cxw.com.commonapp.base.RouterPath;
import cxw.com.commonapp.eventbus.Action;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.factory.CreatePresenter;
import cxw.com.commonapp.mvp.view.BaseListFragment;
import cxw.com.commonapp.mvp.view.BaseMvpFragment;
import cxw.com.commonapp.utils.ToastUtils;


@Route(path = RouterPath.SYSTEM_FRAGMENT) // 路由地址，必须注明
@CreatePresenter(systemPresenter.class)
public class SystemFragment extends BaseListFragment<systemPresenter> {

    private List<SystemTree> list=new ArrayList<>();
    @Override
    protected void onCreateViewBinder() {
        super.onCreateViewBinder();
        getMvpPresenter().getSystemTree(bindToLife());
        adapter = new systemAdapter(list);
    }


    @Override
    protected <T> boolean eventBusHandle(EventTarget<T> event) {
        if (event.action == Action.SUCCESS) {
            T object = event.obj;
            if (event.tag.equals("systemTree")) {

                List<SystemTree> systemTree= (List<SystemTree>) object;
                ToastUtils.showLongToast(systemTree.toString());
                adapter.addData(systemTree);
            }
            return true;
        }
        return false;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_system;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {


    }

}
