package com.dclee.recovery;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.dclee.recovery.base.CacheUtil;
import com.dclee.recovery.helper.PushHelper;
import com.dclee.recovery.util.FileUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sunmi.utils.AidlUtil;
import com.umeng.commonsdk.UMConfigure;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.xiasuhuei321.loadingdialog.manager.StyleManager;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

import java.io.File;

public class MyApp extends Application {

    private boolean isAidl;

    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    public static String UrlAudio = "/rec_staff";

    public boolean isAidl() {
        return isAidl;
    }

    public static DbManager.DaoConfig daoConfig;


    public void setAidl(boolean aidl) {
        isAidl = aidl;
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        x.Ext.init(this);
        CacheUtil.init(this);
        // 是否同意隐私政策，默认为false
        SDKInitializer.setAgreePrivacy(getApplicationContext(), true);
        SDKInitializer.initialize(getApplicationContext());

        ZXingLibrary.initDisplayOpinion(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        StyleManager loadingManager = new StyleManager();
        loadingManager.Anim(false).repeatTime(0).contentSize(-1).intercept(true);

        LoadingDialog.initStyle(loadingManager);

        isAidl = true;
        AidlUtil.getInstance().connectPrinterService(this);

        UMConfigure.setLogEnabled(true);
        //预初始化
        PushHelper.preInit(this);
        //初始化
        initPushSDK();
        //UmengHelper.getInstance().init();
        initDb();
    }

    /**
     * 初始化推送SDK
     */
    private void initPushSDK() {
        /*
         * 若用户已同意隐私政策，直接初始化；
         * 若用户未同意隐私政策，待用户同意后，再通过PushHelper.init(...)方法初始化。
         */
        boolean agreed = true;
        if (agreed) {
            //可以在线程中执行初始化
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PushHelper.init(getApplicationContext());
                }
            }).start();
        }
    }

    public static DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    protected void initDb() {
        //本地数据的初始化
        daoConfig = new DbManager.DaoConfig()
                .setDbName("xutils_db") //设置数据库名
                .setDbVersion(1) //设置数据库版本,每次启动应用时将会检查该版本号,
                //发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
                .setAllowTransaction(true)//设置是否开启事务,默认为false关闭事务
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> entity) {

                    }
                })//设置数据库创建时的Listener
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        if( oldVersion < newVersion ){
                            db.getDatabase().setVersion( newVersion);
                        }
                    }
                });//设置数据库升级时的Listener,这里可以执行相关数据库表的相关修改,比如alter语句增加字段等
        //.setDbDir(null);//设置数据库.db文件存放的目录,默认为包名下databases目录下

    }


}
