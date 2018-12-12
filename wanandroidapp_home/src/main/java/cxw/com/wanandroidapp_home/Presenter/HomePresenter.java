package cxw.com.wanandroidapp_home.Presenter;


import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.trello.rxlifecycle2.LifecycleTransformer;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.https.Observer.RxObserver;
import cxw.com.commonapp.https.RetrofitManager;
import cxw.com.commonapp.https.exception.ApiException;
import cxw.com.commonapp.https.httpresult.HttpResultImpl;
import cxw.com.commonapp.https.transformer.CommontTransformer;
import cxw.com.commonapp.https.transformer.DefaultTransformer;
import cxw.com.commonapp.mvp.presenter.LoadMoreOrRefreshPresenter;
import cxw.com.wanandroidapp_home.Entity.ArticleBean;
import cxw.com.wanandroidapp_home.Entity.BannerBean;
import cxw.com.wanandroidapp_home.FactoryInterface;

public class HomePresenter extends LoadMoreOrRefreshPresenter {
    private int articlePage;
    public LifecycleTransformer<ArticleBean> ArticleLifecycleTransformer;
    public LifecycleTransformer<List<BannerBean>> BannerLifecycleTransformer;
    public String authorArticle, author;

    @Override
    public void doRefresh() {
        articlePage = 0;

        GetBanner();
        GetArticle();
    }

    @Override
    public void loadMore() {

        GetArticle();
    }

    private void GetArticle() {
        RetrofitManager.createService(FactoryInterface.class)
                .article(articlePage)
                .compose(DefaultTransformer.create())
                .compose(ArticleLifecycleTransformer)
                .subscribe(new RxObserver<ArticleBean>() {
                    @Override
                    public void onNext(ArticleBean articleBean) {
                        EventBus.getDefault().post(new EventTarget<>(articleBean, "article"));
                        if (articleBean != null && articleBean.datas != null) {
                            articlePage++;
                        }
                    }
                });
    }

    public void collect(int id) {
        RetrofitManager.createService(FactoryInterface.class)
                .collect(id)
                .compose(CommontTransformer.create())
                .subscribe(new RxObserver<HttpResultImpl<String>>() {
                    @Override
                    public void onNext(HttpResultImpl<String> s) {
                        EventBus.getDefault().post(new EventTarget<>(s, "collect"));

                    }

                });
    }

    public void GetBanner() {
        RetrofitManager.createService(FactoryInterface.class)
                .banner()
                .compose(DefaultTransformer.create())
                .compose(BannerLifecycleTransformer)
                .subscribe(new RxObserver<List<BannerBean>>() {
                    @Override
                    public void onNext(List<BannerBean> bannerBeans) {
                        EventBus.getDefault().post(new EventTarget<>(bannerBeans, "banner"));
                    }
                });

    }

    public void authorArticle() {
        RetrofitManager.createService(FactoryInterface.class)
                .authorArticle(0, author)
                .compose(DefaultTransformer.create())
                .subscribe(new RxObserver<ArticleBean>() {
                    @Override
                    public void onNext(ArticleBean articleBean) {
                        EventBus.getDefault().post(new EventTarget<>(articleBean, "authorArticle"));
                        if (articleBean != null && articleBean.datas != null) {
                            articlePage++;
                        }
                    }
                });
    }


}
