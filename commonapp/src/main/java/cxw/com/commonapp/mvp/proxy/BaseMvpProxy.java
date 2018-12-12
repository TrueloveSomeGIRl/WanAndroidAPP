package cxw.com.commonapp.mvp.proxy;

import android.os.Bundle;
import android.util.Log;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.https.exception.ApiException;
import cxw.com.commonapp.https.exception.ServerException;
import cxw.com.commonapp.https.httpresult.HttpResult;
import cxw.com.commonapp.mvp.factory.PresenterMvpFactory;
import cxw.com.commonapp.mvp.presenter.BaseMvpPresenter;
import cxw.com.commonapp.utils.ToastUtils;


/**
 * @author 刘镓旗
 * @date 2017/11/20
 * @description 代理实现类，用来管理Presenter的生命周期，还有和view之间的关联
 */
public class BaseMvpProxy<P extends BaseMvpPresenter> implements PresenterProxyInterface<P> {

    /**
     * 获取onSaveInstanceState中bundle的key
     */
    private static final String PRESENTER_KEY = "presenter_key";

    /**
     * Presenter工厂类
     */
    private PresenterMvpFactory<P> mFactory;
    private P mPresenter;
    private Bundle mBundle;


    public BaseMvpProxy(PresenterMvpFactory<P> presenterMvpFactory) {
        this.mFactory = presenterMvpFactory;
    }

    /**
     * 设置Presenter的工厂类,这个方法只能在创建Presenter之前调用,也就是调用getMvpPresenter()之前，如果Presenter已经创建则不能再修改
     *
     * @param presenterFactory PresenterFactory类型
     */
    @Override
    public void setPresenterFactory(PresenterMvpFactory<P> presenterFactory) {
        if (mPresenter != null) {
            throw new IllegalArgumentException("这个方法只能在getMvpPresenter()之前调用，如果Presenter已经创建则不能再修改");
        }
        this.mFactory = presenterFactory;
    }

    /**
     * 获取Presenter的工厂类
     *
     * @return PresenterMvpFactory类型
     */
    @Override
    public PresenterMvpFactory<P> getPresenterFactory() {
        return mFactory;
    }

    /**
     * 获取创建的Presenter
     *
     * @return 指定类型的Presenter
     * 如果之前创建过，而且是以外销毁则从Bundle中恢复
     */
    @Override
    public P getMvpPresenter() {
        Log.e("perfect-mvp", "Proxy getMvpPresenter");
        if (mFactory != null) {
            if (mPresenter == null) {
                mPresenter = mFactory.createMvpPresenter();
                mPresenter.onCreatePersenter(mBundle == null ? null : mBundle.getBundle(PRESENTER_KEY));
            }
        }
        Log.e("perfect-mvp", "Proxy getMvpPresenter = " + mPresenter);
        return mPresenter;
    }


    /**
     * 销毁Presenter
     */
    public void onDestroy() {
        Log.e("perfect-mvp", "Proxy onDestroy = ");
        if (mPresenter != null) {

            mPresenter.onDestroyPersenter();
            mPresenter = null;
        }
    }

    /**
     * 意外销毁的时候调用
     *
     * @return Bundle，存入回调给Presenter的Bundle和当前Presenter的id
     */
    public Bundle onSaveInstanceState() {
        Log.e("perfect-mvp", "Proxy onSaveInstanceState = ");
        Bundle bundle = new Bundle();
        getMvpPresenter();
        if (mPresenter != null) {
            Bundle presenterBundle = new Bundle();
            //回调Presenter
            mPresenter.onSaveInstanceState(presenterBundle);
            bundle.putBundle(PRESENTER_KEY, presenterBundle);
        }
        return bundle;
    }

    /**
     * 意外关闭恢复Presenter
     *
     * @param savedInstanceState 意外关闭时存储的Bundler
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e("perfect-mvp", "Proxy onRestoreInstanceState = ");
        Log.e("perfect-mvp", "Proxy onRestoreInstanceState Presenter = " + mPresenter);
        mBundle = savedInstanceState;

    }

    /**
     * 这里是 BasemvpFragment 和 activity
     *
     * @param event
     * @param <T>
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public <T> void onEventMainThread(EventTarget<T> event) {
        T obj = event.obj;
        switch (event.action) {
            case SUCCESS:
                if (obj instanceof HttpResult) {
                    HttpResult httpResult = (HttpResult) obj;
                    ToastUtils.showLongToast(httpResult.getMessage());
                } else {
                    ToastUtils.showLongToast(obj + "");
                }
                break;
            case showLoading:

                break;
            case hideLoading:
                break;
            case makeMessage:
                ToastUtils.showLongToast(obj + "");
                break;
            case noNetWork:
                ToastUtils.showLongToast(((ApiException) obj).message);
                break;
            case noLogin:

                break;
            case onError:
                if (obj instanceof ApiException) {
                    ApiException apie = (ApiException) obj;
                    ToastUtils.showLongToast(apie.message);
                } else {
                    ToastUtils.showLongToast(obj + "");
                }

                break;
        }
    }

}
