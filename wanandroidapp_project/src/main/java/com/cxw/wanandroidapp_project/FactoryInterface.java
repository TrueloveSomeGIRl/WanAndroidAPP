package com.cxw.wanandroidapp_project;



import java.util.List;

import cxw.com.commonapp.https.httpresult.HttpResultImpl;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FactoryInterface {

    @GET("project/tree/json")
    Observable<HttpResultImpl<List<ProjectTabNameBean>>> projectName();
    @GET("project/list/{page}/json")
    Observable<HttpResultImpl<ProjectTreeBean>>  treeProject(@Path("page") int projectPage, @Query("cid") int cid);
}
