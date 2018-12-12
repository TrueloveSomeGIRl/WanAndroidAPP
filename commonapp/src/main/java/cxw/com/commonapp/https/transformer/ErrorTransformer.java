package cxw.com.commonapp.https.transformer;


import android.util.Log;

import cxw.com.commonapp.https.exception.ErrorType;
import cxw.com.commonapp.https.exception.ExceptionEngine;
import cxw.com.commonapp.https.exception.ServerException;
import cxw.com.commonapp.https.httpresult.HttpResult;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;



class ErrorTransformer<T> implements ObservableTransformer<HttpResult<T>, T> {


    public static <M> ErrorTransformer<M> create() {
        return new ErrorTransformer<>();
    }


    private static ErrorTransformer instance;

    private ErrorTransformer() {
    }

    /**
     * 双重校验锁单例(线程安全)
     * 没明白 这里用单例为什么可以
     */
    public static <B> ErrorTransformer<B> getInstance() {
        if (instance == null) {
            synchronized (ErrorTransformer.class) {
                if (instance == null) {
                    instance = new ErrorTransformer<B>();
                }
            }
        }
        return instance;
    }

    @Override
    public ObservableSource<T> apply(Observable<HttpResult<T>> upstream) {
        return upstream.map(tHttpResult -> {
            // 通过对返回码进行业务判断决定是返回错误还是正常取数据
            if (tHttpResult.getStatus() != ErrorType.SUCCESS) {
                throw new ServerException(tHttpResult.getMessage(), tHttpResult.getStatus(),tHttpResult);
            }
            return tHttpResult.getData();

        }).onErrorResumeNext(throwable -> {
            return Observable.error(ExceptionEngine.handleException(throwable));
        });
    }


}
