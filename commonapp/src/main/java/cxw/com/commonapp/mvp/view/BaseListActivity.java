package cxw.com.commonapp.mvp.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kingja.loadsir.core.LoadService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import cxw.com.commonapp.R;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.presenter.LoadMoreOrRefreshPresenter;
import cxw.com.commonapp.mvp.proxy.BaseRecyclerViewProxy;

public abstract class BaseListActivity<P extends LoadMoreOrRefreshPresenter> extends BaseMvpActivity<P> {
    private final BaseRecyclerViewProxy<P> mListProxy = new BaseRecyclerViewProxy<>(this);
    protected RecyclerView recyclerview;
    protected SmartRefreshLayout refreshLayout;
    protected BaseQuickAdapter adapter;
    protected LoadService loadService;

    @Override
    public int setLayoutId() {
        return R.layout.list_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mListProxy.adapter = adapter;
        mListProxy.onCreateViewBinder(mContentView);
        mListProxy.initView();
        recyclerview = mListProxy.recyclerview;
        refreshLayout = mListProxy.refreshLayout;
        loadService = mListProxy.loadService;
    }


    @Override
    protected void initListener() {

    }

    @Override
    public <T> void onEventMainThread(EventTarget<T> event) {
        if (event == null || event.action == null) {
            return;
        }
        if (eventBusHandle(event)) return;   //   这里是请求成功后走自己
        mListProxy.onEventMainThread(event);   //这里请求原来的
    }
}
