package cxw.com.commonapp.mvp.view;

import android.os.Bundle;


import org.greenrobot.eventbus.EventBus;

import cxw.com.commonapp.mvp.presenter.LoadMoreOrRefreshPresenter;


/**
 * ViewPage里面的不需要预加载的Fragment
 *
 * @param <P>
 */
public abstract class LazyLoadFragment<P extends LoadMoreOrRefreshPresenter> extends BaseListFragment<P> {

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (!isVisibleToUser) {
            EventBus.getDefault().unregister(this);
        }
        prepareFetchData();
    }

    /**
     * 取来数据
     */
    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

}