package cxw.com.commonapp.https.Observer;


import android.util.Log;

import cxw.com.commonapp.https.exception.ApiException;
import cxw.com.commonapp.https.exception.ErrorType;


import cxw.com.commonapp.https.httpresult.HttpResultImpl;
import io.reactivex.Observer;


/**
 * 请求服务器，返回的数据做同一处理
 */

public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            ApiException ae = (ApiException) e;
            onError(ae);
        } else {
            onError(new ApiException(e, ErrorType.UNKNOWN_ERROR));
        }
    }

    /**
     * 错误回调
     */
    protected abstract void onError(ApiException ex);
}