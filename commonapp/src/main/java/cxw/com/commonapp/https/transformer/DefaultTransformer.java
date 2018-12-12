package cxw.com.commonapp.https.transformer;



import cxw.com.commonapp.https.httpresult.HttpResult;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class DefaultTransformer<T> implements ObservableTransformer<HttpResult<T>, T> {

    private DefaultTransformer() { }

    public static <M> DefaultTransformer<M> create() {
        return new DefaultTransformer<>();
    }

    @Override
    public ObservableSource<T> apply(Observable<HttpResult<T>> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .compose(ErrorTransformer.getInstance())
                .observeOn(AndroidSchedulers.mainThread());
    }
}