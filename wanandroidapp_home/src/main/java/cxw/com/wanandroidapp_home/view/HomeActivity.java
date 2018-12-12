package cxw.com.wanandroidapp_home.view;


import android.os.Bundle;

import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.view.BaseMvpActivity;
import cxw.com.wanandroidapp_home.R;


public class HomeActivity extends BaseMvpActivity {


    @Override
    public int setLayoutId() {
        return R.layout.activity_home;
    }



    @Override
    protected boolean eventBusHandle(EventTarget event) {
        return false;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }
}
