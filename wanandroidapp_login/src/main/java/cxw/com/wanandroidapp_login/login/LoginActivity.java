package cxw.com.wanandroidapp_login.login;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cxw.com.commonapp.base.RouterPath;
import cxw.com.commonapp.eventbus.Action;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.factory.CreatePresenter;
import cxw.com.commonapp.mvp.view.BaseMvpActivity;
import cxw.com.commonapp.utils.SPUtils;
import cxw.com.wanandroidapp_login.R;
import cxw.com.wanandroidapp_login.R2;

@SuppressLint("NewApi")
@CreatePresenter(LoginPresenter.class)
@Route(path = RouterPath.LOGIN_ACTIVITY) // 路由地址，必须注明
public class LoginActivity extends BaseMvpActivity<LoginPresenter> {


    @BindView(R2.id.login_name)
    TextInputEditText name;
    @BindView(R2.id.login_pwd)
    TextInputEditText pwd;
    @BindView(R2.id.register_tv)
    TextView registerTv;

    @Override
    public int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean eventBusHandle(EventTarget event) {
        if (event.action == Action.SUCCESS) {
            if ("login".equals(event.tag)) {
                ARouter.getInstance().build(RouterPath.FRAMENT_CONTAINER_ACTIVITY).navigation();
                finish();
            }
            return true;
        }
        return false;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView(Bundle savedInstanceState) {
        RxView.clicks(findViewById(R.id.login_in_bt))
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    Map<String, Object> map = new ArrayMap<>();
                    map.put("username", "" + name.getText());
                    map.put("password", "" + pwd.getText());
                    getMvpPresenter().login(map, bindToLife());
                });
    }

    @Override
    protected void initListener() {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
                stringBuilder.append("还没有账号?注册");
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
                                .build(RouterPath.REGISTER_ACTIVITY)
                                .navigation();
                    }
                }, 6, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                registerTv.setText(stringBuilder);
        registerTv.setMovementMethod(LinkMovementMethod.getInstance());
    }


}
