package com.cxw.wanandroidapp_system;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import cxw.com.commonapp.base.BaseApplication;
import cxw.com.commonapp.utils.ToastUtils;

public class systemAdapter extends BaseQuickAdapter<SystemTree, BaseViewHolder> {

    public systemAdapter(@Nullable List<SystemTree> data) {
        super(R.layout.left_name_item_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemTree item) {

        FlexboxLayout flexBoxLayout = helper.getView(R.id.flex_boox_layout);
        helper.setText(R.id.parent_title, item.name);
//        for (int i = 0; i < item.children.size(); i++) {
//            TextView textView = createTextView(item.children.get(i).name);
//            flexBoxLayout.addView(textView);
//        }

    }

    private TextView createTextView(String text) {

        TextView textView = new TextView(BaseApplication.getInstance());
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
}
