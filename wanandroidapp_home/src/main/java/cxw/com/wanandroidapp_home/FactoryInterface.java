package cxw.com.wanandroidapp_home;

import java.util.List;
import java.util.Map;

import cxw.com.commonapp.https.httpresult.HttpResultImpl;
import cxw.com.wanandroidapp_home.Entity.ArticleBean;
import cxw.com.wanandroidapp_home.Entity.BannerBean;
import cxw.com.wanandroidapp_home.Entity.SearchHotWordBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface FactoryInterface {
    /**
     * 首页文章
     *
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<HttpResultImpl<ArticleBean>> article(@Path("page") int page);

    /**
     * banner
     *
     * @return
     */
    @GET("banner/json")
    Observable<HttpResultImpl<List<BannerBean>>> banner();

    /**
     * 喜欢的文章
     */
    @POST("lg/collect/{id}/json")
    Observable<HttpResultImpl<String>> collect(@Path("id") int articleId);

    /**
     * 搜索
     */
    @POST("article/query/{page}/json")
    Observable<HttpResultImpl<ArticleBean>> search(@Path("page") int articleId, @Query("k") String KeyWord);

    /**
     * 搜索热词
     */

    @GET("hotkey/json")
    Observable<HttpResultImpl<List<SearchHotWordBean>>> searchHotWord();
    /**
     *   作者文章列表
     */
    @GET("article/list/{page}/json")
    Observable<HttpResultImpl<ArticleBean>> authorArticle(@Path("page") int articleId, @Query("author") String author);


}
