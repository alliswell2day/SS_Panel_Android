package com.example.huangzumou.myapplication.model.db;

import android.content.Context;
import java.util.Date;


import com.example.huangzumou.myapplication.app.Constants;
import com.example.huangzumou.myapplication.model.bean.RealmUserBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

import com.example.huangzumou.myapplication.util.DateUtil;
import com.example.huangzumou.myapplication.util.LogUtil;
import com.example.huangzumou.myapplication.util.TokenUtil;

/**
 * Created by codeest on 16/8/16.
 */

public class RealmHelper {

    //private static final String DB_NAME = "myRealm.realm";

    private Realm mRealm;


    public RealmHelper(Context mContext) {
        /*
        mRealm = Realm.getInstance(new RealmConfiguration.Builder(mContext)
                .deleteRealmIfMigrationNeeded()
                .name(Constants.DB_NAME)
                .build());
                */

        Realm.init(mContext);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Constants.DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
// Use the config
        mRealm = Realm.getInstance(config);

    }


    /**
     * 增加User 记录
     */

    public void insertUserBean(RealmUserBean realmUserBean){
        mRealm.beginTransaction();
        //realmUserBean.setLastLogin(this.getNow()); //更新时间
        mRealm.copyToRealmOrUpdate(realmUserBean);
        mRealm.commitTransaction();
    }

    /**
     * 查询 by email
     */

    public RealmUserBean queryByEmail(String email){
        RealmResults<RealmUserBean> results = mRealm.where(RealmUserBean.class).findAll();
        for(RealmUserBean item : results){
            //LogUtil.i(item.getEmail()+" : "+item.getExpireTime()+" : "+this.getNow() + " : "+ item.getLastLogin());
            if(item.getEmail().equals(email) && !TokenUtil.checkTokenExpire(item)){
                //LogUtil.i("get");
                return mRealm.copyFromRealm(item);
            }
        }
        return null;
    }


    /**
     * 删除记录
     *
     */

    public void deleteUser(String email){
        RealmUserBean realmUserBean = mRealm.where(RealmUserBean.class).equalTo("email",email).findFirst();
        mRealm.beginTransaction();
        if(realmUserBean != null){
            realmUserBean.deleteFromRealm();
        }
        mRealm.commitTransaction();
    }

    /**
     * 查出最后一次登录的帐号Token资料
     */

    public RealmUserBean queryLastUser(){
        RealmUserBean realmUserBean = mRealm.where(RealmUserBean.class).findFirst();

        if(realmUserBean != null && !TokenUtil.checkTokenExpire(realmUserBean)){
            return realmUserBean;
        }
        /*
        for(RealmUserBean item : results){
            //LogUtil.i(item.getEmail()+" : "+item.getExpireTime()+" : "+this.getNow());
            if(item.getExpireTime() > this.getNow()){
                //LogUtil.i("get");
                return mRealm.copyFromRealm(item);
            }
        }
        */
        return null;
    }


    /*
    private long getNow(){
        return (long)new Date().getTime()/1000;
    }
    */


}
