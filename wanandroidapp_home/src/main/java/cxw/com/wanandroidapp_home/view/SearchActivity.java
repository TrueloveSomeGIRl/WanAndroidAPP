package cxw.com.wanandroidapp_home.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexboxLayout;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cxw.com.commonapp.Entity.SearchHistory;
import cxw.com.commonapp.base.RouterPath;
import cxw.com.commonapp.eventbus.Action;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.factory.CreatePresenter;
import cxw.com.commonapp.mvp.view.BaseMvpActivity;
import cxw.com.commonapp.utils.GreenDaoUtils;
import cxw.com.commonapp.utils.StringUtils;
import cxw.com.commonapp.utils.ToastUtils;
import cxw.com.wanandroidapp.history.DaoSession;
import cxw.com.wanandroidapp.history.SearchHistoryDao;
import cxw.com.wanandroidapp_home.Entity.SearchHotWordBean;
import cxw.com.wanandroidapp_home.Presenter.SearchPresenter;
import cxw.com.wanandroidapp_home.R;
import cxw.com.wanandroidapp_home.R2;
import cxw.com.wanandroidapp_home.adapter.SearchHistoryAdapter;


@CreatePresenter(SearchPresenter.class)
@Route(path = RouterPath.SEARCH)
public class SearchActivity extends BaseMvpActivity<SearchPresenter> {

    @BindView(R2.id.flex_boox_layout)
    FlexboxLayout flexBooxLayout;
    @BindView(R2.id.hot_word_cl)
    ConstraintLayout hotWordCl;
    List<SearchHistory> HistoryList = new ArrayList<>();
    @BindView(R2.id.search_history_rv)
    RecyclerView searchHistoryRv;
    @BindView(R2.id.titlebar)
    CommonTitleBar titlebar;
    private SearchHistoryDao searchHistoryDao;
    private SearchHistoryAdapter mSearchHistoryAdapter;

    @Override
    protected <T> boolean eventBusHandle(EventTarget<T> event) {
        if (event.action == Action.SUCCESS) {
            T object = event.obj;
            if (event.tag.equals("searchHotWord")) {
                List<SearchHotWordBean> searchHotWordBeans = (List<SearchHotWordBean>) object;
                for (int i = 0; i < searchHotWordBeans.size(); i++) {
                    TextView textView = createTextView(searchHotWordBeans.get(i).name);
                    flexBooxLayout.addView(textView);
                }
            }
            return true;
        }
        return false;
    }


    private TextView createTextView(String text) {

        TextView textView = new TextView(this);
        //字体颜色
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        //设置字体大小
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24);
        textView.setText(text);
        //背景
        textView.setBackgroundResource(R.drawable.flextext_rect_shape);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //padding
        int padding = 15;
        ViewCompat.setPaddingRelative(textView, padding, padding,
                padding, padding);
        layoutParams.setMargins(10, 20, 10, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    @Override
    protected void onResume() {
        super.onResume();
        DaoSession daoSession = GreenDaoUtils.getInstance().getDaoSession();
        searchHistoryDao = daoSession.getSearchHistoryDao();
        HistoryList = searchHistoryDao.loadAll();
        mSearchHistoryAdapter = new SearchHistoryAdapter(HistoryList);
        searchHistoryRv.setLayoutManager(new LinearLayoutManager(this));
        searchHistoryRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        searchHistoryRv.setAdapter(mSearchHistoryAdapter);
        mSearchHistoryAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showLongToast("fsdfd");
               searchHistoryDao.deleteByKey((long) 1);
               adapter.notifyDataSetChanged();
            }
        });
        if (HistoryList.size() > 0 && HistoryList != null) {
            mSearchHistoryAdapter.addFooterView(getFooterView());
            mSearchHistoryAdapter.addHeaderView(getHeaderView());

        }
    }

    private View getHeaderView() {
        View headerView = getLayoutInflater().inflate(R.layout.search_header, (ViewGroup) searchHistoryRv.getParent(), false);
        return headerView;
    }

    private View getFooterView() {
        View FooterView = getLayoutInflater().inflate(R.layout.search_footer, (ViewGroup) searchHistoryRv.getParent(), false);
        FooterView.setOnClickListener(v -> {
            searchHistoryDao.deleteAll();
            mSearchHistoryAdapter.replaceData(HistoryList);

        });

        return FooterView;
    }


    @Override
    public int setLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getMvpPresenter().searchHotWord();
    }

    @Override
    protected void initListener() {
       titlebar.setListener((v, action, extra) -> {
           if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
               finish();
           }else if (action == CommonTitleBar.ACTION_SEARCH_DELETE){
           }else if (action == CommonTitleBar.ACTION_RIGHT_TEXT){
               String word=titlebar.getCenterSearchEditText().getText().toString();
               if (word.isEmpty())return;
               ARouter.getInstance().build(RouterPath.SEARCH_RESULT)
                       .withString("searchWord",word)
                       .navigation();
               addSearchHistoryData(word);
           }
       });
    }


    @Override
    protected void initData() {
        super.initData();
        //联想搜索
//        RxTextView.textChanges(searchEt)
//                .filter(charSequence -> charSequence.toString().trim().length() > 0).debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
//                .switchMap((io.reactivex.functions.Function<CharSequence, ObservableSource<ArticleBean>>) charSequence -> RetrofitManager.getInstance()
//                        .getRetrofitService(FactoryInterface.class)
//                        .search(id, searchEt.getText().toString().trim())
//                        .compose(DefaultTransformer.create()))
//                .subscribe(new RxObserver<ArticleBean>() {
//                    @Override
//                    public void onNext(ArticleBean articleBean) {
//                        Log.d("cxw", "onNext:1111111 ");
//                        EventBus.getDefault().post(new EventTarget<>(articleBean, "search"));
//
//                    }
//                });
    }


    private void addSearchHistoryData(String word) {
        SearchHistory searchHistory = new SearchHistory(null, word);
        searchHistoryDao.insert(searchHistory);
    }


}
