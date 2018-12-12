package cxw.com.commonapp.utils;

import android.database.sqlite.SQLiteDatabase;

import cxw.com.commonapp.base.BaseApplication;
import cxw.com.wanandroidapp.history.DaoMaster;
import cxw.com.wanandroidapp.history.DaoSession;

public class GreenDaoUtils {
    private volatile static GreenDaoUtils greenDaoUtils;
    private DaoSession daoSession;
    private SQLiteDatabase database;

    private GreenDaoUtils() {

    }

    public synchronized static GreenDaoUtils getInstance() {
        if (greenDaoUtils == null) {
            synchronized (GreenDaoUtils.class) {
                if (greenDaoUtils == null) {
                    greenDaoUtils = new GreenDaoUtils();
                }
            }
        }

        return greenDaoUtils;
    }

    //初始化数据   这个方法在Application里面调用
    public void init(String dbName) {
        //调用Application里面的上下文   参数二为数据库名字
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(BaseApplication.getInstance(), dbName, null);

        database = helper.getWritableDatabase();

        DaoMaster daoMaster = new DaoMaster(database);

        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getSQLiteDatabase() {
        return database;
    }
}
