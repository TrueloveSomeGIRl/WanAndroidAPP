package cxw.com.wanandroidapp_home.Presenter;




import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.https.Observer.RxObserver;
import cxw.com.commonapp.https.RetrofitManager;
import cxw.com.commonapp.https.transformer.DefaultTransformer;
import cxw.com.commonapp.mvp.presenter.LoadMoreOrRefreshPresenter;
import cxw.com.wanandroidapp_home.Entity.ArticleBean;
import cxw.com.wanandroidapp_home.Entity.SearchHotWordBean;
import cxw.com.wanandroidapp_home.FactoryInterface;




public class SearchPresenter extends LoadMoreOrRefreshPresenter {
    public int page;
    public String KeyWord;

    @Override
    public void doRefresh() {
        super.doRefresh();
        page=1;
        search();
    }

    @Override
    public void loadMore() {
        super.loadMore();
        search();
    }

    public void search() {
        RetrofitManager.createService(FactoryInterface.class)
                .search(page, KeyWord)
                .compose(DefaultTransformer.create())
                .subscribe(new RxObserver<ArticleBean>() {
                    @Override
                    public void onNext(ArticleBean articleBean) {
                        EventBus.getDefault().post(new EventTarget<>(articleBean, "search"));
                        if (articleBean != null) {
                            page++;
                        }
                    }
                });

    }

    public void searchHotWord() {
        RetrofitManager.createService(FactoryInterface.class)
                .searchHotWord()
                .compose(DefaultTransformer.create())
                .subscribe(new RxObserver<List<SearchHotWordBean>>() {
                    @Override
                    public void onNext(List<SearchHotWordBean> searchHotWordBeans) {
                        EventBus.getDefault().post(new EventTarget<>(searchHotWordBeans, "searchHotWord"));
                    }
                });
    }

}
