package cxw.com.wanandroidapp_home.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cxw.com.commonapp.Entity.SearchHistory;
import cxw.com.wanandroidapp_home.R;

public class SearchHistoryAdapter extends BaseQuickAdapter<SearchHistory, BaseViewHolder> {
    public SearchHistoryAdapter(@Nullable List<SearchHistory> data) {
        super(R.layout.search_history_adapter_item, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, SearchHistory item) {
        helper.setText(R.id.search_history_name, item.getName())
                .addOnClickListener(R.id.delete_history);
    }
}
