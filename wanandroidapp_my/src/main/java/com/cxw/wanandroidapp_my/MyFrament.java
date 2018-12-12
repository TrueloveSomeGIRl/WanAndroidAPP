package com.cxw.wanandroidapp_my;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import cxw.com.commonapp.base.RouterPath;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.view.BaseMvpFragment;
@Route(path = RouterPath.MY_FRAGMENT) // 路由地址，必须注明
public class MyFrament extends BaseMvpFragment {
    @Override
    protected boolean eventBusHandle(EventTarget event) {
        return false;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }


}
