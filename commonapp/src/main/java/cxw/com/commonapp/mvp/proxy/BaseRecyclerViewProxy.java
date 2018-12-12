package cxw.com.commonapp.mvp.proxy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;

import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cxw.com.commonapp.R;
import cxw.com.commonapp.StatePage.NetWorkErrorCallback;
import cxw.com.commonapp.StatePage.LoadingCallback;
import cxw.com.commonapp.base.RouterPath;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.https.exception.ApiException;
import cxw.com.commonapp.https.exception.ServerException;
import cxw.com.commonapp.https.httpresult.HttpResult;
import cxw.com.commonapp.mvp.presenter.LoadMoreOrRefreshPresenter;
import cxw.com.commonapp.utils.ToastUtils;

import static cxw.com.commonapp.eventbus.Action.noNetWork;


//代理实现类 SmartRefreshLayout + RecyclerView
public class BaseRecyclerViewProxy<P extends LoadMoreOrRefreshPresenter> {
    public RecyclerView recyclerview;
    public SmartRefreshLayout refreshLayout;
    public BaseQuickAdapter adapter;
    public LoadService loadService;
    public RelativeLayout mRelativeLayout;
    public TextView retryLinkNetworkTv;
    private final PresenterProxyInterface<P> mPresenterProxyInterface;

    public BaseRecyclerViewProxy(PresenterProxyInterface<P> mPresenterProxyInterface) {
        this.mPresenterProxyInterface = mPresenterProxyInterface;
    }

    public void onCreateViewBinder(View view) {
        recyclerview = view.findViewById(R.id.recyclerview);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        mRelativeLayout = view.findViewById(R.id.network_error);
        retryLinkNetworkTv = view.findViewById(R.id.retry_link_network_tv);
        retryLinkNetworkTv.setOnClickListener(v -> {
            mRelativeLayout.setVisibility(View.GONE);
            mPresenterProxyInterface.getMvpPresenter().doRefresh();

        });
        loadService = LoadSir.getDefault().register(recyclerview, (Callback.OnReloadListener) v -> {
            loadService.showCallback(LoadingCallback.class);
           // mPresenterProxyInterface.getMvpPresenter().doRefresh();
        });

    }

    public void initView() {
        if (recyclerview == null) {
            return;
        }
        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
            mPresenterProxyInterface.getMvpPresenter().doRefresh();
        });
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false); //取消内容不满一页时开启上拉加载功能
        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            mPresenterProxyInterface.getMvpPresenter().loadMore();
        });
        recyclerview.setOverScrollMode(View.OVER_SCROLL_NEVER);   //去掉光晕效果
        recyclerview.setAdapter(adapter);
//        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                mPresenterProxyInterface.getMvpPresenter().doRefresh();
//            }
//        }, recyclerview);
    }

    /**
     * 这里是listFragment 和listActivity
     *
     * @param event
     * @param <T>
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public <T> void onEventMainThread(EventTarget<T> event) {
        T obj = event.obj;
        switch (event.action) {
            case SUCCESS:
                break;
            case showLoading:

                break;
            case hideLoading:
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                loadService.showSuccess();
                break;
            case makeMessage:
                break;
            case noNetWork:
                mRelativeLayout.setVisibility(View.VISIBLE);
                ToastUtils.showLongToast(((ApiException) obj).message);
                //  loadService.showCallback(NetWorkErrorCallback.class);
                break;
            case noLogin:
                ARouter.getInstance()
                        .build(RouterPath.LOGIN_ACTIVITY)
                        .navigation();
                break;
            case onError:
                if (obj instanceof ApiException) {
                    ApiException apiException = (ApiException) obj;
                    ToastUtils.showLongToast(apiException.message);
                } else {
                    ToastUtils.showLongToast(obj + "");
                }
                break;
        }
    }
}
