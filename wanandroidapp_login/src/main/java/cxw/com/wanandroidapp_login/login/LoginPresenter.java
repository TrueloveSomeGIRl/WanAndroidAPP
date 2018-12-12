package cxw.com.wanandroidapp_login.login;

import com.trello.rxlifecycle2.LifecycleTransformer;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import cxw.com.commonapp.eventbus.Action;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.https.Observer.RxObserver;
import cxw.com.commonapp.https.RetrofitManager;
import cxw.com.commonapp.https.exception.ApiException;
import cxw.com.commonapp.https.exception.ServerException;
import cxw.com.commonapp.https.httpresult.HttpResultImpl;
import cxw.com.commonapp.https.transformer.DefaultTransformer;
import cxw.com.commonapp.mvp.presenter.BaseMvpPresenter;
import cxw.com.commonapp.utils.StringUtils;
import cxw.com.wanandroidapp_login.FactoryInterface;

public class LoginPresenter extends BaseMvpPresenter {
    public void login(Map<String, Object> map, LifecycleTransformer<LoginBean> lifecycleTransformer) {
        if (StringUtils.isEmpty(map.get("username") + "")) {
            EventBus.getDefault().post(new EventTarget<>(Action.makeMessage, "请输入账号"));
            return;
        }
        if (StringUtils.isEmpty(map.get("password") + "")) {
            EventBus.getDefault().post(new EventTarget<>(Action.makeMessage, "请输入密码"));
            return;
        }
        RetrofitManager.createService(FactoryInterface.class)
                .login(map)
                .compose(DefaultTransformer.create())
                .compose(lifecycleTransformer)
                .subscribe(new RxObserver<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        EventBus.getDefault().post(new EventTarget<>(loginBean, "login"));
                    }

//                    @Override
//                    protected void onError(ApiException ex) {
//                        super.onError(ex);
//                        Throwable cause = ex.getCause();
//                        if (cause instanceof ServerException) {
//                            ServerException serverException = (ServerException) cause;
//                            HttpResultImpl httpResult = (HttpResultImpl) serverException.httpResult;
//                            int msgCode = httpResult.getStatus();
//                            if (msgCode == -1) {
//                                EventBus.getDefault().post(new EventTarget<>(Action.makeMessage, httpResult.getMessage()));
//                            }
//                        }
//                    }
                });
    }


}
