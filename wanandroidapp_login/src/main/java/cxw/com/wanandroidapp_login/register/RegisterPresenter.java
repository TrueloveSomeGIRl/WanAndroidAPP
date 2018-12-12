package cxw.com.wanandroidapp_login.register;

import android.util.ArrayMap;
import android.util.Log;

import com.trello.rxlifecycle2.LifecycleTransformer;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import cxw.com.commonapp.eventbus.Action;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.https.Observer.RxObserver;
import cxw.com.commonapp.https.RetrofitManager;
import cxw.com.commonapp.https.transformer.DefaultTransformer;
import cxw.com.commonapp.mvp.presenter.BaseMvpPresenter;
import cxw.com.commonapp.utils.StringUtils;
import cxw.com.wanandroidapp_login.FactoryInterface;
import cxw.com.wanandroidapp_login.login.LoginBean;
import io.reactivex.ObservableSource;

import io.reactivex.functions.Function;

public class RegisterPresenter extends BaseMvpPresenter {

    private Map<String, Object> loginMap;

    public void register(Map<String, Object> map, LifecycleTransformer lifecycleTransformer) {
        if (StringUtils.isEmpty(map.get("username") + "")) {
            EventBus.getDefault().post(new EventTarget<>(Action.makeMessage, "请输入账号"));
            return;
        }
        if (StringUtils.isEmpty(map.get("password") + "")) {
            EventBus.getDefault().post(new EventTarget<>(Action.makeMessage, "请输入密码"));
            return;
        }
        if (StringUtils.isEmpty(map.get("repasswordf") + "")) {
            EventBus.getDefault().post(new EventTarget<>(Action.makeMessage, "请确认密码"));
            return;
        }

        RetrofitManager.createService(FactoryInterface.class)
                .register(map)
                .compose(DefaultTransformer.create())
                .doOnNext(registerBean -> {
                    loginMap = new ArrayMap<>();
                    loginMap.put("username", registerBean.username);
                    loginMap.put("password", registerBean.password);
                })
                .flatMap((Function<RegisterBean, ObservableSource<LoginBean>>) registerBean -> {
                    return RetrofitManager.createService(FactoryInterface.class).login(loginMap).compose(DefaultTransformer.create());
                })
                .subscribe(new RxObserver<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        EventBus.getDefault().post(new EventTarget<>(loginBean, "registerAndLogin"));
                    }
                });

    }
}
