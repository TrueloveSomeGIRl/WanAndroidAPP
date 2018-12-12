package cxw.com.commonapp.eventbus;

public enum Action {
    /**
     * 到底要不要用enum
     *   还是用注解
     */
    SUCCESS,
    showLoading,
    hideLoading,
    makeMessage,
    noNetWork,
    noLogin,
    onError,

}