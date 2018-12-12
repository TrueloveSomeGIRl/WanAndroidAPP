package cxw.com.commonapp.https.Observer;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import cxw.com.commonapp.eventbus.Action;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.https.exception.ApiException;
import cxw.com.commonapp.https.exception.ErrorType;
import cxw.com.commonapp.utils.NetworkUtils;
import cxw.com.commonapp.utils.ToastUtils;
import io.reactivex.disposables.Disposable;

/**
 * EventBus版的RxSubscriber
 */
public abstract class RxObserver<T> extends BaseObserver<T> {
    private static final String TAG = RxObserver.class.getSimpleName();


    @Override
    protected void onError(ApiException ex) {
        switch (ex.code) {
            case ErrorType.UNKNOWN_ERROR://  未知错误
                ex.printStackTrace();
                ToastUtils.showLongToast(ex.message);
                break;
            case ErrorType.PARSE_ERROR:// 解析错误 JsonParseException JSONException ParseException
                ToastUtils.showLongToast(ex.message);
                break;
            case ErrorType.HTTP_ERROR: // 协议出错  HttpException
                ToastUtils.showLongToast(ex.message);
                break;
            case ErrorType.ERROR://  接口返回失败错误
                EventBus.getDefault().post(new EventTarget<>(Action.onError, ex));
                break;
            case ErrorType.NETWORD_ERROR:// 网络超时和链接异常 ConnectException SocketTimeoutException ConnectTimeoutException UnknownHostException
                EventBus.getDefault().post(new EventTarget<>(Action.noNetWork, ex));
                break;
            case ErrorType.NOT_LOGIN:
                EventBus.getDefault().post(new EventTarget<>(Action.noLogin, ex));
                break;
        }

        onComplete();
    }

    @Override
    public void onSubscribe(Disposable d) {
        //可以做一些准备工作
        if (!NetworkUtils.isConnected()) {
            onComplete();
            // 无网络执行complete后取消注册防调用onError
            if (!d.isDisposed()) {
                d.dispose();
            }
            EventBus.getDefault().post(new EventTarget<>(Action.noNetWork, new ApiException(ErrorType.NETWORD_ERROR, "请先连接网络")));
        }
    }


    @Override
    public void onComplete() {
        EventBus.getDefault().post(new EventTarget<>(Action.hideLoading, null));
    }
}
