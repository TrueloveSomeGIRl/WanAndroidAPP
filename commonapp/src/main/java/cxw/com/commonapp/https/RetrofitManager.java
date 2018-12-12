package cxw.com.commonapp.https;


import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


import cxw.com.commonapp.https.interceptor.AddCookiesInterceptor;

import cxw.com.commonapp.https.interceptor.SaveCookiesInterceptor;
import cxw.com.commonapp.https.logger.okHttpLog.HttpLoggingInterceptorM;
import cxw.com.commonapp.https.logger.okHttpLog.LogInterceptor;
import cxw.com.commonapp.utils.Utils;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static Retrofit mRetrofit;
    private static OkHttpClient mClient;
    public static final String BASE_URL ="http://www.wanandroid.com/";
    private static final int DEFAULT_MILLISECONDS = 20;             //默认的超时时间
    static {

        Log.d("cxw", "static initializer: ");
        mClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                /*Cookies持久化*/
                .addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(new SaveCookiesInterceptor())
                //重复请求
                .retryOnConnectionFailure(true)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    /**
     * 自定义okHttpClient
     *
     * @param serviceClazz
     * @param okHttpClient 外部传入自定义okhttp，如上传文件时加长timeout时间
     * @param <T>
     * @return
     */
    public static <T> T createService(Class<T> serviceClazz, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(serviceClazz);
    }

    /**
     * 创建
     *
     * @param baseUrl
     * @param serviceClazz
     * @param okHttpClient
     * @param <T>
     * @return
     */
    public static <T> T createService(String baseUrl, Class<T> serviceClazz, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(serviceClazz);
    }

    /**
     * 自定义解析器eg：GsonConverterFactory、StringConverterFactory
     *
     * @param serviceClazz
     * @param factory
     * @param <T>
     * @return
     */
    public static <T> T createService(Class<T> serviceClazz, Converter.Factory factory) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClazz);
    }

    /**
     * 内部类单列设计模式
     */
    private RetrofitManager() { }

    private static class RetrofitManagerInstance {
        private final static RetrofitManager RETROFIT_MANAGER = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return RetrofitManagerInstance.RETROFIT_MANAGER;
    }

    public static <T> T createService(Class<T> serviceClazz) {
        return mRetrofit.create(serviceClazz);
    }


}
