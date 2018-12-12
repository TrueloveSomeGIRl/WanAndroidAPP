package com.cxw.wanandroidapp_system;

import java.util.List;

public class SystemTree {



        public int courseId;
        public int id;
        public String name;
        public int order;
        public int parentChapterId;
        public int visible;
        public List<ChildrenBean> children;

        public static class ChildrenBean {
            public int courseId;
            public int id;
            public String name;
            public int order;
            public int parentChapterId;
            public int visible;
            public List<?> children;
        }
}

