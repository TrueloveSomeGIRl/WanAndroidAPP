package cxw.com.wanandroidapp_home.view;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cxw.com.commonapp.base.RouterPath;
import cxw.com.commonapp.eventbus.Action;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.factory.CreatePresenter;
import cxw.com.commonapp.mvp.view.BaseListActivity;
import cxw.com.commonapp.utils.ToastUtils;
import cxw.com.wanandroidapp_home.Entity.ArticleBean;
import cxw.com.wanandroidapp_home.Presenter.SearchPresenter;
import cxw.com.wanandroidapp_home.R;
import cxw.com.wanandroidapp_home.R2;
import cxw.com.wanandroidapp_home.adapter.SearchAdapter;


@Route(path = RouterPath.SEARCH_RESULT)
@CreatePresenter(SearchPresenter.class)
public class SearchListActivity extends BaseListActivity<SearchPresenter> {
    @BindView(R2.id.search_list_toolbar)
    CommonTitleBar searchListToolbar;
    private List<ArticleBean.DatasBean> ArticleList = new ArrayList<>();
    @Autowired
    public String searchWord;
    @Override
    public int setLayoutId() {
        return R.layout.activity_search_list;
    }

    @Override
    protected void initData() {
        super.initData();
        ARouter.getInstance().inject(this);
        getMvpPresenter().KeyWord=searchWord;
        getMvpPresenter().doRefresh();
        adapter = new SearchAdapter(ArticleList);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        searchListToolbar.getCenterTextView().setText(searchWord+" 下的文章");
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected <T> boolean eventBusHandle(EventTarget<T> event) {
        if (event.action == Action.SUCCESS) {
            T object = event.obj;
            if (event.tag.equals("search")) {
                ArticleBean articleBean = (ArticleBean) object;
                if (articleBean.curPage == articleBean.pageCount) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                    refreshLayout.setNoMoreData(true);
                }
                adapter.addData(articleBean.datas);
                refreshLayout.finishLoadMore();
            }
            return true;
        }
        return false;
    }

    @Override
    protected void initListener() {
        super.initListener();
        searchListToolbar.setListener((v, action, extra) -> {
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                finish();
            }
        });
    }
}
