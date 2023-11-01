package com.dclee.recovery.base;

import com.dclee.recovery.MyApp;
import com.dclee.recovery.bean.db.SortInBean;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * db.save(entity);//保存成功之后【不会】对user的主键进行赋值绑定
 * db.saveOrUpdate(entity);//如果一个对象主键为null则会新增该对象,
 * 成功之后【会】对user的主键进行赋值绑定,否则根据主键去查找更新
 * db.saveBindingId(entity);//保存成功之后【会】对user的主键进行赋值绑定,并返回保存是否成功
 */

public class DbHelper {

    private DbManager dbManager;

    public  DbHelper () throws DbException {
        this.dbManager = x.getDb(MyApp.getDaoConfig());
    }


    public boolean addSortInBean(SortInBean data){
        //方法一：
        try {
            dbManager.saveBindingId(data);
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean dbDeleteId( int id,String receiveid){
        try {
            WhereBuilder whereBuilder = WhereBuilder.b();
            whereBuilder.and("ID", "=", id).and("receiveId","=",receiveid);
            dbManager.delete(SortInBean.class, whereBuilder);
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean dbDeleteAll(String receiveid){
        try {
            WhereBuilder whereBuilder = WhereBuilder.b();
            whereBuilder.and("receiveId", "=", receiveid);
            dbManager.delete(SortInBean.class, whereBuilder);
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<SortInBean> dbFindSortInBeanById(String id){

        //使用selector进行复合查询
        List<SortInBean> datas = null;
        try {
            datas = dbManager.selector(SortInBean.class)
                    .where("receiveId", "=", id)
                    .findAll();
        } catch (DbException e) {
            e.printStackTrace();
            return datas;
        }

        return datas;
    }



    /**
     * 现在数据库版本号
     */
    public int getDBVersiona(){
        int version = dbManager.getDatabase().getVersion() ;
        return version;
    }

}
