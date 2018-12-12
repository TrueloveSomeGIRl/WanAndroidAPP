package com.cxw.wanandroidapp_project;

import java.util.List;

public class ProjectTreeBean {

        public int curPage;
        public int offset;
        public boolean over;
        public int pageCount;
        public int size;
        public int total;
        public List<ProjectTree> datas;

        public static class ProjectTree {
            /**
             * apkLink :
             * author : OnexZgj
             * chapterId : 294
             * chapterName : 完整项目
             * collect : false
             * courseId : 13
             * desc : 使用MVP+Dagger2+Retrofit+Rxjava2+RxLifecycle+ARouter框架构建
             一般使用这套框架的项目比较复杂，所以该项目虽然很小，但五脏俱全，很适合学习和研究！
             * envelopePic : http://wanandroid.com/blogimgs/a3e76293-7182-4060-8c0c-ab02328d027e.png
             * fresh : false
             * id : 3448
             * link : http://www.wanandroid.com/blog/show/2377
             * niceDate : 2018-09-29
             * origin :
             * projectLink : https://github.com/OnexZgj/ForYou
             * publishTime : 1538224604000
             * superChapterId : 294
             * superChapterName : 开源项目主Tab
             * tags : [{"name":"项目","url":"/project/list/1?cid=294"}]
             * title :  TODO 最适合练习主流框架的应用
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
