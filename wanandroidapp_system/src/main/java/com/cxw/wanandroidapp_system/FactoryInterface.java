package com.cxw.wanandroidapp_system;


import java.util.List;

import cxw.com.commonapp.https.httpresult.HttpResultImpl;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface FactoryInterface {
    @GET("tree/json")
    Observable<HttpResultImpl<List<SystemTree>>> systemTree();
}
