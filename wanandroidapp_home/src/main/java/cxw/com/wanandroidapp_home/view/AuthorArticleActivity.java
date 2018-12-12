package cxw.com.wanandroidapp_home.view;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

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
import cxw.com.wanandroidapp_home.Entity.ArticleBean;
import cxw.com.wanandroidapp_home.Presenter.HomePresenter;
import cxw.com.wanandroidapp_home.R;
import cxw.com.wanandroidapp_home.R2;
import cxw.com.wanandroidapp_home.adapter.SearchAdapter;

@CreatePresenter(HomePresenter.class)
@Route(path = RouterPath.AUTHOR_ARTICLE) // 路由地址，必须注明
public class AuthorArticleActivity extends BaseListActivity<HomePresenter> {

    @BindView(R2.id.home_fragment_toolbar)
    CommonTitleBar homeFragmentToolbar;
    private List<ArticleBean.DatasBean> ArticleList = new ArrayList<>();
    @Autowired
    public String author;

    @Override
    public int setLayoutId() {
        return R.layout.activity_author_article;
    }


    @Override
    protected void initData() {
        super.initData();
        ARouter.getInstance().inject(this);
        getMvpPresenter().author = author;
        getMvpPresenter().authorArticle();
        adapter = new SearchAdapter(ArticleList);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        homeFragmentToolbar.getCenterTextView().setText(author + "的文章");
        homeFragmentToolbar.setListener((v, action, extra) -> {
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                finish();
            }
        });
    }

    @Override
    protected <T> boolean eventBusHandle(EventTarget<T> event) {
        if (event.action == Action.SUCCESS) {
            T object = event.obj;
            if (event.tag.equals("authorArticle")) {
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


}
