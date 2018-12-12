package cxw.com.commonapp.eventbus;




public class EventTarget<T> {
    /**
     * 动作
     */
    public Action action;
    /**
     * 传递的数据
     */
    public T obj;
    /**
     * 区别不同的网络请求
     */
    public String tag;
    /**
     * 分页
     */
    public int page;

    public EventTarget(T t) {
        this(Action.SUCCESS, t, null, 0);
    }

    /**
     * 分页
     *
     * @param t
     * @param page
     */
    public EventTarget(T t, int page) {
        this(Action.SUCCESS, t, null, page);
    }

    /**
     * @param t
     * @param tag
     * @param page
     */
    public EventTarget(T t, String tag, int... page) {
        this(Action.SUCCESS, t, tag, page.length == 0 ? 0 : page[0]);
    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Action~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public EventTarget(Action action, T t) {
        this(action, t, null, 0);
    }

    /**
     * @param action
     * @param t
     * @param tag    区别不同的网络请求
     */
    public EventTarget(Action action, T t, String tag) {
        this(action, t, tag, 0);
    }

    /**
     * @param action
     * @param t
     * @param tag    区别不同的网络请求
     * @param page   分页
     */
    public EventTarget(Action action, T t, String tag, int page) {
        this.action = action;
        this.obj = t;
        this.tag = tag;
        this.page = page;
    }

}
