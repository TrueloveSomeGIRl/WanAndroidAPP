package com.cxw.wanandroidapp_project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import cxw.com.commonapp.base.RouterPath;
import cxw.com.commonapp.eventbus.Action;
import cxw.com.commonapp.eventbus.EventTarget;
import cxw.com.commonapp.mvp.factory.CreatePresenter;
import cxw.com.commonapp.mvp.view.BaseMvpFragment;

@CreatePresenter(ProjectPresenter.class)
@Route(path = RouterPath.PROJECT_FRAGMENT) // 路由地址，必须注明
public class ProjectFragment extends BaseMvpFragment<ProjectPresenter> {


    @BindView(R2.id.project_ctl)
    SlidingTabLayout stl;
    @BindView(R2.id.project_vp)
    ViewPager Vp;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private List<String> TabName = new ArrayList<>();
    private ProjectTreePagerAdapter mProjectTreePagerAdapter;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        getMvpPresenter().getProjectName();
    }

    @Override
    protected <T> boolean eventBusHandle(EventTarget<T> event) {
        if (event.action == Action.SUCCESS) {
            T object = event.obj;
            if (event.tag.equals("projectName")) {
                List<ProjectTabNameBean> projectNameBeanList = (List<ProjectTabNameBean>) object;
                int TabSize = projectNameBeanList.size();
                for (int i = 0; i < TabSize; i++) {
                    TabName.add(projectNameBeanList.get(i).name);
                    mFragments.add(ProjectTreeFragment.newInstance(projectNameBeanList.get(i).id));
                }
                mProjectTreePagerAdapter = new ProjectTreePagerAdapter(getChildFragmentManager(), TabName, mFragments);
                Vp.setAdapter(mProjectTreePagerAdapter);
                stl.setViewPager(Vp);

            }
            return true;
        }

        return false;
    }


}
