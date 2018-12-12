package cxw.com.commonapp.base;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.core.LoadSir;
import com.tencent.smtt.sdk.QbSdk;

import cxw.com.commonapp.Entity.SearchHistory;
import cxw.com.commonapp.StatePage.EmptyCallback;
import cxw.com.commonapp.StatePage.NetWorkErrorCallback;
import cxw.com.commonapp.StatePage.LoadingCallback;
import cxw.com.commonapp.StatePage.TimeoutCallback;
import cxw.com.commonapp.utils.GreenDaoUtils;
import cxw.com.commonapp.utils.Utils;


public class BaseApplication extends Application {
    private static BaseApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        //---------------------------  x5WebView-----------------------------//
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                Log.d("cxw", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);


        //------------------------------   阿里路由---------------------------------//
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        Utils.init(this);


         //------------------------  页面状态-------------------------------///
        LoadSir.beginBuilder()
                .addCallback(new NetWorkErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();

        //greendao
        GreenDaoUtils.getInstance().init("SearchHistory.db");
    }


    /**
     * 利用单例模式获取Application实例
     *
     * @return
     */
    public static BaseApplication getInstance() {
        if (null == mApplication) {
            mApplication = new BaseApplication();
        }
        return mApplication;
    }

    public boolean isDebug() {  //日志 上线改为false
        return true;
    }
}
