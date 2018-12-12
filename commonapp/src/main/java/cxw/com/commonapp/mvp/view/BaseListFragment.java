package cxw.com.commonapp.mvp.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kingja.loadsir.core.LoadService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import cxw.com.commonapp.R;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.presenter.LoadMoreOrRefreshPresenter;
import cxw.com.commonapp.mvp.proxy.BaseRecyclerViewProxy;

public abstract class BaseListFragment<P extends LoadMoreOrRefreshPresenter> extends BaseMvpFragment<P> {
    private final BaseRecyclerViewProxy<P> mListProxy = new BaseRecyclerViewProxy<>(this);
    protected RecyclerView recyclerview;
    protected SmartRefreshLayout refreshLayout;
    protected BaseQuickAdapter adapter;
    protected LoadService loadService;

    protected boolean first;


    @Override
    protected void onCreateViewBinder() {
        super.onCreateViewBinder();
        first = true;
        mListProxy.onCreateViewBinder(view);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mListProxy.adapter = adapter;
        mListProxy.initView();
        recyclerview = mListProxy.recyclerview;
        refreshLayout = mListProxy.refreshLayout;
        loadService = mListProxy.loadService;
    }

    @Override
    public <T> void onEventMainThread(EventTarget<T> event) {
        if (event == null || event.action == null) {
            return;
        }
        if (eventBusHandle(event)) return;
        mListProxy.onEventMainThread(event);

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.list_view;
    }
}
