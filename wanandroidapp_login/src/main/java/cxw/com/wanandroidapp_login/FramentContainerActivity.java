package cxw.com.wanandroidapp_login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.Explode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;


import cxw.com.commonapp.base.BaseActivity;
import cxw.com.commonapp.base.BaseFragment;
import cxw.com.commonapp.base.RouterPath;
@SuppressLint("NewApi")
@Route(path = RouterPath.FRAMENT_CONTAINER_ACTIVITY) // 路由地址，必须注明
public class FramentContainerActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    FrameLayout homeFragmentContainerFl;
    BottomNavigationView homeBnv;
    private long mExitTime;

    /**
     * fragment数组
     */
    private Fragment[] mFragments;

    /**
     * 当前的fragment
     */
    private Fragment mCurrentFragment;
    private static final String TAG = "FrameworkActivity";
    private BaseFragment HomeFragment;
    private BaseFragment ProjectFragment;
    private BaseFragment SystemFragment;
    private BaseFragment MyFragment;


    @Override
    public int setLayoutId() {
        return R.layout.activity_frament_container;
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initView(Bundle savedInstanceState) {
//         Explode explode = new Explode();
//        explode.setDuration(500);
//        getWindow().setExitTransition(explode);
//        getWindow().setEnterTransition(explode);
        homeFragmentContainerFl = findViewById(R.id.home_fragment_container_fl);
        homeBnv = findViewById(R.id.home_bnv);
        homeBnv.setOnNavigationItemSelectedListener(this);
        getFragments(); //获取fragment
    }

    @Override
    protected void initListener() {

    }

    /**
     * 通过路由路径获取Fragment
     */
    private void getFragments() {
        HomeFragment = (BaseFragment) ARouter.getInstance().build(RouterPath.HOME_FRAGMENT).navigation();
        ProjectFragment = (BaseFragment) ARouter.getInstance().build(RouterPath.PROJECT_FRAGMENT).navigation();
        SystemFragment = (BaseFragment) ARouter.getInstance().build(RouterPath.SYSTEM_FRAGMENT).navigation();
        MyFragment = (BaseFragment) ARouter.getInstance().build(RouterPath.MY_FRAGMENT).navigation();

        mFragments = new Fragment[]{HomeFragment, ProjectFragment, SystemFragment, MyFragment};
        Fragment fragment = mFragments[0];
        addFragment(fragment, R.id.home_fragment_container_fl);
        mCurrentFragment = HomeFragment;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.navigation_home) {
            switchFragment(0);
        } else if (i == R.id.navigation_project) {
            switchFragment(1);
        } else if (i == R.id.navigation_system) {
            switchFragment(2);
        } else if (i == R.id.navigation_my) {
            switchFragment(3);
        }
        return false;
    }

    /**
     * 切换fragment
     */
    private void switchFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = mFragments[position];

        if (!fragment.isAdded()) {
            transaction.hide(mCurrentFragment)
                    //设置tag,当activity意外挂了，会出现fragment重叠问题
                    .add(R.id.home_fragment_container_fl, fragment, fragment.getClass().getName())
                    .show(fragment)
                    .commit();
            Log.e(TAG, "被添加: " + fragment.getClass().hashCode());
        } else {
            //当前fragment不相等再显示
            if (mCurrentFragment != fragment) {
                transaction.hide(mCurrentFragment).show(fragment).commit();
                Log.e(TAG, "被隐藏: " + fragment);
            }
        }
        mCurrentFragment = fragment;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 绑定物理返回键
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // 如果两次按键时间间隔大于2000毫秒，则不退出
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                // 记录exitTime
                mExitTime = System.currentTimeMillis();
            } else {
                // 否则退出程序
                finish();
//                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
