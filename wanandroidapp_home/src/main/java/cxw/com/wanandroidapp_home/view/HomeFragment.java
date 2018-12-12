package cxw.com.wanandroidapp_home.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;


import cxw.com.commonapp.base.RouterPath;
import cxw.com.commonapp.eventbus.Action;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.factory.CreatePresenter;
import cxw.com.commonapp.mvp.view.BaseListFragment;
import cxw.com.commonapp.utils.GlideImageLoader;
import cxw.com.commonapp.utils.ToastUtils;

import cxw.com.wanandroidapp_home.Entity.ArticleBean;
import cxw.com.wanandroidapp_home.Entity.BannerBean;
import cxw.com.wanandroidapp_home.Presenter.HomePresenter;
import cxw.com.wanandroidapp_home.R;
import cxw.com.wanandroidapp_home.R2;
import cxw.com.wanandroidapp_home.adapter.ArticleAdapter;

@CreatePresenter(HomePresenter.class)
@Route(path = RouterPath.HOME_FRAGMENT)
public class HomeFragment extends BaseListFragment<HomePresenter> {

    @BindView(R2.id.home_fragment_toolbar)
    CommonTitleBar homeFragmentToolbar;
    private List<ArticleBean.DatasBean> ArticleList = new ArrayList<>();
    private List<String> BannerList = new ArrayList<>();
    private Banner mBanner;
    private ImageView isLove;
    @Override
    protected void onCreateViewBinder() {
        super.onCreateViewBinder();
        getMvpPresenter().ArticleLifecycleTransformer = bindToLife();
        getMvpPresenter().BannerLifecycleTransformer = bindToLife();
        adapter = new ArticleAdapter(ArticleList);
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        getMvpPresenter().doRefresh();
        adapter.addHeaderView(addHeaderView());
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener((BaseQuickAdapter adapter, View view1, int position) -> {
            ARouter.getInstance()
                    .build(RouterPath.X5WEBVIEW_ACTIVITY)
                    .withString("webUrl", ArticleList.get(position).link)
                    .withString("webTitle", ArticleList.get(position).title)
                    .navigation();
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                isLove=view.findViewById(R.id.article_love);
                getMvpPresenter().collect(ArticleList.get(position).id);
            }
        });


        homeFragmentToolbar.setListener((v, action, extra) -> {
            if (action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                ARouter.getInstance().build(RouterPath.SEARCH)
                        .navigation();
            }
        });

    }

    private View addHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.article_list_head_banner, (ViewGroup) recyclerview.getParent(), false);
        mBanner = view.findViewById(R.id.banner);
        return view;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }


    @Override
    protected <T> boolean eventBusHandle(EventTarget<T> event) {
        if (event.action == Action.SUCCESS) {
            T object = event.obj;
            if (event.tag.equals("banner")) {
                List<BannerBean> bannerBean = (List<BannerBean>) object;
                int urlSize = bannerBean.size();
                BannerList.clear();
                for (int i = 0; i < urlSize; i++) {
                    BannerList.add(bannerBean.get(i).imagePath);
                }
                mBanner.setImages(BannerList).setImageLoader(new GlideImageLoader()).start();
            } else if (event.tag.equals("article")) {
                ArticleBean articleBean = (ArticleBean) object;
                if (articleBean.curPage == articleBean.pageCount) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                    refreshLayout.setNoMoreData(true);
                }
                adapter.addData(articleBean.datas);

                refreshLayout.finishLoadMore();
            } else if (event.tag.equals("collect")) {
                ToastUtils.showShortToast(R.string.collect_success);

                isLove.setImageResource(R.drawable.love);

                adapter.notifyDataSetChanged();
            }
            return true;
        }
        return false;
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }


}
