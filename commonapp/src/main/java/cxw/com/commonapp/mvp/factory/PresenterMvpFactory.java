package cxw.com.commonapp.mvp.factory;


import cxw.com.commonapp.mvp.presenter.BaseMvpPresenter;

/**
 *  Presenter工厂接口
 */
public interface PresenterMvpFactory<P extends BaseMvpPresenter> {

    /**
     * 创建Presenter的接口方法
     * @return 需要创建的Presenter
     */
    P createMvpPresenter();
}
