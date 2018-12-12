package cxw.com.wanandroidapp_home.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleBean {

        public int curPage;
        public int offset;
        public boolean over;
        public int pageCount;
        public int size;
        public int total;
        public List<DatasBean> datas;

        public static class DatasBean {
            /**
             * apkLink :
             * author : zeleven
             * chapterId : 294
             * chapterName : 完整项目
             * Collect : false
             * courseId : 13
             * desc : 玩Android（http://www.wanandroid.com/） APP（MVP + RxJava2 + Retrofit2 + Dagger2）

             * envelopePic : http://wanandroid.com/blogimgs/9027b14c-6916-4e39-9325-2e744208e990.png
             * fresh : false
             * id : 3403
             * link : http://www.wanandroid.com/blog/show/2353
             * niceDate : 2018-09-16
             * origin :
             * projectLink : https://github.com/zeleven/playa
             * publishTime : 1537094061000
             * superChapterId : 294
             * superChapterName : 开源项目主Tab
             * tags : [{"name":"项目","url":"/project/list/1?cid=294"}]
             * title : 玩Android APP（MVP + RxJava2 + Retrofit2 + Dagger2）
             * type : 0
             * userId : -1
             * visible : 1
             * zan : 0
             */
            public String apkLink;
            public String author;
            public int chapterId;
            public String chapterName;
            public boolean collect;
            public int courseId;
            public String desc;
            public String envelopePic;
            public boolean fresh;
            public int id;
            public String link;
            public String niceDate;
            public String origin;
            public String projectLink;
            public long publishTime;
            public int superChapterId;
            public String superChapterName;
            public String title;
            public int type;
            public int userId;
            public int visible;
            public int zan;
            public List<TagsBean> tags;

            public static class TagsBean {
                /**
                 * name : 项目
                 * url : /project/list/1?cid=294
                 */

                public String name;
                public String url;
            }
        }
    }

