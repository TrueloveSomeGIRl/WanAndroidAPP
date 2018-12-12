package cxw.com.commonapp.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;

import android.view.WindowManager;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;

import cxw.com.commonapp.R;
import cxw.com.commonapp.R2;
import cxw.com.commonapp.base.RouterPath;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.view.BaseMvpActivity;
import cxw.com.commonapp.utils.X5WebView;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@Route(path = RouterPath.X5WEBVIEW_ACTIVITY)
public class X5WebViewActivity extends BaseMvpActivity {

    @BindView(R2.id.x5_wb)
    X5WebView mX5WebView;
    @Autowired
    public String webUrl;
    @Autowired
    public String webTitle;
    @BindView(R2.id.pb)
    ProgressBar pb;
    @BindView(R2.id.x5wb_toolbar)
    CommonTitleBar x5wbToolbar;


    @Override
    protected boolean eventBusHandle(EventTarget event) {
        return false;
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_x5_web_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        x5wbToolbar.getCenterTextView().setText(webTitle);
        //启用硬件加速
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                getWindow()
                        .setFlags(
                                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
        mX5WebView.loadUrl(webUrl);
        mX5WebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i == 100) {
                    pb.setVisibility(GONE);
                } else {
                    if (pb.getVisibility() == GONE)
                        pb.setVisibility(VISIBLE);
                    pb.setProgress(i);
                }
            }
        });

    }

    @Override
    protected void initListener() {
        x5wbToolbar.setListener((v, action, extra) -> {
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                finish();
            }
        });
    }

    /**
     * 返回键监听
     * * @param keyCode
     * * @param event
     * * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mX5WebView != null && mX5WebView.canGoBack()) {
                mX5WebView.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        //释放资源
        if (mX5WebView != null)
            mX5WebView.destroy();
        super.onDestroy();
    }

}
