package cxw.com.wanandroidapp_login.register;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.util.ArrayMap;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cxw.com.commonapp.base.RouterPath;
import cxw.com.commonapp.eventbus.Action;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.factory.CreatePresenter;
import cxw.com.commonapp.mvp.view.BaseMvpActivity;
import cxw.com.commonapp.utils.SPUtils;
import cxw.com.wanandroidapp_login.R;
import cxw.com.wanandroidapp_login.R2;

@SuppressLint("CheckResult")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@Route(path = RouterPath.REGISTER_ACTIVITY) // 路由地址，必须注明
@CreatePresenter(RegisterPresenter.class)
public class RegisterActivity extends BaseMvpActivity<RegisterPresenter> {


    @BindView(R2.id.register_name)
    TextInputEditText registerName;
    @BindView(R2.id.register_pwd)
    TextInputEditText registerPwd;
    @BindView(R2.id.register_repeat_pwd)
    TextInputEditText registerRepeatPwd;


    @Override
    protected boolean eventBusHandle(EventTarget event) {
        if (event.action == Action.SUCCESS) {
            if (event.action.equals("registerAndLogin")) {

            }
            return true;
        }
        return false;
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }


    @Override
    protected void initListener() {
        RxView.clicks(findViewById(R.id.register_bt))
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    Map<String, Object> map = new ArrayMap<>();
                    map.put("username", "" + registerName.getText());
                    map.put("password", "" + registerPwd.getText());
                    map.put("repassword", "" + registerRepeatPwd.getText());
                    getMvpPresenter().register(map, bindToLife());
                });
    }


}
