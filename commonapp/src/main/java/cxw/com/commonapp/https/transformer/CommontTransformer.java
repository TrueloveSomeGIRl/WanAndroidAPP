package cxw.com.commonapp.https.transformer;


import cxw.com.commonapp.https.exception.ErrorType;
import cxw.com.commonapp.https.exception.ExceptionEngine;
import cxw.com.commonapp.https.exception.ServerException;
import cxw.com.commonapp.https.httpresult.HttpResult;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 这个是全解析返回 HttpResult<T> -> HttpResult<T>, 加这个的目的主要是做错误统一处理
 */
public class CommontTransformer<T> implements ObservableTransformer<T,T> {


    private CommontTransformer() {
    }

    public static <T> CommontTransformer<T> create() {
        return new CommontTransformer<>();
    }

    private static CommontTransformer instance = null;

    /**
     * 双重校验锁单例(线程安全)
     */
    public static <T> CommontTransformer<T> getInstance() {
        if (instance == null) {
            synchronized (CommontTransformer.class) {
                if (instance == null) {
                    instance = new CommontTransformer<T>();
                }
            }
        }
        return instance;
    }


    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .map(t -> {
                    if (t instanceof HttpResult){
                        HttpResult httpResult= (HttpResult) t;
                        if (httpResult.getStatus()!= ErrorType.SUCCESS){
                            throw new ServerException(httpResult.getMessage(), httpResult.getStatus(), httpResult);
                        }
                    }
                    return t;
                })
                .onErrorResumeNext(throwable -> {
                    return Observable.error(ExceptionEngine.handleException(throwable));
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}