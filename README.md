 #注：本组合库是抄袭了前辈们心血，自己一点一点组装起来的，但是我记不住抄袭了那些前辈们的方案了，这里只记着高级MVP的下面是链接https://github.com/ljqloveyou123/perfect-mvp，当然还有很多缺陷但问题不大，够个人开发者用了，何况是一个应届专科菜鸟组合的呢
这是方便个人开发者的快速开发组合库。WanAndroid采用组件化,来测试这组合库是否可行
采用了Rxjava+Retrofit+Mvp。这里面包含对错误的处理，采用代理模式 简化了加载刷新，频繁find Recyclerview和new adapter，很多就不多说了，下面就介绍下用法
比如首页是整个列表页面，就可以像下面这么写
```
1.布局里面include 
 <include layout="@layout/list_view" />
 
2 创建P层继承自LoadMoreOrRefreshPresenter ，写你自己获取数据的代码 
public class HomePresenter extends LoadMoreOrRefreshPresenter {
    @Override
    public void doRefresh() {
    // 刷新
     GetArticle()；
    }

    @Override
    public void loadMore() {
   //加载
       GetArticle()；
    }
//获取数据
 private void GetArticle() {
        RetrofitManager.createService(FactoryInterface.class)
                .article(articlePage)
                .compose(DefaultTransformer.create())
                .compose(ArticleLifecycleTransformer)
                .subscribe(new RxObserver<ArticleBean>() {
                    @Override
                    public void onNext(ArticleBean articleBean) {
                        EventBus.getDefault().post(new EventTarget<>(articleBean, "article"));  //这里很重要
                        if (articleBean != null && articleBean.datas != null) {
                            articlePage++; // 这里是加载页
                        }
                               // 这里不用管出错的情况，因为已经做了处理
                    }
                });
}


3 创建activity 集成BaseListActivity 如果是Fragment 集成BaseListFragment
@CreatePresenter(HomePresenter.class) //这里是Presenter 看上面那个连接的用法
public class HomeFragment extends BaseListFragment<HomePresenter> {
 @Override
    protected void onCreateViewBinder() {
        super.onCreateViewBinder();
        getMvpPresenter().ArticleLifecycleTransformer = bindToLife();
        adapter = new ArticleAdapter(ArticleList);  
    }
   @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        getMvpPresenter().doRefresh();
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), 
        DividerItemDecoration.VERTICAL));
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    @Override
    public int getFragmentLayout() {
        return R.layout.activity_home;
    }
 @Override
    protected <T> boolean eventBusHandle(EventTarget<T> event) {
        if (event.action == Action.SUCCESS) {
            T object = event.obj;
         if (event.tag.equals("article")) {
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
```

更多用法请看项目吧，当然请高手帮忙重新组合下，能更好的使用
