package cxw.com.wanandroidapp_login;

import java.util.Map;

import cxw.com.commonapp.https.httpresult.HttpResultImpl;
import cxw.com.wanandroidapp_login.login.LoginBean;
import cxw.com.wanandroidapp_login.register.RegisterBean;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface FactoryInterface {
    @FormUrlEncoded
    @POST("user/login")
    Observable<HttpResultImpl<LoginBean>> login(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("user/register")
    Observable<HttpResultImpl<RegisterBean>> register(@FieldMap Map<String, Object> map);
}
