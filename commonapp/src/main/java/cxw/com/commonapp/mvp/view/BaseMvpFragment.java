package cxw.com.commonapp.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cxw.com.commonapp.base.BaseFragment;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.factory.PresenterMvpFactory;
import cxw.com.commonapp.mvp.factory.PresenterMvpFactoryImpl;
import cxw.com.commonapp.mvp.presenter.BaseMvpPresenter;
import cxw.com.commonapp.mvp.proxy.BaseMvpProxy;
import cxw.com.commonapp.mvp.proxy.PresenterProxyInterface;


public abstract class BaseMvpFragment<P extends BaseMvpPresenter> extends BaseFragment implements PresenterProxyInterface<P> {

    /**
     * 调用onSaveInstanceState时存入Bundle的key
     */
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<P>createFactory(getClass()));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void onCreateViewBinder() {
        super.onCreateViewBinder();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState());
    }


    /**
     * 可以实现自己PresenterMvpFactory工厂
     *
     * @param presenterFactory PresenterFactory类型
     */
    @Override
    public void setPresenterFactory(PresenterMvpFactory<P> presenterFactory) {
        mProxy.setPresenterFactory(presenterFactory);
    }


    /**
     * 获取创建Presenter的工厂
     *
     * @return PresenterMvpFactory类型
     */
    @Override
    public PresenterMvpFactory<P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }

    /**
     * 获取Presenter
     *
     * @return P
     */
    @Override
    public P getMvpPresenter() {
        return mProxy.getMvpPresenter();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public <T> void onEventMainThread(EventTarget<T> event) {
        if (event == null || event.action == null) {
            return;
        }
        if (eventBusHandle(event)) return;
        mProxy.onEventMainThread(event);

    }

    /**
     * EventBus的回调处理
     *
     * @param event
     * @return false:要调用默认处理，true:不调用默认处理
     */
    protected abstract <T> boolean eventBusHandle(EventTarget<T> event);

}
