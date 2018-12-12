package cxw.com.wanandroidapp_login;


import android.annotation.SuppressLint;
import android.os.Bundle;


import com.alibaba.android.arouter.launcher.ARouter;

import java.util.concurrent.TimeUnit;

import cxw.com.commonapp.base.RouterPath;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.https.Observer.RxObserver;
import cxw.com.commonapp.mvp.view.BaseMvpActivity;
import cxw.com.commonapp.utils.SPUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends BaseMvpActivity {

    final int time = 5;

    @Override
    protected boolean eventBusHandle(EventTarget event) {
        return false;
    }

    @Override
    public int setLayoutId() {
        return 0;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(time)
                .map(aLong -> time - aLong)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        ARouter.getInstance().build(RouterPath.FRAMENT_CONTAINER_ACTIVITY).navigation();
                        finish();
                    }
                });

    }

    @Override
    protected void initListener() {

    }
}
